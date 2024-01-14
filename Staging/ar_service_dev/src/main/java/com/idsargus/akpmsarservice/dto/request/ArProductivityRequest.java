package com.idsargus.akpmsarservice.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ArProductivityRequest {

	private String Keyword;
	
	private String workFlowName;
	
	private String source;
	
	private String status;
	
	private Long doctorId;
	
	private Long insuranceId;
	
}
