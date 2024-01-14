//package com.idsargus.akpmscommonservice.entity;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//import javax.persistence.Transient;
//import javax.validation.constraints.NotNull;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import lombok.Getter;
//import lombok.Setter;
//
//
//@Getter
//@Setter
//@Entity
//@Table(name = "files")
//@JsonIgnoreProperties({"hibernateLazyInitializer"})
//public class Files extends BaseAuditableEntity
//{
//
//	/**
//	 *
//	 */
//	private static final long serialVersionUID = 1L;
//
//	private String name;
//
//	private String type;
//
//	@Column(name = "system_name")
//	private String systemName;
//
//	@NotNull
//	@Column(name = "is_deleted", columnDefinition = "TINYINT(1) DEFAULT '0'")
//	private boolean deleted = false;
//
//	@NotNull
//	@Column(name = "status", columnDefinition = "TINYINT(1) DEFAULT '1'")
//	private boolean status = true;
//
//	// @ManyToOne(cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,
//	// CascadeType.REMOVE})
//	@ManyToOne()
//	@JoinColumn(name = "process_manual_id", referencedColumnName = "id", unique = false, nullable = true)
//	private ProcessManual processManual;
//
////	@Transient
////	private MultipartFile attachedFile;
//
//	@Transient
//	private Long subProcessManualId;
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public String getType() {
//		return type;
//	}
//
//	public void setType(String type) {
//		this.type = type;
//	}
//
//	public String getSystemName() {
//		return systemName;
//	}
//
//	public void setSystemName(String systemName) {
//		this.systemName = systemName;
//	}
//
//	public boolean isDeleted() {
//		return deleted;
//	}
//
//	public void setDeleted(boolean deleted) {
//		this.deleted = deleted;
//	}
//
//	public boolean isStatus() {
//		return status;
//	}
//
//	public void setStatus(boolean status) {
//		this.status = status;
//	}
//
//	public ProcessManual getProcessManual() {
//		return processManual;
//	}
//
//	public void setProcessManual(ProcessManual processManual) {
//		this.processManual = processManual;
//	}
//
//	public Long getSubProcessManualId() {
//		return subProcessManualId;
//	}
//
//	public void setSubProcessManualId(Long subProcessManualId) {
//		this.subProcessManualId = subProcessManualId;
//	}
//
//	
//}