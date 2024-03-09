package com.idsargus.akpmsarservice.repository.productivity;


import com.idsargus.akpmsarservice.exception.ArgusException;
import com.idsargus.akpmsarservice.model.domain.ArProductivity;
import com.idsargus.akpmsarservice.model.domain.Doctor;
//import com.idsargus.akpmsarservice.repository.productivity.helper.ArProductivityDaoHelper;
import com.idsargus.akpmsarservice.repository.productivity.helper.ArProductivityDaoHelper;
import com.idsargus.akpmsarservice.util.Constants;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Repository
@Transactional
public class ArProductivityDaoImpl implements ArProductivityDao {
	private static final Logger LOGGER = Logger
			.getLogger(String.valueOf(ArProductivityDaoImpl.class));

	@Autowired
	private EntityManager em;

	/**
	 * function is used to get ArProductivity by id
	 *
	 */
	@Override
	public ArProductivity findById(Long id) throws ArgusException {
		ArProductivity arProductivity = em.find(ArProductivity.class, id);

		return arProductivity;
	}

	/**
	 * function is used to find by name
	 */
	@Override
	public ArProductivity findByName(String name) throws ArgusException {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<ArProductivity> criteria = builder
				.createQuery(ArProductivity.class);

		Root<ArProductivity> arProductivity = criteria
				.from(ArProductivity.class);

		criteria.select(arProductivity).where(
				builder.equal(arProductivity.get(Constants.BY_NAME), name));

		return em.createQuery(criteria).getSingleResult();
	}

	@Override
	public List<ArProductivity> findAll(Map<String, String> orderClauses,
										Map<String, String> whereClauses, boolean dependancies)
			throws ArgusException {
		List<ArProductivity> arProductivityList = null;
		List<ArProductivity> arProdList = null;
		String month = whereClauses.get("qaWorksheetMonth");
		String year = whereClauses.get("qaWorksheetYear");
		String limit = orderClauses.get("limit");
		String offset = orderClauses.get("offset");
		String random = orderClauses.get("random");
		StringBuilder queryString = new StringBuilder();

		if (whereClauses.get("workflowId") != null) {
			queryString
					.append("SELECT * FROM ar_productivity AS d INNER JOIN d.workFlows workFlow");
		} else {
			queryString.append("SELECT * FROM ar_productivity AS d where  Month(d.created_on) = "+month+" AND Year(d.created_on) = "+year+" ");
		}

//		queryString
//				.append(ArProductivityDaoHelper.getWhereClause(whereClauses));
		queryString
				.append(ArProductivityDaoHelper.getOrderClause(orderClauses));

		Query query = em.createNativeQuery(
				queryString.toString());
		if (orderClauses != null && orderClauses.get(Constants.OFFSET) != null
				&& orderClauses.get(Constants.LIMIT) != null) {

			query.setFirstResult(Integer.parseInt(orderClauses
					.get(Constants.OFFSET)));
			query.setMaxResults(Integer.parseInt(orderClauses
					.get(Constants.LIMIT)));
		}

		LOGGER.info("SQL:: " + queryString.toString());

		arProductivityList = query.getResultList();

		if (dependancies && arProductivityList != null
				&& arProductivityList.size() > Constants.ZERO) {
			Iterator<ArProductivity> arProductivityIterator = arProductivityList
					.iterator();
			arProdList = new ArrayList<ArProductivity>();
			while (arProductivityIterator.hasNext()) {
				ArProductivity arProductivity = arProductivityIterator.next();
//				arProductivity.setWorkflowIds(ArProductivityDaoHelper
//						.getWorkFlowName(arProductivity.getArWorkflows()));
				Hibernate.initialize(arProductivity.getInsurance());
				Hibernate.initialize(arProductivity.getDoctor());
				arProdList.add(arProductivity);
			}
			return arProdList;
		}

		return arProductivityList;
	}

	/**
	 * to add new department
	 */
	@Override
	public void addProductivity(ArProductivity arProductivity)
			throws ArgusException {
		em.persist(arProductivity);
		return;
	}

	/**
	 * to update existing department
	 */
	@Override
	public void updateProductivity(ArProductivity arProductivity)
			throws ArgusException {
		em.merge(arProductivity);
		return;
	}

