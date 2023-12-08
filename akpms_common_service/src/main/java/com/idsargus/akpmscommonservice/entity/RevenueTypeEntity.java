package com.idsargus.akpmscommonservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "revenue_type")
public class RevenueTypeEntity extends BaseIdEntity {

	private static final long serialVersionUID = 1L;

	private String name;
	
	private String code;
	
	@Column(name = "payments")
	private Float payments = 0F;

	@Column(name = "operations")
	private Float operations = 0F;

	@Column(name = "accounting")
	private Float accounting = 0F;

	@Column(name = "description")
	private String description;

	@NotNull
	@Column(name = "enabled", columnDefinition = "boolean default true", nullable = false)
	private Boolean enabled = true;

	@NotNull
	@Column(name = "deleted", columnDefinition = "boolean default false", nullable = false)
	private Boolean deleted = false;
}
