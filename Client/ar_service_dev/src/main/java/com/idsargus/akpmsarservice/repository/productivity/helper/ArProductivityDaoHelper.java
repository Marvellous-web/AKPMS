/**
 *
 */
package com.idsargus.akpmsarservice.repository.productivity.helper;
;

import com.idsargus.akpmsarservice.util.AkpmsUtil;
import com.idsargus.akpmsarservice.util.Constants;

import java.util.Calendar;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author vishal.joshi
 *
 */
public final class ArProductivityDaoHelper {

	private static final Logger LOGGER = Logger
			.getLogger(String.valueOf(ArProductivityDaoHelper.class));

	private ArProductivityDaoHelper() {

	}

	/**
	 * return where clause
	 *
	 * @param whereClauses
	 * @return
	 */
	public static String getWhereClause(Map<String, String> whereClauses) {
		LOGGER.info("where clause size :" + whereClauses.size());
		StringBuffer queryString = new StringBuffer();
		queryString.append(" WHERE d.deleted =  " + Constants.NON_DELETED);

		if (whereClauses != null && whereClauses.size() > Constants.ZERO) {
			LOGGER.info("in [getQueryFindAll] where is not null");
			Set<String> key = whereClauses.keySet();

			for (String field : key) {
				//LOGGER.debug(key + " :: " + field);
				if (field.equalsIgnoreCase(Constants.KEYWORD)) {
					LOGGER.info("found search text value  = "
							+ whereClauses.get(field));
					queryString.append(" AND ");
					queryString.append(" (d.patientName LIKE '%"
							+ whereClauses.get(Constants.KEYWORD) + "%' ");
					queryString.append(" OR d.patientAccNo LIKE '%"
							+ whereClauses.get(Constants.KEYWORD) + "%' )");
				} else if (field.equalsIgnoreCase("dosFrom")) {
					queryString.append(" AND d.arProductivity.dos >= '"
							+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
									.akpmsNewDateFormat(whereClauses
											.get("dosFrom")),
									Constants.MYSQL_DATE_FORMAT) + "'");
				} else if (field.equalsIgnoreCase("dosTo")) {
					queryString.append(" AND d.arProductivity.dos <= '"
							+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
									.akpmsNewDateFormat(whereClauses
											.get("dosTo")),
									Constants.MYSQL_DATE_FORMAT) + "'");
				} else if (field.equalsIgnoreCase(Constants.WORKFLOW_ID)) {
					queryString.append(" AND workFlow.id = "
							+ whereClauses.get(Constants.WORKFLOW_ID));
				} else if (field.equalsIgnoreCase(Constants.DOCTOR_ID)) {
					queryString.append(" AND d.doctor.id = "
							+ whereClauses.get(Constants.DOCTOR_ID));
				} else if (field.equalsIgnoreCase(Constants.INSURANCE_ID)) {
					queryString.append(" AND d.insurance.id = "
							+ whereClauses.get(Constants.INSURANCE_ID));
				} else if (field.equalsIgnoreCase(Constants.TEAM_ID)) {
					queryString.append(" AND d.team.id = "
							+ whereClauses.get(Constants.TEAM_ID));
				} else if (field.equalsIgnoreCase(Constants.STATUS_CODE)) {
					queryString.append(" AND d.statusCode = '"
							+ whereClauses.get(Constants.STATUS_CODE) + "'");
				} else if (field.equalsIgnoreCase(Constants.SUBSTATUS)) {
					queryString.append(" AND d.subStatus = "
							+ whereClauses.get(Constants.SUBSTATUS));
				} else if (field.equalsIgnoreCase(Constants.SOURCE)) {
					queryString.append(" AND d.source = '"
							+ whereClauses.get(Constants.SOURCE) + "'");
				} else if (field.equalsIgnoreCase(Constants.CREATED_BY)) {
					LOGGER.info("found CREATED_BY = "
							+ whereClauses.get(Constants.CREATED_BY));
					queryString.append(" AND d.createdBy.id = "
							+ whereClauses.get(Constants.CREATED_BY));
				} // date created from
				else if (field.equalsIgnoreCase(Constants.DATE_CREATED_FROM)) {
					LOGGER.info("found DATE_CREATED_FROM = "
							+ whereClauses.get(Constants.DATE_CREATED_FROM));
					queryString.append(" AND d.createdOn >= '"
							+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
									.akpmsNewDateFormat(whereClauses
											.get(Constants.DATE_CREATED_FROM)),
									Constants.MYSQL_DATE_FORMAT) + "'");
				}

