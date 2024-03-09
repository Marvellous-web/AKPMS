/**
 *
 */
package argus.repo.revenueType;

import java.util.List;
import java.util.Map;

import argus.domain.RevenueType;

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
	RevenueType findById(Long id);

	/**
	 *
	 * @param name
	 * @return
	 */
	RevenueType findByName(String name);

	/**
	 *
	 * @param orderClauses
	 * @param whereClauses
	 * @return
	 */
	List<RevenueType> findAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses);
	
	List<RevenueType> findAll(boolean activeOnly);
}
