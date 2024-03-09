package com.idsargus.akpmsarservice.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.idsargus.akpmsarservice.model.domain.RefundRequestWorkFlow;
import com.idsargus.akpmsarservice.model.domain.RekeyRequestRecord;
import com.idsargus.akpmscommonservice.entity.RekeyRequestWorkFlowEntity;


@RepositoryRestResource(path = RekeyRequestWorkFlowRepository.MODULE_NAME, collectionResourceRel = RekeyRequestWorkFlowRepository.MODULE_NAME)
public interface RekeyRequestWorkFlowRepository extends PagingAndSortingRepository<RekeyRequestWorkFlowEntity, Integer>{
	
	public static final String MODULE_NAME = "rekeyRequestWorkFlow";
	@Override
//	@CachePut
	<S extends RekeyRequestWorkFlowEntity> S save(S rekeyRequestWorkFlow);
	
//	@PreAuthorize("hasAuthority('role_admin')")
	@RestResource(path = "customqueryall", rel = "customqueryall")
	//@Query(value= "select i from RekeyRequestWorkFlowEntity i where  CONCAT(i.arProductivity.patientAccountNumber,i.arProductivity.patientName) LIKE %:query% AND (:statusCode is null or i.arProductivity.statusCode = :statusCode) AND (i.arProductivity.id= :productivityId) AND (:source is null or i.arProductivity.source = :source) AND (:doctor is null or i.doctor.id = :doctor) AND (:insurance is null or i.insurance.id = :insurance) AND (:team is null or i.arProductivity.team.id= :team) AND (:subStatus is null or i.arProductivity.subStatus = :subStatus) AND (:createdFrom is null or i.createdOn > :createdFrom) AND (:createdTo is null or i.createdOn < :createdTo) AND (:followFrom is null or i.arProductivity.followUpDate > :followFrom) AND (:followTo is null or i.arProductivity.followUpDate < :followTo) AND (:createdBy is null or i.createdBy.id = :createdBy) ORDER By i.arProductivity.patientName")
//	@Query(value= "select i from RekeyRequestWorkFlowEntity i where " +
//			" CONCAT(i.arProductivity.patientAccountNumber,i.arProductivity.patientName)" +
//			" LIKE %:query% AND (:statusCode is null or i.arProductivity.statusCode = :statusCode) " +
//			"AND (:status is null or i.status =:status) AND (:source is null or " +
//			"i.arProductivity.source = :source) AND (:doctor is null or i.doctor.id = :doctor)" +
//			" AND (:insurance is null or i.insurance.id = :insurance) AND (" +
//			":team is null or i.arProductivity.team.id= :team) AND (:subStatus is null or i.arProductivity.subStatus = :subStatus) " +
//			"AND (:createdFrom is null or DATE(i.createdOn) >= :createdFrom) AND (:createdTo is null or DATE(i.createdOn) <= :createdTo) " +
//			"AND (:followFrom is null or DATE(i.arProductivity.followUpDate) >= :followFrom) AND (:followTo is null or " +
//			"DATE(i.arProductivity.followUpDate) <= :followTo) AND (:createdBy is null or i.createdBy.id = :createdBy) ORDER BY i.id DESC")

