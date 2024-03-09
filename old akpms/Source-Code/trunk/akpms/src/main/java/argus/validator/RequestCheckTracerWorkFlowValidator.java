/**
 *
 */
package argus.validator;

import java.util.Date;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import argus.domain.RequestCheckTracerWorkFlow;

/**
 * @author vishal.joshi
 *
 */
@Service
public class RequestCheckTracerWorkFlowValidator extends
		LocalValidatorFactoryBean implements Validator {

	private static final Logger LOGGER = Logger
			.getLogger(RequestCheckTracerWorkFlowValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return RequestCheckTracerWorkFlow.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		LOGGER.info("in [validate] method : ");
		RequestCheckTracerWorkFlow checkTracerWorkFlow = (RequestCheckTracerWorkFlow) target;
		isNullString(checkTracerWorkFlow.getCheckMailingAdd(),
				checkTracerWorkFlow.getCheckNo(),
				checkTracerWorkFlow.getCheckAmount(), checkTracerWorkFlow
						.getAttachment().getAttachedFile().getSize(), errors,
				checkTracerWorkFlow);
		checkDate(checkTracerWorkFlow.getCheckIssueDate(),
				checkTracerWorkFlow.getCheckCashedDate(), errors);


	}

	/**
	 *
	 * @param checkMailingAdd
	 * @param checkNo
	 * @param errors
	 * @return
	 */
	private static Errors isNullString(String checkMailingAdd, String checkNo,
			Float amount, Long attachmentSize, Errors errors,
			RequestCheckTracerWorkFlow checkTracerWorkFlow) {
		LOGGER.info("in [isNullString] method : checkMailingAdd = "
				+ checkMailingAdd + ", checkNo = " + checkNo);
		if (checkMailingAdd == null | checkMailingAdd.trim().equals("")) {
			errors.rejectValue("checkMailingAdd",
					"requestForCheckTracer.required");
		}
		if (checkNo == null | checkNo.trim().equals("")) {
			errors.rejectValue("checkNo", "requestForCheckNumber.required");
		}

		if (amount == null) {
			errors.rejectValue("checkAmount", "requestForCheckAmount.required");
		}
		if (checkTracerWorkFlow.getId() == null && attachmentSize == 0) {
			errors.rejectValue("attachment.attachedFile",
					"requestForCheckAttachment.required");
		}
		return errors;
	}

	/**
	 *
	 * @param checkDate
	 * @param chasedDate
	 * @param errors
	 * @return
	 */
	private static Errors checkDate(Date checkDate, Date chasedDate,
			Errors errors) {
		LOGGER.info("in [checkDate] method ");
		try {
			if (checkDate != null & chasedDate != null
					& chasedDate.before(checkDate)) {
				errors.rejectValue("checkCashedDate",
						"requestForCheckTracer.dateValidation");
			}
		} catch (Exception e) {
			errors.rejectValue("checkCashedDate",
					"requestForCheckCashedDate.required");
			errors.rejectValue("checkIssueDate",
					"requestForCheckIssueDate.required");
		}

		return errors;
	}

}
