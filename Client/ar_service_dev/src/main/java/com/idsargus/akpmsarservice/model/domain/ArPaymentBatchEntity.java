package com.idsargus.akpmsarservice.model.domain;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.idsargus.akpmscommonservice.entity.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Data
@Table(name="payment_batch")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
//@TableGenerator(name = "tablePaymentBatch", initialValue = 1000000,allocationSize=1)
public class ArPaymentBatchEntity extends ArBaseAuditableEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    @Column(name = "billing_month")
    private Long billingMonth;

    @Column(name = "billing_year")
    private Long billingYear;

    @ManyToOne()
    @JoinColumns({@JoinColumn(name = "doctor_id", referencedColumnName = "id")})
    private DoctorEntity doctor = null;

    @ManyToOne()
    @JoinColumns({@JoinColumn(name = "pro_health_doctor_id", referencedColumnName = "id")})
    private DoctorEntity phDoctor = null;

    @ManyToOne()
    @JoinColumns({@JoinColumn(name = "payment_type_id", referencedColumnName = "id")})
    private PaymentType paymentType = null;

    @ManyToOne()
    @JoinColumns({@JoinColumn(name = "money_source_id", referencedColumnName = "id", unique = false, nullable = false)})
    private MoneySource moneySource = null;

    @ManyToOne()
    @JoinColumns({@JoinColumn(name = "revenue_type_id", referencedColumnName = "id")})
    private RevenueType revenueType = null;

    @ManyToOne()
    @JoinColumns({@JoinColumn(name = "insurance_id", referencedColumnName = "id")})
    private InsuranceEntity insurance = null;

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

    @Column(name = "era_check_no")
    private String eraCheckNo;

    @Column(columnDefinition = "TEXT")
    private String comment;

    // user will fill when he will create payment batch (deposit amt or ndba)
    @Column(name = "deposit_amt")
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
    @Column(name = "refund_chk")
    private Double refundChk = 0.0;

    // will be used on payment batch add
    @Column(name = "nsf_sys_ref")
    private Double nsfSysRef = 0.0;

    // will be used on payment batch add
    @Column(name = "deposit_date")
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
    @Column(name = "suspense_account")
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
    @JoinColumns({@JoinColumn(name = "posted_by_id", referencedColumnName = "id", unique = false, nullable = false)})
    private UserEntity postedBy = null;

    // will be used at payment prod (Auto filled current date),
    // payment batch update (current date)
    @Column(name = "posted_on")
    private Date postedOn = null;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumns({@JoinColumn(name = "revised_by_id", referencedColumnName = "id", unique = false, nullable = false)})
    private UserEntity revisedBy = null;

    @Column(name = "revised_on")
    private Date revisedOn;

    @ManyToOne()
    @JoinColumns({@JoinColumn(name = "reupdated_by_id", referencedColumnName = "id")})
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
    private String createdByPB;

    @Transient
    private String modifiedByPB;

    @Transient
    private String revisedByPB;

    public String getCreatedByPB(){
        return (this.getCreatedBy() == null) ? null : this.getCreatedBy().getFirstName()+ " "+this.getCreatedBy().getLastName();
    }

    public String getModifiedByPB(){
        return (this.getModifiedBy() == null) ? null : this.getModifiedBy().getFirstName()+ " "+this.getModifiedBy().getLastName();
    }

    public String getRevisedByPB(){
        return (this.getRevisedBy() == null) ? null : this.getRevisedBy().getFirstName()+ " "+this.getRevisedBy().getLastName();
    }
}
