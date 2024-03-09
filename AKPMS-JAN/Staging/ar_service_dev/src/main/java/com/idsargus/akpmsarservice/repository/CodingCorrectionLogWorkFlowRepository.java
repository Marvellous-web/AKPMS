package com.idsargus.akpmsarservice.repository;

import java.util.Date;
import java.util.List;

import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.idsargus.akpmsarservice.model.domain.AdjustmentLogWorkFlow;
import com.idsargus.akpmsarservice.model.domain.CodingCorrectionLogWorkFlow;

@RepositoryRestResource(path = CodingCorrectionLogWorkFlowRepository.MODULE_NAME, collectionResourceRel = CodingCorrectionLogWorkFlowRepository.MODULE_NAME)
public interface CodingCorrectionLogWorkFlowRepository
		extends PagingAndSortingRepository<CodingCorrectionLogWorkFlow, Integer>{
	
public static final String MODULE_NAME = "codingCorrectionWorkFlow";
	
	@Override
	@CachePut
	<S extends CodingCorrectionLogWorkFlow> S save(S CodingCorrectionLogWorkFlow);
	
//	@PreAuthorize("hasAuthority('role_admin')")
//	@RestResource(path = "customqueryall", rel = "customqueryall")
//	@Query(value= "select i from CodingCorrectionLogWorkFlow i where (:query is null or " +
//			"CONCAT(i.arProductivity.patientAccountNumber,i.arProductivity.patientName) LIKE %:query%)" +
//			" AND (:nextAction is null or i.nextAction = :nextAction) " +
//			"AND  (:statusCode is null or i.arProductivity.statusCode = :statusCode) " +
//			"AND (:source is null or i.arProductivity.source = :source) " +
//			"AND (:doctor is null or i.doctor.id = :doctor) " +
//			"AND (:insurance is null or i.insurance.id = :insurance)" +
//			" AND (:team is null or i.arProductivity.team.id= :team) " +
//			"AND (:subStatus is null or i.arProductivity.subStatus = :subStatus) " +
//			"AND (:createdFrom is null or DATE(i.createdOn) >= :createdFrom)" +
//			" AND (:createdTo is null or DATE(i.createdOn) <= :createdTo) " +
//			"AND (:followFrom is null or DATE(i.arProductivity.followUpDate) >= :followFrom)" +
//			" AND (:followTo is null or DATE(i.arProductivity.followUpDate) <= :followTo) " +
//			"AND (:createdBy is null or i.createdBy.id = :createdBy) ORDER BY i.id DESC")

	/*
	Status
Patient Name
Patient ID
Batch Number
Sequence Number
Provider
DOS
	 */
	@RestResource(path = "customqueryall", rel = "customqueryall")
	@Query(value= "select i from CodingCorrectionLogWorkFlow i where (:query is null or " +
			"CONCAT(i.arProductivity.patientAccountNumber,i.arProductivity.patientName) LIKE %:query%)" +
			" AND (:nextAction is null or i.nextAction = :nextAction) " +
			"AND  (:statusCode is null or i.arProductivity.statusCode = :statusCode) " +
			"AND (:source is null or i.arProductivity.source = :source) " +
			"AND (COALESCE(:doctor) IS NULL or (i.doctor.id IN (:doctor))) " +
//			"AND (:doctor is null or i.doctor.id = :doctor) " +
			"AND (:companyId is null OR i.company.id= :companyId)" +
			"AND (:groupId is null OR i.group.id= :groupId)" +
			"AND (:insurance is null or i.insurance.id = :insurance)" +
			" AND (:team is null or i.arProductivity.team.id= :team) " +
			"AND (:subStatus is null or i.arProductivity.subStatus = :subStatus) " +
			"AND (:createdFrom is null or DATE(i.createdOn) >= :createdFrom)" +
			" AND (:createdTo is null or DATE(i.createdOn) <= :createdTo) " +
			"AND (:followFrom is null or DATE(i.arProductivity.followUpDate) >= :followFrom)" +
			" AND (:followTo is null or DATE(i.arProductivity.followUpDate) <= :followTo) " +
			"AND (:createdBy is null or i.createdBy.id = :createdBy) ORDER BY CASE WHEN :sortDirection = 'desc' " +
			" THEN CASE " +
			" WHEN :columnName = 'status' THEN i.nextAction "+
			" WHEN :columnName  = 'PatientName' THEN i.arProductivity.patientName " +
			" WHEN :columnName  = 'PatientNumber' THEN i.arProductivity.patientAccountNumber " +
			" WHEN :columnName  = 'BatchNumber' THEN i.batchNo " +
			" WHEN :columnName  = 'Provider' THEN i.doctor.id " +
			" WHEN :columnName  = 'DOS' THEN i.dos " +
			" WHEN :columnName  = 'createdBy' THEN i.createdBy.firstName " +
		//	" WHEN :columnName  = 'modifiedBy' THEN i.modifiedBy.firstName " +
			" WHEN :columnName  = 'modifiedBy' THEN i.modifiedBy "+
			" WHEN :columnName  = 'modifiedOn' THEN i.modifiedOn " +
			" WHEN :columnName  = 'createdOn' THEN i.createdOn " +
			" WHEN :columnName  = 'team' THEN i.arProductivity.team.id " +
			" WHEN :columnName  = 'sequenceNo' THEN i.sequenceNo " +
			" ELSE i.id END " +
			" END DESC, " +
			" CASE WHEN :sortDirection = 'asc' " +
			" THEN CASE " +
			" WHEN :columnName = 'status' THEN i.nextAction "+
			" WHEN :columnName  = 'PatientName' THEN i.arProductivity.patientName " +
			" WHEN :columnName  = 'PatientNumber' THEN i.arProductivity.patientAccountNumber " +
			" WHEN :columnName  = 'BatchNumber' THEN i.batchNo " +
			" WHEN :columnName  = 'Provider' THEN i.doctor.id " +
			" WHEN :columnName  = 'DOS' THEN i.dos " +
			" WHEN :columnName  = 'createdBy' THEN i.createdBy.firstName " +
		//	" WHEN :columnName  = 'modifiedBy' THEN i.modifiedBy.firstName " +
			" WHEN :columnName  = 'modifiedBy' THEN i.modifiedBy "+
			" WHEN :columnName  = 'modifiedOn' THEN i.modifiedOn " +
			" WHEN :columnName  = 'createdOn' THEN i.createdOn " +
			" WHEN :columnName  = 'team' THEN i.arProductivity.team.id " +
			" WHEN :columnName  = 'sequenceNo' THEN i.sequenceNo " +
			" ELSE i.id END " +
			" END ASC, i.id desc ")
	public Page<CodingCorrectionLogWorkFlow> findByQueryAll(@Param("query") String query,
															@Param("columnName") String columnName,
															@Param("sortDirection") String sortDirection,
															@Param("statusCode") String statusCode, @Param("source") String source,
															@Param("doctor") List<Integer> doctor,
															@Param("companyId") Integer companyId,
															@Param("groupId") Integer groupId,
															@Param("nextAction") String nextAction , @Param("insurance") Integer insurance, @Param("team") Integer team, @Param("subStatus") Integer subStatus, @Param("createdFrom") Date createdFrom, @Param("createdTo") Date createdTo, @Param("followFrom") Date followFrom, @Param("followTo") Date followTo, @Param("createdBy") Integer createdBy,
			Pageable pageable);
//@RestResource(path = "customqueryallAsc", rel = "customqueryallAsc")
//	@Query(value= "select i from CodingCorrectionLogWorkFlow i where (:query is null or CONCAT(i.arProductivity.patientAccountNumber,i.arProductivity.patientName) LIKE %:query%) AND (:nextAction is null or i.nextAction = :nextAction) AND  (:statusCode is null or i.arProductivity.statusCode = :statusCode) AND (:source is null or i.arProductivity.source = :source) AND (:doctor is null or i.doctor.id = :doctor) AND (:insurance is null or i.insurance.id = :insurance) AND (:team is null or i.arProductivity.team.id= :team) AND (:subStatus is null or i.arProductivity.subStatus = :subStatus) AND (:createdFrom is null or i.createdOn >= :createdFrom) AND (:createdTo is null or i.createdOn <= :createdTo) AND (:followFrom is null or i.arProductivity.followUpDate >= :followFrom) AND (:followTo is null or i.arProductivity.followUpDate <= :followTo) AND (:createdBy is null or i.createdBy.id = :createdBy) ORDER BY i.id ASC")
//	public Page<CodingCorrectionLogWorkFlow> findByQueryAll(@Param("query") String query, @Param("statusCode") String statusCode, @Param("source") String source, @Param("doctor") Integer doctor,@Param("nextAction") String nextAction , @Param("insurance") Integer insurance, @Param("team") Integer team, @Param("subStatus") Integer subStatus, @Param("createdFrom") Date createdFrom, @Param("createdTo") Date createdTo, @Param("followFrom") Date followFrom, @Param("followTo") Date followTo, @Param("createdBy") Integer createdBy,
//			Pageable pageable);
	@RestResource(path = "count", rel = "count")
	@Query(value= "SELECT count(*) FROM coding_correction_log_workflow i cross join " +
			" ar_productivity arp where i.ar_productivity_id=arp.id", nativeQuery = true)
//	@Query("SELECT count(id)  FROM CodingCorrectionLogWorkFlow")
	public long countById();


	
	@RestResource(path = "worklog", rel = "worklog")
	@Query(value= "SELECT i FROM CodingCorrectionLogWorkFlow i where (i.arProductivity.id= :productivityId) ")
	List<CodingCorrectionLogWorkFlow> getWorkLogs(@Param("productivityId") Integer productivityId);
	
	
	// Arindam Code Changes
//	@Query("select count(id) from CodingCorrectionLogWorkFlow i  where i.nextAction = :nextAction")
//	public long getCodingCorrectionLogWorkFlowCountByworkflowStatus(@Param("nextAction") String nextAction);

	@Query(value= "select count(*) from coding_correction_log_workflow co cross join  ar_productivity arp where co.ar_productivity_id=arp.id and co.workflow_status =:nextAction", nativeQuery= true)
    public long getCodingCorrectionLogWorkFlowCountByworkflowStatus(@Param("nextAction") String nextAction);


}
