package argus.validator;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import argus.domain.RekeyRequestRecord;
import argus.domain.RekeyRequestWorkFlow;

@Service
public class RekeyRequestWorkFlowValidator extends LocalValidatorFactoryBean
		implements Validator {

	private static final Logger LOGGER = Logger
			.getLogger(RekeyRequestWorkFlowValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return RekeyRequestWorkFlow.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		RekeyRequestWorkFlow rekeyRequestWorkFlow = (RekeyRequestWorkFlow) target;

		LOGGER.info("In Coding Correction Workflow validate method");

		if (rekeyRequestWorkFlow.getBatchNumber() == null
				|| rekeyRequestWorkFlow.getBatchNumber().trim().equals("")) {
			errors.rejectValue("batchNumber", "rekeyBatchNumber.required");
		}

		if (rekeyRequestWorkFlow.getRequestReason() == null
				|| rekeyRequestWorkFlow.getRequestReason().trim().equals("")||rekeyRequestWorkFlow.getRequestReason().equals("0")) {
			errors.rejectValue("requestReason", "rekeyRequestReason.required");
		}

		if (rekeyRequestWorkFlow.getStatus() == 0) {
			errors.rejectValue("status", "rekeyStatus.required");
		}

		if (rekeyRequestWorkFlow.getDos() == null
				|| rekeyRequestWorkFlow.getDos().trim().equals("")) {
			errors.rejectValue("dos", "rekeyDos.required");
		}

		if (rekeyRequestWorkFlow.getRekeyRequestRecords() != null) {
			if (!rekeyRequestWorkFlow.getRekeyRequestRecords().isEmpty()) {
				for (RekeyRequestRecord rekeyRequestRecord : rekeyRequestWorkFlow
						.getRekeyRequestRecords()) {
					boolean foundError = false;
					if ((rekeyRequestRecord.getCpt() == null || rekeyRequestRecord
							.getCpt().trim().length() == 0)
							|| rekeyRequestRecord.getRemark() == null
							|| rekeyRequestRecord.getRemark().trim().length() == 0) {
						foundError = true;
						if (rekeyRequestWorkFlow.getRekeyRequestRecords().size() == 1) {
							errors.rejectValue("rekeyRequestRecords",
									"rekey.rekeyRecords.single.required");
						} else {
							errors.rejectValue("rekeyRequestRecords",
									"rekey.rekeyRecords.multiple.required");
						}
					}
					if (foundError) {
						break;
					}
				}
			} else {
				errors.rejectValue("rekeyRequestRecords",
						"rekey.rekeyRecords.empty.required");
			}
		}

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

		if (rekeyRequestWorkFlow.getCpt() == null
				|| rekeyRequestWorkFlow.getCpt().trim().equals("")) {
			errors.rejectValue("cpt", "arProductivityCpt.required");
		}

		getInsuranceValidate(rekeyRequestWorkFlow, errors);
		getDoctorValidate(rekeyRequestWorkFlow, errors);

	}

	/**
	 * 
	 * @param arProductivity
	 * @param errors
	 * @return
	 */
	private static Errors getDoctorValidate(
			RekeyRequestWorkFlow rekeyRequestWorkFlow, Errors errors) {
		LOGGER.debug("in getDoctorValidate");

		if (rekeyRequestWorkFlow.getDoctor() == null
				|| rekeyRequestWorkFlow.getDoctor().getId() == null) {
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
			RekeyRequestWorkFlow rekeyRequestWorkFlow, Errors errors) {
		LOGGER.debug("in getInsuranceValidate");

		if (rekeyRequestWorkFlow.getInsurance() == null
				|| rekeyRequestWorkFlow.getInsurance().getId() == null) {
			errors.rejectValue("insurance.id",
					"arProductivityInsurance.required");
		}

		LOGGER.debug("out getInsuranceValidate");
		return errors;
	}

}
