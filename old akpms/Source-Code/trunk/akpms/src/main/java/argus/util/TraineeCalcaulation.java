package argus.util;

import java.text.DecimalFormat;


import org.apache.log4j.Logger;

import argus.domain.AdminSettings;

public final class TraineeCalcaulation {

	private static final Logger LOGGER = Logger
			.getLogger(TraineeCalcaulation.class);

	private TraineeCalcaulation(){

	}

	private static final int HUNDRED = 100;

	private static final String DECIMAL_FORMAT = "#.##";

	public static float calculateTraineePercent(float idsArgusPercent,
			float argusPercent, float totalProcessManualRead,
			Long totalProcessManuals, AdminSettings adminSettings) {
		LOGGER.info("in calculateTraineePercent");

		float processManualReadAdminPercent = calculateProcessManualReadStatusPercent(
				adminSettings, totalProcessManualRead, totalProcessManuals);

		float idsArgusAdminPercent = calculateArgusPercent(adminSettings,
				argusPercent);

		float argusAdminPercent = calculateIdsArgusPercen(adminSettings,
				idsArgusPercent);

		float avgTotalPercent = (processManualReadAdminPercent
				+ idsArgusAdminPercent + argusAdminPercent);

		DecimalFormat df = new DecimalFormat(DECIMAL_FORMAT);

		LOGGER.info("out calculateTraineePercent ::"
				+ Float.valueOf(df.format(avgTotalPercent)));
		return Float.valueOf(df.format(avgTotalPercent));
	}

	public static float calculateProcessManualReadStatusPercent(
			AdminSettings adminSettings, float totalProcessManualRead,
			Long totalProcessManuals) {
		LOGGER.info("in calculateProcessManualReadStatusPercent");

		float proceesManaulReadStatusRatings = (float) Float
				.parseFloat(adminSettings.getProceesManaulReadStatusRatings());

		LOGGER.info("totalProcessManualRead:: " + totalProcessManualRead);
		LOGGER.info("totalProcessManuals:: " + totalProcessManuals);

		float processManualReadPercent = 0.0f;
		try {
			if(totalProcessManuals  > 0){
				processManualReadPercent = (totalProcessManualRead / totalProcessManuals
						.floatValue()) * HUNDRED;
			}
		} catch (Exception e) {
			LOGGER.info("ERROR ::"+e.toString());
			processManualReadPercent = 0.0f;
		}

		LOGGER.info("processManualReadPercent:: " + processManualReadPercent);

		float processManualReadAdminPercent = 0.0f;
		if (processManualReadPercent != 0.0) {
			processManualReadAdminPercent = (processManualReadPercent * proceesManaulReadStatusRatings) / HUNDRED;
		}

		DecimalFormat df = new DecimalFormat(DECIMAL_FORMAT);

		LOGGER.info("out calculateProcessManualReadStatusPercent ::"
				+ Float.valueOf(df.format(processManualReadAdminPercent)));
		return Float.valueOf(df.format(processManualReadAdminPercent));
	}

	public static float calculateArgusPercent(AdminSettings adminSettings,
			float argusPercent) {
		LOGGER.info("in calculateArgusPercent");

		float argusRatings = (float) Float.parseFloat(adminSettings
				.getArgusRatings());
		float argusAdminPercent = 0.0f;
		if (argusPercent != 0.0) {
			argusAdminPercent = (argusPercent * argusRatings) / HUNDRED;
		}
		DecimalFormat df = new DecimalFormat(DECIMAL_FORMAT);

		LOGGER.info("out calculateArgusPercent :: "
				+ Float.valueOf(df.format(argusAdminPercent)));
		return Float.valueOf(df.format(argusAdminPercent));

	}

	public static float calculateIdsArgusPercen(AdminSettings adminSettings,
			float idsArgusPercent) {
		LOGGER.info("in calculateIdsArgusPercen");

		float idsArgusRatings = (float) Float.parseFloat(adminSettings
				.getIdsArgusRatings());
		float idsArgusAdminPercent = 0.0f;
		if (idsArgusPercent != 0.0) {
			idsArgusAdminPercent = (idsArgusPercent * idsArgusRatings) / HUNDRED;
		}
		DecimalFormat df = new DecimalFormat(DECIMAL_FORMAT);

		LOGGER.info("out calculateIdsArgusPercen :: "
				+ Float.valueOf(df.format(idsArgusAdminPercent)));
		return Float.valueOf(df.format(idsArgusAdminPercent));
	}
}
