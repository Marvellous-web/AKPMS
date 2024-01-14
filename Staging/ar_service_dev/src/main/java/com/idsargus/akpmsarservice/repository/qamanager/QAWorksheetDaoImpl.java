package com.idsargus.akpmsarservice.repository.qamanager;


import com.idsargus.akpmsarservice.exception.ArgusException;
import com.idsargus.akpmsarservice.model.domain.QAWorksheet;
import com.idsargus.akpmsarservice.util.AkpmsUtil;
import com.idsargus.akpmsarservice.util.Constants;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.*;

@Repository("qaWorksheetDao")
@Transactional
public class QAWorksheetDaoImpl implements QAWorksheetDao {



	private static final int ZERO = 0;
	private static final String DELETE_QAWORKSHEET = "UPDATE QAWorksheet as sheet SET sheet.deleted = 1, sheet.modifiedBy = :modifiedBy, "
			+ "sheet.modifiedOn = :modifiedOn WHERE sheet.id = :id";
	// private static final String CREATED_BY =
	// "SELECT DISTINCT q.createdBy.id, q.createdBy.firstName, q.createdBy.lastName FROM QAWorksheet q";

	private static final String CHANGE_STATUS = "UPDATE QAWorksheet as sheet SET sheet.status = :status, sheet.modifiedBy = :modifiedBy, sheet.modifiedOn = :modifiedOn WHERE sheet.id = :id ";

	@Autowired
	private EntityManager em;

	/**
	 * params : id
	 *
	 * return : QAWorksheet
	 */
	@Override
	public QAWorksheet findById(Integer id) throws ArgusException {
		return em.find(QAWorksheet.class, id);
	}

	@Override
	public QAWorksheet findByName(String name) throws ArgusException {
		QAWorksheet qaWorksheet = null;
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<QAWorksheet> criteria = builder
				.createQuery(QAWorksheet.class);

		Root<QAWorksheet> qaworksheet = criteria.from(QAWorksheet.class);

		criteria.select(qaworksheet).where(
				builder.equal(qaworksheet.get(Constants.BY_NAME), name));

		List<QAWorksheet> qaWorksheetList = em.createQuery(criteria)
				.getResultList();
		if (qaWorksheetList != null && qaWorksheetList.size() != 0) {
			qaWorksheet = qaWorksheetList.get(0);
		}
		return qaWorksheet;
	}

