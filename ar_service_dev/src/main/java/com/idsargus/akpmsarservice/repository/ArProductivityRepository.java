package com.idsargus.akpmsarservice.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.idsargus.akpmsarservice.model.domain.ArProductivity;
import com.idsargus.akpmscommonservice.entity.ArProductivityEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

//@Validated
@RepositoryRestResource(path = ArProductivityRepository.MODULE_NAME, collectionResourceRel = ArProductivityRepository.MODULE_NAME) 
public interface ArProductivityRepository extends PagingAndSortingRepository<ArProductivity, Integer> {
	  String MODULE_NAME = "arProductivity";
       
	<S extends ArProductivityEntity> S save(S arProductivity );      


//	 @RestResource(path = "customqueryall", rel = "customqueryall")
//     @Query(value= "SELECT i FROM ArProductivity i where  CONCAT(i.patientAccountNumber,i.patientName) LIKE %:query% AND (:statusCode is null or i.statusCode = :statusCode) AND (:databaseId is null or i.database.id = :databaseId) AND (:source is null or i.source = :source) AND (:doctor is null or i.doctor.id = :doctor) AND (:insurance is null or i.insurance.id = :insurance) AND (:team is null or i.team.id = :team) AND (:subStatus is null or i.subStatus = :subStatus) AND (:createdFrom is null or i.createdOn >= :createdFrom) AND (:createdTo is null or i.createdOn <= :createdTo) AND (:followFrom is null or i.followUpDate > :followFrom) AND (:followto is null or i.followUpDate < :followto) AND (:createdBy is null or i.createdBy.id = :createdBy) ORDER BY i.id DESC")
//	 Page<ArProductivity> findByQueryAll(@Param("query") String query, @Param("statusCode") String statusCode,
//										 @Param("databaseId") Integer databaseId,
//										 @Param("source") String source, @RequestBody Integer doctor , @Param("insurance") Integer insurance,
//										 @Param("team") Integer team, @Param("subStatus") Integer subStatus, @Param("createdFrom") Date createdFrom,
//										 @Param("createdTo") Date createdTo, @Param("followFrom") Date followFrom, @Param("followto") Date followto,
//										 @Param("createdBy") Integer createdBy, Pageable pageable);

//	@RestResource(path = "customqueryall", rel = "customqueryall")
//	@Query(value= "SELECT * FROM ar_productivity i where  CONCAT(i.patient_account_number,i.patient_name) LIKE %:query% AND (COALESCE(:doctor) IS NULL or (i.provider_id IN (:doctor))) AND" +
//			" (:statusCode is null or i.status_code = :statusCode) AND (:databaseId is null or i.ar_database_id = :databaseId) AND" +
//			" (:source is null or i.source = :source) AND " +
//			"(:insurance is null or i.insurance_id = :insurance) AND (:team is null or i.team_id = :team) AND" +
//			" (:subStatus is null or i.sub_status = :subStatus) AND (:createdFrom is null or DATE(i.created_on) >= :createdFrom)" +
//			" AND (:createdTo is null or DATE(i.created_on) <= :createdTo) AND (:followFrom is null or DATE(i.follow_up_date) >= :followFrom) AND" +
//			" (:followto is null or DATE(i.follow_up_date) <= :followto) AND " +
//			"  (:isHd is null or (:isHd = 'true' and (i.cpt >= 2 and i.balance_amount >= 700)) " +
//			"or (:isHd = 'false' and (i.cpt < 2 and i.balance_amount < 700)))" +
//			" AND" +
//			" (:createdBy is null or i.created_by = :createdBy) " +
//			"ORDER BY CASE WHEN :sortDirection = 'desc' " +
//			//Patient Acc No	Patient Name	DOS	CPT	Source	Status Code
//			" THEN CASE " +
//			" WHEN :columnName = 'ardatabase' THEN i.ar_database_id "+
//			" WHEN :columnName  = 'insurance' THEN i.insurance_id " +
//			" WHEN :columnName  = 'team' THEN i.team_id " +
//			" WHEN :columnName  = 'doctor' THEN i.provider_id " +
//			" WHEN :columnName  = 'balance' THEN i.balance_amount " +
//			//" WHEN :columnName  = 'hdnonhd' THEN i.email " +
//			" WHEN :columnName  = 'createdBy' THEN i.created_by " +
//			" WHEN :columnName  = 'createdOn' THEN i.created_on " +
//			" WHEN :columnName = 'PatientAccNo' THEN i.patient_account_number "+
//			" WHEN :columnName = 'PatientName' THEN i.patient_name "+
//			" WHEN :columnName = 'dos' THEN i.dos "+
//			" WHEN :columnName = 'cpt' THEN i.cpt "+
//			" WHEN :columnName = 'Source' THEN i.source "+
//			" WHEN :columnName = 'statusCode' THEN i.status_code "+
//			" WHEN :columnName  = 'modifiedOn' THEN i.modified_on " +
//			" WHEN :columnName  = 'modifiedBy' THEN i.modified_by" +
////			" WHEN :columnName  = 'createdOn' THEN i.createdOn " +
////			" WHEN :columnName  = 'createdBy' THEN i.createdBy.firstName " +
//
//			" ELSE i.id END " +
//			" END DESC, " +
//			" CASE WHEN :sortDirection = 'asc' " +
//			" THEN CASE " +
//			" WHEN :columnName = 'ardatabase' THEN i.ar_database_id "+
//			" WHEN :columnName  = 'balance' THEN i.balance_amount " +
//			" WHEN :columnName  = 'insurance' THEN i.insurance_id " +
//			" WHEN :columnName  = 'team' THEN i.team_id " +
//			" WHEN :columnName  = 'doctor' THEN i.provider_id " +
//			//" WHEN :columnName  = 'hdnonhd' THEN i.email " +
//			" WHEN :columnName  = 'createdBy' THEN i.created_by " +
//			" WHEN :columnName  = 'createdOn' THEN i.created_on " +
//			" WHEN :columnName = 'PatientAccNo' THEN i.patient_account_number "+
//			" WHEN :columnName = 'PatientName' THEN i.patient_name "+
//			" WHEN :columnName = 'dos' THEN i.dos "+
//			" WHEN :columnName = 'cpt' THEN i.cpt "+
//			" WHEN :columnName = 'Source' THEN i.source "+
//			" WHEN :columnName = 'statusCode' THEN i.status_code "+
//			" WHEN :columnName  = 'modifiedOn' THEN i.modified_on " +
//			" WHEN :columnName  = 'modifiedBy' THEN i.modified_by" +
//			" ELSE i.id END " +
//			" END ASC , i.id desc",
//			nativeQuery = true)
//
//	Page<ArProductivity> findByQueryAll(@Param("query") String query, @Param("statusCode") String statusCode,
//										@Param("databaseId") Integer databaseId,
//										@Param("source") String source, @Param("doctor") List<Integer> doctor , @Param("insurance") Integer insurance,
//										@Param("team") Integer team, @Param("subStatus") Integer subStatus, @Param("createdFrom") Date createdFrom,
//										@Param("createdTo") Date createdTo, @Param("followFrom") Date followFrom, @Param("followto") Date followto,
//										@Param("createdBy") Integer createdBy,
//										@Param("columnName") String columnName,
//										@Param("sortDirection") String sortDirection,
//										@Param("isHd") String isHd, Pageable pageable);
	@RestResource(path = "customqueryall", rel = "customqueryall")
	@Query(value= "SELECT * FROM ar_productivity i where  CONCAT(i.patient_account_number,i.patient_name) LIKE %:query% AND (COALESCE(:doctor) IS NULL or (i.provider_id IN (:doctor))) AND" +
			" (:statusCode is null or i.status_code = :statusCode) AND (:databaseId is null or i.ar_database_id = :databaseId) AND" +
			" (:groupId is null or i.group_id = :groupId) AND " +
			" (:source is null or i.source = :source) AND " +
			"(:insurance is null or i.insurance_id = :insurance) AND (:team is null or i.team_id = :team) AND" +
			" (:subStatus is null or i.sub_status = :subStatus) AND (:createdFrom is null or DATE(i.created_on) >= :createdFrom)" +
			" AND (:createdTo is null or DATE(i.created_on) <= :createdTo) AND (:followFrom is null or DATE(i.follow_up_date) >= :followFrom) AND" +
			" (:followto is null or DATE(i.follow_up_date) <= :followto) AND " +
			" (:dosFrom is null or DATE(i.dos_from) >= :dosFrom) AND " +
			" (:dosTo is null or DATE(i.dos_to) <= :dosTo) AND " +
			"  (:isHd is null or (:isHd = 'true' and (i.cpt >= 2 and i.balance_amount >= 700)) " +
			"or (:isHd = 'false' and (i.cpt < 2 and i.balance_amount < 700)))" +
			" AND" +
			" (:createdBy is null or i.created_by = :createdBy) " +
			"ORDER BY CASE WHEN :sortDirection = 'desc' " +
			//Patient Acc No	Patient Name	DOS	CPT	Source	Status Code
			" THEN CASE " +
			" WHEN :columnName = 'ardatabase' THEN i.ar_database_id "+
			" WHEN :columnName  = 'insurance' THEN i.insurance_id " +
			" WHEN :columnName  = 'team' THEN i.team_id " +
			" WHEN :columnName  = 'doctor' THEN i.provider_id " +
			" WHEN :columnName  = 'balance' THEN i.balance_amount " +
			//" WHEN :columnName  = 'hdnonhd' THEN i.email " +
			" WHEN :columnName  = 'createdBy' THEN i.created_by " +
			" WHEN :columnName  = 'createdOn' THEN i.created_on " +
			" WHEN :columnName = 'PatientAccNo' THEN i.patient_account_number "+
			" WHEN :columnName = 'PatientName' THEN i.patient_name "+
			" WHEN :columnName = 'dos' THEN i.dos "+
			" WHEN :columnName = 'dosFrom' THEN i.dos_from "+
			" WHEN :columnName = 'dosTo' THEN i.dos_to "+
			" WHEN :columnName = 'cpt' THEN i.cpt "+
			" WHEN :columnName = 'Source' THEN i.source "+
			" WHEN :columnName = 'statusCode' THEN i.status_code "+
			" WHEN :columnName  = 'modifiedOn' THEN i.modified_on " +
			" WHEN :columnName  = 'modifiedBy' THEN i.modified_by" +
//			" WHEN :columnName  = 'createdOn' THEN i.createdOn " +
//			" WHEN :columnName  = 'createdBy' THEN i.createdBy.firstName " +

			" ELSE i.id END " +
			" END DESC, " +
			" CASE WHEN :sortDirection = 'asc' " +
			" THEN CASE " +
			" WHEN :columnName = 'ardatabase' THEN i.ar_database_id "+
			" WHEN :columnName  = 'balance' THEN i.balance_amount " +
			" WHEN :columnName  = 'insurance' THEN i.insurance_id " +
			" WHEN :columnName  = 'team' THEN i.team_id " +
			" WHEN :columnName  = 'doctor' THEN i.provider_id " +
			//" WHEN :columnName  = 'hdnonhd' THEN i.email " +
			" WHEN :columnName  = 'createdBy' THEN i.created_by " +
			" WHEN :columnName  = 'createdOn' THEN i.created_on " +
			" WHEN :columnName = 'PatientAccNo' THEN i.patient_account_number "+
			" WHEN :columnName = 'PatientName' THEN i.patient_name "+
			" WHEN :columnName = 'dos' THEN i.dos "+
			" WHEN :columnName = 'dosFrom' THEN i.dos_from "+
			" WHEN :columnName = 'dosTo' THEN i.dos_to "+
			" WHEN :columnName = 'cpt' THEN i.cpt "+
			" WHEN :columnName = 'Source' THEN i.source "+
			" WHEN :columnName = 'statusCode' THEN i.status_code "+
			" WHEN :columnName  = 'modifiedOn' THEN i.modified_on " +
			" WHEN :columnName  = 'modifiedBy' THEN i.modified_by" +
			" ELSE i.id END " +
			" END ASC , i.id desc",
			nativeQuery = true)

