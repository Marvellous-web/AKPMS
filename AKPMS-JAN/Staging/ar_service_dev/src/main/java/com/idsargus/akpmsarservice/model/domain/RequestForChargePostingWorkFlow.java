package com.idsargus.akpmsarservice.model.domain;
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

import com.idsargus.akpmscommonservice.entity.ArProductivityEntity;
import com.idsargus.akpmscommonservice.entity.BaseAuditableEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="request_for_charge_posting_workflow")
public class RequestForChargePostingWorkFlow  extends BaseAuditableEntity{


	@OneToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH, CascadeType.REMOVE})
	@JoinColumns({ @JoinColumn(name = "ar_productivity_id", referencedColumnName = "id", unique = false, nullable = true) })
	private ArProductivityEntity arProductivity;

	@Column(name="batch_no")
	private String batchNo;

	@Column(name="patient_name")
	private String patientName;

	@Column(name="account_no")
	private String accountNo;

	private Date dos;

	@Column(name="reason_for_request")
	private int reasonForRequest;

	@Column(name = "charge_posting_remark", columnDefinition = "TEXT")
	private String chargePostingRemark;

	@Column(name = "coding_remark", columnDefinition = "TEXT")
	private String codingRemark;


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

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public Date getDos() {
		return dos;
	}

	public void setDos(Date dos) {
		this.dos = dos;
	}

	public int getReasonForRequest() {
		return reasonForRequest;
	}

	public void setReasonForRequest(int reasonForRequest) {
		this.reasonForRequest = reasonForRequest;
	}

	public String getChargePostingRemark() {
		return chargePostingRemark;
	}

	public void setChargePostingRemark(String chargePostingRemark) {
		this.chargePostingRemark = chargePostingRemark;
	}

	public String getCodingRemark() {
		return codingRemark;
	}

	public void setCodingRemark(String codingRemark) {
		this.codingRemark = codingRemark;
	}



}