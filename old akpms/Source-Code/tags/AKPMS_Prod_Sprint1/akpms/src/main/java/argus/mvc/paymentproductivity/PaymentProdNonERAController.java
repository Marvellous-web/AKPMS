/**
 *
 */
package argus.mvc.paymentproductivity;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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

import argus.domain.Doctor;
import argus.domain.Insurance;
import argus.domain.PaymentBatch;
import argus.domain.paymentproductivity.PaymentProdQueryToTL;
import argus.domain.paymentproductivity.PaymentProductivity;
import argus.exception.ArgusException;
import argus.mvc.paymentproductivity.helper.PaymentProductivityHelper;
import argus.mvc.productivity.helper.ArProductivityHelper;
import argus.repo.doctor.DoctorDao;
import argus.repo.insurance.InsuranceDao;
import argus.repo.paymentbatch.PaymentBatchDao;
import argus.repo.paymentproductivity.PaymentProdQueryToTLDao;
import argus.repo.paymentproductivity.PaymentProductivityDao;
import argus.repo.paymentproductivity.helper.PaymentProductivityDaoHelper;
import argus.repo.user.UserDao;
import argus.util.AkpmsUtil;
import argus.util.Constants;
import argus.util.JsonDataWrapper;
import argus.util.PaymentProductivityJsonData;
import argus.validator.PaymentProductivityNonEraValidator;

/**
 * @author rajiv.k
 *
 */
@Controller
@RequestMapping(value = "/paymentproductivitynonera")
@SessionAttributes({ Constants.PAYMENTPRODUCTIVITY })
public class PaymentProdNonERAController {
	private static final Log LOGGER = LogFactory
			.getLog(PaymentProdNonERAController.class);

	@Autowired
	private PaymentProductivityDao paymentProductivityDao;
	@Autowired
	private PaymentProductivityNonEraValidator paymentProdNonEraValidator;
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private PaymentBatchDao batchDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private PaymentProdQueryToTLDao prodQueryToTLDao;

	@Autowired
	private DoctorDao doctorDao;

	@Autowired
	private InsuranceDao insuranceDao;

	private static final int ONE = 1;

	private static final int TWO = 2;

	private static final int THREE = 3;

	private static final String STRING_ONE = "1";

	private static final String STRING_TWO = "2";

	private static final String STRING_THREE = "3";