	Page<ArProductivity> findByQueryAll(@Param("query") String query, @Param("statusCode") String statusCode,
										@Param("databaseId") Integer databaseId,
										@Param("source") String source, @Param("doctor") List<Integer> doctor , @Param("insurance") Integer insurance,
										@Param("groupId") Integer groupId,
										@Param("team") Integer team, @Param("subStatus") Integer subStatus, @Param("createdFrom") Date createdFrom,
										@Param("createdTo") Date createdTo, @Param("followFrom") Date followFrom, @Param("followto") Date followto,
										@Param("createdBy") Integer createdBy,
										@Param("dosFrom") Date dosFrom,@Param("dosTo") Date dosTo,
										@Param("columnName") String columnName,
										@Param("sortDirection") String sortDirection,
										@Param("isHd") String isHd, Pageable pageable);


//	Page<ArProductivityEntity> getByPatientName(String patientName, Pageable pageable);

	List<ArProductivity> getByPatientAccountNumber(String patientAccountNumber);
	@RestResource(path = "all", rel = "all") 
	@Query("SELECT i FROM ArProductivityEntity i")
	Page<ArProductivityEntity> getAll(Pageable pageable);

	//@RestResource(path = "all", rel = "all")
//	@Query("SELECT i FROM ArProductivityEntity i")
//	List<ArProductivityEntity> getAllArPro();
	@RestResource(path = "count", rel = "count")
	@Query("SELECT count(id)  FROM ArProductivityEntity") 
	Long countById();

