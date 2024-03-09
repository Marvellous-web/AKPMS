/**
 *
 */
package argus.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.NoResultException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import argus.domain.paymentproductivity.PaymentProductivity;
import argus.repo.paymentproductivity.PaymentProductivityDao;
import argus.util.Constants;

/**
 * @author vishal.joshi
 *
 */
@Service
public class PaymentERAValidator extends LocalValidatorFactoryBean implements
		Validator {

	@Autowired
	private PaymentProductivityDao paymentProductivityDao;

	private static final Log LOGGER = LogFactory
			.getLog(PaymentERAValidator.class);

	private static final String ERROR_NULL = "ERROR: NULL ";

	@Override
	public boolean supports(Class<?> clazz) {
		return PaymentProductivity.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		LOGGER.info("in [validate] method");
		try {

		PaymentProductivity paymentProductivity = (PaymentProductivity) target;
		Pattern stringPattern = Pattern
				.compile("^[a-zA-Z0-9_][\\sa-zA-Z0-9_]*");
		Matcher stringNameMatcher = stringPattern.matcher(paymentProductivity
				.getChkNumber());

		if (paymentProductivity.getPaymentBatch().getId() == null) {
			LOGGER.info(Constants.EXCEPTION);
			errors.rejectValue(Constants.PAYMENTBATCH_ID,
					"paymentProductivity.ticketNo");

		}

		if (paymentProductivity.getChkNumber() == null
				|| paymentProductivity.getChkNumber().trim().equals("")) {
				LOGGER.info(Constants.CHK_NUMBER + " is NULL ");
			errors.rejectValue(Constants.CHK_NUMBER,
					"paymentProductivity.chknumber.required");
		}

		if ((paymentProductivity.getChkNumber().trim().length() != 0)
				&& (!stringNameMatcher.matches())) {
			LOGGER.info(Constants.CHK_NUMBER + " is NOT MATCHED  ");
			errors.rejectValue(Constants.CHK_NUMBER,
					"paymentProductivity.chknumber.characters");
		}

			if (paymentProductivity.getPaymentBatch().getElecPostedAmt() == null) {
			LOGGER.info(Constants.ELEC_POSTED_AMT + " is NULL ");
			errors.rejectValue(Constants.ELEC_POSTED_AMT,
					"paymentProductivity.elecPostedAmt.required");
		}

			if (paymentProductivity.getPaymentBatch().getManuallyPostedAmt() != null
					&& paymentProductivity.getPaymentBatch().getElecPostedAmt() != null
				&& paymentProductivity.getPaymentBatch() != null
				&& paymentProductivity.getPaymentBatch().getDepositAmount() != null
				&& paymentProductivity.getPaymentBatch().getDepositAmount() != 0) {

			if (paymentProductivity.getPaymentBatch().getDepositAmount() < (paymentProductivity
						.getPaymentBatch().getManuallyPostedAmt() + paymentProductivity
						.getPaymentBatch().getElecPostedAmt())) {
				errors.rejectValue("ctTotalPosted",
						"paymentProductivity.greater");
			}

		}

			if (paymentProductivity.getPaymentBatch().getManuallyPostedAmt() != null
					&& paymentProductivity.getPaymentBatch().getElecPostedAmt() != null
				&& paymentProductivity.getPaymentBatch() != null
				&& paymentProductivity.getPaymentBatch().getNdba() != null
				&& paymentProductivity.getPaymentBatch().getNdba() != 0) {

			if (paymentProductivity.getPaymentBatch().getNdba() < (paymentProductivity
						.getPaymentBatch().getManuallyPostedAmt() + paymentProductivity
						.getPaymentBatch().getElecPostedAmt())) {
				errors.rejectValue("ctTotalPosted",
						"paymentProductivity.greater");
			}

		}

			if (paymentProductivity.getPaymentBatch().getDatePosted() == null) {
			LOGGER.info(Constants.DATE_POSTED + " is NULL ");
			errors.rejectValue(Constants.DATE_POSTED,
					"paymentProductivity.datePosted.required");
		}

		if (paymentProductivity.getElecTransaction() == null) {
			LOGGER.info(Constants.ELEC_TRANSACTION + " is NULL ");
			errors.rejectValue(Constants.ELEC_TRANSACTION,
					"paymentProductivity.elecTransaction.required");
		}

		if (paymentProductivity.getWorkFlowId() == 0) {
			LOGGER.info(Constants.WORKFLOW_ID + " is NULL ");
				errors.rejectValue("workFlowId",
					"paymentProductivity.workFlowId.required");
		}

		PaymentProductivity isExist = null;

		try {
			isExist = paymentProductivityDao.findByTicketNo(paymentProductivity
					.getPaymentBatch().getId());
		} catch (NoResultException e) {
			LOGGER.error(e.toString());
		} catch (Exception e) {
			LOGGER.error(e.toString());
		}

		if (isExist != null & paymentProductivity.getId() == null) {
			LOGGER.info("Ticket number already updated");
			errors.rejectValue("paymentBatch.id", "paymentBatch.id.duplicate");
		}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}

	}
}
