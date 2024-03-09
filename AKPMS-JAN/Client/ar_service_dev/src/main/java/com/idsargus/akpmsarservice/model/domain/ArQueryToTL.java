package com.idsargus.akpmsarservice.model.domain;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.idsargus.akpmscommonservice.entity.ArProductivityWorkFlowEntity;
import com.idsargus.akpmscommonservice.entity.BaseAuditableEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "query_to_tl")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class ArQueryToTL extends BaseAuditableEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("arProductivity")
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumns({ @JoinColumn(name = "ar_productivity_id", referencedColumnName = "id") })
	private ArProductivity arProductivity;
	
	@Column(name="tl_status")
	private String tlStatus;
	
	@Column(name="tl_remarks")
	private String tlRemarks;

	@Column(name="remark")
	private String remark;

	@Transient
	private String cpt;

	@Transient
	private String dos;

	@Transient
	private Integer insuranceId;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "ar_productivity_workflow_rel", joinColumns = {
			@JoinColumn(name = "ar_productivity_id") }, inverseJoinColumns = {
					@JoinColumn(name = "ar_productivity_workflow_id") })
	private Set<ArProductivityWorkFlowEntity> arWorkflows;
	
	public List<Integer> getWorkflowIds() {
		return (this.arWorkflows == null) ? null
				: this.arWorkflows.stream().map(ArProductivityWorkFlowEntity::getId).collect(Collectors.toList());
	}
	public String getCpt(){
		return (this.arProductivity == null) ? null : this.arProductivity.getCpt();
	}

	public Integer getInsuranceId(){
		return (this.arProductivity == null) ? null : this.arProductivity.getInsurance().getId();
	}
	public String getDos(){
		return (this.arProductivity == null) ? null : this.arProductivity.getDos();
	}

}
