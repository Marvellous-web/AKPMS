package argus.mvc.paymentproductivity;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;

import argus.domain.paymentproductivity.PaymentProductivityHourly;
import argus.exception.ArgusException;
import argus.mvc.productivity.helper.ArProductivityHelper;
import argus.repo.paymentproductivity.PaymentProductivityHourlyDao;
import argus.repo.taskname.TaskNameDao;
import argus.util.Constants;
import argus.util.JsonDataWrapper;
import argus.util.PaymentProductivityHourlyJsonData;
import argus.validator.PaymentProductivityHourlyValidator;

@Controller
@RequestMapping(value = "paymentproductivityhourly")
@SessionAttributes({ Constants.PaymentProductivityHourly })
public class PaymentProductivityHourlyController {

	private static final Log LOGGER = LogFactory
			.getLog(PaymentProductivityHourlyController.class);

	@Autowired
	private TaskNameDao taskNameDao;

	@Autowired
	private PaymentProductivityHourlyDao paymentProductivityHourlyDao;

	@Autowired
	private PaymentProductivityHourlyValidator paymentProductivityHourlyValidator;

	@Autowired
	private MessageSource messageSource;

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

		String success = (String) session
				.getAttribute(Constants.SUCCESS_UPDATE);
		if (success != null) {
			map.put("success", success);
		}
		session.removeAttribute(Constants.SUCCESS_UPDATE);
		map.put("taskNameList", taskNameDao.findAll());
		return "paymentProductivity/paymentProductivityHourlyList";
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
		map.put("taskNameList", taskNameDao.findAll());
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
			map.put("taskNameList", taskNameDao.findAll());
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
			orderClauses = getOrderClause(request);
			whereClauses = getWhereClause(request);

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
	private List<PaymentProductivityHourlyJsonData> getJsonData(
			List<PaymentProductivityHourly> rows) {
		List<PaymentProductivityHourlyJsonData> hourlyJsonData = new ArrayList<PaymentProductivityHourlyJsonData>();

		if (rows != null && rows.size() > Constants.ZERO) {

			for (PaymentProductivityHourly hourly : rows) {
				PaymentProductivityHourlyJsonData hjd = new PaymentProductivityHourlyJsonData();
				hjd.setDetails(hourly.getDetail());
				hjd.setId(hourly.getId());
				hjd.setTaskName(hourly.getTaskName().getName());
				hjd.setTime(hourly.getTime());
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
		return whereClauses;
	}

	private Map<String, String> getOrderClause(WebRequest request) {
		Map<String, String> orderClauses = new HashMap<String, String>();
		int page = 1;
		int rp = 0;

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
				page = Integer.parseInt(request.getParameter(Constants.PAGE));
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

		return orderClauses;
	}
}
