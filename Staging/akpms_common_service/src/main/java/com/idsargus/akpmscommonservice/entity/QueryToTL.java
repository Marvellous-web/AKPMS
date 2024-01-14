package com.idsargus.akpmscommonservice.entity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "query_to_tl")
public class QueryToTL extends BaseAuditableEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumns({ @JoinColumn(name = "ar_productivity_id", referencedColumnName = "id") })
	private ArProductivityEntity arProductivity;
	
	@Column(name="tl_status")
	private String tlStatus;
	
	@Column(name="tl_remarks")
	private String tlRemarks;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "ar_productivity_workflow_rel", joinColumns = {
			@JoinColumn(name = "ar_productivity_id") }, inverseJoinColumns = {
					@JoinColumn(name = "ar_productivity_workflow_id") })
	private Set<ArProductivityWorkFlowEntity> arWorkflows;
	
	public List<Integer> getWorkflowIds() {
		return (this.arWorkflows == null) ? null
				: this.arWorkflows.stream().map(ArProductivityWorkFlowEntity::getId).collect(Collectors.toList());
	}
	
	

}
