package argus.mvc.qamanager;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
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
import org.springframework.web.context.request.WebRequest;

import argus.domain.ArProductivity;
import argus.domain.ChargeProductivity;
import argus.domain.Department;
import argus.domain.QAProductivitySampling;
import argus.domain.QAWorksheet;
import argus.domain.QAWorksheetDoctor;
import argus.domain.QAWorksheetStaff;
import argus.domain.QcPoint;
import argus.domain.User;
import argus.domain.paymentproductivity.CredentialingAccountingProductivity;
import argus.domain.paymentproductivity.PaymentProductivity;
import argus.exception.ArgusException;
import argus.repo.chargeProductivity.ChargeProductivityDao;
import argus.repo.department.DepartmentDao;
import argus.repo.paymentproductivity.CredentialingAccountingProductivityDao;
import argus.repo.paymentproductivity.PaymentProductivityDao;
import argus.repo.productivity.ArProductivityDao;
import argus.repo.qamanager.QAProductivitySamplingDao;
import argus.repo.qamanager.QAWorksheetDao;
import argus.repo.qamanager.QAWorksheetDoctorDao;
import argus.repo.qamanager.QAWorksheetStaffDao;
import argus.repo.qcpoint.QcPointDao;
import argus.repo.user.UserDao;
import argus.util.AkpmsUtil;
import argus.util.Constants;
import argus.util.DepartmentJsonData;
import argus.util.JsonDataWrapper;
import argus.util.QAWorksheetDoctorJsonData;
import argus.util.QAWorksheetJsonData;
import argus.util.QAWorksheetStaffJsonData;
import argus.validator.QAWorksheetValidator;

@Controller
@RequestMapping(value = "/qamanager")
public class QAWorksheetController {

	private static Logger LOGGER = Logger
			.getLogger(QAWorksheetController.class);

	@Autowired
	private QAWorksheetValidator qaworksheetValidator;

	@Autowired
	private DepartmentDao departmentDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private QAWorksheetDao qaWorksheetDao;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private QAWorksheetStaffDao qaWorksheetStaffDao;

	@Autowired
	private QAWorksheetDoctorDao qaWorksheetDoctorDao;

	@Autowired
	private ChargeProductivityDao chargeProductivityDao;

	@Autowired
	private QAProductivitySamplingDao qaProductivitySamplingDao;

	@Autowired
	private ArProductivityDao arProductivityDao;

	@Autowired
	private CredentialingAccountingProductivityDao credentialingAccountingProductivityDao;

	@Autowired
	private PaymentProductivityDao paymentProductivityDao;

	// Print Report criteria
	private Map<String, String> printReportCriteria = new HashMap<String, String>();

	@Resource(name = "arStatusCodes")
	private Properties arStatusCodesProps;

	@Autowired
	private QcPointDao qcPointDao;

	/**
	 * function to use populate all dropdown options from database on add/edit
	 * page
	 *
	 * @param model
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void populateDropDownLists(Map<String, Object> map) {
		map.put(Constants.MONTHS, AkpmsUtil.getMonths());
		map.put(Constants.YEARS, AkpmsUtil.getYears());
		map.put("arStatusCodes", new TreeMap<String, String>(
				(Map) arStatusCodesProps));
		try {
			map.put("parentDepartments",
					departmentDao.findAllParentOrderedByName());
		} catch (ArgusException e) {
			LOGGER.error(e);
		}

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

		map.put("QA_MANAGER_LIST", userList);
	}

	@RequestMapping(method = RequestMethod.GET)
	public String qaWorksheetList(Map<String, Object> map, HttpSession session,
			WebRequest request) {

		String success = (String) session.getAttribute(Constants.SUCCESS);
		if (success != null) {
			map.put(Constants.SUCCESS, success);
			session.removeAttribute(Constants.SUCCESS);
		}

		String error = (String) session.getAttribute(Constants.ERROR);
		if (error != null) {
			map.put(Constants.ERROR, error);
			session.removeAttribute(Constants.ERROR);
		}
		// String update = (String) session.getAttribute(Constants.SUCCESS);
		// if (update != null) {
		// map.put(Constants.SUCCESS, update);
		// session.removeAttribute(Constants.SUCCESS);
		// }

		populateDropDownLists(map);

		// need to verify
		return "qamanager/list";
	}

	@InitBinder("qaworksheet")
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.registerCustomEditor(Date.class,
				new PropertyEditorSupport() {
					public void setAsText(String value) {
						try {
							if (value != null && value.trim().length() > 0) {
								LOGGER.info("comng date value is : " + value);
								setValue(new SimpleDateFormat(
										Constants.DATE_FORMAT).parse(value));
							} else {
								LOGGER.info("Date value is: " + value);
								setValue(null);
							}
						} catch (ParseException e) {
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

		webDataBinder.setValidator(qaworksheetValidator);
	}

	/**
	 * function to show add page to add qa worksheet (GET)
	 *
	 * @param qaworksheet
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = { RequestMethod.GET })
	public String showQAWorksheet(Map<String, Object> model,
			WebRequest request, HttpSession session) {
		QAWorksheet qaworksheet = new QAWorksheet();
		if (request != null) {
			String strId = request.getParameter("id");
			if (strId != null && !strId.equals("") && Long.parseLong(strId) > 0) {
				try {
					qaworksheet = this.qaWorksheetDao.findById(Long
							.parseLong(strId));
					model.put("qaworksheet", qaworksheet);
					if (qaworksheet == null) {
						session.setAttribute(
								Constants.SUCCESS,
								messageSource.getMessage("record.notFound",
										null, Locale.ENGLISH).trim());
						return "redirect:/qamanager";
					} else {
						if (qaworksheet.getSubDepartment() != null) {
							model.put("subDepartmentId", qaworksheet
									.getSubDepartment().getId());
						}
					}
				} catch (ArgusException e) {
					LOGGER.error("Error getting QAWorksheet", e);
				}
				model.put(Constants.BUTTON_NAME, Constants.UPDATE_QAWORKSHEET);
			}
		}
		model.put("qaworksheet", qaworksheet);
		model.put(Constants.BUTTON_NAME, Constants.SAVE_QAWORKSHEET);
		populateDropDownLists(model);

		return "qamanager/add";
	}

	/**
	 * function to fetch json for sub-departments for selected department
	 *
	 * @param parentDeparmentId
	 * @return
	 * @throws NumberFormatException
	 * @throws ArgusException
	 */
	@RequestMapping(value = "/subdepartment", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<DepartmentJsonData> subDepartments(
			@RequestParam(value = "parentDepartmentId", required = true) Long parentDeparmentId)
			throws NumberFormatException, ArgusException {
		List<Department> childDepartments = new ArrayList<Department>();

		if (parentDeparmentId != null) {
			Map<String, String> whereClauses = new HashMap<String, String>();
			whereClauses.put(Constants.PARENT_ID, parentDeparmentId.toString());

			childDepartments = departmentDao.findAll(null, whereClauses, false);
		}

		return getDepartmentJsonData(childDepartments);
	}

	/**
	 * function to add qa worksheet (POST)
	 *
	 * @param qaworksheet
	 * @param results
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/add", method = { RequestMethod.POST })
	public String saveQAWorksheet(
			@Valid @ModelAttribute("qaworksheet") QAWorksheet qaworksheet,
			BindingResult results, Map<String, Object> map, HttpSession session) {

		if (results.hasErrors()) {
			populateDropDownLists(map);
			map.put(Constants.BUTTON_NAME, Constants.SAVE_QAWORKSHEET);
			return "qamanager/add";
		}
		if (qaworksheet.getSubDepartment().getId() == -1) {
			qaworksheet.setSubDepartment(null);
		}
		try {

			/** if not general then general % value should be 0 */

			if (qaworksheet.getType() != Constants.QA_WORKSEET_TYPE_GENERAL) {
				qaworksheet.setGeneralPercentage(new Float(0));
			}

			if (qaworksheet.getId() != null) {

				if (qaworksheet.getType() != Constants.QA_WORKSEET_TYPE_BYSTAFF) {
					// If not by staff. Delete staff
					this.qaWorksheetStaffDao
							.deleteQAWorksheetStaffByQAWorsheetId(qaworksheet
									.getId());
				} else if (qaworksheet.getType() != Constants.QA_WORKSEET_TYPE_BYDOCTOR) {
					// If not by doctor. Delete doctor
					this.qaWorksheetDoctorDao
							.deleteQAWorksheetDoctorByQAWorsheetId(qaworksheet
									.getId());
				}
			}

			if (qaworksheet.getId() != null) {
				qaWorksheetDao.update(qaworksheet);
				session.setAttribute(
						Constants.SUCCESS,
						messageSource.getMessage(
								"qaworksheet.updatedSuccessfully",
								new Object[] { qaworksheet.getId() },
								Locale.ENGLISH).trim());
			} else {
				qaWorksheetDao.save(qaworksheet);
				session.setAttribute(
						Constants.SUCCESS,
						messageSource.getMessage(
								"qaworksheet.addedSuccessfully",
								new Object[] { qaworksheet.getId() },
								Locale.ENGLISH).trim());
			}

		} catch (ArgusException e) {
			LOGGER.error("Exception while saving QAWorksheet ", e);
			session.setAttribute(Constants.ERROR, e.getMessage());
			return "qamanager/add";
		}

