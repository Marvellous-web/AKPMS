package com.idsargus.akpmsarservice.model.domain;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import com.idsargus.akpmscommonservice.entity.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "ar_productivity")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class ArProductivity extends BaseAuditableEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "patient_account_number")
    private String patientAccountNumber;

    @Column(name = "patient_name")
    private String patientName;

    private String dos;

    @Column(name = "dos_from")
    private String dosFrom;

    @Column(name = "dos_to")
    private String dosTo;


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

//    @ManyToOne
//    @JoinColumn(name = "provider_id", referencedColumnName = "id", unique = false, nullable = true)
//    private DoctorEntity doctor;


    @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "provider_id", referencedColumnName = "id", unique = false, nullable = true)
        private DoctorEntity doctor;

    //@ManyToOne()
    //@JoinColumns({ @JoinColumn(name = "insurance_id", referencedColumnName = "id", unique = false, nullable = true) })
    //private Insurance insurance ;


    //@ManyToOne()
    //@JoinColumns({ @JoinColumn(name = "provider_id", referencedColumnName = "id", unique = false, nullable = true) })
    //private Doctor doctor;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private Department team = null;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "ar_database_id", referencedColumnName = "id", unique = false, nullable = true)
//    private ArDatabaseEntity database;

      @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ar_database_id", referencedColumnName = "id", unique = false, nullable = true)
    private DoctorCompanyEntity database;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="company_id", referencedColumnName = "id")
//    private DoctorCompanyEntity company;

    // private String dataBas;
	/*@ManyToOne
	@JoinColumn(
			name = "ar_database_id",
			referencedColumnName = "id",
			unique = false,
			nullable = true
	)
	private ArDatabase arDatabase; */


    @Column(name = "follow_up_date")
    private Date followUpDate;

    //@JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "ar_productivity_workflow_rel", joinColumns = {
            @JoinColumn(name = "ar_productivity_id")}, inverseJoinColumns = {
            @JoinColumn(name = "ar_productivity_workflow_id")})
    private Set<ArProductivityWorkFlowEntity> arWorkflows;


//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="company_id", referencedColumnName = "id")
//    private DoctorCompanyEntity company;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="group_id", referencedColumnName = "id")
    private DoctorGroupEntity group;

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
    private Integer groupId;

    @Transient
    private Integer companyId;
    @Transient
    private Integer databaseId;

    @Transient
    private Integer teamId;

    @Transient
    private List<Integer> workflowIds;

    ///Addede new cr******************from
    @Transient
    private String companyName ;
    public String getCompanyName() {
        return (this.database == null) ? null : this.database.getName();
    }


    public Integer getCompanyId() {
        return (this.database == null) ? null : this.database.getId();
    }
    @Transient
    private String groupName ;
    public String getGroupName() {
        return (this.group == null) ? null : this.group.getName();
    }
    public Integer getGroupId() {
        return (this.group == null) ? null : this.group.getId();
    }

    @Transient
    private String doctorName ;
    public String getDoctorName() {
        return (this.doctor == null) ? null : this.doctor.getName();

    }
    ////to*******************************
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

    @Transient
    private String teamName;
    public String getTeamName() {
        return (this.team == null) ? null : this.team.getName();
    }
    @Transient
    private String createdByName;
    public String getCreatedByName(){
        return (this.getCreatedBy() == null) ? null : this.getCreatedBy().getFirstName()+ " "+ this.getCreatedBy().getLastName();
    }
    @Transient
    private String modifiedByName;
    public String getModifiedByName(){
        return (this.getModifiedBy() == null) ? null : this.getModifiedBy().getFirstName()+ " "+ this.getModifiedBy().getLastName();
    }


    public List<Integer> getWorkflowIds() {
        return (this.arWorkflows == null) ? null
                : this.arWorkflows.stream().map(ArProductivityWorkFlowEntity::getId).collect(Collectors.toList());
    }
}