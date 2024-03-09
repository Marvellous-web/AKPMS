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

import argus.domain.Doctor;
import argus.repo.doctor.DoctorDao;
import argus.util.Constants;

@Service
public class DoctorValidator extends LocalValidatorFactoryBean implements
		Validator {

	private static final Log LOGGER = LogFactory.getLog(DoctorValidator.class);

	@Autowired
	private DoctorDao doctorDao;

	@Override
	public boolean supports(Class<?> clazz) {
		return Doctor.class.isAssignableFrom(clazz);
	}

	private static Errors getNameValidate(Doctor doctor, Errors errors,
			Matcher doctorNameMatcher) {

		if (doctor.getName() == null || doctor.getName().trim().equals("")) {
			errors.rejectValue(Constants.BY_NAME, "doctor.required");
			return errors;
		}
		if ((doctor.getName().trim().length() != 0)
				&& (!doctorNameMatcher.matches())) {
			errors.rejectValue(Constants.BY_NAME, "doctor.characters");
			return errors;
		}

		return errors;

	}

	private static Errors getDoctorValidate(Doctor doctor, Errors errors,
			Pattern doctorNamePattern) {

		Matcher doctorCode = doctorNamePattern.matcher(doctor.getDoctorCode());
		if (doctor.getDoctorCode() == null
				|| doctor.getDoctorCode().trim().equals("")) {
			errors.rejectValue(Constants.DOCTOR_CODE, "doctor.code.required");
		}
		if ((doctor.getDoctorCode().trim().length() != 0)
				&& (!doctorCode.matches())) {
			errors.rejectValue(Constants.DOCTOR_CODE, "doctor.characters");
		}

		return errors;
	}

	public void validate(Object target, Errors errors) {
		LOGGER.info("in [validate] method : ");
		Doctor doctor = (Doctor) target;
		Pattern doctorNamePattern = Pattern
				.compile("^[a-zA-Z0-9_][\\sa-zA-Z0-9_]*");
		Matcher doctorNameMatcher = doctorNamePattern.matcher(doctor.getName());

		getNameValidate(doctor, errors, doctorNameMatcher);

		getDoctorValidate(doctor, errors, doctorNamePattern);

		try {
			Doctor isDoctor = doctorDao.findByName(doctor.getName());

			if (isDoctor != null) {
				if (doctor.getId() == null) {
					LOGGER.info("duplicate doctor name validation in case of add doctor");
					errors.rejectValue("name", "doctor.exist");
				} else if (doctor.getId().longValue() != isDoctor.getId()
						.longValue()) {
					LOGGER.info("duplicate doctor name validation in case of edit doctor");
					errors.rejectValue("name", "doctor.exist");
				}
			}

		} catch (Exception e) {
			LOGGER.info("No Doctor Exists with name = " + doctor.getName(), e);
		}
		try {
			Doctor isDoctor = doctorDao.findByDoctorCode(doctor.getDoctorCode()
					.trim(), false);
			if (isDoctor != null) {
				if (doctor.getId() == null) {
					LOGGER.info("duplicate doctor code validation in case of add doctor");
					errors.rejectValue(Constants.DOCTOR_CODE,
							"doctor.code.exist");
				} else if (doctor.getId().longValue() != isDoctor.getId()
						.longValue()) {
					LOGGER.info("duplicate doctor code validation in case of edit doctor");
					errors.rejectValue(Constants.DOCTOR_CODE,
							"doctor.code.exist");
				}
			}

		} catch (Exception e) {
			LOGGER.info(
					"No Doctor Exists with code = " + doctor.getDoctorCode(), e);
		}

		LOGGER.info("out [validate] method : ");
	}
}
