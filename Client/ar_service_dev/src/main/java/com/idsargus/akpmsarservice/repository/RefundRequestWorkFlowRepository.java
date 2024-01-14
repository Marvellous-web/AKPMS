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

import com.idsargus.akpmsarservice.model.domain.CodingCorrectionLogWorkFlow;
import com.idsargus.akpmsarservice.model.domain.PaymentPostingWorkFlow;
import com.idsargus.akpmsarservice.model.domain.RefundRequestWorkFlow;

@RepositoryRestResource(path = RefundRequestWorkFlowRepository.MODULE_NAME, collectionResourceRel = RefundRequestWorkFlowRepository.MODULE_NAME)
public interface RefundRequestWorkFlowRepository extends PagingAndSortingRepository<RefundRequestWorkFlow, Integer>{
	
	public static final String MODULE_NAME = "refundRequestWorkFlow";
	@Override
//	@CachePut
	<S extends RefundRequestWorkFlow> S save(S refundRequestWorkFlow);
	
//	@PreAuthorize("hasAuthority('role_admin')")
//	@RestResource(path = "customqueryall", rel = "customqueryall")
//	@Query(value= "select i from RefundRequestWorkFlow i where  CONCAT(i.arProductivity.patientAccountNumber,i.arProductivity.patientName) LIKE %:query% AND (:statusCode is null or i.arProductivity.statusCode = :statusCode) AND (:source is null or i.arProductivity.source = :source) AND (:doctor is null or i.doctor.id = :doctor) AND (:insurance is null or i.insurance.id = :insurance) AND (:team is null or i.arProductivity.team.id= :team) AND (:subStatus is null or i.arProductivity.subStatus = :subStatus) AND (:createdFrom is null or i.createdOn > :createdFrom) AND (:createdTo is null or i.createdOn < :createdTo) AND (:followFrom is null or i.arProductivity.followUpDate > :followFrom) AND (:followTo is null or i.arProductivity.followUpDate < :followTo) AND (:createdBy is null or i.createdBy.id = :createdBy) ORDER By i.arProductivity.patientName")
//	public Page<RefundRequestWorkFlow> findByQueryAll(@Param("query") String query, @Param("statusCode") String statusCode, @Param("source") String source, @Param("doctor") Integer doctor, @Param("insurance") Integer insurance, @Param("team") Integer team, @Param("subStatus") Integer subStatus, @Param("createdFrom") Date createdFrom, @Param("createdTo") Date createdTo, @Param("followFrom") Date followFrom, @Param("followTo") Date followTo, @Param("createdBy") Integer createdBy,
//			Pageable pageable);

	@RestResource(path = "customqueryall", rel = "customqueryall")

	@Query(value= "select i from RefundRequestWorkFlow i where  CONCAT(i.arProductivity.patientAccountNumber,i.arProductivity.patientName) LIKE %:query% AND (:statusCode is null or i.arProductivity.statusCode = :statusCode) AND (:status is null or i.status =:status) AND (:source is null or i.arProductivity.source = :source) AND (:doctor is null or i.doctor.id = :doctor) AND (:insurance is null or i.insurance.id = :insurance) AND (:team is null or i.arProductivity.team.name= :team) AND (:subStatus is null or i.arProductivity.subStatus = :subStatus) AND (:createdFrom is null or i.createdOn >= :createdFrom) AND (:createdTo is null or i.createdOn <= :createdTo) AND (:followFrom is null or i.arProductivity.followUpDate >= :followFrom) AND (:followTo is null or i.arProductivity.followUpDate <= :followTo) AND (:createdBy is null or i.createdBy.id = :createdBy) ORDER By i.arProductivity.patientName")
	public Page<RefundRequestWorkFlow> findByQueryAll(@Param("query") String query, @Param("statusCode") String statusCode,@Param("status") Integer status, @Param("source") String source, @Param("doctor") Integer doctor, @Param("insurance") Integer insurance, @Param("team") Integer team, @Param("subStatus") Integer subStatus, @Param("createdFrom") Date createdFrom, @Param("createdTo") Date createdTo, @Param("followFrom") Date followFrom, @Param("followTo") Date followTo, @Param("createdBy") Integer createdBy,
													  Pageable pageable);
	@RestResource(path = "count", rel = "count")
	//	@Query(value= "SELECT count(*) FROM refund_request_workflow i cross join " +
//			" ar_productivity arp where i.ar_productivity_id=arp.id", nativeQuery = true)
	@Query("SELECT count(id)  FROM RefundRequestWorkFlow")
	public long countById();
	
		
	@RestResource(path = "worklog", rel = "worklog")
	@Query(value= "SELECT i FROM RefundRequestWorkFlow i where (i.arProductivity.id= :productivityId) ")
	List<RefundRequestWorkFlow> getWorkLogs(@Param("productivityId") Integer productivityId);


	@Query(value = "SELECT count(*) FROM refund_request_workflow c cross join ar_productivity e  where e.id =c.ar_productivity_id and c.status =:status"
				, nativeQuery=true)
	long getRefundRequestCountByStatus(@Param("status") Integer status);


}
