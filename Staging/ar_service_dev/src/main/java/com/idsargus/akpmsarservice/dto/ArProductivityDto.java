package com.idsargus.akpmsarservice.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArProductivityDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty
	private Float balanceAmount;

	private String cpt;

	private String dos;

	// TODO can be date
	private String followUpDate;

	private String patientAccountNumber;

	private String patientName;

	private String remark;

	private String source;

	private String statusCode;

	private Boolean timilyFiling;

	private String tlRemark;
	
	private Long databaseId;

	private Long doctorId;

	private Long insuranceId;

	private Long teamId;

	private List<Long> workflowIds;
}
