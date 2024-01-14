package com.idsargus.akpmsadminservice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "payment_type")
public class AdminPaymentTypeEntity extends AdminBaseAuditableEntity {

	private static final long serialVersionUID = 1L;

	private String name;

	@Column(name = "description")
	private String description;

	@NotNull
	@Column(name = "enabled", columnDefinition = "boolean default true", nullable = false)
	private Boolean enabled = true;

	@NotNull
	@Column(name = "deleted", columnDefinition = "boolean default false", nullable = false)
	private Boolean deleted = false;
}
