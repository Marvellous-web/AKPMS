package argus.repo.chargeProductivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import argus.domain.ChargeProdReject;
import argus.exception.ArgusException;
import argus.util.AkpmsUtil;
import argus.util.Constants;

@Repository
@Transactional
public class ChargeProdRejectDaoImpl implements ChargeProdRejectDao {

	private static final Logger LOGGER = Logger
			.getLogger(ChargeProdRejectDaoImpl.class);

	@Autowired
	private EntityManager em;

	private static final String FIRST_REQUEST_DUE_COUNT = "select count(*) from charge_productivity_reject WHERE resolved =0 and (date_of_first_request_to_doctor_office is null and DATE_SUB(ADDTIME(TIMESTAMP(CURDATE()),'23:59:59.999999'),INTERVAL 6 DAY) >= created_on)";

	private static final String SECOND_REQUEST_DUE_COUNT = "select count(*) from charge_productivity_reject WHERE resolved =0 and (date_of_first_request_to_doctor_office is not null and date_of_second_request_to_doctor_office is null and DATE_SUB(ADDTIME(TIMESTAMP(CURDATE()),'23:59:59.999999'),INTERVAL 3 DAY) >= date_of_first_request_to_doctor_office)";

	private static final String NUMBER_OF_FIRST_REQUEST_COUNT = "select count(*) from charge_productivity_reject WHERE date_of_first_request_to_doctor_office is not null and date_of_second_request_to_doctor_office is null and resolved = 0";

	private static final String NUMBER_OF_SECOND_REQUEST_COUNT = "select count(*) from charge_productivity_reject WHERE (date_of_first_request_to_doctor_office is not null and date_of_second_request_to_doctor_office is not null) and resolved = 0";

	private static final String RESOLVED_REJECTION_WITH_DUMMY_CPT_COUNT = "select count(*) from charge_productivity_reject where resolved=1 and dummy_cpt =1";

	private static final String RESOLVED_REJECTION_COUNT = "select count(*) from charge_productivity_reject where resolved=1";

	private static final String NUMBER_OF_REJECTIONS_COUNT = "select count(*) from charge_productivity_reject where resolved = 0 and (date_of_first_request_to_doctor_office is null and date_of_second_request_to_doctor_office is null)";

	private static final String BACTHES_BY_STATUS = "select status,count(status) from charge_productivity_reject group by status";

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

		StringBuilder queryString = new StringBuilder();
		queryString
				.append("SELECT COUNT(*) FROM ChargeProdReject AS chProdReject WHERE 1 = 1 ");

		queryString.append(getWhereClause(whereClauses));

		// LOGGER.error("QUERY: " + queryString.toString());

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

