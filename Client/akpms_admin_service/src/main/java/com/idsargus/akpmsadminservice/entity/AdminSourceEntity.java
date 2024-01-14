package com.idsargus.akpmsadminservice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "ar_source")
public class AdminSourceEntity {

	@Id
	protected String id;

	private String name;

	@Override
	public String toString() {
		return getId().toString();
	}

}
