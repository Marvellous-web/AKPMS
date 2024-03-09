/**
 *
 */
package argus.domain;

import java.util.Date;

/**
 * @author bhupender.s
 *
 */
public interface AuditableReModifyOnly {

	Date getReModifiedOn();

	Long getReModifiedBy();

	void setReModifiedBy(Long reModifiedBy);

	void setReModifiedOn(Date reModifiedOn);

	boolean isReModifiedNecessary();

	void setReModifiedNecessary(Boolean reModifiedNecessary);

}
