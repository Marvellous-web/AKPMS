package argus.repo.productivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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

import argus.domain.ArProductivity;
import argus.exception.ArgusException;
import argus.repo.productivity.helper.ArProductivityDaoHelper;
import argus.util.Constants;

@Repository
@Transactional
public class ArProductivityDaoImpl implements ArProductivityDao {
	private static final Log LOGGER = LogFactory
			.getLog(ArProductivityDaoImpl.class);

	@Autowired
	private EntityManager em;

	/**
	 * function is used to get ArProductivity by id
	 *
	 */
	@Override
	public ArProductivity findById(Long id) throws ArgusException {
		ArProductivity arProductivity = em.find(ArProductivity.class, id);

		return arProductivity;
	}

	/**
	 * function is used to find by name
	 */
	@Override
	public ArProductivity findByName(String name) throws ArgusException {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<ArProductivity> criteria = builder
				.createQuery(ArProductivity.class);

		Root<ArProductivity> arProductivity = criteria
				.from(ArProductivity.class);

		criteria.select(arProductivity).where(
				builder.equal(arProductivity.get(Constants.BY_NAME), name));

		return em.createQuery(criteria).getSingleResult();
	}

	@Override
	public List<ArProductivity> findAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses, boolean dependancies)
			throws ArgusException {
		List<ArProductivity> arProductivityList = null;
		List<ArProductivity> arProdList = null;

		StringBuilder queryString = new StringBuilder();

		queryString.append("SELECT d FROM ArProductivity AS d ");
		queryString.append(ArProductivityDaoHelper.getQueryFindAll(
				orderClauses, whereClauses).toString());

		TypedQuery<ArProductivity> query = em.createQuery(
				queryString.toString(), ArProductivity.class);
		if (orderClauses != null && orderClauses.get(Constants.OFFSET) != null
				&& orderClauses.get(Constants.LIMIT) != null) {
			query.setFirstResult(Integer.parseInt(orderClauses
					.get(Constants.OFFSET)));
			query.setMaxResults(Integer.parseInt(orderClauses
					.get(Constants.LIMIT)));
		}

		LOGGER.info("SQL:: " + queryString.toString());

		arProductivityList = query.getResultList();

		if (dependancies && arProductivityList != null
				&& arProductivityList.size() > Constants.ZERO) {
			Iterator<ArProductivity> arProductivityIterator = arProductivityList
					.iterator();
			arProdList = new ArrayList<ArProductivity>();
			while (arProductivityIterator.hasNext()) {
				ArProductivity arProductivity = arProductivityIterator.next();
				arProductivity.setWorkFlowName(ArProductivityDaoHelper
						.getWorkFlowName(arProductivity.getWorkFlow()));
				arProductivity.setSourceName(ArProductivityDaoHelper
						.getSourceName(arProductivity.getSource()));
				Hibernate.initialize(arProductivity.getInsurance());
				Hibernate.initialize(arProductivity.getDoctor());
				arProdList.add(arProductivity);

			}
		}

		return arProdList;
	}

	/**
	 * to add new department
	 */
	@Override
	public void addProductivity(ArProductivity arProductivity)
			throws ArgusException {
		em.persist(arProductivity);
		return;
	}

	/**
	 * to update existing department
	 */
	@Override
	public void updateProductivity(ArProductivity arProductivity)
			throws ArgusException {
		em.merge(arProductivity);
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
		queryString.append("SELECT COUNT(*) FROM ArProductivity AS d ");
		if (whereClauses != null && whereClauses.size() > Constants.ZERO) {
			queryString.append(" WHERE d.deleted = 0  ");
			Set<String> key = whereClauses.keySet();
			for (String field : key) {
				if (field.equalsIgnoreCase(Constants.KEYWORD)) {
					LOGGER.info("found search text value  = "
							+ whereClauses.get(field));
					queryString.append(" AND d.patientName LIKE '%"
							+ whereClauses.get(Constants.KEYWORD) + "%' ");
				} else if (field.equalsIgnoreCase(Constants.WORKFLOW_ID)) {
					queryString.append(" AND d.workFlow = "
							+ Constants.QUERY_TO_TL_WORKFLOW  );
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

	@SuppressWarnings("unchecked")
	public List<Object[]> countByWorkFlow() throws ArgusException {
		StringBuilder queryString = new StringBuilder();
		queryString
				.append("select arp.workFlow,count(1) from ar_productivity arp group by arp.workFlow ");
		List<Object[]> listObject = new ArrayList<Object[]>();
		Query query = em.createNativeQuery(queryString.toString());
		listObject = query.getResultList();
		return listObject;

	}
}
