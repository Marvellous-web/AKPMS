/**
 *
 */
package argus.repo.paymentproductivity.offset;

import java.util.List;
import java.util.Map;

import argus.domain.Files;
import argus.domain.paymentproductivity.PaymentPostingByOffSetManager;
import argus.exception.ArgusException;
import argus.util.OffsetPostingRecordJsonData;

/**
 * @author rajiv.k
 *
 */
public interface PaymentOffsetManagerDao {

	/**
	 *
	 * @param id
	 * @return
	 */
	PaymentPostingByOffSetManager findById(Long id, boolean dependency)
			throws ArgusException;

	/**
	 *
	 * @param Productivity
	 */
	void addPaymentPostingByOffSetManager(
			PaymentPostingByOffSetManager offSetManager)
			throws ArgusException;

	/**
	 *
	 * @param Productivity
	 */
	void updatePaymentPostingByOffSetManager(
			PaymentPostingByOffSetManager offSetManager)
			throws ArgusException;


	List<OffsetPostingRecordJsonData> getPostedRecord(Long offsetId);

	Long hasPosted(long offsetId)
			throws ArgusException;


		/**
	 * 
	 * @param OffsetPostingRecord
	 * @throws Exception
	 */
	void deleteOffsetPostingRecords(List<Long> offsetPostingRecordId)
			throws ArgusException;


	int totalRecordForList(Map<String, String> whereClauses)
			throws ArgusException;

	List<PaymentPostingByOffSetManager> findAll(
			Map<String, String> orderClauses, Map<String, String> whereClauses,
			boolean loadDependancy)
			throws ArgusException;

	List<PaymentPostingByOffSetManager> getPostedOffsetByBatchId(Long batchId)
			throws ArgusException;

	void saveAttachement(Files file) throws ArgusException;

	void updateAttachement(Files file) throws ArgusException;

}
