package argus.repo.taskname;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import argus.domain.TaskName;

@Service
@Transactional
public class TaskNameDaoImpl implements TaskNameDao {

	private static final Log LOGGER = LogFactory.getLog(TaskNameDaoImpl.class);

	@Autowired
	private EntityManager em;

	@Override
	/**
	 *This method will return all the task names
	 */
	public List<TaskName> findAll() {
		LOGGER.info("[findAll]");
		CriteriaBuilder builder = em.getCriteriaBuilder();

		CriteriaQuery<TaskName> taskNameCriteriaQuery = builder
				.createQuery(TaskName.class);

		Root<TaskName> from = taskNameCriteriaQuery.from(TaskName.class);

		CriteriaQuery<TaskName> select = taskNameCriteriaQuery
				.select(from);


		TypedQuery<TaskName> query = em.createQuery(select);

		return query.getResultList();

	}

}
