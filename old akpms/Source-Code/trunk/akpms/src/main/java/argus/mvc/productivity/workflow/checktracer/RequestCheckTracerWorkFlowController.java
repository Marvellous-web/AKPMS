/**
 *
 */
package argus.mvc.productivity.workflow.checktracer;

import java.beans.PropertyEditorSupport;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;

import org.apache.log4j.Logger;
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

import argus.domain.Doctor;
import argus.domain.Files;
import argus.domain.RequestCheckTracerWorkFlow;
import argus.domain.User;
import argus.exception.ArgusException;
import argus.mvc.productivity.helper.ArProductivityHelper;
import argus.repo.processManual.ProcessManualDao;
import argus.repo.productivity.ArProductivityDao;
import argus.repo.productivity.workflow.requestForCheckTracer.RequestForCheckTracerDao;
import argus.util.AkpmsUtil;
import argus.util.Constants;
import argus.util.JsonDataWrapper;
import argus.util.RequestForCheckTracerJsonData;
import argus.util.UserXstreamConverter;
import argus.validator.RequestCheckTracerWorkFlowValidator;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author vishal.joshi
 *
 */
@Controller
@RequestMapping(value = "/flows/checkTracer")
@SessionAttributes({ Constants.CHECKTRACER })
public class RequestCheckTracerWorkFlowController {

	private static final Logger LOGGER = Logger
			.getLogger(RequestCheckTracerWorkFlowController.class);

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

	private String xmlString;

	private Map<String, String> printReportCriteria;

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
	public String requestForCheckTraceList(Model model, HttpSession session) {
		String success = (String) session
				.getAttribute(Constants.SUCCESS_UPDATE);
		if (success != null) {
			model.addAttribute("success",
					messageSource.getMessage(success, null, Locale.ENGLISH));
		}

		session.removeAttribute(Constants.SUCCESS_UPDATE);
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
				map.put(Constants.READ_ONLY, false);
				map.put("showToManager", 1);
			} catch (Exception e) {
				checkTracerWorkFlow = new RequestCheckTracerWorkFlow();

				if (request.getParameter(Constants.ARPRODUCTIVITY_ID) != null) {
					try {
						checkTracerWorkFlow
								.setArProductivity(arProductivityDao.findById(Long.valueOf(request
										.getParameter(Constants.ARPRODUCTIVITY_ID))));
					} catch (Exception ex) {
						LOGGER.info(Constants.EXCEPTION, ex);
					}
				}

				model.addAttribute(Constants.MODE, Constants.ADD);
				map.put(Constants.CHECKTRACER, checkTracerWorkFlow);
				map.put(Constants.OPERATION_TYPE, Constants.ADD);
				map.put(Constants.BUTTON_NAME, Constants.SUBMIT_FOR_APPROVAL);
				map.put(Constants.READ_ONLY, false);
				map.put("showToManager", 0);

				LOGGER.error(e.getMessage(), e);
			}
			model.addAttribute("AR_PRODUCTIVITY_ID",
					request.getParameter(Constants.ARPRODUCTIVITY_ID));
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
			if (request.getParameter(Constants.ARPRODUCTIVITY_ID) != null) {
				LOGGER.info("push AR productivity into coding correction");
				try {
					reTracerWorkFlow
							.setArProductivity(arProductivityDao.findById(Long.parseLong(request
									.getParameter(Constants.ARPRODUCTIVITY_ID))));
				} catch (Exception ex) {
					LOGGER.info(Constants.EXCEPTION, ex);
				}
			}
			if (reTracerWorkFlow.getId() != null) {
				LOGGER.info("in [saveAdjustmentLog]reTracerWorkFlow id is not null");
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
					return "redirect:/flows/checkTracer";
				}
			} else {
				LOGGER.info("going to add CheckTracer");
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

			return "redirect:/flows/checkTracer";
		} else {
			model.addAttribute("AR_PRODUCTIVITY_ID",
					request.getParameter(Constants.ARPRODUCTIVITY_ID));
			LOGGER.info("in error section");
			if (reTracerWorkFlow != null && reTracerWorkFlow.getId() != null) {
				map.put(Constants.OPERATION_TYPE, Constants.EDIT);
				map.put(Constants.BUTTON_NAME, Constants.UPDATE);
				map.put(Constants.READ_ONLY, false);
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
			page = Integer.parseInt(request.getParameter(Constants.PAGE));
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

		printReportCriteria = whereClauses;
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

	@RequestMapping(value = "/print", method = RequestMethod.GET)
	public String getPrint(Map<String, String> map) {

		try {
			List<RequestCheckTracerWorkFlow> rows = checkTracerDao.findAll(
					null, printReportCriteria, true);

			if (rows != null && !rows.isEmpty()) {
				xmlString = getXmlData(rows);
				xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
						+ xmlString;
			} else {
				xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> ";
				xmlString = xmlString + "<empty>No Record found.</empty>";
			}
			LOGGER.info("XML = " + xmlString);
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
		map.put("title", "Request Check Tracer Work Flow List");
		map.put("path", "/flows/checkTracer/print_report");
		return "chargeBatchProcessingPrintReport";
	}

	private String getXmlData(
			List<RequestCheckTracerWorkFlow> requestCheckTracerWorkFlowList)
			throws Exception {
		XStream xstream = new XStream(new DomDriver());
		xstream.setMode(XStream.NO_REFERENCES);
		xstream.processAnnotations(RequestCheckTracerWorkFlow.class);
		xstream.processAnnotations(User.class);
		xstream.processAnnotations(Doctor.class);
		xstream.registerConverter(new UserXstreamConverter());
		return xstream.toXML(requestCheckTracerWorkFlowList);
	}

	@RequestMapping(value = "/print_report", method = RequestMethod.GET, produces = "application/xml")
	@ResponseBody
	public String generateXML(WebRequest request, HttpSession session) {
		LOGGER.info("IN /print_report");
		if (request != null) {
			if (request.getParameter(Constants.PARAM) != null
					&& request.getParameter(Constants.PARAM).equals("xml")) {
				LOGGER.info("got xml");
				return xmlString;
			} else if (request.getParameter(Constants.PARAM) != null
					&& request.getParameter(Constants.PARAM).equals("xsl")) {
				try {
					ServletContext context = session.getServletContext();
					String realPath = context.getRealPath("/resources/xsl");
					LOGGER.info("XSL real path = " + realPath);
					File systemFile = new File(realPath,
							"RequestCheckTracerWorkFlowStylesheet.xsl");
					InputStream is = new FileInputStream(systemFile);
					char[] cbuf = new char[is.available()];
					BufferedReader bReader = new BufferedReader(
							new InputStreamReader(is));
					bReader.read(cbuf);
					String xslData = String.valueOf(cbuf);
					LOGGER.info("got xsl");
					bReader.close();
					return xslData;
				} catch (Exception e) {
					LOGGER.error(Constants.EXCEPTION, e);
					return "";
				}
			}
		}
		return "";
	}
}
