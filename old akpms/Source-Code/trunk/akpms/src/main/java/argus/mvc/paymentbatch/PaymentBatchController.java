package argus.mvc.paymentbatch;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;

import argus.domain.Doctor;
import argus.domain.MoneySource;
import argus.domain.PaymentBatch;
import argus.domain.User;
import argus.domain.paymentproductivity.PaymentPostingByOffSetManager;
import argus.domain.paymentproductivity.PaymentProductivityOffset;
import argus.exception.ArgusException;
import argus.mvc.productivity.helper.ArProductivityHelper;
import argus.repo.adminincome.AdminIncomeDao;
import argus.repo.doctor.DoctorDao;
import argus.repo.insurance.InsuranceDao;
import argus.repo.moneysource.MoneySourceDao;
import argus.repo.payment.PaymentTypeDao;
import argus.repo.paymentbatch.PaymentBatchDao;
import argus.repo.paymentproductivity.offset.PaymentOffsetManagerDao;
import argus.repo.paymentproductivity.offset.PaymentProductivityOffsetDao;
import argus.repo.revenueType.RevenueTypeDao;
import argus.repo.user.UserDao;
import argus.util.AkpmsUtil;
import argus.util.Constants;
import argus.util.ERAOffsetJsonData;
import argus.util.FopXmlToPdfGenrator;
import argus.util.JsonDataWrapper;
import argus.util.PaymentBatchJsonData;
import argus.util.UserXstreamConverter;
import argus.validator.PaymentBatchValidator;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

@Controller
@RequestMapping(value = "/paymentbatch")
@SessionAttributes({ Constants.PAYMENT_BATCH })
@Scope("session")
public class PaymentBatchController {

	private static final Logger LOGGER = Logger
			.getLogger(PaymentBatchController.class);

	private static final String INSURANCE_LIST = "insuranceList";
	private static final String DOCTOR_LIST = "doctorList";
	private static final String REVENUE_TYPES = "revenueTypes";
	private static final String PRO_HEALTH_DOCTOR_LIST = "proHealthDoctorList";
	private static final String MONEY_SOURCES = "moneySources";
	// private static final String PAYMENT_BATCH_MONEY_SOURCES =
	// "paymentBatchMoneySources";
	// private static final String PAY_BATCH_MONEY_SOURCES =
	// "payBatchMoneySources";
	private static final String ADMIN_INCOMES = "adminIncomes";
	private static final String PAYMENT_TYPE_LIST = "paymentTypeList";
	private static final String MONTHS = "months";
	private static final String YEARS = "years";
	// private static final String USER_LIST = "userList";
	private static final String REPORT_TYPE_LIST = "reportTypeList";

	@Autowired
	private InsuranceDao insuranceDao;

	@Autowired
	private DoctorDao doctorDao;

	@Autowired
	private PaymentBatchDao paymentBatchDao;

	@Autowired
	private PaymentTypeDao paymentTypeDao;

	@Autowired
	private RevenueTypeDao revenueTypeDao;

	@Autowired
	private PaymentBatchValidator paymentBatchValidator;

	@Autowired
	private MoneySourceDao moneySourceDao;

	@Autowired
	private AdminIncomeDao adminIncomeDao;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private PaymentProductivityOffsetDao offsetDao;

	@Autowired
	private PaymentOffsetManagerDao paymentOffsetManagerDao;

	@Autowired
	private UserDao userDao;

	private String xmlString;

	private Map<String, String> printReportCriteria = new HashMap<String, String>();;

	// private Map<String, String> printReportOrderCriteria = new
	// HashMap<String,String>();

	@InitBinder
	protected void initBinder(WebDataBinder binder) throws Exception {

		/*
		 * "permissions" is the name of the property that we want to register a
		 * custom editor to you can set property name null and it means you want
		 * to register this editor for occurrences of List class in * command
		 * object
		 */

		/*
		 * LOGGER.debug("in binding for Ph Doctor");
		 * binder.registerCustomEditor(List.class, "phDoctor", new
		 * CustomCollectionEditor(List.class) {
		 * 
		 * @Override protected Object convertElement(Object element) { String
		 * doctorId = (String) element; LOGGER.info("Doctor id = " + doctorId);
		 * Doctor doctor = new Doctor(); if (doctorId != null) {
		 * LOGGER.info("found emailTemplate object"); try { doctor =
		 * doctorDao.findById( (Long.parseLong(doctorId)), false); } catch
		 * (Exception e) { LOGGER.error(Constants.EXCEPTION, e); } } return
		 * doctor; } });
		 */

		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String value) {
				try {
					setValue(ArProductivityHelper.initBinder(value));
				} catch (ArgusException e) {
					LOGGER.error(Constants.EXCEPTION, e);
					setValue(null);
				}
			}

