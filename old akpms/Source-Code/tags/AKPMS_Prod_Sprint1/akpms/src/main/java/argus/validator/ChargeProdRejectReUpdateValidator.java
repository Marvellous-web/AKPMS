/**
 *
 */
package argus.validator;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import argus.domain.ChargeProdReject;

/**
 * @author jasdeep.s
 *
 */
@Service
public class ChargeProdRejectReUpdateValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ChargeProdReject.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object obj, Errors error) {
		ChargeProdReject chargeProdReject = (ChargeProdReject) obj;

		boolean firstRequest = true;

		if (!chargeProdReject.isResolved()) {
			if (chargeProdReject.getDateOfFirstRequestToDoctorOffice() == null) {
				error.rejectValue("dateOfFirstRequestToDoctorOffice",
						"chargeProdReject.dateOfFirstRequestToDoctorOffice.required");
				firstRequest = false;
			}
			if (chargeProdReject.getRemarks() == null
					|| chargeProdReject.getRemarks().trim().length() < 1) {
				error.rejectValue("remarks",
						"chargeProdReject.remarks.required");
			}

			if (!firstRequest
					&& chargeProdReject.getDateOfSecondRequestToDoctorOffice() != null) {
				error.rejectValue("dateOfSecondRequestToDoctorOffice",
						"chargeProdReject.dateOfSecondRequestToDoctorOffice.precedence.required");
			}

			if (chargeProdReject.getDateOfFirstRequestToDoctorOffice() != null
					&& chargeProdReject.getDateOfSecondRequestToDoctorOffice() != null) {
				if (chargeProdReject
						.getDateOfFirstRequestToDoctorOffice()
						.compareTo(
								chargeProdReject
										.getDateOfSecondRequestToDoctorOffice()) > 0) {
					error.rejectValue("dateOfSecondRequestToDoctorOffice",
							"chargeProdReject.dateOfSecondRequestToDoctorOffice.afterFirstRequest");
				}
			}
		} else {
			if (chargeProdReject.getRemarks2() == null
					|| chargeProdReject.getRemarks2().trim().length() < 1) {
				error.rejectValue("remarks2",
						"chargeProdReject.remarks.required");
			}
		}

	}

}
