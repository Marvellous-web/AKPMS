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
import argus.validator.PaymentERAValidator;

/**
 * @author vishal.joshi
 *
 */
@Controller
@RequestMapping(value = "/paymentproductivityERA")
@SessionAttributes({ Constants.PAYMENTPRODUCTIVITY })
public class PaymentProdERAController {

	private static final Log LOGGER = LogFactory
			.getLog(PaymentProdERAController.class);

	private static final int ERA = 1;

	@Autowired
	private PaymentProductivityDao paymentProductivityDao;

	@Autowired
	private PaymentBatchDao batchDao;

	@Autowired
	private PaymentERAValidator eraValidator;

	@Autowired
	private PaymentProdQueryToTLDao prodQueryToTLDao;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private DoctorDao doctorDao;

	@Autowired
	private InsuranceDao insuranceDao;

	@Autowired
	private UserDao userDao;

	@InitBinder
	protected void initBinder(WebDataBinder binder) throws ArgusException {
		LOGGER.info("in [initBinder] method : ");
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

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String productivityLoad(Model model, WebRequest request,
			Map<String, Object> map, HttpSession session) {
		LOGGER.info("in [productivityLoad] method");

		if (null != request && null != request.getParameter(Constants.ID)) {
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

			model.addAttribute(Constants.PAYMENTPRODUCTIVITY, payProductivity);
			model.addAttribute(Constants.MODE, Constants.EDIT);
			map.put(Constants.OPERATION_TYPE, Constants.EDIT);
			map.put(Constants.BUTTON_NAME, Constants.UPDATE);
			map.put(Constants.GET_BUTTON, Constants.GET_DETAILS);
			map.put(Constants.HIDE, Constants.HIDE);

		} else {
			model.addAttribute(Constants.PAYMENTPRODUCTIVITY,
					new PaymentProductivity());
			model.addAttribute(Constants.MODE, Constants.ADD);
			map.put(Constants.OPERATION_TYPE, Constants.ADD);
			map.put(Constants.BUTTON_NAME, Constants.SAVE);
			map.put(Constants.GET_BUTTON, Constants.GET_DETAILS);
		}
		map.put(Constants.CURRENT_DATE,
				new SimpleDateFormat("MM/dd/yyyy").format(new Date()));

		return "paymentProductivity/paymentProdERA";
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
		if (obj[0] != Constants.ERR) {
			payProductivity.setPaymentBatch((PaymentBatch) obj[1]);
			obj[1] = null;
		}
		return obj;

	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String savePaymentProductivity(
			@Valid @ModelAttribute(Constants.PAYMENTPRODUCTIVITY) PaymentProductivity payProductivity,
			BindingResult result, Model model, WebRequest request,
			Map<String, Object> map, HttpSession session) {
		LOGGER.info("in [savePaymentProductivity] method : ");
		PaymentProdQueryToTL prodQueryToTL = new PaymentProdQueryToTL();
		PaymentBatch paymentBatch = new PaymentBatch();
		eraValidator.validate(payProductivity, result);
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
					paymentBatch.setId(payProductivity.getPaymentBatch()
							.getId());
					Calendar currenttime = Calendar.getInstance();
					paymentBatch.setPostedOn(new Date((currenttime.getTime())
							.getTime()));
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
					/*paymentBatch
							.setCtPostedTotal(AkpmsUtil
									.amountCheck(payProductivity
											.getManuallyPostedAmt())
									+ AkpmsUtil.amountCheck(payProductivity
											.getElecPostedAmt()));*/
					paymentBatch.setManuallyPostedAmt(payProductivity.getPaymentBatch().getManuallyPostedAmt());
					paymentBatch.setElecPostedAmt(payProductivity.getPaymentBatch().getElecPostedAmt());
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
				} finally {
					LOGGER.info("FINALLY ");
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
								payProductivity.getWorkFlowId(), 0, false);
					}
				} else if (payProductivity.isOffset()) {
					return "redirect:/paymentproductivitynonera";
				}

			} else {
				try {
					payProductivity.setPaymentProdType(ERA);
					paymentProductivityDao
							.addPaymentProductivity(payProductivity);
					paymentBatch = batchDao.findById(payProductivity
							.getPaymentBatch().getId(), false);
					Calendar currenttime = Calendar.getInstance();
					paymentBatch.setPostedOn(new Date((currenttime.getTime())
							.getTime()));
					paymentBatch.setPostedBy(AkpmsUtil.getLoggedInUser());
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
					/*
					 * paymentBatch .setCtPostedTotal(AkpmsUtil
					 * .amountCheck(payProductivity .getManuallyPostedAmt()) +
					 * AkpmsUtil.amountCheck(payProductivity
					 * .getElecPostedAmt()));
					 */
					paymentBatch.setManuallyPostedAmt(payProductivity
							.getPaymentBatch().getManuallyPostedAmt());
					paymentBatch.setElecPostedAmt(payProductivity
							.getPaymentBatch().getElecPostedAmt());
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

				} catch (ArgusException e) {
					LOGGER.info(Constants.EXCEPTION, e);
				}
			}
			if (payProductivity.isOffset()) {
				payProductivity.setWorkFlowId(Constants.OFFSET_WORK_FLOW);

			}
			return PaymentProductivityHelper.getWorkFlow(
					payProductivity.getWorkFlowId(), 0, false);
		} else {

			LOGGER.info("::ERROR::");
			if (payProductivity != null & payProductivity.getId() != null) {
				map.put(Constants.OPERATION_TYPE, Constants.EDIT);
				map.put(Constants.BUTTON_NAME, Constants.UPDATE);
				map.put(Constants.GET_BUTTON, Constants.GET_DETAILS);
				map.put(Constants.HIDE, Constants.HIDE);
				map.put(Constants.CURRENT_DATE, new SimpleDateFormat(
						"MM/dd/yyyy").format(new Date()));
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
						"MM/dd/yyyy").format(new Date()));
			}

			return "paymentProductivity/paymentProdERA";
		}
	}

	@RequestMapping(value = "/report", method = RequestMethod.GET)
	public String eraProductivityReportLoad(WebRequest request, Model model,
			Map<String, String> map) {
		try {
			List<Doctor> doctorList = doctorDao.findAll(null, null, false);

			model.addAttribute("paymentProductivity", new PaymentProductivity());
			model.addAttribute("doctorList", doctorList);

			Map<String, String> whereClause = new HashMap<String, String>();
			whereClause.put(Constants.STATUS, Constants.ACTIVATE + "");

			model.addAttribute("insuranceList",
					insuranceDao.findAll(null, whereClause));
			try {
				model.addAttribute("postedBy", userDao.getUserByDept());
			} catch (ArgusException ar) {
				LOGGER.error(Constants.EXCEPTION, ar);
			}

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
		return "eraProductivityReport";
	}

	/**
	 * function to use for flexigrid list
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	JsonDataWrapper<PaymentProductivityJsonData> listAllProductivity(
			WebRequest request) {
		LOGGER.info("payment productivity::in Report json method");

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
					& request.getParameter(Constants.DOCTOR).trim().length() > 0) {
				String doctor = request.getParameter(Constants.DOCTOR);
				whereClauses.put(Constants.DOCTOR, doctor);
			}
			if (request.getParameter(Constants.INSURANCE) != null
					& request.getParameter(Constants.INSURANCE).trim().length() > 0) {
				String insurance = request.getParameter(Constants.INSURANCE);
				whereClauses.put(Constants.INSURANCE, insurance);
			}
			if (request.getParameter(Constants.POSTED_BY_ID) != null
					& request.getParameter(Constants.POSTED_BY_ID).trim()
							.length() > 0) {
				String postedBy = request.getParameter(Constants.POSTED_BY_ID);
				whereClauses.put(Constants.POSTED_BY_ID, postedBy);
			}
			// set posted from date
			if (request.getParameter(Constants.DATE_POSTED_FROM) != null
					&& request.getParameter(Constants.DATE_POSTED_FROM).trim()
							.length() > 0) {
				LOGGER.info("DATE_POSTED_FROM= "
						+ request.getParameter(Constants.DATE_POSTED_FROM));
				whereClauses.put(Constants.DATE_POSTED_FROM,
						request.getParameter(Constants.DATE_POSTED_FROM));
			}

			// set posted to date
			if (request.getParameter(Constants.DATE_POSTED_TO) != null
					&& request.getParameter(Constants.DATE_POSTED_TO).trim()
							.length() > 0) {
				LOGGER.info("DATE_POSTED_TO= "
						+ request.getParameter(Constants.DATE_POSTED_TO));
				whereClauses.put(Constants.DATE_POSTED_TO,
						request.getParameter(Constants.DATE_POSTED_TO));
			}

			// set received from date
			if (request.getParameter(Constants.DATE_RECEIVED_FROM) != null
					&& request.getParameter(Constants.DATE_RECEIVED_FROM)
							.trim().length() > 0) {
				LOGGER.info("DATE_RECEIVED_FROM= "
						+ request.getParameter(Constants.DATE_RECEIVED_FROM));
				whereClauses.put(Constants.DATE_RECEIVED_FROM,
						request.getParameter(Constants.DATE_RECEIVED_FROM));
			}

			// set received to date
			if (request.getParameter(Constants.DATE_RECEIVED_TO) != null
					&& request.getParameter(Constants.DATE_RECEIVED_TO).trim()
							.length() > 0) {
				LOGGER.info("DATE_RECEIVED_TO= "
						+ request.getParameter(Constants.DATE_RECEIVED_TO));
				whereClauses.put(Constants.DATE_RECEIVED_TO,
						request.getParameter(Constants.DATE_RECEIVED_TO));
			}

			if (request.getParameter(Constants.TICKET_NUMBER) != null
					&& request.getParameter(Constants.TICKET_NUMBER).trim()
							.length() > 0) {
				String ticketNumber = request
						.getParameter(Constants.TICKET_NUMBER);

				whereClauses.put(Constants.TICKET_NUMBER, ticketNumber);
			}
			whereClauses.put(Constants.PROD_TYPE, Constants.STRING_ONE);
		} else {
			LOGGER.info("request object is coming null");
		}

		int totalRows = 0;
		try {
			totalRows = paymentProductivityDao.totalRecord(whereClauses);
			if (totalRows > 0) {
				List<PaymentProductivity> rows = paymentProductivityDao
						.findAllForReport(orderClauses, whereClauses);
				List<PaymentProductivityJsonData> djd = getJsonData(rows);
				JsonDataWrapper<PaymentProductivityJsonData> jdw = new JsonDataWrapper<PaymentProductivityJsonData>(
						page, totalRows, djd);
				return jdw;
			}
		} catch (Exception e) {
			// TODO: handle exception
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

		if (rows != null && rows.size() > 0) {

			for (PaymentProductivity paymentProductivity : rows) {
				PaymentProductivityJsonData djd = new PaymentProductivityJsonData();
				// if (paymentType.getPaymentProdType() == 1) {
				djd.setId(paymentProductivity.getId());
				djd.setManuallyPostedAmt(paymentProductivity.getPaymentBatch()
						.getManuallyPostedAmt());
				djd.setElecPostedAmt(paymentProductivity.getPaymentBatch()
						.getElecPostedAmt());
				djd.setPaymentBatch(paymentProductivity.getPaymentBatch().getId());
				if (paymentProductivity.getPaymentBatch().getDatePosted() != null) {
					djd.setDatePosted(AkpmsUtil.akpmsDateFormat(
							paymentProductivity.getPaymentBatch()
									.getDatePosted(), Constants.DATE_FORMAT));
				}
				if (paymentProductivity.getCreatedOn() != null) {
					djd.setReceivedDate(AkpmsUtil.akpmsDateFormat(
							paymentProductivity.getCreatedOn(), Constants.DATE_FORMAT));

				}
				djd.setDepositAmt(AkpmsUtil.amountCheck(paymentProductivity
						.getPaymentBatch().getDepositAmount()));
				djd.setDoctorName(paymentProductivity.getPaymentBatch().getDoctor()
						.getName());
				djd.setInsurance(paymentProductivity.getPaymentBatch().getInsurance()
						.getName());
				djd.setRemark(paymentProductivity.getRemark());
				try {
					djd.setPaymentProdType(PaymentProductivityDaoHelper
							.getProductivityType(paymentProductivity
									.getPaymentProdType()));
				} catch (ArgusException ar) {
					LOGGER.error(Constants.EXCEPTION, ar);
				}
				djd.setPostedBy(paymentProductivity.getCreatedBy().getFirstName() + " "
						+ paymentProductivity.getCreatedBy().getLastName());
				djd.setTime(paymentProductivity.getTime());

				deptJsonData.add(djd);
				// }

			}

		}

		return deptJsonData;
	}
}
