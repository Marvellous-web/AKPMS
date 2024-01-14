package com.idsargus.akpmscommonservice.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.TemporalType;

import org.springframework.data.jpa.repository.Temporal;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.idsargus.akpmscommonservice.model.CustomPrincipal;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
//@Getter(AccessLevel.PROTECTED)
//@Setter(AccessLevel.PROTECTED)
@Getter
@Setter
public abstract class BaseAuditableEntity extends BaseIdEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

		
	//@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "created_by", referencedColumnName = "id", insertable = true, updatable = false) })
	private UserEntity createdBy;

	//@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "modified_by", referencedColumnName = "id", insertable = false, updatable = true) })
	private UserEntity modifiedBy;

//	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumns({ @JoinColumn(name = "created_by", referencedColumnName = "id", insertable = true, updatable = true) })
//	private UserEntity createdBy;

//@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumns({ @JoinColumn(name = "modified_by", referencedColumnName = "id", insertable = true, updatable = true) })
//	private UserEntity modifiedBy;
	@Column(name = "created_on", insertable = true, updatable = false)
//	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createdOn;

	//@javax.persistence.Temporal(TemporalType.DATE)
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
