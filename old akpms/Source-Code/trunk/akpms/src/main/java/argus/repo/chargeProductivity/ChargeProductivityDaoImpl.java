package argus.repo.chargeProductivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import argus.domain.ChargeProductivity;
import argus.exception.ArgusException;
import argus.util.AkpmsUtil;
import argus.util.Constants;

@Repository
@Transactional
public class ChargeProductivityDaoImpl implements ChargeProductivityDao {

	private static final Logger LOGGER = Logger
			.getLogger(ChargeProductivityDaoImpl.class);
	private static final String ON_HOLD = "onhold";
	@Autowired
	private EntityManager entityManager;

	@Override
	public void saveProductivity(ChargeProductivity chargeProductivity)
			throws ArgusException {
		entityManager.persist(chargeProductivity);
	}

	@Override
	public void updateProductivity(ChargeProductivity chargeProductivity)
			throws ArgusException {
		entityManager.merge(chargeProductivity);
	}

	@Override
	public ChargeProductivity getChargeProductivityById(Long id)
			throws ArgusException {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ChargeProductivity> query = builder
				.createQuery(ChargeProductivity.class);
		Root<ChargeProductivity> from = query.from(ChargeProductivity.class);

		query.select(from)
				.where(builder.and(builder.equal(from.get("id"), id)));
		TypedQuery<ChargeProductivity> tQuery = entityManager
				.createQuery(query);

		return tQuery.getSingleResult();
	}

	public long checkProductivityWithRejectWorkFlowByTicketNumber(long id)
			throws ArgusException {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> cQuery = builder.createQuery(Long.class);
		Root<ChargeProductivity> from = cQuery.from(ChargeProductivity.class);
		CriteriaQuery<Long> select = cQuery.select(builder.count(from));

		TypedQuery<Long> query = entityManager.createQuery(select.where(
				builder.equal(from.join("ticketNumber").get("id"), id),
				builder.equal(from.get("workFlow"), "reject")));
		return query.getSingleResult();
	}

	public int totalRecord(Map<String, String> whereClauses)
			throws ArgusException {
		int ret = 0;
		StringBuilder queryString = new StringBuilder();
		queryString
				.append("SELECT COUNT(*) FROM ChargeProductivity AS chgProd WHERE 1=1 ");

		queryString.append(getWhereClause(whereClauses));

		try {
			TypedQuery<Long> query = entityManager.createQuery(
					queryString.toString(), Long.class);
			ret = query.getSingleResult().intValue();
		} catch (Exception e) {
			LOGGER.error("Exception = ", e);
		}

		LOGGER.info("count = " + ret);
		return ret;
	}

