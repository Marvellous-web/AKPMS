package argus.mvc;

import java.math.BigInteger;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

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
import org.springframework.web.bind.annotation.SessionAttributes;

import argus.domain.AdminSettings;
import argus.domain.User;
import argus.repo.adminSettings.AdminSettingsDao;
import argus.repo.department.DepartmentDao;
import argus.repo.doctor.DoctorDao;
import argus.repo.insurance.InsuranceDao;
import argus.repo.payment.PaymentTypeDao;
import argus.repo.user.UserDao;
import argus.util.AkpmsUtil;
import argus.util.Constants;
import argus.validator.AdminSettingsValidator;

@Controller
@RequestMapping(value = "/admin")
@SessionAttributes({ "evaluationSettings" })
public class AdminController {
	private static final Log LOGGER = LogFactory.getLog(AdminController.class);

	@Autowired
	private DepartmentDao departmentDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private AdminSettingsDao adminSettingsDao;

	@Autowired
	private AdminSettingsValidator adminSettingsValidator;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private DoctorDao doctorDao;

	@Autowired
	private InsuranceDao insuranceDao;

	@Autowired
	private PaymentTypeDao paymentTypeDao;

	@RequestMapping(value = "/admin")
	public String showHome(Map<String, Object> map) {
		LOGGER.info("in admin home controller");
		User user = AkpmsUtil.getLoggedInUser();
		if (user.getRole().getId().longValue() == Constants.ADMIN_ROLE_ID
				.longValue()) {
			int totalDepts = Constants.ZERO;
			List<Object[]> departmentStats = null;
			try {
				departmentStats = departmentDao.getDepartmentStats();
			} catch (Exception e) {
				LOGGER.error(e.toString());
			}

			LOGGER.info("deptStats Size = " + departmentStats.size());
			if (departmentStats != null
					&& departmentStats.size() > Constants.ZERO) {
				for (Object[] stats : departmentStats) {
					totalDepts = totalDepts
							+ ((BigInteger) stats[Constants.ONE]).intValue();

				}
				LOGGER.info("Total Departments =" + totalDepts);

				if (departmentStats.size() == Constants.ONE) {
					if ((Boolean) departmentStats.get(Constants.ZERO)[Constants.ZERO]) {
						map.put(Constants.ACTIVE_DEPTS,
								departmentStats.get(Constants.ZERO)[Constants.ONE]);
						map.put(Constants.INACTIVE_DEPTS, Constants.ZERO);
					} else {
						map.put(Constants.INACTIVE_DEPTS,
								departmentStats.get(Constants.ZERO)[Constants.ONE]);
						map.put(Constants.ACTIVE_DEPTS, Constants.ZERO);
					}
				} else if (departmentStats.size() == Constants.TWO) {
					map.put(Constants.ACTIVE_DEPTS,
							departmentStats.get(Constants.ONE)[Constants.ONE]);
					map.put(Constants.INACTIVE_DEPTS,
							departmentStats.get(Constants.ZERO)[Constants.ONE]);
				}
				map.put("deptStats", departmentStats);
				map.put("totalDepts", totalDepts);
			} else {
				map.put("totalDepts", Constants.ZERO);
				map.put(Constants.INACTIVE_DEPTS, Constants.ZERO);
				map.put(Constants.ACTIVE_DEPTS, Constants.ZERO);
			}

			List<Object[]> usersStats = null;
			try {
				usersStats = userDao.getUserStats();
			} catch (Exception e) {
				LOGGER.error(e.toString());
			}

			int totalUsers = Constants.ZERO;
			LOGGER.info("usersStats Size = " + usersStats.size());
			if (usersStats != null && usersStats.size() > Constants.ZERO) {
				for (Object[] stats : usersStats) {
					totalUsers = totalUsers
							+ ((BigInteger) stats[Constants.ONE]).intValue();
				}
				if (usersStats.size() == Constants.ONE) {
					if ((Boolean) usersStats.get(Constants.ZERO)[Constants.ZERO]) {
						map.put(Constants.ACTIVE_USERS,
								usersStats.get(Constants.ZERO)[Constants.ONE]);
						map.put(Constants.INACTIVE_USERS, Constants.ZERO);
					} else {
						map.put(Constants.INACTIVE_USERS,
								usersStats.get(Constants.ZERO)[Constants.ONE]);
						map.put(Constants.ACTIVE_USERS, Constants.ZERO);
					}
				} else if (usersStats.size() == Constants.TWO) {
					map.put(Constants.ACTIVE_USERS,
							usersStats.get(Constants.ONE)[Constants.ONE]);
					map.put(Constants.INACTIVE_USERS,
							usersStats.get(Constants.ZERO)[Constants.ONE]);
				}
				LOGGER.info("Total Users =" + totalUsers);
				map.put("usersStats", usersStats);
				map.put("totalUsers", totalUsers);
			} else {
				map.put("totalUsers", Constants.ZERO);
				map.put(Constants.INACTIVE_USERS, Constants.ZERO);
				map.put(Constants.ACTIVE_USERS, Constants.ZERO);
			}



			List<Object[]> insuranceStats = null;
			List<Object[]> doctorStats = null;
			int totalInsurances = Constants.ZERO;
			int totalDoctors = Constants.ZERO;
			try {
				insuranceStats = insuranceDao.getInsuranceStats();
			} catch (Exception e) {
				LOGGER.error(e.toString());
			}
			try {
				doctorStats = doctorDao.getDoctorStats();
			} catch (Exception e) {
				LOGGER.error(e.toString());
			}

			if (insuranceStats != null
					&& insuranceStats.size() > Constants.ZERO) {
				for (Object[] stats : insuranceStats) {
					totalInsurances = totalInsurances
							+ ((BigInteger) stats[Constants.ONE]).intValue();
				}
				if (insuranceStats.size() == Constants.ONE) {
					if ((Boolean) insuranceStats.get(Constants.ZERO)[Constants.ZERO]) {
						map.put(Constants.ACTIVE_INSURANCES,
								insuranceStats.get(Constants.ZERO)[Constants.ONE]);
						map.put(Constants.INACTIVE_INSURANCES, Constants.ZERO);
					} else {
						map.put(Constants.INACTIVE_INSURANCES,
								insuranceStats.get(Constants.ZERO)[Constants.ONE]);
						map.put(Constants.ACTIVE_INSURANCES, Constants.ZERO);
					}
				} else if (insuranceStats.size() == Constants.TWO) {
					map.put(Constants.ACTIVE_INSURANCES,
							insuranceStats.get(Constants.ONE)[Constants.ONE]);
					map.put(Constants.INACTIVE_INSURANCES,
							insuranceStats.get(Constants.ZERO)[Constants.ONE]);
				}
				LOGGER.info("Total insurances =" + totalInsurances);
				map.put("totalInsurances", totalInsurances);
			} else {
				map.put("totalInsurances", Constants.ZERO);
				map.put(Constants.INACTIVE_INSURANCES, Constants.ZERO);
				map.put(Constants.ACTIVE_INSURANCES, Constants.ZERO);
			}

			if (doctorStats != null && doctorStats.size() > Constants.ZERO) {
				for (Object[] stats : doctorStats) {
					totalDoctors = totalDoctors
							+ ((BigInteger) stats[Constants.ONE]).intValue();
				}
				if (doctorStats.size() == Constants.ONE) {
					if ((Boolean) doctorStats.get(Constants.ZERO)[Constants.ZERO]) {
						map.put(Constants.ACTIVE_DOCTORS,
								doctorStats.get(Constants.ZERO)[Constants.ONE]);
						map.put(Constants.INACTIVE_DOCTORS, Constants.ZERO);
					} else {
						map.put(Constants.INACTIVE_DOCTORS,
								doctorStats.get(Constants.ZERO)[Constants.ONE]);
						map.put(Constants.ACTIVE_DOCTORS, Constants.ZERO);
					}
				} else if (doctorStats.size() == Constants.TWO) {
					map.put(Constants.ACTIVE_DOCTORS,
							doctorStats.get(Constants.ONE)[Constants.ONE]);
					map.put(Constants.INACTIVE_DOCTORS,
							doctorStats.get(Constants.ZERO)[Constants.ONE]);
				}
				LOGGER.info("Total Dctors =" + totalDoctors);
				map.put("totalDoctors", totalDoctors);
			} else {
				map.put("totalDoctors", Constants.ZERO);
				map.put(Constants.INACTIVE_DOCTORS, Constants.ZERO);
				map.put(Constants.ACTIVE_DOCTORS, Constants.ZERO);
			}



			List<Object[]> paymentTypeStats = null;
			int totalPaymentTypes = Constants.ZERO;
			try {
				paymentTypeStats = paymentTypeDao.getPaymentTypeStats();
			} catch (Exception e) {
				LOGGER.error(e.toString());
			}
			if (paymentTypeStats != null
					&& paymentTypeStats.size() > Constants.ZERO) {
				for (Object[] stats : paymentTypeStats) {
					totalPaymentTypes = totalPaymentTypes
							+ ((BigInteger) stats[Constants.ONE]).intValue();
				}
				if (paymentTypeStats.size() == Constants.ONE) {
					if ((Boolean) paymentTypeStats.get(Constants.ZERO)[Constants.ZERO]) {
						map.put(Constants.ACTIVE_PAYMENTTYPES,
								paymentTypeStats.get(Constants.ZERO)[Constants.ONE]);
						map.put(Constants.INACTIVE_PAYMENTTYPES, Constants.ZERO);
					} else {
						map.put(Constants.INACTIVE_PAYMENTTYPES,
								paymentTypeStats.get(Constants.ZERO)[Constants.ONE]);
						map.put(Constants.ACTIVE_PAYMENTTYPES, Constants.ZERO);
					}
				} else if (paymentTypeStats.size() == Constants.TWO) {
					map.put(Constants.ACTIVE_PAYMENTTYPES,
							paymentTypeStats.get(Constants.ONE)[Constants.ONE]);
					map.put(Constants.INACTIVE_PAYMENTTYPES,
							paymentTypeStats.get(Constants.ZERO)[Constants.ONE]);
				}
				LOGGER.info("Total PaymentTypes =" + totalPaymentTypes);
				map.put("totalPaymentTypes", totalPaymentTypes);
			} else {
				map.put("totalPaymentTypes", Constants.ZERO);
				map.put(Constants.INACTIVE_PAYMENTTYPES, Constants.ZERO);
				map.put(Constants.ACTIVE_PAYMENTTYPES, Constants.ZERO);
			}


		}

		LOGGER.info("going to load index page");
		return "admin/index";
	}

