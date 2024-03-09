///**
// *
// */
//package com.idsargus.akpmsarservice.repository.paymentproductivity.offset;
//
//import argus.domain.Files;
//import argus.domain.paymentproductivity.OffsetPostingRecord;
//import argus.domain.paymentproductivity.PaymentPostingByOffSetManager;
//import argus.exception.ArgusException;
//import argus.util.AkpmsUtil;
//import argus.util.Constants;
//import argus.util.OffsetPostingRecordJsonData;
//import org.apache.log4j.Logger;
//import org.hibernate.Hibernate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import javax.persistence.Query;
//import javax.persistence.TypedQuery;
//import javax.persistence.criteria.*;
//import java.util.*;
//
///**
// * @author vishal.joshi
// *
// */
//@Repository
//@Transactional
//public class PaymentOffsetManagerDaoImpl implements PaymentOffsetManagerDao {
//
//	private static final Logger LOGGER = Logger
//			.getLogger(PaymentOffsetManagerDaoImpl.class);
//
//	@Autowired
//	private EntityManager em;
//
//	/*
//	 * (non-Javadoc)
//	 *
//	 * @see
//	 * argus.repo.paymentproductivity.offset.PaymentOffsetManagerDao#findById
//	 * (java.lang.Long)
//	 */
//	@Override
//	public PaymentPostingByOffSetManager findById(Long id, boolean dependency)
//			throws ArgusException {
//
//		PaymentPostingByOffSetManager offSetManager = em.find(
//				PaymentPostingByOffSetManager.class, id);
//
//		if (dependency && offSetManager != null) {
//			Hibernate.initialize(offSetManager.getPostingRecords());
//		}
//
//		return offSetManager;
//	}
//
//
//	@Override
//	public void addPaymentPostingByOffSetManager(
//			PaymentPostingByOffSetManager offSetManager) throws ArgusException {
//		em.persist(offSetManager);
//		for (OffsetPostingRecord offsetPostingRecord : offSetManager
//				.getPostingRecords()) {
//			offsetPostingRecord.setOffSetManager(offSetManager);
//			em.persist(offsetPostingRecord);
//		}
//		LOGGER.info("Offset is added by Manager");
//
//	}
//
//	/*
//	 * (non-Javadoc)
//	 *
//	 * @see argus.repo.paymentproductivity.offset.PaymentOffsetManagerDao#
//	 * updatePaymentPostingByOffSetManager
//	 * (argus.domain.paymentproductivity.PaymentPostingByOffSetManager)
//	 */
//	@Override
//	public void updatePaymentPostingByOffSetManager(
//			PaymentPostingByOffSetManager offSetManager) throws ArgusException {
//		em.merge(offSetManager);
//		LOGGER.info("Offset is updated  by Manager");
//
//	}
//
//	/**
//	 * to get total records
//	 */
//
//	@Override
//	public List<OffsetPostingRecordJsonData> getPostedRecord(Long offsetId) {
//		LOGGER.info(" in method [getPostedRecord]");
//		List<OffsetPostingRecord> records = new ArrayList<OffsetPostingRecord>();
//		List<OffsetPostingRecordJsonData> postedRecords = new ArrayList<OffsetPostingRecordJsonData>();
//		StringBuilder queryString = new StringBuilder();
//		queryString
//				.append("FROM OffsetPostingRecord As offset where offset.offSetManager="
//						+ offsetId);
//
//		TypedQuery<OffsetPostingRecord> query = em.createQuery(
//				queryString.toString(), OffsetPostingRecord.class);
//
//		records = query.getResultList();
//
//		for (OffsetPostingRecord offsetRecord : records) {
//			OffsetPostingRecordJsonData jsonData = new OffsetPostingRecordJsonData();
//			jsonData.setCpt(offsetRecord.getCpt());
//			jsonData.setAmount(offsetRecord.getAmount());
//			jsonData.setDosFrom(AkpmsUtil.akpmsDateFormat(
//					offsetRecord.getDosFrom(), Constants.DATE_FORMAT));
//			jsonData.setDosTo(AkpmsUtil.akpmsDateFormat(
//					offsetRecord.getDosTo(), Constants.DATE_FORMAT));
//			jsonData.setOffsetId(offsetId);
//			postedRecords.add(jsonData);
//
//		}
//		return postedRecords;
//	}
//
//	@Override
//	public Long hasPosted(long offsetId) throws ArgusException {
//		LOGGER.info(" in method [hasPosted] for OffsetId = " + offsetId);
//
//		CriteriaBuilder builder = em.getCriteriaBuilder();
//		CriteriaQuery<PaymentPostingByOffSetManager> criteria = builder
//				.createQuery(PaymentPostingByOffSetManager.class);
//		Root<PaymentPostingByOffSetManager> postedRecord = criteria
//				.from(PaymentPostingByOffSetManager.class);
//		criteria.select(postedRecord).where(
//				builder.equal(
//						postedRecord.get(Constants.OFFSET).get(Constants.ID),
//						offsetId));
//		return em.createQuery(criteria).getSingleResult().getId();
//
//	}
//
//	@Override
//	public List<PaymentPostingByOffSetManager> findAll(
//			Map<String, String> orderClauses, Map<String, String> whereClauses,
//			boolean laodDependacy)
//			throws ArgusException {
//		List<PaymentPostingByOffSetManager> ret = null;
//		try {
//			CriteriaBuilder builder = em.getCriteriaBuilder();
//			CriteriaQuery<PaymentPostingByOffSetManager> userCriteriaQuery = builder
//					.createQuery(PaymentPostingByOffSetManager.class);
//			Root<PaymentPostingByOffSetManager> from = userCriteriaQuery
//					.from(PaymentPostingByOffSetManager.class);
//			CriteriaQuery<PaymentPostingByOffSetManager> select = userCriteriaQuery
//					.select(from);
//
//			List<Predicate> predicateList = new ArrayList<Predicate>();
//
//			if (whereClauses != null && whereClauses.size() > 0) {
//				Set<String> key = whereClauses.keySet();
//				for (String field : key) {
//					if (field.equalsIgnoreCase(Constants.TICKET_NUMBER)) {
//						LOGGER.info("found Ticket no  = "
//								+ whereClauses.get(field));
//						predicateList
//								.add(builder.and(builder.equal(
//										from.get(Constants.PAYMENT_BATCH).get(
//												Constants.ID),
//										Long.parseLong(whereClauses
//												.get(Constants.TICKET_NUMBER)))));
//					}
//					if (field.equalsIgnoreCase(Constants.POSTED_BY_ID)) {
//						LOGGER.info("found Posted By value  = "
//								+ whereClauses.get(field));
//
//						predicateList
//								.add(builder.and(builder.equal(
//										from.get("createdBy").get(Constants.ID),
//										Long.parseLong(whereClauses
//												.get(Constants.POSTED_BY_ID)))));
//					}
//
//					// date posted from
//					if (field.equalsIgnoreCase(Constants.DATE_POSTED_FROM)) {
//						LOGGER.info("found DATE_POSTED_FROM = "
//								+ whereClauses.get(Constants.DATE_POSTED_FROM));
//
//						Date fromDate = AkpmsUtil
//								.akpmsNewDateFormat(whereClauses.get(field));
//						Path<Date> dateReceivedPath = from.get("createdOn");
//						predicateList.add(builder.greaterThanOrEqualTo(
//								dateReceivedPath, fromDate));
//
//					}
//					// date posted to
//					if (field.equalsIgnoreCase(Constants.DATE_POSTED_TO)) {
//						LOGGER.info("found DATE_POSTED_TO = "
//								+ whereClauses.get(Constants.DATE_POSTED_TO));
//						Date toDate = AkpmsUtil.getFormattedDate(AkpmsUtil
//								.akpmsNewDateFormat(whereClauses.get(field)));
//
//						Path<Date> dateReceivedPath = from.get("createdOn");
//						predicateList.add(builder.lessThanOrEqualTo(
//								dateReceivedPath, toDate));
//					}
//					if (field.equalsIgnoreCase(Constants.CHECK_NUMBER)) {
//						LOGGER.info("found Prod type value  = "
//								+ whereClauses.get(field));
//
//						predicateList.add(builder.and(builder.equal(
//								from.get(Constants.CHECK_NUMBER),
//								whereClauses.get(Constants.CHECK_NUMBER))));
//
//					}
//
//				}
//
//			}
//			select.where(builder.and(predicateList
//					.toArray(new Predicate[predicateList.size()])));
//			select.distinct(true);
//
//			if (orderClauses != null) {
//				if (orderClauses.get(Constants.ORDER_BY) != null) {
//					if (orderClauses.get(Constants.ORDER_BY).equalsIgnoreCase(
//							"batchId")) {
//						if (orderClauses.get(Constants.SORT_BY) != null
//								&& orderClauses.get(Constants.SORT_BY)
//										.equalsIgnoreCase("desc")) {
//							select.orderBy(builder.desc(from.get(
//									Constants.PAYMENT_BATCH).get(Constants.ID)));
//						} else {
//							select.orderBy(builder.asc(from.get(
//									Constants.PAYMENT_BATCH).get(Constants.ID)));
//
//						}
//					} else if (orderClauses.get(Constants.ORDER_BY)
//							.equalsIgnoreCase("insuranceName")) {
//						if (orderClauses.get(Constants.SORT_BY) != null
//								&& orderClauses.get(Constants.SORT_BY)
//										.equalsIgnoreCase("desc")) {
//							select.orderBy(builder.desc(from
//									.get(Constants.PAYMENT_BATCH)
//									.get("insurance").get(Constants.BY_NAME)));
//						} else {
//							select.orderBy(builder.asc(from
//									.get(Constants.PAYMENT_BATCH)
//									.get("insurance").get(Constants.BY_NAME)));
//
//						}
//					} else if (orderClauses.get(Constants.ORDER_BY)
//							.equalsIgnoreCase(Constants.DOCTOR)) {
//						if (orderClauses.get(Constants.SORT_BY) != null
//								&& orderClauses.get(Constants.SORT_BY)
//										.equalsIgnoreCase("desc")) {
//							select.orderBy(builder.desc(from
//									.get(Constants.PAYMENT_BATCH)
//									.get(Constants.DOCTOR)
//									.get(Constants.BY_NAME)));
//						} else {
//							select.orderBy(builder.asc(from
//									.get(Constants.PAYMENT_BATCH)
//									.get(Constants.DOCTOR)
//									.get(Constants.BY_NAME)));
//
//						}
//					}
//
//					else {
//						if (orderClauses.get(Constants.SORT_BY) != null
//								&& orderClauses.get(Constants.SORT_BY)
//										.equalsIgnoreCase("desc")) {
//							select.orderBy(builder.desc(from.get(orderClauses
//									.get(Constants.ORDER_BY))));
//						} else {
//							select.orderBy(builder.asc(from.get(orderClauses
//									.get(Constants.ORDER_BY))));
//
//						}
//					}
//				} else {
//					select.orderBy(builder.asc(from
//							.get(Constants.PAYMENT_BATCH).get(Constants.ID)));
//				}
//			}
//
//			TypedQuery<PaymentPostingByOffSetManager> query = em
//					.createQuery(select);
//
//			if (orderClauses != null && orderClauses.get("offset") != null
//					&& orderClauses.get("limit") != null) {
//				query.setFirstResult(Integer.parseInt(orderClauses
//						.get("offset")));
//				query.setMaxResults(Integer.parseInt(orderClauses.get("limit")));
//			}
//
//			LOGGER.info("SQL:: " + query.toString());
//			ret = query.getResultList();
//
//			if (ret != null) {
//				Iterator<PaymentPostingByOffSetManager> offsetyList = ret
//						.iterator();
//				while (offsetyList.hasNext()) {
//					PaymentPostingByOffSetManager offsetDB = offsetyList.next();
//					Hibernate.initialize(offsetDB.getPaymentBatch());
//					if (laodDependacy) {
//						Hibernate.initialize(offsetDB.getPostingRecords());
//					}
//				}
//			}
//
//		} catch (Exception e) {
//			LOGGER.error(Constants.EXCEPTION, e);
//			throw new ArgusException(e.getMessage(), e);
//		}
//		return ret;
//	}
//
//	@Override
//	public int totalRecordForList(Map<String, String> whereClauses)
//			throws ArgusException {
//		int ret = 0;
//		try {
//			CriteriaBuilder builder = em.getCriteriaBuilder();
//			CriteriaQuery<Long> cQuery = builder.createQuery(Long.class);
//			Root<PaymentPostingByOffSetManager> from = cQuery
//					.from(PaymentPostingByOffSetManager.class);
//			CriteriaQuery<Long> select = cQuery.select(builder.count(from));
//
//			List<Predicate> predicateList = new ArrayList<Predicate>();
//
//			if (whereClauses != null && whereClauses.size() > 0) {
//				Set<String> key = whereClauses.keySet();
//				for (String field : key) {
//					if (field.equalsIgnoreCase(Constants.TICKET_NUMBER)) {
//						LOGGER.info("found Ticket no  = "
//								+ whereClauses.get(field));
//						predicateList
//								.add(builder.and(builder.equal(
//										from.get(Constants.PAYMENT_BATCH).get(
//												Constants.ID),
//										Long.parseLong(whereClauses
//												.get(Constants.TICKET_NUMBER)))));
//					}
//					if (field.equalsIgnoreCase(Constants.POSTED_BY_ID)) {
//						LOGGER.info("found Posted By value  = "
//								+ whereClauses.get(field));
//
//						predicateList
//								.add(builder.and(builder.equal(
//										from.get("createdBy").get(Constants.ID),
//										Long.parseLong(whereClauses
//												.get(Constants.POSTED_BY_ID)))));
//					}
//
//					// date posted from
//					if (field.equalsIgnoreCase(Constants.DATE_POSTED_FROM)) {
//						LOGGER.info("found DATE_POSTED_FROM = "
//								+ whereClauses.get(Constants.DATE_POSTED_FROM));
//
//						Date fromDate = AkpmsUtil
//								.akpmsNewDateFormat(whereClauses.get(field));
//						Path<Date> dateReceivedPath = from.get("createdOn");
//						predicateList.add(builder.greaterThanOrEqualTo(
//								dateReceivedPath, fromDate));
//
//					}
//					// date posted to
//					if (field.equalsIgnoreCase(Constants.DATE_POSTED_TO)) {
//						LOGGER.info("found DATE_POSTED_TO = "
//								+ whereClauses.get(Constants.DATE_POSTED_TO));
//						Date toDate = AkpmsUtil.getFormattedDate(AkpmsUtil
//								.akpmsNewDateFormat(whereClauses.get(field)));
//
//						Path<Date> dateReceivedPath = from.get("createdOn");
//						predicateList.add(builder.lessThanOrEqualTo(
//								dateReceivedPath, toDate));
//					}
//					if (field.equalsIgnoreCase(Constants.CHECK_NUMBER)) {
//						LOGGER.info("found Check number is  = "
//								+ whereClauses.get(field));
//
//						predicateList.add(builder.and(builder.equal(
//								from.get(Constants.CHECK_NUMBER),
//								whereClauses.get(Constants.CHECK_NUMBER))));
//
//					}
//
//				}
//
//			}
//			select.where(builder.and(predicateList
//					.toArray(new Predicate[predicateList.size()])));
//			select.distinct(true);
//			TypedQuery<Long> query = em.createQuery(select);
//
//			ret = query.getSingleResult().intValue();
//		} catch (Exception e) {
//			LOGGER.error(Constants.EXCEPTION, e);
//			throw new ArgusException(e.getMessage(), e);
//		}
//		LOGGER.info("count = " + ret);
//		return ret;
//	}
//
//	@Override
//	public void deleteOffsetPostingRecords(List<Long> offsetPostingRecordId)
//			throws ArgusException {
//
//		Query query = em
//				.createQuery("Delete from OffsetPostingRecord where id in (:ids)");
//
//		query.setParameter("ids", offsetPostingRecordId);
//		int count = query.executeUpdate();
//
//		LOGGER.info("no. of rows deleted: " + count);
//
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<PaymentPostingByOffSetManager> getPostedOffsetByBatchId(
//			Long batchId) throws ArgusException {
//
//		Query query = em
//				.createQuery("From PaymentPostingByOffSetManager p where p.paymentBatch="
//						+ batchId);
//		return query.getResultList();
//	}
//
//	public void updateAttachement(Files file) throws ArgusException {
//		em.merge(file);
//	}
//
//	public void saveAttachement(Files file) throws ArgusException {
//		em.persist(file);
//	}
//}
