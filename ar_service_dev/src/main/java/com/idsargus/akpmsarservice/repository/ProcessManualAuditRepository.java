package com.idsargus.akpmsarservice.repository;

import java.util.List;

import com.idsargus.akpmscommonservice.entity.ProcessManualAudit;

public interface ProcessManualAuditRepository {
	/**
	 * 
	 * @param processManualId
	 * @return
	 * @throws Exception
	 */
	List<ProcessManualAudit> getProcessManualModificationById(
			long processManualId) throws Exception;
}
