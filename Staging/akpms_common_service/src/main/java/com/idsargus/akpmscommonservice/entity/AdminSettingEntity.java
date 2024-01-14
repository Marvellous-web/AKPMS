package com.idsargus.akpmscommonservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "admin_settings")
public class AdminSettingEntity extends BaseAuditableEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "argus_ratings")
	private String argusRatings;

	@Column(name = "ids_argus_ratings")
	private String idsArgusRatings;

	@Column(name = "procees_manaul_read_status_ratings")
	private String proceesManaulReadStatusRatings;

}
