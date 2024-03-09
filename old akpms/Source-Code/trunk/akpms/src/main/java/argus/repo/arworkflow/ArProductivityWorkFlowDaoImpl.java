package argus.repo.arworkflow;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import argus.domain.ArProductivityWorkFlow;
import argus.util.Constants;

@Repository
@Transactional
public class ArProductivityWorkFlowDaoImpl implements ArProductivityWorkFlowDao {

	@Autowired
	private EntityManager em;

	@Override
	public List<ArProductivityWorkFlow> findAll(String orderBy) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ArProductivityWorkFlow> criteria = cb
				.createQuery(ArProductivityWorkFlow.class);
		Root<ArProductivityWorkFlow> arProdWorkFlow = criteria
				.from(ArProductivityWorkFlow.class);

		criteria.select(arProdWorkFlow).where(
				cb.equal(arProdWorkFlow.get("status"), true));

		if (null == orderBy) {
			criteria.select(arProdWorkFlow).orderBy(
					cb.asc(arProdWorkFlow.get(Constants.BY_NAME)));
		} else {
			criteria.select(arProdWorkFlow).orderBy(
					cb.asc(arProdWorkFlow.get(orderBy)));
		}
		return em.createQuery(criteria).getResultList();
	}

	@Override
	public ArProductivityWorkFlow findById(String id) {
		return em.find(ArProductivityWorkFlow.class, id);
	}

}
