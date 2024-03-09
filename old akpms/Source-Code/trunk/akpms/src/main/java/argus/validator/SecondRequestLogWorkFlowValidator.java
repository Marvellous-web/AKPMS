package argus.validator;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import argus.domain.SecondRequestLogWorkFlow;

@Service
public class SecondRequestLogWorkFlowValidator extends
		LocalValidatorFactoryBean implements Validator {

	private static final Logger LOGGER = Logger
			.getLogger(SecondRequestLogWorkFlowValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return SecondRequestLogWorkFlow.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
//		SecondRequestLogWorkFlow secondRequestLogWorkFlow = (SecondRequestLogWorkFlow) target;

		LOGGER.info("In Coding Correction Workflow validate method");

		/*
		 * if (secondRequestLogWorkFlow.getId() == null) { if
		 * (secondRequestLogWorkFlow.getPcp() == null ||
		 * secondRequestLogWorkFlow.getPcp().trim().equals("")) {
		 * errors.rejectValue("pcp", "pcp.required"); } if
		 * (secondRequestLogWorkFlow.getRegionalManager() == null ||
		 * secondRequestLogWorkFlow.getRegionalManager().trim() .equals("")) {
		 * errors.rejectValue("regionalManager", "regionalManager.required"); }
		 * if (secondRequestLogWorkFlow.getInfoNeeded() == null ||
		 * secondRequestLogWorkFlow.getInfoNeeded().trim() .equals("")) {
		 * errors.rejectValue("infoNeeded", "infoNeeded.required"); } if
		 * (secondRequestLogWorkFlow.getStatus() == Constants.ZERO) {
		 * errors.rejectValue(Constants.STATUS, "workFlow.required"); } }
		 *
		 * else if (secondRequestLogWorkFlow.getId() != null) { if
		 * (secondRequestLogWorkFlow.getManagerRemark() == null ||
		 * secondRequestLogWorkFlow.getManagerRemark().trim() .equals("")) {
		 * errors.rejectValue("managerRemark", "managerRemark.required"); }
		 *
		 * if (secondRequestLogWorkFlow.getStatus() == Constants.ZERO ||
		 * secondRequestLogWorkFlow.getStatus() == Constants.THREE) {
		 * errors.rejectValue(Constants.STATUS, "status.required"); } }
		 */

	}

}
