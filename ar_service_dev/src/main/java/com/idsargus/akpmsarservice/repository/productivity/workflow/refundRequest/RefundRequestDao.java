//package com.idsargus.akpmsarservice.repository.productivity.workflow.refundRequest;
//
//import argus.domain.RefundRequestWorkFlow;
//import argus.exception.ArgusException;
//
//import java.util.List;
//import java.util.Map;
//
//public interface RefundRequestDao {
//	/**
//	 *
//	 * @param id
//	 * @return
//	 */
//	RefundRequestWorkFlow findById(Long id) throws ArgusException;
//
//	/**
//	 *
//	 * @param name
//	 * @return
//	 */
//	RefundRequestWorkFlow findByName(String name) throws ArgusException;
//
//	/**
//	 *
//	 * @param orderClauses
//	 *            key=(orderBy,sortBy,offset,limit)
//	 * @param whereClauses
//	 * @return
//	 */
//
//	/**
//	 * @param name
//	 *            It is the name of the Productivity to be searched
//	 * @param id
//	 *            It is the id of the Productivity to be searched
//	 */
//	List<RefundRequestWorkFlow> findAll(Map<String, String> orderClauses,
//			Map<String, String> whereClauses, boolean dependancies)
//			throws ArgusException;
//
//	/**
//	 *
//	 * @param Productivity
//	 */
//	void addRefundRequestWorkFlow(RefundRequestWorkFlow refundRequestWorkFlow)
//			throws ArgusException;
//
//	/**
//	 *
//	 * @param Productivity
//	 */
//	void updateRefundRequestWorkFlow(RefundRequestWorkFlow refundRequestWorkFlow)
//			throws ArgusException;
//
//	/**
//	 *
//	 * @param conditionMap
//	 * @return
//	 */
//	int totalRecord(Map<String, String> conditionMap) throws ArgusException;
//
//	/**
//	 *
//	 * @param arProdId
//	 * @return
//	 * @throws ArgusException
//	 */
//	RefundRequestWorkFlow getRefundRequestByArProductivityId(int arProdId)
//			throws ArgusException;
//}
