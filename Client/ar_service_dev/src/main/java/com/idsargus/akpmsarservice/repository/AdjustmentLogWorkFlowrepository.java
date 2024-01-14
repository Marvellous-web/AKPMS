package com.idsargus.akpmsarservice.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import com.idsargus.akpmsarservice.model.domain.AdjustmentLogWorkFlow;
import org.springframework.web.bind.annotation.CrossOrigin;

//@CrossOrigin(origins = {"*","http://192.168.20.153:3306/"}, allowedHeaders = {"*", "http://192.168.20.153:3306"})
@RepositoryRestResource(path = AdjustmentLogWorkFlowrepository.MODULE_NAME, collectionResourceRel = AdjustmentLogWorkFlowrepository.MODULE_NAME)
public interface AdjustmentLogWorkFlowrepository extends PagingAndSortingRepository<AdjustmentLogWorkFlow, Integer>{
	
	public static final String MODULE_NAME = "adjustmentLogWorkFlow";
	
	@Override
//	@CachePut
	<S extends AdjustmentLogWorkFlow> S save(S adjustmentLogWorkFlow);
	
//	@PreAuthorize("hasAuthority('role_admin')")
	@RestResource(path = "customqueryall", rel = "customqueryall")

//	@Query(value= "select i from AdjustmentLogWorkFlow i where (:query is null or CONCAT(i.arProductivity.patientAccountNumber,i.arProductivity.patientName) LIKE %:query%) AND (:statusCode is null or i.arProductivity.statusCode = :statusCode) AND (:source is null or i.arProductivity.source = :source) AND (:doctor is null or i.doctor.id = :doctor) AND (:insurance is null or i.insurance.id = :insurance) AND (:team is null or i.arProductivity.team.id= :team) AND (:subStatus is null or i.arProductivity.subStatus = :subStatus) AND (:createdFrom is null or i.createdOn > :createdFrom) AND (:createdTo is null or i.createdOn < :createdTo) AND (:followFrom is null or i.arProductivity.followUpDate > :followFrom) AND (:followTo is null or i.arProductivity.followUpDate < :followTo) AND (:createdBy is null or i.createdBy.id = :createdBy) ORDER By i.arProductivity.patientName")
//	public Page<AdjustmentLogWorkFlow> findByQueryAll(@Param("query") String query, @Param("statusCode") String statusCode, @Param("source") String source, @Param("doctor") Integer doctor, @Param("insurance") Integer insurance, @Param("team") Integer team, @Param("subStatus") Integer subStatus, @Param("createdFrom") Date createdFrom, @Param("createdTo") Date createdTo, @Param("followFrom") Date followFrom, @Param("followTo") Date followTo, @Param("createdBy") Integer createdBy,
//			Pageable pageable);

//	@Query(value= "select i from AdjustmentLogWorkFlow i where (:query is null or CONCAT(i.arProductivity.patientAccountNumber,i.arProductivity.patientName) LIKE %:query%) " +
//			"AND (:withoutTimilyFiling is null or i.withoutTimilyFiling = :withoutTimilyFiling) " +
//			"AND (:workFlowStatus is null  or i.workFlowStatus= :workFlowStatus) " +
//			"AND (:statusCode is null or i.arProductivity.statusCode = :statusCode) " +
//			"AND (:source is null or i.arProductivity.source = :source) " +
//			"AND (:doctor is null or i.doctor.id = :doctor) " +
//			"AND (:insurance is null or i.insurance.id = :insurance) " +
//			"AND (:team is null or i.arProductivity.team.id= :team) " +
//			"AND (:subStatus is null or i.arProductivity.subStatus = :subStatus) " +
//			"AND (:createdFrom is null or DATE(i.createdOn) >= :createdFrom) " +
//			"AND (:createdTo is null or DATE(i.createdOn) <= :createdTo) " +
//			"AND (:followFrom is null or DATE(i.arProductivity.followUpDate) >= :followFrom) " +
//			"AND (:followTo is null or DATE(i.arProductivity.followUpDate)<= :followTo) " +
//			"AND (:createdBy is null or i.createdBy.id = :createdBy) ORDER BY i.id DESC")

