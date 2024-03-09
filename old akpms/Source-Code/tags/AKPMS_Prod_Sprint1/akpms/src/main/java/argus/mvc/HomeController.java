package argus.mvc;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import argus.domain.AdminSettings;
import argus.domain.Department;
import argus.domain.EmailTemplate;
import argus.domain.Permission;
import argus.domain.Role;
import argus.domain.User;
import argus.exception.ArgusException;
import argus.repo.adminSettings.AdminSettingsDao;
import argus.repo.chargeProductivity.ChargeProdRejectDao;
import argus.repo.chargeProductivity.ChargeProductivityDao;
import argus.repo.doctor.DoctorDao;
import argus.repo.emailtemplate.EmailTemplateDao;
import argus.repo.insurance.InsuranceDao;
import argus.repo.payment.PaymentTypeDao;
import argus.repo.paymentbatch.PaymentBatchDao;
import argus.repo.paymentproductivity.PaymentProductivityDao;
import argus.repo.paymentproductivity.PaymentProductivityHourlyDao;
import argus.repo.paymentproductivity.PaymentProductivityRefundRequestDao;
import argus.repo.paymentproductivity.offset.PaymentOffsetManagerDao;
import argus.repo.processManual.ProcessManualDao;
import argus.repo.productivity.ArProductivityDao;
import argus.repo.role.RoleDao;
import argus.repo.traineeEvaluation.TraineeEvaluateDao;
import argus.repo.user.UserDao;
import argus.util.AkpmsUtil;
import argus.util.Constants;
import argus.util.SendMailUtil;
import argus.util.TraineeCalcaulation;
import argus.validator.ChangePasswordValidator;
import argus.validator.MyProfileValidator;

@SuppressWarnings("deprecation")
@Controller
@RequestMapping(value = "/")
@SessionAttributes({ Constants.CHANGE_PASSWORD, "profileCommand" })
public class HomeController {
	private static final Log LOGGER = LogFactory.getLog(HomeController.class);

	private static final int ZERO = 0;

	private static final int ONE = 1;

	private static final int TWO = 2;

	private static final int THREE = 3;

	private static final int FOUR = 4;

	private static final int FIVE = 5;

	private static final int SIX = 6;

	private static final int EIGHT = 8;

	private static final int NINE = 9;

	@Autowired
	private PaymentProductivityDao paymentProductivityDao;

	@Autowired
	private PaymentProductivityRefundRequestDao paymentProductivityRefundRequestDao;

	@Autowired
	private PaymentProductivityHourlyDao paymentProductivityHourlyDao;

	@Autowired
	private ArProductivityDao arProductivityDao;

	@Autowired
	private PaymentOffsetManagerDao paymentOffsetManagerDao;

	@Autowired
	private ChargeProdRejectDao chargeProdRejectDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private EmailTemplateDao emailTemplateDao;

	@Autowired
	private ChangePasswordValidator changePasswordValidator;

	@Autowired
	private MyProfileValidator myProfileValidator;

	@Autowired
	private ProcessManualDao processManualDao;

	@Autowired
	private TraineeEvaluateDao traineeEvaluateDao;

	@Autowired
	private AdminSettingsDao adminSettingsDao;

	@Autowired
	private DoctorDao doctorDao;

	@Autowired
	private InsuranceDao insuranceDao;

	@Autowired
	private PaymentTypeDao paymentTypeDao;

	@Autowired
	private ChargeProductivityDao chargeProductivityDao;

	@Autowired
	private PaymentBatchDao paymentBatchDao;

