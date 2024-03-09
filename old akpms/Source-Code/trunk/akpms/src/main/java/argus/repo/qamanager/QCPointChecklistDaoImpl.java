package argus.repo.qamanager;

import java.util.ArrayList;
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

import argus.domain.QCPointChecklist;
import argus.exception.ArgusException;
import argus.util.AkpmsUtil;
import argus.util.Constants;

@Repository
@Transactional(readOnly = true)
public class QCPointChecklistDaoImpl implements QCPointChecklistDao {

	private static final Logger LOGGER = Logger
			.getLogger(QCPointChecklistDaoImpl.class);

	@Autowired
	private EntityManager em;

	@Override
	public QCPointChecklist findById(Long id) throws ArgusException {
		return em.find(QCPointChecklist.class, id);
	}

	@Override
	public List<QCPointChecklist> findAll(Map<String, String> whereClause,
			Map<String, String> orderByClause, boolean dependencies)
			throws ArgusException {
		List<QCPointChecklist> qcPointCheckLists = null;
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("SELECT qcPointCheckList FROM QCPointChecklist as qcPointChecklist WHERE 1 = 1 ");

		queryString.append(this.getWhereClause(whereClause));
		queryString.append(this.getOrderByClasue(orderByClause));
		LOGGER.debug("findAll query for QCPointChecklist :: " + queryString);
		try {

			TypedQuery<QCPointChecklist> qcPointChecklistQuery = em
					.createQuery(queryString.toString(), QCPointChecklist.class);

			if (orderByClause != null) {
				Set<String> keySet = orderByClause.keySet();
				for (String field : keySet) {
					if (field.equalsIgnoreCase(Constants.OFFSET)) {
						qcPointChecklistQuery.setFirstResult(Integer
								.parseInt(orderByClause.get(Constants.OFFSET)));
					}
					if (field.equalsIgnoreCase(Constants.LIMIT)) {
						qcPointChecklistQuery.setMaxResults(Integer
								.parseInt(orderByClause.get(Constants.LIMIT)));
					}
				}
			}
			qcPointCheckLists = qcPointChecklistQuery.getResultList();

			if (dependencies) {
				for (QCPointChecklist qcPointChecklist : qcPointCheckLists) {
					Hibernate.initialize(qcPointChecklist.getUser());
					Hibernate.initialize(qcPointChecklist.getQcPoint());
				}
			}

		} catch (Exception e) {
			LOGGER.error("Error getting QCPointChecklist ", e);
		}

		return qcPointCheckLists;
	}

