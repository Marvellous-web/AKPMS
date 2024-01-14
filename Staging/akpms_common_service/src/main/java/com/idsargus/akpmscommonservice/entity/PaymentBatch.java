package com.idsargus.akpmscommonservice.entity;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name="payment_batch")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer"})
//@TableGenerator(name = "tablePaymentBatch", initialValue = 1000000,allocationSize=1)
public class PaymentBatch extends BaseAuditableEntity{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	@Column(name="billing_month")
	private Long billingMonth;

	@Column(name="billing_year")
	private Long billingYear;

	@ManyToOne()
	@JoinColumns({ @JoinColumn(name = "doctor_id", referencedColumnName = "id") })
	private DoctorEntity doctor = null;

	@ManyToOne()
	@JoinColumns({ @JoinColumn(name = "pro_health_doctor_id", referencedColumnName = "id") })
	private DoctorEntity phDoctor = null;

	@ManyToOne()
	@JoinColumns({ @JoinColumn(name = "payment_type_id", referencedColumnName = "id") })
	private PaymentType paymentType = null;

	@ManyToOne()
	@JoinColumns({ @JoinColumn(name = "money_source_id", referencedColumnName = "id") })
	private MoneySource moneySource = null;

	@ManyToOne()
	@JoinColumns({ @JoinColumn(name = "revenue_type_id", referencedColumnName = "id") })
	private RevenueType revenueType = null;

	@ManyToOne()
	@JoinColumns({ @JoinColumn(name = "insurance_id", referencedColumnName = "id") })
	private InsuranceEntity insurance = null;

	// added *********  newcr
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="company_id", referencedColumnName = "id")
	private DoctorCompanyEntity company;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="group_id", referencedColumnName = "id")
	private DoctorGroupEntity group;

	// @ManyToOne()
	// @JoinColumns({ @JoinColumn(name = "admin_income", referencedColumnName =
	// "id", unique = false, nullable = true) })
	// private AdminIncome adminIncome = null;

	// @XStreamOmitField
	// @OneToMany(cascade = CascadeType.MERGE, mappedBy = "paymentBatch",
	// targetEntity = PaymentBatchMoneySource.class)
	// private List<PaymentBatchMoneySource> paymentBatchMoneySources;

