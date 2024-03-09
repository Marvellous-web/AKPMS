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

import argus.domain.QAWorksheetStaff;
import argus.exception.ArgusException;
import argus.repo.qamanager.QAWorksheetStaffDao;
import argus.util.Constants;

@Component
public class QAWorksheetStaffValidator implements Validator{
	private static Logger LOGGER = Logger
			.getLogger(QAWorksheetStaffValidator.class);
	@Autowired
	private QAWorksheetStaffDao qaworksheetStaffDao;

	@Override
	public boolean supports(Class<?> clazz) {
		return QAWorksheetStaff.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		QAWorksheetStaff qaworksheetStaff = (QAWorksheetStaff) target;

		ValidationUtils.rejectIfEmpty(errors, "user.id", "required",
				new Object[] { "User" });
		ValidationUtils.rejectIfEmpty(errors, "percentageValue", "required",
				new Object[] { "Percentage Value" });


		if (!errors.hasFieldErrors("user.id")) {
			Map<String, String> where = new HashMap<String, String>();
			where.put(Constants.USER, qaworksheetStaff.getUser().getId()
					.toString());
			where.put(Constants.QAWORKSHEET_ID, qaworksheetStaff
					.getQaWorksheet().getId().toString());
			try {
				List<QAWorksheetStaff> qaworksheetStaffList = qaworksheetStaffDao
						.findAll(null, where, true);

				if (qaworksheetStaffList != null
						&& qaworksheetStaffList.size() != 0) {
					errors.rejectValue("user.id", "user.already.exist",
							new Object[] { qaworksheetStaffList.get(0)
									.getUser().getFirstName()
									+ " "
									+ qaworksheetStaffList.get(0).getUser()
											.getLastName() }, "");
				}
			} catch (ArgusException e) {
				LOGGER.error("Failed to fetch qaworksheet staff", e);
			}
		}
		if (!errors.hasFieldErrors("percentageValue")) {
			if (qaworksheetStaff.getPercentageValue() > Constants.PERCENT_LIMIT) {
				errors.rejectValue("percentageValue", "invalid.percentage");
			} else if (qaworksheetStaff.getPercentageValue() <= 0) {
				errors.rejectValue("percentageValue", "invalid.percentage");
			}
		}
	}
}
