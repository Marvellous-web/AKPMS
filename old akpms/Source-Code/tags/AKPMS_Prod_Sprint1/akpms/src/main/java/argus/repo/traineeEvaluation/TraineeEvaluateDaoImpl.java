/**
 *
 */
package argus.repo.traineeEvaluation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import argus.domain.Department;
import argus.domain.Role;
import argus.domain.TraineeEvaluate;
import argus.domain.User;
import argus.exception.ArgusException;
import argus.util.Constants;

/**
 * @author sumit.v
 *
 */

@Repository
@Transactional
public class TraineeEvaluateDaoImpl implements TraineeEvaluateDao {

	private static final Log LOGGER = LogFactory
			.getLog(TraineeEvaluateDaoImpl.class);

	@Autowired
	private EntityManager em;

	/*
	 * (non-Javadoc)
	 *
	 * @see argus.repo.traineeEvaluation.TraineeEvaluateDao#getEvaluationList()
	 */

	@Override
	public List<TraineeEvaluate> getEvaluationList(Long traineeid, String type)
			throws ArgusException {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<TraineeEvaluate> criteria = cb
				.createQuery(TraineeEvaluate.class);
		Root<TraineeEvaluate> traineeEvaluate = criteria
				.from(TraineeEvaluate.class);
		CriteriaQuery<TraineeEvaluate> criteriaSelect = criteria
				.select(traineeEvaluate);
		criteriaSelect.where(cb.and(
				cb.equal(traineeEvaluate.get("traineeId"), traineeid),
				cb.equal(traineeEvaluate.get("type"), type)));
		criteriaSelect.orderBy(cb.desc(traineeEvaluate.get("modifiedOn")));
		return em.createQuery(criteria).getResultList();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * argus.repo.traineeEvaluation.TraineeEvaluateDao#getCurrentEvaluation()
	 */
	@Override
	public TraineeEvaluate getCurrentEvaluation(Long traineeId, Long ratedById)
			throws ArgusException {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<TraineeEvaluate> criteria = cb
				.createQuery(TraineeEvaluate.class);
		Root<TraineeEvaluate> traineeEvaluate = criteria
				.from(TraineeEvaluate.class);
		CriteriaQuery<TraineeEvaluate> criteriaSelect = criteria
				.select(traineeEvaluate);
		criteriaSelect.where(cb.and(cb.equal(traineeEvaluate.get("traineeId"),
				traineeId), cb.equal(
				traineeEvaluate.get("ratedBy").get(Constants.ID), ratedById)));
		TraineeEvaluate traineeEvaluatuion = em.createQuery(criteriaSelect)
				.getSingleResult();

		if (traineeEvaluatuion != null) {
			Hibernate.initialize(traineeEvaluatuion.getRatedBy());
		}
		return traineeEvaluatuion;

	}

	@Override
	public void updateTraineeEvaluation(TraineeEvaluate traineeEvaluate)
			throws ArgusException {
		em.merge(traineeEvaluate);

	}

	@Override
	public void addTraineeEvaluation(TraineeEvaluate traineeEvaluate)
			throws ArgusException {
		em.persist(traineeEvaluate);

	}

	@SuppressWarnings("unchecked")
	public List<Object[]> findAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses) throws ArgusException {

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT u.first_name, u.last_name, u.id, DATE_FORMAT(te.modified_on, '%m-%d-%Y') as modified, ifNull( evaluator.first_name,'') as evaluator,  "
				+ " ifNull( GROUP_CONCAT(d.name),'') ,ifNull(tempargus.avgScore,0.0) as argusavg,ifNull(tempargus.evaluationCount,0) as arguscount,"
				+ " ifNull((tempargus.avgScore/5)*100,0.0) as argusper,ifNull(tempidsargus.avgScore,0.0) as idsargusavg, "
				+ " ifNull(tempidsargus.evaluationCount,0) as idsarguscount,ifNull((tempidsargus.avgScore/5)*100,0.0) as idsargusper, "
				+ " ifNull(processManualRead.totalProcessManualRead,0) FROM user u ");
		sb.append(" left join user_dept_rel udr on u.id = udr.user_id "
				+ " left join department d on udr.dept_id = d.id ");
		sb.append(" left join ( Select trainee_id, rated_by,max(trainee_evaluate.modified_on) as maxModified,count(rated_by) as evaluationCount,"
				+ " sum(rating) as evaluationScore,(sum(rating)/count(rated_by) ) as avgScore from trainee_evaluate where type='Argus_TL'"
				+ " group by trainee_evaluate.trainee_Id ) as tempargus on u.id = tempargus.trainee_id ");
		sb.append(" left join ( Select trainee_id, rated_by,max(trainee_evaluate.modified_on) as maxModified,count(rated_by) as evaluationCount,"
				+ " sum(rating) as evaluationScore,(sum(rating)/count(rated_by) ) as avgScore from trainee_evaluate where type='IDS_TL'"
				+ " group by trainee_evaluate.trainee_Id ) as tempidsargus on u.id = tempidsargus.trainee_id ");
		sb.append(" left join trainee_evaluate te on u.id = te.trainee_id and te.modified_on="
				+ "(Select max(trainee_evaluate.modified_on) from trainee_evaluate where trainee_evaluate.trainee_Id=u.id)");
		sb.append(" left join user evaluator on te.rated_by = evaluator.id ");
		sb.append("left join (select count(pmur.process_manual_id) as totalProcessManualRead, user_id	from process_manual_user_rel  pmur left join process_manual pm on pmur.process_manual_id = pm.id where pm.`status`  = "
				+ Constants.ACTIVATE
				+ " and pm.is_deleted ="
				+ Constants.NON_DELETED
				+ " group by user_id) "
				+ "as processManualRead on processManualRead.user_id = u.id");
		sb.append(" where u.role_id = " + Constants.TRAINEE_ROLE_ID
				+ " and u.is_deleted = " + Constants.NON_DELETED
				+ " and u.status =  " + Constants.ACTIVATE);

