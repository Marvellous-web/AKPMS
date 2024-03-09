package argus.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import argus.domain.Location;
import argus.repo.location.LocationDao;
import argus.util.Constants;

/**
 *
 * @author vishal.joshi
 *
 */
@Service
public class LocationValidator extends LocalValidatorFactoryBean implements
		Validator {

	private static final Logger LOGGER = Logger
			.getLogger(LocationValidator.class);

	@Autowired
	private LocationDao locationDao;

	@Override
	public boolean supports(Class<?> clazz) {
		return Location.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		Location location = (Location) target;
		Pattern locationNamePattern = Pattern
				.compile("^[a-zA-Z0-9_][\\sa-zA-Z0-9_]*");
		Matcher locationNameMatcher = locationNamePattern.matcher(location
				.getName());

		if (location.getName() == null || location.getName().trim().equals("")) {
			errors.rejectValue(Constants.BY_NAME, "location.required");
		}
		if ((location.getName().trim().length() != 0)
				&& (!locationNameMatcher.matches())) {
			errors.rejectValue(Constants.BY_NAME, "location.characters");
		}

		try {
			Location isLocation = locationDao.findByName(location.getName());

			if (isLocation != null) {
				if (location.getId() == null) {
					LOGGER.info("duplicate location name validation in case of add location");
					errors.rejectValue(Constants.BY_NAME, "location.exist");
				} else if (location.getId().longValue() != isLocation.getId()
						.longValue()) {
					LOGGER.info("duplicate location name validation in case of edit location");
					errors.rejectValue(Constants.BY_NAME, "location.exist");
				}
			}

		} catch (Exception e) {
			LOGGER.info(
"No location Exists with name = " + location.getName(),
					e);
		}
	}
}