	@InitBinder
	protected void initBinder(WebDataBinder binder) throws ArgusException {
		LOGGER.info("in [initBinder] method : ");
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

	/**
	 * This function just load jsp "Payment Productivity List", the list will
	 * load using flexigrid list
	 *
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String productivityList(Map<String, Object> map, WebRequest request,
			HttpSession session) {
		LOGGER.info("IN PaymentProdNonERAController.java-productivityList METHOD - START");
		String success = (String) session
				.getAttribute(Constants.SUCCESS_UPDATE);
		if (success != null) {
			map.put("success", success);
		}

		session.removeAttribute(Constants.SUCCESS_UPDATE);

		if (request.getParameter(Constants.WORKFLOW_ID) != null) {
			LOGGER.info("WORKFLOW_ID = "
					+ request.getParameter(Constants.WORKFLOW_ID));
			map.put(Constants.WORKFLOW_ID,
					request.getParameter(Constants.WORKFLOW_ID));
		}

		map.put("typeList", this.getProductivityTypes());

		if (request.getParameter(Constants.PROD_TYPE) != null) {
			LOGGER.info("prod type = "
					+ request.getParameter(Constants.PROD_TYPE));
			map.put(Constants.PROD_TYPE,
					request.getParameter(Constants.PROD_TYPE));
		}

		try {
			map.put(Constants.POSTED_BY, userDao.getUserByDept());
		} catch (ArgusException ar) {
			LOGGER.error(Constants.EXCEPTION, ar);
		}

		LOGGER.info("IN PaymentProdNonERAController.java-productivityList METHOD - END");
		return "paymentProductivityList";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String productivityNonEraLoad(Model model, WebRequest request,
			Map<String, Object> map,
			@RequestParam(required = false) String successUpdate,
			HttpSession session) {
		LOGGER.info("in [productivityNonEraLoad] method");

		if (null != request & null != request.getParameter(Constants.ID)) {
			PaymentProductivity payProductivity = null;
			try {
				payProductivity = paymentProductivityDao.findById(Long
						.parseLong(request.getParameter(Constants.ID)));
				if (payProductivity == null) {
					session.setAttribute(Constants.SUCCESS_UPDATE,
							"record.notFound");
					return "redirect:/paymentproductivitynonera";
				}
				model.addAttribute("totalProdOffsets", paymentProductivityDao
						.getTotalProductivityOffsetRecord(payProductivity
								.getPaymentBatch().getId()));

			} catch (Exception e) {
				LOGGER.error(e.toString());
			}
			if (payProductivity.getPaymentProdType() == Constants.CAP_WORK_FLOW) {
				map.put(Constants.PROD_TYPE, Constants.CAP);
			} else {
				map.put(Constants.PROD_TYPE, Constants.NON_ERA);
			}
			model.addAttribute(Constants.PAYMENTPRODUCTIVITY, payProductivity);
			model.addAttribute(Constants.MODE, Constants.EDIT);
			map.put(Constants.OPERATION_TYPE, Constants.EDIT);
			map.put(Constants.BUTTON_NAME, Constants.UPDATE);
			map.put(Constants.GET_BUTTON, Constants.GET_DETAILS);
			map.put(Constants.HIDE, Constants.HIDE);
			map.put(Constants.CURRENT_DATE, new SimpleDateFormat(
					Constants.DATE_FORMAT).format(new Date()));
		} else {
			PaymentProductivity payProductivity = new PaymentProductivity();
			int prodType = Constants.ZERO;
			if (request.getParameter(Constants.PROD_TYPE) != null) {
				prodType = Integer.valueOf(request
						.getParameter(Constants.PROD_TYPE));
			}
			if (prodType == Constants.CAP_WORK_FLOW) {
				payProductivity.setPaymentProdType(Constants.CAP_WORK_FLOW);
				map.put(Constants.PROD_TYPE, Constants.CAP);
			} else if (prodType == Constants.NON_ERA_WORK_FLOW) {
				payProductivity.setPaymentProdType(Constants.NON_ERA_WORK_FLOW);
				map.put(Constants.PROD_TYPE, Constants.NON_ERA);
			} else {
				return "redirect:/paymentproductivity";
			}

			model.addAttribute(Constants.PAYMENTPRODUCTIVITY, payProductivity);
			model.addAttribute(Constants.MODE, Constants.ADD);
			map.put(Constants.OPERATION_TYPE, Constants.ADD);
			map.put(Constants.BUTTON_NAME, Constants.SAVE);
			map.put(Constants.GET_BUTTON, Constants.GET_DETAILS);
			map.put(Constants.CURRENT_DATE, new SimpleDateFormat(
					Constants.DATE_FORMAT).format(new Date()));
		}

		return "paymentProductivity/paymentProdNonERA";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String savePaymentProductivity(
			@Valid @ModelAttribute(Constants.PAYMENTPRODUCTIVITY) PaymentProductivity payProductivity,
			BindingResult result, Model model, WebRequest request,
			Map<String, Object> map, HttpSession session) {
		LOGGER.info("in [savePaymentProductivity] method : ");
		PaymentProdQueryToTL prodQueryToTL = new PaymentProdQueryToTL();

		PaymentBatch paymentBatch = new PaymentBatch();
		paymentProdNonEraValidator.validate(payProductivity, result);
		if (!result.hasErrors()) {

			if (payProductivity.getWorkFlowId() == Constants.QUERY_TO_TL_WORK_FLOW
					|| payProductivity.isOffset()) {
				map.put(Constants.TICKET_NUMBER, payProductivity
						.getPaymentBatch().getId());
				map.put(Constants.INSURANCE_NAME, payProductivity
						.getPaymentBatch().getInsurance().getName());

			}
			Object[] ticketNumber = new Object[1];
			if (payProductivity.getId() != null) {
				try {
					paymentProductivityDao
							.updatePaymentProductivity(payProductivity);
					paymentBatch = batchDao.findById(payProductivity
							.getPaymentBatch().getId(), false);
					paymentBatch.setPostedBy(AkpmsUtil.getLoggedInUser());
					Calendar currenttime = Calendar.getInstance();
					paymentBatch.setPostedOn(new Date((currenttime.getTime())
							.getTime()));
					paymentBatch.setManuallyPostedAmt(payProductivity
							.getPaymentBatch().getManuallyPostedAmt());
					paymentBatch.setElecPostedAmt(payProductivity
							.getPaymentBatch().getElecPostedAmt());
					paymentBatch.setAgencyMoney(AkpmsUtil
							.amountCheck(payProductivity.getPaymentBatch()
									.getAgencyMoney()));
					paymentBatch.setSuspenseAccount(AkpmsUtil
							.amountCheck(payProductivity.getPaymentBatch()
									.getSuspenseAccount()));
					paymentBatch.setOldPriorAr(AkpmsUtil
							.amountCheck(payProductivity.getPaymentBatch()
									.getOldPriorAr()));
					paymentBatch.setOtherIncome(AkpmsUtil
							.amountCheck(payProductivity.getPaymentBatch()
									.getOtherIncome()));
					paymentBatch.setDatePosted(payProductivity
							.getPaymentBatch().getDatePosted());
					batchDao.updatePaymentBatch(paymentBatch);
					ticketNumber[0] = paymentBatch.getId();
					session.setAttribute(
							Constants.SUCCESS_UPDATE,
							messageSource
									.getMessage(
											"paymentBatchProductivity.updatedSuccessfully",
											ticketNumber, Locale.ENGLISH)
									.trim());
				} catch (ArgusException e) {
					LOGGER.error(Constants.EXCEPTION, e);
				}

				if (payProductivity.getWorkFlowId() == Constants.QUERY_TO_TL_WORK_FLOW) {
					try {
						prodQueryToTL = prodQueryToTLDao
								.findQueryToTLByProdId(payProductivity.getId());
						return PaymentProductivityHelper.getWorkFlow(
								payProductivity.getWorkFlowId(),
								prodQueryToTL.getId(), true);
					} catch (ArgusException e) {
						LOGGER.error(Constants.EXCEPTION
								+ ": NO RESULT EXCEPTION", e);
					} catch (NoResultException e) {
						LOGGER.error(Constants.EXCEPTION
								+ ": NO RESULT EXCEPTION", e);
						return PaymentProductivityHelper.getWorkFlow(
								payProductivity.getWorkFlowId(),
								Constants.ZERO, false);
					}
				}

			} else {
				try {
					paymentProductivityDao
							.addPaymentProductivity(payProductivity);
					paymentBatch = batchDao.findById(payProductivity
							.getPaymentBatch().getId(), false);
					paymentBatch.setPostedBy(AkpmsUtil.getLoggedInUser());
					Calendar currenttime = Calendar.getInstance();
					paymentBatch.setPostedOn(new Date((currenttime.getTime())
							.getTime()));
					paymentBatch.setManuallyPostedAmt(payProductivity
							.getPaymentBatch().getManuallyPostedAmt());
					paymentBatch.setElecPostedAmt(payProductivity
							.getPaymentBatch().getElecPostedAmt());
					paymentBatch.setAgencyMoney(AkpmsUtil
							.amountCheck(payProductivity.getPaymentBatch()
									.getAgencyMoney()));
					paymentBatch.setSuspenseAccount(AkpmsUtil
							.amountCheck(payProductivity.getPaymentBatch()
									.getSuspenseAccount()));
					paymentBatch.setOldPriorAr(AkpmsUtil
							.amountCheck(payProductivity.getPaymentBatch()
									.getOldPriorAr()));
					paymentBatch.setOtherIncome(AkpmsUtil
							.amountCheck(payProductivity.getPaymentBatch()
									.getOtherIncome()));
					paymentBatch.setDatePosted(payProductivity
							.getPaymentBatch().getDatePosted());
					batchDao.updatePaymentBatch(paymentBatch);
					ticketNumber[0] = paymentBatch.getId();
					session.setAttribute(
							Constants.SUCCESS_UPDATE,
							messageSource
									.getMessage(
											"paymentBatchProductivity.addedSuccessfully",
											ticketNumber, Locale.ENGLISH)
									.trim());
					if (payProductivity.isOffset()) {
						payProductivity
								.setWorkFlowId(Constants.OFFSET_WORK_FLOW);

					}
				} catch (ArgusException e) {
					LOGGER.info(Constants.EXCEPTION, e);
				}
			}

			return PaymentProductivityHelper.getWorkFlow(
					payProductivity.getWorkFlowId(), Constants.ZERO, false);
		} else {
			if (payProductivity.getPaymentProdType() == Constants.CAP_WORK_FLOW) {
				map.put(Constants.PROD_TYPE, Constants.CAP);
			} else {
				map.put(Constants.PROD_TYPE, Constants.NON_ERA);
			}
			if (payProductivity != null & payProductivity.getId() != null) {
				map.put(Constants.OPERATION_TYPE, Constants.EDIT);
				map.put(Constants.BUTTON_NAME, Constants.UPDATE);
				map.put(Constants.GET_BUTTON, Constants.GET_DETAILS);
				map.put(Constants.HIDE, Constants.HIDE);
				map.put(Constants.CURRENT_DATE, new SimpleDateFormat(
						Constants.DATE_FORMAT).format(new Date()));
				try {
					model.addAttribute(
							"totalProdOffsets",
							paymentProductivityDao
									.getTotalProductivityOffsetRecord(payProductivity
											.getPaymentBatch().getId()));
				} catch (Exception e) {
					LOGGER.error(Constants.EXCEPTION, e);
				}
			} else {
				map.put(Constants.OPERATION_TYPE, Constants.ADD);
				map.put(Constants.BUTTON_NAME, Constants.SAVE);
				map.put(Constants.GET_BUTTON, Constants.GET_DETAILS);
				map.put(Constants.CURRENT_DATE, new SimpleDateFormat(
						Constants.DATE_FORMAT).format(new Date()));
			}

			return "paymentProductivity/paymentProdNonERA";
		}
	}

	@RequestMapping(value = "getticketdetail/json", method = RequestMethod.POST)
	@ResponseBody
	public Object[] getDetail(
			@ModelAttribute(Constants.PAYMENTPRODUCTIVITY) PaymentProductivity payProductivity,
			@RequestParam(value = "batchId", required = false) String batchId,
			WebRequest request) {
		Object[] obj = null;
		PaymentBatch paymentBatch = new PaymentBatch();
		obj = PaymentProductivityHelper.getPaymentBatch(obj, batchId,
				paymentBatch, batchDao, messageSource);
		if (obj[Constants.ZERO] != Constants.ERR) {
			payProductivity.setPaymentBatch((PaymentBatch) obj[ONE]);
			obj[ONE] = null;
		}
		return obj;

	}

	/**
	 * function to use for flexigrid list
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/json", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public JsonDataWrapper<PaymentProductivityJsonData> listAllPayment(
			WebRequest request) {
		LOGGER.info("payment productivity::in json method");

		int page = ONE; /* default 1 for page number in json wrapper */
		int rp = Constants.ZERO;

		Map<String, String> orderClauses = new HashMap<String, String>();
		Map<String, String> whereClauses = new HashMap<String, String>();

		if (request != null) {

			if (request.getParameter(Constants.RECORD_PRE_PAGE) != null) {
				rp = Integer.parseInt(request
						.getParameter(Constants.RECORD_PRE_PAGE));
				try {
					orderClauses.put(Constants.LIMIT, "" + rp);
				} catch (Exception e) {
					LOGGER.debug("rp[Record pre Page] not coming or not an integer in request ");
				}
			}

			if (request.getParameter(Constants.PAGE) != null) {
				try {
					page = Integer.parseInt(request
							.getParameter(Constants.PAGE));
					orderClauses.put(Constants.OFFSET, "" + ((rp * page) - rp));
				} catch (Exception e) {
					LOGGER.debug("Exception during parsing");
				}
			}

			if (request.getParameter(Constants.SORT_ORDER) != null) {
				orderClauses.put(Constants.SORT_BY,
						request.getParameter(Constants.SORT_ORDER));
			}

			if (request.getParameter(Constants.SORT_NAME) != null) {
				orderClauses.put(Constants.ORDER_BY,
						request.getParameter(Constants.SORT_NAME));
			}

			if (request.getParameter(Constants.QTYPE) != null
					&& request.getParameter(Constants.QUERY) != null
					&& !request.getParameter(Constants.QUERY).isEmpty()) {
				whereClauses.put(request.getParameter(Constants.QTYPE),
						request.getParameter(Constants.QUERY));
			}
			if (request.getParameter(Constants.PROD_TYPE) != null
					& request.getParameter(Constants.PROD_TYPE).trim().length() > Constants.ZERO) {
				String prodType = request.getParameter(Constants.PROD_TYPE);
				whereClauses.put(Constants.PROD_TYPE, prodType);
			}
			if (request.getParameter(Constants.POSTED_BY_ID) != null
					& request.getParameter(Constants.POSTED_BY_ID).trim()
							.length() > Constants.ZERO) {
				String postedBy = request.getParameter(Constants.POSTED_BY_ID);
				whereClauses.put(Constants.POSTED_BY_ID, postedBy);
			}
			// set posted from date
			if (request.getParameter(Constants.DATE_POSTED_FROM) != null
					&& request.getParameter(Constants.DATE_POSTED_FROM).trim()
							.length() > Constants.ZERO) {
				LOGGER.info("DATE_POSTED_FROM= "
						+ request.getParameter(Constants.DATE_POSTED_FROM));
				whereClauses.put(Constants.DATE_POSTED_FROM,
						request.getParameter(Constants.DATE_POSTED_FROM));
			}

			// set posted to date
			if (request.getParameter(Constants.DATE_POSTED_TO) != null
					&& request.getParameter(Constants.DATE_POSTED_TO).trim()
							.length() > Constants.ZERO) {
				LOGGER.info("DATE_POSTED_TO= "
						+ request.getParameter(Constants.DATE_POSTED_TO));
				whereClauses.put(Constants.DATE_POSTED_TO,
						request.getParameter(Constants.DATE_POSTED_TO));
			}
			if (request.getParameter(Constants.TICKET_NUMBER) != null
					&& request.getParameter(Constants.TICKET_NUMBER).trim()
							.length() > Constants.ZERO) {
				String ticketNumber = request
						.getParameter(Constants.TICKET_NUMBER);

				whereClauses.put(Constants.TICKET_NUMBER, ticketNumber);
			}

			if (request.getParameter(Constants.WORKFLOW_ID) != null
					&& request.getParameter(Constants.WORKFLOW_ID).trim()
							.length() > Constants.ZERO) {
				LOGGER.info(" WORKFLOW_ID = "
						+ request.getParameter(Constants.WORKFLOW_ID));
				String workflowId = request.getParameter(Constants.WORKFLOW_ID);
				whereClauses.put(Constants.WORKFLOW_ID, workflowId);
			}
		} else {
			LOGGER.info("request object is coming null");
		}
		int totalRows = Constants.ZERO;
		try {
			totalRows = paymentProductivityDao.totalRecord(whereClauses);
			if (totalRows > Constants.ZERO) {
				List<PaymentProductivity> rows = paymentProductivityDao
						.findAll(orderClauses, whereClauses, true);
				List<PaymentProductivityJsonData> djd = getJsonData(rows);
				JsonDataWrapper<PaymentProductivityJsonData> jdw = new JsonDataWrapper<PaymentProductivityJsonData>(
						page, totalRows, djd);
				return jdw;
			}
		} catch (Exception e) {
			LOGGER.error(e.toString());
		}

		return null;
	}

