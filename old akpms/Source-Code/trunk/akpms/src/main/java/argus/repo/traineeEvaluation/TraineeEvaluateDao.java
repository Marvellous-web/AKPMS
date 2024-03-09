package argus.repo.traineeEvaluation;

import java.util.List;
import java.util.Map;

import argus.domain.TraineeEvaluate;
import argus.exception.ArgusException;

public interface TraineeEvaluateDao {

	List<TraineeEvaluate> getEvaluationList(Long traineeid, String type)
			throws ArgusException;

	TraineeEvaluate getCurrentEvaluation(Long traineeId, Long ratedById)
			throws ArgusException;

	void updateTraineeEvaluation(TraineeEvaluate traineeEvaluate)
			throws ArgusException;

	void addTraineeEvaluation(TraineeEvaluate traineeEvaluate)
			throws ArgusException;

	List<Object[]> findAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses) throws ArgusException;

	int totalRecord(Map<String, String> whereClauses)
			throws ArgusException;

	List<Object[]> findEvaluationReportTrainees(Long id)
			throws ArgusException;

}
