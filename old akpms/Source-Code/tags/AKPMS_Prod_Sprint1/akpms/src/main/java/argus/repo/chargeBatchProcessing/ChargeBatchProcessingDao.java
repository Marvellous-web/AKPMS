package argus.repo.chargeBatchProcessing;

import java.util.List;
import java.util.Map;

import argus.domain.ChargeBatchProcessing;
import argus.exception.ArgusException;

public interface ChargeBatchProcessingDao {

	ChargeBatchProcessing findById(Long id, boolean dependancies)
			throws ArgusException;

	void save(ChargeBatchProcessing chargeBatchProcessing)
			throws ArgusException;

	void update(ChargeBatchProcessing chargeBatchProcessing)
			throws ArgusException;

	ChargeBatchProcessing getChargeBatchProcessingById(long id,
			boolean dependency) throws ArgusException;

	List<ChargeBatchProcessing> getAllChargeBatchProcessingById(
			Map<String, String> whereclause) throws ArgusException;

	/**
    *
    * @param conditionMap
    * @return
    */
	int totalRecord(Map<String, String> conditionMap,
			ChargeBatchProcessing chargeBatchProcessing);

    /**
	 * 
	 * @param orderClauses
	 * @param whereClauses
	 * @param dependency
	 * @return
	 */
	List<ChargeBatchProcessing> findAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses, boolean dependency);

	List<ChargeBatchProcessing> findAllByDoctorId(Long id);

	List<ChargeBatchProcessing> findAllForReport(
			ChargeBatchProcessing chargeBatchProcessing,
			Map<String, String> whereClause, Map<String, String> orderClause)
			throws ArgusException;

	ChargeBatchProcessing getChargeBatchProcessingShortDetailById(long id)
			throws ArgusException;

}
