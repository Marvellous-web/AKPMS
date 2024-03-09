/**
 *
 */
package argus.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import argus.domain.ChargeProdReject;

/**
 * @author jasdeep.s
 *
 */
@Service
public class ChargeProdRejectValidator extends LocalValidatorFactoryBean
		implements Validator {

	private static final Logger LOGGER = Logger
			.getLogger(ChargeProdRejectValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return ChargeProdReject.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		LOGGER.info("in chargeProductivity [validate] method : ");

		ChargeProdReject chargeProdReject = (ChargeProdReject) target;

		// if (chargeProdReject.getPatientName() == null
		// || chargeProdReject.getPatientName().trim().length() < 1) {
		// errors.rejectValue("patientName",
		// "chargeBatchProcessing.patientName");
		// }
		if (chargeProdReject.getSequence() == null
				|| chargeProdReject.getSequence().trim().length() < 1) {
			// errors.rejectValue("sequence", "chargeBatchProcessing.sequence");
		} else {
			Pattern sequencePattern = Pattern.compile("^[0-9_][\\s0-9_,-]*");
			Matcher sequenceMatcher = sequencePattern.matcher(chargeProdReject
					.getSequence());

			if ((!sequenceMatcher.matches())) {
				errors.rejectValue("sequence",
						"chargeBatchProcessing.sequence.characters");
			}
		}
		// if (chargeProdReject.getAccount() == null
		// || chargeProdReject.getAccount().trim().length() < 1) {
		// errors.rejectValue("account", "chargeBatchProcessing.Account");
		// }
		// if (chargeProdReject.getDateOfService() == null) {
		// errors.rejectValue("dateOfService", "chargeBatchProcessing.dos");
		// }
		if (chargeProdReject.getReasonToReject() == null
				|| chargeProdReject.getReasonToReject().trim().length() < 1) {
			errors.rejectValue("reasonToReject",
					"chargeBatchProcessing.reasonToReject");
		}
		if (chargeProdReject.getInsuranceType() == null
				|| chargeProdReject.getInsuranceType().trim().length() < 1) {
			errors.rejectValue("insuranceType",
					"chargeBatchProcessing.insuranceType");
		}
	}
}