		// if byStaff is checked take it to the next screen
		if (qaworksheet.getType() != null) {
			if (qaworksheet.getType() == Constants.QA_WORKSEET_TYPE_BYSTAFF) {
				return "redirect:/qamanager/addstaff?id=" + qaworksheet.getId();
			} else if (qaworksheet.getType() == Constants.QA_WORKSEET_TYPE_BYDOCTOR) {
				return "redirect:/qamanager/adddoctor?id="
						+ qaworksheet.getId();
			}
		}

		return "redirect:/qamanager";
	}

	// this function will execute qa worksheet and put the record's id in
	// database (from productivity tables) accordingly

	@RequestMapping(value = "/execute", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	StringBuffer executeQAWorksheet(WebRequest request) {
		LOGGER.info("in executeQAWorksheet method");
		LOGGER.info(request.getParameter("id"));

		StringBuffer msg = new StringBuffer();
		try {
			QAWorksheet qaWorksheet = qaWorksheetDao.findById(Long
					.valueOf(request.getParameter(Constants.ID)));

			if (qaWorksheet != null) {
				// delete all the existing samples for this qa worksheet in case
				// of re-execute (This should be )
				Map<String, String> whereClause = new HashMap<String, String>();
				Map<String, String> orderByClause = new HashMap<String, String>();

				whereClause.put(Constants.QA_WORKSHEET_MONTH, qaWorksheet
						.getBillingMonth().toString());

				whereClause.put(Constants.QA_WORKSHEET_YEAR, qaWorksheet
						.getBillingYear().toString());

				// whereClause.put(Constants.DEPARTMENT_ID,
				// qaWorksheet.getDepartment().getId().toString());

				if (qaWorksheet.getPostingDateFrom() != null) {
					whereClause.put(Constants.POSTING_DATE_FROM, qaWorksheet
							.getPostingDateFrom().toString());
				}

				if (qaWorksheet.getPostingDateTo() != null) {
					whereClause.put(Constants.POSTING_DATE_TO, qaWorksheet
							.getPostingDateTo().toString());
				}

				if (qaWorksheet.getScanDateFrom() != null) {
					whereClause.put(Constants.SCAN_DATE_FROM, qaWorksheet
							.getScanDateFrom().toString());
				}

				if (qaWorksheet.getScanDateTo() != null) {
					whereClause.put(Constants.SCAN_DATE_TO, qaWorksheet
							.getScanDateTo().toString());
				}
				
				List<QAWorksheetStaff> qaWorksheetStaffs = null;
				List<QAWorksheetDoctor> qaWorksheetDoctors = null;

				if (qaWorksheet.getType() == Constants.QA_WORKSEET_TYPE_BYSTAFF) {
					Map<String, String> qaWorksheetStaffWhereClauses = new HashMap<String, String>();
					qaWorksheetStaffWhereClauses.put(Constants.QAWORKSHEET_ID,
							qaWorksheet.getId().toString());

					// find staff by worksheet id
					qaWorksheetStaffs = this.qaWorksheetStaffDao.findAll(null,
							qaWorksheetStaffWhereClauses, true);
				} else if (qaWorksheet.getType() == Constants.QA_WORKSEET_TYPE_BYDOCTOR) {
					Map<String, String> qaWorksheetDoctorWhereClauses = new HashMap<String, String>();
					qaWorksheetDoctorWhereClauses.put(Constants.QAWORKSHEET_ID,
							qaWorksheet.getId().toString());

					// find staff by worksheet id
					qaWorksheetDoctors = this.qaWorksheetDoctorDao.findAll(
							null, qaWorksheetDoctorWhereClauses, true);
				}

				boolean allowDuplicateTicketNumber = true;
				if (qaWorksheet.getDepartment().getId() == Long
						.valueOf(Constants.CHARGE_DEPARTMENT_ID)) {

					// fetch records from charge productivity
					if (qaWorksheet.getSubDepartment() != null) {
						// build sub department criteria for ChargeProductivity
						String subDepartmentId = qaWorksheet.getSubDepartment()
								.getId().toString();
						if (subDepartmentId
								.equalsIgnoreCase(Constants.CHARGE_DEPARTMENT_DEMO)) {
							whereClause.put(Constants.CHARGE_DEPARTMENT_DEMO,
									subDepartmentId);
						} else if (subDepartmentId
								.equalsIgnoreCase(Constants.CHARGE_DEPARTMENT_CE)) {
							whereClause.put(Constants.CHARGE_DEPARTMENT_CE,
									subDepartmentId);
						} else if (subDepartmentId
								.equalsIgnoreCase(Constants.CHARGE_DEPARTMENT_CODING_PRIMARY)) {
							whereClause.put(
									Constants.CHARGE_DEPARTMENT_CODING_PRIMARY,
									subDepartmentId);
						} else if (subDepartmentId
								.equalsIgnoreCase(Constants.CHARGE_DEPARTMENT_CODING_SPECIAL)) {
							whereClause.put(
									Constants.CHARGE_DEPARTMENT_CODING_SPECIAL,
									subDepartmentId);
						}
					} else {
						allowDuplicateTicketNumber = false;
					}

					if (qaWorksheetStaffs != null
							&& qaWorksheetStaffs.size() != 0) {

						int calPerValue = 0;
						List<String> userNameList = new ArrayList<String>();
						Integer percentage = 0;
						int total = 0;
						for (QAWorksheetStaff qaWorksheetStaff : qaWorksheetStaffs) {

							whereClause.put(Constants.USER, qaWorksheetStaff
									.getUser().getId().toString());

							int totalRecord = this.chargeProductivityDao
									.totalRecord(whereClause);

							// // calculate all the percentage
							// calPerValue =
							// qaWorksheetStaff.getPercentageValue()
							// .intValue();
							// percentage = calPerValue * (totalRecord / 100);
							if (totalRecord > 0) {
								// calculate all the percentage
								/*calPerValue = qaWorksheetStaff
										.getPercentageValue().intValue();*/
								calPerValue = qaWorksheet.getAccountPercentage().intValue();
								percentage = (int) Math.ceil(calPerValue
										* ((float) totalRecord / 100));
							}

							total += percentage;
							// if percentage is 0 no need to pull up records
							if (percentage != null
									&& percentage > Constants.ZERO) {

								orderByClause.put(Constants.RANDOM_RECOREDS,
										Constants.RANDOM_RECOREDS);
								orderByClause.put(Constants.OFFSET, 0 + "");
								orderByClause.put(Constants.LIMIT,
										percentage.toString());

								List<ChargeProductivity> chargeProductivities = this.chargeProductivityDao
										.findAll(orderByClause, whereClause,
												false);
								fillQAProductivity(qaWorksheet,
										chargeProductivities,
										allowDuplicateTicketNumber);

								// QA worksheet staff full name
								userNameList.add(qaWorksheetStaff.getUser()
										.getFirstName()
										+ " "
										+ qaWorksheetStaff.getUser()
												.getLastName());
							}
							orderByClause.clear();
						}

						msg.append(this.messageSource.getMessage(
								"record.found.staff.message",
								new Object[] {
										total,
										"'"
												+ StringUtils.join(
														userNameList, ", ")
												+ "'",
										qaWorksheet.getDepartment().getName(),
										"'" + qaWorksheet.getName() + "'" },
								Locale.ENGLISH));

					} else if (qaWorksheetDoctors != null
							&& qaWorksheetDoctors.size() != 0) {
						int calPerValue = 0;
						List<String> doctorNameList = new ArrayList<String>();

						Integer percentage = 0;
						int total = 0;

						for (QAWorksheetDoctor qaWorksheetDoctor : qaWorksheetDoctors) {
							whereClause.put(Constants.DOCTOR, qaWorksheetDoctor
									.getDoctor().getId().toString());
							int totalRecord = this.chargeProductivityDao
									.totalRecord(whereClause);

							// // calculate all the percentage
							// calPerValue =
							// qaWorksheetStaff.getPercentageValue()
							// .intValue();
							// percentage = calPerValue * (totalRecord / 100);
							if (totalRecord > 0) {
								// calculate all the percentage
								calPerValue = qaWorksheetDoctor
										.getPercentageValue().intValue();
								percentage = (int) Math.ceil(calPerValue
										* ((float) totalRecord / 100));
							}

							total += percentage;
							// if percentage is 0 no need to pull up records
							if (percentage != null
									&& percentage > Constants.ZERO) {

								orderByClause.put(Constants.RANDOM_RECOREDS,
										Constants.RANDOM_RECOREDS);
								orderByClause.put(Constants.OFFSET, 0 + "");
								orderByClause.put(Constants.LIMIT,
										percentage.toString());

								List<ChargeProductivity> chargeProductivities = this.chargeProductivityDao
										.findAll(orderByClause, whereClause,
												false);
								fillQAProductivity(qaWorksheet,
										chargeProductivities,
										allowDuplicateTicketNumber);

								// QA worksheet staff full name
								doctorNameList.add(qaWorksheetDoctor
										.getDoctor().getName());
							}

							orderByClause.clear();
						}

						msg.append(this.messageSource.getMessage(
								"record.found.doctor.message",
								new Object[] {
										total,
										"'"
												+ StringUtils.join(
														doctorNameList, ", ")
												+ "'",
										qaWorksheet.getDepartment().getName(),
										"'" + qaWorksheet.getName() + "'" },
								Locale.ENGLISH));
					} else {
						int totalRecord = this.chargeProductivityDao
								.totalRecord(whereClause);

						Integer percentage = 0;

						if (totalRecord > 0) {
							// calculate all the percentage
							int generalPercentage = qaWorksheet
									.getGeneralPercentage().intValue();
							// Integer percentage = generalPercentage
							// * (totalRecord / 100);

							percentage = (int) Math.ceil(generalPercentage
									* ((float) totalRecord / 100));
						}

						// if percentage is 0 no need to pull up records
						if (percentage != null && percentage > Constants.ZERO) {

							orderByClause.put(Constants.RANDOM_RECOREDS,
									Constants.RANDOM_RECOREDS);
							orderByClause.put(Constants.OFFSET, 0 + "");
							orderByClause.put(Constants.LIMIT,
									percentage.toString());

							List<ChargeProductivity> chargeProductivities = this.chargeProductivityDao
									.findAll(orderByClause, whereClause, false);

							fillQAProductivityFromQAWorksheet(qaWorksheet,
									chargeProductivities,
									allowDuplicateTicketNumber);

							orderByClause.clear();
						}
						msg.append(this.messageSource.getMessage(
								"record.found.message", new Object[] {
										percentage,
										qaWorksheet.getDepartment().getName(),
										"'" + qaWorksheet.getName() + "'" },
								Locale.ENGLISH));
					}

				} 
				
				else if (qaWorksheet.getDepartment().getId() == Long
						.valueOf(Constants.PAYMENT_DEPARTMENT_ID)) {

					// fetch records from payment productivity

					if (qaWorksheetStaffs != null
							&& qaWorksheetStaffs.size() != 0) {
						int calPerValue = 0;
						List<String> userNameList = new ArrayList<String>();
						Integer percentage = 0;
						int total = 0;
						for (QAWorksheetStaff qaWorksheetStaff : qaWorksheetStaffs) {
							whereClause.put(Constants.USER, qaWorksheetStaff
									.getUser().getId().toString());

							int totalRecord = this.paymentProductivityDao
									.totalRecord(whereClause);

							if (totalRecord > 0) {
								// calculate all the percentage
								calPerValue = qaWorksheetStaff
										.getPercentageValue().intValue();
								percentage = (int) Math.ceil(calPerValue
										* ((float) totalRecord / 100));
							}

							total += percentage;

							// if percentage is 0 no need to pull up records
							if (percentage != null
									&& percentage > Constants.ZERO) {

								orderByClause.put(Constants.RANDOM_RECOREDS,
										Constants.RANDOM_RECOREDS);
								orderByClause.put(Constants.OFFSET, 0 + "");
								orderByClause.put(Constants.LIMIT,
										percentage.toString());

								List<PaymentProductivity> paymentProductivities = this.paymentProductivityDao
										.findAll(orderByClause, whereClause,
												false);

								fillQAProductivity(qaWorksheet,
										paymentProductivities, true);

								// QA worksheet staff full name
								userNameList.add(qaWorksheetStaff.getUser()
										.getFirstName()
										+ " "
										+ qaWorksheetStaff.getUser()
												.getLastName());
							}
							orderByClause.clear();
						}

						msg.append(this.messageSource.getMessage(
								"record.found.staff.message",
								new Object[] {
										total,
										"'"
												+ StringUtils.join(
														userNameList, ", ")
												+ "'",
										qaWorksheet.getDepartment().getName(),
										"'" + qaWorksheet.getName() + "'" },
								Locale.ENGLISH));
					} else if (qaWorksheetDoctors != null
							&& qaWorksheetDoctors.size() != 0) {

						int calPerValue = 0;
						List<String> doctorNameList = new ArrayList<String>();

						Integer percentage = 0;
						int total = 0;

						for (QAWorksheetDoctor qaWorksheetDoctor : qaWorksheetDoctors) {
							whereClause.put(Constants.DOCTOR, qaWorksheetDoctor
									.getDoctor().getId().toString());
							int totalRecord = this.paymentProductivityDao
									.totalRecord(whereClause);

							// // calculate all the percentage
							// calPerValue =
							// qaWorksheetStaff.getPercentageValue()
							// .intValue();
							// percentage = calPerValue * (totalRecord / 100);
							if (totalRecord > 0) {
								// calculate all the percentage
								calPerValue = qaWorksheetDoctor
										.getPercentageValue().intValue();
								percentage = (int) Math.ceil(calPerValue
										* ((float) totalRecord / 100));
							}

							total += percentage;
							// if percentage is 0 no need to pull up records
							if (percentage != null
									&& percentage > Constants.ZERO) {

								orderByClause.put(Constants.RANDOM_RECOREDS,
										Constants.RANDOM_RECOREDS);
								orderByClause.put(Constants.OFFSET, 0 + "");
								orderByClause.put(Constants.LIMIT,
										percentage.toString());

								List<PaymentProductivity> paymentProductivities = this.paymentProductivityDao
										.findAll(orderByClause, whereClause,
												false);

								fillQAProductivity(qaWorksheet,
										paymentProductivities, true);

								// QA worksheet staff full name
								doctorNameList.add(qaWorksheetDoctor
										.getDoctor().getName());
							}

							orderByClause.clear();
						}

						msg.append(this.messageSource.getMessage(
								"record.found.doctor.message",
								new Object[] {
										total,
										"'"
												+ StringUtils.join(
														doctorNameList, ", ")
												+ "'",
										qaWorksheet.getDepartment().getName(),
										"'" + qaWorksheet.getName() + "'" },
								Locale.ENGLISH));
					} else {

						int totalRecord = this.paymentProductivityDao
								.totalRecord(whereClause);

						// calculate all the percentage
						Integer percentage = 0;

						if (totalRecord > 0) {
							percentage = (int) Math.ceil(qaWorksheet
									.getGeneralPercentage().intValue()
									* ((float) totalRecord / 100));

							// percentage = * (totalRecord / 100);
						}

						// if percentage is 0 no need to pull up records
						if (percentage != null && percentage > Constants.ZERO) {

							orderByClause.put(Constants.RANDOM_RECOREDS,
									Constants.RANDOM_RECOREDS);
							orderByClause.put(Constants.OFFSET, 0 + "");
							orderByClause.put(Constants.LIMIT,
									percentage.toString());

							List<PaymentProductivity> paymentProductivities = this.paymentProductivityDao
									.findAll(orderByClause, whereClause, false);

							fillQAProductivityFromQAWorksheet(qaWorksheet,
									paymentProductivities, true);

							orderByClause.clear();
						}
						msg.append(this.messageSource.getMessage(
								"record.found.message", new Object[] {
										percentage,
										qaWorksheet.getDepartment().getName(),
										"'" + qaWorksheet.getName() + "'" },
								Locale.ENGLISH));
					}

				} else if (qaWorksheet.getDepartment().getId() == Long
						.valueOf(Constants.AR_DEPARTMENT_ID)) {

					// fetch records from ar productivity
					if (qaWorksheet.getSubDepartment() != null) {
						// build sub department criteria for ChargeProductivity
						whereClause.put(Constants.SUB_DEPARTMENT_ID,
								qaWorksheet.getSubDepartment().getId()
										.toString());
					}

					if (qaWorksheet.getArStatusCode() != null
							&& !qaWorksheet.getArStatusCode().trim().equals("")) {
						whereClause.put(Constants.STATUS_CODE,
								qaWorksheet.getArStatusCode());
					}

					if (qaWorksheetStaffs != null
							&& qaWorksheetStaffs.size() != 0) {
						int calPerValue = 0;
						List<String> userNameList = new ArrayList<String>();
						Integer percentage = 0;
						int total = 0;
						for (QAWorksheetStaff qaWorksheetStaff : qaWorksheetStaffs) {
							whereClause.put(Constants.USER, qaWorksheetStaff
									.getUser().getId().toString());

							int totalRecord = this.arProductivityDao
									.totalRecord(whereClause);

							if (totalRecord > 0) {
								// calculate all the percentage
								calPerValue = qaWorksheetStaff
										.getPercentageValue().intValue();
								percentage = (int) Math.ceil(calPerValue
										* ((float) totalRecord / 100));
							}

							total += percentage;
							// if percentage is 0 no need to pull up records
							if (percentage != null
									&& percentage > Constants.ZERO) {

								orderByClause.put(Constants.RANDOM_RECOREDS,
										Constants.RANDOM_RECOREDS);
								orderByClause.put(Constants.OFFSET, 0 + "");
								orderByClause.put(Constants.LIMIT,
										percentage.toString());

								List<ArProductivity> arProductivities = this.arProductivityDao
										.findAll(orderByClause, whereClause,
												false);

								fillQAProductivity(qaWorksheet,
										arProductivities, true);

								// QA worksheet staff full name
								userNameList.add(qaWorksheetStaff.getUser()
										.getFirstName()
										+ " "
										+ qaWorksheetStaff.getUser()
												.getLastName());
							}
							orderByClause.clear();
						}

						msg.append(this.messageSource.getMessage(
								"record.found.staff.message",
								new Object[] {
										total,
										"'"
												+ StringUtils.join(
														userNameList, ", ")
												+ "'",
										qaWorksheet.getDepartment().getName(),
										"'" + qaWorksheet.getName() + "'" },
								Locale.ENGLISH));
					} else if (qaWorksheetDoctors != null
							&& qaWorksheetDoctors.size() != 0) {

						int calPerValue = 0;
						List<String> doctorNameList = new ArrayList<String>();

						Integer percentage = 0;
						int total = 0;

						for (QAWorksheetDoctor qaWorksheetDoctor : qaWorksheetDoctors) {
							whereClause.put(Constants.DOCTOR, qaWorksheetDoctor
									.getDoctor().getId().toString());
							int totalRecord = this.arProductivityDao
									.totalRecord(whereClause);

							// // calculate all the percentage
							// calPerValue =
							// qaWorksheetStaff.getPercentageValue()
							// .intValue();
							// percentage = calPerValue * (totalRecord / 100);
							if (totalRecord > 0) {
								// calculate all the percentage
								calPerValue = qaWorksheetDoctor
										.getPercentageValue().intValue();
								percentage = (int) Math.ceil(calPerValue
										* ((float) totalRecord / 100));
							}

							total += percentage;
							// if percentage is 0 no need to pull up records
							if (percentage != null
									&& percentage > Constants.ZERO) {

								orderByClause.put(Constants.RANDOM_RECOREDS,
										Constants.RANDOM_RECOREDS);
								orderByClause.put(Constants.OFFSET, 0 + "");
								orderByClause.put(Constants.LIMIT,
										percentage.toString());

								List<ArProductivity> arProductivities = this.arProductivityDao
										.findAll(orderByClause, whereClause,
												false);

								fillQAProductivity(qaWorksheet,
										arProductivities, true);

								// QA worksheet staff full name
								doctorNameList.add(qaWorksheetDoctor
										.getDoctor().getName());
							}

							orderByClause.clear();
						}

						msg.append(this.messageSource.getMessage(
								"record.found.doctor.message",
								new Object[] {
										total,
										"'"
												+ StringUtils.join(
														doctorNameList, ", ")
												+ "'",
										qaWorksheet.getDepartment().getName(),
										"'" + qaWorksheet.getName() + "'" },
								Locale.ENGLISH));

					} else {

						int totalRecord = this.arProductivityDao
								.totalRecord(whereClause);

						Integer percentage = 0;
						// calculate all the percentage
						// Integer percentage =
						// qaWorksheet.getGeneralPercentage()
						// .intValue() * (totalRecord / 100);
						if (totalRecord > 0) {
							percentage = (int) Math.ceil(qaWorksheet
									.getGeneralPercentage().intValue()
									* ((float) totalRecord / 100));

							// percentage = * (totalRecord / 100);
						}

						// if percentage is 0 no need to pull up records
						if (percentage != null && percentage > Constants.ZERO) {

							orderByClause.put(Constants.RANDOM_RECOREDS,
									Constants.RANDOM_RECOREDS);
							orderByClause.put(Constants.OFFSET, 0 + "");
							orderByClause.put(Constants.LIMIT,
									percentage.toString());

							List<ArProductivity> arProductivities = this.arProductivityDao
									.findAll(orderByClause, whereClause, false);

							fillQAProductivityFromQAWorksheet(qaWorksheet,
									arProductivities, true);

							orderByClause.clear();
						}
						msg.append(this.messageSource.getMessage(
								"record.found.message", new Object[] {
										percentage,
										qaWorksheet.getDepartment().getName(),
										"'" + qaWorksheet.getName() + "'" },
								Locale.ENGLISH));
					}

				} else if (qaWorksheet.getDepartment().getId() == Long
						.valueOf(Constants.ACCOUNTING_DEPARTMENT_ID)) {
					// fetch records from accounting productivity

					if (qaWorksheetStaffs != null
							&& qaWorksheetStaffs.size() != 0) {
						int calPerValue = 0;
						List<String> userNameList = new ArrayList<String>();
						Integer percentage = 0;
						int total = 0;
						for (QAWorksheetStaff qaWorksheetStaff : qaWorksheetStaffs) {
							whereClause.put(Constants.USER, qaWorksheetStaff
									.getUser().getId().toString());

							int totalRecord = this.credentialingAccountingProductivityDao
									.totalRecord(whereClause);
							// // calculate all the percentage
							// calPerValue =
							// qaWorksheetStaff.getPercentageValue()
							// .intValue();
							// percentage = calPerValue * (totalRecord / 100);
							if (totalRecord > 0) {
								// calculate all the percentage
								calPerValue = qaWorksheetStaff
										.getPercentageValue().intValue();
								percentage = (int) Math.ceil(calPerValue
										* ((float) totalRecord / 100));
							}
							total += percentage;

							// if percentage is 0 no need to pull up records
							if (percentage != null
									&& percentage > Constants.ZERO) {

								orderByClause.put(Constants.RANDOM_RECOREDS,
										Constants.RANDOM_RECOREDS);
								orderByClause.put(Constants.OFFSET, 0 + "");
								orderByClause.put(Constants.LIMIT,
										percentage.toString());

								List<CredentialingAccountingProductivity> caProductivities = this.credentialingAccountingProductivityDao
										.findAllJPQL(orderByClause,
												whereClause, false);

								fillQAProductivity(qaWorksheet,
										caProductivities, true);

								// QA worksheet staff full name
								userNameList.add(qaWorksheetStaff.getUser()
										.getFirstName()
										+ " "
										+ qaWorksheetStaff.getUser()
												.getLastName());
							}
							orderByClause.clear();
						}

						msg.append(this.messageSource.getMessage(
								"record.found.staff.message",
								new Object[] {
										total,
										"'"
												+ StringUtils.join(
														userNameList, ", ")
												+ "'",
										qaWorksheet.getDepartment().getName(),
										"'" + qaWorksheet.getName() + "'" },
								Locale.ENGLISH));
					} else {
						int totalRecord = this.credentialingAccountingProductivityDao
								.totalRecord(whereClause);

						// calculate all the percentage
						Integer percentage = 0;
						// qaWorksheet.getGeneralPercentage()
						// .intValue() * (totalRecord / 100);

						if (totalRecord > 0) {
							percentage = (int) Math.ceil(qaWorksheet
									.getGeneralPercentage().intValue()
									* ((float) totalRecord / 100));
						}

						// if percentage is 0 no need to pull up records
						if (percentage != null && percentage > Constants.ZERO) {

							orderByClause.put(Constants.RANDOM_RECOREDS,
									Constants.RANDOM_RECOREDS);
							orderByClause.put(Constants.OFFSET, 0 + "");
							orderByClause.put(Constants.LIMIT,
									percentage.toString());

							List<CredentialingAccountingProductivity> caProductivities = this.credentialingAccountingProductivityDao
									.findAllJPQL(orderByClause, whereClause,
											false);

							fillQAProductivityFromQAWorksheet(qaWorksheet,
									caProductivities, true);

							orderByClause.clear();
						}
						msg.append(this.messageSource.getMessage(
								"record.found.message", new Object[] {
										percentage,
										qaWorksheet.getDepartment().getName(),
										"'" + qaWorksheet.getName() + "'" },
								Locale.ENGLISH));
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
			// msg.append("Record already exist");
		}

		return msg;
	}

	/**
	 * Helper method to save {@link QAProductivitySampling} for
	 * {@link QAWorksheet}
	 *
	 * @param qaWorksheet
	 * @param productivities
	 * @throws ArgusException
	 */
	private void fillQAProductivityFromQAWorksheet(QAWorksheet qaWorksheet,
			List<?> productivities, boolean allowDuplicateTicketNumbers)
			throws ArgusException {

		boolean updateFlag = false;
		boolean saveFlag = false;

		List<Long> ticketNumberList = new ArrayList<Long>();
		// shuffle the list
		if (productivities != null && productivities.size() > 0) {
			// no need to shuffle records we are already pulling random records
			// from db
			/*
			 * long seed = System.nanoTime();
			 * Collections.shuffle(productivities, new Random(seed));
			 */

			User createdBy = AkpmsUtil.getLoggedInUser();

			for (Object object : productivities) {
				saveFlag = true;
				QAProductivitySampling qaSampling = new QAProductivitySampling();

				qaSampling.setQaWorksheet(qaWorksheet);

				if (object instanceof PaymentProductivity) {
					qaSampling
							.setPaymentProductivity((PaymentProductivity) object);

				} else if (object instanceof ChargeProductivity) {
					// qaSampling
					// .setChargeProductivity((ChargeProductivity) object);

					ChargeProductivity chargeProductivity = (ChargeProductivity) object;

					if (allowDuplicateTicketNumbers) {
						qaSampling
								.setChargeProductivity((ChargeProductivity) object);
					} else {

						// check if already in list, then ignore
						if (!ticketNumberList.contains(chargeProductivity
								.getTicketNumber().getId())) {
							qaSampling
									.setChargeProductivity((ChargeProductivity) object);

							ticketNumberList.add(chargeProductivity
									.getTicketNumber().getId());
						} else {
							saveFlag = false;
						}
					}

				} else if (object instanceof ArProductivity) {
					qaSampling.setArProductivity((ArProductivity) object);

				} else if (object instanceof CredentialingAccountingProductivity) {
					qaSampling
							.setCredentialingAccountingProductivity((CredentialingAccountingProductivity) object);
				}

				if (saveFlag) {
					qaSampling.setCreatedOn(new Date());
					qaSampling.setCreatedBy(createdBy);
					// qaSampling.setDepartmentId(qaWorksheet.getDepartment());
					qaProductivitySamplingDao.save(qaSampling);
				}
			}

			if (updateFlag == false) {
				updateFlag = true;
			}
		}
		// Update status in qaworksheet as executed
		if (updateFlag == true) {
			qaWorksheet.setStatus(Constants.ONE);
			this.qaWorksheetDao.update(qaWorksheet);
		}
	}

	/**
	 * Helper method to save {@link QAProductivitySampling} for
	 * {@link QAWorksheetStaff}
	 *
	 * @param qaWorksheet
	 * @param qaWorksheetStaff
	 * @param prodIDs
	 * @throws ArgusException
	 */
	// private void fillQAProductivityFromQAWorksheetStaff(
	// QAWorksheet qaWorksheet, QAWorksheetStaff qaWorksheetStaff,
	// List<?> productivities) throws ArgusException {
	//
	// boolean updateFlag = false;
	// // shuffle the list
	// if (productivities != null && productivities.size() > 0) {
	// // no need to shuffle we are pulling up random records from db
	// /*
	// * long seed = System.nanoTime();
	// * Collections.shuffle(productivities, new Random(seed));
	// */
	//
	// User createdBy = AkpmsUtil.getLoggedInUser();
	// for (Object object : productivities) {
	// QAProductivitySampling qaSampling = new QAProductivitySampling();
	//
	// qaSampling.setQaWorksheet(qaWorksheet);
	//
	// if (object instanceof PaymentProductivity) {
	// qaSampling
	// .setPaymentProductivity((PaymentProductivity) object);
	//
	// } else if (object instanceof ChargeProductivity) {
	// qaSampling
	// .setChargeProductivity((ChargeProductivity) object);
	//
	// } else if (object instanceof ArProductivity) {
	// qaSampling.setArProductivity((ArProductivity) object);
	//
	// } else if (object instanceof CredentialingAccountingProductivity) {
	// qaSampling
	// .setCredentialingAccountingProductivity((CredentialingAccountingProductivity)
	// object);
	// }
	//
	// qaSampling.setCreatedOn(new Date());
	// qaSampling.setCreatedBy(createdBy);
	// // qaSampling.setDepartmentId(qaWorksheet.getDepartment());
	// qaProductivitySamplingDao.save(qaSampling);
	// }
	// if (updateFlag == false) {
	// updateFlag = true;
	// }
	// }
	//
	// // Update status in qaworksheet as executed
	// if (updateFlag == true) {
	// qaWorksheet.setStatus(Constants.ONE);
	// try {
	// this.qaWorksheetDao.update(qaWorksheet);
	// } catch (ArgusException e) {
	// LOGGER.error("Error merging QAWorksheet", e);
	// }
	// }
	// }

	private void fillQAProductivity(QAWorksheet qaWorksheet,
			List<?> productivities, boolean allowDuplicateTicketNumbers)
			throws ArgusException {

		boolean updateFlag = false;
		boolean saveFlag = false;

		List<Long> ticketNumberList = new ArrayList<Long>();

		// shuffle the list
		if (productivities != null && productivities.size() > 0) {
			// no need to shuffle we are pulling up random records from db
			/*
			 * long seed = System.nanoTime();
			 * Collections.shuffle(productivities, new Random(seed));
			 */

			User createdBy = AkpmsUtil.getLoggedInUser();

			for (Object object : productivities) {
				saveFlag = true;
				QAProductivitySampling qaSampling = new QAProductivitySampling();

				qaSampling.setQaWorksheet(qaWorksheet);

				if (object instanceof PaymentProductivity) {
					qaSampling
							.setPaymentProductivity((PaymentProductivity) object);

				} else if (object instanceof ChargeProductivity) {

					ChargeProductivity chargeProductivity = (ChargeProductivity) object;

					if (allowDuplicateTicketNumbers) {
						qaSampling
								.setChargeProductivity((ChargeProductivity) object);
					} else {

						// check if already in list, then ignore
						if (!ticketNumberList.contains(chargeProductivity
								.getTicketNumber().getId())) {
							qaSampling
									.setChargeProductivity((ChargeProductivity) object);

							ticketNumberList.add(chargeProductivity
									.getTicketNumber().getId());
						} else {
							saveFlag = false;
						}
					}

				} else if (object instanceof ArProductivity) {
					qaSampling.setArProductivity((ArProductivity) object);

				} else if (object instanceof CredentialingAccountingProductivity) {
					qaSampling
							.setCredentialingAccountingProductivity((CredentialingAccountingProductivity) object);
				}

				if (saveFlag) {
					qaSampling.setCreatedOn(new Date());
					qaSampling.setCreatedBy(createdBy);
					// qaSampling.setDepartmentId(qaWorksheet.getDepartment());
					qaProductivitySamplingDao.save(qaSampling);
				}
			}
			if (updateFlag == false) {
				updateFlag = true;
			}
		}

		// Update status in qaworksheet as executed
		if (updateFlag == true) {
			qaWorksheet.setStatus(Constants.ONE);
			try {
				this.qaWorksheetDao.update(qaWorksheet);
			} catch (ArgusException e) {
				LOGGER.error("Error merging QAWorksheet", e);
			}
		}
	}

	/**
	 * this method set value in department json data list
	 *
	 * @param rows
	 * @return
	 */
	private List<DepartmentJsonData> getDepartmentJsonData(List<Department> rows) {
		List<DepartmentJsonData> deptJsonData = new ArrayList<DepartmentJsonData>();

		if (rows != null && rows.size() > 0) {

			for (Department department : rows) {
				DepartmentJsonData djd = new DepartmentJsonData();
				djd.setId(department.getId());
				djd.setName(department.getName());
				deptJsonData.add(djd);
			}
		}
		return deptJsonData;
	}

	/***
	 * Handle flexigrid request
	 *
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	JsonDataWrapper<QAWorksheetJsonData> listQAWorksheet(WebRequest request) {

		int page = 1; // default 1 for page number in json wrapper
		int rp = 0;

		Map<String, String> orderClauses = new HashMap<String, String>();
		Map<String, String> whereClauses = new HashMap<String, String>();

		if (request != null) {

			if (request.getParameter("rp") != null) {
				rp = Integer.parseInt(request.getParameter("rp"));
				try {
					orderClauses.put("limit", "" + rp);
				} catch (Exception e) {
					LOGGER.debug("rp[Record pre Page] not an integer");
				}
			}

			if (request.getParameter("page") != null) {
				try {
					page = Integer.parseInt(request.getParameter("page"));
					orderClauses.put("offset", "" + ((rp * page) - rp));
				} catch (Exception e) {
					LOGGER.debug("Exception during parsing");
				}
			}

			if (request.getParameter("sortorder") != null) {
				orderClauses.put("sortBy", request.getParameter("sortorder"));
			}

			if (request.getParameter("sortname") != null) {
				orderClauses.put("orderBy", request.getParameter("sortname"));
			}

			if (request.getParameter("qtype") != null
					&& request.getParameter("query") != null
					&& !request.getParameter("query").isEmpty()) {
				whereClauses.put(request.getParameter("qtype"),
						request.getParameter("query"));
			}

			if (request.getParameter(Constants.KEYWORD) != null
					&& request.getParameter(Constants.KEYWORD).trim().length() > 0) {
				String keyword = request.getParameter(Constants.KEYWORD);
				Pattern percentPattern = Pattern.compile("^[%]*");
				Pattern underScorePattern = Pattern.compile("^[_]*");
				Matcher pecentMatcher = percentPattern.matcher(keyword);
				Matcher underScoreMatcher = underScorePattern.matcher(keyword);
				if (pecentMatcher.matches()) {
					keyword = Constants.QUERY_WILDCARD_PERCENTAGE;
				}
				if (underScoreMatcher.matches()) {
					keyword = Constants.QUERY_WILDCARD_UNDERSCORE;
				}
				whereClauses.put(Constants.KEYWORD, keyword);
			}

			if (request.getParameter(Constants.STATUS) != null
					&& request.getParameter(Constants.STATUS).trim().length() > 0) {
				whereClauses.put(Constants.STATUS,
						request.getParameter(Constants.STATUS));
			}

			// set created by id
			if (request.getParameterValues(Constants.CREATED_BY) != null
					&& request.getParameterValues(Constants.CREATED_BY).length > Constants.ZERO) {
				LOGGER.info("created by = "
						+ request.getParameterValues(Constants.CREATED_BY));
				whereClauses.put(Constants.CREATED_BY, StringUtils.join(
						request.getParameterValues(Constants.CREATED_BY), ","));
			}

			if (request.getParameter(Constants.DATE_CREATED_FROM) != null
					&& request.getParameter(Constants.DATE_CREATED_FROM).trim()
							.length() > Constants.ZERO) {
				LOGGER.info("DATE_CREATED_FROM= "
						+ request.getParameter(Constants.DATE_CREATED_FROM));
				whereClauses.put(Constants.DATE_CREATED_FROM,
						request.getParameter(Constants.DATE_CREATED_FROM));
			}

			if (request.getParameter(Constants.DATE_CREATED_TO) != null
					&& request.getParameter(Constants.DATE_CREATED_TO).trim()
							.length() > Constants.ZERO) {
				LOGGER.info("DATE_CREATED_TO= "
						+ request.getParameter(Constants.DATE_CREATED_TO));
				whereClauses.put(Constants.DATE_CREATED_TO,
						request.getParameter(Constants.DATE_CREATED_TO));
			}

			if (request.getParameter(Constants.DEPARTMENT_ID) != null
					&& request.getParameter(Constants.DEPARTMENT_ID).trim()
							.length() > 0) {
				whereClauses.put(Constants.DEPARTMENT_ID,
						request.getParameter(Constants.DEPARTMENT_ID));
			}

			if (!request.getParameter(Constants.SUB_DEPARTMENT_ID)
					.equalsIgnoreCase("-1")) {
				whereClauses.put(Constants.SUB_DEPARTMENT_ID,
						request.getParameter(Constants.SUB_DEPARTMENT_ID));
			}

		} else {
			LOGGER.info("Null request");
		}
		int totalRows = 0;

		this.printReportCriteria.clear();

		this.printReportCriteria.putAll(whereClauses);

		try {
			totalRows = this.qaWorksheetDao.totalRecord(whereClauses);
			if (totalRows > 0) {
				List<QAWorksheet> rows = qaWorksheetDao.findAll(orderClauses,
						whereClauses, true);

				List<QAWorksheetJsonData> djd = getJsonData(rows);
				JsonDataWrapper<QAWorksheetJsonData> jdw = new JsonDataWrapper<QAWorksheetJsonData>(
						page, totalRows, djd);

				return jdw;
			}
		} catch (ArgusException e) {
			LOGGER.error("Failed to get total records", e);
		}

		return null;
	}

	/***
	 * Helper for flexigrid to help populate json
	 *
	 * @param qaWorksheetList
	 *            (@link QAWorksheet )
	 * @see QAWorksheet.java
	 * @return list of QAWorksheetJsonData (@link QAWorksheetJsonData)
	 */
	private List<QAWorksheetJsonData> getJsonData(
			List<QAWorksheet> qaWorksheetList) {
		List<QAWorksheetJsonData> jsonList = new ArrayList<QAWorksheetJsonData>();
		if (qaWorksheetList != null && qaWorksheetList.size() > 0) {
			for (QAWorksheet qaworksheet : qaWorksheetList) {
				QAWorksheetJsonData json = new QAWorksheetJsonData();
				json.setId(qaworksheet.getId());
				json.setName(qaworksheet.getName());
				json.setType(qaworksheet.getType().toString());
				json.setDepartmentName(qaworksheet.getDepartment().getName());
				if (qaworksheet.getSubDepartment() != null) {
					json.setSubDepartment(qaworksheet.getSubDepartment()
							.getName());
				}
				json.setDepartmentId(qaworksheet.getDepartment().getId());
				json.setMonth(AkpmsUtil.getMonths().get(
						qaworksheet.getBillingMonth()));
				json.setYear(qaworksheet.getBillingYear());
				// no need to check both dates as date from is there the date to
				// is also
				if (qaworksheet.getPostingDateFrom() != null) {
					json.setFromDate(AkpmsUtil.akpmsDateFormat(
							qaworksheet.getPostingDateFrom(),
							Constants.DATE_FORMAT));
					json.setToDate(AkpmsUtil.akpmsDateFormat(
							qaworksheet.getPostingDateTo(),
							Constants.DATE_FORMAT));
				}
				json.setStatus(qaworksheet.getStatus().toString());
				json.setCreatedBy(qaworksheet.getCreatedBy().getFirstName()
						+ " " + qaworksheet.getCreatedBy().getLastName());
				json.setCreatedOn(new SimpleDateFormat(Constants.DATE_FORMAT)
						.format(qaworksheet.getCreatedOn()));
				jsonList.add(json);
			}
		}
		return jsonList;
	}

	/**
	 * function to set department as deleted (is_deleted =1)
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public boolean delete(WebRequest request) {
		LOGGER.info("in delete method");
		LOGGER.info(request.getParameter("items"));

		boolean response = false;

		try {

			int updateCount = qaWorksheetDao.deleteQAWorksheet(new Long(request
					.getParameter("item")));
			if (updateCount > 0) {
				response = true;
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
		return response;
	}

	/**
	 * function to show add page to add qa worksheet (GET)
	 *
	 * @param qaworksheet
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/continue", method = { RequestMethod.GET })
	public String continueQAWorksheet(Map<String, Object> model,
			WebRequest request, HttpSession session,
			@RequestParam(value = "id") Long id) {

		try {
			QAWorksheet qaworksheet = this.qaWorksheetDao.findById(id);

			if (qaworksheet != null) {

				String orderBy = "";
				if (request.getParameter("orderby") != null) {
					orderBy = request.getParameter("orderby");
				}

				if (qaworksheet.getDepartment().getId().toString()
						.equalsIgnoreCase(Constants.PAYMENT_DEPARTMENT_ID)) {
					return "redirect:/qaworksheetlayout/payment/?qaworksheetid="
							+ id + "&orderby=" + orderBy;
				} else if (qaworksheet.getDepartment().getId().toString()
						.equalsIgnoreCase(Constants.CHARGE_DEPARTMENT_ID)) {
					return "redirect:/qaworksheetlayout/coding/?qaworksheetid="
							+ id + "&orderby=" + orderBy;
				} else if (qaworksheet.getDepartment().getId().toString()
						.equalsIgnoreCase(Constants.AR_DEPARTMENT_ID)) {
					return "redirect:/qaworksheetlayout/ar/?qaworksheetid="
							+ id + "&orderby=" + orderBy;
				}
			}

		} catch (Exception e) {
			LOGGER.error(e);
			session.setAttribute(Constants.ERROR, e.getMessage());
		}

		return "qamanager/list";
	}

	/**
	 * function to change department *
	 *
	 *
	 * @param id
	 * @param *
	 *
	 *
	 * @return
	 */
	@RequestMapping(value = "/change_status", method = RequestMethod.GET)
	@ResponseBody
	public boolean changeStatus(@RequestParam int id, @RequestParam int status) {
		LOGGER.info("in changeStatus method");
		LOGGER.info("id = " + id);
		LOGGER.info("status = " + status);

		boolean response = false;

		try {
			int updateCount = qaWorksheetDao.changeStatus(id, status);

			if (updateCount > Constants.ZERO) {
				response = true;
			}

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}

		return response;
	}

	/**
	 * Show QAWorksheetStaff details in remark * .
	 *
	 * @param @link{QAWork * id
	 *
	 * @return
	 */
	@RequestMapping(value = "/qastaff_json", method = RequestMethod.GET)
	@ResponseBody
	public List<QAWorksheetStaffJsonData> showStaffRemarksPopUp(
			@RequestParam(value = "id") Long id,
			@RequestParam(value = "departmentId") Long departmentId) {

		LOGGER.info("id = " + id);
		Map<String, String> whereClause = new HashMap<String, String>();

		whereClause.put(Constants.QAWORKSHEET_ID, id.toString());
		whereClause.put(Constants.DEPARTMENT, departmentId.toString());

		List<QAWorksheetStaffJsonData> staffJsonData = new ArrayList<QAWorksheetStaffJsonData>();

		try {
			staffJsonData
					.addAll(getQAWorksheetStaffsJson(this.qaProductivitySamplingDao
							.findExecutedUsersRecords(whereClause,
									Constants.QA_WORKSEET_TYPE_BYSTAFF)));
			Collections.unmodifiableCollection(staffJsonData);

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}

		return staffJsonData;
	}

	/**
	 * Show QAWorksheetStaff details in remark * .
	 * 
	 * @param @link{QAWork * id
	 * 
	 * @return
	 */
	@RequestMapping(value = "/qadoctor_json", method = RequestMethod.GET)
	@ResponseBody
	public List<QAWorksheetDoctorJsonData> showDoctorRemarksPopUp(
			@RequestParam(value = "id") Long id,
			@RequestParam(value = "departmentId") Long departmentId) {

		LOGGER.info("id = " + id);
		Map<String, String> whereClause = new HashMap<String, String>();

		whereClause.put(Constants.QAWORKSHEET_ID, id.toString());
		whereClause.put(Constants.DEPARTMENT, departmentId.toString());

		List<QAWorksheetDoctorJsonData> doctorJsonData = new ArrayList<QAWorksheetDoctorJsonData>();

		try {
			doctorJsonData
					.addAll(getQAWorksheetDoctorsJson(this.qaProductivitySamplingDao
							.findExecutedUsersRecords(whereClause,
									Constants.QA_WORKSEET_TYPE_BYDOCTOR)));
			Collections.unmodifiableCollection(doctorJsonData);

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}

		return doctorJsonData;
	}

	/**
	 * Helper for * rid
	 *
	 * @return List<QAWorksheetJsonData>
	 */
	private List<QAWorksheetStaffJsonData> getQAWorksheetStaffsJson(
			List<Object[]> records) {

		List<QAWorksheetStaffJsonData> staffJsonData = new ArrayList<QAWorksheetStaffJsonData>();
		for (Object[] record : records) {
			QAWorksheetStaffJsonData jsonData = new QAWorksheetStaffJsonData();

			jsonData.setUserName(record[0] instanceof String ? (String) record[0]
					: "");

			jsonData.setRemarks(record[1] instanceof Long
					&& ((Long) record[1]) != null ? ((Long) record[1])
					.toString() : "");

			jsonData.setArStatusCode(record[2] != null
					&& record[2] instanceof String ? ((String) record[2]) : "");

			staffJsonData.add(jsonData);
		}

		return staffJsonData;
	}

	/**
	 * Helper for * rid
	 * 
	 * @return List<QAWorksheetJsonData>
	 */
	private List<QAWorksheetDoctorJsonData> getQAWorksheetDoctorsJson(
			List<Object[]> records) {

		List<QAWorksheetDoctorJsonData> staffJsonData = new ArrayList<QAWorksheetDoctorJsonData>();
		for (Object[] record : records) {
			QAWorksheetDoctorJsonData jsonData = new QAWorksheetDoctorJsonData();

			jsonData.setDoctorName(record[0] instanceof String ? (String) record[0]
					: "");

			jsonData.setRemarks(record[1] instanceof Long
					&& ((Long) record[1]) != null ? ((Long) record[1])
					.toString() : "");

			// jsonData.setArStatusCode(record[2] != null
			// && record[2] instanceof String ? ((String) record[2]) : "");

			staffJsonData.add(jsonData);
		}

		return staffJsonData;
	}

	@RequestMapping(value = "/printlist", method = RequestMethod.GET)
	public String printList(Map<String, Object> map) {
		try {
			map.put("qaWorksheets", qaWorksheetDao.findAll(null,
					this.printReportCriteria, true));
		} catch (ArgusException e) {
			LOGGER.error(
					"Error while getting list for printing qaworksheet list ",
					e);
		}
		map.put("PRINT_REPORT_CRITERIA", this.printReportCriteria);
		return "printQAWorksheetList";
	}

	@RequestMapping(value = "/sample", method = RequestMethod.GET)
	public String showSearchQASampleRecords(Map<String, Object> map) {
		try {
			map.put("parentDepartments",
					this.departmentDao.findAllParentOrderedByName());
		} catch (ArgusException e) {
			LOGGER.error(e);
		}

		return "qamanager/sample/search";
	}

	private void fetchQCPoints(String departmentId, String subDepartmentId,
			Model model) throws ArgusException {

		Map<String, String> whereClauses = new HashMap<String, String>();
		Map<String, String> orderClauses = new HashMap<String, String>();

		whereClauses.put(Constants.DEPARTMENT_ID, departmentId);

		List<QcPoint> parentQCPoints = new ArrayList<QcPoint>();
		if (subDepartmentId != null) {
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

	@RequestMapping(value = "/searchsample", method = RequestMethod.GET)
	public String searchQASampleRecords(Map<String, Object> map,
			WebRequest request, Model model) {

		Map<String, String> whereClause = new HashMap<String, String>();
		whereClause.put("search_sample", "search_sample");

		Map<String, String[]> paramsMap = request.getParameterMap();

		try {
			if (paramsMap.containsKey(Constants.SUB_DEPARTMENT_ID)
					&& !paramsMap.get(Constants.SUB_DEPARTMENT_ID)[0]
							.equalsIgnoreCase("-1")) {
				fetchQCPoints(paramsMap.get(Constants.DEPARTMENT_ID)[0],
						paramsMap.get(Constants.SUB_DEPARTMENT_ID)[0], model);
			} else {
				fetchQCPoints(paramsMap.get(Constants.DEPARTMENT_ID)[0], null,
						model);
			}
		} catch (Exception e) {
			LOGGER.error("UNABLE TO FETCH QC POINTS" + e.getMessage());
		}

		if (paramsMap.containsKey(Constants.DEPARTMENT_ID)) {
			whereClause.put(Constants.DEPARTMENT_ID,
					paramsMap.get(Constants.DEPARTMENT_ID)[0]);
		}

		// if (paramsMap.containsKey(Constants.SUB_DEPARTMENT_ID)
		// && !paramsMap.get(Constants.SUB_DEPARTMENT_ID)[0]
		// .equalsIgnoreCase("-1")) {
		// whereClause.put(Constants.SUB_DEPARTMENT,
		// paramsMap.get(Constants.SUB_DEPARTMENT_ID)[0]);
		// }

		if (paramsMap.containsKey(Constants.KEYWORD)) {
			whereClause.put(Constants.KEYWORD,
					paramsMap.get(Constants.KEYWORD)[0]);
		}

		whereClause.put(Constants.STATUS, Constants.TWO + ""); // only completed
		map.put("mode", "search");

		String orderBy = null;

		if (request.getParameter("orderby") != null) {
			orderBy = request.getParameter("orderby");
		}

		model.addAttribute("ORDER_BY", orderBy);

		List<QAProductivitySampling> qaProductivitySamplings = new ArrayList<QAProductivitySampling>();
		try {
			qaProductivitySamplings = this.qaProductivitySamplingDao.findAll(
					whereClause, null, true);
		} catch (ArgusException e) {
			LOGGER.error("Exception in searchQASampleRecords ", e);
		}

		map.put("QA_PROD_SAMPLE_DATA_LIST", qaProductivitySamplings);

		String view = "";

		if (request.getParameter(Constants.DEPARTMENT_ID).equalsIgnoreCase(
				Constants.PAYMENT_DEPARTMENT_ID)) {
			view = "paymentLayout";
		} else if (request.getParameter(Constants.DEPARTMENT_ID)
				.equalsIgnoreCase(Constants.CHARGE_DEPARTMENT_ID)) {
			view = "codingLayout";
		} else if (request.getParameter(Constants.DEPARTMENT_ID)
				.equalsIgnoreCase(Constants.AR_DEPARTMENT_ID)) {
			view = "arLayout";
		}

		return view;
	}

}
