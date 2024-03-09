package argus.repo.chargeBatchProcessing;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import argus.domain.ChargeBatchProcessing;
import argus.exception.ArgusException;
import argus.util.AkpmsUtil;
import argus.util.Constants;

@Repository
@Transactional
public class ChargeBatchProcesingDaoImpl implements ChargeBatchProcessingDao {

	private static final Logger LOGGER = Logger
			.getLogger(ChargeBatchProcesingDaoImpl.class);

	@Autowired
	private EntityManager em;

	@Override
	public void save(ChargeBatchProcessing chargeBatchProcessing)
			throws ArgusException {
		em.persist(chargeBatchProcessing);
	}

	@Override
	public void update(ChargeBatchProcessing chargeBatchProcessing)
			throws ArgusException {
		em.merge(chargeBatchProcessing);

	}

	/*
	 * private StringBuilder getQueryFindAll(Map<String, String> orderClauses,
	 * Map<String, String> whereClauses) {
	 *
	 * StringBuilder queryString = new StringBuilder();
	 *
	 * if (whereClauses != null && whereClauses.size() > 0) { Set<String> key =
	 * whereClauses.keySet(); int counter = 1; for (String field : key) { if
	 * (counter != 1) { queryString.append(" AND "); } else { if
	 * (field.equalsIgnoreCase(Constants.KEYWORD)) {
	 * queryString.append(" WHERE c.type"); queryString.append(" LIKE '%" +
	 * whereClauses.get(field) + "%' "); } if
	 * (field.equalsIgnoreCase(Constants.DOCTOR)) {
	 * queryString.append(" WHERE c.doctor.id = " +
	 * whereClauses.get(Constants.DOCTOR)); } } counter++; } }
	 *
	 * if (orderClauses != null) { if (orderClauses.get("orderBy") != null) {
	 * queryString .append(" ORDER BY c." + orderClauses.get("orderBy")); } else
	 * { queryString.append(" ORDER BY c.type"); } if
	 * (orderClauses.get("sortBy") != null) { queryString.append(" " +
	 * orderClauses.get("sortBy")); } else { queryString.append(" ASC"); } }
	 *
	 * return queryString; }
	 */

	@Override
	public ChargeBatchProcessing getChargeBatchProcessingById(long id,
			boolean dependency) throws ArgusException {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ChargeBatchProcessing> cQuery = cb
				.createQuery(ChargeBatchProcessing.class);
		Root<ChargeBatchProcessing> from = cQuery
				.from(ChargeBatchProcessing.class);

		cQuery.select(from).where(cb.equal(from.get("id"), id));
		TypedQuery<ChargeBatchProcessing> query = em.createQuery(cQuery);

		ChargeBatchProcessing chargeBatchProcessing = query.getSingleResult();
		if (chargeBatchProcessing != null && dependency) {
			Hibernate.initialize(chargeBatchProcessing.getReceivedBy());
			Hibernate.initialize(chargeBatchProcessing.getPostedBy());
		}
		return chargeBatchProcessing;
	}

	@Override
	public List<ChargeBatchProcessing> getAllChargeBatchProcessingById(
			Map<String, String> whereclause) throws ArgusException {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ChargeBatchProcessing> cQuery = cb
				.createQuery(ChargeBatchProcessing.class);
		Root<ChargeBatchProcessing> from = cQuery
				.from(ChargeBatchProcessing.class);

		cQuery.select(from);

		TypedQuery<ChargeBatchProcessing> query = em.createQuery(cQuery);

		return query.getResultList();
	}

	@Override
	public int totalRecord(Map<String, String> whereClauses,
			ChargeBatchProcessing chargeBatchProcessing) {
		int ret = 0;
		/*
		 * CriteriaBuilder builder = em.getCriteriaBuilder();
		 * CriteriaQuery<Long> cQuery = builder.createQuery(Long.class);
		 * Root<ChargeBatchProcessing> from = cQuery
		 * .from(ChargeBatchProcessing.class); CriteriaQuery<Long> select =
		 * cQuery.select(builder.count(from));
		 *
		 * List<Predicate> predicateList = new ArrayList<Predicate>();
		 * this.getBatchWherePredicate(builder, from, predicateList,
		 * chargeBatchProcessing, whereClauses);
		 * select.where(predicateList.toArray(new
		 * Predicate[predicateList.size()]));
		 *
		 * try { TypedQuery<Long> query = em.createQuery(select); ret =
		 * query.getSingleResult().intValue(); } catch (Exception e) {
		 * LOGGER.error("Exception = ", e); } LOGGER.info("count = " + ret);
		 */
		StringBuilder queryString = new StringBuilder();
		queryString
				.append("SELECT COUNT(*) FROM ChargeBatchProcessing AS chbatch WHERE 1 = 1");

		queryString.append(getWhereClause(whereClauses));

		try {
			TypedQuery<Long> query = em.createQuery(queryString.toString(),
					Long.class);
			ret = query.getSingleResult().intValue();
		} catch (Exception e) {
			LOGGER.error("Exception = ", e);
		}

		LOGGER.info("count = " + ret);

		return ret;
	}

