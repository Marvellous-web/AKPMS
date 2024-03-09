package argus.repo.doctor;

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
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import argus.domain.Doctor;
import argus.exception.ArgusException;
import argus.util.AkpmsUtil;
import argus.util.Constants;

@Repository
@Transactional
public class DoctorDaoImpl implements DoctorDao {
	private static final Logger LOGGER = Logger.getLogger(DoctorDaoImpl.class);

	private static final String CHANGE_STATUS_INACTIVE = "UPDATE Doctor as d SET d.status = false, d.modifiedBy = :modifiedBy, d.modifiedOn = :modifiedOn WHERE d.id = :id or d.parent.id = :parentId ";

	private static final String CHANGE_STATUS_ACTIVE = "UPDATE Doctor as d SET d.status = true, d.modifiedBy = :modifiedBy, d.modifiedOn = :modifiedOn WHERE d.id= :id ";

	private static final String DELETE_SINGLE_STATUS = "UPDATE Doctor as d SET d.deleted = "
			+ Constants.IS_DELETED
			+ ", d.modifiedBy = :modifiedBy, d.modifiedOn = :modifiedOn WHERE d.id = :id or d.parent.id = :parentId ";

	private static final String DOCTOR_STATS_NATIVE = "SELECT status, COUNT(*) FROM doctor d WHERE d.deleted = "
			+ Constants.NON_DELETED + " GROUP BY status";

	private static final String GET_DEPT_WITH_PARENT_ID_AND_CHILD_COUNT_NATIVE = "SELECT id, name, IFNULL(parent_id,0),"
			+ "(SELECT COUNT(*) FROM doctor d WHERE d.parent_id = dept.id and d.is_deleted = "
			+ Constants.NON_DELETED
			+ " AND d.status = "
			+ Constants.ACTIVATE
			+ ")"
			+ "FROM doctor dept WHERE dept.is_deleted = "
			+ Constants.NON_DELETED
			+ " AND dept.status = "
			+ Constants.ACTIVATE + " ORDER BY dept.name";

	@Autowired
	private EntityManager em;

	/**
	 * function is used to get doctor by id
	 *
	 */
	public Doctor findById(Long id, boolean dependancies) throws ArgusException {
		Doctor doctor = em.find(Doctor.class, id);

		if (doctor != null && dependancies) {
			Hibernate.initialize(doctor.getDoctors());
		}

		return doctor;
	}

	/**
	 * function is used to find by name
	 */
	public Doctor findByName(String name) throws ArgusException {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Doctor> criteria = builder.createQuery(Doctor.class);

		Root<Doctor> doctor = criteria.from(Doctor.class);

		criteria.select(doctor).where(builder.equal(doctor.get("name"), name),
				builder.equal(doctor.get("deleted"), false));

		return em.createQuery(criteria).getSingleResult();
	}

	/**
	 * function is used to find by name
	 */
	public Doctor findByName(String name, Long parentId) throws ArgusException {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Doctor> criteria = builder.createQuery(Doctor.class);

		Root<Doctor> doctor = criteria.from(Doctor.class);

		if(null != parentId){
		criteria.select(doctor).where(builder.equal(doctor.get("name"), name),
				builder.equal(doctor.get("deleted"), false),
				builder.equal(doctor.get("parent").get("id"), parentId));
		}else{
			criteria.select(doctor).where(builder.equal(doctor.get("name"), name),
					builder.equal(doctor.get("deleted"), false),
					builder.isNull(doctor.get("parent").get("id")));
					//builder.equal(doctor.get("parent").get("id"), parentId));
		}

		return em.createQuery(criteria).getSingleResult();
	}

