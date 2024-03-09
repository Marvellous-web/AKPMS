package argus.mvc;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;

import argus.domain.AdminSettings;
import argus.domain.Department;
import argus.domain.EmailTemplate;
import argus.domain.TraineeEvaluate;
import argus.domain.User;
import argus.mvc.user.UserController;
import argus.repo.adminSettings.AdminSettingsDao;
import argus.repo.department.DepartmentDao;
import argus.repo.emailtemplate.EmailTemplateDao;
import argus.repo.processManual.ProcessManualDao;
import argus.repo.traineeEvaluation.TraineeEvaluateDao;
import argus.repo.user.UserDao;
import argus.util.Constants;
import argus.util.EmailUtil;
import argus.util.JsonDataWrapper;
import argus.util.TraineeCalcaulation;
import argus.util.UserJsonData;

@Controller
@RequestMapping(value = "/traineeevaluation")
@SessionAttributes({ "traineeEvaluate" })
public class TraineeEvaluationController {

	private static final Logger LOGGER = Logger
			.getLogger(TraineeEvaluationController.class);

	@Autowired
	private DepartmentDao departmentDao;

	@Autowired
	private AdminSettingsDao adminSettingsDao;

	@Autowired
	private TraineeEvaluateDao traineeEvaluateDao;

	@Autowired
	private EmailTemplateDao emailTemplateDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private ProcessManualDao processManualDao;

	@Autowired
	private EmailUtil emailUtil;

	@InitBinder
	protected void initBinder(WebDataBinder binder) throws Exception {

		/*
		 * "permissions" is the name of the property that we want to register a
		 * custom editor to you can set property name null and it means you want
		 * to register this editor for occurrences of List class in * command
		 * object
		 */

		LOGGER.debug("in binding for departments");
		binder.registerCustomEditor(List.class, "departments",
				new CustomCollectionEditor(List.class) {
					@Override
					protected Object convertElement(Object element) {
						String departmentId = (String) element;
						LOGGER.info("department id = " + departmentId);
						Department department = new Department();
						if (departmentId != null) {
							LOGGER.info("found permission object");
							try {
								department = departmentDao.findById(
										(Long.parseLong(departmentId)), false);
							} catch (Exception e) {
								LOGGER.error(Constants.EXCEPTION, e);
							}
						}
						return department;
					}
				});
	}

