package com.idsargus.akpmsadminservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "department")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class AdminDepartmentEntity extends AdminBaseAuditableEntity {

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

	@OneToMany(targetEntity = AdminDepartmentEntity.class, mappedBy = "parent", fetch = FetchType.LAZY)
	private List<AdminDepartmentEntity> departments = null;

	//@JsonIgnore
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "parent_id", referencedColumnName = "id", unique = false, nullable = false) })
	private AdminDepartmentEntity parent = null;

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