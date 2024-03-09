package argus.repo.permission;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import argus.domain.Permission;
import argus.util.Constants;

@Repository
@Transactional
public class PermissionDaoImpl implements PermissionDao {
	private static final Log LOGGER = LogFactory
			.getLog(PermissionDaoImpl.class);

	private static final String GET_BY_IDS = "SELECT p FROM Permission as p WHERE p.id IN :ids";

	@Autowired
	private EntityManager em;

	@Override
	public Permission findById(String id) {
		return em.find(Permission.class, id);
	}

	@Override
	public Permission findByName(String name) {
		LOGGER.info("in [findByName] name: " + name);
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Permission> criteria = builder
				.createQuery(Permission.class);
		Root<Permission> permission = criteria.from(Permission.class);

		criteria.select(permission).where(
				builder.equal(permission.get(Constants.BY_NAME), name));
		return em.createQuery(criteria).getSingleResult();
	}

	@Override
	public List<Permission> findAll(String orderBy) {
		LOGGER.info("in [findAll] orderBy: " + orderBy);
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Permission> criteria = cb.createQuery(Permission.class);
		Root<Permission> permission = criteria.from(Permission.class);

		if (null == orderBy) {
			criteria.select(permission).orderBy(
					cb.asc(permission.get(Constants.BY_NAME)));
		} else {
			criteria.select(permission)
					.orderBy(cb.asc(permission.get(orderBy)));
		}
		return em.createQuery(criteria).getResultList();
	}

	@Override
	public void addPermission(Permission permission) {
		LOGGER.info("in [addPermission] permission: " + permission.toString());
		em.persist(permission);
		return;
	}

	@SuppressWarnings("unchecked")
	public List<Permission> getPermissionByIds(List<String> ids)
			throws Exception {
		Query query = em.createQuery(GET_BY_IDS, Permission.class);
		query.setParameter("ids", ids);
		LOGGER.info("" + query.toString());
		return query.getResultList();
	}
}
