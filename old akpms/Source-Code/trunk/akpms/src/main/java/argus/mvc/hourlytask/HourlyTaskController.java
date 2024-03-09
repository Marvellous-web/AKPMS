package argus.mvc.hourlytask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
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

import argus.domain.Department;
import argus.domain.HourlyTask;
import argus.exception.ArgusException;
import argus.mvc.payment.PaymentTypeController;
import argus.repo.department.DepartmentDao;
import argus.repo.taskname.HourlyTaskDao;
import argus.util.AkpmsUtil;
import argus.util.Constants;
import argus.util.HourlyTaskJsonData;
import argus.util.JsonDataWrapper;
import argus.validator.HourlyTaskValidator;

@Controller
@RequestMapping(value = "/admin/hourlytask")
@SessionAttributes({ Constants.HOURLY_TASK})
public class HourlyTaskController {

	private static final Logger LOGGER = Logger
			.getLogger(PaymentTypeController.class);

	@Autowired
	private HourlyTaskDao hourlyTaskDao;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private HourlyTaskValidator hourlyTaskValidator;

	@Autowired
	private DepartmentDao departmentDao;

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

		return "admin/hourlyTaskList";
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
	public JsonDataWrapper<HourlyTaskJsonData> listAllPayments(
			WebRequest request) {
		LOGGER.info("in json method");

		int page = 1;
		Map<String, String> orderClauses = new HashMap<String, String>();
		Map<String, String> whereClauses = new HashMap<String, String>();

		if (request != null) {
			page = Integer.parseInt(request.getParameter(Constants.PAGE));
			orderClauses = AkpmsUtil.getOrderClause(request, page);
			whereClauses = getWhereClause(request);
		} else {
			LOGGER.info("request object is coming null");
		}
		int totalRows = 0;
		try {
			totalRows = hourlyTaskDao.totalRecord(whereClauses);
		} catch (Exception e) {
			LOGGER.info(e.getMessage(), e);
		}

		if (totalRows > 0) {
			List<HourlyTask> rows = null;
			try {
				rows = hourlyTaskDao.findAll(orderClauses,whereClauses, true);
			} catch (Exception e) {
				LOGGER.info(e.getMessage());
			}
			List<HourlyTaskJsonData> djd = getJsonData(rows);
			JsonDataWrapper<HourlyTaskJsonData> jdw = new JsonDataWrapper<HourlyTaskJsonData>(
					page, totalRows, djd);
			return jdw;
		}

		return null;
	}

	private List<HourlyTaskJsonData> getJsonData(List<HourlyTask> rows) {
		List<HourlyTaskJsonData> hourlyTaskJsonData = new ArrayList<HourlyTaskJsonData>();

		if (rows != null && rows.size() > 0) {
			for (HourlyTask hourlyTask : rows) {
				HourlyTaskJsonData djd = new HourlyTaskJsonData();

				djd.setId(hourlyTask.getId());
				djd.setName(hourlyTask.getName());
				if (hourlyTask.getDepartment() != null) {
					djd.setDepartment(hourlyTask.getDepartment().getName());
				}
				djd.setDesc(hourlyTask.getDescription());
				if (hourlyTask.isChargeable()) {
					djd.setChargeable(Constants.CHARGEABLE);
				} else {
					djd.setChargeable(Constants.NOT_CHARGEABLE);
				}
				// djd.setType(paymantType.getType());
				hourlyTaskJsonData.add(djd);
			}
		}
		return hourlyTaskJsonData;
	}

	@ModelAttribute("departments")
	public List<Department> departments () {
		List<Department> departments = null;
		try {

			departments = departmentDao.findAllParentOrderedByName();
		} catch (ArgusException e) {
			LOGGER.error("Failed to get all parent department ", e);
		}
		return departments;
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

			HourlyTask hourlyTask = null;
			try {
				hourlyTask = hourlyTaskDao.findById(Long.parseLong(request
						.getParameter("id")));
			} catch (Exception e) {
				LOGGER.info(e.getMessage(), e);
			}

			if (hourlyTask == null) {
				session.setAttribute(Constants.SUCCESS_UPDATE,
						"record.notFound");
				return "redirect:/admin/hourlytask";
			}

			model.addAttribute(Constants.HOURLY_TASK, hourlyTask);
			model.addAttribute("mode", Constants.EDIT);

			map.put(Constants.OPERATION_TYPE, Constants.EDIT);
			map.put(Constants.BUTTON_NAME, Constants.UPDATE);
		} else {
			model.addAttribute(Constants.HOURLY_TASK, new HourlyTask());
			map.put(Constants.OPERATION_TYPE, Constants.ADD);
			map.put(Constants.BUTTON_NAME, Constants.SAVE);

		}

		return "admin/hourlyTaskAdd";
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
			@Valid @ModelAttribute(Constants.HOURLY_TASK) HourlyTask hourlyTask,
			BindingResult result, Model model, WebRequest request,
			Map<String, Object> map, HttpSession session) {
		LOGGER.info("in Hourly Task Add values");
		hourlyTaskValidator.validate(hourlyTask, result);

		if (!result.hasErrors()) {

			if (hourlyTask.getDepartment() == null
					|| hourlyTask.getDepartment().getId() == null) {

				hourlyTask.setDepartment(null);
			}

			if (hourlyTask.getId() != null) {
				try {
					hourlyTaskDao.updateHourlyTask(hourlyTask);
					session.setAttribute(Constants.SUCCESS_UPDATE,
							"hourlyTask.updatedSuccessfully");
				} catch (Exception e) {
					LOGGER.error(Constants.EXCEPTION, e);
				} finally {
					return "redirect:/admin/hourlytask";
				}
			} else {

				try {
					hourlyTaskDao.addHourlyTask(hourlyTask);
					session.setAttribute(Constants.SUCCESS_UPDATE,
							"hourlyTask.addedSuccessfully");
				} catch (Exception e) {
					LOGGER.info(e.getMessage(), e);
				}

			}

			return "redirect:/admin/hourlytask";
		} else {
			if (hourlyTask != null && hourlyTask.getId() != null) {
				map.put(Constants.OPERATION_TYPE, Constants.EDIT);
				map.put(Constants.BUTTON_NAME, Constants.UPDATE);
			} else {
				map.put(Constants.OPERATION_TYPE, Constants.ADD);
				map.put(Constants.BUTTON_NAME, Constants.SAVE);
			}

			return "admin/hourlyTaskAdd";
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
	/*@RequestMapping(value = "/changeStatus", method = RequestMethod.GET)
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

	*//**
	 * function to set payment type as deleted (is_deleted =1)
	 *
	 * @param request
	 * @return
	 *//*
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
*/

	@RequestMapping(value = "/changeStatus", method = RequestMethod.GET)
	@ResponseBody
	public boolean setStatus(@RequestParam int id, @RequestParam int status) {
		LOGGER.info("in changeStatus hourly task method");
		LOGGER.info("id = " + id);
		LOGGER.info("status = " + status);

		boolean response = false;

		try {
			boolean bStatus = false;
			if (status == 1) {
				bStatus = true;
			}
			int updateCount = hourlyTaskDao.changeStatus(id, bStatus);
			if (updateCount > 0) {
				response = true;
			}

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
	/*
	 * private List<Long> stringToList(String str) {
	 * 
	 * List<String> idList = new ArrayList<String>(Arrays.asList(str
	 * .split(","))); List<Long> idListLong = new ArrayList<Long>(); for (String
	 * list : idList) { idListLong.add(Long.parseLong(list)); }
	 * 
	 * return idListLong; }
	 */
}
