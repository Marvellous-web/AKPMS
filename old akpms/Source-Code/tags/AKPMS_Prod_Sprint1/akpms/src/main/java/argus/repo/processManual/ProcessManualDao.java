package argus.repo.processManual;

import java.util.List;

import argus.domain.Files;
import argus.domain.ProcessManual;
import argus.exception.ArgusException;

public interface ProcessManualDao {


	/**
	 *
	 * @param processManualId
	 * @param loadDependancies
	 * @return
	 * @throws ArgusException
	 */
	 ProcessManual getProcessManualById(long processManualId,
			List<String> loadDependancies) throws ArgusException;
	/**
	 *
	 * @return List<ProcessManual>
	 * @throws ArgusException
	 */
	 List<ProcessManual> getAllProcessManuals(Boolean activeOnly, boolean loadDependancies) throws ArgusException;

	/**
	 *
	 * @return List<ProcessManual>
	 * @throws ArgusException
	 */
	 List<ProcessManual> getAllProcessManuals(Boolean activeOnly, boolean loadDependancies, String keyword) throws ArgusException;

	/**
	 *
	 * @param processManual
	 * @throws ArgusException
	 */
	 void saveProcessManual(ProcessManual processManual) throws ArgusException;

	/**
	 *
	 * @param processManual
	 * @throws ArgusException
	 */
	 void updateProcessManual(ProcessManual processManual)
			throws ArgusException;


	/**
	 * function to delete multiple records
	 * @param processManualId
	 * @throws ArgusException
	 */
	 int deleteProcessManuals(List<Long> ids) throws ArgusException;

	/**
	 * function to delete single records
	 * @param processManualId
	 * @throws ArgusException
	 */
	 int deleteProcessManuals(Long id) throws ArgusException;

	/**
	 *
	 * @param id
	 * @param status
	 */
	 int changeStatus(long id, boolean status) throws ArgusException;

	/**
	 *
	 * @param file
	 * @throws ArgusException
	 */
	 void saveAttachement(Files file) throws ArgusException;

	Files getAttachedFile(Long id) throws ArgusException;

	 int deleteAttachedFile(Long id) throws ArgusException;


	 Long  getTotalProcessManuals();

	 Long getTotalProcessManualReadByUser(Long userId);

	 ProcessManual findByName(String name) throws ArgusException;

	 ProcessManual getProcessManualById(long processManualId,
			boolean loadDependancies, Boolean activeOnly) throws ArgusException;

	List<Object[]> getProcessManualByDepartments(List<Long> departmentIds)
			throws ArgusException;

}