	@Query(value= "select i from RekeyRequestWorkFlowEntity i where " +
			" CONCAT(i.arProductivity.patientAccountNumber,i.arProductivity.patientName)" +
			" LIKE %:query% AND (:statusCode is null or i.arProductivity.statusCode = :statusCode) " +
			"AND (:status is null or i.status =:status) AND (:source is null or " +
			"i.arProductivity.source = :source) AND (:doctor is null or i.doctor.id = :doctor)" +
			" AND (:insurance is null or i.insurance.id = :insurance) AND (" +
			":team is null or i.arProductivity.team.id= :team) AND (:subStatus is null or i.arProductivity.subStatus = :subStatus) " +
			"AND (:createdFrom is null or DATE(i.createdOn) >= :createdFrom) AND (:createdTo is null or DATE(i.createdOn) <= :createdTo) " +
			"AND (:followFrom is null or DATE(i.arProductivity.followUpDate) >= :followFrom) AND (:followTo is null or " +
			"DATE(i.arProductivity.followUpDate) <= :followTo) AND (:createdBy is null or i.createdBy.id = :createdBy) 	" +
			" ORDER BY CASE WHEN :sortDirection = 'desc' " +
			" THEN CASE " +
					" WHEN :columnName = 'workFlow' THEN i.status "+
					" WHEN :columnName  = 'PatientName' THEN i.arProductivity.patientName " +
					" WHEN :columnName  = 'PatientId' THEN i.arProductivity.patientAccountNumber " +
					" WHEN :columnName  = 'DOS' THEN i.dos " +
			        " WHEN :columnName = 'doctorName' THEN i.doctor.name " +
			        " WHEN :columnName = 'insurance' THEN i.insurance.name "+
					" WHEN :columnName  = 'team' THEN i.arProductivity.team.id " +
			         " WHEN :columnName  = 'batchNumber' THEN i.batchNumber " +
			        " WHEN :columnName  = 'requestReason' THEN i.requestReason " +
			        " WHEN :columnName  = 'modifiedOn' THEN i.modifiedOn " +
			        " WHEN :columnName  = 'createdOn' THEN i.createdOn " +
			         " WHEN :columnName  = 'createdBy' THEN i.createdBy.firstName " +
			         " WHEN :columnName  = 'modifiedBy' THEN i.modifiedBy " +
			// " WHEN :columnName = 'insurance' THEN i.insurance.name "+
			//					" WHEN :columnName  = 'team' THEN i.createdBy.id " +
			//			         " WHEN :columnName  = 'batchNumber' THEN i.batchNumber " +
			//			        " WHEN :columnName  = 'requestReason' THEN i.requestReason " +
					"  ELSE i.id END " +
					" END DESC, " +
					" CASE WHEN :sortDirection = 'asc' " +
					" THEN CASE " +
			" WHEN :columnName = 'workFlow' THEN i.status "+
			" WHEN :columnName  = 'PatientName' THEN i.arProductivity.patientName " +
			" WHEN :columnName  = 'PatientId' THEN i.arProductivity.patientAccountNumber " +
			" WHEN :columnName  = 'DOS' THEN i.dos " +
			" WHEN :columnName = 'doctorName' THEN i.doctor.name " +
			" WHEN :columnName = 'insurance' THEN i.insurance.name "+
			" WHEN :columnName  = 'team' THEN i.arProductivity.team.id " +
			" WHEN :columnName  = 'batchNumber' THEN i.batchNumber " +
			" WHEN :columnName  = 'requestReason' THEN i.requestReason " +
			" WHEN :columnName  = 'modifiedOn' THEN i.modifiedOn " +
			" WHEN :columnName  = 'createdOn' THEN i.createdOn " +
			" WHEN :columnName  = 'createdBy' THEN i.createdBy.firstName " +
			" WHEN :columnName  = 'modifiedBy' THEN i.modifiedBy " +
					"  ELSE i.id END " +
					" END ASC, i.id desc ")
	/*
Work Flow
Patient ID
Patient Name
DOS
Doctor
Insurance
Team
Batch No.
Request Reason
Created By
Created On
Modified By
Modified On

 */
	Page<RekeyRequestWorkFlowEntity> findByQueryAll(@Param("query") String query,
			@Param("statusCode") String statusCode,
													@Param("status") Integer status,
													@Param("source") String source,
													@Param("doctor") Integer doctor,
													@Param("insurance") Integer insurance,
													@Param("team") Integer team,
													@Param("sortDirection") String sortDirection,
													@Param("columnName") String columnName	 ,
													@Param("subStatus") Integer subStatus,
													@Param("createdFrom") Date createdFrom, @Param("createdTo") Date createdTo, @Param("followFrom") Date followFrom, @Param("followTo") Date followTo, @Param("createdBy") Integer createdBy,
			Pageable pageable);
//  ORDER By i.arProductivity.patientName
	@RestResource(path = "count", rel = "count")
	@Query("SELECT count(id)  FROM RekeyRequestWorkFlowEntity")
	public long countById();

	
	@RestResource(path = "worklog", rel = "worklog")
	@Query(value= "SELECT i FROM RekeyRequestWorkFlowEntity i where (i.arProductivity.id= :productivityId) ")
	List<RekeyRequestWorkFlowEntity> getWorkLogs(@Param("productivityId") Integer productivityId);
	
	//@Query("select count(id) from RekeyRequestWorkFlowEntity i where i.status = :status")
	@Query(value = "SELECT count(*) FROM rekey_request_workflow c cross join ar_productivity e  where e.id =c.ar_productivity_id and c.status =:status"
			, nativeQuery=true)
	public long getRekeyRequestCountByStatus(@Param("status") Integer status);

}