	@Override
	public List<QAWorksheet> findAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses, boolean dependencies)
			throws ArgusException {
		List<QAWorksheet> qaworksheetList = null;

		StringBuilder queryString = new StringBuilder();

		queryString.append("SELECT sheet FROM QAWorksheet AS sheet ");
		queryString.append("WHERE sheet.deleted = 0 ");

		queryString.append(getQueryFindAll(orderClauses, whereClauses)
				.toString());

		TypedQuery<QAWorksheet> query = em.createQuery(queryString.toString(),
				QAWorksheet.class);
		if (orderClauses != null && orderClauses.get(Constants.OFFSET) != null
				&& orderClauses.get(Constants.LIMIT) != null) {
			query.setFirstResult(Integer.parseInt(orderClauses
					.get(Constants.OFFSET)));
			query.setMaxResults(Integer.parseInt(orderClauses
					.get(Constants.LIMIT)));
		}

		//LOGGER.info("SQL:: " + queryString.toString());

		qaworksheetList = query.getResultList();
		if (dependencies && qaworksheetList != null
				&& qaworksheetList.size() > 0) {
			Iterator<QAWorksheet> iterator = qaworksheetList.iterator();
			while (iterator.hasNext()) {
				QAWorksheet qaWorksheet = iterator.next();
				Hibernate.initialize(qaWorksheet.getCreatedBy());
				Hibernate.initialize(qaWorksheet.getCreatedOn());
				/*
				 * Hibernate.initialize(qaWorksheet.getQaWorksheetStaffs()); for
				 * (QAWorksheetStaff staff : qaWorksheet.getQaWorksheetStaffs())
				 * { Hibernate.initialize(staff.getUser()); }
				 */
			}
		}

		return qaworksheetList;
	}

	@Override
	public void save(QAWorksheet qaworksheet) throws ArgusException {
		qaworksheet.setCreatedBy(AkpmsUtil.getLoggedInUser());
		qaworksheet.setCreatedOn(new Date());
		em.persist(qaworksheet);
	}

	@Override
	public void update(QAWorksheet qaworksheet) throws ArgusException {
		em.merge(qaworksheet);
	}

	@Override
	public int deleteQAWorksheet(Long id) throws ArgusException {
		Query query = em.createQuery(DELETE_QAWORKSHEET);

		query.setParameter("modifiedBy", AkpmsUtil.getLoggedInUser());
		query.setParameter("modifiedOn", new Date());
		query.setParameter(Constants.ID, id);

		//LOGGER.info("SQL:" + query.toString());

		return query.executeUpdate();
	}

	@Override
	public int changeStatus(long id, int status) throws ArgusException {
		Query query = em.createQuery(CHANGE_STATUS);

		query.setParameter("modifiedBy", AkpmsUtil.getLoggedInUser());
		query.setParameter("modifiedOn", new Date());

		query.setParameter(Constants.ID, id);
		query.setParameter("status", status);

	//	LOGGER.info("SQL:" + query.toString());
		return query.executeUpdate();
	}

	@Override
	public int totalRecord(Map<String, String> conditionMap)
			throws ArgusException {
		int totalRecords = 0;
		StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT COUNT(*) FROM QAWorksheet AS sheet ");
		queryString.append(" WHERE sheet.deleted = 0 ");

		if (conditionMap != null && conditionMap.size() > ZERO) {
			Set<String> key = conditionMap.keySet();
			for (String field : key) {
				if (field.equalsIgnoreCase(Constants.KEYWORD)) {
//					LOGGER.info("found search text value  = "
//							+ conditionMap.get(field));
					queryString.append(" AND sheet.name LIKE '%"
							+ conditionMap.get(Constants.KEYWORD) + "%' ");
				} else if (field.equalsIgnoreCase(Constants.DEPARTMENT)) {
//					LOGGER.info("found search text DEPARTMENT  = "
//							+ conditionMap.get(field));
					queryString.append(" AND sheet." + field + " = "
							+ conditionMap.get(Constants.DEPARTMENT));
				} else if (field.equalsIgnoreCase(Constants.DEPARTMENT_ID)) {
//					LOGGER.info("found search text DEPARTMENT_ID  = "
//							+ conditionMap.get(field));
					queryString.append(" AND sheet.department.id = "
							+ conditionMap.get(Constants.DEPARTMENT_ID));
				} else if (field.equalsIgnoreCase(Constants.SUB_DEPARTMENT_ID)) {
//					LOGGER.info("found search text SUB_DEPARTMENT_ID  = "
//							+ conditionMap.get(field));
					queryString.append(" AND sheet.subDepartment.id = "
							+ conditionMap.get(Constants.SUB_DEPARTMENT_ID));
					// created by
				} else if (field.equalsIgnoreCase(Constants.CREATED_BY)) {
//					LOGGER.info("found CREATED_BY = "
//							+ conditionMap.get(Constants.CREATED_BY));
					if (conditionMap.get(Constants.CREATED_BY).split(",").length > Constants.ZERO) {
						queryString.append(" AND sheet.createdBy.id IN("
								+ conditionMap.get(Constants.CREATED_BY) + ")");
					} else {
						queryString.append(" AND sheet.createdBy.id = "
								+ conditionMap.get(Constants.CREATED_BY));
					}
					/*
					 * queryString.append(" AND sheet.createdBy.id = " +
					 * conditionMap.get(Constants.CREATED_BY));
					 */

					// date created from
				} else if (field.equalsIgnoreCase(Constants.DATE_CREATED_FROM)) {
//					LOGGER.info("found DATE_CREATED_FROM = "
//							+ conditionMap.get(Constants.DATE_CREATED_FROM));
					queryString.append(" AND sheet.createdOn >= '"
							+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
									.akpmsNewDateFormat(conditionMap
											.get(Constants.DATE_CREATED_FROM)),
									Constants.MYSQL_DATE_FORMAT) + "'");

					// date created to
				} else if (field.equalsIgnoreCase(Constants.DATE_CREATED_TO)) {
//					LOGGER.info("found DATE_CREATED_TO = "
//							+ conditionMap.get(Constants.DATE_CREATED_TO));
					queryString.append(" AND sheet.createdOn <= '"
							+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
									.getFormattedDate(conditionMap
											.get(Constants.DATE_CREATED_TO)),
									Constants.MYSQL_DATE_FORMAT_WITH_TIME)
							+ "'");

				} else {
					queryString.append(" AND sheet." + field);
					queryString.append(" LIKE '%" + conditionMap.get(field)
							+ "%' ");
				}
			}
		}
		TypedQuery<Long> query = em.createQuery(queryString.toString(),
				Long.class);
		totalRecords = query.getSingleResult().intValue();
