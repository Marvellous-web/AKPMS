/**
 *
 */
package argus.mvc.productivity.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.web.context.request.WebRequest;

import argus.exception.ArgusException;
import argus.util.Constants;

/**
 * @author vishal.joshi
 *
 */
public final class ArProductivityHelper {
	private static final Logger LOGGER = Logger
			.getLogger(ArProductivityHelper.class);

	private ArProductivityHelper() {

	}

	/**
	 *
	 * @param request
	 * @return Map<String, String>
	 */
	public static Map<String, String> getOrderClause(WebRequest request) {
		LOGGER.info("in [getOrderClause]");
		Map<String, String> orderClauses = new HashMap<String, String>();
		int page = Constants.ONE;
		int rp = Constants.ZERO;
		if (request.getParameter(Constants.RECORD_PRE_PAGE) != null) {
			rp = Integer.parseInt(request
					.getParameter(Constants.RECORD_PRE_PAGE));
			try {
				orderClauses.put(Constants.LIMIT, "" + rp);
			} catch (Exception e) {
				LOGGER.debug("rp[Record pre Page] not coming or not an integer in request ");
			}
		}

		if (request.getParameter(Constants.PAGE) != null) {
			try {
				page = Integer.parseInt(request.getParameter(Constants.PAGE));
				orderClauses.put(Constants.OFFSET, "" + ((rp * page) - rp));
			} catch (Exception e) {
				LOGGER.debug("Exception during parsing");
			}
		}

		if (request.getParameter(Constants.SORT_ORDER) != null) {
			orderClauses.put(Constants.SORT_BY,
					request.getParameter(Constants.SORT_ORDER));
		}

		if (request.getParameter(Constants.SORT_NAME) != null) {
			orderClauses.put(Constants.ORDER_BY,
					request.getParameter(Constants.SORT_NAME));
		}

		return orderClauses;
	}

	/**
	 *
	 * @param request
	 * @return Map<String, String>
	 */
	public static Map<String, String> getWhereClause(WebRequest request) {
		LOGGER.info("in [getWhereClause]");
		Map<String, String> whereClauses = new HashMap<String, String>();

		if (request.getParameter(Constants.WORKFLOW_ID) != null
				&& request.getParameter(Constants.WORKFLOW_ID) != null
				&& !request.getParameter(Constants.WORKFLOW_ID).isEmpty()) {

			whereClauses.put(Constants.WORKFLOW_ID,
					request.getParameter(Constants.WORKFLOW_ID));

			// substatus will check in case of "query to tl" only
			if (request.getParameter(Constants.WORKFLOW_ID).equalsIgnoreCase(
					Constants.QUERY_TO_TL_WORKFLOW + "")) {
				if (request.getParameter(Constants.SUBSTATUS) != null
						&& request.getParameter(Constants.SUBSTATUS) != null
						&& !request.getParameter(Constants.SUBSTATUS).isEmpty()) {
					whereClauses.put(Constants.SUBSTATUS,
							request.getParameter(Constants.SUBSTATUS));
				}
			}
		}

		if (request.getParameter(Constants.SOURCE) != null
				&& request.getParameter(Constants.SOURCE) != null
				&& !request.getParameter(Constants.SOURCE).isEmpty()) {
			whereClauses.put(Constants.SOURCE,
					request.getParameter(Constants.SOURCE));
		}
		if (request.getParameter(Constants.STATUS_CODE) != null
				&& request.getParameter(Constants.STATUS_CODE) != null
				&& !request.getParameter(Constants.STATUS_CODE).isEmpty()) {
			whereClauses.put(Constants.STATUS_CODE,
					request.getParameter(Constants.STATUS_CODE));
		}
		if (request.getParameter(Constants.DOCTOR_ID) != null
				&& request.getParameter(Constants.DOCTOR_ID) != null
				&& !request.getParameter(Constants.DOCTOR_ID).isEmpty()) {
			whereClauses.put(Constants.DOCTOR_ID,
					request.getParameter(Constants.DOCTOR_ID));
		}
		if (request.getParameter(Constants.INSURANCE_ID) != null
				&& request.getParameter(Constants.INSURANCE_ID) != null
				&& !request.getParameter(Constants.INSURANCE_ID).isEmpty()) {
			whereClauses.put(Constants.INSURANCE_ID,
					request.getParameter(Constants.INSURANCE_ID));
		}

		if (request.getParameter(Constants.TEAM_ID) != null
				&& request.getParameter(Constants.TEAM_ID) != null
				&& !request.getParameter(Constants.TEAM_ID).isEmpty()) {
			whereClauses.put(Constants.TEAM_ID,
					request.getParameter(Constants.TEAM_ID));
		}
		// set created by id
		if (request.getParameter(Constants.CREATED_BY) != null
				&& request.getParameter(Constants.CREATED_BY).trim().length() > Constants.ZERO) {
			LOGGER.info("created by = "
					+ request.getParameter(Constants.CREATED_BY));
			whereClauses.put(Constants.CREATED_BY,
					request.getParameter(Constants.CREATED_BY));
		}

		if (request.getParameter(Constants.DATE_CREATED_FROM) != null
				&& request.getParameter(Constants.DATE_CREATED_FROM).trim()
						.length() > Constants.ZERO) {
			LOGGER.info("DATE_CREATED_FROM= "
					+ request.getParameter(Constants.DATE_CREATED_FROM));
			whereClauses.put(Constants.DATE_CREATED_FROM,
					request.getParameter(Constants.DATE_CREATED_FROM));
		}

		if (request.getParameter(Constants.DATE_CREATED_TO) != null
				&& request.getParameter(Constants.DATE_CREATED_TO).trim()
						.length() > Constants.ZERO) {
			LOGGER.info("DATE_CREATED_TO= "
					+ request.getParameter(Constants.DATE_CREATED_TO));
			whereClauses.put(Constants.DATE_CREATED_TO,
					request.getParameter(Constants.DATE_CREATED_TO));
		}

		if (request.getParameter(Constants.KEYWORD) != null
				&& request.getParameter(Constants.KEYWORD).trim().length() > Constants.ZERO) {
			String keyword = request.getParameter(Constants.KEYWORD);
			if (keyword.contains("%")) {
				return null;
			} else {
				whereClauses.put(Constants.KEYWORD, keyword);
			}
		}



		if (request.getParameter(Constants.FOLLOW_UP_DATE_FROM) != null
				&& request.getParameter(Constants.FOLLOW_UP_DATE_FROM).trim()
						.length() > Constants.ZERO) {
			LOGGER.info("FOLLOW_UP_DATE_FROM= "
					+ request.getParameter(Constants.FOLLOW_UP_DATE_FROM));
			whereClauses.put(Constants.FOLLOW_UP_DATE_FROM,
					request.getParameter(Constants.FOLLOW_UP_DATE_FROM));
		}

		if (request.getParameter(Constants.FOLLOW_UP_DATE_TO) != null
				&& request.getParameter(Constants.FOLLOW_UP_DATE_TO).trim()
						.length() > Constants.ZERO) {
			LOGGER.info("FOLLOW_UP_DATE_TO= "
					+ request.getParameter(Constants.FOLLOW_UP_DATE_TO));
			whereClauses.put(Constants.FOLLOW_UP_DATE_TO,
					request.getParameter(Constants.FOLLOW_UP_DATE_TO));
		}

		if (request.getParameter(Constants.FOLLOUPS) != null
				&& request.getParameter(Constants.FOLLOUPS).trim().length() > Constants.ZERO) {
			LOGGER.info("FOLLOUPS= " + request.getParameter(Constants.FOLLOUPS));
			whereClauses.put(Constants.FOLLOUPS,
					request.getParameter(Constants.FOLLOUPS));
		}

		return whereClauses;
	}