	public List<ChargeProdReject> findAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses, boolean dependancies)
			throws ArgusException {

		StringBuilder queryString = new StringBuilder();
		List<ChargeProdReject> chargeProdReject = null;

		queryString
				.append("SELECT chProdReject FROM ChargeProdReject AS chProdReject WHERE 1 = 1");

		queryString.append(getWhereClause(whereClauses));
		queryString.append(getOrderClause(orderClauses));

		try {
			TypedQuery<ChargeProdReject> query = em.createQuery(
					queryString.toString(), ChargeProdReject.class);
			if (orderClauses != null
					&& orderClauses.get(Constants.OFFSET) != null
					&& orderClauses.get(Constants.LIMIT) != null) {
				query.setFirstResult(Integer.parseInt(orderClauses
						.get(Constants.OFFSET)));
				query.setMaxResults(Integer.parseInt(orderClauses
						.get(Constants.LIMIT)));
			}
			chargeProdReject = query.getResultList();

		} catch (Exception e) {
			LOGGER.error("Exception = ", e);
		}

		return chargeProdReject;
	}

	private StringBuffer getWhereClause(Map<String, String> whereClauses) {
		LOGGER.info("where clause size :" + whereClauses.size());
		StringBuffer queryString = new StringBuffer();

		if (whereClauses != null && whereClauses.size() > 0) {
			Set<String> key = whereClauses.keySet();
			for (String field : key) {
				if (field.equalsIgnoreCase("dummyCpt")) {
					queryString
							.append(" AND chProdReject.dummyCpt = 1 AND chProdReject.resolved = 1 ");
				}

				if (field.equalsIgnoreCase("resolved")) {
					queryString.append(" AND chProdReject.resolved = 1 ");
				}

				if (field.equalsIgnoreCase(Constants.STATUS)) {
					queryString.append(" AND chProdReject.status IN ("
							+ whereClauses.get(Constants.STATUS) + ")");
				}

				if (field.equalsIgnoreCase(Constants.DOCTOR_ID)) {
					queryString
							.append(" AND chProdReject.chargeBatchProcessing.doctor.id = "
									+ whereClauses.get(Constants.DOCTOR_ID));
				}

				if (field.equalsIgnoreCase("reasonToReject")) {
					queryString.append(" AND chProdReject.reasonToReject = '"
							+ whereClauses.get("reasonToReject") + "'");
				}

				if (field.equalsIgnoreCase("workFlow")) {
					queryString.append(" AND chProdReject.workFlow = "
							+ whereClauses.get("workFlow"));
				}

				if (field.equalsIgnoreCase(Constants.TICKET_NUMBER)) {
					queryString
							.append(" AND chProdReject.chargeBatchProcessing =  "
									+ whereClauses.get(Constants.TICKET_NUMBER));
				}

				// keyword will search into ticket number
				if (field.equalsIgnoreCase(Constants.KEYWORD)) {
					String searchKeyword = whereClauses.get(Constants.KEYWORD)
							.trim()
							.toLowerCase();

					queryString.append(" AND ( ");

					queryString.append("    LOWER(STR(chProdReject.chargeBatchProcessing.id)) LIKE '%" + searchKeyword + "%'");
					//queryString.append(" OR LOWER(chProdReject.location.name) LIKE '%"+ searchKeyword + "%'");
					queryString.append("  OR LOWER(chProdReject.patientName) LIKE '%"+ searchKeyword + "%'");
					queryString.append(" OR LOWER(chProdReject.account) LIKE '%"+ searchKeyword + "%'");
				    queryString.append(" OR LOWER(chProdReject.insuranceType) LIKE '%"+ searchKeyword + "%'");
					// queryString.append(" OR LOWER(chProdReject.chargeBatchProcessing.doctor.name) LIKE '%"+
					// searchKeyword + "%'");
					// queryString.append(" OR LOWER(chProdReject.createdBy.firstName) LIKE '%"+
					// searchKeyword + "%'");
					// queryString.append(" OR LOWER(chProdReject.createdBy.lastName) LIKE '%"+
					// searchKeyword + "%'");

					queryString.append(" ) ");
				}

				if (field.equalsIgnoreCase("newRejections")) {
					queryString
							.append(" AND chProdReject.resolved = 0 "
									+ " AND chProdReject.dateOfFirstRequestToDoctorOffice IS NULL "
									+ " AND chProdReject.dateOfSecondRequestToDoctorOffice IS NULL ");
				}

				if (field.equalsIgnoreCase("requestRecord")) {
					if (whereClauses.get(field).equals("1")) {
						queryString
								.append(" AND chProdReject.resolved = 0 "
										+ " AND chProdReject.dateOfFirstRequestToDoctorOffice IS NOT NULL "
										+ " AND chProdReject.dateOfSecondRequestToDoctorOffice IS NULL ");
					} else {
						queryString
								.append(" AND chProdReject.resolved = 0 "
										+ " AND chProdReject.dateOfFirstRequestToDoctorOffice IS NOT NULL "
										+ " AND chProdReject.dateOfSecondRequestToDoctorOffice IS NOT NULL ");
					}
				}

				// created by
				if (field.equalsIgnoreCase(Constants.CREATED_BY)) {
					LOGGER.info("found CREATED_BY = "
							+ whereClauses.get(Constants.CREATED_BY));
					queryString.append(" AND chProdReject.createdBy.id = "
							+ whereClauses.get(Constants.CREATED_BY));
				}

				// date created from
				if (field.equalsIgnoreCase(Constants.DATE_CREATED_FROM)) {
					LOGGER.info("found DATE_CREATED_FROM = "
							+ whereClauses.get(Constants.DATE_CREATED_FROM));
					queryString.append(" AND chProdReject.createdOn >= '"
							+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
									.akpmsNewDateFormat(whereClauses
											.get(Constants.DATE_CREATED_FROM)),
									Constants.MYSQL_DATE_FORMAT) + "'");
				}

				// date created to
				if (field.equalsIgnoreCase(Constants.DATE_CREATED_TO)) {
					LOGGER.info("found DATE_CREATED_TO = "
							+ whereClauses.get(Constants.DATE_CREATED_TO));
					queryString.append(" AND chProdReject.createdOn <= '"
							+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
									.getFormattedDate(whereClauses
											.get(Constants.DATE_CREATED_TO)),
									Constants.MYSQL_DATE_FORMAT_WITH_TIME)
							+ "'");
				}

				if (field.equalsIgnoreCase("requestDueRecord")) {
					if (whereClauses.get(field).equals("1")) {
						queryString
								.append(" AND chProdReject.resolved = 0 "
										+ " AND chProdReject.dateOfFirstRequestToDoctorOffice IS NULL "
										+ " AND chProdReject.dateOfSecondRequestToDoctorOffice IS NULL "
										+ " AND  DATEDIFF(now(),chProdReject.createdOn) >= "
										+ Constants.SIX);
					} else {
						queryString
								.append(" AND chProdReject.resolved = 0 "
										+ " AND chProdReject.dateOfFirstRequestToDoctorOffice IS NOT NULL "
										+ " AND chProdReject.dateOfSecondRequestToDoctorOffice IS NULL "
										+ " AND  DATEDIFF(now(),chProdReject.dateOfFirstRequestToDoctorOffice) >= "
										+ Constants.THREE);
					}
				}
			}
		}

		return queryString;
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

	private StringBuffer getOrderClause(Map<String, String> orderClauses) {

		StringBuffer queryString = new StringBuffer();
		if (orderClauses != null) {
			if (orderClauses.get("orderBy") != null) {
				queryString.append(" ORDER BY chProdReject."
						+ orderClauses.get("orderBy"));
			} else {
				queryString.append(" ORDER BY chProdReject.id");
			}
			if (orderClauses.get("sortBy") != null) {
				queryString.append(" " + orderClauses.get("sortBy"));
			} else {
				queryString.append(" ASC");
			}
		}
		return queryString;
	}

	@Override
	public List<Object[]> getAllRejectBatchesByStatus() throws ArgusException {
		List<Object[]> listObject = new ArrayList<Object[]>();
		Query query = em.createNativeQuery(BACTHES_BY_STATUS);
		listObject = query.getResultList();
		return listObject;
	}
}
