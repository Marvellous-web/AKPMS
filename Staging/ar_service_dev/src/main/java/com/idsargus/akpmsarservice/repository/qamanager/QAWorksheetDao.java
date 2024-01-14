package com.idsargus.akpmsarservice.repository.qamanager;



import com.idsargus.akpmsarservice.exception.ArgusException;
import com.idsargus.akpmsarservice.model.domain.QAWorksheet;

import java.util.List;
import java.util.Map;

public interface QAWorksheetDao {

	/**
	 * 
	 * @param id
	 * @return QAWorksheet
	 */
	QAWorksheet findById(Integer id) throws ArgusException;

	/**
	 *
	 * @param name
	 * @return QAWorksheet
	 */
	QAWorksheet findByName(String name) throws ArgusException;

	/**
	 * 
	 * @param orderClauses
	 *            key=(orderBy,sortBy,offset,limit)
	 * @param whereClauses
	 * @return
	 */

	List<QAWorksheet> findAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses, boolean dependencies)
			throws ArgusException;

	/**
	 * 
	 * @param QAWorksheet
	 */
	void save(QAWorksheet qaworksheet) throws ArgusException;

	/**
	 * 
	 * @param QAWorksheet
	 */
	void update(QAWorksheet qaworksheet) throws ArgusException;

	/**
	 * delete single record
	 * 
	 * @param id
	 * @return deleted entity id
	 */
	int deleteQAWorksheet(Long id) throws ArgusException;

	/**
	 * 
	 * @param id
	 * @param status
	 */
	int changeStatus(long id, int status) throws ArgusException;

	/**
	 * 
	 * @param conditionMap
	 * @return
	 */
	int totalRecord(Map<String, String> conditionMap) throws ArgusException;

	/**
	 * Find all created by in QAWorksheet
	 * 
	 * @return
	 */
	// Map<Long, String> getCreatedBy();
	/**
	 * Find all {@linkQAWorksheet} names based on criteria
	 * 
	 * @param whereClause
	 * @param orderClauses
	 * @return List of {@linkQAWorksheet} names
	 */
	List<Object[]> findQAWorksheetIdAndName(Map<String, String> whereClause,
			Map<String, String> orderClauses);

	QAWorksheet findById(Long id, boolean dependency);
}
