package argus.repo.qamanager;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import argus.domain.QAWorksheetPatientInfo;
import argus.domain.QCPointChecklist;
import argus.exception.ArgusException;
import argus.util.Constants;

@Repository
@Transactional(readOnly = true)
public class QAWorksheetPatientInfoDaoImpl implements QAWorksheetPatientInfoDao {

	private static final Logger LOGGER = Logger
			.getLogger(QAWorksheetPatientInfoDaoImpl.class);

	@Autowired
	private EntityManager em;

	@Override
	public QAWorksheetPatientInfo findById(Long id) throws ArgusException {
		return em.find(QAWorksheetPatientInfo.class, id);
	}

	@Override
	public List<QAWorksheetPatientInfo> findAll(
			Map<String, String> whereClause, Map<String, String> orderByClause, boolean dependencies)
			throws ArgusException {
		List<QAWorksheetPatientInfo> qaWorksheetPatientInfos = null;
		StringBuffer queryString = new StringBuffer();

		queryString
				.append("SELECT patientInfo FROM QAWorksheetPatientInfo as patientInfo WHERE 1 = 1");
		queryString.append(this.getWhereClause(whereClause));
		queryString.append(this.getOrderByClause(orderByClause));
		try {
			TypedQuery<QAWorksheetPatientInfo> qaWorksheetPatientInfoQuery = em
					.createQuery(queryString.toString(),
							QAWorksheetPatientInfo.class);
			if (orderByClause != null) {
				Set <String> keySet = orderByClause.keySet();
				for (String field : keySet) {
					if (field.equalsIgnoreCase(Constants.OFFSET)) {
						qaWorksheetPatientInfoQuery.setFirstResult(Integer
								.parseInt(orderByClause.get(Constants.OFFSET)));
					}
					if (field.equalsIgnoreCase(Constants.LIMIT)) {
						qaWorksheetPatientInfoQuery.setMaxResults(Integer
								.parseInt(orderByClause.get(Constants.LIMIT)));
					}
				}
			}

			qaWorksheetPatientInfos = qaWorksheetPatientInfoQuery
					.getResultList();

			if (qaWorksheetPatientInfos != null && qaWorksheetPatientInfos.size() > 0 && dependencies) {
				for (QAWorksheetPatientInfo qaWorksheetPatientInfo : qaWorksheetPatientInfos) {
					Hibernate.initialize(qaWorksheetPatientInfo.getQaProductivitySampling());
					Hibernate.initialize(qaWorksheetPatientInfo.getQcPointChecklist());
					for (QCPointChecklist qcPointChecklist : qaWorksheetPatientInfo.getQcPointChecklist()) {
						Hibernate.initialize(qcPointChecklist.getQcPoint());
					}
				}
			}

		} catch (Exception e) {
			LOGGER.error("error getting QAWorksheetPatientInfo" + e);
		}
		return qaWorksheetPatientInfos;
	}

	private StringBuffer getWhereClause(Map<String, String> whereClauses) {
		StringBuffer queryString = new StringBuffer();

		if (whereClauses != null && whereClauses.size() > 0) {
			LOGGER.info("whereClause size is : " + whereClauses.size());
			Set<String> keySet = whereClauses.keySet();
			for (String field : keySet) {

				if (field.equalsIgnoreCase(Constants.QA_PRODUCTIVITY_SAMPLING)) {
					queryString.append(" AND ");
					queryString.append("patientInfo."
							+ Constants.QA_PRODUCTIVITY_SAMPLING
							+ ".id = "
							+ whereClauses
									.get(Constants.QA_PRODUCTIVITY_SAMPLING));
				}

			}
			LOGGER.info("Where Caluses : " + queryString);
		}

		return queryString;
	}

	private StringBuffer getOrderByClause(Map<String, String> orderByClause) {
		StringBuffer queryString = new StringBuffer();

		if (orderByClause != null && orderByClause.size() > 0) {
			LOGGER.info("order clause size : " + orderByClause.size());
			Set<String> keySet = orderByClause.keySet();
			for (String field : keySet) {
				if (field.equalsIgnoreCase(Constants.ORDER_BY)) {
					queryString.append(Constants.ORDER_BY + " patientInfo."
							+ orderByClause.get(Constants.ORDER_BY));
				} else {
					queryString.append(Constants.ORDER_BY + " patientInfo.id");
				}
				if (field.equalsIgnoreCase(Constants.SORT_BY)) {
					queryString.append(" "
							+ orderByClause.get(Constants.SORT_BY));
				} else {
					queryString.append(" ASC");
				}

			}
			LOGGER.info("orderBy clause is : " + queryString);
		}

		return queryString;
	}

	@Transactional(readOnly = false)
	@Override
	public void save(QAWorksheetPatientInfo qaWorksheetPatientInfo)
			throws ArgusException {
		em.persist(qaWorksheetPatientInfo);
	}

	@Transactional(readOnly = false)
	@Override
	public void update(QAWorksheetPatientInfo qaWorksheetPatientInfo)
			throws ArgusException {
		em.merge(qaWorksheetPatientInfo);
	}

	@Transactional(readOnly = false)
	@Override
	public Long delete(Long id) {
		String deleteJPQLChild = "DELETE FROM QCPointChecklist WHERE qaWorksheetPatientInfo.id = :id";
		Query queryChild = em.createQuery(deleteJPQLChild);
		queryChild.setParameter("id", id);
		queryChild.executeUpdate();

		String deleteJPQL = "DELETE FROM QAWorksheetPatientInfo WHERE id = :id";
		Query query = em.createQuery(deleteJPQL);
		query.setParameter("id", id);

		return (long) query.executeUpdate();
	}

	@Override
	public int totalRecord(Map<String, String> conditionMap)
			throws ArgusException {
		StringBuffer queryString = new StringBuffer();

		queryString
				.append("SELECT count(*) FROM QAWorksheetPatientInfo as patientInfo WHERE 1 = 1");

		queryString.append(this.getWhereClause(conditionMap));

		try {
			TypedQuery<Long> totalCountQuery = em.createQuery(
					queryString.toString(), Long.class);

			return totalCountQuery.getSingleResult().intValue();
		} catch (Exception e) {
			LOGGER.error("failed to get total count of QAWorksheetPatientInfo",e);
		}

		return 0;
	}

}
