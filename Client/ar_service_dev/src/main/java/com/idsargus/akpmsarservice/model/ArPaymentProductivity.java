package com.idsargus.akpmsarservice.model;

import com.idsargus.akpmscommonservice.entity.BaseAuditableEntity;
import com.idsargus.akpmscommonservice.entity.PaymentBatch;
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
public class ArPaymentProductivity extends BaseAuditableEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    @Column(name = "payment_productivity_type")
    private Integer paymentProdType;

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "batch_id", referencedColumnName = "id")})
    private PaymentBatch paymentBatch;

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "query_to_tl_id", referencedColumnName = "id")})
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
}