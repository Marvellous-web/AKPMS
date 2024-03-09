package argus.repo.arworkflow;

import java.util.List;

import argus.domain.ArProductivityWorkFlow;

public interface ArProductivityWorkFlowDao {

	ArProductivityWorkFlow findById(String id);

	List<ArProductivityWorkFlow> findAll(String orderBy);

}
