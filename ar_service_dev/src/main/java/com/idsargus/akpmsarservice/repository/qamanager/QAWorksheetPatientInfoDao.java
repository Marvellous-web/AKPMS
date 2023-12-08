package com.idsargus.akpmsarservice.repository.qamanager;

import com.idsargus.akpmsarservice.exception.ArgusException;
import com.idsargus.akpmsarservice.model.domain.QAWorksheetPatientInfo;

import java.util.List;
import java.util.Map;

public interface QAWorksheetPatientInfoDao {
	/**
	 * Find {@see QAWorksheetPatientInfo} by id
	 * 
	 * @param id
	 * @return {@see QAWorksheetPatientInfo}
	 * @throws ArgusException
	 *             if something goes wrong.
	 */
	QAWorksheetPatientInfo findById(Long id) throws ArgusException;

	/**
	 * Find all {@see QAWorksheetPatientInfo} on basis of where clauses
	 * and return ordered result based on order by clause
	 * 
	 * @param whereClause
	 * @param orderByClause
	 * @return {@see QAWorksheetPatientInfo}
	 * @throws ArgusException
	 *             if something goes wrong.
	 */
	List<QAWorksheetPatientInfo> findAll(Map<String, String> whereClause,
			Map<String, String> orderByClause, boolean dependencies) throws ArgusException;

	/**
	 * Save {@see QAWorksheetPatientInfo}
	 * 
	 * @param qaWorksheetPatientInfo
	 * @throws ArgusException
	 *             if something goes wrong.
	 */
	void save(QAWorksheetPatientInfo qaWorksheetPatientInfo)
			throws ArgusException;

	/**
	 * Update {@see QASamplingAccountingProductivity}
	 * 
	 * @param qaSamplingAccountingProductivity
	 * @throws ArgusException
	 *             if something goes wrong.
	 */
	void update(QAWorksheetPatientInfo qaWorksheetPatientInfo)
			throws ArgusException;

	/**
	 * Delete {@see QAWorksheetPatientInfo} by supplied id
	 * 
	 * @param qaSamplingAccountingProductivity
	 * @throws ArgusException
	 *             if something goes wrong.
	 */
	Long delete(Long id);

	/**
	 * Find count of total records {@see QASamplingAccountingProductivity}
	 * 
	 * @param conditionMap
	 * @return count of records found in table
	 * @throws ArgusException
	 */
	int totalRecord(Map<String, String> conditionMap) throws ArgusException;
}
