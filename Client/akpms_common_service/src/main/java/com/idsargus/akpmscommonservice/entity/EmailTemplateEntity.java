package com.idsargus.akpmscommonservice.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "email_template")
@Getter
@Setter
public class EmailTemplateEntity extends BaseIdEntity {

	private static final long serialVersionUID = 1L;

	private String name;

	@Column(name = "content", columnDefinition = "TEXT")
	private String content;

	@NotNull
	@Column(name = "subscription_email", columnDefinition = "BIT default 0", nullable = false)
	private Boolean subscriptionEmail;

	@NotNull
	@Column(name = "enabled", columnDefinition = "boolean default true", nullable = false)
	private Boolean enabled = true;
	
	@NotNull
	@Column(name = "status", columnDefinition = "boolean default true", nullable = false)
	private Boolean status = true;

	@ManyToMany(mappedBy = "emailTemplates")
	private List<UserEntity> users;
}