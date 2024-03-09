/**
 *
 */
package argus.mvc.productivity.workflow.refundrequest;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
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

import argus.domain.ArProductivity;
import argus.domain.Department;
import argus.domain.Insurance;
import argus.domain.RefundRequestWorkFlow;
import argus.exception.ArgusException;
import argus.mvc.productivity.helper.ArProductivityHelper;
import argus.repo.department.DepartmentDao;
import argus.repo.doctor.DoctorDao;
import argus.repo.insurance.InsuranceDao;
import argus.repo.productivity.ArProductivityDao;
import argus.repo.productivity.workflow.refundRequest.RefundRequestDao;
import argus.util.Constants;
import argus.util.JsonDataWrapper;
import argus.util.RefundRequestWorkFlowJsonData;
import argus.validator.RefundRequestWorkFlowValidator;

/**
 * @author rajiv.k
 *
 */
@Controller
@RequestMapping(value = "/flows/refundrequest")
@SessionAttributes({ Constants.REFUNDREQUEST })
@Scope("session")
public class RefundRequestWorkFlowController {

	private static final Logger LOGGER = Logger
			.getLogger(RefundRequestWorkFlowController.class);

	@Autowired
	private RefundRequestDao refundRequestDao;

	@Autowired
	private ArProductivityDao arProductivityDao;

	@Autowired
	private RefundRequestWorkFlowValidator refundRequestWorkFlowValidator;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private InsuranceDao insuranceDao;

	@Autowired
	private DoctorDao doctorDao;

	@Autowired
	private DepartmentDao departmentDao;

	private Map<String, String> printReportCriteria;

	private static final String INSURANCE_LIST = "insuranceList";

