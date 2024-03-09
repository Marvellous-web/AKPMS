package argus.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public class ReModifyBaseEntity extends BaseEntity implements AuditableReModify{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="re_modified_by")
	private Long reModifiedBy;
	
	@Column(name="re_modified_on")
	private Date reModifiedOn;
	
	@Transient
	private boolean reModifiedNecessary = false;
	
	@Override
	public Date getReModifiedOn() {
		// TODO Auto-generated method stub
		return reModifiedOn;
	}

	@Override
	public Long getReModifiedBy() {
		// TODO Auto-generated method stub
		return reModifiedBy;
	}

	@Override
	public void setReModifiedBy(Long reModifiedBy) {
		this.reModifiedBy = reModifiedBy;
	}

	@Override
	public void setReModifiedOn(Date reModifiedOn) {
		 this.reModifiedOn = reModifiedOn; 
	}
	
	@Override
	public boolean isReModifiedNecessary() {
		return this.reModifiedNecessary;
	}

	@Override
	public void setReModifiedNecessary(Boolean reModifiedNecessary) {
		this.reModifiedNecessary = reModifiedNecessary;		
	}	
}