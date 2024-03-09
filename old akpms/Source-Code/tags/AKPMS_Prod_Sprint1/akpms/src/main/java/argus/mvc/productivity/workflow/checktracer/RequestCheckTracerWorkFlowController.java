/**
 *
 */
package argus.mvc.productivity.workflow.checktracer;

import java.beans.PropertyEditorSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import argus.domain.Files;
import argus.domain.RequestCheckTracerWorkFlow;
import argus.exception.ArgusException;
import argus.mvc.productivity.helper.ArProductivityHelper;
import argus.repo.processManual.ProcessManualDao;
import argus.repo.productivity.ArProductivityDao;
import argus.repo.productivity.workflow.requestForCheckTracer.RequestForCheckTracerDao;
import argus.util.AkpmsUtil;
import argus.util.Constants;
import argus.util.JsonDataWrapper;
import argus.util.RequestForCheckTracerJsonData;
import argus.validator.RequestCheckTracerWorkFlowValidator;

/**
 * @author vishal.joshi
 *
 */
@Controller
@RequestMapping(value = "/flows/checkTracer")
@SessionAttributes({ Constants.CHECKTRACER })
public class RequestCheckTracerWorkFlowController {

	private static final Log LOGGER = LogFactory
			.getLog(RequestCheckTracerWorkFlowController.class);

	private static final int ONE = 1;

	@Autowired
	private RequestCheckTracerWorkFlowValidator checkTracerWorkFlowValidator;

	@Autowired
	private RequestForCheckTracerDao checkTracerDao;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ProcessManualDao processManualDao;

	@Autowired
	private ArProductivityDao arProductivityDao;

