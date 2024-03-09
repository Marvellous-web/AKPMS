package argus.repo.location;

import java.util.List;
import java.util.Map;

import argus.domain.Location;
import argus.exception.ArgusException;

/**
 * 
 * @author vishal.joshi
 * 
 */
public interface LocationDao {
	/**
	 * 
	 * @param id
	 * @return
	 */
	Location findById(Long id) throws ArgusException;

	/**
	 * 
	 * @param name
	 * @return
	 */
	Location findByName(String name);

	/**
	 * 
	 * @param orderClauses
	 * @param whereClauses
	 * @return
	 */
	List<Location> findAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses);

	/**
	 * 
	 * @param location
	 */
	void addLocation(Location location);

	/**
	 * 
	 * @param conditionMap
	 * @return
	 */
	int totalRecord(Map<String, String> conditionMap);

	/**
	 * 
	 * @param ids
	 *            (Comma separated ids)
	 */
	int deleteLocation(List<Long> ids);

	/**
	 * 
	 * @param id
	 *            (location id)
	 * @param status
	 *            (activate: true/inactivate: false)
	 * @return
	 * @throws Exception
	 */
	int changeStatus(long id, boolean status) throws ArgusException;

	/**
	 * to update existing object
	 * 
	 * @param location
	 */
	void updateLocation(Location location);

	List<Object[]> getLocationStats() throws ArgusException;
}
