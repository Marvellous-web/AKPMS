package argus.repo.paymentbatch;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import argus.domain.PaymentBatch;
import argus.domain.PaymentBatchMoneySource;
import argus.exception.ArgusException;
import argus.util.AkpmsUtil;
import argus.util.Constants;

@Repository
@Transactional
public class PaymentBatchDaoImpl implements PaymentBatchDao {

	private static final Log LOGGER = LogFactory
			.getLog(PaymentBatchDaoImpl.class);

	@Autowired
	private EntityManager em;

	@Override
	public PaymentBatch findById(Long id, boolean dependency)
			throws ArgusException {
		PaymentBatch paymentBatch = em.find(PaymentBatch.class, id);
		if (dependency) {
			Hibernate.initialize(paymentBatch.getPaymentBatchMoneySources());
		}
		return paymentBatch;
	}

	@Override
	public Long addPaymentBatch(PaymentBatch paymentBatch)
			throws ArgusException {
		em.persist(paymentBatch);
		for (PaymentBatchMoneySource offsetPostingRecord : paymentBatch
				.getPaymentBatchMoneySources()) {
			offsetPostingRecord.setPaymentBatch(paymentBatch);
			em.persist(offsetPostingRecord);
		}
		LOGGER.info("PaymentBatch Added successfully");

		return paymentBatch.getId();

	}

	@Override
	public void updatePaymentBatch(PaymentBatch paymentBatch)
			throws ArgusException {
		em.merge(paymentBatch);
		LOGGER.info("PaymentBatch Updated successfully");

	}

