package com.idsargus.akpmscommonservice.entity;

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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "doctor")
//@JsonIgnore
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class DoctorEntity extends BaseAuditableEntity {

	//private static final long serialVersionUID = 1L;

	private String name;

	@Column(name = "doctorCode")
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
	@Column(name = "status", columnDefinition = "boolean default true", nullable = false)
	private Boolean enabled = true;

	@NotNull
	@Column(name = "deleted", columnDefinition = "boolean default false", nullable = false)
	private Boolean deleted;

	
	@OneToMany( mappedBy = "parent", fetch = FetchType.LAZY)
	private List<DoctorEntity> doctors = null;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "parent_id", referencedColumnName = "id", unique = false, nullable = true) })
	private DoctorEntity parent = null;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="company_id",referencedColumnName = "id")
	private DoctorCompanyEntity company;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="group_id",referencedColumnName = "id")
	private DoctorGroupEntity group;


	@Transient
	private String groupName;

	public String getGroupName() {
		return (this.group == null) ? null : this.group.getName();

	}

	@Transient
	private Integer parentId;
	@Transient
	private String companyName;

	public String getCompanyName() {
       return (this.company == null) ? null : this.company.getName();
	}

	@Transient
	private Integer companyId;

	public Integer getCompanyId() {
		return (this.company == null) ? null : this.company.getId();
	}

	@Transient
	private Integer groupId;

	public Integer getGroupId() {
		return (this.group == null) ? null : this.group.getId();
	}

	public Integer getParentId() {
		return (this.parent == null) ? null : this.parent.id;
	}
}
