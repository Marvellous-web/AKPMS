package argus.repo.paymentproductivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import argus.domain.PaymentBatch;
import argus.domain.paymentproductivity.PaymentProductivityRefundRequest;
import argus.exception.ArgusException;
import argus.util.AkpmsUtil;
import argus.util.Constants;

@Repository
@Transactional
public class PaymentProductivityRefundRequestDaoImpl implements
		PaymentProductivityRefundRequestDao {

	private static final Log LOGGER = LogFactory
			.getLog(PaymentProductivityRefundRequestDaoImpl.class);

	private static final String TRANSACTION_DATE = "transactionDate";
	private static final String REQUEST_DATE = "requestDate";

	@Autowired
	private EntityManager em;

	@Override
	public PaymentProductivityRefundRequest findById(Long id)
			throws ArgusException {
		PaymentProductivityRefundRequest paymentProductivityRefundRequest = em
				.find(PaymentProductivityRefundRequest.class, id);

		return paymentProductivityRefundRequest;
	}

	@Override
	public void addPaymentProductivityRefundRequest(
			PaymentProductivityRefundRequest paymentProductivityRefundRequest)
			throws ArgusException {
		em.persist(paymentProductivityRefundRequest);
		LOGGER.info("PaymentProductivityRefundRequest Added successfully");

	}

	@Override
	public void updatePaymentProductivityRefundRequest(
			PaymentProductivityRefundRequest paymentProductivityRefundRequest)
			throws ArgusException {
		em.merge(paymentProductivityRefundRequest);
		LOGGER.info("PaymentProductivityRefundRequest Updated successfully");

	}

	@Override
	public List<PaymentBatch> findAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses) throws ArgusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int totalRecord(Map<String, String> whereClauses) {
		int ret = 0;
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Long> cQuery = builder.createQuery(Long.class);
			Root<PaymentProductivityRefundRequest> from = cQuery
					.from(PaymentProductivityRefundRequest.class);
			CriteriaQuery<Long> select = cQuery.select(builder.count(from));

			List<Predicate> predicateList = new ArrayList<Predicate>();

			if (whereClauses != null && whereClauses.size() > 0) {
				Set<String> key = whereClauses.keySet();
				for (String field : key) {

					if (field.equalsIgnoreCase(Constants.DOCTOR)) {
						LOGGER.info("found Doctor Name  = "
								+ whereClauses.get(field));

						predicateList.add(builder.and(builder.equal(
								from.join(Constants.PAYMENT_BATCH)
										.join(Constants.DOCTOR)
										.get(Constants.ID), Long
										.parseLong(whereClauses
												.get(Constants.DOCTOR)))));
					}

					// date posted from
					if (field.equalsIgnoreCase(Constants.DATE_POSTED_FROM)) {
						LOGGER.info("found DATE_POSTED_FROM = "
								+ whereClauses.get(Constants.DATE_POSTED_FROM));

						Date fromDate = AkpmsUtil
								.akpmsNewDateFormat(whereClauses.get(field));
						Path<Date> dateReceivedPath = from
								.get(Constants.CREATED_ON);
						predicateList.add(builder.greaterThanOrEqualTo(
								dateReceivedPath, fromDate));

					}
					// date posted to
					if (field.equalsIgnoreCase(Constants.DATE_POSTED_TO)) {
						LOGGER.info("found DATE_POSTED_TO = "
								+ whereClauses.get(Constants.DATE_POSTED_TO));
						Date toDate = AkpmsUtil.getFormattedDate(whereClauses
								.get(field));

						Path<Date> dateReceivedPath = from
								.get(Constants.CREATED_ON);
						predicateList.add(builder.lessThanOrEqualTo(
								dateReceivedPath, toDate));
					}
					// date transaction from
					if (field.equalsIgnoreCase(Constants.DATE_TRANSACTION_FROM)) {
						LOGGER.info("found DATE_TRANSACTION_FROM = "
								+ whereClauses
										.get(Constants.DATE_TRANSACTION_FROM));

						Date fromDate = AkpmsUtil
								.akpmsNewDateFormat(whereClauses.get(field));
						Path<Date> dateReceivedPath = from
								.get(TRANSACTION_DATE);
						predicateList.add(builder.greaterThanOrEqualTo(
								dateReceivedPath, fromDate));

					}
					// date transaction to
					if (field.equalsIgnoreCase(Constants.DATE_TRANSACTION_TO)) {
						LOGGER.info("found DATE_TRANSACTION_TO = "
								+ whereClauses
										.get(Constants.DATE_TRANSACTION_TO));
						Date toDate = AkpmsUtil.getFormattedDate(whereClauses
								.get(field));

						Path<Date> dateReceivedPath = from
								.get(TRANSACTION_DATE);
						predicateList.add(builder.lessThanOrEqualTo(
								dateReceivedPath, toDate));
					}
					// date resolution from
					if (field.equalsIgnoreCase(Constants.DATE_RESOLUTION_FROM)) {
						LOGGER.info("found DATE_RESOLUTION_FROM = "
								+ whereClauses
										.get(Constants.DATE_RESOLUTION_FROM));

						Date fromDate = AkpmsUtil
								.akpmsNewDateFormat(whereClauses.get(field));
						Path<Date> dateReceivedPath = from.get(REQUEST_DATE);
						predicateList.add(builder.greaterThanOrEqualTo(
								dateReceivedPath, fromDate));

					}
					// date resolution to
					if (field.equalsIgnoreCase(Constants.DATE_RESOLUTION_TO)) {
						LOGGER.info("found DATE_RESOLUTION_TO = "
								+ whereClauses
										.get(Constants.DATE_RESOLUTION_TO));
						Date toDate = AkpmsUtil.getFormattedDate(whereClauses
								.get(field));

						Path<Date> dateReceivedPath = from.get(REQUEST_DATE);
						predicateList.add(builder.lessThanOrEqualTo(
								dateReceivedPath, toDate));
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

		}
		LOGGER.info("count = " + ret);
		return ret;
	}

	@Override
	public List<PaymentProductivityRefundRequest> findAllForReport(
			Map<String, String> orderClauses, Map<String, String> whereClauses)
			throws ArgusException {
		List<PaymentProductivityRefundRequest> ret = null;
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<PaymentProductivityRefundRequest> userCriteriaQuery = builder
					.createQuery(PaymentProductivityRefundRequest.class);
			Root<PaymentProductivityRefundRequest> from = userCriteriaQuery
					.from(PaymentProductivityRefundRequest.class);
			CriteriaQuery<PaymentProductivityRefundRequest> select = userCriteriaQuery
					.select(from);

			List<Predicate> predicateList = new ArrayList<Predicate>();

			if (whereClauses != null && whereClauses.size() > 0) {
				Set<String> key = whereClauses.keySet();
				for (String field : key) {

					if (field.equalsIgnoreCase(Constants.DOCTOR)) {
						LOGGER.info("found Doctor Name  = "
								+ whereClauses.get(field));

						predicateList.add(builder.and(builder.equal(
								from.join(Constants.PAYMENT_BATCH)
										.join(Constants.DOCTOR)
										.get(Constants.ID), Long
										.parseLong(whereClauses
												.get(Constants.DOCTOR)))));
					}

					// date posted from
					if (field.equalsIgnoreCase(Constants.DATE_POSTED_FROM)) {
						LOGGER.info("found DATE_POSTED_FROM = "
								+ whereClauses.get(Constants.DATE_POSTED_FROM));

						Date fromDate = AkpmsUtil
								.akpmsNewDateFormat(whereClauses.get(field));
						Path<Date> dateReceivedPath = from
								.get(Constants.CREATED_ON);
						predicateList.add(builder.greaterThanOrEqualTo(
								dateReceivedPath, fromDate));

					}
					// date posted to
					if (field.equalsIgnoreCase(Constants.DATE_POSTED_TO)) {
						LOGGER.info("found DATE_POSTED_TO = "
								+ whereClauses.get(Constants.DATE_POSTED_TO));
						Date toDate = AkpmsUtil.getFormattedDate(whereClauses
								.get(field));

						Path<Date> dateReceivedPath = from
								.get(Constants.CREATED_ON);
						predicateList.add(builder.lessThanOrEqualTo(
								dateReceivedPath, toDate));
					}
					// date transaction from
					if (field.equalsIgnoreCase(Constants.DATE_TRANSACTION_FROM)) {
						LOGGER.info("found DATE_TRANSACTION_FROM = "
								+ whereClauses
										.get(Constants.DATE_TRANSACTION_FROM));

						Date fromDate = AkpmsUtil
								.akpmsNewDateFormat(whereClauses.get(field));
						Path<Date> dateReceivedPath = from
								.get(TRANSACTION_DATE);
						predicateList.add(builder.greaterThanOrEqualTo(
								dateReceivedPath, fromDate));

					}
					// date transaction to
					if (field.equalsIgnoreCase(Constants.DATE_TRANSACTION_TO)) {
						LOGGER.info("found DATE_TRANSACTION_TO = "
								+ whereClauses
										.get(Constants.DATE_TRANSACTION_TO));
						Date toDate = AkpmsUtil.getFormattedDate(whereClauses
								.get(field));

						Path<Date> dateReceivedPath = from
								.get(TRANSACTION_DATE);
						predicateList.add(builder.lessThanOrEqualTo(
								dateReceivedPath, toDate));
					}
					// date resolution from
					if (field.equalsIgnoreCase(Constants.DATE_RESOLUTION_FROM)) {
						LOGGER.info("found DATE_RESOLUTION_FROM = "
								+ whereClauses
										.get(Constants.DATE_RESOLUTION_FROM));

						Date fromDate = AkpmsUtil
								.akpmsNewDateFormat(whereClauses.get(field));
						Path<Date> dateReceivedPath = from.get(REQUEST_DATE);
						predicateList.add(builder.greaterThanOrEqualTo(
								dateReceivedPath, fromDate));

					}
					// date resolution to
					if (field.equalsIgnoreCase(Constants.DATE_RESOLUTION_TO)) {
						LOGGER.info("found DATE_RESOLUTION_TO = "
								+ whereClauses
										.get(Constants.DATE_RESOLUTION_TO));
						Date toDate = AkpmsUtil.getFormattedDate(whereClauses
								.get(field));

						Path<Date> dateReceivedPath = from.get(REQUEST_DATE);
						predicateList.add(builder.lessThanOrEqualTo(
								dateReceivedPath, toDate));
					}
				}
			}
			select.where(builder.and(predicateList
					.toArray(new Predicate[predicateList.size()])));
			select.distinct(true);

			TypedQuery<PaymentProductivityRefundRequest> query = em
					.createQuery(select);
			if (orderClauses != null
					&& orderClauses.get(Constants.OFFSET) != null
					&& orderClauses.get(Constants.LIMIT) != null) {
				query.setFirstResult(Integer.parseInt(orderClauses
						.get(Constants.OFFSET)));
				query.setMaxResults(Integer.parseInt(orderClauses
						.get(Constants.LIMIT)));
			}
			LOGGER.info("SQL:: " + query.toString());
			ret = query.getResultList();
			if (ret != null) {
				Iterator<PaymentProductivityRefundRequest> paymentProductivityRefundRequestList = ret
						.iterator();
				while (paymentProductivityRefundRequestList.hasNext()) {
					PaymentProductivityRefundRequest paymentProductivityRefundRequestDB = paymentProductivityRefundRequestList
							.next();
					Hibernate.initialize(paymentProductivityRefundRequestDB
							.getPaymentBatch());

				}
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
			throw new ArgusException(e.getMessage(), e);
		}
		return ret;
	}

}
