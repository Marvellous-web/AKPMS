package com.idsargus.akpmsadminservice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "admin_stat")
public class ArAdminStatEntity {

	@Id
	private String tableName;
	
	private String route;
	
	private Integer total;

	private Integer active;

	private Integer inactive;
}
