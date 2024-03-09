package argus.repo.productivity.workflow.secondrequestlog;

import java.util.List;
import java.util.Map;

import argus.domain.SecondRequestLogWorkFlow;
import argus.exception.ArgusException;

public interface SecondRequestLogDao {



	/**
	 *
	 * @param id
	 * @return
	 * @throws ArgusException
	 */
	SecondRequestLogWorkFlow findById(Long id) throws ArgusException;

	/**
	 *
	 * @param codingCorrectionLogWorkFlow
	 * @throws ArgusException
	 */
	void addSecondRequestLogWorkFlow(SecondRequestLogWorkFlow secondRequestLogWorkFlow)  throws ArgusException;

	   /**
	    *
	    * @param codingCorrectionLogWorkFlow
	    * @throws ArgusException
	    */
	void updateSecondRequestLogWorkFlow(SecondRequestLogWorkFlow secondRequestLogWorkFlow)  throws ArgusException;

	/**
	 *
	 * @param id
	 * @return
	 * @throws ArgusException
	 */
	SecondRequestLogWorkFlow findByProductivityId(Long id) throws ArgusException;

	List<SecondRequestLogWorkFlow> findAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses, boolean dependancies)
			throws ArgusException;

	int totalRecord(Map<String, String> whereClauses)
			throws ArgusException;
}
