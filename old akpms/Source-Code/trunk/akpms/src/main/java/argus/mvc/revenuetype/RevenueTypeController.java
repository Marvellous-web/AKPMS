package argus.mvc.revenuetype;

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

import argus.domain.RevenueType;
import argus.repo.revenueType.RevenueTypeDao;
import argus.util.AkpmsUtil;
import argus.util.Constants;
import argus.util.JsonDataWrapper;
import argus.util.RevenueTypeJsonData;
import argus.validator.RevenueTypeValidator;

/**
 *
 * @author vishal.joshi
 *
 */
@Controller
@RequestMapping(value = "/admin/revenuetype")
@SessionAttributes({ Constants.REVENUETYPE })
public class RevenueTypeController {

	private static final Logger LOGGER = Logger
			.getLogger(RevenueTypeController.class);

	@Autowired
	private RevenueTypeDao revenueTypeDao;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private RevenueTypeValidator revenueTypeValidator;

	/**
	 * This function just load jsp "admin/revenueTypeList", the list will load
	 * using flexigrid list
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String revenueTypeList(Map<String, Object> map, WebRequest request,
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
		return "admin/revenueTypeList";
	}

	/*
	 * private Map<String, String> getOrderClause(WebRequest request) {
	 * Map<String, String> orderClauses = new HashMap<String, String>(); int
	 * page = 1; int rp = 0;
	 * 
	 * if (request.getParameter("rp") != null) { rp =
	 * Integer.parseInt(request.getParameter("rp")); try {
	 * orderClauses.put("limit", "" + rp); } catch (Exception e) {
	 * LOGGER.debug("rp[Record pre Page] not coming or not an integer in request "
	 * ); } }
	 * 
	 * if (request.getParameter("page") != null) { try { page =
	 * Integer.parseInt(request.getParameter("page"));
	 * orderClauses.put("offset", "" + ((rp * page) - rp)); } catch (Exception
	 * e) { LOGGER.debug("Exception during parsing"); } }
	 * 
	 * if (request.getParameter("sortorder") != null) {
	 * orderClauses.put("sortBy", request.getParameter("sortorder")); }
	 * 
	 * if (request.getParameter("sortname") != null) {
	 * orderClauses.put("orderBy", request.getParameter("sortname")); } return
	 * orderClauses; }
	 */

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
	public JsonDataWrapper<RevenueTypeJsonData> listAllRevenueTypes(
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
			totalRows = revenueTypeDao.totalRecord(whereClauses);
		} catch (Exception e) {
			LOGGER.info(e.getMessage(), e);
		}

