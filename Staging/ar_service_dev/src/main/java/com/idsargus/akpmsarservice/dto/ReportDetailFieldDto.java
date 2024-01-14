package com.idsargus.akpmsarservice.dto;

import java.math.BigInteger;
import java.util.Date;

import lombok.Data;

@Data
public class ReportDetailFieldDto {
	
	private String batchNo;
	
	private String costCenterNumber;
	
	private String costCenterName;
	
	private String depositDate;
	
	private String month;
	
	private String moneySource;
	
	private String revenueSubAct;
	
	private String revenueType;
	
	private Double total;
	
	private Double docPercent;
	
	private Double mgmtFees;
	
	private String subName;
	
	private Double subTotal;
	
	private Double subMgmtFees;

}
