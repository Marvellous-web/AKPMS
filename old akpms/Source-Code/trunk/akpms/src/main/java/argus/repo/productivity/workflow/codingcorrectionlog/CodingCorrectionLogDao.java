package argus.repo.productivity.workflow.codingcorrectionlog;

import java.util.List;
import java.util.Map;

import argus.domain.CodingCorrectionLogWorkFlow;
import argus.domain.Files;
import argus.exception.ArgusException;

public interface CodingCorrectionLogDao {


	/**
	 *
	 * @param id
	 * @return
	 * @throws ArgusException
	 */
	CodingCorrectionLogWorkFlow findById(Long id) throws ArgusException;

	/**
	 *
	 * @param codingCorrectionLogWorkFlow
	 * @throws ArgusException
	 */
	void addCodingCorrectionLogWorkFlow(CodingCorrectionLogWorkFlow codingCorrectionLogWorkFlow)  throws ArgusException;

	   /**
	    *
	    * @param codingCorrectionLogWorkFlow
	    * @throws ArgusException
	    */
	void updateCodingCorrectionLogWorkFlow(CodingCorrectionLogWorkFlow codingCorrectionLogWorkFlow)  throws ArgusException;

	/**
	 *
	 * @param id
	 * @return
	 * @throws ArgusException
	 */
	CodingCorrectionLogWorkFlow findByProductivityId(Long id) throws ArgusException;

	int totalRecord(Map<String, String> conditionMap) throws ArgusException;

	List<CodingCorrectionLogWorkFlow> findAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses, boolean dependancies)
			throws Exception;

	void saveAttachement(Files file) throws ArgusException;

	void updateAttachement(Files file) throws ArgusException;

	public List<Object[]> countByWorkFlowStatus() throws ArgusException;

}
