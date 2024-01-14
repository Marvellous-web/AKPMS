package com.idsargus.akpmsarservice.repository.productivity.workflow.paymentposting;

import com.idsargus.akpmsarservice.exception.ArgusException;
import com.idsargus.akpmsarservice.model.domain.PaymentPostingWorkFlow;
import com.idsargus.akpmsarservice.repository.productivity.helper.ArProductivityDaoHelper;
import com.idsargus.akpmsarservice.util.AkpmsUtil;
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
import java.nio.file.Files;
import java.util.*;
import java.util.logging.Logger;

@Repository
@Transactional
public class PaymentPostingWorkflowDaoImpl implements PaymentPostingWorkflowDao {

	private static final Logger LOGGER = Logger
			.getLogger(String.valueOf(PaymentPostingWorkflowDaoImpl.class));

	@Autowired
	private EntityManager em;

	@Override
	public PaymentPostingWorkFlow findById(Long id) throws ArgusException {
		PaymentPostingWorkFlow paymentPostingWorkFlow = em.find(
				PaymentPostingWorkFlow.class, id);

		return paymentPostingWorkFlow;
	}

	@Override
	public void addPaymentPostingWorkFlow(
			PaymentPostingWorkFlow paymentPostingWorkFlow)
			throws ArgusException {
		em.persist(paymentPostingWorkFlow);
		LOGGER.info("PaymentPostingWorkFlow Added successfully");

	}

	@Override
	public void updatePaymentPostingWorkFlow(
			PaymentPostingWorkFlow paymentPostingWorkFlow)
			throws ArgusException {
		em.merge(paymentPostingWorkFlow);
		LOGGER.info("PaymentPostingWorkFlow Updated successfully");

	}

	@Override
	public List<PaymentPostingWorkFlow> findAllByProductivityId(String arProdId)
			throws ArgusException {

		LOGGER.info("int [findByProductivityId] : arProdId = " + arProdId);
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<PaymentPostingWorkFlow> criteria = builder
				.createQuery(PaymentPostingWorkFlow.class);
		Root<PaymentPostingWorkFlow> paymentPostingWorkFlow = criteria
				.from(PaymentPostingWorkFlow.class);
		criteria.select(paymentPostingWorkFlow).where(
				builder.equal(
						paymentPostingWorkFlow.get("arProductivity").get("id"),
						arProdId));
		return em.createQuery(criteria).getResultList();
	}

	@Override
	public int totalRecord(Map<String, String> whereClauses)
			throws ArgusException {
		LOGGER.info("in [totalRecord] method ");
		int totalRecords = 0;
		StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT COUNT(*) FROM PaymentPostingWorkFlow AS d ");
		queryString.append(" WHERE d.deleted = " + Constants.NON_DELETED);

		queryString.append(getWhereClause(whereClauses));

		LOGGER.info("queryString:: " + queryString);
		TypedQuery<Long> query = em.createQuery(queryString.toString(),
				Long.class);

		totalRecords = query.getSingleResult().intValue();
		LOGGER.info("out totalRecord , count = " + totalRecords);
		return totalRecords;
	}

	private StringBuilder getWhereClause(Map<String, String> whereClauses) {
		LOGGER.info("in getWhereClause");
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
				} else if (field.equalsIgnoreCase(Constants.STATUS)) {
					queryString.append(" AND d.status IN ("
							+ whereClauses.get(Constants.STATUS) + ")");
				}

