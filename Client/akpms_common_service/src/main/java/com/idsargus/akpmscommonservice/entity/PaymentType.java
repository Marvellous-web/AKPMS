package com.idsargus.akpmscommonservice.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "payment_type")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class PaymentType extends BaseAuditableEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	@NotNull
	private String name;

	@Column(name = "description", columnDefinition = "TEXT")
	private String desc;

	// @NotNull
	// private String type = "paytypes";

//	@NotNull
//	@Column(name = "status", columnDefinition = "TINYINT(1) DEFAULT '1'")
//	private boolean status = true;

	@NotNull
	@Column(name = "deleted", columnDefinition = "TINYINT(1) DEFAULT '0'")
	private boolean deleted = false;

	@NotNull
	@Column(name = "enabled", columnDefinition = "TINYINT(1) DEFAULT '1'")
	private boolean enabled = true;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}



	/*
	 * public String getType() { return type; }
	 * 
	 * public void setType(String type) { this.type = type; }
	 */

	

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

}