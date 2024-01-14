package com.idsargus.akpmsauthservice.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "department")
@Getter
@Setter
public class DepartmentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;

	private String name;

	@OneToMany(targetEntity = DepartmentEntity.class, mappedBy = "parent", fetch = FetchType.LAZY)
	private List<DepartmentEntity> departments = null;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "parent_id", referencedColumnName = "id", unique = false, nullable = true) })
	private DepartmentEntity parent = null;

	@Column(name = "description", columnDefinition = "TEXT")
	private String desc;

	@NotNull
	@Column(name = "enabled", columnDefinition = "boolean default true", nullable = false)
	private Boolean enabled = true;

	@NotNull
	@Column(name = "deleted", columnDefinition = "boolean default false", nullable = false)
	private Boolean deleted = false;

	@Transient
	private Long parentId;

	@Transient
	private long childCount;

	@Override
	public String toString() {
		return this.id.toString();
	}

}