package com.idsargus.akpmsarservice.model.domain;
import java.time.temporal.TemporalAccessor;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.idsargus.akpmsarservice.model.BaseEntity;
import com.idsargus.akpmscommonservice.entity.BaseAuditableEntity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Data
@Entity
@Table(name = "doctor")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Doctor extends BaseAuditableEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	// @Pattern(regexp = "[A-Za-z ]*", message =
	// "must contain only letters and spaces")
	@NotNull
	private String name;

	private String doctorCode = null;

	@NotNull
	@Column(name = "non_deposit", columnDefinition = "TINYINT(1) DEFAULT '0'")
	private boolean nonDeposit = false;
	
	@Column(name = "payments")
	private Float payments = 0F;

	@Column(name = "operations")
	private Float operations = 0F;

	@Column(name = "accounting")
	private Float accounting = 0F;

//	@XStreamOmitField
	@OneToMany(targetEntity = Doctor.class, mappedBy = "parent", fetch = FetchType.LAZY)
	private List<Doctor> doctors = null;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "parent_id", referencedColumnName = "id", unique = false, nullable = true) })
	private Doctor parent = null;

	@NotNull
	@Column(name = "status", columnDefinition = "TINYINT(1) DEFAULT '1'")
	private boolean status = true;

	@NotNull
	@Column(name = "deleted", columnDefinition = "TINYINT(1) DEFAULT '0'")
	private boolean deleted = false;

	@Transient
	private long parentId;

	@Transient
	private long childCount;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDoctorCode() {
		return doctorCode;
	}

	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Doctor getParent() {
		return parent;
	}

	public void setParent(Doctor parent) {
		this.parent = parent;
	}

	public String toString() {
		return "" + this.getId();
	}

	public List<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public long getChildCount() {
		return childCount;
	}

	public void setChildCount(long childCount) {
		this.childCount = childCount;
	}

	public Float getPayments() {
		return payments;
		
	}

	public void setPayments(Float payments) {
		this.payments = payments;
	}

	public Float getAccounting() {
		return accounting;
	}

	public void setAccounting(Float accounting) {
		this.accounting = accounting;
	}

	public Float getOperations() {
		return operations;
	}

	public void setOperations(Float operations) {
		this.operations = operations;
	}

	public boolean isNonDeposit() {
		return nonDeposit;
	}

	public void setNonDeposit(boolean nonDeposit) {
		this.nonDeposit = nonDeposit;
	}

	}