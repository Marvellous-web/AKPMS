package argus.validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import argus.domain.paymentproductivity.PaymentProductivity;
import argus.util.Constants;

/**
 * @author rajiv.k
 *
 */
@Service
public class PaymentProductivityNonEraValidator extends
		LocalValidatorFactoryBean implements Validator {
	private static final Log LOGGER = LogFactory
			.getLog(PaymentProductivityNonEraValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return PaymentProductivity.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		LOGGER.info("int [validate] method");
		PaymentProductivity paymentProductivity = (PaymentProductivity) target;

		if (paymentProductivity.getPaymentBatch().getId() == null) {
			LOGGER.info(Constants.EXCEPTION);
			errors.rejectValue(Constants.PAYMENTBATCH_ID,
					"paymentProductivity.ticketNo");

		}

		if (paymentProductivity.getPaymentBatch().getManuallyPostedAmt() == null) {
			errors.rejectValue(Constants.MANUALLY_POSTED_AMT,
					"manuallyPostedAmt.required");
		}

		if (paymentProductivity.getPaymentBatch().getManuallyPostedAmt() != null
				&& paymentProductivity.getPaymentBatch() != null
				&& paymentProductivity.getPaymentBatch().getDepositAmount() != null
				&& paymentProductivity.getPaymentBatch().getDepositAmount() != 0
				&& (paymentProductivity.getPaymentBatch().getNdba() == null || paymentProductivity
						.getPaymentBatch().getNdba() == 0)) {

			if (paymentProductivity.getPaymentBatch().getDepositAmount() < (paymentProductivity
					.getPaymentBatch().getManuallyPostedAmt())) {
				errors.rejectValue(Constants.MANUALLY_POSTED_AMT,
						"paymentProductivity.greater");
			}

		}
		if (paymentProductivity.getPaymentBatch().getManuallyPostedAmt() != null
				&& paymentProductivity.getPaymentBatch() != null
				&& paymentProductivity.getPaymentBatch().getNdba() != null
				&& paymentProductivity.getPaymentBatch().getNdba() != 0
				&& (paymentProductivity.getPaymentBatch().getDepositAmount() == null || paymentProductivity
						.getPaymentBatch().getDepositAmount() == 0)) {
			if (paymentProductivity.getPaymentBatch().getNdba() < (paymentProductivity
					.getPaymentBatch().getManuallyPostedAmt())) {
				errors.rejectValue(Constants.MANUALLY_POSTED_AMT,
						"paymentProductivity.greater");
			}

		}

		if (paymentProductivity.getPaymentBatch().getManuallyPostedAmt() != null
				&& paymentProductivity.getPaymentBatch() != null
				&& paymentProductivity.getPaymentBatch().getNdba() == 0
				&& paymentProductivity.getPaymentBatch().getDepositAmount() == 0) {
			if (paymentProductivity.getPaymentBatch().getNdba() < (paymentProductivity
					.getPaymentBatch().getManuallyPostedAmt())) {
				errors.rejectValue(Constants.MANUALLY_POSTED_AMT,
						"paymentProductivity.greater");
			}

		}
		if (paymentProductivity.getPaymentBatch().getDatePosted() == null) {
			LOGGER.info(Constants.DATE_POSTED + " is NULL ");
			errors.rejectValue(Constants.DATE_POSTED,
					"paymentProductivity.datePosted.required");
		}
		if (paymentProductivity.getManualTransaction() == null) {
			errors.rejectValue(Constants.MANUAL_TRANSACTION,
					"manualTransaction.required");
		}
		if (paymentProductivity.getWorkFlowId() == 0) {
			errors.rejectValue("workFlowId", "workFlowId.required");
		}

	}
}
