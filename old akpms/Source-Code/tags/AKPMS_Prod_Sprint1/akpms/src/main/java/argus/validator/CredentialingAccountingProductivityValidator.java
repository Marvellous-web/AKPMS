/**
 *
 */
package argus.validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import argus.domain.paymentproductivity.CredentialingAccountingProductivity;

/**
 * @author rajiv.k
 *
 */
@Service
public class CredentialingAccountingProductivityValidator extends
		LocalValidatorFactoryBean implements Validator {
	private static final Log LOGGER = LogFactory
			.getLog(CredentialingAccountingProductivityValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return CredentialingAccountingProductivity.class
				.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		LOGGER.info("int [validate] method");
		CredentialingAccountingProductivity credentialAccounting = (CredentialingAccountingProductivity) target;
		if (credentialAccounting.getDateRecd() == null) {
			LOGGER.info("Date Recd is NULL ");
			errors.rejectValue("dateRecd", "dateRecd.required");
		}
		if (credentialAccounting.getCredentialingTask() == null
				|| credentialAccounting.getCredentialingTask().trim()
						.equals("")) {
			errors.rejectValue("credentialingTask",
					"credentialingTask.required");
		}

		if (credentialAccounting.getTaskCompleted() == null) {
			LOGGER.info("Task completed date is NULL ");
			errors.rejectValue("taskCompleted", "taskCompleted.required");
		}

	}
}
