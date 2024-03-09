package com.idsargus.akpmsarservice.model;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import org.springframework.data.domain.Auditable;

//import argus.util.XstreamDateConverter;

//import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.idsargus.akpmscommonservice.entity.UserEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author bhupender.s
 * 
 */
@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@OneToOne()
	@JoinColumns({ @JoinColumn(name = "created_by", referencedColumnName = "id", insertable = true, updatable = false) })
	private UserEntity createdBy;

	@OneToOne()
	@JoinColumns({ @JoinColumn(name = "modified_by", referencedColumnName = "id", insertable = false, updatable = true) })
	private UserEntity modifiedBy;

//	@XStreamConverter(value = XstreamDateConverter.class)
	@Column(name = "created_on", insertable = true, updatable = false)
	private Date createdOn;

	@Column(name = "modified_on", insertable = false, updatable = true)
	private Date modifiedOn;

//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see argus.domain.Auditable#getCreatedOn()
//	 */
//	@Override
//	public Date getCreatedOn() {
//		return this.createdOn;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see argus.domain.Auditable#setCreatedOn(java.util.Date)
//	 */
//	@Override
//	public void setCreatedOn(Date createdOn) {
//		this.createdOn = createdOn;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see argus.domain.Auditable#getModifiedOn()
//	 */
//	@Override
//	public Date getModifiedOn() {
//		return this.modifiedOn;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see argus.domain.Auditable#setModifiedOn(java.util.Date)
//	 */
//	@Override
//	public void setModifiedOn(Date modifiedOn) {
//		this.modifiedOn = modifiedOn;
//	}
//
//	@Override
//	public User getCreatedBy() {
//		return createdBy;
//	}
//
//	@Override
//	public void setCreatedBy(User createdBy) {
//		this.createdBy = createdBy;
//	}
//
//	@Override
//	public User getModifiedBy() {
//		return modifiedBy;
//	}
//
//	@Override
//	public void setModifiedBy(User modifiedBy) {
//		this.modifiedBy = modifiedBy;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see argus.domain.Auditable#getCreatedBy()
//	 */

}