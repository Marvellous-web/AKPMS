package argus.validator;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import argus.domain.CodingCorrectionLogWorkFlow;

@Service
public class CodingCorrectionLogWorkFlowValidator extends
		LocalValidatorFactoryBean implements Validator {

	private static final Logger LOGGER = Logger
			.getLogger(CodingCorrectionLogWorkFlowValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return CodingCorrectionLogWorkFlow.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {

		CodingCorrectionLogWorkFlow codingCorrectionLogWorkFlow = (CodingCorrectionLogWorkFlow) target;
		LOGGER.info("In Coding Correction Workflow validate method");
		if (codingCorrectionLogWorkFlow.getId() == null) {
			/*
			 * if (codingCorrectionLogWorkFlow.getBatchNo() == null ||
			 * codingCorrectionLogWorkFlow.getBatchNo().trim() .equals("")) {
			 * errors.rejectValue("batchNo", "batchNo.required"); } if
			 * (codingCorrectionLogWorkFlow.getSequenceNo() == null ||
			 * codingCorrectionLogWorkFlow.getSequenceNo().trim() .equals("")) {
			 * errors.rejectValue("sequenceNo", "sequenceNo.required"); } if
			 * (codingCorrectionLogWorkFlow.getAttachment().getAttachedFile()
			 * .getSize() == 0) { errors.rejectValue("attachment.attachedFile",
			 * "codingCorrectionLogAttachment.required"); }
			 */
		}
		if (codingCorrectionLogWorkFlow.getNextAction() == null
				|| codingCorrectionLogWorkFlow.getNextAction().trim().length() == 0) {
			errors.rejectValue("nextAction", "nextAction.required");
		}
		/*
		 * else if (codingCorrectionLogWorkFlow.getId() != null) { if
		 * (codingCorrectionLogWorkFlow.getCodingRemark() == null ||
		 * codingCorrectionLogWorkFlow.getCodingRemark().trim() .equals("")) {
		 * errors.rejectValue("codingRemark", "codingRemark.required"); } }
		 */

		if (codingCorrectionLogWorkFlow.getCpt() == null
				|| codingCorrectionLogWorkFlow.getCpt().trim().equals("")) {
			errors.rejectValue("cpt", "arProductivityCpt.required");
		}

		getInsuranceValidate(codingCorrectionLogWorkFlow, errors);
		getDoctorValidate(codingCorrectionLogWorkFlow, errors);
	}

	/**
	 * 
	 * @param arProductivity
	 * @param errors
	 * @return
	 */
	private static Errors getDoctorValidate(
			CodingCorrectionLogWorkFlow codingCorrectionLogWorkFlow,
			Errors errors) {
		LOGGER.debug("in getDoctorValidate");

		if (codingCorrectionLogWorkFlow.getDoctor() == null
				|| codingCorrectionLogWorkFlow.getDoctor().getId() == null) {
			errors.rejectValue("doctor.id", "arProductivityDoctor.required");
		}

		LOGGER.debug("out getDoctorValidate");
		return errors;
	}

	/**
	 * 
	 * @param arProductivity
	 * @param errors
	 * @return
	 */
	private static Errors getInsuranceValidate(
			CodingCorrectionLogWorkFlow codingCorrectionLogWorkFlow,
			Errors errors) {
		LOGGER.debug("in getInsuranceValidate");

		if (codingCorrectionLogWorkFlow.getInsurance() == null
				|| codingCorrectionLogWorkFlow.getInsurance().getId() == null) {
			errors.rejectValue("insurance.id",
					"arProductivityInsurance.required");
		}

		LOGGER.debug("out getInsuranceValidate");
		return errors;
	}
}
