package argus.repo.moneysource;

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

import argus.domain.MoneySource;
import argus.util.Constants;

@Repository
@Transactional
public class MoneySourceDaoImpl implements MoneySourceDao {

	@Autowired
	private EntityManager em;

	@Override
	public List<MoneySource> findAll(boolean activeOnly) {

		CriteriaBuilder builder = em.getCriteriaBuilder();

		CriteriaQuery<MoneySource> moneySourceCriteriaQuery = builder
				.createQuery(MoneySource.class);

		Root<MoneySource> from = moneySourceCriteriaQuery
				.from(MoneySource.class);

		CriteriaQuery<MoneySource> select = moneySourceCriteriaQuery
				.select(from);

		List<Predicate> predicateList = new ArrayList<Predicate>();

		if (activeOnly) {
			predicateList.add(builder.and(builder.equal(
					from.get(Constants.STATUS), Constants.ACTIVATE)));
		}

		select.where(predicateList.toArray(new Predicate[predicateList.size()]));

		TypedQuery<MoneySource> query = em.createQuery(select);

		return query.getResultList();

	}

}