	private StringBuffer getWhereClause(Map<String, String> whereClauses) {
		// LOGGER.info("where clause size :" + whereClauses.size());
		StringBuffer queryString = new StringBuffer();

		if (whereClauses != null && whereClauses.size() > 0) {
			Set<String> key = whereClauses.keySet();

			for (String field : key) {
				// created by
				if (field.equalsIgnoreCase(Constants.CREATED_BY)) {
					LOGGER.info("found CREATED_ID = "
							+ whereClauses.get(Constants.CREATED_BY));
					queryString.append(" AND chgProd.createdBy.id = "
							+ whereClauses.get(Constants.CREATED_BY));
				}

				// date created from
				else if (field.equalsIgnoreCase(Constants.DATE_CREATED_FROM)) {
					LOGGER.info("found DATE_CREATED_FROM = "
							+ whereClauses.get(Constants.DATE_CREATED_FROM));
					queryString.append(" AND chgProd.createdOn >= '"
							+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
									.akpmsNewDateFormat(whereClauses
											.get(Constants.DATE_CREATED_FROM)),
									Constants.MYSQL_DATE_FORMAT) + "'");
				}

				// date created to
				else if (field.equalsIgnoreCase(Constants.DATE_CREATED_TO)) {
					LOGGER.info("found DATE_CREATED_TO = "
							+ whereClauses.get(Constants.DATE_CREATED_TO));

					Calendar cl = Calendar.getInstance();
					cl.setTime(AkpmsUtil.akpmsNewDateFormat(whereClauses
							.get(Constants.DATE_CREATED_TO)));
					cl.set(Calendar.HOUR_OF_DAY,
							cl.getActualMaximum(Calendar.HOUR_OF_DAY));
					cl.set(Calendar.MINUTE,
							cl.getActualMaximum(Calendar.MINUTE));
					cl.set(Calendar.SECOND,
							cl.getActualMaximum(Calendar.SECOND));
					queryString.append(" AND chgProd.createdOn <= '"
							+ AkpmsUtil.akpmsDateFormat(cl.getTime(),
									"yyyy-MM-dd HH:mm:ss") + "'");
				}

				// date created from
				else if (field.equalsIgnoreCase(Constants.SCAN_DATE_FROM)) {
					LOGGER.info("found SCAN_DATE_FROM = "
							+ whereClauses.get(Constants.SCAN_DATE_FROM));
					queryString.append(" AND chgProd.scanDate >= '"
							+ AkpmsUtil.akpmsFormatDateSQL(whereClauses
									.get(Constants.SCAN_DATE_FROM)) + "'");
				}

				// date created to
				else if (field.equalsIgnoreCase(Constants.SCAN_DATE_TO)) {
					LOGGER.info("found SCAN_DATE_TO = "
							+ whereClauses.get(Constants.SCAN_DATE_TO));
					queryString.append(" AND chgProd.scanDate <= '"
							+ AkpmsUtil.akpmsFormatDateSQL(whereClauses
									.get(Constants.SCAN_DATE_TO)) + "'");
				}
				
				else if (field.equalsIgnoreCase(Constants.POSTING_DATE_FROM)) {
					LOGGER.info("found DATE_CREATED_FROM = "
							+ whereClauses.get(Constants.POSTING_DATE_FROM));

					queryString.append(" AND chgProd.ticketNumber.dateBatchPosted >= '"
							+ AkpmsUtil.akpmsFormatDateSQL(whereClauses
									.get(Constants.POSTING_DATE_FROM)) + "'");
				}

				else if (field.equalsIgnoreCase(Constants.POSTING_DATE_TO)) {
					LOGGER.info("found POSTING_DATE_TO = "
							+ whereClauses.get(Constants.POSTING_DATE_TO));

					Calendar cl = Calendar.getInstance();
					cl.setTime(AkpmsUtil.akpmsNewDateFormat(
							whereClauses.get(Constants.POSTING_DATE_TO),
							Constants.TIME_STAMP_FORMAT));
					cl.set(Calendar.HOUR_OF_DAY,
							cl.getActualMaximum(Calendar.HOUR_OF_DAY));
					cl.set(Calendar.MINUTE,
							cl.getActualMaximum(Calendar.MINUTE));
					cl.set(Calendar.SECOND,
							cl.getActualMaximum(Calendar.SECOND));

					queryString.append(" AND chgProd.ticketNumber.dateBatchPosted <= '"
							+ AkpmsUtil.akpmsDateFormat(cl.getTime(),
									"yyyy-MM-dd HH:mm:ss") + "'");
				}

				// ticket number
				else if (field.equalsIgnoreCase(Constants.TICKET_NUMBER)) {
					LOGGER.info("found TICKET NUMBER = "
							+ whereClauses.get(Constants.TICKET_NUMBER));
					queryString.append(" AND chgProd.ticketNumber.id = "
							+ whereClauses.get(Constants.TICKET_NUMBER));
				}

				// productivityType
				else if (field.equalsIgnoreCase(Constants.PROD_TYPE)) {
					LOGGER.info("found PROD_TYPE  = "
							+ whereClauses.get(Constants.PROD_TYPE));
					queryString.append(" AND chgProd.productivityType = '"
							+ whereClauses.get(Constants.PROD_TYPE) + "'");
				}

				else if (field.equalsIgnoreCase(Constants.WORKFLOW)) {
					LOGGER.info("WORKFLOW = "
							+ whereClauses.get(Constants.WORKFLOW));
					queryString.append(" AND chgProd.workFlow = '"
							+ whereClauses.get(Constants.WORKFLOW) + "'");
				}

				else if (field.equalsIgnoreCase(Constants.ON_HOLD)) {
					LOGGER.info(Constants.ON_HOLD
							+ whereClauses.get(Constants.ON_HOLD));
					queryString.append(" AND chgProd.onHold = "
							+ whereClauses.get(Constants.ON_HOLD));
				}
				// Fetch records on basis of QAWorksheet criteria [START]
				else if (field
						.equalsIgnoreCase(Constants.CHARGE_DEPARTMENT_DEMO)) {
					queryString
							.append(" AND chgProd.productivityType = 'Demo'");
				} else if (field
						.equalsIgnoreCase(Constants.CHARGE_DEPARTMENT_CE)) {
					queryString.append(" AND chgProd.productivityType = 'CE'");
				} else if (field
						.equalsIgnoreCase(Constants.CHARGE_DEPARTMENT_CODING_PRIMARY)
						|| field.equalsIgnoreCase(Constants.CHARGE_DEPARTMENT_CODING_SPECIAL)) {
					queryString
							.append(" AND chgProd.productivityType = 'Coding'");
				}

				else if (field.equalsIgnoreCase(Constants.MONTH)) {
					queryString.append(" AND MONTH(chgProd.createdOn) = "
							+ whereClauses.get(Constants.MONTH));
				}

				else if (field.equalsIgnoreCase(Constants.YEAR)) {
					queryString.append(" AND YEAR(chgProd.createdOn) = "
							+ whereClauses.get(Constants.YEAR));
				}

				else if (field.equalsIgnoreCase(Constants.QA_WORKSHEET_MONTH)) {
					queryString.append(" AND MONTH(chgProd.scanDate) = "
							+ whereClauses.get(Constants.QA_WORKSHEET_MONTH));
				}

				else if (field.equalsIgnoreCase(Constants.QA_WORKSHEET_YEAR)) {
					queryString.append(" AND YEAR(chgProd.scanDate) = "
							+ whereClauses.get(Constants.QA_WORKSHEET_YEAR));
				}

				

				else if (field.equalsIgnoreCase(Constants.USER)) {
					queryString.append(" AND chgProd.createdBy.id IN ("
							+ whereClauses.get(Constants.USER) + ")");
				} else if (field.equalsIgnoreCase(Constants.DOCTOR)) {
					queryString
							.append(" AND chgProd.ticketNumber.doctor.id IN ("
									+ whereClauses.get(Constants.DOCTOR) + ")");
				}
				// Fetch records on basis of QAWorksheet criteria [END]
			}
		}

