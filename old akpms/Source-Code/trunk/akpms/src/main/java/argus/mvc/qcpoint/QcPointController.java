package argus.mvc.qcpoint;

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
import argus.domain.QcPoint;
import argus.exception.ArgusException;
import argus.repo.department.DepartmentDao;
import argus.repo.qcpoint.QcPointDao;
import argus.util.Constants;
import argus.util.JsonDataWrapper;
import argus.util.QcPointJsonData;
import argus.validator.QcPointValidator;

@Controller
@RequestMapping(value = "/admin/qcpoint")
@SessionAttributes({ "qcPoint" })
public class QcPointController {

	private static final Logger LOGGER = Logger
			.getLogger(QcPointController.class);

	@Autowired
	private QcPointDao qcPointDao;

	@Autowired
	private DepartmentDao departmentDao;

	@Autowired
	private QcPointValidator qcPointVaildator;

	@Autowired
	private MessageSource messageSource;

	/**
	 * This function just load jsp "admin/qcPointList", the list will load using
	 * flexigrid list
	 *
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String qcPointsList(Map<String, Object> map, WebRequest request,
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

		try {
			map.put("departmentList",
					this.departmentDao.findAllParentOrderedByName());
		} catch (Exception e) {
			map.put("departmentList", null);
			LOGGER.error(Constants.EXCEPTION, e);
		}

		return "admin/qcPointList";
	}

	/**
	 * function to use for flexigrid list
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	JsonDataWrapper<QcPointJsonData> listAllQcPoints(WebRequest request) {
		LOGGER.info("qcManager::in json method");

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

			if (request.getParameter(Constants.DEPARTMENT_ID) != null
					&& request.getParameter(Constants.DEPARTMENT_ID).length() > 0) {
				whereClauses.put(Constants.DEPARTMENT_ID,
						request.getParameter(Constants.DEPARTMENT_ID));
			}
		} else {
			LOGGER.info("request object is coming null");
		}
		int totalRows = 0;
		try {
			totalRows = qcPointDao.totalRecord(whereClauses);
			if (totalRows > 0) {
				List<QcPoint> rows = qcPointDao.findAll(orderClauses,
						whereClauses, true);
				List<QcPointJsonData> djd = getJsonData(rows);
				JsonDataWrapper<QcPointJsonData> jdw = new JsonDataWrapper<QcPointJsonData>(
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
	 * this method set value in qcPointjsondata list
	 *
	 * @param rows
	 * @return
	 */
	private List<QcPointJsonData> getJsonData(List<QcPoint> rows) {
		List<QcPointJsonData> qcPointJsonData = new ArrayList<QcPointJsonData>();

		if (rows != null && rows.size() > 0) {

			for (QcPoint qcPoint : rows) {
				QcPointJsonData djd = new QcPointJsonData();
				djd.setId(qcPoint.getId());
				djd.setName(qcPoint.getName());
				djd.setStatus(qcPoint.isStatus());

				try {
					djd.setDepartment(departmentDao.findById(
							qcPoint.getDepartment().getId(), true).getName());
				} catch (ArgusException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// to remove children which have deleted status
				if (qcPoint.getQcPoints() != null
						&& qcPoint.getQcPoints().size() > 0) {
					Iterator<QcPoint> subQcPointIterator = qcPoint
							.getQcPoints().iterator();

					while (subQcPointIterator.hasNext()) {
						QcPoint subQcPoint = subQcPointIterator.next();
						if (subQcPoint.isDeleted()) {
							subQcPointIterator.remove();
						}
					}
				}

				djd.setChildCount(new Long(qcPoint.getQcPoints().size()));

				if (qcPoint.getParent() != null) {
					djd.setParentName(qcPoint.getParent().getName());
					djd.setParentStatus(Boolean.toString(qcPoint.getParent()
							.isStatus()));
				} else {
					djd.setParentName("--");
					djd.setParentStatus(Constants.STRING_TWO);
				}
				qcPointJsonData.add(djd);
			}
		}
		return qcPointJsonData;
	}

	/**
	 * function to show add qcPoint page
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String loadQcPointAdd(Model model, WebRequest request,
			HttpSession session, Map<String, Object> map) {

		if (request.getParameter(Constants.ID) != null) {
			try {
				model.addAttribute("subQcPoints", qcPointDao
						.findAllParentOrderedByName(new Long(request
								.getParameter(Constants.ID))));
			} catch (Exception e) {
				model.addAttribute("subQcPoints", null);
				LOGGER.error(Constants.EXCEPTION, e);
			}
			try {
				model.addAttribute("departmentList",
						departmentDao.findAllParentOrderedByName());
			} catch (Exception e) {
				model.addAttribute("departmentList", null);
				LOGGER.error(Constants.EXCEPTION, e);
			}

		} else {
			try {
				model.addAttribute("subQcPoints",
						qcPointDao.findAllParentOrderedByName());
			} catch (Exception e) {
				model.addAttribute("subQcPoints", null);
				LOGGER.error(Constants.EXCEPTION, e);
			}

			try {
				model.addAttribute("departmentList",
						departmentDao.findAllParentOrderedByName());
			} catch (Exception e) {
				model.addAttribute("departmentList", null);
				LOGGER.error(Constants.EXCEPTION, e);
			}
		}

		if (null != request && null != request.getParameter("id")) {
			QcPoint qcPoint = null;
			try {
				qcPoint = qcPointDao.findById(
						Long.parseLong(request.getParameter("id")), true);

				if (qcPoint == null) {
					session.setAttribute(Constants.SUCCESS_UPDATE,
							"record.notFound");
					return "redirect:/admin/qcpoint";
				} else {
					if (null != qcPoint.getSubDepartment()
							&& null != qcPoint.getSubDepartment().getId()) {
						map.put("subDepartmentId", qcPoint.getSubDepartment()
								.getId());
					}
				}

			} catch (Exception e) {
				LOGGER.error(e.toString());
			}

			/* to remove the children which has deleted status */
			if (qcPoint.getQcPoints() != null
					&& qcPoint.getQcPoints().size() > 0) {
				Iterator<QcPoint> qcPointIterator = qcPoint.getQcPoints()
						.iterator();

				while (qcPointIterator.hasNext()) {
					QcPoint dpt = qcPointIterator.next();
					if (dpt.isDeleted()) {
						qcPointIterator.remove();
					}
				}
			}

			model.addAttribute("qcPoint", qcPoint);
			model.addAttribute("mode", "Edit");
			map.put("childCount", qcPoint.getQcPoints().size());
			map.put("operationType", "Edit");
			map.put("buttonName", "Update");
		} else {
			model.addAttribute("qcPoint", new QcPoint());
			model.addAttribute("mode", "Add");
			map.put("operationType", "Add");
			map.put("childCount", 0);
			map.put("buttonName", "Save");
		}
		return "admin/qcPointAdd";
	}

	/**
	 *
	 * @param qcPoint
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String processQcPointAdd(
			@Valid @ModelAttribute("qcPoint") QcPoint qcPoint,
			BindingResult result, Model model, Map<String, Object> map,
			WebRequest request, HttpSession session) {

		qcPointVaildator.validate(qcPoint, result);

		if (!result.hasErrors()) {
			QcPoint parent = null;
			if (null != qcPoint.getParent().getId()) {
				try {
					parent = qcPointDao.findById(qcPoint.getParent().getId(),
							false);
				} catch (Exception e) {
					LOGGER.error(e.toString());
				}
			}

			Department subDepartment = null;
			if (null != qcPoint.getSubDepartment()
					&& null != qcPoint.getSubDepartment().getId()) {
				try {
					subDepartment = departmentDao.findById(qcPoint
							.getSubDepartment().getId(), false);
				} catch (Exception e) {
					LOGGER.error(e.toString());
				}
			}

			try {
				qcPoint.setParent(parent);
				qcPoint.setSubDepartment(subDepartment);

				if (null != qcPoint.getId()) {
					qcPointDao.updateQcPoint(qcPoint);
					/*
					 * check if inactive qcPoint which has children also, then
					 * inactive its children as well
					 */
					LOGGER.info("Qc point status:: " + qcPoint.isStatus()
							+ " childCount :: "
							+ request.getParameter("childCount"));
					if (!qcPoint.isStatus()
							&& Integer.valueOf(request
									.getParameter("childCount")) > Constants.ZERO) {
						LOGGER.info("inactivate children as well");
						int updateCount = qcPointDao.changeStatus(
								qcPoint.getId(), qcPoint.isStatus());
						LOGGER.info(updateCount + " children inactivated");
					}

					session.setAttribute(Constants.SUCCESS_UPDATE,
							"qcPoint.updatedSuccessfully");
				} else {
					qcPointDao.addQcPoint(qcPoint);
					session.setAttribute(Constants.SUCCESS_UPDATE,
							"qcPoint.addedSuccessfully");
				}
			} catch (Exception e) {
				LOGGER.error(Constants.EXCEPTION, e);
			}

			return "redirect:/admin/qcpoint";
		} else {
			if (null != qcPoint.getId()) {
				map.put(Constants.OPERATION_TYPE, Constants.EDIT);
				map.put(Constants.BUTTON_NAME, Constants.UPDATE);
				try {
					model.addAttribute("subQcPoints", qcPointDao
							.findAllParentOrderedByName(qcPoint.getId()));
				} catch (Exception e) {
					model.addAttribute("subQcPoints", null);
				}
				try {
					model.addAttribute("departmentList",
							departmentDao.findAllParentOrderedByName());
				} catch (Exception e) {
					model.addAttribute("departmentList", null);
					LOGGER.error(Constants.EXCEPTION, e);
				}
				map.put("childCount", request.getParameter("childCount"));
			} else {
				map.put("childCount", Constants.ZERO);
				map.put(Constants.OPERATION_TYPE, Constants.ADD);
				map.put(Constants.BUTTON_NAME, Constants.SAVE);
				try {
					model.addAttribute("subQcPoints",
							qcPointDao.findAllParentOrderedByName());
				} catch (Exception e) {
					model.addAttribute("subQcPoints", null);
				}
				try {
					model.addAttribute("departmentList",
							departmentDao.findAllParentOrderedByName());
				} catch (Exception e) {
					model.addAttribute("departmentList", null);
					LOGGER.error(Constants.EXCEPTION, e);
				}
			}

			return "admin/qcPointAdd";
		}
	}

	/**
	 * function to change qcPoint status
	 *
	 * @param id
	 * @param status
	 *            (1: activate. 0: inactivate)
	 * @return
	 */
	@RequestMapping(value = "/changeStatus", method = RequestMethod.GET)
	@ResponseBody
	public boolean changeQcPointStatus(@RequestParam int id,
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
			int updateCount = qcPointDao.changeStatus(id, bStatus);
			if (updateCount > Constants.ZERO) {
				response = true;
			}

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
		return response;
	}

	/**
	 * function to set qcPoint as deleted (is_deleted =1)
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteQcPoint(WebRequest request) {
		LOGGER.info("in deleteQcPoint method");
		LOGGER.info(request.getParameter("items"));

		boolean response = false;

		try {

			int updateCount = qcPointDao.deleteQcPoint(new Long(request
					.getParameter("item")));
			if (updateCount > 0) {
				response = true;
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
		return response;
	}
}
