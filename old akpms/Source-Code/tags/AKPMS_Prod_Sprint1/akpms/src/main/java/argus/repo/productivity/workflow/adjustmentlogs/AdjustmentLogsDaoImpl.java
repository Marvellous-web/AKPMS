package argus.repo.productivity.workflow.adjustmentlogs;

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import argus.domain.AdjustmentLogWorkFlow;
import argus.exception.ArgusException;
import argus.repo.productivity.helper.ArProductivityDaoHelper;
import argus.util.Constants;

@Repository
@Transactional
public class AdjustmentLogsDaoImpl implements AdjustmentLogsDao {
	private static final Log LOGGER = LogFactory
			.getLog(AdjustmentLogsDaoImpl.class);

	@Autowired
	private EntityManager em;

	/**
	 * function is used to get ArProductivity by id
	 *
	 */
	@Override
	public AdjustmentLogWorkFlow findById(Long id) throws ArgusException {
		LOGGER.info("in [findById] method : id = " + id);
		AdjustmentLogWorkFlow adjLogWorkFlow = em.find(
				AdjustmentLogWorkFlow.class, id);

		return adjLogWorkFlow;
	}

	/**
	 * function is used to find by name
	 */
	@Override
	public AdjustmentLogWorkFlow findByName(String name) throws ArgusException {
		LOGGER.info("in [findByName] method : name = " + name);
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<AdjustmentLogWorkFlow> criteria = builder
				.createQuery(AdjustmentLogWorkFlow.class);

		Root<AdjustmentLogWorkFlow> adjLogWorkFlow = criteria
				.from(AdjustmentLogWorkFlow.class);

		criteria.select(adjLogWorkFlow).where(
				builder.equal(adjLogWorkFlow.get(Constants.BY_NAME), name));

		return em.createQuery(criteria).getSingleResult();
	}

	@Override
	public List<AdjustmentLogWorkFlow> findAll(
			Map<String, String> orderClauses, Map<String, String> whereClauses,
			boolean dependancies) throws ArgusException {
		LOGGER.info("in [findAll(Map<String, String> orderClauses,"
				+ " Map<String, String> whereClauses, boolean dependancies)] method :");
		List<AdjustmentLogWorkFlow> adjLogWorkFlowsList = null;

		StringBuilder queryString = new StringBuilder();

		queryString.append("SELECT d FROM AdjustmentLogWorkFlow AS d ");

		queryString.append(ArProductivityDaoHelper.getQueryFindAll(
				orderClauses, whereClauses).toString());

		TypedQuery<AdjustmentLogWorkFlow> query = em.createQuery(
				queryString.toString(), AdjustmentLogWorkFlow.class);
		if (orderClauses != null && orderClauses.get(Constants.OFFSET) != null
				&& orderClauses.get(Constants.LIMIT) != null) {
			query.setFirstResult(Integer.parseInt(orderClauses
					.get(Constants.OFFSET)));
			query.setMaxResults(Integer.parseInt(orderClauses
					.get(Constants.LIMIT)));
		}

		LOGGER.info("SQL:: " + queryString.toString());

		adjLogWorkFlowsList = query.getResultList();

		if (dependancies && adjLogWorkFlowsList != null
				&& adjLogWorkFlowsList.size() > Constants.ZERO) {
			Iterator<AdjustmentLogWorkFlow> adjIterator = adjLogWorkFlowsList
					.iterator();

			while (adjIterator.hasNext()) {
				AdjustmentLogWorkFlow adjLogWorkFlow = adjIterator.next();
				Hibernate.initialize(adjLogWorkFlow.getArProductivity());

			}
		}

		return adjLogWorkFlowsList;
	}

	/**
	 * to add new department
	 */
	@Override
	public void addAdjLogWorkFlow(AdjustmentLogWorkFlow adjLogWorkFlow)
			throws ArgusException {
		LOGGER.info("in [addAdjLogWorkFlow] method :");
		em.persist(adjLogWorkFlow);
		return;
	}

	/**
	 * to update existing department
	 */
	@Override
	public void updateAdjLogWorkFlow(AdjustmentLogWorkFlow adjWorkFlow)
			throws ArgusException {
		LOGGER.info("in [updateAdjLogWorkFlow] method :");
		em.merge(adjWorkFlow);
		return;
	}

	/**
	 * to get total records
	 */
	@Override
	public int totalRecord(Map<String, String> whereClauses)
			throws ArgusException {
		LOGGER.info("in [totalRecord] method :");
		int totalRecords = 0;
		StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT COUNT(*) FROM AdjustmentLogWorkFlow AS d ");
		if (whereClauses != null && whereClauses.size() > Constants.ZERO) {
			queryString.append(" WHERE d.deleted = 0 AND ");
			Set<String> key = whereClauses.keySet();
			for (String field : key) {
				if (field.equalsIgnoreCase(Constants.KEYWORD)) {
					LOGGER.info("found search text value  = "
							+ whereClauses.get(field));
					queryString.append(" d.arProductivity.patientName LIKE '%"
							+ whereClauses.get(Constants.KEYWORD) + "%' ");
				} else {
					queryString.append(" AND d." + field);
					queryString.append(" LIKE '%" + whereClauses.get(field)
							+ "%' ");
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
	public AdjustmentLogWorkFlow getAdjLogByArProductivityId(int arProdId)
			throws ArgusException, NoResultException {
		LOGGER.info("int [getAdjLogByArProductivityId] : arProdId = "
				+ arProdId);
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<AdjustmentLogWorkFlow> criteria = builder
				.createQuery(AdjustmentLogWorkFlow.class);
		Root<AdjustmentLogWorkFlow> adjLogWorkFlow = criteria
				.from(AdjustmentLogWorkFlow.class);
		criteria.select(adjLogWorkFlow).where(
				builder.equal(adjLogWorkFlow.get("arProductivity").get("id"),
						arProdId));
		return em.createQuery(criteria).getSingleResult();

	}

}
