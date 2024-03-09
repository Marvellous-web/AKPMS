package com.idsargus.akpmsarservice.repository;

import java.util.List;

import com.idsargus.akpmscommonservice.entity.ProcessManualAudit;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.idsargus.akpmsarservice.exception.AppException;
import com.idsargus.akpmsarservice.model.domain.ArFiles;
import com.idsargus.akpmsarservice.model.domain.ProcessManual;
import org.springframework.stereotype.Repository;



@RepositoryRestResource(
	    path = "processManual",
	    collectionResourceRel = "processManual"
	)
public interface ProcessManualRepository extends CrudRepository<ProcessManual, Integer> {
	    String MODULE_NAME = "processManual";

	    ProcessManual getProcessManualById(Integer processManualId, List<String> loadDependancies) throws AppException;

	    List<ProcessManual> getAllProcessManuals(Boolean activeOnly, boolean loadDependancies) throws AppException;

	    List<ProcessManual> getAllProcessManuals(Boolean activeOnly, boolean loadDependancies, String keyword) throws AppException;

	    ProcessManual saveProcessManual(ProcessManual processManual) throws AppException;

	    ProcessManual updateProcessManual(ProcessManual processManual) throws AppException;



	    int deleteProcessManuals(List<Integer> ids) throws AppException;

	    int deleteProcessManuals(Integer id) throws AppException;

	    int changeStatus(Integer int1, Boolean status) throws AppException;

	    void saveAttachement(ArFiles file) throws AppException;

	    ArFiles getAttachedFile(Integer id) throws AppException;

	    int deleteAttachedFile(Integer id) throws AppException;

	    Long getTotalProcessManuals();

	    Long getTotalProcessManualReadByUser(Integer userId);

	    ProcessManual findByName(String name) throws AppException;

	    ProcessManual getProcessManualById(Integer int1, boolean loadDependancies, Boolean activeOnly) throws AppException;

	    List<Object[]> getProcessManualByDepartments(List<Integer> departmentIds) throws AppException;

	    int updateProcessManualsPositons(Double position, Integer long1) throws AppException;

	    int updatePositionById(Double position, Integer Id) throws AppException;


	}

