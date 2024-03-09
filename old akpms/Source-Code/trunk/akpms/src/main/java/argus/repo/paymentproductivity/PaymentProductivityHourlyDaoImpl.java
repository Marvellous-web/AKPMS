package argus.repo.paymentproductivity;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import argus.domain.paymentproductivity.PaymentProductivityHourly;
import argus.exception.ArgusException;
import argus.util.AkpmsUtil;
import argus.util.Constants;

@Service
@Transactional
public class PaymentProductivityHourlyDaoImpl implements
		PaymentProductivityHourlyDao {

	private static final Logger LOGGER = Logger
			.getLogger(PaymentProductivityHourlyDao.class);

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
				.append("SELECT COUNT(*) FROM PaymentProductivityHourly AS pmtprohour WHERE 1=1 ");
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
				if (field.equalsIgnoreCase(Constants.TASK_NAME)) {
					queryString.append(" AND pmtprohour.hourlyTask.id="
							+ whereClauses.get(field));
				}
				// created by
				if (field.equalsIgnoreCase(Constants.CREATED_BY)) {
					LOGGER.info("found CREATED_BY = "
							+ whereClauses.get(Constants.CREATED_BY));
					queryString.append(" AND pmtprohour.createdBy.id = "
							+ whereClauses.get(Constants.CREATED_BY));
				}

				// date created from
				if (field.equalsIgnoreCase(Constants.DATE_CREATED_FROM)) {
					LOGGER.info("found DATE_CREATED_FROM = "
							+ whereClauses.get(Constants.DATE_CREATED_FROM));
					queryString.append(" AND pmtprohour.createdOn >= '"
							+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
									.akpmsNewDateFormat(whereClauses
											.get(Constants.DATE_CREATED_FROM)),
									Constants.MYSQL_DATE_FORMAT) + "'");
				}

				// date created to
				if (field.equalsIgnoreCase(Constants.DATE_CREATED_TO)) {
					LOGGER.info("found DATE_CREATED_TO = "
							+ whereClauses.get(Constants.DATE_CREATED_TO));
					queryString.append(" AND pmtprohour.createdOn <= '"
							+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
									.getFormattedDate(whereClauses
											.get(Constants.DATE_CREATED_TO)),
									Constants.MYSQL_DATE_FORMAT_WITH_TIME)
							+ "'");
				}

				// task received date from
				if (field.equalsIgnoreCase(Constants.TASK_RECEIVED_DATE_FROM)) {
					LOGGER.info("found TASK_RECEIVED_DATE_FROM = "
							+ whereClauses
									.get(Constants.TASK_RECEIVED_DATE_FROM));
					queryString
							.append(" AND pmtprohour.dateReceived >= '"
									+ AkpmsUtil.akpmsDateFormat(
											AkpmsUtil
													.akpmsNewDateFormat(whereClauses
															.get(Constants.TASK_RECEIVED_DATE_FROM)),
											Constants.MYSQL_DATE_FORMAT) + "'");
				}

				// date created to
				if (field.equalsIgnoreCase(Constants.TASK_RECEIVED_DATE_TO)) {
					LOGGER.info("found TASK_RECEIVED_DATE_TO = "
							+ whereClauses.get(Constants.TASK_RECEIVED_DATE_TO));
					queryString
							.append(" AND pmtprohour.dateReceived <= '"
									+ AkpmsUtil.akpmsDateFormat(
											AkpmsUtil
													.getFormattedDate(whereClauses
															.get(Constants.TASK_RECEIVED_DATE_TO)),
											Constants.MYSQL_DATE_FORMAT_WITH_TIME)
									+ "'");
				}

				// task completed date from
				if (field.equalsIgnoreCase(Constants.TASK_COMPLETED_DATE_FROM)) {
					LOGGER.info("found TASK_COMPLETED_DATE_FROM = "
							+ whereClauses
									.get(Constants.TASK_COMPLETED_DATE_FROM));
					queryString
							.append(" AND pmtprohour.taskCompleted >= '"
									+ AkpmsUtil.akpmsDateFormat(
											AkpmsUtil
													.akpmsNewDateFormat(whereClauses
															.get(Constants.TASK_COMPLETED_DATE_FROM)),
											Constants.MYSQL_DATE_FORMAT) + "'");
				}

				// date created to
				if (field.equalsIgnoreCase(Constants.TASK_COMPLETED_DATE_TO)) {
					LOGGER.info("found TASK_COMPLETED_DATE_TO = "
							+ whereClauses
									.get(Constants.TASK_COMPLETED_DATE_TO));
					queryString
							.append(" AND pmtprohour.taskCompleted <= '"
									+ AkpmsUtil.akpmsDateFormat(
											AkpmsUtil
													.getFormattedDate(whereClauses
															.get(Constants.TASK_COMPLETED_DATE_TO)),
											Constants.MYSQL_DATE_FORMAT_WITH_TIME)
									+ "'");
				}
			}
		}

		return queryString;
	}

	@Override
	public List<PaymentProductivityHourly> findAll(
			Map<String, String> orderClauses, Map<String, String> whereClauses)
			throws Exception {
		/*
		 * List<PaymentProductivityHourly> ret = null;
		 * 
		 * CriteriaBuilder builder = em.getCriteriaBuilder();
		 * CriteriaQuery<PaymentProductivityHourly>
		 * paymentProductivityHourlyQuery = builder
		 * .createQuery(PaymentProductivityHourly.class);
		 * Root<PaymentProductivityHourly> from = paymentProductivityHourlyQuery
		 * .from(PaymentProductivityHourly.class);
		 * CriteriaQuery<PaymentProductivityHourly> select =
		 * paymentProductivityHourlyQuery .select(from);
		 * 
		 * List<Predicate> predicateList = new ArrayList<Predicate>();
		 * 
		 * if (whereClauses != null && whereClauses.size() > 0) { Set<String>
		 * key = whereClauses.keySet(); for (String field : key) {
		 * 
		 * if (field.equalsIgnoreCase("taskName")) {
		 * predicateList.add(builder.and(builder.equal(
		 * from.get("hourlyTask").get("id"), whereClauses.get(field)))); }
		 * 
		 * } }
		 * 
		 * select.where(builder.and(predicateList .toArray(new
		 * Predicate[predicateList.size()]))); select.distinct(true);
		 * 
		 * if (orderClauses != null) { if (orderClauses.get(Constants.ORDER_BY)
		 * != null) { if (orderClauses.get(Constants.SORT_BY) != null &&
		 * orderClauses.get(Constants.SORT_BY) .equalsIgnoreCase("desc")) {
		 * select.orderBy(builder.desc(from.get(orderClauses
		 * .get(Constants.ORDER_BY)))); } else {
		 * select.orderBy(builder.asc(from.get(orderClauses
		 * .get(Constants.ORDER_BY))));
		 * 
		 * } } } TypedQuery<PaymentProductivityHourly> query =
		 * em.createQuery(select); if (orderClauses != null &&
		 * orderClauses.get("offset") != null && orderClauses.get("limit") !=
		 * null) {
		 * query.setFirstResult(Integer.parseInt(orderClauses.get("offset")));
		 * query.setMaxResults(Integer.parseInt(orderClauses.get("limit"))); }
		 * 
		 * LOGGER.info("SQL:: " + query.toString()); ret =
		 * query.getResultList();
		 */

		List<PaymentProductivityHourly> ret = null;

		StringBuilder queryString = new StringBuilder();
		queryString
				.append("SELECT pmtprohour FROM PaymentProductivityHourly AS pmtprohour WHERE 1=1 ");

		queryString.append(getWhereClause(whereClauses));
		queryString.append(getOrderClause(orderClauses));

		try {
			TypedQuery<PaymentProductivityHourly> query = em.createQuery(
					queryString.toString(), PaymentProductivityHourly.class);
			if (orderClauses != null
					&& orderClauses.get(Constants.OFFSET) != null
					&& orderClauses.get(Constants.LIMIT) != null) {
				query.setFirstResult(Integer.parseInt(orderClauses
						.get(Constants.OFFSET)));
				query.setMaxResults(Integer.parseInt(orderClauses
						.get(Constants.LIMIT)));
			}

			ret = query.getResultList();

		} catch (Exception e) {
			LOGGER.error("Exception = ", e);
		}

		return ret;
	}

	private StringBuffer getOrderClause(Map<String, String> orderClauses) {

		StringBuffer queryString = new StringBuffer();
		if (orderClauses != null) {
			if (orderClauses.get("orderBy") != null) {
				queryString.append(" ORDER BY pmtprohour."
						+ orderClauses.get("orderBy"));
			} else {
				queryString.append(" ORDER BY pmtprohour.id");
			}
			if (orderClauses.get("sortBy") != null) {
				queryString.append(" " + orderClauses.get("sortBy"));
			} else {
				queryString.append(" ASC");
			}
		} else {
			queryString.append(" ORDER BY pmtprohour.id DESC");
		}
		return queryString;
	}

}
