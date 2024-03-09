/**
 *
 */
package argus.mvc.productivity.workflow.helper;


import org.apache.log4j.Logger;

import argus.exception.ArgusException;

/**
 * @author vishal.joshi
 *
 */
public final class WorkFlowHelper {
	private static final Logger LOGGER = Logger
			.getLogger(WorkFlowHelper.class);

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
