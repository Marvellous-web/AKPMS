package argus.repo.adminincome;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import argus.domain.AdminIncome;
import argus.util.Constants;

@Repository
@Transactional
public class AdminIncomeDaoImpl implements AdminIncomeDao {

	@Autowired
	private EntityManager em;

	public List<AdminIncome> findAll(boolean activeOnly) {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<AdminIncome> adminIncomeCriteriaQuery = builder
				.createQuery(AdminIncome.class);
		Root<AdminIncome> from = adminIncomeCriteriaQuery
				.from(AdminIncome.class);
		CriteriaQuery<AdminIncome> select = adminIncomeCriteriaQuery
				.select(from);

		List<Predicate> predicateList = new ArrayList<Predicate>();
		
		if (activeOnly) {
			predicateList.add(builder.and(builder.equal(
					from.get(Constants.STATUS), Constants.ACTIVATE)));
		}
		
		select.where(predicateList.toArray(new Predicate[predicateList.size()]));

		TypedQuery<AdminIncome> query = em.createQuery(select);

		return query.getResultList();

	}

}
