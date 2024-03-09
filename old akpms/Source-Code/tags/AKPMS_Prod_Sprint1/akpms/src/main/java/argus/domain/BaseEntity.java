/**
 *
 */
package argus.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import argus.util.XstreamDateConverter;

import com.thoughtworks.xstream.annotations.XStreamConverter;

/**
 * @author bhupender.s
 * 
 */
@MappedSuperclass
public abstract class BaseEntity implements Auditable, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@OneToOne()
	@JoinColumns({ @JoinColumn(name = "created_by", referencedColumnName = "id", insertable = true, updatable = false) })
	private User createdBy;

	@OneToOne()
	@JoinColumns({ @JoinColumn(name = "modified_by", referencedColumnName = "id", insertable = false, updatable = true) })
	private User modifiedBy;

	@XStreamConverter(value = XstreamDateConverter.class)
	@Column(name = "created_on", insertable = true, updatable = false)
	private Date createdOn;

	@Column(name = "modified_on", insertable = false, updatable = true)
	private Date modifiedOn;

	/*
	 * (non-Javadoc)
	 * 
	 * @see argus.domain.Auditable#getCreatedOn()
	 */
	@Override
	public Date getCreatedOn() {
		return this.createdOn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see argus.domain.Auditable#setCreatedOn(java.util.Date)
	 */
	@Override
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see argus.domain.Auditable#getModifiedOn()
	 */
	@Override
	public Date getModifiedOn() {
		return this.modifiedOn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see argus.domain.Auditable#setModifiedOn(java.util.Date)
	 */
	@Override
	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	@Override
	public User getCreatedBy() {
		return createdBy;
	}

	@Override
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public User getModifiedBy() {
		return modifiedBy;
	}

	@Override
	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see argus.domain.Auditable#getCreatedBy()
	 */

}
