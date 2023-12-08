package com.idsargus.akpmsarservice.dto;

import java.util.List;

import lombok.Data;

@Data
public class ReportDetailJournalEntryResponseDto {
	
	private List<ReportDetailFieldDto> detailEntryList;
	private Double grandTotal;
	private Double grandTotalMgmtFee;
}
