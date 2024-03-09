package argus.repo.insurance;

import java.util.List;
import java.util.Map;

import argus.domain.Insurance;
import argus.exception.ArgusException;
/**
 *
 * @author vishal.joshi
 *
 */
public interface InsuranceDao
{
	/**
	 *
	 * @param id
	 * @return
	 */
	Insurance findById(Long id) throws ArgusException;

    /**
     *
     * @param name
     * @return
     */
     Insurance findByName(String name);

    /**
     *
     * @param orderClauses
     * @param whereClauses
     * @return
     */
     List<Insurance> findAll(Map<String,String> orderClauses, Map<String,String> whereClauses );

    /**
     *
     * @param insurance
     */
     void addInsurance(Insurance insurance);

    /**
     *
     * @param conditionMap
     * @return
     */
     int totalRecord(Map<String,String> conditionMap);

    /**
     *
     * @param ids (Comma separated ids)
     */
     int deleteInsurance(List<Long> ids);

    /**
     *
     * @param id (insurance id)
     * @param status (activate: true/inactivate: false)
     * @return
     * @throws Exception
     */
     int changeStatus(long id, boolean status) throws ArgusException;

    /**
     * to update existing object
     * @param insurance
     */
     void updateInsurance(Insurance insurance);

	List<Object[]> getInsuranceStats() throws ArgusException;
}
