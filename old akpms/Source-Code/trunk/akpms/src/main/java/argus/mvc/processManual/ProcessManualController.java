package argus.mvc.processManual;

import java.beans.PropertyEditorSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import argus.domain.Department;
import argus.domain.EmailTemplate;
import argus.domain.Files;
import argus.domain.ProcessManual;
import argus.domain.ProcessManualAudit;
import argus.domain.User;
import argus.mvc.user.UserController;
import argus.repo.department.DepartmentDao;
import argus.repo.emailtemplate.EmailTemplateDao;
import argus.repo.processManual.ProcessManualAuditDao;
import argus.repo.processManual.ProcessManualDao;
import argus.util.AkpmsUtil;
import argus.util.Constants;
import argus.util.EmailUtil;
import argus.util.ProcessManualAuditJsonData;
import argus.util.SubProcessManualJsonData;
import argus.validator.ProcessManualVaildator;

@Controller
@RequestMapping(value = "/processmanual")
@SessionAttributes({ "processManual" })
public class ProcessManualController {

	private static final Logger LOGGER = Logger
			.getLogger(ProcessManualController.class);

	@Autowired
	private ProcessManualDao processManualDao;

	@Autowired
	private ProcessManualAuditDao processManualAuditDao;

	@Autowired
	private ProcessManualVaildator processManualVaildator;

	@Autowired
	private DepartmentDao departmentDao;

	@Autowired
	private EmailTemplateDao emailTemplateDao;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private EmailUtil emailUtil;

	/**
	 * Used to bind properties to model class
	 *
	 * @param binder
	 * @throws Exception
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) throws Exception {

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
								LOGGER.error("Exception: ", e);
							}
						}
						return department;
					}
				});

		binder.registerCustomEditor(ProcessManual.class, "processManual",
				new PropertyEditorSupport() {
					@Override
					public void setAsText(String text) {
						LOGGER.info(" going to create ProcessManual Object for files class");
						Long id = Long.parseLong(text);
						ProcessManual processManual = new ProcessManual();
						processManual.setId(id);
						setValue(processManual);
					}
				});

		binder.registerCustomEditor(ProcessManual.class, "parentId",
				new PropertyEditorSupport() {
					@Override
					public void setAsText(String text) {
						LOGGER.info(" going to create Parent ProcessManual Object for SubSection");
						Long id = Long.parseLong(text);
						ProcessManual processManual = new ProcessManual();
						processManual.setId(id);
						setValue(processManual);
					}
				});

	}

	/**
	 *
	 * @param map
	 * @param successUpdate
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String processManualList(Map<String, Object> map,
			@RequestParam(required = false) String successUpdate,
			HttpSession session, WebRequest request) {
		try {
			String success = (String) session
					.getAttribute(Constants.SUCCESS_UPDATE);
			if (success != null) {
				map.put("success",
						messageSource.getMessage(success, null, Locale.ENGLISH)
								.trim());
			} else {
				success = request.getParameter(Constants.SUCCESS_UPDATE);
				if (success != null
						&& Integer.parseInt(success) == Constants.TWO) {
					map.put("success", "Section has been successfully deleted.");
				}
			}

			session.removeAttribute(Constants.SUCCESS_UPDATE);

			List<ProcessManual> processManualList = null;
			// User loggedInUser = AkpmsUtil.getLoggedInUser();

			if (null != request.getParameter("kwd")
					&& request.getParameter("kwd").trim().length() > Constants.ZERO) {
				LOGGER.info("in search manual : controller");
				String keyword = null;
				keyword = request.getParameter("kwd").trim();
				if (AkpmsUtil
						.checkPermission(Constants.PERMISSION_DOCUMENT_MANAGER)) {
					processManualList = processManualDao.getAllProcessManuals(
							false, true, keyword);
				} else {
					processManualList = processManualDao.getAllProcessManuals(
							true, true, keyword);
				}

				if (processManualList != null && !processManualList.isEmpty()) {
					for (ProcessManual processManual : processManualList) {
						processManual.setContent(AkpmsUtil
								.removeHTML(processManual.getContent()));
					}
					map.put("searchSection", processManualList);
				} else {
					map.put("searchSection", "");
				}
			} else {
				LOGGER.info("in list manual : controller");
				if (AkpmsUtil
						.checkPermission(Constants.PERMISSION_DOCUMENT_MANAGER)) {
					processManualList = processManualDao.getAllProcessManuals(
							false, true);
				} else {
					processManualList = processManualDao.getAllProcessManuals(
							true, true);
				}

				if (processManualList != null && !processManualList.isEmpty()) {
					map.put("section", processManualList);
				} else {
					map.put("section", "");
				}
			}

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}

		return "processManualList";
	}

	/**
	 *
	 * @param processManual
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String processProcessManual(
			@ModelAttribute("processManual") ProcessManual processManual,
			BindingResult result, Model model, Map<String, Object> map,
			WebRequest request, HttpSession session) {

		processManualVaildator.validate(processManual, result);
		boolean isNew = true;
		Double position = 0.0;
		if (request.getParameter("position") != null) {
			position = Double.valueOf(request.getParameter("position"));
		}
		LOGGER.info(" is there error " + result.hasErrors() + " ERROR: "
				+ result.getAllErrors().toString());
		if (!result.hasErrors()) {
			ProcessManual parent = null;

			if (!request.getParameter("parentId").equalsIgnoreCase("0")) {
				try {
					parent = processManualDao.getProcessManualById(new Long(
							request.getParameter("parentId")), true, false);

					/* set position code START */

