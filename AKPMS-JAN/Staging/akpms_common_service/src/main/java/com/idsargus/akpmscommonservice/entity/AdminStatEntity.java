package com.idsargus.akpmscommonservice.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "admin_stat")
public class AdminStatEntity {

	@Id
	private String tableName;
	
	private String route;
	
	private Integer total;

	private Integer active;

	private Integer inactive;
}
