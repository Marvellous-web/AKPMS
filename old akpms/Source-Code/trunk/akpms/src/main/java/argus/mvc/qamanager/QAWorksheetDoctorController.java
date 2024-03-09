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

import argus.domain.Doctor;
import argus.domain.QAWorksheet;
import argus.domain.QAWorksheetDoctor;
import argus.exception.ArgusException;
import argus.repo.chargeProductivity.ChargeProductivityDao;
import argus.repo.doctor.DoctorDao;
import argus.repo.paymentproductivity.CredentialingAccountingProductivityDao;
import argus.repo.paymentproductivity.PaymentProductivityDao;
import argus.repo.productivity.ArProductivityDao;
import argus.repo.qamanager.QAProductivitySamplingDao;
import argus.repo.qamanager.QAWorksheetDao;
import argus.repo.qamanager.QAWorksheetDoctorDao;
import argus.util.Constants;
import argus.util.JsonDataWrapper;
import argus.util.QAWorksheetDoctorJsonData;
import argus.validator.QAWorksheetDoctorValidator;

@Controller
public class QAWorksheetDoctorController {

	private static Logger LOGGER = Logger
			.getLogger(QAWorksheetDoctorController.class);

	@Autowired
	private DoctorDao doctorDao;

	@Autowired
	private QAWorksheetDao qaworksheetDao;

