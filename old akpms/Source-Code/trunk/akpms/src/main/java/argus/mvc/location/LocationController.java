package argus.mvc.location;

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

import argus.domain.Location;
import argus.repo.location.LocationDao;
import argus.util.AkpmsUtil;
import argus.util.Constants;
import argus.util.JsonDataWrapper;
import argus.util.LocationJsonData;
import argus.validator.LocationValidator;

@Controller
@RequestMapping(value = "/admin/location")
@SessionAttributes({ Constants.LOCATION })
public class LocationController {
	private static final Logger LOGGER = Logger.getLogger(Location.class);

	@Autowired
	private LocationDao locationDao;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private LocationValidator locationValidator;

	/**
	 * This function just load jsp "admin/locationList", the list will load
	 * using flexigrid list
	 *
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String locationsList(Map<String, Object> map, WebRequest request,
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
		return "admin/locationList";
	}

	/*
	 * private Map<String, String> getOrderClause(WebRequest request) {
	 * Map<String, String> orderClauses = new HashMap<String, String>(); int
	 * page = 1; int rp = 0; if (request.getParameter("rp") != null) { rp =
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
	 * orderClauses.put("orderBy", request.getParameter("sortname")); }
	 *
	 * return orderClauses; }
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
	public @ResponseBody
	JsonDataWrapper<LocationJsonData> listAllLocations(WebRequest request) {
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
		int totalRows = locationDao.totalRecord(whereClauses);
		if (totalRows > 0) {
			List<Location> rows = locationDao.findAll(orderClauses,
					whereClauses);
			List<LocationJsonData> djd = getJsonData(rows);
			JsonDataWrapper<LocationJsonData> jdw = new JsonDataWrapper<LocationJsonData>(
					page, totalRows, djd);
			return jdw;
		}
		return null;
	}

	private List<LocationJsonData> getJsonData(List<Location> rows) {
		List<LocationJsonData> locationJsonData = new ArrayList<LocationJsonData>();

		if (rows != null && rows.size() > 0) {
			for (Location location : rows) {
				LocationJsonData djd = new LocationJsonData();

				djd.setId(location.getId());
				djd.setName(location.getName());
				djd.setStatus(location.isStatus());
				djd.setDesc(location.getDesc());
				locationJsonData.add(djd);
			}
		}
		return locationJsonData;
	}

	/**
	 *
	 * @param location
	 * @param result
	 * @param model
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String saveLocation(
			@Valid @ModelAttribute(Constants.LOCATION) Location location,
			BindingResult result, Model model, WebRequest request,
			Map<String, Object> map, HttpSession session) {
		LOGGER.info("in Location Add values");
		locationValidator.validate(location, result);
		if (!result.hasErrors()) {

			if (location.getId() != null) {
				try {
					locationDao.updateLocation(location);
					session.setAttribute(Constants.SUCCESS_UPDATE,
							"location.updatedSuccessfully");
				} catch (Exception e) {
					LOGGER.error("Exception : ", e);
				} finally {
					return "redirect:/admin/location";
				}
			} else {
				// new location will be activate on addition
				locationDao.addLocation(location);
				session.setAttribute(Constants.SUCCESS_UPDATE,
						"location.addedSuccessfully");
			}

			return "redirect:/admin/location";
		} else {
			if (location != null && location.getId() != null) {
				map.put(Constants.OPERATION_TYPE, Constants.EDIT);
				map.put(Constants.BUTTON_NAME, Constants.UPDATE);
			} else {
				map.put(Constants.OPERATION_TYPE, Constants.ADD);
				map.put(Constants.BUTTON_NAME, Constants.SAVE);
			}
			return "admin/locationAdd";
		}
	}

	/**
	 * function to show add location page
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String locationsAdd(Model model, WebRequest request,
			Map<String, Object> map, HttpSession session,
			@RequestParam(required = false) String successUpdate) {
		LOGGER.info("in Location Add");
		if (null != request && null != request.getParameter(Constants.ID)) {

			try {
				Location location = locationDao.findById(Long.parseLong(request
						.getParameter(Constants.ID)));
				if (location == null) {
					session.setAttribute(Constants.SUCCESS_UPDATE,
							"record.notFound");
					return "redirect:/admin/location";
				}
				model.addAttribute(Constants.LOCATION, location);
				model.addAttribute("mode", Constants.EDIT);

				map.put(Constants.OPERATION_TYPE, Constants.EDIT);
				map.put(Constants.BUTTON_NAME, Constants.UPDATE);

			} catch (Exception e) {
				LOGGER.error(Constants.EXCEPTION, e);
			}

		} else {
			model.addAttribute(Constants.LOCATION, new Location());
			map.put(Constants.OPERATION_TYPE, Constants.ADD);
			map.put(Constants.BUTTON_NAME, Constants.SAVE);

		}

		return "admin/locationAdd";
	}

	/**
	 * function to change location status
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
			int updateCount = locationDao.changeStatus(id, bStatus);
			if (updateCount > 0) {
				response = true;
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);

		}
		return response;
	}

	/**
	 * function to set location as deleted (is_deleted =1)
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteLocation(WebRequest request) {
		LOGGER.info("in deleteLocation method");
		LOGGER.info(request.getParameter("items"));

		boolean response = false;

		try {
			int updateCount = locationDao.deleteLocation(stringToList(request
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
