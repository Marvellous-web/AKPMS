package argus.mvc.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import argus.domain.Department;
import argus.domain.EmailTemplate;
import argus.domain.Permission;
import argus.domain.Role;
import argus.domain.User;
import argus.repo.department.DepartmentDao;
import argus.repo.emailtemplate.EmailTemplateDao;
import argus.repo.permission.PermissionDao;
import argus.repo.role.RoleDao;
import argus.repo.user.UserDao;
import argus.util.AkpmsUtil;
import argus.util.Constants;
import argus.util.JsonDataWrapper;
import argus.util.SendMailUtil;
import argus.util.UserJsonData;
import argus.validator.UserValidator;

@Controller
@RequestMapping(value = "/admin/user")
@SessionAttributes({ "user" })
public class UserController {

	private static final Log LOGGER = LogFactory.getLog(UserController.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private DepartmentDao departmentDao;

	@Autowired
	private PermissionDao permissionDao;

	@Autowired
	private EmailTemplateDao emailTemplateDao;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private MessageSource messageSource;

	/**
	 * Used to bind properties to model class
	 *
	 * @param binder
	 * @throws Exception
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) throws Exception {

		/*
		 * "permissions" is the name of the property that we want to register a
		 * custom editor to you can set property name null and it means you want
		 * to register this editor for occurrences of List class in * command
		 * object
		 */
		LOGGER.debug("in binding for permission");
		binder.registerCustomEditor(List.class, "permissions",
				new CustomCollectionEditor(List.class) {
					@Override
					protected Object convertElement(Object element) {
						String permissionId = (String) element;
						LOGGER.info("permission id = " + permissionId);
						Permission permission = new Permission();
						if (permissionId != null) {
							LOGGER.info("found permission object");
							try {
								permission = permissionDao
										.findById(permissionId);
							} catch (Exception e) {
								LOGGER.error(Constants.EXCEPTION, e);
							}
						}
						return permission;
					}
				});
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

	/**
	 * function to get user list
	 * 
	 * @param map
	 * @param session
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String userList(Map<String, Object> map, HttpSession session,
			WebRequest request) {
		map.put("roles", roleDao.findAll("name"));
		String success = (String) session
				.getAttribute(Constants.SUCCESS_UPDATE);
		if (success != null) {
			map.put("success",
					messageSource.getMessage(success, null, Locale.ENGLISH)
							.trim());
		}

		try {
			Map<String, List<Department>> departmentMap = departmentParentChildFormation(departmentDao
					.getDepartmentsWithParentIdAndChildCount());
			map.put("departments", departmentMap);
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
		if (request.getParameter(Constants.STATUS) != null) {
			map.put(Constants.STATUS, request.getParameter(Constants.STATUS));
		}
		session.removeAttribute(Constants.SUCCESS_UPDATE);
		return "admin/userList";
	}

	/**
	 * 
	 * This method Handle ADD and EDIT requests. It helps in page designing and
	 * also fetch User related data IF it is an EDIT request.
	 * 
	 * @param model
	 * @param request
	 * @param map
	 * @return 'It returns view name'
	 * 
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String loadUserAdd(Model model, WebRequest request,
			Map<String, Object> map, HttpSession session,
			@RequestParam(required = false) String successUpdate) {
		LOGGER.debug("in usereditpage");

		if (null != request && null != request.getParameter("id")) {
			try {
				User user = userDao.findById(
						Long.parseLong(request.getParameter("id")), true);

				if (user == null) {
					session.setAttribute(Constants.SUCCESS_UPDATE,
							"record.notFound");
					return "redirect:/admin/user";
				}

				model.addAttribute("user", user);
				request.setAttribute(Constants.REQUEST_ATTRIBUTE_PASSWORD,
						user.getPassword(), RequestAttributes.SCOPE_SESSION);
				request.setAttribute(Constants.REQUEST_ATTRIBUTE_CREATED_BY,
						user.getCreatedBy(), RequestAttributes.SCOPE_SESSION);
				map.put("operationType", "Edit");
				map.put("buttonName", "Update");
				map.put("userRole", user.getRole().getId().toString());
			} catch (Exception e) {
				/* We Need to show exception on Front end */
				model.addAttribute("user", new User());
				LOGGER.error(Constants.EXCEPTION, e);
			}
		} else {
			/* set default role as standard user */
			User user = new User();
			Role role = new Role();
			role.setId(Constants.STANDART_USER_ROLE_ID);
			user.setRole(role);
			model.addAttribute("user", user);
			map.put("operationType", "Add");
			map.put("buttonName", "Save");
			map.put("userRole", Constants.STANDART_USER_ROLE_ID.toString());
		}

		model.addAttribute("roles", roleDao.findAll("name"));
		model.addAttribute("traineeUserRole",
				Constants.TRAINEE_ROLE_ID.toString());
		model.addAttribute("permissions", permissionDao.findAll("name"));
		LOGGER.info("Permission Count::" + permissionDao.findAll("name").size());

		model.addAttribute("emailTemplate",
				emailTemplateDao.findEmailTemplatesForSubscription());
		LOGGER.info("emailTemplate Count::"
				+ emailTemplateDao.findEmailTemplatesForSubscription().size());

		try {
			Map<String, List<Department>> deptMap = departmentParentChildFormation(departmentDao
					.getDepartmentsWithParentIdAndChildCount());
			model.addAttribute("departments", deptMap);
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}

		LOGGER.debug("out usereditpage");
		return "admin/userAdd";
	}

	/**
	 *
	 * This method can handle both ADD and EDIT operation requests. It will
	 * perform ADD or EDIT operation based on condition IF User ID is available.
	 * *
	 *
	 * @param user
	 * @param result
	 * @param model
	 * @param request
	 * @return 'It returns view name'
	 *
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String processUserAdd(HttpServletRequest servletRequest,
			@Valid @ModelAttribute("user") User user, BindingResult result,
			Model model, WebRequest request, Map<String, Object> map,
			HttpSession session) {
		userValidator.validate(user, result);

		if (!result.hasErrors()) {

			if (user != null && user.getId() != null) {
				try {
					if (request != null) {
						String password = (String) request.getAttribute(
								Constants.REQUEST_ATTRIBUTE_PASSWORD,
								RequestAttributes.SCOPE_SESSION);
						request.removeAttribute(
								Constants.REQUEST_ATTRIBUTE_PASSWORD,
								RequestAttributes.SCOPE_SESSION);
						request.removeAttribute(
								Constants.REQUEST_ATTRIBUTE_CREATED_BY,
								RequestAttributes.SCOPE_SESSION);
						user.setPassword(password);
					}
					Role role = roleDao.findById(user.getRole().getId());
					user.setRole(role);
					userDao.updateUser(user);
					session.setAttribute(Constants.SUCCESS_UPDATE,
							"user.updatedSuccessfully");
				} catch (Exception e) {
					LOGGER.error(Constants.EXCEPTION, e);
				} finally {
					return "redirect:/admin/user";
				}
			} else {

				Role role = roleDao.findById(user.getRole().getId());
				user.setRole(role);
				StringBuffer contextPath = servletRequest.getRequestURL();
				String siteURL = contextPath.substring(Constants.ZERO,
						contextPath.indexOf("admin") - Constants.ONE);
				String userFirstName = user.getFirstName();
				String randomPassword = AkpmsUtil
						.getRandomString(Constants.TEN);
				user.setPassword(randomPassword);

				try {
					userDao.register(user);
					EmailTemplate registerTemplate = emailTemplateDao
							.findById(Constants.ARGUS_REGISTRATION_MAIL_TEMPLATE);
					if (registerTemplate.isStatus()) {
						String to[] = { user.getEmail() };

						String emailForm = messageSource.getMessage(
								Constants.EMAIL_FROM, null, Locale.ENGLISH)
								.trim();
						String emailHost = messageSource.getMessage(
								Constants.EMAIL_HOST, null, Locale.ENGLISH)
								.trim();
						String emailPassword = messageSource.getMessage(
								Constants.EMAIL_PASSWORD, null, Locale.ENGLISH)
								.trim();

						String emailTemplate = registerTemplate.getContent();

						emailTemplate = emailTemplate.replace(
								"USER_FIRST_NAME", userFirstName);
						emailTemplate = emailTemplate.replace("SITE_URL",
								siteURL);
						emailTemplate = emailTemplate.replace("USER_EMAIL",
								user.getEmail());
						emailTemplate = emailTemplate.replace("USER_PASSWORD",
								randomPassword);
						String subject = registerTemplate.getName();

						SendMailUtil.sendMail(emailForm, to, emailHost,
								emailForm, emailPassword, subject,
								emailTemplate, null, null, null);
					}

				} catch (Exception e) {
					LOGGER.error(e.getMessage());
				}
				session.setAttribute(Constants.SUCCESS_UPDATE,
						"user.addedSuccessfully");

				return "redirect:/admin/user";
			}
		} else {
			List<ObjectError> oeList = result.getAllErrors();
			for (ObjectError oe : oeList) {
				LOGGER.info("Errors:" + oe.getDefaultMessage());
			}

			model.addAttribute("roles", roleDao.findAll("name"));
			try {
				Map<String, List<Department>> deptMap = departmentParentChildFormation(departmentDao
						.getDepartmentsWithParentIdAndChildCount());
				model.addAttribute("departments", deptMap);
			} catch (Exception e) {
				LOGGER.error(Constants.EXCEPTION, e);
			}
			model.addAttribute("permissions", permissionDao.findAll("name"));
			model.addAttribute("emailTemplate",
					emailTemplateDao.findEmailTemplatesForSubscription());
			if (user != null && user.getId() != null) {
				map.put("operationType", "Edit");
				map.put("buttonName", "Update");
			} else {
				map.put("operationType", "Add");
				map.put("buttonName", "Save");
			}
			map.put("userRole", user.getRole().getId().toString());
			model.addAttribute("traineeUserRole",
					Constants.TRAINEE_ROLE_ID.toString());

			return "admin/userAdd";
		}
	}

	/**
	 *
	 * It is used to create Department's Parent Child formation. So that we can
	 * show them in correct form on view.
	 *
	 * @param deptList
	 * @return Map<String, List<Department>>
	 *
	 */
	public static Map<String, List<Department>> departmentParentChildFormation(
			List<Department> deptList) {
		Map<String, List<Department>> deptMap = new HashMap<String, List<Department>>();
		if (deptList != null && deptList.size() > Constants.ZERO) {
			List<Department> tempList = new ArrayList<Department>();
			tempList.addAll(deptList);

			LOGGER.info("IN Department List for ParentChild formation");
			for (Department dept : deptList) {
				if (dept.getParentId() == Constants.ZERO) {
					if (dept.getChildCount() > Constants.ZERO) {
						List<Department> list = new ArrayList<Department>();
						if (dept.isStatus() && !dept.isDeleted()) {

							list.add(dept);
						} else {
							continue;
						}
						for (Department childDept : tempList) {
							if (childDept.getParentId() == dept.getId()) {
								if (childDept.isStatus() && !dept.isDeleted()) {
									list.add(childDept);
								}
							}
						}

						deptMap.put(dept.getName(), list);
					} else {
						List<Department> list = new ArrayList<Department>();
						if (dept.isStatus() && !dept.isDeleted()) {

							list.add(dept);
						} else {
							continue;
						}
						deptMap.put(dept.getName(), list);
					}
				}
			}
		}

		LOGGER.info("OUT Department List for ParentChild formation");
		return deptMap;
	}

	/**
	 *
	 * It handles all FlexiGrid AJAX requests
	 *
	 * @param request
	 * @return JSON
	 */
	@RequestMapping(value = "/json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	JsonDataWrapper<UserJsonData> userJsonList(WebRequest request) {
		LOGGER.info("user::in json method");

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
			if (request.getParameter(Constants.STATUS) != null
					&& request.getParameter(Constants.STATUS).trim().length() > Constants.ZERO) {
				int status = Integer.parseInt(request
						.getParameter(Constants.STATUS));
				LOGGER.info("Status is = " + status);
				whereClauses.put(Constants.STATUS,
						request.getParameter(Constants.STATUS));
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
			totalRows = userDao.totalRecord(whereClauses);
		} catch (Exception e) {
			LOGGER.error(e.toString());
		}

		if (totalRows > Constants.ZERO) {
			try {
				List<User> rows = userDao.findAll(orderClauses, whereClauses);
				List<UserJsonData> djd = getJsonData(rows);
				JsonDataWrapper<UserJsonData> jdw = new JsonDataWrapper<UserJsonData>(
						page, totalRows, djd);
				return jdw;
			} catch (Exception e) {
				LOGGER.error(e.toString());
			}

		}

		return null;
	}

	/**
	 *
	 * function used to get only relative and required data from User Objects.
	 *
	 * @param rows
	 * @return
	 */
	private List<UserJsonData> getJsonData(List<User> rows) {
		List<UserJsonData> deptJsonData = new ArrayList<UserJsonData>();

		if (rows != null && rows.size() > Constants.ZERO) {
			for (User user : rows) {
				UserJsonData ujd = new UserJsonData();
				ujd.setId(user.getId());
				ujd.setFirstName(user.getFirstName());
				ujd.setLastName(user.getLastName());
				ujd.setStatus(user.isStatus());
				ujd.setEmail(user.getEmail());

				if (user.getRole() != null) {
					ujd.setRoleName(user.getRole().getName());
				} else {
					ujd.setRoleName("");
				}
				if (user.getDepartments() != null
						&& user.getDepartments().size() > Constants.ZERO) {
					StringBuilder deptName = new StringBuilder();
					int i = Constants.ZERO;
					for (Department dept : user.getDepartments()) {
						if (i == Constants.ZERO) {
							if (dept.isStatus() && !dept.isDeleted()) {
								deptName.append(dept.getName());
								i++;
							}
						} else {
							if (dept.isStatus() && !dept.isDeleted()) {
								deptName.append(", " + dept.getName());
								i++;
							}
						}
					}
					ujd.setDepartmentNames(deptName.toString());
				} else {
					LOGGER.info("department object coming null or list size 0");
				}

				deptJsonData.add(ujd);
			}
		}
		return deptJsonData;
	}

	/**
	 * function to change user status
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
			if (status == Constants.ONE) {
				bStatus = true;
			}
			int updateCount = userDao.changeStatus(id, bStatus);
			if (updateCount > Constants.ZERO) {
				response = true;
			}

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
		return response;
	}

	/**
	 * function to set user as deleted (is_deleted =1)
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteUser(WebRequest request) {
		LOGGER.info("in deleteUser method");
		LOGGER.info(request.getParameter("items"));

		boolean response = false;

		try {
			int updateCount = userDao.deleteUser(new Long(request
					.getParameter("item")));
			if (updateCount > Constants.ZERO) {
				response = true;
			}

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}

		return response;
	}


}
