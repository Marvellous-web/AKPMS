//package com.idsargus.akpmsarservice.repository.productivity.workflow.codingcorrectionlog;
//
//import argus.domain.CodingCorrectionLogWorkFlow;
//import argus.domain.Files;
//import argus.exception.ArgusException;
//import argus.repo.productivity.helper.ArProductivityDaoHelper;
//import argus.util.AkpmsUtil;
//import argus.util.Constants;
//import org.apache.log4j.Logger;
//import org.hibernate.Hibernate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import javax.persistence.Query;
//import javax.persistence.TypedQuery;
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Root;
//import java.util.*;
//
//@Repository
//@Transactional
//public class CodingCorrectionLogDaoImpl implements CodingCorrectionLogDao {
//
//	private static final Logger LOGGER = Logger
//			.getLogger(CodingCorrectionLogDaoImpl.class);
//
//	@Autowired
//	private EntityManager em;
//
//	@Override
//	public CodingCorrectionLogWorkFlow findById(Long id) throws ArgusException {
//		CodingCorrectionLogWorkFlow codingCorrectionLogWorkFlow = em.find(
//				CodingCorrectionLogWorkFlow.class, id);
//
//		return codingCorrectionLogWorkFlow;
//	}
//
//	@Override
//	public void addCodingCorrectionLogWorkFlow(
//			CodingCorrectionLogWorkFlow codingCorrectionLogWorkFlow)
//			throws ArgusException {
//		em.persist(codingCorrectionLogWorkFlow);
//		LOGGER.info("CodingCorrectionLogWorkFlow Added successfully");
//
//	}
//
//	@Override
//	public void updateCodingCorrectionLogWorkFlow(
//			CodingCorrectionLogWorkFlow codingCorrectionLogWorkFlow)
//			throws ArgusException {
//		em.merge(codingCorrectionLogWorkFlow);
//		LOGGER.info("CodingCorrectionLogWorkFlow Updated successfully");
//
//	}
//
//	@Override
//	public CodingCorrectionLogWorkFlow findByProductivityId(Long arProdId)
//			throws ArgusException {
//
//		LOGGER.info("int [findByProductivityId] : arProdId = " + arProdId);
//		CriteriaBuilder builder = em.getCriteriaBuilder();
//		CriteriaQuery<CodingCorrectionLogWorkFlow> criteria = builder
//				.createQuery(CodingCorrectionLogWorkFlow.class);
//		Root<CodingCorrectionLogWorkFlow> codingCorrectionLogWorkFlow = criteria
//				.from(CodingCorrectionLogWorkFlow.class);
//		criteria.select(codingCorrectionLogWorkFlow).where(
//				builder.equal(codingCorrectionLogWorkFlow.get("arProductivity")
//						.get("id"), arProdId));
//		return em.createQuery(criteria).getSingleResult();
//	}
//
//	@Override
//	public int totalRecord(Map<String, String> whereClauses)
//			throws ArgusException {
//		LOGGER.info("in [totalRecord] method ");
//		int totalRecords = 0;
//		StringBuilder queryString = new StringBuilder();
//		queryString
//				.append("SELECT COUNT(*) FROM CodingCorrectionLogWorkFlow AS d ");
//		queryString.append(" WHERE d.deleted = " + Constants.NON_DELETED);
//
//		queryString.append(getWhereClause(whereClauses));
//
//		LOGGER.info("queryString:: " + queryString);
//		TypedQuery<Long> query = em.createQuery(queryString.toString(),
//				Long.class);
//		totalRecords = query.getSingleResult().intValue();
//		LOGGER.info("out totalRecord , count = " + totalRecords);
//		return totalRecords;
//	}
//
//	private StringBuilder getWhereClause(Map<String, String> whereClauses) {
//		LOGGER.info("in getWhereClause");
//		StringBuilder queryString = new StringBuilder();
//
//		if (whereClauses != null && whereClauses.size() > Constants.ZERO) {
//			Set<String> key = whereClauses.keySet();
//			for (String field : key) {
//				if (field.equalsIgnoreCase(Constants.AR_PROD_PATIENT_NAME)) {
//					LOGGER.info(Constants.AR_PROD_PATIENT_NAME + " = "
//							+ whereClauses.get(field));
//					queryString
//							.append(" AND d.arProductivity.patientName LIKE '%"
//									+ whereClauses
//											.get(Constants.AR_PROD_PATIENT_NAME)
//									+ "%' ");
//				} else if (field
//						.equalsIgnoreCase(Constants.AR_PROD_PATIENT_ACC_NO)) {
//					LOGGER.info(Constants.AR_PROD_PATIENT_ACC_NO + " = "
//							+ whereClauses.get(field));
//					queryString
//							.append(" AND d.arProductivity.patientAccNo LIKE '%"
//									+ whereClauses
//											.get(Constants.AR_PROD_PATIENT_ACC_NO)
//									+ "%' ");
//				} else if (field.equalsIgnoreCase(Constants.DATE_CREATED_FROM)) {
//					queryString.append(" AND d.arProductivity.createdOn >= '"
//							+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
//									.akpmsNewDateFormat(whereClauses
//											.get(Constants.DATE_CREATED_FROM)),
//									Constants.MYSQL_DATE_FORMAT) + "'");
//				} else if (field.equalsIgnoreCase(Constants.DATE_CREATED_TO)) {
//					queryString.append(" AND d.arProductivity.createdOn <= '"
//							+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
//									.getFormattedDate(whereClauses
//											.get(Constants.DATE_CREATED_TO)),
//									Constants.MYSQL_DATE_FORMAT_WITH_TIME)
//							+ "'");
//				} else if (field.equalsIgnoreCase(Constants.WORKFLOW)) {
//					queryString.append(" AND d.nextAction ='"
//							+ whereClauses.get(Constants.WORKFLOW) + "' ");
//				} else if (field.equalsIgnoreCase(Constants.TEAM_ID)) {
//					queryString.append(" AND d.arProductivity.team.id ="
//							+ whereClauses.get(Constants.TEAM_ID));
//				}
//
//				// else {
//				// queryString.append(" AND cclw." + field);
//				// queryString.append(" LIKE '%" + whereClauses.get(field)
//				// + "%' ");
//				// }
//			}
//		}
//		LOGGER.info("out getWhereClause");
//		return queryString;
//	}
//
//	@Override
//	public List<CodingCorrectionLogWorkFlow> findAll(
//			Map<String, String> orderClauses, Map<String, String> whereClauses,
//			boolean dependancies) throws Exception {
//		LOGGER.info("in [findAll(Map<String, String> orderClauses,"
//				+ " Map<String, String> whereClauses, boolean dependancies)] method :");
//		List<CodingCorrectionLogWorkFlow> codingCorrectionLogWorkFlowList = null;
//
//		StringBuilder queryString = new StringBuilder();
//
//		queryString.append("SELECT d FROM CodingCorrectionLogWorkFlow AS d ");
//		queryString.append(" WHERE d.deleted = " + Constants.NON_DELETED);
//		// queryString
//		// .append(ArProductivityDaoHelper.getWhereClause(whereClauses));
//		queryString.append(getWhereClause(whereClauses));
//		queryString
//				.append(ArProductivityDaoHelper.getOrderClause(orderClauses));
//		// queryString.append(ArProductivityDaoHelper.getQueryFindAll(
//		// orderClauses, whereClauses).toString());
//
//		TypedQuery<CodingCorrectionLogWorkFlow> query = em.createQuery(
//				queryString.toString(), CodingCorrectionLogWorkFlow.class);
//		if (orderClauses != null && orderClauses.get(Constants.OFFSET) != null
//				&& orderClauses.get(Constants.LIMIT) != null) {
//			query.setFirstResult(Integer.parseInt(orderClauses
//					.get(Constants.OFFSET)));
//			query.setMaxResults(Integer.parseInt(orderClauses
//					.get(Constants.LIMIT)));
//		}
//
//		LOGGER.info("SQL:: " + queryString.toString());
//
//		codingCorrectionLogWorkFlowList = query.getResultList();
//
//		if (dependancies && codingCorrectionLogWorkFlowList != null
//				&& codingCorrectionLogWorkFlowList.size() > Constants.ZERO) {
//			Iterator<CodingCorrectionLogWorkFlow> codingCorrectionIterator = codingCorrectionLogWorkFlowList
//					.iterator();
//			LOGGER.info("in dependancies");
//			while (codingCorrectionIterator.hasNext()) {
//				CodingCorrectionLogWorkFlow codingCorrectionWorkFlow = codingCorrectionIterator
//						.next();
//				LOGGER.info("in dependancies loop : "
//						+ codingCorrectionWorkFlow.getId());
//				Hibernate.initialize(codingCorrectionWorkFlow
//						.getArProductivity());
//			}
//		}
//
//		return codingCorrectionLogWorkFlowList;
//	}
//
//	public void saveAttachement(Files file) throws ArgusException {
//		em.persist(file);
//	}
//
//	public void updateAttachement(Files file) throws ArgusException {
//		em.merge(file);
//	}
//
//	@SuppressWarnings("unchecked")
//	public List<Object[]> countByWorkFlowStatus() throws ArgusException {
//		StringBuilder queryString = new StringBuilder();
//		queryString
//				.append("select workflow_status,count(1) from coding_correction_log_workflow workflow group by workflow.workflow_status ");
//		List<Object[]> listObject = new ArrayList<Object[]>();
//		Query query = em.createNativeQuery(queryString.toString());
//		listObject = query.getResultList();
//		return listObject;
//
//	}
//
//}
