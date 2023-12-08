package com.idsargus.akpmsarservice.model.domain;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.idsargus.akpmscommonservice.entity.UserEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="notifications")
public class Notifications {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	@Column(columnDefinition="TEXT")
	private String content;
	
	@Column(length=255)
	private String type;
	
	@Column(name="created_on")
	private Date createdOn;
	
	@ManyToMany(cascade={CascadeType.ALL})
	@JoinTable(name="user_notification_rel", joinColumns={@JoinColumn(name="notification_id")}, inverseJoinColumns={@JoinColumn(name="user_id")})
	private List<UserEntity> userList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public List<UserEntity> getUserList() {
		return userList;
	}

	public void setUserList(List<UserEntity> userList) {
		this.userList = userList;
	} 	
}