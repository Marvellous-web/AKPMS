package argus.mvc.paymentproductivity;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import argus.domain.paymentproductivity.PaymentProductivity;
import argus.domain.paymentproductivity.PaymentProductivityRefundRequest;
import argus.exception.ArgusException;
import argus.mvc.paymentproductivity.helper.PaymentProductivityHelper;
import argus.mvc.productivity.helper.ArProductivityHelper;
import argus.repo.doctor.DoctorDao;
import argus.repo.paymentbatch.PaymentBatchDao;
import argus.repo.paymentproductivity.PaymentProductivityRefundRequestDao;
import argus.util.AkpmsUtil;
import argus.util.Constants;
import argus.util.JsonDataWrapper;
import argus.util.RefundRequestReportJsonData;
import argus.validator.PaymentProductivityRefundRequestValidator;

@Controller
@RequestMapping(value = "/paymentproductivityrefundrequest")
@SessionAttributes({ Constants.PaymentProductivityRefundRequest })
public class PaymentProductivityRefundRequestController {

	private static final Log LOGGER = LogFactory
			.getLog(PaymentProductivityRefundRequestController.class);

	private static final String DOCTOR_LIST = "doctorList";

	@Autowired
	private PaymentProductivityRefundRequestDao paymentProductivityRefundRequestDao;

	@Autowired
	private PaymentBatchDao paymentBatchDao;

