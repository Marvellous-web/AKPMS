/**
 *
 */
package argus.domain.paymentproductivity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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

import argus.domain.BaseEntity;
import argus.domain.EntityListener;
import argus.domain.PaymentBatch;

/**
 * @author vishal.joshi
 *
 */
@Entity
@EntityListeners(EntityListener.class)
@Table(name = "payment_productivity")
public class PaymentProductivity extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "payment_productivity_type")
	private int paymentProdType;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "batch_id", referencedColumnName = "id", unique = false, nullable = false) })
	private PaymentBatch paymentBatch;

	@OneToOne
	@JoinColumns({ @JoinColumn(name = "query_to_tl_id", referencedColumnName = "id", unique = false, nullable = true) })
	private PaymentProdQueryToTL paymentProdQueryToTL;

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
	private int workFlowId;

	@NotNull
	@Column(name = "deleted", columnDefinition = "TINYINT(1) DEFAULT '0'")
	private boolean deleted = false;

	@Column(name = "is_offset", columnDefinition = "TINYINT(1) DEFAULT '0'")
	private boolean offset = false;

	@Transient
	private String workFlowName;

	@Transient
	private Double ctTotalPosted;

	/*
	 * @Transient private String dosTo;
	 * 
	 * public String getDosTo() { return dosTo; }
	 * 
	 * public void setDosTo(String dosTo) { this.dosTo = dosTo; }
	 */

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the paymentProdType
	 */
	public int getPaymentProdType() {
		return paymentProdType;
	}

	/**
	 * @param paymentProdType
	 *            the paymentProdType to set
	 */
	public void setPaymentProdType(int paymentProdType) {
		this.paymentProdType = paymentProdType;
	}

	/**
	 * @return the paymentBatch
	 */
	public PaymentBatch getPaymentBatch() {
		return paymentBatch;
	}

	/**
	 * @param paymentBatch
	 *            the paymentBatch to set
	 */
	public void setPaymentBatch(PaymentBatch paymentBatch) {
		this.paymentBatch = paymentBatch;
	}

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
	 * @return the workFlowName
	 */
	public String getWorkFlowName() {
		return workFlowName;
	}

	/**
	 * @param workFlowName
	 *            the workFlowName to set
	 */
	public void setWorkFlowName(String workFlowName) {
		this.workFlowName = workFlowName;
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
	public Double getCtTotalPosted() {
		Double manuallyPostedAmount = 0.0D;
		Double elecPostedAmount = 0.0D;

		if (paymentBatch.getManuallyPostedAmt() != null) {
			manuallyPostedAmount = paymentBatch.getManuallyPostedAmt();
		}
		if (paymentBatch.getElecPostedAmt() != null) {
			elecPostedAmount = paymentBatch.getElecPostedAmt();
		}

		return manuallyPostedAmount + elecPostedAmount;
	}

	/**
	 * @param ctTotalPosted
	 *            the ctTotalPosted to set
	 */
	// public void setCtTotalPosted(Double ctTotalPosted) {
	// this.ctTotalPosted = ctTotalPosted;
	// }

	/**
	 * @return the paymentProdQueryToTL
	 */
	public PaymentProdQueryToTL getPaymentProdQueryToTL() {
		return paymentProdQueryToTL;
	}

	/**
	 * @param paymentProdQueryToTL
	 *            the paymentProdQueryToTL to set
	 */
	public void setPaymentProdQueryToTL(
			PaymentProdQueryToTL paymentProdQueryToTL) {
		this.paymentProdQueryToTL = paymentProdQueryToTL;
	}

}
