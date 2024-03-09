package argus.validator;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import argus.domain.ChargeProductivity;
import argus.util.Constants;

@Service
public class ChargeProductivityValidator extends LocalValidatorFactoryBean
		implements Validator {
	private static final Logger LOGGER = Logger
			.getLogger(ChargeProductivityValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return ChargeProductivity.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		LOGGER.info("in chargeProductivity [validate] method : ");

		ChargeProductivity chargeProductivity = (ChargeProductivity) target;

		// if (chargeProductivity.isOnHoldFlag()) {
		// if (chargeProductivity.getUnholdRemarks() == null
		// || chargeProductivity.getUnholdRemarks().trim().length() == 0) {
		// errors.rejectValue("unholdRemarks",
		// "chargeBatchProductivityUnHoldRemarks.required");
		// }
		// }
		// if (!chargeProductivity.isOnHoldFlag()) {

		// if (chargeProductivity.getNumberOfTransactions() == null) {
		// errors.rejectValue("numberOfTransactions",
		// "chargeBatchProcessing.required");
		// }
		if (chargeProductivity.getScanDate() == null) {
			LOGGER.info(Constants.SCAN_DATE + " is NULL ");
			errors.rejectValue(Constants.SCAN_DATE,
					"paymentProductivity.scanDate.required");
		}

		if (chargeProductivity.isOnHold()
				&& chargeProductivity.getRemarks().trim().isEmpty()) {
			LOGGER.info(Constants.SCAN_DATE + " is NULL ");
			errors.rejectValue("remarks", "chargeProdReject.remarks.required");
		}

		if (chargeProductivity.getProductivityType().equalsIgnoreCase("CE")) {
			LOGGER.info("in validation:: chargeProductivity.getTicketNumber().getDateBatchPosted()"
					+ chargeProductivity.getTicketNumber().getDateBatchPosted());
			if (chargeProductivity.getTicketNumber().getDateBatchPosted() == null) {
				errors.rejectValue("ticketNumber.dateBatchPosted",
						"chargeBatchProcessing.postedOn.required");
			}
		}
		/*
		 * if (chargeProductivity.getProductivityType() == null ||
		 * chargeProductivity.getProductivityType().trim().length() < 1) {
		 * errors.rejectValue("productivityType",
		 * "chargeBatchProcessing.required"); } if
		 * ((chargeProductivity.getProductivityType() != null &&
		 * chargeProductivity .getProductivityType().trim().length() > 1) &&
		 * (chargeProductivity.getWorkFlow() == null || chargeProductivity
		 * .getWorkFlow().trim().length() < 1)) { errors.rejectValue("workFlow",
		 * "chargeBatchProcessing.required"); }
		 */

		// }
	}
}
