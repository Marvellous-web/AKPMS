package com.idsargus.akpmsarservice.model.domain;

import com.idsargus.akpmscommonservice.entity.BaseAuditableEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "qa_worksheet_patient_info")
public class QAWorksheetPatientInfo extends BaseAuditableEntity {

    private static final long serialVersionUID = -7370823622555907742L;


    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH,
            CascadeType.REFRESH })
    @JoinColumn(name = "qa_productivity_sampling_id", nullable = false, unique = false)
    private QAProductivitySampling qaProductivitySampling;


//    @OneToMany(mappedBy = "qaWorksheetPatientInfo", fetch = FetchType.LAZY, cascade = {
//            CascadeType.REMOVE, CascadeType.REFRESH }, orphanRemoval = true)
//    private List<QCPointChecklist> qcPointChecklist = new ArrayList<QCPointChecklist>();

    @OneToMany(mappedBy = "qaWorksheetPatientInfo", fetch = FetchType.LAZY, cascade = {
            CascadeType.REMOVE, CascadeType.REFRESH }, orphanRemoval = true)
    private List<QCPointChecklist> qcPointChecklist = new ArrayList<QCPointChecklist>();
    @Column(name = "cpt_codes_demo", nullable = true, unique = false)
    private String cptCodesDemo;

    @Column(name = "remarks", nullable = true, unique = false, columnDefinition="TEXT", length=400)
    private String remarks;

    @Column(name = "patient_name", nullable = true)
    private String patientName;

    @Column(name = "account_number", nullable = true)
    private String accountNumber;

    @Column(name = "sr_no", nullable = true)
    private String srNo;

    @Column(name = "cheque", nullable = true)
    private String check;

    @Column(name = "transaction", nullable = true)
    private String transaction;



}

