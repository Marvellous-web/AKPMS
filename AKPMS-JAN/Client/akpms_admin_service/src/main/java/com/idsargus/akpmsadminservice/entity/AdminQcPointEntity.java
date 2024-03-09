package com.idsargus.akpmsadminservice.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "qc_point")
public class AdminQcPointEntity extends AdminBaseAuditableEntity {

	private static final long serialVersionUID = 1L;

	private String name;

	@OneToMany(targetEntity = AdminQcPointEntity.class, mappedBy = "parent", fetch = FetchType.LAZY)
	private List<AdminQcPointEntity> qcPoints = null;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "parent_id", referencedColumnName = "id", unique = false, nullable = true) })
	private AdminQcPointEntity parent = null;

	@OneToOne
	@JoinColumns(@JoinColumn(name = "department_id", referencedColumnName = "id", nullable = false, unique = false))
	private AdminDepartmentEntity department;

	@OneToOne
	@JoinColumns(@JoinColumn(name = "sub_department_id", referencedColumnName = "id", nullable = true, unique = false))
	private AdminDepartmentEntity  subDepartment;

	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

	@NotNull
	@Column(name = "enabled", columnDefinition = "boolean default true", nullable = false)
	private Boolean enabled = true;

	@NotNull
	@Column(name = "deleted", columnDefinition = "boolean default false", nullable = false)
	private Boolean deleted = false;

	@Transient
	private Integer parentId;

	@Transient
	private Integer departmentId;

	@Transient
	private Integer subDepartmentId;

	public Integer getParentId() {
		return (this.parent == null) ? null : this.parent.getId();
	}

	public Integer getDepartmentId() {
		return (this.department == null) ? null : this.department.getId();
	}

	public Integer getSubDepartmentId() {
		return (this.subDepartment == null) ? null : this.subDepartment.getId();
	}
}
