package argus.mvc.productivity.workflow.codingcorrectionlog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import argus.domain.ArProductivity;
import argus.domain.CodingCorrectionLogWorkFlow;
import argus.domain.Department;
import argus.domain.Doctor;
import argus.domain.Files;
import argus.domain.Insurance;
import argus.domain.User;
import argus.exception.ArgusException;
import argus.mvc.productivity.helper.ArProductivityHelper;
import argus.repo.department.DepartmentDao;
import argus.repo.doctor.DoctorDao;
import argus.repo.file.FileDao;
import argus.repo.insurance.InsuranceDao;
import argus.repo.processManual.ProcessManualDao;
import argus.repo.productivity.ArProductivityDao;
import argus.repo.productivity.workflow.codingcorrectionlog.CodingCorrectionLogDao;
import argus.util.AkpmsUtil;
import argus.util.CodingCorrectionLogWorkFlowJsonData;
import argus.util.Constants;
import argus.util.JsonDataWrapper;
import argus.util.UserXstreamConverter;
import argus.validator.CodingCorrectionLogWorkFlowValidator;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

@Controller
@RequestMapping(value = "/flows/codingcorrectionlogworkflow")
@SessionAttributes({ "codingCorrectionLogWorkFlow" })
@Scope("session")
public class CodingCorrectionLogWorkFlowController {

	private static final Logger LOGGER = Logger
			.getLogger(CodingCorrectionLogWorkFlowController.class);

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

	@Autowired
	private InsuranceDao insuranceDao;

	@Autowired
	private DoctorDao doctorDao;

	@Autowired
	private FileDao fileDao;

	@Autowired
	private DepartmentDao departmentDao;

	private String xmlString;

	private static final String INSURANCE_LIST = "insuranceList";

	private static final String DOCTOR_LIST = "doctorList";

	private Map<String, String> printReportCriteria;

