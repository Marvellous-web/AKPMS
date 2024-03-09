/**
 *
 */
package argus.mvc.productivity.workflow.adjustmentlog;

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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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

import argus.domain.AdjustmentLogWorkFlow;
import argus.domain.ArProductivity;
import argus.domain.Department;
import argus.domain.Doctor;
import argus.domain.Insurance;
import argus.domain.User;
import argus.exception.ArgusException;
import argus.mvc.productivity.helper.ArProductivityHelper;
import argus.repo.department.DepartmentDao;
import argus.repo.doctor.DoctorDao;
import argus.repo.insurance.InsuranceDao;
import argus.repo.productivity.ArProductivityDao;
import argus.repo.productivity.helper.ArProductivityDaoHelper;
import argus.repo.productivity.workflow.adjustmentlogs.AdjustmentLogsDao;
import argus.util.ArProductivityJsonData;
import argus.util.Constants;
import argus.util.JsonDataWrapper;
import argus.util.UserXstreamConverter;
import argus.validator.AdjustmentLogWorkFlowValidator;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author vishal.joshi
 *
 */
@Controller
@RequestMapping(value = "/flows/adjustmentlogs")
@SessionAttributes({ Constants.ADJUSTMENTLOG })
@Scope("session")
public class AdjustmentLogWorkFlowController {

	@Autowired
	private AdjustmentLogWorkFlowValidator flowValidator;

	@Autowired
	private AdjustmentLogsDao adjLogsDao;

	@Autowired
	private ArProductivityDao arProductivityDao;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private InsuranceDao insuranceDao;

	@Autowired
	private DoctorDao doctorDao;

	@Autowired
	private DepartmentDao departmentDao;

	private String xmlString;

	private Map<String, String> printReportCriteria;

	private static final String INSURANCE_LIST = "insuranceList";

	private static final String DOCTOR_LIST = "doctorList";

