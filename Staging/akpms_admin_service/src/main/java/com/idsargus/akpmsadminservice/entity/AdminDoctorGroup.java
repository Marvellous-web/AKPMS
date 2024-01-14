package com.idsargus.akpmsadminservice.entity;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
@Table(name = "doctor_group")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class AdminDoctorGroup extends AdminBaseAuditableEntity {

    @NotNull
    @Column(unique=true)
    private String name;

    //@NotNull
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "doctor_company_id", referencedColumnName = "id")
    private AdminDoctorCompanyEntity company;

    @NotNull
    @Column(name = "enabled", columnDefinition = "boolean default true", nullable = false)
    private Boolean enabled = true;

}