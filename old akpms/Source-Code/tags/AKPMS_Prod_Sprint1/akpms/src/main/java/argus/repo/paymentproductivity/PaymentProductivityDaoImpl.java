package argus.repo.paymentproductivity;

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import argus.domain.paymentproductivity.PaymentProductivity;
import argus.exception.ArgusException;
import argus.repo.paymentproductivity.helper.PaymentProductivityDaoHelper;
import argus.util.AkpmsUtil;
import argus.util.Constants;

@Repository
@Transactional
public class PaymentProductivityDaoImpl implements PaymentProductivityDao {
	private static final Log LOGGER = LogFactory
			.getLog(PaymentProductivityDaoImpl.class);

	private static final String DELETE_STATUS_SINGLE = "UPDATE PaymentProductivity as p SET p.deleted =1, p.modifiedBy = :modifiedBy, p.modifiedOn = :modifiedOn WHERE p.id = :id";
	private static final String PAYMENT_PRODUCTIVITY_TYPE = "SELECT payment_productivity_type, count(*) FROM payment_productivity as p where p.deleted = 0 group By payment_productivity_type";
	private static final String OFFSET_MANAGER_RECORD = "SELECT workflow_id, count(*) FROM payment_productivity as p where p.deleted = 0 group By workflow_id";
	private static final String TOTAL_OFFSET_RECORD = "SELECT COUNT(*) FROM PaymentProductivityOffset AS p";
	private static final String TOTAL_PRODUCTIVITYOFFSET_RECORD = "SELECT COUNT(*) FROM PaymentProductivityOffset AS p where p.paymentBatch.id = "
			+ ":batchId";

	@Autowired
	private EntityManager em;

	/**
	 * function is used to get PaymentProductivity by id
	 *
	 */
	@Override
	public PaymentProductivity findById(Long id) throws ArgusException {
		PaymentProductivity paymentProductivity = em.find(
				PaymentProductivity.class, id);

		return paymentProductivity;
	}

	/**
	 * function is used to find by name
	 */
	@Override
	public PaymentProductivity findByName(String name) throws ArgusException {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<PaymentProductivity> criteria = builder
				.createQuery(PaymentProductivity.class);

		Root<PaymentProductivity> paymentProductivity = criteria
				.from(PaymentProductivity.class);

		criteria.select(paymentProductivity)
				.where(builder.equal(
						paymentProductivity.get(Constants.BY_NAME), name));

		return em.createQuery(criteria).getSingleResult();
	}

