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

import argus.domain.QAProductivitySampling;
import argus.domain.QAWorksheetPatientInfo;
import argus.domain.QCPointChecklist;
import argus.exception.ArgusException;
import argus.util.AkpmsUtil;
import argus.util.Constants;

@Repository
@Transactional(readOnly = true)
public class QAProductivitySamplingDaoImpl implements QAProductivitySamplingDao {
	private static final Logger LOGGER = Logger
			.getLogger(QAProductivitySamplingDaoImpl.class);

	@Autowired
	private EntityManager em;

	@Override
	public QAProductivitySampling findById(Long id) throws ArgusException {
		return em.find(QAProductivitySampling.class, id);
	}

	@Override
	public List<QAProductivitySampling> findAll(
			Map<String, String> whereClause, Map<String, String> orderByClause,
			boolean dependencies) throws ArgusException {

		StringBuilder queryString = new StringBuilder();
		List<QAProductivitySampling> qaSamplings = null;

		if (whereClause != null && whereClause.containsKey("search_sample")
				&& whereClause.containsKey(Constants.KEYWORD)) {
			queryString
					.append("SELECT qaSamplingProd FROM QAProductivitySampling AS qaSamplingProd");

			queryString
					.append(" LEFT JOIN qaSamplingProd.qaWorksheetPatientInfos patientInfo ");

			queryString
					.append(" LEFT JOIN qaSamplingProd.qaWorksheet qaworksheet ");

			if (whereClause.containsKey(Constants.DEPARTMENT_ID)) {
				if (whereClause.get(Constants.DEPARTMENT_ID).equalsIgnoreCase(
						Constants.PAYMENT_DEPARTMENT_ID)) {
					queryString
							.append(" LEFT JOIN qaSamplingProd.paymentProductivity prod ");
				} else if (whereClause.get(Constants.DEPARTMENT_ID)
						.equalsIgnoreCase(Constants.CHARGE_DEPARTMENT_ID)) {
					queryString
							.append(" LEFT JOIN qaSamplingProd.chargeProductivity prod ");
				} else if (whereClause.get(Constants.DEPARTMENT_ID)
						.equalsIgnoreCase(Constants.AR_DEPARTMENT_ID)) {
					queryString
							.append(" LEFT JOIN qaSamplingProd.arProductivity prod ");
				}
			}

		} else {
			queryString
					.append("SELECT qaSamplingProd FROM QAProductivitySampling AS qaSamplingProd ");

			// queryString
			// .append(" LEFT JOIN qaSamplingProd.qaWorksheet qaworksheet ");
		}

		queryString.append(" WHERE 1 = 1 ");

		queryString.append(getWhereClause(whereClause));
		queryString.append(getOrderClause(orderByClause));
		
		
		LOGGER.error("-----findAll : Native SQL : " + queryString);

		TypedQuery<QAProductivitySampling> query = em.createQuery(
				queryString.toString(), QAProductivitySampling.class);
		if (orderByClause != null
				&& orderByClause.get(Constants.OFFSET) != null
				&& orderByClause.get(Constants.LIMIT) != null) {
			query.setFirstResult(Integer.parseInt(orderByClause
					.get(Constants.OFFSET)));
			query.setMaxResults(Integer.parseInt(orderByClause
					.get(Constants.LIMIT)));
		}

		qaSamplings = query.getResultList();
		if(qaSamplings == null)
		LOGGER.info("----qa sampling is  null---- " );

		if (dependencies) {
			if (whereClause != null
					&& whereClause.containsKey(Constants.DEPARTMENT_ID)
					&& whereClause.get(Constants.DEPARTMENT_ID)
							.equalsIgnoreCase(Constants.AR_DEPARTMENT_ID)) {

				for (QAProductivitySampling qaSampling : qaSamplings) {
					Hibernate.initialize(qaSampling.getQcPointChecklists());
					Hibernate.initialize(qaSampling.getQaWorksheet());
					for (QCPointChecklist qcPointChecklist : qaSampling
							.getQcPointChecklists()) {
						Hibernate.initialize(qcPointChecklist.getQcPoint());
					}
				}
			} else {
				for (QAProductivitySampling qaSampling : qaSamplings) {

					Hibernate.initialize(qaSampling
							.getQaWorksheetPatientInfos());
					Hibernate.initialize(qaSampling.getQaWorksheet());

					for (QAWorksheetPatientInfo qaWorksheetPatientInfo : qaSampling
							.getQaWorksheetPatientInfos()) {
						Hibernate.initialize(qaWorksheetPatientInfo
								.getQcPointChecklist());

						for (QCPointChecklist qcPointChecklist : qaWorksheetPatientInfo
								.getQcPointChecklist()) {
							Hibernate.initialize(qcPointChecklist.getQcPoint());
						}
					}
				}
			}
		}

		if (whereClause != null
				&& whereClause.containsKey(Constants.DEPARTMENT_ID)) {
			if (Constants.PAYMENT_DEPARTMENT_ID.equalsIgnoreCase(whereClause
					.get(Constants.DEPARTMENT_ID)) && qaSamplings != null) {
				for (QAProductivitySampling qaSampling : qaSamplings) {
					Hibernate.initialize(qaSampling.getPaymentProductivity());
					if (qaSampling.getPaymentProductivity() != null) {
						Hibernate.initialize(qaSampling
								.getPaymentProductivity().getPaymentBatch());
					}
				}
			} else if (Constants.CHARGE_DEPARTMENT_ID
					.equalsIgnoreCase(whereClause.get(Constants.DEPARTMENT_ID))
					&& qaSamplings != null) {
				for (QAProductivitySampling qaSampling : qaSamplings) {
					Hibernate.initialize(qaSampling.getChargeProductivity());
				}
			} else if (Constants.ACCOUNTING_DEPARTMENT_ID
					.equalsIgnoreCase(whereClause.get(Constants.DEPARTMENT_ID))
					&& qaSamplings != null) {
				for (QAProductivitySampling qaSampling : qaSamplings) {
					Hibernate.initialize(qaSampling
							.getCredentialingAccountingProductivity());
				}
			} else if (Constants.AR_DEPARTMENT_ID.equalsIgnoreCase(whereClause
					.get(Constants.DEPARTMENT_ID)) && qaSamplings != null) {
				for (QAProductivitySampling qaSampling : qaSamplings) {
					Hibernate.initialize(qaSampling.getArProductivity());
				}
			} else if (Constants.QAWORKSHEET_ID.equalsIgnoreCase(whereClause
					.get(Constants.QAWORKSHEET_ID))) {
				for (QAProductivitySampling qaSampling : qaSamplings) {
					Hibernate.initialize(qaSampling.getQaWorksheet());
				}
			}
		}

		return qaSamplings;
	}

