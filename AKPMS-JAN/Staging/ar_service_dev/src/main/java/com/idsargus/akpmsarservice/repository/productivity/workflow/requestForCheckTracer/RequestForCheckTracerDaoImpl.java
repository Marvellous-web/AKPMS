//package com.idsargus.akpmsarservice.repository.productivity.workflow.requestForCheckTracer;
//
//import argus.domain.Files;
//import argus.domain.RequestCheckTracerWorkFlow;
//import argus.exception.ArgusException;
//import argus.repo.productivity.helper.ArProductivityDaoHelper;
//import argus.util.Constants;
//import org.apache.log4j.Logger;
//import org.hibernate.Hibernate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import javax.persistence.TypedQuery;
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Root;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//@Repository
//@Transactional
//public class RequestForCheckTracerDaoImpl implements RequestForCheckTracerDao {
//	private static final Logger LOGGER = Logger
//			.getLogger(RequestForCheckTracerDaoImpl.class);
//
//	@Autowired
//	private EntityManager em;
//
//	/**
//	 * function is used to get ArProductivity by id
//	 *
//	 */
//	@Override
//	public RequestCheckTracerWorkFlow findById(Long id) throws ArgusException {
//		LOGGER.info("in [findById] method : id = " + id);
//		RequestCheckTracerWorkFlow checkTracerWorkFlow = em.find(
//				RequestCheckTracerWorkFlow.class, id);
//
//		return checkTracerWorkFlow;
//	}
//
//	/**
//	 * function is used to find by name
//	 */
//	@Override
//	public RequestCheckTracerWorkFlow findByName(String name) throws ArgusException {
//		LOGGER.info("in [findByName] method : name = " + name);
//		CriteriaBuilder builder = em.getCriteriaBuilder();
//		CriteriaQuery<RequestCheckTracerWorkFlow> criteria = builder
//				.createQuery(RequestCheckTracerWorkFlow.class);
//
//		Root<RequestCheckTracerWorkFlow> checkTracerWorkFlow = criteria
//				.from(RequestCheckTracerWorkFlow.class);
//
//		criteria.select(checkTracerWorkFlow).where(
//				builder.equal(checkTracerWorkFlow.get(Constants.BY_NAME), name));
//
//		return em.createQuery(criteria).getSingleResult();
//	}
//
//	@Override
//	public List<RequestCheckTracerWorkFlow> findAll(
//			Map<String, String> orderClauses, Map<String, String> whereClauses,
//			boolean dependancies) throws ArgusException {
//		LOGGER.info("in [findAll(Map<String, String> orderClauses,"
//				+ " Map<String, String> whereClauses, boolean dependancies)] method :");
//		List<RequestCheckTracerWorkFlow> checkTracerWorkFlowsList = null;
//
//		StringBuilder queryString = new StringBuilder();
//
//		queryString.append("SELECT d FROM RequestCheckTracerWorkFlow AS d ");
//
//		queryString
//				.append(ArProductivityDaoHelper.getWhereClause(whereClauses));
//		queryString
//				.append(ArProductivityDaoHelper.getOrderClause(orderClauses));
//
//		TypedQuery<RequestCheckTracerWorkFlow> query = em.createQuery(
//				queryString.toString(), RequestCheckTracerWorkFlow.class);
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
//		checkTracerWorkFlowsList = query.getResultList();
//
//		if (dependancies && checkTracerWorkFlowsList != null
//				&& checkTracerWorkFlowsList.size() > Constants.ZERO) {
//			Iterator<RequestCheckTracerWorkFlow> checkTracerIterator = checkTracerWorkFlowsList
//					.iterator();
//
//			while (checkTracerIterator.hasNext()) {
//				RequestCheckTracerWorkFlow checkTracerWorkFlow = checkTracerIterator.next();
//				Hibernate.initialize(checkTracerWorkFlow.getArProductivity());
//
//			}
//		}
//
//		return checkTracerWorkFlowsList;
//	}
//
//	/**
//	 * to add new department
//	 */
//	@Override
//	public void addCheckTracerWorkFlow(RequestCheckTracerWorkFlow checkTracerWorkFlow)
//			throws ArgusException {
//		LOGGER.info("in [addCheckTracerWorkFlow] method :");
//		em.persist(checkTracerWorkFlow);
//		return;
//	}
//
//	/**
//	 * to update existing department
//	 */
//	@Override
//	public void updateCheckTracerWorkFlow(RequestCheckTracerWorkFlow checkTracerWorkFlow)
//			throws ArgusException {
//		LOGGER.info("in [updateCheckTracerWorkFlow] method :");
//		em.merge(checkTracerWorkFlow);
//		return;
//	}
//
//	/**
//	 * to get total records
//	 */
//	@Override
//	public int totalRecord(Map<String, String> whereClauses)
//			throws ArgusException {
//		LOGGER.info("in [totalRecord] method :");
//		int totalRecords = 0;
//		StringBuilder queryString = new StringBuilder();
//		queryString.append("SELECT COUNT(*) FROM RequestCheckTracerWorkFlow AS d ");
//		if (whereClauses != null && whereClauses.size() > Constants.ZERO) {
//			queryString.append(" WHERE d.deleted = 0 ");
//			Set<String> key = whereClauses.keySet();
//			for (String field : key) {
//				if (field.equalsIgnoreCase(Constants.KEYWORD)) {
//					LOGGER.info("found search text value  = "
//							+ whereClauses.get(field));
//					queryString.append(" d.patientName LIKE '%"
//							+ whereClauses.get(Constants.KEYWORD) + "%' ");
//				} else {
//					queryString.append(" AND d." + field);
//					queryString.append(" LIKE '%" + whereClauses.get(field)
//							+ "%' ");
//				}
//			}
//		}
//		TypedQuery<Long> query = em.createQuery(queryString.toString(),
//				Long.class);
//		totalRecords = query.getSingleResult().intValue();
//		LOGGER.info("count = " + totalRecords);
//		return totalRecords;
//	}
//
//	@Override
//	public RequestCheckTracerWorkFlow getCheckTracerByArProductivityId(int arProdId)
//			throws ArgusException {
//		LOGGER.info("int [getCheckTracerByArProductivityId] : arProdId = "
//				+ arProdId);
//		CriteriaBuilder builder = em.getCriteriaBuilder();
//		CriteriaQuery<RequestCheckTracerWorkFlow> criteria = builder
//				.createQuery(RequestCheckTracerWorkFlow.class);
//		Root<RequestCheckTracerWorkFlow> checkTracerWorkFlow = criteria
//				.from(RequestCheckTracerWorkFlow.class);
//		criteria.select(checkTracerWorkFlow).where(
//				builder.equal(checkTracerWorkFlow.get("arProductivity").get("id"),
//						arProdId));
//		return em.createQuery(criteria).getSingleResult();
//
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
//}
