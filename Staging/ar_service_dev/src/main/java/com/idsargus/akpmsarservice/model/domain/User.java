package com.idsargus.akpmsarservice.model.domain;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.idsargus.akpmscommonservice.entity.*;
import lombok.Getter;
import lombok.Setter;

//import argus.util.Constants;

@Entity
@Getter
@Setter
@Table(name = "user")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class User extends BaseAuditableEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email", updatable = false, insertable = true)
	private String email;


	@Column(name = "password", updatable = false, insertable = true)
	private String password;

	@Column(name= "contact")
	private String contact;

	@Column(name = "address", columnDefinition = "TEXT")
	private String address;

	@NotNull
	@Column(name = "enabled", columnDefinition = "boolean default true", nullable = false)
	private Boolean enabled = true;

	@NotNull
	@Column(name = "deleted", columnDefinition = "boolean default false", nullable = false)
	private Boolean deleted = false;

	@Transient
	private String columnName;
	//@Transient
	//added by ARN
//	@Column(name = "role_id", nullable=false)
	//   private Integer roleId;


	//	@OneToOne
	//	@JoinColumn(name = "role_id")
	//added by ARN
	@ManyToOne(optional=false)
	@JoinColumn(name="role_id", insertable=false, updatable=false)
	private RoleEntity role;


	@Transient
	private List<String> permissionIds;

	@Transient
	private List<Integer> departmentIds;

	@Transient
	private List<Integer> emailTemplateIds;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_permission", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
			@JoinColumn(name = "permission_id", referencedColumnName = "id") })
	private Set<PermissionEntity> permissions;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_department", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
			@JoinColumn(name = "department_id", referencedColumnName = "id") })
	private Set<DepartmentEntity> departments;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_email_template", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
			@JoinColumn(name = "email_template_id", referencedColumnName = "id") })
	private Set<EmailTemplateEntity> emailTemplates;

	public Integer getRoleId() {
		return (this.role == null) ? null : this.role.getId();
	}

	public List<String> getPermissionIds() {
		return (this.permissions == null) ? null
				: this.permissions.stream().map(PermissionEntity::getId).collect(Collectors.toList());
	}

	public List<Integer> getDepartmentIds() {
		return (this.departments == null) ? null
				: this.departments.stream().map(DepartmentEntity::getId).collect(Collectors.toList());
	}

	public List<Integer> getEmailTemplateIds() {
		return (this.emailTemplates == null) ? null
				: this.emailTemplates.stream().map(EmailTemplateEntity::getId).collect(Collectors.toList());
	}
}