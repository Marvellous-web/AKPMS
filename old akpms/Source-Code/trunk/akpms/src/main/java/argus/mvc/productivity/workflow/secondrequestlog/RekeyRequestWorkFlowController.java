package argus.mvc.productivity.workflow.secondrequestlog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

import argus.domain.ArProductivity;
import argus.domain.Department;
import argus.domain.Doctor;
import argus.domain.Insurance;
import argus.domain.RekeyRequestRecord;
import argus.domain.RekeyRequestWorkFlow;
import argus.domain.User;
import argus.exception.ArgusException;
import argus.mvc.productivity.helper.ArProductivityHelper;
import argus.repo.department.DepartmentDao;
import argus.repo.doctor.DoctorDao;
import argus.repo.insurance.InsuranceDao;
import argus.repo.productivity.ArProductivityDao;
import argus.repo.productivity.workflow.rekeyrequest.RekeyRequestDao;
import argus.util.AkpmsUtil;
import argus.util.Constants;
import argus.util.JsonDataWrapper;
import argus.util.RekeyRequestRecordJsonData;
import argus.util.RekeyRequestWorkFlowJsonData;
import argus.util.UserXstreamConverter;
import argus.validator.RekeyRequestWorkFlowValidator;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

@Controller
@RequestMapping(value = "/flows/rekeyrequest")
@SessionAttributes({ "rekeyRequestWorkFlow" })
@Scope("session")
public class RekeyRequestWorkFlowController {

	private static final Logger LOGGER = Logger
			.getLogger(RekeyRequestWorkFlowController.class);

	@Autowired
	private RekeyRequestDao rekeyRequestDao;

	@Autowired
	private RekeyRequestWorkFlowValidator rekeyRequestWorkFlowValidator;

	@Autowired
	private ArProductivityDao arProductivityDao;

	@Autowired
	private MessageSource messageSource;

	private String xmlString;

	private Map<String, String> printReportCriteria;

	@Autowired
	private InsuranceDao insuranceDao;

	@Autowired
	private DoctorDao doctorDao;

	@Autowired
	private DepartmentDao departmentDao;

	private static final String INSURANCE_LIST = "insuranceList";

	private static final String DOCTOR_LIST = "doctorList";

