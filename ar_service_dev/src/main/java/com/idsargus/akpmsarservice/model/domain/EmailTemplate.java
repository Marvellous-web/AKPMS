package com.idsargus.akpmsarservice.model.domain;
import java.io.Serializable;
import java.time.temporal.TemporalAccessor;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.idsargus.akpmsarservice.model.BaseEntity;
import com.idsargus.akpmscommonservice.entity.BaseAuditableEntity;
import com.idsargus.akpmscommonservice.entity.UserEntity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
//@EntityListeners(EntityListener.class)
@Table(name = "email_template")
public class EmailTemplate extends BaseAuditableEntity {
	/** Default value included to remove warning. Remove or modify at will. **/
	private static final long serialVersionUID = 1L;


	// @Pattern(regexp = "[A-Za-z ]*", message =
	// "must contain only letters and spaces")

	private String name;

	@Column(name = "content", columnDefinition = "TEXT")
	private String content;

	@NotNull
	@Column(name = "subscription_email", columnDefinition = "TINYINT(1) DEFAULT '0'")
	private boolean subscriptionEmail = false;

	@NotNull
	@Column(name = "status", columnDefinition = "TINYINT(1) DEFAULT '1'")
	private boolean status = true;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_email_subscription_rel", joinColumns = { @JoinColumn(name = "email_template_id") }, inverseJoinColumns = { @JoinColumn(name = "user_id") })
	private List<UserEntity> users;

	@NotNull
	@Column(name = "is_deleted", columnDefinition = "TINYINT(1) DEFAULT '0'")
	private boolean deleted = false;

	public String toString() {
		return "" + this.getId();
	}

	
}