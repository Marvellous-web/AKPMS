package argus.mvc.chargeProductivity;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;

import argus.domain.ChargeBatchProcessing;
import argus.domain.ChargeProdReject;
import argus.domain.Doctor;
import argus.domain.Location;
import argus.domain.User;
import argus.exception.ArgusException;
import argus.repo.chargeBatchProcessing.ChargeBatchProcessingDao;
import argus.repo.chargeProductivity.ChargeProdRejectDao;
import argus.repo.chargeProductivity.ChargeProductivityDao;
import argus.repo.doctor.DoctorDao;
import argus.repo.location.LocationDao;
import argus.repo.user.UserDao;
import argus.util.AkpmsUtil;
import argus.util.ChargeBatchRejectJsonData;
import argus.util.Constants;
import argus.util.JsonDataWrapper;
import argus.validator.ChargeProdRejectReUpdateValidator;
import argus.validator.ChargeProdRejectValidator;

@Controller
@RequestMapping(value = "/chargeproductivityreject")
@SessionAttributes(value = Constants.CHARGE_PROD_REJECT)
@Scope("session")
public class ChargeProdRejectController {

	private static final Logger LOGGER = Logger
			.getLogger(ChargeProdRejectController.class);

	private static final String CHARGE_PROD_REJECT_VIEW_PATH = "chargeProductivity/chargeProdRejection";

	@Autowired
	private ChargeBatchProcessingDao chargeBatchProcessingDao;

	@Autowired
	private ChargeProductivityDao chargeProductivityDao;

	@Autowired
	private ChargeProdRejectDao chargeProdRejectDao;

	@Autowired
	private ChargeProdRejectValidator chargeProdRejectValidator;

	@Autowired
	private ChargeProdRejectReUpdateValidator chargeProdRejectReUpdateValidator;

	@Resource(name = "chargeBatchInsuranceTypeProperties")
	private Properties chargeBatchInsuranceType;

	@Resource(name = "chargeBatchTypeProperties")
	private Properties chargeBatchtype;

	// private List<String> reasonsToReject = new ArrayList<String>();

	private int page = 1;

	private Map<String, String> printReportCriteria;

	@Autowired
	private DoctorDao doctorDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private LocationDao locationDao;

	// @PostConstruct
	// public void init() {
	// reasonsToReject.add("Coding");
	// reasonsToReject.add("Charge entry");
	// reasonsToReject.add("Demo");
	// }

