/**
 *
 */
package argus.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import argus.domain.paymentproductivity.OffsetRecord;
import argus.domain.paymentproductivity.PaymentProductivityOffset;
import argus.util.Constants;

/**
 * @author rajiv.k
 *
 */
@Service
public class EraOffsetValidator extends LocalValidatorFactoryBean implements
		Validator {
	private static final Logger LOGGER = Logger
			.getLogger(EraOffsetValidator.class);
	private static final String ERROR_NULL = "ERROR: NULL ";
	@Override
	public boolean supports(Class<?> clazz) {
		return PaymentProductivityOffset.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		LOGGER.info("in [validate] method");
		PaymentProductivityOffset paymentProductivityOffset = (PaymentProductivityOffset) target;

		Pattern offsetPattern = Pattern
				.compile("^[a-zA-Z0-9_][\\sa-zA-Z0-9_]*");

		getChkValidate(paymentProductivityOffset, errors);

		if (paymentProductivityOffset.getChkDate() == null
				|| String.valueOf(paymentProductivityOffset.getChkDate())
						.trim().equals("")) {
			errors.rejectValue(Constants.CHK_DATE,
					"requestForCheckIssueDate.required");
			LOGGER.info(ERROR_NULL
					+ errors.getFieldValue(Constants.CHK_DATE));
		}

		if (paymentProductivityOffset.getPatientName() == null
				|| String.valueOf(paymentProductivityOffset.getPatientName())
						.trim().equals("")) {
			errors.rejectValue(Constants.PATIENT_NAME,
					"arProductivityPatientName.required");
			LOGGER.info(ERROR_NULL
					+ errors.getFieldValue(Constants.PATIENT_NAME));
		}

		getAccountNoValidate(paymentProductivityOffset, errors, offsetPattern);

		if (paymentProductivityOffset.getRemark() == null
				|| String.valueOf(paymentProductivityOffset.getRemark()).trim()
						.equals("")) {
			errors.rejectValue(Constants.REMARK,
					"paymentProductivity.remark.required");
			LOGGER.info(ERROR_NULL + errors.getFieldValue(Constants.REMARK));
		}

		if (paymentProductivityOffset.getOffsetRecords() != null) {
			if (!paymentProductivityOffset.getOffsetRecords().isEmpty()) {
				for (OffsetRecord offsetRecord : paymentProductivityOffset
						.getOffsetRecords()) {
					boolean foundError = false;
					if ((offsetRecord.getCpt() == null || offsetRecord.getCpt()
							.trim().length() == 0)
							|| offsetRecord.getDos() == null
							|| offsetRecord.getAmount() == null) {
						foundError = true;
						if (paymentProductivityOffset.getOffsetRecords().size() == 1) {
							errors.rejectValue("offsetRecords",
									"paymentProductivity.offsetRecords.single.required");
						} else {
							errors.rejectValue("offsetRecords",
									"paymentProductivity.offsetRecords.multiple.required");
						}
					}
					if (foundError) {
						break;
					}
				}
			} else {
				errors.rejectValue("offsetRecords",
						"paymentProductivity.offsetRecords.empty.required");
			}
		}
	}

	private static Errors getChkValidate(
			PaymentProductivityOffset paymentProductivityOffset, Errors errors) {

		LOGGER.info("in [getChkValidate] method");
		if (paymentProductivityOffset.getChkNumber() == null
				|| paymentProductivityOffset.getChkNumber().trim().equals("")) {
			errors.rejectValue(Constants.CHK_NUMBER,
					"requestForCheckNumber.required");
			LOGGER.info(ERROR_NULL
					+ errors.getFieldValue(Constants.CHK_NUMBER));
		}
		return errors;

	}

	private static Errors getAccountNoValidate(
			PaymentProductivityOffset paymentProductivityOffset, Errors errors,
			Pattern offsetPattern) {
		LOGGER.info("in [getAccountNoValidate] method");
		Matcher accountNoMatcher = offsetPattern
				.matcher(paymentProductivityOffset.getAccountNumber());
		if (paymentProductivityOffset.getAccountNumber() == null
				|| paymentProductivityOffset.getAccountNumber().trim()
						.equals("")) {
			errors.rejectValue(Constants.ACCOUNT_NUMBER,
					"paymentProductivity.accountNumber.required");
			LOGGER.info(ERROR_NULL
					+ errors.getFieldValue(Constants.ACCOUNT_NUMBER));
		}
		if ((paymentProductivityOffset.getAccountNumber().trim().length() != 0)
				&& (!accountNoMatcher.matches())) {
			errors.rejectValue(Constants.ACCOUNT_NUMBER,
					"paymentProductivity.accountNumber.characters");
			LOGGER.info("ERROR: Char "
					+ errors.getFieldValue(Constants.ACCOUNT_NUMBER));
		}

		LOGGER.info("ERROR: Char "
				+ errors.getFieldValue(Constants.ACCOUNT_NUMBER));

		return errors;

	}

}
