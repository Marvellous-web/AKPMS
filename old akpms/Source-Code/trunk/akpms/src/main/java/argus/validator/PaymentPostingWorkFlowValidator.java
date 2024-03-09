package argus.validator;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import argus.domain.PaymentPostingWorkFlow;

@Service
public class PaymentPostingWorkFlowValidator extends LocalValidatorFactoryBean
		implements Validator {

	private static final Logger LOGGER = Logger
			.getLogger(PaymentPostingWorkFlowValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return PaymentPostingWorkFlow.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {

		PaymentPostingWorkFlow paymentPostingWorkFlow = (PaymentPostingWorkFlow) target;
		LOGGER.info("In Payment Posting Workflow validate method");

		if(paymentPostingWorkFlow.isOffSet()) {
			if (paymentPostingWorkFlow.getEob() == null) {
				errors.rejectValue("eob.attachedFile",
						"paymentPosting.eob.required");
			}
		}

		else {
			if (paymentPostingWorkFlow.isEobAvailable()) {

				if (paymentPostingWorkFlow.getEob() == null) {
					errors.rejectValue("eob.attachedFile",
							"paymentPosting.eob.required");
				}

				if (paymentPostingWorkFlow.getCopyCancelCheck() == null) {
					errors.rejectValue("copyCancelCheck.attachedFile",
							"paymentPosting.copyCancelCheck.required");
				}

			}

			else {

				if (paymentPostingWorkFlow.getCpt() == null
						|| paymentPostingWorkFlow.getCpt().trim().equals("")) {
					errors.rejectValue("cpt", "paymentPosting.cpt.required");
				}

				if (paymentPostingWorkFlow.getBilledAmount() == null) {
					errors.rejectValue("billedAmount",
							"paymentPosting.billedAmount.required");
				}

				if (paymentPostingWorkFlow.getPrimaryAmount() == null) {
					errors.rejectValue("primaryAmount",
							"paymentPosting.primaryAmount.required");
				}

				if (paymentPostingWorkFlow.getSecondaryAmount() == null) {
					errors.rejectValue("secondaryAmount",
							"paymentPosting.secondaryAmount.required");
				}

				if (paymentPostingWorkFlow.getContractualAdj() == null) {
					errors.rejectValue("contractualAdj",
							"paymentPosting.contractualAdj.required");
				}

				/*
				 * if (paymentPostingWorkFlow.getPatientResponse() == null) {
				 * errors.rejectValue("patientResponse",
				 * "paymentPosting.patientResponse.required"); }
				 */

				if (paymentPostingWorkFlow.getCheckIssueDate() == null) {
					errors.rejectValue("checkIssueDate",
							"paymentPosting.checkIssueDate.required");
				}

				/*
				 * if (paymentPostingWorkFlow.getCheckCashedDate() == null) {
				 * errors.rejectValue("checkCashedDate",
				 * "paymentPosting.checkCashedDate.required"); }
				 */

			}

			if (paymentPostingWorkFlow.getCheckNo() == null
					|| paymentPostingWorkFlow.getCheckNo().trim().equals("")) {
				errors.rejectValue("checkNo", "paymentPosting.checkNo.required");
			}
			/*
			 * if (paymentPostingWorkFlow.getBulkPaymentAmount() == null) {
			 * errors.rejectValue("bulkPaymentAmount",
			 * "paymentPosting.bulkPaymentAmount.required"); }
			 */

			if (paymentPostingWorkFlow.getAddressCheckSend() == null
					|| paymentPostingWorkFlow.getAddressCheckSend().trim()
							.equals("")) {
				errors.rejectValue("addressCheckSend",
						"paymentPosting.addressCheckSend.required");
			}
		}

		getInsuranceValidate(paymentPostingWorkFlow, errors);
		getDoctorValidate(paymentPostingWorkFlow, errors);
	}

	/**
	 *
	 * @param arProductivity
	 * @param errors
	 * @return
	 */
	private static Errors getDoctorValidate(
			PaymentPostingWorkFlow paymentPostingWorkFlow, Errors errors) {
		LOGGER.debug("in getDoctorValidate");

		if (paymentPostingWorkFlow.getDoctor() == null
				|| paymentPostingWorkFlow.getDoctor().getId() == null) {
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
			PaymentPostingWorkFlow paymentPostingWorkFlow, Errors errors) {
		LOGGER.debug("in getInsuranceValidate");

		if (paymentPostingWorkFlow.getInsurance() == null
				|| paymentPostingWorkFlow.getInsurance().getId() == null) {
			errors.rejectValue("insurance.id",
					"arProductivityInsurance.required");
		}

		LOGGER.debug("out getInsuranceValidate");
		return errors;
	}

}
