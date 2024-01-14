package com.idsargus.akpmscommonservice.entity;

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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "department")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class DepartmentEntity extends BaseAuditableEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Integer id;

	private String name;

	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

	@NotNull
	@Column(name = "enabled", columnDefinition = "boolean default true", nullable = false)
	private Boolean enabled = true;

	@NotNull
	@Column(name = "deleted", columnDefinition = "boolean default false", nullable = false)
	private Boolean deleted = false;

	@OneToMany(targetEntity = DepartmentEntity.class, mappedBy = "parent", fetch = FetchType.LAZY)
	private List<DepartmentEntity> departments = null;

	//@JsonIgnore
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "parent_id", referencedColumnName = "id", unique = false, nullable = false) })
	private DepartmentEntity parent = null;

	@Transient
	private Integer parentId;

	@Override
	public String toString() {
		return super.getId().toString();
	}

	public Integer getParentId() {
		return (this.parent == null) ? null : this.parent.getId();
	}

}