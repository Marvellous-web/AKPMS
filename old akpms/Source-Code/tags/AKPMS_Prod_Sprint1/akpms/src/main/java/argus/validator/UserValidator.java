/**
 *
 */
package argus.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import argus.domain.User;
import argus.repo.user.UserDao;

/**
 * @author sumit.v
 *
 */
@Service
public class UserValidator extends LocalValidatorFactoryBean implements
		Validator {

	private static final Log LOGGER = LogFactory.getLog(UserValidator.class);

	private static final String EMAIL = "email";

	@Autowired
	private UserDao userDao;

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		LOGGER.info("Validate method");
		User user = (User) target;
		Pattern emailPttern = Pattern
				.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");

		User isExist = null;
		try {
			isExist = userDao.findByEmail(user.getEmail(), false);
		} catch (Exception e) {
			LOGGER.error(e.toString());
		}

		if (isExist != null) {
			if (user.getId() == null) {
				LOGGER.info("duplicate username validation in case of add user");
				errors.rejectValue(EMAIL, "user.exist");
			} else if (user.getId().longValue() != isExist.getId().longValue()) {
				LOGGER.info("User with this email id already exists. Please enter a different email id");
				errors.rejectValue(EMAIL, "user.exist");
			}
		}

		if (user.getFirstName() == null
				|| user.getFirstName().trim().equals("")) {
			errors.rejectValue("firstName", "firstName.required");
		}

		if (user.getLastName() == null || user.getLastName().trim().equals("")) {
			errors.rejectValue("lastName", "lastName.required");
		}

		if (user.getEmail() == null || user.getEmail().trim().equals("")) {
			errors.rejectValue(EMAIL, "email.required");
		}
		else
		{
			Matcher emailMatcher = emailPttern.matcher(user.getEmail().toLowerCase());

			if(!emailMatcher.matches())
			{
				errors.rejectValue(EMAIL, "email.invalid");
			}
		}
		if (user.getContact() == null || user.getContact().trim().equals("")) {
			errors.rejectValue("contact", "contact.required");
		}
		if (user.getRole() == null) {
			errors.rejectValue("role", "role.required");
		}
	}

}
