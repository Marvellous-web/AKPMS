package argus.repo.insurance;

import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import argus.domain.Insurance;
import argus.exception.ArgusException;
import argus.util.AkpmsUtil;
import argus.util.Constants;

@Repository
@Transactional
/**
 *
 * @author vishal.joshi
 *
 */
public class InsuranceDaoImpl implements InsuranceDao {
	private static final Log LOGGER = LogFactory.getLog(InsuranceDaoImpl.class);
	private static final String CHANGE_STATUS = "UPDATE Insurance as i SET i.status = :status, i.modifiedBy = :modifiedBy, i.modifiedOn = :modifiedOn WHERE i.id= :id";
	private static final String DELETE_STATUS = "UPDATE Insurance as i SET i.deleted = "
			+ Constants.IS_DELETED
			+ ", i.modifiedBy = :modifiedBy, i.modifiedOn = :modifiedOn WHERE i.id IN :ids";
	private static final String INSURANCE_STATS_NATIVE = "SELECT status, COUNT(*) FROM akpms.insurance i WHERE i.is_deleted = 0 GROUP BY status";

	@Autowired
	private EntityManager em;

	public Insurance findById(Long id) throws ArgusException {
		return em.find(Insurance.class, id);
	}

	public Insurance findByName(String name) {
		LOGGER.info("[findByName]: name = " + name);

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Insurance> criteria = builder
				.createQuery(Insurance.class);

		Root<Insurance> insurance = criteria.from(Insurance.class);
		criteria.select(insurance).where(
				builder.equal(insurance.get(Constants.BY_NAME), name));

		return em.createQuery(criteria).getSingleResult();
	}

	private StringBuilder getQueryFindAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses) {
		StringBuilder queryString = new StringBuilder();
		if (whereClauses != null && whereClauses.size() > 0) {
			Set<String> key = whereClauses.keySet();
			for (String field : key) {
				LOGGER.info("Search IN:" + field + "="
						+ whereClauses.get(field));
				if (field.equalsIgnoreCase(Constants.STATUS)) {
					queryString.append(" AND i.status = "
							+ whereClauses.get(field));
				} else {
					LOGGER.info("keyword : " + field);
					queryString.append(" AND i.name");
					queryString.append(" LIKE '%" + whereClauses.get(field)
							+ "%' ");
				}
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

	public List<Insurance> findAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses) {
		LOGGER.info("[findAll]");
		List<Insurance> ret = null;

		StringBuilder queryString = new StringBuilder();

		queryString.append("SELECT i FROM Insurance AS i ");
		queryString.append(" WHERE i.deleted =  " + Constants.NON_DELETED);

		queryString.append(getQueryFindAll(orderClauses, whereClauses)
				.toString());

		try {
			TypedQuery<Insurance> query = em.createQuery(
					queryString.toString(), Insurance.class);
			if (orderClauses != null && orderClauses.get("offset") != null
					&& orderClauses.get("limit") != null) {
				query.setFirstResult(Integer.parseInt(orderClauses
						.get("offset")));
				query.setMaxResults(Integer.parseInt(orderClauses.get("limit")));
			}

			LOGGER.info("SQL:: " + queryString.toString());

			ret = query.getResultList();

		} catch (Exception e) {
			LOGGER.error("Exception = ", e);
		}

		return ret;
	}

	public void addInsurance(Insurance insurance) {
		LOGGER.info("[addInsurance]");
		em.persist(insurance);
		return;
	}

	public int totalRecord(Map<String, String> whereClauses) {
		LOGGER.info("[totalRecord]");
		int ret = 0;
		StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT COUNT(*) FROM Insurance AS i ");
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
					queryString.append(" AND i.name");
					queryString.append(" LIKE '%" + whereClauses.get(field)
							+ "%' ");
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
	public int deleteInsurance(List<Long> ids) {
		LOGGER.info("[deleteInsurance]");
		Query query = em.createQuery(DELETE_STATUS);

		query.setParameter("modifiedBy", AkpmsUtil.getLoggedInUser());
		query.setParameter("modifiedOn", new Date());
		query.setParameter("ids", ids);

		LOGGER.info("SQL:" + query.toString());

		return query.executeUpdate();
	}

	@Override
	public int changeStatus(long id, boolean status) throws ArgusException {
		LOGGER.info("[changeStatus] : status = " + status + ", id = " + id);
		Query query = em.createQuery(CHANGE_STATUS);

		query.setParameter("modifiedBy", AkpmsUtil.getLoggedInUser());
		query.setParameter("modifiedOn", new Date());
		query.setParameter("status", status);
		query.setParameter("id", id);

		return query.executeUpdate();
	}

	@Override
	public void updateInsurance(Insurance insurance) {
		LOGGER.info("[updateInsurance] :");
		em.merge(insurance);
		return;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getInsuranceStats() throws ArgusException {
		List<Object[]> listObject = new ArrayList<Object[]>();
		Query query = em.createNativeQuery(INSURANCE_STATS_NATIVE);
		listObject = query.getResultList();
		return listObject;
	}
}
