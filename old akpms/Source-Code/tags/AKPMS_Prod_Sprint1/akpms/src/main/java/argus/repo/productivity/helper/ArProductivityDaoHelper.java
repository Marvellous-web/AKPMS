/**
 *
 */
package argus.repo.productivity.helper;

import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import argus.util.AkpmsUtil;
import argus.util.Constants;

/**
 * @author vishal.joshi
 *
 */
public final class ArProductivityDaoHelper {

	private static final Log LOGGER = LogFactory
			.getLog(ArProductivityDaoHelper.class);

	private ArProductivityDaoHelper() {

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
			queryString.append(" WHERE d.deleted = 0 ");
			Set<String> key = whereClauses.keySet();
			for (String field : key) {
				if (field.equalsIgnoreCase(Constants.KEYWORD)) {
					LOGGER.info("found search text value  = "
							+ whereClauses.get(field));
					queryString.append(" AND d.patientName LIKE '%"
							+ whereClauses.get(Constants.KEYWORD) + "%' ");
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
					queryString.append(" AND d.workFlow = "
							+ Constants.QUERY_TO_TL_WORKFLOW);
				} else {
					queryString.append(" AND d." + field);
					queryString.append(" LIKE '%" + whereClauses.get(field)
							+ "%' ");
				}
			}
		}
		if (orderClauses != null) {
			LOGGER.info("in [getQueryFindAll] orderby is not null");
			if (orderClauses.get(Constants.ORDER_BY) != null) {
				queryString.append(" ORDER BY d."
						+ orderClauses.get(Constants.ORDER_BY));
			} else {
				queryString.append(" ORDER BY d.arProductivity.patientName");
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
	public static String getSourceName(int source) {
		LOGGER.info("int [getSourceName()] method");
		String sourceName = "";
		switch (source) {
		case Constants.PAYER_EDIT_SOURCE:
			sourceName = Constants.SOURCE_PAYER_EDIT;
			break;
		case Constants.UNPAID_INACTIVE_SOURCE:
			sourceName = Constants.SOURCE_UNPAID_INACTIVE;
			break;
		case Constants.CORRESPONDENCE_SOURCE:
			sourceName = Constants.SOURCE_CORRESPONDENCE;
			break;
		case Constants.DIVIDER_SET_TO_DENY_SOURCE:
			sourceName = Constants.SOURCE_DIVIDER_SET_TO_DENY;
			break;
		case Constants.IPA_FFS_SOURCE:
			sourceName = Constants.SOURCE_IPA_FFS;
			break;
		case Constants.MISSING_INFO_SOURCE:
			sourceName = Constants.SOURCE_MISSING_INFO;
			break;
		case Constants.HOURLY_SOURCE:
			sourceName = Constants.SOURCE_HOURLY;
			break;

		default:
			break;
		}

		return sourceName;
	}
}