	@Override
	public List<PaymentProductivity> findAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses, boolean dependancies)
			throws ArgusException {
		List<PaymentProductivity> paymentProductivityList = null;
		// List<PaymentProductivity> paymentProdList = null;

		StringBuilder queryString = new StringBuilder();

		queryString.append("SELECT p FROM PaymentProductivity AS p ");
		queryString.append(" WHERE p.deleted = 0");
		queryString.append(PaymentProductivityDaoHelper.getQueryFindAll(
				orderClauses, whereClauses).toString());

		TypedQuery<PaymentProductivity> query = em.createQuery(
				queryString.toString(), PaymentProductivity.class);
		if (orderClauses != null && orderClauses.get(Constants.OFFSET) != null
				&& orderClauses.get(Constants.LIMIT) != null) {
			query.setFirstResult(Integer.parseInt(orderClauses
					.get(Constants.OFFSET)));
			query.setMaxResults(Integer.parseInt(orderClauses
					.get(Constants.LIMIT)));
		}

		LOGGER.info("SQL:: " + queryString.toString());

		paymentProductivityList = query.getResultList();

		if (dependancies && paymentProductivityList != null
				&& paymentProductivityList.size() > Constants.ZERO) {
			Iterator<PaymentProductivity> paymentProductivityIterator = paymentProductivityList
					.iterator();

			// paymentProdList = new ArrayList<PaymentProductivity>();
			while (paymentProductivityIterator.hasNext()) {
				PaymentProductivity paymentProductivity = paymentProductivityIterator
						.next();
				paymentProductivity
						.setWorkFlowName(PaymentProductivityDaoHelper
								.getWorkFlowName(paymentProductivity
										.getWorkFlowId()));
				Hibernate.initialize(paymentProductivity.getPaymentBatch());
				Hibernate.initialize(paymentProductivity.getPaymentBatch()
						.getDoctor());
				Hibernate.initialize(paymentProductivity.getPaymentBatch()
						.getInsurance());
				// paymentProdList.add(paymentProductivity);

			}
		}

		return paymentProductivityList;
	}

	/**
	 * to add new department
	 */
	@Override
	public void addPaymentProductivity(PaymentProductivity paymentProductivity)
			throws ArgusException {
		em.persist(paymentProductivity);
		return;
	}

	/**
	 * to update existing department
	 */
	@Override
	public void updatePaymentProductivity(
			PaymentProductivity paymentProductivity) throws ArgusException {
		em.merge(paymentProductivity);
		return;
	}

	/**
	 * to get total records
	 */
	@Override
	public int totalRecord(Map<String, String> whereClauses)
			throws ArgusException {
		int totalRecords = 0;
		StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT COUNT(*) FROM PaymentProductivity AS p ");
		if (whereClauses != null && whereClauses.size() > Constants.ZERO) {
			queryString.append(" WHERE p.deleted = 0");
			Set<String> key = whereClauses.keySet();
			for (String field : key) {
				queryString.append(" AND ");
				if (field.equalsIgnoreCase(Constants.TICKET_NUMBER)) {
					LOGGER.info("found search text value  = "
							+ whereClauses.get(field));
					queryString.append(" p.paymentBatch ="
							+ whereClauses.get(Constants.TICKET_NUMBER));
				}
				if (field.equalsIgnoreCase(Constants.PROD_TYPE)) {
					LOGGER.info("found Prod type value  = "
							+ whereClauses.get(field));
					queryString.append(" p.paymentProdType = "
							+ whereClauses.get(Constants.PROD_TYPE));
				}
				if (field.equalsIgnoreCase(Constants.POSTED_BY_ID)) {
					LOGGER.info("found Posted By value  = "
							+ whereClauses.get(field));
					queryString.append(" p.createdBy = "
							+ whereClauses.get(Constants.POSTED_BY_ID));

				}

				/* date posted from */
				if (field.equalsIgnoreCase(Constants.DATE_POSTED_FROM)) {
					LOGGER.info("found DATE_POSTED_FROM = "
							+ whereClauses.get(Constants.DATE_POSTED_FROM));

					queryString.append(" p.paymentBatch.datePosted >= '"
							+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
									.akpmsNewDateFormat(whereClauses
											.get(Constants.DATE_POSTED_FROM)),
									Constants.MYSQL_DATE_FORMAT) + "'");
				}

				/* date posted to */
				if (field.equalsIgnoreCase(Constants.DATE_POSTED_TO)) {
					LOGGER.info("found DATE_POSTED_TO = "
							+ whereClauses.get(Constants.DATE_POSTED_TO));
					// queryString.append(" p.datePosted <= '"
					// + AkpmsUtil.akpmsDateFormat(AkpmsUtil
					// .akpmsNewDateFormat(whereClauses
					// .get(Constants.DATE_POSTED_TO)),
					// Constants.MYSQL_DATE_FORMAT) + "'");
					queryString.append(" p.paymentBatch.datePosted <= '"
							+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
									.getFormattedDate(whereClauses
											.get(Constants.DATE_POSTED_TO)),
									Constants.MYSQL_DATE_FORMAT_WITH_TIME)
							+ "'");
				}
				if (field.equalsIgnoreCase("workflowId")
						& whereClauses.get(field) != null
						& whereClauses.get(field).trim().length() > 0) {
					LOGGER.info("found search text value  = "
							+ whereClauses.get(field));
					queryString.append(" p.workFlowId ="
							+ whereClauses.get("workflowId"));
				}
				if (field.equalsIgnoreCase(Constants.DATE_RECEIVED_FROM)) {
					LOGGER.info("found DATE_RECEIVED_FROM = "
							+ whereClauses.get(Constants.DATE_RECEIVED_FROM));
					queryString
							.append(" p.createdOn >= '"
									+ AkpmsUtil.akpmsDateFormat(
											AkpmsUtil
													.akpmsNewDateFormat(whereClauses
															.get(Constants.DATE_RECEIVED_FROM)),
											Constants.MYSQL_DATE_FORMAT) + "'");

				}
				if (field.equalsIgnoreCase(Constants.DATE_RECEIVED_TO)) {
					LOGGER.info("found DATE_RECIEVED_TO = "
							+ whereClauses.get(Constants.DATE_RECEIVED_TO));
					// queryString.append(" p.createdOn <= '"
					// + AkpmsUtil.akpmsDateFormat(AkpmsUtil
					// .akpmsNewDateFormat(whereClauses
					// .get(Constants.DATE_RECEIVED_TO)),
					// Constants.MYSQL_DATE_FORMAT) + "'");
					queryString.append(" p.createdOn <= '"
							+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
									.getFormattedDate(whereClauses
											.get(Constants.DATE_RECEIVED_TO)),
									Constants.MYSQL_DATE_FORMAT_WITH_TIME)
							+ "'");

				}

				if (field.equalsIgnoreCase(Constants.INSURANCE)) {
					LOGGER.info("found search text value  = "
							+ whereClauses.get(field));
					queryString.append(" p.paymentBatch.insurance.id ="
							+ whereClauses.get(Constants.INSURANCE));
				}
			}
		}
		TypedQuery<Long> query = em.createQuery(queryString.toString(),
				Long.class);
		totalRecords = query.getSingleResult().intValue();
		LOGGER.info("count = " + totalRecords);
		return totalRecords;
	}

	@Override
	public int deletePaymentProductivity(Long id) throws ArgusException {
		Query query = em.createQuery(DELETE_STATUS_SINGLE);

		query.setParameter("modifiedBy", AkpmsUtil.getLoggedInUser());
		query.setParameter("modifiedOn", new Date());
		query.setParameter("id", id);

		LOGGER.info("SQL:" + query.toString());
		return query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAllProductivityType() throws ArgusException {
		List<Object[]> listObject = new ArrayList<Object[]>();
		Query query = em.createNativeQuery(PAYMENT_PRODUCTIVITY_TYPE);
		listObject = query.getResultList();
		return listObject;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAllProductivityByWorkFlow() throws ArgusException {
		List<Object[]> listObject = new ArrayList<Object[]>();
		Query query = em.createNativeQuery(OFFSET_MANAGER_RECORD);
		listObject = query.getResultList();
		return listObject;

	}

	@Override
	public PaymentProductivity findByTicketNo(Long ticketNo)
			throws ArgusException {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<PaymentProductivity> criteria = builder
				.createQuery(PaymentProductivity.class);

		Root<PaymentProductivity> paymentProductivity = criteria
				.from(PaymentProductivity.class);

		criteria.select(paymentProductivity).where(
				builder.equal(paymentProductivity.get(Constants.PAYMENT_BATCH),
						ticketNo));

		return em.createQuery(criteria).getSingleResult();
	}

	/*
	 * @Override public int totalRecordForNonEraReport(Map<String, String>
	 * whereClauses) throws ArgusException {
	 *
	 * int ret = 0; try { CriteriaBuilder builder = em.getCriteriaBuilder();
	 * CriteriaQuery<Long> cQuery = builder.createQuery(Long.class);
	 * Root<PaymentProductivity> from = cQuery .from(PaymentProductivity.class);
	 * CriteriaQuery<Long> select = cQuery.select(builder.count(from));
	 *
	 * List<Predicate> predicateList = new ArrayList<Predicate>();
	 * predicateList.add(builder.and(builder.equal( from.<String>
	 * get("deleted"), 0))); predicateList.add(builder.and(builder.equal(
	 * from.<String> get("paymentProdType"), 2)));
	 *
	 * if (whereClauses != null && whereClauses.size() > 0) { Set<String> key =
	 * whereClauses.keySet(); for (String field : key) { if
	 * (field.equalsIgnoreCase(Constants.TICKET_NUMBER)) {
	 * LOGGER.info("found Ticket no  = " + whereClauses.get(field));
	 * predicateList.add(builder.and(builder.equal(
	 * from.get("paymentBatch").get("id"), Long.parseLong(whereClauses
	 * .get(Constants.TICKET_NUMBER))))); } if
	 * (field.equalsIgnoreCase(Constants.POSTED_BY_ID)) {
	 * LOGGER.info("found Posted By value  = " + whereClauses.get(field));
	 *
	 * predicateList.add(builder.and(builder.equal(
	 * from.get("createdBy").get("id"), Long.parseLong(whereClauses
	 * .get(Constants.POSTED_BY_ID))))); } if
	 * (field.equalsIgnoreCase(Constants.DOCTOR)) {
	 * LOGGER.info("found Doctor Name  = " + whereClauses.get(field));
	 *
	 * predicateList.add(builder.and(builder.equal( from.join("paymentBatch")
	 * .join(Constants.DOCTOR).get("id"), Long .parseLong(whereClauses
	 * .get(Constants.DOCTOR))))); } if
	 * (field.equalsIgnoreCase(Constants.INSURANCE)) {
	 * LOGGER.info("found Doctor Name  = " + whereClauses.get(field));
	 *
	 * predicateList.add(builder.and(builder.equal( from.join("paymentBatch")
	 * .join(Constants.INSURANCE).get("id"), Long.parseLong(whereClauses
	 * .get(Constants.INSURANCE))))); } // date posted from if
	 * (field.equalsIgnoreCase(Constants.DATE_POSTED_FROM)) {
	 * LOGGER.info("found DATE_POSTED_FROM = " +
	 * whereClauses.get(Constants.DATE_POSTED_FROM));
	 *
	 * Date fromDate = AkpmsUtil .akpmsNewDateFormat(whereClauses.get(field));
	 * Path<Date> dateReceivedPath = from.get("datePosted");
	 * predicateList.add(builder.greaterThanOrEqualTo( dateReceivedPath,
	 * fromDate));
	 *
	 * } // date posted to if (field.equalsIgnoreCase(Constants.DATE_POSTED_TO))
	 * { LOGGER.info("found DATE_POSTED_TO = " +
	 * whereClauses.get(Constants.DATE_POSTED_TO)); Date toDate =
	 * AkpmsUtil.getFormattedDate(whereClauses .get(field));
	 *
	 * Path<Date> dateReceivedPath = from.get("datePosted");
	 * predicateList.add(builder.lessThanOrEqualTo( dateReceivedPath, toDate));
	 * } // date received from if
	 * (field.equalsIgnoreCase(Constants.DATE_RECEIVED_FROM)) {
	 * LOGGER.info("found DATE_RECEIVED_FROM = " + whereClauses
	 * .get(Constants.DATE_RECEIVED_FROM));
	 *
	 * Date fromDate = AkpmsUtil .akpmsNewDateFormat(whereClauses.get(field));
	 * Path<Date> dateReceivedPath = from.get("createdOn");
	 * predicateList.add(builder.greaterThanOrEqualTo( dateReceivedPath,
	 * fromDate));
	 *
	 * } // date received to if
	 * (field.equalsIgnoreCase(Constants.DATE_RECEIVED_TO)) {
	 * LOGGER.info("found DATE_RECEIVED_TO = " +
	 * whereClauses.get(Constants.DATE_RECEIVED_TO)); Date toDate =
	 * AkpmsUtil.getFormattedDate(whereClauses .get(field));
	 *
	 * Path<Date> dateReceivedPath = from.get("createdOn");
	 * predicateList.add(builder.lessThanOrEqualTo( dateReceivedPath, toDate));
	 * }
	 *
	 * }
	 *
	 * } select.where(builder.and(predicateList .toArray(new
	 * Predicate[predicateList.size()]))); select.distinct(true);
	 * TypedQuery<Long> query = em.createQuery(select);
	 *
	 * ret = query.getSingleResult().intValue(); } catch (Exception e) {
	 * LOGGER.error(Constants.EXCEPTION, e); throw new
	 * ArgusException(e.getMessage(), e); } LOGGER.info("count = " + ret);
	 * return ret; }
	 */

	@Override
	public List<PaymentProductivity> findAllForReport(
			Map<String, String> orderClauses, Map<String, String> whereClauses)
			throws ArgusException {
		List<PaymentProductivity> ret = null;
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<PaymentProductivity> userCriteriaQuery = builder
					.createQuery(PaymentProductivity.class);
			Root<PaymentProductivity> from = userCriteriaQuery
					.from(PaymentProductivity.class);
			CriteriaQuery<PaymentProductivity> select = userCriteriaQuery
					.select(from);

			List<Predicate> predicateList = new ArrayList<Predicate>();
			predicateList.add(builder.and(builder.equal(
					from.<String> get("deleted"), Constants.NON_DELETED)));

			if (whereClauses != null && whereClauses.size() > 0) {
				Set<String> key = whereClauses.keySet();
				for (String field : key) {
					if (field.equalsIgnoreCase(Constants.PROD_TYPE)
							& whereClauses.get(field) != null
							& whereClauses.get(field).trim().length() > 0) {
						LOGGER.info("found Prod type value  = "
								+ whereClauses.get(field));
						predicateList.add(builder.and(builder.equal(from
								.get("paymentProdType"), Long
								.parseLong(whereClauses
										.get(Constants.PROD_TYPE)))));

					}
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

					if (field.equalsIgnoreCase(Constants.DOCTOR)) {
						LOGGER.info("found Doctor Name  = "
								+ whereClauses.get(field));

						predicateList.add(builder.and(builder.equal(
								from.join("paymentBatch")
										.join(Constants.DOCTOR).get("id"), Long
										.parseLong(whereClauses
												.get(Constants.DOCTOR)))));
					}
					if (field.equalsIgnoreCase(Constants.INSURANCE)) {
						LOGGER.info("found Doctor Name  = "
								+ whereClauses.get(field));

						predicateList.add(builder.and(builder.equal(
								from.join("paymentBatch")
										.join(Constants.INSURANCE).get("id"),
								Long.parseLong(whereClauses
										.get(Constants.INSURANCE)))));
					}

					// date posted from
					if (field.equalsIgnoreCase(Constants.DATE_POSTED_FROM)) {
						LOGGER.info("found DATE_POSTED_FROM = "
								+ whereClauses.get(Constants.DATE_POSTED_FROM));

						Date fromDate = AkpmsUtil
								.akpmsNewDateFormat(whereClauses.get(field));
						Path<Date> dateReceivedPath = from.get("paymentBatch")
								.get("datePosted");
						predicateList.add(builder.greaterThanOrEqualTo(
								dateReceivedPath, fromDate));

					}
					// date posted to
					if (field.equalsIgnoreCase(Constants.DATE_POSTED_TO)) {
						LOGGER.info("found DATE_POSTED_TO = "
								+ whereClauses.get(Constants.DATE_POSTED_TO));
						Date toDate = AkpmsUtil.getFormattedDate(AkpmsUtil
								.akpmsNewDateFormat(whereClauses.get(field)));

						Path<Date> dateReceivedPath = from.get("paymentBatch")
								.get("datePosted");
						predicateList.add(builder.lessThanOrEqualTo(
								dateReceivedPath, toDate));
					}
					// date received from
					if (field.equalsIgnoreCase(Constants.DATE_RECEIVED_FROM)) {
						LOGGER.info("found DATE_RECEIVED_FROM = "
								+ whereClauses
										.get(Constants.DATE_RECEIVED_FROM));

						Date fromDate = AkpmsUtil
								.akpmsNewDateFormat(whereClauses.get(field));
						Path<Date> dateReceivedPath = from.get("createdOn");
						predicateList.add(builder.greaterThanOrEqualTo(
								dateReceivedPath, fromDate));

					}
					// date received to
					if (field.equalsIgnoreCase(Constants.DATE_RECEIVED_TO)) {
						LOGGER.info("found DATE_RECEIVED_TO = "
								+ whereClauses.get(Constants.DATE_RECEIVED_TO));
						Date toDate = AkpmsUtil.getFormattedDate(AkpmsUtil
								.akpmsNewDateFormat(whereClauses.get(field)));

						Path<Date> dateReceivedPath = from.get("createdOn");
						predicateList.add(builder.lessThanOrEqualTo(
								dateReceivedPath, toDate));
					}
				}
			}
			select.where(builder.and(predicateList
					.toArray(new Predicate[predicateList.size()])));
			select.distinct(true);

			if (orderClauses != null) {
				if (orderClauses.get(Constants.ORDER_BY) != null) {
					if (orderClauses.get(Constants.SORT_BY) != null
							&& orderClauses.get(Constants.SORT_BY)
									.equalsIgnoreCase("desc")) {
						select.orderBy(builder.desc(this.getOrder(from,
								orderClauses)));
					} else {
						select.orderBy(builder.asc(this.getOrder(from,
								orderClauses)));

					}
				}
			}
			TypedQuery<PaymentProductivity> query = em.createQuery(select);

			if (orderClauses != null && orderClauses.get("offset") != null
					&& orderClauses.get("limit") != null) {
				query.setFirstResult(Integer.parseInt(orderClauses
						.get("offset")));
				query.setMaxResults(Integer.parseInt(orderClauses.get("limit")));
			}
			LOGGER.info("SQL:: " + query.toString());
			ret = query.getResultList();
			if (ret != null) {
				Iterator<PaymentProductivity> paymentProductivityList = ret
						.iterator();
				while (paymentProductivityList.hasNext()) {
					PaymentProductivity paymentProductivityDB = paymentProductivityList
							.next();
					Hibernate.initialize(paymentProductivityDB
							.getPaymentBatch());

				}
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
			throw new ArgusException(e.getMessage(), e);
		}
		return ret;
	}

	/*
	 * @Override public int totalRecordForCapReport(Map<String, String>
	 * whereClauses) throws ArgusException { int ret = 0; try { CriteriaBuilder
	 * builder = em.getCriteriaBuilder(); CriteriaQuery<Long> cQuery =
	 * builder.createQuery(Long.class); Root<PaymentProductivity> from = cQuery
	 * .from(PaymentProductivity.class); CriteriaQuery<Long> select =
	 * cQuery.select(builder.count(from));
	 *
	 * List<Predicate> predicateList = new ArrayList<Predicate>();
	 * predicateList.add(builder.and(builder.equal( from.<String>
	 * get("deleted"), Constants.ZERO)));
	 * predicateList.add(builder.and(builder.equal( from.<String>
	 * get("paymentProdType"), Constants.THREE)));
	 *
	 * if (whereClauses != null && whereClauses.size() > Constants.ZERO) {
	 * Set<String> key = whereClauses.keySet(); for (String field : key) { if
	 * (field.equalsIgnoreCase(Constants.TICKET_NUMBER)) {
	 * LOGGER.info("found Ticket no  = " + whereClauses.get(field));
	 * predicateList .add(builder.and(builder.equal(
	 * from.get("paymentBatch").get("id"), Long.parseLong(whereClauses
	 * .get(Constants.TICKET_NUMBER))))); } if
	 * (field.equalsIgnoreCase(Constants.POSTED_BY_ID)) {
	 * LOGGER.info("found Posted By value  = " + whereClauses.get(field));
	 *
	 * predicateList .add(builder.and(builder.equal(
	 * from.get("createdBy").get("id"), Long.parseLong(whereClauses
	 * .get(Constants.POSTED_BY_ID))))); } if
	 * (field.equalsIgnoreCase(Constants.DOCTOR)) {
	 * LOGGER.info("found Doctor Name  = " + whereClauses.get(field));
	 *
	 * predicateList.add(builder.and(builder.equal( from.join("paymentBatch")
	 * .join(Constants.DOCTOR).get("id"), Long .parseLong(whereClauses
	 * .get(Constants.DOCTOR))))); } if
	 * (field.equalsIgnoreCase(Constants.INSURANCE)) {
	 * LOGGER.info("found Doctor Name  = " + whereClauses.get(field));
	 *
	 * predicateList.add(builder.and(builder.equal( from.join("paymentBatch")
	 * .join(Constants.INSURANCE).get("id"), Long.parseLong(whereClauses
	 * .get(Constants.INSURANCE))))); } // date posted from if
	 * (field.equalsIgnoreCase(Constants.DATE_POSTED_FROM)) {
	 * LOGGER.info("found DATE_POSTED_FROM = " +
	 * whereClauses.get(Constants.DATE_POSTED_FROM));
	 *
	 * Date fromDate = AkpmsUtil .akpmsNewDateFormat(whereClauses.get(field));
	 * Path<Date> dateReceivedPath = from.get("datePosted");
	 * predicateList.add(builder.greaterThanOrEqualTo( dateReceivedPath,
	 * fromDate));
	 *
	 * } // date posted to if (field.equalsIgnoreCase(Constants.DATE_POSTED_TO))
	 * { LOGGER.info("found DATE_POSTED_TO = " +
	 * whereClauses.get(Constants.DATE_POSTED_TO)); Date toDate =
	 * AkpmsUtil.getFormattedDate(whereClauses .get(field));
	 *
	 * Path<Date> dateReceivedPath = from.get("datePosted");
	 * predicateList.add(builder.lessThanOrEqualTo( dateReceivedPath, toDate));
	 * } // date received from if
	 * (field.equalsIgnoreCase(Constants.DATE_RECEIVED_FROM)) {
	 * LOGGER.info("found DATE_RECEIVED_FROM = " + whereClauses
	 * .get(Constants.DATE_RECEIVED_FROM));
	 *
	 * Date fromDate = AkpmsUtil .akpmsNewDateFormat(whereClauses.get(field));
	 * Path<Date> dateReceivedPath = from.get("createdOn");
	 * predicateList.add(builder.greaterThanOrEqualTo( dateReceivedPath,
	 * fromDate));
	 *
	 * } // date received to if
	 * (field.equalsIgnoreCase(Constants.DATE_RECEIVED_TO)) {
	 * LOGGER.info("found DATE_RECEIVED_TO = " +
	 * whereClauses.get(Constants.DATE_RECEIVED_TO)); Date toDate =
	 * AkpmsUtil.getFormattedDate(whereClauses .get(field));
	 *
	 * Path<Date> dateReceivedPath = from.get("createdOn");
	 * predicateList.add(builder.lessThanOrEqualTo( dateReceivedPath, toDate));
	 * }
	 *
	 * }
	 *
	 * } select.where(builder.and(predicateList .toArray(new
	 * Predicate[predicateList.size()]))); select.distinct(true);
	 * TypedQuery<Long> query = em.createQuery(select);
	 *
	 * ret = query.getSingleResult().intValue(); } catch (Exception e) {
	 * LOGGER.error(Constants.EXCEPTION, e); throw new
	 * ArgusException(e.getMessage(), e); } LOGGER.info("count = " + ret);
	 * return ret; }
	 */

	/*
	 * @Override public int totalRecordForEraReport(Map<String, String>
	 * whereClauses) throws ArgusException { int ret = 0; try { CriteriaBuilder
	 * builder = em.getCriteriaBuilder(); CriteriaQuery<Long> cQuery =
	 * builder.createQuery(Long.class); Root<PaymentProductivity> from = cQuery
	 * .from(PaymentProductivity.class); CriteriaQuery<Long> select =
	 * cQuery.select(builder.count(from));
	 *
	 * List<Predicate> predicateList = new ArrayList<Predicate>();
	 * predicateList.add(builder.and(builder.equal( from.<String>
	 * get("deleted"), 0))); predicateList.add(builder.and(builder.equal(
	 * from.<String> get("paymentProdType"), 1)));
	 *
	 * if (whereClauses != null && whereClauses.size() > 0) { Set<String> key =
	 * whereClauses.keySet(); for (String field : key) { if
	 * (field.equalsIgnoreCase(Constants.TICKET_NUMBER)) {
	 * LOGGER.info("found Ticket no  = " + whereClauses.get(field));
	 * predicateList .add(builder.and(builder.equal(
	 * from.get("paymentBatch").get("id"), Long.parseLong(whereClauses
	 * .get(Constants.TICKET_NUMBER))))); } if
	 * (field.equalsIgnoreCase(Constants.POSTED_BY_ID)) {
	 * LOGGER.info("found Posted By value  = " + whereClauses.get(field));
	 *
	 * predicateList .add(builder.and(builder.equal(
	 * from.get("createdBy").get("id"), Long.parseLong(whereClauses
	 * .get(Constants.POSTED_BY_ID))))); } if
	 * (field.equalsIgnoreCase(Constants.DOCTOR)) {
	 * LOGGER.info("found Doctor Name  = " + whereClauses.get(field));
	 *
	 * predicateList.add(builder.and(builder.equal( from.join("paymentBatch")
	 * .join(Constants.DOCTOR).get("id"), Long .parseLong(whereClauses
	 * .get(Constants.DOCTOR))))); } if
	 * (field.equalsIgnoreCase(Constants.INSURANCE)) {
	 * LOGGER.info("found Doctor Name  = " + whereClauses.get(field));
	 *
	 * predicateList.add(builder.and(builder.equal( from.join("paymentBatch")
	 * .join(Constants.INSURANCE).get("id"), Long.parseLong(whereClauses
	 * .get(Constants.INSURANCE))))); } // date posted from if
	 * (field.equalsIgnoreCase(Constants.DATE_POSTED_FROM)) {
	 * LOGGER.info("found DATE_POSTED_FROM = " +
	 * whereClauses.get(Constants.DATE_POSTED_FROM));
	 *
	 * Date fromDate = AkpmsUtil .akpmsNewDateFormat(whereClauses.get(field));
	 * Path<Date> dateReceivedPath = from.get("datePosted");
	 * predicateList.add(builder.greaterThanOrEqualTo( dateReceivedPath,
	 * fromDate));
	 *
	 * } // date posted to if (field.equalsIgnoreCase(Constants.DATE_POSTED_TO))
	 * { LOGGER.info("found DATE_POSTED_TO = " +
	 * whereClauses.get(Constants.DATE_POSTED_TO)); Date toDate =
	 * AkpmsUtil.getFormattedDate(whereClauses .get(field));
	 *
	 * Path<Date> dateReceivedPath = from.get("datePosted");
	 * predicateList.add(builder.lessThanOrEqualTo( dateReceivedPath, toDate));
	 * } // date received from if
	 * (field.equalsIgnoreCase(Constants.DATE_RECEIVED_FROM)) {
	 * LOGGER.info("found DATE_RECEIVED_FROM = " + whereClauses
	 * .get(Constants.DATE_RECEIVED_FROM));
	 *
	 * Date fromDate = AkpmsUtil .akpmsNewDateFormat(whereClauses.get(field));
	 * Path<Date> dateReceivedPath = from.get("createdOn");
	 * predicateList.add(builder.greaterThanOrEqualTo( dateReceivedPath,
	 * fromDate));
	 *
	 * } // date received to if
	 * (field.equalsIgnoreCase(Constants.DATE_RECEIVED_TO)) {
	 * LOGGER.info("found DATE_RECEIVED_TO = " +
	 * whereClauses.get(Constants.DATE_RECEIVED_TO)); Date toDate =
	 * AkpmsUtil.getFormattedDate(whereClauses .get(field));
	 *
	 * Path<Date> dateReceivedPath = from.get("createdOn");
	 * predicateList.add(builder.lessThanOrEqualTo( dateReceivedPath, toDate));
	 * }
	 *
	 * }
	 *
	 * } select.where(builder.and(predicateList .toArray(new
	 * Predicate[predicateList.size()]))); select.distinct(true);
	 * TypedQuery<Long> query = em.createQuery(select);
	 *
	 * ret = query.getSingleResult().intValue(); } catch (Exception e) {
	 * LOGGER.error(Constants.EXCEPTION, e); throw new
	 * ArgusException(e.getMessage(), e); } LOGGER.info("count = " + ret);
	 * return ret; }
	 */

	@Override
	public int getTotalOffsetRecord() throws ArgusException {
		int totalOffsetRecord = 0;
		StringBuilder queryString = new StringBuilder();
		queryString.append(TOTAL_OFFSET_RECORD);

		TypedQuery<Long> query = em.createQuery(queryString.toString(),
				Long.class);
		totalOffsetRecord = query.getSingleResult().intValue();
		return totalOffsetRecord;

	}

	@Override
	public int getTotalProductivityOffsetRecord(Long batchId)
			throws ArgusException {
		int totalOffsetRecord = 0;
		StringBuilder queryString = new StringBuilder();
		queryString.append(TOTAL_PRODUCTIVITYOFFSET_RECORD);

		TypedQuery<Long> query = em.createQuery(queryString.toString(),
				Long.class);
		query.setParameter("batchId", batchId);
		totalOffsetRecord = query.getSingleResult().intValue();
		return totalOffsetRecord;

	}

	private Path<Object> getOrder(Root<PaymentProductivity> from,
			Map<String, String> orderClauses) {
		if (orderClauses.get(Constants.ORDER_BY)
				.equals(Constants.TICKET_NUMBER)) {
			return from.join(Constants.PAYMENT_BATCH).get(Constants.ID);
		} else if (orderClauses.get(Constants.ORDER_BY).equals("doctorName")) {
			return from.join(Constants.PAYMENT_BATCH).get(Constants.DOCTOR)
					.get(Constants.BY_NAME);
		} else if (orderClauses.get(Constants.ORDER_BY).equals(
				Constants.INSURANCE)) {
			return from.join(Constants.PAYMENT_BATCH).get(Constants.INSURANCE)
					.get(Constants.BY_NAME);
		} else if (orderClauses.get(Constants.ORDER_BY).equals("depositAmt")) {
			return from.join(Constants.PAYMENT_BATCH).get("depositAmount");
		} else if (orderClauses.get(Constants.ORDER_BY).equals(
				Constants.POSTED_BY)) {
			return from.get(Constants.CREATED_BY).get(Constants.FIRST_NAME);
		} else if (orderClauses.get(Constants.ORDER_BY).equals(
				Constants.MANUALLY_POSTED_AMT)) {
			return from.get(Constants.PAYMENT_BATCH).get("manuallyPostedAmt");
		} else if (orderClauses.get(Constants.ORDER_BY).equals(
				Constants.ELEC_POSTED_AMT)) {
			return from.get(Constants.PAYMENT_BATCH).get("elecPostedAmt");
		} else if (orderClauses.get(Constants.ORDER_BY).equals(
				Constants.DATE_POSTED)) {
			return from.get(Constants.PAYMENT_BATCH).get("datePosted");
		} else if (orderClauses.get(Constants.ORDER_BY).equals(
				Constants.SUSPENSE)) {
			return from.get(Constants.PAYMENT_BATCH).get("suspenseAccount");
		} else {
			return from.get(orderClauses.get(Constants.ORDER_BY));
		}
	}

}
