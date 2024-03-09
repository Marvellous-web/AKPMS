package com.idsargus.akpmscommonservice.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "qc_point")
public class QcPointEntity extends BaseAuditableEntity {

	private static final long serialVersionUID = 1L;

	private String name;

	@OneToMany(targetEntity = QcPointEntity.class, mappedBy = "parent", fetch = FetchType.LAZY)
	private List<QcPointEntity> qcPoints = null;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "parent_id", referencedColumnName = "id", unique = false, nullable = true) })
	private QcPointEntity parent = null;

	@OneToOne
	@JoinColumns(@JoinColumn(name = "department_id", referencedColumnName = "id", nullable = false, unique = false))
	private DepartmentEntity department;

	@OneToOne
	@JoinColumns(@JoinColumn(name = "sub_department_id", referencedColumnName = "id", nullable = true, unique = false))
	private DepartmentEntity subDepartment;

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
		return (this.parent == null) ? null : this.parent.id;
	}

	public Integer getDepartmentId() {
		return (this.department == null) ? null : this.department.getId();
	}

	public Integer getSubDepartmentId() {
		return (this.subDepartment == null) ? null : this.subDepartment.getId();
	}
}
