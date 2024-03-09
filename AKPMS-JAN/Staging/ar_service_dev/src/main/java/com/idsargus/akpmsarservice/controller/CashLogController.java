package com.idsargus.akpmsarservice.controller;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.idsargus.akpmsarservice.dto.ReportDailyPayRecLogDto;
import com.idsargus.akpmsarservice.dto.ReportDailyPayRecLogResponseDto;
import com.idsargus.akpmsarservice.dto.ReportDetailFieldDto;
import com.idsargus.akpmsarservice.dto.ReportDetailJournalEntryResponseDto;
import com.idsargus.akpmsarservice.dto.ReportJournalEntryResponseDto;
import com.idsargus.akpmsarservice.exception.AppException;
import com.idsargus.akpmsarservice.repository.CashLogRepository;
import com.idsargus.akpmsarservice.util.AkpmsUtil;
import com.idsargus.akpmsarservice.util.Constants;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "v1/arapi/cashlog/")
@Slf4j
public class CashLogController {

	private static final String MONTHS = "months";
	private static final String YEARS = "years";

	@Autowired
	private CashLogRepository cashLogRepository;

	private String month;

	private String year;

	/**
	 * REPORT -1 : JOURNAL ENTRIES MANAGEMENT FEE [FOR THE MONTH OF ****-**]
	 *
	 * @param month
	 * @param year
	 * @return
	 */
	@GetMapping(path = "rptjemngfee")
	public ResponseEntity<?> journalEntryManagementFee(@RequestParam(value = "month") String month,
													   @RequestParam(value = "year") String year)  {
		log.info("Enter journalEntryManagementFee");
		List<ReportJournalEntryResponseDto> listResponse = new ArrayList<>();
		int month_numeric = Integer.valueOf(month);
		if(month_numeric < 10){
			month = "0" + month;
		}
		List<Object[]> reportData = new ArrayList<Object[]>();
		try {
			reportData = cashLogRepository.journalEntryManagementFee(month, year);
			
			//if condition added ARR
			if(reportData.size()-1>0) {
			for(int i=0;i<reportData.size()-1;i++) {
				ReportJournalEntryResponseDto responseDto = new ReportJournalEntryResponseDto();
				responseDto.setRevenueSubAct((String)reportData.get(i)[0]);
				responseDto.setRevenueName((String)reportData.get(i)[1]);
				responseDto.setDoctorName((String)reportData.get(i)[3]);
				responseDto.setTotalDeposit(getRoundedValue((Double) reportData.get(i)[4]));
				responseDto.setCostCenter((String)reportData.get(i)[2]);
				responseDto.setDoctorCode((String) reportData.get(i)[2]);
				responseDto.setDepositAmount(getRoundedValue((Double) reportData.get(i)[6]));
				responseDto.setTransactionDesc1("Management Fee");
				responseDto.setTransactionDesc2((String)reportData.get(i)[3]);
				responseDto.setDebit(getRoundedValue((Double)reportData.get(i)[5]));
				listResponse.add(responseDto);
			}
			ReportJournalEntryResponseDto responseDtoOther1 = new ReportJournalEntryResponseDto();
			responseDtoOther1.setRevenueSubAct("3620");
			responseDtoOther1.setCostCenter("9260");
			responseDtoOther1.setTransactionDesc1("Management Fee");
			responseDtoOther1.setTransactionDesc2("B.O. - Payment");
		
//		    responseDtoOther1.setCredit((Double)reportData.get(reportData.size()-1)[6]);
			
			listResponse.add(responseDtoOther1);
			ReportJournalEntryResponseDto responseDtoOther2 = new ReportJournalEntryResponseDto();
			responseDtoOther2.setRevenueSubAct("3620");
			responseDtoOther2.setCostCenter("9010");
			responseDtoOther2.setTransactionDesc1("Management Fee");
			responseDtoOther2.setTransactionDesc2("Accounting");
			responseDtoOther2.setCredit(getRoundedValue((Double)reportData.get(reportData.size()-1)[7]));
			listResponse.add(responseDtoOther2);
			ReportJournalEntryResponseDto responseDtoOther3 = new ReportJournalEntryResponseDto();
			responseDtoOther3.setRevenueSubAct("3620");
			responseDtoOther3.setCostCenter("9030");
			responseDtoOther3.setTransactionDesc1("Management Fee");
			responseDtoOther3.setTransactionDesc2("Operation");
			responseDtoOther3.setCredit(getRoundedValue((Double)reportData.get(reportData.size()-1)[8]));
			listResponse.add(responseDtoOther3);
			ReportJournalEntryResponseDto responseDtoTotal = new ReportJournalEntryResponseDto();
			responseDtoTotal.setTransactionDesc1("TOTAL:");
			responseDtoTotal.setDebit(getRoundedValue((Double)reportData.get(reportData.size()-1)[5]));
			responseDtoTotal.setCredit(getRoundedValue((Double)reportData.get(reportData.size()-1)[5]));
			responseDtoTotal.setTotalDeposit(getRoundedValue((Double)reportData.get(reportData.size()-1)[6]));
			responseDtoTotal.setDepositAmount(getRoundedValue((Double)reportData.get(reportData.size()-1)[6]));
			listResponse.add(responseDtoTotal);
			}
		} catch (Exception e) {
			log.error(Constants.ERROR, e);
			return new ResponseEntity<AppException>(new AppException(e.getMessage(), e.getCause()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		log.info("Exit journalEntryManagementFee");
		return new ResponseEntity<List<ReportJournalEntryResponseDto>>(listResponse, HttpStatus.OK);
	}

	/**
	 * REPORT -2: JOURNAL ENTRIES SUMMARY [FOR THE MONTH OF ****-**]
	 *
	 * @param month
	 * @param year
	 * @return
	 */
	@GetMapping(value = "rptentrysummary")
	public ResponseEntity<?> journalEntrySummary(@RequestParam(value = "month") String month,
												 @RequestParam(value = "year")String year) {
		log.info("in journalEntrySummary");
		List<ReportJournalEntryResponseDto> listResponse = new ArrayList<>();
		int month_numeric = Integer.valueOf(month);
		if(month_numeric < 10){
			month = "0" + month;
		}
		List<Object[]> reportData = new ArrayList<Object[]>();
		double total = 0;
		double totalCredit=0;
		try {
			reportData = cashLogRepository.journalEntrySummary(month, year);
			for(int i=0;i<reportData.size();i++) {
				ReportJournalEntryResponseDto responseDto = new ReportJournalEntryResponseDto();
				responseDto.setRevenueCode((String)reportData.get(i)[0]);
				responseDto.setRevenueSubAct((String)reportData.get(i)[0]);
				responseDto.setRevenueName((String)reportData.get(i)[1]);
				responseDto.setDoctorCode((String)reportData.get(i)[2]);
				responseDto.setDoctorName((String)reportData.get(i)[3]);
				responseDto.setTransactionDesc1((String)reportData.get(i)[1]);
				responseDto.setTransactionDesc2((String)reportData.get(i)[3]);
				responseDto.setTotalDeposit(getRoundedValue((Double) reportData.get(i)[4]));
				responseDto.setCredit(getRoundedValue((Double)reportData.get(i)[4]));

				responseDto.setMgmtfee(getRoundedValue((Double)reportData.get(i)[5]));
				totalCredit = totalCredit+(Double)reportData.get(i)[4];
//				responseDto.setRevenueCode((String)reportData.get(i)[0]);
//				responseDto.setRevenueSubAct((String)reportData.get(i)[0]);
////				responseDto.setTicketNumber((BigInteger) reportData.get(i)[1]);
//				responseDto.setRevenueName((String)reportData.get(i)[2]);
//				responseDto.setDoctorCode((String)reportData.get(i)[3]);
//				responseDto.setDoctorName((String)reportData.get(i)[4]);
//
//				responseDto.setDepositDate ((String)reportData.get(i)[5]);
//				responseDto.setCostCenter((String)reportData.get(i)[3]);
//				responseDto.setTransactionDesc1((String)reportData.get(i)[2]);
//				responseDto.setTransactionDesc2((String)reportData.get(i)[4]);
//
//				responseDto.setTotalDeposit((Double) reportData.get(i)[8]);
//				responseDto.setCredit((Double)reportData.get(i)[8]);
//
//				responseDto.setDocPercentage((Double)reportData.get(i)[9]);
//				responseDto.setMgmtfee((Double)reportData.get(i)[10]);
////				total+=(Double)reportData.get(i)[4];
				listResponse.add(responseDto);
			}
			ReportJournalEntryResponseDto responseDtoTotal = new ReportJournalEntryResponseDto();
			responseDtoTotal.setRevenueSubAct("1010");
			responseDtoTotal.setCostCenter("1100");
			responseDtoTotal.setTransactionDesc1("Cash - Shared Account");
			responseDtoTotal.setDebit(getRoundedValue(total));
			responseDtoTotal.setTotalCredit(getRoundedValue(totalCredit));
			listResponse.add(responseDtoTotal);
		} catch (Exception e) {
			log.error(Constants.ERROR, e);
			return new ResponseEntity<AppException>(new AppException(e.getMessage(), e.getCause()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		log.info("out journalEntrySummary");
		return new ResponseEntity<List<ReportJournalEntryResponseDto>>(listResponse, HttpStatus.OK);
	}

	private double getRoundedValue(Double value){
		if(value != null) {
			String pattern = "#.##";
			DecimalFormat decimalFormat = new DecimalFormat(pattern);
			return Double.parseDouble(decimalFormat.format(value));
		}
		return 0;
	}

	/**
	 * REPORT -3: DETAILED JOURNAL ENTRIES [FOR THE MONTH OF ****-**]
	 *
	 * @param month
	 * @param year
	 * @return
	 */
	@GetMapping(value = "rptentrydetailed")
	public ResponseEntity<?> journalEntryDetailed(@RequestParam(value = "month") String month,
												  @RequestParam(value = "year")String year) {
		log.info("Enter journalEntryDetailed");
		ReportDetailJournalEntryResponseDto response = new ReportDetailJournalEntryResponseDto();
		List<Object[]> reportData = new ArrayList<Object[]>();
		List<ReportDetailFieldDto> records = new ArrayList<>();
		double grandTotal = 0;
		try {
			reportData = cashLogRepository.journalEntryDetailedWithManagementFee(
					month, year);
			
			for(Object[] objArr : reportData) {
				if(objArr[0] != null) {
					ReportDetailFieldDto repFieldDto = new ReportDetailFieldDto();
					//repFieldDto.setBatchNo((BigInteger)objArr[0]);
					repFieldDto.setBatchNo(objArr[0].toString());
					repFieldDto.setCostCenterNumber((String)objArr[1]);
					repFieldDto.setCostCenterName((String)objArr[2]);
					repFieldDto.setDepositDate((String)objArr[3]);
					repFieldDto.setMonth(month+"-"+year);
					repFieldDto.setMoneySource((String)objArr[5]);
					repFieldDto.setRevenueSubAct((String)objArr[6]);
					repFieldDto.setRevenueType((String)objArr[7]);
					repFieldDto.setTotal(getRoundedValue((Double)objArr[8]));
					grandTotal+=(Double)objArr[8];
					records.add(repFieldDto);
				} else if(objArr[2] != null && objArr[0] == null) {
					ReportDetailFieldDto subDto = new ReportDetailFieldDto();
					subDto.setSubName((String)objArr[2]);
					subDto.setSubTotal(getRoundedValue((Double)objArr[8]));
					records.add(subDto);
				}
				
			}
			response.setDetailEntryList(records);
			response.setGrandTotal(getRoundedValue(grandTotal));
		} catch (Exception e) {
			log.error(Constants.ERROR, e);
			return new ResponseEntity<AppException>(new AppException(e.getMessage(), e.getCause()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		log.info("Exit journalEntryDetailed");
		return new ResponseEntity<ReportDetailJournalEntryResponseDto>(response, HttpStatus.OK);
	}

	/**
	 * REPORT -4: DETAILED JOURNAL ENTRIES WITH MANAGEMENT FEES [FOR THE MONTH
	 * OF ****-**]
	 *
	 * @param map
	 * @param request
	 * @return
	 */
	@GetMapping(value = "rptentrywithmngfee")
	public ResponseEntity<?> journalEntryWithManagementFee(@RequestParam(value = "month") String month, @RequestParam(value = "year")String year) {
		log.info("Enter journalEntryWithManagementFee");
		ReportDetailJournalEntryResponseDto response = new ReportDetailJournalEntryResponseDto();
		List<Object[]> reportData = new ArrayList<Object[]>();
		List<ReportDetailFieldDto> records = new ArrayList<>();
		double grandTotal = 0;
		double grandTotalMgmtFee = 0;
		try {
			reportData = cashLogRepository.journalEntryDetailedWithManagementFee(
					month, year);
			
			for(Object[] objArr : reportData) {
				if(objArr[0] != null) {
					ReportDetailFieldDto repFieldDto = new ReportDetailFieldDto();
					//repFieldDto.setBatchNo((BigInteger)objArr[0]);
					repFieldDto.setBatchNo(objArr[0].toString());
					repFieldDto.setCostCenterNumber((String)objArr[1]);
					repFieldDto.setCostCenterName((String)objArr[2]);
					repFieldDto.setDepositDate((String)objArr[3]);
					repFieldDto.setMonth(month+"-"+year);
					repFieldDto.setMoneySource((String)objArr[5]);
					repFieldDto.setRevenueSubAct((String)objArr[6]);
					repFieldDto.setRevenueType((String)objArr[7]);
					repFieldDto.setTotal((Double)objArr[8]);
					grandTotal+=(Double)objArr[8];
					repFieldDto.setDocPercent(objArr[9] != null ? (Double)objArr[9] : 0);
					repFieldDto.setMgmtFees(objArr[10] != null ? (Double)objArr[10] : 0);
					grandTotalMgmtFee+=objArr[10] != null ? (Double)objArr[10] : 0;
					records.add(repFieldDto);
				} else if(objArr[2] != null && objArr[0] == null) {
					ReportDetailFieldDto subDto = new ReportDetailFieldDto();
					subDto.setSubName((String)objArr[2]);
					subDto.setSubTotal((Double)objArr[8]);
					subDto.setSubMgmtFees((Double)objArr[10]);
					records.add(subDto);
				}
				
			}
			response.setDetailEntryList(records);
			response.setGrandTotal(grandTotal);
			response.setGrandTotalMgmtFee(grandTotalMgmtFee);
		} catch (Exception e) {
			log.error(Constants.ERROR, e);
			return new ResponseEntity<AppException>(new AppException(e.getMessage(), e.getCause()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		log.info("Exit journalEntryWithManagementFee");
		return new ResponseEntity<ReportDetailJournalEntryResponseDto>(response, HttpStatus.OK);
	}

	/**
	 * REPORT 5: DAILY PAYMENT RECEIPT LOG
	 *
	 * @param map
	 * @param request
	 * @return
	 */
	@GetMapping(value = "rptdailypaymentreceiptlog")
	public ResponseEntity<?> dailyPaymentReceiptLog(@RequestParam(value = "month") String month, @RequestParam(value = "year")String year, @RequestParam(value = "depositDateFrom")String depositDateFrom, @RequestParam(value = "depositDateTo")String depositDateTo) {
		log.info("Enter journalEntryWithManagementFee");
		ReportDailyPayRecLogResponseDto response = new ReportDailyPayRecLogResponseDto();
		List<ReportDailyPayRecLogDto> records = new ArrayList<>();
		List<Object[]> reportData = new ArrayList<Object[]>();

		try {
			reportData = cashLogRepository.dailyPaymentReceiptLog(month, year,
					depositDateFrom, depositDateTo);
		} catch (Exception e) {
			log.error(Constants.ERROR, e);
			return new ResponseEntity<AppException>(new AppException(e.getMessage(), e.getCause()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		List<Object[]> reportData1 = new ArrayList<Object[]>();
		reportData1 = reportData;
		Double totalDeposit = 0.0;
		Double totalLockBox = 0.0;
		Double totalTelecheck = 0.0;
		Double totalEft = 0.0;
		Double totalCreditCard = 0.0;
		Double totalVault = 0.0;
		int counter = 0;
		for (Object[] tObj : reportData) {
			if(tObj[Constants.THREE]!=null) {
				totalDeposit = totalDeposit
						+ Double.parseDouble(tObj[Constants.THREE].toString());
			}
			if(tObj[Constants.FOUR]!=null) {
				totalVault = totalVault
						+ Double.parseDouble(tObj[Constants.FOUR].toString());
			}
			if(tObj[Constants.FIVE]!=null) {

				totalCreditCard = totalCreditCard
						+ Double.parseDouble(tObj[Constants.FIVE].toString());
			}

			if(tObj[Constants.SIX]!=null) {
				totalTelecheck = totalTelecheck
						+ Double.parseDouble(tObj[Constants.SIX].toString());
			}
			String[] moneySourceIds = null;
			String[] depositAmts = null;

			if (tObj[10] != null && tObj[10] != "") {
				moneySourceIds = tObj[10].toString().split(",");
			}

			if (tObj[11] != null && tObj[11] != "") {
				depositAmts = tObj[11].toString().split(",");
			}

			int index = 0;
			Double subTotalEFT = 0D;
			Double subTotalLockbox = 0D;
			if (moneySourceIds != null && moneySourceIds.length > 0) {
				for (String msi : moneySourceIds) {
					// if (index<depositAmts.length ) -- condition has been added for index should be less the total length of deposit amount.
					if (index<depositAmts.length ) {
						if (msi.equalsIgnoreCase(Constants.MONEY_SOURCE_EFT
								.toString())) {
							totalEft = totalEft
									+ Double.parseDouble(depositAmts[index]);

							subTotalEFT = subTotalEFT
									+ Double.parseDouble(depositAmts[index]);
						} else if (msi
								.equalsIgnoreCase(Constants.MONEY_SOURCE_LOCKBOX
										.toString())) {
							totalLockBox = totalLockBox
									+ Double.parseDouble(depositAmts[index]);

							subTotalLockbox = subTotalLockbox
									+ Double.parseDouble(depositAmts[index]);
						}
						reportData1.set(counter, tObj);
						index++;
					}
				}
			}
			if(tObj[Constants.NINE]!=null) {
				tObj[Constants.NINE] = subTotalEFT;
			}
			if(tObj[Constants.EIGHT]!=null){
				tObj[Constants.EIGHT] = subTotalLockbox;
			}
			counter++;
		}

		for (Object[] tObj : reportData1) {
			ReportDailyPayRecLogDto dto = new ReportDailyPayRecLogDto();
			dto.setProvider((String)tObj[0]);
			dto.setDepositDate((String)tObj[1]);
			dto.setRevenueType((String)tObj[2]);
			dto.setDepositTotal(getRoundedValue((Double)tObj[3]));
			dto.setLockBox((Double)tObj[8]);
			dto.setVault((Double)tObj[4]);
			dto.setCreditCard((Double)tObj[5]);
			dto.setTeleChecks((Double)tObj[6]);
			dto.setEft(getRoundedValue((Double)tObj[9]));
			records.add(dto);
		 }
		response.setRecords(records);
		response.setTotalDeposit(getRoundedValue(totalDeposit));
		response.setTotalLockBox(totalLockBox);
		response.setTotalVault(totalVault);
		response.setTotalCreditCard(totalCreditCard);
		response.setTotalTelecheck(totalTelecheck);
		response.setTotalEft(getRoundedValue(totalEft));
		log.info("Exit journalEntryWithManagementFee");
		return new ResponseEntity<ReportDailyPayRecLogResponseDto>(response, HttpStatus.OK);
	}
	
}