	/*
	 * @Override public List<ChargeBatchProcessing> findAll( Map<String, String>
	 * orderClauses, Map<String, String> whereClauses, boolean dependency) {
	 * List<ChargeBatchProcessing> ret = null;
	 *
	 * StringBuilder queryString = new StringBuilder();
	 *
	 * queryString.append("SELECT c FROM ChargeBatchProcessing AS c ");
	 *
	 * queryString.append(getQueryFindAll(orderClauses, whereClauses)
	 * .toString());
	 *
	 * try { TypedQuery<ChargeBatchProcessing> query = em.createQuery(
	 * queryString.toString(), ChargeBatchProcessing.class); if (orderClauses !=
	 * null && orderClauses.get("offset") != null && orderClauses.get("limit")
	 * != null) { query.setFirstResult(Integer.parseInt(orderClauses
	 * .get("offset")));
	 * query.setMaxResults(Integer.parseInt(orderClauses.get("limit"))); }
	 *
	 * LOGGER.info("SQL:: " + queryString.toString());
	 *
	 * ret = query.getResultList(); if ((ret != null && !ret.isEmpty()) &&
	 * dependency) { for (ChargeBatchProcessing chargeBatchProcessing : ret) {
	 * Hibernate.initialize(chargeBatchProcessing.getReceivedBy()); } }
	 *
	 * } catch (Exception e) { LOGGER.error("Exception = ", e); }
	 *
	 * return ret; }
	 */

	@Override
	public List<ChargeBatchProcessing> findAllByDoctorId(Long id) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ChargeBatchProcessing> cQuery = cb
				.createQuery(ChargeBatchProcessing.class);
		Root<ChargeBatchProcessing> from = cQuery
				.from(ChargeBatchProcessing.class);

