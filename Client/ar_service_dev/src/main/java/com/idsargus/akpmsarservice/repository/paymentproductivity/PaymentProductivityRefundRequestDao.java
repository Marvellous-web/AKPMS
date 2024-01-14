//package com.idsargus.akpmsarservice.repository.paymentproductivity;
//
//import argus.domain.PaymentBatch;
//import argus.domain.paymentproductivity.PaymentProductivityRefundRequest;
//import argus.exception.ArgusException;
//
//import java.util.List;
//import java.util.Map;
//
//public interface PaymentProductivityRefundRequestDao {
//
//	/**
//	 *
//	 * @param id
//	 * @return
//	 * @throws ArgusException
//	 */
//	PaymentProductivityRefundRequest findById(Long id) throws ArgusException;
//
//	/**
//	 *
//	 * @param paymentProductivityRefundRequest
//	 * @throws ArgusException
//	 */
//	void addPaymentProductivityRefundRequest(
//			PaymentProductivityRefundRequest paymentProductivityRefundRequest)
//			throws ArgusException;
//
//	/**
//	 *
//	 * @param paymentProductivityRefundRequest
//	 * @throws ArgusException
//	 */
//	void updatePaymentProductivityRefundRequest(
//			PaymentProductivityRefundRequest paymentProductivityRefundRequest)
//			throws ArgusException;
//
//	/**
//	 *
//	 * @param orderClauses
//	 * @param whereClauses
//	 * @return
//	 * @throws ArgusException
//	 */
//	List<PaymentBatch> findAll(Map<String, String> orderClauses,
//			Map<String, String> whereClauses) throws ArgusException;
//
//	/**
//	 *
//	 * @param whereClauses
//	 * @return
//	 */
//	int totalRecord(Map<String, String> whereClauses);
//
//	/**
//	 * @param name
//	 *            It is the name of the Productivity to be searched
//	 * @param id
//	 *            It is the id of the Productivity to be searched
//	 */
//	List<PaymentProductivityRefundRequest> findAllForReport(
//			Map<String, String> orderClauses, Map<String, String> whereClauses)
//			throws ArgusException;
//
//}