	@Override
	public List<PaymentBatch> findAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses) throws ArgusException {

		StringBuilder queryString = new StringBuilder();
		List<PaymentBatch> paymentBatch = null;

		queryString
				.append("SELECT pmtbatch FROM PaymentBatch AS pmtbatch WHERE 1 = 1");

		queryString.append(getWhereClause(whereClauses));
		queryString.append(getOrderClause(orderClauses));
		try {
			TypedQuery<PaymentBatch> query = em.createQuery(
					queryString.toString(), PaymentBatch.class);
			if (orderClauses != null
					&& orderClauses.get(Constants.OFFSET) != null
					&& orderClauses.get(Constants.LIMIT) != null) {
				query.setFirstResult(Integer.parseInt(orderClauses
						.get(Constants.OFFSET)));
				query.setMaxResults(Integer.parseInt(orderClauses
						.get(Constants.LIMIT)));
			}
			paymentBatch = query.getResultList();

		} catch (Exception e) {
			LOGGER.error("Exception = ", e);
		}

		return paymentBatch;
	}

	@Override
	public int totalRecord(Map<String, String> whereClauses) {
		int ret = 0;

		StringBuilder queryString = new StringBuilder();
		queryString
				.append("SELECT COUNT(*) FROM PaymentBatch AS pmtbatch WHERE 1 = 1");

		queryString.append(getWhereClause(whereClauses));

		try {
			TypedQuery<Long> query = em.createQuery(queryString.toString(),
					Long.class);
			ret = query.getSingleResult().intValue();
		} catch (Exception e) {
			LOGGER.error("Exception = ", e);
		}

		LOGGER.info("count = " + ret);
		return ret;
	}

	private StringBuffer getWhereClause(Map<String, String> whereClauses) {
		LOGGER.info("where clause size :" + whereClauses.size());
		StringBuffer queryString = new StringBuffer();

		if (whereClauses != null && whereClauses.size() > 0) {
			Set<String> key = whereClauses.keySet();

			for (String field : key) {
				// billing month
				if (field.equalsIgnoreCase(Constants.MONTH)) {
					LOGGER.info("found billing month = "
							+ whereClauses.get(Constants.MONTH));
					queryString.append(" AND pmtbatch.billingMonth = "
							+ whereClauses.get(Constants.MONTH));
				}

				// billing year
				if (field.equalsIgnoreCase(Constants.YEAR)) {
					LOGGER.info("found billing year = "
							+ whereClauses.get(Constants.YEAR));
					queryString.append(" AND pmtbatch.billingYear = "
							+ whereClauses.get(Constants.YEAR));
				}

				// doctor id
				if (field.equalsIgnoreCase(Constants.DOCTOR_ID)) {
					LOGGER.info("found DOCTOR_ID = "
							+ whereClauses.get(Constants.DOCTOR_ID));
					queryString.append(" AND pmtbatch.doctor.id = "
							+ whereClauses.get(Constants.DOCTOR_ID));
				}

				// pro health doctor ids
				if (field.equalsIgnoreCase(Constants.PH_DOCTOR_IDS)) {
					LOGGER.info("found PH_DOCTOR_IDS = "
							+ whereClauses.get(Constants.PH_DOCTOR_IDS));

					String[] ids = whereClauses.get(Constants.PH_DOCTOR_IDS)
							.split(",");
					if (ids.length > 0) {
						queryString.append(" AND (");
						int i = 0;
						for (String id : ids) {
							queryString.append(id
									+ " MEMBER OF pmtbatch.phDoctorList ");
							i++;
							if (i < ids.length) {
								queryString.append(" OR ");
							}
						}
						queryString.append(" ) ");
					}
				}

				// insurance id
				if (field.equalsIgnoreCase(Constants.INSURANCE_ID)) {
					LOGGER.info("found INSURANCE_ID = "
							+ whereClauses.get(Constants.INSURANCE_ID));
					queryString.append(" AND pmtbatch.insurance.id = "
							+ whereClauses.get(Constants.INSURANCE_ID));
				}

				// created by
				if (field.equalsIgnoreCase(Constants.CREATED_BY)) {
					LOGGER.info("found CREATED_BY = "
							+ whereClauses.get(Constants.CREATED_BY));
					queryString.append(" AND pmtbatch.createdBy.id = "
							+ whereClauses.get(Constants.CREATED_BY));
				}

				// posted by
				if (field.equalsIgnoreCase(Constants.POSTED_BY)) {
					LOGGER.info("found POSTED_ID = "
							+ whereClauses.get(Constants.POSTED_BY));
					queryString.append(" AND pmtbatch.postedBy.id = "
							+ whereClauses.get(Constants.POSTED_BY));
				}

				// revenue types
				if (field.equalsIgnoreCase(Constants.REVENUE_TYPE_IDS)) {
					LOGGER.info("found REVENUE_TYPE_IDS = "
							+ whereClauses.get(Constants.REVENUE_TYPE_IDS));
					queryString.append(" AND pmtbatch.revenueType.id IN ("
							+ whereClauses.get(Constants.REVENUE_TYPE_IDS)
							+ ")");
				}

				// payment types
				if (field.equalsIgnoreCase(Constants.PAYMENT_TYPE_IDS)) {
					LOGGER.info("found PAYMENT_TYPE_IDS = "
							+ whereClauses.get(Constants.PAYMENT_TYPE_IDS));
					queryString.append(" AND pmtbatch.paymentType.id IN ("
							+ whereClauses.get(Constants.PAYMENT_TYPE_IDS)
							+ ")");
				}

				// money sources
				if (field.equalsIgnoreCase(Constants.MONEY_SOURCE_IDS)) {
					LOGGER.info("found MONEY_SOURCE_IDS = "
							+ whereClauses.get(Constants.MONEY_SOURCE_IDS));
					queryString.append(" AND pmtbatch.moneySource.id IN ("
							+ whereClauses.get(Constants.MONEY_SOURCE_IDS)
							+ ")");
				}

				// date deposit from
				if (field.equalsIgnoreCase(Constants.DATE_DEPOSIT_FROM)) {
					LOGGER.info("found DATE_DEPOSIT_FROM = "
							+ whereClauses.get(Constants.DATE_DEPOSIT_FROM));
					queryString.append(" AND pmtbatch.depositDate >= '"
							+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
									.akpmsNewDateFormat(whereClauses
											.get(Constants.DATE_DEPOSIT_FROM)),
									Constants.MYSQL_DATE_FORMAT) + "'");
				}

				// date deposit to
				if (field.equalsIgnoreCase(Constants.DATE_DEPOSIT_TO)) {
					LOGGER.info("found DATE_DEPOSIT_TO = "
							+ whereClauses.get(Constants.DATE_DEPOSIT_TO));
					queryString.append(" AND pmtbatch.depositDate <= '"
							+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
									.getFormattedDate(whereClauses
											.get(Constants.DATE_DEPOSIT_TO)),
									Constants.MYSQL_DATE_FORMAT_WITH_TIME)
							+ "'");
				}

				// date posted from (CT POSTED DATE)
				if (field.equalsIgnoreCase(Constants.DATE_POSTED_FROM)) {
					LOGGER.info("found DATE_POSTED_FROM = "
							+ whereClauses.get(Constants.DATE_POSTED_FROM));
					queryString.append(" AND pmtbatch.datePosted >= '"
							+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
									.akpmsNewDateFormat(whereClauses
											.get(Constants.DATE_POSTED_FROM)),
									Constants.MYSQL_DATE_FORMAT) + "'");
				}

				// date posted to (CT POSTED DATE)
				if (field.equalsIgnoreCase(Constants.DATE_POSTED_TO)) {
					LOGGER.info("found DATE_POSTED_TO = "
							+ whereClauses.get(Constants.DATE_POSTED_TO));
					queryString.append(" AND pmtbatch.datePosted <= '"
							+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
									.getFormattedDate(whereClauses
											.get(Constants.DATE_POSTED_TO)),
									Constants.MYSQL_DATE_FORMAT_WITH_TIME)
							+ "'");
				}

				// ERA_CHECK_NO
				if (field.equalsIgnoreCase(Constants.ERA_CHECK_NO)) {
					LOGGER.info("found ERA_CHECK_NO = "
							+ whereClauses.get(Constants.ERA_CHECK_NO));
					queryString.append(" AND pmtbatch.eraCheckNo = '"
							+ whereClauses.get(Constants.ERA_CHECK_NO) + "'");
				}

				// TICKET_NUMBER_FROM
				if (field.equalsIgnoreCase(Constants.TICKET_NUMBER_FROM)) {
					LOGGER.info("found TICKET_NUMBER_FROM = "
							+ whereClauses.get(Constants.TICKET_NUMBER_FROM));
					queryString.append(" AND pmtbatch.id >= "
							+ whereClauses.get(Constants.TICKET_NUMBER_FROM));
				}

				// TICKET_NUMBER_TO
				if (field.equalsIgnoreCase(Constants.TICKET_NUMBER_TO)) {
					LOGGER.info("found TICKET_NUMBER_TO = "
							+ whereClauses.get(Constants.TICKET_NUMBER_TO));
					queryString.append(" AND pmtbatch.id <= "
							+ whereClauses.get(Constants.TICKET_NUMBER_TO));
				}

				// not posted yet
				if (field.equalsIgnoreCase(Constants.TRANSACTION_TYPE)) {
					LOGGER.info("found TRANSACTION_TYPE = "
							+ whereClauses.get(Constants.TRANSACTION_TYPE));
					queryString.append(" AND pmtbatch.postedBy IS NULL");
				}
				// date created from
				if (field.equalsIgnoreCase(Constants.DATE_CREATED_FROM)) {
					LOGGER.info("found DATE_CREATED_FROM = "
							+ whereClauses.get(Constants.DATE_CREATED_FROM));
					queryString.append(" AND pmtbatch.createdOn >= '"
							+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
									.akpmsNewDateFormat(whereClauses
											.get(Constants.DATE_CREATED_FROM)),
									Constants.MYSQL_DATE_FORMAT) + "'");
				}

				// date created to
				if (field.equalsIgnoreCase(Constants.DATE_CREATED_TO)) {
					LOGGER.info("found DATE_CREATED_TO = "
							+ whereClauses.get(Constants.DATE_CREATED_TO));
					queryString.append(" AND pmtbatch.createdOn <= '"
							+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
									.getFormattedDate(whereClauses
											.get(Constants.DATE_CREATED_TO)),
									Constants.MYSQL_DATE_FORMAT_WITH_TIME)
							+ "'");
				}
			}
		}

		return queryString;
	}

	private StringBuffer getOrderClause(Map<String, String> orderClauses) {

		StringBuffer queryString = new StringBuffer();
		if (orderClauses != null) {
			if (orderClauses.get("orderBy") != null) {
				queryString.append(" ORDER BY pmtbatch."
						+ orderClauses.get("orderBy"));
			} else {
				queryString.append(" ORDER BY pmtbatch.id");
			}
			if (orderClauses.get("sortBy") != null) {
				queryString.append(" " + orderClauses.get("sortBy"));
			} else {
				queryString.append(" ASC");
			}
		}
		return queryString;
	}

}
