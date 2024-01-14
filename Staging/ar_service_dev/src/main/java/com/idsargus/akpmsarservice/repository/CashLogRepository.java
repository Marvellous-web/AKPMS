package com.idsargus.akpmsarservice.repository;

import java.util.List;

import com.idsargus.akpmsarservice.exception.AppException;
import org.springframework.stereotype.Repository;

@Repository
public interface CashLogRepository {

	List<Object[]> journalEntryManagementFee(String month, String year)
			throws AppException;

	List<Object[]> journalEntrySummary(String month, String year)
			throws AppException;

	List<Object[]> journalEntryDetailedWithManagementFee(String month,
			String year) throws AppException;

	
	List<Object[]> dailyPaymentReceiptLog(String month, String year,
			String depositDateFrom, String depositDateTo) throws AppException;

}
