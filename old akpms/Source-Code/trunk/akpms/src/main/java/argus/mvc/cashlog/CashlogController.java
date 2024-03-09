/**
 *
 */
package argus.mvc.cashlog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import argus.repo.cashlog.CashlogDao;
import argus.util.AkpmsUtil;
import argus.util.Constants;

/**
 * @author bhupender.s
 *
 */

@Controller
@RequestMapping(value = "/cashlog")
public class CashlogController {

	private static final String MONTHS = "months";
	private static final String YEARS = "years";
	private static final Logger LOGGER = Logger
			.getLogger(CashlogController.class);

	@Autowired
	private CashlogDao cashlogDao;

	private String month;

	private String year;

	@RequestMapping(value = "/")
	public String cashlogPage(Map<String, Object> map) {
		map.put(MONTHS, AkpmsUtil.getMonths());
		map.put(YEARS, AkpmsUtil.getYears());

		return "cashlogIndex";
	}

	/**
	 * REPORT -1 : JOURNAL ENTRIES MANAGEMENT FEE [FOR THE MONTH OF ****-**]
	 *
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "rptjemngfee", method = RequestMethod.GET)
	public String journalEntryManagementFee(Map<String, Object> map,
			WebRequest request) {
		LOGGER.info("in journalEntryManagementFee");

		month = request.getParameter("month");
		year = request.getParameter("year");
		int month_numeric = Integer.valueOf(month);
		map.put("month", AkpmsUtil.getMonths().get(month_numeric));
		if(month_numeric < 10){
			month = "0" + month;
		}
		map.put("month_numeric", month);
		map.put("year", year);
		List<Object[]> reportData = new ArrayList<Object[]>();
		try {
			reportData = cashlogDao.journalEntryManagementFee(month, year);
		} catch (Exception e) {
			LOGGER.error(Constants.ERROR, e);
		}
		map.put("reportData", reportData);

		LOGGER.info("out journalEntryManagementFee");

		map.put("SHOW_FOOTER", false);
		return "rptJEMngFeePopup";
	}

	/**
	 * REPORT -2: JOURNAL ENTRIES SUMMARY [FOR THE MONTH OF ****-**]
	 *
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "rptentrysummary")
	public String journalEntrySummary(Map<String, Object> map,
			WebRequest request) {
		LOGGER.info("in journalEntrySummary");

		month = request.getParameter("month");
		year = request.getParameter("year");
		int month_numeric = Integer.valueOf(month);
		map.put("month", AkpmsUtil.getMonths().get(month_numeric));
		if(month_numeric < 10){
			month = "0" + month;
		}
		map.put("month_numeric", month);
		map.put("year", year);
		List<Object[]> reportData = new ArrayList<Object[]>();
		try {
			// reportData = cashlogDao.journalEntrySummary(month, year);
			reportData = cashlogDao.journalEntrySummary(month, year);
		} catch (Exception e) {
			LOGGER.error(Constants.ERROR, e);
		}
		map.put("reportData", reportData);
		map.put("SHOW_FOOTER", false);
		LOGGER.info("out journalEntrySummary");
		return "rptEntrySummaryPopup";
	}

	/**
	 * REPORT -3: DETAILED JOURNAL ENTRIES [FOR THE MONTH OF ****-**]
	 *
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "rptentrydetailed")
	public String journalEntryDetailed(Map<String, Object> map,
			WebRequest request) {
		LOGGER.info("in journalEntryDetailed");

		month = request.getParameter("month");
		year = request.getParameter("year");

		map.put("month", AkpmsUtil.getMonths().get(Integer.valueOf(month)));
		map.put("year", year);

		String ticketNumber = null;
		if (request.getParameter("chkTicketNumber") != null) {
			ticketNumber = request.getParameter("chkTicketNumber");
		}

		map.put("bilingMonth", AkpmsUtil.getMonths()
				.get(Integer.valueOf(month)));
		map.put("billingYear", year);
		map.put("showTicketNumber", ticketNumber);

		List<Object[]> reportData = new ArrayList<Object[]>();
		try {
			reportData = cashlogDao.journalEntryDetailedWithManagementFee(
					month, year);

		} catch (Exception e) {
			LOGGER.error(Constants.ERROR, e);
		}
		map.put("reportData", reportData);

		LOGGER.info("out journalEntryDetailed");
		map.put("SHOW_FOOTER", false);
		return "rptEntryDetailedPopup";
	}

	/**
	 * REPORT -4: DETAILED JOURNAL ENTRIES WITH MANAGEMENT FEES [FOR THE MONTH
	 * OF ****-**]
	 *
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "rptentrywithmngfee")
	public String journalEntryWithManagementFee(Map<String, Object> map,
			WebRequest request) {
		LOGGER.info("in journalEntryWithManagementFee");

		month = request.getParameter("month");
		year = request.getParameter("year");
		map.put("month", AkpmsUtil.getMonths().get(Integer.valueOf(month)));
		map.put("month_numeric", month);
		map.put("year", year);

		map.put("bilingMonth", AkpmsUtil.getMonths()
				.get(Integer.valueOf(month)));
		map.put("billingYear", year);

		String ticketNumber = null;
		if (request.getParameter("chkTicketNumber") != null) {
			ticketNumber = request.getParameter("chkTicketNumber");
		}
		map.put("showTicketNumber", ticketNumber);

		List<Object[]> reportData = new ArrayList<Object[]>();
		try {
			reportData = cashlogDao.journalEntryDetailedWithManagementFee(
					month, year);

		} catch (Exception e) {
			LOGGER.error(Constants.ERROR, e);
		}
		map.put("reportData", reportData);

		LOGGER.info("out journalEntryWithManagementFee");
		map.put("SHOW_FOOTER", false);
		return "rptEntryWithMngFeePopup";
	}

	/**
	 * REPORT 5: DAILY PAYMENT RECEIPT LOG
	 *
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "rptdailypaymentreceiptlog")
	public String dailyPaymentReceiptLog(Map<String, Object> map,
			WebRequest request) {
		LOGGER.info("in journalEntryWithManagementFee");

		month = request.getParameter("month");
		year = request.getParameter("year");

		String depositDateFrom = request.getParameter("depositDateFrom");
		String depositDateTo = request.getParameter("depositDateTo");

		map.put("month", AkpmsUtil.getMonths().get(Integer.valueOf(month)));
		map.put("year", year);

		map.put("depositDateFrom", depositDateFrom);
		map.put("depositDateTo", depositDateTo);

		List<Object[]> reportData = new ArrayList<Object[]>();

		try {
			reportData = cashlogDao.dailyPaymentReceiptLog(month, year,
					depositDateFrom, depositDateTo);
		} catch (Exception e) {
			LOGGER.error(Constants.ERROR, e);
		}

		// Map<String, Object[]> treeMap = new TreeMap<String, Object[]>();
		List<Object[]> reportData1 = new ArrayList<Object[]>();
		reportData1 = reportData;
		Double totalDeposit = 0.0;
		Double totalLockBox = 0.0;
		Double totalTelecheck = 0.0;
		Double totalEft = 0.0;
		// Double totalMail = 0.0;
		Double totalCreditCard = 0.0;
		Double totalVault = 0.0;
		int counter = 0;
		for (Object[] tObj : reportData) {
			totalDeposit = totalDeposit
					+ Double.parseDouble(tObj[Constants.THREE].toString());
			totalVault = totalVault
					+ Double.parseDouble(tObj[Constants.FOUR].toString());
			totalCreditCard = totalCreditCard
					+ Double.parseDouble(tObj[Constants.FIVE].toString());
			totalTelecheck = totalTelecheck
					+ Double.parseDouble(tObj[Constants.SIX].toString());

			String[] moneySourceIds = null;
			String[] depositAmts = null;

			if (tObj[10] != null && tObj[10] != "") {
				LOGGER.info("tObj[10] ::" + tObj[10].toString());
				LOGGER.info("tObj[10] ::" + tObj[10].getClass());
				moneySourceIds = tObj[10].toString().split(",");
				LOGGER.info("moneySourceIds ::" + moneySourceIds);
			}

			if (tObj[11] != null && tObj[11] != "") {
				depositAmts = tObj[11].toString().split(",");
			}

			int index = 0;
			Double subTotalEFT = 0D;
			Double subTotalLockbox = 0D;
			if (moneySourceIds != null && moneySourceIds.length > 0) {
				for (String msi : moneySourceIds) {
					LOGGER.info("moneySourceId ::" + msi);
					if (msi.equalsIgnoreCase(Constants.MONEY_SOURCE_EFT
							.toString())) {
						LOGGER.info("eft ");
						totalEft = totalEft
								+ Double.parseDouble(depositAmts[index]);

						subTotalEFT = subTotalEFT
								+ Double.parseDouble(depositAmts[index]);
						// totalEft = totalEft
						// + Double.parseDouble(depositAmts[index]);

						// tObj[Constants.NINE] = tObj[Constants.THREE];
						// tObj[Constants.NINE] = depositAmts[index];
						// tObj[Constants.NINE] = totalEft;
						// tObj[Constants.EIGHT] = "0.0";
						// reportData1.set(counter, tObj);
					} else if (msi
							.equalsIgnoreCase(Constants.MONEY_SOURCE_LOCKBOX
									.toString())) {
						LOGGER.info("lockbox ");
						totalLockBox = totalLockBox
								+ Double.parseDouble(depositAmts[index]);

						subTotalLockbox = subTotalLockbox
								+ Double.parseDouble(depositAmts[index]);
						// totalLockBox = totalLockBox
						// + Double.parseDouble(depositAmts[index]);

						// tObj[Constants.EIGHT] = tObj[Constants.THREE];
						// tObj[Constants.EIGHT] = depositAmts[index];
						// tObj[Constants.EIGHT] = totalLockBox;
						// tObj[Constants.NINE] = "0.0";
						// reportData1.set(counter, tObj);
					}
					reportData1.set(counter, tObj);
					index++;
				}
			}
			tObj[Constants.NINE] = subTotalEFT;
			tObj[Constants.EIGHT] = subTotalLockbox;
			// LOGGER.info("seven :: " + tObj[Constants.SEVEN]);
			// if (Long.parseLong(tObj[Constants.SEVEN].toString()) ==
			// Constants.MONEY_SOURCE_EFT) {
			// LOGGER.info("eft ");
			// totalEft = totalEft
			// + Double.parseDouble(tObj[Constants.THREE].toString());
			// tObj[Constants.NINE] = tObj[Constants.THREE];
			// tObj[Constants.EIGHT] = "0.0";
			// reportData1.set(counter, tObj);
			// } else if (Long.parseLong(tObj[Constants.SEVEN].toString()) ==
			// Constants.MONEY_SOURCE_LOCKBOX) {
			// LOGGER.info("lockbox ");
			// totalLockBox = totalLockBox
			// + Double.parseDouble(tObj[Constants.THREE].toString());
			// tObj[Constants.EIGHT] = tObj[Constants.THREE];
			// tObj[Constants.NINE] = "0.0";
			// reportData1.set(counter, tObj);
			// }

			// totalMail = totalMail
			// + Double.parseDouble(tObj[Constants.TEN].toString());
			counter++;
		}

		// for (Object[] tObj : reportData) {
		// treeMap.put(tObj[Constants.SIX].toString().trim(), tObj);
		// }
		map.put("totalDeposit", totalDeposit);

		map.put("totalVault", totalVault);
		map.put("totalCreditCard", totalCreditCard);
		map.put("totalTelecheck", totalTelecheck);

		map.put("totalLockBox", totalLockBox);
		map.put("totalEft", totalEft);
		// map.put("totalMail", totalMail);
		map.put("reportData", reportData1);

		LOGGER.info("out journalEntryWithManagementFee");
		map.put("SHOW_FOOTER", false);
		return "rptDailyPaymentReceiptLogPopup";
	}
	
}
