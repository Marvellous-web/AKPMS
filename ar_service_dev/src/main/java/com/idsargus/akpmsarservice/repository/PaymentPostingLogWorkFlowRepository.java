package com.idsargus.akpmsarservice.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.idsargus.akpmsarservice.model.domain.PaymentPostingWorkFlow;

import javax.transaction.Transactional;

@RepositoryRestResource(path = PaymentPostingLogWorkFlowRepository.MODULE_NAME, collectionResourceRel = PaymentPostingLogWorkFlowRepository.MODULE_NAME)
public interface PaymentPostingLogWorkFlowRepository extends CrudRepository<PaymentPostingWorkFlow, Integer>{

	public static final String MODULE_NAME = "paymentPostingWorkFlow";
	@Override
//	@CachePut
	<S extends PaymentPostingWorkFlow> S save(S paymentPostingWorkFlow);
	
//	@RestResource(path = "customqueryall", rel = "customqueryall")
//	@Query(value= "select i from PaymentPostingWorkFlow i where  CONCAT(i.arProductivity.patientAccountNumber,i.arProductivity.patientName) LIKE %:query% AND (:statusCode is null or i.arProductivity.statusCode = :statusCode) AND (:source is null or i.arProductivity.source = :source) AND (:doctor is null or i.doctor.id = :doctor) AND (:insurance is null or i.insurance.id = :insurance) AND (:team is null or i.arProductivity.team.id= :team) AND (:subStatus is null or i.arProductivity.subStatus = :subStatus) AND (:createdFrom is null or i.createdOn > :createdFrom) AND (:createdTo is null or i.createdOn < :createdTo) AND (:followFrom is null or i.arProductivity.followUpDate > :followFrom) AND (:followTo is null or i.arProductivity.followUpDate < :followTo) AND (:createdBy is null or i.createdBy.id = :createdBy) ORDER By i.arProductivity.patientName")
//	public Page<PaymentPostingWorkFlow> findByQueryAll(@Param("query") String query, @Param("statusCode") String statusCode, @Param("source") String source, @Param("doctor") Integer doctor, @Param("insurance") Integer insurance, @Param("team") Integer team, @Param("subStatus") Integer subStatus, @Param("createdFrom") Date createdFrom, @Param("createdTo") Date createdTo, @Param("followFrom") Date followFrom, @Param("followTo") Date followTo, @Param("createdBy") Integer createdBy,
//			Pageable pageable);
	
//	@RestResource(path = "customqueryall", rel = "customqueryall")
//	@Query(value= "select i from PaymentPostingWorkFlow i where  CONCAT(i.arProductivity.patientAccountNumber,i.arProductivity.patientName) "
//			+ "LIKE %:query% AND (:status is null or i.status = :status)  AND (:statusCode is null or i.arProductivity.statusCode = :statusCode) "
//			+ "AND (:offSet is null or i.offSet = :offSet) AND (:source is null or i.arProductivity.source = :source) "
//			+ "AND (:doctor is null or i.doctor.id = :doctor) AND (:insurance is null or i.insurance.id = :insurance) "
//			+ "AND (:team is null or i.arProductivity.team.id= :team) AND (:subStatus is null or i.arProductivity.subStatus = :subStatus) "
//			+ "AND (:createdFrom is null or DATE(i.createdOn) >= :createdFrom) AND (:createdTo is null or DATE(i.createdOn) <= :createdTo) "
//			+ "AND (:followFrom is null or DATE(i.arProductivity.followUpDate) >= :followFrom) AND (:followTo is null or DATE(i.arProductivity.followUpDate) <= :followTo) "
//			+ "AND (:createdBy is null or i.createdBy.id = :createdBy) ORDER By i.id DESC")

