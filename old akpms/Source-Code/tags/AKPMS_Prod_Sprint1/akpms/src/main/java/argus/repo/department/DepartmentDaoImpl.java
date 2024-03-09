package argus.repo.department;

import java.math.BigInteger;
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

import argus.domain.Department;
import argus.exception.ArgusException;
import argus.util.AkpmsUtil;
import argus.util.Constants;

@Repository
@Transactional
public class DepartmentDaoImpl implements DepartmentDao {
	private static final Log LOGGER = LogFactory
			.getLog(DepartmentDaoImpl.class);

	private static final int THREE = 3;

	private static final int TWO = 2;

	private static final int ONE = 1;

	private static final int ZERO = 0;

	private static final String CHANGE_STATUS_INACTIVE = "UPDATE Department as d SET d.status = false, d.modifiedBy = :modifiedBy, d.modifiedOn = :modifiedOn WHERE d.id = :id or d.parent.id = :parentId ";

	private static final String CHANGE_STATUS_ACTIVE = "UPDATE Department as d SET d.status = true, d.modifiedBy = :modifiedBy, d.modifiedOn = :modifiedOn WHERE d.id= :id ";

	private static final String DELETE_SINGLE_STATUS = "UPDATE Department as d SET d.deleted = 1, d.modifiedBy = :modifiedBy, d.modifiedOn = :modifiedOn WHERE d.id = :id or d.parent.id = :parentId ";

	private static final String DEPT_STATS_NATIVE = "SELECT status, COUNT(*) FROM akpms.department d WHERE d.is_deleted = 0 GROUP BY status";

	private static final String GET_DEPT_WITH_PARENT_ID_AND_CHILD_COUNT_NATIVE = "SELECT id, name, IFNULL(parent_id,0),"
			+ "(SELECT COUNT(*) FROM department d WHERE d.parent_id = dept.id and d.is_deleted = 0 AND d.status = 1"
			+ ")"
			+ "FROM department dept WHERE dept.is_deleted = 0 AND dept.status = 1 ORDER BY dept.name";

	@Autowired
	private EntityManager em;

	/**
	 * function is used to get department by id
	 *
	 */
	public Department findById(Long id, boolean dependancies)
			throws ArgusException {
		Department department = em.find(Department.class, id);

		if (department != null && dependancies) {
			Hibernate.initialize(department.getDepartments());
		}

		return department;
	}

	/**
	 * function is used to find by name
	 */
	public Department findByName(String name) throws ArgusException {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Department> criteria = builder
				.createQuery(Department.class);

		Root<Department> department = criteria.from(Department.class);

		criteria.select(department).where(
				builder.equal(department.get(Constants.BY_NAME), name),
				builder.equal(department.get(Constants.DELETED), false));

		return em.createQuery(criteria).getSingleResult();
	}

