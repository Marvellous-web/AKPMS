package argus.validator;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import argus.domain.ChargeBatchProcessing;
import argus.util.Constants;

@Service
public class ChargeBatchProcessingValidator extends LocalValidatorFactoryBean
		implements Validator {

	private static final Logger LOGGER = Logger
			.getLogger(ChargeBatchProcessingValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return ChargeBatchProcessing.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		LOGGER.info("in [validate] method");
		ChargeBatchProcessing chargeBatchProcessing = (ChargeBatchProcessing) target;
		try {
			if (chargeBatchProcessing.getId() == null
					|| chargeBatchProcessing.isEditDoctorSection()) {
				if (chargeBatchProcessing.getType() == null
						|| chargeBatchProcessing.getType().trim().equals("")) {
					errors.rejectValue("type",
							"chargeBatchProcessing.type.required");
				}

				if (chargeBatchProcessing.getDoctor() == null
						|| chargeBatchProcessing.getDoctor().getId() == null) {
					errors.rejectValue("doctor",
							"chargeBatchProcessing.doctor.required");
				}

				if (chargeBatchProcessing.getDosFrom() == null) {
					errors.rejectValue("dosFrom",
							"chargeBatchProcessing.dosfrom.required");
				}

				if (chargeBatchProcessing.getDosTo() == null) {
					errors.rejectValue("dosTo",
							"chargeBatchProcessing.dosto.required");
				}
				if ((chargeBatchProcessing.getDosTo() != null && chargeBatchProcessing
						.getDosFrom() != null)
						&& chargeBatchProcessing.getDosTo().before(
								chargeBatchProcessing.getDosFrom())) {
					errors.rejectValue("dosFrom",
							"chargeBatchProcessing.dosfrom.mustBeBefore");
				}

				if (chargeBatchProcessing.getNumberOfSuperbills() == null) {
					errors.rejectValue("numberOfSuperbills",
							"chargeBatchProcessing.numberOfSuperbills.required");
				}

				if (chargeBatchProcessing.getNumberOfAttachments() == null) {
					errors.rejectValue("numberOfAttachments",
							"chargeBatchProcessing.numberOfAttachments.required");
				}

			} else if (!chargeBatchProcessing.isEditDoctorSection()) {
				if (chargeBatchProcessing.getReceivedBy() == null
						|| chargeBatchProcessing.getReceivedBy().getId() == null) {
					errors.rejectValue("receivedBy",
							"chargeBatchProcessing.receivedBy.required");
				}

				if (chargeBatchProcessing.getDateReceived() == null) {
					errors.rejectValue("dateReceived",
							"chargeBatchProcessing.dateReceived.required");
				}

				if (chargeBatchProcessing.getNumberOfSuperbillsArgus() == null) {
					errors.rejectValue("numberOfSuperbillsArgus",
							"chargeBatchProcessing.numberOfSuperbillsArgus.required");
				}

				if (chargeBatchProcessing.getNumberOfAttachmentsArgus() == null) {
					errors.rejectValue("numberOfAttachmentsArgus",
							"chargeBatchProcessing.numberOfAttachmentsArgus.required");
				}

				if (chargeBatchProcessing.getPostedBy() != null) {
					LOGGER.info("Posted By not null");
					if (chargeBatchProcessing.getPostedBy() == null
							|| chargeBatchProcessing.getPostedBy().getId() == null) {
						errors.rejectValue("postedBy",
								"chargeBatchProcessing.postedBy.required");
					}
					if (chargeBatchProcessing.getDateBatchPosted() == null) {
						errors.rejectValue("dateBatchPosted",
								"chargeBatchProcessing.postedOn.required");
					}
				}

			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}

		LOGGER.info("out [validate] method");
	}
}
