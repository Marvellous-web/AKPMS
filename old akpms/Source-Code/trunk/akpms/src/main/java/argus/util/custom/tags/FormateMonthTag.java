package argus.util.custom.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.SkipPageException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.log4j.Logger;

import argus.util.AkpmsUtil;
import argus.util.Constants;

/**
 * The tag class is responsible for format month integer value to its string
 * represantation e.g 1 to Jan
 *
 */
public class FormateMonthTag extends SimpleTagSupport {

	private static final Logger LOGGER = Logger
			.getLogger(FormateMonthTag.class);

	private Integer value;

	@Override
	public void doTag() throws JspException, IOException {

		if (this.value > Constants.ZERO && this.value <= Constants.TWELVE) {

			try {
				getJspContext().getOut()
						.write(AkpmsUtil.getMonths().get(value));
				LOGGER.debug("Month value for formate : " + value
						+ ". After formate : "
						+ AkpmsUtil.getMonths().get(value));
			} catch (Exception e) {
				// stop page from loading further by throwing SkipPageException
				throw new SkipPageException("Exception in formatting " + value);
			}
		} else {
			throw new IllegalArgumentException(
					"Please provide valid value argument for formatMonth tag");
		}
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

}
