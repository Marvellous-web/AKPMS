package argus.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import argus.domain.ArProductivity;

/**
 * 
 * @author vishal.joshi
 * 
 */
@Service
public class ArProductivityValidator extends LocalValidatorFactoryBean
		implements Validator {

	private static final Logger LOGGER = Logger
			.getLogger(ArProductivityValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return ArProductivity.class.isAssignableFrom(clazz);
	}

	/**
	 * @param Object
	 *            target
	 * @param rrors
	 *            errors
	 */
	@Override
	public void validate(Object target, Errors errors) {
		LOGGER.info("in [validation] ");

		ArProductivity arProductivity = (ArProductivity) target;
		Pattern patientAccNoPattern = Pattern
				.compile("^[a-zA-Z0-9_][\\sa-zA-Z0-9_]*");

		/*
		 * if (arProductivity.getDos() == null ||
		 * arProductivity.getDos().equals("")) { errors.rejectValue("dos",
		 * "arProductivityDos.required"); }
		 */
		if (arProductivity.getCpt() == null
				|| arProductivity.getCpt().trim().equals("")) {
			errors.rejectValue("cpt", "arProductivityCpt.required");
		}

		getPatientValidate(arProductivity, errors, patientAccNoPattern);
		getInsuranceValidate(arProductivity, errors);
		getDoctorValidate(arProductivity, errors);
		getValidated(arProductivity, errors);

		LOGGER.info("out [validation] ");
	}

	/**
	 * 
	 * @param arProductivity
	 * @param errors
	 * @return
	 */
	private static Errors getValidated(ArProductivity arProductivity,
			Errors errors) {
		LOGGER.debug("in getValidated");

		// if (arProductivity.getDataBas() == null
		// || arProductivity.getDataBas().equals("")) {
		// errors.rejectValue("dataBas", "arProductivityDatabase.required");
		// }
		if (arProductivity.getArDatabase() == null
				|| arProductivity.getArDatabase().getId() == null) {
			errors.rejectValue("arDatabase.id",
					"arProductivityDatabase.required");
		}

		if (arProductivity.getBalanceAmt() == null
				|| arProductivity.getBalanceAmt() < 0) {
			errors.rejectValue("balanceAmt",
					"arProductivityBalanceAmt.required");
		}

		// if workflow selected then remarks/notes is required

		if (null != arProductivity.getWorkFlows()
				&& arProductivity.getWorkFlows().size() > 0) {
			if (arProductivity.getRemark() == null
					|| arProductivity.getRemark().trim().equals("")) {
				errors.rejectValue("remark", "arProductivityRemark.required");
			}
		}
		// if (arProductivity.getWorkFlow() != 0) {
		// if (arProductivity.getRemark() == null
		// || arProductivity.getRemark().trim().equals("")) {
		// errors.rejectValue("remark", "arProductivityRemark.required");
		// }
		// }

		if (arProductivity.getSource() == "") {
			errors.rejectValue("source", "arProductivitySource.required");
		}
		
		if (arProductivity.getStatusCode() == "") {
			errors.rejectValue("statusCode", "arProductivityStatusCode.required");
		}

		/*
		 * if (arProductivity.getWorkFlow() == 0) {
		 * errors.rejectValue("workFlow", "arProductivityWorkFlow.required"); }
		 */

		LOGGER.debug("out getValidated");
		return errors;
	}

	/**
	 * 
	 * @param arProductivity
	 * @param errors
	 * @return
	 */
	private static Errors getDoctorValidate(ArProductivity arProductivity,
			Errors errors) {
		LOGGER.debug("in getDoctorValidate");

		if (arProductivity.getDoctor() == null
				|| arProductivity.getDoctor().getId() == null) {
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
	private static Errors getInsuranceValidate(ArProductivity arProductivity,
			Errors errors) {
		LOGGER.debug("in getInsuranceValidate");

		if (arProductivity.getInsurance() == null
				|| arProductivity.getInsurance().getId() == null) {
			errors.rejectValue("insurance.id",
					"arProductivityInsurance.required");
		}

		LOGGER.debug("out getInsuranceValidate");
		return errors;
	}

	/**
	 * 
	 * @param arProductivity
	 * @param errors
	 * @param patientAccNoPattern
	 * @return
	 */
	private static Errors getPatientValidate(ArProductivity arProductivity,
			Errors errors, Pattern patientAccNoPattern) {
		LOGGER.debug("in getPatientValidate");
		Matcher patientAccNoMatcher = patientAccNoPattern
				.matcher(arProductivity.getPatientAccNo());

		if (arProductivity.getPatientAccNo() == null
				|| arProductivity.getPatientAccNo().trim().equals("")) {
			errors.rejectValue("patientAccNo",
					"arProductivityPatientAccNo.required");
		}
		if ((arProductivity.getPatientAccNo().trim().length() != 0)
				&& (!patientAccNoMatcher.matches())) {
			errors.rejectValue("patientAccNo",
					"arProductivityPatientAccNo.characters");
		}
		if (arProductivity.getPatientName() == null
				|| arProductivity.getPatientName().trim().equals("")) {
			errors.rejectValue("patientName",
					"arProductivityPatientName.required");
		}
		if ((arProductivity.getPatientName().trim().length() != 0)
				&& (!patientAccNoMatcher.matches())) {
			errors.rejectValue("patientName",
					"arProductivityPatientName.characters");
		}

		LOGGER.debug("out getPatientValidate");
		return errors;
	}

}
