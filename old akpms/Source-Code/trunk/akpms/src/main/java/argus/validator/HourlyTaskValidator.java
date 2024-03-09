package argus.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import argus.domain.HourlyTask;
import argus.repo.taskname.HourlyTaskDao;

@Service
public class HourlyTaskValidator extends LocalValidatorFactoryBean implements
Validator {

	private static final Logger LOGGER = Logger
			.getLogger(HourlyTaskValidator.class);

	@Autowired
	private HourlyTaskDao hourlyTaskDao;


	@Override
	public boolean supports(Class<?> clazz) {
		return HourlyTask.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		HourlyTask hourlyTask = (HourlyTask) target;
		Pattern paymentNamePattern = Pattern
				.compile("^[a-zA-Z0-9_][\\sa-zA-Z0-9_,-]*");
		Matcher paymentNameMatcher = paymentNamePattern.matcher(hourlyTask
				.getName());

		if (hourlyTask.getName() == null
				|| hourlyTask.getName().trim().equals("")) {
			errors.rejectValue("name", "hourlyTask.required");
		} else if ((hourlyTask.getName().trim().length() != 0)
				&& (!paymentNameMatcher.matches())) {
			errors.rejectValue("name", "hourlyTask.characters");
		}

		// ValidationUtils.rejectIfEmpty(errors, "department.id",
		// "hourlyTask.department.required", null, null);

		try {
			HourlyTask isExist = null;
			isExist = hourlyTaskDao.findByName(hourlyTask.getName());

			if (isExist != null) {
				if (hourlyTask.getId() == null) {
					LOGGER.info("duplicate Hourly Task name validation in case of add ");
					errors.rejectValue("name", "hourlyTask.exist");
				} else if (hourlyTask.getId().longValue() != isExist.getId()
						.longValue()) {
					LOGGER.info("duplicate payment type name validation in case of edit ");
					errors.rejectValue("name", "hourlyTask.exist");
				}
			}

		} catch (Exception e) {
			LOGGER.info(
					"No Hourly Task Exists with name = "
							+ hourlyTask.getName(), e);
		}
	}
}
