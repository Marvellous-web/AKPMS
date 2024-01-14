package com.idsargus.akpmscommonservice.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;




@Entity
@Table(name="payment_batch")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class PaymentBatchEntity extends BaseAuditableEntity{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Integer id;

	@Column(name="billing_month")
	private Long billingMonth;

	@Column(name="billing_year")
	private Long billingYear;

	@ManyToOne()
	@JoinColumns({ @JoinColumn(name = "doctor_id", referencedColumnName = "id", unique = false, nullable = true) })
	private DoctorEntity doctor = null;

	@ManyToOne()
	@JoinColumns({ @JoinColumn(name = "pro_health_doctor_id", referencedColumnName = "id", unique = false, nullable = true) })
	private DoctorEntity phDoctorList = null;

	@ManyToOne()
	@JoinColumns({ @JoinColumn(name = "payment_type_id", referencedColumnName = "id", unique = false, nullable = true) })
	private PaymentTypeEntity paymentType = null;

	@ManyToOne()
	@JoinColumns({ @JoinColumn(name = "revenue_type_id", referencedColumnName = "id", unique = false, nullable = true) })
	private RevenueTypeEntity revenueType = null;

	@ManyToOne()
	@JoinColumns({ @JoinColumn(name = "insurance_id", referencedColumnName = "id", unique = false, nullable = true) })
	private InsuranceEntity insurance = null;

//	@ManyToOne()
//	@JoinColumns({ @JoinColumn(name = "admin_income", referencedColumnName = "id", unique = false, nullable = true) })
//	private AdminIncome adminIncome = null;


//	@OneToMany(cascade = CascadeType.MERGE, mappedBy = "paymentBatch", targetEntity = PaymentBatchMoneySourceEntity.class)
//	private List<PaymentBatchMoneySourceEntity> paymentBatchMoneySources;

//	@Column(name = "isMoneySource")
//	private boolean moneySource = false;

	@Column(name="era_check_no")
	private String eraCheckNo;

	@Column(columnDefinition = "TEXT")
	private String comment;

	// user will fill when he will create payment batch (deposit amt or ndba)
	@Column(name="deposit_amt")
	private Double depositAmount = 0.0;

	// user will fill when he will create payment batch (deposit amt or ndba)
	private Double ndba = 0.0;

	// will be used on payment batch add/update/reupdate
	@Column(name = "offset_plus")
	private Double offsetPlus = 0.0;

	// will be used on payment batch add/update/reupdate
	@Column(name = "offset_minus")
	private Double offsetMinus = 0.0;

	// will be used on payment batch add
	@Column(name="refund_chk")
	private Double refundChk = 0.0;

	// will be used on payment batch add
	@Column(name="nsf_sys_ref")
	private Double nsfSysRef = 0.0;

	// will be used on payment batch add

	@Column(name="deposit_date")
	private Date depositDate;


	// column move from prod, will be used at payment productivity, payment
	// batch add/update/reupdate
	@Column(name = "agency_money")
	private Double agencyMoney = 0.0;

	// column move from prod, will be used at payment productivity, payment
	// batch add/update/reupdate
	@Column(name = "other_income")
	private Double otherIncome = 0.0;

	// column move from prod, will be used at payment productivity, payment
	// batch add/update/reupdate
	@Column(name = "old_prior_ar")
	private Double oldPriorAr = 0.0;

	// column move from prod, will be used at payment productivity, payment
	// batch add/update/reupdate
	@Column(name="suspense_account")
	private Double suspenseAccount = 0.0;

	// column move from prod, this should be used to calculate CT posted total
	// will be used at payment prod, payment batch update/reupdate
	@Column(name = "manually_posted_amt")
	private Double manuallyPostedAmt = 0.0;

	// column move from prod, this should be used to calculate CT posted total
	// will be used at payment prod, payment batch update/reupdate
	@Column(name = "electronically_posted_amt")
	private Double elecPostedAmt = 0.0;

	/*
	 * column move from prod will be used at payment prod, payment batch
	 * update/reupdate (CT POSTED DATE)
	 */
	
	@Column(name = "date_posted")
	private Date datePosted;


	// will be used at payment prod (Auto filled from session)
	// payment batch reupdate (from dropdown)
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumns({ @JoinColumn(name = "posted_by_id", referencedColumnName = "id", unique = false, nullable = true) })
	private UserEntity postedBy = null;

	// will be used at payment prod (Auto filled current date),
	// payment batch update (current date)
	@Column(name="posted_on")
	private Date postedOn = null;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumns({ @JoinColumn(name = "revised_by_id", referencedColumnName = "id", unique = false, nullable = true) })
	private UserEntity revisedBy = null;

	@Column(name="revised_on")
	private Date revisedOn;

	@ManyToOne()
	@JoinColumns({ @JoinColumn(name = "reupdated_by_id", referencedColumnName = "id", unique = false, nullable = true) })
	private UserEntity reUpdatedBy = null;

	@Column(name = "reupdated_on")
	private Date reUpdatedOn;

	@Column(name = "offset_type")
	private String offsetType;

	@Transient
	@Column(columnDefinition = "TINYINT(1) DEFAULT '0'")
	private boolean addMore = false;

	@Transient
	private long posting = 0;

	@Transient
	private Double ctPostedTotal;



}