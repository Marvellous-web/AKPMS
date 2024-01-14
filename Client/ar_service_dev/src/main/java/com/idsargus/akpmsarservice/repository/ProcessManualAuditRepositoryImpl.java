package com.idsargus.akpmsarservice.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.idsargus.akpmscommonservice.entity.ProcessManualAudit;

import lombok.extern.slf4j.Slf4j;

@Repository
@Transactional
@Slf4j
public class ProcessManualAuditRepositoryImpl implements ProcessManualAuditRepository{

//	private static final Logger LOGGER = Logger
//			.getLogger(ProcessManualDaoImpl.class);

	@Autowired
	private EntityManager em;

	/* (non-Javadoc)
	 * @see argus.repo.processManual.ProcessManualAuditDao#getProcessManualModificationById(long)
	 */
	@Override
	public List<ProcessManualAudit> getProcessManualModificationById(
			long processManualId) throws Exception {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<ProcessManualAudit> criteria = builder
				.createQuery(ProcessManualAudit.class);
		Root<ProcessManualAudit> from = criteria.from(ProcessManualAudit.class);

		criteria.select(from).where(
				builder.and(builder.equal(from.get("processManualId"), processManualId),
						builder.equal(from.get("deleted"), 0),builder.isNotNull(from.get("modificationSummary")),builder.notEqual(from.get("modificationSummary"),""))).orderBy(builder.asc(from.get("id")));

		TypedQuery<ProcessManualAudit> query = em.createQuery(criteria);

		List<ProcessManualAudit> processManualAuditList = query.getResultList();

		log.info("RECORD_COUNT: "+ processManualAuditList.size());

		return processManualAuditList;
	}
	
	
}
