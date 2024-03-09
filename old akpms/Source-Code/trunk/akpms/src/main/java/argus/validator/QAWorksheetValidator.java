package argus.validator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import argus.domain.QAWorksheet;
import argus.exception.ArgusException;
import argus.repo.qamanager.QAWorksheetDao;
import argus.util.Constants;

@Component
public class QAWorksheetValidator implements Validator {

	private static Logger LOGGER = Logger.getLogger(QAWorksheetValidator.class);

	@Autowired
	private QAWorksheetDao qaWorksheetDao;

	@Override
	public boolean supports(Class<?> clazz) {
		return QAWorksheet.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		QAWorksheet qaworksheet = (QAWorksheet) target;


		ValidationUtils.rejectIfEmpty(errors, "billingMonth", "required",
				new Object[] { "Billing Month" });
		ValidationUtils.rejectIfEmpty(errors, "billingYear", "required",
				new Object[] { "Billing Year" });
		ValidationUtils.rejectIfEmpty(errors, "name", "required",
				new Object[] { "Worksheet Name" });
		ValidationUtils.rejectIfEmpty(errors, "type", "required",
				new Object[] { "By Staff or General" });
		ValidationUtils.rejectIfEmpty(errors, "department.id", "required",
				new Object[] { "Department" });

		// in case of charge department, sub-department is required
		// validation removed
		// if (!(qaworksheet.getDepartment() == null || qaworksheet
		// .getDepartment().getId() == null)) {
		// if (Constants.CHARGE_DEPARTMENT_ID.equals(qaworksheet
		// .getDepartment().getId().toString())
		// && (qaworksheet.getSubDepartment() == null || qaworksheet
		// .getSubDepartment().getId() == -1)) {
		// errors.rejectValue("subDepartment.id", "subdepartment.required");
		// }
		// }

		try {
			/*
			 * check for duplicate QAWorksheet name but not in case of edit.
			 */
			if (qaworksheet.getId() == null && !errors.hasFieldErrors("name")
					&& qaWorksheetDao.findByName(qaworksheet.getName()) != null) {
				errors.rejectValue("name", "qaworksheet.already.exist",
						new Object[] { qaworksheet.getName() }, "");
			}
		} catch (ArgusException e) {
			LOGGER.error("Failed to get QAWorksheet", e);
		}

		if (qaworksheet.getType() != null
				&& qaworksheet.getType() == Constants.QA_WORKSEET_TYPE_GENERAL) {
			ValidationUtils.rejectIfEmpty(errors, "generalPercentage",
					"required", new Object[] { "Percentage for qa" });
			if (!errors.hasFieldErrors("generalPercentage")) {
				if (qaworksheet.getGeneralPercentage() > Constants.PERCENT_LIMIT) {
					errors.rejectValue("generalPercentage",
							"invalid.percentage");
				}
			}
		}
	}

}
