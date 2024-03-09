package argus.repo.paymentbatch;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import argus.domain.PaymentBatch;
import argus.exception.ArgusException;
import argus.util.AkpmsUtil;
import argus.util.Constants;

@Repository
@Transactional
public class PaymentBatchDaoImpl implements PaymentBatchDao {

	private static final Logger LOGGER = Logger
			.getLogger(PaymentBatchDaoImpl.class);

	@Autowired
	private EntityManager em;

	@Override
	public PaymentBatch findById(Long id, boolean dependency)
			throws ArgusException {
		PaymentBatch paymentBatch = em.find(PaymentBatch.class, id);
		// if (dependency) {
		// Hibernate.initialize(paymentBatch.getPaymentBatchMoneySources());
		// }
		return paymentBatch;
	}

	@Override
	public Long addPaymentBatch(PaymentBatch paymentBatch)
			throws ArgusException {
		em.persist(paymentBatch);
		/*
		 * for (PaymentBatchMoneySource offsetPostingRecord : paymentBatch
		 * .getPaymentBatchMoneySources()) {
		 * offsetPostingRecord.setPaymentBatch(paymentBatch);
		 * em.persist(offsetPostingRecord); }
		 */
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
	public List<PaymentBatch> findAllForReport(
			Map<String, String> orderClauses, Map<String, String> whereClauses)
			throws ArgusException {

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
			Collections.sort(paymentBatch, new Comparator<PaymentBatch>() {
				public int compare(PaymentBatch lhs, PaymentBatch rhs) {
					return (lhs.getId().compareTo(rhs.getId()));
				}
			});

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
									+ " MEMBER OF pmtbatch.phDoctor ");
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
				/*
				 * if (field.equalsIgnoreCase(Constants.TICKET_NUMBER_FROM)) {
				 * LOGGER.info("found TICKET_NUMBER_FROM = " +
				 * whereClauses.get(Constants.TICKET_NUMBER_FROM));
				 * queryString.append(" AND pmtbatch.id >= " +
				 * whereClauses.get(Constants.TICKET_NUMBER_FROM)); }
				 */

				// not posted yet
				if (field.equalsIgnoreCase(Constants.TRANSACTION_TYPE)) {
					LOGGER.info("found TRANSACTION_TYPE = "
							+ whereClauses.get(Constants.TRANSACTION_TYPE));
					if (whereClauses.get(Constants.TRANSACTION_TYPE)
							.equalsIgnoreCase("notPostedYet")) {
						queryString.append(" AND pmtbatch.postedBy IS NULL");
					} else {
						queryString
								.append(" AND pmtbatch.postedBy IS NOT NULL");
					}
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

				// TICKET_NUMBER_SEARCH
				if (field.equalsIgnoreCase(Constants.TICKET_NUMBER_SEARCH)) {
					LOGGER.info("found" + Constants.TICKET_NUMBER_SEARCH
							+ " = "
							+ whereClauses.get(Constants.TICKET_NUMBER_SEARCH));

					queryString.append(generateTicketNumberQuery(whereClauses
							.get(Constants.TICKET_NUMBER_SEARCH)));
				}

				// OLD PRIOR AR
				if (field.equalsIgnoreCase(Constants.OLD_PRIOR_AR)) {
					LOGGER.info("found" + Constants.OLD_PRIOR_AR + " = "
							+ whereClauses.get(Constants.OLD_PRIOR_AR));

					queryString.append(" AND pmtbatch.oldPriorAr > 0");
				}

				// suspence account
				if (field.equalsIgnoreCase(Constants.SUSPENSE_ACCOUNT)) {
					LOGGER.info("found" + Constants.SUSPENSE_ACCOUNT + " = "
							+ whereClauses.get(Constants.SUSPENSE_ACCOUNT));

					queryString.append(" AND pmtbatch.suspenseAccount > 0");
				}
				// NDBA
				if (field.equalsIgnoreCase(Constants.NDBA)) {
					LOGGER.info("found" + Constants.NDBA + " = "
							+ whereClauses.get(Constants.NDBA));

					queryString.append(" AND pmtbatch.ndba > 0");
				}
				// get posted by for qaworksheet by staff
				if (field.equalsIgnoreCase(Constants.POSTED_BY)) {
					LOGGER.info("found" + Constants.POSTED_BY + ".id = "
							+ whereClauses.get(Constants.POSTED_BY));

					queryString.append(" AND pmtbatch.postedBy.id = "
							+ Constants.POSTED_BY);
				}
			}
		}

		return queryString;
	}

	// inputStr = "1,4,5-10,34,2-4,7"
	private String generateTicketNumberQuery(String inputStr) {
		LOGGER.debug("in commanSepString " + inputStr);

		HashSet<Long> ticketNumberSet = new HashSet<Long>();
		StringBuffer betweenQueryString = new StringBuffer();

		if (inputStr != "") {
			List<String> commaSepArr = Arrays.asList(inputStr
					.split("\\s*,\\s*"));

			if (commaSepArr.size() > 0) {
				for (int counter = 0; counter < commaSepArr.size(); counter++) {
					// console.log(commaSepArr[counter]);
					String element = commaSepArr.get(counter);

					if (element.indexOf("-") > -1) {
						List<String> hypenSepArr = Arrays.asList(element
								.split("\\s*-\\s*"));

						if (hypenSepArr.size() == 2) {
							long upperLt = 0L;
							long lowerLt = 0L;
							if (Long.valueOf(hypenSepArr.get(1)) >= Long
									.valueOf(hypenSepArr.get(0))) {
								upperLt = Long.valueOf(hypenSepArr.get(1));
								lowerLt = Long.valueOf(hypenSepArr.get(0));
							} else if (Long.valueOf(hypenSepArr.get(1)) < Long
									.valueOf(hypenSepArr.get(0))) {
								upperLt = Long.valueOf(hypenSepArr.get(0));
								lowerLt = Long.valueOf(hypenSepArr.get(1));
							}
							betweenQueryString
									.append(" AND (pmtbatch.id between "
											+ lowerLt + " and " + upperLt
											+ ") ");
						}
					} else {
						try {
							ticketNumberSet.add(Long.valueOf(element));
						} catch (Exception e) {
							ticketNumberSet.add(0L);
							LOGGER.error("Exception occurred while adding ticekt number");
						}
					}
				}
			}
		}

		String outputString = "";
		if (ticketNumberSet.size() > 0) {
			outputString = ticketNumberSet.toString();

			outputString = " and pmtbatch.id in ("
					+ outputString.substring(1, outputString.length() - 1)
					+ ") ";
		}

		outputString += betweenQueryString.toString();

		LOGGER.debug("out commanSepString " + outputString);
		return outputString;
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
		} else {
			queryString.append(" ORDER BY pmtbatch.id DESC");
		}
		return queryString;
	}

}
