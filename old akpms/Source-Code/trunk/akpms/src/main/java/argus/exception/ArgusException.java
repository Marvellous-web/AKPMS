/**
 *
 */
package argus.exception;

import org.apache.log4j.Logger;

/**
 * @author vishal.joshi
 *
 */
public class ArgusException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(ArgusException.class);

	/**
	 * the message of the ArgusException.
	 */
	private String message;

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * A public constructor for ArgusException specifying exception message.
	 *
	 * @param msg
	 *            exception message.
	 */
	public ArgusException(String msg) {
		LOGGER.info("[Constructor]: (msg: " + msg + ")");
		this.message = msg;
	}
	/**
	 * A public constructor of <code>ArgusException</code> containing message
	 * and root cause (as <code>Throwable</code>) of the exception.
	 *
	 * @param msg
	 *            exception message.
	 * @param e
	 *            Throwable object.
	 *
	 */
	public ArgusException(String msg, Throwable e) {
		this.message = msg;
		this.initCause(e);
	}

	/**
	 * Gets the class name and exception message.
	 *
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String s = getClass().getName();
		return s + ": " + message;
	}
}
