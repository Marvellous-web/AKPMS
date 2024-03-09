/**
 *
 */
package argus.repo.revenueType;

import java.util.List;
import java.util.Map;

import argus.domain.RevenueType;
import argus.exception.ArgusException;

/**
 * @author bhupender.s
 *
 */
public interface RevenueTypeDao {
	/**
	 *
	 * @param id
	 * @return
	 */
	RevenueType findById(Long id) throws ArgusException;

	/**
	 *
	 * @param name
	 * @return
	 */
	RevenueType findByName(String name) throws ArgusException;

	/**
	 *
	 * @param orderClauses
	 * @param whereClauses
	 * @return
	 */
	List<RevenueType> findAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses) throws ArgusException;

	List<RevenueType> findAll(boolean activeOnly) throws ArgusException;

	/**
	 * 
	 * @param Revenue
	 *            Type
	 */
	void addRevenueType(RevenueType revenueType) throws ArgusException;

	/**
	 * 
	 * @param conditionMap
	 * @return
	 */
	int totalRecord(Map<String, String> conditionMap) throws ArgusException;

	/**
	 * 
	 * @param ids
	 *            (Comma separated ids)
	 */
	int deleteRevenueType(List<Long> ids) throws ArgusException;

	/**
	 * 
	 * @param id
	 *            (Revenue type id)
	 * @param status
	 *            (activate: true/inactivate: false)
	 * @return
	 * @throws Exception
	 */
	int changeStatus(long id, boolean status) throws ArgusException;

	/**
	 * to update existing object
	 * 
	 * @param Revenue
	 *            type
	 */
	void updateRevenueType(RevenueType revenueType) throws ArgusException;

	List<Object[]> getRevenueTypeStats() throws ArgusException;
}
