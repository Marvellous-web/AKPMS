package argus.mvc.qamanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import argus.domain.Department;
import argus.domain.QAProductivitySampling;
import argus.domain.QcPoint;
import argus.domain.User;
import argus.exception.ArgusException;
import argus.repo.department.DepartmentDao;
import argus.repo.qamanager.QAProductivitySamplingDao;
import argus.repo.qamanager.QCPointChecklistDao;
import argus.repo.qcpoint.QcPointDao;
import argus.repo.user.UserDao;
import argus.util.AkpmsUtil;
import argus.util.Constants;

@Controller
@RequestMapping(value = "/qamanager/reports")
public class QAReportController {

	private static final Logger LOGGER = Logger
			.getLogger(QAReportController.class);

	@Autowired
	private DepartmentDao departmentDao;

	@Autowired
	private QAProductivitySamplingDao daoQaProductivitySamplingDao;

	@Autowired
	private QCPointChecklistDao checklistDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private QcPointDao qcPointDao;

	@Autowired
	private QAProductivitySamplingDao qaProductivitySamplingDao;

	@ModelAttribute("parentDepartments")
	public List<Department> parentDepartments() {

		List<Department> parentDepartemts = null;
		try {
			parentDepartemts = departmentDao.findAllParentOrderedByName();
		} catch (ArgusException e) {
			LOGGER.error("Failed to findAllDepartmentOrderedByName", e);
		}

		return parentDepartemts;
	}

	@RequestMapping(value = "")
	public String qAReports(Model modelMap) {

		modelMap.addAttribute(Constants.MONTHS, AkpmsUtil.getMonths());
		modelMap.addAttribute(Constants.YEARS, AkpmsUtil.getYears());

		// Fetch all active user who have qa manager permission
		List<User> userList = new ArrayList<User>();

		try {
			Map<String, String> whereClauses = new HashMap<String, String>();
			whereClauses.put(Constants.SELECTED_ROLES_IDS,
					Constants.STANDART_USER_ROLE_ID.toString());
			whereClauses.put(Constants.STATUS, Constants.STRING_ONE);
			whereClauses.put(Constants.PERMISSION,
					Constants.PERMISSION_QA_WORKSHEET_MANAGER);

			userList = userDao.findAll(null, whereClauses);
		} catch (Exception e) {
			LOGGER.error(e);
		}

		modelMap.addAttribute("QA_MANAGER_LIST", userList);
		LOGGER.info(":: showing reports ::");

		return "qareport";
	}

	@RequestMapping(value = "/qareportuser", method = RequestMethod.GET)
	public String calculatedQAErrorReportData(Map<String, Object> map,
			WebRequest request) {

		Map<String, String> whereClause = null;
		if (request != null) {
			whereClause = getQAReportCriteriaMap(request.getParameterMap());
			map = populateGenericMapData(map, request);

			try {

				/*
				 * List<Object[]> qaReportResults =
				 * this.daoQaProductivitySamplingDao
				 * .findProductivityCountWithUsersAndQAWorksheets( whereClause,
				 * orderClause);
				 */
				List<Object[]> qaReportResults = this.daoQaProductivitySamplingDao
						.findQAWorksheetUserReportData(whereClause);

				map.put("reportData", qaReportResults);

			} catch (NumberFormatException e) {
				LOGGER.error(e);
			} catch (ArgusException e) {
				LOGGER.error(e);
			}
		}
		
		if(map.get(Constants.DEPARTMENT_ID).equals(Constants.PAYMENT_DEPARTMENT_ID) || map.get(Constants.DEPARTMENT_ID).equals(Constants.CHARGE_DEPARTMENT_ID) )
			return "qaReportUser";
		else
			return "qaReportUserOld";
	}

	@RequestMapping(value = "/qareportqcpoint", method = RequestMethod.GET)
	public String majorQAErrorReportData(Map<String, Object> map,
			WebRequest request) {
		Map<String, String> whereClause = null;
		if (request != null) {
			map = populateGenericMapData(map, request);

			whereClause = getQAReportCriteriaMap(request.getParameterMap());

			try {
				// fetch user for selected department/sub-department

				List<Object[]> qaReportResults = this.checklistDao
						.findMostlyErrorsDoneByUsers(whereClause);

				map.put("qaReportData", qaReportResults);

			} catch (NumberFormatException e) {
				LOGGER.error(e);
			} catch (ArgusException e) {
				LOGGER.error(e);
			}
		}

		return "qaReportQcPoint";
	}

