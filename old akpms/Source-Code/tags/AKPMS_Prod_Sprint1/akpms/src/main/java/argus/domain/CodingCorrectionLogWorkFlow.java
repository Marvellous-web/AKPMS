package argus.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="coding_correction_log_workflow")
public class CodingCorrectionLogWorkFlow extends ModifyOnlyBaseEntity{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@OneToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH, CascadeType.REMOVE})
	@JoinColumns({ @JoinColumn(name = "ar_productivity_id", referencedColumnName = "id", unique = false, nullable = true) })
	private ArProductivity arProductivity;

	@Column(name="batch_no")
	private String batchNo;

	@Column(name="sequence_no")
	private String sequenceNo;

	@Column(name = "coding_remark", columnDefinition = "TEXT")
	private String codingRemark;

	@NotNull
	@Column(name = "deleted", columnDefinition = "TINYINT(1) DEFAULT '0'")
	private boolean deleted = false;

	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH,
			CascadeType.DETACH, CascadeType.REMOVE })
	@JoinColumns({ @JoinColumn(name = "attachment_id", referencedColumnName = "id", nullable = true) })
	private Files attachment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public ArProductivity getArProductivity() {
		return arProductivity;
	}

	public void setArProductivity(ArProductivity arProductivity) {
		this.arProductivity = arProductivity;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(String sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public String getCodingRemark() {
		return codingRemark;
	}

	public void setCodingRemark(String codingRemark) {
		this.codingRemark = codingRemark;
	}

	@Override
	public Date getModifiedOn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setModifiedOn(Date modifiedOn) {
		// TODO Auto-generated method stub

	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Files getAttachment() {
		return attachment;
	}

	public void setAttachment(Files attachment) {
		this.attachment = attachment;
	}

}
