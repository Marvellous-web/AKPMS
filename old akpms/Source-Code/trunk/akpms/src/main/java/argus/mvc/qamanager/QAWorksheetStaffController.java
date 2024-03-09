package argus.mvc.qamanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import argus.domain.QAWorksheet;
import argus.domain.QAWorksheetStaff;
import argus.exception.ArgusException;
import argus.repo.chargeProductivity.ChargeProductivityDao;
import argus.repo.paymentproductivity.CredentialingAccountingProductivityDao;
import argus.repo.paymentproductivity.PaymentProductivityDao;
import argus.repo.productivity.ArProductivityDao;
import argus.repo.qamanager.QAProductivitySamplingDao;
import argus.repo.qamanager.QAWorksheetDao;
import argus.repo.qamanager.QAWorksheetStaffDao;
import argus.repo.user.UserDao;
import argus.util.Constants;
import argus.util.JsonDataWrapper;
import argus.util.QAWorksheetStaffJsonData;
import argus.validator.QAWorksheetStaffValidator;

@Controller
public class QAWorksheetStaffController {

	private static Logger LOGGER = Logger
			.getLogger(QAWorksheetStaffController.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private QAWorksheetDao qaworksheetDao;

	@Autowired
	private QAWorksheetStaffDao qaworksheetStaffDao;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private QAWorksheetStaffValidator validator;

	@Autowired
	private QAProductivitySamplingDao qaProductivitySamplingDao;
	
	@Autowired
	private PaymentProductivityDao paymentProductivityDao;
	
	@Autowired
	private ChargeProductivityDao chargeProductivityDao;
	
	@Autowired
	private ArProductivityDao arProductivityDao;
	
	@Autowired
	private CredentialingAccountingProductivityDao caProductivityDao;
	/**
	 * Bind validator to model
	 * 
	 * @param webDataBinder
	 */
	@InitBinder("qaworksheetStaff")
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.setDisallowedFields("id");
		webDataBinder.setValidator(validator);
	}

	private void poputlateForm(Long id, Map<String, Object> map) {
		QAWorksheet qaworksheet = null;
		try {
			qaworksheet = (QAWorksheet) qaworksheetDao.findById(id);
			map.put(Constants.QAWORKSHEET, qaworksheet);

			// fetch user for selected department/sub-department
			if (qaworksheet.getDepartment().getId() != null
					&& qaworksheet.getSubDepartment() != null
					&& qaworksheet.getSubDepartment().getId() != null) {
				map.put(Constants.USER_LIST, userDao
						.getUserByParentDeptIdAndSubDeptById(qaworksheet
								.getDepartment().getId(), qaworksheet
								.getSubDepartment().getId()));
			} else if (qaworksheet.getDepartment().getId() != null) {
				map.put(Constants.USER_LIST, userDao
						.getUserByDeptId(qaworksheet.getDepartment().getId()));
			}
		} catch (ArgusException e1) {
			LOGGER.error("Failed getting QAWorksheet", e1);
		}
	}

	/**
	 * Show QAWorksheetStaff
	 * 
	 * @param QAWorksheetStaff
	 *            qaworksheetStaff as model
	 * @see (@link QAWorksheetStaff.java)
	 * @param Long
	 *            id (QAworksheet id)
	 * @see (@link QAWorksheet.java)
	 * @return Next result to be dispalayed ("qamanager/qaWorksheetStaff")
	 * */
	@RequestMapping(value = "qamanager/addstaff", method = RequestMethod.GET)
	public String showQAWorksheetStaff(
			@ModelAttribute("qaworksheetStaff") QAWorksheetStaff qaworksheetStaff,
			@RequestParam(value = "id", required = true) Long id,
			Map<String, Object> map, HttpSession session) {
		poputlateForm(id, map);
		String success = (String) session.getAttribute(Constants.SUCCESS);
		if (success != null) {
			map.put("success", success);
			session.removeAttribute(Constants.SUCCESS);
		}
		String error = (String) session.getAttribute(Constants.ERROR);
		if (error != null) {
			map.put(Constants.ERROR, error);
			session.removeAttribute(Constants.ERROR);
		}

		return "qamanager/addstaff";
	}

