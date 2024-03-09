package argus.validator;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import argus.domain.RefundRequestWorkFlow;

@Service
public class RefundRequestWorkFlowValidator extends LocalValidatorFactoryBean
		implements Validator {

	private static final Logger LOGGER = Logger
			.getLogger(RefundRequestWorkFlowValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return RefundRequestWorkFlow.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		LOGGER.info("[RefundRequestWorkFlowValidator] In Validate Method");
		RefundRequestWorkFlow refundRequestWorkFlow = (RefundRequestWorkFlow) target;

		if (refundRequestWorkFlow.getResponsibleParty() == null
				|| refundRequestWorkFlow.getResponsibleParty().trim()
						.equals("")) {
			errors.rejectValue("responsibleParty", "responsibleParty.required");
		}

		if (refundRequestWorkFlow.getCpt() == null
				|| refundRequestWorkFlow.getCpt().trim().equals("")) {
			errors.rejectValue("cpt", "arProductivityCpt.required");
		}

		getInsuranceValidate(refundRequestWorkFlow, errors);
		getDoctorValidate(refundRequestWorkFlow, errors);
	}

	/**
	 * 
	 * @param arProductivity
	 * @param errors
	 * @return
	 */
	private static Errors getDoctorValidate(
			RefundRequestWorkFlow refundRequestWorkFlow, Errors errors) {
		LOGGER.debug("in getDoctorValidate");

		if (refundRequestWorkFlow.getDoctor() == null
				|| refundRequestWorkFlow.getDoctor().getId() == null) {
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
			RefundRequestWorkFlow refundRequestWorkFlow, Errors errors) {
		LOGGER.debug("in getInsuranceValidate");

		if (refundRequestWorkFlow.getInsurance() == null
				|| refundRequestWorkFlow.getInsurance().getId() == null) {
			errors.rejectValue("insurance.id",
					"arProductivityInsurance.required");
		}

		LOGGER.debug("out getInsuranceValidate");
		return errors;
	}

}
