package argus.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

	private static final Log LOGGER = LogFactory
			.getLog(ArProductivityValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return ArProductivity.class.isAssignableFrom(clazz);
	}

	/**
	 * @param Object target
	 * @param rrors errors
	 */
	@Override
	public void validate(Object target, Errors errors) {
		LOGGER.info("[validation] ");
		ArProductivity arProductivity = (ArProductivity) target;
		Pattern patientAccNoPattern = Pattern
				.compile("^[a-zA-Z0-9_][\\sa-zA-Z0-9_]*");

		getPatientValidate(arProductivity, errors, patientAccNoPattern);

		getInsuranceValidate(arProductivity, errors);

		if (arProductivity.getDos() == null
				|| arProductivity.getDos().equals("")) {
			errors.rejectValue("dos", "arProductivityDos.required");
		}
		if (arProductivity.getCpt() == null
				|| arProductivity.getCpt().trim().equals("")) {
			errors.rejectValue("cpt", "arProductivityCpt.required");
		}
		getDoctorValidate(arProductivity, errors);
		getValidated(arProductivity, errors);
	}

	/**
	 *
	 * @param arProductivity
	 * @param errors
	 * @return
	 */
	private static Errors getValidated(ArProductivity arProductivity,
			Errors errors){
		if (arProductivity.getDataBas() == null
				|| arProductivity.getDataBas().equals("")) {
			errors.rejectValue("dataBas", "arProductivityDatabase.required");
		}
		if (arProductivity.getBalanceAmt() == null) {
			errors.rejectValue("balanceAmt",
					"arProductivityBalanceAmt.required");
		}
		if (arProductivity.getRemark() == null
				|| arProductivity.getRemark().trim().equals("")) {
			errors.rejectValue("remark", "arProductivityRemark.required");
		}

		if (arProductivity.getSource() == 0) {
			errors.rejectValue("source", "arProductivitySource.required");
		}

		if (arProductivity.getWorkFlow() == 0) {
			errors.rejectValue("workFlow", "arProductivityWorkFlow.required");
		}

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
		if (arProductivity.getInsurance() == null
				|| arProductivity.getInsurance().getId() == 0) {
			errors.rejectValue("doctor.id", "arProductivityDoctor.required");
		}
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
		if (arProductivity.getInsurance() == null
				|| arProductivity.getInsurance().getId() == 0) {
			errors.rejectValue("insurance.id",
					"arProductivityInsurance.required");
		}
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
		return errors;

	}

}