	private Map<String, Object> populateGenericMapData(Map<String, Object> map,
			WebRequest request) {

		
		  map.put(Constants.START_MONTH,request.getParameter("qaReportFromDate"));
		  map.put(Constants.END_MONTH,request.getParameter("qaReportToDate"));
		
	//For Sub department
		if(request.getParameter(Constants.SUB_DEPARTMENT).equalsIgnoreCase(
				Constants.CHARGE_ENTRY_ID)) {
			map.put(Constants.SUB_DEPARTMENT_NAME, Constants.CHARGE_ENTRY_NAME);
		}else if (request.getParameter(Constants.SUB_DEPARTMENT).equalsIgnoreCase(
				Constants.CODING_PRIMARY_CARE_ID)) {
			map.put(Constants.SUB_DEPARTMENT_NAME, Constants.CODING_PRIMARY_CARE_NAME);
		}else if (request.getParameter(Constants.SUB_DEPARTMENT).equalsIgnoreCase(
				Constants.CODING_SPECIALIST_ID)) {
			map.put(Constants.SUB_DEPARTMENT_NAME, Constants.CODING_SPECIALIST_NAME);
		}else if (request.getParameter(Constants.SUB_DEPARTMENT).equalsIgnoreCase(
				Constants.DEMO_AND_VERIFICATION_ID)) {
			map.put(Constants.SUB_DEPARTMENT_NAME, Constants.DEMO_AND_VERIFICATION_NAME);
		}else if (request.getParameter(Constants.SUB_DEPARTMENT).equalsIgnoreCase(
				Constants.PAYMENT_POSTING_ID)) {
			map.put(Constants.SUB_DEPARTMENT_NAME, Constants.PAYMENT_POSTING_NAME);
		}else if (request.getParameter(Constants.SUB_DEPARTMENT).equalsIgnoreCase(
				Constants.TREASURY_ID)) {
			map.put(Constants.SUB_DEPARTMENT_NAME, Constants.TREASURY_NAME);
		}else if (request.getParameter(Constants.SUB_DEPARTMENT).equalsIgnoreCase(
				Constants.HMO_ID)) {
			map.put(Constants.SUB_DEPARTMENT_NAME, Constants.HMO_NAME);
		}else if (request.getParameter(Constants.SUB_DEPARTMENT).equalsIgnoreCase(
				Constants.MCL_ID)) {
			map.put(Constants.SUB_DEPARTMENT_NAME, Constants.MCL_NAME);
		}else if (request.getParameter(Constants.SUB_DEPARTMENT).equalsIgnoreCase(
				Constants.MCR_ID)) {
			map.put(Constants.SUB_DEPARTMENT_NAME, Constants.MCR_NAME);
		}else if (request.getParameter(Constants.SUB_DEPARTMENT).equalsIgnoreCase(
				Constants.PPO_ID)) {
			map.put(Constants.SUB_DEPARTMENT_NAME, Constants.PPO_NAME);
		}else if (request.getParameter(Constants.SUB_DEPARTMENT).equalsIgnoreCase(
				Constants.SELF_PAY_AND_CEP_ID)) {
			map.put(Constants.SUB_DEPARTMENT_NAME, Constants.SELF_PAY_AND_CEP_NAME);
		}else if (request.getParameter(Constants.SUB_DEPARTMENT).equalsIgnoreCase(
				Constants.WORK_COMP_ID)) {
			map.put(Constants.SUB_DEPARTMENT_NAME, Constants.WORK_COMP_NAME);
		}else if (request.getParameter(Constants.SUB_DEPARTMENT).equalsIgnoreCase(
				Constants.CHDP_ID)) {
			map.put(Constants.SUB_DEPARTMENT_NAME, Constants.CHDP_NAME);
		}
		else if (request.getParameter(Constants.SUB_DEPARTMENT).equalsIgnoreCase(
				"-1")) {
			map.put(Constants.SUB_DEPARTMENT_NAME, "All");
		}
		  
	//For Department
		if (request.getParameter(Constants.DEPARTMENT).equalsIgnoreCase(
				Constants.PAYMENT_DEPARTMENT_ID)) {
			map.put(Constants.DEPARTMENT, Constants.PAYMENT_DEPARTMENT_NAME);
		} else if (request.getParameter(Constants.DEPARTMENT).equalsIgnoreCase(
				Constants.CHARGE_DEPARTMENT_ID)) {
			map.put(Constants.DEPARTMENT, Constants.CHARGE_DEPARTMENT_NAME);
		}else if (request.getParameter(Constants.DEPARTMENT).equalsIgnoreCase(
				Constants.ACCOUNTING_DEPARTMENT_ID)) {
			map.put(Constants.DEPARTMENT, Constants.ACCOUNTING_DEPARTMENT_NAME);
		} else if (request.getParameter(Constants.DEPARTMENT).equalsIgnoreCase(
				Constants.AR_DEPARTMENT_ID)) {
			map.put(Constants.DEPARTMENT, Constants.AR_DEPARTMENT_NAME);
		}
		map.put(Constants.DEPARTMENT_ID,
				request.getParameter(Constants.DEPARTMENT));

		return map;
	}