				else if (field.equalsIgnoreCase(Constants.DOCTOR_ID)) {
					queryString.append(" AND d.arProductivity.doctor.id = "
							+ whereClauses.get(Constants.DOCTOR_ID));
				} else if (field.equalsIgnoreCase(Constants.INSURANCE_ID)) {
					queryString.append(" AND d.arProductivity.insurance.id = "
							+ whereClauses.get(Constants.INSURANCE_ID));
				} else if (field.equalsIgnoreCase(Constants.ARPRODUCTIVITY_ID)) {

					queryString.append(" AND d.arProductivity.id = "
							+ whereClauses.get(Constants.ARPRODUCTIVITY_ID));
				} else if (field.equalsIgnoreCase(Constants.TEAM_ID)) {
					queryString.append(" AND d.arProductivity.team.id = "
							+ whereClauses.get(Constants.TEAM_ID));
				} else if (field.equalsIgnoreCase(Constants.ISOFFSET)) {
					queryString.append(" AND d.offSet = "
							+ whereClauses.get(Constants.ISOFFSET));
				}
				// else {
				// queryString.append(" AND cclw." + field);
				// queryString.append(" LIKE '%" + whereClauses.get(field)
				// + "%' ");
				// }
			}
		}
		LOGGER.info("out getWhereClause");
		return queryString;
	}

	@Override
	public List<PaymentPostingWorkFlow> findAll(
			Map<String, String> orderClauses, Map<String, String> whereClauses,
			boolean dependancies) throws Exception {
		LOGGER.info("in [findAll(Map<String, String> orderClauses,"
				+ " Map<String, String> whereClauses, boolean dependancies)] method :");
		List<PaymentPostingWorkFlow> paymentPostingWorkFlowList = null;

		StringBuilder queryString = new StringBuilder();

		queryString.append("SELECT d FROM PaymentPostingWorkFlow AS d ");
		queryString.append(" WHERE d.deleted = " + Constants.NON_DELETED);
		// queryString
		// .append(ArProductivityDaoHelper.getWhereClause(whereClauses));
		queryString.append(getWhereClause(whereClauses));
		queryString
				.append(ArProductivityDaoHelper.getOrderClause(orderClauses));
		// queryString.append(ArProductivityDaoHelper.getQueryFindAll(
		// orderClauses, whereClauses).toString());

		TypedQuery<PaymentPostingWorkFlow> query = em.createQuery(
				queryString.toString(), PaymentPostingWorkFlow.class);
		if (orderClauses != null && orderClauses.get(Constants.OFFSET) != null
				&& orderClauses.get(Constants.LIMIT) != null) {
			query.setFirstResult(Integer.parseInt(orderClauses
					.get(Constants.OFFSET)));
			query.setMaxResults(Integer.parseInt(orderClauses
					.get(Constants.LIMIT)));
		}

		LOGGER.info("SQL:: " + queryString.toString());

		paymentPostingWorkFlowList = query.getResultList();

		if (dependancies && paymentPostingWorkFlowList != null
				&& paymentPostingWorkFlowList.size() > Constants.ZERO) {
			Iterator<PaymentPostingWorkFlow> paymentPostingWorkFlowIterator = paymentPostingWorkFlowList
					.iterator();
			LOGGER.info("in dependancies");
			while (paymentPostingWorkFlowIterator.hasNext()) {
				PaymentPostingWorkFlow paymentPostingWorkFlow = paymentPostingWorkFlowIterator
						.next();
				LOGGER.info("in dependancies loop : "
						+ paymentPostingWorkFlow.getId());
				Hibernate
						.initialize(paymentPostingWorkFlow.getArProductivity());
			}
		}

		return paymentPostingWorkFlowList;
	}

	@Override
	public void saveAttachement(Files file) throws ArgusException {
		em.persist(file);
	}

	@Override
	public void updateAttachement(Files file) throws ArgusException {
		em.merge(file);
	}

	@Override
	public PaymentPostingWorkFlow findByProductivityId(Long arProdId)
			throws ArgusException {

		LOGGER.info("int [findByProductivityId] : arProdId = " + arProdId);
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<PaymentPostingWorkFlow> criteria = builder
				.createQuery(PaymentPostingWorkFlow.class);
		Root<PaymentPostingWorkFlow> paymentPostingWorkFlow = criteria
				.from(PaymentPostingWorkFlow.class);
		criteria.select(paymentPostingWorkFlow).where(
				builder.equal(
						paymentPostingWorkFlow.get("arProductivity").get("id"),
						arProdId));
		return em.createQuery(criteria).getSingleResult();
	}

	@Override
	public void updateStatus(String ids, String ticketNumber, String status) {
		// TODO Auto-generated method stub
		StringBuilder queryString = new StringBuilder();
		ids = ids.substring(0, ids.trim().length() - 1);
		if (status.equalsIgnoreCase(Constants.STATUS_APPROVE)) {
			queryString
					.append("UPDATE PaymentPostingWorkFlow AS d set d.status ='"
							+ Constants.STATUS_APPROVE + "'");
		} else if (status.equalsIgnoreCase(Constants.STATUS_REJECT)) {
			queryString
					.append("UPDATE PaymentPostingWorkFlow AS d set d.status ='"
							+ Constants.STATUS_REJECT + "'");
		} /*else if (status.equalsIgnoreCase(Constants.STATUS_COMPLETED)) {
			queryString
					.append("UPDATE PaymentPostingWorkFlow AS d set d.status ='"
							+ Constants.STATUS_COMPLETED + "'");
		}*/
		queryString.append(" where id IN( " + ids + " ) ");
		em.createQuery(queryString.toString()).executeUpdate();

	}

	@Override
	public Object countOffset() throws ArgusException {
		StringBuilder queryString = new StringBuilder();
		queryString
				.append("select count(*) from payment_posting_workflow ppw where ppw.offset = 1; ");

		Query query = em.createNativeQuery(queryString.toString());

		return query.getSingleResult();
	}
	
	@Override
	public Object countOffsetPending() throws ArgusException {
		StringBuilder queryString = new StringBuilder();
		queryString
				.append("select count(*) from payment_posting_workflow ppw where ppw.offset = 1 AND ppw.status = 'Pending'; ");

		Query query = em.createNativeQuery(queryString.toString());

		return query.getSingleResult();
	}
	
	@Override
	public Object countOffsetApproved() throws ArgusException {
		StringBuilder queryString = new StringBuilder();
		queryString
				.append("select count(*) from payment_posting_workflow ppw where ppw.offset = 1 AND ppw.status = 'Approve'; ");

		Query query = em.createNativeQuery(queryString.toString());

		return query.getSingleResult();
	}
	
	@Override
	public Object countOffsetCompleted() throws ArgusException {
		StringBuilder queryString = new StringBuilder();
		queryString
				.append("select count(*) from payment_posting_workflow ppw where ppw.offset = 1 AND ppw.status = 'Completed'; ");

		Query query = em.createNativeQuery(queryString.toString());

		return query.getSingleResult();
	}

	@Override
	public List<Object[]> getStatusCount() throws ArgusException {
		List<Object[]> listObject = new ArrayList<Object[]>();

		StringBuilder queryString = new StringBuilder();
		queryString
				.append("SELECT status, COUNT(*) FROM payment_posting_workflow ppw group by ppw.status order by ppw.status");

		Query query = em.createNativeQuery(queryString.toString());
		listObject = query.getResultList();
		return listObject;
	}
}
