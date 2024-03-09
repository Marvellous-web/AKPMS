package argus.repo.productivity.workflow.adjustmentlogs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import argus.domain.AdjustmentLogWorkFlow;
import argus.exception.ArgusException;
import argus.repo.productivity.helper.ArProductivityDaoHelper;
import argus.util.AkpmsUtil;
import argus.util.Constants;

@Repository
@Transactional
public class AdjustmentLogsDaoImpl implements AdjustmentLogsDao {
	private static final Logger LOGGER = Logger
			.getLogger(AdjustmentLogsDaoImpl.class);

	@Autowired
	private EntityManager em;

	/**
	 * function is used to get ArProductivity by id
	 *
	 */
	@Override
	public AdjustmentLogWorkFlow findById(Long id) throws ArgusException {
		LOGGER.info("in [findById] method : id = " + id);
		AdjustmentLogWorkFlow adjLogWorkFlow = em.find(
				AdjustmentLogWorkFlow.class, id);

		return adjLogWorkFlow;
	}

	/**
	 * function is used to find by name
	 */
	@Override
	public AdjustmentLogWorkFlow findByName(String name) throws ArgusException {
		LOGGER.info("in [findByName] method : name = " + name);
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<AdjustmentLogWorkFlow> criteria = builder
				.createQuery(AdjustmentLogWorkFlow.class);

		Root<AdjustmentLogWorkFlow> adjLogWorkFlow = criteria
				.from(AdjustmentLogWorkFlow.class);

		criteria.select(adjLogWorkFlow).where(
				builder.equal(adjLogWorkFlow.get(Constants.BY_NAME), name));

		return em.createQuery(criteria).getSingleResult();
	}

	@Override
	public List<AdjustmentLogWorkFlow> findAll(
			Map<String, String> orderClauses, Map<String, String> whereClauses,
			boolean dependancies) throws ArgusException {
		LOGGER.info("in [findAll(Map<String, String> orderClauses,"
				+ " Map<String, String> whereClauses, boolean dependancies)] method :");
		List<AdjustmentLogWorkFlow> adjLogWorkFlowsList = null;

		StringBuilder queryString = new StringBuilder();

		queryString.append("SELECT d FROM AdjustmentLogWorkFlow AS d ");
		queryString.append(" WHERE d.deleted = 0");

		queryString.append(getWhereClause(whereClauses));
		// queryString
		// .append(ArProductivityDaoHelper.getWhereClause(whereClauses));
		queryString
				.append(ArProductivityDaoHelper.getOrderClause(orderClauses));
		// queryString.append(ArProductivityDaoHelper.getQueryFindAll(
		// orderClauses, whereClauses).toString());

		TypedQuery<AdjustmentLogWorkFlow> query = em.createQuery(
				queryString.toString(), AdjustmentLogWorkFlow.class);
		if (orderClauses != null && orderClauses.get(Constants.OFFSET) != null
				&& orderClauses.get(Constants.LIMIT) != null) {
			query.setFirstResult(Integer.parseInt(orderClauses
					.get(Constants.OFFSET)));
			query.setMaxResults(Integer.parseInt(orderClauses
					.get(Constants.LIMIT)));
		}

		LOGGER.info("SQL:: " + queryString.toString());

		adjLogWorkFlowsList = query.getResultList();

		if (dependancies && adjLogWorkFlowsList != null
				&& adjLogWorkFlowsList.size() > Constants.ZERO) {
			Iterator<AdjustmentLogWorkFlow> adjIterator = adjLogWorkFlowsList
					.iterator();

			while (adjIterator.hasNext()) {
				AdjustmentLogWorkFlow adjLogWorkFlow = adjIterator.next();
				Hibernate.initialize(adjLogWorkFlow.getArProductivity());

			}
		}

		return adjLogWorkFlowsList;
	}

	/**
	 * to add new department
	 */
	@Override
	public void addAdjLogWorkFlow(AdjustmentLogWorkFlow adjLogWorkFlow)
			throws ArgusException {
		LOGGER.info("in [addAdjLogWorkFlow] method :");
		em.persist(adjLogWorkFlow);
		return;
	}

	/**
	 * to update existing department
	 */
	@Override
	public void updateAdjLogWorkFlow(AdjustmentLogWorkFlow adjWorkFlow)
			throws ArgusException {
		LOGGER.info("in [updateAdjLogWorkFlow] method :");
		em.merge(adjWorkFlow);
		return;
	}

	/**
	 * to get total records
	 */
	@Override
	public int totalRecord(Map<String, String> whereClauses)
			throws ArgusException {
		LOGGER.info("in [totalRecord] method :");
		int totalRecords = 0;
		StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT COUNT(*) FROM AdjustmentLogWorkFlow AS d ");
		queryString.append(" WHERE d.deleted = 0");

		queryString.append(getWhereClause(whereClauses));

		TypedQuery<Long> query = em.createQuery(queryString.toString(),
				Long.class);
		totalRecords = query.getSingleResult().intValue();
		LOGGER.info("count = " + totalRecords);
		return totalRecords;
	}

