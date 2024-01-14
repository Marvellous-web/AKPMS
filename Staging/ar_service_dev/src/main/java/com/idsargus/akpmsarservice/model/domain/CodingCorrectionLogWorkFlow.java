package com.idsargus.akpmsarservice.model.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.idsargus.akpmscommonservice.entity.*;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "coding_correction_log_workflow")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class CodingCorrectionLogWorkFlow extends ArBaseAuditableEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumns({ @JoinColumn(name = "ar_productivity_id", referencedColumnName = "id") })
	private ArProductivityEntity arProductivity;

	@Column(name = "batch_no")
	private String batchNo;

	@Column(name = "sequence_no")
	private String sequenceNo;

	@Column(name = "coding_remark", columnDefinition = "TEXT")
	private String codingRemark = null;

	@NotNull
	@Column(name = "deleted", columnDefinition = "TINYINT(1) DEFAULT '0'")
	private boolean deleted = false;

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH, CascadeType.DETACH, CascadeType.REMOVE })
	@JoinColumns({ @JoinColumn(name = "attachment_id", referencedColumnName = "id") })
	private ArFiles attachment = null;

	@Column(name = "workflow_status")
	private String nextAction;

	/************* add fields from productivity (START) ************/
	@ManyToOne()
	@JoinColumns({ @JoinColumn(name = "insurance_id", referencedColumnName = "id") })
	private InsuranceEntity insurance;

	private String dos;

	@Transient
	private String modifiedByUser;


	@Column(name = "cpt", length = 100)
	private String cptcode;

	@ManyToOne()
	@JoinColumns({ @JoinColumn(name = "provider_id", referencedColumnName = "id") })
	private DoctorEntity doctor;

	//added new cr from
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="company_id", referencedColumnName = "id")
	private DoctorCompanyEntity company;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="group_id", referencedColumnName = "id")
	private DoctorGroupEntity group;

	//------to-----

	@Column(name="balance_amt")
	private Float balanceAmt;
	
//	@Column(name="balanceAmt")	
//	private Float balance_amt;

	@Column(name = "remark", columnDefinition = "TEXT")
	private String remark;

	/************* add fields from productivity (END) ************/


	///Addede new cr******************from

	@Transient
	private Integer doctorId;
	public Integer getDoctorId() {
		return (this.doctor == null) ? null : this.doctor.getId();
	}
	@Transient
	private Integer groupId;
	public Integer getGroupId() {
		return (this.group == null) ? null : this.group.getId();
	}
	@Transient
	private Integer companyId;
	public Integer getCompanyId() {
		return (this.company == null) ? null : this.company.getId();
	}
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

	public ArProductivityEntity getArProductivity() {
		return arProductivity;
	}

	public void setArProductivity(ArProductivityEntity arProductivity) {
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

//	public String getCodingRemark() {
//		return codingRemark;
//	}
//
//	public void setCodingRemark(String codingRemark) {
//		this.codingRemark = codingRemark;
//	}
//
//	@Override
//	public Date getModifiedOn() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void setModifiedOn(Date modifiedOn) {
//		// TODO Auto-generated method stub
//
//	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public ArFiles getAttachment() {
		return attachment;
	}

	public void setAttachment(ArFiles attachment) {
		this.attachment = attachment;
	}

	public String getNextAction() {
		return nextAction;
	}

	public void setNextAction(String nextAction) {
		this.nextAction = nextAction;
	}

	/**
	 * @return the insurance
	 */
	public InsuranceEntity getInsurance() {
		return insurance;
	}

	/**
	 * @param insurance
	 *            the insurance to set
	 */
	public void setInsurance(InsuranceEntity insurance) {
		this.insurance = insurance;
	}

	/**
	 * @return the dos
	 */
	public String getDos() {
		return dos;
	}

	/**
	 * @param dos
	 *            the dos to set
	 */
	public void setDos(String dos) {
		this.dos = dos;
	}

	/**
	 * @return the cpt
	 */
	public String getCptcode() {
		return cptcode;
	}

	/**
	 * @param cptcode
	 *            the cpt to set
	 */
	public void setCptcode(String cptcode) {
		this.cptcode = cptcode;
	}

	/**
	 * @return the doctor
	 */
	public DoctorEntity getDoctor() {
		return doctor;
	}

	/**
	 * @param doctor
	 *            the doctor to set
	 */
	public void setDoctor(DoctorEntity doctor) {
		this.doctor = doctor;
	}

	/**
	 * @return the balanceAmt
	 */
	public Float getBalanceAmt() {
		return balanceAmt;
	}

	/**
	 * @param balanceAmt
	 *            the balanceAmt to set
	 */
	public void setBalanceAmt(Float balanceAmt) {
		this.balanceAmt = balanceAmt;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
}