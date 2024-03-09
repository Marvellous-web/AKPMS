package argus.repo.productivity.workflow.refundRequest;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
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

import argus.domain.RefundRequestWorkFlow;
import argus.exception.ArgusException;
import argus.repo.productivity.helper.ArProductivityDaoHelper;
import argus.util.Constants;

@Repository
@Transactional
public class RefundRequestDaoImpl implements RefundRequestDao {
	private static final Log LOGGER = LogFactory
			.getLog(RefundRequestDaoImpl.class);

	@Autowired
	private EntityManager em;

	@Override
	public RefundRequestWorkFlow findById(Long id) throws ArgusException {
		LOGGER.info("in [findById] method : id = " + id);
		RefundRequestWorkFlow refundRequestWorkFlow = em.find(
				RefundRequestWorkFlow.class, id);
		return refundRequestWorkFlow;
	}

	@Override
	public RefundRequestWorkFlow findByName(String name) throws ArgusException {
		LOGGER.info("in [findByName] method : name = " + name);
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<RefundRequestWorkFlow> criteria = builder
				.createQuery(RefundRequestWorkFlow.class);

		Root<RefundRequestWorkFlow> refundRequestWorkFlow = criteria
				.from(RefundRequestWorkFlow.class);

		criteria.select(refundRequestWorkFlow).where(
				builder.equal(refundRequestWorkFlow.get(Constants.BY_NAME),
						name));

		return em.createQuery(criteria).getSingleResult();
	}

	@Override
	public List<RefundRequestWorkFlow> findAll(
			Map<String, String> orderClauses, Map<String, String> whereClauses,
			boolean dependancies) throws ArgusException {
		LOGGER.info("in [findAll(Map<String, String> orderClauses,"
				+ " Map<String, String> whereClauses, boolean dependancies)] method :");
		List<RefundRequestWorkFlow> refundRequestWorkFlowList = null;

		StringBuilder queryString = new StringBuilder();

		queryString.append("SELECT d FROM RefundRequestWorkFlow AS d ");

		queryString.append(ArProductivityDaoHelper.getQueryFindAll(
				orderClauses, whereClauses).toString());

		TypedQuery<RefundRequestWorkFlow> query = em.createQuery(
				queryString.toString(), RefundRequestWorkFlow.class);
		if (orderClauses != null && orderClauses.get(Constants.OFFSET) != null
				&& orderClauses.get(Constants.LIMIT) != null) {
			query.setFirstResult(Integer.parseInt(orderClauses
					.get(Constants.OFFSET)));
			query.setMaxResults(Integer.parseInt(orderClauses
					.get(Constants.LIMIT)));
		}

		LOGGER.info("SQL:: " + queryString.toString());

		refundRequestWorkFlowList = query.getResultList();

		if (dependancies && refundRequestWorkFlowList != null
				&& refundRequestWorkFlowList.size() > Constants.ZERO) {
			Iterator<RefundRequestWorkFlow> refundRequestIterator = refundRequestWorkFlowList
					.iterator();

			while (refundRequestIterator.hasNext()) {
				RefundRequestWorkFlow refundRequestWorkFlow = refundRequestIterator
						.next();
				Hibernate.initialize(refundRequestWorkFlow.getArProductivity());

			}
		}

		return refundRequestWorkFlowList;
	}

	@Override
	public void addRefundRequestWorkFlow(
			RefundRequestWorkFlow refundRequestWorkFlow) throws ArgusException {
		LOGGER.info("in [addRefundRequestWorkFlow] method :");
		em.persist(refundRequestWorkFlow);

	}

	@Override
	public void updateRefundRequestWorkFlow(
			RefundRequestWorkFlow refundRequestWorkFlow) throws ArgusException {
		LOGGER.info("in [updateRefundRequestWorkFlow] method :");
		em.merge(refundRequestWorkFlow);

	}

	@Override
	public int totalRecord(Map<String, String> whereClauses)
			throws ArgusException {
		LOGGER.info("in [totalRecord] method :");
		int totalRecords = 0;
		StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT COUNT(*) FROM RefundRequestWorkFlow AS d ");
		if (whereClauses != null && whereClauses.size() > Constants.ZERO) {
			queryString.append(" WHERE d.deleted = 0 ");
			Set<String> key = whereClauses.keySet();
			for (String field : key) {
				if (field.equalsIgnoreCase(Constants.KEYWORD)) {
					LOGGER.info("found search text value  = "
							+ whereClauses.get(field));
					queryString.append(" d.patientName LIKE '%"
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
	public RefundRequestWorkFlow getRefundRequestByArProductivityId(int arProdId)
			throws ArgusException {
		LOGGER.info("int [getRefundRequestByArProductivityId] : arProdId = "
				+ arProdId);
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<RefundRequestWorkFlow> criteria = builder
				.createQuery(RefundRequestWorkFlow.class);
		Root<RefundRequestWorkFlow> refundRequestWorkFlow = criteria
				.from(RefundRequestWorkFlow.class);
		criteria.select(refundRequestWorkFlow).where(
				builder.equal(
						refundRequestWorkFlow.get("arProductivity").get("id"),
						arProdId));
		return em.createQuery(criteria).getSingleResult();
	}

}