	@Transactional(readOnly = false)
	@Override
	public void save(QAProductivitySampling qaProductivitySampling)
			throws ArgusException {
		em.persist(qaProductivitySampling);
	}

	@Transactional(readOnly = false)
	@Override
	public void update(QAProductivitySampling qaProductivitySampling)
			throws ArgusException {
		em.merge(qaProductivitySampling);
	}

	@Transactional(readOnly = false)
	@Override
	public Long delete(Long id) {
		String deleteQuery = "DELETE FROM QAProductivitySampling qaSampling WHERE qaSampling.id = :id";
		Query query = em.createQuery(deleteQuery);
		query.setParameter("id", id);
		return new Long(query.executeUpdate());
	}

	@Transactional(readOnly = false)
	@Override
	public List<QAWorksheetPatientInfo> deleteDependencies(Long id)
			throws Exception {
		// select dependencies in QCPointChecklist for all patientinfoids
		String fetchPatientInfo = "SELECT patientinfo FROM QAWorksheetPatientInfo patientinfo WHERE patientinfo.qaProductivitySampling.id = :id";
		Query fetchPatientInfoQuery = em.createQuery(fetchPatientInfo);
		fetchPatientInfoQuery.setParameter("id", id);

		List<QAWorksheetPatientInfo> qaWorksheetPatientInfos = fetchPatientInfoQuery
				.getResultList();
		if (qaWorksheetPatientInfos.size() != 0) {

			for (QAWorksheetPatientInfo qaWorksheetPatientInfo : qaWorksheetPatientInfos) {

				String deleteJPQLChild = "DELETE FROM QCPointChecklist qcpointchecklist WHERE qcpointchecklist.qaWorksheetPatientInfo.id = :id";
				Query queryChild = em.createQuery(deleteJPQLChild);
				queryChild.setParameter("id", qaWorksheetPatientInfo.getId());
				queryChild.executeUpdate();

			}

			// delete patient info for that qaSamplingProd id
			String deletePatientInfo = "DELETE FROM QAWorksheetPatientInfo qaworksheetpatientinfo WHERE qaworksheetpatientinfo.qaProductivitySampling.id = :id";
			Query queryDeletePatientInfo = em.createQuery(deletePatientInfo);
			queryDeletePatientInfo.setParameter("id", id);
			queryDeletePatientInfo.executeUpdate();

		} else {
			// delete ar records from checklist based on sampling id
			String deleteJPQLChild = "DELETE FROM QCPointChecklist qcpointchecklist WHERE qcpointchecklist.qaProductivitySampling.id = :id";
			Query queryChild = em.createQuery(deleteJPQLChild);
			queryChild.setParameter("id", id);
			queryChild.executeUpdate();

		}

		// delete corresponding sampling record
		this.delete(id);

		LOGGER.info(qaWorksheetPatientInfos);

		return qaWorksheetPatientInfos;
	}

