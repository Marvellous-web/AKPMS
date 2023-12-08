package com.idsargus.akpmsauthservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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

	@Override
	public String toString() {
		return getId().toString();
	}

}
