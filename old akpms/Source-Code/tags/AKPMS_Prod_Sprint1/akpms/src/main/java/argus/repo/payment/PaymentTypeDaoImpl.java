/**
 *
 */
package argus.repo.payment;

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import argus.domain.PaymentType;
import argus.exception.ArgusException;
import argus.util.Constants;

/**
 * @author vishal.joshi
 *
 */

@Repository
@Transactional
public class PaymentTypeDaoImpl implements PaymentTypeDao {

	private static final Log LOGGER = LogFactory
			.getLog(PaymentTypeDaoImpl.class);
	private static final String CHANGESTATUS = "UPDATE PaymentType as i SET i.status = ? WHERE i.id= ?";
	private static final String DELETESTATUS = "UPDATE PaymentType as i SET i.deleted = 1 WHERE i.id IN :ids";
	private static final String PAYMENT_TYPE_STATS_NATIVE = "SELECT status, COUNT(*) FROM akpms.payment_type i WHERE i.deleted = 0 GROUP BY status";

	@Autowired
	private EntityManager em;

	public PaymentType findById(Long id) {
		return em.find(PaymentType.class, id);
	}

	public PaymentType findByName(String name) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<PaymentType> criteria = builder
				.createQuery(PaymentType.class);
		Root<PaymentType> paymentType = criteria.from(PaymentType.class);
		criteria.select(paymentType).where(
				builder.equal(paymentType.get("name"), name));
		return em.createQuery(criteria).getSingleResult();
	}

	private StringBuilder getQueryFindAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses) {

		StringBuilder queryString = new StringBuilder();

		if (whereClauses != null && whereClauses.size() > 0) {
			Set<String> key = whereClauses.keySet();
			for (String field : key) {
				if (field.equalsIgnoreCase(Constants.STATUS)) {
					queryString.append(" AND i.status = "
							+ whereClauses.get(Constants.STATUS));
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

	public List<PaymentType> findAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses) {
		List<PaymentType> ret = null;

		StringBuilder queryString = new StringBuilder();

		queryString.append("SELECT i FROM PaymentType AS i ");
		queryString.append(" WHERE i.deleted = " + Constants.NON_DELETED);

		queryString.append(getQueryFindAll(orderClauses, whereClauses)
				.toString());

		try {
			TypedQuery<PaymentType> query = em.createQuery(
					queryString.toString(), PaymentType.class);
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

	public void addPaymentType(PaymentType paymentType) {
		em.persist(paymentType);
		return;
	}

	public int totalRecord(Map<String, String> whereClauses) {
		int ret = 0;
		StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT COUNT(*) FROM PaymentType AS i ");
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
	public int deletePaymentType(List<Long> ids) {
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
	public void updatePaymentType(PaymentType paymentType) {
		em.merge(paymentType);
		return;
	}

	@Override
	public PaymentType findByNameAndType(String name, String type) {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<PaymentType> criteria = builder
				.createQuery(PaymentType.class);
		Root<PaymentType> paymentType = criteria.from(PaymentType.class);

		criteria.select(paymentType).where(
				builder.equal(paymentType.get("name"), name),
				builder.equal(paymentType.get("type"), type));
		return em.createQuery(criteria).getSingleResult();

	}

	@Override
	public List<Object[]> getPaymentTypeStats() throws ArgusException {
		List<Object[]> listObject = new ArrayList<Object[]>();
		Query query = em.createNativeQuery(PAYMENT_TYPE_STATS_NATIVE);
		listObject = query.getResultList();
		return listObject;
	}

}
