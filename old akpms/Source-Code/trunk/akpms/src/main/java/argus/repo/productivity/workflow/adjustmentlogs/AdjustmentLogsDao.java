package argus.repo.productivity.workflow.adjustmentlogs;

import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import argus.domain.AdjustmentLogWorkFlow;
import argus.exception.ArgusException;

public interface AdjustmentLogsDao
{

	/**
    *
    * @param id
    * @return
    */
	AdjustmentLogWorkFlow findById(Long id)  throws ArgusException;
	/**
	 *
	 * @param name
	 * @return
	 */
	AdjustmentLogWorkFlow findByName(String name) throws ArgusException;

    /**
     *
     * @param orderClauses key=(orderBy,sortBy,offset,limit)
     * @param whereClauses
     * @return
     */

    /**
     * @param name It is the name of the Productivity to be searched
     * @param id It is the id of the Productivity to be searched
     */
     List<AdjustmentLogWorkFlow> findAll(Map<String,String> orderClauses, Map<String,String> whereClauses ,boolean dependancies)  throws ArgusException;

    /**
     *
     * @param Productivity
     */
     void addAdjLogWorkFlow(AdjustmentLogWorkFlow adjLogWorkFlow)  throws ArgusException;

    /**
     *
     * @param Productivity
     */
     void updateAdjLogWorkFlow(AdjustmentLogWorkFlow adjLogWorkFlow)  throws ArgusException;


    /**
     *
     * @param conditionMap
     * @return
     */
     int totalRecord(Map<String,String> conditionMap)  throws ArgusException;

     /**
      *
      * @param arProdId
      * @return
      * @throws ArgusException
      */
	AdjustmentLogWorkFlow getAdjLogByArProductivityId(int arProdId)
			throws ArgusException, NoResultException;

	/**
	 *
	 * @return
	 * @throws ArgusException
	 */
	public Object countByWithoutTimilyFilling() throws ArgusException;

	/**
	 *
	 * @return
	 * @throws ArgusException
	 */
	public List<Object[]> getWorkStatusCountByTimilyFilingStatus()
			throws ArgusException;
}
