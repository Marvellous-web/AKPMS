package argus.repo.processManual;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import argus.domain.Files;
import argus.domain.ProcessManual;
import argus.exception.ArgusException;
import argus.util.AkpmsUtil;
import argus.util.Constants;

@Repository
@Transactional
public class ProcessManualDaoImpl implements ProcessManualDao {

	private static final Logger LOGGER = Logger
			.getLogger(ProcessManualDaoImpl.class);

	@Autowired
	private EntityManager em;

	private static final String DELETE_MULTI_STATUS = " UPDATE ProcessManual as pm SET pm.modifiedBy = :modifiedBy, pm.modifiedOn = :modifiedOn, pm.deleted = "
			+ Constants.IS_DELETED + " WHERE pm.id IN :ids";

	private static final String DELETE_SINGLE_STATUS = "UPDATE ProcessManual as pm SET pm.modifiedBy = :modifiedBy, pm.modifiedOn = :modifiedOn, pm.deleted = "
			+ Constants.IS_DELETED + " WHERE pm.id = :id or pm.parent.id = :parent_id";

	private static final String CHANGE_STATUS_INACTIVE = "UPDATE ProcessManual as pm SET pm.modifiedBy = :modifiedBy, pm.modifiedOn = :modifiedOn, modificationSummary = null, pm.status = "
			+ Constants.INACTIVATE
			+ " WHERE pm.id= :id or pm.parent.id = :parent_id";

	private static final String CHANGE_STATUS_ACTIVE = "UPDATE ProcessManual as pm SET pm.modifiedBy = :modifiedBy, pm.modifiedOn = :modifiedOn, modificationSummary = null, pm.status = "
			+ Constants.ACTIVATE + " WHERE pm.id= :id ";

