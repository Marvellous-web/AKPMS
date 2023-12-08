package com.idsargus.akpmsadminservice.wspojo;

import com.idsargus.akpmsadminservice.entity.AdminDepartmentEntity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DepartmentTreeViewResponse {

    private Integer id;
    private String name;
    private List<AdminDepartmentEntity> listDepartment;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<AdminDepartmentEntity> getListDepartment() {
		return listDepartment;
	}
	public void setListDepartment(List<AdminDepartmentEntity> listDepartment) {
		this.listDepartment = listDepartment;
	}
    
    
}
