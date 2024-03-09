package argus.repo.taskname;

import java.util.List;
import java.util.Map;

import argus.domain.HourlyTask;
import argus.exception.ArgusException;

public interface HourlyTaskDao {

	List<HourlyTask> findAll(
			Map<String, String> orderClauses, Map<String, String> whereClauses, boolean dependency);

	HourlyTask findById(Long id) throws ArgusException;

	/**
	 *
	 * @param name
	 * @return
	 */
	HourlyTask findByName(String name) throws ArgusException;

	void addHourlyTask(HourlyTask hourlyTask) throws ArgusException;

	int totalRecord(Map<String, String> conditionMap) throws ArgusException;

	void updateHourlyTask(HourlyTask hourlyTask) throws ArgusException;

	int changeStatus(long id, boolean status) throws ArgusException;


}