	/*
	Work Flow
Patient ID
Patient Name
DOS
CPT Code
	 */
	@Query(value= "select i from AdjustmentLogWorkFlow i where (:query is null or CONCAT(i.arProductivity.patientAccountNumber,i.arProductivity.patientName) LIKE %:query%) " +
			"AND (:withoutTimilyFiling is null or i.withoutTimilyFiling = :withoutTimilyFiling) " +
			"AND (:workFlowStatus is null  or i.workFlowStatus= :workFlowStatus) " +
			"AND (:statusCode is null or i.arProductivity.statusCode = :statusCode) " +
			"AND (:source is null or i.arProductivity.source = :source) " +
			"AND (COALESCE(:doctor) IS NULL or (i.doctor.id IN (:doctor))) " +
			//"AND (:doctor is null or i.doctor.id = :doctor) " +
			//"AND (COALESCE(:doctor) IS NULL or (i.doctor.id IN (:doctor)))" +
			"AND (:companyId is null OR i.company.id= :companyId)" +
			"AND (:groupId is null OR i.group.id= :groupId)" +
			"AND (:insurance is null or i.insurance.id = :insurance) " +
			"AND (:team is null or i.arProductivity.team.id= :team) " +
			"AND (:subStatus is null or i.arProductivity.subStatus = :subStatus) " +
			"AND (:createdFrom is null or DATE(i.createdOn) >= :createdFrom) " +
			"AND (:createdTo is null or DATE(i.createdOn) <= :createdTo) " +
			"AND (:followFrom is null or DATE(i.arProductivity.followUpDate) >= :followFrom) " +
			"AND (:followTo is null or DATE(i.arProductivity.followUpDate)<= :followTo) " +
			"AND (:createdBy is null or i.createdBy.id = :createdBy) " +
			" ORDER BY CASE WHEN :sortDirection = 'desc' " +
			" THEN CASE " +
			" WHEN :columnName = 'workFlow' THEN i.workFlowStatus "+
			" WHEN :columnName  = 'PatientName' THEN i.arProductivity.patientName " +
			" WHEN :columnName  = 'PatientId' THEN i.arProductivity.patientAccountNumber " +
			" WHEN :columnName  = 'DOS' THEN i.dos " +
			" WHEN :columnName  = 'cptCode' THEN i.cptcode" +
			" WHEN :columnName  = 'insurance' THEN i.insurance.name " +
			" WHEN :columnName  = 'team' THEN i.arProductivity.team.id " +
			" WHEN :columnName  = 'balanceAmt' THEN i.balanceAmt " +
			" WHEN :columnName  = 'source' THEN i.arProductivity.source " +
			" WHEN :columnName  = 'createdBy' THEN i.createdBy.firstName " +
			" WHEN :columnName  = 'modifiedBy' THEN i.modifiedBy " +
			" WHEN :columnName  = 'modifiedOn' THEN i.modifiedOn " +
			" WHEN :columnName  = 'createdOn' THEN i.createdOn " +
			"  ELSE i.id END " +
			" END DESC, " +
			" CASE WHEN :sortDirection = 'asc' " +
			" THEN CASE " +
			" WHEN :columnName = 'workFlow' THEN i.workFlowStatus "+
			" WHEN :columnName  = 'PatientName' THEN i.arProductivity.patientName " +
			" WHEN :columnName  = 'PatientId' THEN i.arProductivity.patientAccountNumber " +
			" WHEN :columnName  = 'DOS' THEN i.dos " +
			" WHEN :columnName  = 'cptCode' THEN i.cptcode" +
			" WHEN :columnName  = 'insurance' THEN i.insurance.name " +
			" WHEN :columnName  = 'team' THEN i.arProductivity.team.id " +
			" WHEN :columnName  = 'balanceAmt' THEN i.balanceAmt " +
			" WHEN :columnName  = 'source' THEN i.arProductivity.source " +
			" WHEN :columnName  = 'createdBy' THEN i.createdBy.firstName " +
			" WHEN :columnName  = 'modifiedBy' THEN i.modifiedBy " +
			" WHEN :columnName  = 'modifiedOn' THEN i.modifiedOn " +
			" WHEN :columnName  = 'createdOn' THEN i.createdOn " +
			"  ELSE i.id END " +
			" END ASC , i.id desc")
	public Page<AdjustmentLogWorkFlow> findByQueryAll(@Param("query") String query,
													  @Param("statusCode") String statusCode,
													  @Param("source") String source,
													  @Param("doctor") List<Integer> doctor,
													  @Param("companyId") Integer companyId,
													  @Param("groupId") Integer groupId,
													  @Param("withoutTimilyFiling") Integer withoutTimilyFiling,
													  @Param("workFlowStatus") Integer workFlowStatus,
													  @Param("insurance") Integer insurance,
													  @Param("team") Integer team,
													  @Param("subStatus") Integer subStatus,
													  @Param("createdFrom") Date createdFrom,
													  @Param("createdTo") Date createdTo,
													  @Param("followFrom") Date followFrom,
													  @Param("followTo") Date followTo,
													  @Param("createdBy") Integer createdBy,
													  @Param("sortDirection") String sortDirection,
													  @Param("columnName") String columnName	 ,
			Pageable pageable);



//		@RestResource(path = "customqueryall", rel = "customqueryall")
//
//		@Query(value= "select i from AdjustmentLogWorkFlow i where (:query is null or CONCAT(i.arProductivity.patientAccountNumber,i.arProductivity.patientName) LIKE %:query%) " +
//				"AND (:withoutTimilyFiling is null or i.withoutTimilyFiling = :withoutTimilyFiling) " +
//				"AND (:workFlowStatus is null  or i.workFlowStatus= :workFlowStatus) " +
//				"AND (:statusCode is null or i.arProductivity.statusCode = :statusCode) " +
//				"AND (:source is null or i.arProductivity.source = :source) " +
//				"AND (:doctor is null or i.doctor.id = :doctor) " +
//				"AND (:insurance is null or i.insurance.id = :insurance) " +
//				"AND (:team is null or i.arProductivity.team.id= :team) " +
//				"AND (:subStatus is null or i.arProductivity.subStatus = :subStatus) " +
//				"AND (:createdFrom is null or i.createdOn >= :createdFrom) " +
//				"AND (:createdTo is null or i.createdOn <= :createdTo) " +
//				"AND (:followFrom is null or i.arProductivity.followUpDate >= :followFrom) " +
//				"AND (:followTo is null or i.arProductivity.followUpDate <= :followTo) " +
//				"AND (:createdBy is null or i.createdBy.id = :createdBy)ORDER BY CASE WHEN :sortDirection = 'desc' "  +
//				" THEN CASE " +
//				" WHEN :columnName = 'workFlowStatus' THEN i.workFlowStatus " +
//				" WHEN :columnName  = 'patientAccountNumber' THEN i.arProductivity.patientAccountNumber " +
//				" WHEN :columnName  = 'patientName' THEN i.arProductivity.patientName " +
//				" WHEN :columnName  = 'dos' THEN i.arProductivity.dos " +
//				" WHEN :columnName  = 'cptcode' THEN i.arProductivity.cptcode ELSE i.id END " +
//				" END DESC, " +
//				" CASE WHEN :sortDirection = 'asc' " +
//				" THEN CASE " +
//				" WHEN :columnName = 'workFlowStatus' THEN i.workFlowStatus " +
//				" WHEN :columnName  = 'patientAccountNumber' THEN i.arProductivity.patientAccountNumber " +
//				" WHEN :columnName  = 'patientName' THEN i.arProductivity.patientName " +
//				" WHEN :columnName  = 'dos' THEN i.arProductivity.dos " +
//				" WHEN :columnName  = 'cptcode' THEN i.arProductivity.cptcode ELSE i.id END " +
//				" WHEN :columnName  = 'payment' THEN i.paymentType ELSE i.id END " +
//				" END ASC, i.id DESC ")
//		public Page<AdjustmentLogWorkFlow> findByQueryAll(@Param("query") String query,
//														  @Param("statusCode") String statusCode,
//														  @Param("source") String source,
//														  @Param("doctor") Integer doctor,
//														  @Param("withoutTimilyFiling") Integer withoutTimilyFiling,
//														  @Param("workFlowStatus") Integer workFlowStatus,
//														  @Param("insurance") Integer insurance,
//														  @Param("team") Integer team,
//														  @Param("subStatus") Integer subStatus,
//														  @Param("createdFrom") Date createdFrom,
//														  @Param("createdTo") Date createdTo,
//														  @Param("followFrom") Date followFrom,
//														  @Param("followTo") Date followTo,
//														  @Param("createdBy") Integer createdBy,
//				Pageable pageable);

