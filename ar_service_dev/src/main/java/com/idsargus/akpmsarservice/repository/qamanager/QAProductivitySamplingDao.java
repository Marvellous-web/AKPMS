package com.idsargus.akpmsarservice.repository.qamanager;



import com.idsargus.akpmsarservice.exception.ArgusException;
import com.idsargus.akpmsarservice.model.domain.QAProductivitySampling;
import com.idsargus.akpmsarservice.model.domain.QAWorksheetPatientInfo;

import java.util.List;
import java.util.Map;

public interface QAProductivitySamplingDao {
	/**
	 * Find {@see QASamplingAccountingProductivity} by id
	 *
	 * @param id
	 * @return {@see QASamplingAccountingProductivity}
	 * @throws ArgusException
	 *             if something goes wrong.
	 */
	QAProductivitySampling findById(Long id) throws ArgusException;

	/**
	 * Find all {@see QASamplingAccountingProductivity} on basis of where
	 * clauses and return ordered result based on order by clause
	 * 
	 * @param whereClause
	 * @param orderByClause
	 * @return {@see QASamplingAccountingProductivity}
	 * @throws ArgusException
	 *             if something goes wrong.
	 */
	List<QAProductivitySampling> findAll(Map<String, String> whereClause,
			Map<String, String> orderByClause, boolean dependencies)
			throws ArgusException;

	/**
	 * Save {@see QASamplingAccountingProductivity}
	 *
	 * @param qaSamplingAccountingProductivity
	 * @throws ArgusException
	 *             if something goes wrong.
	 */
	void save(QAProductivitySampling qaSamplingAccountingProductivity)
			throws ArgusException;

	/**
	 * Update {@see QASamplingAccountingProductivity}
	 *
	 * @param qaSamplingAccountingProductivity
	 * @throws ArgusException
	 *             if something goes wrong.
	 */
	void update(QAProductivitySampling qaSamplingAccountingProductivity)
			throws ArgusException;

	/**
	 * Delete {@see QASamplingAccountingProductivity} by supplied id
	 *
	 * @param qaSamplingAccountingProductivity
	 * @throws ArgusException
	 *             if something goes wrong.
	 */
	Long delete(Long id);

	List<QAWorksheetPatientInfo> deleteDependencies(Long id) throws Exception;

	/**
	 * Find count of total records {@see QASamplingAccountingProductivity}
	 *
	 * @param conditionMap
	 * @return count of records found in table
	 * @throws ArgusException
	 */
	int totalRecord(Map<String, String> conditionMap) throws ArgusException;

	List<Object[]> findProductivityCountWithUsersAndQAWorksheets(
			Map<String, String> whereClause, Map<String, String> orderClause)
			throws ArgusException;

	List<Object[]> findQAWorksheetUserReportData(Map<String, String> whereClause)
			throws ArgusException;

	List<Object[]> findExecutedUsersRecords(Map<String, String> whereClause,
			Short qaWorksheetType)
			throws ArgusException;

	List<Object[]> monthlyQASummary(Map<String, String> whereClause)
			throws ArgusException;
}
