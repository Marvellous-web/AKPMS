/**
 *
 */
package argus.validator;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import argus.domain.paymentproductivity.OffsetPostingRecord;
import argus.domain.paymentproductivity.PaymentPostingByOffSetManager;
import argus.util.Constants;

/**
 * @author jasdeep.s
 *
 */
@Service
public class OffsetManagerValidator extends LocalValidatorFactoryBean implements
		Validator {
	private static final Logger LOGGER = Logger
			.getLogger(OffsetManagerValidator.class);

	private static final String POSTING_RECORDS = "postingRecords";

	@Override
	public boolean supports(Class<?> clazz) {
		return OffsetManagerValidator.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		LOGGER.info("in [validate] method");
		PaymentPostingByOffSetManager offSetManager = (PaymentPostingByOffSetManager) target;

		if (offSetManager.getPostingRecords() != null) {
			if (!offSetManager.getPostingRecords().isEmpty()) {
				for (OffsetPostingRecord offsetRecord : offSetManager
						.getPostingRecords()) {
					boolean foundError = false;
					if ((offsetRecord.getCpt() == null || offsetRecord.getCpt()
							.trim().length() == 0)
							|| offsetRecord.getDosFrom() == null
							|| offsetRecord.getDosTo() == null
							|| offsetRecord.getAmount() == null) {
						foundError = true;
						if (offSetManager.getPostingRecords().size() == 1) {
							errors.rejectValue(POSTING_RECORDS,
									"paymentProductivity.offsetRecords.single.required");
						} else {
							errors.rejectValue(POSTING_RECORDS,
									"paymentProductivity.offsetRecords.multiple.required");
						}
					}
					if (offsetRecord.getDosFrom() != null
							&& offsetRecord.getDosTo() != null) {
						if (offsetRecord.getDosFrom().compareTo(
								offsetRecord.getDosTo()) > 0) {
							foundError = true;
							errors.rejectValue(POSTING_RECORDS,
									"paymentProductivity.offsetRecords.date");
						}
					}
					if (foundError) {
						break;
					}
				}
			} else {
				errors.rejectValue(POSTING_RECORDS,
						"paymentProductivity.offsetRecords.empty.required");
			}
		}

		if (offSetManager.getPatientName() == null
				|| String.valueOf(offSetManager.getPatientName()).trim()
						.equals("")) {
			errors.rejectValue(Constants.PATIENT_NAME,
					"offsetPosting_Patient_Name.required");
			LOGGER.info("ERROR: NULL "
					+ errors.getFieldValue(Constants.PATIENT_NAME));
		}
		if (offSetManager.getAccountNumber() == null
				|| String.valueOf(offSetManager.getAccountNumber()).trim()
						.equals("")) {
			errors.rejectValue(Constants.ACCOUNT_NUMBER,
					"offsetPosting_AccountNumber.required");
			LOGGER.info("ERROR: NULL "
					+ errors.getFieldValue(Constants.ACCOUNT_NUMBER));
		}
		if (offSetManager.getCheckNumber() == null
				|| offSetManager.getCheckNumber().trim().equals("")) {
			errors.rejectValue("checkNumber",
					"offsetPosting_checkNumber.required");

		}
		if (offSetManager.getDateOfCheck() == null) {
			errors.rejectValue("dateOfCheck",
					"offsetPosting_dateOfCheck.required");
		}
	}
}
