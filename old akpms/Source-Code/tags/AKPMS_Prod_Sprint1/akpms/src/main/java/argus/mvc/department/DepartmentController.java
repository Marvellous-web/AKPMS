package argus.mvc.department;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

import argus.domain.Department;
import argus.repo.department.DepartmentDao;
import argus.util.Constants;
import argus.util.DepartmentJsonData;
import argus.util.JsonDataWrapper;
import argus.validator.DepartmentVaildator;

@Controller
@RequestMapping(value = "/admin/department")
@SessionAttributes({ "department" })
public class DepartmentController {

	private static final Log LOGGER = LogFactory.getLog(Department.class);

	@Autowired
	private DepartmentDao departmentDao;

	@Autowired
	private DepartmentVaildator departmentVaildator;

	@Autowired
	private MessageSource messageSource;

	/**
	 * This function just load jsp "admin/departmentList", the list will load
	 * using flexigrid list
	 *
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String departmentsList(Map<String, Object> map, WebRequest request,
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
		return "admin/departmentList";
	}

	/**
	 * function to use for flexigrid list
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	JsonDataWrapper<DepartmentJsonData> listAllDepartments(WebRequest request) {
		LOGGER.info("department::in json method");

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
			if (request.getParameter(Constants.KEYWORD) != null
					&& request.getParameter(Constants.KEYWORD).trim()
							.length() > 0) {
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
		} else{
			LOGGER.info("request object is coming null");
		}
		int totalRows = 0;
		try {
			totalRows = departmentDao.totalRecord(whereClauses);
			if (totalRows > 0) {
				List<Department> rows = departmentDao.findAll(orderClauses,
						whereClauses, true);
				List<DepartmentJsonData> djd = getJsonData(rows);
				JsonDataWrapper<DepartmentJsonData> jdw = new JsonDataWrapper<DepartmentJsonData>(
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
	 * this method set value in departmentjsondata list
	 *
	 * @param rows
	 * @return
	 */
	private List<DepartmentJsonData> getJsonData(List<Department> rows) {
		List<DepartmentJsonData> deptJsonData = new ArrayList<DepartmentJsonData>();

		if (rows != null && rows.size() > 0) {

			for (Department department : rows) {
				DepartmentJsonData djd = new DepartmentJsonData();
				djd.setId(department.getId());
				djd.setName(department.getName());
				djd.setStatus(department.isStatus());

				// to remove children which have deleted status
				if (department.getDepartments() != null
						&& department.getDepartments().size() > 0) {
					Iterator<Department> subDepartmentIterator = department
							.getDepartments().iterator();

					while (subDepartmentIterator.hasNext()) {
						Department subDepartment = subDepartmentIterator.next();
						if (subDepartment.isDeleted()) {
							subDepartmentIterator.remove();
						}
					}
				}

				djd.setChildCount(new Long(department.getDepartments().size()));

				if (department.getParent() != null) {
					djd.setParentName(department.getParent().getName());
					djd.setParentStatus(Boolean.toString(department.getParent()
							.isStatus()));
				} else {
					djd.setParentName("--");
					djd.setParentStatus(Constants.STRING_TWO);
				}
				deptJsonData.add(djd);
			}
		}
		return deptJsonData;
	}

	/**
	 * function to show add department page
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String loadDepartmentAdd(Model model, WebRequest request,
			HttpSession session,
			Map<String, Object> map) {

		if (request.getParameter(Constants.ID) != null) {
			try {
				model.addAttribute("subDepartments", departmentDao
						.findAllParentOrderedByName(new Long(request
								.getParameter(Constants.ID))));
			} catch (Exception e) {
				model.addAttribute("subDepartments", null);
				LOGGER.error(Constants.EXCEPTION, e);
			}

		} else {
			try {
				model.addAttribute("subDepartments",
						departmentDao.findAllParentOrderedByName());
			} catch (Exception e) {
				model.addAttribute("subDepartments", null);
				LOGGER.error(Constants.EXCEPTION, e);
			}
		}

		if (null != request && null != request.getParameter("id")) {
			Department department = null;
			try {
				department = departmentDao.findById(
						Long.parseLong(request.getParameter("id")), true);
				if (department == null) {
					session.setAttribute(Constants.SUCCESS_UPDATE,
							"record.notFound");
					return "redirect:/admin/department";
				}

			} catch (Exception e) {
				LOGGER.error(e.toString());
			}

			/* to remove the children which has deleted status */
			if (department.getDepartments() != null
					&& department.getDepartments().size() > 0) {
				Iterator<Department> departmentIterator = department
						.getDepartments().iterator();

				while (departmentIterator.hasNext()) {
					Department dpt = departmentIterator.next();
					if (dpt.isDeleted()) {
						departmentIterator.remove();
					}
				}
			}

