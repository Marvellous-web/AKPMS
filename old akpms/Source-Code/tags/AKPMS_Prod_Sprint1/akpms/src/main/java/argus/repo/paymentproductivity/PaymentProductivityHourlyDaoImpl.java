package argus.repo.paymentproductivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import argus.domain.paymentproductivity.PaymentProductivityHourly;
import argus.exception.ArgusException;
import argus.util.Constants;

@Service
@Transactional
public class PaymentProductivityHourlyDaoImpl implements
		PaymentProductivityHourlyDao {

	private static final Log LOGGER = LogFactory
			.getLog(PaymentProductivityHourlyDao.class);

	@Autowired
	private EntityManager em;

	@Override
	public PaymentProductivityHourly findById(Long id) throws ArgusException {
		PaymentProductivityHourly paymentProductivityHourly = em.find(
				PaymentProductivityHourly.class, id);

		return paymentProductivityHourly;
	}

	@Override
	/**
	 * This method will add PaymentProductivityHourly to database
	 */
	public void addPaymentProductivityHourly(
			PaymentProductivityHourly paymentProductivityHourly)
			throws ArgusException {
		em.persist(paymentProductivityHourly);
		LOGGER.info("PaymentProductivityHourly Added successfully");

	}

	/**
	 * This method will update PaymentProductivityHourly
	 */
	public void updatePaymentProductivityHourly(
			PaymentProductivityHourly paymentProductivityHourly)
			throws ArgusException {
		em.merge(paymentProductivityHourly);
		LOGGER.info("PaymentProductivityHourly Updated successfully");

	}

	@Override
	public int totalRecord(Map<String, String> whereClauses) {
		int ret = 0;

		StringBuilder queryString = new StringBuilder();
		queryString
				.append("SELECT COUNT(*) FROM PaymentProductivityHourly AS pmtprohour ");
		if (whereClauses != null && whereClauses.size() > 0) {
			queryString.append(getWhereClause(whereClauses));
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

	private StringBuffer getWhereClause(Map<String, String> whereClauses) {
		LOGGER.info("where clause size :" + whereClauses.size());
		StringBuffer queryString = new StringBuffer();

		if (whereClauses != null && whereClauses.size() > 0) {
			Set<String> key = whereClauses.keySet();

			for (String field : key) {
				if (field.equalsIgnoreCase("taskName")) {
					queryString.append(" WHERE pmtprohour.taskName.id="
							+ whereClauses.get(field));
				}
			}
		}

		return queryString;
	}

	@Override
	public List<PaymentProductivityHourly> findAll(
			Map<String, String> orderClauses, Map<String, String> whereClauses)
			throws Exception {
		List<PaymentProductivityHourly> ret = null;

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<PaymentProductivityHourly> paymentProductivityHourlyQuery = builder
				.createQuery(PaymentProductivityHourly.class);
		Root<PaymentProductivityHourly> from = paymentProductivityHourlyQuery
				.from(PaymentProductivityHourly.class);
		CriteriaQuery<PaymentProductivityHourly> select = paymentProductivityHourlyQuery
				.select(from);

		List<Predicate> predicateList = new ArrayList<Predicate>();

		if (whereClauses != null && whereClauses.size() > 0) {
			Set<String> key = whereClauses.keySet();
			for (String field : key) {

				if (field.equalsIgnoreCase("taskName")) {
					predicateList.add(builder.and(builder.equal(
							from.get("taskName").get("id"),
							whereClauses.get(field))));
				}

			}
		}

		select.where(builder.and(predicateList
				.toArray(new Predicate[predicateList.size()])));
		select.distinct(true);

		if (orderClauses != null) {
			if (orderClauses.get(Constants.ORDER_BY) != null) {
				if (orderClauses.get(Constants.SORT_BY) != null
						&& orderClauses.get(Constants.SORT_BY)
								.equalsIgnoreCase("desc")) {
					select.orderBy(builder.desc(from.get(orderClauses
							.get(Constants.ORDER_BY))));
				} else {
					select.orderBy(builder.asc(from.get(orderClauses
							.get(Constants.ORDER_BY))));

				}
			}
		}
		TypedQuery<PaymentProductivityHourly> query = em.createQuery(select);
		if (orderClauses != null && orderClauses.get("offset") != null
				&& orderClauses.get("limit") != null) {
			query.setFirstResult(Integer.parseInt(orderClauses.get("offset")));
			query.setMaxResults(Integer.parseInt(orderClauses.get("limit")));
		}

		LOGGER.info("SQL:: " + query.toString());
		ret = query.getResultList();

		return ret;

	}

}
