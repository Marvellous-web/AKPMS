package com.idsargus.akpmsauthservice.entity;

import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;
	
	private String firstName;
	
	private String lastName;

	private String email;

	private String password;

	private String address;

	private String contact;

//	@NotNull
//	@Column(name = "enabled", columnDefinition = "BIT default '1'")
//	private boolean enabled;
//
//	@NotNull
//	@Column(name = "deleted", columnDefinition = "BIT default '0'")
//	private boolean deleted;

	@NotNull
	@Column(name = "enabled", columnDefinition = "boolean default true", nullable = false)
	private Boolean enabled = true;

	@NotNull
	@Column(name = "deleted", columnDefinition = "boolean default false", nullable = false)
	private Boolean deleted = false;

	@OneToOne
	@JoinColumn(name = "role_id")
	private RoleEntity role;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_permission", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "permission_id", referencedColumnName = "id") })
	private Set<PermissionEntity> permissions;

//	@Transient
//	private List<Map<String, Object>> permissionWithDepartments;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_department", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "department_id", referencedColumnName = "id") })
	private Set<DepartmentEntity> departments;

	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

		permissions.forEach(p -> {
			authorities.add(new SimpleGrantedAuthority(p.getId()));
		});

		authorities.add(new SimpleGrantedAuthority(this.role.getName()));

		return authorities;
	}

}
