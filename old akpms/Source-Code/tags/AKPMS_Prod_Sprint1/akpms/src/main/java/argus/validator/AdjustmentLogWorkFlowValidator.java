/**
 *
 */
package argus.validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
public class AdjustmentLogWorkFlowValidator extends LocalValidatorFactoryBean implements
		Validator {

	private static final Log LOGGER = LogFactory
			.getLog(AdjustmentLogWorkFlowValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return AdjustmentLogWorkFlow.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		LOGGER.info("in [validate] method : ");
		AdjustmentLogWorkFlow adjLogWorkFlow = (AdjustmentLogWorkFlow) target;

		if(adjLogWorkFlow.getWorkFlowStatus()==0){
			errors.rejectValue("workFlowStatus", "arProductivityWorkFlow.required");
		}
		if (adjLogWorkFlow.getId() != null
				&& (adjLogWorkFlow.getRemark() == null || adjLogWorkFlow
						.getRemark().trim().length() == 0)) {
			errors.rejectValue("remark", "arProductivityRemark.required");
		}

	}

}