	/**
	 * this method set value in PaymentProductivityJsonData list
	 *
	 * @param rows
	 * @return
	 */
	private List<PaymentProductivityJsonData> getJsonData(
			List<PaymentProductivity> rows) {
		List<PaymentProductivityJsonData> deptJsonData = new ArrayList<PaymentProductivityJsonData>();

		if (rows != null && rows.size() > Constants.ZERO) {

			for (PaymentProductivity paymentType : rows) {
				PaymentProductivityJsonData djd = new PaymentProductivityJsonData();
				djd.setId(paymentType.getId());
				djd.setAgencyIncome(AkpmsUtil.amountCheck(paymentType
						.getPaymentBatch().getAgencyMoney()));
				djd.setChkNumber(paymentType.getChkNumber());
				djd.setElecPostedAmt(paymentType.getPaymentBatch()
						.getElecPostedAmt());
				djd.setElecTransaction(paymentType.getElecTransaction());
				djd.setManuallyPostedAmt(paymentType.getPaymentBatch()
						.getManuallyPostedAmt());
				djd.setManualTransaction(paymentType.getManualTransaction());
				djd.setOldPriorAr(AkpmsUtil.amountCheck(paymentType
						.getPaymentBatch().getOldPriorAr()));
				djd.setOtherIncome(AkpmsUtil.amountCheck(paymentType
						.getPaymentBatch().getOtherIncome()));
				djd.setWorkFlowName(paymentType.getWorkFlowName());
				djd.setPaymentBatch(paymentType.getPaymentBatch().getId());
				djd.setDatePosted(AkpmsUtil.akpmsDateFormat(paymentType
						.getPaymentBatch().getDatePosted(),
						Constants.DATE_FORMAT));
				if (paymentType.getCreatedOn() != null) {
					djd.setReceivedDate(AkpmsUtil.akpmsDateFormat(
							paymentType.getCreatedOn(), Constants.DATE_FORMAT));

				}
				djd.setRemark(paymentType.getRemark());
				if (paymentType.getPaymentBatch().getDoctor() != null) {
					djd.setDoctorName(paymentType.getPaymentBatch().getDoctor()
							.getName());
				}
				if (paymentType.getPaymentBatch().getInsurance() != null) {
					djd.setInsurance(paymentType.getPaymentBatch()
							.getInsurance().getName());
				}
				djd.setSuspense(AkpmsUtil.amountCheck(paymentType
						.getPaymentBatch().getSuspenseAccount()));
				djd.setTime(paymentType.getTime());
				djd.setDepositAmt(AkpmsUtil.amountCheck(paymentType
						.getPaymentBatch().getDepositAmount()));
				try {
					djd.setPaymentProdType(PaymentProductivityDaoHelper
							.getProductivityType(paymentType
									.getPaymentProdType()));
				} catch (ArgusException ar) {
					LOGGER.error(Constants.EXCEPTION, ar);
				}
				djd.setPostedBy(paymentType.getCreatedBy().getFirstName() + " "
						+ paymentType.getCreatedBy().getLastName());
				if (paymentType.getPaymentProdQueryToTL() != null
						&& paymentType.getPaymentProdQueryToTL().getId() != null) {
					djd.setQueryToTL(true);
				}
				deptJsonData.add(djd);
			}

		}

		return deptJsonData;
	}

