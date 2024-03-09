/**
 *
 */
package argus.domain;

import java.util.Date;

/**
 * @author bhupender.s
 *
 */
public interface AuditableModifyOnly {

	Date getModifiedOn();

	User getModifiedBy();

	void setModifiedBy(User user);

	void setModifiedOn(Date modifiedOn);
}