	@RequestMapping(method = RequestMethod.GET)
	public String userList(Map<String, Object> map,
			@RequestParam(required = false) String successUpdate) {
		if (successUpdate != null && successUpdate.equals("1")) {
			map.put("success", "User updated Successfully");
		} else if (successUpdate != null && successUpdate.equals("2")) {
			map.put("success", "User added Successfully");
		}
		try {
			Map<String, List<Department>> deptMap = UserController
					.departmentParentChildFormation(departmentDao
							.getDepartmentsWithParentIdAndChildCount());
			map.put("departments", deptMap);
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
		return "traineeList";
	}

	@RequestMapping(value = "/json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	JsonDataWrapper<UserJsonData> listAllDepartments(WebRequest request) {
		LOGGER.info("user::in json method");

		int page = 1; // default 1 for page number in json wrapper
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
			whereClauses.put(Constants.SELECTED_ROLES_IDS, "4");
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

			if (request.getParameter(Constants.SELECTED_DEPARTMENTS_IDS) != null
					&& request.getParameter(Constants.SELECTED_DEPARTMENTS_IDS)
							.trim().length() > Constants.ZERO) {
				String selectedDepartmentsIds = request
						.getParameter(Constants.SELECTED_DEPARTMENTS_IDS);
				whereClauses.put(Constants.SELECTED_DEPARTMENTS_IDS,
						selectedDepartmentsIds);
			}
			if (request.getParameter(Constants.SELECTED_ROLES_IDS) != null
					&& request.getParameter(Constants.SELECTED_ROLES_IDS)
							.trim().length() > Constants.ZERO) {

				String selectedRolesIds = request
						.getParameter(Constants.SELECTED_ROLES_IDS);
				LOGGER.info("found role id = " + selectedRolesIds);
				whereClauses
						.put(Constants.SELECTED_ROLES_IDS, selectedRolesIds);
			}
			if (request.getParameter(Constants.KEYWORD) != null
					&& request.getParameter(Constants.KEYWORD).trim()
							.length() > Constants.ZERO) {
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

		} else {
			LOGGER.info("request object is coming null");
		}

		int totalRows = Constants.ZERO;
		try {
			totalRows = traineeEvaluateDao.totalRecord(whereClauses);
		} catch (Exception e) {
			LOGGER.error(e.toString());
		}

		if (totalRows > Constants.ZERO) {
			try {
				List<Object[]> rows = traineeEvaluateDao.findAll(orderClauses,
						whereClauses);
				Long totalProcessManuals = processManualDao
						.getTotalProcessManuals();
				LOGGER.info("total process manuals: " + totalProcessManuals);
				List<UserJsonData> djd = getJsonData(rows, totalProcessManuals);

				JsonDataWrapper<UserJsonData> jdw = new JsonDataWrapper<UserJsonData>(
						page, totalRows, djd);
				return jdw;
			} catch (Exception e) {

				LOGGER.error(e.toString());
			}

		}

		return null;
	}

	private List<UserJsonData> getJsonData(List<Object[]> rows,
			Long totalProcessManuals) {
		List<UserJsonData> deptJsonData = new ArrayList<UserJsonData>();
		if (rows != null && rows.size() > Constants.ZERO) {
			for (Object[] user : rows) {
				List<Long> userDeptList = new ArrayList<Long>();
				try {
					for (Department dept : userDao.findById(
							Long.parseLong(user[2].toString()), true)
							.getDepartments()) {
						userDeptList.add(dept.getId());
					}
				} catch (Exception e) {
					LOGGER.error(Constants.EXCEPTION, e);
				}
				totalProcessManuals = 0L;
				List<Object[]> totalProcessManualsByDept = null;
				try {
					totalProcessManualsByDept = processManualDao
							.getProcessManualByDepartments(userDeptList);
				} catch (Exception e) {
					LOGGER.error(Constants.EXCEPTION, e);
				}
				if (totalProcessManualsByDept.size() > Constants.ZERO) {
					totalProcessManuals = Long.valueOf(String
							.valueOf(totalProcessManualsByDept.size() - 1));
					totalProcessManuals = totalProcessManuals
							+ Long.valueOf(totalProcessManualsByDept
									.get(totalProcessManualsByDept.size() - 1)[Constants.ZERO]
									.toString());
				}
				UserJsonData ujd = new UserJsonData();
				ujd.setId(Long.parseLong(user[2].toString()));
				ujd.setFirstName(user[Constants.ZERO].toString());
				ujd.setLastName(user[1].toString());

				if (null == user[Constants.THREE]) {
					ujd.setLastEvaluatedOn("");
				} else {
					ujd.setLastEvaluatedOn(user[Constants.THREE].toString());
				}

				ujd.setLastEvaluatedBy(user[Constants.FOUR].toString());
				ujd.setDepartmentNames(user[Constants.FIVE].toString());
				try {
					AdminSettings adminSettings = adminSettingsDao
							.getAdminSettings();

					float traineePercent = TraineeCalcaulation
							.calculateTraineePercent(Float
									.parseFloat(user[Constants.ELEVEN]
											.toString()), Float
									.parseFloat(user[Constants.EIGHT]
											.toString()), Float
									.parseFloat(user[Constants.TWELVE]
											.toString()), totalProcessManuals,
									adminSettings);
					ujd.setTraineePercent(traineePercent);
				} catch (Exception e) {
					LOGGER.error(e.toString());
				}
				deptJsonData.add(ujd);
				LOGGER.info("deptJsonData ADDED");
			}
		}
		return deptJsonData;
	}

	@RequestMapping(value = "/evaluatetrainee", method = RequestMethod.GET)
	public String loadEvaluateTrainee(WebRequest request,
			Map<String, Object> map) {
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String traineeid = request.getParameter("tid");
		AdminSettings adminSettings = new AdminSettings();
		TraineeEvaluate currentEvaluation = new TraineeEvaluate();
		try {
			adminSettings = adminSettingsDao.getAdminSettings();
			map.put("adminSettings", adminSettings);
			List<TraineeEvaluate> traineeIDSArgusEvaluationList = traineeEvaluateDao
					.getEvaluationList(Long.valueOf(traineeid),
							Constants.TYPE_IDS_TL);
			List<TraineeEvaluate> traineeArgusEvaluationList = traineeEvaluateDao
					.getEvaluationList(Long.valueOf(traineeid),
							Constants.TYPE_ARGUS_TL);
			float totalIDSRatings = 0.0f;
			DecimalFormat df = new DecimalFormat("#.##");
			if (traineeIDSArgusEvaluationList.size() > Constants.ZERO) {
				for (TraineeEvaluate traineeEvaluation : traineeIDSArgusEvaluationList) {
					if (traineeEvaluation.getRating() == null) {
						totalIDSRatings = totalIDSRatings + Constants.ZERO;
						traineeEvaluation.setRating(0f);
					} else {
						totalIDSRatings = totalIDSRatings
								+ traineeEvaluation.getRating();
					}
				}

				float avgIDSRating = Float.valueOf(df.format(totalIDSRatings
						/ traineeIDSArgusEvaluationList.size()));
				map.put("avgIDSRating", avgIDSRating);
				float idsArgusPercent = TraineeCalcaulation
						.calculateIdsArgusPercen(adminSettings,
								(avgIDSRating / Constants.FIVE)
										* Constants.HUNDRED);
				map.put("idsArgusPercent", idsArgusPercent);
			} else {
				map.put("avgIDSRating", Constants.ZERO);
				map.put("idsArgusPercent", Constants.ZERO);
			}
			if (traineeArgusEvaluationList.size() > Constants.ZERO) {
				float totalArgusRatings = 0.0f;
				for (TraineeEvaluate traineeEvaluation : traineeArgusEvaluationList) {
					if (traineeEvaluation.getRating() == null) {
						totalArgusRatings = totalArgusRatings + Constants.ZERO;
						traineeEvaluation.setRating(0f);
					} else {
						totalArgusRatings = totalArgusRatings
								+ traineeEvaluation.getRating();
					}

					// traineeEvaluation.setModifiedOn(AkpmsUtil
					// .akpmsDateFormat(traineeEvaluation.getModifiedOn()
					// .toString()));
				}
				float avgArgusRating = Float.valueOf(df
						.format(totalArgusRatings
								/ traineeArgusEvaluationList.size()));
				map.put("avgArgusRating", avgArgusRating);
				float argusPercent = TraineeCalcaulation.calculateArgusPercent(
						adminSettings, (avgArgusRating / 5) * 100);
				map.put("argusPercent", argusPercent);
			} else {
				map.put("avgArgusRating", Constants.ZERO);
				map.put("argusPercent", Constants.ZERO);
			}
			List<Long> userDeptList = new ArrayList<Long>();
			for (Department dept : userDao.findById(Long.valueOf(traineeid),
					true).getDepartments()) {
				userDeptList.add(dept.getId());
			}

			Long totalProcessManuals = 0L;
			List<Object[]> totalProcessManualsByDept = processManualDao
					.getProcessManualByDepartments(userDeptList);
			if (totalProcessManualsByDept.size() > Constants.ZERO) {
				totalProcessManuals = Long.valueOf(String
						.valueOf(totalProcessManualsByDept.size() - 1));
				totalProcessManuals = totalProcessManuals
						+ Long.valueOf(totalProcessManualsByDept
								.get(totalProcessManualsByDept.size() - 1)[Constants.ZERO]
								.toString());
			}
			Long totalProcessManualsReadByUser = processManualDao
					.getTotalProcessManualReadByUser(Long.valueOf(traineeid));
			float processManualReadPercent = 0.0f;
			if (totalProcessManuals != null) {
				processManualReadPercent = (totalProcessManualsReadByUser
						.floatValue() / totalProcessManuals.floatValue())
						* Constants.HUNDRED;
				processManualReadPercent = TraineeCalcaulation
						.calculateProcessManualReadStatusPercent(adminSettings,
								totalProcessManualsReadByUser.floatValue(),
								totalProcessManuals);
			}
			map.put("totalProcessManuals", totalProcessManuals);
			map.put("totalProcessManualsReadByUser",
					totalProcessManualsReadByUser);
			map.put("processManulReadPercent", processManualReadPercent);
			map.put("traineeIDSArgusEvaluationList",
					traineeIDSArgusEvaluationList);
			map.put("traineeArgusEvaluationList", traineeArgusEvaluationList);

			currentEvaluation = traineeEvaluateDao.getCurrentEvaluation(
					Long.valueOf(traineeid), user.getId());
			if (currentEvaluation.getId() != null) {
				map.put("operation", "Revaluate");
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION + e);
			map.put("traineeEvaluate", currentEvaluation);
			map.put("adminSettings", adminSettings);
			map.put("operation", "Evaluate");
			return "evaluateTrainee";
		}
		map.put("traineeEvaluate", currentEvaluation);
		map.put("adminSettings", adminSettings);

		return "evaluateTrainee";
	}

	@RequestMapping(value = "/evaluatetrainee", method = RequestMethod.POST)
	public String processEvaluateTrainee(
			@ModelAttribute("traineeEvaluate") TraineeEvaluate traineeEvaluate,
			WebRequest request, Map<String, Object> map) {
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String traineeid = request.getParameter("tid");
		traineeEvaluate.setRatedBy(user);
		traineeEvaluate.setTraineeId(Long.valueOf(traineeid));
		traineeEvaluate.setModifiedOn(new Date());
		traineeEvaluate.setModifiedBy(user.getId());
		if (traineeEvaluate.getRating() == null) {
			traineeEvaluate.setRating(0f);
		}

		try {
			if (traineeEvaluate.getId() != null) {
				traineeEvaluateDao.updateTraineeEvaluation(traineeEvaluate);
			} else {
				traineeEvaluateDao.addTraineeEvaluation(traineeEvaluate);
			}
		} catch (Exception e) {
			LOGGER.error(e);
		}

		try {
			AdminSettings adminSettings = adminSettingsDao.getAdminSettings();
			map.put("adminSettings", adminSettings);
			EmailTemplate traineeEvaluateTemplate = emailTemplateDao
					.findById(Constants.TRAINEE_EVALUATION_TEMPLATE);
			if (traineeEvaluateTemplate.isStatus()) {
				List<User> evaluationSubscriberUsers = traineeEvaluateTemplate
						.getUsers();

				if (evaluationSubscriberUsers != null) {
					List<String> to = new ArrayList<String>();
					for (User userToMail : evaluationSubscriberUsers) {
						if (userToMail.isStatus() && !userToMail.isDeleted()) {
							to.add(userToMail.getEmail());
						}
					}

					LOGGER.info("Users count who will get mail:" + to.isEmpty());

					if (!to.isEmpty()) {
						// String emailForm = messageSource.getMessage(
						// Constants.EMAIL_FROM, null, Locale.ENGLISH)
						// .trim();
						// String emailHost = messageSource.getMessage(
						// Constants.EMAIL_HOST, null, Locale.ENGLISH)
						// .trim();
						// String emailPassword = messageSource.getMessage(
						// Constants.EMAIL_PASSWORD, null, Locale.ENGLISH)
						// .trim();
						String subject = traineeEvaluateTemplate.getName();
						String emailTemplate = traineeEvaluateTemplate
								.getContent();
						emailTemplate = emailTemplate.replace("EVALUATOR_NAME",
								traineeEvaluate.getRatedBy().getFirstName());
						emailTemplate = emailTemplate.replace(
								"TRAINEE_NAME",
								userDao.findById(
										traineeEvaluate.getTraineeId(), false)
										.getFirstName());
						emailTemplate = emailTemplate.replace("TRAINEE_RATING",
								traineeEvaluate.getRating().toString());
						emailTemplate = emailTemplate
								.replace("TRAINEE_COMMENT",
										traineeEvaluate.getComment());

						/******* send email start ***********/
						// EmailUtil emailUtil = new EmailUtil();
						emailUtil.setSubject(subject);
						emailUtil.setContent(emailTemplate);
						emailUtil.setType("html");
						emailUtil.setTo(to);
						emailUtil.sendEmail();
						/******* send email end ***********/

						// String stringArrayTO[] = to.toArray(new String[to
						// .size()]);
						// SendMailUtil.sendMail(emailForm, stringArrayTO,
						// emailHost, emailForm, emailPassword, subject,
						// emailTemplate, null, null, null);
					} else {
						LOGGER.info("There is no recipient available");
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
		return "redirect:/traineeevaluation/evaluatetrainee?tid=" + traineeid;

	}
}
