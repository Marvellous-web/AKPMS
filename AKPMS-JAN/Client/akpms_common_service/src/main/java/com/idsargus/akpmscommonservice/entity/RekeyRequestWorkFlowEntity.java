package com.idsargus.akpmscommonservice.entity;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Getter;
import lombok.Setter;

//@XStreamAlias("ARRekeyRequestWorkFlow")
@Entity
@Getter
@Setter
@Table(name = "rekey_request_workflow")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class RekeyRequestWorkFlowEntity extends BaseAuditableEntity {

	private static final long serialVersionUID = 1L;
	
	@OneToOne(cascade = CascadeType.ALL)//, mappedBy =  "id")
	@JoinColumns({ @JoinColumn(name = "ar_productivity_id", referencedColumnName = "id") })
	private ArProductivityEntity arProductivity;

	@Column(name = "batch_number")
	private String batchNumber;

	@Column(name = "request_reason")
	private String requestReason;

	@Column(name="status")
	private int status = 1;

	@Column(name = "charge_posting_remark")
	private String chargePostingRemark;

	@Column(name = "coding_remark")
	private String codingRemark;

	@Column(name = "charge_posting_remark_date")
	private Date chargePostingRemarkDate;

	@Column(name = "coding_remark_date")
	private Date codingRemarkDate;

	@Column(name="dos")
	private String dos;

	/************* add fields from productivity (START) ************/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "insurance_id", referencedColumnName = "id") })
	private InsuranceEntity insurance;

	// @XStreamConverter(value = XstreamDateConverter.class)
	// private String dos;

	@Column(name = "cpt", length = 100)
	private String cpt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "provider_id", referencedColumnName = "id") })
	private DoctorEntity doctor;

	private Float balanceAmt;

	@Column(name = "remark")
	private String remark;

	@Transient
	private Integer arProductivityId;
	 
	@Transient
	private Integer insuranceId;
	
	@Transient
	private Integer doctorId;
	/************* add fields from productivity (END) ************/
//
//	@OneToMany(cascade = CascadeType.MERGE, mappedBy = "rekeyRequestWorkFlow", fetch = FetchType.EAGER, targetEntity = RekeyRequestRecord.class)
//	private List<RekeyRequestRecordEntity> rekeyRequestRecords;


//	/**
//	 * @return the arProductivity
//	 */
//	public ArProductivityEntity getArProductivity() {
//		return arProductivity;
//	}
//
//	/**
//	 * @param arProductivity
//	 *            the arProductivity to set
//	 */
//	public void setArProductivity(ArProductivityEntity arProductivity) {
//		this.arProductivity = arProductivity;
//	}
//
//	/**
//	 * @return the batchNumber
//	 */
//	public String getBatchNumber() {
//		return batchNumber;
//	}
//
//	/**
//	 * @param batchNumber
//	 *            the batchNumber to set
//	 */
//	public void setBatchNumber(String batchNumber) {
//		this.batchNumber = batchNumber;
//	}
//
//	/**
//	 * @return the requestReason
//	 */
//	public String getRequestReason() {
//		return requestReason;
//	}
//
//	/**
//	 * @param requestReason
//	 *            the requestReason to set
//	 */
//	public void setRequestReason(String requestReason) {
//		this.requestReason = requestReason;
//	}
//
//	/**
//	 * @return the status
//	 */
//	public int getStatus() {
//		return status;
//	}
//
//	/**
//	 * @param status
//	 *            the status to set
//	 */
//	public void setStatus(int status) {
//		this.status = status;
//	}
//
//	/**
//	 * @return the chargePostingRemark
//	 */
//	public String getChargePostingRemark() {
//		return chargePostingRemark;
//	}
//
//	/**
//	 * @param chargePostingRemark
//	 *            the chargePostingRemark to set
//	 */
//	public void setChargePostingRemark(String chargePostingRemark) {
//		this.chargePostingRemark = chargePostingRemark;
//	}
//
//	/**
//	 * @return the codingRemark
//	 */
//	public String getCodingRemark() {
//		return codingRemark;
//	}
//
//	/**
//	 * @param codingRemark
//	 *            the codingRemark to set
//	 */
//	public void setCodingRemark(String codingRemark) {
//		this.codingRemark = codingRemark;
//	}
//
//	/**
//	 * @return the chargePostingRemarkDate
//	 */
//	public Date getChargePostingRemarkDate() {
//		return chargePostingRemarkDate;
//	}
//
//	/**
//	 * @param chargePostingRemarkDate
//	 *            the chargePostingRemarkDate to set
//	 */
//	public void setChargePostingRemarkDate(Date chargePostingRemarkDate) {
//		this.chargePostingRemarkDate = chargePostingRemarkDate;
//	}
//
//	/**
//	 * @return the codingRemarkDate
//	 */
//	public Date getCodingRemarkDate() {
//		return codingRemarkDate;
//	}
//
//	/**
//	 * @param codingRemarkDate
//	 *            the codingRemarkDate to set
//	 */
//	public void setCodingRemarkDate(Date codingRemarkDate) {
//		this.codingRemarkDate = codingRemarkDate;
//	}

	/**
	 * @return the rekeyRequestRecords
	 */
//	public List<RekeyRequestRecord> getRekeyRequestRecords() {
//		return rekeyRequestRecords;
//	}
//
//	/**
//	 * @param rekeyRequestRecords
//	 *            the rekeyRequestRecords to set
//	 */
//	public void setRekeyRequestRecords(
//			List<RekeyRequestRecord> rekeyRequestRecords) {
//		this.rekeyRequestRecords = rekeyRequestRecords;
//	}

	/**
	 * @return the dos
	 */
//	public String getDos() {
//		return dos;
//	}
//
//	/**
//	 * @param dos the dos to set
//	 */
//	public void setDos(String dos) {
//		this.dos = dos;
//	}
//
//	/**
//	 * @return the insurance
//	 */
//	public InsuranceEntity getInsurance() {
//		return insurance;
//	}
//
//	/**
//	 * @param insurance
//	 *            the insurance to set
//	 */
//	public void setInsurance(InsuranceEntity insurance) {
//		this.insurance = insurance;
//	}
//
//	/**
//	 * @return the doctor
//	 */
//	public DoctorEntity getDoctor() {
//		return doctor;
//	}
//
//	/**
//	 * @param doctor
//	 *            the doctor to set
//	 */
//	public void setDoctor(DoctorEntity doctor) {
//		this.doctor = doctor;
//	}
//
//	/**
//	 * @return the balanceAmt
//	 */
//	public Float getBalanceAmt() {
//		return balanceAmt;
//	}
//
//	/**
//	 * @param balanceAmt
//	 *            the balanceAmt to set
//	 */
//	public void setBalanceAmt(Float balanceAmt) {
//		this.balanceAmt = balanceAmt;
//	}
//
//	/**
//	 * @return the remark
//	 */
//	public String getRemark() {
//		return remark;
//	}
//
//	/**
//	 * @param remark
//	 *            the remark to set
//	 */
//	public void setRemark(String remark) {
//		this.remark = remark;
//	}
//
//	/**
//	 * @return the cpt
//	 */
//	public String getCpt() {
//		return cpt;
//	}
//
//	/**
//	 * @param cpt
//	 *            the cpt to set
//	 */
//	public void setCpt(String cpt) {
//		this.cpt = cpt;
//	}


}