	private Map<String, String> getQAReportCriteriaMap(
			Map<String, String[]> parameterMap) {
		Map<String, String> criteriaMap = new HashMap<String, String>(
				Constants.FOUR);
		if (parameterMap != null && parameterMap.size() > Constants.ZERO) {
			if (parameterMap.containsKey(Constants.DEPARTMENT)) {
				criteriaMap.put(Constants.DEPARTMENT,
						parameterMap.get(Constants.DEPARTMENT)[0]);
				criteriaMap.put(Constants.DEPARTMENT_ID,
						parameterMap.get(Constants.DEPARTMENT)[0]);
			}
			if (parameterMap.containsKey(Constants.SUB_DEPARTMENT)
					&& !parameterMap.get(Constants.SUB_DEPARTMENT)[0]
							.equals("-1")) {
				criteriaMap.put(Constants.SUB_DEPARTMENT,
						parameterMap.get(Constants.SUB_DEPARTMENT)[0]);
			}
			if (parameterMap.containsKey(Constants.QA_REPORT_FROM_DATE)) {
				criteriaMap.put(Constants.QA_REPORT_FROM_DATE,
						parameterMap.get(Constants.QA_REPORT_FROM_DATE)[0]);
			}
			if (parameterMap.containsKey(Constants.QA_REPORT_TO_DATE)) {
				criteriaMap.put(Constants.QA_REPORT_TO_DATE,
						parameterMap.get(Constants.QA_REPORT_TO_DATE)[0]);
			}
			// set created by id
			if (parameterMap.containsKey(Constants.CREATED_BY)) {
				criteriaMap.put(Constants.CREATED_BY, StringUtils.join(
						parameterMap.get(Constants.CREATED_BY), ","));
			}
		}

		return criteriaMap;
	}

	@RequestMapping(value = "/summaryreport", method = RequestMethod.GET)
	public String qaSummaryReport(Map<String, Object> map, WebRequest request,
			Model model) {

		Map<String, String> whereClause = null;

		List<QAProductivitySampling> qaProductivitiesSampleList = new ArrayList<QAProductivitySampling>();

		if (request != null) {
			map = populateGenericMapData(map, request);

			try {
				fetchQCPoints(request.getParameter(Constants.DEPARTMENT),
						request.getParameter(Constants.SUB_DEPARTMENT), model);
			} catch (Exception e) {
				LOGGER.error("unable to fetch qc points");
				LOGGER.error(e);
			}

			String orderBy = null;

			if (request.getParameter("orderby") != null) {
				orderBy = request.getParameter("orderby");
			}

			Map<String, String> orderClauses = new HashMap<String, String>();

			if (orderBy != null) {
				orderClauses.put(Constants.ORDER_BY, orderBy);
			}

			whereClause = getQAReportCriteriaMap(request.getParameterMap());

			whereClause.put(Constants.STATUS, Constants.STRING_TWO);

			try {
				qaProductivitiesSampleList = this.qaProductivitySamplingDao
						.findAll(whereClause, orderClauses, true);
			} catch (Exception e) {
				LOGGER.error("unable to fetch sampling data");
				LOGGER.error(e);
			}
		}

		model.addAttribute("QA_PROD_SAMPLE_DATA_LIST",
				qaProductivitiesSampleList);

		String view = "";
		if (request.getParameter(Constants.DEPARTMENT).equalsIgnoreCase(
				Constants.AR_DEPARTMENT_ID)) {
			view = "arSummaryReport";
		} else if (request.getParameter(Constants.DEPARTMENT).equalsIgnoreCase(
				Constants.PAYMENT_DEPARTMENT_ID)) {
			view = "paymentSummaryReport";
		} else if (request.getParameter(Constants.DEPARTMENT).equalsIgnoreCase(
				Constants.CHARGE_DEPARTMENT_ID)) {
			view = "codingSummaryReport";
		}

		return view;
	}