	@RequestMapping(value = "/evaluationsettings", method = RequestMethod.GET)
	public String getAdminSettings(Model model, HttpSession session) {

		String success = (String) session
				.getAttribute(Constants.SUCCESS_UPDATE);
		if (success != null) {
			model.addAttribute("success",
					messageSource.getMessage(success, null, Locale.ENGLISH)
							.trim());
		}
		session.removeAttribute(Constants.SUCCESS_UPDATE);

		try {
			AdminSettings adminSettings = adminSettingsDao.getAdminSettings();
			model.addAttribute("evaluationSettings", adminSettings);
		} catch (Exception e) {
			LOGGER.info("No result found for Admin Settings", e);
		}

		return "admin/evaluationSettings";
	}

	@RequestMapping(value = "/evaluationsettings", method = RequestMethod.POST)
	public String updateAdminSettings(
			@ModelAttribute("evaluationSettings") AdminSettings adminSettings,
			BindingResult result, HttpSession session) {
		adminSettingsValidator.validate(adminSettings, result);
		if (result.hasErrors()) {
			return "admin/evaluationSettings";
		}

		double proceesManaulReadStatusRatings = (double) Double
				.parseDouble(adminSettings.getProceesManaulReadStatusRatings());
		double argusRatings = (double) Double.parseDouble(adminSettings
				.getArgusRatings());
		double idsArgusRatings = (double) Double.parseDouble(adminSettings
				.getIdsArgusRatings());

		if ((proceesManaulReadStatusRatings + argusRatings + idsArgusRatings) != Constants.HUNDRED) {
			result.rejectValue("argusRatings", "ratings.exceedMaximum");
			LOGGER.info("evaluation settings in error");
			return "admin/evaluationSettings";
		}

		try {
			adminSettings.setId(Constants.ONE);
			adminSettingsDao.updateAdminSettings(adminSettings);
		} catch (Exception e) {
			LOGGER.info("No result found for Admin Settings", e);
		}
		session.setAttribute(Constants.SUCCESS_UPDATE,
				"evaluationsettings.updatedSuccessfully");

		return "redirect:evaluationsettings";
	}
}