	@InitBinder
	protected void initBinder(WebDataBinder binder) throws ArgusException {
		LOGGER.info("in [initBinder] method : ");
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			public void setAsText(String value) {
				try {
					if (value != null && value.trim().length() > 0) {
						LOGGER.info("comng date value is : " + value);
						setValue(new SimpleDateFormat(Constants.DATE_FORMAT)
								.parse(value));
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

	}

	private String authenticationCheck() {
		if (!AkpmsUtil.checkDepartment(Long
				.valueOf(Constants.CHARGE_DEPARTMENT_ID))
				|| (AkpmsUtil.getLoggedInUser().getRole().getId() != Constants.STANDART_USER_ROLE_ID)) {
			return Constants.ERROR;
		} else {
			return "";
		}
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String getRejectedBatch(Model model, WebRequest request,
			Map<String, Object> map) {

		try {
			if (authenticationCheck().equals(Constants.ERROR)) {
				return "redirect:error403";
			}
			if (null != request
					&& null != request.getParameter(Constants.BATCH_ID)) {
				try {
					ChargeProdReject chargeProdReject = new ChargeProdReject();
					long id = Long.parseLong(request
							.getParameter(Constants.BATCH_ID));
					ChargeBatchProcessing chargeBatchProcessing = chargeBatchProcessingDao
							.getChargeBatchProcessingById(id, false);
					if (chargeBatchProcessing != null) {
						LOGGER.info("found chargeBatchProcess object");
						chargeProdReject
								.setChargeBatchProcessing(chargeBatchProcessing);
						map.put(Constants.HIDE, Constants.HIDE);
					} else {
						LOGGER.info("no charge batch process object found corresponding batch id = "
								+ id);
					}
					model.addAttribute(Constants.CHARGE_PROD_REJECT,
							chargeProdReject);
					model.addAttribute("insuranceTypes",
							chargeBatchInsuranceType);
					// model.addAttribute("reasonsToReject", reasonsToReject);
					map.put(Constants.OPERATION_TYPE, Constants.ADD);

				} catch (Exception e) {
					map.put("warning", "Batch ID does not exist");
					LOGGER.error(Constants.EXCEPTION, e);
				}
			} else {
				LOGGER.info("Batch id is coming null");
				if (null != request
						&& null != request.getParameter(Constants.REJECTION_ID)) {
					try {
						long id = Long.parseLong(request
								.getParameter(Constants.REJECTION_ID));
						ChargeProdReject chargeProdReject = chargeProdRejectDao
								.getChargeProdRejectById(id, true);

						ChargeBatchProcessing chargeBatchProcessing = chargeBatchProcessingDao
								.getChargeBatchProcessingById(chargeProdReject
										.getChargeBatchProcessing().getId(),
										false);
						if (chargeBatchProcessing != null) {
							LOGGER.info("found chargeBatchProcess object");
							chargeProdReject
									.setChargeBatchProcessing(chargeBatchProcessing);
						} else {
							LOGGER.info("no charge batch process object found corresponding batch id = "
									+ id);
						}

						model.addAttribute(Constants.CHARGE_PROD_REJECT,
								chargeProdReject);
						map.put(Constants.OPERATION_TYPE, Constants.EDIT);
						map.put(Constants.HIDE, Constants.HIDE);
					} catch (Exception e) {
						LOGGER.error(Constants.EXCEPTION, e);
					}
				} else {
					model.addAttribute(Constants.CHARGE_PROD_REJECT,
							new ChargeProdReject());
					map.put(Constants.OPERATION_TYPE, Constants.ADD);
				}

				model.addAttribute("insuranceTypes", chargeBatchInsuranceType);
				// model.addAttribute("reasonsToReject", reasonsToReject);
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}

		try {
			Map<String, String> whereClause = new HashMap<String, String>();
			whereClause.put(Constants.STATUS, Constants.ACTIVATE + "");

			List<Location> locationList = locationDao
					.findAll(null, whereClause);
			model.addAttribute(Constants.LOCATION_LIST, locationList);
		} catch (Exception e) {
			LOGGER.error("unable to load locations");
			LOGGER.error(e);
		}

		if (null != request.getParameter(Constants.POPUP)) {
			map.put(Constants.POPUP, true);
			return CHARGE_PROD_REJECT_VIEW_PATH + Constants.POPUP;
		}

		map.put(Constants.POPUP, false);

		return CHARGE_PROD_REJECT_VIEW_PATH;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String postRejectedBatch(
			@ModelAttribute(value = Constants.CHARGE_PROD_REJECT) ChargeProdReject chargeProdReject,
			Model model, WebRequest request, BindingResult result,
			Map<String, Object> map, HttpSession session) {
		LOGGER.info("insurance = " + chargeProdReject.getInsuranceType());
		try {
			chargeProdRejectValidator.validate(chargeProdReject, result);

			if (!result.hasErrors()) {
				try {
					if (chargeProdReject.getId() != null) {
						LOGGER.info("going to update");

						// need to check the reason behind this and rectify:
						// bhuppi jun04-14
						if (chargeProdReject.getLocation().getId() == 0) {
							chargeProdReject.setLocation(null);
						}
						chargeProdRejectDao.update(chargeProdReject);
						session.setAttribute(Constants.SUCCESS_UPDATE,
								messageSource.getMessage(
										"reject.updatedSuccessfully", null,
										Locale.ENGLISH));
					} else {
						LOGGER.info("going to save");
						// need to check the reason behind this and rectify:
						// bhuppi jun04-14
						if (chargeProdReject.getLocation().getId() == 0) {
							chargeProdReject.setLocation(null);
						}

						chargeProdRejectDao.save(chargeProdReject);

						session.setAttribute(Constants.SUCCESS_UPDATE,
								messageSource.getMessage(
										"reject.addedSuccessfully", null,
										Locale.ENGLISH));
					}
					if (chargeProdReject.isAddMore()) {
						return "redirect:/chargeproductivityreject/add?batchId="
								+ chargeProdReject.getChargeBatchProcessing()
										.getId();
					}
				} catch (Exception e) {
					LOGGER.error(Constants.EXCEPTION, e);
				}

				if (null != request.getParameter(Constants.POPUP)) {
					return "redirect:/" + Constants.CLOSE_POPUP;
				}
				return "redirect:/chargeproductivityreject";
			} else {
				try {
					Map<String, String> whereClause = new HashMap<String, String>();
					whereClause.put(Constants.STATUS, Constants.ACTIVATE + "");

					List<Location> locationList = locationDao.findAll(null,
							whereClause);
					model.addAttribute(Constants.LOCATION_LIST, locationList);
				} catch (Exception e) {
					LOGGER.error("unable to load locations");
					LOGGER.error(e);
				}

				List<ObjectError> oeList = result.getAllErrors();
				for (ObjectError oe : oeList) {
					LOGGER.info("Errors:" + "field name = "
							+ oe.getObjectName() + " , value ="
							+ oe.getDefaultMessage());
				}

				if (chargeProdReject.getId() != null) {
					map.put(Constants.OPERATION_TYPE, Constants.EDIT);
					map.put(Constants.HIDE, Constants.HIDE);
				} else {
					map.put(Constants.OPERATION_TYPE, Constants.ADD);
					if (chargeProdReject.getChargeBatchProcessing() != null
							&& chargeProdReject.getChargeBatchProcessing()
									.getId() != null) {
						try {
							ChargeBatchProcessing chargeBatchProcessing = chargeBatchProcessingDao
									.getChargeBatchProcessingById(
											chargeProdReject
													.getChargeBatchProcessing()
													.getId(), false);
							chargeProdReject
									.setChargeBatchProcessing(chargeBatchProcessing);
						} catch (Exception e) {
							LOGGER.error(Constants.EXCEPTION, e);
						}
					}
					if (null != request
							&& null != request.getParameter(Constants.BATCH_ID)) {
						map.put(Constants.HIDE, Constants.HIDE);
					}
				}
				model.addAttribute(Constants.CHARGE_PROD_REJECT,
						chargeProdReject);
				model.addAttribute("insuranceTypes", chargeBatchInsuranceType);
				// model.addAttribute("reasonsToReject", reasonsToReject);
				model.addAttribute("validationError", "yes");

				if (null != request.getParameter(Constants.POPUP)) {
					map.put(Constants.POPUP, true);
					return CHARGE_PROD_REJECT_VIEW_PATH + Constants.POPUP;
				}

				map.put(Constants.POPUP, false);
				return CHARGE_PROD_REJECT_VIEW_PATH;
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);

			if (null != request.getParameter(Constants.POPUP)) {
				map.put(Constants.POPUP, true);
				return CHARGE_PROD_REJECT_VIEW_PATH + Constants.POPUP;
			}

			map.put(Constants.POPUP, false);
			return CHARGE_PROD_REJECT_VIEW_PATH;
		}
	}

	/**
	 * function to get rejection list
	 *
	 * @param request
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String getListPage(WebRequest request, Model model,
			HttpSession session) {
		if (authenticationCheck().equals(Constants.ERROR)) {
			return "redirect:error403";
		}
		String success = (String) session
				.getAttribute(Constants.SUCCESS_UPDATE);
		if (success != null) {
			model.addAttribute("success", success);
		}

		session.removeAttribute(Constants.SUCCESS_UPDATE);

		if (request != null && request.getParameter("fetchType") != null) {
			model.addAttribute("fetchType", request.getParameter("fetchType"));
		}

		List<Doctor> doctorList = new ArrayList<Doctor>();
		try {
			doctorList = doctorDao.findAll(null, getActiveDoctorOnly(), false);
		} catch (Exception e) {
			LOGGER.info(e.getMessage(), e);
		}

		Map<String, String> whereClause = new HashMap<String, String>();
		whereClause.put(Constants.DEPARTMENT_WITH_CHILD,
				Constants.CHARGE_DEPARTMENT_ID);
		whereClause.put(Constants.SELECTED_ROLES_IDS,
				Constants.STANDART_USER_ROLE_ID.toString());
		//Commented the line below to display deactivated users in dropdown menu
		//whereClause.put(Constants.STATUS, Constants.ACTIVATE + "");

		try {
			List<User> userList = userDao.findAll(null, whereClause);
			model.addAttribute(Constants.USER_LIST, userList);
		} catch (Exception e) {
			LOGGER.info(e.getMessage(), e);
		}

		model.addAttribute(Constants.DOCTOR_LIST, doctorList);
		// model.addAttribute("reasonsToReject", reasonsToReject);

		return "chargeProductivity/chargeProdRejectionList";
	}

	/*
	 * private Map<String, String> getOrderClause(WebRequest request) {
	 * Map<String, String> orderClauses = new HashMap<String, String>();
	 *
	 * int rp = 0;
	 *
	 * if (request.getParameter(Constants.RECORD_PRE_PAGE) != null) { rp =
	 * Integer.parseInt(request .getParameter(Constants.RECORD_PRE_PAGE)); try {
	 * orderClauses.put(Constants.LIMIT, "" + rp); } catch (Exception e) {
	 * LOGGER
	 * .debug("rp[Record pre Page] not coming or not an integer in request "); }
	 * }
	 *
	 * if (request.getParameter(Constants.PAGE) != null) { try { page =
	 * Integer.parseInt(request.getParameter(Constants.PAGE));
	 * orderClauses.put(Constants.OFFSET, "" + ((rp * page) - rp)); } catch
	 * (Exception e) { LOGGER.debug("Exception during parsing"); } }
	 *
	 * if (request.getParameter(Constants.SORT_ORDER) != null) {
	 * orderClauses.put(Constants.SORT_BY,
	 * request.getParameter(Constants.SORT_ORDER)); }
	 *
	 * if (request.getParameter(Constants.SORT_NAME) != null) {
	 * orderClauses.put(Constants.ORDER_BY,
	 * request.getParameter(Constants.SORT_NAME)); } return orderClauses; }
	 */

	private Map<String, String> getWhereClause(WebRequest request) {
		Map<String, String> whereClauses = new HashMap<String, String>();

		if (request.getParameter(Constants.QTYPE) != null
				&& request.getParameter(Constants.QUERY) != null
				&& !request.getParameter(Constants.QUERY).isEmpty()) {
			whereClauses.put(request.getParameter(Constants.QTYPE),
					request.getParameter(Constants.QUERY));
		}
		if (request.getParameter(Constants.KEYWORD) != null
				&& request.getParameter(Constants.KEYWORD).trim().length() > 0) {
			String keyword = request.getParameter(Constants.KEYWORD);
			if (keyword.contains("%")) {
				return null;
			} else {
				whereClauses.put(Constants.KEYWORD, keyword);
			}
		}
		if (request.getParameter("dummyCpt") != null) {
			whereClauses.put("dummyCpt", request.getParameter("dummyCpt"));
		}
		if (request.getParameter(Constants.TICKET_NUMBER) != null
				&& request.getParameter(Constants.TICKET_NUMBER).trim()
						.length() > 0) {
			whereClauses.put(Constants.TICKET_NUMBER,
					request.getParameter(Constants.TICKET_NUMBER));
		}

		if (request.getParameter("newRejections") != null) {
			whereClauses.put("newRejections",
					request.getParameter("newRejections"));
		}
		if (request.getParameter("resolved") != null) {
			whereClauses.put("resolved", request.getParameter("resolved"));
		}

		if (request.getParameter("requestRecord") != null) {
			whereClauses.put("requestRecord",
					request.getParameter("requestRecord"));
		}
		if (request.getParameter("requestDueRecord") != null) {
			whereClauses.put("requestDueRecord",
					request.getParameter("requestDueRecord"));
		}
		if (request.getParameter(Constants.DOCTOR_ID) != null
				&& request.getParameter(Constants.DOCTOR_ID).trim().length() > Constants.ZERO) {
			whereClauses.put(Constants.DOCTOR_ID,
					request.getParameter(Constants.DOCTOR_ID));
		}
		if (request.getParameter("workFlow") != null
				&& request.getParameter("workFlow").trim().length() > Constants.ZERO) {
			whereClauses.put("workFlow", request.getParameter("workFlow"));
		}

		// if (request.getParameter("location") != null
		// && request.getParameter("location").trim().length() > Constants.ZERO)
		// {
		// whereClauses.put("location", request.getParameter("location"));
		// }

		if (request.getParameter("reasonToReject") != null
				&& request.getParameter("reasonToReject").trim().length() > Constants.ZERO) {
			whereClauses.put("reasonToReject",
					request.getParameter("reasonToReject"));
		}

		// set created by id
		if (request.getParameter(Constants.CREATED_BY) != null
				&& request.getParameter(Constants.CREATED_BY).trim().length() > Constants.ZERO) {
			LOGGER.info("created by = "
					+ request.getParameter(Constants.CREATED_BY));
			whereClauses.put(Constants.CREATED_BY,
					request.getParameter(Constants.CREATED_BY));
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

		String status[] = request.getParameterValues(Constants.STATUS);

		if (status != null && status.length > 0) {
			int counter = 0;
			String statusString = "";
			for (String stat : status) {
				if (counter == 0) {
					statusString = statusString + "'" + stat + "'";
				} else {
					statusString = statusString + ",'" + stat + "'";
				}
				counter = 1;
			}
			whereClauses.put(Constants.STATUS, statusString);
		}

		return whereClauses;
	}

	@RequestMapping(value = "/json", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public JsonDataWrapper<ChargeBatchRejectJsonData> getRejectionList(
			WebRequest request) {
		LOGGER.info("in json method");
		try {
			Map<String, String> orderClauses = new HashMap<String, String>();
			Map<String, String> whereClauses = new HashMap<String, String>();

			if (request != null) {
				page = Integer.parseInt(request.getParameter(Constants.PAGE));
				orderClauses = AkpmsUtil.getOrderClause(request, page);
				whereClauses = getWhereClause(request);
				printReportCriteria = whereClauses;

			} else {
				LOGGER.info("request object is coming null");
			}
			int totalRows = 0;
			try {
				totalRows = chargeProdRejectDao.totalRecords(whereClauses);
			} catch (Exception e) {
				LOGGER.error(Constants.EXCEPTION, e);
			}

			if (totalRows > 0) {
				List<ChargeProdReject> rows = chargeProdRejectDao.findAll(
						orderClauses, whereClauses, true);
				List<ChargeBatchRejectJsonData> chargeBatchRejectJsonDataList = getJsonData(rows);
				JsonDataWrapper<ChargeBatchRejectJsonData> jdw = new JsonDataWrapper<ChargeBatchRejectJsonData>(
						page, totalRows, chargeBatchRejectJsonDataList);
				return jdw;
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}

		return null;
	}

	private List<ChargeBatchRejectJsonData> getJsonData(
			List<ChargeProdReject> rows) {
		List<ChargeBatchRejectJsonData> chargeBatchRejectJsonDataList = new ArrayList<ChargeBatchRejectJsonData>();

		if (rows != null && !rows.isEmpty()) {
			for (ChargeProdReject chargeProdReject : rows) {
				ChargeBatchRejectJsonData chargeBatchRejectJsonData = new ChargeBatchRejectJsonData();
				chargeBatchRejectJsonData.setId(chargeProdReject.getId());
				chargeBatchRejectJsonData.setPatientName(chargeProdReject
						.getPatientName());
				chargeBatchRejectJsonData.setAccount(chargeProdReject
						.getAccount());
				chargeBatchRejectJsonData.setSequence(chargeProdReject
						.getSequence());
				
				if(chargeProdReject.getReasonToReject() !=null) {
				chargeBatchRejectJsonData.setReasonToReject(chargeProdReject
						.getReasonToReject());
				} else {
					chargeBatchRejectJsonData.setReasonToReject("");
				}
				
				if (chargeProdReject.getLocation() != null) {
					chargeBatchRejectJsonData.setLocation(chargeProdReject
							.getLocation().getName());
				} else {
					chargeBatchRejectJsonData.setLocation("");
				}

				if (chargeProdReject.getDob() != null) {
					chargeBatchRejectJsonData.setDob(new SimpleDateFormat(
							Constants.DATE_FORMAT).format(chargeProdReject
							.getDob()));
				}

				if (chargeProdReject.getDateOfFirstRequestToDoctorOffice() != null) {
					chargeBatchRejectJsonData
							.setDateOfFirstRequestToDoctorOffice(new SimpleDateFormat(
									Constants.DATE_FORMAT).format(chargeProdReject
									.getDateOfFirstRequestToDoctorOffice()));
				}

				if (chargeProdReject.getDateOfSecondRequestToDoctorOffice() != null) {
					chargeBatchRejectJsonData
							.setDateOfSecondRequestToDoctorOffice(new SimpleDateFormat(
									Constants.DATE_FORMAT).format(chargeProdReject
									.getDateOfSecondRequestToDoctorOffice()));
				}
				StringBuilder doctorName = new StringBuilder();

				if (null != chargeProdReject.getChargeBatchProcessing()
						.getDoctor()) {
					doctorName.append(chargeProdReject
							.getChargeBatchProcessing().getDoctor().getName()
							.replace("(Non-Deposit)", ""));

					if (null != chargeProdReject.getChargeBatchProcessing()
							.getDoctor().getParent()) {
						doctorName.append(" ("
								+ chargeProdReject.getChargeBatchProcessing()
										.getDoctor().getParent().getName()
										.replace("(Non-Deposit)", "") + ") ");
					}
				}

				if (chargeProdReject.getCreatedOn() != null) {
					chargeBatchRejectJsonData.setCreatedOn(AkpmsUtil
							.akpmsDateFormat(chargeProdReject.getCreatedOn(),
									Constants.DATE_FORMAT));
				}
				
				if (chargeProdReject.getCompletedOn() != null) {
					chargeBatchRejectJsonData.setCompletedOn(AkpmsUtil
							.akpmsDateFormat(chargeProdReject.getCompletedOn(),
									Constants.DATE_FORMAT));
				}

				if (chargeProdReject.getCreatedBy() != null) {
					chargeBatchRejectJsonData.setCreatedBy(chargeProdReject
							.getCreatedBy().getFirstName()
							+ " "
							+ chargeProdReject.getCreatedBy().getLastName());
				} else {
					chargeBatchRejectJsonData.setCreatedBy("");
				}

				chargeBatchRejectJsonData.setDoctorName(doctorName.toString());
				if (chargeProdReject.getDateOfService() != null) {
					chargeBatchRejectJsonData
							.setDateOfService(new SimpleDateFormat(
									Constants.DATE_FORMAT)
									.format(chargeProdReject.getDateOfService()));
				}
				if(chargeProdReject.getInsuranceType() != null){
				chargeBatchRejectJsonData
						.setInsuranceType(chargeBatchInsuranceType
								.getProperty(chargeProdReject
										.getInsuranceType()));
				}else{
					chargeBatchRejectJsonData
					.setInsuranceType("");
				}
				chargeBatchRejectJsonData
						.setChargeBatchProcessing(chargeProdReject
								.getChargeBatchProcessing().getId());
				chargeBatchRejectJsonData.setStatus(chargeProdReject
						.getStatus());
				chargeBatchRejectJsonData.setWorkFLow(chargeProdReject
						.getWorkFlow());

				if (null != chargeProdReject.getChargeBatchProcessing()
						.getDateReceived()) {
					chargeBatchRejectJsonData.setDateReceived(AkpmsUtil
							.akpmsDateFormat(chargeProdReject
									.getChargeBatchProcessing()
									.getDateReceived(), Constants.DATE_FORMAT));
				}

				chargeBatchRejectJsonDataList.add(chargeBatchRejectJsonData);
			}
		}
		return chargeBatchRejectJsonDataList;
	}

	@RequestMapping(value = "batch/json", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Object[] getDetail(@RequestParam(Constants.BATCH_ID) String batchId,
			WebRequest request) {
		Object[] obj = null;
		try {
			if (batchId != null && !batchId.trim().isEmpty()) {
				long id = Long.parseLong(batchId);

				LOGGER.info("coming Batch id = " + id);

				try {
					long count = chargeProductivityDao
							.checkProductivityWithRejectWorkFlowByTicketNumber(id);
					if (count < Constants.ONE) {
						obj = new Object[2];
						obj[Constants.ZERO] = Constants.ERR;
						obj[Constants.ONE] = messageSource.getMessage(
								"productivity.noProductivity", null,
								Locale.ENGLISH);
						return obj;
					}
				} catch (Exception e) {
					LOGGER.error(Constants.EXCEPTION, e);
					obj = new Object[Constants.TWO];
					obj[Constants.ZERO] = Constants.ERR;
					obj[Constants.ONE] = messageSource
							.getMessage("productivity.noProductivity", null,
									Locale.ENGLISH);
					return obj;
				}
				ChargeBatchProcessing chargeBatchProcessing = chargeBatchProcessingDao
						.getChargeBatchProcessingShortDetailById(id);
				// obj = new Object[Constants.TWO];
				// obj[Constants.ZERO] = chargeBatchProcessing.getId();
				// LOGGER.info("Doctor name = "
				// + chargeBatchProcessing.getDoctor().getName());
				// obj[Constants.ONE] = chargeBatchProcessing.getDoctor()
				// .getName();

				obj = new Object[Constants.FIVE];
				obj[Constants.ZERO] = chargeBatchProcessing.getId();
				obj[Constants.ONE] = new SimpleDateFormat(Constants.DATE_FORMAT)
						.format(chargeBatchProcessing.getDosFrom());
				obj[Constants.TWO] = new SimpleDateFormat(Constants.DATE_FORMAT)
						.format(chargeBatchProcessing.getDosTo());

				obj[Constants.THREE] = chargeBatchtype
						.getProperty(chargeBatchProcessing.getType());

				LOGGER.info("Doctor name = "
						+ chargeBatchProcessing.getDoctor().getName());
				StringBuilder doctorName = new StringBuilder();
				doctorName.append(chargeBatchProcessing.getDoctor().getName()
						.replace("(Non-Deposit)", ""));
				if (null != chargeBatchProcessing.getDoctor().getParent()) {
					doctorName.append(" ("
							+ chargeBatchProcessing.getDoctor().getParent()
									.getName().replace("(Non-Deposit)", "")
							+ ") ");
				}
				obj[Constants.FOUR] = doctorName;
			} else {
				LOGGER.info("coming Batch id is null or empty ");
				obj = new Object[Constants.TWO];
				obj[Constants.ZERO] = Constants.ERR;
				obj[Constants.ONE] = messageSource.getMessage(
						"chargeProdReject.ticketNumber.required", null,
						Locale.ENGLISH);
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
			obj = new Object[Constants.TWO];
			obj[Constants.ZERO] = Constants.ERR;
			obj[Constants.ONE] = messageSource.getMessage(
					"productivity.noProductivity", null, Locale.ENGLISH);
		}
		return obj;
	}

	@RequestMapping(value = "/reupdate", method = RequestMethod.GET)
	public String getReUpdate(WebRequest request, Map<String, Object> map) {
		if (authenticationCheck().equals(Constants.ERROR)) {
			return "redirect:error403";
		}

		if (null != request
				&& null != request.getParameter(Constants.REJECTION_ID)) {
			try {
				long id = Long.parseLong(request
						.getParameter(Constants.REJECTION_ID));
				ChargeProdReject chargeProdReject = chargeProdRejectDao
						.getChargeProdRejectById(id, true);
				map.put(Constants.CHARGE_PROD_REJECT, chargeProdReject);
				map.put("batchId", request.getParameter("batchId"));
			} catch (Exception e) {
				LOGGER.error(Constants.EXCEPTION, e);
			}
		} else {
			LOGGER.warn(Constants.REJECTION_ID + " parmeter is coming null");
			map.put(Constants.CHARGE_PROD_REJECT, new ChargeProdReject());
		}
		if (null != request.getParameter(Constants.POPUP)) {
			map.put(Constants.POPUP, true);
			return "chargeProductivity/chargeProdRejectReUpdate"
					+ Constants.POPUP;
		}

		map.put(Constants.POPUP, false);

		return "chargeProductivity/chargeProdRejectReUpdate";
	}

	@RequestMapping(value = "/reupdate", method = RequestMethod.POST)
	public String setReUpdate(
			@ModelAttribute(Constants.CHARGE_PROD_REJECT) ChargeProdReject chargeProdReject,
			BindingResult result, Map<String, Object> map, WebRequest request,
			HttpSession session) {
		Calendar currenttime = Calendar.getInstance();
		if (request.getParameter("operation") != null
				&& request.getParameter("operation").trim().length() > 0) {
			if (request.getParameter("operation").equals("resolved")) {
				chargeProdReject.setResolved(true);
				chargeProdReject.setStatus(Constants.STATUS_RESOLVE);
				chargeProdReject.setResolvedBy(AkpmsUtil.getLoggedInUser());
				chargeProdReject.setResolvedOn(new Date((currenttime.getTime())
						.getTime()));

				chargeProdReject.setCompletedBy(null);
				chargeProdReject.setCompletedOn(null);

			} else if (request.getParameter("operation").equals("completed")) {
				chargeProdReject.setStatus(Constants.STATUS_COMPLETED);
				chargeProdReject.setCompletedBy(AkpmsUtil.getLoggedInUser());
				chargeProdReject.setCompletedOn(new Date(
						(currenttime.getTime()).getTime()));

			} else if (request.getParameter("operation").equals("pending")) {
				chargeProdReject.setResolved(false);
				chargeProdReject.setStatus(Constants.STATUS_PENDING);
				chargeProdReject.setResolvedBy(null);
				chargeProdReject.setResolvedOn(null);

				chargeProdReject.setCompletedBy(null);
				chargeProdReject.setCompletedOn(null);
			}
			try {
				LOGGER.info("going set Resolved");

				chargeProdRejectDao.update(chargeProdReject);
				session.setAttribute(Constants.SUCCESS_UPDATE, messageSource
						.getMessage("resolution.updatedSuccessfully", null,
								Locale.ENGLISH));

			} catch (Exception e) {
				LOGGER.error(Constants.EXCEPTION, e);
			}

			if (null != request.getParameter(Constants.POPUP)) {
				return "redirect:/" + Constants.CLOSE_POPUP;
			}
			return "redirect:/chargeproductivityreject";

		}
		chargeProdRejectReUpdateValidator.validate(chargeProdReject, result);
		if (!result.hasErrors()) {
			try {
				LOGGER.info("NO Error found ");
				chargeProdRejectDao.update(chargeProdReject);
				session.setAttribute(Constants.SUCCESS_UPDATE, messageSource
						.getMessage("resolution.updatedSuccessfully", null,
								Locale.ENGLISH));
				if (null != request.getParameter(Constants.POPUP)) {
					return "redirect:/" + Constants.CLOSE_POPUP;
				}
				return "redirect:/chargeproductivityreject";
			} catch (Exception e) {
				LOGGER.error(Constants.EXCEPTION, e);
				if (null != request.getParameter(Constants.POPUP)) {
					return "redirect:/" + Constants.CLOSE_POPUP;
				}
				return "redirect:/chargeProductivity/chargeProdRejectReUpdate";
			}
		} else {
			List<ObjectError> oeList = result.getAllErrors();
			for (ObjectError oe : oeList) {
				LOGGER.info("Errors:" + "field name = " + oe.getObjectName()
						+ " , value =" + oe.getDefaultMessage());
			}
			map.put("batchId", request.getParameter("batchId"));
			map.put(Constants.CHARGE_PROD_REJECT, chargeProdReject);

			if (null != request.getParameter(Constants.POPUP)) {
				map.put(Constants.POPUP, true);
				return "chargeProductivity/chargeProdRejectReUpdate"
						+ Constants.POPUP;
			}

			map.put(Constants.POPUP, false);

			return "chargeProductivity/chargeProdRejectReUpdate";
		}
	}

	@RequestMapping(value = "/printableweb", method = RequestMethod.GET)
	public String printReportListViewOnWeb(HttpServletResponse response,
			HttpSession session, Model model, Map<String, Object> map) {

		try {
			// String xmlString = null;
			Map<String, String> orderClauses = new HashMap<String, String>();
			// orderClauses.put(Constants.SORT_BY, Constants.SORT_ORDER_DESC);

			List<ChargeProdReject> rows = chargeProdRejectDao.findAll(
					orderClauses, printReportCriteria, true);

			model.addAttribute("CHARGE_BATCHES_REJECT", rows);

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}

		map.put("PRINT_REPORT_CRITERIA", printReportCriteria);
		map.put("SHOW_FOOTER", false);
		return "chargeproductivity/chargeProdRejectListToPrint";
	}

	public Map<String, String> getActiveDoctorOnly() {
		Map<String, String> whereClause = new HashMap<String, String>();
		// whereClause.put(Constants.PARENT_ONLY, null);
		whereClause.put(Constants.STATUS, Constants.STRING_ONE);
		return whereClause;
	}
}
