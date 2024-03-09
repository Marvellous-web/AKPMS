/**
 *
 */
package argus.repo.paymentproductivity.offset;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import argus.domain.paymentproductivity.OffsetRecord;
import argus.domain.paymentproductivity.PaymentProductivityOffset;
import argus.exception.ArgusException;
import argus.util.AkpmsUtil;
import argus.util.Constants;
import argus.util.OffsetRecordJsonData;

/**
 * @author rajiv.k
 *
 */
@Repository
@Transactional
public class PaymentProductivityOffsetDaoImpl implements
		PaymentProductivityOffsetDao {
	private static final Logger LOGGER = Logger
			.getLogger(PaymentProductivityOffsetDaoImpl.class);

	private static final String OFFSET_RECORD_BY_STATUS = "select status,count(status) from payment_productivity_offset group by status";

	@Autowired
	private EntityManager em;

	/**
	 * function is used to get PaymentProductivityOffset by id
	 *
	 */

	@Override
	public PaymentProductivityOffset findById(Long id, boolean dependency)
			throws ArgusException {
		PaymentProductivityOffset paymentProductivityOffset = em.find(
				PaymentProductivityOffset.class, id);
		if (dependency && paymentProductivityOffset != null) {
			Hibernate.initialize(paymentProductivityOffset.getOffsetRecords());
		}
		return paymentProductivityOffset;
	}

	@Override
	public void addPaymentProductivityOffset(
			PaymentProductivityOffset paymentProductivityOffset)
			throws ArgusException {
		em.persist(paymentProductivityOffset);
		for (OffsetRecord offsetRecord : paymentProductivityOffset
				.getOffsetRecords()) {
			offsetRecord.setProductivityOffset(paymentProductivityOffset);
			em.persist(offsetRecord);
		}
		LOGGER.info("Offset is added");
	}

	@Override
	public void updatePaymentProductivityOffset(
			PaymentProductivityOffset paymentProductivityOffset)
			throws ArgusException {
		em.merge(paymentProductivityOffset);
		LOGGER.info("Offset is updated");
	}

	@Override
	public PaymentProductivityOffset findOffsetByProdId(long prodId)
			throws ArgusException {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<PaymentProductivityOffset> criteria = builder
				.createQuery(PaymentProductivityOffset.class);
		Root<PaymentProductivityOffset> paymentProdOffsetRoot = criteria
				.from(PaymentProductivityOffset.class);

		criteria.select(paymentProdOffsetRoot).where(
				builder.equal(paymentProdOffsetRoot.get("paymentProductivity")
						.get(Constants.ID), prodId));
		return em.createQuery(criteria).getSingleResult();
	}

	@Override
	public int totalRecord(Map<String, String> whereClauses)
			throws ArgusException {
		int ret = 0;
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Long> cQuery = builder.createQuery(Long.class);
			Root<PaymentProductivityOffset> from = cQuery
					.from(PaymentProductivityOffset.class);
			CriteriaQuery<Long> select = cQuery.select(builder.count(from));

			List<Predicate> predicateList = new ArrayList<Predicate>();

			if (whereClauses != null && whereClauses.size() > 0) {
				Set<String> key = whereClauses.keySet();
				for (String field : key) {
					if (field.equalsIgnoreCase(Constants.TICKET_NUMBER)) {
						LOGGER.info("found Ticket no  = "
								+ whereClauses.get(field));
						predicateList
								.add(builder.and(builder.equal(
										from.get("paymentBatch").get("id"),
										Long.parseLong(whereClauses
												.get(Constants.TICKET_NUMBER)))));
					}
					if (field.equalsIgnoreCase(Constants.POSTED_BY_ID)) {
						LOGGER.info("found Posted By value  = "
								+ whereClauses.get(field));

						predicateList
								.add(builder.and(builder.equal(
										from.get("createdBy").get("id"),
										Long.parseLong(whereClauses
												.get(Constants.POSTED_BY_ID)))));
					}

					// date posted from
					if (field.equalsIgnoreCase(Constants.DATE_POSTED_FROM)) {
						LOGGER.info("found DATE_POSTED_FROM = "
								+ whereClauses.get(Constants.DATE_POSTED_FROM));

						Date fromDate = AkpmsUtil
								.akpmsNewDateFormat(whereClauses.get(field));
						Path<Date> dateReceivedPath = from.get("createdOn");
						predicateList.add(builder.greaterThanOrEqualTo(
								dateReceivedPath, fromDate));

					}
					// date posted to
					if (field.equalsIgnoreCase(Constants.DATE_POSTED_TO)) {
						LOGGER.info("found DATE_POSTED_TO = "
								+ whereClauses.get(Constants.DATE_POSTED_TO));
						Date toDate = AkpmsUtil.getFormattedDate(whereClauses
								.get(field));

						Path<Date> dateReceivedPath = from.get("createdOn");
						predicateList.add(builder.lessThanOrEqualTo(
								dateReceivedPath, toDate));
					}
					if (field.equalsIgnoreCase(Constants.ACCOUNT_NUMBER)) {
						LOGGER.info("found Posted By value  = "
								+ whereClauses.get(field));

						predicateList.add(builder.and(builder.equal(
								from.get("accountNumber"),
								whereClauses.get(Constants.ACCOUNT_NUMBER))));
					}
					if (field.equalsIgnoreCase(Constants.CHECK_NUMBER)) {
						LOGGER.info("found Posted By value  = "
								+ whereClauses.get(field));

						predicateList.add(builder.and(builder.equal(
								from.get("chkNumber"),
								whereClauses.get(Constants.CHECK_NUMBER))));
					}
					if (field.equalsIgnoreCase(Constants.PATIENT_NAME)) {
						LOGGER.info("found Posted By value  = "
								+ whereClauses.get(field));

						predicateList.add(builder.and(builder.like(
								from.<String> get(Constants.PATIENT_NAME),
								"%" + whereClauses.get(Constants.PATIENT_NAME)
										+ "%")));
					}

					if (field.equalsIgnoreCase(Constants.STATUS)) {
						LOGGER.info("found status value  = "
								+ whereClauses.get(field));

						predicateList.add(builder.and(builder.equal(
								from.get(Constants.STATUS),
								whereClauses.get(Constants.STATUS))));
					}

					if (field.equalsIgnoreCase(Constants.OFFSET_TICKET_NUMBER)) {
						LOGGER.info("found OFFSET_TICKET_NUMBER value  = "
								+ whereClauses.get(field));

						predicateList
								.add(builder.and(builder.equal(
										from.get(Constants.OFFSET_TICKET_NUMBER),
										Long.parseLong(whereClauses
												.get(Constants.OFFSET_TICKET_NUMBER)))));
					}

					if (field.equalsIgnoreCase(Constants.DOCTOR_ID)) {
						LOGGER.info("found Doctor Id  = "
								+ whereClauses.get(field));
						predicateList
								.add(builder.and(builder.equal(
										from.get("paymentBatch").get("doctor").get("id"),
										Long.parseLong(whereClauses
												.get(Constants.DOCTOR_ID)))));
					}

					if (field.equalsIgnoreCase(Constants.INSURANCE_ID)) {
						LOGGER.info("found Insurance Id  = "
								+ whereClauses.get(field));
						predicateList
								.add(builder.and(builder.equal(
										from.get("paymentBatch").get("insurance").get("id"),
										Long.parseLong(whereClauses
												.get(Constants.INSURANCE_ID)))));
					}

				}

			}
			select.where(builder.and(predicateList
					.toArray(new Predicate[predicateList.size()])));
			select.distinct(true);
			TypedQuery<Long> query = em.createQuery(select);

			ret = query.getSingleResult().intValue();
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
			throw new ArgusException(e.getMessage(), e);
		}
		LOGGER.info("count = " + ret);
		return ret;
	}

	public List<PaymentProductivityOffset> findAll(
			Map<String, String> orderClauses, Map<String, String> whereClauses,
			boolean dependancies) throws ArgusException {
		List<PaymentProductivityOffset> ret = null;
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<PaymentProductivityOffset> userCriteriaQuery = builder
					.createQuery(PaymentProductivityOffset.class);
			Root<PaymentProductivityOffset> from = userCriteriaQuery
					.from(PaymentProductivityOffset.class);
			CriteriaQuery<PaymentProductivityOffset> select = userCriteriaQuery
					.select(from);

			List<Predicate> predicateList = new ArrayList<Predicate>();

			if (whereClauses != null && whereClauses.size() > 0) {
				Set<String> key = whereClauses.keySet();
				for (String field : key) {
					if (field.equalsIgnoreCase(Constants.TICKET_NUMBER)) {
						LOGGER.info("found Ticket no  = "
								+ whereClauses.get(field));
						predicateList
								.add(builder.and(builder.equal(
										from.get("paymentBatch").get("id"),
										Long.parseLong(whereClauses
												.get(Constants.TICKET_NUMBER)))));
					}
					if (field.equalsIgnoreCase(Constants.POSTED_BY_ID)) {
						LOGGER.info("found Posted By value  = "
								+ whereClauses.get(field));

						predicateList
								.add(builder.and(builder.equal(
										from.get("createdBy").get("id"),
										Long.parseLong(whereClauses
												.get(Constants.POSTED_BY_ID)))));
					}

					// date posted from
					if (field.equalsIgnoreCase(Constants.DATE_POSTED_FROM)) {
						LOGGER.info("found DATE_POSTED_FROM = "
								+ whereClauses.get(Constants.DATE_POSTED_FROM));

						Date fromDate = AkpmsUtil
								.akpmsNewDateFormat(whereClauses.get(field));
						Path<Date> dateReceivedPath = from.get("createdOn");
						predicateList.add(builder.greaterThanOrEqualTo(
								dateReceivedPath, fromDate));

					}
					// date posted to
					if (field.equalsIgnoreCase(Constants.DATE_POSTED_TO)) {
						LOGGER.info("found DATE_POSTED_TO = "
								+ whereClauses.get(Constants.DATE_POSTED_TO));
						Date toDate = AkpmsUtil.getFormattedDate(whereClauses
								.get(field));

						Path<Date> dateReceivedPath = from.get("createdOn");
						predicateList.add(builder.lessThanOrEqualTo(
								dateReceivedPath, toDate));
					}
					if (field.equalsIgnoreCase(Constants.ACCOUNT_NUMBER)) {
						LOGGER.info("found Posted By value  = "
								+ whereClauses.get(field));

						predicateList.add(builder.and(builder.equal(
								from.get("accountNumber"),
								whereClauses.get(Constants.ACCOUNT_NUMBER))));
					}
					if (field.equalsIgnoreCase(Constants.CHECK_NUMBER)) {
						LOGGER.info("found Posted By value  = "
								+ whereClauses.get(field));

						predicateList.add(builder.and(builder.equal(
								from.get("chkNumber"),
								whereClauses.get(Constants.CHECK_NUMBER))));
					}
					if (field.equalsIgnoreCase(Constants.PATIENT_NAME)) {
						LOGGER.info("found Posted By value  = "
								+ whereClauses.get(field));

						predicateList.add(builder.and(builder.like(
								from.<String> get(Constants.PATIENT_NAME),
								"%" + whereClauses.get(Constants.PATIENT_NAME)
										+ "%")));
					}

					if (field.equalsIgnoreCase(Constants.STATUS)) {
						LOGGER.info("found status value  = "
								+ whereClauses.get(field));

						predicateList.add(builder.and(builder.equal(
								from.get(Constants.STATUS),
								whereClauses.get(Constants.STATUS))));
					}

					if (field.equalsIgnoreCase(Constants.OFFSET_TICKET_NUMBER)) {
						LOGGER.info("found OFFSET_TICKET_NUMBER value  = "
								+ whereClauses.get(field));

						predicateList
								.add(builder.and(builder.equal(
										from.get(Constants.OFFSET_TICKET_NUMBER),
										Long.parseLong(whereClauses
												.get(Constants.OFFSET_TICKET_NUMBER)))));
					}

					if (field.equalsIgnoreCase(Constants.DOCTOR_ID)) {
						LOGGER.info("found Doctor Id  = "
								+ whereClauses.get(field));
						predicateList
								.add(builder.and(builder.equal(
										from.get("paymentBatch").get("doctor").get("id"),
										Long.parseLong(whereClauses
												.get(Constants.DOCTOR_ID)))));
					}

					if (field.equalsIgnoreCase(Constants.INSURANCE_ID)) {
						LOGGER.info("found Insurance Id  = "
								+ whereClauses.get(field));
						predicateList
								.add(builder.and(builder.equal(
										from.get("paymentBatch").get("insurance").get("id"),
										Long.parseLong(whereClauses
												.get(Constants.INSURANCE_ID)))));
					}

				}
			}
			select.where(builder.and(predicateList
					.toArray(new Predicate[predicateList.size()])));
			select.distinct(true);

			if (orderClauses != null) {
				if (orderClauses.get(Constants.ORDER_BY) != null) {
					if (orderClauses.get(Constants.ORDER_BY).equalsIgnoreCase(
							"batchId")) {
						if (orderClauses.get(Constants.SORT_BY) != null
								&& orderClauses.get(Constants.SORT_BY)
										.equalsIgnoreCase("desc")) {
							select.orderBy(builder.desc(from
									.get("paymentBatch").get("id")));
						} else {
							select.orderBy(builder.asc(from.get("paymentBatch")
									.get("id")));

						}
					} else {
						if (orderClauses.get(Constants.SORT_BY) != null
								&& orderClauses.get(Constants.SORT_BY)
										.equalsIgnoreCase("desc")) {
							select.orderBy(builder.desc(from.get(orderClauses
									.get(Constants.ORDER_BY))));
						} else {
							select.orderBy(builder.asc(from.get(orderClauses
									.get(Constants.ORDER_BY))));

						}
					}
				} else {
					select.orderBy(builder.asc(from.get("paymentBatch").get(
							"id")));
				}
			}

			TypedQuery<PaymentProductivityOffset> query = em
					.createQuery(select);
			if (orderClauses != null && orderClauses.get("offset") != null
					&& orderClauses.get("limit") != null) {
				query.setFirstResult(Integer.parseInt(orderClauses
						.get("offset")));
				query.setMaxResults(Integer.parseInt(orderClauses.get("limit")));
			}
			LOGGER.info("SQL:: " + query.toString());
			ret = query.getResultList();
			if (ret != null) {
				Iterator<PaymentProductivityOffset> paymentProductivityOffsetList = ret
						.iterator();
				while (paymentProductivityOffsetList.hasNext()) {
					PaymentProductivityOffset paymentProductivityOffsetDB = paymentProductivityOffsetList
							.next();
					if (dependancies) {
						Hibernate.initialize(paymentProductivityOffsetDB
								.getOffsetRecords());
						Hibernate.initialize(paymentProductivityOffsetDB
								.getPaymentBatch());
						Hibernate.initialize(paymentProductivityOffsetDB
								.getCreatedBy());

					}

				}
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
			throw new ArgusException(e.getMessage(), e);
		}
		return ret;
	}

	@Override
	public List<OffsetRecordJsonData> getPostedRecord(Long offsetId) {

		List<OffsetRecord> records = new ArrayList<OffsetRecord>();
		List<OffsetRecordJsonData> postedRecords = new ArrayList<OffsetRecordJsonData>();
		StringBuilder queryString = new StringBuilder();
		queryString
				.append("FROM OffsetRecord As offset where offset.productivityOffset="
						+ offsetId);

		TypedQuery<OffsetRecord> query = em.createQuery(queryString.toString(),
				OffsetRecord.class);

		records = query.getResultList();

		for (OffsetRecord offsetRecord : records) {
			OffsetRecordJsonData jsonData = new OffsetRecordJsonData();
			jsonData.setCpt(offsetRecord.getCpt());
			jsonData.setAmount(offsetRecord.getAmount());
			jsonData.setDos(AkpmsUtil.akpmsDateFormat(offsetRecord.getDos(),
					Constants.DATE_FORMAT));
			jsonData.setOffsetId(offsetId);
			postedRecords.add(jsonData);

		}
		return postedRecords;
	}

	@Override
	public void deleteOffsetRecords(List<Long> toRemoveOffsetRecordList)
			throws ArgusException {

		Query query = em
				.createQuery("Delete from OffsetRecord where id in (:ids)");

		query.setParameter("ids", toRemoveOffsetRecordList);
		int count = query.executeUpdate();

		LOGGER.info("no. of rows deleted: " + count);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PaymentProductivityOffset> getOffsetByBatchId(Long batchId)
			throws ArgusException {

		Query query = em
				.createQuery("From PaymentProductivityOffset p where p.paymentBatch="
						+ batchId);
		return query.getResultList();
	}

	@Override
	public void updateStatus(String ids, String ticketNumber,String status) {
		// TODO Auto-generated method stub
		StringBuilder queryString = new StringBuilder();
		ids = ids.substring(0, ids.trim().length()-1);

 		queryString.append("UPDATE PaymentProductivityOffset AS d set d.offsetTicketNumber = "+ticketNumber+",d.status ='"+status+"' ");
		queryString.append(" where id IN( " + ids +" ) ");

		em.createQuery(queryString.toString()).executeUpdate();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAllOffsetByStatus() throws ArgusException {
		List<Object[]> listObject = new ArrayList<Object[]>();
		Query query = em.createNativeQuery(OFFSET_RECORD_BY_STATUS);
		listObject = query.getResultList();
		return listObject;
	}

}
