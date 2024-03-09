/**
 *
 */
package argus.repo.revenueType;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import argus.domain.RevenueType;
import argus.util.Constants;

/**
 * @author bhupender.s
 *
 */

@Repository
@Transactional
public class RevenueTypeDaoImpl implements RevenueTypeDao {

	private static final Log LOGGER = LogFactory.getLog(RevenueTypeDaoImpl.class);

	@Autowired
	private EntityManager em;

	public RevenueType findById(Long id) {
		return em.find(RevenueType.class, id);
	}

	public RevenueType findByName(String name) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<RevenueType> criteria = builder
				.createQuery(RevenueType.class);
		Root<RevenueType> revenueType = criteria.from(RevenueType.class);
		criteria.select(revenueType).where(
				builder.equal(revenueType.get("name"), name));
		
		return em.createQuery(criteria).getSingleResult();
	}



	private StringBuilder getQueryFindAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses){

		StringBuilder queryString = new StringBuilder();

		if (whereClauses != null && whereClauses.size() > 0) {
			Set<String> key = whereClauses.keySet();
			for (String field : key) {
				LOGGER.info("searchText : "+field);
				queryString.append(" AND i.name");
				queryString
						.append(" LIKE '%" + whereClauses.get(field) + "%' ");
			}
		}

		if (orderClauses != null) {
			if (orderClauses.get("orderBy") != null) {
				queryString
						.append(" ORDER BY i." + orderClauses.get("orderBy"));
			} else{
				queryString.append(" ORDER BY i.name");
			}
			if (orderClauses.get("sortBy") != null) {
				queryString.append(" " + orderClauses.get("sortBy"));
			} else{
				queryString.append(" ASC");
			}
		}

		return queryString;
	}

	public List<RevenueType> findAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses) {
		List<RevenueType> ret = null;

		StringBuilder queryString = new StringBuilder();

		queryString.append("SELECT i FROM RevenueType AS i ");
		queryString.append(" WHERE i.deleted =  "+Constants.NON_DELETED);

		queryString.append(getQueryFindAll(orderClauses, whereClauses).toString());

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
	
	public List<RevenueType> findAll(boolean activeOnly) {
		List<RevenueType> revenueTypeList = null;

		StringBuilder queryString = new StringBuilder();

		queryString.append("SELECT revType FROM RevenueType AS revType ");
		queryString.append(" WHERE revType.deleted =  "+Constants.NON_DELETED);

		if(activeOnly){
			queryString.append(" AND revType.status = "+Constants.ACTIVATE);
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
}
