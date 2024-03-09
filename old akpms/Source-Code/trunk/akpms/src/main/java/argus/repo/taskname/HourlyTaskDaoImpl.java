package argus.repo.taskname;

import java.util.ArrayList;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import argus.domain.HourlyTask;
import argus.exception.ArgusException;
import argus.util.Constants;

@Service
@Transactional
public class HourlyTaskDaoImpl implements HourlyTaskDao {

	private static final Logger LOGGER = Logger.getLogger(HourlyTaskDaoImpl.class);

	private static final String CHANGESTATUS = "UPDATE HourlyTask as i SET i.status = ? WHERE i.id= ?";

	@Autowired
	private EntityManager em;

	@Override
	/**
	 *This method will return all the task names
	 */
	public List<HourlyTask> findAll(
			Map<String, String> orderClauses, Map<String, String> whereClauses, boolean dependency) {
		LOGGER.info("[findAll]");
		CriteriaBuilder builder = em.getCriteriaBuilder();

		CriteriaQuery<HourlyTask> taskNameCriteriaQuery = builder
				.createQuery(HourlyTask.class);

		Root<HourlyTask> from = taskNameCriteriaQuery.from(HourlyTask.class);

		CriteriaQuery<HourlyTask> select = taskNameCriteriaQuery
				.select(from);

		List<Predicate> predicateList = new ArrayList<Predicate>();

		if (whereClauses != null && whereClauses.size() > 0) {
			Set<String> key = whereClauses.keySet();
			for (String field : key) {

				if (field.equalsIgnoreCase(Constants.KEYWORD)) {
					predicateList.add(builder.and(builder.like(
							from.<String> get("name"), "%"
									+ whereClauses.get(field) + "%")));
				}

			}
		}

		select.where(builder.and(predicateList
				.toArray(new Predicate[predicateList.size()])));
		select.distinct(true);

		if (orderClauses != null) {
			if (orderClauses.get(Constants.ORDER_BY) != null) {
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
		}


		TypedQuery<HourlyTask> query = em.createQuery(select);

		if (orderClauses != null && orderClauses.get("offset") != null
				&& orderClauses.get("limit") != null) {
			query.setFirstResult(Integer.parseInt(orderClauses.get("offset")));
			query.setMaxResults(Integer.parseInt(orderClauses.get("limit")));
		}
		List<HourlyTask> hourlyTasks = query.getResultList();
		if (dependency) {
			for (HourlyTask hourlyTask : hourlyTasks) {
				Hibernate.initialize(hourlyTask.getDepartment());
			}
		}	
		return hourlyTasks;

	}

	@Override
	public HourlyTask findById(Long id) throws ArgusException {
		return em.find(HourlyTask.class, id);
		}

	@Override
	public HourlyTask findByName(String name) throws ArgusException {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<HourlyTask> criteria = builder
				.createQuery(HourlyTask.class);
		Root<HourlyTask> hourlyTask = criteria.from(HourlyTask.class);
		criteria.select(hourlyTask).where(
				builder.equal(hourlyTask.get("name"), name));
		return em.createQuery(criteria).getSingleResult();
	}

	@Override
	public void addHourlyTask(HourlyTask hourlyTask) throws ArgusException {
		em.persist(hourlyTask);
		return;
	}

	@Override
	public int totalRecord(Map<String, String> whereClauses)
			throws ArgusException {
		int ret = 0;
		StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT COUNT(*) FROM HourlyTask AS i ");
		queryString.append(" WHERE i.deleted = 0 ");

		if (whereClauses != null && whereClauses.size() > 0) {
			Set<String> key = whereClauses.keySet();
			for (String field : key) {

				if (field.equalsIgnoreCase(Constants.STATUS)) {
					LOGGER.info("found search text value  = "
							+ whereClauses.get(field));
					queryString.append(" AND i.status LIKE '%"
							+ whereClauses.get(Constants.STATUS) + "%' ");
				} else {
					queryString.append(" AND i.name");
					queryString.append(" LIKE '%" + whereClauses.get(field)
							+ "%' ");
				}
			}
		}

		try {
			TypedQuery<Long> query = em.createQuery(queryString.toString(),
					Long.class);
			ret = query.getSingleResult().intValue();
		} catch (Exception e) {
			LOGGER.error("Exception = ", e);
		}
		LOGGER.info("count = " + ret);
		return ret;
	}

	@Override
	public void updateHourlyTask(HourlyTask hourlyTask) throws ArgusException {
		em.merge(hourlyTask);
		return;
	}

	@Override
	public int changeStatus(long id, boolean status) throws ArgusException {
		Query query = em.createQuery(CHANGESTATUS);
		query.setParameter(1, status);
		query.setParameter(2, id);

		// return updatedRowCount
		return query.executeUpdate();
	}

}
