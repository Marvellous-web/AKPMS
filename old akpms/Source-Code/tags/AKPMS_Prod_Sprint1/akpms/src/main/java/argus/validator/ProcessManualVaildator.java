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

import argus.domain.ProcessManual;

/**
 * @author sumit.v
 *
 */
@Service
public class ProcessManualVaildator extends LocalValidatorFactoryBean implements
		Validator {
	private static final Log LOGGER = LogFactory.getLog(ProcessManualVaildator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return ProcessManual.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		LOGGER.info("in ProcessManualVaildator - validate");

		ProcessManual processManual = (ProcessManual) target;
		if (processManual.getTitle() == null
				|| processManual.getTitle().trim().equals("")) {
			errors.rejectValue("title", "processManualTitle.required");
		}

		if (processManual.getContent() == null
				|| processManual.getContent().trim().equals("")) {
			errors.rejectValue("content", "processManualContent.required");
		}

		if (processManual.getId() != null
				&& (processManual.getModificationSummary() == null || processManual
						.getModificationSummary().trim().length() == 0)) {
			errors.rejectValue("modificationSummary",
					"processManualModificationSummary.required");
		}
		LOGGER.info("out ProcessManualVaildator - validate");
	}
}
