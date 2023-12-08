package com.idsargus.akpmsadminservice.wspojo;

import lombok.Data;

import java.util.List;

//Arindam Code Changes
@Data
public class DashboardItem {
	
	private Integer id;
	private String name;
	private List<DashboardCount> children;

}