		cQuery.select(from).where(cb.equal(from.get("doctor").get("id"), id));
		TypedQuery<ChargeBatchProcessing> query = em.createQuery(cQuery);
		return query.getResultList();
	}

	@Override
	public List<ChargeBatchProcessing> findAll(
			ChargeBatchProcessing chargeBatchProcessing,
			Map<String, String> whereClause, Map<String, String> orderClause,
			boolean dependency)
			throws ArgusException {

		StringBuilder queryString = new StringBuilder();
		List<ChargeBatchProcessing> chargeBatches = null;

		queryString
				.append("SELECT chbatch FROM ChargeBatchProcessing AS chbatch WHERE 1 = 1 ");

		queryString.append(getWhereClause(whereClause));
		queryString.append(getOrderClause(orderClause));

		try {
			TypedQuery<ChargeBatchProcessing> query = em.createQuery(
					queryString.toString(), ChargeBatchProcessing.class);
			if (orderClause != null
					&& orderClause.get(Constants.OFFSET) != null
					&& orderClause.get(Constants.LIMIT) != null) {
				query.setFirstResult(Integer.parseInt(orderClause
						.get(Constants.OFFSET)));
				query.setMaxResults(Integer.parseInt(orderClause
						.get(Constants.LIMIT)));
			}

			chargeBatches = query.getResultList();

		} catch (Exception e) {
			LOGGER.error("Exception = ", e);
		}

		if ((chargeBatches != null && !chargeBatches.isEmpty()) && dependency) {
			for (ChargeBatchProcessing chargeBatchProcessing2 : chargeBatches) {
				Hibernate.initialize(chargeBatchProcessing2.getReceivedBy());
				Hibernate.initialize(chargeBatchProcessing2.getPostedBy());
			}
		}

		return chargeBatches;
	}

	@Override
	public List<ChargeBatchProcessing> findAllForReport(
			ChargeBatchProcessing chargeBatchProcessing,
			Map<String, String> whereClause, Map<String, String> orderClause,
			boolean dependency)
			throws ArgusException {

		StringBuilder queryString = new StringBuilder();
		List<ChargeBatchProcessing> chargeBatches = null;

		queryString
				.append("SELECT chbatch FROM ChargeBatchProcessing AS chbatch WHERE 1 = 1 ");

		queryString.append(getWhereClause(whereClause));
		queryString.append(getOrderClause(orderClause));

		try {
			TypedQuery<ChargeBatchProcessing> query = em.createQuery(
					queryString.toString(), ChargeBatchProcessing.class);
			if (orderClause != null
					&& orderClause.get(Constants.OFFSET) != null
					&& orderClause.get(Constants.LIMIT) != null) {
				query.setFirstResult(Integer.parseInt(orderClause
						.get(Constants.OFFSET)));
				query.setMaxResults(Integer.parseInt(orderClause
						.get(Constants.LIMIT)));
			}

			chargeBatches = query.getResultList();

			Collections.sort(chargeBatches, new Comparator<ChargeBatchProcessing>() {
			     public int compare(ChargeBatchProcessing lhs, ChargeBatchProcessing rhs) {
			         return (lhs.getId().compareTo(rhs.getId()));
			     }
			});

		} catch (Exception e) {
			LOGGER.error("Exception = ", e);
		}

		if ((chargeBatches != null && !chargeBatches.isEmpty()) && dependency) {
			for (ChargeBatchProcessing chargeBatchProcessing2 : chargeBatches) {
				Hibernate.initialize(chargeBatchProcessing2.getReceivedBy());
			}
		}

		return chargeBatches;
	}

	/*
	 * @Override public List<ChargeBatchProcessing> findAll(
	 * ChargeBatchProcessing chargeBatchProcessing, Map<String, String>
	 * whereClause, Map<String, String> orderClause) throws ArgusException {
	 * List<ChargeBatchProcessing> chargeBatchProcessingList = null;
	 *
	 * CriteriaBuilder builder = em.getCriteriaBuilder();
	 *
	 * CriteriaQuery<ChargeBatchProcessing> chargeBatchCriteriaQuery = builder
	 * .createQuery(ChargeBatchProcessing.class);
	 *
	 * Root<ChargeBatchProcessing> from = chargeBatchCriteriaQuery
	 * .from(ChargeBatchProcessing.class);
	 *
	 * CriteriaQuery<ChargeBatchProcessing> select = chargeBatchCriteriaQuery
	 * .select(from);
	 *
	 * List<Predicate> predicateList = new ArrayList<Predicate>();
	 *
	 * // this.getBatchWherePredicate(builder, from, predicateList, //
	 * chargeBatchProcessing, whereClause);
	 * select.where(predicateList.toArray(new Predicate[predicateList.size()]));
	 * if (orderClause != null) { if (orderClause.get(Constants.ORDER_BY) !=
	 * null) { if (orderClause.get(Constants.SORT_BY) != null &&
	 * orderClause.get(Constants.SORT_BY).equalsIgnoreCase( "desc")) {
	 * select.orderBy(builder.desc(this .getOrder(from, orderClause))); } else {
	 * select.orderBy(builder.asc(this.getOrder(from, orderClause))); } } }
	 * TypedQuery<ChargeBatchProcessing> query = em.createQuery(select); if
	 * (orderClause != null && orderClause.get("offset") != null &&
	 * orderClause.get("limit") != null) {
	 * query.setFirstResult(Integer.parseInt(orderClause.get("offset")));
	 * query.setMaxResults(Integer.parseInt(orderClause.get("limit"))); }
	 * chargeBatchProcessingList = query.getResultList();
	 *
	 * if (chargeBatchProcessingList != null) { for (ChargeBatchProcessing
	 * chargeBatchProcess : chargeBatchProcessingList) {
	 * Hibernate.initialize(chargeBatchProcess.getCreatedBy());
	 * Hibernate.initialize(chargeBatchProcess.getReceivedBy());
	 * Hibernate.initialize(chargeBatchProcess.getPostedBy()); } }
	 *
	 * return chargeBatchProcessingList; }
	 */
	/*
	 * private Path<Object> getOrder(Root<ChargeBatchProcessing> from,
	 * Map<String, String> orderClause) { if
	 * (orderClause.get(Constants.ORDER_BY).equals("doctor")) { return
	 * from.join("doctor", JoinType.LEFT).get("name");
	 *
	 * } else if (orderClause.get(Constants.ORDER_BY).equals("receivedBy")) {
	 * return from.join("receivedBy", JoinType.LEFT).get("firstName");
	 *
	 * } else if (orderClause.get(Constants.ORDER_BY).equals("postedBy")) {
	 * return from.join("postedBy", JoinType.LEFT).get("firstName");
	 *
	 * } else { return from.get(orderClause.get(Constants.ORDER_BY)); }
	 *
	 * }
	 */

	/*
	 * private void getBatchWherePredicate(CriteriaBuilder builder,
	 * Root<ChargeBatchProcessing> from, List<Predicate> predicateList,
	 * ChargeBatchProcessing chargeBatchProcessing, Map<String, String>
	 * whereClause) {
	 *
	 * if (chargeBatchProcessing.getDoctor() != null &&
	 * chargeBatchProcessing.getDoctor().getId() != null) {
	 * predicateList.add(builder.and(builder.equal(
	 * from.get("doctor").get("id"), chargeBatchProcessing
	 * .getDoctor().getId()))); }
	 *
	 * if (chargeBatchProcessing.getReceivedBy() != null &&
	 * chargeBatchProcessing.getReceivedBy().getId() != null) { predicateList
	 * .add(builder.and(builder.equal( from.get("receivedBy").get("id"),
	 * chargeBatchProcessing.getReceivedBy().getId()))); }
	 *
	 * if (chargeBatchProcessing.getDosFrom() != null) { Path<Date>
	 * dateCreatedPath = from.get("createdOn");
	 * predicateList.add(builder.greaterThanOrEqualTo(dateCreatedPath,
	 * chargeBatchProcessing.getDosFrom()));
	 *
	 * } if (chargeBatchProcessing.getDosTo() != null) { Path<Date>
	 * dateCreatedPath = from.get("createdOn"); predicateList
	 * .add(builder.lessThanOrEqualTo(dateCreatedPath, AkpmsUtil
	 * .getFormattedDate(chargeBatchProcessing.getDosTo()))); }
	 *
	 * if (chargeBatchProcessing.getDateReceivedFrom() != null) { Path<Date>
	 * dateReceivedPath = from.get("dateReceived");
	 * predicateList.add(builder.greaterThanOrEqualTo(dateReceivedPath,
	 * chargeBatchProcessing.getDateReceivedFrom())); } if
	 * (chargeBatchProcessing.getDateReceivedTo() != null) { Path<Date>
	 * dateReceivedPath = from.get("dateReceived");
	 * predicateList.add(builder.lessThanOrEqualTo(dateReceivedPath,
	 * AkpmsUtil.getFormattedDate(chargeBatchProcessing .getDateReceivedTo())));
	 * }
	 *
	 * if (chargeBatchProcessing.getDateBatchPostedFrom() != null) { Path<Date>
	 * dateBatchPath = from.get("dateBatchPosted");
	 * predicateList.add(builder.greaterThanOrEqualTo(dateBatchPath,
	 * chargeBatchProcessing.getDateBatchPostedFrom()));
	 *
	 * } if (chargeBatchProcessing.getDateBatchPostedTo() != null) { Path<Date>
	 * dateBatchPath = from.get("dateBatchPosted");
	 * predicateList.add(builder.lessThanOrEqualTo(dateBatchPath,
	 * AkpmsUtil.getFormattedDate(chargeBatchProcessing
	 * .getDateBatchPostedTo()))); }
	 *
	 * if (chargeBatchProcessing.getPostedBy() != null &&
	 * chargeBatchProcessing.getPostedBy().getId() != null) {
	 * predicateList.add(builder.and(builder.equal(from.get("postedBy")
	 * .get("id"), chargeBatchProcessing.getPostedBy().getId()))); }
	 *
	 * if (whereClause != null && !whereClause.isEmpty()) {
	 * LOGGER.info("in where clause"); int month = 0; int year = 0; Set<String>
	 * keys = whereClause.keySet(); for (String key : keys) { if
	 * (key.equals(Constants.CHARGE_BATCH_REPORT_TYPE)) { String reportType =
	 * whereClause.get(key); LOGGER.info("report type in doa impl " +
	 * reportType); String[] reportTypes = reportType.split(","); if
	 * (reportTypes != null && reportTypes.length > 1) {
	 * LOGGER.info("In if section "); for (String type : reportTypes) {
	 * LOGGER.info("type = " + type); this.getBatchReportTypePredicate(builder,
	 * from, predicateList, type.trim()); } } else {
	 * LOGGER.info("In else portion type = " + reportType);
	 * this.getBatchReportTypePredicate(builder, from, predicateList,
	 * reportType.trim()); } } else if (key
	 * .equals(Constants.CHARGE_BATCH_PROCESSING_TICKET_RANGE_FROM)) { String
	 * ticketRangeFrom = whereClause.get(key);
	 * predicateList.add(builder.and(builder.ge( from.<Long> get("id"),
	 * Long.parseLong(ticketRangeFrom)))); } else if (key
	 * .equals(Constants.CHARGE_BATCH_PROCESSING_TICKET_RANGE_TO)) { String
	 * ticketRangeTo = whereClause.get(key);
	 * predicateList.add(builder.and(builder.le( from.<Long> get("id"),
	 * Long.parseLong(ticketRangeTo)))); } } if
	 * (whereClause.containsKey(Constants.MONTH) &&
	 * whereClause.get(Constants.MONTH) != null) { month =
	 * Integer.parseInt(whereClause.get(Constants.MONTH));
	 * LOGGER.info("month  = " + month); } if
	 * (whereClause.containsKey(Constants.YEAR) &&
	 * whereClause.get(Constants.YEAR) != null) { year =
	 * Integer.parseInt(whereClause.get(Constants.YEAR)); LOGGER.info("Year = "
	 * + year); } if (month != 0 && year != 0) {
	 * LOGGER.info("both month and year available");
	 * predicateList.add(builder.and( builder.equal( builder.function("year",
	 * Integer.class, from.get("postedOn")), year), builder.equal(
	 * builder.function("month", Integer.class, from.get("postedOn")), month)));
	 * } else if (month != 0) { LOGGER.info("only month available ");
	 * predicateList.add(builder.and(builder.equal( builder.function("month",
	 * Integer.class, from.get("postedOn")), month))); } else if (year != 0) {
	 * LOGGER.info("only year available");
	 * predicateList.add(builder.and(builder.equal( builder.function("year",
	 * Integer.class, from.get("postedOn")), year))); } } }
	 *
	 * private void getBatchReportTypePredicate(CriteriaBuilder builder,
	 * Root<ChargeBatchProcessing> from, List<Predicate> predicateList, String
	 * type) {
	 *
	 * if (type.equals(Constants.CHARGE_BATCH_REPORT_TYPE_POSTEDBATCH)) {
	 * predicateList.add(builder.or(builder.or(
	 * builder.isNotNull(from.get("postedBy")),
	 * builder.isNotNull(from.get("postedOn"))))); } else if (type
	 * .equals(Constants.CHARGE_BATCH_REPORT_TYPE_UNPOSTEDBATCH)) {
	 * predicateList.add(builder.or(builder.or(
	 * builder.isNull(from.get("postedBy")),
	 * builder.isNull(from.get("postedOn")))));
	 *
	 * } else if (type
	 * .equals(Constants.CHARGE_BATCH_REPORT_TYPE_BATCHWITHDISCREPANCY)) {
	 * predicateList.add(builder.or(builder.or(
	 * builder.notEqual(from.get("numberOfSuperbills"),
	 * from.get("numberOfSuperbillsArgus")),
	 * builder.notEqual(from.get("numberOfAttachments"),
	 * from.get("numberOfAttachmentsArgus"))))); } }
	 */

	public ChargeBatchProcessing getChargeBatchProcessingShortDetailById(long id)
			throws ArgusException {

		String queryString = "SELECT cbp FROM ChargeBatchProcessing cbp WHERE cbp.id = :id";
		TypedQuery<ChargeBatchProcessing> cq = em.createQuery(queryString,
				ChargeBatchProcessing.class);
		cq.setParameter("id", id);
		cq.getSingleResult();

		return cq.getSingleResult();
	}

	@Override
	public ChargeBatchProcessing findById(Long id, boolean dependancies)
			throws ArgusException {
		ChargeBatchProcessing chargeBatchProcessing = em.find(
				ChargeBatchProcessing.class, id);
		return chargeBatchProcessing;
	}

	private StringBuffer getWhereClause(Map<String, String> whereClauses) {
		LOGGER.info("where clause size :" + whereClauses.size());
		StringBuffer queryString = new StringBuffer();

		if (whereClauses != null && whereClauses.size() > 0) {
			Set<String> key = whereClauses.keySet();

			for (String field : key) {
				// billing month
				// if (field.equalsIgnoreCase(Constants.MONTH)) {
				// LOGGER.info("found billing month = "
				// + whereClauses.get(Constants.MONTH));
				// queryString.append(" AND chbatch.billingMonth = "
				// + whereClauses.get(Constants.MONTH));
				// }
				//
				// // billing year
				// if (field.equalsIgnoreCase(Constants.YEAR)) {
				// LOGGER.info("found billing year = "
				// + whereClauses.get(Constants.YEAR));
				// queryString.append(" AND chbatch.billingYear = "
				// + whereClauses.get(Constants.YEAR));
				// }

				// doctor id
				if (field.equalsIgnoreCase(Constants.DOCTOR)) {
					LOGGER.info("found DOCTOR = "
							+ whereClauses.get(Constants.DOCTOR));
					queryString.append(" AND chbatch.doctor.id = "
							+ whereClauses.get(Constants.DOCTOR));
				}

				// created by
				if (field.equalsIgnoreCase(Constants.CREATED_BY)) {
					LOGGER.info("found CREATED_BY = "
							+ whereClauses.get(Constants.CREATED_BY));
					queryString.append(" AND chbatch.createdBy.id = "
							+ whereClauses.get(Constants.CREATED_BY));
				}

				// posted by
				if (field.equalsIgnoreCase(Constants.POSTED_BY)) {
					LOGGER.info("found POSTED_ID = "
							+ whereClauses.get(Constants.POSTED_BY));
					queryString.append(" AND chbatch.postedBy.id = "
							+ whereClauses.get(Constants.POSTED_BY));
				}

				// received by
				if (field.equalsIgnoreCase(Constants.RECEIVED_BY)) {
					LOGGER.info("found RECEIVED_BY = "
							+ whereClauses.get(Constants.RECEIVED_BY));
					queryString.append(" AND chbatch.receivedBy.id = "
							+ whereClauses.get(Constants.RECEIVED_BY));
				}

				// date deposit from
				if (field.equalsIgnoreCase(Constants.DATE_RECEIVED_FROM)) {
					LOGGER.info("found DATE_RECEIVED_FROM = "
							+ whereClauses.get(Constants.DATE_RECEIVED_FROM));
					queryString
							.append(" AND chbatch.dateReceived >= '"
									+ AkpmsUtil.akpmsDateFormat(
											AkpmsUtil
													.akpmsNewDateFormat(whereClauses
															.get(Constants.DATE_RECEIVED_FROM)),
											Constants.MYSQL_DATE_FORMAT) + "'");
				}

				// date deposit to
				if (field.equalsIgnoreCase(Constants.DATE_RECEIVED_TO)) {
					LOGGER.info("found DATE_RECEIVED_TO = "
							+ whereClauses.get(Constants.DATE_RECEIVED_TO));
					queryString.append(" AND chbatch.dateReceived <= '"
							+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
									.getFormattedDate(whereClauses
											.get(Constants.DATE_RECEIVED_TO)),
									Constants.MYSQL_DATE_FORMAT_WITH_TIME)
							+ "'");
				}

				// date created from
				if (field.equalsIgnoreCase(Constants.DATE_CREATED_FROM)) {
					LOGGER.info("found DATE_CREATED_FROM = "
							+ whereClauses.get(Constants.DATE_CREATED_FROM));
					queryString
							.append(" AND chbatch.createdOn >= '"
									+ AkpmsUtil.akpmsDateFormat(
											AkpmsUtil
													.akpmsNewDateFormat(whereClauses
															.get(Constants.DATE_CREATED_FROM)),
											Constants.MYSQL_DATE_FORMAT) + "'");
				}

				// date created to
				if (field.equalsIgnoreCase(Constants.DATE_CREATED_TO)) {
					LOGGER.info("found DATE_CREATED_TO = "
							+ whereClauses.get(Constants.DATE_CREATED_TO));
					queryString.append(" AND chbatch.createdOn <= '"
							+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
									.getFormattedDate(whereClauses
											.get(Constants.DATE_CREATED_TO)),
									Constants.MYSQL_DATE_FORMAT_WITH_TIME)
							+ "'");
				}

				// date posted from (CT POSTED DATE)
				if (field.equalsIgnoreCase(Constants.DATE_BATCH_POSTED_FROM)) {
					LOGGER.info("found DATE_BATCH_POSTED_FROM = "
							+ whereClauses
									.get(Constants.DATE_BATCH_POSTED_FROM));
					queryString
							.append(" AND chbatch.dateBatchPosted >= '"
									+ AkpmsUtil.akpmsDateFormat(
											AkpmsUtil
													.akpmsNewDateFormat(whereClauses
															.get(Constants.DATE_BATCH_POSTED_FROM)),
											Constants.MYSQL_DATE_FORMAT) + "'");
				}

				// date posted to (CT POSTED DATE)
				if (field.equalsIgnoreCase(Constants.DATE_BATCH_POSTED_TO)) {
					LOGGER.info("found DATE_BATCH_POSTED_TO = "
							+ whereClauses.get(Constants.DATE_BATCH_POSTED_TO));
					queryString
							.append(" AND chbatch.dateBatchPosted <= '"
									+ AkpmsUtil.akpmsDateFormat(
											AkpmsUtil
													.getFormattedDate(whereClauses
															.get(Constants.DATE_BATCH_POSTED_TO)),
											Constants.MYSQL_DATE_FORMAT_WITH_TIME)
									+ "'");
				}

				// date DOS from
				if (field.equalsIgnoreCase(Constants.DOS_FROM)) {
					LOGGER.info("found DOS_FROM = "
							+ whereClauses.get(Constants.DOS_FROM));
					queryString.append(" AND chbatch.dosFrom >= '"
							+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
									.akpmsNewDateFormat(whereClauses
											.get(Constants.DOS_FROM)),
									Constants.MYSQL_DATE_FORMAT) + "'");
				}

				// date DOS to
				if (field.equalsIgnoreCase(Constants.DOS_TO)) {
					LOGGER.info("found DOS_TO = "
							+ whereClauses.get(Constants.DOS_TO));
					queryString.append(" AND chbatch.dosTo <= '"
							+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
									.getFormattedDate(whereClauses
											.get(Constants.DOS_TO)),
									Constants.MYSQL_DATE_FORMAT_WITH_TIME)
							+ "'");
				}

				// TICKET_NUMBER_SEARCH
				if (field.equalsIgnoreCase(Constants.TICKET_NUMBER_SEARCH)) {
					LOGGER.info("found" + Constants.TICKET_NUMBER_SEARCH
							+ " = "
							+ whereClauses.get(Constants.TICKET_NUMBER_SEARCH));

					queryString.append(generateTicketNumberQuery(whereClauses
							.get(Constants.TICKET_NUMBER_SEARCH)));
				}

				// get only received or not received batches
				if (field.equalsIgnoreCase(Constants.RECEIVED)) {
					LOGGER.info("found" + Constants.RECEIVED + " = "
							+ whereClauses.get(Constants.RECEIVED));

					if (whereClauses.get(Constants.RECEIVED).equalsIgnoreCase(
							"1")) {
						queryString
								.append(" AND chbatch.receivedBy IS NOT NULL");
					} else if (whereClauses.get(Constants.RECEIVED)
							.equalsIgnoreCase("0")) {
						queryString.append(" AND chbatch.receivedBy IS NULL");
					}
				}

				// get only received or not received batches
				if (field.equalsIgnoreCase(Constants.CHARGE_BATCH_REPORT_TYPE)) {
					LOGGER.info("found "
							+ Constants.CHARGE_BATCH_REPORT_TYPE
							+ " = "
							+ whereClauses
									.get(Constants.CHARGE_BATCH_REPORT_TYPE));

					String reportType = whereClauses.get(
							Constants.CHARGE_BATCH_REPORT_TYPE).trim();
					// String[] reportTypes = reportType.split(",");

					List<String> reportTypes = Arrays.asList(reportType
							.split("\\s*,\\s*"));

					if (reportTypes.size() > 0) {
						if (!(reportTypes
								.contains(Constants.CHARGE_BATCH_REPORT_TYPE_UNPOSTEDBATCH) && reportTypes
								.contains(Constants.CHARGE_BATCH_REPORT_TYPE_POSTEDBATCH))) {

							if (reportTypes
									.contains(Constants.CHARGE_BATCH_REPORT_TYPE_POSTEDBATCH)) {
								queryString
										.append(" AND chbatch.postedBy IS NOT NULL");
							} else if (reportTypes
									.contains(Constants.CHARGE_BATCH_REPORT_TYPE_UNPOSTEDBATCH)) {
								queryString
										.append(" AND chbatch.postedBy IS NULL");
							}

						}
						if (reportTypes
								.contains(Constants.CHARGE_BATCH_REPORT_TYPE_BATCHWITHDISCREPANCY)) {
							queryString
									.append(" AND (chbatch.numberOfSuperbills != chbatch.numberOfSuperbillsArgus or  chbatch.numberOfAttachments != chbatch.numberOfSuperbillsArgus)");
						}
					}

					// if (reportTypes != null && reportTypes.length >= 1) {
					// LOGGER.info("In if section ");
					// for (String type : reportTypes) {
					// LOGGER.info("type = " + type);
					// if (type.trim()
					// .equalsIgnoreCase(
					// Constants.CHARGE_BATCH_REPORT_TYPE_UNPOSTEDBATCH)) {
					// queryString.append(" AND chbatch.postedBy IS NULL");
					// } else if (type
					// .trim()
					// .equalsIgnoreCase(Constants.CHARGE_BATCH_REPORT_TYPE_POSTEDBATCH))
					// {
					// queryString.append(" AND chbatch.postedBy IS NOT NULL");
					// } else if (type
					// .trim()
					// .equalsIgnoreCase(Constants.CHARGE_BATCH_REPORT_TYPE_BATCHWITHDISCREPANCY))
					// {
					// queryString
					// .append(" AND (chbatch.numberOfSuperbills != chbatch.numberOfSuperbillsArgus or  chbatch.numberOfAttachments != chbatch.numberOfSuperbillsArgus)");
					// }
					// }
					// }
				}
			}
		}

		return queryString;
	}

	// inputStr = "1,4,5-10,34,2-4,7"
	private String generateTicketNumberQuery(String inputStr) {
		LOGGER.debug("in commanSepString " + inputStr);

		HashSet<Long> ticketNumberSet = new HashSet<Long>();
		StringBuffer betweenQueryString = new StringBuffer();

		if (inputStr != "") {
			List<String> commaSepArr = Arrays.asList(inputStr
					.split("\\s*,\\s*"));

			if (commaSepArr.size() > 0) {
				for (int counter = 0; counter < commaSepArr.size(); counter++) {
					// console.log(commaSepArr[counter]);
					String element = commaSepArr.get(counter);

					if (element.indexOf("-") > -1) {
						List<String> hypenSepArr = Arrays.asList(element
								.split("\\s*-\\s*"));

						if (hypenSepArr.size() == 2) {
							long upperLt = 0L;
							long lowerLt = 0L;
							if (Long.valueOf(hypenSepArr.get(1)) >= Long
									.valueOf(hypenSepArr.get(0))) {
								upperLt = Long.valueOf(hypenSepArr.get(1));
								lowerLt = Long.valueOf(hypenSepArr.get(0));
							} else if (Long.valueOf(hypenSepArr.get(1)) < Long
									.valueOf(hypenSepArr.get(0))) {
								upperLt = Long.valueOf(hypenSepArr.get(0));
								lowerLt = Long.valueOf(hypenSepArr.get(1));
							}
							betweenQueryString
									.append(" AND (chbatch.id between "
											+ lowerLt + " and " + upperLt
											+ ") ");
						}
					} else {
						ticketNumberSet.add(Long.valueOf(element));
					}
				}
			}
		}

		String outputString = "";
		if (ticketNumberSet.size() > 0) {
			outputString = ticketNumberSet.toString();

			outputString = " and chbatch.id in ("
					+ outputString.substring(1, outputString.length() - 1)
					+ ") ";
		}

		outputString += betweenQueryString.toString();

		LOGGER.debug("out commanSepString " + outputString);
		return outputString;
	}

	private StringBuffer getOrderClause(Map<String, String> orderClauses) {

		StringBuffer queryString = new StringBuffer();
		if (orderClauses != null) {
			if (orderClauses.get("orderBy") != null) {
				queryString.append(" ORDER BY chbatch."
						+ orderClauses.get("orderBy"));
			} else {
				queryString.append(" ORDER BY chbatch.id");
			}
			if (orderClauses.get("sortBy") != null) {
				queryString.append(" " + orderClauses.get("sortBy"));
			} else {
				queryString.append(" ASC");
			}
		}
		return queryString;
	}
}
