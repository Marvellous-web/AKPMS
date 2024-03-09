package com.idsargus.akpmsarservice.model.domain;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.idsargus.akpmscommonservice.entity.BaseAuditableEntity;
import com.idsargus.akpmscommonservice.entity.UserEntity;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "process_manual")
//@EntityListeners(EntityListener.class)
public class ProcessManual extends ArBaseAuditableEntity{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String title;

	@GeneratedValue(
			strategy = GenerationType.SEQUENCE
	)
	private Double position = 1D;
	
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "parent_id", referencedColumnName = "id", unique = false, nullable = true) })
	private ProcessManual parent;

	@OneToMany(cascade=CascadeType.ALL,targetEntity = ProcessManual.class, mappedBy = "parent", fetch = FetchType.LAZY)
	private List<ProcessManual> processManualList;

	@Column(columnDefinition = "LONGTEXT")
	private String content;

	@Column(name = "modification_summary", columnDefinition = "TEXT")
	private String modificationSummary;

	@Column(name = "created_by" ,insertable = false, updatable = false)
	private Integer createdByName;

	@Column(name = "modified_by", insertable = false, updatable = false)
	private Integer modifiedByName;

	@Column(name = "created_on", insertable = false, updatable = false)
	private Date createdOnName;
	@Column(name = "modified_on", insertable = false, updatable = false)
	private Date modifiedOnDate;


	@NotNull
	@Column(name = "notification", columnDefinition = "TINYINT(1) DEFAULT '1'")
	private boolean notification = true;

	@NotNull
	@Column(name = "status")
	private boolean status;

	@NotNull
	@Column(name = "is_deleted", columnDefinition = "TINYINT(1) DEFAULT '0'")
	private boolean deleted = false;

	@OneToMany(cascade = CascadeType.ALL, mappedBy= "processManual", targetEntity = ArFiles.class)
//	@JoinTable(name = "process_manual_files_rel", joinColumns = { @JoinColumn(name = "process_manual_id") }, inverseJoinColumns = { @JoinColumn(name = "files_id") })
	private List<ArFiles> files;

	// for joing the tables (many-to-many)
	@ManyToMany(cascade = { CascadeType.MERGE}, fetch = FetchType.EAGER)
	@JoinTable(name = "process_manual_department_rel", joinColumns = { @JoinColumn(name = "process_manual_id") }, inverseJoinColumns = { @JoinColumn(name = "department_id") })
	private List<Department> departments;

	@ManyToMany(cascade = {CascadeType.DETACH, CascadeType.REFRESH,CascadeType.MERGE})
	@JoinTable(name="process_manual_user_rel", joinColumns={@JoinColumn(name="process_manual_id")}, inverseJoinColumns={@JoinColumn(name="user_id")})
	private List<UserEntity> userList;

	@OneToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
	@JoinColumn(name="id",referencedColumnName = "section_id",insertable = false, updatable = false)
	private ProcessManualPosition processManualPosition;

	@Transient
	private boolean readAndUnderstood;

	@Transient
	private boolean showReadAndUnderstood;




}