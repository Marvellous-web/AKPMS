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

import argus.domain.paymentproductivity.PaymentProductivity;
import argus.util.Constants;

/**
 * @author vishal.joshi
 *
 */
@Service
public class PaymentProductivityValidator extends LocalValidatorFactoryBean
		implements Validator {

	private static final Log LOGGER = LogFactory
			.getLog(PaymentProductivityValidator.class);

	private boolean validate = false;

	@Override
	public boolean supports(Class<?> clazz) {
		return PaymentProductivity.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		LOGGER.info("in [validate] method");
		PaymentProductivity paymentProductivity = (PaymentProductivity) target;

		if (this.validate) {
			LOGGER.info("in [validate] method : validate is TRUE : ");
			errors.rejectValue(Constants.PAYMENTBATCH_ID,
					"paymentProductivity.ticketNoMatcher");

		}
		if (paymentProductivity.getPaymentBatch() == null) {

			LOGGER.info("in [validate] method : getPaymentBatch().getId() is NULL : ");
			errors.rejectValue(Constants.PAYMENTBATCH_ID,
					"paymentProductivity.ticketNo");
		}
	}

	/**
	 *
	 * @param target
	 * @param errors
	 * @param paymentBatchId
	 */
	public void getValidate(Object target, Errors errors, String paymentBatchId) {
		LOGGER.info("in [getValidate] method : paymentBatchId = "
				+ paymentBatchId);
		PaymentProductivity paymentProductivity = (PaymentProductivity) target;
		long batchId = 0;
		try {
			batchId = Long.valueOf(paymentBatchId);
			paymentProductivity.getPaymentBatch().setId(batchId);
			this.validate = false;
			validate(paymentProductivity, errors);
		} catch (NumberFormatException e) {
			LOGGER.info("in [getValidate] method : NUMBERFORMAT EXCEPTION WHILE FORMATING IN LONG : ");
			if (paymentBatchId == null | paymentBatchId.trim().equals("")) {
				LOGGER.info("** paymentBatchId is NULL **");
				paymentProductivity.setPaymentBatch(null);
				validate(paymentProductivity, errors);
			} else {
				LOGGER.info("** paymentBatchId is NOT NULL **");
				this.validate = true;
				validate(target, errors);
			}

		} catch (Exception e) {
			LOGGER.info("in [getValidate] method : EXCEPTION WHILE FORMATING IN LONG : ");
			paymentProductivity.getPaymentBatch().setId(batchId);
			validate(paymentProductivity, errors);
		}
	}

}
