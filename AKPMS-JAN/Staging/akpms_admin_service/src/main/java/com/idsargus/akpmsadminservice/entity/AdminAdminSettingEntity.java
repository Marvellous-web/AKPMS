package com.idsargus.akpmsadminservice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "admin_settings")
public class AdminAdminSettingEntity extends AdminBaseAuditableEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "argus_ratings")
	private String argusRatings;

	@Column(name = "ids_argus_ratings")
	private String idsArgusRatings;

	@Column(name = "procees_manaul_read_status_ratings")
	private String proceesManaulReadStatusRatings;

}
