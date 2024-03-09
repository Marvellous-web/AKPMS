/**
 *
 */
package argus.domain;

import java.util.Date;


/**
 * @author bhupender.s
 *
 */
public interface AuditableCreateOnly {

	Date getCreatedOn();

	User getCreatedBy();

	void setCreatedBy(User createBy);

	void setCreatedOn(Date createdOn);

}
