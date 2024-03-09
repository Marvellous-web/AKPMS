package com.idsargus.akpmsadminservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.idsargus.akpmscommonservice.entity.BaseIdEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "revenue_type")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class AdminRevenueType extends BaseIdEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	@NotNull
	private String name;

	@NotNull
	private String code;

	@Column(name = "description", columnDefinition = "TEXT")
	private String desc;

	@NotNull
	@Column(name = "enabled", columnDefinition = "TINYINT(1) DEFAULT '1'")
	private boolean enabled = true;

	@NotNull
	@Column(name = "deleted", columnDefinition = "TINYINT(1) DEFAULT '0'")
	private boolean deleted = false;

	private float payments = 0.01F;

	private float accounting = 0;

	private float operations = 0;


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


	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the payments
	 */
	public float getPayments() {
		return payments;
	}

	/**
	 * @param payments the payments to set
	 */
	public void setPayments(float payments) {
		this.payments = payments;
	}

	/**
	 * @return the accounting
	 */
	public float getAccounting() {
		return accounting;
	}

	/**
	 * @param accounting the accounting to set
	 */
	public void setAccounting(float accounting) {
		this.accounting = accounting;
	}

	/**
	 * @return the operations
	 */
	public float getOperations() {
		return operations;
	}

	/**
	 * @param operations the operations to set
	 */
	public void setOperations(float operations) {
		this.operations = operations;
	}

}