	/**
	 * Add QAWorksheetStaff
	 * 
	 * @param QAWorksheetStaff
	 *            qaworksheetStaff as model
	 * @see(link QAWorksheetStaff.java)
	 * @return return Next result to be dispalayed
	 *         ("qamanager/qaWorksheetStaff")
	 */
	@RequestMapping(value = "qamanager/addstaff", method = RequestMethod.POST)
	public String saveQAWorksheetStaff(
			@Valid @ModelAttribute("qaworksheetStaff") QAWorksheetStaff qaworksheetStaff,
			BindingResult results, WebRequest request, Map<String, Object> map,
			HttpSession session) {

		if (results.hasErrors()) {
			poputlateForm(qaworksheetStaff.getQaWorksheet().getId(), map);
			return "qamanager/addstaff";
		}

		try {
			Map<String, String> whereClauses = new HashMap<String, String>();
			int totalRecord = 0;
			QAWorksheet qaWorksheet = this.qaworksheetDao.findById(
					qaworksheetStaff.getQaWorksheet().getId(), true);
			
			whereClauses.put(Constants.MONTH, qaWorksheet.getBillingMonth()
					.toString());

			whereClauses.put(Constants.YEAR, qaWorksheet.getBillingYear()
					.toString());

			if (qaWorksheet.getPostingDateFrom() != null) {
				whereClauses.put(Constants.POSTING_DATE_FROM, qaWorksheet
						.getPostingDateFrom().toString());
			}

			if (qaWorksheet.getPostingDateTo() != null) {
				whereClauses.put(Constants.POSTING_DATE_TO, qaWorksheet
						.getPostingDateTo().toString());
			}
			
			if (Constants.PAYMENT_DEPARTMENT_ID.equalsIgnoreCase(qaWorksheet
					.getDepartment().getId().toString())) {
				whereClauses.put(Constants.USER, qaworksheetStaff.getUser().getId().toString());
				totalRecord = this.paymentProductivityDao.totalRecord(whereClauses);
				//done
				
			} else if (Constants.CHARGE_DEPARTMENT_ID.equalsIgnoreCase(qaWorksheet
					.getDepartment().getId().toString())) {
				whereClauses.put(Constants.USER, qaworksheetStaff.getUser().getId().toString());
				// fetch records from charge productivity
				if (qaWorksheet.getSubDepartment() != null) {
					// build sub department criteria for ChargeProductivity
					String subDepartmentId = qaWorksheet.getSubDepartment()
							.getId().toString();
					if (subDepartmentId
							.equalsIgnoreCase(Constants.CHARGE_DEPARTMENT_DEMO)) {
						whereClauses.put(Constants.CHARGE_DEPARTMENT_DEMO,
								subDepartmentId);
					} else if (subDepartmentId
							.equalsIgnoreCase(Constants.CHARGE_DEPARTMENT_CE)) {
						whereClauses.put(Constants.CHARGE_DEPARTMENT_CE,
								subDepartmentId);
					} else if (subDepartmentId
							.equalsIgnoreCase(Constants.CHARGE_DEPARTMENT_CODING_PRIMARY)) {
						whereClauses.put(
								Constants.CHARGE_DEPARTMENT_CODING_PRIMARY,
								subDepartmentId);
					} else if (subDepartmentId
							.equalsIgnoreCase(Constants.CHARGE_DEPARTMENT_CODING_SPECIAL)) {
						whereClauses.put(
								Constants.CHARGE_DEPARTMENT_CODING_SPECIAL,
								subDepartmentId);
					}
					if (qaWorksheet.getDepartment().getId() == Long
							.valueOf(Constants.AR_DEPARTMENT_ID) 
							&& qaWorksheet.getArStatusCode() != null
							&& ! qaWorksheet.getArStatusCode().trim().equals("")) {
						whereClauses.put(
								Constants.STATUS_CODE,
								qaWorksheet.getArStatusCode());
					}
				}
				totalRecord = this.chargeProductivityDao.totalRecord(whereClauses);
			
			} else if (Constants.AR_DEPARTMENT_ID.equalsIgnoreCase(qaWorksheet
					.getDepartment().getId().toString())) {
				whereClauses.put(Constants.USER, qaworksheetStaff.getUser().getId().toString());
				totalRecord = this.arProductivityDao.totalRecord(whereClauses);
				
			} else if (Constants.ACCOUNTING_DEPARTMENT_ID.equalsIgnoreCase(qaWorksheet
					.getDepartment().getId().toString())) {
				whereClauses.put(Constants.USER, qaworksheetStaff.getUser().getId().toString());
				totalRecord = this.caProductivityDao.totalRecord(whereClauses);
			}
			
			totalRecord = new Float(qaworksheetStaff.getPercentageValue() * (totalRecord  / 100)).intValue();
			
			qaworksheetStaff.setRemarks(totalRecord + "");

			qaworksheetStaffDao.save(qaworksheetStaff);

			session.setAttribute(Constants.SUCCESS, messageSource.getMessage(
					"qaworksheet.staff.addedSuccessfully",
					new Object[] { qaworksheetStaff.getId() }, Locale.ENGLISH));

		} catch (ArgusException e) {
			LOGGER.error("Failed to save QAWorkheetStaff", e);
		}
		String addMore = request.getParameter(Constants.ADD_MORE);
		if (addMore != null && !addMore.equals("")
				&& Long.parseLong(addMore) == Constants.ONE) {
			return "redirect:/qamanager/addstaff?id="
					+ qaworksheetStaff.getQaWorksheet().getId();
		}

		return "redirect:/qamanager";
	}