	@InitBinder
	protected void initBinder(WebDataBinder binder) throws ArgusException {
		LOGGER.info("in [initBinder] method ");
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			public void setAsText(String value) {
				try {
					setValue(ArProductivityHelper.initBinder(value));
				} catch (ArgusException e) {
					LOGGER.error(Constants.EXCEPTION, e);
					setValue(null);
				}
			}

			public String getAsText() {
				if (getValue() != null) {
					return new SimpleDateFormat(Constants.DATE_FORMAT)
							.format((Date) getValue());
				}
				return "";
			}
		});
	}

	@RequestMapping(method = RequestMethod.GET)
	public String requestForCheckTraceList() {
		return "flows/arProductivity/requestForCheckTracerList";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String requestForCheckTraceLoad(Model model, WebRequest request,
			Map<String, Object> map,
			@RequestParam(required = false) String successUpdate) {
		LOGGER.info("in [requestForCheckTraceLoad] method");
		RequestCheckTracerWorkFlow checkTracerWorkFlow = null;
		if (request.getParameter(Constants.ARPRODUCTIVITY_ID) != null
				&& !request.getParameter(Constants.ARPRODUCTIVITY_ID)
						.equals("")) {

			try {
				checkTracerWorkFlow = checkTracerDao
						.getCheckTracerByArProductivityId((Integer.parseInt(request
								.getParameter(Constants.ARPRODUCTIVITY_ID))));

				model.addAttribute(Constants.CHECKTRACER, checkTracerWorkFlow);
				model.addAttribute(Constants.MODE, Constants.EDIT);
				map.put(Constants.OPERATION_TYPE, Constants.EDIT);
				map.put(Constants.BUTTON_NAME, Constants.UPDATE);
				map.put(Constants.READ_ONLY, true);
				map.put("showToManager", 1);

			} catch (Exception e) {
				checkTracerWorkFlow = new RequestCheckTracerWorkFlow();
				if (request.getParameter(Constants.ARPRODUCTIVITY_ID) != null) {
					try {
						checkTracerWorkFlow.setArProductivity(arProductivityDao
								.findById(Long.valueOf(request
										.getParameter(Constants.ARPRODUCTIVITY_ID))));
					} catch (Exception ex) {
						LOGGER.info(Constants.EXCEPTION, ex);
					}
				}
				map.put(Constants.CHECKTRACER, checkTracerWorkFlow);
				model.addAttribute(Constants.MODE, Constants.ADD);
				map.put(Constants.OPERATION_TYPE, Constants.ADD);
				map.put(Constants.BUTTON_NAME, Constants.SUBMIT_FOR_APPROVAL);
				map.put(Constants.READ_ONLY, false);
				map.put("showToManager", 0);
				LOGGER.error(e.getMessage(), e);
			}
		}
		return "flows/arProductivity/requestForCheckTracer";
	}

	@SuppressWarnings("finally")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String saveRequestCheckTracer(
			@Valid @ModelAttribute(Constants.CHECKTRACER) RequestCheckTracerWorkFlow reTracerWorkFlow,
			BindingResult result, Model model, WebRequest request,
			Map<String, Object> map, HttpSession session) {
		LOGGER.info("in [saveRequestCheckTracer] method");
		checkTracerWorkFlowValidator.validate(reTracerWorkFlow, result);
		if (!result.hasErrors()) {

			if (reTracerWorkFlow.getId() != null) {
				LOGGER.info("in [saveAdjustmentLog]adjLogWorkFlow is not null");
				try {
					if (reTracerWorkFlow.getAttachment().getAttachedFile()
							.getSize() != 0) {
						fileUpload(reTracerWorkFlow);
					}
					checkTracerDao.updateCheckTracerWorkFlow(reTracerWorkFlow);
					session.setAttribute(Constants.SUCCESS_UPDATE,
							"requestForCheckTracer.updatedSuccessfully");
					map.put("showFinalRemark", ONE);
				} catch (Exception e) {
					LOGGER.error(Constants.EXCEPTION, e);
				} finally {
					return "redirect:/flows/arProductivity";
				}
			} else {
				try {
					fileUpload(reTracerWorkFlow);
					checkTracerDao.addCheckTracerWorkFlow(reTracerWorkFlow);
					session.setAttribute(Constants.SUCCESS_UPDATE,
							"requestForCheckTracer.addedSuccessfully");

				} catch (Exception e) {
					LOGGER.error(Constants.EXCEPTION, e);
					return "flows/arProductivity/requestForCheckTracer";
				}
			}
			return "redirect:/flows/arProductivity";
		} else {
			if (reTracerWorkFlow != null && reTracerWorkFlow.getId() != null) {
				map.put(Constants.OPERATION_TYPE, Constants.EDIT);
				map.put(Constants.BUTTON_NAME, Constants.UPDATE);
				map.put(Constants.READ_ONLY, true);
				map.put("showToManager", 1);
			} else {
				map.put(Constants.OPERATION_TYPE, Constants.ADD);
				map.put(Constants.BUTTON_NAME, Constants.SUBMIT_FOR_APPROVAL);
				map.put(Constants.READ_ONLY, false);
				map.put("showToManager", 0);
			}
			return "flows/arProductivity/requestForCheckTracer";
		}
	}

	@RequestMapping(value = "/fileDownload", method = RequestMethod.GET)
	public void downloadAttachment(HttpServletRequest request,
			HttpServletResponse response, @RequestParam("id") Long id,
			HttpSession session) {
		LOGGER.info("In download method. Id = " + id);
		try {
			if (id != null) {
				Files file = processManualDao.getAttachedFile(id);

				String realPath = messageSource.getMessage(
						"attachments.storage.space.arProductivity", null,
						Locale.ENGLISH).trim();

				File systemFile = new File(realPath, file.getSystemName());
				InputStream is = new FileInputStream(systemFile);

				response.setHeader("Content-Disposition",
						"attachment; filename=" + file.getName());
				IOUtils.copy(is, response.getOutputStream());
				response.flushBuffer();
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
	}

	public void fileUpload(RequestCheckTracerWorkFlow reTracerWorkFlow) {

		try {
			Files newFile = reTracerWorkFlow.getAttachment();
			if (newFile.getAttachedFile().getSize() != 0) {

				MultipartFile file = newFile.getAttachedFile();
				if (null != file && file.getSize() > 0) {

					byte[] fileData = file.getBytes();
					String originalFileName = file.getOriginalFilename();
					Long timeMili = System.currentTimeMillis();
					StringBuilder systemName = new StringBuilder();
					if (newFile.getProcessManual() != null) {

						systemName.append(newFile.getProcessManual().getId());
						systemName.append("_");
						if (newFile.getSubProcessManualId() != null) {
							systemName.append(newFile.getSubProcessManualId());
						} else {
							systemName.append("0");
						}
						systemName.append("_");
					} else {
						LOGGER.info(" Process Manual not found in files object");
					}

					systemName.append(timeMili);
					systemName.append("_");
					systemName.append(originalFileName);

					String realPath = messageSource.getMessage(
							"attachments.storage.space.arProductivity", null,
							Locale.ENGLISH).trim();

					LOGGER.info("real Path = " + realPath);

					File dir = new File(realPath);
					if (!dir.exists()) {
						dir.mkdirs();
					}
					File fileNamePath = new File(dir, systemName.toString());
					OutputStream outputStream = new FileOutputStream(
							fileNamePath);
					outputStream.write(fileData);
					outputStream.close();

					newFile.setName(originalFileName);
					newFile.setSystemName(systemName.toString());
					if (reTracerWorkFlow.getId() != null) {
						checkTracerDao.updateAttachement(newFile);
					} else {
						checkTracerDao.saveAttachement(newFile);
					}
					// set message here
				} else {
					LOGGER.info("there is no attachment coming in file object");
				}
			} else {
				LOGGER.info("Attached file Object is coming null");
			}

		} catch (Exception e) {

		}
	}

	@RequestMapping(value = "/json", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonDataWrapper<RequestForCheckTracerJsonData> listAllRequestForCheckTracer(
			WebRequest request) {
		LOGGER.info("in [listAllArProductivity]json method");
		int page = 1;
		Map<String, String> orderClauses = new HashMap<String, String>();
		Map<String, String> whereClauses = new HashMap<String, String>();
		if (request != null) {
			orderClauses = ArProductivityHelper.getOrderClause(request);
			whereClauses = ArProductivityHelper.getWhereClause(request);
		} else {
			LOGGER.info("request object is coming null");
		}
		if (request.getParameter(Constants.AR_PROD_PATIENT_NAME) != null
				& request.getParameter(Constants.AR_PROD_PATIENT_NAME).trim()
						.length() > 0) {
			String patientName = request
					.getParameter(Constants.AR_PROD_PATIENT_NAME);
			whereClauses.put(Constants.AR_PROD_PATIENT_NAME, patientName);
		}
		if (request.getParameter("arProductivity.patientAccNo") != null
				& request.getParameter("arProductivity.patientAccNo").trim()
						.length() > 0) {
			String patientAccNo = request
					.getParameter("arProductivity.patientAccNo");
			whereClauses.put("arProductivity.patientAccNo", patientAccNo);
		}
		if (request.getParameter("checkNo") != null
				& request.getParameter("checkNo").trim().length() > 0) {
			String checkNo = request.getParameter("checkNo");
			whereClauses.put("checkNo", checkNo);
		}
		try {
			int totalRows = checkTracerDao.totalRecord(whereClauses);
			if (totalRows > 0) {
				List<RequestCheckTracerWorkFlow> rows = checkTracerDao.findAll(
						orderClauses, whereClauses, true);
				List<RequestForCheckTracerJsonData> rtjd = getJsonData(rows);
				JsonDataWrapper<RequestForCheckTracerJsonData> jdw = new JsonDataWrapper<RequestForCheckTracerJsonData>(
						page, totalRows, rtjd);
				return jdw;
			}
		} catch (ArgusException e) {
			LOGGER.info(Constants.EXCEPTION, e);
		}
		return null;
	}

	private List<RequestForCheckTracerJsonData> getJsonData(
			List<RequestCheckTracerWorkFlow> rows) {
		List<RequestForCheckTracerJsonData> requestForCheckTracerJsonData = new ArrayList<RequestForCheckTracerJsonData>();

		if (rows != null && rows.size() > 0) {
			for (RequestCheckTracerWorkFlow eTemp : rows) {
				RequestForCheckTracerJsonData rtjd = new RequestForCheckTracerJsonData();

				rtjd.setId(eTemp.getId());
				rtjd.setCashedDate(AkpmsUtil.akpmsDateFormat(
						eTemp.getCheckCashedDate(), Constants.DATE_FORMAT));
				rtjd.setCheckAmount(eTemp.getCheckAmount());
				rtjd.setCheckIssueDate(AkpmsUtil.akpmsDateFormat(
						eTemp.getCheckIssueDate(), Constants.DATE_FORMAT));
				rtjd.setPatientName(eTemp.getArProductivity().getPatientName());
				rtjd.setPatientAccNo(eTemp.getArProductivity()
						.getPatientAccNo());
				rtjd.setCheckNo(eTemp.getCheckNo());
				rtjd.setAttachmentId(eTemp.getAttachment().getId());
				rtjd.setAttachmentName(eTemp.getAttachment().getName());
				rtjd.setArProdId(eTemp.getArProductivity().getId());
				requestForCheckTracerJsonData.add(rtjd);
			}
		}
		return requestForCheckTracerJsonData;
	}
}
