package argus.validator;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import argus.domain.ChargeBatchProcessing;
@Service
public class ChargeBatchReportValidator extends LocalValidatorFactoryBean
implements Validator {
private static final Logger LOGGER = Logger.getLogger(ChargeBatchProcessingValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return ChargeBatchProcessing.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		LOGGER.info("in [validate] method : ");
		ChargeBatchProcessing chargeBatchProcessing = (ChargeBatchProcessing) target;

		if (chargeBatchProcessing.getDateReceivedFrom() == null) {
			errors.rejectValue("dateReceivedFrom",
					"chargeBatchProcessing.dateReceivedFrom.required");
		}
		if(chargeBatchProcessing.getDateReceivedFrom() != null && chargeBatchProcessing.getDateReceivedTo() == null){
			errors.rejectValue("dateReceivedTo", "chargeBatchProcessing.dateReceivedTo.required");
		}
		if ((chargeBatchProcessing.getDateReceivedTo() != null && chargeBatchProcessing
				.getDateReceivedFrom() != null)
				&& chargeBatchProcessing.getDateReceivedTo().before(
						chargeBatchProcessing.getDateReceivedFrom())) {
			errors.rejectValue("dateReceivedFrom",
					"chargeBatchProcessing.dateReceivedTo.mustBeBefore");
		}
	}
}