	/**
	 * function to set productivity as deleted (is_deleted =1)
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteProductivity(WebRequest request) {
		LOGGER.info("in Productivity method");
		LOGGER.info(request.getParameter("items"));

		boolean response = false;

		try {

			int updateCount = paymentProductivityDao
					.deletePaymentProductivity(new Long(request
							.getParameter("item")));
			if (updateCount > Constants.ZERO) {
				response = true;
			}

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
		return response;
	}

	private Map<Integer, String> getProductivityTypes() {
		Map<Integer, String> typeList = new HashMap<Integer, String>();
		typeList.put(ONE,
				messageSource.getMessage(STRING_ONE, null, Locale.ENGLISH));
		typeList.put(TWO,
				messageSource.getMessage(STRING_TWO, null, Locale.ENGLISH));
		typeList.put(THREE,
				messageSource.getMessage(STRING_THREE, null, Locale.ENGLISH));
		return typeList;
	}

	@RequestMapping(value = "/report", method = RequestMethod.GET)
	public String nonEraProductivityReportLoad(WebRequest request, Model model,
			Map<String, String> map) {
		try {
			List<Doctor> doctorList = doctorDao.findAll(null, null, false);

			model.addAttribute("paymentProductivity", new PaymentProductivity());
			model.addAttribute("doctorList", doctorList);

			Map<String, String> whereClause = new HashMap<String, String>();
			whereClause.put(Constants.STATUS, Constants.ACTIVATE + "");
			List<Insurance> insuranceList = insuranceDao.findAll(null,
					whereClause);
			model.addAttribute("insuranceList", insuranceList);

			try {
				model.addAttribute("postedBy", userDao.getUserByDept());
			} catch (ArgusException ar) {
				LOGGER.error(Constants.EXCEPTION, ar);
			}

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
		return "nonEraProductivityReport";
	}

	/**
	 * function to use for flexigrid list
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/reportJson", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	JsonDataWrapper<PaymentProductivityJsonData> listAllProductivityReport(
			WebRequest request) {
		LOGGER.info("payment productivity::in Report json method");

		int page = ONE; /* default 1 for page number in json wrapper */
		int rp = Constants.ZERO;

