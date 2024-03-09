package argus.repo.qamanager;

import java.util.List;
import java.util.Map;

import argus.domain.QAProductivitySampling;
import argus.domain.QCPointChecklist;
import argus.exception.ArgusException;

public interface QCPointChecklistDao {

	/**
	 * Find {@see QCPointChecklist} by id
	 *
	 * @param id
	 * @return {@see QCPointChecklist}
	 * @throws ArgusException
	 *             if something goes wrong.
	 */
	QCPointChecklist findById(Long id) throws ArgusException;

	/**
	 * Find all {@see QCPointChecklist} on basis of where clauses
	 * and return ordered result based on order by clause
	 *
	 * @param whereClause
	 * @param orderByClause
	 * @return {@see QCPointChecklist}
	 * @throws ArgusException
	 *             if something goes wrong.
	 */
	List<QCPointChecklist> findAll(Map<String, String> whereClause,
			Map<String, String> orderByClause,  boolean dependencies) throws ArgusException;

	/**
	 * Save {@see QCPointChecklist}
	 *
	 * @param QCPointChecklist
	 * @throws ArgusException
	 *             if something goes wrong.
	 */
	void save(QCPointChecklist qcPointChecklist)
			throws ArgusException;

	/**
	 * Update {@see QASamplingAccountingProductivity}
	 *
	 * @param qaSamplingAccountingProductivity
	 * @throws ArgusException
	 *             if something goes wrong.
	 */
	void update(QCPointChecklist qcPointChecklist)
			throws ArgusException;

	/**
	 * Delete {@see QCPointChecklist} by supplied id
	 *
	 * @param id
	 * @throws ArgusException
	 *             if something goes wrong.
	 */
	Long delete(Long id);

	Long delete(Long qcPointId, Long qaPatientInfoId);

	/**
	 * Delete {@see QCPointChecklist} by {@link QCPoint} id and {@link QAProductivitySampling} id
	 *
	 * @param qcPointId
	 * @param qaProductivitySamplingId
	 * @throws ArgusException
	 *             if something goes wrong.
	 */
	Long deleteByQCPointIdAndQAProductivitySamplingId(Long qcPointId, Long qaProductivitySamplingId);

	/**
	 * Find count of total records {@see QCPointChecklist}
	 *
	 * @param conditionMap
	 * @return count of records found in table
	 * @throws ArgusException
	 */
	int totalRecord(Map<String, String> conditionMap) throws ArgusException;

	/**
	 * Find mostly errors done by users on {@see QCPoint} {@see
	 * QCPointChecklist}
	 * 
	 * @param whereClauses
	 * @return List<Object[]>
	 * @throws ArgusException
	 */
	List<Object[]> findMostlyErrorsDoneByUsers (Map<String, String> whereClauses) throws ArgusException;


	/**
	 * Delete all qc point checklist on sample id
	 * 
	 * @param qaProductivitySamplingId
	 * @return
	 */
	Long deleteByQAProductivitySamplingId(Long qaProductivitySamplingId);

	Long deleteByQAProductivitySamplingId(Long qaProductivitySamplingId,
			Long patientInfoId);
}