	/**
	 * fetch qc points for given department
	 *
	 * @param departmentId
	 * @param model
	 * @throws ArgusException
	 */
	// private void fetchQCPoints(String departmentId, String subDepartmentId,
	// Model model) throws ArgusException {
	//
	// Map<String, String> whereClauses = new HashMap<String, String>();
	// Map<String, String> orderClauses = new HashMap<String, String>();
	//
	// List<QcPoint> parentQCPoints = qcPointDao
	// .getQcPointsWithParentIdAndChildCount(
	// Long.valueOf(departmentId),
	// Long.valueOf(subDepartmentId));
	// model.addAttribute("PARENT_QC_POINTS", parentQCPoints);
	//
	// whereClauses.put(Constants.DEPARTMENT_ID, departmentId);
	//
	// if (null != subDepartmentId) {
	// whereClauses.put(Constants.SUB_DEPARTMENT_ID, subDepartmentId);
	// }
	//
	// whereClauses.put(Constants.CHILD_ONLY, "true");
	//
	// orderClauses.put(Constants.ORDER_BY, "parent.id, d.id");
	//
	// List<QcPoint> qcPointChildrenOnly = qcPointDao.findAll(orderClauses,
	// whereClauses, false);
	//
	// LOGGER.info("qcPointChildrenOnly:: " + qcPointChildrenOnly.size());
	//
	// model.addAttribute("CHILDREN_QC_POINTS", qcPointChildrenOnly);
	// }

	private void fetchQCPoints(String departmentId, String subDepartmentId,
			Model model) throws ArgusException {

		Map<String, String> whereClauses = new HashMap<String, String>();
		Map<String, String> orderClauses = new HashMap<String, String>();

		whereClauses.put(Constants.DEPARTMENT_ID, departmentId);

		List<QcPoint> parentQCPoints = new ArrayList<QcPoint>();
		if (subDepartmentId != null && !subDepartmentId.equalsIgnoreCase("-1")) {
			whereClauses.put(Constants.SUB_DEPARTMENT_ID, subDepartmentId);

			parentQCPoints = qcPointDao.getQcPointsWithParentIdAndChildCount(
					Long.valueOf(departmentId), Long.valueOf(subDepartmentId));
		} else {
			parentQCPoints = qcPointDao.getQcPointsWithParentIdAndChildCount(
					Long.valueOf(departmentId), null);
		}

		model.addAttribute("PARENT_QC_POINTS", parentQCPoints);

		Map<Long, String> parentQCPointMap = new HashMap<Long, String>();

		if (parentQCPoints != null && parentQCPoints.size() > 0) {
			for (QcPoint qcPoint : parentQCPoints) {
				parentQCPointMap.put(qcPoint.getId(), qcPoint.getName());
			}
		}

		model.addAttribute("PARENT_QC_POINTS_MAP", parentQCPointMap);

		whereClauses.put(Constants.CHILD_ONLY, "true");

		orderClauses.put(Constants.ORDER_BY, "parent.id, d.id");

		List<QcPoint> qcPointChildrenOnly = qcPointDao.findAll(orderClauses,
				whereClauses, false);

		LOGGER.info("qcPointChildrenOnly:: " + qcPointChildrenOnly.size());

		model.addAttribute("CHILDREN_QC_POINTS", qcPointChildrenOnly);
	}

	/**
	 * 
	 * @param map
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/monthlysummaryreport", method = RequestMethod.GET)
	public String monthlySummaryReport(Map<String, Object> map,
			WebRequest request, Model model) {

		Map<String, String> whereClause = null;
		if (request != null) {
			whereClause = getQAReportCriteriaMap(request.getParameterMap());
			map = populateGenericMapData(map, request);

			try {
				List<Object[]> qaReportResults = this.daoQaProductivitySamplingDao
						.monthlyQASummary(whereClause);

				map.put("reportData", qaReportResults);

			} catch (NumberFormatException e) {
				LOGGER.error(e);
			} catch (ArgusException e) {
				LOGGER.error(e);
			}
		}

		return "monthlysummaryreport";
	}
}
