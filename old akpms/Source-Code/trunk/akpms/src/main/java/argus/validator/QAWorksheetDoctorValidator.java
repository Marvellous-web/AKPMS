package argus.validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import argus.domain.QAWorksheetDoctor;
import argus.exception.ArgusException;
import argus.repo.qamanager.QAWorksheetDoctorDao;
import argus.util.Constants;

@Component
public class QAWorksheetDoctorValidator implements Validator {
	private static Logger LOGGER = Logger
			.getLogger(QAWorksheetDoctorValidator.class);
	@Autowired
	private QAWorksheetDoctorDao qaworksheetDoctorDao;

	@Override
	public boolean supports(Class<?> clazz) {
		return QAWorksheetDoctor.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		QAWorksheetDoctor qaworksheetDoctor = (QAWorksheetDoctor) target;

		ValidationUtils.rejectIfEmpty(errors, "doctor.id", "required",
				new Object[] { "Doctor" });
		ValidationUtils.rejectIfEmpty(errors, "percentageValue", "required",
				new Object[] { "Percentage Value" });

		if (!errors.hasFieldErrors("doctor.id")) {
			Map<String, String> where = new HashMap<String, String>();
			where.put(Constants.DOCTOR, qaworksheetDoctor.getDoctor().getId()
					.toString());
			where.put(Constants.QAWORKSHEET_ID, qaworksheetDoctor
					.getQaWorksheet().getId().toString());
			try {
				List<QAWorksheetDoctor> qaworksheetDoctorList = qaworksheetDoctorDao
						.findAll(null, where, true);

				if (qaworksheetDoctorList != null
						&& qaworksheetDoctorList.size() != 0) {
					errors.rejectValue("doctor.id", "doctor.already.exist",
							new Object[] { qaworksheetDoctorList.get(0)
									.getDoctor().getName() }, "");
				}
			} catch (ArgusException e) {
				LOGGER.error("Failed to fetch qaworksheet doctor", e);
			}
		}
		if (!errors.hasFieldErrors("percentageValue")) {
			if (qaworksheetDoctor.getPercentageValue() > Constants.PERCENT_LIMIT) {
				errors.rejectValue("percentageValue", "invalid.percentage");
			} else if (qaworksheetDoctor.getPercentageValue() <= 0) {
				errors.rejectValue("percentageValue", "invalid.percentage");
			}
		}
	}
}
