package com.idsargus.akpmsarservice.repository.qamanager;


import com.idsargus.akpmsarservice.exception.ArgusException;
import com.idsargus.akpmsarservice.model.domain.QAWorksheetDoctor;

import java.util.List;
import java.util.Map;

public interface QAWorksheetDoctorDao {

	/**
	 * 
	 * @param id
	 * @return QAWorksheetDoctor
	 */
	QAWorksheetDoctor findById(Long id) throws ArgusException;

   /**
    *
    * @param orderClauses key=(orderBy,sortBy,offset,limit)
    * @param whereClauses
    * @return
    */
	List<QAWorksheetDoctor> findAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses, boolean dependencies)
			throws ArgusException;

      /**
	 * 
	 * @param QAWorksheetDoctor
	 */
	void save(QAWorksheetDoctor qaworksheetDoctor) throws ArgusException;

   	/**
	 * 
	 * @param QAWorksheetDoctor
	 */
	void update(QAWorksheetDoctor qaworksheetDoctor) throws ArgusException;

   /**
    * delete single record
    * @param id
    * @return deleted entity id
    */
	int delete(Long id) throws ArgusException;

      /**
    *
    * @param conditionMap
    * @return
    */
    int totalRecord(Map<String,String> conditionMap)  throws ArgusException;

    /**
	 * Delete QAWorksheetDoctor by (@link QAWorksheet)
	 * 
	 * @param qaworksheetId
	 * @return
	 * @throws ArgusException
	 */
	int deleteQAWorksheetDoctorByQAWorsheetId(Long qaworksheetId)
			throws ArgusException;

}
