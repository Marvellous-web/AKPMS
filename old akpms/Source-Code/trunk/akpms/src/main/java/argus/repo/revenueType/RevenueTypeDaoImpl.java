/**
 *
 */
package argus.repo.revenueType;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import argus.domain.RevenueType;
import argus.exception.ArgusException;
import argus.util.Constants;

/**
 * @author bhupender.s
 *
 */

@Repository
@Transactional
public class RevenueTypeDaoImpl implements RevenueTypeDao {

	private static final Logger LOGGER = Logger
			.getLogger(RevenueTypeDaoImpl.class);
	private static final String CHANGESTATUS = "UPDATE RevenueType as i SET i.status = ? WHERE i.id= ?";
	private static final String DELETESTATUS = "UPDATE RevenueType as i SET i.deleted = 1 WHERE i.id IN :ids";
	private static final String REVENUE_TYPE_STATS_NATIVE = "SELECT status, COUNT(*) FROM revenue_type i WHERE i.deleted = 0 GROUP BY status";

	@Autowired
	private EntityManager em;

	public RevenueType findById(Long id) throws ArgusException {
		return em.find(RevenueType.class, id);
	}

	public RevenueType findByName(String name) throws ArgusException {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<RevenueType> criteria = builder
				.createQuery(RevenueType.class);
		Root<RevenueType> revenueType = criteria.from(RevenueType.class);
		criteria.select(revenueType).where(
				builder.equal(revenueType.get("name"), name));

		return em.createQuery(criteria).getSingleResult();
	}

	private StringBuilder getQueryFindAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses) throws ArgusException {

		StringBuilder queryString = new StringBuilder();

		if (whereClauses != null && whereClauses.size() > 0) {
			Set<String> key = whereClauses.keySet();
			for (String field : key) {
				LOGGER.info("searchText : " + field);

				queryString.append(" AND (i.name");
				queryString
						.append(" LIKE '%" + whereClauses.get(field) + "%' ");
				queryString.append(" OR i.code");
				queryString.append(" LIKE '%" + whereClauses.get(field)
						+ "%' ) ");
			}
		}

		if (orderClauses != null) {
			if (orderClauses.get("orderBy") != null) {
				queryString
						.append(" ORDER BY i." + orderClauses.get("orderBy"));
			} else {
				queryString.append(" ORDER BY i.name");
			}
			if (orderClauses.get("sortBy") != null) {
				queryString.append(" " + orderClauses.get("sortBy"));
			} else {
				queryString.append(" ASC");
			}
		}

		return queryString;
	}

	public List<RevenueType> findAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses) throws ArgusException {
		List<RevenueType> ret = null;

		StringBuilder queryString = new StringBuilder();

		queryString.append("SELECT i FROM RevenueType AS i ");
		queryString.append(" WHERE i.deleted =  " + Constants.NON_DELETED);

		queryString.append(getQueryFindAll(orderClauses, whereClauses)
				.toString());

		try {
			TypedQuery<RevenueType> query = em.createQuery(
					queryString.toString(), RevenueType.class);
			if (orderClauses != null) {
				if (orderClauses.get("offset") != null
						&& orderClauses.get("limit") != null) {
					query.setFirstResult(Integer.parseInt(orderClauses
							.get("offset")));
					query.setMaxResults(Integer.parseInt(orderClauses
							.get("limit")));
				}
			}

			LOGGER.info("SQL:: " + queryString.toString());

			ret = query.getResultList();

		} catch (Exception e) {
			LOGGER.error("Exception = ", e);
		}

		return ret;
	}

	public List<RevenueType> findAll(boolean activeOnly) throws ArgusException {
		List<RevenueType> revenueTypeList = null;

		StringBuilder queryString = new StringBuilder();

		queryString.append("SELECT revType FROM RevenueType AS revType ");
		queryString
				.append(" WHERE revType.deleted =  " + Constants.NON_DELETED);

		if (activeOnly) {
			queryString.append(" AND revType.status = " + Constants.ACTIVATE);
		}

		try {
			TypedQuery<RevenueType> query = em.createQuery(
					queryString.toString(), RevenueType.class);

			LOGGER.info("SQL:: " + queryString.toString());

			revenueTypeList = query.getResultList();

		} catch (Exception e) {
			LOGGER.error("Exception = ", e);
		}

		return revenueTypeList;
	}

	public void addRevenueType(RevenueType revenueType) throws ArgusException {
		em.persist(revenueType);
		return;
	}

	public int totalRecord(Map<String, String> whereClauses)
			throws ArgusException {
		int ret = 0;
		StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT COUNT(*) FROM RevenueType AS i ");
		queryString.append(" WHERE i.deleted = 0 ");

		if (whereClauses != null && whereClauses.size() > 0) {
			Set<String> key = whereClauses.keySet();
			for (String field : key) {

				if (field.equalsIgnoreCase(Constants.STATUS)) {
					LOGGER.info("found search text value  = "
							+ whereClauses.get(field));
					queryString.append(" AND i.status LIKE '%"
							+ whereClauses.get(Constants.STATUS) + "%' ");
				} else {
					queryString.append(" AND (i.name");
					queryString.append(" LIKE '%" + whereClauses.get(field)
							+ "%' ");
					queryString.append(" OR i.code");
					queryString.append(" LIKE '%" + whereClauses.get(field)
							+ "%' ) ");
				}
			}
		}

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

	@Override
	public int deleteRevenueType(List<Long> ids) throws ArgusException {
		Query query = em.createQuery(DELETESTATUS);

		query.setParameter("ids", ids);

		LOGGER.info("SQL:" + query.toString());

		// return updatedRowCount
		return query.executeUpdate();
	}

	@Override
	public int changeStatus(long id, boolean status) throws ArgusException {
		Query query = em.createQuery(CHANGESTATUS);
		query.setParameter(1, status);
		query.setParameter(2, id);

		// return updatedRowCount
		return query.executeUpdate();
	}

	@Override
	public void updateRevenueType(RevenueType revenueType)
			throws ArgusException {
		em.merge(revenueType);
		return;
	}

	@Override
	public List<Object[]> getRevenueTypeStats() throws ArgusException {
		List<Object[]> listObject = new ArrayList<Object[]>();
		Query query = em.createNativeQuery(REVENUE_TYPE_STATS_NATIVE);
		listObject = query.getResultList();
		return listObject;
	}

}
