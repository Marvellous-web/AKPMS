package argus.mvc.paymentproductivity;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import argus.domain.HourlyTask;
import argus.domain.User;
import argus.domain.paymentproductivity.PaymentProductivityHourly;
import argus.exception.ArgusException;
import argus.mvc.productivity.helper.ArProductivityHelper;
import argus.repo.paymentproductivity.PaymentProductivityHourlyDao;
import argus.repo.taskname.HourlyTaskDao;
import argus.repo.user.UserDao;
import argus.util.AkpmsUtil;
import argus.util.Constants;
import argus.util.JsonDataWrapper;
import argus.util.PaymentProductivityHourlyJsonData;
import argus.validator.PaymentProductivityHourlyValidator;

@Controller
@RequestMapping(value = "paymentproductivityhourly")
@SessionAttributes({ Constants.PaymentProductivityHourly })
public class PaymentProductivityHourlyController {

	private static final Logger LOGGER = Logger
			.getLogger(PaymentProductivityHourlyController.class);

	@Autowired
	private HourlyTaskDao hourlyTaskDao;

	@Autowired
	private PaymentProductivityHourlyDao paymentProductivityHourlyDao;

	@Autowired
	private PaymentProductivityHourlyValidator paymentProductivityHourlyValidator;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private UserDao userDao;

	private Map<String, String> printReportCriteria;

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

	@RequestMapping(method = RequestMethod.GET)
	public String paymentProductivityHourlyList(Map<String, Object> map,
			HttpSession session) {

		try {
			String success = (String) session
					.getAttribute(Constants.SUCCESS_UPDATE);
			if (success != null) {
				map.put("success", success);
			}
			session.removeAttribute(Constants.SUCCESS_UPDATE);
			// map.put("taskNameList", hourlyTaskDao.findAll(null, null,
			// false));

			Map<String, String> whereClause = new HashMap<String, String>();
			// whereClause.put(Constants.DEPARTMENT_WITH_CHILD,
			// Constants.PAYMENT_DEPARTMENT_ID);
			whereClause.put(Constants.SELECTED_ROLES_IDS,
					Constants.STANDART_USER_ROLE_ID.toString());
			whereClause.put(Constants.STATUS, Constants.ACTIVATE + "");
			List<User> userList = userDao.findAll(null, whereClause);

			map.put(Constants.USER_LIST, userList);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}

		return "paymentProductivity/paymentProductivityHourlyList";
	}

