package com.idsargus.akpmsadminservice.util;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.idsargus.akpmscommonservice.entity.BaseAuditableEntity;
import com.idsargus.akpmscommonservice.entity.DoctorCompanyEntity;
import com.idsargus.akpmscommonservice.entity.DoctorGroupEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "doctor")
public class DoctorEntity extends BaseAuditableEntity {

	private static final long serialVersionUID = 1L;

	private String name;

	@Column(name = "code")
	private String code;

	@Column(name = "payments")
	private Float payments = 0F;

	@Column(name = "operations")
	private Float operations = 0F;

	@Column(name = "accounting")
	private Float accounting = 0F;

	@NotNull
	@Column(name = "non_deposit", columnDefinition = "BIT default 1", nullable = false)
	private Boolean nonDeposit;

	@NotNull
	@Column(name = "enabled", columnDefinition = "boolean default true", nullable = false)
	private Boolean enabled = true;

	@NotNull
	@Column(name = "deleted", columnDefinition = "boolean default false", nullable = false)
	private Boolean deleted = false;

	@OneToMany(targetEntity = DoctorEntity.class, mappedBy = "parent", fetch = FetchType.LAZY)
	private List<DoctorEntity> doctors = null;

	@JsonIgnore
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "parent_id", referencedColumnName = "id", unique = false, nullable = true) })
	private DoctorEntity parent = null;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="company_id",referencedColumnName = "id")
	private DoctorCompanyEntity company;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="group_id",referencedColumnName = "id")
	private DoctorGroupEntity group;

	@Transient
	private Integer parentId;

	public Integer getParentId() {
		return (this.parent == null) ? null : this.parent.id;
	}
}