	@RequestMapping(method = RequestMethod.GET)
	public String rekeyRequestWorkFlowList(Model model, HttpSession session) {
		String success = (String) session
				.getAttribute(Constants.SUCCESS_UPDATE);
		if (success != null) {
			// model.addAttribute("success",
			// messageSource.getMessage(success, null, Locale.ENGLISH));
			model.addAttribute("success", success);
		}

		session.removeAttribute(Constants.SUCCESS_UPDATE);

		try {
			Department dept = departmentDao.findById(
					Long.parseLong(Constants.AR_DEPARTMENT_ID), true);
			model.addAttribute(Constants.TEAM_LIST, dept.getDepartments());
		} catch (Exception e) {
			model.addAttribute(Constants.TEAM_LIST, null);
		}

		return "flows/rekeyRequestWorkFlowList";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String rekeyRequestWorkFlowLoad(Map<String, Object> map,
			Model model, HttpServletRequest request, HttpSession session) {

		RekeyRequestWorkFlow rekeyRequestWorkFlow = null;
		ArProductivity arProductivity = null;

		List<RekeyRequestRecord> rekeyRequestRecordList = new ArrayList<RekeyRequestRecord>();
		// map.put("rekeyRequestRecords", rekeyRequestRecordList);

		if (request.getParameter("arProductivity.id") != null) {
			try {
				arProductivity = arProductivityDao.findById(Long
						.valueOf(request
								.getParameter(Constants.ARPRODUCTIVITY_ID)));
				map.put(Constants.ARPRODUCTIVITY, arProductivity);
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}

			try {
				rekeyRequestWorkFlow = rekeyRequestDao
						.findByProductivityId(Long.valueOf(request
								.getParameter("arProductivity.id")));

				if (null != rekeyRequestWorkFlow) {
					rekeyRequestRecordList = rekeyRequestWorkFlow
							.getRekeyRequestRecords();
					map.put(Constants.OPERATION_TYPE, Constants.EDIT);
					map.put(Constants.BUTTON_NAME, Constants.UPDATE);
				} else {
					rekeyRequestRecordList.add(new RekeyRequestRecord());
					map.put(Constants.OPERATION_TYPE, Constants.ADD);
					map.put(Constants.BUTTON_NAME, Constants.SAVE);
				}

				session.setAttribute("initialStatus",
						rekeyRequestWorkFlow.getStatus());

				// map.put(Constants.READ_ONLY, true);

				// byOffSetManager = managerDao.findById(
				// Long.parseLong(request.getParameter(Constants.ID)),
				// true);
				// postingRecordList = byOffSetManager.getPostingRecords();
				// paymentBatch = byOffSetManager.getPaymentBatch();
				// model.addAttribute("postingRecord", postingRecordList);

			} catch (NoResultException ex) {
				rekeyRequestWorkFlow = new RekeyRequestWorkFlow();

				// set values from productivity in case of add
				if (null != arProductivity) {
					rekeyRequestWorkFlow.setDoctor(arProductivity.getDoctor());
					rekeyRequestWorkFlow.setInsurance(arProductivity
							.getInsurance());
					rekeyRequestWorkFlow.setRemark(arProductivity.getRemark());
					rekeyRequestWorkFlow.setCpt(arProductivity.getCpt());
					rekeyRequestWorkFlow.setBalanceAmt(arProductivity
							.getBalanceAmt());
					rekeyRequestWorkFlow.setDos(arProductivity.getDos());
					rekeyRequestWorkFlow.setCreatedOn(arProductivity.getCreatedOn());
					rekeyRequestWorkFlow.setCreatedBy(arProductivity.getCreatedBy());
				}

				rekeyRequestRecordList.add(new RekeyRequestRecord());
				rekeyRequestWorkFlow
						.setRekeyRequestRecords(rekeyRequestRecordList);
				// map.put(Constants.READ_ONLY, false);
				map.put("rekeyRequestWorkFlow", rekeyRequestWorkFlow);
				map.put(Constants.BUTTON_NAME, Constants.SAVE);
				map.put(Constants.OPERATION_TYPE, Constants.ADD);
				LOGGER.error(Constants.EXCEPTION, ex);

			} catch (Exception e) {
				LOGGER.info("Error occured while fetching RekeyRequestWorkFlow Object");
				LOGGER.error(Constants.EXCEPTION, e);
			}

		} else if (request.getParameter(Constants.ID) != null) {
			try {
				rekeyRequestWorkFlow = rekeyRequestDao.findById(Long
						.valueOf(request.getParameter(Constants.ID)));

				rekeyRequestRecordList = rekeyRequestWorkFlow
						.getRekeyRequestRecords();

				map.put(Constants.BUTTON_NAME, Constants.UPDATE);
				map.put(Constants.OPERATION_TYPE, Constants.EDIT);
				// map.put(Constants.READ_ONLY, true);
			} catch (Exception e) {
				LOGGER.info("Exception occured while fetching Rekey Request  WorkFlow");
				LOGGER.info(Constants.EXCEPTION, e);
			}
		} else {
			rekeyRequestWorkFlow = new RekeyRequestWorkFlow();
			map.put(Constants.READ_ONLY, false);
			rekeyRequestRecordList.add(new RekeyRequestRecord());
		}

		map.put("rekeyRequestRecords", rekeyRequestRecordList);
		populateValuesOnUI(model, request);

		map.put("rekeyRequestWorkFlow", rekeyRequestWorkFlow);

		if (null != request.getParameter(Constants.POPUP)) {
			map.put(Constants.POPUP, true);
			return "flows/rekeyrequestworkflow/add" + Constants.POPUP;
		}

		map.put(Constants.POPUP, false);

		return "flows/rekeyrequestworkflow/add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String rekeyRequestWorkFlowProcess(
			@ModelAttribute("rekeyRequestWorkFlow") RekeyRequestWorkFlow rekeyRequestWorkFlow,
			Map<String, Object> map, BindingResult result,
			HttpServletRequest request, Model model, HttpSession session) {

		List<RekeyRequestRecord> rekeyRequestRecordList = new ArrayList<RekeyRequestRecord>();
		List<Long> toRemoveRekeyRequestRecordList = new ArrayList<Long>();

		StringBuilder toRemoveIds = new StringBuilder();
		int counter = 0;

		for (RekeyRequestRecord rekeyRequestRecord : rekeyRequestWorkFlow
				.getRekeyRequestRecords()) {
			LOGGER.info(":: VALUES CPT ::" + rekeyRequestRecord.getCpt());
			LOGGER.info(":: VALUES FROM ::" + rekeyRequestRecord.getRemark());

			if ((rekeyRequestRecord.getCpt() == null || rekeyRequestRecord
					.getCpt().trim().length() == 0)
					&& (rekeyRequestRecord.getRemark() == null || rekeyRequestRecord
							.getRemark().trim().length() == 0)) {
				if (rekeyRequestRecord.getId() != null) {
					toRemoveRekeyRequestRecordList.add(rekeyRequestRecord
							.getId());
					if (counter == 0) {
						toRemoveIds.append(rekeyRequestRecord.getId());
					} else {
						toRemoveIds.append("," + rekeyRequestRecord.getId());
					}
					counter++;
				}
			} else {
				rekeyRequestRecordList.add(rekeyRequestRecord);
			}

			rekeyRequestWorkFlow.setRekeyRequestRecords(rekeyRequestRecordList);
			model.addAttribute("rekeyRequestRecords",
					rekeyRequestWorkFlow.getRekeyRequestRecords());
		}

		if (request.getParameter("toRemoveIds") != null
				&& request.getParameter("toRemoveIds").trim().length() > 0) {
			toRemoveIds = new StringBuilder(request.getParameter("toRemoveIds"));
			String[] splittArray = null;
			splittArray = request.getParameter("toRemoveIds").split(",");
			for (String value : splittArray) {
				toRemoveRekeyRequestRecordList.add(Long.valueOf(value));
			}
		}

		map.put("toRemoveIds", toRemoveIds);
		rekeyRequestWorkFlowValidator.validate(rekeyRequestWorkFlow, result);
		if (result.hasErrors()) {
			try {
				map.put(Constants.ARPRODUCTIVITY, arProductivityDao
						.findById(rekeyRequestWorkFlow.getArProductivity()
								.getId()));
				populateValuesOnUI(model, request);
				if (rekeyRequestWorkFlow.getId() != null) {
					map.put(Constants.BUTTON_NAME, Constants.UPDATE);
					map.put(Constants.OPERATION_TYPE, Constants.EDIT);
				} else {
					map.put(Constants.BUTTON_NAME, Constants.SAVE);
					map.put(Constants.OPERATION_TYPE, Constants.ADD);
				}
				map.put("toRemoveIds", toRemoveIds);

				if (rekeyRequestWorkFlow.getRekeyRequestRecords() == null
						|| rekeyRequestWorkFlow.getRekeyRequestRecords()
								.isEmpty()) {
					rekeyRequestRecordList.clear();
					rekeyRequestRecordList.add(new RekeyRequestRecord());
					// rekeyRequestWorkFlow.getRekeyRequestRecords().add(new
					// RekeyRequestRecord());
				}
				map.put("rekeyRequestRecords", rekeyRequestRecordList);
			} catch (Exception e) {
				LOGGER.error(Constants.EXCEPTION, e);

			}
			if (null != request.getParameter(Constants.POPUP)) {
				map.put(Constants.POPUP, true);
				return "flows/rekeyrequestworkflow/add" + Constants.POPUP;
			}
			map.put(Constants.POPUP, false);
			return "flows/rekeyrequestworkflow/add";
		}

		LOGGER.info("In rekeyrequestworkflow method");
		if (rekeyRequestWorkFlow.getId() != null) {
			try {
				rekeyRequestDao
						.updateRekeyRequestWorkFlow(rekeyRequestWorkFlow);
				session.setAttribute(
						Constants.SUCCESS_UPDATE,
						messageSource
								.getMessage(
										"arProductivity.rekeyRequestWorkFlow.updatedSuccessfully",
										null, Locale.ENGLISH));

				if (!toRemoveRekeyRequestRecordList.isEmpty()) {
					rekeyRequestDao
							.deleteRekeyRequestRecords(toRemoveRekeyRequestRecordList);
				}
			} catch (ArgusException e) {
				LOGGER.info("Exception occured while updating Coding Correction Log");
				LOGGER.info(Constants.EXCEPTION, e);
			}
		} else {
			try {
				rekeyRequestDao.addRekeyRequestWorkFlow(rekeyRequestWorkFlow);
				session.setAttribute(
						Constants.SUCCESS_UPDATE,
						messageSource
								.getMessage(
										"arProductivity.rekeyRequestWorkFlow.addedSuccessfully",
										null, Locale.ENGLISH));
			} catch (ArgusException e) {
				LOGGER.info("Exception occured while adding Rekey Request ");
				LOGGER.info(Constants.EXCEPTION, e);
			}
		}

		LOGGER.info("Out rekeyRequestWorkFlowProcess method");
		// return "redirect:/flows/arproductivity/rekeyrequestworkflow";

		if (null != request.getParameter(Constants.POPUP)) {
			map.put(Constants.POPUP, true);
			return "redirect:/" + Constants.CLOSE_POPUP;
		}
		map.put(Constants.POPUP, false);

		return "redirect:/flows/rekeyrequest";
	}

	@RequestMapping(value = "/json", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonDataWrapper<RekeyRequestWorkFlowJsonData> listAllArProductivity(
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
		if (request.getParameter(Constants.STATUS) != null
				& request.getParameter(Constants.STATUS).trim().length() > 0) {
			String status = request.getParameter(Constants.STATUS);
			whereClauses.put(Constants.STATUS, status);
		}
		printReportCriteria = whereClauses;
		try {
			int totalRows = rekeyRequestDao.totalRecord(whereClauses);
			if (totalRows > 0) {
				List<RekeyRequestWorkFlow> rows = rekeyRequestDao.findAll(
						orderClauses, whereClauses, true);
				List<RekeyRequestWorkFlowJsonData> djd = getJsonData(rows);
				JsonDataWrapper<RekeyRequestWorkFlowJsonData> jdw = new JsonDataWrapper<RekeyRequestWorkFlowJsonData>(
						page, totalRows, djd);
				return jdw;
			}
		} catch (ArgusException e) {
			LOGGER.info(Constants.EXCEPTION, e);
		}
		return null;
	}

	private List<RekeyRequestWorkFlowJsonData> getJsonData(
			List<RekeyRequestWorkFlow> rows) {
		List<RekeyRequestWorkFlowJsonData> rekeyRequestWorkFlowJsonDataList = new ArrayList<RekeyRequestWorkFlowJsonData>();

		if (rows != null && rows.size() > 0) {
			for (RekeyRequestWorkFlow eTemp : rows) {
				RekeyRequestWorkFlowJsonData djd = new RekeyRequestWorkFlowJsonData();

				djd.setId(eTemp.getId());
				djd.setArProdId(eTemp.getArProductivity().getId());
				djd.setInsurance(eTemp.getArProductivity().getInsurance()
						.getName());
				djd.setDoctor(eTemp.getArProductivity().getDoctor().getName());

				if (eTemp.getArProductivity().getTeam() != null) {
					djd.setTeam(eTemp.getArProductivity().getTeam().getName());
				}

				if (eTemp.getArProductivity().getDos() != null) {
					djd.setDos(eTemp.getArProductivity().getDos());
				}
				djd.setRequestReason(messageSource.getMessage(
						eTemp.getRequestReason(), null, Locale.ENGLISH));
				djd.setWorkflowId(eTemp.getStatus());
				// djd.setDos(eTemp.getArProductivity().getDos());
				djd.setPatientAccNo(eTemp.getArProductivity().getPatientAccNo());
				djd.setPatientName(eTemp.getArProductivity().getPatientName());
				djd.setBatchNo(eTemp.getBatchNumber());
				rekeyRequestWorkFlowJsonDataList.add(djd);
				if (eTemp.getCreatedBy() == null) {
					djd.setCreatedBy("");
				} else
					djd.setCreatedBy(eTemp.getCreatedBy().getFirstName() + " "
							+ eTemp.getCreatedBy().getLastName());
				if (eTemp.getCreatedOn() == null) {
					djd.setCreatedOn("");
				} else
					djd.setCreatedOn(new SimpleDateFormat(Constants.DATE_FORMAT)
							.format(eTemp.getCreatedOn()));
			}
		}
		return rekeyRequestWorkFlowJsonDataList;
	}

	@RequestMapping(value = "/print", method = RequestMethod.GET)
	public String getPrint(Map<String, String> map) {

		try {
			List<RekeyRequestWorkFlow> rows = rekeyRequestDao.findAll(null,
					printReportCriteria, true);

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
		map.put("title", "Rekey Request  Work Flow List");
		map.put("path", "/flows/rekeyrequestworkflow/print_report");
		return "chargeBatchProcessingPrintReport";
	}

	private String getXmlData(
			List<RekeyRequestWorkFlow> rekeyRequestWorkFlowList)
			throws Exception {
		XStream xstream = new XStream(new DomDriver());
		xstream.setMode(XStream.NO_REFERENCES);
		xstream.processAnnotations(RekeyRequestWorkFlow.class);
		xstream.processAnnotations(User.class);
		xstream.processAnnotations(Doctor.class);
		xstream.registerConverter(new UserXstreamConverter());
		return xstream.toXML(rekeyRequestWorkFlowList);
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
							"Adjustment_SecondRequest_LogWorkFlowStylesheet.xsl");
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

	@RequestMapping(value = "/printhtmlreport", method = RequestMethod.GET)
	public String printHtmlReport(Map<String, Object> map) {

		List<RekeyRequestWorkFlow> rows = null;
		try {
			rows = rekeyRequestDao.findAll(null, printReportCriteria, true);

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}

		map.put("PRINT_REPORT_CRITERIA", printReportCriteria);
		map.put("SHOW_FOOTER", false);

		map.put("rekeyRequestList", rows);
		return "rekeyRequestWorkflowListToPrint";
	}

	/**
	 * this function will populate values in dropdown from database or property
	 * files, this function will use on add/edit AR productivity and list page
	 *
	 * @param model
	 * @param request
	 */
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
					"REJECTION_REASONS",
					AkpmsUtil.getPropertyMap(request.getRealPath("WEB-INF"
							+ File.separator + "classes" + File.separator
							+ "arRejectionReason.properties")));
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error(Constants.ERROR, e);
		}

		LOGGER.debug("out populateValuesOnUI");
	}

	@RequestMapping(value = "/getRecord", method = RequestMethod.GET)
	@ResponseBody
	public List<RekeyRequestRecordJsonData> getRecord(@RequestParam long id) {
		LOGGER.info("in getRecord method");
		LOGGER.info("id = " + id);
		List<RekeyRequestRecordJsonData> recordList = new ArrayList<RekeyRequestRecordJsonData>();

		try {

			recordList = rekeyRequestDao.getRekeyRequestRecords(id);
			if (recordList.size() > 0) {
				return recordList;
			}

		} catch (Exception e) {
			LOGGER.info(Constants.EXCEPTION + " :: " + e.getMessage());
		}

		return recordList;
	}
}
