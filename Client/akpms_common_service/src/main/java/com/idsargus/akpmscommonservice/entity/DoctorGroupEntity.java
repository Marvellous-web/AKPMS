package com.idsargus.akpmscommonservice.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "doctor_group")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class DoctorGroupEntity extends BaseAuditableEntity {

	@NotNull
	@Column(unique=true)
	private String name;

	//@NotNull
	@ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "doctor_company_id", referencedColumnName = "id")
	private DoctorCompanyEntity company;

	@NotNull
	@Column(name = "enabled", columnDefinition = "boolean default true", nullable = false)
	private Boolean enabled = true;

}
