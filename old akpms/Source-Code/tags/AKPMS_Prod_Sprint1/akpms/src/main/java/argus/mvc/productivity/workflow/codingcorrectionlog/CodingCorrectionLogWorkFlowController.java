package argus.mvc.productivity.workflow.codingcorrectionlog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import argus.domain.CodingCorrectionLogWorkFlow;
import argus.domain.Files;
import argus.exception.ArgusException;
import argus.mvc.productivity.helper.ArProductivityHelper;
import argus.repo.processManual.ProcessManualDao;
import argus.repo.productivity.ArProductivityDao;
import argus.repo.productivity.workflow.codingcorrectionlog.CodingCorrectionLogDao;
import argus.util.AkpmsUtil;
import argus.util.CodingCorrectionLogWorkFlowJsonData;
import argus.util.Constants;
import argus.util.JsonDataWrapper;
import argus.validator.CodingCorrectionLogWorkFlowValidator;

@Controller
@RequestMapping(value = "/flows/codingcorrectionlogworkflow")
@SessionAttributes({ "codingCorrectionLogWorkFlow" })
public class CodingCorrectionLogWorkFlowController {

	private static final Log LOGGER = LogFactory
			.getLog(CodingCorrectionLogWorkFlowController.class);

	@Autowired
	private CodingCorrectionLogDao codingCorrectionLogDao;

	@Autowired
	private CodingCorrectionLogWorkFlowValidator codingCorrectionLogWorkFlowValidator;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ProcessManualDao processManualDao;

	@Autowired
	private ArProductivityDao arProductivityDao;

