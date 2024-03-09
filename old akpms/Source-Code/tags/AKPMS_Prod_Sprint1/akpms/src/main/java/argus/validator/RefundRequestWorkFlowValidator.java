package argus.validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import argus.domain.RefundRequestWorkFlow;

@Service
public class RefundRequestWorkFlowValidator extends LocalValidatorFactoryBean
		implements Validator {

	private static final Log LOGGER = LogFactory
			.getLog(RefundRequestWorkFlowValidator.class);

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
	}

}
