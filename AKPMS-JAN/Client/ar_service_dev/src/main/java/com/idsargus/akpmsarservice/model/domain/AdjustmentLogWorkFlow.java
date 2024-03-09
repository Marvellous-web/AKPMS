package com.idsargus.akpmsarservice.model.domain;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.idsargus.akpmsarservice.model.BaseEntity;
//import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.idsargus.akpmscommonservice.entity.*;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//@XStreamAlias("ARLogWorkFlow")
@Getter
@Setter
@Entity
@Table(name = "adjustment_log_workflow")
//@EntityListeners(EntityListener.class)
public class AdjustmentLogWorkFlow extends BaseAuditableEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@OneToOne(fetch =FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.MERGE,
			CascadeType.REFRESH, CascadeType.REMOVE })
	@JoinColumns({ @JoinColumn(name = "ar_productivity_id", referencedColumnName = "id", unique = false, nullable = false) })
	private ArProductivityEntity arProductivity;

	/************* add fields from productivity (START) ************/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "insurance_id", referencedColumnName = "id", unique = false, nullable = false) })
	private InsuranceEntity insurance = null;

	// @XStreamConverter(value = XstreamDateConverter.class)
	private String dos;

	@Transient
	private String createdByName;
	@Column(name = "cpt", length = 100)
	private String cptcode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "provider_id",
			referencedColumnName = "id", unique = false, nullable = false) })
	private DoctorEntity doctor = null;

	//added new cr from
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="company_id", referencedColumnName = "id")
	private DoctorCompanyEntity company;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="group_id", referencedColumnName = "id")
	private DoctorGroupEntity group;

	//------to-----

	private Float balanceAmt;
	/************* add fields from productivity (END) ************/

	/* This field not in use*/
	@Column(name = "without_timily_filing")
	private Integer withoutTimilyFiling; //Not in use

	@Column(name = "remark", columnDefinition = "TEXT")
	private String remark;

	@Column(name = "manager_remark", columnDefinition = "TEXT")
	private String managerRemark;

	@Column(name = "work_flow_status")
	private Integer workFlowStatus;

	@NotNull
	@Column(name = "deleted", columnDefinition = "TINYINT(1) DEFAULT '0'")
	private boolean deleted = false;

	@Transient
	private Integer doctorId;
	public Integer getDoctorId() {
		return (this.doctor == null) ? null : this.doctor.getId();
	}
	@Transient
	private Integer groupId;
	public Integer getGroupId() {
		return (this.group == null) ? null : this.group.getId();
	}
	@Transient
	private Integer companyId;
	public Integer getCompanyId() {
		return (this.company == null) ? null : this.company.getId();
	}
	public ArProductivityEntity getArProductivity() {
		return arProductivity;
	}

	///Addede new cr******************from
	@Transient
	private String companyName ;
	public String getCompanyName() {
		return (this.company == null) ? null : this.company.getName();
	}


	@Transient
	private String groupName ;
	public String getGroupName() {
		return (this.group == null) ? null : this.group.getName();
	}

	@Transient
	private String doctorName;
	public String getCreatedByName(){
		return (this.getCreatedBy() == null) ? null : this.getCreatedBy().getFirstName() + " "+ this.getCreatedBy().getFirstName();

	}
	public String getDoctorName(){
		return (this.doctor == null) ? null : this.doctor.getName();

	}
	public void setArProductivity(ArProductivityEntity arProductivity) {
		this.arProductivity = arProductivity;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getWorkFlowStatus() {
		return workFlowStatus;
	}

	public void setWorkFlowStatus(Integer workFlowStatus) {
		this.workFlowStatus = workFlowStatus;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 * @return the insurance
	 */
	public InsuranceEntity getInsurance() {
		return insurance;
	}

	/**
	 * @param insurance
	 *            the insurance to set
	 */
	public void setInsurance(InsuranceEntity insurance) {
		this.insurance = insurance;
	}

	/**
	 * @return the dos
	 */
	public String getDos() {
		return dos;
	}

	/**
	 * @param dos
	 *            the dos to set
	 */
	public void setDos(String dos) {
		this.dos = dos;
	}

	/**
	 * @return the cpt
	 */
	public String getCptcode() {
		return cptcode;
	}

	/**
	 * @param cpt
	 *            the cpt to set
	 */
	public void setCptcode(String cptcode) {
		this.cptcode = cptcode;
	}

	/**
	 * @return the doctor
	 */
	public DoctorEntity getDoctor() {
		return doctor;
	}

	/**
	 * @param doctor
	 *            the doctor to set
	 */
	public void setDoctor(DoctorEntity doctor) {
		this.doctor = doctor;
	}

	/**
	 * @return the balanceAmt
	 */
	public Float getBalanceAmt() {
		return balanceAmt;
	}

	/**
	 * @param balanceAmt
	 *            the balanceAmt to set
	 */
	public void setBalanceAmt(Float balanceAmt) {
		this.balanceAmt = balanceAmt;
	}

	/**
	 * @return the managerRemark
	 */
	public String getManagerRemark() {
		return managerRemark;
	}

	/**
	 * @param managerRemark
	 *            the managerRemark to set
	 */
	public void setManagerRemark(String managerRemark) {
		this.managerRemark = managerRemark;
	}

	/**
	 * @return the withoutTimilyFiling
	 */
	public Integer getWithoutTimilyFiling() {
		return withoutTimilyFiling;
	}

	/**
	 * @param withoutTimilyFiling
	 *            the withoutTimilyFiling to set
	 */
	public void setWithoutTimilyFiling(Integer withoutTimilyFiling) {
		this.withoutTimilyFiling = withoutTimilyFiling;
	}

	
}