	private static final Logger LOGGER = Logger
			.getLogger(AdjustmentLogWorkFlowController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String adjustmentLogList(Model model, HttpSession session,
			HttpServletRequest request) {

		String success = (String) session
				.getAttribute(Constants.SUCCESS_UPDATE);

		String timilyFiling = request.getParameter("timilyFiling");
		String workFlowStatus = request.getParameter("workFlowStatus");

		if (timilyFiling != null) {
			model.addAttribute("timilyFiling", timilyFiling);
		}
		if (workFlowStatus != null) {
			model.addAttribute("workFlowStatus", workFlowStatus);
		}
		if (success != null) {
			// model.addAttribute("success",
			// messageSource.getMessage(success, null, Locale.ENGLISH));
			model.addAttribute("success", success);
		}

		try {
			Department dept = departmentDao.findById(
					Long.parseLong(Constants.AR_DEPARTMENT_ID), true);
			model.addAttribute(Constants.TEAM_LIST, dept.getDepartments());
		} catch (Exception e) {
			model.addAttribute(Constants.TEAM_LIST, null);
		}

		session.removeAttribute(Constants.SUCCESS_UPDATE);

		return "flows/arProductivity/adjustmentLogsList";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String adjustmentLogLoad(Model model, HttpServletRequest request,
			Map<String, Object> map,
			@RequestParam(required = false) String successUpdate,
			HttpSession session, HttpServletResponse response) {
		LOGGER.info("in [adjustmentLogLoad] method");

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

		int arProductivityId = 0;
		if (request.getParameter(Constants.ARPRODUCTIVITY_ID) != null
				&& !request.getParameter(Constants.ARPRODUCTIVITY_ID)
						.equals("")) {

			arProductivityId = Integer.valueOf(request
					.getParameter(Constants.ARPRODUCTIVITY_ID));

			ArProductivity arProductivity = null;
			try {
				// Fetch AR producitivity to show on read only labels and other
				// fields if workflow details not filled yet
				arProductivity = arProductivityDao.findById(Long
						.valueOf(request
								.getParameter(Constants.ARPRODUCTIVITY_ID)));
				map.put(Constants.ARPRODUCTIVITY, arProductivity);
			} catch (Exception e) {
				LOGGER.info("problem in fetching arproducitivity");
				LOGGER.error(e.getMessage(), e);
			}

			AdjustmentLogWorkFlow adjLogWorkFlow = null;

			try {
				adjLogWorkFlow = adjLogsDao
						.getAdjLogByArProductivityId(arProductivityId);

				model.addAttribute(Constants.ADJUSTMENTLOG, adjLogWorkFlow);
				model.addAttribute(Constants.MODE, Constants.EDIT);
				map.put(Constants.OPERATION_TYPE, Constants.EDIT);
				map.put(Constants.BUTTON_NAME, Constants.UPDATE);

				if (adjLogWorkFlow.getWorkFlowStatus() == Constants.WORKFLOW_ESCALATE) {
					map.put(Constants.OPERATION_TYPE, Constants.EDIT);
					map.put(Constants.CHECK_WORK_FLOW,
							Constants.WORKFLOW_ESCALATE);
				}
			} catch (ArgusException e) {
				LOGGER.error(e.getMessage(), e);
			} catch (NoResultException ex) {
				LOGGER.error(ex.getMessage(), ex);

				adjLogWorkFlow = new AdjustmentLogWorkFlow();

				if (null != arProductivity) {
					// set values from productivity in case of add
					adjLogWorkFlow.setDoctor(arProductivity.getDoctor());
					adjLogWorkFlow.setInsurance(arProductivity.getInsurance());
					adjLogWorkFlow.setRemark(arProductivity.getRemark());
					adjLogWorkFlow.setCpt(arProductivity.getCpt());
					adjLogWorkFlow
							.setBalanceAmt(arProductivity.getBalanceAmt());
					adjLogWorkFlow.setDos(arProductivity.getDos());
				}

				map.put(Constants.ADJUSTMENTLOG, adjLogWorkFlow);
				model.addAttribute(Constants.MODE, Constants.ADD);
				map.put(Constants.OPERATION_TYPE, Constants.ADD);
				map.put(Constants.BUTTON_NAME, Constants.SAVE);
				map.put(Constants.READ_ONLY, false);
			}
		} else {
			map.put(Constants.ADJUSTMENTLOG, new AdjustmentLogWorkFlow());
			model.addAttribute(Constants.MODE, Constants.ADD);
			map.put(Constants.OPERATION_TYPE, Constants.ADD);
			map.put(Constants.BUTTON_NAME, Constants.SAVE);
			map.put(Constants.READ_ONLY, false);
		}

		if (null != request.getParameter(Constants.POPUP)) {
			map.put(Constants.POPUP, true);
			return "flows/arProductivity/adjustmentLogs" +Constants.POPUP;
		}

		map.put(Constants.POPUP, false);
		return "flows/arProductivity/adjustmentLogs";
	}

	@SuppressWarnings("finally")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String saveAdjustmentLog(
			@Valid @ModelAttribute(Constants.ADJUSTMENTLOG) AdjustmentLogWorkFlow adjLogWorkFlow,
			BindingResult result, Model model, WebRequest request,
			Map<String, Object> map, HttpSession session) {
		LOGGER.info("in [saveAdjustmentLog] method");
		flowValidator.validate(adjLogWorkFlow, result);

		if (!result.hasErrors()) {
			if (adjLogWorkFlow.getId() != null) {
				LOGGER.info("in [saveAdjustmentLog]adjLogWorkFlow is not null");
				try {
					adjLogsDao.updateAdjLogWorkFlow(adjLogWorkFlow);
					session.setAttribute(Constants.SUCCESS_UPDATE,
							messageSource.getMessage(
									"adjustmentlogs.updatedSuccessfully", null,
									Locale.ENGLISH));
					// map.put("showFinalRemark", 1);
				} catch (Exception e) {
					LOGGER.error(Constants.EXCEPTION, e);
				} finally {
					// return "redirect:/flows/arProductivity";
					if (null != request.getParameter(Constants.POPUP)) {
						return "redirect:/" + Constants.CLOSE_POPUP;
					}

					return "redirect:/flows/adjustmentlogs";
				}
			} else {
				try {
					adjLogsDao.addAdjLogWorkFlow(adjLogWorkFlow);
					session.setAttribute(Constants.SUCCESS_UPDATE,
							messageSource.getMessage(
									"adjustmentlogs.addedSuccessfully", null,
									Locale.ENGLISH));

				} catch (Exception e) {
					LOGGER.error(Constants.EXCEPTION, e);

					if (null != request.getParameter(Constants.POPUP)) {
						map.put(Constants.POPUP, true);
						return "flows/arProductivity/adjustmentLogs" +Constants.POPUP;
					}
					map.put(Constants.POPUP, false);
					return "flows/adjustmentlogs/add?arProductivity.id="
							+ adjLogWorkFlow.getArProductivity().getId();
				}
			}

			if (null != request.getParameter(Constants.POPUP)) {
				return "redirect:/" + Constants.CLOSE_POPUP;
			}

			// return "redirect:/flows/arProductivity";
			return "redirect:/flows/adjustmentlogs";
		} else {
			if (adjLogWorkFlow != null && adjLogWorkFlow.getId() != null) {
				map.put(Constants.OPERATION_TYPE, Constants.EDIT);
				map.put(Constants.BUTTON_NAME, Constants.UPDATE);
				// map.put(Constants.CHECK_WORK_FLOW,
				// Constants.WORKFLOW_ESCALATE);
				map.put(Constants.READ_ONLY, true);
				try {
					map.put(Constants.ARPRODUCTIVITY, arProductivityDao
							.findById(adjLogWorkFlow.getArProductivity()
									.getId()));

					Map<String, String> whereClause = new HashMap<String, String>();
					whereClause.put(Constants.STATUS, Constants.ACTIVATE + "");
					List<Insurance> insuranceList = insuranceDao.findAll(null,
							whereClause);
					model.addAttribute(INSURANCE_LIST, insuranceList);
					model.addAttribute(DOCTOR_LIST,
							doctorDao.findAll(null, whereClause, false));
				} catch (Exception e) {
					LOGGER.error(Constants.EXCEPTION, e);

				}

			} else {
				map.put(Constants.OPERATION_TYPE, Constants.ADD);
				map.put(Constants.BUTTON_NAME, Constants.SAVE);
			}

			map.put("arProductivity.id", adjLogWorkFlow.getArProductivity()
					.getId());

			if (null != request.getParameter(Constants.POPUP)) {
				map.put(Constants.POPUP, true);
				return "flows/arProductivity/adjustmentLogs" +Constants.POPUP;
			}

			map.put(Constants.POPUP, false);
			// return "flows/adjustmentlogs/add";
			return "flows/arProductivity/adjustmentLogs";
		}
	}

	@RequestMapping(value = "/json", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonDataWrapper<ArProductivityJsonData> listAllArProductivity(
			WebRequest request) {
		LOGGER.info("in [listAllArProductivity]json method");
		int page = 1;
		boolean both = false;
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
			whereClauses.put(Constants.DATE_CREATED_FROM,
					request.getParameter(Constants.DATE_CREATED_FROM));
		}
		if (request.getParameter(Constants.DATE_CREATED_TO) != null
				& request.getParameter(Constants.DATE_CREATED_TO).trim()
						.length() > 0) {
			whereClauses.put(Constants.DATE_CREATED_TO,
					request.getParameter(Constants.DATE_CREATED_TO));
		}

		if (request.getParameter(Constants.WORK_FLOW_STATUS) != null
				& request.getParameter(Constants.WORK_FLOW_STATUS).trim()
						.length() > 0) {
			whereClauses.put(Constants.WORK_FLOW_STATUS,
					request.getParameter(Constants.WORK_FLOW_STATUS));
		}

		if (request.getParameter(Constants.TIMILY_FILING) != null) {
			whereClauses.put(Constants.TIMILY_FILING,
					request.getParameter(Constants.TIMILY_FILING));
			both = true;
		}
		if (request.getParameter(Constants.WITHOUT_TIMILY_FILING) != null) {
			if (both) {
				whereClauses.remove(Constants.TIMILY_FILING);
			} else {
				whereClauses.put(Constants.WITHOUT_TIMILY_FILING,
						request.getParameter(Constants.WITHOUT_TIMILY_FILING));
			}
		}

		printReportCriteria = whereClauses;
		try {
			int totalRows = adjLogsDao.totalRecord(whereClauses);
			if (totalRows > 0) {
				List<AdjustmentLogWorkFlow> rows = adjLogsDao.findAll(
						orderClauses, whereClauses, true);
				List<ArProductivityJsonData> djd = getJsonData(rows);
				JsonDataWrapper<ArProductivityJsonData> jdw = new JsonDataWrapper<ArProductivityJsonData>(
						page, totalRows, djd);
				return jdw;
			}
		} catch (ArgusException e) {
			LOGGER.info(Constants.EXCEPTION, e);
		}
		return null;
	}

	private List<ArProductivityJsonData> getJsonData(
			List<AdjustmentLogWorkFlow> rows) {
		List<ArProductivityJsonData> arProdJsonData = new ArrayList<ArProductivityJsonData>();

		if (rows != null && rows.size() > 0) {
			for (AdjustmentLogWorkFlow eTemp : rows) {
				ArProductivityJsonData djd = new ArProductivityJsonData();

				if (eTemp.getArProductivity() != null) {
					djd.setId(eTemp.getArProductivity().getId());
					djd.setInsurance(eTemp.getArProductivity().getInsurance()
							.getName());
					djd.setBalanceAmt(eTemp.getArProductivity().getBalanceAmt());
					djd.setDoctor(eTemp.getArProductivity().getDoctor()
							.getName());
					if (eTemp.getArProductivity().getTeam() != null) {
						djd.setTeam(eTemp.getArProductivity().getTeam()
								.getName());
					}
					djd.setCpt(eTemp.getArProductivity().getCpt());
					djd.setDos(eTemp.getArProductivity().getDos());

					// djd.setDos(eTemp.getArProductivity().getDos());
					djd.setPatientAccNo(eTemp.getArProductivity()
							.getPatientAccNo());
					djd.setPatientName(eTemp.getArProductivity()
							.getPatientName());
					// djd.setSourceName(ArProductivityDaoHelper.getSourceName(eTemp
					// .getArProductivity().getSource()));
					djd.setWorkFlowName(ArProductivityDaoHelper
							.getWorkFlowName(eTemp.getArProductivity()
									.getWorkFlow()));
					djd.setWorkflowId(eTemp.getWorkFlowStatus());
					djd.setDataBas(eTemp.getArProductivity().getArDatabase()
							.getName());
					djd.setSource(eTemp.getArProductivity().getSource());
					djd.setCreatedBy(eTemp.getArProductivity().getCreatedBy().getFirstName()+ " " +eTemp.getArProductivity().getCreatedBy().getLastName());
					djd.setCreatedOn(new SimpleDateFormat(Constants.DATE_FORMAT)
					.format(eTemp.getCreatedOn()));
				}

				arProdJsonData.add(djd);
			}
		}
		return arProdJsonData;
	}

	@RequestMapping(value = "/print", method = RequestMethod.GET)
	public String getPrint(Map<String, String> map) {

		try {
			List<AdjustmentLogWorkFlow> rows = adjLogsDao.findAll(null,
					printReportCriteria, true);
			if (rows != null && !rows.isEmpty()) {
				xmlString = getXmlData(rows);
				xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
						+ xmlString;
			} else {
				xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> ";
				xmlString = xmlString + "<empty>No Record found.</empty>";
			}
			// LOGGER.info("XML = " + xmlString);
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
		map.put("title", "AdjustmentLog Work Flow List");
		map.put("path", "/flows/adjustmentlogs/print_report");
		return "chargeBatchProcessingPrintReport";
	}

	private String getXmlData(
			List<AdjustmentLogWorkFlow> adjustmentLogWorkFlowList)
			throws Exception {
		XStream xstream = new XStream(new DomDriver());
		xstream.setMode(XStream.NO_REFERENCES);
		xstream.processAnnotations(AdjustmentLogWorkFlow.class);
		xstream.processAnnotations(User.class);
		xstream.processAnnotations(Doctor.class);
		xstream.registerConverter(new UserXstreamConverter());
		return xstream.toXML(adjustmentLogWorkFlowList);
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

		List<AdjustmentLogWorkFlow> rows = null;
		try {
			rows = adjLogsDao.findAll(null, printReportCriteria, true);

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}

		map.put("PRINT_REPORT_CRITERIA", printReportCriteria);
		map.put("SHOW_FOOTER", false);

		map.put("adjustmentLogList", rows);
		return "adjustmentLogListToPrint";
	}

}
