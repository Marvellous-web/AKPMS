//package com.idsargus.akpmsarservice.repository.productivity.workflow.rekeyrequest;
//
//import argus.domain.RekeyRequestRecord;
//import argus.domain.RekeyRequestWorkFlow;
//import argus.exception.ArgusException;
//import argus.repo.productivity.helper.ArProductivityDaoHelper;
//import argus.util.AkpmsUtil;
//import argus.util.Constants;
//import argus.util.RekeyRequestRecordJsonData;
//import org.apache.log4j.Logger;
//import org.hibernate.Hibernate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import javax.persistence.NoResultException;
//import javax.persistence.Query;
//import javax.persistence.TypedQuery;
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Root;
//import java.util.*;
//
//@Repository
//@Transactional
//public class RekeyRequestDaoImpl implements RekeyRequestDao {
//
//	private static final Logger LOGGER = Logger
//			.getLogger(RekeyRequestDaoImpl.class);
//	@Autowired
//	private EntityManager em;
//
//	@Override
//	public RekeyRequestWorkFlow findById(Long id) throws ArgusException {
//		RekeyRequestWorkFlow rekeyRequestWorkFlow = em.find(
//				RekeyRequestWorkFlow.class, id);
//
//		return rekeyRequestWorkFlow;
//	}
//
//	@Override
//	public void addRekeyRequestWorkFlow(
//			RekeyRequestWorkFlow rekeyRequestWorkFlow) throws ArgusException {
//		em.persist(rekeyRequestWorkFlow);
//		for (RekeyRequestRecord rekeyRequestRecord : rekeyRequestWorkFlow
//				.getRekeyRequestRecords()) {
//			rekeyRequestRecord.setRekeyRequestWorkFlow(rekeyRequestWorkFlow);
//			em.persist(rekeyRequestRecord);
//		}
//		LOGGER.info("RekeyRequestWorkFlow Added successfully");
//
//	}
//
//	@Override
//	public void updateRekeyRequestWorkFlow(
//			RekeyRequestWorkFlow rekeyRequestWorkFlow) throws ArgusException {
//		em.merge(rekeyRequestWorkFlow);
//		LOGGER.info("RekeyRequestWorkFlow Updated successfully");
//
//	}
//
//	@Override
//	public RekeyRequestWorkFlow findByProductivityId(Long arProdId)
//			throws ArgusException, NoResultException {
//
//		LOGGER.info("int [findByProductivityId] : arProdId = " + arProdId);
//		CriteriaBuilder builder = em.getCriteriaBuilder();
//		CriteriaQuery<RekeyRequestWorkFlow> criteria = builder
//				.createQuery(RekeyRequestWorkFlow.class);
//		Root<RekeyRequestWorkFlow> secondRequestLogWorkFlow = criteria
//				.from(RekeyRequestWorkFlow.class);
//		criteria.select(secondRequestLogWorkFlow).where(
//				builder.equal(secondRequestLogWorkFlow.get("arProductivity")
//						.get("id"), arProdId));
//		return em.createQuery(criteria).getSingleResult();
//
//	}
//
//	public int totalRecord(Map<String, String> whereClauses)
//			throws ArgusException {
//		LOGGER.info("in [totalRecord] method :");
//		int totalRecords = 0;
//		StringBuilder queryString = new StringBuilder();
//		queryString.append("SELECT COUNT(*) FROM RekeyRequestWorkFlow AS d ");
//		queryString.append(" WHERE 1=1 ");
//
//		queryString.append(getWhereClause(whereClauses));
//
//		LOGGER.info("querystring :: " + queryString.toString());
//		TypedQuery<Long> query = em.createQuery(queryString.toString(),
//				Long.class);
//		totalRecords = query.getSingleResult().intValue();
//		LOGGER.info("count = " + totalRecords);
//		return totalRecords;
//	}
//
//	public List<RekeyRequestWorkFlow> findAll(Map<String, String> orderClauses,
//			Map<String, String> whereClauses, boolean dependancies)
//			throws ArgusException {
//		LOGGER.info("in [findAll(Map<String, String> orderClauses,"
//				+ " Map<String, String> whereClauses, boolean dependancies)] method :");
//		List<RekeyRequestWorkFlow> secondRequestLogWorkFlowList = null;
//
//		StringBuilder queryString = new StringBuilder();
//
//		queryString.append("SELECT d FROM RekeyRequestWorkFlow AS d ");
//		queryString.append(" WHERE 1=1");
//		//
//		// queryString
//		// .append(ArProductivityDaoHelper.getWhereClause(whereClauses));
//		queryString.append(getWhereClause(whereClauses));
//
//		queryString
//				.append(ArProductivityDaoHelper.getOrderClause(orderClauses));
//
//		LOGGER.info("querystring :: " + queryString.toString());
//		TypedQuery<RekeyRequestWorkFlow> query = em.createQuery(
//				queryString.toString(), RekeyRequestWorkFlow.class);
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
//		secondRequestLogWorkFlowList = query.getResultList();
//
//		if (dependancies && secondRequestLogWorkFlowList != null
//				&& secondRequestLogWorkFlowList.size() > Constants.ZERO) {
//			Iterator<RekeyRequestWorkFlow> adjIterator = secondRequestLogWorkFlowList
//					.iterator();
//
//			while (adjIterator.hasNext()) {
//				RekeyRequestWorkFlow adjLogWorkFlow = adjIterator.next();
//				Hibernate.initialize(adjLogWorkFlow.getArProductivity());
//
//			}
//		}
//
//		return secondRequestLogWorkFlowList;
//	}
//
//	private StringBuilder getWhereClause(Map<String, String> whereClauses) {
//		LOGGER.info("where clause size :" + whereClauses.size());
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
//				} else if (field.equalsIgnoreCase(Constants.STATUS)) {
//					queryString.append(" AND d.status ="
//							+ whereClauses.get(Constants.STATUS));
//				} else if (field.equalsIgnoreCase(Constants.TEAM_ID)) {
//					queryString.append(" AND d.arProductivity.team.id ="
//							+ whereClauses.get(Constants.TEAM_ID));
//				}
//				// else {
//				// queryString.append(" AND cclw." + field);
//				// queryString.append(" LIKE '%" + whereClauses.get(field)
//				// + "%' ");
//				// }
//			}
//		}
//
//		return queryString;
//	}
//
//	@Override
//	public void deleteRekeyRequestRecords(List<Long> rekeyrequestRecordId)
//			throws ArgusException {
//
//		Query query = em
//				.createQuery("Delete from RekeyRequestRecord where id in (:ids)");
//
//		query.setParameter("ids", rekeyrequestRecordId);
//		int count = query.executeUpdate();
//
//		LOGGER.info("no. of rows deleted: " + count);
//
//	}
//
//	@Override
//	public List<RekeyRequestRecordJsonData> getRekeyRequestRecords(
//			Long rekeyRequestId) throws ArgusException {
//
//		List<RekeyRequestRecord> records = new ArrayList<RekeyRequestRecord>();
//		List<RekeyRequestRecordJsonData> recordsList = new ArrayList<RekeyRequestRecordJsonData>();
//		StringBuilder queryString = new StringBuilder();
//		queryString
//				.append("FROM RekeyRequestRecord As record where record.rekeyRequestWorkFlow="
//						+ rekeyRequestId);
//
//		TypedQuery<RekeyRequestRecord> query = em.createQuery(
//				queryString.toString(), RekeyRequestRecord.class);
//
//		records = query.getResultList();
//
//		for (RekeyRequestRecord rekeyRequestRecord : records) {
//			RekeyRequestRecordJsonData jsonData = new RekeyRequestRecordJsonData();
//			jsonData.setCpt(rekeyRequestRecord.getCpt());
//			jsonData.setRemark(rekeyRequestRecord.getRemark());
//			jsonData.setRekeyRequestId(rekeyRequestId);
//			recordsList.add(jsonData);
//
//		}
//		return recordsList;
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Object[]> countByStatus() throws ArgusException {
//		StringBuilder queryString = new StringBuilder();
//		queryString
//				.append("select status,count(1) from rekey_request_workflow workflow group by workflow.status ");
//		List<Object[]> listObject = new ArrayList<Object[]>();
//		Query query = em.createNativeQuery(queryString.toString());
//		listObject = query.getResultList();
//		return listObject;
//
//	}
//}
