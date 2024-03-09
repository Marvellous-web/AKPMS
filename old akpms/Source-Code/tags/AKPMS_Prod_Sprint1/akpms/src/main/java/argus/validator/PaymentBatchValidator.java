package argus.validator;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import argus.domain.PaymentBatch;

@Service
public class PaymentBatchValidator extends LocalValidatorFactoryBean implements
		Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return PaymentBatch.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {

		PaymentBatch paymentBatch = (PaymentBatch) target;

		if (paymentBatch.getBillingMonth() == 0) {
			errors.rejectValue("billingMonth", "billingMonth.required");
		}
		if (paymentBatch.getBillingYear() == 0) {
			errors.rejectValue("billingYear", "billingYear.required");
		}
		if (paymentBatch.getDoctor().getId() == 0) {
			errors.rejectValue("doctor.id", "doctor.required");
		}
		if (paymentBatch.getPaymentType().getId() == 0) {
			errors.rejectValue("paymentType.id", "paymentType.required");
		}
		if (paymentBatch.getInsurance().getId() == 0) {
			errors.rejectValue("insurance.id", "insurance.required");
		}
		if (paymentBatch.getDepositDate() == null) {
			errors.rejectValue("depositDate", "depositDate.required");
		}
		if (paymentBatch.getDepositAmount().longValue() > 0
				&& paymentBatch.getNdba().longValue() > 0) {
			errors.rejectValue("depositAmount",
					"paymentBatch.eitherDepositAmountOrNDBA");

		}

	}

}
