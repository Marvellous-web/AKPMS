package com.idsargus.akpmsadminservice.entity;


import com.idsargus.akpmscommonservice.entity.QueryToTL;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Getter
@Setter
@Entity
@Table(name = "payment_productivity")
//@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class AdminPaymentProductivity extends AdminBaseAuditableEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	@Column(name = "payment_productivity_type")
	private Integer paymentProdType;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "batch_id", referencedColumnName = "id") })
	private AdminPaymentBatch paymentBatch;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "query_to_tl_id", referencedColumnName = "id") })
	private QueryToTL paymentProdQueryToTL;

	@Column(name = "chk_number")
	private String chkNumber;

	@Column(name = "manual_transaction")
	private Integer manualTransaction;

	@Column(name = "electronically_transaction")
	private Integer elecTransaction;

	@Column(name = "time")
	private Integer time;

	@Column(name = "remark", columnDefinition = "TEXT")
	private String remark;

	@Column(name = "workflow_id")
	private Integer workFlowId;

	@NotNull
	@Column(name = "deleted", columnDefinition = "TINYINT(1) DEFAULT '0'")
	private boolean deleted = false;

	@Column(name = "is_offset", columnDefinition = "TINYINT(1) DEFAULT '0'")
	private boolean offset = false;

	@Column(name = "scan_date")
	private Date scanDate;

//	@Transient
//	private String workFlowName;
//
//	@Transient
//	private Double ctTotalPosted;

	/*
	 * @Transient private String dosTo;
	 *
	 * public String getDosTo() { return dosTo; }
	 *
	 * public void setDosTo(String dosTo) { this.dosTo = dosTo; }
	 */

	

	/**
	 * @return the chkNumber
	 */
	public String getChkNumber() {
		return chkNumber;
	}

	/**
	 * @param chkNumber
	 *            the chkNumber to set
	 */
	public void setChkNumber(String chkNumber) {
		this.chkNumber = chkNumber;
	}

	/**
	 * @return the manualTransaction
	 */
	public Integer getManualTransaction() {
		return manualTransaction;
	}

	/**
	 * @param manualTransaction
	 *            the manualTransaction to set
	 */
	public void setManualTransaction(Integer manualTransaction) {
		this.manualTransaction = manualTransaction;
	}

	/**
	 * @return the time
	 */
	public Integer getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(Integer time) {
		this.time = time;
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
	 * @return the workFlowId
	 */
	public int getWorkFlowId() {
		return workFlowId;
	}

	/**
	 * @param workFlowId
	 *            the workFlowId to set
	 */
	public void setWorkFlowId(int workFlowId) {
		this.workFlowId = workFlowId;
	}

	
	/**
	 * @return the deleted
	 */
	public boolean isDeleted() {
		return deleted;
	}

	/**
	 * @param deleted
	 *            the deleted to set
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	/**
	 * @return the elecTransaction
	 */
	public Integer getElecTransaction() {
		return elecTransaction;
	}

	/**
	 * @param elecTransaction
	 *            the elecTransaction to set
	 */
	public void setElecTransaction(Integer elecTransaction) {
		this.elecTransaction = elecTransaction;
	}

	/**
	 * @return the offset
	 */
	public boolean isOffset() {
		return offset;
	}

	/**
	 * @param offset
	 *            the offset to set
	 */
	public void setOffset(boolean offset) {
		this.offset = offset;
	}

	/**
	 * @return the ctTotalPosted
	 */
//	public Double getCtTotalPosted() {
//		Double manuallyPostedAmount = 0.0D;
//		Double elecPostedAmount = 0.0D;
//
//		if (paymentBatch.getManuallyPostedAmt() != null) {
//			manuallyPostedAmount = paymentBatch.getManuallyPostedAmt();
//		}
//		if (paymentBatch.getElecPostedAmt() != null) {
//			elecPostedAmount = paymentBatch.getElecPostedAmt();
//		}
//
//		return manuallyPostedAmount + elecPostedAmount;
//	}

	
	/**
	 * @return the scanDate
	 */
	public Date getScanDate() {
		return scanDate;
	}

	/**
	 * @param scanDate
	 *            the scanDate to set
	 */
	public void setScanDate(Date scanDate) {
		this.scanDate = scanDate;
	}

}