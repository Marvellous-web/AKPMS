package argus.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import argus.domain.PaymentType;
import argus.repo.payment.PaymentTypeDao;
@Service
public class PaymentTypeValidator extends LocalValidatorFactoryBean implements
		Validator {

	private static final Log LOGGER = LogFactory.getLog(PaymentTypeValidator.class);

	@Autowired
	private PaymentTypeDao paymentDao;

	@Override
	public boolean supports(Class<?> clazz) {
		return PaymentType.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		PaymentType payment = (PaymentType) target;
		Pattern paymentNamePattern = Pattern
				.compile("^[a-zA-Z0-9_][\\sa-zA-Z0-9_]*");
		Matcher paymentNameMatcher = paymentNamePattern.matcher(payment
				.getName());

		if (payment.getName() == null
				|| payment.getName().trim().equals("")) {
			errors.rejectValue("name", "paymenttype.required");
		}
		if ((payment.getName().trim().length() != 0)
				&& (!paymentNameMatcher.matches())) {
			errors.rejectValue("name", "paymenttype.characters");
		}
		PaymentType isExist = null;
		try {
			isExist = paymentDao.findByNameAndType(payment.getName(),
					payment.getType());
		} catch (Exception e) {
			LOGGER.error(e.toString());
		}

		if (isExist != null) {
			if (payment.getId() == null) {
				LOGGER.info("duplicate payment name validation in case of add payment");
				errors.rejectValue("name", "paymenttype.exist");
			}
		}
	}
}