		return queryString;
	}

	public List<ChargeProductivity> findAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses, boolean dependency)
			throws ArgusException {
		List<ChargeProductivity> ret = null;
		StringBuilder queryString = new StringBuilder();

		queryString
				.append("SELECT chgProd FROM ChargeProductivity AS chgProd WHERE 1=1 ");

		queryString.append(getQueryFindAll(orderClauses, whereClauses)
				.toString());

		try {
			TypedQuery<ChargeProductivity> query = entityManager.createQuery(
					queryString.toString(), ChargeProductivity.class);
			if (orderClauses != null
					&& orderClauses.get(Constants.OFFSET) != null
					&& orderClauses.get(Constants.LIMIT) != null) {
				query.setFirstResult(Integer.parseInt(orderClauses
						.get(Constants.OFFSET)));
				query.setMaxResults(Integer.parseInt(orderClauses
						.get(Constants.LIMIT)));
			}

			LOGGER.info("SQL:: " + queryString.toString());

			ret = query.getResultList();
			if (ret != null && dependency) {
				for (ChargeProductivity chargeProductivity : ret) {
					Hibernate.initialize(chargeProductivity.getTicketNumber());
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception = ", e);
		}
		return ret;
	}

	private StringBuilder getQueryFindAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses) {

		StringBuilder queryString = new StringBuilder();

		queryString.append(getWhereClause(whereClauses));
		// dont want to break old code
		// this is block is implemented for productivity sampling where users
		// random records
		// are pulled up.
		if (orderClauses != null
				&& orderClauses.containsKey(Constants.RANDOM_RECOREDS)) {
			LOGGER.info("Found : "
					+ orderClauses.get(Constants.RANDOM_RECOREDS));
			queryString.append(" ORDER BY RAND()");

			// MAKE orderClauses null so next order by not gets executed
			orderClauses = null;
			return queryString;
		}
		if (orderClauses != null) {
			if (orderClauses.get("orderBy") != null) {
				queryString.append(" ORDER BY chgProd."
						+ orderClauses.get("orderBy"));
			} else {
				queryString.append(" ORDER BY chgProd.type");
			}
			if (orderClauses.get("sortBy") != null) {
				queryString.append(" " + orderClauses.get("sortBy"));
			} else {
				queryString.append(" ASC");
			}
		} else {
			queryString.append(" ORDER BY chgProd.id desc");
		}

		return queryString;
	}

	@Override
	public int totalOnHold() throws ArgusException {
		int ret = 0;
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> cQuery = builder.createQuery(Long.class);
			Root<ChargeProductivity> from = cQuery
					.from(ChargeProductivity.class);
			CriteriaQuery<Long> select = cQuery.select(builder.count(from));

			List<Predicate> predicateList = new ArrayList<Predicate>();
			predicateList.add(builder.and(builder.equal(
					from.<String> get("workFlow"), ON_HOLD)));
			predicateList.add(builder.and(builder.isNull(from
					.<String> get("onHoldOn"))));
			predicateList.add(builder.and(builder.isNull(from
					.<String> get("onHoldBy"))));

			select.where(builder.and(predicateList
					.toArray(new Predicate[predicateList.size()])));
			select.distinct(true);
			TypedQuery<Long> query = entityManager.createQuery(select);

			ret = query.getSingleResult().intValue();
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
			throw new ArgusException(e.getMessage(), e);
		}
		LOGGER.info("count = " + ret);
		return ret;
	}

	@Override
	public int totalOnHoldRecord() throws ArgusException {
		int ret = 0;
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> cQuery = builder.createQuery(Long.class);
			Root<ChargeProductivity> from = cQuery
					.from(ChargeProductivity.class);
			CriteriaQuery<Long> select = cQuery.select(builder.count(from));

			List<Predicate> predicateList = new ArrayList<Predicate>();
			// predicateList.add(builder.and(builder.equal(
			// from.<String> get("workFlow"), ON_HOLD)));

			predicateList.add(builder.and(builder.equal(
					from.<String> get("onHold"), true)));

			select.where(builder.and(predicateList
					.toArray(new Predicate[predicateList.size()])));
			select.distinct(true);
			TypedQuery<Long> query = entityManager.createQuery(select);

			ret = query.getSingleResult().intValue();
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
			throw new ArgusException(e.getMessage(), e);
		}
		LOGGER.info("count = " + ret);
		return ret;
	}

}