	private StringBuilder getQueryFindAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses) {

		StringBuilder queryString = new StringBuilder();

		if (whereClauses != null && whereClauses.size() > ZERO) {
			Set<String> key = whereClauses.keySet();
			for (String field : key) {
				if (field.equalsIgnoreCase(Constants.KEYWORD)) {
					LOGGER.info("found search text value  = "
							+ whereClauses.get(field));
					queryString.append(" AND d.name LIKE '%"
							+ whereClauses.get(Constants.KEYWORD) + "%' ");
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

	public List<Department> findAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses, boolean dependancies)
			throws ArgusException {
		List<Department> departmentList = null;

		StringBuilder queryString = new StringBuilder();

		queryString.append("SELECT d FROM Department AS d ");
		queryString.append(" WHERE d.deleted = " + Constants.NON_DELETED);

		queryString.append(getQueryFindAll(orderClauses, whereClauses)
				.toString());

		TypedQuery<Department> query = em.createQuery(queryString.toString(),
				Department.class);
		if (orderClauses != null && orderClauses.get(Constants.OFFSET) != null
				&& orderClauses.get(Constants.LIMIT) != null) {
			query.setFirstResult(Integer.parseInt(orderClauses
					.get(Constants.OFFSET)));
			query.setMaxResults(Integer.parseInt(orderClauses
					.get(Constants.LIMIT)));
		}

		LOGGER.info("SQL:: " + queryString.toString());

		departmentList = query.getResultList();

		if (dependancies && departmentList != null
				&& departmentList.size() > ZERO) {
			Iterator<Department> departmentIterator = departmentList.iterator();

			while (departmentIterator.hasNext()) {
				Department department = departmentIterator.next();
				Hibernate.initialize(department.getDepartments());

			}
		}

		return departmentList;
	}

	/**
	 * to add new department
	 */
	public void addDepartment(Department department) throws ArgusException {
		em.persist(department);
		return;
	}

	/**
	 * to update existing department
	 */
	public void updateDepartment(Department department) throws ArgusException {
		em.merge(department);
		return;
	}

	/**
	 * to get all parent department order by name
	 */
	public List<Department> findAllParentOrderedByName() throws ArgusException {
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<Department> criteria = cb.createQuery(Department.class);
		Root<Department> department = criteria.from(Department.class);

		criteria.select(department)
				.where(cb.isNull(department.get("parent").get(Constants.ID)),
						cb.and(cb.equal(department.get(Constants.DELETED),
								Constants.NON_DELETED)),
						cb.and(cb.equal(department.get(Constants.STATUS),
								Constants.ACTIVATE)))
				.orderBy(cb.asc(department.get(Constants.BY_NAME)));

		return em.createQuery(criteria).getResultList();
	}

	/**
	 * to get all parent department order by name, except one (in edit case)
	 */
	@Override
	public List<Department> findAllParentOrderedByName(Long id)
			throws ArgusException {
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<Department> criteria = cb.createQuery(Department.class);
		Root<Department> department = criteria.from(Department.class);

		criteria.select(department)
				.where(cb.isNull(department.get("parent").get(Constants.ID)),
						cb.and(cb.equal(department.get(Constants.DELETED),
								Constants.NON_DELETED)),
						cb.and(cb.equal(department.get(Constants.STATUS),
								Constants.ACTIVATE)),
						cb.and(cb.notEqual(department.get(Constants.ID), id)))
				.orderBy(cb.asc(department.get("name")));

		return em.createQuery(criteria).getResultList();
	}

	/**
	 * to get total records
	 */
	public int totalRecord(Map<String, String> whereClauses)
			throws ArgusException {
		int totalRecords = 0;
		StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT COUNT(*) FROM Department AS d ");
		queryString.append(" WHERE d.deleted = 0 ");

		if (whereClauses != null && whereClauses.size() > ZERO) {
			Set<String> key = whereClauses.keySet();
			for (String field : key) {
				if (field.equalsIgnoreCase(Constants.KEYWORD)) {
					LOGGER.info("found search text value  = "
							+ whereClauses.get(field));
					queryString.append(" AND d.name LIKE '%"
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

	/**
	 * to delete single department
	 */
	@Override
	public int deleteDepartment(Long id) throws ArgusException {
		Query query = em.createQuery(DELETE_SINGLE_STATUS);

		query.setParameter("modifiedBy", AkpmsUtil.getLoggedInUser());
		query.setParameter("modifiedOn", new Date());
		query.setParameter(Constants.ID, id);
		query.setParameter("parentId", id);

		LOGGER.info("SQL:" + query.toString());

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

			LOGGER.info("SQL:" + query.toString());
			return query.executeUpdate();
		} else {
			Query query = em.createQuery(CHANGE_STATUS_ACTIVE);
			query.setParameter("modifiedBy", AkpmsUtil.getLoggedInUser());
			query.setParameter("modifiedOn", new Date());
			query.setParameter(Constants.ID, id);

			LOGGER.info("SQL:" + query.toString());
			return query.executeUpdate();
		}
	}

	/**
	 * to get parent and its child count only
	 */
	public List<Department> getDepartmentsWithParentIdAndChildCount()
			throws ArgusException {
		List<Department> departmentList = null;
		Query query = em
				.createNativeQuery(GET_DEPT_WITH_PARENT_ID_AND_CHILD_COUNT_NATIVE);
		@SuppressWarnings("unchecked")
		List<Object[]> listObject = query.getResultList();

		if (listObject != null && listObject.size() > ZERO) {
			departmentList = new ArrayList<Department>();

			for (Object[] obj : listObject) {
				Department dept = new Department();

				if (obj[ZERO] instanceof BigInteger) {
					dept.setId(((BigInteger) obj[ZERO]).longValue());
				} else {
					dept.setId((Long) obj[ZERO]);
				}
				dept.setName((String) obj[ONE]);
				if (obj[TWO] instanceof BigInteger) {
					dept.setParentId(((BigInteger) obj[TWO]).longValue());
				} else {
					dept.setParentId((Long) obj[TWO]);
				}
				if (obj[THREE] instanceof BigInteger) {
					dept.setChildCount(((BigInteger) obj[THREE]).longValue());
				} else {
					dept.setChildCount((Long) obj[THREE]);
				}
				departmentList.add(dept);
			}
		}
		return departmentList;
	}

	/**
	 * function to get department status for active and inactive departments
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getDepartmentStats() throws ArgusException {
		List<Object[]> listObject = new ArrayList<Object[]>();
		Query query = em.createNativeQuery(DEPT_STATS_NATIVE);
		listObject = query.getResultList();
		return listObject;
	}
}
