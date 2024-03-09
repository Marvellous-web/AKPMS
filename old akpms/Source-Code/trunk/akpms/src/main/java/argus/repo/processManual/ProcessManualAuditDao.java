/**
 *
 */
package argus.repo.processManual;

import java.util.List;

import argus.domain.ProcessManualAudit;

/**
 * @author bhupender.s
 *
 */
public interface ProcessManualAuditDao {

	/**
	 * 
	 * @param processManualId
	 * @return
	 * @throws Exception
	 */
	List<ProcessManualAudit> getProcessManualModificationById(
			long processManualId) throws Exception;
}
