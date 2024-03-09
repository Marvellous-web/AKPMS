package argus.mvc.insurance;

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

import argus.domain.Insurance;
import argus.repo.insurance.InsuranceDao;
import argus.util.AkpmsUtil;
import argus.util.Constants;
import argus.util.InsuranceJsonData;
import argus.util.JsonDataWrapper;
import argus.validator.InsuranceValidator;

@Controller
@RequestMapping(value = "/admin/insurance")
@SessionAttributes({ Constants.INSURANCE })
public class InsuranceController {
	private static final Logger LOGGER = Logger.getLogger(Insurance.class);

	@Autowired
	private InsuranceDao insuranceDao;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private InsuranceValidator insuranceValidator;

	/**
	 * This function just load jsp "admin/insuranceList", the list will load
	 * using flexigrid list
	 *
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String insurancesList(Map<String, Object> map, WebRequest request,
			HttpSession session) {
		String success = (String) session
				.getAttribute(Constants.SUCCESS_UPDATE);
		if (success != null) {
			map.put("success",
					messageSource.getMessage(success, null, Locale.ENGLISH)
							.trim());
		}
		session.removeAttribute(Constants.SUCCESS_UPDATE);
		if (request.getParameter(Constants.STATUS) != null) {
			map.put(Constants.STATUS, request.getParameter(Constants.STATUS));
		}
		return "admin/insuranceList";
	}

	/*private Map<String, String> getOrderClause(WebRequest request) {
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
	}*/

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
	public @ResponseBody
	JsonDataWrapper<InsuranceJsonData> listAllInsurances(WebRequest request) {
		LOGGER.info("in json method");

		int page = 1;

		Map<String, String> orderClauses = new HashMap<String, String>();
		Map<String, String> whereClauses = new HashMap<String, String>();

		if (request != null) {
			page = Integer.parseInt(request.getParameter("page"));
			orderClauses = AkpmsUtil.getOrderClause(request, page);
			whereClauses = getWhereClause(request);

		} else {
			LOGGER.info("request object is coming null");
		}
		int totalRows = insuranceDao.totalRecord(whereClauses);
		if (totalRows > 0) {
			List<Insurance> rows = insuranceDao.findAll(orderClauses,
					whereClauses);
			List<InsuranceJsonData> djd = getJsonData(rows);
			JsonDataWrapper<InsuranceJsonData> jdw = new JsonDataWrapper<InsuranceJsonData>(
					page, totalRows, djd);
			return jdw;
		}
		return null;
	}

	private List<InsuranceJsonData> getJsonData(List<Insurance> rows) {
		List<InsuranceJsonData> insuranceJsonData = new ArrayList<InsuranceJsonData>();

		if (rows != null && rows.size() > 0) {
			for (Insurance insurance : rows) {
				InsuranceJsonData djd = new InsuranceJsonData();

				djd.setId(insurance.getId());
				djd.setName(insurance.getName());
				djd.setStatus(insurance.isStatus());
				djd.setDesc(insurance.getDesc());
				insuranceJsonData.add(djd);
			}
		}
		return insuranceJsonData;
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
			@Valid @ModelAttribute(Constants.INSURANCE) Insurance insurance,
			BindingResult result, Model model, WebRequest request,
			Map<String, Object> map, HttpSession session) {
		LOGGER.info("in Insurance Add values");
		insuranceValidator.validate(insurance, result);
		if (!result.hasErrors()) {

			if (insurance.getId() != null) {
				try {
					insuranceDao.updateInsurance(insurance);
					session.setAttribute(Constants.SUCCESS_UPDATE,
							"insurance.updatedSuccessfully");
				} catch (Exception e) {
					LOGGER.error("Exception : ", e);
				} finally {
					return "redirect:/admin/insurance";
				}
			} else {
				// new insurance will be activate on addition
				insuranceDao.addInsurance(insurance);
				session.setAttribute(Constants.SUCCESS_UPDATE,
						"insurance.addedSuccessfully");
			}

			return "redirect:/admin/insurance";
		} else {
			if (insurance != null && insurance.getId() != null) {
				map.put(Constants.OPERATION_TYPE, Constants.EDIT);
				map.put(Constants.BUTTON_NAME, Constants.UPDATE);
			} else {
				map.put(Constants.OPERATION_TYPE, Constants.ADD);
				map.put(Constants.BUTTON_NAME, Constants.SAVE);
			}
			return "admin/insuranceAdd";
		}
	}

	/**
	 * function to show add insurance page
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String insurancesAdd(Model model, WebRequest request,
			Map<String, Object> map, HttpSession session,
			@RequestParam(required = false) String successUpdate) {
		LOGGER.info("in Insurance Add");
		if (null != request && null != request.getParameter(Constants.ID)) {

			try {
				Insurance insurance = insuranceDao.findById(Long
						.parseLong(request.getParameter(Constants.ID)));
				if (insurance == null) {
					session.setAttribute(Constants.SUCCESS_UPDATE,
							"record.notFound");
					return "redirect:/admin/insurance";
				}
				model.addAttribute(Constants.INSURANCE, insurance);
				model.addAttribute("mode", Constants.EDIT);

				map.put(Constants.OPERATION_TYPE, Constants.EDIT);
				map.put(Constants.BUTTON_NAME, Constants.UPDATE);

			} catch (Exception e) {
				LOGGER.error(Constants.EXCEPTION, e);
			}

		} else {
			model.addAttribute(Constants.INSURANCE, new Insurance());
			map.put(Constants.OPERATION_TYPE, Constants.ADD);
			map.put(Constants.BUTTON_NAME, Constants.SAVE);

		}

		return "admin/insuranceAdd";
	}

	/**
	 * function to change insurance status
	 *
	 * @param id
	 * @param status
	 *            (1: activate. 0: inactivate)
	 * @return
	 */
	@RequestMapping(value = "/changeStatus", method = RequestMethod.GET)
	@ResponseBody
	public boolean setStatus(@RequestParam int id, @RequestParam int status) {
		LOGGER.info("in changeStatus method");
		LOGGER.info("id = " + id);
		LOGGER.info("status = " + status);

		boolean response = false;

		try {
			boolean bStatus = false;
			if (status == 1) {
				bStatus = true;
			}
			int updateCount = insuranceDao.changeStatus(id, bStatus);
			if (updateCount > 0) {
				response = true;
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);

		}
		return response;
	}

	/**
	 * function to set insurance as deleted (is_deleted =1)
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteInsurance(WebRequest request) {
		LOGGER.info("in deleteInsurance method");
		LOGGER.info(request.getParameter("items"));

		boolean response = false;

		try {
			int updateCount = insuranceDao.deleteInsurance(stringToList(request
					.getParameter("items")));
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
