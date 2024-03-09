package com.idsargus.akpmsadminservice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;



@Entity
@Table(name="payment_productivity_offset")
@Getter
@Setter
//@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class AdminPaymentProductivityOffset extends AdminBaseAuditableEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	@Column(name = "account_number")
	private Double accountNumber;

	@Column(name = "chk_number")
	private String chkNumber;

	@Column(name = "chk_date")
	private Date chkDate;

	@Column(name = "patient_name")
	private String patientName;
	
	@Column(name = "offset_remark", columnDefinition = "TEXT")
	private String offsetRemark;
	
	@Column(name = "remark", columnDefinition = "TEXT")
	private String remark;

	@Column(name = "status")
	private String status;
	
	@Column(name = "payment_batch_id")
	private Integer paymentBatchId;
//	@ManyToOne
//	@JoinColumns({ @JoinColumn(name = "payment_batch_id", referencedColumnName = "id") })
//	private AdminPaymentBatch paymentBatch;
	
//	@NotNull
//	@Column(name = "deleted", columnDefinition = "TINYINT(1) DEFAULT '0'")
//	private boolean deleted = false;
//
//	@Column(name = "is_offset", columnDefinition = "TINYINT(1) DEFAULT '0'")
//	private boolean offset = false;
//
//	@Column(name = "scan_date")
//	private Date scanDate;

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
//	public Double getCtTotalPosted() {
//	Double manuallyPostedAmount = 0.0D;
//	Double elecPostedAmount = 0.0D;
//
//	if (paymentBatch.getManuallyPostedAmt() != null) {
//		manuallyPostedAmount = paymentBatch.getManuallyPostedAmt();
//	}
//	if (paymentBatch.getElecPostedAmt() != null) {
//		elecPostedAmount = paymentBatch.getElecPostedAmt();
//	}
//
//	return manuallyPostedAmount + elecPostedAmount;
//}

	


}
