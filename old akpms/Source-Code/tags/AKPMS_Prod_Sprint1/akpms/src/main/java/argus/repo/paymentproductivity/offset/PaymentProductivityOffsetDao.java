/**
 *
 */
package argus.repo.paymentproductivity.offset;

import java.util.List;
import java.util.Map;

import argus.domain.paymentproductivity.PaymentProductivityOffset;
import argus.exception.ArgusException;
import argus.util.OffsetRecordJsonData;

/**
 * @author rajiv.k
 *
 */
public interface PaymentProductivityOffsetDao {

	/**
	 *
	 * @param id
	 * @return
	 */
	PaymentProductivityOffset findById(Long id, boolean dependency)
			throws ArgusException;

	/**
	 *
	 * @param orderClauses
	 *            key=(orderBy,sortBy,offset,limit)
	 * @param whereClauses
	 * @return
	 */

	/**
	 * @param name
	 *            It is the name of the Productivity to be searched
	 * @param id
	 *            It is the id of the Productivity to be searched
	 */
	List<PaymentProductivityOffset> findAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses, boolean dependancies)
			throws ArgusException;


	/**
	 *
	 * @param Productivity
	 */
	void addPaymentProductivityOffset(
			PaymentProductivityOffset paymentProductivityOffset)
			throws ArgusException;

	/**
	 *
	 * @param Productivity
	 */
	void updatePaymentProductivityOffset(
			PaymentProductivityOffset paymentProductivityOffset)
			throws ArgusException;

	/**
	 *
	 * @param prodId
	 * @return
	 * @throws ArgusException
	 */
	PaymentProductivityOffset findOffsetByProdId(long prodId)
			throws ArgusException;


	/**
	 *
	 * @param conditionMap
	 * @return
	 */
	int totalRecord(Map<String, String> conditionMap) throws ArgusException;

	/**
	 * @param name
	 *            It is the name of the Productivity to be searched
	 * @param id
	 *            It is the id of the Productivity to be searched
	 */
	List<OffsetRecordJsonData> getPostedRecord(Long offsetId);

	/**
	 *
	 * @param toRemoveOffsetRecordList
	 *            It is the list of offset records to be removed
	 *
	 * @throws ArgusException
	 */
	void deleteOffsetRecords(List<Long> toRemoveOffsetRecordList)
			throws ArgusException;

	/**
	 *
	 * @param batchId
	 * @return
	 * @throws ArgusException
	 */
	List<PaymentProductivityOffset> getOffsetByBatchId(Long batchId)
			throws ArgusException;


}
