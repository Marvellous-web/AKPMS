package argus.repo.paymentproductivity.helper;

import java.util.Calendar;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import argus.exception.ArgusException;
import argus.util.AkpmsUtil;
import argus.util.Constants;

/**
 * @author rajiv.k
 *
 */
public final class PaymentProductivityDaoHelper {
	private static final Logger LOGGER = Logger
			.getLogger(PaymentProductivityDaoHelper.class);

	private PaymentProductivityDaoHelper() {

	}

	/**
	 *
	 * @param orderClauses
	 * @param whereClauses
	 * @return
	 */
	public static StringBuilder getQueryFindAll(
			Map<String, String> orderClauses, Map<String, String> whereClauses) {
		LOGGER.info("in [getQueryFindAll]");
		StringBuilder queryString = new StringBuilder();
		if (whereClauses != null && whereClauses.size() > Constants.ZERO) {
			LOGGER.info("in [getQueryFindAll] where is not null");
			Set<String> key = whereClauses.keySet();
			for (String field : key) {
				// queryString.append("");
				if (field.equalsIgnoreCase(Constants.PROD_TYPE)
						& whereClauses.get(field) != null
						& whereClauses.get(field).trim().length() > 0) {
					LOGGER.info("found Prod type value  = "
							+ whereClauses.get(field));
					queryString.append(" AND p.paymentProdType = "
							+ whereClauses.get(Constants.PROD_TYPE));

				} else if (field.equalsIgnoreCase(Constants.POSTED_BY_ID)
						& whereClauses.get(field) != null
						& whereClauses.get(field).trim().length() > 0) {
					LOGGER.info("found Posted By value  = "
							+ whereClauses.get(field));
					queryString.append(" AND p.createdBy = "
							+ whereClauses.get(Constants.POSTED_BY_ID));

				}

				// date posted from
				else if (field.equalsIgnoreCase(Constants.DATE_POSTED_FROM)) {
					LOGGER.info("found DATE_POSTED_FROM = "
							+ whereClauses.get(Constants.DATE_POSTED_FROM));

					queryString.append(" AND p.paymentBatch.datePosted >= '"
							+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
									.akpmsNewDateFormat(whereClauses
											.get(Constants.DATE_POSTED_FROM)),
									Constants.MYSQL_DATE_FORMAT) + "'");
				}

				// date posted to
				else if (field.equalsIgnoreCase(Constants.DATE_POSTED_TO)) {
					LOGGER.info("found DATE_POSTED_TO = "
							+ whereClauses.get(Constants.DATE_POSTED_TO));

					queryString.append(" AND p.paymentBatch.datePosted <= '"
							+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
									.getFormattedDate(whereClauses
											.get(Constants.DATE_POSTED_TO)),
									Constants.MYSQL_DATE_FORMAT_WITH_TIME)
							+ "'");
				} else if (field.equalsIgnoreCase(Constants.TICKET_NUMBER)
						& whereClauses.get(field) != null
						& whereClauses.get(field).trim().length() > 0) {
					LOGGER.info("found search text value  = "
							+ whereClauses.get(field));
					queryString.append(" AND p.paymentBatch ="
							+ whereClauses.get(Constants.TICKET_NUMBER));
				} else if (field.equalsIgnoreCase("workflowId")
						& whereClauses.get(field) != null
						& whereClauses.get(field).trim().length() > 0) {
					LOGGER.info("found search text value  = "
							+ whereClauses.get(field));
					queryString.append(" AND p.workFlowId ="
							+ whereClauses.get("workflowId"));
				}

				else if (field.equalsIgnoreCase(Constants.DATE_CREATED_FROM)) {
					LOGGER.info("found DATE_CREATED_FROM = "
							+ whereClauses.get(Constants.DATE_CREATED_FROM));
					queryString.append(" AND p.createdOn >= '"
							+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
									.akpmsNewDateFormat(whereClauses
											.get(Constants.DATE_CREATED_FROM)),
									Constants.MYSQL_DATE_FORMAT) + "'");
				}