		if (totalRows > 0) {
			List<RevenueType> rows = null;
			try {
				rows = revenueTypeDao.findAll(orderClauses, whereClauses);
			} catch (Exception e) {
				LOGGER.info(e.getMessage());
			}
			List<RevenueTypeJsonData> djd = getJsonData(rows);
			JsonDataWrapper<RevenueTypeJsonData> jdw = new JsonDataWrapper<RevenueTypeJsonData>(
					page, totalRows, djd);
			return jdw;
		}
		return null;
	}

	private List<RevenueTypeJsonData> getJsonData(List<RevenueType> rows) {
		List<RevenueTypeJsonData> revenueTypeJsonData = new ArrayList<RevenueTypeJsonData>();

		if (rows != null && rows.size() > 0) {
			for (RevenueType revenueType : rows) {
				RevenueTypeJsonData djd = new RevenueTypeJsonData();

				djd.setId(revenueType.getId());
				djd.setName(revenueType.getName());
				djd.setStatus(revenueType.isStatus());
				djd.setDesc(revenueType.getDesc());
				djd.setCode(revenueType.getCode());
				revenueTypeJsonData.add(djd);
			}
		}
		return revenueTypeJsonData;
	}

	/**
	 * function to show add insurance page
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String revenueTypeAdd(Model model, WebRequest request,
			HttpSession session, Map<String, Object> map,
			@RequestParam(required = false) String successUpdate) {
		LOGGER.info("in revenue type Add");
		if (null != request && null != request.getParameter("id")) {

			RevenueType revenueType = null;
			try {
				revenueType = revenueTypeDao.findById(Long.parseLong(request
						.getParameter("id")));
			} catch (Exception e) {
				LOGGER.info(e.getMessage(), e);
			}

			if (revenueType == null) {
				session.setAttribute(Constants.SUCCESS_UPDATE,
						"record.notFound");
				return "redirect:/admin/revenuetype";
			}

			model.addAttribute(Constants.REVENUETYPE, revenueType);
			model.addAttribute("mode", Constants.EDIT);

			map.put(Constants.OPERATION_TYPE, Constants.EDIT);
			map.put(Constants.BUTTON_NAME, Constants.UPDATE);
		} else {
			model.addAttribute(Constants.REVENUETYPE, new RevenueType());
			map.put(Constants.OPERATION_TYPE, Constants.ADD);
			map.put(Constants.BUTTON_NAME, Constants.SAVE);

		}

		return "admin/revenueTypeAdd";
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
			@Valid @ModelAttribute(Constants.REVENUETYPE) RevenueType revenueType,
			BindingResult result, Model model, WebRequest request,
			Map<String, Object> map, HttpSession session) {
		LOGGER.info("in revenue Add values");
		revenueTypeValidator.validate(revenueType, result);
		if (!result.hasErrors()) {

			if (revenueType.getId() != null) {
				try {
					revenueTypeDao.updateRevenueType(revenueType);
					session.setAttribute(Constants.SUCCESS_UPDATE,
							"revenuetype.updatedSuccessfully");
				} catch (Exception e) {
					LOGGER.error(Constants.EXCEPTION, e);
				} finally {
					return "redirect:/admin/revenuetype";
				}
			} else {
				try {
					revenueTypeDao.addRevenueType(revenueType);
					session.setAttribute(Constants.SUCCESS_UPDATE,
							"revenuetype.addedSuccessfully");
				} catch (Exception e) {
					LOGGER.info(e.getMessage(), e);
				}
			}

			return "redirect:/admin/revenuetype";
		} else {
			if (revenueType != null && revenueType.getId() != null) {
				map.put(Constants.OPERATION_TYPE, Constants.EDIT);
				map.put(Constants.BUTTON_NAME, Constants.UPDATE);
			} else {
				map.put(Constants.OPERATION_TYPE, Constants.ADD);
				map.put(Constants.BUTTON_NAME, Constants.SAVE);
			}
			return "admin/revenueTypeAdd";
		}
	}

	/**
	 * function to change revenue type status
	 * 
	 * @param id
	 * @param status
	 *            (1: activate. 0: inactivate)
	 * @return
	 */
	@RequestMapping(value = "/changeStatus", method = RequestMethod.GET)
	@ResponseBody
	public boolean setStatus(@RequestParam int id, @RequestParam int status) {
		LOGGER.info("in changeStatus revenue type method");
		LOGGER.info("id = " + id);
		LOGGER.info("status = " + status);

		boolean response = false;

		try {
			boolean bStatus = false;
			if (status == 1) {
				bStatus = true;
			}
			int updateCount = revenueTypeDao.changeStatus(id, bStatus);
			if (updateCount > 0) {
				response = true;
			}

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
		return response;
	}

	/**
	 * function to set revenue type as deleted (is_deleted =1)
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteRevenueType(WebRequest request, HttpSession session) {
		LOGGER.info("in delete revenue type method");
		LOGGER.info(request.getParameter("items"));

		boolean response = false;

		try {

			int updateCount = revenueTypeDao
					.deleteRevenueType(stringToList(request
							.getParameter("items")));
			if (updateCount > 0) {
				response = true;
			}
			session.setAttribute(Constants.SUCCESS_UPDATE,
					"revenuetype.deletedSuccessfully");

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