	@RequestMapping(method = RequestMethod.GET)
	public String codingCorrectionLogWorkFlowList() {
		return "flows/arProductivity/codingCorrectionLogWorkflowList";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String codingCorrectionLogWorkFlowLoad(Map<String, Object> map,
			WebRequest request) {
		CodingCorrectionLogWorkFlow codingCorrectionLogWorkFlow = null;

		if (request.getParameter(Constants.ARPRODUCTIVITY_ID) != null
				&& !request.getParameter(Constants.ARPRODUCTIVITY_ID)
						.equals("")) {
			try {
				codingCorrectionLogWorkFlow = codingCorrectionLogDao
						.findByProductivityId(Long.valueOf(request
								.getParameter(Constants.ARPRODUCTIVITY_ID)));
				map.put("codingCorrectionLogWorkFlow",
						codingCorrectionLogWorkFlow);
				map.put("readOnly", true);
			} catch (Exception e) {

				codingCorrectionLogWorkFlow = new CodingCorrectionLogWorkFlow();
				if (request.getParameter(Constants.ARPRODUCTIVITY_ID) != null) {
					try {
						codingCorrectionLogWorkFlow
								.setArProductivity(arProductivityDao.findById(Long.valueOf(request
										.getParameter(Constants.ARPRODUCTIVITY_ID))));
					} catch (Exception ex) {
						LOGGER.info(Constants.EXCEPTION, ex);
					}
				}

				map.put("readOnly", false);

				LOGGER.info("Exception occured while fetching Coding Correction Log");
				LOGGER.info(Constants.EXCEPTION, e);
			}
		}
		map.put("codingCorrectionLogWorkFlow", codingCorrectionLogWorkFlow);
		return "flows/arproductivity/codingcorrectionlogworkflow";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String codingCorrectionLogWorkFlowProcess(
			@ModelAttribute("codingCorrectionLogWorkFlow") CodingCorrectionLogWorkFlow codingCorrectionLogWorkFlow,
			Map<String, Object> map, BindingResult result) {

		codingCorrectionLogWorkFlowValidator.validate(
				codingCorrectionLogWorkFlow, result);

		if (result.hasErrors()) {
			return "flows/arproductivity/codingcorrectionlogworkflow";
		}

		LOGGER.info("In codingCorrectionLogWorkFlowProcess method");
		if (codingCorrectionLogWorkFlow.getId() != null) {
			try {
				if (codingCorrectionLogWorkFlow.getAttachment()
						.getAttachedFile().getSize() != 0) {
					fileUpload(codingCorrectionLogWorkFlow);
				}
				codingCorrectionLogDao
						.updateCodingCorrectionLogWorkFlow(codingCorrectionLogWorkFlow);
			} catch (ArgusException e) {
				LOGGER.info("Exception occured while updating Coding Correction Log");
				LOGGER.info(Constants.EXCEPTION, e);
			}
		} else {
			try {
				fileUpload(codingCorrectionLogWorkFlow);
				codingCorrectionLogDao
						.addCodingCorrectionLogWorkFlow(codingCorrectionLogWorkFlow);
			} catch (ArgusException e) {
				LOGGER.info("Exception occured while adding Coding Correction Log");
				LOGGER.info(Constants.EXCEPTION, e);
			}
		}
		LOGGER.info("Out codingCorrectionLogWorkFlowProcess method");
		return "redirect:/flows/codingcorrectionlogworkflow";
	}

	@RequestMapping(value = "/json", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonDataWrapper<CodingCorrectionLogWorkFlowJsonData> listAllCodingCorrectionLog(
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
		if (request.getParameter("dosFrom") != null
				& request.getParameter("dosFrom").trim().length() > 0) {
			String dosFrom = request.getParameter("dosFrom");
			whereClauses.put("dosFrom", dosFrom);
		}
		if (request.getParameter("dosTo") != null
				& request.getParameter("dosTo").trim().length() > 0) {
			String dosTo = request.getParameter("dosTo");
			whereClauses.put("dosTo", dosTo);
		}

		try {
			int totalRows = codingCorrectionLogDao.totalRecord(whereClauses);
			if (totalRows > 0) {
				List<CodingCorrectionLogWorkFlow> rows = codingCorrectionLogDao
						.findAll(orderClauses, whereClauses, true);
				List<CodingCorrectionLogWorkFlowJsonData> ccjd = getJsonData(rows);
				JsonDataWrapper<CodingCorrectionLogWorkFlowJsonData> jdw = new JsonDataWrapper<CodingCorrectionLogWorkFlowJsonData>(
						page, totalRows, ccjd);
				return jdw;
			}
		} catch (ArgusException e) {
			LOGGER.info(Constants.EXCEPTION, e);
		}
		return null;
	}

	private List<CodingCorrectionLogWorkFlowJsonData> getJsonData(
			List<CodingCorrectionLogWorkFlow> rows) {
		List<CodingCorrectionLogWorkFlowJsonData> codingCorrectionLogWorkFlowJsonData = new ArrayList<CodingCorrectionLogWorkFlowJsonData>();

		if (rows != null && rows.size() > 0) {
			for (CodingCorrectionLogWorkFlow eTemp : rows) {
				CodingCorrectionLogWorkFlowJsonData ccjd = new CodingCorrectionLogWorkFlowJsonData();

				ccjd.setId(eTemp.getId());
				ccjd.setPatientAccNo(eTemp.getArProductivity()
						.getPatientAccNo());
				ccjd.setPatientName(eTemp.getArProductivity().getPatientName());
				ccjd.setBatchNo(eTemp.getBatchNo());
				ccjd.setSeqNo(eTemp.getSequenceNo());
				ccjd.setProvider(eTemp.getArProductivity().getDoctor()
						.getName());
				ccjd.setDos(AkpmsUtil.akpmsDateFormat(eTemp.getArProductivity()
						.getDos(), Constants.DATE_FORMAT));
				ccjd.setArProdId(eTemp.getArProductivity().getId());
				ccjd.setAttachmentId(eTemp.getAttachment().getId());
				ccjd.setAttachmentName(eTemp.getAttachment().getName());

				codingCorrectionLogWorkFlowJsonData.add(ccjd);
			}
		}
		return codingCorrectionLogWorkFlowJsonData;
	}

	public void fileUpload(
			CodingCorrectionLogWorkFlow codingCorrectionLogWorkFlow) {

		try {
			Files newFile = codingCorrectionLogWorkFlow.getAttachment();
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
					if (codingCorrectionLogWorkFlow.getId() != null) {
						codingCorrectionLogDao.updateAttachement(newFile);
					} else {
						codingCorrectionLogDao.saveAttachement(newFile);
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
}
