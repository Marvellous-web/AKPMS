package argus.mvc;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
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
import argus.util.AkpmsUtil;
import argus.util.Constants;
import argus.util.EmailUtil;

@Controller
@RequestMapping(value = "/forget")
@SessionAttributes({ "user" })
public class ForgetPasswordController {

	private static final Logger LOGGER = Logger
			.getLogger(ForgetPasswordController.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private EmailTemplateDao emailTemplateDao;

	@Autowired
	private EmailUtil emailUtil;

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(HttpServletRequest request, Model model,
			Map<String, Object> map, HttpSession session) {
		LOGGER.info("in ForgetPassword get method");
		try {
			InetAddress addr = InetAddress.getLocalHost();
			LOGGER.info("request server IP addr  = " + addr.getHostAddress());

			LOGGER.info("public.key: "
					+ messageSource.getMessage("public.key", null,
							Locale.ENGLISH).trim());

			LOGGER.info("private.key: "
					+ messageSource.getMessage("private.key", null,
							Locale.ENGLISH).trim());

			request.setAttribute("publicKey",
					messageSource
							.getMessage("public.key", null, Locale.ENGLISH)
							.trim());
			request.setAttribute(
					"privateKey",
					messageSource.getMessage("private.key", null,
							Locale.ENGLISH).trim());

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
				userDB = userDao.findByEmail(user.getEmail(), true);
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
			LOGGER.info("private.key: "
					+ messageSource.getMessage("private.key", null,
							Locale.ENGLISH).trim());
			reCaptcha.setPrivateKey(messageSource.getMessage("private.key",
					null, Locale.ENGLISH).trim());

			PasswordEncoder encoder = new Md5PasswordEncoder();
			String randomPassword = AkpmsUtil.getRandomString(Constants.TEN);
			userDB.setPassword(encoder.encodePassword(randomPassword,
					Constants.SALT));
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
				userDao.changePassword(userDB.getId(),
						encoder.encodePassword(randomPassword, Constants.SALT));
				EmailTemplate forgetPasswordTemplate = emailTemplateDao
						.findById(Constants.FORGET_PASSWORD_TEMPLATE);
				// String password = userDB.getPassword();
				String password = randomPassword;
				String forgetPasswordText = forgetPasswordTemplate.getContent();

				forgetPasswordText = forgetPasswordText.replace(
						"USER_FIRST_NAME", userDB.getFirstName());

				forgetPasswordText = forgetPasswordText.replace(
						"USER_PASSWORD", password);
				String subject = forgetPasswordTemplate.getName();

				// String emailForm = messageSource.getMessage(
				// Constants.EMAIL_FROM, null, Locale.ENGLISH).trim();
				// String emailHost = messageSource.getMessage(
				// Constants.EMAIL_HOST, null, Locale.ENGLISH).trim();
				// String emailPassword = messageSource.getMessage(
				// Constants.EMAIL_PASSWORD, null, Locale.ENGLISH).trim();

				try {
					// SendMailUtil.sendMail(emailForm, to, emailHost,
					// emailForm,
					// emailPassword, subject, forgetPasswordText, null,
					// null, null);

					/******* send email start ***********/
					// EmailUtil emailUtil = new EmailUtil();
					emailUtil.setSubject(subject);
					emailUtil.setContent(forgetPasswordText);
					emailUtil.setType("html");

					ArrayList<String> to = new ArrayList<String>();
					to.add(userDB.getEmail());

					emailUtil.setTo(to);
					emailUtil.sendEmail();
					/******* send email end ***********/
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
