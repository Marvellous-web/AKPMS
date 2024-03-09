package argus.repo.chargeProductivity;

import java.util.List;
import java.util.Map;

import argus.domain.ChargeProdReject;
import argus.exception.ArgusException;

public interface ChargeProdRejectDao {

	/**
	 *
	 * @param ChargeProdReject
	 * @throws ArgusException
	 */
	void save(ChargeProdReject chargeProdReject) throws ArgusException;

	/**
	 *
	 * @param ChargeProdReject
	 * @throws ArgusException
	 */
	void update(ChargeProdReject chargeProdReject) throws ArgusException;

	/**
	 *
	 * @param id
	 * @param dependencies
	 * @return ChargeProdReject
	 * @throws ArgusException
	 */
	ChargeProdReject getChargeProdRejectById(Long id,
			boolean dependencies) throws ArgusException;

	/**
	 *
	 * @param conditionMap
	 * @return
	 * @throws ArgusException
	 */
	int totalRecords(Map<String, String> conditionMap)
			throws ArgusException;

	/**
	 *
	 * @param orderClauses
	 * @param whereClauses
	 * @return
	 * @throws ArgusException
	 */
	List<ChargeProdReject> findAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses, boolean dependancies)
			throws ArgusException;

	long getRejectionCount(Long batchId) throws ArgusException;

	long getResolvedRejectionCount(Long batchId) throws ArgusException;

	long getResolvedWithDummyCPTCount(Long batchId)
			throws ArgusException;

	long getFirstRequestDueCount(Long batchId) throws ArgusException;

	long getSecondRequestDueCount(Long batchId) throws ArgusException;

	long getNumberOfFirstRequestsCount(Long batchId)
			throws ArgusException;

	long getNumberOfSecondRequestsCount(Long batchId)
			throws ArgusException;

	long hasRejectionDone(Long batchId) throws ArgusException;

	public List<Object[]> getAllRejectBatchesByStatus() throws ArgusException;

}
