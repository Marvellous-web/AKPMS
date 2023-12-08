package com.idsargus.akpmsarservice.model.domain;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.idsargus.akpmsarservice.model.BaseEntity;
import com.idsargus.akpmscommonservice.entity.BaseAuditableEntity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Entity
@Table(name = "ar_database")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class ArDatabase extends BaseAuditableEntity {
	/** Default value included to remove warning. Remove or modify at will. **/
	private static final long serialVersionUID = 1L;


	// @Pattern(regexp = "[A-Za-z ]*", message =
	// "must contain only letters and spaces")
	@NotNull
	private String name;

	@Column(name = "description", columnDefinition = "TEXT")
	private String desc;

	@NotNull
	@Column(name = "status", columnDefinition = "TINYINT(1) DEFAULT '1'")
	private boolean status = true;

	@NotNull
	@Column(name = "is_deleted", columnDefinition = "TINYINT(1) DEFAULT '0'")
	private boolean deleted = false;


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

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

}