	/**
	 *
	 * @param value
	 * @return
	 * @throws ArgusException
	 */
	public static Date initBinder(String value) throws ArgusException {
		try {
			if (value != null && value.trim().length() > Constants.ZERO) {
				LOGGER.info("comng date value is : " + value);
				return new SimpleDateFormat(Constants.DATE_FORMAT).parse(value);
			} else {
				LOGGER.info("Date value is: " + value);
				return null;
			}
		} catch (ParseException e) {
			LOGGER.error(Constants.EXCEPTION, e);
			throw new ArgusException(e.getMessage(), e);
		}
	}

	/**
	 *
	 * @param workFlowId
	 * @return
	 * @throws ArgusException
	 */
	public static String getFlow(int workFlowId, Long productivityId)
			throws ArgusException {
		LOGGER.info("in [getFlow()] method");
		StringBuilder workFlow = new StringBuilder();

		switch (workFlowId) {
		case Constants.CODING_CORRECTION_WORKFLOW:
			workFlow.append("redirect:/flows/codingcorrectionlogworkflow/add");
			break;
		case Constants.SECOND_REQUEST_LOG_WORKFLOW:
			workFlow.append("redirect:/flows/secondrequestlogworkflow/add");
			break;
		case Constants.FOUR:
			workFlow.append("redirect:/flows/rekeyrequest/add");
			break;
		case Constants.ZERO:
		case Constants.FIVE:
			workFlow.append("redirect:/flows/paymentpostingworkflow/add");
			break;
		case Constants.SEVEN:
		case Constants.ADJUSTMENT_LOG_WORKFLOW:
			workFlow.append("redirect:/flows/arProductivity");
			break;
		case Constants.REQ_FOR_CHECK_TRACER_WORKFLOW:
			workFlow.append("redirect:/flows/checkTracer/add");
			break;
		case Constants.REFUND_REQUEST_WORKFLOW:
			workFlow.append("redirect:/flows/refundrequest/add");
			break;
		case Constants.REQUEST_FOR_DOCS:
			workFlow.append("redirect:/flows/arProductivity");
			break;
		default:
			break;
		}

		if (workFlow.toString() != "" && productivityId > 0) {
			workFlow.append("?arProductivity.id=" + productivityId);
		} else {
			// productivity list page
			workFlow.append("redirect:/flows/arProductivity");
		}

		LOGGER.info("out [getFlow()] method :: " + workFlow.toString());

		return workFlow.toString();
	}
}
