package argus.repo.qamanager;

import java.util.List;
import java.util.Map;

import argus.domain.QAWorksheetStaff;
import argus.exception.ArgusException;

public interface QAWorksheetStaffDao {

	/**
    *
    * @param id
    * @return  QAWorksheetStaff
    */
	QAWorksheetStaff findById(Long id)  throws ArgusException;

   /**
    *
    * @param orderClauses key=(orderBy,sortBy,offset,limit)
    * @param whereClauses
    * @return
    */
    List<QAWorksheetStaff> findAll(Map<String,String> orderClauses, Map<String,String> whereClauses, boolean dependencies)  throws ArgusException;

      /**
    *
    * @param QAWorksheetStaff
    */
	void save(QAWorksheetStaff qaworksheetStaff) throws ArgusException;

   /**
    *
    * @param QAWorksheetStaff
    */
	void update(QAWorksheetStaff qaworksheetStaff) throws ArgusException;

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
     * Delete QAWorksheetStaff by (@link QAWorksheet)
     * @param qaworksheetId
     * @return
     * @throws ArgusException
     */
	int deleteQAWorksheetStaffByQAWorsheetId(Long qaworksheetId)
			throws ArgusException;
    
}
