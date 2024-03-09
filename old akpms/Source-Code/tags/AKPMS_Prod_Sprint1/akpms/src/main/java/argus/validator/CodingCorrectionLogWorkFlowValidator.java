package argus.validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import argus.domain.CodingCorrectionLogWorkFlow;

@Service
public class CodingCorrectionLogWorkFlowValidator extends
		LocalValidatorFactoryBean implements Validator {

	private static final Log LOGGER = LogFactory
			.getLog(CodingCorrectionLogWorkFlowValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return CodingCorrectionLogWorkFlow.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {

		CodingCorrectionLogWorkFlow codingCorrectionLogWorkFlow = (CodingCorrectionLogWorkFlow) target;
		LOGGER.info("In Coding Correction Workflow validate method");
		if (codingCorrectionLogWorkFlow.getId() == null) {
			if (codingCorrectionLogWorkFlow.getBatchNo() == null
					|| codingCorrectionLogWorkFlow.getBatchNo().trim()
							.equals("")) {
				errors.rejectValue("batchNo", "batchNo.required");
			}
			if (codingCorrectionLogWorkFlow.getSequenceNo() == null
					|| codingCorrectionLogWorkFlow.getSequenceNo().trim()
							.equals("")) {
				errors.rejectValue("sequenceNo", "sequenceNo.required");
			}
			if (codingCorrectionLogWorkFlow.getAttachment().getAttachedFile()
					.getSize() == 0) {

				errors.rejectValue("attachment.attachedFile",
						"codingCorrectionLogAttachment.required");

			}
		}

		else if (codingCorrectionLogWorkFlow.getId() != null) {
			if (codingCorrectionLogWorkFlow.getCodingRemark() == null
					|| codingCorrectionLogWorkFlow.getCodingRemark().trim()
							.equals("")) {
				errors.rejectValue("codingRemark", "codingRemark.required");
			}
		}

	}

}
