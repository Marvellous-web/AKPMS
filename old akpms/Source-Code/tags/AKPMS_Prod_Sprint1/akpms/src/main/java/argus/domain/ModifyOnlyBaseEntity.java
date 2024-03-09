package argus.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class ModifyOnlyBaseEntity implements AuditableModifyOnly{


	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE,
			CascadeType.REFRESH, CascadeType.REMOVE })
	@JoinColumns({ @JoinColumn(name = "modified_by", referencedColumnName = "id", insertable=false, updatable=true)})
	private User modifiedBy;


	@Column(name = "modified_on", insertable=false, updatable=true)
	private Date modifiedOn;



	public User getModifiedBy() {
		return modifiedBy;
	}


	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	public Date getModifiedOn() {
		return modifiedOn;
	}


	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}


}