	/*
	Status
Patient Name
Patient ID
CPT Code
Provider
Insurance
DOS
Billed Amt.
Amount Paid By Primary
Amount Paid By Secondary
Contractual Adjustment
Bulk Amt. of Payment
Patient Response

	 */
	@RestResource(path = "customqueryall", rel = "customqueryall")
	@Query(value= "select i from PaymentPostingWorkFlow i where  CONCAT(i.arProductivity.patientAccountNumber,i.arProductivity.patientName) "
			+ "LIKE %:query% AND (:status is null or i.status = :status)  AND (:statusCode is null or i.arProductivity.statusCode = :statusCode) "
			+ "AND (:offSet is null or i.offSet = :offSet) AND (:source is null or i.arProductivity.source = :source) "
//			+ "AND (:doctor is null or i.doctor.id = :doctor) "
			+ "AND (COALESCE(:doctor) IS NULL or (i.doctor.id IN (:doctor))) "
			+ "AND (:insurance is null or i.insurance.id = :insurance) "
			+" AND (:databaseId is null or i.arProductivity.database.id = :databaseId)  "
			+"AND (:companyId is null OR i.company.id= :companyId)"
			+"AND (:groupId is null OR i.group.id= :groupId)"
			+ "AND (:team is null or i.arProductivity.team.id= :team) AND (:subStatus is null or i.arProductivity.subStatus = :subStatus) "
			+ "AND (:createdFrom is null or DATE(i.createdOn) >= :createdFrom) AND (:createdTo is null or DATE(i.createdOn) <= :createdTo) "
			+ "AND (:followFrom is null or DATE(i.arProductivity.followUpDate) >= :followFrom) AND (:followTo is null or DATE(i.arProductivity.followUpDate) <= :followTo) "
			+ "AND (:createdBy is null or i.createdBy.id = :createdBy) " +
			" ORDER BY CASE WHEN :sortDirection = 'desc' " +
			" THEN CASE " +
			" WHEN :columnName = 'status' THEN i.status "+
			" WHEN :columnName  = 'patientName' THEN i.arProductivity.patientName " +
			" WHEN :columnName  = 'patientId' THEN  i.arProductivity.patientAccountNumber" +
			" WHEN :columnName  = 'cptCode' THEN i.cptcode " +
			" WHEN :columnName = 'insurance' THEN i.insurance.name "+
		//	" WHEN :columnName  = 'provider' THEN i.doctor.name " +  will not get latest record
			" WHEN :columnName  = 'provider' THEN i.doctor.id " +
			" WHEN :columnName  = 'dos' THEN i.dos " +
			" WHEN :columnName  = 'amountPaidByPrimary' THEN i.primaryAmount " +
			" WHEN :columnName = 'amountPaidBySecondary' THEN i.secondaryAmount "+
			" WHEN :columnName  = 'contractualAdjustment' THEN i.contractualAdj " +
			" WHEN :columnName  = 'bulkAmtOfPayment' THEN i.bulkPaymentAmount " +
			" WHEN :columnName  = 'patientResponse' THEN i.patientResponse " +
			" WHEN :columnName = 'dateCheckIssue' THEN i.checkIssueDate "+
			" WHEN :columnName  = 'dateCheckCashed' THEN i.checkCashedDate " +
			" WHEN :columnName  = 'addressCheckSendTo' THEN i.addressCheckSend " +
			" WHEN :columnName  = 'modifiedOn' THEN i.modifiedOn " +
			" WHEN :columnName  = 'createdOn' THEN i.createdOn " +
			" WHEN :columnName  = 'modifiedBy' THEN i.modifiedBy " +
			" WHEN :columnName  = 'createdBy' THEN i.createdBy.firstName " +
			"  ELSE i.id END " +
			" END DESC, " +
			" CASE WHEN :sortDirection = 'asc' " +
			" THEN CASE " +
			" WHEN :columnName = 'status' THEN i.status "+
			" WHEN :columnName  = 'patientName' THEN i.arProductivity.patientName " +
			" WHEN :columnName  = 'patientId' THEN  i.arProductivity.patientAccountNumber" +
			" WHEN :columnName  = 'cptCode' THEN i.cptcode " +
			" WHEN :columnName = 'insurance' THEN i.insurance.name "+
		//	" WHEN :columnName  = 'provider' THEN i.doctor.name " +
			" WHEN :columnName  = 'provider' THEN i.doctor.id " +
			" WHEN :columnName  = 'dos' THEN i.dos " +
			" WHEN :columnName  = 'amountPaidByPrimary' THEN i.primaryAmount " +
			" WHEN :columnName = 'amountPaidBySecondary' THEN i.secondaryAmount "+
			" WHEN :columnName  = 'contractualAdjustment' THEN i.contractualAdj " +
			" WHEN :columnName  = 'bulkAmtOfPayment' THEN i.bulkPaymentAmount " +
			" WHEN :columnName  = 'patientResponse' THEN i.patientResponse " +
			" WHEN :columnName = 'dateCheckIssue' THEN i.checkIssueDate "+
			" WHEN :columnName  = 'dateCheckCashed' THEN i.checkCashedDate " +
			" WHEN :columnName  = 'addressCheckSendTo' THEN i.addressCheckSend " +
			" WHEN :columnName  = 'modifiedOn' THEN i.modifiedOn " +
			" WHEN :columnName  = 'createdOn' THEN i.createdOn " +
			" WHEN :columnName  = 'createdBy' THEN i.createdBy.firstName " +
			" WHEN :columnName  = 'modifiedBy' THEN i.modifiedBy " +
			"  ELSE i.id END " +
			" END ASC , i.id desc")
	public Page<PaymentPostingWorkFlow> findByQueryAll(@Param("query") String query,
			@Param("statusCode") String statusCode, @Param("status") String status,
			@Param("offSet") String offSet,
			@Param("source") String source,@Param("doctor") List<Integer> doctor,
			@Param("databaseId") Integer databaseId,
			@Param("companyId") Integer companyId,
			@Param("groupId") Integer groupId,
			@Param("insurance") Integer insurance, @Param("team") Integer team, 
			@Param("subStatus") Integer subStatus, @Param("createdFrom") Date createdFrom, 
			@Param("createdTo") Date createdTo, @Param("followFrom") Date followFrom,
			@Param("followTo") Date followTo, @Param("createdBy") Integer createdBy,
													   @Param("sortDirection") String sortDirection,
													   @Param("columnName") String columnName	 ,
			Pageable pageable);
	