			@Override
			public String getAsText() {
				if (getValue() != null) {
					return new SimpleDateFormat(Constants.DATE_FORMAT)
							.format((Date) getValue());
				}
				return "";
			}
		});
	}

	/**
	 * default listing page for all payment batches
	 * 
	 * @param map
	 * @param session
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String paymentBatchList(Map<String, Object> map,
			HttpSession session, WebRequest request) {

		if (!hasAccessPaymentBatchList()) {
			return Constants.REDIRECT_ERROR_403;
		}

		String success = (String) session
				.getAttribute(Constants.SUCCESS_UPDATE);
		if (success != null) {
			map.put("success", success);
		}

		map.put("createdFrom", new SimpleDateFormat(Constants.DATE_FORMAT)
				.format(AkpmsUtil.getDateBeforeNDays(Constants.ONE)));
		map.put("createdTo",
				new SimpleDateFormat(Constants.DATE_FORMAT).format(new Date()));

		session.removeAttribute(Constants.SUCCESS_UPDATE);

		if (request.getParameter(Constants.PAYMENT_TYPE) != null) {
			LOGGER.info(" payment type :: "
					+ request.getParameter(Constants.PAYMENT_TYPE));
			map.put(Constants.PAYMENT_TYPE,
					request.getParameter(Constants.PAYMENT_TYPE));
		} else {
			map.put(Constants.PAYMENT_TYPE, "");
		}

		populateDropDownLists(map);
		// need to verify
		map.put(REPORT_TYPE_LIST, this.getBatchReportTypes());
		return "paymentbatch/paymentBatchList";
	}

	/**
	 * function to put report type on payment batch list/search page
	 * 
	 * @return
	 */

	private Map<String, String> getBatchReportTypes() {
		Map<String, String> reportTypeList = new HashMap<String, String>();

		/*
		 * reportTypeList.put("all", messageSource.getMessage("report.type.all",
		 * null, Locale.ENGLISH)); reportTypeList.put("posted",
		 * messageSource.getMessage( "report.type.posted", null,
		 * Locale.ENGLISH)); reportTypeList.put("notPostedYet",
		 * messageSource.getMessage( "report.type.notPostedYet", null,
		 * Locale.ENGLISH));
		 */

		reportTypeList.put("all", Constants.ALL_BATCH);
		reportTypeList.put("posted", Constants.POSTED_BATCH);
		reportTypeList.put("notPostedYet", Constants.NOT_POSTED_BATCH);

		return reportTypeList;
	}

	/**
	 * function to appear add payment batch page
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String paymentBatchLoad(Map<String, Object> map, Model model,
			WebRequest request, HttpSession session) {

		LOGGER.info("in paymentBatchLoad get");

		if (!hasAccessPaymentBatch(request)) {
			return Constants.REDIRECT_ERROR_403;
		}
		String success = (String) session
				.getAttribute(Constants.SUCCESS_UPDATE);
		if (success != null) {
			map.put("success", success);
		}

		session.removeAttribute(Constants.SUCCESS_UPDATE);

		PaymentBatch paymentBatch = null;
		if (request.getParameter("paymentBatchId") != null) {
			try {
				paymentBatch = paymentBatchDao.findById(
						Long.valueOf(request.getParameter("paymentBatchId")),
						true);
				map.put(Constants.OPERATION_TYPE, Constants.REVISE);
				map.put(Constants.BUTTON_NAME, Constants.UPDATE);
				Map<String, String> whereClauses = new HashMap<String, String>();
				whereClauses.put(Constants.TICKET_NUMBER, paymentBatch.getId()
						.toString());
				if (null != request.getParameter("type")) {
					map.put("totalOffsetPosted", paymentOffsetManagerDao
							.totalRecordForList(whereClauses));
				}

			} catch (Exception e) {
				LOGGER.info("Exception occured while fetching Payment Batch");
				LOGGER.error(Constants.EXCEPTION, e);
			}
		} else {
			if (paymentBatch == null) {
				paymentBatch = new PaymentBatch();
				Integer.valueOf(Calendar.getInstance().get(Calendar.YEAR))
						.longValue();
				paymentBatch
						.setBillingMonth(Integer.valueOf(
								Calendar.getInstance().get(Calendar.MONTH))
								.longValue() + 1);
				paymentBatch.setBillingYear(Integer.valueOf(
						Calendar.getInstance().get(Calendar.YEAR)).longValue());
			}

			if (null != request.getParameter("type")) {
				try {
					paymentBatch.setPaymentType(paymentTypeDao
							.findById(Constants.PAYMENT_TYPE_OFFSET));
				} catch (Exception e) {
					LOGGER.info(e.getMessage(), e);
				}
			}

			map.put(Constants.OPERATION_TYPE, Constants.ADD);
			map.put(Constants.BUTTON_NAME, Constants.SAVE);
		}

		if (null != request.getParameter("type")) {
			map.put("type", request.getParameter("type"));
		}

		populateDropDownLists(map);
		map.put(Constants.PAYMENT_BATCH, paymentBatch);
		map.put(Constants.READ_ONLY, false);

		if (null != request.getParameter(Constants.POPUP)) {
			map.put(Constants.POPUP, true);
			return "paymentbatch/paymentBatchAdd" + Constants.POPUP;
		}

		map.put(Constants.POPUP, false);

		LOGGER.info("out paymentBatchLoad get");
		return "paymentbatch/paymentBatchAdd";
	}

	/**
	 * This method loads the payment batch to be revised
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String revisePaymentBatchLoad(Map<String, Object> map, Model model,
			WebRequest request) {
		LOGGER.info("in revisePaymentBatchLoad get");

		if (!hasAccessPaymentBatch(request)) {
			return Constants.REDIRECT_ERROR_403;
		}

		PaymentBatch paymentBatch = null;
		if (request.getParameter("updateBatchId") != null) {
			try {
				paymentBatch = paymentBatchDao.findById(
						Long.valueOf(request.getParameter("updateBatchId")),
						true);
				// paymentBatch.setDateOfDeposit(AkpmsUtil.akpmsDateFormat(
				// paymentBatch.getDepositDate(), Constants.DATE_FORMAT));
				if (paymentBatch.getPostedBy() != null
						&& !AkpmsUtil.checkPermission("P-9")) {
					map.put(Constants.BATCH_POSTED, true);
				} else {
					map.put(Constants.BATCH_POSTED, false);
				}
				if (request.getParameter("viewBatch") != null
						&& request.getParameter("viewBatch").trim().equals("1")) {
					map.put(Constants.BATCH_POSTED, true);
					map.put(Constants.OPERATION_TYPE, Constants.VIEW);
				} else {
					map.put(Constants.OPERATION_TYPE, Constants.UPDATE);
				}

				// List<PaymentBatchMoneySource> batchmoneyList = new
				// ArrayList<PaymentBatchMoneySource>();
				// for (PaymentBatchMoneySource batchMoneySource : paymentBatch
				// .getPaymentBatchMoneySources()) {
				// MoneySource moneySource = new MoneySource();
				// moneySource = batchMoneySource.getMoneySource();
				// batchMoneySource.setMoneySource(moneySource);
				// batchmoneyList.add(batchMoneySource);
				// }

				// map.put(PAY_BATCH_MONEY_SOURCES, batchmoneyList);
				map.put(Constants.BUTTON_NAME, Constants.UPDATE);
				// double plusMinusPosting = (paymentBatch.getDepositAmount()
				// .longValue() + paymentBatch.getNdba())
				// - (paymentBatch.getManuallyPostedAmt().longValue() +
				// paymentBatch
				// .getElecPostedAmt().longValue());
				double plusMinusPosting = (paymentBatch.getDepositAmount() + paymentBatch
						.getNdba())
						- (paymentBatch.getManuallyPostedAmt() + paymentBatch
								.getElecPostedAmt());
				LOGGER.info("plusMinusPosting :: " + plusMinusPosting);
				map.put("plusMinusPosting", plusMinusPosting);
			} catch (Exception e) {
				LOGGER.info("Exception occured while fetching Payment Batch");
				LOGGER.error(Constants.EXCEPTION, e);
			}
		}

		populateDropDownLists(map);
		map.put(Constants.PAYMENT_BATCH, paymentBatch);
		map.put(Constants.READ_ONLY, true);

		LOGGER.info("out revisePaymentBatchLoad get");

		if (null != request.getParameter(Constants.POPUP)) {
			map.put(Constants.POPUP, true);
			return "paymentbatch/paymentBatchUpdate" + Constants.POPUP;
		}

		map.put(Constants.POPUP, false);
		return "paymentbatch/paymentBatchUpdate";
	}

	/**
	 * This methods update the revised payment batch
	 * 
	 * @param paymentBatch
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String revisePaymentBatchProcess(
			@ModelAttribute(Constants.PAYMENT_BATCH) PaymentBatch paymentBatch,
			Map<String, Object> map, Model model, WebRequest request,
			BindingResult result, HttpSession session) {
		LOGGER.info("in revisePaymentBatchLoad post");

		try {
			if (request.getParameter("revisedById").trim().length() > 0) {
				paymentBatch.setRevisedBy((userDao.findById(
						Long.valueOf(request.getParameter("revisedById")),
						false)));
			} else {
				paymentBatch.setRevisedBy(null);
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
		LOGGER.info("In Revise payment batch method");
		Object[] ticketNumber = new Object[1];
		Calendar currenttime = Calendar.getInstance();
		map.put("plusMinusPosting",
				(paymentBatch.getDepositAmount() + paymentBatch.getNdba())
						- (paymentBatch.getManuallyPostedAmt() + paymentBatch
								.getElecPostedAmt()));
		/*
		 * if (paymentBatch.getPostedBy() == null) {
		 * paymentBatch.setPostedBy(AkpmsUtil.getLoggedInUser()); paymentBatch
		 * .setPostedOn(new Date((currenttime.getTime()).getTime())); if
		 * (paymentBatch.getDatePosted() == null) {
		 * result.rejectValue("datePosted", "datePosted.required"); } } else {
		 */
		if (paymentBatch.getPostedBy().getId().longValue() == 0) {
			result.rejectValue("postedBy.id",
					"chargeBatchProcessing.postedBy.required");
		}
		if (paymentBatch.getDatePosted() == null) {
			result.rejectValue("datePosted", "datePosted.required");
		}
		if (paymentBatch.getPostedOn() == null) {
			result.rejectValue("postedOn", "postedOn.required");
		}
		/*
		 * if (result.hasErrors()) { map.put(Constants.OPERATION_TYPE,
		 * Constants.EDIT); map.put(Constants.OPERATION_TYPE, "Revise");
		 * map.put(Constants.BUTTON_NAME, Constants.UPDATE);
		 * map.put(Constants.READ_ONLY, true); map.put(Constants.BATCH_POSTED,
		 * false); populateDropDownLists(map); return
		 * "paymentbatch/paymentBatchUpdate"; }
		 */
		paymentBatch.setReUpdatedBy(AkpmsUtil.getLoggedInUser());
		paymentBatch
				.setReUpdatedOn(new Date((currenttime.getTime()).getTime()));
		// }
		if (result.hasErrors()) {
			map.put(Constants.OPERATION_TYPE, Constants.EDIT);
			map.put(Constants.OPERATION_TYPE, Constants.REVISE);
			map.put(Constants.BUTTON_NAME, Constants.UPDATE);
			map.put(Constants.READ_ONLY, true);
			map.put(Constants.BATCH_POSTED, false);
			populateDropDownLists(map);

			if (null != request.getParameter(Constants.POPUP)) {
				map.put(Constants.POPUP, true);
				return "paymentbatch/paymentBatchUpdate" + Constants.POPUP;
			}

			map.put(Constants.POPUP, false);
			return "paymentbatch/paymentBatchUpdate";
		}
		try {
			paymentBatchDao.updatePaymentBatch(paymentBatch);
			ticketNumber[0] = paymentBatch.getId();
			session.setAttribute(
					Constants.SUCCESS_UPDATE,
					messageSource.getMessage(
							"paymentBatchTicket.updatedSuccessfully",
							ticketNumber, Locale.ENGLISH).trim());
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}

		LOGGER.info("out revisePaymentBatchLoad post");

		if (null != request.getParameter(Constants.POPUP)) {
			return "redirect:/" + Constants.CLOSE_POPUP;
		}
		return "redirect:/paymentbatch";
	}

	/**
	 * function to use populate all dropdown options from database on add/edit
	 * page of payment batch
	 * 
	 * @param model
	 */
	private void populateDropDownLists(Map<String, Object> map) {
		LOGGER.info("in populateDropDownLists");
		Map<String, String> whereClause = new HashMap<String, String>();
		try {
			whereClause.put(Constants.STATUS, Constants.ACTIVATE + "");

			map.put(INSURANCE_LIST, insuranceDao.findAll(null, whereClause));
		} catch (Exception e) {
			LOGGER.info("EXception while getting insurance list");
			LOGGER.info(e.getMessage());
			map.put(INSURANCE_LIST, null);
		}
		try {
			whereClause.clear();
			whereClause.put(Constants.STATUS, Constants.ACTIVATE + "");
			whereClause.put(Constants.PARENT_ONLY, "1");
			map.put(DOCTOR_LIST, doctorDao.findAll(null, whereClause, false));
		} catch (Exception e) {
			LOGGER.info("EXception while getting doctor list");
			LOGGER.info(e.getMessage());
			map.put(DOCTOR_LIST, null);
		}
		try {
			whereClause.clear();
			whereClause.put(Constants.STATUS, Constants.ACTIVATE + "");
			whereClause.put(Constants.PARENT_ID, Constants.PRO_HEALTH_GROUP_ID
					+ "");

			map.put(PRO_HEALTH_DOCTOR_LIST,
					doctorDao.findAll(null, whereClause, false));
		} catch (Exception e) {
			LOGGER.info("EXception while getting pro health doctor list");
			LOGGER.info(e.getMessage());
			map.put(PRO_HEALTH_DOCTOR_LIST, null);
		}
		try {
			List<MoneySource> moneySourceList = moneySourceDao.findAll(true);
			// List<PaymentBatchMoneySource> batchmoneyList = new
			// ArrayList<PaymentBatchMoneySource>();
			// for (MoneySource moneySource : moneySourceList) {
			// PaymentBatchMoneySource batchMoneySource = new
			// PaymentBatchMoneySource();
			// batchMoneySource.setMoneySource(moneySource);
			// batchmoneyList.add(batchMoneySource);
			// }
			// map.put(PAYMENT_BATCH_MONEY_SOURCES, batchmoneyList);
			map.put(MONEY_SOURCES, moneySourceList);
		} catch (Exception e) {
			LOGGER.info("EXception while getting pro MONEY_SOURCES list");
			LOGGER.info(e.getMessage());
			map.put(MONEY_SOURCES, null);
		}
		try {
			map.put(ADMIN_INCOMES, adminIncomeDao.findAll(true));
		} catch (Exception e) {
			LOGGER.info("EXception while getting pro ADMIN_INCOMES list");
			LOGGER.info(e.getMessage());
			map.put(ADMIN_INCOMES, null);
		}
		try {
			map.put(REVENUE_TYPES, revenueTypeDao.findAll(true));
		} catch (Exception e) {
			LOGGER.info("EXception while getting REVENUE_TYPES list");
			LOGGER.info(e.getMessage());
			map.put(REVENUE_TYPES, null);
		}
		try {
			whereClause.clear();
			whereClause.put(Constants.STATUS, Constants.ACTIVATE + "");
			map.put(PAYMENT_TYPE_LIST,
					paymentTypeDao.findAll(null, whereClause));
		} catch (Exception e) {
			LOGGER.info("EXception while getting PAYMENT_TYPE_LIST list");
			LOGGER.info(e.getMessage());
			map.put(PAYMENT_TYPE_LIST, null);
		}
		try {
			whereClause.clear();
			whereClause.put(Constants.DEPARTMENT_WITH_CHILD,
					Constants.PAYMENT_DEPARTMENT_ID);
			whereClause.put(Constants.SELECTED_ROLES_IDS,
					Constants.STANDART_USER_ROLE_ID.toString());
			whereClause.put(Constants.STATUS, Constants.ACTIVATE + "");
			List<User> userList = userDao.findAll(null, whereClause);

			map.put(Constants.USER_LIST, userList);
		} catch (Exception e) {
			LOGGER.info("EXception while getting USER_LIST list");
			LOGGER.info(e.getMessage());
			map.put(Constants.USER_LIST, null);
		}
		map.put(MONTHS, AkpmsUtil.getMonths());
		map.put(YEARS, AkpmsUtil.getYears());
		LOGGER.info("out populateDropDownLists");
	}

	/**
	 * this function will execute when user click on submit button on add/edit
	 * payment batch
	 * 
	 * @param paymentBatch
	 * @param map
	 * @param result
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String paymentBatchProcess(
			@ModelAttribute(Constants.PAYMENT_BATCH) PaymentBatch paymentBatch,
			Map<String, Object> map, BindingResult result, Model model,
			HttpSession session, WebRequest request) {

		LOGGER.info("In paymentBatchProcess method");
		paymentBatchValidator.validate(paymentBatch, result);

		if (result.hasErrors()) {
			if (paymentBatch.getId() != null) {
				map.put(Constants.OPERATION_TYPE, Constants.EDIT);
				map.put(Constants.BUTTON_NAME, Constants.UPDATE);
				try {
					map.put(Constants.OPERATION_TYPE, Constants.REVISE);
					map.put(Constants.BUTTON_NAME, Constants.UPDATE);
					Map<String, String> whereClauses = new HashMap<String, String>();
					whereClauses.put(Constants.TICKET_NUMBER, paymentBatch
							.getId().toString());
					if (null != request.getParameter("type")) {
						map.put("totalOffsetPosted", paymentOffsetManagerDao
								.totalRecordForList(whereClauses));
					}

				} catch (Exception e) {
					LOGGER.error(Constants.EXCEPTION, e);
				}

			} else {
				map.put(Constants.OPERATION_TYPE, Constants.ADD);
				map.put(Constants.BUTTON_NAME, Constants.SAVE);

			}

			populateDropDownLists(map);

			if (null != request.getParameter(Constants.POPUP)) {
				map.put(Constants.POPUP, true);
				return "paymentbatch/paymentBatchAdd" + Constants.POPUP;
			}

			map.put(Constants.POPUP, false);
			return "paymentbatch/paymentBatchAdd";
		}

		if (paymentBatch.getMoneySource() == null
				|| paymentBatch.getMoneySource().getId() == null) {

			paymentBatch.setMoneySource(null);
		}
		if (paymentBatch.getPhDoctor() == null
				|| paymentBatch.getPhDoctor().getId() == null) {
			paymentBatch.setPhDoctor(null);
		}
		if (paymentBatch.getInsurance() == null
				|| paymentBatch.getInsurance().getId() == null) {
			paymentBatch.setInsurance(null);
		}
		/*
		 * if (paymentBatch.getDoctor().getId().longValue() ==
		 * Constants.PRO_HEALTH_GROUP_ID .longValue() &&
		 * paymentBatch.getPaymentType().getId().longValue() ==
		 * Constants.PAYMENT_TYPE_OTC .longValue()) {
		 * paymentBatch.getRevenueType().setId(
		 * Constants.REVENUE_TYPE_PATIENT_COLLECTION); }
		 */

		if (paymentBatch.getPaymentType().getId().longValue() != Constants.PAYMENT_TYPE_ADMIN_INCOME
				.longValue()) {
			// paymentBatch.setAdminIncome(null);
			paymentBatch.setRevenueType(null);
		}
		if (paymentBatch.getDoctor().getId().longValue() != Constants.PRO_HEALTH_GROUP_ID
				.longValue()) {
			paymentBatch.setPhDoctor(null);
			//paymentBatch.setRevenueType(null);
		}

		// to pass ticket number into message properties
		Object[] ticketNumber = new Object[1];
		if (paymentBatch.getId() != null) {
			try {
				paymentBatch.setRevisedBy(AkpmsUtil.getLoggedInUser());
				Calendar currenttime = Calendar.getInstance();
				paymentBatch.setRevisedOn(new Date((currenttime.getTime())
						.getTime()));
				paymentBatchDao.updatePaymentBatch(paymentBatch);
				ticketNumber[0] = paymentBatch.getId();
				session.setAttribute(
						Constants.SUCCESS_UPDATE,
						messageSource.getMessage(
								"paymentBatchTicket.updatedSuccessfully",
								ticketNumber, Locale.ENGLISH).trim());

			} catch (ArgusException e) {
				LOGGER.info("Exception occured while updating paymentBatch");
				LOGGER.info(Constants.EXCEPTION, e);
			}
		} else {
			try {
				Long batchId = 0L;
				if (request.getParameter("type") != null
						&& request.getParameter("type")
								.equals(Constants.OFFSET)) {
					paymentBatch.setOffsetType("offline");
					batchId = paymentBatchDao.addPaymentBatch(paymentBatch);
					ticketNumber[0] = paymentBatch.getId();
					map.put(Constants.BATCH_ID, batchId);
					session.setAttribute(
							Constants.SUCCESS_UPDATE,
							messageSource.getMessage(
									"paymentBatchTicket.addedSuccessfully",
									ticketNumber, Locale.ENGLISH).trim());
					// return "redirect:/paymentoffsetmanager/add";
				} else {
					paymentBatch.setOffsetType("online");
					batchId = paymentBatchDao.addPaymentBatch(paymentBatch);
				}
				ticketNumber[0] = paymentBatch.getId();
				session.setAttribute(
						Constants.SUCCESS_UPDATE,
						messageSource.getMessage(
								"paymentBatchTicket.addedSuccessfully",
								ticketNumber, Locale.ENGLISH).trim());

			} catch (ArgusException e) {
				LOGGER.info("Exception occured while PaymentBatch");
				LOGGER.info(Constants.EXCEPTION, e);
			}
		}

		LOGGER.info("Out paymentBatchProcess method");
		if (paymentBatch.isAddMore()) {
			if (request.getParameter("type") != null
					&& request.getParameter("type").equalsIgnoreCase(
							Constants.OFFSET)) {
				LOGGER.info("type :: " + request.getParameter("type"));
				return "redirect:/paymentbatch/add?type="
						+ request.getParameter("type");
			}
			return "redirect:/paymentbatch/add";
		}

		if (null != request.getParameter(Constants.POPUP)) {
			return "redirect:/" + Constants.CLOSE_POPUP;
		}
		return "redirect:/paymentbatch";
	}

	/**
	 * function to set json list on payment batch list page
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/json", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public JsonDataWrapper<PaymentBatchJsonData> listAllPaymentBatch(
			WebRequest request) {
		LOGGER.info("in json method");

		int page = 1;
		Map<String, String> orderClauses = new HashMap<String, String>();
		Map<String, String> whereClauses = new HashMap<String, String>();

		if (request != null) {
			page = Integer.parseInt(request.getParameter(Constants.PAGE));
			orderClauses = AkpmsUtil.getOrderClause(request, page);
			whereClauses = getWhereClause(request);
			printReportCriteria.clear();
			// printReportOrderCriteria.clear();
			printReportCriteria.putAll(whereClauses);
			// printReportOrderCriteria.putAll(orderClauses);
		} else {
			LOGGER.info("request object is coming null");
		}
		int totalRows = paymentBatchDao.totalRecord(whereClauses);
		if (totalRows > 0) {
			List<PaymentBatch> rows = null;
			try {
				rows = paymentBatchDao.findAll(orderClauses, whereClauses);
				List<PaymentBatchJsonData> djd = getJsonData(rows);
				JsonDataWrapper<PaymentBatchJsonData> jdw = new JsonDataWrapper<PaymentBatchJsonData>(
						page, totalRows, djd);
				return jdw;
			} catch (Exception e) {
				LOGGER.info("ERROR: " + e.getMessage());
			}
		}

		LOGGER.info("out json method");
		return null;
	}

	/**
	 * function to set payment batch list in json wrapper
	 * 
	 * @param rows
	 * @return
	 */
	private List<PaymentBatchJsonData> getJsonData(List<PaymentBatch> rows)
			throws Exception {
		List<PaymentBatchJsonData> paymentBatchJsonData = new ArrayList<PaymentBatchJsonData>();
		LOGGER.info("IN Payment batch get JSON Data");
		if (rows != null && rows.size() > 0) {
			for (PaymentBatch paymentBatch : rows) {
				PaymentBatchJsonData djd = new PaymentBatchJsonData();

				djd.setId(paymentBatch.getId());

				djd.setCreatedBy(paymentBatch.getCreatedBy().getFirstName()
						+ " " + paymentBatch.getCreatedBy().getLastName());

				djd.setDepositDate(AkpmsUtil.akpmsDateFormat(
						paymentBatch.getDepositDate(), Constants.DATE_FORMAT));

				djd.setBillingMonth(paymentBatch.getBillingMonth().toString()
						+ "/" + paymentBatch.getBillingYear());

				djd.setDepositAmount(paymentBatch.getDepositAmount());
				djd.setNdba(paymentBatch.getNdba());

				StringBuilder doctorName = new StringBuilder();
				if (paymentBatch.getDoctor().isNonDeposit()) {
					doctorName.append(paymentBatch.getDoctor().getName()
							+ " (Non-Deposit)");
				} else {
					doctorName.append(paymentBatch.getDoctor().getName());
				}

				if (null != paymentBatch.getPhDoctor()) {
					doctorName.append(" ("
							+ paymentBatch.getPhDoctor().getName() + ")");
				}
				djd.setDoctor(doctorName.toString());
				if (null != paymentBatch.getInsurance()) {
					djd.setInsurance(paymentBatch.getInsurance().getName());
				} else {
					djd.setInsurance("");
				}
				djd.setPaymentType(paymentBatch.getPaymentType().getName());

				if (paymentBatch.getPostedOn() != null) {
					djd.setPostedOn(AkpmsUtil.akpmsDateFormat(
							paymentBatch.getPostedOn(), Constants.DATE_FORMAT));
				}

				if (paymentBatch.getDatePosted() != null) {
					djd.setDatePosted(AkpmsUtil.akpmsDateFormat(
							paymentBatch.getDatePosted(), Constants.DATE_FORMAT));
				}

				if (paymentBatch.getPostedBy() != null) {
					djd.setPostedBy(paymentBatch.getPostedBy().getFirstName()
							+ " " + paymentBatch.getPostedBy().getLastName());
				}
				if (paymentBatch.getRevisedBy() != null) {
					djd.setRevisedBy(paymentBatch.getRevisedBy().getFirstName()
							+ " " + paymentBatch.getRevisedBy().getLastName());
				}
				if (paymentBatch.getRevisedOn() != null) {
					djd.setRevisedOn(AkpmsUtil.akpmsDateFormat(
							paymentBatch.getRevisedOn(), Constants.DATE_FORMAT));
				}
				if (paymentBatch.getOffsetType() != null) {
					djd.setOffsetType(paymentBatch.getOffsetType());
				}

				paymentBatchJsonData.add(djd);
			}
		}

		LOGGER.info("OUT Payment batch get JSON Data");

		return paymentBatchJsonData;
	}

	private Map<String, String> getWhereClause(WebRequest request) {
		Map<String, String> whereClauses = new HashMap<String, String>();

		// set billing month
		if (request.getParameter(Constants.MONTH) != null
				&& request.getParameter(Constants.MONTH).trim().length() > Constants.ZERO) {
			LOGGER.info("billing month = "
					+ request.getParameter(Constants.MONTH));
			whereClauses.put(Constants.MONTH,
					request.getParameter(Constants.MONTH));
		}

		// set billing year
		if (request.getParameter(Constants.YEAR) != null
				&& request.getParameter(Constants.YEAR).trim().length() > Constants.ZERO) {
			LOGGER.info("billing year = "
					+ request.getParameter(Constants.YEAR));
			whereClauses.put(Constants.YEAR,
					request.getParameter(Constants.YEAR));
		}

		// set doctor id
		if (request.getParameter(Constants.DOCTOR_ID) != null
				&& request.getParameter(Constants.DOCTOR_ID).trim().length() > Constants.ZERO) {
			LOGGER.info("billing doctor id = "
					+ request.getParameter(Constants.DOCTOR_ID));
			whereClauses.put(Constants.DOCTOR_ID,
					request.getParameter(Constants.DOCTOR_ID));
		}

		// set pro health doctor ids
		if (request.getParameter(Constants.PH_DOCTOR_IDS) != null
				&& request.getParameter(Constants.PH_DOCTOR_IDS).trim()
						.length() > Constants.ZERO) {
			LOGGER.info("PH_DOCTOR_IDS =  "
					+ request.getParameter(Constants.PH_DOCTOR_IDS));
			whereClauses.put(Constants.PH_DOCTOR_IDS,
					request.getParameter(Constants.PH_DOCTOR_IDS));
		}

		// set insurance id
		if (request.getParameter(Constants.INSURANCE_ID) != null
				&& request.getParameter(Constants.INSURANCE_ID).trim().length() > Constants.ZERO) {
			LOGGER.info("insurance id = "
					+ request.getParameter(Constants.INSURANCE_ID));
			whereClauses.put(Constants.INSURANCE_ID,
					request.getParameter(Constants.INSURANCE_ID));
		}

		// set created by id
		if (request.getParameter(Constants.CREATED_BY) != null
				&& request.getParameter(Constants.CREATED_BY).trim().length() > Constants.ZERO) {
			LOGGER.info("created by = "
					+ request.getParameter(Constants.CREATED_BY));
			whereClauses.put(Constants.CREATED_BY,
					request.getParameter(Constants.CREATED_BY));
		}

		// set created by id
		if (request.getParameter(Constants.POSTED_BY) != null
				&& request.getParameter(Constants.POSTED_BY).trim().length() > Constants.ZERO) {
			LOGGER.info("date deposit from = "
					+ request.getParameter(Constants.POSTED_BY));
			whereClauses.put(Constants.POSTED_BY,
					request.getParameter(Constants.POSTED_BY));
		}

		// set REVENUE_TYPE_IDS
		if (request.getParameter(Constants.REVENUE_TYPE_IDS) != null
				&& request.getParameter(Constants.REVENUE_TYPE_IDS).trim()
						.length() > Constants.ZERO) {
			LOGGER.info("REVENUE_TYPE_IDS= "
					+ request.getParameter(Constants.REVENUE_TYPE_IDS));
			whereClauses.put(Constants.REVENUE_TYPE_IDS,
					request.getParameter(Constants.REVENUE_TYPE_IDS));
		}

		// set PAYMENT_TYPE_IDS
		if (request.getParameter(Constants.PAYMENT_TYPE_IDS) != null
				&& request.getParameter(Constants.PAYMENT_TYPE_IDS).trim()
						.length() > Constants.ZERO) {
			LOGGER.info("PAYMENT_TYPE_IDS= "
					+ request.getParameter(Constants.PAYMENT_TYPE_IDS));
			whereClauses.put(Constants.PAYMENT_TYPE_IDS,
					request.getParameter(Constants.PAYMENT_TYPE_IDS));
		}

		// set MONEY_SOURCE_IDS
		if (request.getParameter(Constants.MONEY_SOURCE_IDS) != null
				&& request.getParameter(Constants.MONEY_SOURCE_IDS).trim()
						.length() > Constants.ZERO) {
			LOGGER.info("MONEY_SOURCE_IDS= "
					+ request.getParameter(Constants.MONEY_SOURCE_IDS));
			whereClauses.put(Constants.MONEY_SOURCE_IDS,
					request.getParameter(Constants.MONEY_SOURCE_IDS));
		}

		// set deposit from date
		if (request.getParameter(Constants.DATE_DEPOSIT_FROM) != null
				&& request.getParameter(Constants.DATE_DEPOSIT_FROM).trim()
						.length() > Constants.ZERO) {
			LOGGER.info("DATE_DEPOSIT_FROM= "
					+ request.getParameter(Constants.DATE_DEPOSIT_FROM));
			whereClauses.put(Constants.DATE_DEPOSIT_FROM,
					request.getParameter(Constants.DATE_DEPOSIT_FROM));
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
		// set deposit to date
		if (request.getParameter(Constants.DATE_DEPOSIT_TO) != null
				&& request.getParameter(Constants.DATE_DEPOSIT_TO).trim()
						.length() > Constants.ZERO) {
			LOGGER.info("DATE_DEPOSIT_TO= "
					+ request.getParameter(Constants.DATE_DEPOSIT_TO));
			whereClauses.put(Constants.DATE_DEPOSIT_TO,
					request.getParameter(Constants.DATE_DEPOSIT_TO));
		}

		// set posted from date (CT POSTED DATE)
		if (request.getParameter(Constants.DATE_POSTED_FROM) != null
				&& request.getParameter(Constants.DATE_POSTED_FROM).trim()
						.length() > Constants.ZERO) {
			LOGGER.info("DATE_POSTED_FROM= "
					+ request.getParameter(Constants.DATE_POSTED_FROM));
			whereClauses.put(Constants.DATE_POSTED_FROM,
					request.getParameter(Constants.DATE_POSTED_FROM));
		}

		// set posted to date (CT POSTED DATE)
		if (request.getParameter(Constants.DATE_POSTED_TO) != null
				&& request.getParameter(Constants.DATE_POSTED_TO).trim()
						.length() > Constants.ZERO) {
			LOGGER.info("DATE_POSTED_TO= "
					+ request.getParameter(Constants.DATE_POSTED_TO));
			whereClauses.put(Constants.DATE_POSTED_TO,
					request.getParameter(Constants.DATE_POSTED_TO));
		}

		// set transaction type
		if (request.getParameter(Constants.TRANSACTION_TYPE) != null
				&& request.getParameter(Constants.TRANSACTION_TYPE).trim()
						.length() > Constants.ZERO) {
			LOGGER.info("transaction type = "
					+ request.getParameter(Constants.TRANSACTION_TYPE));
			if (!request.getParameter(Constants.TRANSACTION_TYPE)
					.equalsIgnoreCase("all")) {
				LOGGER.info("transaction type in");
				whereClauses.put(Constants.TRANSACTION_TYPE,
						request.getParameter(Constants.TRANSACTION_TYPE));
			}
		}

		if (request.getParameter(Constants.ERA_CHECK_NO) != null
				&& request.getParameter(Constants.ERA_CHECK_NO).trim().length() > Constants.ZERO) {
			LOGGER.info("ERA_CHECK_NO = "
					+ request.getParameter(Constants.ERA_CHECK_NO));
			whereClauses.put(Constants.ERA_CHECK_NO,
					request.getParameter(Constants.ERA_CHECK_NO));
		}

		if (request.getParameter(Constants.TICKET_NUMBER_SEARCH) != null
				&& request.getParameter(Constants.TICKET_NUMBER_SEARCH).trim()
						.length() > Constants.ZERO) {
			LOGGER.info(Constants.TICKET_NUMBER_SEARCH + "= "
					+ request.getParameter(Constants.TICKET_NUMBER_SEARCH));

			whereClauses.put(Constants.TICKET_NUMBER_SEARCH,
					request.getParameter(Constants.TICKET_NUMBER_SEARCH));
			/*
			 * if (AkpmsUtil.commanSepString(request
			 * .getParameter(Constants.TICKET_NUMBER_SEARCH)) != null) {
			 * whereClauses.put(Constants.TICKET_NUMBER_IDS, AkpmsUtil
			 * .commanSepString(request
			 * .getParameter(Constants.TICKET_NUMBER_SEARCH))); }
			 */
		}

		if (request.getParameter(Constants.OLD_PRIOR_AR) != null
				&& request.getParameter(Constants.OLD_PRIOR_AR).trim().length() > Constants.ZERO) {
			LOGGER.info(Constants.OLD_PRIOR_AR + "= "
					+ request.getParameter(Constants.OLD_PRIOR_AR));

			whereClauses.put(Constants.OLD_PRIOR_AR,
					request.getParameter(Constants.OLD_PRIOR_AR));
		}

		if (request.getParameter(Constants.SUSPENSE_ACCOUNT) != null
				&& request.getParameter(Constants.SUSPENSE_ACCOUNT).trim()
						.length() > Constants.ZERO) {
			LOGGER.info(Constants.SUSPENSE_ACCOUNT + "= "
					+ request.getParameter(Constants.SUSPENSE_ACCOUNT));

			whereClauses.put(Constants.SUSPENSE_ACCOUNT,
					request.getParameter(Constants.SUSPENSE_ACCOUNT));
		}

		if (request.getParameter(Constants.NDBA) != null
				&& request.getParameter(Constants.NDBA).trim().length() > Constants.ZERO) {
			LOGGER.info(Constants.NDBA + "= "
					+ request.getParameter(Constants.NDBA));

			whereClauses.put(Constants.NDBA,
					request.getParameter(Constants.NDBA));
		}

		return whereClauses;
	}

	/*
	 * private Map<String, String> getOrderClause(WebRequest request) {
	 * Map<String, String> orderClauses = new HashMap<String, String>(); int
	 * page = Constants.ONE; int rp = Constants.ZERO;
	 * 
	 * if (request.getParameter(Constants.RECORD_PRE_PAGE) != null) { rp =
	 * Integer.parseInt(request .getParameter(Constants.RECORD_PRE_PAGE)); try {
	 * orderClauses.put("limit", "" + rp); } catch (Exception e) {
	 * LOGGER.error(Constants.EXCEPTION, e); } }
	 * 
	 * if (request.getParameter(Constants.PAGE) != null) { try { page =
	 * Integer.parseInt(request.getParameter(Constants.PAGE));
	 * orderClauses.put(Constants.OFFSET, "" + ((rp * page) - rp)); } catch
	 * (Exception e) { LOGGER.error(Constants.EXCEPTION, e); } }
	 * 
	 * if (request.getParameter(Constants.SORT_ORDER) != null) {
	 * orderClauses.put(Constants.SORT_BY,
	 * request.getParameter(Constants.SORT_ORDER)); }
	 * 
	 * if (request.getParameter(Constants.SORT_NAME) != null) {
	 * orderClauses.put(Constants.ORDER_BY,
	 * request.getParameter(Constants.SORT_NAME)); }
	 * 
	 * return orderClauses; }
	 */

	/**
	 * function to use print payment batch report page load
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/report", method = RequestMethod.GET)
	public String printBatchReport(Map<String, Object> map) {
		Map<String, String> whereClause = new HashMap<String, String>();

		if (!hasAccessPaymentBatchList()) {
			return Constants.REDIRECT_ERROR_403;
		}

		try {
			whereClause.put(Constants.STATUS, Constants.ACTIVATE + "");
			whereClause.put(Constants.PARENT_ONLY, null);
			map.put(DOCTOR_LIST, doctorDao.findAll(null, whereClause, false));
		} catch (Exception e) {
			LOGGER.info("EXception while getting doctor list");
			LOGGER.info(e.getMessage());
			map.put(DOCTOR_LIST, null);
		}

		try {
			whereClause.clear();
			whereClause.put(Constants.STATUS, Constants.ACTIVATE + "");
			whereClause.put(Constants.PARENT_ID, Constants.PRO_HEALTH_GROUP_ID
					+ "");

			map.put(PRO_HEALTH_DOCTOR_LIST,
					doctorDao.findAll(null, whereClause, false));
		} catch (Exception e) {
			LOGGER.info("EXception while getting pro health doctor list");
			LOGGER.info(e.getMessage());
			map.put(PRO_HEALTH_DOCTOR_LIST, null);
		}

		try {
			whereClause.clear();
			whereClause.put(Constants.DEPARTMENT_WITH_CHILD,
					Constants.PAYMENT_DEPARTMENT_ID);
			whereClause.put(Constants.SELECTED_ROLES_IDS,
					Constants.STANDART_USER_ROLE_ID.toString());
			whereClause.put(Constants.STATUS, Constants.ACTIVATE + "");
			List<User> userList = userDao.findAll(null, whereClause);

			map.put(Constants.USER_LIST, userList);
		} catch (Exception e) {
			LOGGER.info("EXception while getting USER_LIST list");
			LOGGER.info(e.getMessage());
			map.put(Constants.USER_LIST, null);
		}
		map.put(Constants.PAYMENT_BATCH, new PaymentBatch());
		return "paymentbatch/paymentBatchPrint";
	}

	/**
	 * on submit create xml and show on other page (print payment batches)
	 * 
	 * @param paymentBatch
	 * @param result
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/report", method = RequestMethod.POST)
	public void printBatchReport(
			@ModelAttribute(Constants.PAYMENT_BATCH) PaymentBatch paymentBatch,
			BindingResult result, WebRequest request, Map<String, Object> map,
			HttpServletResponse response, HttpSession session) {
		Map<String, String> whereClause = new HashMap<String, String>();
		if (!result.hasErrors()) {
			if (paymentBatch != null) {
				LOGGER.info("have got payment batch object");
				if (paymentBatch.getDoctor() != null
						&& paymentBatch.getDoctor().getId() != null) {
					LOGGER.info(" Selected Doctor ID"
							+ paymentBatch.getDoctor().getId());
					whereClause.put(Constants.DOCTOR_ID, paymentBatch
							.getDoctor().getId().toString());
				}
				if (paymentBatch.getCreatedBy() != null
						&& paymentBatch.getCreatedBy().getId() != null) {
					LOGGER.info(" Selected User ID"
							+ paymentBatch.getCreatedBy().getId());
					whereClause.put(Constants.CREATED_BY, paymentBatch
							.getCreatedBy().getId().toString());
				}
				if (paymentBatch.getEraCheckNo() != null
						&& paymentBatch.getEraCheckNo().trim().length() > Constants.ZERO) {
					LOGGER.info("EraCheckNo: " + paymentBatch.getEraCheckNo());
					whereClause.put(Constants.ERA_CHECK_NO,
							paymentBatch.getEraCheckNo());
				}
				if (request != null) {
					// set pro health doctor ids
					if (request.getParameter(Constants.PH_DOCTOR_IDS) != null
							&& request.getParameter(Constants.PH_DOCTOR_IDS)
									.trim().length() > Constants.ZERO) {
						LOGGER.info("PH_DOCTOR_IDS =  "
								+ request.getParameter(Constants.PH_DOCTOR_IDS));
						whereClause.put(Constants.PH_DOCTOR_IDS,
								request.getParameter(Constants.PH_DOCTOR_IDS));
					}
					if (request.getParameter(Constants.TICKET_NUMBER_TO) != null
							&& request.getParameter(Constants.TICKET_NUMBER_TO)
									.trim().length() > Constants.ZERO) {
						LOGGER.info("ticketNumberTo = "
								+ request.getParameter("ticketNumberTo"));
						whereClause.put(Constants.TICKET_NUMBER_TO,
								request.getParameter("ticketNumberTo"));
					}
					if (request.getParameter(Constants.TICKET_NUMBER_FROM) != null
							&& request
									.getParameter(Constants.TICKET_NUMBER_FROM)
									.trim().length() > Constants.ZERO) {
						LOGGER.info("ticketNumberFrom = "
								+ request.getParameter("ticketNumberFrom"));
						whereClause.put(Constants.TICKET_NUMBER_FROM,
								request.getParameter("ticketNumberFrom"));
					}
					if (request.getParameter(Constants.DATE_CREATED_FROM) != null
							&& request
									.getParameter(Constants.DATE_CREATED_FROM)
									.trim().length() > Constants.ZERO) {
						LOGGER.info(Constants.DATE_CREATED_FROM
								+ " = "
								+ request
										.getParameter(Constants.DATE_CREATED_FROM));
						whereClause.put(Constants.DATE_CREATED_FROM, request
								.getParameter(Constants.DATE_CREATED_FROM));
					}
					if (request.getParameter(Constants.DATE_CREATED_TO) != null
							&& request.getParameter(Constants.DATE_CREATED_TO)
									.trim().length() > Constants.ZERO) {
						LOGGER.info(Constants.DATE_CREATED_TO
								+ " = "
								+ request
										.getParameter(Constants.DATE_CREATED_TO));
						whereClause
								.put(Constants.DATE_CREATED_TO,
										request.getParameter(Constants.DATE_CREATED_TO));
					}
				}

				// here to get result and then convert it to xml
				List<PaymentBatch> rows = null;
				try {
					rows = paymentBatchDao.findAll(null, whereClause);
					if (rows != null && !rows.isEmpty()) {

						for (PaymentBatch pBatch : rows) {
							pBatch.setPosting((pBatch.getDepositAmount() + pBatch
									.getNdba())
									- (pBatch.getManuallyPostedAmt() + pBatch
											.getElecPostedAmt()));
						}
						xmlString = getXmlData(rows);
						// adding XML header
						xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> "
								+ xmlString;
					} else {
						String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> ";
						xmlString = xml + "<empty>No Record found.</empty>";
					}
				} catch (Exception e) {
					LOGGER.error(Constants.EXCEPTION, e);
				}

			} else {
				LOGGER.info("payment batch object is coming null");
				String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> ";
				xmlString = xml + "<empty>No Record found.</empty>";
			}

		} else {
			LOGGER.info("Have found some binding error");
			String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> ";
			xmlString = xml + "<empty>No Record found.</empty>";
		}
		map.put("path", "/paymentbatch/print_report");
		map.put("title", "Payment Batch Report");

		try {
			// Generate PDF
			FopXmlToPdfGenrator.generatePDFReport(response, session,
					"PaymentBatchStylesheet_FO.xsl", xmlString, messageSource
							.getMessage("report.paymentBatch.name", null,
									Locale.ENGLISH));
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
		// return "chargeBatchProcessingPrintReport";
		// return null;
	}

	public String getXmlData(List<PaymentBatch> paymentBatchList) {
		XStream xstream = new XStream(new DomDriver());
		xstream.setMode(XStream.NO_REFERENCES);// XPATH_ABSOLUTE_REFERENCES);
		xstream.processAnnotations(PaymentBatch.class);
		xstream.processAnnotations(User.class);
		xstream.processAnnotations(Doctor.class);
		xstream.registerConverter(new UserXstreamConverter());
		return xstream.toXML(paymentBatchList);
		// return xml;
	}

	/**
	 * show xml for payment batches list
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	/*
	 * @RequestMapping(value = "/print_report", method = RequestMethod.GET,
	 * produces = "application/xml")
	 * 
	 * @ResponseBody public String generateXML(WebRequest request, HttpSession
	 * session) { LOGGER.info("IN /print_report"); if (request != null) { if
	 * (request.getParameter("param") != null &&
	 * request.getParameter("param").equals("xml")) { LOGGER.info("got xml = ");
	 * return xmlString; } else if (request.getParameter("param") != null &&
	 * request.getParameter("param").equals("xsl")) { try { ServletContext
	 * context = session.getServletContext(); String realPath =
	 * context.getRealPath("/resources/xsl"); LOGGER.info("XSL real path = " +
	 * realPath); File systemFile = new File(realPath,
	 * "PaymentBatchStylesheet.xsl"); InputStream is = new
	 * FileInputStream(systemFile); char[] cbuf = new char[is.available()];
	 * BufferedReader bReader = new BufferedReader( new InputStreamReader(is));
	 * bReader.read(cbuf); String xslData = String.valueOf(cbuf);
	 * LOGGER.info("got xsl"); bReader.close(); return xslData; } catch
	 * (Exception e) { LOGGER.error(Constants.EXCEPTION, e); return ""; } } }
	 * return ""; }
	 */

	@RequestMapping(value = "/print", method = RequestMethod.GET)
	public void printReport(HttpServletResponse response, HttpSession session,
			Model model) {

		try {
			String xmlString = null;
			Map<String, String> orderClauses = new HashMap<String, String>();
			orderClauses.put(Constants.SORT_BY, null);
			List<PaymentBatch> rows = paymentBatchDao.findAll(orderClauses,
					printReportCriteria);
			if (rows != null && !rows.isEmpty()) {

				for (PaymentBatch pBatch : rows) {
					pBatch.setPosting((pBatch.getDepositAmount() + pBatch
							.getNdba())
							- (pBatch.getManuallyPostedAmt() + pBatch
									.getElecPostedAmt()));

					// pBatch.setDepositAmount((pBatch.getDepositAmount() +
					// pBatch
					// .getNdba()));

					pBatch.setCtPostedTotal((pBatch.getManuallyPostedAmt() + pBatch
							.getElecPostedAmt()));

				}
				xmlString = getXmlData(rows);
				// adding XML header
				xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
						+ xmlString;
			} else {
				String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> ";
				xmlString = xml + "<empty>No Record found.</empty>";
			}

			// Generate PDF
			FopXmlToPdfGenrator.generatePDFReport(response, session,
					"PaymentBatchStylesheet_FO.xsl", xmlString, messageSource
							.getMessage("report.paymentBatch.name", null,
									Locale.ENGLISH));

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
	}

	@RequestMapping(value = "/printbatcheshtml", method = RequestMethod.GET)
	public String printBatchesHtml(HttpServletResponse response,
			HttpSession session, Model model, Map<String, Object> map) {

		try {
			// String xmlString = null;
			Map<String, String> orderClauses = new HashMap<String, String>();
			orderClauses.put(Constants.SORT_BY, null);
			List<PaymentBatch> rows = paymentBatchDao.findAll(orderClauses,
					printReportCriteria);

			model.addAttribute("PAYMENT_BATCHES", rows);
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}

		map.put("PRINT_REPORT_CRITERIA", printReportCriteria);
		map.put("SHOW_FOOTER", false);
		return "paymentbatch/paymentBatchWebPrint";
	}

	@RequestMapping(value = "/printablepdf", method = RequestMethod.GET)
	public void printReportListView(HttpServletResponse response,
			HttpSession session, Model model) {

		try {
			String xmlString = null;
			Map<String, String> orderClauses = new HashMap<String, String>();
			orderClauses.put(Constants.SORT_BY, null);
			List<PaymentBatch> rows = paymentBatchDao.findAll(orderClauses,
					printReportCriteria);
			if (rows != null && !rows.isEmpty()) {
				double depositTotal = 0;
				double postedTotal = 0;
				double overUnderPosting = 0;
				double agencyMoneyTotal = 0;
				double otherIncomeTotal = 0;
				double oldPriorARTotal = 0;
				double ctNFSSystemRefundTotal = 0;

				for (PaymentBatch pBatch : rows) {
					pBatch.setPosting((pBatch.getDepositAmount() + pBatch
							.getNdba())
							- (pBatch.getManuallyPostedAmt() + pBatch
									.getElecPostedAmt()));
					pBatch.setDepositAmount((pBatch.getDepositAmount() + pBatch
							.getNdba()));
					pBatch.setCtPostedTotal((pBatch.getManuallyPostedAmt() + pBatch
							.getElecPostedAmt()));
					depositTotal += pBatch.getDepositAmount();
					postedTotal += pBatch.getCtPostedTotal();
					agencyMoneyTotal += pBatch.getAgencyMoney();
					otherIncomeTotal += pBatch.getOtherIncome();
					oldPriorARTotal += pBatch.getOldPriorAr();
					ctNFSSystemRefundTotal += pBatch.getNsfSysRef();
				}

				overUnderPosting = depositTotal - postedTotal;
				xmlString = getXmlData(rows);
				// adding XML header
				xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <pb>"
						+ xmlString;

				StringBuffer sb = new StringBuffer();
				sb.append("<depositTotal>")
						.append(AkpmsUtil.formatDoubleNumber(depositTotal))
						.append("</depositTotal>");
				sb.append("<postedTotal>")
						.append(AkpmsUtil.formatDoubleNumber(postedTotal))
						.append("</postedTotal>");
				sb.append("<overUnderPosting>")
						.append(AkpmsUtil.formatDoubleNumber(overUnderPosting))
						.append("</overUnderPosting>");
				sb.append("<agencyMoneyTotal>")
						.append(AkpmsUtil.formatDoubleNumber(agencyMoneyTotal))
						.append("</agencyMoneyTotal>");
				sb.append("<otherIncomeTotal>")
						.append(AkpmsUtil.formatDoubleNumber(otherIncomeTotal))
						.append("</otherIncomeTotal>");
				sb.append("<oldPriorARTotal>")
						.append(AkpmsUtil.formatDoubleNumber(oldPriorARTotal))
						.append("</oldPriorARTotal>");
				sb.append("<ctNFSSystemRefundTotal>")
						.append(AkpmsUtil
								.formatDoubleNumber(ctNFSSystemRefundTotal))
						.append("</ctNFSSystemRefundTotal>");
				sb.append("</pb>");
				xmlString = xmlString + sb.toString();

			} else {
				xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> ";
				xmlString = xmlString + "<empty>No Record found.</empty>";
			}

			// Generate PDF
			FopXmlToPdfGenrator.generatePDFReport(response, session,
					"PaymentBatchStylesheet_ListView_FO.xsl", xmlString,
					messageSource.getMessage("report.paymentBatchList.name",
							null, Locale.ENGLISH));

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
	}

	@RequestMapping(value = "/printablehtml", method = RequestMethod.GET)
	public String printTabularHtml(HttpServletResponse response,
			HttpSession session, Model model, Map<String, Object> map) {

		try {
			Map<String, String> orderClauses = new HashMap<String, String>();
			orderClauses.put(Constants.SORT_BY, null);
			List<PaymentBatch> rows = paymentBatchDao.findAll(orderClauses,
					printReportCriteria);

			model.addAttribute("PAYMENT_BATCHES", rows);

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}

		map.put("PRINT_REPORT_CRITERIA", printReportCriteria);
		return "paymentbatch/paymentBatchListPrint";
	}

	@RequestMapping(value = "/printableweb", method = RequestMethod.GET)
	public String printReportListViewOnWeb(HttpServletResponse response,
			HttpSession session, Model model, Map<String, Object> map) {

		try {
			// String xmlString = null;
			Map<String, String> orderClauses = new HashMap<String, String>();
			// orderClauses.put(Constants.SORT_BY, Constants.SORT_ORDER_DESC);
			orderClauses.put(Constants.ORDER_BY, "doctor.name");
			List<PaymentBatch> rows = paymentBatchDao.findAll(orderClauses,
					printReportCriteria);

			if (rows != null && !rows.isEmpty()) {
				double depositTotal = 0;
				double postedTotal = 0;
				double overUnderPosting = 0;
				double agencyMoneyTotal = 0;
				double otherIncomeTotal = 0;
				double oldPriorARTotal = 0;
				double ctNFSSystemRefundTotal = 0;

				for (PaymentBatch pBatch : rows) {
					pBatch.setPosting((pBatch.getDepositAmount() + pBatch
							.getNdba())
							- (pBatch.getManuallyPostedAmt() + pBatch
									.getElecPostedAmt()));
					depositTotal += pBatch.getDepositAmount();
					postedTotal += pBatch.getCtPostedTotal();
					agencyMoneyTotal += pBatch.getAgencyMoney();
					otherIncomeTotal += pBatch.getOtherIncome();
					oldPriorARTotal += pBatch.getOldPriorAr();
					ctNFSSystemRefundTotal += pBatch.getNsfSysRef();
				}

				overUnderPosting = depositTotal - postedTotal;

				model.addAttribute("PAYMENT_BATCHES", rows);

				model.addAttribute("DEPOSIT_TOTAL",
						AkpmsUtil.formatDoubleNumber(depositTotal));
				model.addAttribute("POSTED_TOTAL",
						AkpmsUtil.formatDoubleNumber(postedTotal));
				model.addAttribute("OVERUNDER_POSTING",
						AkpmsUtil.formatDoubleNumber(overUnderPosting));
				model.addAttribute("AGENCY_MONEY",
						AkpmsUtil.formatDoubleNumber(agencyMoneyTotal));
				model.addAttribute("OTHER_INCOME",
						AkpmsUtil.formatDoubleNumber(otherIncomeTotal));
				model.addAttribute("OLD_PRIOR_AR",
						AkpmsUtil.formatDoubleNumber(oldPriorARTotal));
				model.addAttribute("SYS_REFUND_TOTAL",
						AkpmsUtil.formatDoubleNumber(ctNFSSystemRefundTotal));
			} else {

			}

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}

		map.put("PRINT_REPORT_CRITERIA", printReportCriteria);
		return "paymentbatch/paymentBatchListToPrint";
	}

	@RequestMapping(value = "/getOffset", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<ERAOffsetJsonData> getOffsetByBatchId(WebRequest request) {
		LOGGER.info(":: [getOffsetByBatchId] method:: ");
		if (request != null && request.getParameter(Constants.BATCH_ID) != null) {
			LOGGER.info("batchId = "
					+ Long.valueOf(request.getParameter(Constants.BATCH_ID)));
			try {
				List<PaymentProductivityOffset> offsetList = offsetDao
						.getOffsetByBatchId(Long.valueOf(request
								.getParameter(Constants.BATCH_ID)));
				if (offsetList != null && !offsetList.isEmpty()) {
					return getOffsetJsonData(offsetList);
				} else {
					return null;
				}
			} catch (Exception e) {
				LOGGER.error(Constants.EXCEPTION, e);
				return null;
			}
		}
		return null;
	}

	@RequestMapping(value = "/getOffsetPosting", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<ERAOffsetJsonData> getPostedOffsetByBatchId(WebRequest request) {
		LOGGER.info(":: [getPostedOffsetByBatchId] method:: ");
		if (request != null && request.getParameter(Constants.BATCH_ID) != null) {
			LOGGER.info("batchId = "
					+ Long.valueOf(request.getParameter(Constants.BATCH_ID)));
			try {
				List<PaymentPostingByOffSetManager> offsetList = paymentOffsetManagerDao
						.getPostedOffsetByBatchId(Long.valueOf(request
								.getParameter(Constants.BATCH_ID)));
				if (offsetList != null && !offsetList.isEmpty()) {
					return getOffsetPostingJsonData(offsetList);
				} else {
					return null;
				}
			} catch (Exception e) {
				LOGGER.error(Constants.EXCEPTION, e);
				return null;
			}
		}
		return null;
	}

	private List<ERAOffsetJsonData> getOffsetJsonData(
			List<PaymentProductivityOffset> offsetList) {

		List<ERAOffsetJsonData> offsetPostingJsonDataList = new ArrayList<ERAOffsetJsonData>();
		if (offsetList != null) {
			for (PaymentProductivityOffset paymentProductivityOffset : offsetList) {
				ERAOffsetJsonData eraoffsetJsonData = new ERAOffsetJsonData();
				eraoffsetJsonData.setAccountNumber(paymentProductivityOffset
						.getAccountNumber());
				eraoffsetJsonData.setChkNumber(paymentProductivityOffset
						.getChkNumber());
				eraoffsetJsonData.setChkDate(new SimpleDateFormat(
						Constants.DATE_FORMAT).format(paymentProductivityOffset
						.getChkDate()));
				eraoffsetJsonData.setPatientName(paymentProductivityOffset
						.getPatientName());
				offsetPostingJsonDataList.add(eraoffsetJsonData);
			}
		}
		return offsetPostingJsonDataList;
	}

	private List<ERAOffsetJsonData> getOffsetPostingJsonData(
			List<PaymentPostingByOffSetManager> offsetPostingList) {

		List<ERAOffsetJsonData> offsetPostingJsonDataList = new ArrayList<ERAOffsetJsonData>();
		if (offsetPostingList != null) {
			for (PaymentPostingByOffSetManager paymentProductivityOffset : offsetPostingList) {
				ERAOffsetJsonData eraoffsetJsonData = new ERAOffsetJsonData();
				eraoffsetJsonData.setAccountNumber(paymentProductivityOffset
						.getAccountNumber());
				eraoffsetJsonData.setChkNumber(paymentProductivityOffset
						.getCheckNumber());
				eraoffsetJsonData.setChkDate(new SimpleDateFormat(
						Constants.DATE_FORMAT).format(paymentProductivityOffset
						.getDateOfCheck()));
				eraoffsetJsonData.setPatientName(paymentProductivityOffset
						.getPatientName());
				offsetPostingJsonDataList.add(eraoffsetJsonData);
			}
		}
		return offsetPostingJsonDataList;
	}

	/**
	 * function to check that user has access for payment batch or not access
	 * for offset manager + standard user access for standuser + payement
	 * departmnet + permisson for create and revise batch
	 * 
	 * @return
	 */
	private boolean hasAccessPaymentBatch(WebRequest request) {
		boolean hasAccess = false;

		// standard user can view batch details
		if (AkpmsUtil.getLoggedInUser().getRole().getId() == Constants.STANDART_USER_ROLE_ID
				&& (request.getParameter("viewBatch") != null && request
						.getParameter("viewBatch").trim().equals("1"))) {
			hasAccess = true;
		} else {
			/* standard user + offset manager can add payment batch */
			if (AkpmsUtil.checkPermission(Constants.PERMISSION_OFFSET_MANAGER)
					&& (AkpmsUtil.getLoggedInUser().getRole().getId() == Constants.STANDART_USER_ROLE_ID)) {
				hasAccess = true;
			}
			/*
			 * payment department + standard user + permission to revise and
			 * create batch can add payment batch
			 */
			else if (AkpmsUtil.checkDepartment(Long
					.valueOf(Constants.PAYMENT_DEPARTMENT_ID))
					&& (AkpmsUtil.getLoggedInUser().getRole().getId() == Constants.STANDART_USER_ROLE_ID)
					&& AkpmsUtil
							.checkPermission(Constants.PERMISSION_REVISE_CREATE_BATCH)) {
				hasAccess = true;
			}
		}

		return hasAccess;
	}

	private boolean hasAccessPaymentBatchList() {
		boolean hasAccess = false;
		/* standard user + offset manager can add payment batch */
		if (AkpmsUtil.checkPermission(Constants.PERMISSION_OFFSET_MANAGER)
				&& (AkpmsUtil.getLoggedInUser().getRole().getId() == Constants.STANDART_USER_ROLE_ID)) {
			hasAccess = true;
		}
		/*
		 * payment department + standard user + permission to revise and create
		 * batch can add payment batch
		 */
		else if (AkpmsUtil.checkDepartment(Long
				.valueOf(Constants.PAYMENT_DEPARTMENT_ID))
				&& (AkpmsUtil.getLoggedInUser().getRole().getId() == Constants.STANDART_USER_ROLE_ID)) {
			hasAccess = true;
		}

		return hasAccess;
	}
}
