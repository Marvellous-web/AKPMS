package com.idsargus.akpmsarservice.model.domain;

import com.idsargus.akpmscommonservice.entity.BaseAuditableEntity;
import com.idsargus.akpmscommonservice.entity.DoctorEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "qa_worksheet_doctor")
public class QAWorksheetDoctor extends BaseAuditableEntity {

    /**
     *
     */
    private static final long serialVersionUID = 2236128987870371938L;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,
            CascadeType.REFRESH})
    @JoinColumns(@JoinColumn(name = "qaworksheet_id", referencedColumnName = "id", unique = false, nullable = false))
    private QAWorksheet qaWorksheet;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,
            CascadeType.REFRESH})
    @JoinColumns(@JoinColumn(name = "doctor_id", referencedColumnName = "id", unique = false, nullable = false))
    private DoctorEntity doctor;

    @Column(name = "percentage_value")
    private Float percentageValue;

    @Column(length = 600, columnDefinition = "Text")
    private String remarks;

    @Transient
    private String doctorName;

    public String getDoctorName(){
        return (this.doctor == null) ? null : this.doctor.getName();
    }

}