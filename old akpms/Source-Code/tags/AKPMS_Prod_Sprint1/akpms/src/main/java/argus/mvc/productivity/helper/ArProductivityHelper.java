/**
 *
 */
package argus.mvc.productivity.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.WebRequest;

import argus.exception.ArgusException;
import argus.util.Constants;

/**
 * @author vishal.joshi
 *
 */
public final class ArProductivityHelper {
	private static final Log LOGGER = LogFactory
			.getLog(ArProductivityHelper.class);

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

		if (request.getParameter(Constants.QTYPE) != null
				&& request.getParameter(Constants.QUERY) != null
				&& !request.getParameter(Constants.QUERY).isEmpty()) {
			whereClauses.put(request.getParameter(Constants.QTYPE),
					request.getParameter(Constants.QUERY));
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
	public static String getFlow(int workFlowId) throws ArgusException {
		LOGGER.info("int [getFlow()] method");
		String workFlow = "";
		switch (workFlowId) {
		case Constants.ADJUSTMENT_LOG_WORKFLOW:
			workFlow = "redirect:/flows/adjustmentlogs/add";
			break;
		case Constants.CODING_CORRECTION_WORKFLOW:
			workFlow = "redirect:/flows/codingcorrectionlogworkflow/add";
			break;
		case Constants.SECOND_REQUEST_LOG_WORKFLOW:
			workFlow = "redirect:/flows/secondrequestlogworkflow/add";
			break;
		case Constants.FOUR:
			workFlow = "";
			break;
		case Constants.FIVE:
			workFlow = "";
			break;
		case Constants.REQ_FOR_CHECK_TRACER_WORKFLOW:
			workFlow = "redirect:/flows/checkTracer/add";
			break;
		case Constants.SEVEN:
			workFlow = "redirect:/flows/arProductivity";
			break;
		case Constants.REFUND_REQUEST_WORKFLOW:
			workFlow = "redirect:/flows/refundrequest/add";
			break;
		default:
			break;
		}

		return workFlow;
	}
}
