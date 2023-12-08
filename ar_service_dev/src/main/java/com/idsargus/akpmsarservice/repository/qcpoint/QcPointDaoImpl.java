package com.idsargus.akpmsarservice.repository.qcpoint;


import com.idsargus.akpmsarservice.exception.ArgusException;
import com.idsargus.akpmsarservice.model.domain.QcPoint;
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
import java.math.BigInteger;
import java.util.*;

@Repository
@Transactional
public class QcPointDaoImpl implements QcPointDao {
//	private static final Logger LOGGER = Logger
//			.getLogger(DepartmentDaoImpl.class);

	private static final int THREE = 3;

	private static final int TWO = 2;

	private static final int ONE = 1;

	private static final int ZERO = 0;

	private static final String CHANGE_STATUS_INACTIVE = "UPDATE QcPoint as d SET d.status = false, d.modifiedBy = :modifiedBy, d.modifiedOn = :modifiedOn WHERE d.id = :id or d.parent.id = :parentId ";

	private static final String CHANGE_STATUS_ACTIVE = "UPDATE QcPoint as d SET d.status = true, d.modifiedBy = :modifiedBy, d.modifiedOn = :modifiedOn WHERE d.id= :id ";

	private static final String DELETE_SINGLE_STATUS = "UPDATE qc_point as d SET d.deleted = 1, d.modifiedBy = :modifiedBy, d.modifiedOn = :modifiedOn WHERE d.id = :id or d.parent.id = :parentId ";

	private static final String DEPT_STATS_NATIVE = "SELECT status, COUNT(*) FROM qc_point d WHERE d.deleted = 0 GROUP BY status";

	@Autowired
	private EntityManager em;

	/**
	 * function is used to get department by id
	 *
	 */
	public QcPoint findById(Long id, boolean dependancies)
			throws ArgusException {
		QcPoint qcPoint = em.find(QcPoint.class, id);

		if (qcPoint != null && dependancies) {
			//Hibernate.initialize(qcPoint.getQcPoints());
			Hibernate.initialize(qcPoint.getDepartment());
			Hibernate.initialize(qcPoint.getCreatedBy());
		}

		return qcPoint;
	}

	/**
	 * function is used to find by name
	 */
	public QcPoint findByName(String name) throws ArgusException {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<QcPoint> criteria = builder.createQuery(QcPoint.class);

		Root<QcPoint> qcPoint = criteria.from(QcPoint.class);

		criteria.select(qcPoint).where(
				builder.equal(qcPoint.get(Constants.BY_NAME), name),
				builder.equal(qcPoint.get(Constants.DELETED), false));

		return em.createQuery(criteria).getSingleResult();
	}

