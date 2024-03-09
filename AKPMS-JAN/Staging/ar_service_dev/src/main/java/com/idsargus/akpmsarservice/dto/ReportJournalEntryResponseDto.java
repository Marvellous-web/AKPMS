package com.idsargus.akpmsarservice.dto;

import java.math.BigInteger;
import java.util.List;

import lombok.Data;

@Data
public class ReportJournalEntryResponseDto {
	
	private String revenueSubAct;


	private String costCenter;
	
	private String transactionDesc1;
	
	private String transactionDesc2;
	
	private Double debit;

	private Double depositAmount;
	private Double credit;

	private String doctorCode;
	private BigInteger ticketNumber;

	private String doctorName;
	private String revenueName;
	private String revenueCode;
	private Double totalDeposit;

	private Double docPercentage;
	private Double mgmtfee;
	private String depositDate;

	private Double totalCredit;


}
