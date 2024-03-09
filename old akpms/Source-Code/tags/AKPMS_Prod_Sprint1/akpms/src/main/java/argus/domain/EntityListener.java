/**
 * this class is used to manage audit fields like createdby, modifiedby, createdon, modifiedon
 */
package argus.domain;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import argus.util.AkpmsUtil;

/**
 * @author bhupender.s
 *
 */
public class EntityListener {

	private static final Log LOGGER = LogFactory.getLog(EntityListener.class);

	@PrePersist
	public void onPrePersist(Object o) {
		LOGGER.info("in EntityListener-onPrePersist method.");
		populateTimestamp(o);
	}

	@PreUpdate
	public void onPreUpdate(Object o) {
		LOGGER.info("in EntityListener-onPreUpdate method.");
		populateTimestamp(o);
	}

	protected void populateTimestamp(Object obj) {
		LOGGER.info("in EntityListener-populateTimestamp method.");
		Calendar currenttime = Calendar.getInstance();
		if (obj instanceof Auditable) {
			Auditable ts = (Auditable) obj;
			if (ts.getCreatedBy() == null || ts.getCreatedBy().getId() == null) {
				LOGGER.info("in createdOn.");
				ts.setCreatedOn(new Date((currenttime.getTime()).getTime()));
				ts.setCreatedBy(AkpmsUtil.getLoggedInUser());
			} else {
				LOGGER.info("in modifiedOn.");
				ts.setModifiedOn(new Date((currenttime.getTime()).getTime()));
				ts.setModifiedBy(AkpmsUtil.getLoggedInUser());
			}
		}else if (obj instanceof AuditableReModify) {
			AuditableReModify ts = (AuditableReModify)obj;
			if (ts.getCreatedBy() == null ) {
				LOGGER.info("in createdOn.");
				ts.setCreatedOn(new Date((currenttime.getTime()).getTime()));
				ts.setCreatedBy(AkpmsUtil.getLoggedInUser());
			} else if(ts.getModifiedBy() == null) {
				LOGGER.info("in modifiedOn.");
				ts.setModifiedOn(new Date((currenttime.getTime()).getTime()));
				ts.setModifiedBy(AkpmsUtil.getLoggedInUser());
			}else if(ts.isReModifiedNecessary()){
				LOGGER.info("in reModifiedOn.");
				ts.setReModifiedOn(new Date((currenttime.getTime()).getTime()));
				ts.setReModifiedBy(AkpmsUtil.getLoggedInUser().getId());
			}
		}
	}


}
