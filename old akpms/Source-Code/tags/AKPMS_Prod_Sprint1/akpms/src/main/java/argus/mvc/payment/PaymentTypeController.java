package argus.mvc.payment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;

import argus.domain.PaymentType;
import argus.repo.payment.PaymentTypeDao;
import argus.util.Constants;
import argus.util.JsonDataWrapper;
import argus.util.PaymentTypeJsonData;
import argus.validator.PaymentTypeValidator;

/**
 *
 * @author vishal.joshi
 *
 */
@Controller
@RequestMapping(value = "/admin/payment")
@SessionAttributes({ Constants.PAYMENT })
public class PaymentTypeController {

	private static final Log LOGGER = LogFactory.getLog(PaymentType.class);

	@Autowired
	private PaymentTypeDao paymentTypeDao;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private PaymentTypeValidator paymentTypeValidator;

	/**
	 * This function just load jsp "admin/paymentTypeList", the list will load
	 * using flexigrid list
	 *
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String paymentList(Map<String, Object> map, WebRequest request,
			HttpSession session) {
		String success = (String) session
				.getAttribute(Constants.SUCCESS_UPDATE);
		if (success != null) {
			map.put("success",
					messageSource.getMessage(success, null, Locale.ENGLISH)
							.trim());
		}
		if (request.getParameter(Constants.STATUS) != null) {
			map.put(Constants.STATUS, request.getParameter(Constants.STATUS));
		}
		session.removeAttribute(Constants.SUCCESS_UPDATE);
		return "admin/paymentTypeList";
	}

	private Map<String, String> getOrderClause(WebRequest request) {
		Map<String, String> orderClauses = new HashMap<String, String>();
		int page = 1;
		int rp = 0;

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
		return orderClauses;
	}

	private Map<String, String> getWhereClause(WebRequest request) {
		Map<String, String> whereClauses = new HashMap<String, String>();

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
		return whereClauses;
	}

	/**
	 * function to use for flexigrid list
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/json", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonDataWrapper<PaymentTypeJsonData> listAllPayments(
			WebRequest request) {
		LOGGER.info("in json method");

		int page = 1;
		Map<String, String> orderClauses = new HashMap<String, String>();
		Map<String, String> whereClauses = new HashMap<String, String>();

		if (request != null) {

			page = Integer.parseInt(request.getParameter(Constants.PAGE));
			orderClauses = getOrderClause(request);
			whereClauses = getWhereClause(request);
		} else {
			LOGGER.info("request object is coming null");
		}
		int totalRows = paymentTypeDao.totalRecord(whereClauses);
		if (totalRows > 0) {
			List<PaymentType> rows = null;
			try {
				rows = paymentTypeDao.findAll(orderClauses, whereClauses);
			} catch (Exception e) {
				LOGGER.info(e.getMessage());
			}
			List<PaymentTypeJsonData> djd = getJsonData(rows);
			JsonDataWrapper<PaymentTypeJsonData> jdw = new JsonDataWrapper<PaymentTypeJsonData>(
					page, totalRows, djd);
			return jdw;
		}
		return null;
	}

	private List<PaymentTypeJsonData> getJsonData(List<PaymentType> rows) {
		List<PaymentTypeJsonData> paymentTypeJsonData = new ArrayList<PaymentTypeJsonData>();

		if (rows != null && rows.size() > 0) {
			for (PaymentType paymantType : rows) {
				PaymentTypeJsonData djd = new PaymentTypeJsonData();

				djd.setId(paymantType.getId());
				djd.setName(paymantType.getName());
				djd.setStatus(paymantType.isStatus());
				djd.setDesc(paymantType.getDesc());
				djd.setType(paymantType.getType());
				paymentTypeJsonData.add(djd);
			}
		}
		return paymentTypeJsonData;
	}

	/**
	 * function to show add insurance page
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String paymentAdd(Model model, WebRequest request,
			HttpSession session, Map<String, Object> map,
			@RequestParam(required = false) String successUpdate) {
		LOGGER.info("in payment Add");
		if (null != request && null != request.getParameter("id")) {
			PaymentType payment = paymentTypeDao.findById(Long
					.parseLong(request.getParameter("id")));

			if (payment == null) {
				session.setAttribute(Constants.SUCCESS_UPDATE,
						"record.notFound");
				return "redirect:/admin/payment";
			}

			model.addAttribute(Constants.PAYMENT, payment);
			model.addAttribute("mode", Constants.EDIT);

			map.put(Constants.OPERATION_TYPE, Constants.EDIT);
			map.put(Constants.BUTTON_NAME, Constants.UPDATE);
		} else {
			model.addAttribute(Constants.PAYMENT, new PaymentType());
			map.put(Constants.OPERATION_TYPE, Constants.ADD);
			map.put(Constants.BUTTON_NAME, Constants.SAVE);

		}

		return "admin/paymentTypeAdd";
	}

	/**
	 *
	 * @param insurance
	 * @param result
	 * @param model
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String saveInsurance(
			@Valid @ModelAttribute(Constants.PAYMENT) PaymentType paymentType,
			BindingResult result, Model model, WebRequest request,
			Map<String, Object> map, HttpSession session) {
		LOGGER.info("in payment Add values");
		paymentTypeValidator.validate(paymentType, result);
		if (!result.hasErrors()) {

			if (paymentType.getId() != null) {
				try {
					paymentTypeDao.updatePaymentType(paymentType);
					session.setAttribute(Constants.SUCCESS_UPDATE,
							"paymenttype.updatedSuccessfully");
				} catch (Exception e) {
					LOGGER.error(Constants.EXCEPTION, e);
				} finally {
					return "redirect:/admin/payment";
				}
			} else {
				// new insurance will be activate on addition
				paymentTypeDao.addPaymentType(paymentType);
				session.setAttribute(Constants.SUCCESS_UPDATE,
						"paymenttype.addedSuccessfully");
			}

			return "redirect:/admin/payment";
		} else {
			if (paymentType != null && paymentType.getId() != null) {
				map.put(Constants.OPERATION_TYPE, Constants.EDIT);
				map.put(Constants.BUTTON_NAME, Constants.UPDATE);
			} else {
				map.put(Constants.OPERATION_TYPE, Constants.ADD);
				map.put(Constants.BUTTON_NAME, Constants.SAVE);
			}
			return "admin/paymentTypeAdd";
		}
	}

	/**
	 * function to change payment type status
	 *
	 * @param id
	 * @param status
	 *            (1: activate. 0: inactivate)
	 * @return
	 */
	@RequestMapping(value = "/changeStatus", method = RequestMethod.GET)
	@ResponseBody
	public boolean setStatus(@RequestParam int id, @RequestParam int status) {
		LOGGER.info("in changeStatus payment type method");
		LOGGER.info("id = " + id);
		LOGGER.info("status = " + status);

		boolean response = false;

		try {
			boolean bStatus = false;
			if (status == 1) {
				bStatus = true;
			}
			int updateCount = paymentTypeDao.changeStatus(id, bStatus);
			if (updateCount > 0) {
				response = true;
			}

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
		return response;
	}

	/**
	 * function to set payment type as deleted (is_deleted =1)
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public boolean deletePaymentType(WebRequest request, HttpSession session) {
		LOGGER.info("in delete payment type method");
		LOGGER.info(request.getParameter("items"));

		boolean response = false;

		try {

			int updateCount = paymentTypeDao
					.deletePaymentType(stringToList(request
							.getParameter("items")));
			if (updateCount > 0) {
				response = true;
			}
			session.setAttribute(Constants.SUCCESS_UPDATE,
					"paymenttype.deletedSuccessfully");

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
		return response;
	}

	/**
	 * function use to convert string to list<Long>
	 *
	 * @param str
	 *            string
	 * @return long type list
	 */
	private List<Long> stringToList(String str) {

		List<String> idList = new ArrayList<String>(Arrays.asList(str
				.split(",")));
		List<Long> idListLong = new ArrayList<Long>();
		for (String list : idList) {
			idListLong.add(Long.parseLong(list));
		}

		return idListLong;
	}
}