	@Query("SELECT count(i.id) FROM ArProductivityEntity i where i.followUpDate= :followUpDate ")
	long getFollowupCount(@Param("followUpDate") Date followUpDate);
	
	// comment for build
	//   why don't you fetch data. get it
	// more
	
//	
//	@RestResource(path = "customqueryall", rel = "customqueryall") 
//	
//    @Query(" SELECT i FROM ArProductivityEntity i where " +
//			 " CONCAT(i.patientAccountNumber,i.patientName)" +
//			 " LIKE %:query% AND (:statusCode is null or i.statusCode = :statusCode)" +
//			 " AND (:source is null or i.source = :source) AND " +
//			 "(:doctor is null or i.doctor.id = :doctor) AND " +
//			 "(:insurance is null or i.insurance.id = :insurance) AND (:team is null or i.team.id = :team) AND (:subStatus is null or i.subStatus = :subStatus) AND (:createdFrom is null or i.createdOn > :createdFrom) AND (:createdTo is null or i.createdOn < :createdTo) AND (:followFrom is null or i.followUpDate > :followFrom) AND (:followto is null or i.followUpDate < :followto) AND (:createdBy is null or i.createdBy.id = :createdBy) ORDER BY i.id DESC")
//	 Page<ArProductivityEntity> findByQueryAll(@Param("query") String query, @Param("statusCode") String statusCode,
//			@Param("source") String source, @Param("doctor") Integer doctor, @Param("insurance") Integer insurance,
//			@Param("team") Integer team, @Param("subStatus") Integer subStatus, @Param("createdFrom") Date createdFrom,
//			@Param("createdTo") Date createdTo, @Param("followFrom") Date followFrom, @Param("followto") Date followto,
//			@Param("createdBy") Integer createdBy, Pageable pageable); 
	
}