	/**
	 * to get total records
	 */
	@Override
	public int totalRecord(Map<String, String> whereClauses)
			throws ArgusException {
		String month = whereClauses.get("qaWorksheetMonth");
		String year = whereClauses.get("qaWorksheetYear");
		ArrayList totalRecords = new ArrayList();
		StringBuilder queryString = new StringBuilder();
		if (whereClauses.get("workflowId") != null) {
			queryString
					.append("SELECT COUNT(*) FROM ar_productivity AS d inner join d.workFlows workFlow ");
		} else {
			queryString.append("SELECT * FROM ar_productivity AS d where d.deleted  = 0" +
					" AND Month(d.created_on) = "+month+" AND Year(d.created_on) = "+year+"");
		}
//		queryString
//				.append(ArProductivityDaoHelper.getWhereClause(whereClauses));

		Query query = em.createNativeQuery(queryString.toString());

		totalRecords = (ArrayList) query.getResultList();
		LOGGER.info("count = " + totalRecords.size());
		return totalRecords.size();
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> countByWorkFlow() throws ArgusException {
		StringBuilder queryString = new StringBuilder();
		queryString
				.append("select workfLow_id,count(1) from arprod_workflow_rel workflow group by workflow.workfLow_id ");
		List<Object[]> listObject = new ArrayList<Object[]>();
		Query query = em.createNativeQuery(queryString.toString());
		listObject = query.getResultList();
		return listObject;

	}

	/**
	 * This method will return the number of records with respect the
	 * ARProductivity ID according to its work flow
	 */
	@Override
	public List<Object[]> getWorkFlowCountByArId(Long arProdId)
			throws ArgusException {
		StringBuilder queryString = new StringBuilder();
		queryString
				.append("select COUNT(ad.id) , COUNT(cc.id) , COUNT(pp.id) ,0 as qtl, COUNT(rr.id) , COUNT(rk.id),0 as rd");
		queryString.append(" from ar_productivity  ap ");
		queryString
				.append(" left join coding_correction_log_workflow cc on ap.id = cc.ar_productivity_id ");
		queryString
				.append(" left join payment_posting_workflow pp on ap.id = pp.ar_productivity_id ");
		queryString
				.append(" left join refund_request_workflow rr on ap.id = rr.ar_productivity_id ");
		queryString
				.append(" left join adjustment_log_workflow ad on ap.id = ad.ar_productivity_id ");
		queryString
				.append(" left join rekey_request_workflow rk on ap.id = rk.ar_productivity_id ");
		queryString.append(" where  ap.id = ?");
		Query query = em.createNativeQuery(queryString.toString());
		query.setParameter(1, arProdId);
		return query.getResultList();
	}

	@Override
	public Object countByTimilyFiling() throws ArgusException {
		StringBuilder queryString = new StringBuilder();
		queryString
				.append("select count(*) from ar_productivity ap where ap.timilyFiling = 1; ");

		Query query = em.createNativeQuery(queryString.toString());
		return query.getSingleResult();

	}

	@Override
	public int queryToTLSubStatusCount(Integer subStatusId)
			throws ArgusException {
		String queryString = "SELECT COUNT(*) FROM ArProductivity ap WHERE ap.subStatus=:subStatus";
		return ((Long) em.createQuery(queryString)
				.setParameter("subStatus", subStatusId).getSingleResult())
				.intValue();
	}

	@Override
	public int followUpDateCount() throws ArgusException {
		String queryString = "SELECT COUNT(*) FROM ArProductivity ap WHERE ap.followUpDate IS NOT NULL";
		return ((Long) em.createQuery(queryString).getSingleResult())
				.intValue();
	}
	
	@Override
	public List<Doctor> fetchDoctorsByDatabase(Long databaseId) {
			String queryString = "SELECT id,name FROM doctor WHERE id in (SELECT distinct(provider_id) FROM ar_productivity WHERE database_id="
					+ databaseId + ")";
			Query query = em.createNativeQuery(queryString.toString());
			List<Doctor> doctorsList = (List<Doctor>) query.getResultList();
			return doctorsList;
	}

}
