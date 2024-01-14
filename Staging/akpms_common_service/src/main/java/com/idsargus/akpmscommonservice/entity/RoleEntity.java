package com.idsargus.akpmscommonservice.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "role")
public class RoleEntity extends BaseIdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;

}