	public QcPoint findByNameAndDepartmentId(String name, Long departmentId)
			throws ArgusException {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<QcPoint> criteria = builder.createQuery(QcPoint.class);

		Root<QcPoint> qcPoint = criteria.from(QcPoint.class);

		criteria.select(qcPoint).where(
				builder.equal(qcPoint.get(Constants.BY_NAME), name),
				builder.equal(qcPoint.get(Constants.DEPARTMENT_ID),
						departmentId),
				builder.equal(qcPoint.get(Constants.DELETED), false));

		return em.createQuery(criteria).getSingleResult();

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
					queryString.append(" AND d.name LIKE '%"
							+ whereClauses.get(Constants.KEYWORD) + "%' ");
				} else if (field.equalsIgnoreCase(Constants.PARENT_ONLY)) {
					queryString.append(" AND d.parent = null");
				} else if (field.equalsIgnoreCase(Constants.CHILD_ONLY)) {
					queryString.append(" AND d.parent IS NOT NULL");
				} else if (field.equalsIgnoreCase(Constants.PARENT_ID)) {
					queryString.append(" AND d.parent.id = "
							+ whereClauses.get(Constants.PARENT_ID));
				} else if (field.equalsIgnoreCase(Constants.DEPARTMENT_ID)) {
					queryString.append(" AND d.department.id = "
							+ whereClauses.get(Constants.DEPARTMENT_ID));
				} else if (field.equalsIgnoreCase(Constants.SUB_DEPARTMENT_ID)) {
					queryString.append(" AND d.subDepartment.id = "
							+ whereClauses.get(Constants.SUB_DEPARTMENT_ID));
				} else {
					queryString.append(" AND d." + field);
					queryString.append(" LIKE '%" + whereClauses.get(field)
							+ "%' ");
				}
			}
		}

		if (orderClauses != null) {
			if (orderClauses.get(Constants.ORDER_BY) != null) {
				queryString.append(" ORDER BY d."
						+ orderClauses.get(Constants.ORDER_BY));
			} else {
				queryString.append(" ORDER BY d.name");
			}
			if (orderClauses.get(Constants.SORT_BY) != null) {
				queryString.append(" " + orderClauses.get(Constants.SORT_BY));
			} else {
				queryString.append(" ASC");
			}
		}

		return queryString;
	}

	public List<QcPoint> findAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses, boolean dependancies)
			throws ArgusException {
		List<QcPoint> qcPointList = null;

		StringBuilder queryString = new StringBuilder();

		queryString.append("SELECT d FROM QcPoint AS d ");
		queryString.append(" WHERE d.deleted = " + Constants.NON_DELETED);

		queryString.append(getQueryFindAll(orderClauses, whereClauses)
				.toString());

		TypedQuery<QcPoint> query = em.createQuery(queryString.toString(),
				QcPoint.class);
		if (orderClauses != null && orderClauses.get(Constants.OFFSET) != null
				&& orderClauses.get(Constants.LIMIT) != null) {
			query.setFirstResult(Integer.parseInt(orderClauses
					.get(Constants.OFFSET)));
			query.setMaxResults(Integer.parseInt(orderClauses
					.get(Constants.LIMIT)));
		}

//		LOGGER.info("SQL:: " + queryString.toString());

		qcPointList = query.getResultList();

		if (dependancies && qcPointList != null && qcPointList.size() > ZERO) {
			Iterator<QcPoint> qcPointIterator = qcPointList.iterator();

			while (qcPointIterator.hasNext()) {
				QcPoint qcPoint = qcPointIterator.next();
				//Hibernate.initialize(qcPoint.getQcPoints());
				Hibernate.initialize(qcPoint.getCreatedBy());

			}
			for (QcPoint qcPoint : qcPointList) {
				Hibernate.initialize(qcPoint.getDepartment());
			}
		}

		return qcPointList;
	}

	/**
	 * to add new QcPoint
	 */
	public void addQcPoint(QcPoint qcPoint) throws ArgusException {
		em.persist(qcPoint);
		return;
	}

	/**
	 * to update existing qcPoint
	 */
	public void updateQcPoint(QcPoint qcPoint) throws ArgusException {
		em.merge(qcPoint);
		return;
	}

	/**
	 * to get all parent qcPoint order by name
	 */
	public List<QcPoint> findAllParentOrderedByName() throws ArgusException {
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<QcPoint> criteria = cb.createQuery(QcPoint.class);
		Root<QcPoint> qcPoint = criteria.from(QcPoint.class);

		criteria.select(qcPoint)
				.where(cb.isNull(qcPoint.get("parent").get(Constants.ID)),
						cb.and(cb.equal(qcPoint.get(Constants.DELETED),
								Constants.NON_DELETED)),
						cb.and(cb.equal(qcPoint.get(Constants.STATUS),
								Constants.ACTIVATE)))
				.orderBy(cb.asc(qcPoint.get(Constants.BY_NAME)));

		return em.createQuery(criteria).getResultList();
	}

	/**
	 * to get all parent qcPoint order by name, except one (in edit case)
	 */
	@Override
	public List<QcPoint> findAllParentOrderedByName(Long id)
			throws ArgusException {
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<QcPoint> criteria = cb.createQuery(QcPoint.class);
		Root<QcPoint> qcPoint = criteria.from(QcPoint.class);

		criteria.select(qcPoint)
				.where(cb.isNull(qcPoint.get("parent").get(Constants.ID)),
						cb.and(cb.equal(qcPoint.get(Constants.DELETED),
								Constants.NON_DELETED)),
						cb.and(cb.equal(qcPoint.get(Constants.STATUS),
								Constants.ACTIVATE)),
						cb.and(cb.notEqual(qcPoint.get(Constants.ID), id)))
				.orderBy(cb.asc(qcPoint.get(Constants.BY_NAME)));

		return em.createQuery(criteria).getResultList();
	}

	/**
	 * to get total records
	 */
	public int totalRecord(Map<String, String> whereClauses)
			throws ArgusException {
		int totalRecords = 0;
		StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT COUNT(*) FROM QcPoint AS d ");
		queryString.append(" WHERE d.deleted = 0 ");

		if (whereClauses != null && whereClauses.size() > ZERO) {
			Set<String> key = whereClauses.keySet();
			for (String field : key) {
				if (field.equalsIgnoreCase(Constants.KEYWORD)) {
//					LOGGER.info("found search text value  = "
//							+ whereClauses.get(field));
					queryString.append(" AND d.name LIKE '%"
							+ whereClauses.get(Constants.KEYWORD) + "%' ");
				} else if (field.equalsIgnoreCase(Constants.DEPARTMENT_ID)) {
//					LOGGER.info("found search text value  = "
//							+ whereClauses.get(field));
					queryString.append(" AND d.department.id  = "
							+ whereClauses.get(Constants.DEPARTMENT_ID));
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
	//	LOGGER.info("count = " + totalRecords);
		return totalRecords;
	}

	/**
	 * to delete single qcPoint
	 */
	@Override
	public int deleteQcPoint(Long id) throws ArgusException {
		Query query = em.createQuery(DELETE_SINGLE_STATUS);

		query.setParameter("modifiedBy", AkpmsUtil.getLoggedInUser());
		query.setParameter("modifiedOn", new Date());
		query.setParameter(Constants.ID, id);
		query.setParameter("parentId", id);

		//LOGGER.info("SQL:" + query.toString());

		return query.executeUpdate();
	}

	/**
	 * to change the status active to inactive, inactive to active
	 */
	@Override
	public int changeStatus(long id, boolean status) throws ArgusException {

		if (!status) {
			Query query = em.createQuery(CHANGE_STATUS_INACTIVE);
			query.setParameter("modifiedBy", AkpmsUtil.getLoggedInUser());
			query.setParameter("modifiedOn", new Date());
			query.setParameter(Constants.ID, id);
			query.setParameter("parentId", id);

			//LOGGER.info("SQL:" + query.toString());
			return query.executeUpdate();
		} else {
			Query query = em.createQuery(CHANGE_STATUS_ACTIVE);
			query.setParameter("modifiedBy", AkpmsUtil.getLoggedInUser());
			query.setParameter("modifiedOn", new Date());
			query.setParameter(Constants.ID, id);

		//	LOGGER.info("SQL:" + query.toString());
			return query.executeUpdate();
		}
	}

	/**
	 * to get parent and its child count only
	 */
	@Override
	public List<QcPoint> getQcPointsWithParentIdAndChildCount(
			Long departmentId, Long subDepartmentId) throws ArgusException {
		List<QcPoint> qcPointList = null;

		StringBuffer stringBuffer = new StringBuffer();

		stringBuffer.append("SELECT id, name,IFNULL(parent_id,0),");
		stringBuffer
				.append(" (SELECT COUNT(*) FROM qc_point d WHERE d.parent_id = q.id and d.deleted = 0 AND d.status = 1 AND d.department_id = "
						+ departmentId + ")");
		stringBuffer
				.append(" FROM qc_point q WHERE q.deleted = 0 AND q.status = 1 AND q.parent_id is null AND q.department_id = "
						+ departmentId);

		if (null != subDepartmentId) {
			stringBuffer
					.append(" AND q.sub_department_id = " + subDepartmentId);
		}
		//LOGGER.info("QUERY :: " + stringBuffer.toString());

		Query query = em.createNativeQuery(stringBuffer.toString());
		@SuppressWarnings("unchecked")
		List<Object[]> listObject = query.getResultList();

		if (listObject != null && listObject.size() > ZERO) {
			qcPointList = new ArrayList<QcPoint>();

			for (Object[] obj : listObject) {
				QcPoint qcPoint = new QcPoint();

				if (obj[ZERO] instanceof BigInteger) {
					qcPoint.setId((int) ((BigInteger) obj[ZERO]).longValue());
				} else {
					qcPoint.setId(Math.toIntExact((Long) obj[ZERO]));
				}

				qcPoint.setName((String) obj[ONE]);
				if (obj[TWO] instanceof BigInteger) {
					qcPoint.setParentId(((BigInteger) obj[TWO]).longValue());
				} else {
					qcPoint.setParentId((Long) obj[TWO]);
				}
				if (obj[THREE] instanceof BigInteger) {
					qcPoint.setChildCount(((BigInteger) obj[THREE]).longValue());
				} else {
					qcPoint.setChildCount((Long) obj[THREE]);
				}
				qcPointList.add(qcPoint);
			}
		}

		return qcPointList;
	}

	/**
	 * function to get qcPoint status for active and inactive qcPoints
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getQcPointStats() throws ArgusException {
		List<Object[]> listObject = new ArrayList<Object[]>();
		Query query = em.createNativeQuery(DEPT_STATS_NATIVE);
		listObject = query.getResultList();
		return listObject;
	}

}
