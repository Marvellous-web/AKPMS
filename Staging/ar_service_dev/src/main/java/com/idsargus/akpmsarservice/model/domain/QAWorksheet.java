package com.idsargus.akpmsarservice.model.domain;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.idsargus.akpmscommonservice.entity.BaseAuditableEntity;

import com.idsargus.akpmscommonservice.entity.BaseIdEntity;
import com.idsargus.akpmscommonservice.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContextHolder;


@Getter
@Setter
@Entity
@Table(name="qa_worksheet")
public class QAWorksheet extends BaseIdEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH,
            CascadeType.REFRESH})
    @JoinColumns(@JoinColumn(name = "department_id", referencedColumnName = "id", nullable = false, unique = false))
    private Department department;

    @OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH,
            CascadeType.REFRESH})
    @JoinColumns({ @JoinColumn(name = "created_by", referencedColumnName = "id", insertable = true, updatable = true) })
    private UserEntity createdBy;

    //@JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH,
            CascadeType.REFRESH})
    @JoinColumns({ @JoinColumn(name = "modified_by", referencedColumnName = "id", insertable = true, updatable = true) })
    private UserEntity modifiedBy;
    @OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH,
            CascadeType.REFRESH})
    @JoinColumns(@JoinColumn(name = "sub_department_id", referencedColumnName = "id", nullable = true, unique = false))
    private Department subDepartment;

    /** QAWorksheet status 0 saved ,1 run, 2 compleate*/
    @Column(name = "status")
    private Integer status = 0;

    @Column(name = "general_percentage")
    private Float generalPercentage;

    @Column(name = "account_percentage")
    private Float accountPercentage;

    @Column(name = "billing_month")
    private Integer billingMonth;

    @Column(name = "billing_year")
    private Integer billingYear;

    @Column(name = "payment_productivity_type")
    private Integer paymentProductivityType;

    @Column(name = "posting_date_from")
    private Date postingDateFrom;

    @Column(name = "posting_date_to")
    private Date postingDateTo;

    @Column(name = "scan_date_from")
    private Date scanDateFrom;

    @Column(name = "scan_date_to")
    private Date scanDateTo;

    @Column(name = "name")
    private String name;

	// Field added  for Genrating QA report (QC Point Filtered by Created user Id)
	//@ManyToOne(fetch=FetchType.LAZY)
    //@JoinColumns({ @JoinColumn(name = "created_by", referencedColumnName = "id", insertable = false, updatable = false) })
//    @Column(name = "created_by", insertable = false, updatable = false)
//    private Long createdByTest;

    @NotNull
    @Column(name = "deleted", columnDefinition = "TINYINT(1) DEFAULT '0'")
    private Boolean deleted = false;

//    @OneToMany(targetEntity = QAWorksheetStaff.class ,mappedBy = "qaWorksheet",fetch=FetchType.EAGER)
//    private List<QAWorksheetStaff> qaWorksheetStaffs = new ArrayList<QAWorksheetStaff>();

  //  private Short type = Constants.QA_WORKSEET_TYPE_GENERAL;

    // @Null
    // @Column(name = "multi_dept")
    // private String multiDept;

    @Column(name= "type")
    private String type;

    @Column(name="ar_status_code")
    private String arStatusCode;

    @Transient
    private String departmentName;
    public String getDepartmentName() {
        return (this.department == null) ? null : this.department.getName();
    }
    @Column(name = "created_on", insertable = true, updatable = false)
//	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdOn;

    @javax.persistence.Temporal(TemporalType.DATE)
    @Column(name = "modified_on", insertable = false, updatable = true)
    private Date modifiedOn;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createdOn = Timestamp.valueOf(now);

//		CustomPrincipal principal = (CustomPrincipal) SecurityContextHolder.getContext().getAuthentication()
//				.getPrincipal();
////		createdBy = principal.getEmail();

        System.out.println("--------------------------"+(String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//		createdBy.setId(principal.getId());
//		createdBy = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @PreUpdate
    public void preUpdate() {
        LocalDateTime now = LocalDateTime.now();
        modifiedOn = Timestamp.valueOf(now);

//		CustomPrincipal principal = (CustomPrincipal) SecurityContextHolder.getContext().getAuthentication()
//				.getPrincipal();
//		modifiedBy = principal.getEmail();

//		CustomPrincipal str = (CustomPrincipal) SecurityContextHolder.getContext().getAuthentication().getDetails();
//		modifiedBy = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