		Map<String, String> orderClauses = new HashMap<String, String>();
		Map<String, String> whereClauses = new HashMap<String, String>();

		if (request != null) {

			if (request.getParameter("rp") != null) {
				rp = Integer.parseInt(request.getParameter("rp"));
				try {
					orderClauses.put("limit", "" + rp);
				} catch (Exception e) {
					LOGGER.debug("rp[Record pre Page] not coming or not an integer in request ");
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
			if (request.getParameter(Constants.DOCTOR) != null
					& request.getParameter(Constants.DOCTOR).trim().length() > Constants.ZERO) {
				String doctor = request.getParameter(Constants.DOCTOR);
				whereClauses.put(Constants.DOCTOR, doctor);
			}
			if (request.getParameter(Constants.INSURANCE) != null
					& request.getParameter(Constants.INSURANCE).trim().length() > Constants.ZERO) {
				String insurance = request.getParameter(Constants.INSURANCE);
				whereClauses.put(Constants.INSURANCE, insurance);
			}
			if (request.getParameter(Constants.POSTED_BY_ID) != null
					& request.getParameter(Constants.POSTED_BY_ID).trim()
							.length() > Constants.ZERO) {
				String postedBy = request.getParameter(Constants.POSTED_BY_ID);
				whereClauses.put(Constants.POSTED_BY_ID, postedBy);
			}

			/* set posted from date */
			if (request.getParameter(Constants.DATE_POSTED_FROM) != null
					&& request.getParameter(Constants.DATE_POSTED_FROM).trim()
							.length() > Constants.ZERO) {
				LOGGER.info("DATE_POSTED_FROM= "
						+ request.getParameter(Constants.DATE_POSTED_FROM));
				whereClauses.put(Constants.DATE_POSTED_FROM,
						request.getParameter(Constants.DATE_POSTED_FROM));
			}

			/* set posted to date */
			if (request.getParameter(Constants.DATE_POSTED_TO) != null
					&& request.getParameter(Constants.DATE_POSTED_TO).trim()
							.length() > Constants.ZERO) {
				LOGGER.info("DATE_POSTED_TO= "
						+ request.getParameter(Constants.DATE_POSTED_TO));
				whereClauses.put(Constants.DATE_POSTED_TO,
						request.getParameter(Constants.DATE_POSTED_TO));
			}

			/* set received from date */
			if (request.getParameter(Constants.DATE_RECEIVED_FROM) != null
					&& request.getParameter(Constants.DATE_RECEIVED_FROM)
							.trim().length() > Constants.ZERO) {
				LOGGER.info("DATE_RECEIVED_FROM= "
						+ request.getParameter(Constants.DATE_RECEIVED_FROM));
				whereClauses.put(Constants.DATE_RECEIVED_FROM,
						request.getParameter(Constants.DATE_RECEIVED_FROM));
			}

			/* set received to date */
			if (request.getParameter(Constants.DATE_RECEIVED_TO) != null
					&& request.getParameter(Constants.DATE_RECEIVED_TO).trim()
							.length() > Constants.ZERO) {
				LOGGER.info("DATE_RECEIVED_TO= "
						+ request.getParameter(Constants.DATE_RECEIVED_TO));
				whereClauses.put(Constants.DATE_RECEIVED_TO,
						request.getParameter(Constants.DATE_RECEIVED_TO));
			}
			if (request.getParameter(Constants.TICKET_NUMBER) != null
					&& request.getParameter(Constants.TICKET_NUMBER).trim()
							.length() > Constants.ZERO) {
				String ticketNumber = request
						.getParameter(Constants.TICKET_NUMBER);

				whereClauses.put(Constants.TICKET_NUMBER, ticketNumber);
			}
			whereClauses.put(Constants.PROD_TYPE, STRING_TWO);
		} else {
			LOGGER.info("request object is coming null");
		}
		int totalRows = Constants.ZERO;
		try {
			totalRows = paymentProductivityDao.totalRecord(whereClauses);
			if (totalRows > Constants.ZERO) {
				List<PaymentProductivity> rows = paymentProductivityDao
						.findAllForReport(orderClauses, whereClauses);
				List<PaymentProductivityJsonData> djd = getJsonDataForNonEraReport(rows);
				JsonDataWrapper<PaymentProductivityJsonData> jdw = new JsonDataWrapper<PaymentProductivityJsonData>(
						page, totalRows, djd);
				return jdw;
			}
		} catch (Exception e) {
			LOGGER.error(e.toString());
		}

		return null;
	}

	@RequestMapping(value = "/capreport", method = RequestMethod.GET)
	public String capProductivityReportLoad(WebRequest request, Model model,
			Map<String, String> map) {
		try {
			List<Doctor> doctorList = doctorDao.findAll(null, null, false);

			model.addAttribute("paymentProductivity", new PaymentProductivity());
			model.addAttribute("doctorList", doctorList);

			Map<String, String> whereClause = new HashMap<String, String>();
			whereClause.put(Constants.STATUS, Constants.ACTIVATE + "");
			List<Insurance> insuranceList = insuranceDao.findAll(null,
					whereClause);
			model.addAttribute("insuranceList", insuranceList);
			try {
				model.addAttribute(Constants.POSTED_BY, userDao.getUserByDept());
			} catch (ArgusException ar) {
				LOGGER.error(Constants.EXCEPTION, ar);
			}

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
		return "capProductivityReport";
	}

	/**
	 * function to use for flexigrid list
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/capReportJson", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	JsonDataWrapper<PaymentProductivityJsonData> listCapProductivityReport(
			WebRequest request) {
		LOGGER.info("payment productivity::in Report json method");

		int page = ONE; /* default 1 for page number in json wrapper */
		int rp = Constants.ZERO;

		Map<String, String> orderClauses = new HashMap<String, String>();
		Map<String, String> whereClauses = new HashMap<String, String>();

		if (request != null) {

			if (request.getParameter(Constants.RECORD_PRE_PAGE) != null) {
				rp = Integer.parseInt(request
						.getParameter(Constants.RECORD_PRE_PAGE));
				try {
					orderClauses.put(Constants.LIMIT, "" + rp);
				} catch (Exception e) {
					LOGGER.debug("rp[Record pre Page] not coming or not an integer in request ");
				}
			}

			if (request.getParameter(Constants.PAGE) != null) {
				try {
					page = Integer.parseInt(request
							.getParameter(Constants.PAGE));
					orderClauses.put(Constants.OFFSET, "" + ((rp * page) - rp));
				} catch (Exception e) {
					LOGGER.debug("Exception during parsing");
				}
			}

			if (request.getParameter(Constants.SORT_ORDER) != null) {
				orderClauses.put(Constants.SORT_BY,
						request.getParameter(Constants.SORT_ORDER));
			}

			if (request.getParameter(Constants.SORT_NAME) != null) {
				orderClauses.put(Constants.ORDER_BY,
						request.getParameter(Constants.SORT_NAME));
			}

			if (request.getParameter(Constants.QTYPE) != null
					&& request.getParameter(Constants.QUERY) != null
					&& !request.getParameter(Constants.QUERY).isEmpty()) {
				whereClauses.put(request.getParameter(Constants.QTYPE),
						request.getParameter(Constants.QUERY));
			}
			if (request.getParameter(Constants.DOCTOR) != null
					& request.getParameter(Constants.DOCTOR).trim().length() > Constants.ZERO) {
				String doctor = request.getParameter(Constants.DOCTOR);
				whereClauses.put(Constants.DOCTOR, doctor);
			}
			if (request.getParameter(Constants.INSURANCE) != null
					& request.getParameter(Constants.INSURANCE).trim().length() > Constants.ZERO) {
				String insurance = request.getParameter(Constants.INSURANCE);
				whereClauses.put(Constants.INSURANCE, insurance);
			}
			if (request.getParameter(Constants.POSTED_BY_ID) != null
					& request.getParameter(Constants.POSTED_BY_ID).trim()
							.length() > Constants.ZERO) {
				String postedBy = request.getParameter(Constants.POSTED_BY_ID);
				whereClauses.put(Constants.POSTED_BY_ID, postedBy);
			}
			/* set posted from date */
			if (request.getParameter(Constants.DATE_POSTED_FROM) != null
					&& request.getParameter(Constants.DATE_POSTED_FROM).trim()
							.length() > Constants.ZERO) {
				LOGGER.info("DATE_POSTED_FROM= "
						+ request.getParameter(Constants.DATE_POSTED_FROM));
				whereClauses.put(Constants.DATE_POSTED_FROM,
						request.getParameter(Constants.DATE_POSTED_FROM));
			}
			/* set posted to date */
			if (request.getParameter(Constants.DATE_POSTED_TO) != null
					&& request.getParameter(Constants.DATE_POSTED_TO).trim()
							.length() > Constants.ZERO) {
				LOGGER.info("DATE_POSTED_TO= "
						+ request.getParameter(Constants.DATE_POSTED_TO));
				whereClauses.put(Constants.DATE_POSTED_TO,
						request.getParameter(Constants.DATE_POSTED_TO));
			}

			/* set received from date */
			if (request.getParameter(Constants.DATE_RECEIVED_FROM) != null
					&& request.getParameter(Constants.DATE_RECEIVED_FROM)
							.trim().length() > Constants.ZERO) {
				LOGGER.info("DATE_RECEIVED_FROM= "
						+ request.getParameter(Constants.DATE_RECEIVED_FROM));
				whereClauses.put(Constants.DATE_RECEIVED_FROM,
						request.getParameter(Constants.DATE_RECEIVED_FROM));
			}

			/* set received to date */
			if (request.getParameter(Constants.DATE_RECEIVED_TO) != null
					&& request.getParameter(Constants.DATE_RECEIVED_TO).trim()
							.length() > Constants.ZERO) {
				LOGGER.info("DATE_RECEIVED_TO= "
						+ request.getParameter(Constants.DATE_RECEIVED_TO));
				whereClauses.put(Constants.DATE_RECEIVED_TO,
						request.getParameter(Constants.DATE_RECEIVED_TO));
			}
			if (request.getParameter(Constants.TICKET_NUMBER) != null
					&& request.getParameter(Constants.TICKET_NUMBER).trim()
							.length() > Constants.ZERO) {
				String ticketNumber = request
						.getParameter(Constants.TICKET_NUMBER);

				whereClauses.put(Constants.TICKET_NUMBER, ticketNumber);
			}
			whereClauses.put(Constants.PROD_TYPE, STRING_THREE);
		} else {
			LOGGER.info("request object is coming null");
		}
		int totalRows = Constants.ZERO;
		try {
			totalRows = paymentProductivityDao.totalRecord(whereClauses);
			if (totalRows > Constants.ZERO) {
				List<PaymentProductivity> rows = paymentProductivityDao
						.findAllForReport(orderClauses, whereClauses);
				List<PaymentProductivityJsonData> djd = getJsonDataForCapReport(rows);
				JsonDataWrapper<PaymentProductivityJsonData> jdw = new JsonDataWrapper<PaymentProductivityJsonData>(
						page, totalRows, djd);
				return jdw;
			}
		} catch (Exception e) {
			LOGGER.error(e.toString());
		}

		return null;
	}

	/**
	 * this method set value in PaymentProductivityJsonData list
	 *
	 * @param rows
	 * @return
	 */
	private List<PaymentProductivityJsonData> getJsonDataForNonEraReport(
			List<PaymentProductivity> rows) {
		List<PaymentProductivityJsonData> deptJsonData = new ArrayList<PaymentProductivityJsonData>();

		if (rows != null && rows.size() > Constants.ZERO) {

			for (PaymentProductivity paymentType : rows) {
				PaymentProductivityJsonData djd = new PaymentProductivityJsonData();
				// if (paymentType.getPaymentProdType() == TWO) {
				djd.setId(paymentType.getId());
				djd.setAgencyIncome(AkpmsUtil.amountCheck(paymentType
						.getPaymentBatch().getAgencyMoney()));
				djd.setChkNumber(paymentType.getChkNumber());
				djd.setElecPostedAmt(paymentType.getPaymentBatch()
						.getElecPostedAmt());
				djd.setElecTransaction(paymentType.getElecTransaction());
				djd.setManuallyPostedAmt(paymentType.getPaymentBatch()
						.getManuallyPostedAmt());
				djd.setManualTransaction(paymentType.getManualTransaction());
				djd.setOldPriorAr(AkpmsUtil.amountCheck(paymentType
						.getPaymentBatch().getOldPriorAr()));
				djd.setOtherIncome(AkpmsUtil.amountCheck(paymentType
						.getPaymentBatch().getOtherIncome()));
				djd.setWorkFlowName(paymentType.getWorkFlowName());
				djd.setPaymentBatch(paymentType.getPaymentBatch().getId());
				djd.setDatePosted(AkpmsUtil.akpmsDateFormat(paymentType
						.getPaymentBatch().getDatePosted(),
						Constants.DATE_FORMAT));
				if (paymentType.getCreatedOn() != null) {
					djd.setReceivedDate(AkpmsUtil.akpmsDateFormat(
							paymentType.getCreatedOn(), Constants.DATE_FORMAT));

				}
				djd.setRemark(paymentType.getRemark());
				if (paymentType.getPaymentBatch().getDoctor() != null) {
					djd.setDoctorName(paymentType.getPaymentBatch().getDoctor()
							.getName());
				}
				if (paymentType.getPaymentBatch().getInsurance() != null) {
					djd.setInsurance(paymentType.getPaymentBatch()
							.getInsurance().getName());
				}
				djd.setSuspense(AkpmsUtil.amountCheck(paymentType
						.getPaymentBatch().getSuspenseAccount()));
				djd.setTime(paymentType.getTime());
				djd.setDepositAmt(AkpmsUtil.amountCheck(paymentType
						.getPaymentBatch().getDepositAmount()));
				try {
					djd.setPaymentProdType(PaymentProductivityDaoHelper
							.getProductivityType(paymentType
									.getPaymentProdType()));
				} catch (ArgusException ar) {
					LOGGER.error(Constants.EXCEPTION, ar);
				}
				djd.setPostedBy(paymentType.getCreatedBy().getFirstName() + " "
						+ paymentType.getCreatedBy().getLastName());

				deptJsonData.add(djd);
				// }

			}

		}

		return deptJsonData;
	}

	/**
	 * this method set value in PaymentProductivityJsonData list
	 *
	 * @param rows
	 * @return
	 */
	private List<PaymentProductivityJsonData> getJsonDataForCapReport(
			List<PaymentProductivity> rows) {
		List<PaymentProductivityJsonData> deptJsonData = new ArrayList<PaymentProductivityJsonData>();

		if (rows != null && rows.size() > Constants.ZERO) {

			for (PaymentProductivity paymentType : rows) {
				PaymentProductivityJsonData djd = new PaymentProductivityJsonData();
				// if (paymentType.getPaymentProdType() == THREE) {
				djd.setId(paymentType.getId());
				djd.setAgencyIncome(AkpmsUtil.amountCheck(paymentType
						.getPaymentBatch().getAgencyMoney()));
				djd.setChkNumber(paymentType.getChkNumber());
				djd.setElecPostedAmt(paymentType.getPaymentBatch()
						.getElecPostedAmt());
				djd.setElecTransaction(paymentType.getElecTransaction());
				djd.setManuallyPostedAmt(paymentType.getPaymentBatch()
						.getManuallyPostedAmt());
				djd.setManualTransaction(paymentType.getManualTransaction());
				djd.setOldPriorAr(AkpmsUtil.amountCheck(paymentType
						.getPaymentBatch().getOldPriorAr()));
				djd.setOtherIncome(AkpmsUtil.amountCheck(paymentType
						.getPaymentBatch().getOtherIncome()));
				djd.setWorkFlowName(paymentType.getWorkFlowName());
				djd.setPaymentBatch(paymentType.getPaymentBatch().getId());
				djd.setDatePosted(AkpmsUtil.akpmsDateFormat(paymentType
						.getPaymentBatch().getDatePosted(),
						Constants.DATE_FORMAT));
				if (paymentType.getCreatedOn() != null) {
					djd.setReceivedDate(AkpmsUtil.akpmsDateFormat(
							paymentType.getCreatedOn(), Constants.DATE_FORMAT));

				}
				djd.setRemark(paymentType.getRemark());
				if (paymentType.getPaymentBatch().getDoctor() != null) {
					djd.setDoctorName(paymentType.getPaymentBatch().getDoctor()
							.getName());
				}
				if (paymentType.getPaymentBatch().getInsurance() != null) {
					djd.setInsurance(paymentType.getPaymentBatch()
							.getInsurance().getName());
				}
				djd.setSuspense(AkpmsUtil.amountCheck(paymentType
						.getPaymentBatch().getSuspenseAccount()));
				djd.setTime(paymentType.getTime());
				djd.setDepositAmt(AkpmsUtil.amountCheck(paymentType
						.getPaymentBatch().getDepositAmount()));
				try {
					djd.setPaymentProdType(PaymentProductivityDaoHelper
							.getProductivityType(paymentType
									.getPaymentProdType()));
				} catch (ArgusException ar) {
					LOGGER.error(Constants.EXCEPTION, ar);
				}
				djd.setPostedBy(paymentType.getCreatedBy().getFirstName() + " "
						+ paymentType.getCreatedBy().getLastName());

				deptJsonData.add(djd);
				// }

			}

		}

		return deptJsonData;
	}

}
