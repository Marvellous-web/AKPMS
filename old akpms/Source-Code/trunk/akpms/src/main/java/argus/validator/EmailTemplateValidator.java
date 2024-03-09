/**
 *
 */
package argus.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import argus.domain.EmailTemplate;

/**
 * @author sumit.v
 *
 */
public class EmailTemplateValidator extends LocalValidatorFactoryBean implements
		Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return EmailTemplate.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		EmailTemplate emailTemplate = (EmailTemplate) target;
		if (emailTemplate.getName() == null
				|| emailTemplate.getName().trim().equals("")) {
			errors.rejectValue("name", "emailTemplateName.required");
		}
	}
}
