/**
 *
 */
package argus.mvc.productivity.workflow.helper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import argus.exception.ArgusException;

/**
 * @author vishal.joshi
 *
 */
public final class WorkFlowHelper {
	private static final Log LOGGER = LogFactory
			.getLog(WorkFlowHelper.class);

	private WorkFlowHelper() {

	}
	/**
	 *
	 * @param arProductivity
	 * @return String
	 * @throws ArgusException
	 */
	public static String getFlow(int workFlowId)
			throws ArgusException {
		LOGGER.info("int [getFlow()] method");
		return null;
	}
}
