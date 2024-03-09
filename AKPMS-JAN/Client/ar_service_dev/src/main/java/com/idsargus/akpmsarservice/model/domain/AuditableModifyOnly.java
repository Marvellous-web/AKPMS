package com.idsargus.akpmsarservice.model.domain;
import java.util.Date;

import com.idsargus.akpmscommonservice.entity.UserEntity;

/**
 * @author bhupender.s
 *
 */
public interface AuditableModifyOnly {

	Date getModifiedOn();

	UserEntity getModifiedBy();

	void setModifiedBy(UserEntity user);

	void setModifiedOn(Date modifiedOn);
}