				// date created to
				else if (field.equalsIgnoreCase(Constants.DATE_CREATED_TO)) {
					LOGGER.info("found DATE_CREATED_TO = "
							+ whereClauses.get(Constants.DATE_CREATED_TO));
					queryString.append(" AND p.createdOn <= '"
							+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
									.getFormattedDate(whereClauses
											.get(Constants.DATE_CREATED_TO)),
									Constants.MYSQL_DATE_FORMAT_WITH_TIME)
							+ "'");
				} else

				if (field.equalsIgnoreCase(Constants.DOCTOR_ID)) {
					LOGGER.info("found DOCTOR_ID = "
							+ whereClauses.get(Constants.DOCTOR_ID));
					queryString.append(" AND p.paymentBatch.doctor.id = "
							+ whereClauses.get(Constants.DOCTOR_ID));
				} else

				// Fetch records on basis of QAWorksheet criteria [START]
				if (field.equalsIgnoreCase(Constants.QA_WORKSHEET_MONTH)) {
					queryString.append(" AND MONTH(p.createdOn) = "
							+ whereClauses.get(Constants.QA_WORKSHEET_MONTH));
				} else

				if (field.equalsIgnoreCase(Constants.QA_WORKSHEET_YEAR)) {
					queryString.append(" AND YEAR(p.createdOn) = "
							+ whereClauses.get(Constants.QA_WORKSHEET_YEAR));
				} else

				// Fetch records on basis of QAWorksheet criteria [START]
				if (field.equalsIgnoreCase(Constants.MONTH)) {
					queryString.append(" AND MONTH(p.createdOn) = "
							+ whereClauses.get(Constants.MONTH));
				} else

				if (field.equalsIgnoreCase(Constants.YEAR)) {
					queryString.append(" AND YEAR(p.createdOn) = "
							+ whereClauses.get(Constants.YEAR));
				} else if (field.equalsIgnoreCase(Constants.POSTING_DATE_FROM)) {
					LOGGER.info("found DATE_CREATED_FROM = "
							+ whereClauses.get(Constants.POSTING_DATE_FROM));

					queryString.append(" AND p.createdOn >= '"
							+ AkpmsUtil.akpmsFormatDateSQL(whereClauses
									.get(Constants.POSTING_DATE_FROM)) + "'");
				} else

				if (field.equalsIgnoreCase(Constants.POSTING_DATE_TO)) {
					LOGGER.info("found POSTING_DATE_TO = "
							+ whereClauses.get(Constants.POSTING_DATE_TO));

					Calendar cl = Calendar.getInstance();
					cl.setTime(AkpmsUtil.akpmsNewDateFormat(
							whereClauses.get(Constants.POSTING_DATE_TO),
							Constants.TIME_STAMP_FORMAT));
					cl.set(Calendar.HOUR_OF_DAY,
							cl.getActualMaximum(Calendar.HOUR_OF_DAY));
					cl.set(Calendar.MINUTE,
							cl.getActualMaximum(Calendar.MINUTE));
					cl.set(Calendar.SECOND,
							cl.getActualMaximum(Calendar.SECOND));

					queryString.append(" AND p.createdOn <= '"
							+ AkpmsUtil.akpmsDateFormat(cl.getTime(),
									"yyyy-MM-dd HH:mm:ss") + "'");
				} else

				if (field.equalsIgnoreCase(Constants.USER)) {
					queryString.append(" AND p.createdBy.id IN ( "
							+ whereClauses.get(Constants.USER) + ")");
				} else

				if (field.equalsIgnoreCase(Constants.DOCTOR)) {
					queryString.append(" AND p.paymentBatch.doctor.id IN ("
							+ whereClauses.get(Constants.DOCTOR) + ")");
				}

				// Fetch records on basis of QAWorksheet criteria [END]

			}

		}

		// dont want to break old code
		// this is block is implemented for productivity sampling where users
		// random records
		// are pulled up.
		if (orderClauses != null
				&& orderClauses.containsKey(Constants.RANDOM_RECOREDS)) {
			LOGGER.info("Found : "
					+ orderClauses.get(Constants.RANDOM_RECOREDS));
			queryString.append(" ORDER BY RAND()");

			// MAKE orderClauses null so next order by not gets appended
			orderClauses = null;
		}
		if (orderClauses != null) {
			LOGGER.info("in [getQueryFindAll] orderby is not null");
			if (orderClauses.get(Constants.ORDER_BY) != null) {
				queryString.append(" ORDER BY p."
						+ orderClauses.get(Constants.ORDER_BY));
			} else {
				queryString.append(" ORDER BY p.paymentProdType");
			}
			if (orderClauses.get(Constants.SORT_BY) != null) {
				queryString.append(" " + orderClauses.get(Constants.SORT_BY));
			} else {
				queryString.append(" ASC");
			}
		}
		return queryString;
	}

	/**
	 *
	 * @param workFlowId
	 * @return String : work flow name
	 */
	public static String getWorkFlowName(int workFlowId) throws ArgusException {
		LOGGER.info("int [getWorkFlowName()] method : workFlowId ="
				+ workFlowId);
		String workFlowName = "";
		switch (workFlowId) {
		case Constants.TO_AR_IPA_FFS_HMO_WORK_FLOW:
			workFlowName = Constants.WORK_FLOW_TO_AR_IPA_FFS_HMO;
			break;
		case Constants.TO_AR_FFS_WORK_FLOW:
			workFlowName = Constants.WORK_FLOW_TO_AR_FFS;
			break;
		case Constants.TO_AR_CEP_WORK_FLOW:
			workFlowName = Constants.WORK_FLOW_TO_AR_CEP;
			break;
		case Constants.TO_AR_MCL_WORK_FLOW:
			workFlowName = Constants.WORK_FLOW_TO_AR_MCL;
			break;
		case Constants.OFFSET_WORK_FLOW:
			workFlowName = Constants.WORK_FLOW_OFFSET;
			break;
		case Constants.QUERY_TO_TL_WORK_FLOW:
			workFlowName = Constants.WORK_FLOW_QUERY_TO_TL;
			break;
		default:
			break;
		}

		return workFlowName;
	}

	/**
	 *
	 * @param workFlowId
	 * @return String : work flow name
	 */
	public static String getProductivityType(int workFlowId)
			throws ArgusException {
		LOGGER.info("int [getProductivityType()] method : workFlowId ="
				+ workFlowId);
		String workFlowName = "";
		switch (workFlowId) {
		case Constants.ERA_WORK_FLOW:
			workFlowName = Constants.ERA;
			break;
		case Constants.NON_ERA_WORK_FLOW:
			workFlowName = Constants.NON_ERA;
			break;
		case Constants.CAP_WORK_FLOW:
			workFlowName = Constants.CAP;
			break;
		case Constants.REFUND_REQUEST_WORK_FLOW:
			workFlowName = Constants.REFUND_REQUEST;
			break;
		case Constants.HOURLY_WORK_FLOW:
			workFlowName = Constants.HOURLY;
			break;

		default:
			break;
		}

		return workFlowName;
	}

}
