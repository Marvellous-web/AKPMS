package com.idsargus.akpmscommonservice.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "permission")
public class PermissionEntity {

	@Id
	protected String id;

	private String name;

	@Column(columnDefinition = "TEXT")
	private String description;
	
	
	//Added by ARN
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_permission_rel", joinColumns = { @JoinColumn(name = "permission_id") }, inverseJoinColumns = { @JoinColumn(name = "user_id") })
	private List<UserEntity> users;
	
	@Override
	public String toString() {
		return getId().toString();
	}

}
