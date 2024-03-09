/**
 *
 */
package argus.repo.cashlog;

import java.util.List;

import argus.exception.ArgusException;

/**
 * @author bhupender.s
 *
 */
public interface CashlogDao {

	List<Object[]> journalEntryManagementFee(String month, String year)
			throws ArgusException;

	List<Object[]> journalEntrySummary(String month, String year)
			throws ArgusException;

	List<Object[]> journalEntryDetailed(String month, String year)
			throws ArgusException;

	List<Object[]> journalEntryWithManagementFee(String month,
			String year) throws ArgusException;

	List<Object[]> dailyPaymentReceiptLog(String month, String year)
			throws ArgusException;

}
