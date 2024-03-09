package com.idsargus.akpmsadminservice.entity;


import com.idsargus.akpmscommonservice.entity.BaseIdEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@MappedSuperclass
//@Getter(AccessLevel.PROTECTED)
//@Setter(AccessLevel.PROTECTED)
@Getter
@Setter
public abstract class AdminBaseAuditableEntity extends BaseIdEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    //@JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "created_by", referencedColumnName = "id", insertable = true, updatable = true) })
    private User createdBy;

    //@JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "modified_by", referencedColumnName = "id", insertable = true, updatable = true) })
    private User modifiedBy;

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
