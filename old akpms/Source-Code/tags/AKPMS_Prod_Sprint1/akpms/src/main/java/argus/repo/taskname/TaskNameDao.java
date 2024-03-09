package argus.repo.taskname;

import java.util.List;

import argus.domain.TaskName;

public interface TaskNameDao {

	List<TaskName> findAll();

}
