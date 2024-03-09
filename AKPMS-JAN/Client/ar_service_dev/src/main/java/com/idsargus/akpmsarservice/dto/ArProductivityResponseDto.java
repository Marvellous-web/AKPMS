package com.idsargus.akpmsarservice.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArProductivityResponseDto extends ArProductivityDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String databaseName;

	private String doctorName;

	private String insuranceName;

	private String teamName;
	
	private String createdBy;
	
	private String createdOn;
	
	private String modifiedBy;
	
	private String modifiedOn;
}
