package argus.repo.productivity.workflow.codingcorrectionlog;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import argus.domain.CodingCorrectionLogWorkFlow;
import argus.domain.Files;
import argus.exception.ArgusException;
import argus.repo.productivity.helper.ArProductivityDaoHelper;
import argus.util.AkpmsUtil;
import argus.util.Constants;

@Repository
@Transactional
public class CodingCorrectionLogDaoImpl implements CodingCorrectionLogDao {

	private static final Log LOGGER = LogFactory
			.getLog(CodingCorrectionLogDaoImpl.class);

	@Autowired
	private EntityManager em;

	@Override
	public CodingCorrectionLogWorkFlow findById(Long id) throws ArgusException {
		CodingCorrectionLogWorkFlow codingCorrectionLogWorkFlow = em.find(
				CodingCorrectionLogWorkFlow.class, id);

		return codingCorrectionLogWorkFlow;
	}

	@Override
	public void addCodingCorrectionLogWorkFlow(
			CodingCorrectionLogWorkFlow codingCorrectionLogWorkFlow)
			throws ArgusException {
		em.persist(codingCorrectionLogWorkFlow);
		LOGGER.info("CodingCorrectionLogWorkFlow Added successfully");

	}

	@Override
	public void updateCodingCorrectionLogWorkFlow(
			CodingCorrectionLogWorkFlow codingCorrectionLogWorkFlow)
			throws ArgusException {
		em.merge(codingCorrectionLogWorkFlow);
		LOGGER.info("CodingCorrectionLogWorkFlow Updated successfully");

	}

	@Override
	public CodingCorrectionLogWorkFlow findByProductivityId(Long arProdId)
			throws ArgusException {

		LOGGER.info("int [findByProductivityId] : arProdId = " + arProdId);
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<CodingCorrectionLogWorkFlow> criteria = builder
				.createQuery(CodingCorrectionLogWorkFlow.class);
		Root<CodingCorrectionLogWorkFlow> codingCorrectionLogWorkFlow = criteria
				.from(CodingCorrectionLogWorkFlow.class);
		criteria.select(codingCorrectionLogWorkFlow).where(
				builder.equal(codingCorrectionLogWorkFlow.get("arProductivity")
						.get("id"), arProdId));
		return em.createQuery(criteria).getSingleResult();

	}

	@Override
	public int totalRecord(Map<String, String> whereClauses)
			throws ArgusException {
		LOGGER.info("in [totalRecord] method :");
		int totalRecords = 0;
		StringBuilder queryString = new StringBuilder();
		queryString
				.append("SELECT COUNT(*) FROM CodingCorrectionLogWorkFlow AS d ");
		if (whereClauses != null && whereClauses.size() > Constants.ZERO) {
			queryString.append(" WHERE d.deleted = 0 ");
			Set<String> key = whereClauses.keySet();
			for (String field : key) {
				if (field.equalsIgnoreCase(Constants.KEYWORD)) {
					LOGGER.info("found search text value  = "
							+ whereClauses.get(field));
					queryString.append(" d.patientName LIKE '%"
							+ whereClauses.get(Constants.KEYWORD) + "%' ");
				} else if (field.equalsIgnoreCase("dosFrom")) {
					queryString.append(" AND d.arProductivity.dos >= '"
							+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
									.akpmsNewDateFormat(whereClauses
											.get("dosFrom")),
									Constants.MYSQL_DATE_FORMAT) + "'");
				} else if (field.equalsIgnoreCase("dosTo")) {
					queryString.append(" AND d.arProductivity.dos <= '"
							+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
									.akpmsNewDateFormat(whereClauses
											.get("dosTo")),
									Constants.MYSQL_DATE_FORMAT) + "'");
				} else {
					queryString.append(" AND d." + field);
					queryString.append(" LIKE '%" + whereClauses.get(field)
							+ "%' ");
				}
			}
		}
		TypedQuery<Long> query = em.createQuery(queryString.toString(),
				Long.class);
		totalRecords = query.getSingleResult().intValue();
		LOGGER.info("count = " + totalRecords);
		return totalRecords;
	}

	@Override
	public List<CodingCorrectionLogWorkFlow> findAll(
			Map<String, String> orderClauses, Map<String, String> whereClauses,
			boolean dependancies) throws ArgusException {
		LOGGER.info("in [findAll(Map<String, String> orderClauses,"
				+ " Map<String, String> whereClauses, boolean dependancies)] method :");
		List<CodingCorrectionLogWorkFlow> codingCorrectionLogWorkFlowList = null;

		StringBuilder queryString = new StringBuilder();

		queryString.append("SELECT d FROM CodingCorrectionLogWorkFlow AS d ");

		queryString.append(ArProductivityDaoHelper.getQueryFindAll(
				orderClauses, whereClauses).toString());

		TypedQuery<CodingCorrectionLogWorkFlow> query = em.createQuery(
				queryString.toString(), CodingCorrectionLogWorkFlow.class);
		if (orderClauses != null && orderClauses.get(Constants.OFFSET) != null
				&& orderClauses.get(Constants.LIMIT) != null) {
			query.setFirstResult(Integer.parseInt(orderClauses
					.get(Constants.OFFSET)));
			query.setMaxResults(Integer.parseInt(orderClauses
					.get(Constants.LIMIT)));
		}

		LOGGER.info("SQL:: " + queryString.toString());

		codingCorrectionLogWorkFlowList = query.getResultList();

		if (dependancies && codingCorrectionLogWorkFlowList != null
				&& codingCorrectionLogWorkFlowList.size() > Constants.ZERO) {
			Iterator<CodingCorrectionLogWorkFlow> checkTracerIterator = codingCorrectionLogWorkFlowList
					.iterator();

			while (checkTracerIterator.hasNext()) {
				CodingCorrectionLogWorkFlow checkTracerWorkFlow = checkTracerIterator
						.next();
				Hibernate.initialize(checkTracerWorkFlow.getArProductivity());

			}
		}

		return codingCorrectionLogWorkFlowList;
	}

	public void saveAttachement(Files file) throws ArgusException {
		em.persist(file);
	}

	public void updateAttachement(Files file) throws ArgusException {
		em.merge(file);
	}

}