	@RestResource(path = "count", rel = "count")
		@Query(value= "SELECT count(*) FROM payment_posting_workflow i cross join " +
			" ar_productivity arp where i.ar_productivity_id=arp.id", nativeQuery = true)
//	@Query("SELECT count(id)  FROM PaymentPostingWorkFlow")
	public long countById(); 
	

	@RestResource(path = "worklog", rel = "worklog")
	@Query(value= "SELECT i FROM PaymentPostingWorkFlow i where (i.arProductivity.id= :productivityId) ")
	List<PaymentPostingWorkFlow> getWorkLogs(@Param("productivityId") Integer productivityId);

	@Transactional
	@Modifying
	@Query(value = "update payment_posting_workflow set " +
			" status = :status, " +
			" off_set =  CASE WHEN :offSet <> '' THEN :offSet ELSE off_set END, " +
			"provider_id = CASE WHEN :doctor <> -1 THEN :doctor ELSE provider_id END, " +
			//"created_by = CASE WHEN  :createdBy <> '' THEN :createdBy ELSE created_by END , " +
			"insurance_id = CASE WHEN  :insurance <> -1 THEN :insurance ELSE insurance_id END , " +
			"ar_productivity_id = CASE WHEN :team <> -1 THEN :team ELSE ar_productivity_id END " +
			" where id IN (:pplIds)", nativeQuery = true)
	public Integer bulkUpdateByTicketNumber(@Param("pplIds") List<Integer> pplIds,
											@Param("status") String status,
											@Param("offSet") String offSet,
											@Param("doctor") Integer doctor,
											@Param("insurance") Integer insurance,
											@Param("team") Integer team);
	
//	@Query("select count(id) from PaymentPostingWorkFlow i where i.status = :status")
//	public long getPaymentPostingCountBystatus(@Param("status") String status);
	
	@Query(value = "select count(*) from payment_posting_workflow ppw cross join ar_productivity ar on ar.id =ppw.ar_productivity_id where status =:status", 
			nativeQuery=true)
	public long getPaymentPostingCountBystatus(@Param("status") String status);

	
//	@Query(value= "SELECT count(i.id) FROM PaymentPostingWorkFlow i where (i.status= :status AND i.offSet= :offSet) ")
//	public long getPaymentPostingCountByStatusAndOffset(@Param("status") String status, @Param("offSet") String offSet);

	@Query(value= "select count(*) from payment_posting_workflow ppw cross join ar_productivity ar on ar.id =ppw.ar_productivity_id where status =:status AND off_set= :offSet",
			nativeQuery=true)
	public long getPaymentPostingCountByStatusAndOffset(@Param("status") String status, @Param("offSet") String offSet);
	  
	
}
