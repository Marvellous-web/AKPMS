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

import argus.domain.Insurance;
import argus.repo.insurance.InsuranceDao;
import argus.util.Constants;

/**
 *
 * @author vishal.joshi
 *
 */
@Service
public class InsuranceValidator extends LocalValidatorFactoryBean implements
		Validator {

	private static final Log LOGGER = LogFactory
			.getLog(InsuranceValidator.class);

	@Autowired
	private InsuranceDao insuranceDao;

	@Override
	public boolean supports(Class<?> clazz) {
		return Insurance.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		Insurance insurance = (Insurance) target;
		Pattern insuranceNamePattern = Pattern
				.compile("^[a-zA-Z0-9_][\\sa-zA-Z0-9_]*");
		Matcher insuranceNameMatcher = insuranceNamePattern.matcher(insurance
				.getName());

		if (insurance.getName() == null
				|| insurance.getName().trim().equals("")) {
			errors.rejectValue(Constants.BY_NAME, "insurance.required");
		}
		if ((insurance.getName().trim().length() != 0)
				&& (!insuranceNameMatcher.matches())) {
			errors.rejectValue(Constants.BY_NAME, "insurance.characters");
		}

		try {
			Insurance isInsurance = insuranceDao
					.findByName(insurance.getName());

			if (isInsurance != null) {
				if (insurance.getId() == null) {
					LOGGER.info("duplicate insurance name validation in case of add insurance");
					errors.rejectValue(Constants.BY_NAME, "insurance.exist");
				} else if (insurance.getId().longValue() != isInsurance.getId()
						.longValue()) {
					LOGGER.info("duplicate insurance name validation in case of edit insurance");
					errors.rejectValue(Constants.BY_NAME, "insurance.exist");
				}
			}

		} catch (Exception e) {
			LOGGER.info(
					"No insurance Exists with name = " + insurance.getName(), e);
		}
	}
}