	private StringBuilder getWhereClause(Map<String, String> whereClauses) {
		LOGGER.info("where clause size :" + whereClauses.size());
		StringBuilder queryString = new StringBuilder();

		if (whereClauses != null && whereClauses.size() > Constants.ZERO) {
			Set<String> key = whereClauses.keySet();
			for (String field : key) {
				if (field.equalsIgnoreCase(Constants.AR_PROD_PATIENT_NAME)) {
					LOGGER.info(Constants.AR_PROD_PATIENT_NAME + " = "
							+ whereClauses.get(field));
					queryString
							.append(" AND d.arProductivity.patientName LIKE '%"
									+ whereClauses
											.get(Constants.AR_PROD_PATIENT_NAME)
									+ "%' ");
				} else if (field
						.equalsIgnoreCase(Constants.AR_PROD_PATIENT_ACC_NO)) {
					LOGGER.info(Constants.AR_PROD_PATIENT_ACC_NO + " = "
							+ whereClauses.get(field));
					queryString
							.append(" AND d.arProductivity.patientAccNo LIKE '%"
									+ whereClauses
											.get(Constants.AR_PROD_PATIENT_ACC_NO)
									+ "%' ");
				} else if (field.equalsIgnoreCase(Constants.DATE_CREATED_FROM)) {

					queryString.append(" AND d.arProductivity.createdOn >= '"
							+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
									.akpmsNewDateFormat(whereClauses
											.get(Constants.DATE_CREATED_FROM)),
									Constants.MYSQL_DATE_FORMAT) + "'");

				} else if (field.equalsIgnoreCase(Constants.DATE_CREATED_TO)) {

					queryString.append(" AND d.arProductivity.createdOn <= '"
							+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
									.getFormattedDate(whereClauses
											.get(Constants.DATE_CREATED_TO)),
									Constants.MYSQL_DATE_FORMAT_WITH_TIME)
							+ "'");
				} else if (field.equalsIgnoreCase(Constants.TIMILY_FILING)) {

					queryString.append(" AND d.arProductivity.timilyFiling = "
							+ whereClauses.get(Constants.TIMILY_FILING));
				} else if (field.equalsIgnoreCase(Constants.TEAM_ID)) {
					queryString.append(" AND d.arProductivity.team.id = "
							+ whereClauses.get(Constants.TEAM_ID));
				} else if (field.equalsIgnoreCase(Constants.ARPRODUCTIVITY_ID)) {
					queryString.append(" AND d.arProductivity.id = "
							+ whereClauses.get(Constants.ARPRODUCTIVITY_ID));
				} else if (field
						.equalsIgnoreCase(Constants.WITHOUT_TIMILY_FILING)) {

					queryString
							.append(" AND d.arProductivity.timilyFiling = "
									+ whereClauses
											.get(Constants.WITHOUT_TIMILY_FILING));
				} else if (field.equalsIgnoreCase(Constants.WORK_FLOW_STATUS)) {
					queryString.append(" AND d.workFlowStatus = "
							+ whereClauses.get(Constants.WORK_FLOW_STATUS));
				}
			}
		}

		return queryString;
	}

	@Override
	public AdjustmentLogWorkFlow getAdjLogByArProductivityId(int arProdId)
			throws ArgusException, NoResultException {
		LOGGER.info("int [getAdjLogByArProductivityId] : arProdId = "
				+ arProdId);
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<AdjustmentLogWorkFlow> criteria = builder
				.createQuery(AdjustmentLogWorkFlow.class);
		Root<AdjustmentLogWorkFlow> adjLogWorkFlow = criteria
				.from(AdjustmentLogWorkFlow.class);
		criteria.select(adjLogWorkFlow).where(
				builder.equal(adjLogWorkFlow.get("arProductivity").get("id"),
						arProdId),builder.and(builder.equal(adjLogWorkFlow.get("deleted"), false)));
		return em.createQuery(criteria).getSingleResult();

	}

	public Object countByWithoutTimilyFilling() throws ArgusException {
		StringBuilder queryString = new StringBuilder();
		queryString
				.append("select count(*) from adjustment_log_workflow  al where al.without_timily_filing = 1");
		Query query = em.createNativeQuery(queryString.toString());
		return query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getWorkStatusCountByTimilyFilingStatus()
			throws ArgusException {
		List<Object[]> listObject = new ArrayList<Object[]>();
		StringBuilder queryString = new StringBuilder();
		queryString
				.append("select ap.timilyFiling, al.work_flow_status, count(*) ");
		queryString
				.append("from adjustment_log_workflow al left join ar_productivity ap on al.ar_productivity_id = ap.id ");
		queryString.append("group by ap.timilyFiling,al.work_flow_status ");
		queryString.append("order by ap.timilyFiling,al.work_flow_status");
		Query query = em.createNativeQuery(queryString.toString());
		listObject = query.getResultList();
		return listObject;
	}

}
