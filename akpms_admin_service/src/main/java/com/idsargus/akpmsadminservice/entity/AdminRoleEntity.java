package com.idsargus.akpmsadminservice.entity;

import com.idsargus.akpmscommonservice.entity.BaseIdEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "role")
public class AdminRoleEntity extends BaseIdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;

}