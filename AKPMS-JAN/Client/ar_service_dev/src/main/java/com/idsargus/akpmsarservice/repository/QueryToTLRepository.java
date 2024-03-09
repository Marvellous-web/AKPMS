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

import com.idsargus.akpmsarservice.model.domain.ArQueryToTL;
import com.idsargus.akpmscommonservice.entity.QueryToTL;

@RepositoryRestResource(path = QueryToTLRepository.MODULE_NAME, collectionResourceRel = QueryToTLRepository.MODULE_NAME)
public interface QueryToTLRepository extends PagingAndSortingRepository<ArQueryToTL, Integer>{
	
	public static final String MODULE_NAME = "queryToTL";
	@Override
//	@CachePut
	<S extends ArQueryToTL> S save(S arQueryToTL);
	

//	@RestResource(path = "customqueryall", rel = "customqueryall")
//	@Query(value= "select i from ArQueryToTL i where  CONCAT(i.arProductivity.patientAccountNumber,i.arProductivity.patientName) LIKE %:query% " +
//			"AND (:statusCode is null or i.arProductivity.statusCode = :statusCode)" +
//			" AND (:subStatus is null or i.tlStatus = :subStatus) " +
//			"AND (:source is null or i.arProductivity.source = :source)" +
//			" AND (COALESCE(:doctor) IS NULL or (i.arProductivity.doctor.id IN (:doctor)))" +
////	" AND (:doctor is null or i.arProductivity.doctor.id = :doctor)" +
////			where (COALESCE(:doctor) IS NULL or (i.doctor.id IN (:doctor)))
//			" AND (:insurance is null or i.arProductivity.insurance.id = :insurance) " +
//			"AND (:team is null or i.arProductivity.team.id= :team)  " +
//			"AND (:createdFrom is null or DATE(i.createdOn) >= :createdFrom) " +
//			"AND (:createdTo is null or DATE(i.createdOn) <= :createdTo) " +
//			"AND (:followFrom is null or DATE(i.arProductivity.followUpDate) >= :followFrom)" +
//			" AND (:followTo is null or DATE(i.arProductivity.followUpDate) <= :followTo) " +
//			"AND (:createdBy is null or i.createdBy.id = :createdBy) ORDER BY i.id DESC")
//	public Page<ArQueryToTL> findByQueryAll(@Param("query") String query,
//											@Param("status") String status,
//											@Param("statusCode") String statusCode,
//											@Param("source") String source,
//											@Param("doctor") List<Integer> doctor,
//											@Param("insurance") Integer insurance,
//											@Param("team") Integer team,
//											@Param("subStatus") String subStatus,
//											@Param("createdFrom") Date createdFrom,
//											@Param("createdTo") Date createdTo,
//											@Param("followFrom") Date followFrom,
//											@Param("followTo") Date followTo,
//											@Param("createdBy") Integer createdBy,
//			Pageable pageable);

//
@RestResource(path = "customqueryall", rel = "customqueryall")

@Query(value= "select i from ArQueryToTL i where  CONCAT(i.arProductivity.patientAccountNumber,i.arProductivity.patientName) LIKE %:query% " +
		"AND (:statusCode is null or i.arProductivity.statusCode = :statusCode)" +
		" AND (:subStatus is null or i.tlStatus = :subStatus) " +
		"AND (:source is null or i.arProductivity.source = :source)" +
		" AND (:doctor is null or i.arProductivity.doctor.id = :doctor)" +
		" AND (:insurance is null or i.arProductivity.insurance.id = :insurance) " +
		"AND (:team is null or i.arProductivity.team.id= :team)  " +
		"AND (:createdFrom is null or DATE(i.createdOn) >= :createdFrom) " +
		"AND (:createdTo is null or DATE(i.createdOn) <= :createdTo) " +
		"AND (:followFrom is null or DATE(i.arProductivity.followUpDate) >= :followFrom)" +
		" AND (:followTo is null or DATE(i.arProductivity.followUpDate) <= :followTo) " +
		"AND (:createdBy is null or i.createdBy.id = :createdBy)"+
		" ORDER BY CASE WHEN :sortDirection = 'desc' " +
		" THEN CASE " +
		" WHEN :columnName = 'PatientAccNo' THEN i.arProductivity.patientAccountNumber"+
		" WHEN :columnName  = 'PatientName' THEN i.arProductivity.patientName " +
		" WHEN :columnName  = 'dos' THEN i.arProductivity.dos " +
		" WHEN :columnName  = 'cpt' THEN i.arProductivity.cpt" +
		" WHEN :columnName  = 'Source' THEN i.arProductivity.source " +
		" WHEN :columnName  = 'statusCode' THEN i.arProductivity.statusCode " +
//		" WHEN :columnName  = 'ardatabase' THEN i.arProductivity.doctor.id " +
		" WHEN :columnName  = 'doctor' THEN i.arProductivity.doctor.id " +
	    " WHEN :columnName  = 'insurance' THEN i.arProductivity.insurance.name " +
//		" WHEN :columnName  = 'insurance' THEN i.arProductivity.insurance.id " +
		" WHEN :columnName  = 'team' THEN i.arProductivity.team.id " +
		" WHEN :columnName  = 'balance' THEN i.arProductivity.balanceAmount " +
		" WHEN :columnName  = 'createdBy' THEN i.createdBy.firstName " +
		" WHEN :columnName  = 'modifiedBy' THEN i.modifiedBy.firstName " +
		" WHEN :columnName  = 'modifiedOn' THEN i.modifiedOn " +
		" WHEN :columnName  = 'createdOn' THEN i.createdOn " +
		"  ELSE i.id END " +
		" END DESC, " +
		" CASE WHEN :sortDirection = 'asc' " +
		" THEN CASE " +
		" WHEN :columnName = 'PatientAccNo' THEN i.arProductivity.patientAccountNumber"+
		" WHEN :columnName  = 'PatientName' THEN i.arProductivity.patientName " +
		" WHEN :columnName  = 'dos' THEN i.arProductivity.dos " +
		" WHEN :columnName  = 'cpt' THEN i.arProductivity.cpt" +
		" WHEN :columnName  = 'Source' THEN i.arProductivity.source " +
		" WHEN :columnName  = 'statusCode' THEN i.arProductivity.statusCode " +
//		" WHEN :columnName  = 'ardatabase' THEN i.arProductivity.doctor.id " +
//		" WHEN :columnName  = 'insurance' THEN i.arProductivity.insurance.id " +
		" WHEN :columnName  = 'insurance' THEN i.arProductivity.insurance.name " +
		" WHEN :columnName  = 'team' THEN i.arProductivity.team.id " +
//		" WHEN :columnName  = 'doctor' THEN i.arProductivity.doctor.id " +
		" WHEN :columnName  = 'doctor' THEN i.arProductivity.doctor.id " +
		" WHEN :columnName  = 'balance' THEN i.arProductivity.balanceAmount " +
		" WHEN :columnName  = 'createdBy' THEN i.createdBy.firstName " +
		" WHEN :columnName  = 'modifiedBy' THEN i.modifiedBy.firstName " +
		" WHEN :columnName  = 'modifiedOn' THEN i.modifiedOn " +
		" WHEN :columnName  = 'createdOn' THEN i.createdOn " +
		"  ELSE i.id END " +
		" END ASC , i.id desc")
        public Page<ArQueryToTL> findByQueryAll(@Param("query") String query,
										@Param("status") String status,
										@Param("statusCode") String statusCode,
										@Param("source") String source,
										@Param("doctor") Integer doctor,
										@Param("insurance") Integer insurance,
										@Param("team") Integer team,
										@Param("subStatus") String subStatus,
										@Param("createdFrom") Date createdFrom,
										@Param("createdTo") Date createdTo,
										@Param("followFrom") Date followFrom,
										@Param("followTo") Date followTo,
										@Param("createdBy") Integer createdBy,
										@Param("sortDirection") String sortDirection,
										@Param("columnName") String columnName,
										Pageable pageable);

//ORDER By i.arProductivity.patientName
	//AND (:subStatus is null or i.arProductivity.subStatus = :subStatus)
	@RestResource(path = "count", rel = "count")
	@Query("SELECT count(id)  FROM ArQueryToTL")
	public long countById();
	
		
	@RestResource(path = "worklog", rel = "worklog")
	@Query(value= "SELECT i FROM ArQueryToTL i where (i.arProductivity.id= :productivityId) ")
	List<ArQueryToTL> getWorkLogs(@Param("productivityId") Integer productivityId);
	
	//Arindam Code Change
			@Query("SELECT count(id) FROM QueryToTL")
			public Long getQueryToTLCount();


	@Query(value = "SELECT count(*) FROM query_to_tl q where q.tl_status = :status ", nativeQuery = true)
	public Long countByStatus(@Param("status") String status);
}