				// date created to
				else if (field.equalsIgnoreCase(Constants.DATE_CREATED_TO)) {
					LOGGER.info("found DATE_CREATED_TO = "
							+ whereClauses.get(Constants.DATE_CREATED_TO));
					queryString.append(" AND d.createdOn <= '"
							+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
									.getFormattedDate(whereClauses
											.get(Constants.DATE_CREATED_TO)),
									Constants.MYSQL_DATE_FORMAT_WITH_TIME)
							+ "'");
				}

				// FOLLOW UP date from
				else if (field.equalsIgnoreCase(Constants.FOLLOW_UP_DATE_FROM)) {
					LOGGER.info("found FOLLOW_UP_DATE_FROM = "
							+ whereClauses.get(Constants.FOLLOW_UP_DATE_FROM));
					queryString
							.append(" AND d.followUpDate >= '"
									+ AkpmsUtil.akpmsDateFormat(
											AkpmsUtil
													.akpmsNewDateFormat(whereClauses
															.get(Constants.FOLLOW_UP_DATE_FROM)),
											Constants.MYSQL_DATE_FORMAT) + "'");
				}

				// date created to
				else if (field.equalsIgnoreCase(Constants.FOLLOW_UP_DATE_TO)) {
					LOGGER.info("found FOLLOW_UP_DATE_TO = "
							+ whereClauses.get(Constants.FOLLOW_UP_DATE_TO));
					queryString.append(" AND d.followUpDate <= '"
							+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
									.getFormattedDate(whereClauses
											.get(Constants.FOLLOW_UP_DATE_TO)),
									Constants.MYSQL_DATE_FORMAT_WITH_TIME)
							+ "'");
				}
				// date created to
				else if (field.equalsIgnoreCase(Constants.FOLLOUPS)) {
					LOGGER.info("found FOLLOUPS = "
							+ whereClauses.get(Constants.FOLLOUPS));
					queryString.append(" AND d.followUpDate IS NOT NULL ");
				}

				// else {
				// queryString.append(" AND d." + field);
				// queryString.append(" LIKE '%" + whereClauses.get(field)
				// + "%' ");
				// }
				// Fetch records on basis of QAWorksheet criteria [START]
				else if (field.equalsIgnoreCase(Constants.SUB_DEPARTMENT_ID)) {
					queryString.append(" AND d.team.id = "
							+ whereClauses.get(Constants.SUB_DEPARTMENT_ID));
				}

				else if (field.equalsIgnoreCase(Constants.MONTH)) {
					queryString.append(" AND MONTH(d.createdOn) = "
							+ whereClauses.get(Constants.MONTH));
				}

				else if (field.equalsIgnoreCase(Constants.YEAR)) {
					queryString.append(" AND YEAR(d.createdOn) = "
							+ whereClauses.get(Constants.YEAR));
				}

				else if (field.equalsIgnoreCase(Constants.QA_WORKSHEET_MONTH)) {
					queryString.append(" AND MONTH(d.createdOn) = "
							+ whereClauses.get(Constants.QA_WORKSHEET_MONTH));
				}

				else if (field.equalsIgnoreCase(Constants.QA_WORKSHEET_YEAR)) {
					queryString.append(" AND YEAR(d.createdOn) = "
							+ whereClauses.get(Constants.QA_WORKSHEET_YEAR));
				}

				else if (field.equalsIgnoreCase(Constants.POSTING_DATE_FROM)) {
					LOGGER.info("found DATE_CREATED_FROM = "
							+ whereClauses.get(Constants.POSTING_DATE_FROM));

					queryString.append(" AND d.createdOn >= '"
							+ AkpmsUtil.akpmsFormatDateSQL(whereClauses
									.get(Constants.POSTING_DATE_FROM)) + "'");
				}

				else if (field.equalsIgnoreCase(Constants.POSTING_DATE_TO)) {
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

					queryString.append(" AND d.createdOn <= '"
							+ AkpmsUtil.akpmsDateFormat(cl.getTime(),
									"yyyy-MM-dd HH:mm:ss") + "'");
				}

				else if (field.equalsIgnoreCase(Constants.USER)) {
					queryString.append(" AND d.createdBy.id in ( "
							+ whereClauses.get(Constants.USER) + ")");
				} else if (field.equalsIgnoreCase(Constants.STATUS_CODE)) {
					queryString.append(" AND d.statusCode = '"
							+ whereClauses.get(Constants.STATUS_CODE) + "'");
				} else if (field.equalsIgnoreCase(Constants.DOCTOR)) {
					queryString.append(" AND d.doctor.id in ( "
							+ whereClauses.get(Constants.DOCTOR) + ")");
				}
				// Fetch records on basis of QAWorksheet criteria [END]
			}
		}
		//LOGGER.debug("queryString :: " + queryString.toString());
		return queryString.toString();
	}

	/**
	 * return order by clause
	 *
	 * @param orderClauses
	 * @return
	 */
	public static String getOrderClause(Map<String, String> orderClauses) {

		StringBuffer queryString = new StringBuffer();
		// dont want to break old code
		// this is block is implemented for productivity sampling where users
		// random records
		// are pulled up.
		if (orderClauses != null
				&& orderClauses.containsKey(Constants.RANDOM_RECOREDS)) {
			LOGGER.info("Found : "
					+ orderClauses.get(Constants.RANDOM_RECOREDS));
			queryString.append(" ORDER BY RAND()");

			// MAKE orderClauses null so next order by not gets executed
			orderClauses = null;
		}

		if (orderClauses != null) {
			LOGGER.info("in [getQueryFindAll] orderby is not null");
			if (orderClauses.get(Constants.ORDER_BY) != null) {
				queryString.append(" ORDER BY d."
						+ orderClauses.get(Constants.ORDER_BY));
			} else {
				queryString.append(" ORDER BY d.createdOn");
			}
			if (orderClauses.get(Constants.SORT_BY) != null) {
				queryString.append(" " + orderClauses.get(Constants.SORT_BY));
			} else {
				queryString.append(" DESC");
			}
		}

		return queryString.toString();
	}

	/**
	 *
	 * @param workFlowId
	 * @return String : work flow name
	 */
	public static String getWorkFlowName(int workFlowId) {
		LOGGER.info("int [getWorkFlowName()] method");
		String workFlow = "";
		switch (workFlowId) {
		case Constants.ADJUSTMENT_LOG_WORKFLOW:
			workFlow = Constants.WORKFLOW_ADJUSTMENT_LOG;
			break;
		case Constants.CODING_CORRECTION_WORKFLOW:
			workFlow = Constants.WORKFLOW_CODING_CORRECTION;
			break;
		case Constants.SECOND_REQUEST_LOG_WORKFLOW:
			workFlow = Constants.WORKFLOW_SECOND_REQ_LOG;
			break;
		case Constants.REKEY_REQ_TO_CHARGE_POSTING_WORKFLOW:
			workFlow = Constants.WORKFLOW_REKEY_REQ_TO_CHARGE_POSTING;
			break;
		case Constants.PAYMENT_POSTING_LOG_WORKFLOW:
			workFlow = Constants.WORKFLOW_PAYMENT_POSTING_LOG;
			break;
		case Constants.REQ_FOR_CHECK_TRACER_WORKFLOW:
			workFlow = Constants.WORKFLOW_REQ_FOR_CHECK_TRACER;
			break;
		case Constants.QUERY_TO_TL_WORKFLOW:
			workFlow = Constants.WORKFLOW_QUERY_TO_TL;
			break;

		case Constants.REFUND_REQUEST_WORKFLOW:
			workFlow = Constants.WORKFLOW_REFUND_REQUEST;
			break;

		case Constants.REQUEST_FOR_DOCS:
			workFlow = Constants.WORKFLOW_REQUEST_FOR_DOCS;
			break;

		default:
			break;
		}

		return workFlow;
	}

	/**
	 *
	 * @param source
	 * @return String : source name
	 */
	/*
	 * public static String getSourceName(int source) {
	 * LOGGER.info("int [getSourceName()] method"); String sourceName = "";
	 * switch (source) { case Constants.PAYER_EDIT_SOURCE: sourceName =
	 * Constants.SOURCE_PAYER_EDIT; break; case
	 * Constants.UNPAID_INACTIVE_SOURCE: sourceName =
	 * Constants.SOURCE_UNPAID_INACTIVE; break; case
	 * Constants.CORRESPONDENCE_SOURCE: sourceName =
	 * Constants.SOURCE_CORRESPONDENCE; break; case
	 * Constants.DIVIDER_SET_TO_DENY_SOURCE: sourceName =
	 * Constants.SOURCE_DIVIDER_SET_TO_DENY; break; case
	 * Constants.IPA_FFS_SOURCE: sourceName = Constants.SOURCE_IPA_FFS; break;
	 * case Constants.MISSING_INFO_SOURCE: sourceName =
	 * Constants.SOURCE_MISSING_INFO; break; case Constants.HOURLY_SOURCE:
	 * sourceName = Constants.SOURCE_HOURLY; break;
	 *
	 * default: break; }
	 *
	 * return sourceName; }
	 */
}