	/* Ascending descending columns   remarks
    Work Flow
    Patient ID
    Patient Name
    DOS
    CPT Code*/


//ORDER By i.arProductivity.patientName
//	public Page<AdjustmentLogWorkFlow> getByPatientName(String patientName, Pageable pageable); 
	
	@Query("SELECT i.arProductivity  FROM AdjustmentLogWorkFlow i")
	List<Integer> getProductivityIds();
	
	@RestResource(path = "count", rel = "count")
	@Query(value= "SELECT count(*) FROM adjustment_log_workflow i cross join " +
			" ar_productivity arp where i.ar_productivity_id=arp.id", nativeQuery = true)
	public long countById();

	@RestResource(path = "check", rel = "check")
	@Query(value= "SELECT i FROM AdjustmentLogWorkFlow i where (:query is null or i.cptcode= :query) ")
	 Page<AdjustmentLogWorkFlow> findAllcount(@Param("query") String query,Pageable pageable);
	
	@RestResource(path = "worklog", rel = "worklog")
	@Query(value= "SELECT i FROM AdjustmentLogWorkFlow i where (i.arProductivity.id= :productivityId) ")
	List<AdjustmentLogWorkFlow> getWorkLogs(@Param("productivityId") Integer productivityId);
	
	//Arindam Code Change
//			@Query("SELECT count(*) FROM AdjustmentLogWorkFlow")
//			public Long getAdjustmentLogWorkFlowCount();     
	//@Query(value= "SELECT count(id) FROM AdjustmentLogWorkFlow where withoutTimilyFiling= :withoutTimilyFiling AND workFlowStatus= :workFlowStatus")
	@Query(value= "SELECT count(id) FROM AdjustmentLogWorkFlow i where (i.withoutTimilyFiling= :withoutTimilyFiling AND i.workFlowStatus= :workFlowStatus) ")
	public long getAdjustmentLogCountTimilyFiling(@Param("withoutTimilyFiling") Integer withoutTimilyFiling, @Param("workFlowStatus") Integer workFlowStatus);
	
	//@Query(value= "SELECT count(i.id) FROM AdjustmentLogWorkFlow i where i.withoutTimilyFiling= :withoutTimilyFiling ")
	@Query(value= "select count(*) from adjustment_log_workflow co cross join " +
			" ar_productivity arp where co.ar_productivity_id=arp.id and co.without_timily_filing =:withoutTimilyFiling"
			, nativeQuery= true)
	public long getAdjustmentLogCount(@Param("withoutTimilyFiling") Integer withoutTimilyFiling);
	
}
