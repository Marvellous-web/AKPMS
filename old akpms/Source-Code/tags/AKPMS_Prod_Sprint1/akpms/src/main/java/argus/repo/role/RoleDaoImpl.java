package argus.repo.role;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import argus.domain.Role;

@Repository
@Transactional
public class RoleDaoImpl implements RoleDao {

	@Autowired
	private EntityManager em;


	@Override
	public Role findById(Long id) {
		return em.find(Role.class, id);
	}

	@Override
	public Role findByName(String name) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Role> criteria = builder
				.createQuery(Role.class);
		Root<Role> role = criteria.from(Role.class);

		criteria.select(role).where(
				builder.equal(role.get("name"), name));
		return em.createQuery(criteria).getSingleResult();
	}

	@Override
	public List<Role> findAll(String orderBy) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Role> criteria = cb.createQuery(Role.class);
		Root<Role> role = criteria.from(Role.class);
		CriteriaQuery<Role> criteriaSelect  = criteria.select(role) ;
		criteriaSelect.where(cb.notEqual(role.get("id"), 1));
		if (null != orderBy) {
			criteriaSelect.orderBy(cb.asc(role.get(orderBy)));
		} else {
			criteriaSelect.orderBy(cb.asc(role.get("name")));
		}
		return em.createQuery(criteria).getResultList();
	}

	@Override
	public void addRole(Role role) {
		em.persist(role);
		return;

	}



}