		if (whereClauses != null && whereClauses.size() > 0) {
			Set<String> key = whereClauses.keySet();
			for (String field : key) {
				if (field.equalsIgnoreCase(Constants.SELECTED_DEPARTMENTS_IDS)) {
					sb.append(" and d.id in(" + whereClauses.get(field) + ") ");
				} else if (field.equalsIgnoreCase(Constants.KEYWORD)) {
					sb.append(" and (");
					sb.append(" u.first_name like '%" + whereClauses.get(field)
							+ "%'");
					sb.append(" or u.last_name like '%"
							+ whereClauses.get(field) + "%'");
					sb.append(" or u.email like '%" + whereClauses.get(field)
							+ "%'");
					sb.append(" or u.contact like '%" + whereClauses.get(field)
							+ "%'");
					sb.append(")");
				}
			}
		}
		sb.append(" group by u.id");

		LOGGER.info(sb.toString());
		return em.createNativeQuery(sb.toString()).getResultList();
	}

	public int totalRecord(Map<String, String> whereClauses)
			throws ArgusException {
		int ret = 0;
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Long> cQuery = builder.createQuery(Long.class);
			Root<User> from = cQuery.from(User.class);
			CriteriaQuery<Long> select = cQuery.select(builder.count(from));

			List<Predicate> predicateList = new ArrayList<Predicate>();
			predicateList.add(builder.and(builder.equal(
					from.<String> get("deleted"), false)));
			predicateList.add(builder.and(builder.notEqual(from.get("role")
					.get("id"), Constants.ADMIN_ROLE_ID)));

			if (whereClauses != null && whereClauses.size() > 0) {
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
								LOGGER.info("more than 1 dept available");
								for (String stId : strIds) {
									Department dept = new Department();
									dept.setId(Long.parseLong(stId));
									deptList.add(dept);
								}

								for (String stId : strIds) {
									deptListLong.add(Long.parseLong(stId));
								}

							} else {
								Department dept = new Department();
								dept.setId(Long.parseLong(whereClauses
										.get(Constants.SELECTED_DEPARTMENTS_IDS)));
								deptList.add(dept);
								deptListLong
										.add(Long.parseLong(whereClauses
												.get(Constants.SELECTED_DEPARTMENTS_IDS)));
							}

							predicateList.add(builder.and(builder.in(
									from.join("departments").get("id")).value(
									deptListLong)));
						}
					} else if (field
							.equalsIgnoreCase(Constants.SELECTED_ROLES_IDS)) {
						if (whereClauses.get(Constants.SELECTED_ROLES_IDS) != null) {
							List<Role> roleList = new ArrayList<Role>();
							String[] strIds = whereClauses.get(
									Constants.SELECTED_ROLES_IDS).split(",");
							if (strIds != null && strIds.length > 1) {
								LOGGER.info("more than 1 dept available");
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
									from.get("role")).value(roleList)));
						}
					} else if (field.equalsIgnoreCase(Constants.KEYWORD)) {
						LOGGER.info("found search text value  = "
								+ whereClauses.get(field));
						Predicate p = builder.or(builder.like(
								from.<String> get("firstName"), "%"
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
			LOGGER.error("Exception = ", e);
		}
		LOGGER.info("count = " + ret);
		return ret;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> findEvaluationReportTrainees(Long id)
			throws ArgusException {

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT u.first_name, u.last_name, u.id, "
				+ " ifNull(tempargus.avgScore,0.0) as argusavg,ifNull(tempargus.evaluationCount,0) as arguscount,"
				+ " ifNull((tempargus.avgScore/5)*100,0.0) as argusper,ifNull(tempidsargus.avgScore,0.0) as idsargusavg, "
				+ " ifNull(tempidsargus.evaluationCount,0) as idsarguscount,ifNull((tempidsargus.avgScore/5)*100,0.0) as idsargusper, "
				+ " ifNull(processManualRead.totalProcessManualRead,0) FROM user u ");
		sb.append(" left join ( Select trainee_id, rated_by,max(trainee_evaluate.modified_on) as maxModified,count(rated_by) as evaluationCount,"
				+ " sum(rating) as evaluationScore,(sum(rating)/count(rated_by) ) as avgScore from trainee_evaluate where type='Argus_TL'"
				+ " group by trainee_evaluate.trainee_Id ) as tempargus on u.id = tempargus.trainee_id ");
		sb.append(" left join ( Select trainee_id, rated_by,max(trainee_evaluate.modified_on) as maxModified,count(rated_by) as evaluationCount,"
				+ " sum(rating) as evaluationScore,(sum(rating)/count(rated_by) ) as avgScore from trainee_evaluate where type='IDS_TL'"
				+ " group by trainee_evaluate.trainee_Id ) as tempidsargus on u.id = tempidsargus.trainee_id ");
		sb.append("left join (select count(pmur.process_manual_id) as totalProcessManualRead, user_id	from process_manual_user_rel  pmur left join process_manual pm on pmur.process_manual_id = pm.id where pm.`status`  = "
				+ Constants.ACTIVATE
				+ " and pm.is_deleted ="
				+ Constants.NON_DELETED
				+ " group by user_id) "
				+ "as processManualRead on processManualRead.user_id = u.id");
		sb.append(" where u.role_id = " + Constants.TRAINEE_ROLE_ID
				+ " and u.is_deleted = " + Constants.NON_DELETED
				+ " and u.status =  " + Constants.ACTIVATE);
		if (id != 0) {
			sb.append(" and u.id=" + id);
		}
		sb.append(" group by u.id");

		return em.createNativeQuery(sb.toString()).getResultList();
	}
}
