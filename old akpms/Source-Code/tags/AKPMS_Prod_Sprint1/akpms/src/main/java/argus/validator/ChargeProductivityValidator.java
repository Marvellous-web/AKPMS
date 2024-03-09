package argus.validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import argus.domain.ChargeProductivity;

@Service
public class ChargeProductivityValidator extends LocalValidatorFactoryBean
		implements Validator {
	private static final Log LOGGER = LogFactory
			.getLog(ChargeProductivityValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return ChargeProductivity.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		LOGGER.info("in chargeProductivity [validate] method : ");

		ChargeProductivity chargeProductivity = (ChargeProductivity) target;
		if (chargeProductivity.isOnHoldFlag()) {
			if (chargeProductivity.getUnholdRemarks() == null
					|| chargeProductivity.getUnholdRemarks().trim().length() == 0) {
				errors.rejectValue("unholdRemarks",
						"chargeBatchProductivityUnHoldRemarks.required");
			}
		}
		if (!chargeProductivity.isOnHoldFlag()) {

			if (chargeProductivity.getNumberOfTransactions() == null) {
				errors.rejectValue("numberOfTransactions",
						"chargeBatchProcessing.required");
			}

			LOGGER.info("in validation:: chargeProductivity.getTicketNumber().getDateBatchPosted()"
					+ chargeProductivity.getTicketNumber().getDateBatchPosted());
			if (chargeProductivity.getTicketNumber().getDateBatchPosted() == null) {
				errors.rejectValue("ticketNumber.dateBatchPosted",
						"chargeBatchProcessing.postedOn.required");
			}
			if (chargeProductivity.getProductivityType() == null
					|| chargeProductivity.getProductivityType().trim().length() < 1) {
				errors.rejectValue("productivityType",
						"chargeBatchProcessing.required");
			}
			if ((chargeProductivity.getProductivityType() != null && chargeProductivity
					.getProductivityType().trim().length() > 1)
					&& (chargeProductivity.getWorkFlow() == null || chargeProductivity
							.getWorkFlow().trim().length() < 1)) {
				errors.rejectValue("workFlow", "chargeBatchProcessing.required");
			}

		}
	}
}
