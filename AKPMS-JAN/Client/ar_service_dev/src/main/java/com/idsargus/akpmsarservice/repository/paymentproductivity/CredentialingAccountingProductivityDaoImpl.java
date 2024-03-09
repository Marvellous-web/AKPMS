/**
 *
 */
package com.idsargus.akpmsarservice.repository.paymentproductivity;


import com.idsargus.akpmsarservice.exception.ArgusException;
import com.idsargus.akpmsarservice.model.domain.CredentialingAccountingProductivity;
import com.idsargus.akpmsarservice.util.AkpmsUtil;
import com.idsargus.akpmsarservice.util.Constants;
import com.idsargus.akpmscommonservice.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;
import java.util.logging.Logger;

/**
 * @author rajiv.k
 * 
 */
@Repository
@Transactional
public class CredentialingAccountingProductivityDaoImpl implements
		CredentialingAccountingProductivityDao {
	private static final Logger LOGGER = Logger
			.getLogger(String.valueOf(CredentialingAccountingProductivityDaoImpl.class));

	private static final String DATE_RECIEVED = "dateRecd";
	private static final String TASK_COMPLETED = "taskCompleted";
	@Autowired
	private EntityManager em;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * argus.repo.paymentproductivity.CredentialingAccountingProductivityDao
	 * #findById(java.lang.Long)
	 */
	@Override
	public CredentialingAccountingProductivity findById(Long id)
			throws ArgusException {
		CredentialingAccountingProductivity credentialingAccountingProductivity = em
				.find(CredentialingAccountingProductivity.class, id);

		return credentialingAccountingProductivity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * argus.repo.paymentproductivity.CredentialingAccountingProductivityDao
	 * #findByName(java.lang.String)
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * argus.repo.paymentproductivity.CredentialingAccountingProductivityDao
	 * #findAll(java.util.Map, java.util.Map, boolean)
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
					if (field.equalsIgnoreCase(Constants.MONTH)) {
						Expression<Integer> createdOn = from.get(Constants.CREATED_ON);
						Expression<Integer> month =  builder.function(Constants.MONTH, Integer.class,
								createdOn);
						predicateList.add(builder.equal(month,  whereClauses.get(Constants.MONTH)));
					}

					if (field.equalsIgnoreCase(Constants.YEAR)) {
						Expression<Integer> createdOn = from.get(Constants.CREATED_ON);
						Expression<Integer> year =  builder.function(Constants.YEAR, Integer.class,
								createdOn);
						predicateList.add(builder.equal(year,  whereClauses.get(Constants.YEAR)));
					}

					if (field.equalsIgnoreCase(Constants.POSTING_DATE_FROM)) {
						LOGGER.info("found DATE_CREATED_FROM = "
								+ whereClauses.get(Constants.POSTING_DATE_FROM));
						Path<Date> createOn = from.get(Constants.CREATED_ON);
						predicateList.add(builder.greaterThanOrEqualTo(
								createOn, AkpmsUtil
								.akpmsNewDateFormat(whereClauses
										.get(Constants.POSTING_DATE_FROM), Constants.TIME_STAMP_FORMAT)));
					}

					if (field.equalsIgnoreCase(Constants.POSTING_DATE_TO)) {
						LOGGER.info("found POSTING_DATE_TO = "
								+ whereClauses.get(Constants.POSTING_DATE_TO));
						Path<Date> createOn = from.get(Constants.CREATED_ON);
						predicateList.add(builder.lessThanOrEqualTo(
								createOn, AkpmsUtil
								.akpmsNewDateFormat(whereClauses
										.get(Constants.POSTING_DATE_TO), Constants.TIME_STAMP_FORMAT)));
					}

					if (field.equalsIgnoreCase(Constants.USER)) {
						Expression<UserEntity> user = from.get(Constants.USER);
						Expression<Long> userId =  builder.function(Constants.ID, Long.class,
								user);
						predicateList.add(builder.equal(userId,  whereClauses.get(Constants.USER)));
					}
				}
			}
			//this is block is implemented for productivity sampling where users random records 
			// are pulled up.
			if (orderClauses != null && orderClauses.containsKey(Constants.RANDOM_RECOREDS)) {
				LOGGER.info("Found : " + orderClauses.get(Constants.RANDOM_RECOREDS));
					//builder.
					//queryString.append(" ORDER BY RANDOM()");
					
					//orderClauses = null;
			}
			select.where(builder.and(predicateList
					.toArray(new Predicate[predicateList.size()])));
			select.distinct(true);

			TypedQuery<CredentialingAccountingProductivity> query = em
					.createQuery(select);

			LOGGER.info("SQL:: " + query.toString());
			ret = query.getResultList();

		} catch (Exception e) {
		//	LOGGER.error(Constants.EXCEPTION, e);
			throw new ArgusException(e.getMessage(), e);
		}
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * argus.repo.paymentproductivity.CredentialingAccountingProductivityDao
	 * #addCredentialingAccountingProductivity
	 * (argus.domain.paymentproductivity.CredentialingAccountingProductivity)
	 */
	@Override
	public void addCredentialingAccountingProductivity(
			CredentialingAccountingProductivity credentialingAccountingProductivity)
			throws ArgusException {
		em.persist(credentialingAccountingProductivity);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * argus.repo.paymentproductivity.CredentialingAccountingProductivityDao
	 * #updateCredentialingAccountingProductivity
	 * (argus.domain.paymentproductivity.CredentialingAccountingProductivity)
	 */
	@Override
	public void updateCredentialingAccountingProductivity(
			CredentialingAccountingProductivity credentialingAccountingProductivity)
			throws ArgusException {
		em.merge(credentialingAccountingProductivity);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * argus.repo.paymentproductivity.CredentialingAccountingProductivityDao
	 * #totalRecord(java.util.Map)
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
					
					if (field.equalsIgnoreCase(Constants.USER)) {
						
						predicateList
						.add(builder.and(builder.equal(
								from.get(Constants.CREATED_BY).get(
										Constants.ID),
								Long.parseLong(whereClauses
										.get(Constants.USER)))));
						
						/*Expression<User> user = from.get(Constants.CREATED_BY);
						
						Expression<Long> userId =  builder.function(Constants.ID, Long.class,
								user);
						predicateList.add(builder.equal(userId,  whereClauses.get(Constants.USER)));*/
					}

				}

			}
			select.where(builder.and(predicateList
					.toArray(new Predicate[predicateList.size()])));
			select.distinct(true);
			TypedQuery<Long> query = em.createQuery(select);

			ret = query.getSingleResult().intValue();
		} catch (Exception e) {
			//LOGGER.error(Constants.EXCEPTION, e);
			throw new ArgusException(e.getMessage(), e);
		}
		LOGGER.info("count = " + ret);
		return ret;
	}

	@Override
	public List<CredentialingAccountingProductivity> findAllJPQL(
			Map<String, String> orderClauses, Map<String, String> whereClauses,
			boolean dependancies) throws ArgusException {
		
		StringBuffer queryString = new StringBuffer();
		
		queryString.append("SELECT caProd FROM CredentialingAccountingProductivity AS caProd WEHER 1 = 1 ");
		queryString.append(getQueryWhereClause(whereClauses));
		queryString.append(getOrderClause(orderClauses));
		
		TypedQuery<CredentialingAccountingProductivity> query = em.createQuery(
				queryString.toString(), CredentialingAccountingProductivity.class);
		
		if (orderClauses != null && orderClauses.get(Constants.OFFSET) != null
				&& orderClauses.get(Constants.LIMIT) != null) {
			query.setFirstResult(Integer.parseInt(orderClauses
					.get(Constants.OFFSET)));
			query.setMaxResults(Integer.parseInt(orderClauses
					.get(Constants.LIMIT)));
		}

		LOGGER.info("SQL:: " + queryString.toString());

		
		return query.getResultList();
	}
	/**
	 * return where  clause
	 *
	 * @param whereClause
	 * @return
	 */
	private StringBuffer getQueryWhereClause(Map<String, String> whereClause) {
		StringBuffer queryString = new StringBuffer();
		if (whereClause != null && whereClause.size() > Constants.ZERO) {
			
			if (whereClause.containsKey(Constants.POSTED_BY_ID)) {
				queryString.append(" AND caProd.createdBy = " + whereClause.get(Constants.POSTED_BY_ID));
			}
			if (whereClause.containsKey(Constants.DATE_POSTED_FROM)) {
				queryString.append(" AND caProd.dateRecd >= " + "'" + AkpmsUtil.akpmsDateFormat(AkpmsUtil
						.getFormattedDate(whereClause.get(Constants.DATE_POSTED_FROM)),
						Constants.MYSQL_DATE_FORMAT_WITH_TIME) + "'");
			}
			if (whereClause.containsKey(Constants.DATE_POSTED_TO)) {
				queryString.append(" AND caProd.dateRecd <= " + "'" + AkpmsUtil.akpmsDateFormat(AkpmsUtil
						.getFormattedDate(whereClause.get(Constants.DATE_POSTED_TO)),
						Constants.MYSQL_DATE_FORMAT_WITH_TIME) + "'");
			}
			if (whereClause.containsKey(Constants.DATE_RECEIVED_FROM)) {
				queryString.append(" AND caProd.dateRecd >= " + "'" + AkpmsUtil.akpmsDateFormat(AkpmsUtil
						.getFormattedDate(Constants.DATE_RECEIVED_FROM),
						Constants.MYSQL_DATE_FORMAT_WITH_TIME) + "'");
			}
			if (whereClause.containsKey(Constants.DATE_RECEIVED_FROM)) {
				queryString.append(" AND caProd.dateRecd <= " + "'" + AkpmsUtil.akpmsDateFormat(AkpmsUtil
						.getFormattedDate(Constants.DATE_RECEIVED_TO),
						Constants.MYSQL_DATE_FORMAT_WITH_TIME) + "'");
			}
			
			if (whereClause.containsKey(Constants.MONTH)) {
				queryString.append(" AND MONTH(caProd.createdOn) = " + whereClause.get(Constants.MONTH));
			}
	
			if (whereClause.containsKey(Constants.YEAR)) {
				queryString.append(" AND YEAR(caProd.createdOn) = " + whereClause.get(Constants.YEAR));
			}
			
			if (whereClause.containsKey(Constants.POSTING_DATE_FROM)){
				LOGGER.info("found DATE_CREATED_FROM = "
						+ whereClause.get(Constants.POSTING_DATE_FROM));
				
				queryString.append(" AND caProd.createdOn >= '"
						+ AkpmsUtil.akpmsFormatDateSQL(whereClause
										.get(Constants.POSTING_DATE_FROM)) + "'");
			}
			
			if(whereClause.containsKey(Constants.POSTING_DATE_TO)){
				
				Calendar cl = Calendar.getInstance();
				cl.setTime(AkpmsUtil.akpmsNewDateFormat(whereClause.get(Constants.POSTING_DATE_TO), Constants.TIME_STAMP_FORMAT));
				cl.set(Calendar.HOUR_OF_DAY,cl.getActualMaximum(Calendar.HOUR_OF_DAY));
				cl.set(Calendar.MINUTE,cl.getActualMaximum(Calendar.MINUTE));
				cl.set(Calendar.SECOND,cl.getActualMaximum(Calendar.SECOND));
				
				queryString.append(" AND caProd.createdOn <= '"+ AkpmsUtil.akpmsDateFormat(cl.getTime(),
								"yyyy-MM-dd HH:mm:ss") + "'");
			}
			
			if (whereClause.containsKey(Constants.USER)) {
				queryString.append(" AND caProd.createdBy = " + whereClause.get(Constants.USER));
			}
			
			
		}
		return queryString;
	}
	/**
	 * return order by clause
	 *
	 * @param orderClauses
	 * @return
	 */
	public StringBuffer getOrderClause(Map<String, String> orderClauses) {

		StringBuffer queryString = new StringBuffer();
		//this is block is implemented for productivity sampling where users random records 
		// are pulled up.
		if (orderClauses != null && orderClauses.containsKey(Constants.RANDOM_RECOREDS)) {
			LOGGER.info("Found : " + orderClauses.get(Constants.RANDOM_RECOREDS));
				queryString.append(" ORDER BY RAND()");
				
			// MAKE orderClauses null so next order by not gets executed
				orderClauses = null;
				return queryString;
		}
		
		if (orderClauses != null) {
			LOGGER.info("in [getQueryFindAll] orderby is not null");
			if (orderClauses.get(Constants.ORDER_BY) != null) {
				queryString.append(" ORDER BY caProd."
						+ orderClauses.get(Constants.ORDER_BY));
			} else {
				queryString.append(" ORDER BY caProd.createdOn");
			}
			if (orderClauses.get(Constants.SORT_BY) != null) {
				queryString.append(" " + orderClauses.get(Constants.SORT_BY));
			} else {
				queryString.append(" DESC");
			}
		}

		return queryString;
	}
}
