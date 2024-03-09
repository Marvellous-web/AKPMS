package com.idsargus.akpmsadminservice.wspojo;

import lombok.Data;

import java.util.List;

@Data
public class DashboardCount {
	
	private Integer id;
	private String name;
	private long count;
	private String url;
	private String filterName1;
	private String filterValue1;
	private String filterName2;
	private String filterValue2;
	
	private List<DashboardCount> children;
	
}
