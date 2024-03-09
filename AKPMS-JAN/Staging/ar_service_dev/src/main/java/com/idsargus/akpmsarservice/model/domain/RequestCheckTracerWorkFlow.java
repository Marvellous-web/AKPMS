package com.idsargus.akpmsarservice.model.domain;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.idsargus.akpmscommonservice.entity.ArProductivityEntity;
import com.idsargus.akpmscommonservice.entity.BaseAuditableEntity;

import lombok.Getter;
import lombok.Setter;

//import argus.util.XstreamDateConverter;
//
//import com.thoughtworks.xstream.annotations.XStreamAlias;
//import com.thoughtworks.xstream.annotations.XStreamConverter;

//@XStreamAlias("RequestCheckTracerWorkFlow")
@Getter
@Setter
@Entity
@Table(name="request_check_tracer_workflow")
//@EntityListeners(EntityListener.class)
public class RequestCheckTracerWorkFlow extends BaseAuditableEntity {



	@OneToOne()
	@JoinColumns({ @JoinColumn(name = "ar_productivity_id", referencedColumnName = "id", unique = false, nullable = false) })
	private ArProductivityEntity arProductivity;

	@Column(name="check_mailing_address")
	private String checkMailingAdd;

	@Column(name="check_no")
	private String checkNo;

//	@XStreamConverter(value = XstreamDateConverter.class)
	@Column(name="check_issue_date")
	private Date checkIssueDate;

//	@XStreamConverter(value = XstreamDateConverter.class)
	@Column(name="check_cashed_date")
	private Date checkCashedDate;

	@Column(name="check_amount")
	private Float checkAmount;

	@Column(name = "argus_remark", columnDefinition = "TEXT")
	private String argusRemark;

	@Column(name="status")
	private int status;

	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH,
			CascadeType.DETACH, CascadeType.REMOVE })
	@JoinColumns({ @JoinColumn(name = "attachment_id", referencedColumnName = "id", nullable = true) })
	private ArFiles attachment;

	@NotNull
	@Column(name = "deleted", columnDefinition = "TINYINT(1) DEFAULT '0'")
	private boolean deleted = false;

	
	public String getCheckNo() {
		return checkNo;
	}

	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}

	public Date getCheckIssueDate() {
		return checkIssueDate;
	}

	public void setCheckIssueDate(Date checkIssueDate) {
		this.checkIssueDate = checkIssueDate;
	}

	public Date getCheckCashedDate() {
		return checkCashedDate;
	}

	public void setCheckCashedDate(Date checkCashedDate) {
		this.checkCashedDate = checkCashedDate;
	}

	public String getArgusRemark() {
		return argusRemark;
	}

	public void setArgusRemark(String argusRemark) {
		this.argusRemark = argusRemark;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public ArProductivityEntity getArProductivity() {
		return arProductivity;
	}

	public void setArProductivity(ArProductivityEntity arProductivity) {
		this.arProductivity = arProductivity;
	}

	public String getCheckMailingAdd() {
		return checkMailingAdd;
	}

	public void setCheckMailingAdd(String checkMailingAdd) {
		this.checkMailingAdd = checkMailingAdd;
	}

	public Float getCheckAmount() {
		return checkAmount;
	}

	public void setCheckAmount(Float checkAmount) {
		this.checkAmount = checkAmount;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public ArFiles getAttachment() {
		return attachment;
	}

	public void setAttachment(ArFiles attachment) {
		this.attachment = attachment;
	}

}