//		LOGGER.info("count = " + totalRecords);
		return totalRecords;
	}

	private StringBuilder getQueryFindAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses) {

		StringBuilder queryString = new StringBuilder();

		if (whereClauses != null && whereClauses.size() > ZERO) {
			Set<String> key = whereClauses.keySet();
			for (String field : key) {
				if (field.equalsIgnoreCase(Constants.KEYWORD)) {
//					LOGGER.info("found search text value  = "
//							+ whereClauses.get(field));
					queryString.append(" AND sheet.name LIKE '%"
							+ whereClauses.get(Constants.KEYWORD) + "%' ");

				} else if (field.equalsIgnoreCase(Constants.DEPARTMENT)) {
					queryString.append(" AND sheet." + Constants.DEPARTMENT
							+ "." + Constants.ID + " = "
							+ whereClauses.get(Constants.DEPARTMENT));

				} else if (field.equalsIgnoreCase(Constants.SUB_DEPARTMENT)) {
					queryString.append(" AND sheet." + Constants.SUB_DEPARTMENT
							+ "." + Constants.ID + " = "
							+ whereClauses.get(Constants.SUB_DEPARTMENT));

				} else if (field.equalsIgnoreCase(Constants.MONTH)) {
					queryString.append(" AND MONTH(sheet.createdOn) = "
							+ whereClauses.get(Constants.MONTH));

				} else if (field.equalsIgnoreCase(Constants.YEAR)) {
					queryString.append(" AND YEAR(sheet.createdOn) = "
							+ whereClauses.get(Constants.YEAR));
				} else if (field.equalsIgnoreCase(Constants.DEPARTMENT_ID)) {
//					LOGGER.info("found search text DEPARTMENT_ID  = "
//							+ whereClauses.get(field));
					queryString.append(" AND sheet.department.id = "
							+ whereClauses.get(Constants.DEPARTMENT_ID));
				} else if (field.equalsIgnoreCase(Constants.SUB_DEPARTMENT_ID)) {
//					LOGGER.info("found search text SUB_DEPARTMENT_ID  = "
//							+ whereClauses.get(field));
					queryString.append(" AND sheet.subDepartment.id = "
							+ whereClauses.get(Constants.SUB_DEPARTMENT_ID));
					// created by
				} else if (field.equalsIgnoreCase(Constants.CREATED_BY)) {
//					LOGGER.info("found CREATED_BY = "
//							+ whereClauses.get(Constants.CREATED_BY));

					if (whereClauses.get(Constants.CREATED_BY).split(",").length > 0) {
						queryString.append(" AND sheet.createdBy.id IN("
								+ whereClauses.get(Constants.CREATED_BY) + ")");
					} else {
						queryString.append(" AND sheet.createdBy.id = "
								+ whereClauses.get(Constants.CREATED_BY));
					}

					// date created from
				} else if (field.equalsIgnoreCase(Constants.DATE_CREATED_FROM)) {
//					LOGGER.info("found DATE_CREATED_FROM = "
//							+ whereClauses.get(Constants.DATE_CREATED_FROM));
					queryString.append(" AND sheet.createdOn >= '"
							+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
									.akpmsNewDateFormat(whereClauses
											.get(Constants.DATE_CREATED_FROM)),
									Constants.MYSQL_DATE_FORMAT) + "'");

					// date created to
				} else if (field.equalsIgnoreCase(Constants.DATE_CREATED_TO)) {
//					LOGGER.info("found DATE_CREATED_TO = "
//							+ whereClauses.get(Constants.DATE_CREATED_TO));
					queryString.append(" AND sheet.createdOn <= '"
							+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
									.getFormattedDate(whereClauses
											.get(Constants.DATE_CREATED_TO)),
									Constants.MYSQL_DATE_FORMAT_WITH_TIME)
							+ "'");

				} else {
					queryString.append(" AND sheet." + field);
					queryString.append(" LIKE '%" + whereClauses.get(field)
							+ "%' ");
				}
			}
		}

		if (orderClauses != null) {
			if (orderClauses.get(Constants.ORDER_BY) != null) {
				queryString.append(" ORDER BY sheet."
						+ orderClauses.get(Constants.ORDER_BY));
			} else {
				queryString.append(" ORDER BY sheet.name");
			}
			if (orderClauses.get(Constants.SORT_BY) != null) {
				queryString.append(" " + orderClauses.get(Constants.SORT_BY));
			} else {
				queryString.append(" ASC");
			}
		} else {
			queryString.append(" ORDER BY sheet.id DESC");
		}

		return queryString;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> findQAWorksheetIdAndName(
			Map<String, String> whereClause, Map<String, String> orderClauses) {
		List<Object[]> allQAWorksheetName = null;
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("SELECT sheet.id, sheet.name FROM QAWorksheet AS sheet WHERE sheet.deleted = 0 AND sheet.status = 2 ");

		queryString.append(this.getQueryFindAll(orderClauses, whereClause));

		Query query = em.createQuery(queryString.toString());

		if (orderClauses != null && orderClauses.get(Constants.OFFSET) != null
				&& orderClauses.get(Constants.LIMIT) != null) {
			query.setFirstResult(Integer.parseInt(orderClauses
					.get(Constants.OFFSET)));
			query.setMaxResults(Integer.parseInt(orderClauses
					.get(Constants.LIMIT)));
		}
		//LOGGER.debug("QAWorksheet name only JPQL " + queryString);

		allQAWorksheetName = (List<Object[]>) query.getResultList();

		return allQAWorksheetName;
	}

	@Override
	public QAWorksheet findById(Long id, boolean dependency) {
		QAWorksheet qaWorksheet = em.find(QAWorksheet.class, id);
		if (dependency) {
			Hibernate.initialize(qaWorksheet.getDepartment());
		}
		return qaWorksheet;
	}

	// @Override
	// public Map<Long, String> getCreatedBy() {
	// Map<Long, String> users = new TreeMap<Long, String>();
	// @SuppressWarnings("unchecked")
	// List<Object[]> rows = em.createQuery(CREATED_BY).getResultList();
	// for (Object[] row : rows) {
	// users.put((Long)row[0], ((String)row[1]) + ((String)row[2]));
	// }
	//
	// return users;
	// }
}
