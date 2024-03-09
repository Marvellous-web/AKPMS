package argus.repo.paymentproductivity;

import java.util.ArrayList;
import java.util.Date;
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

import argus.domain.paymentproductivity.PaymentProdQueryToTL;
import argus.exception.ArgusException;
import argus.repo.productivity.helper.ArProductivityDaoHelper;
import argus.util.AkpmsUtil;
import argus.util.Constants;

@Repository
@Transactional
public class PaymentProdQueryToTLDaoImpl implements PaymentProdQueryToTLDao {
	private static final Log LOGGER = LogFactory
			.getLog(PaymentProdQueryToTLDaoImpl.class);

	private static final String DELETE_STATUS_SINGLE = "UPDATE PaymentProdQueryToTL as p SET p.deleted = "
			+ Constants.DELETED
			+ ", p.modifiedBy = :modifiedBy, p.modifiedOn = :modifiedOn WHERE p.id = :id";

	@Autowired
	private EntityManager em;

	/**
	 * function is used to get PaymentProductivity by id
	 *
	 */
	@Override
	public PaymentProdQueryToTL findById(Long id) throws ArgusException {
		PaymentProdQueryToTL paymentProdQueryToTL = em.find(PaymentProdQueryToTL.class, id);

		return paymentProdQueryToTL;
	}

	/**
	 * function is used to find by name
	 */
	@Override
	public PaymentProdQueryToTL findByName(String name) throws ArgusException {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<PaymentProdQueryToTL> criteria = builder
				.createQuery(PaymentProdQueryToTL.class);

		Root<PaymentProdQueryToTL> paymentProductivityQueryToTL = criteria
				.from(PaymentProdQueryToTL.class);

		criteria.select(paymentProductivityQueryToTL).where(
				builder.equal(paymentProductivityQueryToTL.get(Constants.BY_NAME), name));

		return em.createQuery(criteria).getSingleResult();
	}

	@Override
	public List<PaymentProdQueryToTL> findAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses, boolean dependancies)
			throws ArgusException {
		List<PaymentProdQueryToTL> paymentProductivityList = null;
		List<PaymentProdQueryToTL> paymentProdList = null;

		StringBuilder queryString = new StringBuilder();

		queryString.append("SELECT d FROM PaymentProdQueryToTL AS d ");
		queryString.append(ArProductivityDaoHelper.getQueryFindAll(
				orderClauses, whereClauses).toString());

		TypedQuery<PaymentProdQueryToTL> query = em.createQuery(
				queryString.toString(), PaymentProdQueryToTL.class);
		if (orderClauses != null && orderClauses.get(Constants.OFFSET) != null
				&& orderClauses.get(Constants.LIMIT) != null) {
			query.setFirstResult(Integer.parseInt(orderClauses
					.get(Constants.OFFSET)));
			query.setMaxResults(Integer.parseInt(orderClauses
					.get(Constants.LIMIT)));
		}

		LOGGER.info("SQL:: " + queryString.toString());

		paymentProductivityList = query.getResultList();

		if (dependancies && paymentProductivityList != null
				&& paymentProductivityList.size() > Constants.ZERO) {
			Iterator<PaymentProdQueryToTL> paymentProductivityIterator = paymentProductivityList
					.iterator();
			paymentProdList = new ArrayList<PaymentProdQueryToTL>();
			while (paymentProductivityIterator.hasNext()) {
				PaymentProdQueryToTL paymentProductivity = paymentProductivityIterator.next();
				Hibernate.initialize(paymentProductivity.getPaymentProductivity());
				Hibernate.initialize(paymentProductivity.getPaymentProductivity().getPaymentBatch());
				Hibernate.initialize(paymentProductivity.getPaymentProductivity().getPaymentBatch().getInsurance());
				Hibernate.initialize(paymentProductivity.getPaymentProductivity().getPaymentBatch().getDoctor());
				paymentProdList.add(paymentProductivity);

			}
		}

		return paymentProdList;
	}

	/**
	 * to add new department
	 */
	@Override
	public void addPaymentProdQueryToTL(PaymentProdQueryToTL paymentProdQueryToTL)
			throws ArgusException {
		em.persist(paymentProdQueryToTL);
		return;
	}

	/**
	 * to update existing department
	 */
	@Override
	public void updatePaymentProdQueryToTL(PaymentProdQueryToTL paymentProdQueryToTL)
			throws ArgusException {
		em.merge(paymentProdQueryToTL);
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
		queryString.append("SELECT COUNT(*) FROM PaymentProdQueryToTL AS d ");
		if (whereClauses != null && whereClauses.size() > Constants.ZERO) {
			queryString.append(" WHERE d.deleted = 0 AND ");
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
	public int deletePaymentProdQueryToTL(Long id) throws ArgusException {
		Query query = em.createQuery(DELETE_STATUS_SINGLE);

		query.setParameter("modifiedBy", AkpmsUtil.getLoggedInUser());
		query.setParameter("modifiedOn", new Date());
		query.setParameter("id", id);

		LOGGER.info("SQL:" + query.toString());
		return query.executeUpdate();
	}

	@Override
	public PaymentProdQueryToTL findQueryToTLByProdId(long prodId)
			throws ArgusException {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<PaymentProdQueryToTL> criteria = builder
				.createQuery(PaymentProdQueryToTL.class);
		Root<PaymentProdQueryToTL> paymentProdQueryToTLRoot = criteria
				.from(PaymentProdQueryToTL.class);

		criteria.select(paymentProdQueryToTLRoot).where(
				builder.equal(
						paymentProdQueryToTLRoot.get("paymentProductivity")
								.get(Constants.ID),
						prodId));
		return em.createQuery(criteria).getSingleResult();

	}
}
