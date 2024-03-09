package com.idsargus.akpmsarservice.model.domain;

import com.idsargus.akpmscommonservice.entity.BaseAuditableEntity;
import com.idsargus.akpmscommonservice.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "qc_point_checklist")
public class QCPointChecklist extends BaseAuditableEntity {

    // @Column(name = "check_point")
    // private Short checkPoint;
    private static final long serialVersionUID = 1;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,
            CascadeType.REFRESH})
    @JoinColumn(name = "qc_point_id", nullable = false, unique = false)
    private QcPoint qcPoint;

    // @ManyToOne(fetch = FetchType.LAZY, targetEntity = QCPointChecklist.class,
    // cascade = {CascadeType.REMOVE, CascadeType.REFRESH })
    @ManyToOne(cascade = {CascadeType.REMOVE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "qa_worksheet_patient_info_id")
    private QAWorksheetPatientInfo qaWorksheetPatientInfo;

    @ManyToOne(cascade = {CascadeType.REFRESH}, targetEntity = QAProductivitySampling.class)
    @JoinColumn(name = "qa_productivity_sampling_id", referencedColumnName = "id", nullable = true)
    private QAProductivitySampling qaProductivitySampling;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumns({@JoinColumn(name = "user_id", referencedColumnName = "id", unique = false, nullable = true)})
    private UserEntity user;

}
