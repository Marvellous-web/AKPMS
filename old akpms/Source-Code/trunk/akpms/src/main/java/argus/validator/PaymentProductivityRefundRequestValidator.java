package argus.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import argus.domain.paymentproductivity.PaymentProductivityRefundRequest;

@Service
public class PaymentProductivityRefundRequestValidator extends
		LocalValidatorFactoryBean implements Validator {

	private static final Logger LOGGER = Logger
			.getLogger(PaymentProductivityRefundRequestValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return PaymentProductivityRefundRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		LOGGER.info("int [validate] method");
		PaymentProductivityRefundRequest paymentProductivityRefundRequest = (PaymentProductivityRefundRequest) target;

		Pattern stringPattern = Pattern
				.compile("^[a-zA-Z0-9_][\\sa-zA-Z0-9_]*");
		Matcher stringNameMatcher = stringPattern
				.matcher(paymentProductivityRefundRequest.getAccountNumber());

		if (paymentProductivityRefundRequest.getDoctor() == null
				|| paymentProductivityRefundRequest.getDoctor().getId() == 0) {
			errors.rejectValue("doctor.id", "doctor.required");
		}
		if (paymentProductivityRefundRequest.getPatientName() == null
				|| paymentProductivityRefundRequest.getPatientName().trim()
						.equals("")) {
			errors.rejectValue("patientName", "patientName.required");
		}
		if (paymentProductivityRefundRequest.getAccountNumber() == null
				|| paymentProductivityRefundRequest.getAccountNumber().trim()
						.equals("")) {
			errors.rejectValue("accountNumber", "accountNumber.required");
		}

		if ((paymentProductivityRefundRequest.getAccountNumber().trim()
				.length() != 0)
				&& (!stringNameMatcher.matches())) {
			errors.rejectValue("accountNumber", "accountNumber.invalid");
		}

		if (paymentProductivityRefundRequest.getCreditAmount() == null
				|| paymentProductivityRefundRequest.getCreditAmount() == 0.0) {
			errors.rejectValue("creditAmount", "creditAmount.required");
		}
		if (paymentProductivityRefundRequest.getTransactionDate() == null) {
			errors.rejectValue("transactionDate", "transactionDate.required");
		}

	}

}
