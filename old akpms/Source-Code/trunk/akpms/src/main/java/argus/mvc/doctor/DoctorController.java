/**
 *
 */
package argus.mvc.doctor;

import java.io.IOException;
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

import argus.domain.Doctor;
import argus.repo.doctor.DoctorDao;
import argus.util.AkpmsUtil;
import argus.util.Constants;
import argus.util.DoctorJsonData;
import argus.util.JsonDataWrapper;
import argus.utility.TimeConverter;
import argus.validator.DoctorValidator;

/**
 * @author vishal.joshi
 *
 */
@Controller
@RequestMapping(value = "/admin/doctor")
@SessionAttributes({ Constants.DOCTOR })
public class DoctorController {

	private static final Logger LOGGER = Logger.getLogger(DoctorController.class);

	@Autowired
	private DoctorDao doctorDao;

	@Autowired
	private DoctorValidator doctorVaildator;

	@Autowired
	private MessageSource messageSource;

	/**
	 * This function just load jsp "admin/doctorList", the list will load using
	 * flexigrid list
	 *
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String doctorsList(Map<String, Object> map, WebRequest request,
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
		return "admin/doctorList";
	}

	/**
	 * function to use for flexigrid list
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	JsonDataWrapper<DoctorJsonData> listAllDoctors(WebRequest request) {
		LOGGER.info("doctor::in json method");

		int page = Constants.ONE; /* default 1 for page number in json wrapper */
		int rp = Constants.ZERO;

		Map<String, String> orderClauses = new HashMap<String, String>();
		Map<String, String> whereClauses = new HashMap<String, String>();

		if (request != null) {

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
					page = Integer.parseInt(request
							.getParameter(Constants.PAGE));
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
			if (request.getParameter(Constants.QTYPE) != null
					&& request.getParameter(Constants.QUERY) != null
					&& !request.getParameter(Constants.QUERY).isEmpty()) {
				whereClauses.put(request.getParameter(Constants.QTYPE),
						request.getParameter(Constants.QUERY));
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
					&& request.getParameter(Constants.STATUS).trim().length() > Constants.ZERO) {
				whereClauses.put(Constants.STATUS,
						request.getParameter(Constants.STATUS));
			}
		} else {
			LOGGER.info("request object is coming null");
		}

		int totalRows = Constants.ZERO;
		try {
			totalRows = doctorDao.totalRecord(whereClauses);
			if (totalRows > Constants.ZERO) {
				List<Doctor> rows = doctorDao.findAll(orderClauses,
						whereClauses, true);
				
				List<DoctorJsonData> djd = getJsonData(rows);
				JsonDataWrapper<DoctorJsonData> jdw = new JsonDataWrapper<DoctorJsonData>(
						page, totalRows, djd);
				return jdw;
			}
		} catch (Exception e) {
			LOGGER.error(e.toString());
		}