	private static final String DOCTOR_LIST = "doctorList";

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
	public String refundRequestWorkFlowList(Model model, HttpSession session) {

		String success = (String) session
				.getAttribute(Constants.SUCCESS_UPDATE);
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
		return "flows/arProductivity/refundRequestWorkFlowList";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String refundRequestLoad(Model model, WebRequest request,
			Map<String, Object> map,
			@RequestParam(required = false) String successUpdate) {
		LOGGER.info("in [refundRequestLoad] method");
		RefundRequestWorkFlow refundRequestWorkFlow = null;

		if (request.getParameter(Constants.ARPRODUCTIVITY_ID) != null
				&& !request.getParameter(Constants.ARPRODUCTIVITY_ID)
						.equals("")) {
			try {
				refundRequestWorkFlow = refundRequestDao
						.getRefundRequestByArProductivityId((Integer.parseInt(request
								.getParameter(Constants.ARPRODUCTIVITY_ID))));
				map.put("refundRequest", refundRequestWorkFlow);
				map.put("readOnly", true);
			} catch (Exception e) {

				refundRequestWorkFlow = new RefundRequestWorkFlow();
				if (request.getParameter(Constants.ARPRODUCTIVITY_ID) != null) {
					try {
						ArProductivity arProductivity = arProductivityDao
								.findById(Long.valueOf(request
										.getParameter(Constants.ARPRODUCTIVITY_ID)));
						refundRequestWorkFlow.setArProductivity(arProductivity);

						// set values from productivity in case of add
						refundRequestWorkFlow.setDoctor(arProductivity
								.getDoctor());
						refundRequestWorkFlow.setInsurance(arProductivity
								.getInsurance());
						refundRequestWorkFlow.setReason(arProductivity
								.getRemark());
						refundRequestWorkFlow.setCpt(arProductivity.getCpt());
						refundRequestWorkFlow.setBalanceAmt(arProductivity
								.getBalanceAmt());
						refundRequestWorkFlow.setDos(arProductivity.getDos());

					} catch (Exception ex) {
						LOGGER.info(Constants.EXCEPTION, e);
					}
				}

				map.put("readOnly", false);

				LOGGER.info("Exception occured while fetching Refund Request");
				LOGGER.info(Constants.EXCEPTION, e);
			}
		}
		populateValuesOnUI(model);

		map.put("refundRequest", refundRequestWorkFlow);
		return "flows/arProductivity/refundRequest";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String refundRequestAdd(
			@ModelAttribute(Constants.REFUNDREQUEST) RefundRequestWorkFlow refundRequestWorkFlow,
			Map<String, Object> map, BindingResult result, HttpSession session,
			Model model) {

		refundRequestWorkFlowValidator.validate(refundRequestWorkFlow, result);
		if (result.hasErrors()) {
			populateValuesOnUI(model);
			return "flows/arProductivity/refundRequest";
		}

		LOGGER.info("In refundRequestAdd method");
		if (refundRequestWorkFlow.getId() != null) {

			try {
				refundRequestDao
						.updateRefundRequestWorkFlow(refundRequestWorkFlow);
				session.setAttribute(Constants.SUCCESS_UPDATE,
						messageSource
								.getMessage(
										"arProductivity.refundRequest.updatedSuccessfully",
										null, Locale.ENGLISH));

			} catch (ArgusException e) {
				LOGGER.info("Exception occured while adding Refund Request");
				LOGGER.info(Constants.EXCEPTION, e);
			}

		} else {
			try {
				refundRequestDao
						.addRefundRequestWorkFlow(refundRequestWorkFlow);
				session.setAttribute(Constants.SUCCESS_UPDATE,
						messageSource
								.getMessage(
										"arProductivity.refundRequest.addedSuccessfully",
										null, Locale.ENGLISH));
			} catch (ArgusException e) {
				LOGGER.info("Exception occured while adding Refund Request");
				LOGGER.info(Constants.EXCEPTION, e);
			}
		}

		LOGGER.info("Out refundRequestAdd method");
		return "redirect:/flows/refundrequest";
	}

	@RequestMapping(value = "/json", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonDataWrapper<RefundRequestWorkFlowJsonData> listAllRefundRequest(
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

		if (request.getParameter(Constants.STATUS) != null
				&& request.getParameter(Constants.STATUS).trim().length() > 0) {
			String status = request.getParameter("status");
			whereClauses.put(Constants.STATUS, status);
		}

		printReportCriteria = whereClauses;
		try {
			int totalRows = refundRequestDao.totalRecord(whereClauses);
			if (totalRows > 0) {
				List<RefundRequestWorkFlow> rows = refundRequestDao.findAll(
						orderClauses, whereClauses, true);
				List<RefundRequestWorkFlowJsonData> ccjd = getJsonData(rows);
				JsonDataWrapper<RefundRequestWorkFlowJsonData> jdw = new JsonDataWrapper<RefundRequestWorkFlowJsonData>(
						page, totalRows, ccjd);
				return jdw;
			}
		} catch (ArgusException e) {
			LOGGER.info(Constants.EXCEPTION, e);
		}
		return null;
	}

	private List<RefundRequestWorkFlowJsonData> getJsonData(
			List<RefundRequestWorkFlow> rows) {
		List<RefundRequestWorkFlowJsonData> refundRequestWorkFlowJsonDataList = new ArrayList<RefundRequestWorkFlowJsonData>();

		if (rows != null && rows.size() > 0) {
			for (RefundRequestWorkFlow eTemp : rows) {
				RefundRequestWorkFlowJsonData rrjd = new RefundRequestWorkFlowJsonData();
				rrjd.setPatientName(eTemp.getArProductivity().getPatientName());

				if (eTemp.getArProductivity().getTeam() != null) {
					rrjd.setTeam(eTemp.getArProductivity().getTeam().getName());
				}

				rrjd.setPatientAccNo(eTemp.getArProductivity()
						.getPatientAccNo());
				rrjd.setResponsibleParty(eTemp.getResponsibleParty());
				rrjd.setDos(eTemp.getDos());
				rrjd.setId(eTemp.getId());
				if (eTemp.getStatus() == 0) {
					rrjd.setStatus("Pending");
				} else if (eTemp.getStatus() == 1) {
					rrjd.setStatus("Done");
				} else {
					rrjd.setStatus("Reject");
				}
				rrjd.setArProdId(eTemp.getArProductivity().getId());
				rrjd.setTotalAmount(eTemp.getTotalAmount());
				refundRequestWorkFlowJsonDataList.add(rrjd);
			}
		}
		return refundRequestWorkFlowJsonDataList;
	}

	@RequestMapping(value = "/printhtmlreport", method = RequestMethod.GET)
	public String printHtmlReport(Map<String, Object> map) {

		List<RefundRequestWorkFlow> rows = null;
		try {
			rows = refundRequestDao.findAll(null, printReportCriteria, true);

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}

		map.put("PRINT_REPORT_CRITERIA", printReportCriteria);
		map.put("SHOW_FOOTER", false);

		map.put("refundRequestWorkFlowList", rows);
		return "refundRequestWorkFlowListToPrint";
	}

	private void populateValuesOnUI(Model model) {
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

		LOGGER.debug("out populateValuesOnUI");
	}

}
