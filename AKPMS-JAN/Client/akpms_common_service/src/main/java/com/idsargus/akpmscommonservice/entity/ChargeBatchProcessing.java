package com.idsargus.akpmscommonservice.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "charge_batch_processing")
@TableGenerator(name = "tableChargeBatch", initialValue = 1000000, allocationSize = 1)
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class ChargeBatchProcessing extends BaseAuditableEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "type")
	private String type;// = "CHARGE_BATCH_TYPE_FACILITY";

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doctor_id", referencedColumnName = "id")
	private DoctorEntity doctor;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dos_from")
	private Date dosFrom; // Date Of Service From

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dos_to")
	private Date dosTo; // Date Of Service To

	@Column(name = "number_of_superbills")
	private Integer numberOfSuperbills = 0;

	@Column(name = "number_of_attachments")
	private Integer numberOfAttachments = 0;

	@Column(name = "remark", columnDefinition = "TEXT")
	private String remarks;

	@Column(name = "date_received")
	private Date dateReceived;

	@Column(name = "date_posted")
	private Date dateBatchPosted;

	@Column(name= "reduction_in_federal_spending")
	private boolean  reductionInFederalSpending;
	
	

//	@Column(name = "scan_date")
//	private Date scanDate;

//	public Date getScanDate() {
//		return scanDate;
//	}
//
//	public void setScanDate(Date scanDate) {
//		this.scanDate = scanDate;
//	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "received_by", referencedColumnName = "id", nullable = true, unique = false)
	private UserEntity receivedBy;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "posted_by", referencedColumnName = "id", nullable = true, unique = false)
	private UserEntity postedBy;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="company_id", referencedColumnName = "id")
	private DoctorCompanyEntity company;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="group_id", referencedColumnName = "id")
	private DoctorGroupEntity group;

	@Column(name = "number_of_superbills_argus")
	private Integer numberOfSuperbillsArgus = 0;

	@Column(name = "number_of_attachments_argus")
	private Integer numberOfAttachmentsArgus = 0;

	@Column(name = "remarks_argus", columnDefinition = "TEXT")
	private String remarksArgus;

	@Transient
	private String dateFrom;

	@Transient
	private String dateTo;

	@Transient
	private Date dateReceivedFrom;

	@Transient
	private Date dateReceivedTo;

	@Transient
	private Date dateBatchPostedTo;

	@Transient
	private Date dateBatchPostedFrom;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "posted_on")
	private Date postedOn;

	@Transient
	private String typeValue;

	@Transient
	private boolean editDoctorSection = false;

	@Transient
	private String tempBatchType;

	@Transient
	private int superBillDifference = 0;

	@Transient
	private int attachmentDifference = 0;

	@Transient
	private Integer doctorId;

	@Transient
	private String doctorName ;

	public Integer getDoctorId(){
		return (this.doctor == null) ? null : this.doctor.getId();
	}
	@Transient
	private Integer groupId;

	@Transient
	private Integer companyId;
	@Transient
	private String companyName ;
	public String getCompanyName() {
		return (this.company == null) ? null : this.company.getName();

	}

	@Transient
	private String groupName ;
	public String getGroupName() {
		return (this.group == null) ? null : this.group.getName();
	}
	public Integer getCompanyId() {
		return (this.company == null) ? null : this.company.getId();

	}
	public Integer getGroupId() {
		return (this.group == null) ? null : this.group.getId();

	}
	public String getDoctorName() {
		return (this.doctor == null) ? null : this.doctor.getName();
	}

	public Date getPostedOn() {
		return postedOn;
	}

	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

}