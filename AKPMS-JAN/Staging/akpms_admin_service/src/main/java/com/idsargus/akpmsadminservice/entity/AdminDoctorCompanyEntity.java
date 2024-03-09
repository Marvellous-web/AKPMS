package com.idsargus.akpmsadminservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.idsargus.akpmscommonservice.entity.DoctorGroupEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "doctor_company")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class AdminDoctorCompanyEntity extends AdminBaseAuditableEntity {

	@NotNull
	@Column(unique=true)
	private String name;

	@NotNull
	@Column(name = "enabled", columnDefinition = "boolean default true", nullable = false)
	private Boolean enabled = true;

//	@OneToMany(mappedBy="company", cascade={CascadeType.ALL})
//	private Set<DoctorGroupEntity> doctorgroups;
}
