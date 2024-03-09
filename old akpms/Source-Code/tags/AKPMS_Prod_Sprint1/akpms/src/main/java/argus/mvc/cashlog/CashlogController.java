/**
 *
 */
package argus.mvc.cashlog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
	private static final Log LOGGER = LogFactory
			.getLog(CashlogController.class);

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

	@RequestMapping(value = "rptjemngfee")
	public String journalEntryManagementFee(Map<String, Object> map,
			WebRequest request) {
		LOGGER.info("in journalEntryManagementFee");

		month = request.getParameter("month");
		year = request.getParameter("year");
		map.put("month", AkpmsUtil.getMonths().get(Integer.valueOf(month)));
		map.put("year", year);
		List<Object[]> reportData = new ArrayList<Object[]>();
		try {
			reportData = cashlogDao.journalEntryManagementFee(month, year);

		} catch (Exception e) {
			LOGGER.error(Constants.ERROR, e);
		}
		map.put("reportData", reportData);

		LOGGER.info("out journalEntryManagementFee");

		return "rptJEMngFee";
	}

	@RequestMapping(value = "rptentrysummary")
	public String journalEntrySummary(Map<String, Object> map,
			WebRequest request) {
		LOGGER.info("in journalEntrySummary");

		month = request.getParameter("month");
		year = request.getParameter("year");
		map.put("month", AkpmsUtil.getMonths().get(Integer.valueOf(month)));
		map.put("year", year);
		List<Object[]> reportData = new ArrayList<Object[]>();
		try {
			reportData = cashlogDao.journalEntrySummary(month, year);

		} catch (Exception e) {
			LOGGER.error(Constants.ERROR, e);
		}
		map.put("reportData", reportData);

		LOGGER.info("out journalEntrySummary");
		return "rptEntrySummary";
	}

	@RequestMapping(value = "rptentrydetailed")
	public String journalEntryDetailed(Map<String, Object> map,
			WebRequest request) {
		LOGGER.info("in journalEntryDetailed");

		month = request.getParameter("month");
		year = request.getParameter("year");
		map.put("month", AkpmsUtil.getMonths().get(Integer.valueOf(month)));
		map.put("year", year);

		List<Object[]> reportData = new ArrayList<Object[]>();
		try {
			reportData = cashlogDao.journalEntryDetailed(month, year);

		} catch (Exception e) {
			LOGGER.error(Constants.ERROR, e);
		}
		map.put("reportData", reportData);

		LOGGER.info("out journalEntryDetailed");

		return "rptEntryDetailed";
	}

	@RequestMapping(value = "rptentrywithmngfee")
	public String journalEntryWithManagementFee(Map<String, Object> map,
			WebRequest request) {
		LOGGER.info("in journalEntryWithManagementFee");

		month = request.getParameter("month");
		year = request.getParameter("year");
		map.put("month", AkpmsUtil.getMonths().get(Integer.valueOf(month)));
		map.put("year", year);
		List<Object[]> reportData = new ArrayList<Object[]>();
		try {
			reportData = cashlogDao.journalEntryWithManagementFee(month, year);

		} catch (Exception e) {
			LOGGER.error(Constants.ERROR, e);
		}
		map.put("reportData", reportData);

		LOGGER.info("out journalEntryWithManagementFee");

		return "rptEntryWithMngFee";
	}

	@RequestMapping(value = "rptdailypaymentreceiptlog")
	public String dailyPaymentReceiptLog(Map<String, Object> map,
			WebRequest request) {
		LOGGER.info("in journalEntryWithManagementFee");

		month = request.getParameter("month");
		year = request.getParameter("year");
		map.put("month", AkpmsUtil.getMonths().get(Integer.valueOf(month)));
		map.put("year", year);
		List<Object[]> reportData = new ArrayList<Object[]>();
		try {
			reportData = cashlogDao.dailyPaymentReceiptLog(month, year);

		} catch (Exception e) {
			LOGGER.error(Constants.ERROR, e);
		}
		Map<String, Object[]> treeMap = new TreeMap<String, Object[]>();
		Double totalDeposit = 0.0;
		Double totalLockBox = 0.0;
		Double totalTeleCards = 0.0;
		Double totalEft = 0.0;
		Double totalMail = 0.0;
		for (Object[] tObj : reportData) {
			totalDeposit = totalDeposit
					+ Double.parseDouble(tObj[Constants.FIVE].toString());
			totalLockBox = totalLockBox
					+ Double.parseDouble(tObj[Constants.NINE].toString());
			totalTeleCards = totalTeleCards
					+ Double.parseDouble(tObj[Constants.ELEVEN].toString());
			totalEft = totalEft
					+ Double.parseDouble(tObj[Constants.EIGHT].toString());
			totalMail = totalMail
					+ Double.parseDouble(tObj[Constants.TEN].toString());
		}
		for (Object[] tObj : reportData) {
			treeMap.put(tObj[Constants.SIX].toString().trim(), tObj);
		}
		map.put("totalDeposit", totalDeposit);
		map.put("totalLockBox", totalLockBox);
		map.put("totalTeleChecks", totalTeleCards);
		map.put("totalEft", totalEft);
		map.put("totalMail", totalMail);
		map.put("reportData", reportData);

		LOGGER.info("out journalEntryWithManagementFee");

		return "rptDailyPaymentReceiptLog";
	}
}
