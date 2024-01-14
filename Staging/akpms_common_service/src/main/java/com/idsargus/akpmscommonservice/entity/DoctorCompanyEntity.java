package com.idsargus.akpmscommonservice.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "doctor_company")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class DoctorCompanyEntity extends BaseAuditableEntity {

	@NotNull
	@Column(unique=true)
	private String name;

	@NotNull
	@Column(name = "enabled", columnDefinition = "boolean default true", nullable = false)
	private Boolean enabled = true;

//	@OneToMany(mappedBy="company", cascade={CascadeType.ALL})
//	private Set<DoctorGroupEntity> doctorgroups;
}
