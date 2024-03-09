package argus.mvc.emailtemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

import argus.domain.EmailTemplate;
import argus.repo.emailtemplate.EmailTemplateDao;
import argus.util.Constants;
import argus.util.EmailTemplateJsonData;
import argus.util.JsonDataWrapper;
import argus.validator.EmailTemplateValidator;

@Controller
@RequestMapping(value = "/admin/emailtemplate")
@SessionAttributes({ "emailTemplate" })
public class EmailTemplateController {

	private static final Logger LOGGER = Logger.getLogger(EmailTemplate.class);

	@Autowired
	private EmailTemplateDao emailTemplateDao;

	@Autowired
	private MessageSource messageSource;

	/**
	 * This function just load jsp "admin/emailTemplateList", the list will load
	 * using flexigrid list
	 *
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String emailTemplatesList(Map<String, Object> map,
			HttpSession session) {
		String success = (String) session
				.getAttribute(Constants.SUCCESS_UPDATE);
		if (success != null) {
			map.put("success",
					messageSource.getMessage(success, null, Locale.ENGLISH)
							.trim());
		}
		session.removeAttribute(Constants.SUCCESS_UPDATE);
		return "admin/emailTemplateList";
	}

	/**
	 * function to use for flexigrid list
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	JsonDataWrapper<EmailTemplateJsonData> listAllEmailTemplates(
			WebRequest request) {
		LOGGER.info("emailTemplate::in json method");

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
				if (keyword.contains("%")) {
					return null;
				} else {
					whereClauses.put(Constants.KEYWORD, keyword);
				}
			}
		} else {
			LOGGER.info("request object is coming null");
		}

		int totalRows = emailTemplateDao.totalRecord(whereClauses);

		if (totalRows > 0) {
			List<EmailTemplate> rows = emailTemplateDao.findAll(orderClauses,
					whereClauses);
			List<EmailTemplateJsonData> djd = getJsonData(rows);
			JsonDataWrapper<EmailTemplateJsonData> jdw = new JsonDataWrapper<EmailTemplateJsonData>(
					page, totalRows, djd);

			return jdw;
		}

		return null;
	}

	private List<EmailTemplateJsonData> getJsonData(List<EmailTemplate> rows) {
		List<EmailTemplateJsonData> eTemplateJsonData = new ArrayList<EmailTemplateJsonData>();

		if (rows != null && rows.size() > 0) {
			for (EmailTemplate eTemp : rows) {
				EmailTemplateJsonData djd = new EmailTemplateJsonData();

				djd.setId(eTemp.getId());
				djd.setName(eTemp.getName());
				djd.setStatus(eTemp.isStatus());
				if (eTemp.isSubscriptionEmail()) {
					djd.setSubscriptionEmail("Subscription-Email");
				}
				eTemplateJsonData.add(djd);
			}
		}
		return eTemplateJsonData;
	}

	/**
	 *
	 * @param emailTemplate
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String loadEmailTemplateAdd(
			@Valid @ModelAttribute("emailTemplate") EmailTemplate emailTemplate,
			BindingResult result, Model model, Map<String, Object> map,
			HttpSession session) {

		EmailTemplateValidator emailTemplateValidator = new EmailTemplateValidator();
		emailTemplateValidator.validate(emailTemplate, result);
		if (!result.hasErrors()) {
			if (null != emailTemplate.getId()) {
				emailTemplateDao.updateEmailTemplate(emailTemplate);
				session.setAttribute(Constants.SUCCESS_UPDATE,
						"emailTemplate.updatedSuccessfully");
			} else {
				emailTemplate.setStatus(true);
				emailTemplateDao.addEmailTemplate(emailTemplate);
				session.setAttribute(Constants.SUCCESS_UPDATE,
						"emailTemplate.addedSuccessfully");
			}

			return "redirect:/admin/emailtemplate";
		} else {
			if (emailTemplate.getId() != null) {
				map.put("operationType", "Edit");
				map.put("buttonName", "Update");
			} else {
				map.put("operationType", "Add");
				map.put("buttonName", "Save");
			}
			return "admin/emailTemplateAdd";
		}
	}

	/**
	 * function to show add emailTemplate page
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String processEmailTemplateAdd(Model model, WebRequest request,
			HttpSession session, Map<String, Object> map) {

		if (null != request && null != request.getParameter("id")) {
			EmailTemplate emailTemplate = emailTemplateDao.findById(Long
					.parseLong(request.getParameter("id")));

			if (emailTemplate == null) {
				session.setAttribute(Constants.SUCCESS_UPDATE,
						"record.notFound");
				return "redirect:/admin/emailtemplate";
			}

			model.addAttribute("emailTemplate", emailTemplate);
			model.addAttribute("mode", "Edit");
			map.put("operationType", "Edit");
			map.put("buttonName", "Update");
		} else {
			model.addAttribute("emailTemplate", new EmailTemplate());
			model.addAttribute("mode", "Add");
			map.put("operationType", "Add");
			map.put("buttonName", "Save");
		}

		return "admin/emailTemplateAdd";
	}

	/**
	 * function to change emailTemplate status
	 *
	 * @param id
	 * @param status
	 *            (1: activate. 0: inactivate)
	 * @return
	 */
	@RequestMapping(value = "/changeStatus", method = RequestMethod.GET)
	@ResponseBody
	public boolean changeEmailTemplateStatus(@RequestParam int id,
			@RequestParam int status) {
		LOGGER.info("in changeStatus method");
		LOGGER.info("id = " + id);
		LOGGER.info("status = " + status);

		boolean response = false;

		try {
			boolean bStatus = false;
			if (status == 1) {
				bStatus = true;
			}
			int updateCount = emailTemplateDao.changeStatus(id, bStatus);
			if (updateCount > 0) {
				response = true;
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
		return response;
	}
}
