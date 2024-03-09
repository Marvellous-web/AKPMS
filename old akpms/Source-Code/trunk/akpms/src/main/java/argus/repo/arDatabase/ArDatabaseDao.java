package argus.repo.arDatabase;

import java.util.List;
import java.util.Map;

import argus.domain.ArDatabase;
import argus.exception.ArgusException;

/**
 *
 * @author vishal.joshi
 *
 */
public interface ArDatabaseDao {
	/**
	 *
	 * @param id
	 * @return
	 */
	ArDatabase findById(Long id) throws ArgusException;

	/**
	 * 
	 * @param name
	 * @return
	 */
	ArDatabase findByName(String name);

	/**
	 * 
	 * @param orderClauses
	 * @param whereClauses
	 * @return
	 */
	List<ArDatabase> findAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses);

	/**
	 * 
	 * @param arDatabase
	 */
	void addArDatabase(ArDatabase arDatabase);

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
	int deleteArDatabase(List<Long> ids);

	/**
	 * 
	 * @param id
	 *            (arDatabase id)
	 * @param status
	 *            (activate: true/inactivate: false)
	 * @return
	 * @throws Exception
	 */
	int changeStatus(long id, boolean status) throws ArgusException;

	/**
	 * to update existing object
	 * 
	 * @param arDatabase
	 */
	void updateArDatabase(ArDatabase arDatabase);

	List<Object[]> getArDatabaseStats() throws ArgusException;
}