//	 @Column(name = "isMoneySource")
//	 private boolean isMoneySource = false;

	@Column(name="era_check_no")
	private String eraCheckNo;

	@Column(columnDefinition = "TEXT")
	private String comment;

	// user will fill when he will create payment batch (deposit amt or ndba)
	@Column(name="deposit_amt")
	private Double depositAmount = 0.0;

	// user will fill when he will create payment batch (deposit amt or ndba)
	@Column(name ="ndba")
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
	@JoinColumns({ @JoinColumn(name = "revised_by_id", referencedColumnName = "id") })
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

	@Column(name = "credit_card")
	private Double creditCard = 0D;

	private Double vault = 0D; // cash vault and check valut

	private Double telecheck = 0D;

	@Transient
	private Double posting = 0D;

	@Transient
	private Double ctPostedTotal = 0D;

	@Transient
	private String companyName ;

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
	private Integer doctorId;

	public Integer getDoctorId() {
		return (this.doctor == null) ? null : this.doctor.getId();
	}


	public String getCompanyName() {
		return (this.company == null) ? null : this.company.getName();
	}

	@Transient
	private String groupName ;
	public String getGroupName() {
		return (this.group == null) ? null : this.group.getName();

	}
	public Long getBillingMonth() {
		return billingMonth;
	}

	public void setBillingMonth(Long billingMonth) {
		this.billingMonth = billingMonth;
	}

	public Long getBillingYear() {
		return billingYear;
	}

	public void setBillingYear(Long billingYear) {
		this.billingYear = billingYear;
	}

	
	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}


	public String getEraCheckNo() {
		return eraCheckNo;
	}

	public void setEraCheckNo(String eraCheckNo) {
		this.eraCheckNo = eraCheckNo;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Double getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(Double depositAmount) {
		this.depositAmount = depositAmount;
	}

	public Double getNdba() {
		return ndba;
	}

	public void setNdba(Double ndba) {
		this.ndba = ndba;
	}

	public Double getAgencyMoney() {
		return agencyMoney;
	}

	public void setAgencyMoney(Double agencyMoney) {
		this.agencyMoney = agencyMoney;
	}

	public Double getOtherIncome() {
		return otherIncome;
	}

	public void setOtherIncome(Double otherIncome) {
		this.otherIncome = otherIncome;
	}

	public Double getOldPriorAr() {
		return oldPriorAr;
	}

	public void setOldPriorAr(Double oldPriorAr) {
		this.oldPriorAr = oldPriorAr;
	}

	public Double getRefundChk() {
		return refundChk;
	}

	public void setRefundChk(Double refundChk) {
		this.refundChk = refundChk;
	}

	public Double getNsfSysRef() {
		return nsfSysRef;
	}

	public void setNsfSysRef(Double nsfSysRef) {
		this.nsfSysRef = nsfSysRef;
	}

	public Date getDepositDate() {
		return depositDate;
	}

	public void setDepositDate(Date depositDate) {
		this.depositDate = depositDate;
	}

	public Double getSuspenseAccount() {
		return suspenseAccount;
	}

	public void setSuspenseAccount(Double suspenseAccount) {
		this.suspenseAccount = suspenseAccount;
	}

	public Double getOffsetPlus() {
		return offsetPlus;
	}

	public void setOffsetPlus(Double offsetPlus) {
		this.offsetPlus = offsetPlus;
	}

	public Double getOffsetMinus() {
		return offsetMinus;
	}

	public void setOffsetMinus(Double offsetMinus) {
		this.offsetMinus = offsetMinus;
	}


	public Date getPostedOn() {
		return postedOn;
	}

	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	/*
	 * public AdminIncome getAdminIncome() { return adminIncome; }
	 *
	 * public void setAdminIncome(AdminIncome adminIncome) { this.adminIncome =
	 * adminIncome; }
	 */

	public RevenueType getRevenueType() {
		return revenueType;
	}

	public void setRevenueType(RevenueType revenueType) {
		this.revenueType = revenueType;
	}


	public Date getRevisedOn() {
		return revisedOn;
	}

	public void setRevisedOn(Date revisedOn) {
		this.revisedOn = revisedOn;
	}


	public Date getReUpdatedOn() {
		return reUpdatedOn;
	}

	public void setReUpdatedOn(Date reUpdatedOn) {
		this.reUpdatedOn = reUpdatedOn;
	}

	

	/**
	 * @return the paymentBatchMoneySources
	 */
	// public List<PaymentBatchMoneySource> getPaymentBatchMoneySources() {
	// return paymentBatchMoneySources;
	// }

	/**
	 * @param paymentBatchMoneySources
	 *            the paymentBatchMoneySources to set
	 */
	// public void setPaymentBatchMoneySources(
	// List<PaymentBatchMoneySource> paymentBatchMoneySources) {
	// this.paymentBatchMoneySources = paymentBatchMoneySources;
	// }

	// public boolean isMoneySource() {
	// return moneySource;
	// }
	//
	// public void setMoneySource(boolean moneySource) {
	// this.moneySource = moneySource;
	// }

	/**
	 * @return the offsetType
	 */
	public String getOffsetType() {
		return offsetType;
	}

	/**
	 * @param offsetType
	 *            the offsetType to set
	 */
	public void setOffsetType(String offsetType) {
		this.offsetType = offsetType;
	}

	/**
	 * @return the addMore
	 */
	public boolean isAddMore() {
		return addMore;
	}

	/**
	 * @param addMore
	 *            the addMore to set
	 */
	public void setAddMore(boolean addMore) {
		this.addMore = addMore;
	}

	public Double getPosting() {
		return posting;
	}

	public void setPosting(Double posting) {
		this.posting = posting;
	}

	/**
	 * @return the manuallyPostedAmt
	 */
	public Double getManuallyPostedAmt() {
		return manuallyPostedAmt;
	}

	/**
	 * @param manuallyPostedAmt
	 *            the manuallyPostedAmt to set
	 */
	public void setManuallyPostedAmt(Double manuallyPostedAmt) {
		this.manuallyPostedAmt = manuallyPostedAmt;
	}

	/**
	 * @return the elecPostedAmt
	 */
	public Double getElecPostedAmt() {
		return elecPostedAmt;
	}

	/**
	 * @param elecPostedAmt
	 *            the elecPostedAmt to set
	 */
	public void setElecPostedAmt(Double elecPostedAmt) {
		this.elecPostedAmt = elecPostedAmt;
	}

	/**
	 * @return the datePosted
	 */
	public Date getDatePosted() {
		return datePosted;
	}

	/**
	 * @param datePosted
	 *            the datePosted to set
	 */
	public void setDatePosted(Date datePosted) {
		this.datePosted = datePosted;
	}

	/**
	 * @return the ctPostedTotal
	 */

	public MoneySource getMoneySource() {
		return moneySource;
	}

	public void setMoneySource(MoneySource moneySource) {
		this.moneySource = moneySource;
	}

	/**
	 * @return the creditCard
	 */
	public Double getCreditCard() {
		return creditCard;
	}

	/**
	 * @param creditCard
	 *            the creditCard to set
	 */
	public void setCreditCard(Double creditCard) {
		this.creditCard = creditCard;
	}

	/**
	 * @return the vault
	 */
	public Double getVault() {
		return vault;
	}

	/**
	 * @param vault
	 *            the vault to set : This field will use in cashlog report
	 */
	public void setVault(Double vault) {
		this.vault = vault;
	}

	/**
	 * @return the telecheck : This field will use in cashlog report
	 */
	public Double getTelecheck() {
		return telecheck;
	}

	/**
	 * @param telecheck
	 *            the telecheck to set : This field will use in cashlog report
	 */
	public void setTelecheck(Double telecheck) {
		this.telecheck = telecheck;
	}

	public Double getCtPostedTotal() {
		return getManuallyPostedAmt()+getElecPostedAmt();
	}

	/**
	 * @param ctPostedTotal
	 *            the ctPostedTotal to set
	 */
	public void setCtPostedTotal(Double ctPostedTotal) {
		this.ctPostedTotal = ctPostedTotal;
	}
}