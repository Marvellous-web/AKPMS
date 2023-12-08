package com.idsargus.akpmsadminservice.wspojo;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ArSearchDto extends WsRequestDto {
	@NotEmpty
	private String patientName;
	@NotEmpty
	private Float balanceAmount;
	@NotEmpty
	private String Authorization;
	

}
