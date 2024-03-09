/**
 *
 */
package argus.validator;


import org.apache.log4j.Logger;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import argus.domain.Department;
import argus.domain.User;
import argus.mvc.HomeController;
import argus.util.Constants;

/**
 * @author sumit.v
 *
 */
@Service
public class ChangePasswordValidator extends LocalValidatorFactoryBean
		implements Validator {

	private static final Logger LOGGER = Logger
			.getLogger(ChangePasswordValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return Department.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		LOGGER.info("Validate method");
		HomeController.ChangePasswordModel changePassword = (HomeController.ChangePasswordModel) target;
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		PasswordEncoder encoder = new Md5PasswordEncoder();
		if (changePassword.getOldPassword() == null
				|| changePassword.getOldPassword().trim().equals("")) {
			errors.rejectValue("oldPassword", "oldpwd.required");
			return;
		} else if (user != null
				&& !encoder.isPasswordValid(user.getPassword(),
						changePassword.getOldPassword(), Constants.SALT)) {
			errors.rejectValue("oldPassword", "oldpwd.notMatched");
			return;
		}

		if (changePassword.getNewPassword() == null
				|| changePassword.getNewPassword().trim().equals("")) {
			errors.rejectValue("newPassword", "newpwd.required");
		}

		if (changePassword.getConfirmPassword() == null
				|| changePassword.getConfirmPassword().trim().equals("")) {
			errors.rejectValue("confirmPassword", "confirmpwd.required");
			return;
		}

		if (!changePassword.getNewPassword().equals(
				changePassword.getConfirmPassword())) {
			errors.rejectValue("confirmPassword", "new.notMatched");
		}
	}
}
