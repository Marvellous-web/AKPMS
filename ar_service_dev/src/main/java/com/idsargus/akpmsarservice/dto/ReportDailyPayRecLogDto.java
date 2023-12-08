package com.idsargus.akpmsarservice.dto;

import lombok.Data;

@Data
public class ReportDailyPayRecLogDto {
	
	private String provider;
	
	private String depositDate;
	
	private Double depositTotal;
	
	private String revenueType;
	
	private Double lockBox;
	
	private Double vault;
	
	private Double creditCard;
	
	private Double teleChecks;
	
	private Double eft;
		
}
