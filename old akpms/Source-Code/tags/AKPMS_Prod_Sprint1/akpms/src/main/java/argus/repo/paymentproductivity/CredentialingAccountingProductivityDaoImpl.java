/**
 *
 */
package argus.repo.paymentproductivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import argus.domain.paymentproductivity.CredentialingAccountingProductivity;
import argus.exception.ArgusException;
import argus.util.AkpmsUtil;
import argus.util.Constants;

/**
 * @author rajiv.k
 *
 */
@Repository
@Transactional
public class CredentialingAccountingProductivityDaoImpl implements
		CredentialingAccountingProductivityDao {
	private static final Log LOGGER = LogFactory
			.getLog(CredentialingAccountingProductivityDaoImpl.class);

	private static final String DATE_RECIEVED = "dateRecd";
	private static final String TASK_COMPLETED = "taskCompleted";
	@Autowired
	private EntityManager em;

	/* (non-Javadoc)
	 * @see argus.repo.paymentproductivity.CredentialingAccountingProductivityDao#findById(java.lang.Long)
	 */
	@Override
	public CredentialingAccountingProductivity findById(Long id)
			throws ArgusException {
		CredentialingAccountingProductivity credentialingAccountingProductivity = em
				.find(CredentialingAccountingProductivity.class, id);

		return credentialingAccountingProductivity;
	}

	/* (non-Javadoc)
	 * @see argus.repo.paymentproductivity.CredentialingAccountingProductivityDao#findByName(java.lang.String)
	 */
	@Override
	public CredentialingAccountingProductivity findByName(String name)
			throws ArgusException {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<CredentialingAccountingProductivity> criteria = builder
				.createQuery(CredentialingAccountingProductivity.class);

		Root<CredentialingAccountingProductivity> credentialingAccountingProductivity = criteria
				.from(CredentialingAccountingProductivity.class);

		criteria.select(credentialingAccountingProductivity).where(
				builder.equal(credentialingAccountingProductivity
						.get(Constants.BY_NAME), name));

		return em.createQuery(criteria).getSingleResult();
	}

	/* (non-Javadoc)
	 * @see argus.repo.paymentproductivity.CredentialingAccountingProductivityDao#findAll(java.util.Map, java.util.Map, boolean)
	 */
	@Override
	public List<CredentialingAccountingProductivity> findAll(
			Map<String, String> orderClauses, Map<String, String> whereClauses,
			boolean dependancies) throws ArgusException {
		List<CredentialingAccountingProductivity> ret = null;
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<CredentialingAccountingProductivity> userCriteriaQuery = builder
					.createQuery(CredentialingAccountingProductivity.class);
			Root<CredentialingAccountingProductivity> from = userCriteriaQuery
					.from(CredentialingAccountingProductivity.class);
			CriteriaQuery<CredentialingAccountingProductivity> select = userCriteriaQuery
					.select(from);

			List<Predicate> predicateList = new ArrayList<Predicate>();

			if (dependancies && whereClauses != null && whereClauses.size() > 0) {
				Set<String> key = whereClauses.keySet();
				for (String field : key) {

					if (field.equalsIgnoreCase(Constants.POSTED_BY_ID)) {
						LOGGER.info("found Posted By value  = "
								+ whereClauses.get(field));

						predicateList
								.add(builder.and(builder.equal(
										from.get(Constants.CREATED_BY).get(
												Constants.ID),
										Long.parseLong(whereClauses
												.get(Constants.POSTED_BY_ID)))));
					}

					// set Date Recd from.
					if (field.equalsIgnoreCase(Constants.DATE_POSTED_FROM)) {
						LOGGER.info("found DATE_POSTED_FROM = "
								+ whereClauses.get(Constants.DATE_POSTED_FROM));

						Date fromDate = AkpmsUtil
								.akpmsNewDateFormat(whereClauses.get(field));
						Path<Date> dateReceivedPath = from.get(DATE_RECIEVED);
						predicateList.add(builder.greaterThanOrEqualTo(
								dateReceivedPath, fromDate));

					}
					// set Date Recd to
					if (field.equalsIgnoreCase(Constants.DATE_POSTED_TO)) {
						LOGGER.info("found DATE_POSTED_TO = "
								+ whereClauses.get(Constants.DATE_POSTED_TO));
						Date toDate = AkpmsUtil.getFormattedDate(whereClauses
								.get(field));

						Path<Date> dateReceivedPath = from.get(DATE_RECIEVED);
						predicateList.add(builder.lessThanOrEqualTo(
								dateReceivedPath, toDate));
					}
					// set Task completed from date
					if (field.equalsIgnoreCase(Constants.DATE_RECEIVED_FROM)) {
						LOGGER.info("found DATE_RECEIVED_FROM = "
								+ whereClauses
										.get(Constants.DATE_RECEIVED_FROM));

						Date fromDate = AkpmsUtil
								.akpmsNewDateFormat(whereClauses.get(field));
						Path<Date> dateReceivedPath = from.get(TASK_COMPLETED);
						predicateList.add(builder.greaterThanOrEqualTo(
								dateReceivedPath, fromDate));

					}
					// set Task completed to date
					if (field.equalsIgnoreCase(Constants.DATE_RECEIVED_TO)) {
						LOGGER.info("found DATE_RECEIVED_TO = "
								+ whereClauses.get(Constants.DATE_RECEIVED_TO));
						Date toDate = AkpmsUtil.getFormattedDate(whereClauses
								.get(field));

						Path<Date> dateReceivedPath = from.get(TASK_COMPLETED);
						predicateList.add(builder.lessThanOrEqualTo(
								dateReceivedPath, toDate));
					}

				}
			}
			select.where(builder.and(predicateList
					.toArray(new Predicate[predicateList.size()])));
			select.distinct(true);

			TypedQuery<CredentialingAccountingProductivity> query = em
					.createQuery(select);

			LOGGER.info("SQL:: " + query.toString());
			ret = query.getResultList();

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
			throw new ArgusException(e.getMessage(), e);
		}
		return ret;
	}

	/* (non-Javadoc)
	 * @see argus.repo.paymentproductivity.CredentialingAccountingProductivityDao#addCredentialingAccountingProductivity(argus.domain.paymentproductivity.CredentialingAccountingProductivity)
	 */
	@Override
	public void addCredentialingAccountingProductivity(
			CredentialingAccountingProductivity credentialingAccountingProductivity)
			throws ArgusException {
		em.persist(credentialingAccountingProductivity);

	}

	/* (non-Javadoc)
	 * @see argus.repo.paymentproductivity.CredentialingAccountingProductivityDao#updateCredentialingAccountingProductivity(argus.domain.paymentproductivity.CredentialingAccountingProductivity)
	 */
	@Override
	public void updateCredentialingAccountingProductivity(
			CredentialingAccountingProductivity credentialingAccountingProductivity)
			throws ArgusException {
		em.merge(credentialingAccountingProductivity);

	}

	/* (non-Javadoc)
	 * @see argus.repo.paymentproductivity.CredentialingAccountingProductivityDao#totalRecord(java.util.Map)
	 */
	@Override
	public int totalRecord(Map<String, String> whereClauses)
			throws ArgusException {
		int ret = 0;
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Long> cQuery = builder.createQuery(Long.class);
			Root<CredentialingAccountingProductivity> from = cQuery
					.from(CredentialingAccountingProductivity.class);
			CriteriaQuery<Long> select = cQuery.select(builder.count(from));

			List<Predicate> predicateList = new ArrayList<Predicate>();


			if (whereClauses != null && whereClauses.size() > 0) {
				Set<String> key = whereClauses.keySet();
				for (String field : key) {

					if (field.equalsIgnoreCase(Constants.POSTED_BY_ID)) {
						LOGGER.info("found Posted By value  = "
								+ whereClauses.get(field));

						predicateList
								.add(builder.and(builder.equal(
										from.get(Constants.CREATED_BY).get(
												Constants.ID),
										Long.parseLong(whereClauses
												.get(Constants.POSTED_BY_ID)))));
					}

					// set Date Recd from.
					if (field.equalsIgnoreCase(Constants.DATE_POSTED_FROM)) {
						LOGGER.info("found DATE_POSTED_FROM = "
								+ whereClauses.get(Constants.DATE_POSTED_FROM));

						Date fromDate = AkpmsUtil
								.akpmsNewDateFormat(whereClauses.get(field));
						Path<Date> dateReceivedPath = from.get(DATE_RECIEVED);
						predicateList.add(builder.greaterThanOrEqualTo(
								dateReceivedPath, fromDate));

					}
					// set Date Recd to
					if (field.equalsIgnoreCase(Constants.DATE_POSTED_TO)) {
						LOGGER.info("found DATE_POSTED_TO = "
								+ whereClauses.get(Constants.DATE_POSTED_TO));
						Date toDate = AkpmsUtil.getFormattedDate(whereClauses
								.get(field));

						Path<Date> dateReceivedPath = from.get(DATE_RECIEVED);
						predicateList.add(builder.lessThanOrEqualTo(
								dateReceivedPath, toDate));
					}
					// set Task completed from date
					if (field.equalsIgnoreCase(Constants.DATE_RECEIVED_FROM)) {
						LOGGER.info("found DATE_RECEIVED_FROM = "
								+ whereClauses
										.get(Constants.DATE_RECEIVED_FROM));

						Date fromDate = AkpmsUtil
								.akpmsNewDateFormat(whereClauses.get(field));
						Path<Date> dateReceivedPath = from.get(TASK_COMPLETED);
						predicateList.add(builder.greaterThanOrEqualTo(
								dateReceivedPath, fromDate));

					}
					// set Task completed to date
					if (field.equalsIgnoreCase(Constants.DATE_RECEIVED_TO)) {
						LOGGER.info("found DATE_RECEIVED_TO = "
								+ whereClauses.get(Constants.DATE_RECEIVED_TO));
						Date toDate = AkpmsUtil.getFormattedDate(whereClauses
								.get(field));

						Path<Date> dateReceivedPath = from.get(TASK_COMPLETED);
						predicateList.add(builder.lessThanOrEqualTo(
								dateReceivedPath, toDate));
					}

				}

			}
			select.where(builder.and(predicateList
					.toArray(new Predicate[predicateList.size()])));
			select.distinct(true);
			TypedQuery<Long> query = em.createQuery(select);

			ret = query.getSingleResult().intValue();
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
			throw new ArgusException(e.getMessage(), e);
		}
		LOGGER.info("count = " + ret);
		return ret;
	}

}
