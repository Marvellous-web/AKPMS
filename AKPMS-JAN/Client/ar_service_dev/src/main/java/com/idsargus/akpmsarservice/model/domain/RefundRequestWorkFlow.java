package com.idsargus.akpmsarservice.model.domain;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.idsargus.akpmscommonservice.entity.ArProductivityEntity;
import com.idsargus.akpmscommonservice.entity.BaseAuditableEntity;
import com.idsargus.akpmscommonservice.entity.DoctorEntity;
import com.idsargus.akpmscommonservice.entity.InsuranceEntity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author rajiv.k
 *
 */
@Getter
@Setter
@Entity
@Table(name = "refund_request_workflow")
public class RefundRequestWorkFlow extends BaseAuditableEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@OneToOne(cascade = CascadeType.ALL)//,  mappedBy =  "id")
	@JoinColumns({ @JoinColumn(name = "ar_productivity_id", referencedColumnName = "id") })
	private ArProductivityEntity arProductivity;

	@Column(name = "batch")
	private String batch;

	@Column(name= "page_no")
	private Integer pageNumber;

	@Column(name= "refund_lettter_date")
	private Date refundLetterDate;
	@Column(name = "responsible_party")
	private String responsibleParty;

	@Column(name= "refund_requested_amt")
	private Float refundRequestAmt;
	@Column(name = "total_amount")
	private Double totalAmount;

	@Column(name = "dos")
	private String dos;

	@Column(name = "reason")
	private String reason;

	@Column(name = "remark", columnDefinition = "TEXT")
	private String remark;

//	@Column(name = "status")
//	private int status;

	@Column(name = "status")
	private int status;

	@NotNull
	@Column(name = "deleted", columnDefinition = "TINYINT(1) DEFAULT '0'")
	private boolean deleted = false;

	@Transient
	private String dataBas;

	@Transient
	private String patientName;

	@Transient
	private String patientAccNo;

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

	/************* add fields from productivity (END) ************/


	/**
	 * @return the arProductivity
	 */
	public ArProductivityEntity getArProductivity() {
		return arProductivity;
	}

	/**
	 * @param arProductivity
	 *            the arProductivity to set
	 */
	public void setArProductivity(ArProductivityEntity arProductivity) {
		this.arProductivity = arProductivity;
	}

	/**
	 * @return the responsibleParty
	 */
	public String getResponsibleParty() {
		return responsibleParty;
	}

	/**
	 * @param responsibleParty
	 *            the responsibleParty to set
	 */
	public void setResponsibleParty(String responsibleParty) {
		this.responsibleParty = responsibleParty;
	}

	/**
	 * @return the totalAmount
	 */
	public Double getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount
	 *            the totalAmount to set
	 */
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
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
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason
	 *            the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
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

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the dataBas
	 */
	public String getDataBas() {
		return dataBas;
	}

	/**
	 * @param dataBas
	 *            the dataBas to set
	 */
	public void setDataBas(String dataBas) {
		this.dataBas = dataBas;
	}

	/**
	 * @return the patientName
	 */
	public String getPatientName() {
		return patientName;
	}

	/**
	 * @param patientName
	 *            the patientName to set
	 */
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	/**
	 * @return the patientAccNo
	 */
	public String getPatientAccNo() {
		return patientAccNo;
	}

	/**
	 * @param patientAccNo
	 *            the patientAccNo to set
	 */
	public void setPatientAccNo(String patientAccNo) {
		this.patientAccNo = patientAccNo;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
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
	 * @return the cpt
	 */
	public String getCpt() {
		return cpt;
	}

	/**
	 * @param cpt
	 *            the cpt to set
	 */
	public void setCpt(String cpt) {
		this.cpt = cpt;
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
}