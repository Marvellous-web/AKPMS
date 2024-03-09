/**
 *
 */
package argus.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import argus.domain.AdminSettings;

/**
 * @author sumit.v
 *
 */
@Service
public class AdminSettingsValidator extends LocalValidatorFactoryBean implements
		Validator {

	private static final Log LOGGER = LogFactory
			.getLog(AdminSettingsValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return AdminSettings.class.isAssignableFrom(clazz);
	}


	public void validate(Object target, Errors errors) {
		LOGGER.info("Validate method");
		AdminSettings adminSettings = (AdminSettings) target;

		Pattern digitPattern =
                Pattern.compile("^\\d{0,5}(\\.\\d{1,2})?");


		if(adminSettings.getProceesManaulReadStatusRatings()==null||adminSettings.getProceesManaulReadStatusRatings().trim().equals(""))
		{
			errors.rejectValue("proceesManaulReadStatusRatings", "proceesManaulReadStatusRatings.required");
		}
		else
		{
			Matcher patternMatcher = digitPattern.matcher(adminSettings.getProceesManaulReadStatusRatings());
			if(!patternMatcher.matches())
			{
				errors.rejectValue("proceesManaulReadStatusRatings", "proceesManaulReadStatusRatings.invalid");
			}

		}

		if(adminSettings.getArgusRatings()==null||adminSettings.getArgusRatings().trim().equals(""))
		{
			errors.rejectValue("argusRatings", "argusRatings.required");
		}
		else
		{
			Matcher patternMatcher = digitPattern.matcher(adminSettings.getArgusRatings());
			if(!patternMatcher.matches())
			{
				errors.rejectValue("argusRatings", "argusRatings.invalid");
			}

		}
		if(adminSettings.getIdsArgusRatings()==null||adminSettings.getIdsArgusRatings().trim().equals(""))
		{
			errors.rejectValue("idsArgusRatings", "idsArgusRatings.required");
		}
		else
		{
			Matcher patternMatcher = digitPattern.matcher(adminSettings.getIdsArgusRatings());
			if(!patternMatcher.matches())
			{
				errors.rejectValue("idsArgusRatings", "idsArgusRatings.invalid");
			}

		}

	}


}
