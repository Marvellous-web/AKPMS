package com.idsargus.akpmsarservice.model.domain;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.idsargus.akpmscommonservice.entity.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "payment_posting_workflow")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class PaymentPostingWorkFlow extends BaseAuditableEntity {


	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumns({ @JoinColumn(name = "ar_productivity_id", referencedColumnName = "id") })
	private ArProductivityEntity arProductivity;

	@Column(name = "cpt")
	private String cptcode;

	@Column(name = "billed_amount")
	private Double billedAmount = 0.0;

	@Column(name = "primary_amount")
	private Double primaryAmount = 0.0;

	@Column(name = "secondary_amount")
	private Double secondaryAmount = 0.0;

	@Column(name = "contractual_adj")
	private Double contractualAdj = 0.0;

	@Column(name = "bulk_payment_amount")
	private Double bulkPaymentAmount = 0.0;

	@Column(name = "patient_response")
	private Double patientResponse;

	@Column(name = "check_issue_date")
	private Date checkIssueDate = null;

	@Column(name = "check_no")
	private String checkNo;

	@Column(name = "check_cashed_date", columnDefinition = "TEXT")
	private Date checkCashedDate = null;

	@Column(name = "address_check_send")
	private String addressCheckSend;

//	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
//			CascadeType.REFRESH, CascadeType.DETACH, CascadeType.REMOVE })
//	@JoinColumns({ @JoinColumn(name = "copy_cancel_check", referencedColumnName = "id") })
//	private ArFiles copyCancelCheck ;

//	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
//			CascadeType.REFRESH, CascadeType.DETACH, CascadeType.REMOVE })
//	@JoinColumns({ @JoinColumn(name = "eob", referencedColumnName = "id") })
//	private ArFiles eob ;

	@Column(name = "copy_cancel_check")
	private Double copyCancelCheck;
	//@JsonIgnore
	@Column(name = "eob")
	private Double eob;

	//@JsonIgnore
	@NotNull
	@Column(name ="reduction_in_federal_spending", columnDefinition = "tinyint(1) default '0'")
	private boolean reductionInFederalSpending = false;

	@NotNull
	@Column(name = "deleted", columnDefinition = "tinyint(1) default '0'")
	private boolean deleted = false;

	@Column(name = "status")
	private String status;

	@Column(name = "ticketNumber")
	private String ticketNumber;

	@Transient
	private boolean addMore = false;

	private boolean isEobAvailable = false;

	@Column(name = "off_set")
	private String offSet;

	/************* add fields from productivity (START) ************/
	@ManyToOne()
	@JoinColumns({ @JoinColumn(name = "insurance_id", referencedColumnName = "id") })
	private InsuranceEntity insurance ;

	// @XStreamConverter(value = XstreamDateConverter.class)
	private String dos;

	@ManyToOne()
	@JoinColumns({ @JoinColumn(name = "provider_id", referencedColumnName = "id") })
	private DoctorEntity doctor;

	//added new cr ----from
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="company_id", referencedColumnName = "id")
	private DoctorCompanyEntity company;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="group_id", referencedColumnName = "id")
	private DoctorGroupEntity group;

	//------to
	private Float balanceAmt;

	//added new ---from

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
	public String getCompanyName() {
		return (this.company == null) ? null : this.company.getName();
	}


	@Transient
	private String groupName ;
	public String getGroupName() {
		return (this.group == null) ? null : this.group.getName();
	}

  // added ---upto-----here
	@Column(name = "remark", columnDefinition = "TEXT")
	private String remark;

	/************* add fields from productivity (END) ************/

	public boolean isEobAvailable() {
		return isEobAvailable;
	}

	public void setEobAvailable(boolean isEobAvailable) {
		this.isEobAvailable = isEobAvailable;
	}


	public ArProductivityEntity getArProductivity() {
		return arProductivity;
	}

	public void setArProductivity(ArProductivityEntity arProductivity) {
		this.arProductivity = arProductivity;
	}

	public String getCptcode() {
		return cptcode;
	}

	public void setCpt(String cptcode) {
		this.cptcode = cptcode;
	}

	public Double getBilledAmount() {
		return billedAmount;
	}

	public void setBilledAmount(Double billedAmount) {
		this.billedAmount = billedAmount;
	}

	public Double getPrimaryAmount() {
		return primaryAmount;
	}

	public void setPrimaryAmount(Double primaryAmount) {
		this.primaryAmount = primaryAmount;
	}

	public Double getSecondaryAmount() {
		return secondaryAmount;
	}

	public void setSecondaryAmount(Double secondaryAmount) {
		this.secondaryAmount = secondaryAmount;
	}

	public Double getContractualAdj() {
		return contractualAdj;
	}

	public void setContractualAdj(Double contractualAdj) {
		this.contractualAdj = contractualAdj;
	}

	public Double getBulkPaymentAmount() {
		return bulkPaymentAmount;
	}

	public void setBulkPaymentAmount(Double bulkPaymentAmount) {
		this.bulkPaymentAmount = bulkPaymentAmount;
	}

	/**
	 * @return the patientResponse
	 */
	public Double getPatientResponse() {
		return patientResponse;
	}

	/**
	 * @param patientResponse
	 *            the patientResponse to set
	 */
	public void setPatientResponse(Double patientResponse) {
		this.patientResponse = patientResponse;
	}

	public Date getCheckIssueDate() {
		return checkIssueDate;
	}

	public void setCheckIssueDate(Date checkIssueDate) {
		this.checkIssueDate = checkIssueDate;
	}

	public String getCheckNo() {
		return checkNo;
	}

	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}

	public Date getCheckCashedDate() {
		return checkCashedDate;
	}

	public void setCheckCashedDate(Date checkCashedDate) {
		this.checkCashedDate = checkCashedDate;
	}

	public String getAddressCheckSend() {
		return addressCheckSend;
	}

	public void setAddressCheckSend(String addressCheckSend) {
		this.addressCheckSend = addressCheckSend;
	}

//	public ArFiles getCopyCancelCheck() {
//		return copyCancelCheck;
//	}
//
//	public void setCopyCancelCheck(ArFiles copyCancelCheck) {
//		this.copyCancelCheck = copyCancelCheck;
//	}
//
//	public ArFiles getEob() {
//		return eob;
//	}
//
//	public void setEob(ArFiles eob) {
//		this.eob = eob;
//	}


	public Double getCopyCancelCheck() {
		return copyCancelCheck;
	}

	public void setCopyCancelCheck(Double copyCancelCheck) {
		this.copyCancelCheck = copyCancelCheck;
	}

	public Double getEob() {
		return eob;
	}

	public void setEob(Double eob) {
		this.eob = eob;
	}

	public boolean isReductionInFederalSpending() {
		return reductionInFederalSpending;
	}

	public void setReductionInFederalSpending(boolean reductionInFederalSpending) {
		this.reductionInFederalSpending = reductionInFederalSpending;
	}

	public Boolean getReductionInFederalSpending() {
		return reductionInFederalSpending;
	}

	public void setReductionInFederalSpending(Boolean reductionInFederalSpending) {
		this.reductionInFederalSpending = reductionInFederalSpending;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public boolean isAddMore() {
		return addMore;
	}

	public void setAddMore(boolean addMore) {
		this.addMore = addMore;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
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