	/**
	 * Used to bind properties to model class
	 *
	 * @param binder
	 * @throws Exception
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) throws ArgusException {

		LOGGER.debug("in binding for emailtemplate");
		binder.registerCustomEditor(List.class, "emailTemplate",
				new CustomCollectionEditor(List.class) {
					@Override
					protected Object convertElement(Object element) {
						String emailTemplateId = (String) element;
						LOGGER.info("emailTemplate  id = " + emailTemplateId);
						EmailTemplate emailTemplate = new EmailTemplate();
						if (emailTemplateId != null) {
							LOGGER.info("found emailTemplate object");
							try {
								emailTemplate = emailTemplateDao.findById((Long
										.parseLong(emailTemplateId)));
							} catch (Exception e) {
								LOGGER.error(Constants.EXCEPTION, e);
							}
						}
						return emailTemplate;
					}
				});
	}

	@RequestMapping(value = "/login")
	public String showLogin(Map<String, Object> map, HttpSession session) {
		LOGGER.info("in login");
		String mailSend = (String) session.getAttribute("mailSend");
		if (mailSend != null) {
			map.put("mailSend",
					messageSource.getMessage(mailSend, null, Locale.ENGLISH)
							.trim());
		}
		session.removeAttribute("mailSend");
		return "login";
	}

	@RequestMapping()
	public String showDashboard(Map<String, Object> map) {
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		LOGGER.info("in dashboard");
		if (user.getRole().getId().longValue() == Constants.ADMIN_ROLE_ID
				.longValue()) {
			return "redirect:admin";
		} else {

			try {
				userDao.insertLoginDetails(user.getId());
				if (user.getRole().getId().longValue() == Constants.TRAINEE_ROLE_ID
						.longValue()) {
					List<Long> userDeptList = new ArrayList<Long>();
					for (Department dept : userDao.findById(
							Long.valueOf(user.getId()), true).getDepartments()) {
						userDeptList.add(dept.getId());
					}
					Long totalProcessManuals = 0L;
					List<Object[]> totalProcessManualsByDept = processManualDao
							.getProcessManualByDepartments(userDeptList);
					if (totalProcessManualsByDept.size() > ZERO) {
						totalProcessManuals = Long
								.valueOf(String
										.valueOf(totalProcessManualsByDept
												.size() - ONE));
						totalProcessManuals = totalProcessManuals
								+ Long.valueOf(totalProcessManualsByDept
										.get(totalProcessManualsByDept.size()
												- ONE)[ZERO].toString());
					}
					try {
						List<Object[]> traineeEvaluationReportDataList = traineeEvaluateDao
								.findEvaluationReportTrainees(user.getId());
						totalProcessManuals = processManualDao
								.getTotalProcessManuals();
						AdminSettings adminSettings = adminSettingsDao
								.getAdminSettings();
						for (Object[] trainee : traineeEvaluationReportDataList) {
							float idsArgusPercent = TraineeCalcaulation
									.calculateIdsArgusPercen(adminSettings,
											Float.parseFloat(trainee[EIGHT]
													.toString()));
							float argusPercent = TraineeCalcaulation
									.calculateArgusPercent(adminSettings, Float
											.parseFloat(trainee[FIVE]
													.toString()));
							float processManualReadPercent = TraineeCalcaulation
									.calculateProcessManualReadStatusPercent(
											adminSettings, Float
													.parseFloat(trainee[NINE]
															.toString()),
											totalProcessManuals);
							float traineePercent = TraineeCalcaulation
									.calculateTraineePercent(Float
											.parseFloat(trainee[EIGHT]
													.toString()), Float
											.parseFloat(trainee[FIVE]
													.toString()), Float
											.parseFloat(trainee[NINE]
													.toString()),
											totalProcessManuals, adminSettings);
							map.put("idsArgusPercent", idsArgusPercent);
							map.put("argusPercent", argusPercent);
							map.put("processManualReadPercent",
									processManualReadPercent);
							map.put("traineePercent", traineePercent);
						}
					} catch (Exception e) {
						LOGGER.error(Constants.EXCEPTION, e);
					}
				}

				if (AkpmsUtil.checkPermission("P-11")
						|| AkpmsUtil.checkPermission("P-12")) {

					List<Object[]> insuranceStats = null;
					List<Object[]> doctorStats = null;
					int totalInsurances = ZERO;
					int totalDoctors = ZERO;
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

					if (insuranceStats != null && insuranceStats.size() > ZERO) {
						for (Object[] stats : insuranceStats) {
							totalInsurances = totalInsurances
									+ ((BigInteger) stats[ONE]).intValue();
						}
						if (insuranceStats.size() == ONE) {
							if ((Boolean) insuranceStats.get(ZERO)[ZERO]) {
								map.put(Constants.ACTIVE_INSURANCES,
										insuranceStats.get(ZERO)[ONE]);
								map.put(Constants.INACTIVE_INSURANCES, ZERO);
							} else {
								map.put(Constants.INACTIVE_INSURANCES,
										insuranceStats.get(ZERO)[ONE]);
								map.put(Constants.ACTIVE_INSURANCES, ZERO);
							}
						} else if (insuranceStats.size() == TWO) {
							map.put(Constants.ACTIVE_INSURANCES,
									insuranceStats.get(ONE)[ONE]);
							map.put(Constants.INACTIVE_INSURANCES,
									insuranceStats.get(ZERO)[ONE]);
						}
						LOGGER.info("Total insurances =" + totalInsurances);
						map.put("totalInsurances", totalInsurances);
					} else {
						map.put("totalInsurances", ZERO);
						map.put(Constants.INACTIVE_INSURANCES, ZERO);
						map.put(Constants.ACTIVE_INSURANCES, ZERO);
					}

					if (doctorStats != null && doctorStats.size() > ZERO) {
						for (Object[] stats : doctorStats) {
							totalDoctors = totalDoctors
									+ ((BigInteger) stats[ONE]).intValue();
						}
						if (doctorStats.size() == ONE) {
							if ((Boolean) doctorStats.get(ZERO)[ZERO]) {
								map.put(Constants.ACTIVE_DOCTORS,
										doctorStats.get(ZERO)[ONE]);
								map.put(Constants.INACTIVE_DOCTORS, ZERO);
							} else {
								map.put(Constants.INACTIVE_DOCTORS,
										doctorStats.get(ZERO)[ONE]);
								map.put(Constants.ACTIVE_DOCTORS, ZERO);
							}
						} else if (doctorStats.size() == TWO) {
							map.put(Constants.ACTIVE_DOCTORS,
									doctorStats.get(ONE)[ONE]);
							map.put(Constants.INACTIVE_DOCTORS,
									doctorStats.get(ZERO)[ONE]);
						}
						LOGGER.info("Total Dctors =" + totalDoctors);
						map.put("totalDoctors", totalDoctors);
					} else {
						map.put("totalDoctors", ZERO);
						map.put(Constants.INACTIVE_DOCTORS, ZERO);
						map.put(Constants.ACTIVE_DOCTORS, ZERO);
					}

				}

				if (AkpmsUtil.checkPermission("P-12")) {

					List<Object[]> paymentTypeStats = null;
					int totalPaymentTypes = ZERO;
					try {
						paymentTypeStats = paymentTypeDao.getPaymentTypeStats();
					} catch (Exception e) {
						LOGGER.error(e.toString());
					}
					if (paymentTypeStats != null
							&& paymentTypeStats.size() > ZERO) {
						for (Object[] stats : paymentTypeStats) {
							totalPaymentTypes = totalPaymentTypes
									+ ((BigInteger) stats[ONE]).intValue();
						}
						if (paymentTypeStats.size() == ONE) {
							if ((Boolean) paymentTypeStats.get(ZERO)[ZERO]) {
								map.put(Constants.ACTIVE_PAYMENTTYPES,
										paymentTypeStats.get(ZERO)[ONE]);
								map.put(Constants.INACTIVE_PAYMENTTYPES, ZERO);
							} else {
								map.put(Constants.INACTIVE_PAYMENTTYPES,
										paymentTypeStats.get(ZERO)[ONE]);
								map.put(Constants.ACTIVE_PAYMENTTYPES, ZERO);
							}
						} else if (paymentTypeStats.size() == TWO) {
							map.put(Constants.ACTIVE_PAYMENTTYPES,
									paymentTypeStats.get(ONE)[ONE]);
							map.put(Constants.INACTIVE_PAYMENTTYPES,
									paymentTypeStats.get(ZERO)[ONE]);
						}
						LOGGER.info("Total PaymentTypes =" + totalPaymentTypes);
						map.put("totalPaymentTypes", totalPaymentTypes);
					} else {
						map.put("totalPaymentTypes", ZERO);
						map.put(Constants.INACTIVE_PAYMENTTYPES, ZERO);
						map.put(Constants.ACTIVE_PAYMENTTYPES, ZERO);
					}

				}

				LOGGER.info("in payment productivity controller");
				List<Object[]> paymentType = null;

				List<Object[]> workFlow = null;

				List<Object[]> arProdworkFlow = null;

				int totalpaymentType = ZERO;
				int era = ZERO;
				int nonEra = ZERO;
				int cap = ZERO;
				int refundRequest = ZERO;
				int hourly = ZERO;
				try {

					paymentType = paymentProductivityDao
							.getAllProductivityType();

					LOGGER.info("payment Type Size = " + paymentType.size());
					if (paymentType != null && paymentType.size() > ZERO) {
						for (Object[] type : paymentType) {
							totalpaymentType = totalpaymentType
									+ ((BigInteger) type[ONE]).intValue();

							if (((Integer) type[ZERO]) == ONE) {
								era = era + ((BigInteger) type[ONE]).intValue();
							}
							if (((Integer) type[ZERO]) == TWO) {
								nonEra = nonEra
										+ ((BigInteger) type[ONE]).intValue();
							}
							if (((Integer) type[ZERO]) == THREE) {
								cap = cap + ((BigInteger) type[ONE]).intValue();
							}
							if (((Integer) type[ZERO]) == FOUR) {
								refundRequest = refundRequest
										+ ((BigInteger) type[ONE]).intValue();
							}
							if (((Integer) type[ZERO]) == FIVE) {
								hourly = hourly
										+ ((BigInteger) type[ONE]).intValue();
							}
						}
						LOGGER.info("Total ERA =" + era);
						LOGGER.info("Total NON ERA =" + nonEra);
						map.put("era", era);
						map.put("nonEra", nonEra);
						map.put("cap", cap);
					} else {
						map.put("era", ZERO);
						map.put("nonEra", ZERO);
						map.put("cap", ZERO);
					}

					refundRequest = paymentProductivityRefundRequestDao
							.totalRecord(null);
					hourly = paymentProductivityHourlyDao.totalRecord(null);

				} catch (ArgusException ar) {
					LOGGER.info(Constants.EXCEPTION + " :: " + ar.getMessage());
				}

				int totalWorkFlowType = ZERO;
				int toArIpaFfsHmo = ZERO;
				int toArFfs = ZERO;
				int toArCep = ZERO;
				int toArMcl = ZERO;
				int offset = ZERO;
				int queryToTl = ZERO;
				int postedOffset = ZERO;
				long firstRequestDueCount = ZERO;
				long secondRequestDueCount = ZERO;
				long numberOfFirstRequestsCount = ZERO;
				long numberOfSecondRequestsCount = ZERO;
				long rejectionCount = ZERO;
				long resolvedRejectionCount = ZERO;
				long resolvedRejectionWithDummyCPTCount = ZERO;
				int onHold = 0;
				int totalOnHold = 0;
				int totalOffsetBatches = ZERO;
				try {
					postedOffset = paymentOffsetManagerDao
							.totalRecordForList(null);
					map.put("postedOffset", postedOffset);
					Map<String, String> whereClauses = new HashMap<String, String>();
					whereClauses.put(Constants.PAYMENT_TYPE_IDS,
							String.valueOf(Constants.PAYMENT_TYPE_OFFSET));
					totalOffsetBatches = paymentBatchDao
							.totalRecord(whereClauses);
					map.put("totalOffsetBatches", totalOffsetBatches);
					firstRequestDueCount = chargeProdRejectDao
							.getFirstRequestDueCount(null);
					LOGGER.info(Constants.FIRST_REQUEST_DUE_COUNT + " = "
							+ firstRequestDueCount);
					map.put(Constants.FIRST_REQUEST_DUE_COUNT,
							firstRequestDueCount);

					secondRequestDueCount = chargeProdRejectDao
							.getSecondRequestDueCount(null);
					LOGGER.info(Constants.SECOND_REQUEST_DUE_COUNT + " = "
							+ secondRequestDueCount);
					map.put(Constants.SECOND_REQUEST_DUE_COUNT,
							secondRequestDueCount);

					numberOfFirstRequestsCount = chargeProdRejectDao
							.getNumberOfFirstRequestsCount(null);
					LOGGER.info(Constants.NUMBER_OF_FIRST_REQUESTS_COUNT
							+ " = " + numberOfFirstRequestsCount);
					map.put(Constants.NUMBER_OF_FIRST_REQUESTS_COUNT,
							numberOfFirstRequestsCount);

					numberOfSecondRequestsCount = chargeProdRejectDao
							.getNumberOfSecondRequestsCount(null);
					LOGGER.info(Constants.NUMBER_OF_SECOND_REQUESTS_COUNT
							+ " = " + numberOfSecondRequestsCount);
					map.put(Constants.NUMBER_OF_SECOND_REQUESTS_COUNT,
							numberOfSecondRequestsCount);

					rejectionCount = chargeProdRejectDao
							.getRejectionCount(null);
					LOGGER.info(Constants.REJECTION_COUNT + " = "
							+ rejectionCount);
					map.put(Constants.REJECTION_COUNT, rejectionCount);

					resolvedRejectionCount = chargeProdRejectDao
							.getResolvedRejectionCount(null);
					LOGGER.info(Constants.RESOLVED_REJECTION_COUNT + " = "
							+ resolvedRejectionCount);
					map.put(Constants.RESOLVED_REJECTION_COUNT,
							resolvedRejectionCount);

					resolvedRejectionWithDummyCPTCount = chargeProdRejectDao
							.getResolvedWithDummyCPTCount(null);
					LOGGER.info(Constants.RESOLVED_REJECTION_WITH_DUMMY_CPT_COUNT
							+ " = " + resolvedRejectionWithDummyCPTCount);
					map.put(Constants.RESOLVED_REJECTION_WITH_DUMMY_CPT_COUNT,
							resolvedRejectionWithDummyCPTCount);
					onHold = chargeProductivityDao.totalOnHold();
					totalOnHold = chargeProductivityDao.totalOnHoldRecord();
					map.put("totalOnHold", totalOnHold);
					map.put("onHold", onHold);

				} catch (Exception e) {
					LOGGER.error(Constants.EXCEPTION, e);
				}

				try {
					workFlow = paymentProductivityDao
							.getAllProductivityByWorkFlow();
					offset = paymentProductivityDao.getTotalOffsetRecord();
					map.put("offset", offset);
				} catch (ArgusException ar) {
					LOGGER.info(Constants.EXCEPTION + " :: " + ar.getMessage());
				}

				LOGGER.info("Work Flow Size = " + workFlow.size());
				if (workFlow != null && workFlow.size() > ZERO) {
					for (Object[] flow : workFlow) {

						totalWorkFlowType = totalWorkFlowType
								+ ((BigInteger) flow[ONE]).intValue();

						if (((Integer) flow[ZERO]) == ONE) {
							toArIpaFfsHmo = toArIpaFfsHmo
									+ ((BigInteger) flow[ONE]).intValue();
						}
						if (((Integer) flow[ZERO]) == TWO) {
							toArFfs = toArFfs
									+ ((BigInteger) flow[ONE]).intValue();
						}
						if (((Integer) flow[ZERO]) == THREE) {
							toArCep = toArCep
									+ ((BigInteger) flow[ONE]).intValue();
						}
						if (((Integer) flow[ZERO]) == FOUR) {
							toArMcl = toArMcl
									+ ((BigInteger) flow[ONE]).intValue();
						}
						if (((Integer) flow[ZERO]) == SIX) {
							queryToTl = queryToTl
									+ ((BigInteger) flow[ONE]).intValue();
						}
					}

					LOGGER.info("Total offset =" + offset);
					LOGGER.info("Total queryToTl =" + queryToTl);

					map.put("toArIpaFfsHmo", toArIpaFfsHmo);
					map.put("toArFfs", toArFfs);
					map.put("toArCep", toArCep);
					map.put("toArMcl", toArMcl);
					map.put("queryToTl", queryToTl);

				} else {
					map.put("toArIpaFfsHmo", ZERO);
					map.put("toArFfs", ZERO);
					map.put("toArCep", ZERO);
					map.put("toArMcl", ZERO);
					map.put("queryToTl", ZERO);

				}

				map.put("refundRequest", refundRequest);
				map.put("hourly", hourly);

				try {
					int adjustLog = ZERO;
					int codingCorr = ZERO;
					int secondReq = ZERO;
					int rekeyReq = ZERO;
					int paymentPosting = ZERO;
					int checkTracer = ZERO;
					int toTL = ZERO;
					int arRefundRequest = ZERO;
					arProdworkFlow = arProductivityDao.countByWorkFlow();
					if (arProdworkFlow != null && arProdworkFlow.size() > ZERO) {
						for (Object[] arProd : arProdworkFlow) {
							if (arProd[ZERO]
									.equals(Constants.ADJUSTMENT_LOG_WORKFLOW)) {
								adjustLog = adjustLog
										+ ((BigInteger) arProd[ONE]).intValue();
							} else if (arProd[ZERO]
									.equals(Constants.CODING_CORRECTION_WORKFLOW)) {
								codingCorr = codingCorr
										+ ((BigInteger) arProd[ONE]).intValue();
							} else if (arProd[ZERO]
									.equals(Constants.SECOND_REQUEST_LOG_WORKFLOW)) {
								secondReq = secondReq
										+ ((BigInteger) arProd[ONE]).intValue();
							} else if (arProd[ZERO]
									.equals(Constants.REKEY_REQ_TO_CHARGE_POSTING_WORKFLOW)) {
								rekeyReq = rekeyReq
										+ ((BigInteger) arProd[ONE]).intValue();
							} else if (arProd[ZERO]
									.equals(Constants.PAYMENT_POSTING_LOG_WORKFLOW)) {
								paymentPosting = paymentPosting
										+ ((BigInteger) arProd[ONE]).intValue();
							} else if (arProd[ZERO]
									.equals(Constants.REQ_FOR_CHECK_TRACER_WORKFLOW)) {
								checkTracer = checkTracer
										+ ((BigInteger) arProd[ONE]).intValue();
							} else if (arProd[ZERO]
									.equals(Constants.QUERY_TO_TL_WORKFLOW)) {
								toTL = toTL
										+ ((BigInteger) arProd[ONE]).intValue();
							} else if (arProd[ZERO]
									.equals(Constants.REFUND_REQUEST_WORKFLOW)) {
								arRefundRequest = arRefundRequest
										+ ((BigInteger) arProd[ONE]).intValue();
							}
						}
					}
					map.put("adjustLog", adjustLog);
					map.put("codingCorr", codingCorr);
					map.put("secondReq", secondReq);
					map.put("rekeyReq", rekeyReq);
					map.put("paymentPosting", paymentPosting);
					map.put("checkTracer", checkTracer);
					map.put("toTL", toTL);
					map.put("refundRequest", arRefundRequest);
				} catch (Exception e) {
					LOGGER.error("Error", e);
				}
				User userPrinciple = (User) SecurityContextHolder.getContext()
						.getAuthentication().getPrincipal();
				List<Department> dept = new ArrayList<Department>();
				dept = userPrinciple.getDepartments();
				boolean credentialingAccounting = false;
				for (Department d : dept) {
					if (d.getId() == 4 || d.getId() == 5) {
						credentialingAccounting = true;
						map.put("credentialingAccounting",
								credentialingAccounting);
					}
				}

				// ///// producitivy end here //
				// ////////////////////////////////
			} catch (Exception e) {
				LOGGER.error(e.toString());
			}
			return "index";
		}

	}

	@RequestMapping(value = "/changepassword")
	public String loadChangePassword(Model model) {
		try {
			User user = (User) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			User userDB = userDao.findById(user.getId(), false);

			if (!userDB.isStatus() || userDB.isDeleted()) {
				return "redirect:/j_spring_security_logout";
			}

			LOGGER.info("in change password");
			model.addAttribute(Constants.CHANGE_PASSWORD,
					new ChangePasswordModel());
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
		return Constants.CHANGE_PASSWORD;
	}

	@RequestMapping(value = "/changepassword", method = RequestMethod.POST)
	public String processChangePassword(
			@ModelAttribute(Constants.CHANGE_PASSWORD) HomeController.ChangePasswordModel modelObj,
			BindingResult result, Model model, HttpServletRequest request) {
		LOGGER.info("got oldPassword through inner obj = "
				+ modelObj.getOldPassword());
		changePasswordValidator.validate(modelObj, result);
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		if (result.hasErrors()) {
			return Constants.CHANGE_PASSWORD;
		} else {
			try {
				user.setPassword(modelObj.getNewPassword());
				userDao.updateUser(user);
				model.addAttribute("success",
						"Password has changed successfully");
				EmailTemplate changePasswordTemplate = emailTemplateDao
						.findById(Constants.CHANGE_PASSWORD_TEMPLATE);
				if (changePasswordTemplate.isStatus()) {
					String to[] = { user.getEmail() };
					String changePassword = changePasswordTemplate.getContent();
					changePassword = changePassword.replace("USER_FIRST_NAME",
							user.getFirstName());
					changePassword = changePassword.replace("NEW_PASSWORD",
							modelObj.getNewPassword());
					String subject = changePasswordTemplate.getName();
					String emailForm = messageSource.getMessage(
							Constants.EMAIL_FROM, null, Locale.ENGLISH).trim();
					String emailHost = messageSource.getMessage(
							Constants.EMAIL_HOST, null, Locale.ENGLISH).trim();
					String emailPassword = messageSource.getMessage(
							Constants.EMAIL_PASSWORD, null, Locale.ENGLISH)
							.trim();
					try {
						SendMailUtil.sendMail(emailForm, to, emailHost,
								emailForm, emailPassword, subject,
								changePassword, null, null, null);
					} catch (Exception e) {

					}
				}
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
			return Constants.CHANGE_PASSWORD;
		}

	}

	@RequestMapping(value = "/myprofile", method = RequestMethod.GET)
	public String loadMyProfile(Model model, HttpSession session,
			Map<String, Object> map) {
		LOGGER.info("in my profile");

		User userPrinciple = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String success = (String) session
				.getAttribute(Constants.SUCCESS_UPDATE);
		if (success != null) {
			map.put("success",
					messageSource.getMessage(success, null, Locale.ENGLISH)
							.trim());
		}
		try {
			User user = userDao.findById(userPrinciple.getId(), true);

			if (!user.isStatus() || user.isDeleted()) {
				return "redirect:/j_spring_security_logout";
			}

			getUser(user);

			model.addAttribute("profileCommand", user);
			model.addAttribute("departments", user.getDepartments());
		} catch (Exception e) {
			/* We Need to show exception on Front end */
			LOGGER.error(Constants.EXCEPTION, e);
		}

		model.addAttribute("roles", roleDao.findAll(Constants.BY_NAME));
		model.addAttribute("role", userPrinciple.getRole());
		try {
			model.addAttribute(Constants.DEPENDANCY_USERS,
					userDao.findAll(Constants.FIRST_NAME));
		} catch (Exception e) {
			model.addAttribute(Constants.DEPENDANCY_USERS, null);
		}

		model.addAttribute("permissions", userPrinciple.getPermissions());
		model.addAttribute("emailTemplate",
				emailTemplateDao.findEmailTemplatesForSubscription());

		LOGGER.debug("out my profile");
		session.removeAttribute("successUpdate");
		return "myProfile";
	}

	private static void getUser(User user) {

		if (user.getDepartments() != null
				&& user.getDepartments().size() > ZERO) {
			Iterator<Department> deptIter = user.getDepartments().iterator();
			while (deptIter.hasNext()) {
				Department dept = deptIter.next();
				if (!dept.isStatus() || dept.isDeleted()) {
					deptIter.remove();
				}
			}
		}
	}

	@RequestMapping(value = "/myprofile", method = RequestMethod.POST)
	public String processMyProfile(@ModelAttribute("profileCommand") User user,
			BindingResult result, Map<String, Object> map, Model model,
			HttpSession session) {
		LOGGER.info("Going for Update profile");
		LOGGER.info("User Id after Post=" + user.getId());

		myProfileValidator.validate(user, result);

		User userPrinciple = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		User userDB = null;

		try {
			userDB = userDao.findById(userPrinciple.getId(), true);

			if (!userDB.isStatus() || userDB.isDeleted()) {
				return "redirect:/j_spring_security_logout";
			}

			if (result.hasErrors()) {

				model.addAttribute("role", userPrinciple.getRole());
				model.addAttribute("departments", userDB.getDepartments());
				model.addAttribute("permissions",
						userPrinciple.getPermissions());
				model.addAttribute("emailTemplate",
						emailTemplateDao.findEmailTemplatesForSubscription());

				return "myProfile";
			}

			user.setId(userDB.getId());
			user.setRole(userDB.getRole());
			user.setDepartments(userDB.getDepartments());
			user.setRole(userDB.getRole());
			user.setPassword(userDB.getPassword());
			user.setStatus(userDB.isStatus());
			user.setToken(userDB.getToken());
			user.setDeleted(userDB.isDeleted());
			user.setPermissions(userDB.getPermissions());

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}

		try {
			userDao.updateUser(user);
			Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
			Role role = user.getRole();
			grantedAuthorities.add(new GrantedAuthorityImpl(role.getName()));
			List<Permission> pemissionList = user.getPermissions();
			for (Permission permission : pemissionList) {
				grantedAuthorities.add(new GrantedAuthorityImpl(permission
						.getName()));
			}
			SecurityContextHolder.getContext().setAuthentication(
					new UsernamePasswordAuthenticationToken(user, user
							.getPassword(), grantedAuthorities));
			session.setAttribute(Constants.SUCCESS_UPDATE,
					"profile.updateSuccessfully");
		} catch (Exception e) {

			LOGGER.error(Constants.EXCEPTION, e);
		}
		LOGGER.info("Going for Update profile");
		return "redirect:/myprofile";

	}

	public static class ChangePasswordModel {

		private String oldPassword;

		private String newPassword;

		private String confirmPassword;

		public ChangePasswordModel() {
		}

		public ChangePasswordModel(String oldPassword, String newPassword,
				String confirmPassword) {
			this.oldPassword = oldPassword;
			this.newPassword = newPassword;
			this.confirmPassword = confirmPassword;
		}

		public String getOldPassword() {
			return oldPassword;
		}

		public void setOldPassword(String oldPassword) {
			this.oldPassword = oldPassword;
		}

		public String getNewPassword() {
			return newPassword;
		}

		public void setNewPassword(String newPassword) {
			this.newPassword = newPassword;
		}

		public String getConfirmPassword() {
			return confirmPassword;
		}

		public void setConfirmPassword(String confirmPassword) {
			this.confirmPassword = confirmPassword;
		}
	}

	@RequestMapping(value = "/error404")
	public String get404() {
		LOGGER.info("in errorr 404 method");
		return "error-404";
	}

	@RequestMapping(value = "/error403")
	public String get401() {
		LOGGER.info("in errorr 403 access-denied method");
		return "accessDenied";
	}
}