	public List<Doctor> findAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses, boolean dependancies)
			throws ArgusException {
		List<Doctor> doctorList = null;

		StringBuilder queryString = new StringBuilder();

		queryString.append("SELECT d FROM Doctor AS d ");
		queryString.append(" WHERE d.deleted =  " + Constants.NON_DELETED);
		if (whereClauses != null && whereClauses.size() > 0) {
			Set<String> key = whereClauses.keySet();
			for (String field : key) {
				if (field.equalsIgnoreCase(Constants.KEYWORD)) {
					LOGGER.info("found search text value  = "
							+ whereClauses.get(field));
					queryString.append(" AND ( d.name LIKE '%"
							+ whereClauses.get(Constants.KEYWORD) + "%' ");
					queryString.append(" OR d.doctorCode LIKE '%"
							+ whereClauses.get(Constants.KEYWORD) + "%' )");
				} else if (field.equalsIgnoreCase(Constants.STATUS)) {
					queryString.append(" AND d.status = "
							+ whereClauses.get(field));
				} else if (field.equalsIgnoreCase(Constants.PARENT_ID)) {
					queryString.append(" AND d.parent.id = "
							+ whereClauses.get(field));
				} else if (field.equalsIgnoreCase(Constants.PARENT_ONLY)) {
					queryString.append(" AND d.parent.id IS NULL ");
				} else if (field.equalsIgnoreCase(Constants.STATUS)) {
					queryString.append(" AND d.status = "
							+ whereClauses.get(field));
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
		} else {
			queryString.append(" ORDER BY d.name");
		}

		TypedQuery<Doctor> query = em.createQuery(queryString.toString(),
				Doctor.class);
		if (orderClauses != null) {
			if (orderClauses.get(Constants.OFFSET) != null
					&& orderClauses.get("limit") != null) {
				query.setFirstResult(Integer.parseInt(orderClauses
						.get(Constants.OFFSET)));
				query.setMaxResults(Integer.parseInt(orderClauses
						.get(Constants.LIMIT)));
			}
		}

		LOGGER.info("SQL:: " + queryString.toString());

		doctorList = query.getResultList();

		if (dependancies) {
			if (doctorList != null && doctorList.size() > Constants.ZERO) {
				Iterator<Doctor> doctorIterator = doctorList.iterator();

				while (doctorIterator.hasNext()) {
					Doctor doctor = doctorIterator.next();
					Hibernate.initialize(doctor.getDoctors());
				}
			}
		}

		return doctorList;
	}

	/**
	 * to add new doctor
	 */
	public void addDoctor(Doctor doctor) throws ArgusException {
		em.persist(doctor);
		return;
	}

	/**
	 * to update existing doctor
	 */
	public void updateDoctor(Doctor doctor) throws ArgusException {
		em.merge(doctor);
		return;
	}

	/**
	 * to get all parent doctor order by name, except one (in edit case)
	 */
	public List<Doctor> findAllParentOrderedByName(Long id)
			throws ArgusException {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Doctor> criteria = builder.createQuery(Doctor.class);
		Root<Doctor> from = criteria.from(Doctor.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();

		predicateList.add(builder.and(builder.equal(from.get("deleted"),
				Constants.NON_DELETED)));

		predicateList.add(builder.and(builder.equal(from.get(Constants.STATUS),
				Constants.BOOLEAN_ACTIVATE)));

		predicateList.add(builder.and(builder.isNull(from.get("parent").get(
				Constants.ID))));

		if (null != id && id > 0) {
			predicateList.add(builder.and(builder.notEqual(
					from.get(Constants.ID), id)));
		}

		criteria.select(from).where(
				predicateList.toArray(new Predicate[predicateList.size()]));

		return em.createQuery(criteria).getResultList();
	}

	/**
	 * to get total records
	 */
	public int totalRecord(Map<String, String> whereClauses)
			throws ArgusException {
		int totalRecords = 0;
		StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT COUNT(*) FROM Doctor AS d ");
		queryString.append(" WHERE d.deleted =  " + Constants.NON_DELETED);

		if (whereClauses != null && whereClauses.size() > 0) {
			Set<String> key = whereClauses.keySet();
			for (String field : key) {
				if (field.equalsIgnoreCase(Constants.KEYWORD)) {
					LOGGER.info("found search text value  = "
							+ whereClauses.get(field));
					queryString.append(" AND( d.name LIKE '%"
							+ whereClauses.get(Constants.KEYWORD) + "%' ");
					queryString.append(" OR d.doctorCode LIKE '%"
							+ whereClauses.get(Constants.KEYWORD) + "%') ");
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
	 * to delete single doctor
	 */
	@Override
	public int deleteDoctor(Long id) throws ArgusException {
		Query query = em.createQuery(DELETE_SINGLE_STATUS);

		query.setParameter("modifiedBy", AkpmsUtil.getLoggedInUser());
		query.setParameter("modifiedOn", new Date());
		query.setParameter("id", id);
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
			query.setParameter("id", id);
			query.setParameter("parentId", id);

			LOGGER.info("SQL:" + query.toString());
			return query.executeUpdate();
		} else {
			Query query = em.createQuery(CHANGE_STATUS_ACTIVE);

			query.setParameter("modifiedBy", AkpmsUtil.getLoggedInUser());
			query.setParameter("modifiedOn", new Date());
			query.setParameter("id", id);

			LOGGER.info("SQL:" + query.toString());
			return query.executeUpdate();
		}
	}

	/**
	 * to get parent and its child count only
	 */
	public List<Doctor> getDoctorsWithParentIdAndChildCount()
			throws ArgusException {
		List<Doctor> doctorList = null;
		Query query = em
				.createNativeQuery(GET_DEPT_WITH_PARENT_ID_AND_CHILD_COUNT_NATIVE);
		@SuppressWarnings("unchecked")
		List<Object[]> listObject = query.getResultList();

		if (listObject != null && listObject.size() > Constants.ZERO) {
			doctorList = new ArrayList<Doctor>();

			for (Object[] obj : listObject) {
				Doctor dept = new Doctor();

				if (obj[Constants.ZERO] instanceof BigInteger) {
					dept.setId(((BigInteger) obj[Constants.ZERO]).longValue());
				} else {
					dept.setId((Long) obj[Constants.ZERO]);
				}
				dept.setName((String) obj[Constants.ONE]);
				if (obj[Constants.TWO] instanceof BigInteger) {
					dept.setParentId(((BigInteger) obj[Constants.TWO])
							.longValue());
				} else {
					dept.setParentId((Long) obj[Constants.TWO]);
				}
				if (obj[Constants.THREE] instanceof BigInteger) {
					dept.setChildCount(((BigInteger) obj[Constants.THREE])
							.longValue());
				} else {
					dept.setChildCount((Long) obj[Constants.THREE]);
				}
				doctorList.add(dept);
			}
		}
		return doctorList;
	}

	/**
	 * function to get doctor status for active and inactive doctors
	 */
	public List<Object[]> getDoctorStats() throws ArgusException {
		Query query = em.createNativeQuery(DOCTOR_STATS_NATIVE);
		@SuppressWarnings("unchecked")
		List<Object[]> listObject = query.getResultList();

		return listObject;
	}

	/*
	 * @Override public Doctor findByDoctorCode(String doctorCode, boolean
	 * dependancies) throws ArgusException { CriteriaBuilder builder =
	 * em.getCriteriaBuilder(); CriteriaQuery<Doctor> criteria =
	 * builder.createQuery(Doctor.class);
	 *
	 * Root<Doctor> doctor = criteria.from(Doctor.class);
	 *
	 * criteria.select(doctor).where( builder.equal(doctor.get("doctorCode"),
	 * doctorCode), builder.equal(doctor.get("deleted"), false));
	 *
	 * return em.createQuery(criteria).getSingleResult(); }
	 */
}