	private StringBuffer getWhereClause(Map<String, String> whereClause) {
		StringBuffer queryString = new StringBuffer();

		if (whereClause != null && whereClause.size() > 0) {
			LOGGER.info("QCPointChecklist whereClause size : "
					+ whereClause.size());
			Set<String> keySet = whereClause.keySet();
			for (String field : keySet) {

				if (field.equalsIgnoreCase(Constants.QC_POINT_CHECKPOINT)) {
					queryString.append(" AND ");
					queryString.append("qcPointChecklist."
							+ Constants.QC_POINT_CHECKPOINT + " = "
							+ whereClause.get(Constants.QC_POINT_CHECKPOINT));
				} else if (field
						.equalsIgnoreCase(Constants.QA_WORKSHEET_SAMPLE_ID)) {
					queryString.append(" AND ");
					queryString
							.append("qcPointChecklist.qaProductivitySampling.id = "
									+ whereClause
											.get(Constants.QA_WORKSHEET_SAMPLE_ID));
				} else if (field.equalsIgnoreCase(Constants.QA_PATIENT_INFO_ID)) {
					queryString.append(" AND ");
					queryString
							.append("qcPointChecklist.qaWorksheetPatientInfo.id = "
									+ whereClause
											.get(Constants.QA_PATIENT_INFO_ID));
				} else if (field.equalsIgnoreCase(Constants.DEPARTMENT)) { // departmentId
																			// of
																			// productivities
					if (whereClause.get(Constants.DEPARTMENT).equalsIgnoreCase(
							Constants.AR_DEPARTMENT_ID)) {

						LOGGER.info("found DEPARTMENT_ID = "
								+ whereClause.get(Constants.DEPARTMENT));
						queryString
								.append(" AND qcPointChecklist.qaProductivitySampling.qaWorksheet.department.id = "
										+ whereClause.get(Constants.DEPARTMENT));
					} else {
						LOGGER.info("found DEPARTMENT_ID = "
								+ whereClause.get(Constants.DEPARTMENT));
						queryString
								.append(" AND qcPointChecklist.qaWorksheetPatientInfo.qaProductivitySampling.qaWorksheet.department.id = "
										+ whereClause.get(Constants.DEPARTMENT));
					}
				}
				// QaWorksheet subDepartment
				else if (field.equalsIgnoreCase(Constants.SUB_DEPARTMENT)) {
					if (whereClause.get(Constants.DEPARTMENT).equalsIgnoreCase(
							Constants.AR_DEPARTMENT_ID)) {

						LOGGER.info("found SUB_DEPARTMENT = "
								+ whereClause.get(Constants.SUB_DEPARTMENT));
						queryString
								.append(" AND qcPointChecklist.qaProductivitySampling.qaWorksheet.subDepartment.id = "
										+ whereClause
												.get(Constants.SUB_DEPARTMENT));
					} else {
						LOGGER.info("found DEPARTMENT_ID = "
								+ whereClause.get(Constants.SUB_DEPARTMENT));
						queryString
								.append(" AND qcPointChecklist.qaWorksheetPatientInfo.qaProductivitySampling.qaWorksheet.subDepartment.id = "
										+ whereClause
												.get(Constants.SUB_DEPARTMENT));
					}

				}
				// QaWorksheet month on which qaWorksheet is created
				else if (field.equalsIgnoreCase(Constants.MONTH)) {
					if (whereClause.get(Constants.DEPARTMENT).equalsIgnoreCase(
							Constants.AR_DEPARTMENT_ID)) {
						queryString
								.append(" AND MONTH(qcPointChecklist.qaProductivitySampling.qaWorksheet.createdOn) = "
										+ whereClause.get(Constants.MONTH));
					} else {
						queryString
								.append(" AND MONTH(qcPointChecklist.qaWorksheetPatientInfo.qaProductivitySampling.qaWorksheet.createdOn) = "
										+ whereClause.get(Constants.MONTH));
					}
				}
				// QAWorksheet year on which qaWorksheet is created
				else if (field.equalsIgnoreCase(Constants.YEAR)) {
					if (whereClause.get(Constants.DEPARTMENT).equalsIgnoreCase(
							Constants.AR_DEPARTMENT_ID)) {
						queryString
								.append(" AND YEAR(qcPointChecklist.qaProductivitySampling.qaWorksheet.createdOn) = "
										+ whereClause.get(Constants.YEAR));
					} else {
						queryString
								.append(" AND YEAR(qcPointChecklist.qaWorksheetPatientInfo.qaProductivitySampling.qaWorksheet.createdOn) = "
										+ whereClause.get(Constants.YEAR));
					}
				}

			}

			LOGGER.info("QCPointChecklist whreClase content : " + queryString);
		}

		return queryString;
	}

	private StringBuffer getOrderByClasue(Map<String, String> orderByClause) {
		StringBuffer queryString = new StringBuffer();

		if (orderByClause != null && orderByClause.size() > 0) {
			LOGGER.info("QCPointChecklist orderByClause size : "
					+ orderByClause.size());

			Set<String> keySet = orderByClause.keySet();
			for (String field : keySet) {
				if (field.equalsIgnoreCase(Constants.ORDER_BY)) {
					queryString.append(" " + Constants.ORDER_BY
							+ " qcPointChecklist."
							+ orderByClause.get(Constants.ORDER_BY));
				} else {
					queryString.append(Constants.ORDER_BY
							+ " qcPointChecklist.id");
				}
				if (field.equalsIgnoreCase(Constants.SORT_BY)) {
					queryString.append(" "
							+ orderByClause.get(Constants.SORT_BY));
				} else {
					queryString.append(" " + " ASC");
				}
			}
		}
		return queryString;
	}

