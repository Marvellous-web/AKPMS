package argus.repo.chargeProductivity;

import java.util.ArrayList;
import java.util.Calendar;
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

import argus.domain.ChargeProdReject;
import argus.exception.ArgusException;
import argus.util.Constants;

@Repository
@Transactional
public class ChargeProdRejectDaoImpl implements ChargeProdRejectDao {

	private static final Log LOGGER = LogFactory
			.getLog(ChargeProdRejectDaoImpl.class);

	@Autowired
	private EntityManager em;

	private static final String FIRST_REQUEST_DUE_COUNT = "select count(*) from charge_productivity_reject WHERE resolved =0 and (date_of_first_request_to_doctor_office is null and DATE_SUB(ADDTIME(TIMESTAMP(CURDATE()),'23:59:59.999999'),INTERVAL 6 DAY) >= created_on)";

	private static final String SECOND_REQUEST_DUE_COUNT = "select count(*) from charge_productivity_reject WHERE resolved =0 and (date_of_first_request_to_doctor_office is not null and date_of_second_request_to_doctor_office is null and DATE_SUB(ADDTIME(TIMESTAMP(CURDATE()),'23:59:59.999999'),INTERVAL 3 DAY) >= date_of_first_request_to_doctor_office)";

	private static final String NUMBER_OF_FIRST_REQUEST_COUNT = "select count(*) from charge_productivity_reject WHERE date_of_first_request_to_doctor_office is not null and date_of_second_request_to_doctor_office is null and resolved = 0";

	private static final String NUMBER_OF_SECOND_REQUEST_COUNT = "select count(*) from charge_productivity_reject WHERE (date_of_first_request_to_doctor_office is not null and date_of_second_request_to_doctor_office is not null) and resolved = 0";

	private static final String RESOLVED_REJECTION_WITH_DUMMY_CPT_COUNT = "select count(*) from charge_productivity_reject where resolved=1 and dummy_cpt =1";

	private static final String RESOLVED_REJECTION_COUNT = "select count(*) from charge_productivity_reject where resolved=1";

	private static final String NUMBER_OF_REJECTIONS_COUNT = "select count(*) from charge_productivity_reject where resolved = 0 and (date_of_first_request_to_doctor_office is null and date_of_second_request_to_doctor_office is null)";

	@Override
	public void save(ChargeProdReject chargeProdReject) throws ArgusException {
		LOGGER.info("In save method");
		em.persist(chargeProdReject);
	}

	@Override
	public void update(ChargeProdReject chargeProdReject) throws ArgusException {
		LOGGER.info("In update method");
		em.merge(chargeProdReject);
	}

	@Override
	public ChargeProdReject getChargeProdRejectById(Long id,
			boolean dependencies) throws ArgusException {
		LOGGER.info("In get by Id method");
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ChargeProdReject> cQuery = cb
				.createQuery(ChargeProdReject.class);
		Root<ChargeProdReject> from = cQuery.from(ChargeProdReject.class);

		cQuery.select(from).where(cb.equal(from.get("id"), id));
		ChargeProdReject chargeProdReject = em.createQuery(cQuery)
				.getSingleResult();
		if (chargeProdReject != null && dependencies) {
			Hibernate.initialize(chargeProdReject.getChargeBatchProcessing());
		}
		return em.createQuery(cQuery).getSingleResult();
	}

	@Override
	public int totalRecords(Map<String, String> whereClauses)
			throws ArgusException {
		int ret = 0;

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> cQuery = builder.createQuery(Long.class);
		Root<ChargeProdReject> from = cQuery.from(ChargeProdReject.class);
		CriteriaQuery<Long> select = cQuery.select(builder.count(from));

		List<Predicate> predicateList = new ArrayList<Predicate>();
		getWhereClause(whereClauses, predicateList, builder, from);
		select.where(builder.and(predicateList
				.toArray(new Predicate[predicateList.size()])));
		select.distinct(true);
		TypedQuery<Long> query = em.createQuery(select);

		ret = query.getSingleResult().intValue();
		return ret;
	}

