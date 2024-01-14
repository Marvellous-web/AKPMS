package com.idsargus.akpmsarservice.repository.user;


import com.idsargus.akpmsarservice.model.domain.Department;
import com.idsargus.akpmsarservice.model.domain.Permission;
import com.idsargus.akpmsarservice.model.domain.Role;
import com.idsargus.akpmsarservice.model.domain.User;
import com.idsargus.akpmsarservice.util.AkpmsUtil;
import com.idsargus.akpmsarservice.util.Constants;
import com.idsargus.akpmscommonservice.entity.UserEntity;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {
	private static final Logger LOGGER = Logger.getLogger(String.valueOf(UserDaoImpl.class));

	private static final String CHANGE_STATUS = "UPDATE User as u SET u.status = :status, u.modifiedBy = :modifiedBy, u.modifiedOn = :modifiedOn WHERE u.id= :id";

	private static final String RESET_PASSWORD = "UPDATE User as u SET u.password = :password, u.modifiedBy = :modifiedBy, u.modifiedOn = :modifiedOn WHERE u.id= :id";

	private static final String DELETE_STATUS_SINGLE = "UPDATE User as u SET u.deleted = "
			+ Constants.IS_DELETED
			+ ", u.modifiedBy = :modifiedBy, u.modifiedOn = :modifiedOn WHERE u.id = :id";

	private static final String CHANGE_PASSWORD = "UPDATE User as u SET u.password = :password WHERE u.id= :id";

	private static final String USERS_STATS_NATIVE = "SELECT status ,COUNT(*) FROM user u WHERE u.is_deleted = 0 and u.id > "
			+ Constants.ADMIN_ROLE_ID + "  GROUP BY status";
	private static final String OFFSET_MANAGER = "P-5";

	@Autowired
	private EntityManager em;

	@Override
	public UserEntity findById(Long id, boolean dependancies) {

		UserEntity user = em.find(UserEntity.class, id);

		if (user != null && dependancies) {
			Hibernate.initialize(user.getDepartments());
			Hibernate.initialize(user.getPermissions());
			Hibernate.initialize(user.getEmailTemplates());
		}

		return user;
	}
	@Override
	public User findUserById(Integer id) {

		User user = em.find(User.class, id);

		if (user != null) {
			Hibernate.initialize(user.getDepartments());
			Hibernate.initialize(user.getPermissions());
			Hibernate.initialize(user.getEmailTemplates());
		}

		return user;
	}

	@Override
	public User findByEmail(String email, boolean loadDependacies)
		{

		User userDB = null;
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<User> criteria = builder.createQuery(User.class);
			Root<User> user = criteria.from(User.class);

			criteria.select(user).where(
					builder.and(builder.equal(user.get("email"), email)));

			userDB = em.createQuery(criteria).getSingleResult();
			if (userDB != null && loadDependacies) {
				Hibernate.initialize(userDB.getPermissions());
				Hibernate.initialize(userDB.getDepartments());
				Hibernate.initialize(userDB.getEmailTemplates());
			}
			return userDB;
		} catch (Exception e) {
//			LOGGER.error("Exception Reason : " + e.getMessage());
//			LOGGER.error(Constants.EXCEPTION, e);
			return null;
		}
	}

	@Override
	public List<User> findAll(String orderBy)  {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> criteria = cb.createQuery(User.class);
		Root<User> user = criteria.from(User.class);

		if (null == orderBy) {
			criteria.select(user).orderBy(
					cb.asc(user.get(Constants.FIRST_NAME)));
		} else {
			criteria.select(user).orderBy(cb.asc(user.get(orderBy)));
		}
		return em.createQuery(criteria).getResultList();
	}

	@Override
	public void register(User user)  {
		// em.persist(user);
		em.merge(user);
		return;
	}

	@Override
	public void updateUser(User user)  {
		em.merge(user);
	}

	@Override
	public List<User> findAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses) {
		List<User> ret = null;
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<User> userCriteriaQuery = builder
					.createQuery(User.class);
			Root<User> from = userCriteriaQuery.from(User.class);
			CriteriaQuery<User> select = userCriteriaQuery.select(from);

			List<Predicate> predicateList = new ArrayList<Predicate>();
			predicateList
					.add(builder.and(builder.equal(
							from.<String> get(Constants.DELETED),
							Constants.NON_DELETED)));

			predicateList.add(builder.and(builder.notEqual(
					from.get(Constants.ROLE).get(Constants.ID),
					Constants.ADMIN_ROLE_ID)));

			if (whereClauses != null && whereClauses.size() > Constants.ZERO) {
				Set<String> key = whereClauses.keySet();
				for (String field : key) {
					if (field
							.equalsIgnoreCase(Constants.SELECTED_DEPARTMENTS_IDS)) {
						LOGGER.info("found departments = "
								+ whereClauses
										.get(Constants.SELECTED_DEPARTMENTS_IDS));

						if (whereClauses
								.get(Constants.SELECTED_DEPARTMENTS_IDS) != null) {
							List<Department> deptList = new ArrayList<Department>();
							List<Long> deptListLong = new ArrayList<Long>();
							String[] strIds = whereClauses.get(
									Constants.SELECTED_DEPARTMENTS_IDS).split(
									",");
							if (strIds != null && strIds.length > 1) {
								LOGGER.info(Constants.MORE_THAN_ONE_DEPT_AVAILABLE);
								for (String stId : strIds) {
									Department dept = new Department();
									dept.setId((int) Long.parseLong(stId));
									deptList.add(dept);
								}
								for (String stId : strIds) {
									deptListLong.add(Long.parseLong(stId));
								}
							} else {
								Department dept = new Department();
								dept.setId((int) Long.parseLong(whereClauses
										.get(Constants.SELECTED_DEPARTMENTS_IDS)));
								deptList.add(dept);

								deptListLong
										.add(Long.parseLong(whereClauses
												.get(Constants.SELECTED_DEPARTMENTS_IDS)));
							}
							Join<User, Department> join = from.join(
									"departments", JoinType.LEFT);
							Subquery<Department> subquery = userCriteriaQuery
									.subquery(Department.class);
							Root<Department> fromDept = subquery
									.from(Department.class);
							subquery.select(fromDept);
							subquery.where(builder.in(join.get(Constants.ID))
									.value(deptListLong));
							predicateList.add(builder.and(builder.in(join)
									.value(subquery)));
						}
					} else if (field
							.equalsIgnoreCase(Constants.SELECTED_ROLES_IDS)) {
						if (whereClauses.get(Constants.SELECTED_ROLES_IDS) != null) {
							List<Role> roleList = new ArrayList<Role>();
							String[] strIds = whereClauses.get(
									Constants.SELECTED_ROLES_IDS).split(",");
							if (strIds != null && strIds.length > Constants.ONE) {
								LOGGER.info(Constants.MORE_THAN_ONE_DEPT_AVAILABLE);
								for (String stId : strIds) {
									Role role = new Role();
									role.setId(Long.parseLong(stId));
									roleList.add(role);
								}
							} else {
								Role role = new Role();
								role.setId(Long.parseLong(whereClauses
										.get(Constants.SELECTED_ROLES_IDS)));
								roleList.add(role);
							}
							predicateList.add(builder.and(builder.in(
									from.get(Constants.ROLE)).value(roleList)));
						}

					} else if (field.equalsIgnoreCase(Constants.STATUS)) {
						boolean status;
						if (Integer.parseInt(whereClauses.get(field)) == 1) {
							status = true;
						} else {
							status = false;
						}
						predicateList.add(builder.and(builder.equal(
								from.<String> get("status"), status)));
					} else if (field.equalsIgnoreCase(Constants.KEYWORD)) {
						Predicate p = builder.or(builder.like(
								from.<String> get(Constants.FIRST_NAME), "%"
										+ whereClauses.get(field) + "%"),
								builder.like(from.<String> get("lastName"), "%"
										+ whereClauses.get(field) + "%"),
								builder.like(from.<String> get("contact"), "%"
										+ whereClauses.get(field) + "%"),
								builder.like(from.<String> get("email"), "%"
										+ whereClauses.get(field) + "%"));
						predicateList.add(builder.and(p));
					} else if (field.equalsIgnoreCase(Constants.STATUS)) {
						predicateList.add(builder.and(builder.equal(from
								.<String> get(Constants.STATUS), Boolean
								.parseBoolean(whereClauses
										.get(Constants.STATUS)))));
					} else if (field
							.equalsIgnoreCase(Constants.DEPARTMENT_WITH_CHILD)) {

						Path<Long> departmentId = from.join("departments").get(
								Constants.ID);
						Path<Department> parentDepartmentId = from.join(
								"departments").get("parent");
						long deptId = Long.parseLong(whereClauses
								.get(Constants.DEPARTMENT_WITH_CHILD));

						Department parentDept = new Department();
						parentDept.setId((int) deptId);

						predicateList
								.add(builder.and(builder.or(builder.equal(
										departmentId, deptId), builder.equal(
										parentDepartmentId, parentDept))));
					} else if (field.equalsIgnoreCase(Constants.PERMISSION)) {

						List<Permission> permissionList = new ArrayList<Permission>();
						List<String> permissionListLong = new ArrayList<String>();
						String[] strIds = whereClauses
								.get(Constants.PERMISSION).split(",");

						if (strIds != null && strIds.length > 1) {
							LOGGER.info("more than one permissions...");
							for (String stId : strIds) {
								Permission permission = new Permission();
								permission.setId(stId);
								permissionList.add(permission);
							}
							for (String stId : strIds) {
								permissionListLong.add(stId);
							}
						} else {
							Permission permission = new Permission();
							permission.setId(whereClauses
									.get(Constants.PERMISSION));
							permissionList.add(permission);

							permissionListLong.add(whereClauses
									.get(Constants.PERMISSION));
						}
						Join<User, Permission> join = from.join("permissions",
								JoinType.LEFT);
						Subquery<Permission> subquery = userCriteriaQuery
								.subquery(Permission.class);
						Root<Permission> fromPermission = subquery
								.from(Permission.class);
						subquery.select(fromPermission);
						subquery.where(builder.in(join.get(Constants.ID))
								.value(permissionListLong));
						predicateList.add(builder.and(builder.in(join).value(
								subquery)));

					} else {
						predicateList.add(builder.like(
								from.<String> get(field),
								whereClauses.get(field)));
					}
				}
			}

			select.where(builder.and(predicateList
					.toArray(new Predicate[predicateList.size()])));
			select.distinct(true);

			if (orderClauses != null) {
				if (orderClauses.get(Constants.ORDER_BY) != null) {
					if (orderClauses.get(Constants.ORDER_BY).equalsIgnoreCase(
							"roleName")) {
						if (orderClauses.get(Constants.SORT_BY) != null
								&& orderClauses.get(Constants.SORT_BY)
										.equalsIgnoreCase("desc")) {
							select.orderBy(builder.desc(from
									.get(Constants.ROLE).get("name")));
						} else {
							select.orderBy(builder.asc(from.get(Constants.ROLE)
									.get("name")));

						}
					} else {
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
				} else {
					select.orderBy(builder.asc(from.get(Constants.FIRST_NAME)));
				}
			} else {
				select.orderBy(builder.asc(from.get(Constants.FIRST_NAME)));
			}

			TypedQuery<User> query = em.createQuery(select);
			if (orderClauses != null && orderClauses.get("offset") != null
					&& orderClauses.get("limit") != null) {
				query.setFirstResult(Integer.parseInt(orderClauses
						.get("offset")));
				query.setMaxResults(Integer.parseInt(orderClauses.get("limit")));
			}

			LOGGER.info("SQL:: " + query.toString());
			ret = query.getResultList();
			if (ret != null) {
				Iterator<User> userList = ret.iterator();
				while (userList.hasNext()) {
					User userDB = userList.next();
					Hibernate.initialize(userDB.getDepartments());

				}
			}
		} catch (Exception e) {
//			LOGGER.error(Constants.EXCEPTION, e);
//			throw new ArgusException(e.getMessage(), e);
		}
		return ret;
	}

	@Override
	public int changeStatus(long id, boolean status)  {
		Query query = em.createQuery(CHANGE_STATUS);

		query.setParameter("status", status);
		query.setParameter("modifiedBy", AkpmsUtil.getLoggedInUser());
		query.setParameter("modifiedOn", new Date());
		query.setParameter("id", id);

		LOGGER.info("SQL:" + query.toString());
		return query.executeUpdate();
	}
	@Override
	public int deleteUser(Long id) {
		Query query = em.createQuery(DELETE_STATUS_SINGLE);

		query.setParameter("modifiedBy", AkpmsUtil.getLoggedInUser());
		query.setParameter("modifiedOn", new Date());
		query.setParameter("id", id);

		LOGGER.info("SQL:" + query.toString());
		return query.executeUpdate();
	}

	@Override
	public int totalRecord(Map<String, String> whereClauses)
		{

		int ret = 0;
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Long> cQuery = builder.createQuery(Long.class);
			Root<User> from = cQuery.from(User.class);
			CriteriaQuery<Long> select = cQuery.select(builder.count(from));

			List<Predicate> predicateList = new ArrayList<Predicate>();
			predicateList.add(builder.and(builder.equal(
					from.<String> get(Constants.DELETED), false)));

			// if (whereClauses != null && whereClauses.size() > Constants.ZERO
			// && whereClauses.get(Constants.SELECTED_ROLES_IDS) != null) {
			predicateList.add(builder.and(builder.notEqual(
					from.get(Constants.ROLE).get(Constants.ID),
					Constants.ADMIN_ROLE_ID)));
			// } else {
			// Predicate pRole = builder.or(builder.notEqual(
			// from.get(Constants.ROLE).get(Constants.ID),
			// Constants.ADMIN_ROLE_ID), builder.isNull(from.get(
			// Constants.ROLE).get(Constants.ID)));
			//
			// predicateList.add(builder.and(pRole));
			// }

			if (whereClauses != null && whereClauses.size() > Constants.ZERO) {
				Set<String> key = whereClauses.keySet();
				for (String field : key) {
					if (field
							.equalsIgnoreCase(Constants.SELECTED_DEPARTMENTS_IDS)) {
						LOGGER.info("found departments = "
								+ whereClauses
										.get(Constants.SELECTED_DEPARTMENTS_IDS));

						if (whereClauses
								.get(Constants.SELECTED_DEPARTMENTS_IDS) != null) {
							List<Department> deptList = new ArrayList<Department>();
							List<Long> deptListLong = new ArrayList<Long>();

							String[] strIds = whereClauses.get(
									Constants.SELECTED_DEPARTMENTS_IDS).split(
									",");
							if (strIds != null && strIds.length > Constants.ONE) {
								LOGGER.info(Constants.MORE_THAN_ONE_DEPT_AVAILABLE);
								for (String stId : strIds) {
									Department dept = new Department();
									dept.setId((int) Long.parseLong(stId));
									deptList.add(dept);
								}

								for (String stId : strIds) {
									deptListLong.add(Long.parseLong(stId));
								}

							} else {
								Department dept = new Department();
								dept.setId((int) Long.parseLong(whereClauses
										.get(Constants.SELECTED_DEPARTMENTS_IDS)));
								deptList.add(dept);
								deptListLong
										.add(Long.parseLong(whereClauses
												.get(Constants.SELECTED_DEPARTMENTS_IDS)));
							}

							predicateList.add(builder.and(builder.in(
									from.join("departments").get(Constants.ID))
									.value(deptListLong)));
						}
					} else if (field
							.equalsIgnoreCase(Constants.SELECTED_ROLES_IDS)) {
						if (whereClauses.get(Constants.SELECTED_ROLES_IDS) != null) {
							List<Role> roleList = new ArrayList<Role>();
							String[] strIds = whereClauses.get(
									Constants.SELECTED_ROLES_IDS).split(",");
							if (strIds != null && strIds.length > Constants.ONE) {
								LOGGER.info(Constants.MORE_THAN_ONE_DEPT_AVAILABLE);
								for (String stId : strIds) {
									Role role = new Role();
									role.setId(Long.parseLong(stId));
									roleList.add(role);
								}
							} else {
								Role role = new Role();
								role.setId(Long.parseLong(whereClauses
										.get(Constants.SELECTED_ROLES_IDS)));
								roleList.add(role);
							}
							predicateList.add(builder.and(builder.in(
									from.get(Constants.ROLE)).value(roleList)));
						}
					} else if (field.equalsIgnoreCase(Constants.STATUS)) {
						boolean status;
						if (Integer.parseInt(whereClauses.get(field)) == Constants.ONE) {
							status = true;
						} else {
							status = false;
						}
						predicateList.add(builder.and(builder.equal(
								from.<String> get(Constants.STATUS), status)));
					}

					else if (field.equalsIgnoreCase(Constants.KEYWORD)) {
						LOGGER.info("found search text value  = "
								+ whereClauses.get(field));
						Predicate p = builder.or(builder.like(
								from.<String> get(Constants.FIRST_NAME), "%"
										+ whereClauses.get(field) + "%"),
								builder.like(from.<String> get("lastName"), "%"
										+ whereClauses.get(field) + "%"),
								builder.like(from.<String> get("contact"), "%"
										+ whereClauses.get(field) + "%"),
								builder.like(from.<String> get("email"), "%"
										+ whereClauses.get(field) + "%"));
						predicateList.add(builder.and(p));
					} else {
						predicateList.add(builder.like(
								from.<String> get(field),
								whereClauses.get(field)));
					}
				}
			}

			select.where(builder.and(predicateList
					.toArray(new Predicate[predicateList.size()])));
			select.distinct(true);
			TypedQuery<Long> query = em.createQuery(select);

			ret = query.getSingleResult().intValue();
		} catch (Exception e) {
//			LOGGER.error(Constants.EXCEPTION, e);
//			throw new ArgusException(e.getMessage(), e);
		}
		LOGGER.info("count = " + ret);
		return ret;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getUserStats() {
		List<Object[]> listObject = new ArrayList<Object[]>();
		Query query = em.createNativeQuery(USERS_STATS_NATIVE);
		listObject = query.getResultList();
		return listObject;
	}

	@Override
	public void insertLoginDetails(Long userid) {
		StringBuilder queryString = new StringBuilder();
		queryString
				.append("Insert into login_logs(user_id,last_login) values(:user_id,now())");
		Query query = em.createNativeQuery(queryString.toString());
		query.setParameter("user_id", userid);

		query.executeUpdate();
	}

	@Override
	public String getLastLoginDetails(Long userId)  {
		String lastLoginTime = null;
		try {
			String queryString = "SELECT last_login FROM login_logs where user_id= :user_id order by last_login desc limit 1";
			Query query = em.createNativeQuery(queryString);
			query.setParameter("user_id", userId);
			Date lastLogin = (Date) query.getSingleResult();
			LOGGER.info("lastLogin = " + lastLogin);
			SimpleDateFormat inFormatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			lastLoginTime = inFormatter.format(lastLogin);
			return lastLoginTime;
		} catch (Exception e) {
//			LOGGER.error("Exception Reason : " + e.getMessage());
//			LOGGER.error(Constants.EXCEPTION, e);
			return null;
		}
	}

	@Override
	public List<User> getUserByDept()  {
		List<User> userList = null;
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<User> userCriteriaQuery = builder.createQuery(User.class);
		Root<User> from = userCriteriaQuery.from(User.class);
		CriteriaQuery<User> select = userCriteriaQuery.select(from);

		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.add(builder.and(builder.equal(
				from.<String> get(Constants.DELETED), Constants.NON_DELETED)));
		predicateList.add(builder.and(builder.equal(
				from.<String> get(Constants.STATUS), Constants.ONE)));
		predicateList.add(builder.and(builder.equal(
				from.join(Constants.DEPARTMENTS).get(Constants.ID),
				Constants.TWO)));

		userCriteriaQuery.select(from).orderBy(
				builder.asc(from.get(Constants.FIRST_NAME)));

		select.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<User> query = em.createQuery(select);
		userList = query.getResultList();

		return userList;
	}

	public List<User> getUserByParentDeptIdAndSubDeptById(long parentDeptId,
			long subDeptId) {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<User> userCriteriaQuery = builder.createQuery(User.class);
		Root<User> from = userCriteriaQuery.from(User.class);
		CriteriaQuery<User> select = userCriteriaQuery.select(from);

		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.add(builder.and(builder.equal(
				from.<String> get(Constants.DELETED), Constants.NON_DELETED)));
		predicateList.add(builder.and(builder.equal(
				from.<String> get(Constants.STATUS), Constants.ONE)));
		predicateList.add(builder.and(builder.equal(
				from.join(Constants.DEPARTMENTS).get(Constants.ID),
				parentDeptId), (builder.equal(from.join(Constants.DEPARTMENTS)
				.get(Constants.ID), subDeptId))));
		select.where(predicateList.toArray(new Predicate[predicateList.size()]));
		select.distinct(true);
		select.orderBy(builder.asc(from.get(Constants.FIRST_NAME)));
		TypedQuery<User> query = em.createQuery(select);

		return query.getResultList();
	}

	public List<User> getUserByDeptId(long deptId) {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<User> userCriteriaQuery = builder.createQuery(User.class);
		Root<User> from = userCriteriaQuery.from(User.class);
		CriteriaQuery<User> select = userCriteriaQuery.select(from);

		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.add(builder.and(builder.equal(
				from.<String> get(Constants.DELETED), Constants.NON_DELETED)));
		predicateList.add(builder.and(builder.equal(
				from.<String> get(Constants.STATUS), Constants.ONE)));
		predicateList.add(builder.and(builder.equal(
				from.join(Constants.DEPARTMENTS).get(Constants.ID), deptId)));
		select.where(predicateList.toArray(new Predicate[predicateList.size()]));
		select.distinct(true);
		select.orderBy(builder.asc(from.get(Constants.FIRST_NAME)));

		TypedQuery<User> query = em.createQuery(select);

		return query.getResultList();
	}

	@Override
	public List<User> getCredentialingAccountingDeptUser()
		 {
		List<User> userList = null;
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<User> userCriteriaQuery = builder.createQuery(User.class);
		Root<User> from = userCriteriaQuery.from(User.class);
		CriteriaQuery<User> select = userCriteriaQuery.select(from);

		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.add(builder.and(builder.equal(
				from.<String> get(Constants.DELETED), Constants.NON_DELETED)));
		predicateList.add(builder.and(builder.equal(
				from.<String> get(Constants.STATUS), Constants.ONE)));
		predicateList.add(builder.or(builder.equal(
				from.join(Constants.DEPARTMENTS).get(Constants.ID),
				Constants.FOUR),
				(builder.equal(
						from.join(Constants.DEPARTMENTS).get(Constants.ID),
						Constants.FIVE))));
		select.where(predicateList.toArray(new Predicate[predicateList.size()]));
		select.distinct(true);
		TypedQuery<User> query = em.createQuery(select);
		userList = query.getResultList();

		return userList;
	}

	@Override
	public List<User> getOffsetUser()  {
		List<User> userList = null;
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<User> userCriteriaQuery = builder.createQuery(User.class);
		Root<User> from = userCriteriaQuery.from(User.class);
		CriteriaQuery<User> select = userCriteriaQuery.select(from);

		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.add(builder.and(builder.equal(
				from.<String> get(Constants.DELETED), Constants.NON_DELETED)));
		predicateList.add(builder.and(builder.equal(
				from.<String> get(Constants.STATUS), Constants.ONE)));
		predicateList.add(builder.and(builder.equal(
				from.join(Constants.DEPARTMENTS).get(Constants.ID),
				Constants.TWO)));
		predicateList.add(builder.and(builder.equal(
				from.join(Constants.PERMISSION).get(Constants.ID),
				OFFSET_MANAGER)));
		select.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<User> query = em.createQuery(select);
		userList = query.getResultList();

		return userList;
	}

	@Override
	public int changePassword(Long id, String newPassword)
			 {
		Query query = em.createQuery(CHANGE_PASSWORD);

		query.setParameter("password", newPassword);
		query.setParameter("id", id);

		LOGGER.info("SQL:" + query.toString());
		return query.executeUpdate();

	}

}
