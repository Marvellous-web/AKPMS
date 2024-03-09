/**
 *
 */
package argus.validator;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import argus.domain.User;

/**
 * @author sumit.v
 *
 */
@Service
public class MyProfileValidator extends LocalValidatorFactoryBean implements
		Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		User user = (User) target;


		if (user.getFirstName() == null
				|| user.getFirstName().trim().equals("")) {
			errors.rejectValue("firstName", "firstName.required");
		}

		if (user.getLastName() == null || user.getLastName().trim().equals("")) {
			errors.rejectValue("lastName", "lastName.required");
		}

		if (user.getEmail() == null || user.getEmail().trim().equals("")) {
			errors.rejectValue("email", "email.required");
		}
		if (user.getContact() == null || user.getContact().trim().equals("")) {
			errors.rejectValue("contact", "contact.required");
		}

	}

}
