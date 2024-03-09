/**
 *
 */
package argus.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import argus.domain.paymentproductivity.PaymentProdQueryToTL;
import argus.util.Constants;

/**
 * @author vishal.joshi
 *
 */
@Service
public class EraQueryToTLValidator extends LocalValidatorFactoryBean implements
		Validator {

	private static final Log LOGGER = LogFactory
			.getLog(EraQueryToTLValidator.class);

	private static final String ERROR_NULL = "ERROR: NULL ";
	@Override
	public boolean supports(Class<?> clazz) {
		return PaymentProdQueryToTL.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		LOGGER.info("in [validate] method");
		PaymentProdQueryToTL prodQueryToTL = (PaymentProdQueryToTL) target;

		Pattern queryToTLPattern = Pattern
				.compile("^[a-zA-Z0-9_][\\sa-zA-Z0-9_]*");

		getChkValidate(prodQueryToTL, errors);

		if (prodQueryToTL.getChkDate() == null
				|| String.valueOf(prodQueryToTL.getChkDate()).trim().equals("")) {
			errors.rejectValue(Constants.CHK_DATE,
					"requestForCheckIssueDate.required");
			LOGGER.info(ERROR_NULL
					+ errors.getFieldValue(Constants.CHK_DATE));
		}

		if (prodQueryToTL.getPatientName() == null
				|| String.valueOf(prodQueryToTL.getPatientName()).trim()
						.equals("")) {
			errors.rejectValue(Constants.PATIENT_NAME,
					"arProductivityPatientName.required");
			LOGGER.info(ERROR_NULL
					+ errors.getFieldValue(Constants.PATIENT_NAME));
		}

		if (prodQueryToTL.getAmount() == null) {
			errors.rejectValue(Constants.AMOUNT,
					"paymentProductivity.amount.required");
			LOGGER.info(ERROR_NULL + errors.getFieldValue(Constants.AMOUNT));
		}

		getAccountNoValidate(prodQueryToTL, errors, queryToTLPattern);

	}

	private static Errors getChkValidate(PaymentProdQueryToTL prodQueryToTL,
			Errors errors) {

		LOGGER.info("in [getChkValidate] method");
		if (prodQueryToTL.getChkNumber() == null
				|| prodQueryToTL.getChkNumber().trim().equals("")) {
			errors.rejectValue(Constants.CHK_NUMBER,
					"requestForCheckNumber.required");
			LOGGER.info(ERROR_NULL
					+ errors.getFieldValue(Constants.CHK_NUMBER));
		}
		return errors;

	}

	private static Errors getAccountNoValidate(
			PaymentProdQueryToTL prodQueryToTL, Errors errors,
			Pattern queryToTLPattern) {
		LOGGER.info("in [getAccountNoValidate] method");
		Matcher accountNoMatcher = queryToTLPattern.matcher(prodQueryToTL
				.getAccountNumber());
		if (prodQueryToTL.getAccountNumber() == null
				|| prodQueryToTL.getAccountNumber().trim().equals("")) {
			errors.rejectValue(Constants.ACCOUNT_NUMBER,
					"paymentProductivity.accountNumber.required");
			LOGGER.info(ERROR_NULL
					+ errors.getFieldValue(Constants.ACCOUNT_NUMBER));
		}
		if ((prodQueryToTL.getAccountNumber().trim().length() != 0)
				&& (!accountNoMatcher.matches())) {
			LOGGER.info("in [validate] Char method " + Constants.ACCOUNT_NUMBER);
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
