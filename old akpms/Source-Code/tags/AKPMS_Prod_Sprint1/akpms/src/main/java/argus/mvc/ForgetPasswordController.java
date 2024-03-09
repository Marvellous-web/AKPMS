package argus.mvc;

import java.net.InetAddress;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import argus.domain.EmailTemplate;
import argus.domain.User;
import argus.repo.emailtemplate.EmailTemplateDao;
import argus.repo.user.UserDao;
import argus.util.Constants;
import argus.util.SendMailUtil;

@Controller
@RequestMapping(value = "/forget")
@SessionAttributes({ "user" })
public class ForgetPasswordController {

	private static final Log LOGGER = LogFactory
			.getLog(ForgetPasswordController.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private EmailTemplateDao emailTemplateDao;

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(HttpServletRequest request, Model model,
			Map<String, Object> map, HttpSession session) {
		LOGGER.info("in ForgetPassword get method");
		try {
			InetAddress addr = InetAddress.getLocalHost();
			LOGGER.info("request server IP addr  = " + addr.getHostAddress());

			if (addr.getHostAddress().equalsIgnoreCase("192.168.72.122")) {
				LOGGER.info("using public private key Of  192.168.72.122");
				request.setAttribute(
						"publicKey",
						messageSource.getMessage(
								"domain.192.168.72.122.public", null,
								Locale.ENGLISH).trim());
				request.setAttribute(
						"privateKey",
						messageSource.getMessage(
								"domain.192.168.72.122.private", null,
								Locale.ENGLISH).trim());
			} else if (addr.getHostAddress().equalsIgnoreCase("192.168.72.123")) {
				LOGGER.info("using public private key Of  192.168.72.123");
				request.setAttribute(
						"publicKey",
						messageSource.getMessage(
								"domain.192.168.72.123.public", null,
								Locale.ENGLISH).trim());
				request.setAttribute(
						"privateKey",
						messageSource.getMessage(
								"domain.192.168.72.123.private", null,
								Locale.ENGLISH).trim());
			} else if (addr.getHostAddress().equalsIgnoreCase("192.168.72.154")) {
				LOGGER.info("using public private key Of  192.168.72.154");
				request.setAttribute(
						"publicKey",
						messageSource.getMessage(
								"domain.192.168.72.154.public", null,
								Locale.ENGLISH).trim());
				request.setAttribute(
						"privateKey",
						messageSource.getMessage(
								"domain.192.168.72.154.private", null,
								Locale.ENGLISH).trim());
			} else if (addr.getHostAddress().equalsIgnoreCase("202.164.43.80")) {
				LOGGER.info("using public private key Of  202.164.43.80");
				request.setAttribute(
						"publicKey",
						messageSource.getMessage("domain.202.164.43.80.public",
								null, Locale.ENGLISH).trim());
				request.setAttribute(
						"privateKey",
						messageSource.getMessage(
								"domain.202.164.43.80.private", null,
								Locale.ENGLISH).trim());
			} else if (addr.getHostAddress().equalsIgnoreCase("192.168.75.207")) {
				LOGGER.info("using public private key Of  192.168.75.207");
				request.setAttribute(
						"publicKey",
						messageSource.getMessage(
								"domain.192.168.75.207.public", null,
								Locale.ENGLISH).trim());
				request.setAttribute(
						"privateKey",
						messageSource.getMessage(
								"domain.192.168.75.207.private", null,
								Locale.ENGLISH).trim());
			} else {
				LOGGER.info(" in else block \"localhost\"");
				request.setAttribute(
						"publicKey",
						messageSource.getMessage("domain.localhost.com.public",
								null, Locale.ENGLISH).trim());
				request.setAttribute(
						"privateKey",
						messageSource.getMessage(
								"domain.localhost.com.private", null,
								Locale.ENGLISH).trim());
			}
			model.addAttribute("user", new User());
			String error = (String) session.getAttribute(Constants.ERROR);
			if (error != null) {
				map.put(Constants.ERROR,
						messageSource.getMessage(error, null, Locale.ENGLISH)
								.trim());
			}
			session.removeAttribute("error");
			return "forgetPassword";
		} catch (Exception e) {
			LOGGER.error("Exception : ", e);
			return "forgetPassword";
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(@ModelAttribute("user") User user,
			HttpServletRequest request, Model model, Map<String, Object> map,
			HttpSession session) {
		LOGGER.info("in ForgetPassword post method");
		User userDB;

		LOGGER.info("e-mail = " + user.getEmail());
		if (user.getEmail() == null || user.getEmail().trim().length() < 1) {
			session.setAttribute(Constants.ERROR,
					"forgetPassword.usernameRequired");
			return "redirect:forget";
		} else {
			try {
				userDB = userDao.findByEmail(user.getEmail(), false);
				if (userDB == null) {
					session.setAttribute(Constants.ERROR,
							"forgetPassword.usernameInvalid");
					return "redirect:forget";
				} else {
					if (!userDB.isStatus() || userDB.isDeleted()) {
						session.setAttribute(Constants.ERROR,
								"forgetPassword.accountDeactivate");
						return "redirect:forget";
					}
				}
			} catch (Exception e) {
				LOGGER.error(Constants.EXCEPTION, e);
				return "redirect:forget";
			}
		}
		ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
		try {
			InetAddress addr = InetAddress.getLocalHost();

			LOGGER.info("request came from IP addr  = "
					+ request.getRemoteAddr());

			if (addr.getHostAddress().equalsIgnoreCase("192.168.72.122")) {
				LOGGER.info("using private key Of 192.168.72.122");
				reCaptcha.setPrivateKey(messageSource.getMessage(
						"domain.192.168.72.122.private", null, Locale.ENGLISH)
						.trim());
			} else if (addr.getHostAddress().equalsIgnoreCase("192.168.72.123")) {
				LOGGER.info("using private key Of 192.168.72.123");
				reCaptcha.setPrivateKey(messageSource.getMessage(
						"domain.192.168.72.123.private", null, Locale.ENGLISH)
						.trim());
			} else if (addr.getHostAddress().equalsIgnoreCase("192.168.72.154")) {
				LOGGER.info("using private key Of 192.168.72.154");
				reCaptcha.setPrivateKey(messageSource.getMessage(
						"domain.192.168.72.154.private", null, Locale.ENGLISH)
						.trim());
			} else if (addr.getHostAddress().equalsIgnoreCase("202.164.43.80")) {
				LOGGER.info("using private key Of 202.164.43.80");
				reCaptcha.setPrivateKey(messageSource.getMessage(
						"domain.202.164.43.80.private", null, Locale.ENGLISH)
						.trim());
			} else if (addr.getHostAddress().equalsIgnoreCase("192.168.75.207")) {
				LOGGER.info("using private key Of 192.168.75.207");
				reCaptcha.setPrivateKey(messageSource.getMessage(
						"domain.192.168.75.207.private", null, Locale.ENGLISH)
						.trim());
			} else {
				LOGGER.info("using private key of Localhost");
				reCaptcha.setPrivateKey(messageSource.getMessage(
						"domain.localhost.com.private", null, Locale.ENGLISH)
						.trim());
			}


			String remoteAddr = addr.getHostAddress();
			String challengeField = request
					.getParameter("recaptcha_challenge_field");
			String responseField = request
					.getParameter("recaptcha_response_field");

			LOGGER.info("remoteAddr = " + remoteAddr);
			LOGGER.info("challengeField = " + challengeField);
			LOGGER.info("responseField = " + responseField);
			ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(
					remoteAddr, challengeField, responseField);

			if (!reCaptchaResponse.isValid()) {
				LOGGER.info("invalid captcha");
				session.setAttribute("error", "forgetPassword.captchaInvalid");
				return "redirect:forget";
			} else {
				EmailTemplate forgetPasswordTemplate = emailTemplateDao
						.findById(Constants.FORGET_PASSWORD_TEMPLATE);
				String to[] = { userDB.getEmail() };
				String password = userDB.getPassword();
				String forgetPasswordText = forgetPasswordTemplate.getContent();

				forgetPasswordText = forgetPasswordText.replace("USER_FIRST_NAME", userDB.getFirstName());

				forgetPasswordText = forgetPasswordText.replace(
						"USER_PASSWORD", password);
				String subject = forgetPasswordTemplate.getName();

				String emailForm = messageSource.getMessage(
						Constants.EMAIL_FROM, null, Locale.ENGLISH).trim();
				String emailHost = messageSource.getMessage(
						Constants.EMAIL_HOST, null, Locale.ENGLISH).trim();
				String emailPassword = messageSource.getMessage(
						Constants.EMAIL_PASSWORD, null, Locale.ENGLISH).trim();

				try {
					SendMailUtil.sendMail(emailForm, to, emailHost, emailForm,
							emailPassword, subject, forgetPasswordText, null,
							null, null);
				} catch (Exception e) {
					LOGGER.error(e.getMessage());
				}

				LOGGER.info("succed, user id = " + userDB.getId());
				session.setAttribute("mailSend", "forgetPassword.mailSend");
				return "redirect:login";
			}
		} catch (Exception e) {
			return "redirect:login";
		}

	}
}