	@ModelAttribute("taskNameList")
	public List<HourlyTask> taskNameList() {
		return hourlyTaskDao.findAll(null, null, true);
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String hourlyLoad(WebRequest request, Map<String, Object> map) {
		LOGGER.info("In [hourlyLoad] method");
		PaymentProductivityHourly paymentProductivityHourly = null;
		if (null != request && null != request.getParameter(Constants.ID)) {

			try {
				paymentProductivityHourly = paymentProductivityHourlyDao
						.findById(Long.valueOf(request
								.getParameter(Constants.ID)));
				if (paymentProductivityHourly == null) {
					paymentProductivityHourly = new PaymentProductivityHourly();
					map.put(Constants.ERROR,
							"No record found for the corresponding id");
					map.put(Constants.CURRENT_DATE, new Date());
					map.put(Constants.OPERATION_TYPE, Constants.ADD);
					map.put(Constants.BUTTON_NAME, Constants.SAVE);
					LOGGER.info("Out [hourlyLoad] method");
					return "paymentProductivity/paymentProductivityHourly";
				}
			} catch (Exception e) {
				LOGGER.error(e.toString());
				map.put(Constants.ERROR,
						"No record exists for the corresponding Id");

			}
			map.put(Constants.MODE, Constants.EDIT);
			map.put(Constants.OPERATION_TYPE, Constants.EDIT);
			map.put(Constants.BUTTON_NAME, Constants.UPDATE);
		} else {
			paymentProductivityHourly = new PaymentProductivityHourly();
			map.put(Constants.CURRENT_DATE, new Date());
			map.put(Constants.OPERATION_TYPE, Constants.ADD);
			map.put(Constants.BUTTON_NAME, Constants.SAVE);
		}

		map.put("paymentProductivityHourly", paymentProductivityHourly);

		LOGGER.info("Out [hourlyLoad] method");
		return "paymentProductivity/paymentProductivityHourly";

	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String hourlyProcess(
			@ModelAttribute(Constants.PaymentProductivityHourly) PaymentProductivityHourly paymentProductivityHourly,
			WebRequest request, Map<String, Object> map, BindingResult result,
			HttpSession session) {
		LOGGER.info("In [hourlyProcess] method");
		paymentProductivityHourlyValidator.validate(paymentProductivityHourly,
				result);
		if (!result.hasErrors()) {
			if (paymentProductivityHourly.getId() != null) {
				try {
					paymentProductivityHourlyDao
							.updatePaymentProductivityHourly(paymentProductivityHourly);
					session.setAttribute(
							Constants.SUCCESS_UPDATE,
							messageSource.getMessage(
									"arProductivity.updatedSuccessfully", null,
									Locale.ENGLISH).trim());
				} catch (Exception e) {
					LOGGER.error(Constants.EXCEPTION, e);

				}
			} else {
				try {
					paymentProductivityHourlyDao
							.addPaymentProductivityHourly(paymentProductivityHourly);
					session.setAttribute(
							Constants.SUCCESS_UPDATE,
							messageSource.getMessage(
									"arProductivity.addedSuccessfully", null,
									Locale.ENGLISH).trim());
				} catch (Exception e) {
					LOGGER.error(Constants.EXCEPTION, e);
				}
			}
		} else {
			if (paymentProductivityHourly.getId() != null) {
				map.put(Constants.MODE, Constants.EDIT);
				map.put(Constants.OPERATION_TYPE, Constants.EDIT);
				map.put(Constants.BUTTON_NAME, Constants.UPDATE);
			} else {
				map.put(Constants.CURRENT_DATE, new Date());
				map.put(Constants.OPERATION_TYPE, Constants.ADD);
				map.put(Constants.BUTTON_NAME, Constants.SAVE);
			}
			// map.put("taskNameList", hourlyTaskDao.findAll(null, null,
			// false));
			return "paymentProductivity/paymentProductivityHourly";
		}
		LOGGER.info("Out [hourlyProcess] method");
		return "redirect:/paymentproductivityhourly";

	}

	@RequestMapping(value = "/json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	JsonDataWrapper<PaymentProductivityHourlyJsonData> listAllPayment(
			WebRequest request) {
		LOGGER.info("payment productivity hourly::in json method");

		int page = Constants.ONE; /* default 1 for page number in json wrapper */

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

		int totalRows = Constants.ZERO;
		try {
			totalRows = paymentProductivityHourlyDao.totalRecord(whereClauses);
			if (totalRows > Constants.ZERO) {
				List<PaymentProductivityHourly> rows = paymentProductivityHourlyDao
						.findAll(orderClauses, whereClauses);
				List<PaymentProductivityHourlyJsonData> djd = getJsonData(rows);
				JsonDataWrapper<PaymentProductivityHourlyJsonData> jdw = new JsonDataWrapper<PaymentProductivityHourlyJsonData>(
						page, totalRows, djd);
				return jdw;
			}
		} catch (Exception e) {
			LOGGER.error(e.toString(), e);
		}

		return null;
	}

	/**
	 * this method set value in PaymentProductivityJsonData list
	 *
	 * @param rows
	 * @return
	 */
	private List<PaymentProductivityHourlyJsonData> getJsonData(
			List<PaymentProductivityHourly> rows) {
		List<PaymentProductivityHourlyJsonData> hourlyJsonData = new ArrayList<PaymentProductivityHourlyJsonData>();

		if (rows != null && rows.size() > Constants.ZERO) {

			for (PaymentProductivityHourly hourly : rows) {
				PaymentProductivityHourlyJsonData hjd = new PaymentProductivityHourlyJsonData();
				hjd.setDetails(hourly.getDetail());
				hjd.setId(hourly.getId());
				hjd.setTaskName(hourly.getHourlyTask().getName());
				hjd.setMinutesspent(hourly.getMinutesspent());
				// hjd.setTime(hourly.getHours().doubleValue()+(hourly.getMinutes()/60));
				if (hourly.getDateReceived() != null) {
					hjd.setDateReceived(AkpmsUtil.akpmsDateFormat(
							hourly.getDateReceived(), Constants.DATE_FORMAT));
				}
				if (hourly.getTaskCompleted() != null) {
					hjd.setTaskCompletedDate(AkpmsUtil.akpmsDateFormat(
							hourly.getTaskCompleted(), Constants.DATE_FORMAT));
				}
				Double mins = 0.0;

				try {
					mins = Double.valueOf(AkpmsUtil.formatDoubleNumber(hourly
							.getMinutes().doubleValue() / 60));
				} catch (Exception e) {
					mins = 0.0;
				}

				hjd.setTime(hourly.getHours() + mins);

				hourlyJsonData.add(hjd);
			}

		}

		return hourlyJsonData;
	}

	private Map<String, String> getWhereClause(WebRequest request) {

		Map<String, String> whereClauses = new HashMap<String, String>();
		if (request.getParameter(Constants.TASK_NAME) != null
				&& request.getParameter(Constants.TASK_NAME).trim().length() > Constants.ZERO) {
			LOGGER.info("Task Name = "
					+ request.getParameter(Constants.TASK_NAME));
			whereClauses.put(Constants.TASK_NAME,
					request.getParameter(Constants.TASK_NAME));
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

		if (request.getParameter(Constants.TASK_RECEIVED_DATE_FROM) != null
				&& request.getParameter(Constants.TASK_RECEIVED_DATE_FROM)
						.trim().length() > Constants.ZERO) {
			LOGGER.info("TASK_RECEIVED_DATE_FROM= "
					+ request.getParameter(Constants.TASK_RECEIVED_DATE_FROM));
			whereClauses.put(Constants.TASK_RECEIVED_DATE_FROM,
					request.getParameter(Constants.TASK_RECEIVED_DATE_FROM));
		}

		if (request.getParameter(Constants.TASK_RECEIVED_DATE_TO) != null
				&& request.getParameter(Constants.TASK_RECEIVED_DATE_TO).trim()
						.length() > Constants.ZERO) {
			LOGGER.info("TASK_RECEIVED_DATE_TO= "
					+ request.getParameter(Constants.TASK_RECEIVED_DATE_TO));
			whereClauses.put(Constants.TASK_RECEIVED_DATE_TO,
					request.getParameter(Constants.TASK_RECEIVED_DATE_TO));
		}

		if (request.getParameter(Constants.TASK_COMPLETED_DATE_FROM) != null
				&& request.getParameter(Constants.TASK_COMPLETED_DATE_FROM)
						.trim().length() > Constants.ZERO) {
			LOGGER.info("TASK_COMPLETED_DATE_FROM= "
					+ request.getParameter(Constants.TASK_COMPLETED_DATE_FROM));
			whereClauses.put(Constants.TASK_COMPLETED_DATE_FROM,
					request.getParameter(Constants.TASK_COMPLETED_DATE_FROM));
		}

		if (request.getParameter(Constants.TASK_COMPLETED_DATE_TO) != null
				&& request.getParameter(Constants.TASK_COMPLETED_DATE_TO)
						.trim().length() > Constants.ZERO) {
			LOGGER.info("TASK_COMPLETED_DATE_TO= "
					+ request.getParameter(Constants.TASK_COMPLETED_DATE_TO));
			whereClauses.put(Constants.TASK_COMPLETED_DATE_TO,
					request.getParameter(Constants.TASK_COMPLETED_DATE_TO));
		}

		return whereClauses;
	}

	/*
	 * private Map<String, String> getOrderClause(WebRequest request) {
	 * Map<String, String> orderClauses = new HashMap<String, String>(); int
	 * page = 1; int rp = 0;
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
	 * request.getParameter(Constants.SORT_NAME)); }
	 *
	 * return orderClauses; }
	 */

	@RequestMapping(value = "/printableweb", method = RequestMethod.GET)
	public String printReportListViewOnWeb(HttpServletResponse response,
			HttpSession session, Model model, Map<String, Object> map) {

		try {
			// String xmlString = null;
			Map<String, String> orderClauses = new HashMap<String, String>();
			// orderClauses.put(Constants.SORT_BY, Constants.SORT_ORDER_DESC);

			List<PaymentProductivityHourly> rows = paymentProductivityHourlyDao
					.findAll(orderClauses, printReportCriteria);

			model.addAttribute("HOURLY_TASK_DETAILS", rows);

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}

		map.put("PRINT_REPORT_CRITERIA", printReportCriteria);
		map.put("SHOW_FOOTER", false);
		return "paymentProductivity/hourlyTaskListToPrint";
	}
}