			model.addAttribute("department", department);
			model.addAttribute("mode", "Edit");
			map.put("childCount", department.getDepartments().size());
			map.put("operationType", "Edit");
			map.put("buttonName", "Update");
		} else {
			model.addAttribute("department", new Department());
			model.addAttribute("mode", "Add");
			map.put("operationType", "Add");
			map.put("childCount", 0);
			map.put("buttonName", "Save");
		}
		return "admin/departmentAdd";
	}

	/**
	 *
	 * @param department
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String processDepartmentAdd(
			@Valid @ModelAttribute("department") Department department,
			BindingResult result, Model model, Map<String, Object> map,
			WebRequest request, HttpSession session) {

		departmentVaildator.validate(department, result);

		if (!result.hasErrors()) {
			Department parent = null;
			if (null != department.getParent().getId()) {
				try {
					parent = departmentDao.findById(department.getParent()
							.getId(), false);
				} catch (Exception e) {
					LOGGER.error(e.toString());
				}
			}

			try {
				department.setParent(parent);
				if (null != department.getId()) {
					departmentDao.updateDepartment(department);
					/*
					 * check if inactive department which has children also,
					 * then inactive its children as well
					 */
					LOGGER.info("dept status:: " + department.isStatus()
							+ " childCount :: "
							+ request.getParameter("childCount"));
					if (!department.isStatus()
							&& Integer.valueOf(request
									.getParameter("childCount")) > Constants.ZERO) {
						LOGGER.info("inactivate children as well");
						int updateCount = departmentDao.changeStatus(
								department.getId(), department.isStatus());
						LOGGER.info(updateCount + " children inactivated");
					}

					session.setAttribute(Constants.SUCCESS_UPDATE,
							"department.updatedSuccessfully");
				} else {
					departmentDao.addDepartment(department);
					session.setAttribute(Constants.SUCCESS_UPDATE,
							"department.addedSuccessfully");
				}
			} catch (Exception e) {
				LOGGER.error(Constants.EXCEPTION, e);
			}

			return "redirect:/admin/department";
		} else {
			if (null != department.getId()) {
				map.put(Constants.OPERATION_TYPE, Constants.EDIT);
				map.put(Constants.BUTTON_NAME, Constants.UPDATE);
				try {
					model.addAttribute("subDepartments", departmentDao
							.findAllParentOrderedByName(department.getId()));
				} catch (Exception e) {
					model.addAttribute("subDepartments", null);
				}

				map.put("childCount", request.getParameter("childCount"));
			} else {
				map.put("childCount", Constants.ZERO);
				map.put(Constants.OPERATION_TYPE, Constants.ADD);
				map.put(Constants.BUTTON_NAME, Constants.SAVE);
				try {
					model.addAttribute("subDepartments",
							departmentDao.findAllParentOrderedByName());
				} catch (Exception e) {
					model.addAttribute("subDepartments", null);
				}

			}

			return "admin/departmentAdd";
		}
	}

	/**
	 * function to change department status
	 *
	 * @param id
	 * @param status
	 *            (1: activate. 0: inactivate)
	 * @return
	 */
	@RequestMapping(value = "/changeStatus", method = RequestMethod.GET)
	@ResponseBody
	public boolean changeDepartmentStatus(@RequestParam int id,
			@RequestParam int status) {
		LOGGER.info("in changeStatus method");
		LOGGER.info("id = " + id);
		LOGGER.info("status = " + status);

		boolean response = false;

		try {
			boolean bStatus = false;
			if (status == Constants.ONE) {
				bStatus = true;
			}
			int updateCount = departmentDao.changeStatus(id, bStatus);
			if (updateCount > Constants.ZERO) {
				response = true;
			}

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
		return response;
	}

	/**
	 * function to set department as deleted (is_deleted =1)
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteDepartment(WebRequest request) {
		LOGGER.info("in deleteDepartment method");
		LOGGER.info(request.getParameter("items"));

		boolean response = false;

		try {

			int updateCount = departmentDao.deleteDepartment(new Long(request
					.getParameter("item")));
			if (updateCount > 0){
				response = true;
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
		return response;
	}

}
