/**
 *
 */
package argus.validator;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import argus.domain.AdjustmentLogWorkFlow;

/**
 * @author vishal.joshi
 *
 */
@Service
public class AdjustmentLogWorkFlowValidator extends LocalValidatorFactoryBean
		implements Validator {

	private static final Logger LOGGER = Logger
			.getLogger(AdjustmentLogWorkFlowValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return AdjustmentLogWorkFlow.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		LOGGER.info("in [validate] method : ");
		AdjustmentLogWorkFlow adjLogWorkFlow = (AdjustmentLogWorkFlow) target;

		if (adjLogWorkFlow.getWorkFlowStatus() == 0) {
			errors.rejectValue("workFlowStatus",
					"arProductivityWorkFlow.required");
		}
		// if (adjLogWorkFlow.getId() != null
		// && (adjLogWorkFlow.getRemark() == null || adjLogWorkFlow
		// .getRemark().trim().length() == 0)) {
		// errors.rejectValue("remark", "arProductivityRemark.required");
		// }

		if (adjLogWorkFlow.getCpt() == null
				|| adjLogWorkFlow.getCpt().trim().equals("")) {
			errors.rejectValue("cpt", "arProductivityCpt.required");
		}

		getInsuranceValidate(adjLogWorkFlow, errors);
		getDoctorValidate(adjLogWorkFlow, errors);

	}

	/**
	 * 
	 * @param arProductivity
	 * @param errors
	 * @return
	 */
	private static Errors getDoctorValidate(
			AdjustmentLogWorkFlow adjLogWorkFlow, Errors errors) {
		LOGGER.debug("in getDoctorValidate");

		if (adjLogWorkFlow.getDoctor() == null
				|| adjLogWorkFlow.getDoctor().getId() == null) {
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
			AdjustmentLogWorkFlow adjLogWorkFlow, Errors errors) {
		LOGGER.debug("in getInsuranceValidate");

		if (adjLogWorkFlow.getInsurance() == null
				|| adjLogWorkFlow.getInsurance().getId() == null) {
			errors.rejectValue("insurance.id",
					"arProductivityInsurance.required");
		}

		LOGGER.debug("out getInsuranceValidate");
		return errors;
	}

}
