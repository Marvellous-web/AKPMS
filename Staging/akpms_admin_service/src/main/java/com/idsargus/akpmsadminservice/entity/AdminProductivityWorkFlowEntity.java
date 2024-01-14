package com.idsargus.akpmsadminservice.entity;

import com.idsargus.akpmscommonservice.entity.BaseIdEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "ar_productivity_workflow")
public class AdminProductivityWorkFlowEntity extends BaseIdEntity {

	private static final long serialVersionUID = 1L;

	private String name;

	@NotNull
	@Column(name = "enabled", columnDefinition = "boolean default true", nullable = false)
	private Boolean enabled = true;

//	@ManyToOne
//    private ArProductivityEntity arProductivityEntity;
}
