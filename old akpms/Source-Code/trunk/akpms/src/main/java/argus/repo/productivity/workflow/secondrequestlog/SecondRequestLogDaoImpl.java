package argus.repo.productivity.workflow.secondrequestlog;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import argus.domain.SecondRequestLogWorkFlow;
import argus.exception.ArgusException;
import argus.repo.productivity.helper.ArProductivityDaoHelper;
import argus.util.AkpmsUtil;
import argus.util.Constants;

@Repository
@Transactional
public class SecondRequestLogDaoImpl implements SecondRequestLogDao {

	private static final Logger LOGGER = Logger
			.getLogger(SecondRequestLogDaoImpl.class);
	@Autowired
	private EntityManager em;

	@Override
	public SecondRequestLogWorkFlow findById(Long id) throws ArgusException {
		SecondRequestLogWorkFlow secondRequestLogWorkFlow = em.find(
				SecondRequestLogWorkFlow.class, id);

		return secondRequestLogWorkFlow;
	}

	@Override
	public void addSecondRequestLogWorkFlow(
			SecondRequestLogWorkFlow secondRequestLogWorkFlow)
			throws ArgusException {
		em.persist(secondRequestLogWorkFlow);
		LOGGER.info("SecondRequestLogWorkFlow Added successfully");

	}

	@Override
	public void updateSecondRequestLogWorkFlow(
			SecondRequestLogWorkFlow secondRequestLogWorkFlow)
			throws ArgusException {
		em.merge(secondRequestLogWorkFlow);
		LOGGER.info("SecondRequestLogWorkFlow Updated successfully");

	}

	@Override
	public SecondRequestLogWorkFlow findByProductivityId(Long arProdId)
			throws ArgusException, NoResultException {

		LOGGER.info("int [findByProductivityId] : arProdId = "
				+ arProdId);
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<SecondRequestLogWorkFlow> criteria = builder
				.createQuery(SecondRequestLogWorkFlow.class);
		Root<SecondRequestLogWorkFlow> secondRequestLogWorkFlow = criteria
				.from(SecondRequestLogWorkFlow.class);
		criteria.select(secondRequestLogWorkFlow).where(
				builder.equal(secondRequestLogWorkFlow.get("arProductivity").get("id"),
						arProdId));
		return em.createQuery(criteria).getSingleResult();


}

	public int totalRecord(Map<String, String> whereClauses)
			throws ArgusException {
		LOGGER.info("in [totalRecord] method :");
		int totalRecords = 0;
		StringBuilder queryString = new StringBuilder();
		queryString
				.append("SELECT COUNT(*) FROM SecondRequestLogWorkFlow AS d ");
		queryString.append(" WHERE 1=1 ");

		queryString.append(getWhereClause(whereClauses));

		LOGGER.info("querystring :: " + queryString.toString());
		TypedQuery<Long> query = em.createQuery(queryString.toString(),
				Long.class);
		totalRecords = query.getSingleResult().intValue();
		LOGGER.info("count = " + totalRecords);
		return totalRecords;
	}

	public List<SecondRequestLogWorkFlow> findAll(
			Map<String, String> orderClauses, Map<String, String> whereClauses,
			boolean dependancies) throws ArgusException {
		LOGGER.info("in [findAll(Map<String, String> orderClauses,"
				+ " Map<String, String> whereClauses, boolean dependancies)] method :");
		List<SecondRequestLogWorkFlow> secondRequestLogWorkFlowList = null;

		StringBuilder queryString = new StringBuilder();

		queryString.append("SELECT d FROM SecondRequestLogWorkFlow AS d ");
		queryString.append(" WHERE 1=1");
		//
		// queryString
		// .append(ArProductivityDaoHelper.getWhereClause(whereClauses));
		queryString.append(getWhereClause(whereClauses));

		queryString
				.append(ArProductivityDaoHelper.getOrderClause(orderClauses));

		LOGGER.info("querystring :: " + queryString.toString());
		TypedQuery<SecondRequestLogWorkFlow> query = em.createQuery(
				queryString.toString(), SecondRequestLogWorkFlow.class);
		if (orderClauses != null && orderClauses.get(Constants.OFFSET) != null
				&& orderClauses.get(Constants.LIMIT) != null) {
			query.setFirstResult(Integer.parseInt(orderClauses
					.get(Constants.OFFSET)));
			query.setMaxResults(Integer.parseInt(orderClauses
					.get(Constants.LIMIT)));
		}

		LOGGER.info("SQL:: " + queryString.toString());

		secondRequestLogWorkFlowList = query.getResultList();

		if (dependancies && secondRequestLogWorkFlowList != null
				&& secondRequestLogWorkFlowList.size() > Constants.ZERO) {
			Iterator<SecondRequestLogWorkFlow> adjIterator = secondRequestLogWorkFlowList
					.iterator();

			while (adjIterator.hasNext()) {
				SecondRequestLogWorkFlow adjLogWorkFlow = adjIterator.next();
				Hibernate.initialize(adjLogWorkFlow.getArProductivity());

			}
		}

		return secondRequestLogWorkFlowList;
	}

	private StringBuilder getWhereClause(Map<String, String> whereClauses) {
		LOGGER.info("where clause size :" + whereClauses.size());
		StringBuilder queryString = new StringBuilder();

		if (whereClauses != null && whereClauses.size() > Constants.ZERO) {
			Set<String> key = whereClauses.keySet();
			for (String field : key) {
				if (field.equalsIgnoreCase(Constants.AR_PROD_PATIENT_NAME)) {
					LOGGER.info(Constants.AR_PROD_PATIENT_NAME + " = "
							+ whereClauses.get(field));
					queryString
							.append(" AND d.arProductivity.patientName LIKE '%"
									+ whereClauses
											.get(Constants.AR_PROD_PATIENT_NAME)
									+ "%' ");
				} else if (field
						.equalsIgnoreCase(Constants.AR_PROD_PATIENT_ACC_NO)) {
					LOGGER.info(Constants.AR_PROD_PATIENT_ACC_NO + " = "
							+ whereClauses.get(field));
					queryString
							.append(" AND d.arProductivity.patientAccNo LIKE '%"
									+ whereClauses
											.get(Constants.AR_PROD_PATIENT_ACC_NO)
									+ "%' ");
				} else if (field.equalsIgnoreCase("dosFrom")) {
					queryString.append(" AND d.arProductivity.dos >= '"
							+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
									.akpmsNewDateFormat(whereClauses
											.get("dosFrom")),
									Constants.MYSQL_DATE_FORMAT) + "'");
				} else if (field.equalsIgnoreCase("dosTo")) {
					queryString.append(" AND d.arProductivity.dos <= '"
							+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
									.akpmsNewDateFormat(whereClauses
											.get("dosTo")),
									Constants.MYSQL_DATE_FORMAT) + "'");
				}
				// else {
				// queryString.append(" AND cclw." + field);
				// queryString.append(" LIKE '%" + whereClauses.get(field)
				// + "%' ");
				// }
			}
		}

		return queryString;
	}
}
