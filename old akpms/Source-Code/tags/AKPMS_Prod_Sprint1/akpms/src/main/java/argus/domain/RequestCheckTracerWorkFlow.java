package argus.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="request_check_tracer_workflow")
@EntityListeners(EntityListener.class)
public class RequestCheckTracerWorkFlow extends ModifyOnlyBaseEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@OneToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE,
			CascadeType.REFRESH, CascadeType.REMOVE })
	@JoinColumns({ @JoinColumn(name = "ar_productivity_id", referencedColumnName = "id", unique = false, nullable = false) })
	private ArProductivity arProductivity;

	@Column(name="check_mailing_address")
	private String checkMailingAdd;

	@Column(name="check_no")
	private String checkNo;

	@Column(name="check_issue_date")
	private Date checkIssueDate;

	@Column(name="check_cashed_date")
	private Date checkCashedDate;

	@Column(name="check_amount")
	private Float checkAmount;

	@Column(name = "argus_remark", columnDefinition = "TEXT")
	private String argusRemark;

	@Column(name="status")
	private int status;

	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH,
			CascadeType.DETACH, CascadeType.REMOVE })
	@JoinColumns({ @JoinColumn(name = "attachment_id", referencedColumnName = "id", nullable = true) })
	private Files attachment;

	@NotNull
	@Column(name = "deleted", columnDefinition = "TINYINT(1) DEFAULT '0'")
	private boolean deleted = false;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getCheckNo() {
		return checkNo;
	}

	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}

	public Date getCheckIssueDate() {
		return checkIssueDate;
	}

	public void setCheckIssueDate(Date checkIssueDate) {
		this.checkIssueDate = checkIssueDate;
	}

	public Date getCheckCashedDate() {
		return checkCashedDate;
	}

	public void setCheckCashedDate(Date checkCashedDate) {
		this.checkCashedDate = checkCashedDate;
	}

	public String getArgusRemark() {
		return argusRemark;
	}

	public void setArgusRemark(String argusRemark) {
		this.argusRemark = argusRemark;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public ArProductivity getArProductivity() {
		return arProductivity;
	}

	public void setArProductivity(ArProductivity arProductivity) {
		this.arProductivity = arProductivity;
	}

	public String getCheckMailingAdd() {
		return checkMailingAdd;
	}

	public void setCheckMailingAdd(String checkMailingAdd) {
		this.checkMailingAdd = checkMailingAdd;
	}

	public Float getCheckAmount() {
		return checkAmount;
	}

	public void setCheckAmount(Float checkAmount) {
		this.checkAmount = checkAmount;
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