	/**
	 * Populate QAWorksheet Staff flexigrid list
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "qamanager/staffjson", method = { RequestMethod.GET }, produces = "application/json")
	public @ResponseBody JsonDataWrapper<QAWorksheetStaffJsonData> qaworksheetStaffFlexPopulater(
			WebRequest request) throws ArgusException {
		List<QAWorksheetStaffJsonData> jsonData = new ArrayList<QAWorksheetStaffJsonData>();
		if (request != null) {
			String qaworksheetId = request
					.getParameter(Constants.QAWORKSHEET_ID);

			if (qaworksheetId != null && !qaworksheetId.equals("")) {
				jsonData = getQAWorksheetStaffJsonData(Long
						.parseLong(qaworksheetId));
			}
		}
		int page = 1;
		JsonDataWrapper<QAWorksheetStaffJsonData> jdw = new JsonDataWrapper<QAWorksheetStaffJsonData>(
				page, jsonData.size(), jsonData);
		return jdw;
	}

	/**
	 * Helper for flex grid
	 * 
	 * @return List<QAWorksheetJsonData>
	 */
	private List<QAWorksheetStaffJsonData> getQAWorksheetStaffJsonData(Long id)
			throws ArgusException {
		List<QAWorksheetStaffJsonData> qaworksheetStaffJD = new ArrayList<QAWorksheetStaffJsonData>();
		Map<String, String> whereClausesQAWorksheetStaff = new HashMap<String, String>();
		whereClausesQAWorksheetStaff.put(Constants.QAWORKSHEET_ID,
				id.toString());
		List<QAWorksheetStaff> qaworksheetStaffList = qaworksheetStaffDao
				.findAll(null, whereClausesQAWorksheetStaff, true);

		for (QAWorksheetStaff staff : qaworksheetStaffList) {
			qaworksheetStaffJD.add(new QAWorksheetStaffJsonData(staff.getId(), staff
					.getUser().getFirstName()
					+ " "
					+ staff.getUser().getLastName(),
					staff.getPercentageValue(), staff.getRemarks()));
		}
		return qaworksheetStaffJD;
	}

	/**
	 * Delete QAWorksheetStaff user and re populate the flexigrid
	 * 
	 * @param item
	 *            : QAWorksheetStaff id
	 * @see (@Link args.domain.QAWorksheetStaff.java)
	 * @return List<QAWorksheetJsonData>
	 */
	@RequestMapping(value = "/qamanager/deletestaff", method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteQAWorksheetStaffUser(
			@RequestParam(value = "item", required = true) Long item) {

		LOGGER.info("QAWorksheetStaff deleted. Id is : " + item);

		boolean isDeleted = false;
		int deletedItemId = 0;
		try {
			deletedItemId = qaworksheetStaffDao.delete(item);
		} catch (ArgusException e) {
			LOGGER.error("Failed to delete staff", e);
		}
		if (deletedItemId > 0) {
			isDeleted = true;
		}

		return isDeleted;
	}

}