	@Override
	public int totalRecord(Map<String, String> conditionMap)
			throws ArgusException {
		int ret = 0;
		StringBuilder queryString = new StringBuilder();
		queryString
				.append("SELECT COUNT(*) FROM QAProductivitySampling AS qaSamplingProd WHERE 1 = 1");

		queryString.append(getWhereClause(conditionMap));

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

	private StringBuffer getWhereClause(Map<String, String> whereClause) {
		LOGGER.info("where clause size :" + whereClause.size());
		StringBuffer queryString = new StringBuffer();

		if (whereClause != null && whereClause.size() > 0) {
			Set<String> key = whereClause.keySet();

			for (String field : key) {
				if (field.equalsIgnoreCase(Constants.SAMPLING_PRODUCTIVITY_ID)) {
					LOGGER.info("found SAMPLING_PRODUCTIVITY_ID = "
							+ whereClause
									.get(Constants.SAMPLING_PRODUCTIVITY_ID));
					queryString.append(" AND qaSamplingProd."
							+ Constants.SAMPLING_PRODUCTIVITY_ID
							+ " = "
							+ whereClause
									.get(Constants.SAMPLING_PRODUCTIVITY_ID));
				} else

				// created by
				if (field.equalsIgnoreCase(Constants.CREATED_BY)) {
					LOGGER.info("found CREATED_BY = "
							+ whereClause.get(Constants.CREATED_BY));
					queryString.append(" AND qaSamplingProd.createdBy.id in ( "
							+ whereClause.get(Constants.CREATED_BY) + " )");
				} else

				// posted by
				if (field.equalsIgnoreCase(Constants.POSTED_BY)) {
					LOGGER.info("found POSTED_ID = "
							+ whereClause.get(Constants.POSTED_BY));
					queryString.append(" AND qaSamplingProd.postedBy.id = "
							+ whereClause.get(Constants.POSTED_BY));
				} else
				// departmentId of productivities
				if (field.equalsIgnoreCase(Constants.DEPARTMENT_ID)) {
					LOGGER.info("found DEPARTMENT_ID = "
							+ whereClause.get(Constants.DEPARTMENT_ID));
					queryString
							.append(" AND qaSamplingProd.qaWorksheet.department.id"
									+ " = "
									+ whereClause.get(Constants.DEPARTMENT_ID));
				} else

				// QAWORKSHEET ID
				if (field.equalsIgnoreCase(Constants.QAWORKSHEET_ID)) {
					LOGGER.info("found QAWORKSHEET_ID = "
							+ whereClause.get(Constants.QAWORKSHEET_ID));
					queryString.append(" AND qaSamplingProd.qaWorksheet.id = "
							+ whereClause.get(Constants.QAWORKSHEET_ID));
					queryString
							.append(" AND qaSamplingProd.qaWorksheet.deleted != "
									+ Constants.ONE);
				} else
				// QaWorksheet subDepartment
				if (field.equalsIgnoreCase(Constants.SUB_DEPARTMENT)) {
					LOGGER.info("found SUB_DEPARTMENT = "
							+ whereClause.get(Constants.SUB_DEPARTMENT));
					queryString
							.append(" AND qaSamplingProd.qaWorksheet.subDepartment.id"
									+ " = "
									+ whereClause.get(Constants.SUB_DEPARTMENT));
				} else

				// QaWorksheet month on which qaWorksheet is created
				if (field.equalsIgnoreCase(Constants.MONTH)) {
					queryString
							.append(" AND MONTH(qaSamplingProd.qaWorksheet.createdOn) = "
									+ whereClause.get(Constants.MONTH));
				} else

				// QAWorksheet year on which qaWorksheet is created
				if (field.equalsIgnoreCase(Constants.YEAR)) {
					queryString
							.append(" AND YEAR(qaSamplingProd.qaWorksheet.createdOn) = "
									+ whereClause.get(Constants.YEAR));
				} else

				// Keyword search
				if (field.equalsIgnoreCase(Constants.KEYWORD)
						&& whereClause.containsKey("search_sample")) {
					queryString.append(" AND ( ");

					queryString
							.append("qaSamplingProd.qaWorksheet.name LIKE '%"
									+ whereClause.get(Constants.KEYWORD)
									+ "%' ");
					queryString.append(" OR patientInfo.patientName LIKE '%"
							+ whereClause.get(Constants.KEYWORD) + "%' ");

					queryString.append(" OR patientInfo.accountNumber LIKE '%"
							+ whereClause.get(Constants.KEYWORD) + "%' ");

					queryString.append(" OR patientInfo.transaction LIKE '%"
							+ whereClause.get(Constants.KEYWORD) + "%' ");

					queryString.append(" OR patientInfo.cptCodesDemo LIKE '%"
							+ whereClause.get(Constants.KEYWORD) + "%' ");

					if (whereClause.containsKey(Constants.DEPARTMENT_ID)) {
						if (whereClause.get(Constants.DEPARTMENT_ID)
								.equalsIgnoreCase(
										Constants.PAYMENT_DEPARTMENT_ID)) {
							queryString
									.append(" OR prod.paymentBatch.id LIKE '%"
											+ whereClause
													.get(Constants.KEYWORD)
											+ "%' ");
						} else if (whereClause.get(Constants.DEPARTMENT_ID)
								.equalsIgnoreCase(
										Constants.CHARGE_DEPARTMENT_ID)) {
							queryString
									.append(" OR prod.ticketNumber.id LIKE '%"
											+ whereClause
													.get(Constants.KEYWORD)
											+ "%' ");
						} else if (whereClause.get(Constants.DEPARTMENT_ID)
								.equalsIgnoreCase(Constants.AR_DEPARTMENT_ID)) {
							queryString
									.append(" OR prod.arDatabase.name LIKE '%"
											+ whereClause
													.get(Constants.KEYWORD)
											+ "%' ");
							queryString.append(" OR prod.patientAccNo LIKE '%"
									+ whereClause.get(Constants.KEYWORD)
									+ "%' ");
						}
					}

					queryString.append(" ) ");
				}
				// User
				else if (field.equalsIgnoreCase(Constants.USER)) {
					if (whereClause.get(Constants.DEPARTMENT_ID)
							.equalsIgnoreCase(Constants.PAYMENT_DEPARTMENT_ID)) {
						queryString
								.append(" AND qaSamplingProd.paymentProductivity.createdBy = "
										+ whereClause.get(Constants.USER));
					} else if (whereClause.get(Constants.DEPARTMENT_ID)
							.equalsIgnoreCase(Constants.CHARGE_DEPARTMENT_ID)) {
						queryString
								.append(" AND qaSamplingProd.chargeProductivity.createdBy = "
										+ whereClause.get(Constants.USER));
					} else if (whereClause.get(Constants.DEPARTMENT_ID)
							.equalsIgnoreCase(Constants.AR_DEPARTMENT_ID)) {
						queryString
								.append(" AND qaSamplingProd.arProductivity.createdBy = "
										+ whereClause.get(Constants.USER));
					}

				} else if (field.equalsIgnoreCase(Constants.ID)) {
					queryString.append(" AND qaSamplingProd.id = "
							+ whereClause.get(Constants.ID));
				} else if (field.equalsIgnoreCase(Constants.STATUS)) {
					queryString
							.append(" AND qaSamplingProd.qaWorksheet.status = "
									+ whereClause.get(Constants.STATUS));
				}

				/* date posted from */
				else if (field.equalsIgnoreCase(Constants.QA_REPORT_FROM_DATE)) {
					LOGGER.info("found QA_REPORT_FROM_DATE = "
							+ whereClause.get(Constants.QA_REPORT_FROM_DATE));

					queryString
							.append(" AND qaSamplingProd.qaWorksheet.createdOn >= '"
									+ AkpmsUtil.akpmsDateFormat(
											AkpmsUtil
													.akpmsNewDateFormat(whereClause
															.get(Constants.QA_REPORT_FROM_DATE)),
											Constants.MYSQL_DATE_FORMAT) + "'");
				}

				/* date posted to */
				else if (field.equalsIgnoreCase(Constants.QA_REPORT_TO_DATE)) {
					LOGGER.info("found QA_REPORT_TO_DATE = "
							+ whereClause.get(Constants.QA_REPORT_TO_DATE));
					queryString
							.append(" AND qaSamplingProd.qaWorksheet.createdOn <= '"
									+ AkpmsUtil.akpmsDateFormat(
											AkpmsUtil
													.getFormattedDate(whereClause
															.get(Constants.QA_REPORT_TO_DATE)),
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
			if (orderClauses.get(Constants.ORDER_BY) != null
					&& !orderClauses.get(Constants.ORDER_BY).equalsIgnoreCase(
							"")) {
				queryString.append(" ORDER BY qaSamplingProd."
						+ orderClauses.get(Constants.ORDER_BY));
			} else {
				queryString.append(" ORDER BY qaSamplingProd.id");
			}

			if (orderClauses.get(Constants.SORT_BY) != null) {
				queryString.append(" " + orderClauses.get(Constants.SORT_BY));
			} else {
				queryString.append(" ASC");
			}
		} else {
			queryString.append(" ORDER BY qaSamplingProd.id ASC");
		}

		return queryString;
	}
	
	

	
	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findProductivityCountWithUsersAndQAWorksheets(
			Map<String, String> whereClause, Map<String, String> orderClause)
			throws ArgusException {
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT ");
		String alias = null;
		String reffernceName = null;
		String qaChecklist = null;
		String patientInfo = null;
		List<Object[]> reportData = new ArrayList<Object[]>();

		if (whereClause != null && whereClause.size() > Constants.ZERO) {

			if (whereClause.get(Constants.DEPARTMENT).equalsIgnoreCase(
					Constants.PAYMENT_DEPARTMENT_ID)) {
				alias = "payment";
				reffernceName = "paymentProductivity";
				patientInfo = ", COUNT(patientInfo.id)";
				qaChecklist = "LEFT JOIN qaSamplingProd.qaWorksheetPatientInfos patientInfo LEFT JOIN patientInfo.qcPointChecklist checklist";

			} else if (whereClause.get(Constants.DEPARTMENT).equalsIgnoreCase(
					Constants.CHARGE_DEPARTMENT_ID)) {
				alias = "charge";
				reffernceName = "chargeProductivity";
				patientInfo = ", COUNT(patientInfo.id)";
				qaChecklist = "LEFT JOIN qaSamplingProd.qaWorksheetPatientInfos patientInfo LEFT JOIN patientInfo.qcPointChecklist checklist";

			} else if (whereClause.get(Constants.DEPARTMENT).equalsIgnoreCase(
					Constants.AR_DEPARTMENT_ID)) {
				alias = "ar";
				// there is no patientInfo for ar
				patientInfo = "";
				reffernceName = "arProductivity";
				qaChecklist = "LEFT JOIN qaSamplingProd.qcPointChecklists checklist";
			} else if (whereClause.get(Constants.DEPARTMENT).equalsIgnoreCase(
					Constants.ACCOUNTING_DEPARTMENT_ID)) {
				alias = "ca";
				// there is no patientInfo for ar
				patientInfo = "";
				reffernceName = "credentialingAccountingProductivity";
				qaChecklist = "LEFT JOIN qaSamplingProd.qcPointChecklists checklist";
			}

			queryString.append("COUNT(" + alias + ".id), " + alias
					+ ".createdBy, worksheet.name " + patientInfo
					+ ", COUNT(checklist.id), " + alias
					+ " FROM QAProductivitySampling qaSamplingProd"
					+ " LEFT JOIN qaSamplingProd." + reffernceName + " "
					+ alias
					+ " LEFT JOIN qaSamplingProd.qaWorksheet worksheet "
					+ qaChecklist + " WHERE 1 = 1");
			if (whereClause.containsKey(Constants.MONTH)) {
				queryString.append(" AND MONTH(worksheet.createdOn) = "
						+ whereClause.get(Constants.MONTH));

			}

			if (whereClause.containsKey(Constants.YEAR)) {
				queryString.append(" AND YEAR(worksheet.createdOn) = "
						+ whereClause.get(Constants.YEAR));

			}

			if (whereClause.containsKey(Constants.DEPARTMENT)) {
				queryString.append(" AND worksheet." + Constants.DEPARTMENT
						+ " = " + whereClause.get(Constants.DEPARTMENT));
			}
			if (whereClause.containsKey(Constants.SUB_DEPARTMENT)) {
				queryString.append(" AND worksheet." + Constants.SUB_DEPARTMENT
						+ " = " + whereClause.get(Constants.SUB_DEPARTMENT));
			}
			queryString.append(" AND worksheet." + Constants.STATUS + " = 2");
			queryString.append(" GROUP BY " + alias
					+ ".createdBy.id, worksheet.id");

			queryString.append(getOrderClause(orderClause));

			Query query = em.createQuery(queryString.toString());

			if (orderClause != null
					&& orderClause.get(Constants.OFFSET) != null
					&& orderClause.get(Constants.LIMIT) != null) {
				query.setFirstResult(Integer.parseInt(orderClause
						.get(Constants.OFFSET)));
				query.setMaxResults(Integer.parseInt(orderClause
						.get(Constants.LIMIT)));
			}

			LOGGER.debug("findProductivityCountWithUsersAndQAWorksheets : JSQL :: "
					+ queryString);
			reportData.addAll((List<Object[]>) query.getResultList());
		}

		return reportData;
	}
	

	@SuppressWarnings("unchecked")
	public List<Object[]> findQAWorksheetUserReportData(
			Map<String, String> whereClause) throws ArgusException {

		StringBuffer queryString = new StringBuffer();
		String tableName = null;
		String totalTransaction = null;
		String totalAccount = null;
		String qaAccounts =null;
		List<Object[]> reportData = new ArrayList<Object[]>();

		if (whereClause != null && whereClause.size() > Constants.ZERO) {

			if (whereClause.get(Constants.DEPARTMENT).equalsIgnoreCase(
					Constants.PAYMENT_DEPARTMENT_ID)) {
				tableName = "payment_productivity";
				totalTransaction = "(IFNULL(payment_productivity.manual_transaction,0) + IFNULL(payment_productivity.electronically_transaction,0)) AS totalTransaction, ";
				totalAccount = " (IFNULL(payment_productivity.manual_transaction,0) + IFNULL(payment_productivity.electronically_transaction,0)) AS totalAccount, ";
				qaAccounts =" IFNULL(CAST(sum(qa_worksheet_patient_info.transaction) AS SIGNED INTEGER),0)  as qa_accounts";
			} else if (whereClause.get(Constants.DEPARTMENT).equalsIgnoreCase(
					Constants.CHARGE_DEPARTMENT_ID)) {
				tableName = "charge_productivity";
				totalTransaction = "(IFNULL(charge_productivity.t1,0) + IFNULL(charge_productivity.t2,0) + IFNULL(charge_productivity.t3,0)) AS totalTransaction, ";
				totalAccount = " CASE charge_productivity.productivity_type  "
						+ " when 'demo' Then "
								+ "IFNULL(charge_productivity.t1,0) + IFNULL(charge_productivity.t2,0)"
						+ " when 'CE' Then "
								+ "IFNULL(charge_productivity.t3,0)"
						+ " else  "
								+ "IFNULL(charge_productivity.t1,0) + IFNULL(charge_productivity.t2,0) + IFNULL(charge_productivity.t3,0)"
						+ " end    AS totalAccount,";
				qaAccounts ="count(distinct qa_worksheet_patient_info.id) as qa_accounts";

			} else if (whereClause.get(Constants.DEPARTMENT).equalsIgnoreCase(
					Constants.AR_DEPARTMENT_ID)) {
				tableName = "ar_productivity";
				// totalTransaction =
				// "COUNT(qa_productivity_sampling.id) AS totalTransaction, ";
				totalTransaction = "CAST(ar_productivity.cpt AS SIGNED INTEGER) as totalTransaction, ";
				totalAccount = "CAST(ar_productivity.cpt AS SIGNED INTEGER) as totalAccount, ";
				qaAccounts ="count(distinct qa_worksheet_patient_info.id) as qa_accounts";
				
			} else if (whereClause.get(Constants.DEPARTMENT).equalsIgnoreCase(
					Constants.ACCOUNTING_DEPARTMENT_ID)) {
				tableName = "credentialing_accounting_productivity";
				totalTransaction = "COUNT(distinct qa_productivity_sampling.id) AS totalTransaction, ";
				totalAccount = " COUNT(distinct  qa_productivity_sampling.id) AS totalAccount, ";
				qaAccounts ="count(distinct qa_worksheet_patient_info.id) as qa_accounts";
			}

			queryString
					.append("SELECT qa_worksheet.name, temp.qa_worksheet_id, temp.user_id, CONCAT(user.first_name,' ',user.last_name), "
							+ " sum(temp.totalTransaction), sum(temp.qa_accounts), sum(temp.totalAccount), sum(temp.totalError),  "
							// total error percentage
							+ "FORMAT((sum(totalError)/ sum(temp.qa_accounts))*100,2),FORMAT((sum(totalError)/ sum(temp.totalTransaction))*100,2) "
							+ "FROM ( ");

			queryString.append("SELECT " + "qa_productivity_sampling.id, "
					+ "qa_productivity_sampling.qa_worksheet_id, "
					+ totalTransaction + totalAccount + tableName + ".created_by as user_id, "+qaAccounts+", "
					+ tableName + ".id as productivityId, ");

			if (whereClause.get(Constants.DEPARTMENT).equalsIgnoreCase(
					Constants.AR_DEPARTMENT_ID)) {
				queryString
						.append(" (count(DISTINCT qc_point_checklist.id)* case when CAST(ar_productivity.cpt AS SIGNED INTEGER) = 0 then 1 else CAST(ar_productivity.cpt AS SIGNED INTEGER) end  ) as totalError ");
			} else {
				queryString
						.append(" count(DISTINCT qc_point_checklist.id) as totalError ");
			}

			queryString
					.append(" FROM qa_productivity_sampling "
							+ "LEFT JOIN "
							+ tableName
							+ " ON "
							+ tableName
							+ ".id = qa_productivity_sampling."
							+ tableName
							+ "_id "

							+ "LEFT JOIN qa_worksheet ON qa_worksheet.id = qa_productivity_sampling.qa_worksheet_id "
							+ "LEFT JOIN qc_point_checklist ON qc_point_checklist.qa_productivity_sampling_id = qa_productivity_sampling.id "
							+ "LEFT JOIN qa_worksheet_patient_info ON qa_worksheet_patient_info.qa_productivity_sampling_id = qa_productivity_sampling.id "
							+ "WHERE 1 = 1 AND " + tableName
							+ ".created_by IS NOT NULL");

			if (whereClause.containsKey(Constants.QA_REPORT_FROM_DATE)) {
				queryString.append(" AND qa_worksheet.created_on >= "
						+ "'"
						+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
								.getFormattedDate(whereClause
										.get(Constants.QA_REPORT_FROM_DATE)),
								Constants.MYSQL_DATE_FORMAT) + "'");
			}
			if (whereClause.containsKey(Constants.QA_REPORT_TO_DATE)) {
				queryString.append(" AND qa_worksheet.created_on <= "
						+ "'"
						+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
								.getFormattedDate(whereClause
										.get(Constants.QA_REPORT_TO_DATE)),
								Constants.MYSQL_DATE_FORMAT_WITH_TIME) + "'");
			}
			if (whereClause.containsKey(Constants.DEPARTMENT)) {
				queryString.append(" AND qa_worksheet.department_id = "
						+ whereClause.get(Constants.DEPARTMENT));
			}
			if (whereClause.containsKey(Constants.SUB_DEPARTMENT)) {
				queryString.append(" AND qa_worksheet.sub_department_id = "
						+ whereClause.get(Constants.SUB_DEPARTMENT));
			}
			if (whereClause.containsKey(Constants.CREATED_BY)) {
				queryString.append(" AND qa_worksheet.created_by IN("
						+ whereClause.get(Constants.CREATED_BY) + ") ");
			}

			queryString.append(" AND qa_worksheet." + Constants.STATUS
					+ " = 2 ");
			queryString.append("GROUP BY qa_productivity_sampling.id "
					+ ") as temp ");

			queryString
					.append("LEFT JOIN qa_worksheet ON temp.qa_worksheet_id = qa_worksheet.id "
							+ "LEFT JOIN user on user.id = temp.user_id ");

			queryString
					.append("GROUP BY temp.user_id , temp.qa_worksheet_id WITH ROLLUP");

			LOGGER.error("findProductivityCountWithUsersAndQAWorksheets : Native SQL :: "
					+ queryString);

			Query query = em.createNativeQuery(queryString.toString());

			/*
			 * if (orderClause != null && orderClause.get(Constants.OFFSET) !=
			 * null && orderClause.get(Constants.LIMIT) != null) {
			 * query.setFirstResult(Integer.parseInt(orderClause
			 * .get(Constants.OFFSET)));
			 * query.setMaxResults(Integer.parseInt(orderClause
			 * .get(Constants.LIMIT))); }
			 */

			reportData.addAll((List<Object[]>) query.getResultList());
		}

		return reportData;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> findExecutedUsersRecords(
			Map<String, String> whereClause, Short qaWorksheetType)
			throws ArgusException {

		List<Object[]> arrObj = new ArrayList<Object[]>();
		String alias = null;
		String entityName = null;
		String doctorField = null;

		if (whereClause != null && whereClause.size() > Constants.ZERO) {
			StringBuffer queryString = new StringBuffer();

			if (whereClause.get(Constants.DEPARTMENT).equalsIgnoreCase(
					Constants.PAYMENT_DEPARTMENT_ID)) {
				entityName = "paymentProductivity";
				alias = "payment";
				doctorField = entityName + ".paymentBatch.doctor.id";
			} else if (whereClause.get(Constants.DEPARTMENT).equalsIgnoreCase(
					Constants.CHARGE_DEPARTMENT_ID)) {
				entityName = "chargeProductivity";
				alias = "charge";
				doctorField = entityName + ".ticketNumber.doctor.id";
			} else if (whereClause.get(Constants.DEPARTMENT).equalsIgnoreCase(
					Constants.AR_DEPARTMENT_ID)) {
				entityName = "arProductivity";
				alias = "ar";
				doctorField = entityName + ".doctor.id";
			} else if (whereClause.get(Constants.DEPARTMENT).equalsIgnoreCase(
					Constants.ACCOUNTING_DEPARTMENT_ID)) {
				entityName = "credentialingAccountingProductivity";
				alias = "ca";
				doctorField = "";
			}

			if (qaWorksheetType == Constants.QA_WORKSEET_TYPE_BYSTAFF) {
				queryString
						.append("SELECT CONCAT("
								+ alias
								+ ".createdBy.firstName, ' ', "
								+ alias
								+ ".createdBy.lastName), COUNT(*), sample.qaWorksheet.arStatusCode "
								+ "FROM QAProductivitySampling AS sample "
								+ "LEFT JOIN sample." + entityName + " "
								+ alias + " ");
				queryString.append("WHERE 1 = 1 ");
				queryString.append("AND sample.qaWorksheet.id = "
						+ whereClause.get(Constants.QAWORKSHEET_ID) + " ");
				queryString.append("GROUP BY " + alias + ".createdBy.id");

			} else if (qaWorksheetType == Constants.QA_WORKSEET_TYPE_BYDOCTOR) {
				queryString.append("SELECT " + doctorField
						+ " as name, COUNT(*) "
						+ "FROM QAProductivitySampling AS sample "
						+ "LEFT JOIN sample." + entityName + " " + alias + " ");
				queryString.append("WHERE 1 = 1 ");
				queryString.append("AND sample.qaWorksheet.id = "
						+ whereClause.get(Constants.QAWORKSHEET_ID) + " ");
				queryString.append("GROUP BY " + doctorField);
			}

			Query query = em.createQuery(queryString.toString());

			arrObj.addAll((List<Object[]>) query.getResultList());
		}

		return arrObj;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> monthlyQASummary(Map<String, String> whereClause)
			throws ArgusException {

		List<Object[]> reportData = new ArrayList<Object[]>();

		String entityName = null;
		if (whereClause != null && whereClause.size() > Constants.ZERO) {
			StringBuffer queryString = new StringBuffer();

			if (whereClause.get(Constants.DEPARTMENT).equalsIgnoreCase(
					Constants.PAYMENT_DEPARTMENT_ID)) {
				entityName = "payment_productivity";
			} else if (whereClause.get(Constants.DEPARTMENT).equalsIgnoreCase(
					Constants.CHARGE_DEPARTMENT_ID)) {
				entityName = "charge_productivity";
			} else if (whereClause.get(Constants.DEPARTMENT).equalsIgnoreCase(
					Constants.AR_DEPARTMENT_ID)) {
				entityName = "ar_productivity";
			} else if (whereClause.get(Constants.DEPARTMENT).equalsIgnoreCase(
					Constants.ACCOUNTING_DEPARTMENT_ID)) {
				entityName = "credentialing_accounting_productivity";
			}

			queryString
					.append(" SELECT DATE_FORMAT(productivity.created_on, '%m-%d-%Y') AS day, COUNT(productivity.id) AS productivityCount, IFNULL(QA.sampleCount, 0) as sampleCount, IFNULL(QA.errorTotal, 0) as errorCount, IFNULL(QA.errorTotal/QA.sampleCount, 0) as errorRate ");
			queryString.append(" FROM " + entityName + " as productivity ");
			queryString.append(" LEFT JOIN ( ");
			queryString
					.append(" SELECT DATE_FORMAT(qa_worksheet.created_on,'%d') as day, COUNT(qa_productivity_sampling.id) AS sampleCount, sum(temp.errorCount) as errorTotal ");
			queryString.append(" FROM qa_productivity_sampling ");
			queryString
					.append(" LEFT JOIN qa_worksheet ON qa_productivity_sampling.qa_worksheet_id = qa_worksheet.id ");
			queryString.append(" LEFT JOIN ( ");
			queryString
					.append(" SELECT COUNT(*) AS errorCount, qc_point_checklist.qa_productivity_sampling_id AS sampleId FROM qc_point_checklist  ");
			queryString
					.append(" GROUP BY qc_point_checklist.qa_productivity_sampling_id ");
			queryString
					.append(" ) AS temp on temp.sampleId = qa_productivity_sampling.id ");
			queryString.append(" WHERE 1=1  ");

			if (whereClause.containsKey(Constants.DEPARTMENT_ID)) {
				queryString.append(" AND qa_worksheet.department_id = "
						+ whereClause.get(Constants.DEPARTMENT_ID));
			}
			if (whereClause.containsKey(Constants.SUB_DEPARTMENT)) {
				queryString.append(" AND qa_worksheet.sub_department_id = "
						+ whereClause.get(Constants.SUB_DEPARTMENT));
			}
			if (whereClause.containsKey(Constants.CREATED_BY)) {
				queryString.append(" AND qa_worksheet.created_by IN("
						+ whereClause.get(Constants.CREATED_BY) + ") ");
			}

			queryString
					.append(" AND qa_worksheet." + Constants.STATUS + " = 2");

			// queryString.append(" AND DATE_FORMAT(qa_worksheet.created_on,'%Y-%m-%d') >= '2014-10-01'  ");
			// queryString.append(" AND DATE_FORMAT(qa_worksheet.created_on,'%Y-%m-%d') <= '2014-10-31' ");

			if (whereClause.containsKey(Constants.QA_REPORT_FROM_DATE)) {
				queryString.append(" AND qa_worksheet.created_on >= "
						+ "'"
						+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
								.getFormattedDate(whereClause
										.get(Constants.QA_REPORT_FROM_DATE)),
								Constants.MYSQL_DATE_FORMAT) + "'");
			}
			if (whereClause.containsKey(Constants.QA_REPORT_TO_DATE)) {
				queryString.append(" AND qa_worksheet.created_on <= "
						+ "'"
						+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
								.getFormattedDate(whereClause
										.get(Constants.QA_REPORT_TO_DATE)),
								Constants.MYSQL_DATE_FORMAT_WITH_TIME) + "'");
			}

			queryString
					.append(" GROUP BY DATE_FORMAT(qa_worksheet.created_on,'%d')  ");
			queryString
					.append(" ) as QA on QA.day = DATE_FORMAT(productivity.created_on, '%d') ");
			queryString.append(" WHERE 1=1 ");

			// queryString.append(" AND DATE_FORMAT(productivity.created_on,'%Y-%m-%d') >= '2014-10-01' ");
			// queryString.append(" AND DATE_FORMAT(productivity.created_on,'%Y-%m-%d') <= '2014-10-30' ");

			if (whereClause.containsKey(Constants.QA_REPORT_FROM_DATE)) {
				queryString.append(" AND productivity.created_on >= "
						+ "'"
						+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
								.getFormattedDate(whereClause
										.get(Constants.QA_REPORT_FROM_DATE)),
								Constants.MYSQL_DATE_FORMAT) + "'");
			}
			if (whereClause.containsKey(Constants.QA_REPORT_TO_DATE)) {
				queryString.append(" AND productivity.created_on <= "
						+ "'"
						+ AkpmsUtil.akpmsDateFormat(AkpmsUtil
								.getFormattedDate(whereClause
										.get(Constants.QA_REPORT_TO_DATE)),
								Constants.MYSQL_DATE_FORMAT_WITH_TIME) + "'");
			}

			if (whereClause.containsKey(Constants.SUB_DEPARTMENT)) {
				if (whereClause.get(Constants.DEPARTMENT).equalsIgnoreCase(
						Constants.PAYMENT_DEPARTMENT_ID)) {

				} else if (whereClause.get(Constants.DEPARTMENT)
						.equalsIgnoreCase(Constants.CHARGE_DEPARTMENT_ID)) {
					if (whereClause.get(Constants.SUB_DEPARTMENT)
							.equalsIgnoreCase(Constants.SIX + "")) {
						queryString
								.append(" AND productivity.productivity_type = CE");
					} else if (whereClause.get(Constants.SUB_DEPARTMENT)
							.equalsIgnoreCase(Constants.EIGHT + "")) {
						queryString
								.append(" AND productivity.productivity_type = Coding");
					} else if (whereClause.get(Constants.SUB_DEPARTMENT)
							.equalsIgnoreCase(Constants.NINE + "")) {
						queryString
								.append(" AND productivity.productivity_type = Demo");
					}

				} else if (whereClause.get(Constants.DEPARTMENT)
						.equalsIgnoreCase(Constants.AR_DEPARTMENT_ID)) {
					queryString.append(" AND productivity.team_id >= "
							+ whereClause.get(Constants.SUB_DEPARTMENT));
				} else if (whereClause.get(Constants.DEPARTMENT)
						.equalsIgnoreCase(Constants.ACCOUNTING_DEPARTMENT_ID)) {

				}
			}

			queryString
					.append(" GROUP BY DATE_FORMAT(productivity.created_on, '%d') ");
			queryString.append(" ORDER By day; ");

			LOGGER.error("findProductivityCountWithUsersAndQAWorksheets : Native SQL :: "
					+ queryString);

			Query query = em.createNativeQuery(queryString.toString());

			reportData.addAll((List<Object[]>) query.getResultList());
		}

		return reportData;
	}
	

}
