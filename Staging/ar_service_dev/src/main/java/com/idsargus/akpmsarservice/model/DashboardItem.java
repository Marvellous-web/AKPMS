package com.idsargus.akpmsarservice.model;

import java.util.List;

import lombok.Data;

//Arindam Code Changes
@Data
public class DashboardItem {
	
	private Integer id;
	private String name;
	private List<DashboardCount> children;

}
