package com.idsargus.akpmsadminservice.entity;


import com.idsargus.akpmscommonservice.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "permission")
public class AdminPermissionEntity {

	@Id
	protected String id;

	private String name;

	@Column(columnDefinition = "TEXT")
	private String description;
	
	
	//Added by ARN
//	@ManyToMany(cascade = CascadeType.ALL)
//	@JoinTable(name = "user_permission_rel", joinColumns = { @JoinColumn(name = "permission_id") }, inverseJoinColumns = { @JoinColumn(name = "user_id") })
//	private List<User> users;

//	@ManyToMany(cascade = CascadeType.ALL)
//	@JoinTable(name = "user_permission", joinColumns = { @JoinColumn(name = "permission_id") }, inverseJoinColumns = { @JoinColumn(name = "user_id") })
//	private List<UserEntity> users;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_permission", joinColumns = { @JoinColumn(name = "permission_id") }, inverseJoinColumns = { @JoinColumn(name = "user_id") })
    private List<User> users;
	@Override
	public String toString() {
		return getId().toString();
	}

}
