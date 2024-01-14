package com.idsargus.akpmsarservice.model.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.idsargus.akpmscommonservice.entity.BaseAuditableEntity;
import com.idsargus.akpmscommonservice.entity.ChargeProductivity;
import com.idsargus.akpmscommonservice.entity.PaymentProductivity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "qa_productivity_sampling")
public class QAProductivitySampling extends ArBaseAuditableEntity {

    private static final long serialVersionUID = 1L;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH,
            CascadeType.DETACH})
    @JoinColumn(name = "qa_worksheet_id", nullable = true, referencedColumnName = "id")
    private QAWorksheet qaWorksheet;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH,
            CascadeType.DETACH})
    @JoinColumn(name = "payment_productivity_id", nullable = true, referencedColumnName = "id")
    private PaymentProductivity paymentProductivity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH,
            CascadeType.DETACH})
    @JoinColumn(name = "charge_productivity_id", nullable = true, unique = false, referencedColumnName = "id")
    private ChargeProductivity chargeProductivity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH,
            CascadeType.DETACH})
    @JoinColumn(name = "ar_productivity_id", nullable = true, unique = false, referencedColumnName = "id")
    private ArProductivity arProductivity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH,
            CascadeType.DETACH })
    @JoinColumn(name = "credentialing_accounting_productivity_id", nullable = true, unique = false, referencedColumnName = "id")
    private CredentialingAccountingProductivity credentialingAccountingProductivity;
@JsonManagedReference
    @OneToMany(mappedBy = "qaProductivitySampling", fetch = FetchType.LAZY, targetEntity = QAWorksheetPatientInfo.class, cascade = {
            CascadeType.DETACH, CascadeType.REFRESH })
    private List<QAWorksheetPatientInfo> qaWorksheetPatientInfos = new ArrayList<QAWorksheetPatientInfo>();
    @JsonManagedReference
    @OneToMany(mappedBy = "qaProductivitySampling",fetch = FetchType.LAZY, targetEntity = QCPointChecklist.class, cascade = {
            CascadeType.REFRESH, CascadeType.REMOVE }, orphanRemoval = true)
    private List<QCPointChecklist> qcPointChecklists = new ArrayList<QCPointChecklist>();

    @Column(name = "remarks", nullable = true, unique = false, columnDefinition = "TEXT", length = 400)
    private String remarks;

    @Column(name = "cpt_count")
    private String cptCount;

//    @Transient
//    private Float percentage;

}