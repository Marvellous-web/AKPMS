package argus.validator;

import org.apache.log4j.Logger;
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

	private static final Logger LOGGER = Logger
			.getLogger(PaymentTypeValidator.class);

	@Autowired
	private PaymentTypeDao paymentDao;

	@Override
	public boolean supports(Class<?> clazz) {
		return PaymentType.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		PaymentType paymentType = (PaymentType) target;
		// special char validation removed on 02-jul-14 after Annie's email
//		Pattern paymentNamePattern = Pattern
//				.compile("^[a-zA-Z0-9_][\\sa-zA-Z0-9_,-]*");
//		Matcher paymentNameMatcher = paymentNamePattern.matcher(paymentType
//				.getName());

		if (paymentType.getName() == null
				|| paymentType.getName().trim().equals("")) {
			errors.rejectValue("name", "paymenttype.required");
		}
//		else if ((paymentType.getName().trim().length() != 0)
//				&& (!paymentNameMatcher.matches())) {
//			errors.rejectValue("name", "paymenttype.characters");
//		}

		try {
			PaymentType isExist = null;
			isExist = paymentDao.findByName(paymentType.getName());

			if (isExist != null) {
				if (paymentType.getId() == null) {
					LOGGER.info("duplicate payment type name validation in case of add ");
					errors.rejectValue("name", "paymenttype.exist");
				} else if (paymentType.getId().longValue() != isExist.getId()
						.longValue()) {
					LOGGER.info("duplicate payment type name validation in case of edit ");
					errors.rejectValue("name", "paymenttype.exist");
				}
			}

		} catch (Exception e) {
			LOGGER.info(
					"No payment type Exists with name = "
							+ paymentType.getName(), e);
		}
	}
}
