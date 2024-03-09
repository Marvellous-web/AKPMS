package com.idsargus.akpmsarservice.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.idsargus.akpmsarservice.model.domain.RequestCheckTracerWorkFlow;
import com.idsargus.akpmscommonservice.entity.RekeyRequestWorkFlowEntity;

@RepositoryRestResource(path = RequestCheckTracerWorkFlowRepository.MODULE_NAME, collectionResourceRel = RequestCheckTracerWorkFlowRepository.MODULE_NAME)
public interface RequestCheckTracerWorkFlowRepository extends CrudRepository<RequestCheckTracerWorkFlow, Integer>{
	
	public static final String MODULE_NAME = "requestCheckTracerWorkFlow";
	
	@Override
//	@CachePut
	<S extends RequestCheckTracerWorkFlow> S save(S requestCheckTracerWorkFlow);
	
//	@PreAuthorize("hasAuthority('role_admin')")
	@RestResource(path = "customqueryall", rel = "customqueryall")
	@Query(value= "select i from RequestCheckTracerWorkFlow i where  CONCAT(i.arProductivity.patientAccountNumber,i.arProductivity.patientName) LIKE %:query% AND (:statusCode is null or i.arProductivity.statusCode = :statusCode) AND (:source is null or i.arProductivity.source = :source) AND (:doctor is null or i.arProductivity.doctor.id = :doctor) AND (:insurance is null or i.arProductivity.insurance.id = :insurance) AND (:team is null or i.arProductivity.team.id= :team) AND (:subStatus is null or i.arProductivity.subStatus = :subStatus) AND (:createdFrom is null or i.createdOn > :createdFrom) AND (:createdTo is null or i.createdOn < :createdTo) AND (:followFrom is null or i.arProductivity.followUpDate > :followFrom) AND (:followTo is null or i.arProductivity.followUpDate < :followTo) AND (:createdBy is null or i.createdBy.id = :createdBy) ORDER By i.arProductivity.patientName")
	public Page<RequestCheckTracerWorkFlow> findByQueryAll(@Param("query") String query, @Param("statusCode") String statusCode, @Param("source") String source, @Param("doctor") Integer doctor, @Param("insurance") Integer insurance, @Param("team") Integer team, @Param("subStatus") Integer subStatus, @Param("createdFrom") Date createdFrom, @Param("createdTo") Date createdTo, @Param("followFrom") Date followFrom, @Param("followTo") Date followTo, @Param("createdBy") Integer createdBy,
			Pageable pageable);

	@RestResource(path = "count", rel = "count")
	@Query("SELECT count(id)  FROM RequestCheckTracerWorkFlow")
	public long countById();
	
	
	@RestResource(path = "worklog", rel = "worklog")
	@Query(value= "SELECT i FROM RequestCheckTracerWorkFlow i where (i.arProductivity.id= :productivityId) ")
	List<RequestCheckTracerWorkFlow> getWorkLogs(@Param("productivityId") Integer productivityId);

}
