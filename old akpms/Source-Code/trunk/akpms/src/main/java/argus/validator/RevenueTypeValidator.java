package argus.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import argus.domain.RevenueType;
import argus.repo.revenueType.RevenueTypeDao;

@Service
public class RevenueTypeValidator extends LocalValidatorFactoryBean implements
		Validator {

	private static final Logger LOGGER = Logger
			.getLogger(RevenueTypeValidator.class);

	@Autowired
	private RevenueTypeDao revenueTypeDao;

	@Override
	public boolean supports(Class<?> clazz) {
		return RevenueType.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		RevenueType revenueType = (RevenueType) target;
		Pattern revenueTypeNamePattern = Pattern
				.compile("^[a-zA-Z0-9_][\\sa-zA-Z0-9_,-]*");
		Matcher revenueTypeNameMatcher = revenueTypeNamePattern
				.matcher(revenueType.getName());

		if (revenueType.getName() == null
				|| revenueType.getName().trim().equals("")) {
			errors.rejectValue("name", "revenuetype.required");
		} else if ((revenueType.getName().trim().length() != 0)
				&& (!revenueTypeNameMatcher.matches())) {
			errors.rejectValue("name", "revenuetype.characters");
		}

		if (revenueType.getCode() == null
				|| revenueType.getCode().trim().equals("")) {
			errors.rejectValue("code", "revenuecode.required");
		}

		try {
			RevenueType isExist = null;
			isExist = revenueTypeDao.findByName(revenueType.getName());

			if (isExist != null) {
				if (revenueType.getId() == null) {
					LOGGER.info("duplicate revenue type name validation in case of add ");
					errors.rejectValue("name", "revenuetype.exist");
				} else if (revenueType.getId().longValue() != isExist.getId()
						.longValue()) {
					LOGGER.info("duplicate revenue type name validation in case of edit ");
					errors.rejectValue("name", "revenuetype.exist");
				}
			}

		} catch (Exception e) {
			LOGGER.info(
					"No revenue type Exists with name = "
							+ revenueType.getName(), e);
		}
	}
}