	@RequestMapping(method = RequestMethod.GET)
	public String codingCorrectionLogWorkFlowList(Model model,
			HttpSession session, HttpServletRequest request) {
		String success = (String) session
				.getAttribute(Constants.SUCCESS_UPDATE);
		if (success != null) {
			// model.addAttribute("success",
			// messageSource.getMessage(success, null, Locale.ENGLISH));
			model.addAttribute("success", success);
		}

		String workflow = request.getParameter(Constants.WORKFLOW);
		if (workflow != null) {
			model.addAttribute(Constants.WORKFLOW, workflow);
		}

		session.removeAttribute(Constants.SUCCESS_UPDATE);

		try {
			Department dept = departmentDao.findById(
					Long.parseLong(Constants.AR_DEPARTMENT_ID), true);
			model.addAttribute(Constants.TEAM_LIST, dept.getDepartments());
		} catch (Exception e) {
			model.addAttribute(Constants.TEAM_LIST, null);
		}

		return "flows/arProductivity/codingCorrectionLogWorkflowList";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String codingCorrectionLogWorkFlowLoad(HttpServletRequest request,
			Model model) {
		CodingCorrectionLogWorkFlow codingCorrectionLogWorkFlow = null;

		if (request.getParameter(Constants.ARPRODUCTIVITY_ID) != null
				&& !request.getParameter(Constants.ARPRODUCTIVITY_ID)
						.equals("")) {
			try {
				codingCorrectionLogWorkFlow = codingCorrectionLogDao
						.findByProductivityId(Long.valueOf(request
								.getParameter(Constants.ARPRODUCTIVITY_ID)));
				// map.put("readOnly", true);
			} catch (Exception e) {
				// no entry founf for coding correction corresponding to AR
				// productivity.
				codingCorrectionLogWorkFlow = new CodingCorrectionLogWorkFlow();

				try {
					ArProductivity arProductivity = arProductivityDao
							.findById(Long.parseLong(request
									.getParameter(Constants.ARPRODUCTIVITY_ID)));
					codingCorrectionLogWorkFlow
							.setArProductivity(arProductivity);

					// set values from productivity in case of add
					codingCorrectionLogWorkFlow.setDoctor(arProductivity
							.getDoctor());
					codingCorrectionLogWorkFlow.setInsurance(arProductivity
							.getInsurance());
					codingCorrectionLogWorkFlow.setRemark(arProductivity
							.getRemark());
					codingCorrectionLogWorkFlow.setCpt(arProductivity.getCpt());
					codingCorrectionLogWorkFlow.setBalanceAmt(arProductivity
							.getBalanceAmt());
					codingCorrectionLogWorkFlow.setDos(arProductivity.getDos());

					// model.addAttribute("AR_PRODUCTIVITY_ID",
					// request.getParameter(Constants.ARPRODUCTIVITY_ID));
				} catch (Exception ex) {
					LOGGER.info(Constants.EXCEPTION, ex);
				}

				// map.put("readOnly", false);
				LOGGER.info("Exception occured while fetching Coding Correction Log");
				LOGGER.info(Constants.EXCEPTION, e);
			}

			model.addAttribute("AR_PRODUCTIVITY_ID",
					request.getParameter(Constants.ARPRODUCTIVITY_ID));
		}

		model.addAttribute("codingCorrectionLogWorkFlow",
				codingCorrectionLogWorkFlow);
		populateValuesOnUI(model, request);

		if (null != request.getParameter(Constants.POPUP)) {
//			map.put(Constants.POPUP, true);
			return "flows/arProductivity/codingcorrectionlogworkflow" +Constants.POPUP;
		}

//		map.put(Constants.POPUP, false);

		return "flows/arproductivity/codingcorrectionlogworkflow";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String codingCorrectionLogWorkFlowProcess(
			@ModelAttribute("codingCorrectionLogWorkFlow") CodingCorrectionLogWorkFlow codingCorrectionLogWorkFlow,
			BindingResult result, HttpServletRequest request, Model model,
			HttpSession session) {

		codingCorrectionLogWorkFlowValidator.validate(
				codingCorrectionLogWorkFlow, result);

		if (result.hasErrors()) {
			model.addAttribute("AR_PRODUCTIVITY_ID",
					request.getParameter(Constants.ARPRODUCTIVITY_ID));
			codingCorrectionLogWorkFlow.setNextAction(request
					.getParameter("currentAction"));
			populateValuesOnUI(model, request);
			if (null != request.getParameter(Constants.POPUP)) {
				return "flows/arProductivity/codingcorrectionlogworkflow" + Constants.POPUP;
			}
			return "flows/arproductivity/codingcorrectionlogworkflow";
		}
		LOGGER.info("In codingCorrectionLogWorkFlowProcess method");
		// push AR productivity into coding correction
		if (request.getParameter(Constants.ARPRODUCTIVITY_ID) != null) {
			LOGGER.info("push AR productivity into coding correction");
			try {
				codingCorrectionLogWorkFlow.setArProductivity(arProductivityDao
						.findById(Long.parseLong(request
								.getParameter(Constants.ARPRODUCTIVITY_ID))));
				// model.addAttribute("AR_PRODUCTIVITY_ID",
				// request.getParameter(Constants.ARPRODUCTIVITY_ID));
			} catch (Exception ex) {
				LOGGER.info(Constants.EXCEPTION, ex);
			}
		}

		if (codingCorrectionLogWorkFlow.getId() != null) {
			LOGGER.info("in edit mode");
			String isFileDeleted = request.getParameter("fileDeleted");
			try {

				if (codingCorrectionLogWorkFlow.getAttachment() != null
						&& codingCorrectionLogWorkFlow.getAttachment()
								.getAttachedFile().getSize() != 0) {
					AkpmsUtil.fileUpload(
							codingCorrectionLogWorkFlow.getAttachment(),
							fileDao, messageSource);
				} else if (isFileDeleted != null
						&& !isFileDeleted.trim().isEmpty()
						&& isFileDeleted.equals("true")) {

					fileDao.deleteAttachedFile(codingCorrectionLogWorkFlow
							.getAttachment().getId());
					codingCorrectionLogWorkFlow.setAttachment(null);

				} else if (codingCorrectionLogWorkFlow.getAttachment() == null
						|| codingCorrectionLogWorkFlow.getAttachment().getId() == null) {
					codingCorrectionLogWorkFlow.setAttachment(null);
				}
				codingCorrectionLogDao
						.updateCodingCorrectionLogWorkFlow(codingCorrectionLogWorkFlow);
				session.setAttribute(Constants.SUCCESS_UPDATE,
						messageSource.getMessage("arProductivity.codingcorrectionworkflow.updatedSuccessfully", null, Locale.ENGLISH));
			} catch (ArgusException e) {
				LOGGER.info("Exception occured while updating Coding Correction Log");
				LOGGER.info(Constants.EXCEPTION, e);
			}
		} else {
			LOGGER.info("in add mode");
			try {
				if (codingCorrectionLogWorkFlow.getAttachment() != null
						&& codingCorrectionLogWorkFlow.getAttachment()
								.getAttachedFile().getSize() != 0) {
					// fileUpload(codingCorrectionLogWorkFlow);
					AkpmsUtil.fileUpload(
							codingCorrectionLogWorkFlow.getAttachment(),
							fileDao, messageSource);
				} else {
					codingCorrectionLogWorkFlow.setAttachment(null);
				}
				codingCorrectionLogDao
						.addCodingCorrectionLogWorkFlow(codingCorrectionLogWorkFlow);
				session.setAttribute(Constants.SUCCESS_UPDATE,
						messageSource.getMessage("arProductivity.codingcorrectionworkflow.addedSuccessfully", null, Locale.ENGLISH));
			} catch (ArgusException e) {
				LOGGER.info("Exception occured while adding Coding Correction Log");
				LOGGER.info(Constants.EXCEPTION, e);
			}
		}

		LOGGER.info("Out codingCorrectionLogWorkFlowProcess method");

		if (null != request.getParameter(Constants.POPUP)) {
			return "redirect:/" + Constants.CLOSE_POPUP;
		}

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
		if (request.getParameter(Constants.AR_PROD_PATIENT_ACC_NO) != null
				& request.getParameter(Constants.AR_PROD_PATIENT_ACC_NO).trim()
						.length() > 0) {
			String patientAccNo = request
					.getParameter("arProductivity.patientAccNo");
			whereClauses.put("arProductivity.patientAccNo", patientAccNo);
		}
		if (request.getParameter(Constants.DATE_CREATED_FROM) != null
				& request.getParameter(Constants.DATE_CREATED_FROM).trim()
						.length() > 0) {
			String dosFrom = request.getParameter(Constants.DATE_CREATED_FROM);
			whereClauses.put(Constants.DATE_CREATED_FROM, dosFrom);
		}
		if (request.getParameter(Constants.DATE_CREATED_TO) != null
				& request.getParameter(Constants.DATE_CREATED_TO).trim()
						.length() > 0) {
			String dosTo = request.getParameter(Constants.DATE_CREATED_TO);
			whereClauses.put(Constants.DATE_CREATED_TO, dosTo);
		}
		if (request.getParameter(Constants.WORKFLOW) != null
				& request.getParameter(Constants.WORKFLOW).trim().length() > 0) {
			String workFlow = request.getParameter(Constants.WORKFLOW);
			whereClauses.put(Constants.WORKFLOW, workFlow);
		}

		printReportCriteria = whereClauses;
		try {
			int totalRows = codingCorrectionLogDao.totalRecord(whereClauses);
			if (totalRows > 0) {
				List<CodingCorrectionLogWorkFlow> rows = codingCorrectionLogDao
						.findAll(orderClauses, whereClauses, true);
				LOGGER.info("row size : " + rows.size());
				List<CodingCorrectionLogWorkFlowJsonData> ccjd = getJsonData(rows);
				JsonDataWrapper<CodingCorrectionLogWorkFlowJsonData> jdw = new JsonDataWrapper<CodingCorrectionLogWorkFlowJsonData>(
						page, totalRows, ccjd);
				return jdw;
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
		return null;
	}

	private List<CodingCorrectionLogWorkFlowJsonData> getJsonData(
			List<CodingCorrectionLogWorkFlow> rows) {
		LOGGER.info("in getJsonData");
		List<CodingCorrectionLogWorkFlowJsonData> codingCorrectionLogWorkFlowJsonData = new ArrayList<CodingCorrectionLogWorkFlowJsonData>();

		if (rows != null && rows.size() > 0) {
			for (CodingCorrectionLogWorkFlow eTemp : rows) {
				CodingCorrectionLogWorkFlowJsonData ccjd = new CodingCorrectionLogWorkFlowJsonData();

				ccjd.setId(eTemp.getId());
				LOGGER.info("eTemp.getArProductivity() : "
						+ eTemp.getArProductivity());
				if (null != eTemp.getArProductivity()) {
					ccjd.setPatientAccNo(eTemp.getArProductivity()
							.getPatientAccNo());
					ccjd.setPatientName(eTemp.getArProductivity()
							.getPatientName());
					ccjd.setProvider(eTemp.getArProductivity().getDoctor()
							.getName());
					ccjd.setDos(eTemp.getArProductivity().getDos());
					ccjd.setArProdId(eTemp.getArProductivity().getId());

					if (eTemp.getArProductivity().getTeam() != null) {
						ccjd.setTeam(eTemp.getArProductivity().getTeam()
								.getName());
					}
				}

				ccjd.setBatchNo(eTemp.getBatchNo());
				ccjd.setSeqNo(eTemp.getSequenceNo());
				ccjd.setCreatedBy(eTemp.getCreatedBy().getFirstName()+" "+ eTemp.getCreatedBy().getLastName());
				ccjd.setCreatedOn(new SimpleDateFormat(Constants.DATE_FORMAT)
				.format(eTemp.getCreatedOn()));

				if (eTemp.getAttachment() != null
						&& eTemp.getAttachment().getId() != null) {
					ccjd.setAttachmentId(eTemp.getAttachment().getId());
					ccjd.setAttachmentName(eTemp.getAttachment().getName());
				}

				/*
				 * if (eTemp.getCodingRemark() != null) {
				 * ccjd.setStatus("Done"); } else { ccjd.setStatus("Pending"); }
				 */
				ccjd.setStatus(eTemp.getNextAction());

				codingCorrectionLogWorkFlowJsonData.add(ccjd);
			}
		}
		LOGGER.info("out getJsonData");
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
					/*
					 * if (codingCorrectionLogWorkFlow.getId() != null) {
					 * codingCorrectionLogDao.updateAttachement(newFile); } else
					 * { codingCorrectionLogDao.saveAttachement(newFile); }
					 */
					if (codingCorrectionLogWorkFlow.getId() == null) {
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
						"attachment; filename=\"" + file.getName() + "\"");
				IOUtils.copy(is, response.getOutputStream());
				response.flushBuffer();
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
	}

	@RequestMapping(value = "/print", method = RequestMethod.GET)
	public String getPrint(Map<String, String> map) {

		try {
			List<CodingCorrectionLogWorkFlow> rows = codingCorrectionLogDao
					.findAll(null, printReportCriteria, true);

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
		map.put("title", "Coding Correction Log Work Flow List");
		map.put("path", "/flows/codingcorrectionlogworkflow/print_report");
		return "chargeBatchProcessingPrintReport";
	}

	@RequestMapping(value = "/printhtmlreport", method = RequestMethod.GET)
	public String printHtmlReport(Map<String, Object> map) {

		try {
			List<CodingCorrectionLogWorkFlow> rows = codingCorrectionLogDao
					.findAll(null, printReportCriteria, true);

			map.put("codingCorrectionList", rows);
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}

		map.put("PRINT_REPORT_CRITERIA", printReportCriteria);
		map.put("SHOW_FOOTER", false);

		return "codingCorrectionLogWorkflowListToPrint";
	}

	private String getXmlData(
			List<CodingCorrectionLogWorkFlow> codingCorrectionLogWorkFlowList)
			throws Exception {
		XStream xstream = new XStream(new DomDriver());
		xstream.setMode(XStream.NO_REFERENCES);
		xstream.processAnnotations(CodingCorrectionLogWorkFlow.class);
		xstream.processAnnotations(User.class);
		xstream.processAnnotations(Doctor.class);
		xstream.registerConverter(new UserXstreamConverter());
		return xstream.toXML(codingCorrectionLogWorkFlowList);
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
							"CodingCorrectionLogWorkFlowStylesheet.xsl");
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

	private void populateValuesOnUI(Model model, HttpServletRequest request) {
		LOGGER.debug("in populateValuesOnUI");

		try {
			Map<String, String> whereClause = new HashMap<String, String>();
			whereClause.put(Constants.STATUS, Constants.ACTIVATE + "");
			List<Insurance> insuranceList = insuranceDao.findAll(null,
					whereClause);
			model.addAttribute(INSURANCE_LIST, insuranceList);
			model.addAttribute(DOCTOR_LIST,
					doctorDao.findAll(null, whereClause, false));
		} catch (Exception e) {
			LOGGER.error(e);
		}

		/* populate status code from property file */
		try {
			model.addAttribute(
					"NEXT_ACTION",
					AkpmsUtil.getPropertyMap(request.getRealPath("WEB-INF"
							+ File.separator + "classes" + File.separator
							+ "codingCorrectionNextStep.properties")));
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error(Constants.ERROR, e);
		}

		LOGGER.debug("out populateValuesOnUI");
	}
}