	@Transactional(readOnly = false)
	@Override
	public void save(QCPointChecklist qcPointChecklist) throws ArgusException {
		em.persist(qcPointChecklist);
	}

	@Transactional(readOnly = false)
	@Override
	public void update(QCPointChecklist qcPointChecklist) throws ArgusException {
		em.merge(qcPointChecklist);
	}

	@Transactional(readOnly = false)
	@Override
	public Long delete(Long id) {
		String deleteJPQL = "DELETE FROM QCPointChecklist as qcPointChecklist WHERE qcPointChecklist.id = :id";

		Query query = em.createQuery(deleteJPQL);

		query.setParameter("id", id);

		return new Long(query.executeUpdate());
	}

	@Transactional(readOnly = false)
	@Override
	public Long delete(Long qcPointId, Long qaPatientInfoId) {
		String deleteJPQL = "DELETE FROM QCPointChecklist as qcPointChecklist WHERE qcPointChecklist.qcPoint.id = :qcPointId AND qcPointChecklist.qaWorksheetPatientInfo.id = :qaPatientInfoId";

		Query query = em.createQuery(deleteJPQL);

		query.setParameter("qcPointId", qcPointId);
		query.setParameter("qaPatientInfoId", qaPatientInfoId);

		return new Long(query.executeUpdate());
	}

	@Override
	public int totalRecord(Map<String, String> conditionMap)
			throws ArgusException {
		StringBuffer queryString = new StringBuffer();

		queryString
				.append("SELECT COUNT(*) FROM QCPointChecklist AS qcPointChecklist WHERE 1 = 1");

		if (conditionMap != null && conditionMap.size() > 0) {
			queryString.append(this.getWhereClause(conditionMap));
		}

		TypedQuery<Long> totalCountQuery = em.createQuery(
				queryString.toString(), Long.class);

		return totalCountQuery.getSingleResult().intValue();
	}

	@Override
	public Long deleteByQCPointIdAndQAProductivitySamplingId(Long qcPointId,
			Long qaProductivitySamplingId) {
		String deleteJPQL = "DELETE FROM QCPointChecklist as qcPointChecklist WHERE qcPointChecklist.qcPoint.id = :qcPointId AND qcPointChecklist.qaProductivitySampling.id = :qaProductivitySamplingId";

		Query query = em.createQuery(deleteJPQL);

		query.setParameter("qcPointId", qcPointId);
		query.setParameter("qaProductivitySamplingId", qaProductivitySamplingId);

		return new Long(query.executeUpdate());
	}