					List<ProcessManual> subProcessManualList = parent
							.getProcessManualList();
					if (subProcessManualList.isEmpty()) {
						processManual.setPosition(position + 1);
					} else {
						processManualDao.updateProcessManualsPositons(position, new Long(
								request.getParameter("parentId")));
						processManual.setPosition(position + 1);
					}
					/* set position code END */

				} catch (Exception e) {
					LOGGER.error(Constants.EXCEPTION, e);
				}
			}
			processManual.setParent(parent);
			processManual.setTitle(StringEscapeUtils.escapeHtml(processManual
					.getTitle()));

			if (null != processManual.getId()) {
				try {
					if (!processManual.isStatus()) {
						processManualDao.changeStatus(processManual.getId(),
								false);
					}

					processManual
							.setModificationSummary(StringEscapeUtils
									.escapeHtml(processManual
											.getModificationSummary()));

					processManualDao.updateProcessManual(processManual);
					isNew = false;
					session.setAttribute(Constants.SUCCESS_UPDATE,
							"processmanual.updatedSuccessfully");
				} catch (Exception e) {
					LOGGER.error(Constants.EXCEPTION, e);
				}
			} else {
				/* new Process Manual will be activate on addition */
				try {
					processManualDao.saveProcessManual(processManual);
					session.setAttribute(Constants.SUCCESS_UPDATE,
							"processmanual.addedSuccessfully");
				} catch (Exception e) {
					// TODO: handle exception
					LOGGER.info("ERROR: " + e.getMessage());
				}
			}
			try {
				if (processManual.isNotification()) {
					EmailTemplate processManualMailTemplate = emailTemplateDao
							.findById(Constants.PROCESS_MANUAL_MODIFICATION_MAIL_TEMPLATE);
					if (processManualMailTemplate.isStatus()) {

						List<User> evaluationSubscriberUsers = processManualMailTemplate
								.getUsers();
						if (evaluationSubscriberUsers != null) {
							List<String> to = new ArrayList<String>();
							for (User userToMail : evaluationSubscriberUsers) {
								if (userToMail.isStatus()
										&& !userToMail.isDeleted()) {
									to.add(userToMail.getEmail());
								}
							}
							LOGGER.info("Users count who will get mail:"
									+ to.isEmpty());

							if (!to.isEmpty()) {
								String emailTemplateContent = processManualMailTemplate
										.getContent();

								// String emailForm = messageSource.getMessage(
								// Constants.EMAIL_FROM, null,
								// Locale.ENGLISH).trim();
								// String emailHost = messageSource.getMessage(
								// Constants.EMAIL_HOST, null,
								// Locale.ENGLISH).trim();
								// String emailPassword = messageSource
								// .getMessage(Constants.EMAIL_PASSWORD,
								// null, Locale.ENGLISH).trim();

								if (processManual.getParent() != null) {
									String parentName = processManual
											.getParent().getTitle();
									emailTemplateContent = emailTemplateContent
											.replace(
													"PROCESS_MANUAL",
													parentName
															+ " > "
															+ processManual
																	.getTitle());
								} else {
									emailTemplateContent = emailTemplateContent
											.replace("PROCESS_MANUAL",
													processManual.getTitle());
								}
								String subject = processManualMailTemplate
										.getName();
								if (isNew) {
									emailTemplateContent = emailTemplateContent
											.replace("ADDED_MODIFIED", "added");
								} else {
									emailTemplateContent = emailTemplateContent
											.replace("ADDED_MODIFIED",
													"modified");
								}
								// String stringArrayTO[] = to
								// .toArray(new String[to.size()]);

								// SendMailUtil.sendMail(emailForm,
								// stringArrayTO,
								// emailHost, emailForm, emailPassword,
								// subject, emailTemplateContent, null,
								// null, null);

								/******* send email start ***********/
								// EmailUtil emailUtil = new EmailUtil();
								emailUtil.setSubject(subject);
								emailUtil.setContent(emailTemplateContent);
								emailUtil.setType("html");
								emailUtil.setTo(to);
								emailUtil.sendEmail();
								/******* send email end ***********/

							} else {
								LOGGER.info("There is no recipient available");
							}
						}
					}

				}
			} catch (Exception e) {
				LOGGER.error(Constants.EXCEPTION, e);
			}
			if (parent == null) {
				return "redirect:/processmanual";
			} else {
				return "redirect:/processmanual/detail?id="
						+ request.getParameter("parentId")
						+ "#div_subsection_end_" + processManual.getId();
			}
		} else { /* in case of error */
			if (null != processManual.getId()) {
				map.put("operationType", "Edit");
				map.put("buttonName", "Update");
			} else {
				map.put("operationType", "Add");
				map.put("buttonName", "Save");
			}

			try {
				Map<String, List<Department>> departmentMap = UserController
						.departmentParentChildFormation(departmentDao
								.getDepartmentsWithParentIdAndChildCount());
				model.addAttribute("departments", departmentMap);
			} catch (Exception e) {
				LOGGER.error(Constants.EXCEPTION, e);
			}

			return "processManualAdd";
		}
	}

	/**
	 * function to show add ProcessManual page
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String loadProcessManualAdd(Model model, WebRequest request,
			Map<String, Object> map) {

		try {
			Map<String, List<Department>> departmentMap = UserController
					.departmentParentChildFormation(departmentDao
							.getDepartmentsWithParentIdAndChildCount());
			model.addAttribute("departments", departmentMap);
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}

		if (null != request && null != request.getParameter("id")) {
			ProcessManual processManual = null;
			try {
				List<String> loadDependency = new ArrayList<String>();
				loadDependency.add(Constants.DEPENDANCY_DEPARTMENTS);

				processManual = processManualDao.getProcessManualById(
						Long.parseLong(request.getParameter("id")),
						loadDependency);

				/* modification summary should be blank every time */
				processManual.setModificationSummary(null);
				processManual.setTitle(StringEscapeUtils
						.unescapeHtml(processManual.getTitle()));
			} catch (Exception e) {
				LOGGER.error(Constants.EXCEPTION, e);
			}
			model.addAttribute("processManual", processManual);
			model.addAttribute("mode", "Edit");
			map.put("operationType", "Edit");
			map.put("buttonName", "Update");
		} else {
			model.addAttribute("processManual", new ProcessManual());
			model.addAttribute("mode", "Add");
			map.put("operationType", "Add");
			map.put("buttonName", "Save");
		}

		return "processManualAdd";
	}

	/**
	 * function to change ProcessManual status
	 *
	 * @param id
	 * @param status
	 *            (1: activate. 0: inactivate)
	 * @return
	 */
	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
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

			int updateCount = processManualDao.changeStatus(id, bStatus);
			LOGGER.info("update Count:: " + updateCount);

			if (updateCount > Constants.ZERO) {
				response = true;
			}

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
		return response;
	}

	/**
	 * function to set ProcessManual as deleted (is_deleted =1)
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteProcessManual(WebRequest request) {
		LOGGER.info("in deleteProcessManual method");
		LOGGER.info(request.getParameter("items"));

		boolean response = false;

		try {
			int updateCount = Constants.ZERO;

			try {
				updateCount = processManualDao.deleteProcessManuals(new Long(
						request.getParameter("item")));
			} catch (Exception e) {
				// TODO: handle exception
			}

			if (updateCount > Constants.ZERO) {
				response = true;
			}

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
		return response;
	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String processManualDetail(Model model, WebRequest request,
			Map<String, Object> map, HttpSession session) {

		if (request != null && request.getParameter("id") != null) {
			try {
				ProcessManual processManual = null;
				if (AkpmsUtil
						.checkPermission(Constants.PERMISSION_DOCUMENT_MANAGER)) {
					processManual = processManualDao.getProcessManualById(
							Long.parseLong(request.getParameter("id")), true,
							false);
				} else {
					processManual = processManualDao.getProcessManualById(
							Long.parseLong(request.getParameter("id")), true,
							true);
				}

				if (processManual != null) {
					String success = (String) session
							.getAttribute(Constants.SUCCESS_UPDATE);
					if (success != null) {
						map.put("success",
								messageSource.getMessage(success, null,
										Locale.ENGLISH).trim());
					}
					session.removeAttribute(Constants.SUCCESS_UPDATE);
					LOGGER.info("got Process manual Object. object Id = "
							+ processManual);
					User loggedInUser = AkpmsUtil.getLoggedInUser();

					List<Long> userDeptList = new ArrayList<Long>();
					for (Department dept : loggedInUser.getDepartments()) {
						userDeptList.add(dept.getId());
					}
					for (Department dept : processManual.getDepartments()) {
						if (userDeptList.contains(dept.getId())) {
							processManual.setShowReadAndUnderstood(true);
							break;
						}
					}
					/* Role id 4 : Trainee */
					if (loggedInUser.getRole().getId() == Constants.TRAINEE_ROLE_ID) {
						Long loggedInUserId = loggedInUser.getId();
						for (User user : processManual.getUserList()) {
							if (loggedInUserId == user.getId()) {
								processManual.setReadAndUnderstood(true);
							}
						}
						for (ProcessManual subProcessManual : processManual
								.getProcessManualList()) {
							for (User user : subProcessManual.getUserList()) {
								if (loggedInUserId == user.getId()) {
									subProcessManual.setReadAndUnderstood(true);
								}
							}
						}
					}

					map.put("section", processManual);
					model.addAttribute("processManual", new ProcessManual());
					model.addAttribute("newFile", new Files());
				} else {
					return "redirect:/processmanual";
				}

			} catch (Exception e) {
				if (e.getMessage().equalsIgnoreCase(
						Constants.NO_ENTRY_FOUND_FOR_QUERY)) {
					return "redirect:/processmanual";
				}
				LOGGER.error(Constants.EXCEPTION, e);
			}
		} else {
			LOGGER.info("Request Object or Id in request Object is coming null");
		}

		return "processManualDetail";
	}

	@RequestMapping(value = "/fileupload", method = RequestMethod.POST)
	public String addFile(@ModelAttribute("newFile") Files newFile,
			BindingResult result, Model model, Map<String, Object> map,
			HttpSession session) {
		LOGGER.info(" In addFile method ");
		Long parentProcessManualId = Long.valueOf(1);
		try {
			if (newFile != null) {
				parentProcessManualId = newFile.getProcessManual().getId();
				MultipartFile file = newFile.getAttachedFile();
				if (null != file && file.getSize() > Constants.ZERO) {

					byte[] fileData = file.getBytes();
					String originalFileName = file.getOriginalFilename();
					Long timeMili = System.currentTimeMillis();

					StringBuilder systemName = new StringBuilder();
					if (newFile.getProcessManual() != null) {
						parentProcessManualId = newFile.getProcessManual()
								.getId();
						systemName.append(newFile.getProcessManual().getId());
						systemName.append("_");
						if (newFile.getSubProcessManualId() != null) {
							systemName.append(newFile.getSubProcessManualId());
						} else {
							systemName.append("0");
						}
						systemName.append("_");
					} else {
						LOGGER.info(" Process Manual not found in files object");
					}

					systemName.append(timeMili);
					systemName.append("_");
					systemName.append(originalFileName);

					String realPath = messageSource.getMessage(
							"attachments.storage.space", null, Locale.ENGLISH)
							.trim();

					LOGGER.info("real Path = " + realPath);

					File dir = new File(realPath);
					if (!dir.exists()) {
						dir.mkdirs();
					}
					File fileNamePath = new File(dir, systemName.toString());
					OutputStream outputStream = new FileOutputStream(
							fileNamePath);
					outputStream.write(fileData);
					outputStream.close();

					if (newFile.getSubProcessManualId() != null
							&& newFile.getSubProcessManualId() != Constants.ZERO) {
						ProcessManual processManual = new ProcessManual();
						processManual.setId(newFile.getSubProcessManualId());
						newFile.setProcessManual(processManual);
					}

					newFile.setName(originalFileName);
					newFile.setSystemName(systemName.toString());

					processManualDao.saveAttachement(newFile);
					/* set message here */
					session.setAttribute(Constants.SUCCESS_UPDATE,
							"processmanual.fileAttachedSuccessfully");
					return "redirect:/processmanual/detail?id="
							+ parentProcessManualId;
				} else {
					LOGGER.info("there is no attachment coming in file object");
					return "redirect:/processmanual/detail?id="
							+ parentProcessManualId;
				}
			} else {
				LOGGER.info("Attached file Object is coming null");
				return "redirect:/processmanual/detail?id="
						+ parentProcessManualId;
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
			return "redirect:/processmanual/detail?id=" + parentProcessManualId;
		}
	}

	@RequestMapping(value = "/fileDownload", method = RequestMethod.GET)
	public void downloadAttachment(HttpServletRequest request,
			HttpServletResponse response, @RequestParam("id") Long id,
			HttpSession session) {
		LOGGER.info("In download method. Id = " + id);
		try {
			if (id != null) {
				Files file = processManualDao.getAttachedFile(id);

				String realPath = messageSource.getMessage(
						"attachments.storage.space", null, Locale.ENGLISH)
						.trim();

				File systemFile = new File(realPath, file.getSystemName());
				InputStream is = new FileInputStream(systemFile);

				response.setHeader("Content-Disposition",
						"attachment; filename=" + file.getName());
				IOUtils.copy(is, response.getOutputStream());
				response.flushBuffer();
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
	}

	@RequestMapping(value = "/fileDelete", method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteAttachment(@RequestParam("id") Long id) {
		LOGGER.info("IN Delete Attachment menthod");
		boolean succeed = false;
		try {
			if (id != null) {
				int updatecount = processManualDao.deleteAttachedFile(id);
				if (updatecount > Constants.ZERO) {
					LOGGER.info("Attachment has deleted successfully");
					succeed = true;
				}
				return succeed;
			} else {
				LOGGER.info("File id is coming null");
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
		return succeed;
	}

	/**
	 * function to get modification summary for selected section (process
	 * manual)
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/modificationsummary/json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<ProcessManualAuditJsonData> listModificationSummary(
			WebRequest request) {
		LOGGER.info("process manual::in modificationsummary json method");

		try {
			List<ProcessManualAudit> rows = processManualAuditDao
					.getProcessManualModificationById(new Long(request
							.getParameter("id")));

			return getProcessManualAuditJsonData(rows);

		} catch (Exception e) {
			LOGGER.error(e.toString());
		}

		return null;
	}

	/**
	 * this method set value in ProcessManualAuditJsonData list
	 *
	 * @param rows
	 * @return
	 */
	private List<ProcessManualAuditJsonData> getProcessManualAuditJsonData(
			List<ProcessManualAudit> rows) {
		LOGGER.info("in getProcessManualAuditJsonData method");
		List<ProcessManualAuditJsonData> processmanualAuditJsonData = new ArrayList<ProcessManualAuditJsonData>();

		LOGGER.info("row size:" + rows.size());
		if (rows != null && rows.size() > Constants.ZERO) {
			for (ProcessManualAudit processManualAudit : rows) {
				ProcessManualAuditJsonData pmajd = new ProcessManualAuditJsonData();
				if (processManualAudit.getModifiedBy() != null) {
					pmajd.setId(processManualAudit.getId());
					pmajd.setModificationSummary(processManualAudit
							.getModificationSummary());
					pmajd.setModifiedBy(processManualAudit.getModifiedBy()
							.getFirstName()
							+ " "
							+ processManualAudit.getModifiedBy().getLastName());
					pmajd.setModifiedOn(AkpmsUtil.akpmsDateFormat(
							processManualAudit.getModifiedOn(), null));

					processmanualAuditJsonData.add(pmajd);
				}
			}
		}

		return processmanualAuditJsonData;
	}

	@RequestMapping(value = "/readUnderstand/json", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public boolean readAndUnderstand(
			@RequestParam(value = "id", required = false) Long processManualId,
			WebRequest request) {
		boolean isRead = false;
		try {
			if (processManualId != null) {
				ProcessManual processManual = processManualDao
						.getProcessManualById(processManualId, true, false);
				User user = AkpmsUtil.getLoggedInUser();
				processManual.getUserList().add(user);
				processManualDao.updateProcessManual(processManual);
				isRead = true;
				return isRead;
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
			return isRead;
		}
		return isRead;
	}

	@RequestMapping(value = "/subSection/json", method = RequestMethod.GET)
	@ResponseBody
	public SubProcessManualJsonData getSubSection(
			@RequestParam(value = "subSectionId") Long subSectionId) {
		LOGGER.info("IN SubSection method");
		SubProcessManualJsonData subProcessManualJsonData = null;
		List<String> dependencies = new ArrayList<String>();

		try {
			if (subSectionId != null) {
				ProcessManual processManual = processManualDao
						.getProcessManualById(subSectionId, null);
				ProcessManual tempProcessManual = processManualDao
						.getProcessManualById(
								processManual.getParent().getId(), true, false);
				List<ProcessManual> pml = tempProcessManual
						.getProcessManualList();
				double previousPosition = 0;
				for (int i = 0; i < pml.size(); i++) {
					ProcessManual pm = pml.get(i);
					if (pm.getId().equals(subSectionId)) {
						if(i == 0){
							System.out.println("This is the first subsection , there is no subSection before this. ");
							previousPosition = processManual.getParent().getPosition();
						}
						else{
							previousPosition = pml.get(i - 1).getPosition();
						}
					}
				}
				
				
				if (previousPosition != 0) {
					processManual.setPosition(previousPosition);
				}

				subProcessManualJsonData = this
						.getSubProcessManualJsonData(processManual);
				LOGGER.info("OUT SubSection");
				return subProcessManualJsonData;
			} else {
				LOGGER.info("OUT SubSection");
				return subProcessManualJsonData;
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
			return subProcessManualJsonData;
		}

	}

	/**
	 * function to set edit subsction popup data this is functional as wrapper
	 *
	 * @param processManual
	 * @return
	 */
	private SubProcessManualJsonData getSubProcessManualJsonData(
			ProcessManual processManual) {
		SubProcessManualJsonData subProcessManualJsonData = null;
		try {

			if (processManual != null) {
				subProcessManualJsonData = new SubProcessManualJsonData();
				subProcessManualJsonData.setId(processManual.getId());
				subProcessManualJsonData.setTitle(StringEscapeUtils
						.unescapeHtml(processManual.getTitle()));
				subProcessManualJsonData.setContent(processManual.getContent());
				subProcessManualJsonData.setNotification(processManual
						.isNotification());
				subProcessManualJsonData.setStatus(processManual.isStatus());
				subProcessManualJsonData.setParentId(processManual.getParent()
						.getId());
				subProcessManualJsonData.setCreatedBy(processManual
						.getCreatedBy().getId());
				subProcessManualJsonData.setCreatedOn(processManual
						.getCreatedOn());
				subProcessManualJsonData.setPosition(processManual
						.getPosition());
				return subProcessManualJsonData;
			} else {
				return subProcessManualJsonData;
			}

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
			return subProcessManualJsonData;
		}
	}
}