	@Autowired
	private QAWorksheetDoctorDao qaworksheetDoctorDao;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private QAWorksheetDoctorValidator validator;

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
	@InitBinder("qaworksheetDoctor")
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.setDisallowedFields("id");
		webDataBinder.setValidator(validator);
	}

	private void poputlateForm(Long id, Map<String, Object> map) {
		QAWorksheet qaworksheet = null;

		try {
			qaworksheet = (QAWorksheet) qaworksheetDao.findById(id);
			map.put(Constants.QAWORKSHEET, qaworksheet);
		} catch (ArgusException e1) {
			LOGGER.error("Failed getting QAWorksheet", e1);
		}

		try {
			Map<String, String> whereClause = new HashMap<String, String>();
			whereClause.put(Constants.STATUS, Constants.ACTIVATE + "");
			map.put(Constants.DOCTOR_LIST,
					doctorDao.findAll(null, whereClause, false));
		} catch (Exception e) {
			LOGGER.error("Failed getting Doctor list", e);
			map.put(Constants.DOCTOR_LIST, new ArrayList<Doctor>());
		}

	}

	/**
	 * Show QAWorksheetDoctor
	 * 
	 * @param QAWorksheetDoctor
	 *            qaworksheetDoctor as model
	 * @see (@link QAWorksheetDoctor.java)
	 * @param Long
	 *            id (QAworksheet id)
	 * @see (@link QAWorksheet.java)
	 * @return Next result to be dispalayed ("qamanager/qaWorksheetDoctor")
	 * */
	@RequestMapping(value = "qamanager/adddoctor", method = RequestMethod.GET)
	public String showQAWorksheetDoctor(
			@ModelAttribute("qaworksheetDoctor") QAWorksheetDoctor qaworksheetDoctor,
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

		return "qamanager/adddoctor";
	}

	/**
	 * Add QAWorksheetDoctor
	 * 
	 * @param QAWorksheetDoctor
	 *            qaworksheetDoctor as model
	 * @see(link QAWorksheetDoctor.java)
	 * @return return Next result to be dispalayed
	 *         ("qamanager/qaWorksheetDoctor")
	 */
	@RequestMapping(value = "qamanager/adddoctor", method = RequestMethod.POST)
	public String saveQAWorksheetDoctor(
			@Valid @ModelAttribute("qaworksheetDoctor") QAWorksheetDoctor qaworksheetDoctor,
			BindingResult results, WebRequest request, Map<String, Object> map,
			HttpSession session) {

		if (results.hasErrors()) {
			poputlateForm(qaworksheetDoctor.getQaWorksheet().getId(), map);
			return "qamanager/adddoctor";
		}

		try {
			Map<String, String> whereClauses = new HashMap<String, String>();
			int totalRecord = 0;
			QAWorksheet qaWorksheet = this.qaworksheetDao.findById(
					qaworksheetDoctor.getQaWorksheet().getId(), true);

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
				whereClauses.put(Constants.USER, qaworksheetDoctor.getDoctor()
						.getId().toString());
				totalRecord = this.paymentProductivityDao
						.totalRecord(whereClauses);
				// done

			} else if (Constants.CHARGE_DEPARTMENT_ID
					.equalsIgnoreCase(qaWorksheet.getDepartment().getId()
							.toString())) {
				whereClauses.put(Constants.USER, qaworksheetDoctor.getDoctor()
						.getId().toString());
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
							&& !qaWorksheet.getArStatusCode().trim().equals("")) {
						whereClauses.put(Constants.STATUS_CODE,
								qaWorksheet.getArStatusCode());
					}
				}
				totalRecord = this.chargeProductivityDao
						.totalRecord(whereClauses);

			} else if (Constants.AR_DEPARTMENT_ID.equalsIgnoreCase(qaWorksheet
					.getDepartment().getId().toString())) {
				whereClauses.put(Constants.USER, qaworksheetDoctor.getDoctor()
						.getId().toString());
				totalRecord = this.arProductivityDao.totalRecord(whereClauses);

			} else if (Constants.ACCOUNTING_DEPARTMENT_ID
					.equalsIgnoreCase(qaWorksheet.getDepartment().getId()
							.toString())) {
				whereClauses.put(Constants.USER, qaworksheetDoctor.getDoctor()
						.getId().toString());
				totalRecord = this.caProductivityDao.totalRecord(whereClauses);
			}

			totalRecord = new Float(qaworksheetDoctor.getPercentageValue()
					* (totalRecord / 100)).intValue();

			qaworksheetDoctor.setRemarks(totalRecord + "");

			qaworksheetDoctorDao.save(qaworksheetDoctor);

			session.setAttribute(Constants.SUCCESS, messageSource.getMessage(
					"qaworksheet.doctor.addedSuccessfully",
					new Object[] { qaworksheetDoctor.getId() }, Locale.ENGLISH));

		} catch (ArgusException e) {
			LOGGER.error("Failed to save QAWorkheetDoctor", e);
		}
		String addMore = request.getParameter(Constants.ADD_MORE);
		if (addMore != null && !addMore.equals("")
				&& Long.parseLong(addMore) == Constants.ONE) {
			return "redirect:/qamanager/adddoctor?id="
					+ qaworksheetDoctor.getQaWorksheet().getId();
		}

		return "redirect:/qamanager";
	}

	/**
	 * Populate QAWorksheet Doctor flexigrid list
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "qamanager/doctorjson", method = { RequestMethod.GET }, produces = "application/json")
	public @ResponseBody
	JsonDataWrapper<QAWorksheetDoctorJsonData> qaworksheetDoctorFlexPopulater(
			WebRequest request) throws ArgusException {
		List<QAWorksheetDoctorJsonData> jsonData = new ArrayList<QAWorksheetDoctorJsonData>();
		if (request != null) {
			String qaworksheetId = request
					.getParameter(Constants.QAWORKSHEET_ID);

			if (qaworksheetId != null && !qaworksheetId.equals("")) {
				jsonData = getQAWorksheetDoctorJsonData(Long
						.parseLong(qaworksheetId));
			}
		}
		int page = 1;
		JsonDataWrapper<QAWorksheetDoctorJsonData> jdw = new JsonDataWrapper<QAWorksheetDoctorJsonData>(
				page, jsonData.size(), jsonData);

		return jdw;
	}

	/**
	 * Helper for flex grid
	 * 
	 * @return List<QAWorksheetJsonData>
	 */
	private List<QAWorksheetDoctorJsonData> getQAWorksheetDoctorJsonData(Long id)
			throws ArgusException {

		List<QAWorksheetDoctorJsonData> qaworksheetDoctorJD = new ArrayList<QAWorksheetDoctorJsonData>();
		Map<String, String> whereClausesQAWorksheetDoctor = new HashMap<String, String>();
		whereClausesQAWorksheetDoctor.put(Constants.QAWORKSHEET_ID,
				id.toString());
		List<QAWorksheetDoctor> qaworksheetDoctorList = qaworksheetDoctorDao
				.findAll(null, whereClausesQAWorksheetDoctor, true);

		for (QAWorksheetDoctor doctor : qaworksheetDoctorList) {
			qaworksheetDoctorJD.add(new QAWorksheetDoctorJsonData(doctor
					.getId(), doctor.getDoctor().getName(), doctor
					.getPercentageValue(), doctor.getRemarks()));
		}

		return qaworksheetDoctorJD;
	}

	/**
	 * Delete QAWorksheetDoctor user and re populate the flexigrid
	 * 
	 * @param item
	 *            : QAWorksheetDoctor id
	 * @see (@Link args.domain.QAWorksheetDoctor.java)
	 * @return List<QAWorksheetJsonData>
	 */
	@RequestMapping(value = "/qamanager/deletedoctor", method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteQAWorksheetDoctorUser(
			@RequestParam(value = "item", required = true) Long item) {

		LOGGER.info("QAWorksheetDoctor deleted. Id is : " + item);

		boolean isDeleted = false;
		int deletedItemId = 0;
		try {
			deletedItemId = qaworksheetDoctorDao.delete(item);
		} catch (ArgusException e) {
			LOGGER.error("Failed to delete doctor", e);
		}
		if (deletedItemId > 0) {
			isDeleted = true;
		}

		return isDeleted;
	}

}
