package argus.repo.productivity;

import java.util.List;
import java.util.Map;

import argus.domain.ArProductivity;
import argus.exception.ArgusException;

public interface ArProductivityDao
{

	/**
    *
    * @param id
    * @return
    */
	ArProductivity findById(Long id)  throws ArgusException;
	/**
	 *
	 * @param name
	 * @return
	 */
	 ArProductivity findByName(String name) throws ArgusException;

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
     List<ArProductivity> findAll(Map<String,String> orderClauses, Map<String,String> whereClauses ,boolean dependancies)  throws ArgusException;

    /**
     *
     * @param Productivity
     */
     void addProductivity(ArProductivity arProductivity)  throws ArgusException;

    /**
     *
     * @param Productivity
     */
     void updateProductivity(ArProductivity arProductivity)  throws ArgusException;


    /**
     *
     * @param conditionMap
     * @return
     */
     int totalRecord(Map<String,String> conditionMap)  throws ArgusException;

     /**
	 * 
	 * @return
	 * @throws ArgusException
	 */
	List<Object[]> countByWorkFlow() throws ArgusException;

}
