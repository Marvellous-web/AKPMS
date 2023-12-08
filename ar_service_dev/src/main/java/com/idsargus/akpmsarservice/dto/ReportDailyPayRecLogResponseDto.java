package com.idsargus.akpmsarservice.dto;

import java.util.List;

import lombok.Data;

@Data
public class ReportDailyPayRecLogResponseDto {
	
	private List<ReportDailyPayRecLogDto> records;
	
	private Double totalDeposit;
	
	private Double totalLockBox;
	
	private Double totalVault;
	
	private Double totalCreditCard;
	
	private Double totalTelecheck;
	
	private Double totalEft;

}