	public ProcessManual getProcessManualById(long processManualId,
			boolean loadDependancies, Boolean activeOnly) throws ArgusException {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<ProcessManual> criteria = builder
				.createQuery(ProcessManual.class);

		Root<ProcessManual> from = criteria.from(ProcessManual.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();

		predicateList.add(builder.and(builder.equal(from.get(Constants.DELETED),
				Constants.NON_DELETED)));

		predicateList.add(builder.and(builder.equal(from.get(Constants.ID),
				processManualId)));

		if (activeOnly) {
			predicateList.add(builder.and(builder.equal(from.get(Constants.STATUS),
					Constants.BOOLEAN_ACTIVATE)));
		}

		criteria.select(from)
				.where(predicateList.toArray(new Predicate[predicateList.size()]))
				.orderBy(builder.asc(from.get("position")));

		TypedQuery<ProcessManual> query = em.createQuery(criteria);

		ProcessManual processManual = query.getSingleResult();
		if (processManual != null && loadDependancies) {

			List<Predicate> subPredicateList = new ArrayList<Predicate>();
			subPredicateList.add(builder.and(builder.equal(from.get(Constants.DELETED),
					Constants.NON_DELETED)));
			subPredicateList.add(builder.and(builder.equal(from.get("parent")
					.get(Constants.ID), processManual.getId())));

			if (activeOnly) {
				subPredicateList.add(builder.and(builder.equal(
						from.get(Constants.STATUS), Constants.BOOLEAN_ACTIVATE)));
			}
			criteria.select(from).where(
					subPredicateList.toArray(new Predicate[subPredicateList
							.size()])).orderBy(builder.asc(from.get("position")));

			List<ProcessManual> subProcessManualList = em.createQuery(criteria)
					.getResultList();
			processManual.setProcessManualList(subProcessManualList);
			Hibernate.initialize(processManual.getUserList());
			for (ProcessManual subProcessManual : processManual
					.getProcessManualList()) {
				Hibernate.initialize(subProcessManual.getFiles());
				Hibernate.initialize(subProcessManual.getUserList());
			}
			Hibernate.initialize(processManual.getDepartments());
			Hibernate.initialize(processManual.getFiles());
		}

		return processManual;
	}

	public ProcessManual getProcessManualById(long processManualId,
			List<String> loadDependancies) throws ArgusException {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<ProcessManual> criteria = builder
				.createQuery(ProcessManual.class);
		Root<ProcessManual> from = criteria.from(ProcessManual.class);

		criteria.select(from)
				.where(builder.and(builder.equal(from.get(Constants.ID),
						processManualId), builder.equal(from.get(Constants.DELETED),
						Constants.NON_DELETED)))
				.orderBy(builder.asc(from.get("position")));

		TypedQuery<ProcessManual> query = em.createQuery(criteria);

		ProcessManual processManual = query.getSingleResult();
		if (processManual != null && loadDependancies != null
				&& !loadDependancies.isEmpty()) {
			for (String dependency : loadDependancies) {
				if (dependency
						.equalsIgnoreCase(Constants.DEPENDANCY_DEPARTMENTS)) {
					Hibernate.initialize(processManual.getDepartments());
				} else if (dependency
						.equalsIgnoreCase(Constants.DEPENDANCY_USERS)) {
					Hibernate.initialize(processManual.getUserList());
				} else if (dependency
						.equalsIgnoreCase(Constants.DEPENDANCY_FILES)) {
					Hibernate.initialize(processManual.getFiles());
				} else if (dependency
						.equalsIgnoreCase(Constants.DEPENDANCY_SUB_PROCESS_MANUALS)) {
					Hibernate.initialize(processManual.getProcessManualList());

					for (ProcessManual subProcessManual : processManual
							.getProcessManualList()) {
						Hibernate.initialize(subProcessManual.getFiles());
						Hibernate.initialize(subProcessManual.getUserList());
					}
				}
			}
		}
		return processManual;
	}

	@Override
	public List<ProcessManual> getAllProcessManuals(Boolean activeOnly,
			boolean loadDependancies) throws ArgusException {

		LOGGER.info("in list manual : model");

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<ProcessManual> criteria = builder
				.createQuery(ProcessManual.class);

		Root<ProcessManual> from = criteria.from(ProcessManual.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.add(builder.and(builder.equal(from.get(Constants.DELETED),
				Constants.NON_DELETED)));

		predicateList.add(builder.and(builder.isNull(from.get("parent").get(
				Constants.ID))));

		if (activeOnly) {
			predicateList.add(builder.and(builder.equal(from.get(Constants.STATUS),
					Constants.BOOLEAN_ACTIVATE)));
		}
		criteria.select(from).where(
predicateList.toArray(new Predicate[predicateList.size()]))
				.orderBy(builder.asc(from.get(Constants.ID)));
		List<ProcessManual> processManualList = em.createQuery(criteria)
				.getResultList();

		if (processManualList != null) {
			for (ProcessManual processManual : processManualList) {
				List<Predicate> subPredicateList = new ArrayList<Predicate>();
				subPredicateList.add(builder.and(builder.equal(
						from.get(Constants.DELETED), Constants.NON_DELETED)));
				subPredicateList.add(builder.and(builder.equal(
						from.get("parent").get(Constants.ID),
						processManual.getId())));

				if (activeOnly) {
					subPredicateList.add(builder.and(builder.equal(
						from.get(Constants.STATUS), Constants.BOOLEAN_ACTIVATE)));
				}

				criteria.select(from).where(
						subPredicateList.toArray(new Predicate[subPredicateList
								.size()])).orderBy(builder.asc(from.get("position")));
				List<ProcessManual> subProcessManualList = em.createQuery(
						criteria).getResultList();
				if(subProcessManualList != null && !subProcessManualList.isEmpty())
				{
					for (ProcessManual subProcessManual : subProcessManualList)
					{
						Hibernate.initialize(subProcessManual.getUserList());

					}
				}
				processManual.setProcessManualList(subProcessManualList);
				Hibernate.initialize(processManual.getUserList());
				Hibernate.initialize(processManual.getDepartments());
			}
		}

		LOGGER.info("result count:" + processManualList.size());
		return processManualList;
	}

	@Override
	public List<ProcessManual> getAllProcessManuals(Boolean activeOnly,
			boolean loadDependancies, String keyword) throws ArgusException {
		LOGGER.info("in search manual : model");
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<ProcessManual> criteria = builder
				.createQuery(ProcessManual.class);

		Root<ProcessManual> from = criteria.from(ProcessManual.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.add(builder.and(builder.equal(from.get(Constants.DELETED),
				Constants.NON_DELETED)));
		if (activeOnly) {
			predicateList.add(builder.and(builder.equal(from.get(Constants.STATUS),
					Constants.BOOLEAN_ACTIVATE)));
		}
		if (keyword != null) {
			Predicate p = builder.or(
					builder.like(from.<String> get("title"), "%" + keyword
							+ "%"),
					builder.like(from.<String> get("content"), "%" + keyword
							+ "%"));
			predicateList.add(builder.and(p));
		}
		criteria.select(from).where(
				predicateList.toArray(new Predicate[predicateList.size()]));
		List<ProcessManual> processManualList = em.createQuery(criteria)
				.getResultList();

		LOGGER.info("result count:" + processManualList.size());
		return processManualList;
	}

	@Override
	public void saveProcessManual(ProcessManual processManual) throws ArgusException {
		em.persist(processManual);
	}

	@Override
	public void updateProcessManual(ProcessManual processManual)
			throws ArgusException {
		em.merge(processManual);
	}


	@Override
	public int deleteProcessManuals(List<Long> ids) throws ArgusException {
		Query query = em.createQuery(DELETE_MULTI_STATUS);

		query.setParameter("modifiedBy", AkpmsUtil.getLoggedInUser());
		query.setParameter("modifiedOn", new Date());

		query.setParameter("ids", ids);
		return query.executeUpdate();
	}

	@Override
	public int updateProcessManualsPositons(Double position, long parentId) throws ArgusException {
		
		Query query = em.createNativeQuery("update process_manual as pm set pm.position = (pm.position+1) where pm.parent_id = "+parentId+" and pm.position > "+position);
		return query.executeUpdate();	
	}
	
	@Override
	public int deleteProcessManuals(Long id) throws ArgusException {
		Query query = em.createQuery(DELETE_SINGLE_STATUS);

		query.setParameter(Constants.ID, id);
		query.setParameter("parent_id", id);
		query.setParameter("modifiedBy", AkpmsUtil.getLoggedInUser());
		query.setParameter("modifiedOn", new Date());

		LOGGER.info("SQL:" + query.toString());

		return query.executeUpdate();
	}

	@Override
	public int changeStatus(long id, boolean status) throws ArgusException {
		LOGGER.info("in change status, process manual impl");
		if (!status) {
			Query query = em.createQuery(CHANGE_STATUS_INACTIVE);

			query.setParameter(Constants.ID, id);
			query.setParameter("parent_id", id);
			query.setParameter("modifiedBy", AkpmsUtil.getLoggedInUser());
			query.setParameter("modifiedOn", new Date());

			return query.executeUpdate();
		} else {
			Query query = em.createQuery(CHANGE_STATUS_ACTIVE);

			query.setParameter(Constants.ID, id);
			query.setParameter("modifiedBy", AkpmsUtil.getLoggedInUser());
			query.setParameter("modifiedOn", new Date());

			return query.executeUpdate();
		}
	}

	public void saveAttachement(Files file) throws ArgusException {
		em.persist(file);
	}

	public Files getAttachedFile(Long id) throws ArgusException {

		return em.find(Files.class, id);
	}

	public Long getTotalProcessManuals() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> cQuery = builder.createQuery(Long.class);
		Root<ProcessManual> from = cQuery.from(ProcessManual.class);
		CriteriaQuery<Long> select = cQuery.select(builder.count(from));
		select.where(builder.and(
				builder.equal(from.get(Constants.STATUS), Constants.ACTIVATE),
				builder.equal(from.get(Constants.DELETED), Constants.NON_DELETED)));
		return em.createQuery(select).getSingleResult();

	}

	public Long getTotalProcessManualReadByUser(Long userId) {
		StringBuilder sb = new StringBuilder();
		sb.append(" Select count(*) from process_manual_user_rel ");
		sb.append(" left join process_manual pm on pm.id = process_manual_user_rel.process_manual_id ");
		sb.append(" where user_id=? and pm.is_deleted = "
				+ Constants.NON_DELETED + " and pm.status =  "
				+ Constants.ACTIVATE);
		Query query = em.createNativeQuery(sb.toString());
		query.setParameter(1, userId);
		return (Long.parseLong(query.getSingleResult().toString()));
	}

	public ProcessManual findByName(String name) throws ArgusException {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<ProcessManual> criteria = builder
				.createQuery(ProcessManual.class);

		Root<ProcessManual> processManual = criteria.from(ProcessManual.class);

		criteria.select(processManual).where(
				builder.equal(processManual.get("title"), name),
				builder.equal(processManual.get(Constants.DELETED),
						Constants.BOOLEAN_NON_DELETED));

		return em.createQuery(criteria).getSingleResult();
	}
	public int deleteAttachedFile(Long id) throws ArgusException
	{
		Query query = em.createQuery("UPDATE  Files  SET deleted = true WHERE id = "+id);
		return query.executeUpdate();
	}

	@Override
	public List<Object[]> getProcessManualByDepartments(List<Long> departmentIds)
			throws ArgusException {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		sb.append(" SELECT COUNT(*),parent_id from process_manual where process_manual.parent_id IN ");
		sb.append(" ( ");
		sb.append(" select distinct(process_manual.id) from process_manual ");
		sb.append(" left join process_manual_department_rel on process_manual.id = process_manual_department_rel.process_manual_id ");
		sb.append(" where process_manual_department_rel.department_id IN ");
		sb.append(" ( ");
		if (departmentIds.isEmpty()) {
			sb.append(" 0 ");
		} else {
		for (Long id : departmentIds) {
			if (i == 0) {
				sb.append(id);
			} else {
				sb.append(" , " + id);
			}
			i++;
		}
		}
		sb.append(" ) ");
		sb.append(" AND process_manual.parent_id is null and process_manual.is_deleted = 0 and process_manual.`status`=1) ");
		sb.append(" and process_manual.is_deleted = 0 and process_manual.`status`=1 group by process_manual.parent_id ");
		sb.append(" WITH ROLLUP ");
		Query query = em.createNativeQuery(sb.toString());
		return query.getResultList();
	}
}
