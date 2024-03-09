package com.idsargus.akpmscommonservice.entity;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "ar_productivity")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class ArProductivityEntity extends BaseAuditableEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "patient_account_number")
	private String patientAccountNumber;

	@Column(name = "patient_name")
	private String patientName;

	private String dos;

	private String cpt;

	@Column(name = "balance_amount")
	private Float balanceAmount;
	
	//added workFlow by ARN
//	@Column(name="work_flow")
//	private Integer workFlow;

	private String source;

	@Column(name = "status_code")
	private String statusCode;

	@Column(name = "tl_remark", columnDefinition = "TEXT")
	private String tlRemark;

	@Column(name = "remark", columnDefinition = "TEXT")
	private String remark;

	@NotNull
	@Column(name = "timily_filing", columnDefinition = "boolean default false", nullable = false)
	private Boolean timilyFiling = false;

	@Column(name = "sub_status")
	private Integer subStatus;

//	@Column(name = "productivity_type")
//	private Integer productivityType;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "insurance_id", referencedColumnName = "id", unique = false, nullable = true)
	private InsuranceEntity insurance;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "provider_id", referencedColumnName = "id", unique = false, nullable = true)
	private DoctorEntity doctor;
	

//	@OneToOne
//	@JoinColumn(name = "team_id")
//	private DepartmentEntity team;
	//@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", referencedColumnName = "id", unique = false, nullable = true)
	private DepartmentEntity team = null;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ar_database_id", referencedColumnName = "id", unique = false, nullable = true)
	private ArDatabaseEntity database;


	@Column(name = "follow_up_date")
	private Date followUpDate;

	//@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "ar_productivity_workflow_rel", joinColumns = {
			@JoinColumn(name = "ar_productivity_id") }, inverseJoinColumns = {
					@JoinColumn(name = "ar_productivity_workflow_id") })
	private Set<ArProductivityWorkFlowEntity> arWorkflows;

//	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumns({ @JoinColumn(name = "created_by", referencedColumnName = "id", insertable = true, updatable = false) })
//	private UserEntity createdBy;
//
//	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumns({ @JoinColumn(name = "modified_by", referencedColumnName = "id", insertable = false, updatable = true) })
//	private UserEntity modifiedBy;

	@Transient
	private Integer insuranceId;

	@Transient
	private Integer doctorId;

	@Transient
	private Integer databaseId;

	@Transient
	private Integer teamId;

	@Transient
	private List<Integer> workflowIds;

	public Integer getInsuranceId() {
		return (this.insurance == null) ? null : this.insurance.getId();
	}

	public Integer getDoctorId() {
		return (this.doctor == null) ? null : this.doctor.getId();
	}

	public Integer getDatabaseId() {
		return (this.database == null) ? null : this.database.getId();
	}

	public Integer getTeamId() {
		return (this.team == null) ? null : this.team.getId();
	}
	
	public List<Integer> getWorkflowIds() {
		return (this.arWorkflows == null) ? null
				: this.arWorkflows.stream().map(ArProductivityWorkFlowEntity::getId).collect(Collectors.toList());
	}

}