	@Override
	public List<Object[]> findMostlyErrorsDoneByUsers(
			Map<String, String> whereClauses) throws ArgusException {
		StringBuffer queryString = new StringBuffer();
		String alias = null;
		String errorSelect = null;
		String qaChecklist=null;
		List<Object[]> listObjsArr = new ArrayList<Object[]>();
		
		if (whereClauses != null && whereClauses.size() > Constants.ZERO) {
			
		
			if (whereClauses.get(Constants.DEPARTMENT).equalsIgnoreCase(
					Constants.PAYMENT_DEPARTMENT_ID)) {
				alias = "payment";
				errorSelect =" COUNT(DISTINCT qcPointCheckList.id) ";
				qaChecklist = " LEFT JOIN qaPS.paymentProductivity ";

			} else if (whereClauses.get(Constants.DEPARTMENT).equalsIgnoreCase(
					Constants.CHARGE_DEPARTMENT_ID)) {
				alias = "charge";
				errorSelect =" COUNT(DISTINCT qcPointCheckList.id) ";
				qaChecklist = " LEFT JOIN qaPS.chargeProductivity ";

			} else if (whereClauses.get(Constants.DEPARTMENT).equalsIgnoreCase(
					Constants.AR_DEPARTMENT_ID)) {
				alias = "ar";
				errorSelect ="sum(arPr.cpt), arPr.cpt as cpt  ";
				qaChecklist = " LEFT JOIN qaPS.arProductivity arPr ";
			} else if (whereClauses.get(Constants.DEPARTMENT).equalsIgnoreCase(
					Constants.ACCOUNTING_DEPARTMENT_ID)) {
				alias = "ca";
				errorSelect =" COUNT(DISTINCT qcPointCheckList.id) ";
				qaChecklist = " LEFT JOIN qaPS.credentialingAccountingProductivity ";
			}
			
			
			
			
		queryString
				.append("SELECT qcP.name, CONCAT(u.firstName, ' ', u.lastName),  "
						+ errorSelect
						+ "FROM QCPointChecklist qcPointCheckList "
						+ "LEFT JOIN qcPointCheckList.qcPoint qcP "
						+ "LEFT JOIN qcPointCheckList.user u "
						+ "LEFT JOIN qcPointCheckList.qaProductivitySampling qaPS "
						+ "LEFT JOIN qaPS.qaWorksheet qaW "
						+ qaChecklist
						+ "WHERE 1=1 ");

		if (whereClauses != null && whereClauses.size() > Constants.ZERO) {
			if (whereClauses.containsKey(Constants.DEPARTMENT)) {
				queryString.append(" AND qaW.department.id = "
						+ whereClauses.get(Constants.DEPARTMENT) + " ");
			}
			if (whereClauses.containsKey(Constants.SUB_DEPARTMENT)) {
				queryString.append(" AND qaW.subDepartment.id = "
						+ whereClauses.get(Constants.SUB_DEPARTMENT) + " ");
			}
			if (whereClauses.containsKey(Constants.QA_REPORT_FROM_DATE)) {
				queryString.append(" AND qaW.createdOn >= "
						+  "'" + AkpmsUtil.akpmsDateFormat(AkpmsUtil
								.getFormattedDate(whereClauses
										.get(Constants.QA_REPORT_FROM_DATE)),
								Constants.MYSQL_DATE_FORMAT)
						+ "'" + " ");
			}
			if (whereClauses.containsKey(Constants.QA_REPORT_TO_DATE)) {
				queryString.append(" AND qaW.createdOn <= "
						+ "'" + AkpmsUtil.akpmsDateFormat(AkpmsUtil
								.getFormattedDate(whereClauses
										.get(Constants.QA_REPORT_TO_DATE)),
								Constants.MYSQL_DATE_FORMAT_WITH_TIME)
						+ "'" + " ");
			}
			if (whereClauses.containsKey(Constants.CREATED_BY)) {
				queryString.append(" AND qaW.createdBy IN ("
						+ whereClauses.get(Constants.CREATED_BY) + ") ");
			}
		}
		queryString.append(" AND qaW.status = 2 " + "GROUP BY qcP.id, u.id "
				+ "ORDER BY u.firstName");

		LOGGER.error("Find mostly errors done by user JPQL :: " + queryString);

		Query query = em.createQuery(queryString.toString());

		//@SuppressWarnings("unchecked")
		 listObjsArr = (List<Object[]>) query.getResultList();
		}
		return listObjsArr;
	}

	@Override
	public Long deleteByQAProductivitySamplingId(Long qaProductivitySamplingId) {
		String deleteJPQL = "DELETE FROM QCPointChecklist as qcPointChecklist WHERE qcPointChecklist.qaProductivitySampling.id = :qaProductivitySamplingId";

		Query query = em.createQuery(deleteJPQL);

		query.setParameter("qaProductivitySamplingId", qaProductivitySamplingId);

		LOGGER.debug(query.toString() + " ::SQL:: " + deleteJPQL);

		return new Long(query.executeUpdate());
	}

	@Override
	public Long deleteByQAProductivitySamplingId(Long qaProductivitySamplingId,
			Long patientInfoId) {
		String deleteJPQL = "DELETE FROM QCPointChecklist as qcPointChecklist WHERE qcPointChecklist.qaProductivitySampling.id = :qaProductivitySamplingId AND qcPointChecklist.qaWorksheetPatientInfo.id = :patientInfoId";

		Query query = em.createQuery(deleteJPQL);

		query.setParameter("qaProductivitySamplingId", qaProductivitySamplingId);
		query.setParameter("patientInfoId", patientInfoId);

		LOGGER.debug(query.toString() + " ::SQL:: " + deleteJPQL);

		return new Long(query.executeUpdate());
	}

}