	@Autowired
	private DoctorDao doctorDao;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private PaymentProductivityRefundRequestValidator paymentProductivityRefundRequestValidator;

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
	public String refundRequestLoad(WebRequest request, Map<String, Object> map) {
		LOGGER.info("in [refundRequestLoad] method");

		try {
			Map<String, String> whereClause = new HashMap<String, String>();
			whereClause.put(Constants.STATUS, Constants.ACTIVATE + "");

			map.put(DOCTOR_LIST, doctorDao.findAll(null, whereClause, false));
		} catch (Exception e) {
			LOGGER.info("EXception while getting doctor list");
			LOGGER.info(e.getMessage());
			map.put(DOCTOR_LIST, null);
		}

		if (null != request && null != request.getParameter(Constants.ID)) {
			PaymentProductivityRefundRequest paymentProductivityRefundRequest = null;
			try {
				paymentProductivityRefundRequest = paymentProductivityRefundRequestDao
						.findById(Long.valueOf(request
								.getParameter(Constants.ID)));
			} catch (Exception e) {
				LOGGER.error(e.toString());
			}
			map.put(Constants.PaymentProductivityRefundRequest,
					paymentProductivityRefundRequest);
			map.put(Constants.MODE, Constants.EDIT);
			map.put(Constants.OPERATION_TYPE, Constants.EDIT);
			map.put(Constants.BUTTON_NAME, Constants.UPDATE);
			map.put(Constants.GET_BUTTON, Constants.GET_DETAILS);
			map.put(Constants.HIDE, Constants.HIDE);

		} else {
			map.put(Constants.PaymentProductivityRefundRequest,
					new PaymentProductivityRefundRequest());
			map.put(Constants.MODE, Constants.ADD);
			map.put(Constants.OPERATION_TYPE, Constants.ADD);
			map.put(Constants.BUTTON_NAME, Constants.SAVE);
			map.put(Constants.GET_BUTTON, Constants.GET_DETAILS);
			map.put(Constants.CURRENT_DATE, new SimpleDateFormat(
					Constants.DATE_FORMAT).format(new Date()));
		}

		return "paymentProductivity/paymentProductivityRefundRequestAdd";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String refundRequestProcess(
			@ModelAttribute(Constants.PaymentProductivityRefundRequest) PaymentProductivityRefundRequest paymentProductivityRefundRequest,
			BindingResult result, Map<String, Object> map, WebRequest request) {

		paymentProductivityRefundRequestValidator.validate(
				paymentProductivityRefundRequest, result);
		try {
			Map<String, String> whereClause = new HashMap<String, String>();
			whereClause.put(Constants.STATUS, Constants.ACTIVATE + "");

			map.put(DOCTOR_LIST, doctorDao.findAll(null, whereClause, false));
		} catch (Exception e) {
			LOGGER.info("EXception while getting doctor list");
			LOGGER.info(e.getMessage());
			map.put(DOCTOR_LIST, null);
		}
		if (!result.hasErrors()) {
			if (paymentProductivityRefundRequest.getId() != null) {
				try {
					paymentProductivityRefundRequestDao
							.updatePaymentProductivityRefundRequest(paymentProductivityRefundRequest);
				} catch (Exception e) {
					LOGGER.error(e.toString());
					map.put(Constants.MODE, Constants.EDIT);
					map.put(Constants.OPERATION_TYPE, Constants.EDIT);
					map.put(Constants.BUTTON_NAME, Constants.UPDATE);
					map.put(Constants.HIDE, Constants.HIDE);
					map.put(Constants.CURRENT_DATE, new SimpleDateFormat(
							Constants.DATE_FORMAT).format(new Date()));
				}
			} else {
				try {
					PaymentBatch paymentBatch = paymentBatchDao.findById(
							paymentProductivityRefundRequest.getPaymentBatch()
									.getId(), false);
					paymentBatch.setPostedBy(AkpmsUtil.getLoggedInUser());
					Calendar currenttime = Calendar.getInstance();
					paymentBatch.setPostedOn(new Date((currenttime.getTime())
							.getTime()));
					paymentBatchDao.updatePaymentBatch(paymentBatch);
					paymentProductivityRefundRequestDao
							.addPaymentProductivityRefundRequest(paymentProductivityRefundRequest);

				} catch (Exception e) {
					LOGGER.error(e.toString());
					map.put(Constants.MODE, Constants.ADD);
					map.put(Constants.OPERATION_TYPE, Constants.ADD);
					map.put(Constants.BUTTON_NAME, Constants.SAVE);
				}
			}
		} else {
			map.put(Constants.GET_BUTTON, Constants.GET_DETAILS);

			if (paymentProductivityRefundRequest.getId() != null) {
				map.put(Constants.MODE, Constants.EDIT);
				map.put(Constants.OPERATION_TYPE, Constants.EDIT);
				map.put(Constants.BUTTON_NAME, Constants.UPDATE);
				map.put(Constants.HIDE, Constants.HIDE);
				map.put(Constants.CURRENT_DATE, new SimpleDateFormat(
						Constants.DATE_FORMAT).format(new Date()));
			} else {
				map.put(Constants.MODE, Constants.ADD);
				map.put(Constants.OPERATION_TYPE, Constants.ADD);
				map.put(Constants.BUTTON_NAME, Constants.SAVE);
			}
			return "paymentProductivity/paymentProductivityRefundRequestAdd";

		}

		return "redirect:/paymentproductivityrefundrequest/add";
	}

	@RequestMapping(value = "getticketdetail/json", method = RequestMethod.POST)
	@ResponseBody
	public Object[] getDetail(
			@ModelAttribute(Constants.PaymentProductivityRefundRequest) PaymentProductivityRefundRequest paymentProductivityRefundRequest,
			@RequestParam(value = "batchId", required = false) String batchId,
			WebRequest request) {
		Object[] obj = null;
		PaymentBatch paymentBatch = new PaymentBatch();
		obj = PaymentProductivityHelper.getPaymentBatch(obj, batchId,
				paymentBatch, paymentBatchDao, messageSource);
		if (!obj[Constants.ZERO].equals(Constants.ERR)) {
			paymentProductivityRefundRequest
					.setPaymentBatch((PaymentBatch) obj[Constants.ONE]);
			obj[Constants.ONE] = null;
		}
		return obj;

	}

	@RequestMapping(value = "/report", method = RequestMethod.GET)
	public String eraProductivityReportLoad(WebRequest request, Model model,
			Map<String, String> map) {
		try {
			List<Doctor> doctorList = doctorDao.findAll(null, null, false);

			model.addAttribute("paymentProductivity", new PaymentProductivity());
			model.addAttribute("doctorList", doctorList);

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
		return "refundRequestProductivityReport";
	}

	/**
	 * function to use for flexigrid list
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	JsonDataWrapper<RefundRequestReportJsonData> listAllProductivity(
			WebRequest request) {
		LOGGER.info("payment productivity::in Report json method");

		int page = 1; /* default 1 for page number in json wrapper */
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

			/* set posted from date */
			if (request.getParameter(Constants.DATE_POSTED_FROM) != null
					&& request.getParameter(Constants.DATE_POSTED_FROM).trim()
							.length() > 0) {
				LOGGER.info("DATE_POSTED_FROM= "
						+ request.getParameter(Constants.DATE_POSTED_FROM));
				whereClauses.put(Constants.DATE_POSTED_FROM,
						request.getParameter(Constants.DATE_POSTED_FROM));
			}

			/* set posted to date */
			if (request.getParameter(Constants.DATE_POSTED_TO) != null
					&& request.getParameter(Constants.DATE_POSTED_TO).trim()
							.length() > 0) {
				LOGGER.info("DATE_POSTED_TO= "
						+ request.getParameter(Constants.DATE_POSTED_TO));
				whereClauses.put(Constants.DATE_POSTED_TO,
						request.getParameter(Constants.DATE_POSTED_TO));
			}

			/* set transaction from date */
			if (request.getParameter(Constants.DATE_TRANSACTION_FROM) != null
					&& request.getParameter(Constants.DATE_TRANSACTION_FROM)
							.trim().length() > 0) {
				LOGGER.info("DATE_TRANSACTION_FROM= "
						+ request.getParameter(Constants.DATE_TRANSACTION_FROM));
				whereClauses.put(Constants.DATE_TRANSACTION_FROM,
						request.getParameter(Constants.DATE_TRANSACTION_FROM));
			}

			/* set transaction to date */
			if (request.getParameter(Constants.DATE_TRANSACTION_TO) != null
					&& request.getParameter(Constants.DATE_TRANSACTION_TO)
							.trim().length() > 0) {
				LOGGER.info("DATE_TRANSACTION_TO= "
						+ request.getParameter(Constants.DATE_TRANSACTION_TO));
				whereClauses.put(Constants.DATE_TRANSACTION_TO,
						request.getParameter(Constants.DATE_TRANSACTION_TO));
			}
			/* set resolution from date */
			if (request.getParameter(Constants.DATE_RESOLUTION_FROM) != null
					&& request.getParameter(Constants.DATE_RESOLUTION_FROM)
							.trim().length() > 0) {
				LOGGER.info("DATE_RESOLUTION_FROM= "
						+ request.getParameter(Constants.DATE_RESOLUTION_FROM));
				whereClauses.put(Constants.DATE_RESOLUTION_FROM,
						request.getParameter(Constants.DATE_RESOLUTION_FROM));
			}

			/* set resolution to date */
			if (request.getParameter(Constants.DATE_RESOLUTION_FROM) != null
					&& request.getParameter(Constants.DATE_RESOLUTION_FROM)
							.trim().length() > 0) {
				LOGGER.info("DATE_RESOLUTION_FROM= "
						+ request.getParameter(Constants.DATE_RESOLUTION_FROM));
				whereClauses.put(Constants.DATE_RESOLUTION_FROM,
						request.getParameter(Constants.DATE_RESOLUTION_FROM));
			}

		} else {
			LOGGER.info("request object is coming null");
		}
		int totalRows = 0;
		try {
			totalRows = paymentProductivityRefundRequestDao
					.totalRecord(whereClauses);
			if (totalRows > 0) {
				List<PaymentProductivityRefundRequest> rows = paymentProductivityRefundRequestDao
						.findAllForReport(orderClauses, whereClauses);
				List<RefundRequestReportJsonData> djd = getJsonData(rows);
				JsonDataWrapper<RefundRequestReportJsonData> jdw = new JsonDataWrapper<RefundRequestReportJsonData>(
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
	private List<RefundRequestReportJsonData> getJsonData(
			List<PaymentProductivityRefundRequest> rows) {
		List<RefundRequestReportJsonData> deptJsonData = new ArrayList<RefundRequestReportJsonData>();

		if (rows != null && rows.size() > 0) {

			for (PaymentProductivityRefundRequest payment : rows) {
				RefundRequestReportJsonData djd = new RefundRequestReportJsonData();
				djd.setId(payment.getId());
				djd.setTimeTaken(payment.getTimeTaken());
				djd.setTicketNumber(payment.getPaymentBatch().getId());
				djd.setCreatedDate(AkpmsUtil.akpmsDateFormat(
						payment.getCreatedOn(), Constants.DATE_FORMAT));
				djd.setDoctorName(payment.getPaymentBatch().getDoctor()
						.getName());
				djd.setCreditAmount(payment.getCreditAmount());
				djd.setTransactionDate(AkpmsUtil.akpmsDateFormat(
						payment.getTransactionDate(), Constants.DATE_FORMAT));
				djd.setResolutionDate(AkpmsUtil.akpmsDateFormat(
						payment.getRequestDate(), Constants.DATE_FORMAT));
				djd.setFindings(payment.getFindings());
				djd.setResolutionOrRemark(payment.getResolutionOrRemark());

				deptJsonData.add(djd);
			}

		}

		return deptJsonData;
	}
}