	public List<ChargeProdReject> findAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses, boolean dependancies)
			throws ArgusException {

		List<ChargeProdReject> ret = null;
		List<ChargeProdReject> chargeList = null;
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<ChargeProdReject> chargeProdRejectQuery = builder
				.createQuery(ChargeProdReject.class);
		Root<ChargeProdReject> from = chargeProdRejectQuery
				.from(ChargeProdReject.class);
		CriteriaQuery<ChargeProdReject> select = chargeProdRejectQuery
				.select(from);

		List<Predicate> predicateList = new ArrayList<Predicate>();

		getWhereClause(whereClauses, predicateList, builder, from);

		select.where(builder.and(predicateList
				.toArray(new Predicate[predicateList.size()])));
		select.distinct(true);

		if (orderClauses != null) {
			if (orderClauses.get(Constants.ORDER_BY) != null) {
				if (orderClauses.get(Constants.SORT_BY) != null
						&& orderClauses.get(Constants.SORT_BY)
								.equalsIgnoreCase("desc")) {
					select.orderBy(builder.desc(from.get(orderClauses
							.get(Constants.ORDER_BY))));
				} else {
					select.orderBy(builder.asc(from.get(orderClauses
							.get(Constants.ORDER_BY))));

				}
			} else {
				select.orderBy(builder.asc(from.get(Constants.PATIENT_NAME)));
			}
		}
		TypedQuery<ChargeProdReject> query = em.createQuery(select);
		if (orderClauses != null && orderClauses.get("offset") != null
				&& orderClauses.get("limit") != null) {
			query.setFirstResult(Integer.parseInt(orderClauses.get("offset")));
			query.setMaxResults(Integer.parseInt(orderClauses.get("limit")));
		}

		LOGGER.info("SQL:: " + query.toString());
		ret = query.getResultList();
		if (dependancies && ret != null && ret.size() > Constants.ZERO) {
			Iterator<ChargeProdReject> chargeRejectIterator = ret.iterator();
			chargeList = new ArrayList<ChargeProdReject>();
			while (chargeRejectIterator.hasNext()) {
				ChargeProdReject chargeProdReject = chargeRejectIterator.next();
				Hibernate.initialize(chargeProdReject
						.getChargeBatchProcessing());
				chargeList.add(chargeProdReject);
			}

		}
		return chargeList;
	}

	private void getWhereClause(Map<String, String> whereClauses,
			List<Predicate> predicateList, CriteriaBuilder builder,
			Root<ChargeProdReject> from) {

		if (whereClauses != null && whereClauses.size() > 0) {
			Set<String> key = whereClauses.keySet();
			for (String field : key) {
				if (field.equalsIgnoreCase(Constants.KEYWORD)) {
					Predicate p = builder.or(builder.equal(
							from.get("chargeBatchProcessing").get("id"),
							whereClauses.get(field)));
					predicateList.add(builder.and(p));
				}
				if (field.equalsIgnoreCase("dummyCpt")) {
					LOGGER.info("found resolved with dummy CPT value  = "
							+ whereClauses.get(field));

					predicateList.add(builder.and(
							builder.equal(from.get("dummyCpt"), 1),
							builder.equal(from.get("resolved"), 1)));
				}
				if (field.equalsIgnoreCase("resolved")) {
					LOGGER.info("coming for resolved rejections = "
							+ whereClauses.get(field));

					predicateList.add(builder.and(builder.equal(
							from.get("resolved"), 1)));
				}
				if (field.equalsIgnoreCase(Constants.TICKET_NUMBER)) {

					predicateList.add(builder.and(builder.equal(
							from.get("chargeBatchProcessing").get("id"),
							whereClauses.get(Constants.TICKET_NUMBER))));
				}
				if (field.equalsIgnoreCase("newRejections")) {
					LOGGER.info("coming for new rejections ,value  = "
							+ whereClauses.get(field));

					predicateList
							.add(builder.and(
									builder.equal(from.get("resolved"), 0),
									builder.isNull(from
											.get("dateOfFirstRequestToDoctorOffice")),
									builder.isNull(from
											.get("dateOfSecondRequestToDoctorOffice"))));
				}
				if (field.equalsIgnoreCase("requestRecord")) {
					if (whereClauses.get(field).equals("1")) {
						predicateList
								.add(builder.and(
										builder.equal(from.get("resolved"), 0),
										builder.isNotNull(from
												.get("dateOfFirstRequestToDoctorOffice")),
										builder.isNull(from
												.get("dateOfSecondRequestToDoctorOffice"))));
					} else {
						predicateList
								.add(builder.and(
										builder.equal(from.get("resolved"), 0),
										builder.isNotNull(from
												.get("dateOfFirstRequestToDoctorOffice")),
										builder.isNotNull(from
												.get("dateOfSecondRequestToDoctorOffice"))));
					}
				}
				if (field.equalsIgnoreCase("requestDueRecord")) {
					long dayInMs = 1000 * 60 * 60 * 24;
					Date date = new Date();
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					calendar.add(Calendar.HOUR_OF_DAY,
							calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
					calendar.add(Calendar.MINUTE,
							calendar.getActualMaximum(Calendar.MINUTE));
					calendar.add(Calendar.SECOND,
							calendar.getActualMaximum(Calendar.SECOND));

					if (whereClauses.get(field).equals("1")) {
						Path<Date> createdOn = from.get("createdOn");
						predicateList.add(builder.and(builder.equal(
								from.get("resolved"), 0), builder.isNull(from
								.get("dateOfFirstRequestToDoctorOffice")),
								builder.greaterThanOrEqualTo(builder
										.literal(new java.sql.Date(calendar
												.getTimeInMillis()
												- (dayInMs * Constants.SIX))),
										createdOn)));
					} else {
						Path<Date> dateOfFirstRequestToDoctorOffice = from
								.get("dateOfFirstRequestToDoctorOffice");
						predicateList
								.add(builder.and(
										builder.equal(from.get("resolved"), 0),
										builder.isNotNull(from
												.get("dateOfFirstRequestToDoctorOffice")),
										builder.isNull(from
												.get("dateOfSecondRequestToDoctorOffice")),
										builder.greaterThanOrEqualTo(
												builder.literal(new java.sql.Date(
														calendar.getTimeInMillis()
																- (dayInMs * Constants.THREE))),
												dateOfFirstRequestToDoctorOffice)));
					}
				}
			}
		}

	}

	public long getRejectionCount(Long batchId) throws ArgusException {
		StringBuffer query = new StringBuffer(NUMBER_OF_REJECTIONS_COUNT);
		if (batchId != null) {
			query.append(" and charge_batch_id = " + batchId);
		}
		return ((java.math.BigInteger) em.createNativeQuery(query.toString())
				.getSingleResult()).longValue();
	}

	public long getResolvedRejectionCount(Long batchId) throws ArgusException {
		StringBuffer query = new StringBuffer(RESOLVED_REJECTION_COUNT);
		if (batchId != null) {
			query.append(" and charge_batch_id = " + batchId);
		}
		return ((java.math.BigInteger) em.createNativeQuery(query.toString())
				.getSingleResult()).longValue();
	}

	public long getResolvedWithDummyCPTCount(Long batchId)
			throws ArgusException {
		StringBuffer query = new StringBuffer(
				RESOLVED_REJECTION_WITH_DUMMY_CPT_COUNT);
		if (batchId != null) {
			query.append(" and charge_batch_id = " + batchId);
		}
		return ((java.math.BigInteger) em.createNativeQuery(query.toString())
				.getSingleResult()).longValue();
	}

	public long getFirstRequestDueCount(Long batchId) throws ArgusException {
		StringBuffer query = new StringBuffer(FIRST_REQUEST_DUE_COUNT);
		if (batchId != null) {
			query.append(" and charge_batch_id = " + batchId);
		}
		return ((java.math.BigInteger) em.createNativeQuery(query.toString())
				.getSingleResult()).longValue();
	}

	public long getSecondRequestDueCount(Long batchId) throws ArgusException {
		StringBuffer query = new StringBuffer(SECOND_REQUEST_DUE_COUNT);
		if (batchId != null) {
			query.append(" and charge_batch_id = " + batchId);
		}
		return ((java.math.BigInteger) em.createNativeQuery(query.toString())
				.getSingleResult()).longValue();
	}

	public long getNumberOfFirstRequestsCount(Long batchId)
			throws ArgusException {
		StringBuffer query = new StringBuffer(NUMBER_OF_FIRST_REQUEST_COUNT);
		if (batchId != null) {
			query.append(" and charge_batch_id = " + batchId);
		}
		return ((java.math.BigInteger) em.createNativeQuery(query.toString())
				.getSingleResult()).longValue();
	}

	public long getNumberOfSecondRequestsCount(Long batchId)
			throws ArgusException {
		StringBuffer query = new StringBuffer(NUMBER_OF_SECOND_REQUEST_COUNT);
		if (batchId != null) {
			query.append(" and charge_batch_id = " + batchId);
		}
		return ((java.math.BigInteger) em.createNativeQuery(query.toString())
				.getSingleResult()).longValue();
	}

	public long hasRejectionDone(Long batchId) throws ArgusException {
		String query = "SELECT COUNT(cpr.chargeBatchProcessing) FROM ChargeProdReject cpr WHERE cpr.chargeBatchProcessing.id = :id";
		Query qry = em.createQuery(query);
		qry.setParameter("id", batchId);

		return (Long) qry.getSingleResult();
	}
}