		return null;
	}

	/**
	 * this method set value in doctorjsondata list
	 *
	 * @param rows
	 * @return
	 */
	private List<DoctorJsonData> getJsonData(List<Doctor> rows) {
		List<DoctorJsonData> deptJsonData = new ArrayList<DoctorJsonData>();
		
		if (rows != null && rows.size() > Constants.ZERO) {

			for (Doctor doctor : rows) {
				doctor.setCreatedOn(TimeConverter.convertTime(doctor.getCreatedOn()));
				doctor.setModifiedOn(TimeConverter.convertTime(doctor.getModifiedOn()));
					
				DoctorJsonData djd = new DoctorJsonData();
				djd.setId(doctor.getId());
				djd.setName(doctor.getName());
				djd.setStatus(doctor.isStatus());
				djd.setDoctorCode(doctor.getDoctorCode());

				/* to remove children which have deleted status */
				if (doctor.getDoctors() != null
						&& doctor.getDoctors().size() > 0) {
					Iterator<Doctor> subDoctorIterator = doctor.getDoctors()
							.iterator();

					while (subDoctorIterator.hasNext()) {
						Doctor subDoctor = subDoctorIterator.next();
						if (subDoctor.isDeleted()) {
							subDoctorIterator.remove();
						}
					}
				}

				djd.setChildCount(new Long(doctor.getDoctors().size()));

				if (doctor.getParent() != null) {
					djd.setParentName(doctor.getParent().getName());
					djd.setParentStatus(Boolean.toString(doctor.getParent()
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
	 * function to show add doctor page
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String loadDoctorAdd(Model model, WebRequest request,
			HttpSession session, Map<String, Object> map) {

		if (null != request && null != request.getParameter(Constants.ID)) {
			Doctor doctor = null;
			try {
				doctor = doctorDao.findById(
						Long.parseLong(request.getParameter(Constants.ID)),
						true);
				if (doctor == null) {
					session.setAttribute(Constants.SUCCESS_UPDATE,
							"record.notFound");
					return "redirect:/admin/doctor";
				}

			} catch (Exception e) {
				LOGGER.error(e.toString());
			}

			/* to remove the children which has deleted status */
			if (doctor.getDoctors() != null
					&& doctor.getDoctors().size() > Constants.ZERO) {
				Iterator<Doctor> doctorIterator = doctor.getDoctors()
						.iterator();

				while (doctorIterator.hasNext()) {
					Doctor dpt = doctorIterator.next();
					if (dpt.isDeleted()) {
						doctorIterator.remove();
					}
				}
			}

			try {
				model.addAttribute("parentDoctors", doctorDao
						.findAllParentOrderedByName(new Long(request
								.getParameter("id"))));
			} catch (Exception e) {
				model.addAttribute("parentDoctors", null);
			}

			model.addAttribute("doctor", doctor);
			model.addAttribute("mode", "Edit");
			map.put("childCount", doctor.getDoctors().size());
			map.put("operationType", "Edit");
			map.put("buttonName", "Update");
		} else {
			try {
				model.addAttribute("parentDoctors",
						doctorDao.findAllParentOrderedByName(null));
			} catch (Exception e) {
				model.addAttribute("parentDoctors", null);
			}

			model.addAttribute("doctor", new Doctor());
			model.addAttribute("mode", "Add");
			map.put("operationType", "Add");
			map.put("childCount", 0);
			map.put("buttonName", "Save");
		}
		//check access type
		if (hasAccessExtraOptions()) {
			model.addAttribute(Constants.SHOW_EXTRA_OPTIONS, "block");
		} else {
			model.addAttribute(Constants.SHOW_EXTRA_OPTIONS, "none");
		}

		return "admin/doctorAdd";
	}

	/**
	 *
	 * @param doctor
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String processDoctorAdd(
			@Valid @ModelAttribute("doctor") Doctor doctor,
			BindingResult result, Model model, Map<String, Object> map,
			WebRequest request, HttpSession session) {

		doctorVaildator.validate(doctor, result);

		if (!result.hasErrors()) {
			LOGGER.info("no error");
			Doctor parent = null;
			if (null != doctor.getParent().getId()) {
				try {
					parent = doctorDao.findById(doctor.getParent().getId(),
							false);
				} catch (Exception e) {
					LOGGER.error(e.toString());
				}
			}

			try {
				doctor.setParent(parent);
				if (null != doctor.getId()) {
					doctorDao.updateDoctor(doctor);
					/*
					 * check if inactive doctor which has children also, then
					 * inactive its children as well
					 */
					LOGGER.info("dept status:: " + doctor.isStatus()
							+ " childCount :: "
							+ request.getParameter("childCount"));
					if (!doctor.isStatus()
							&& Integer.valueOf(request
									.getParameter("childCount")) > Constants.ZERO) {
						LOGGER.info("inactivate children as well");
						int updateCount = doctorDao.changeStatus(
								doctor.getId(), doctor.isStatus());
						LOGGER.info(updateCount + " children inactivated");
					}

					session.setAttribute(Constants.SUCCESS_UPDATE,
							"doctor.updatedSuccessfully");
				} else {
					doctorDao.addDoctor(doctor);
					session.setAttribute(Constants.SUCCESS_UPDATE,
							"doctor.addedSuccessfully");
				}
			} catch (Exception e) {
				LOGGER.error(e.toString());
			}

			return "redirect:/admin/doctor";
		} else {
			LOGGER.info("in error");
			if (null != doctor.getId()) {
				map.put("operationType", "Edit");
				map.put("buttonName", "Update");
				try {
					model.addAttribute("parentDoctors", doctorDao
							.findAllParentOrderedByName(doctor.getId()));
				} catch (Exception e) {
					model.addAttribute("parentDoctors", null);
				}

				map.put("childCount", request.getParameter("childCount"));
			} else {
				map.put("childCount", Constants.ZERO);
				map.put("operationType", "Add");
				map.put("buttonName", "Save");
				try {
					model.addAttribute("parentDoctors",
							doctorDao.findAllParentOrderedByName(null));
				} catch (Exception e) {
					model.addAttribute("parentDoctors", null);
				}
			}

			if (hasAccessExtraOptions()) {
				map.put(Constants.SHOW_EXTRA_OPTIONS, "block");
			} else {
				map.put(Constants.SHOW_EXTRA_OPTIONS, "none");
			}

			return "admin/doctorAdd";
		}
	}

	/**
	 * function to change doctor status
	 *
	 * @param id
	 * @param status
	 *            (1: activate. 0: inactivate)
	 * @return
	 */
	@RequestMapping(value = "/changeStatus", method = RequestMethod.GET)
	@ResponseBody
	public boolean changeDoctorStatus(@RequestParam int id,
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

			int updateCount = doctorDao.changeStatus(id, bStatus);
			if (updateCount > Constants.ZERO) {
				response = true;
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
		return response;
	}

	/**
	 * function to set doctor as deleted (is_deleted =1)
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteDoctor(WebRequest request) {
		LOGGER.info("in deleteDoctor method");
		LOGGER.info(request.getParameter("items"));

		boolean response = false;

		try {

			int updateCount = doctorDao.deleteDoctor(new Long(request
					.getParameter("item")));
			if (updateCount > Constants.ZERO) {
				response = true;
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
		return response;
	}

	private boolean hasAccessExtraOptions() {
		boolean hasAccess = false;
		/*
		 * ADMIN USER OR( standard user + p-13 (SUB_ADMIN_ACCOUNTING) +
		 * ACCOUNTING DEPARTMENT )
		 */
		if (((AkpmsUtil.getLoggedInUser().getRole().getId() == Constants.ADMIN_ROLE_ID))
				|| (AkpmsUtil.checkPermission(Constants.SUB_ADMIN_ACCOUNTING)
						&& (AkpmsUtil.getLoggedInUser().getRole().getId() == Constants.STANDART_USER_ROLE_ID) && (AkpmsUtil
							.checkDepartment(Long
									.valueOf(Constants.ACCOUNTING_DEPARTMENT_ID))))) {
			hasAccess = true;
		}

		return hasAccess;
	}
}
