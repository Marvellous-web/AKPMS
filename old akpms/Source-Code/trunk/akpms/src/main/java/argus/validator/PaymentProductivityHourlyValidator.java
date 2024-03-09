package argus.validator;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import argus.domain.paymentproductivity.PaymentProductivityHourly;

@Service
public class PaymentProductivityHourlyValidator extends
		LocalValidatorFactoryBean implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return PaymentProductivityHourly.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		PaymentProductivityHourly paymentProductivityHourly = (PaymentProductivityHourly) target;

		if ((paymentProductivityHourly.getHours() == null && paymentProductivityHourly
				.getMinutes() == null)
				|| (paymentProductivityHourly.getHours() == 0 && paymentProductivityHourly
						.getMinutes() == 0)) {
			errors.rejectValue("hours", "time.required");
		}
		if (paymentProductivityHourly.getHourlyTask().getId() == 0) {
			errors.rejectValue("hourlyTask.id", "taskName.required");
		}
		if (paymentProductivityHourly.getDetail() == null
				|| paymentProductivityHourly.getDetail().trim().equals("")) {
			errors.rejectValue("detail", "detail.required");
		}
	}
}
