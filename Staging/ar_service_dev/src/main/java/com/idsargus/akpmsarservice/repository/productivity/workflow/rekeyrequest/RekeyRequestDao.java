//package com.idsargus.akpmsarservice.repository.productivity.workflow.rekeyrequest;
//
//import argus.domain.RekeyRequestWorkFlow;
//import argus.exception.ArgusException;
//import argus.util.RekeyRequestRecordJsonData;
//
//import java.util.List;
//import java.util.Map;
//
//public interface RekeyRequestDao {
//
//
//
//	/**
//	 *
//	 * @param id
//	 * @return
//	 * @throws ArgusException
//	 */
//	RekeyRequestWorkFlow findById(Long id) throws ArgusException;
//
//	/**
//	 *
//	 * @param codingCorrectionLogWorkFlow
//	 * @throws ArgusException
//	 */
//	void addRekeyRequestWorkFlow(RekeyRequestWorkFlow rekeyRequestWorkFlow)
//			throws ArgusException;
//
//	   /**
//	    *
//	    * @param codingCorrectionLogWorkFlow
//	    * @throws ArgusException
//	    */
//	void updateRekeyRequestWorkFlow(RekeyRequestWorkFlow rekeyRequestWorkFlow)
//			throws ArgusException;
//
//	/**
//	 *
//	 * @param id
//	 * @return
//	 * @throws ArgusException
//	 */
//	RekeyRequestWorkFlow findByProductivityId(Long id) throws ArgusException;
//
//	List<RekeyRequestWorkFlow> findAll(Map<String, String> orderClauses,
//			Map<String, String> whereClauses, boolean dependancies)
//			throws ArgusException;
//
//	int totalRecord(Map<String, String> whereClauses)
//			throws ArgusException;
//
//	public void deleteRekeyRequestRecords(List<Long> rekeyrequestRecordId)
//			throws ArgusException;
//
//	public List<RekeyRequestRecordJsonData> getRekeyRequestRecords(
//			Long rekeyRequestId) throws ArgusException;
//
//	public List<Object[]> countByStatus() throws ArgusException;
//}
