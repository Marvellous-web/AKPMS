package com.idsargus.akpmscommonservice.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ar_status_code")
public class ArStatusCodeEntity {

	@Id
	protected String id;

	private String name;

	@Override
	public String toString() {
		return getId().toString();
	}

}
