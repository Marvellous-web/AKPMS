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

import com.idsargus.akpmscommonservice.entity.PaymentProductivity;

@RepositoryRestResource(path = PaymentProductivityRepository.MODULE_NAME, collectionResourceRel = PaymentProductivityRepository.MODULE_NAME)
public interface PaymentProductivityRepository extends CrudRepository<PaymentProductivity, Integer>{
	

	public static final String MODULE_NAME = "paymentProductivity";
	
	@RestResource(path = "customqueryall", rel = "customqueryall")
	@Query("SELECT i FROM PaymentProductivity i where (:paymentType is null OR i.paymentProdType = :paymentType )AND (:batch is null OR i.paymentBatch.id = :batch)" +
		//	" AND (:doctor is null OR i.paymentBatch.doctor.id= :doctor)  " +
			"AND (COALESCE(:doctor) is null or (i.paymentBatch.doctor.id IN (:doctor))) " +
			" AND (:groupId is null or i.paymentBatch.group.id=:groupId) " +
			" AND (:companyId is null or i.paymentBatch.company.id=:companyId) " +
			" AND (:createdBy is null or i.createdBy.id= :createdBy)  " +
			"AND (:postedBy is null OR i.paymentBatch.postedBy.id= :postedBy) AND " +
//			"AND (:postedBy is null OR  i.createdBy.id= :postedBy) AND " +
			"(:workFlowId is null OR i.workFlowId = :workFlowId) AND " +
			"(:createdFrom is null or DATE(i.createdOn) >= :createdFrom) AND" +
			//scanDate
			" (:ctPostedFrom is null or DATE(i.paymentBatch.datePosted) >= :ctPostedFrom)  AND" +
			" (:ctPostedTo is null or DATE(i.paymentBatch.datePosted) <= :ctPostedTo)  AND " +
//			" (:ctPostedFrom is null or DATE(i.scanDate) >= :ctPostedFrom)  AND" +
//			" (:ctPostedTo is null or DATE(i.scanDate) <= :ctPostedTo)  AND " +
			"(:createdTo is null or DATE(i.createdOn) <= :createdTo) AND " +
			"(:postedFrom is null or DATE(i.paymentBatch.postedOn) >= :postedFrom) AND " +
			"(:postedTo is null or DATE(i.paymentBatch.postedOn) <= :postedTo) ORDER BY CASE WHEN :sortDirection = 'desc' " +
			" THEN CASE " +
			" WHEN :columnName  = 'suspenseAmt' THEN i.paymentBatch.suspenseAccount " +
			" WHEN :columnName  = 'agencyMoney' THEN i.paymentBatch.agencyMoney " +
			" WHEN :columnName  = 'otherIncome' THEN i.paymentBatch.otherIncome " +
			" WHEN :columnName = 'oldPriorAr' THEN i.paymentBatch.oldPriorAr "+
			" WHEN :columnName  = 'ctPostedDt' THEN i.paymentBatch.datePosted " +
//			" WHEN :columnName  = 'ctPostedDt' THEN i.scanDate " +
		//	" WHEN :columnName  = 'ctPostedDt' THEN i.paymentBatch.datePosted " +
			" WHEN :columnName  = 'manually' THEN i.paymentBatch.manuallyPostedAmt " +
			" WHEN :columnName  = 'elecTransaction' THEN i.elecTransaction " +
			" WHEN :columnName = 'scanDate' THEN i.scanDate "+
			" WHEN :columnName  = 'createdBy' THEN i.createdBy.firstName " +
			" WHEN :columnName  = 'modifiedBy' THEN i.modifiedBy " +
			" WHEN :columnName  = 'modifiedOn' THEN i.modifiedOn " +
			" WHEN :columnName  = 'createdOn' THEN i.createdOn " +
		//	" WHEN :columnName  = 'postedBy' THEN i.paymentBatch.postedBy.firstName " +
			" WHEN :columnName  = 'postedBy' THEN i.createdBy.firstName " +
			" WHEN :columnName = 'ticketNumber' THEN i.paymentBatch.id "+
			//" WHEN :columnName  = 'drOffice' THEN i.lastName " +
			" WHEN :columnName  = 'paymentType' THEN i.paymentBatch.paymentType " +
			" WHEN :columnName  = 'ckNumber' THEN i.chkNumber " +
			" WHEN :columnName = 'elecPostAmt' THEN i.paymentBatch.elecPostedAmt "+
		//	" WHEN :columnName  = 'postedAmt' THEN i.modifiedOn " +
			" ELSE i.id END " +
			" END DESC, " +
			" CASE WHEN :sortDirection = 'asc' " +
			" THEN CASE " +
			" WHEN :columnName  = 'suspenseAmt' THEN i.paymentBatch.suspenseAccount " +
			" WHEN :columnName  = 'agencyMoney' THEN i.paymentBatch.agencyMoney " +
			" WHEN :columnName  = 'otherIncome' THEN i.paymentBatch.otherIncome " +
			" WHEN :columnName = 'oldPriorAr' THEN i.paymentBatch.oldPriorAr "+
		//	" WHEN :columnName  = 'ctPostedDt' THEN i.paymentBatch.postedOn " +
			" WHEN :columnName  = 'ctPostedDt' THEN i.paymentBatch.datePosted " +
//			" WHEN :columnName  = 'ctPostedDt' THEN i.scanDate " +
			" WHEN :columnName  = 'manually' THEN i.paymentBatch.manuallyPostedAmt " +
			" WHEN :columnName  = 'elecTransaction' THEN i.elecTransaction " +
			" WHEN :columnName = 'scanDate' THEN i.scanDate "+
			" WHEN :columnName  = 'createdBy' THEN i.createdBy.firstName " +
			" WHEN :columnName  = 'modifiedBy' THEN i.modifiedBy " +
			" WHEN :columnName  = 'modifiedOn' THEN i.modifiedOn " +
			" WHEN :columnName  = 'createdOn' THEN i.createdOn " +
		//	" WHEN :columnName  = 'postedBy' THEN i.paymentBatch.postedBy.firstName " +
			" WHEN :columnName  = 'postedBy' THEN i.createdBy.firstName " +
			" WHEN :columnName = 'ticketNumber' THEN i.paymentBatch.id "+
			//" WHEN :columnName  = 'drOffice' THEN i.lastName " +
			" WHEN :columnName  = 'paymentType' THEN i.paymentBatch.paymentType " +
			" WHEN :columnName  = 'ckNumber' THEN i.chkNumber " +
			" WHEN :columnName = 'elecPostAmt' THEN i.paymentBatch.elecPostedAmt "+
			//" WHEN :columnName  = 'postedAmt' THEN i.modifiedOn" +
			" ELSE i.id END " +
			" END ASC , i.id desc")
	public Page<PaymentProductivity> findByQueryAll(@Param("paymentType") Integer paymentType,
													@Param("batch") Integer batch,
													@Param("doctor")  List<Integer>  doctor,
													@Param("companyId") Integer companyId,
													@Param("groupId") Integer groupId,
													@Param("postedBy") Integer postedBy,
													@Param("createdBy") Integer createdBy,
													@Param("workFlowId") Integer workFlowId,
													@Param("createdFrom") Date createdFrom,
													@Param("columnName") String columnName,
													@Param("sortDirection") String sortDirection,
													@Param("createdTo") Date createdTo, @Param("postedFrom") Date postedFrom, @Param("postedTo") Date postedTo,@Param("ctPostedFrom") Date ctPostedFrom,@Param("ctPostedTo") Date ctPostedTo, Pageable pageable);
	
	
	@Override
	<S extends PaymentProductivity> S save(S paymentProductivity);
	
//	@Query("select * from PaymentProductivity i where i.workFlowId = :workFlowId")
//	public PaymentProductivity findByWorkFlowId(int workFlowId);
	
//	@Query("select count(id) from PaymentProductivity i where i.paymentProdType = :paymentType")
//	public long getProductivityCountById(@Param("paymentType") Integer paymentType);
	
	@Query(value = "select count(*) from payment_productivity paymentpro0_ cross join payment_batch paymentbat1_ where paymentpro0_.batch_id=paymentbat1_.id and paymentpro0_.payment_productivity_type=:paymentType", 
			nativeQuery=true)
	public long getProductivityCountById(@Param("paymentType") Integer paymentType);
	
//	@Query("select count(id) from PaymentProductivity i where i.workFlowId = :workFlowId")
//	public long getProductivityCountByworkflowId(@Param("workFlowId") Integer workFlowId);
	
//	@Query("select count(id) from PaymentProductivity i where (i.workFlowId = :workFlowId) AND (i.paymentProdType = :paymentType)")
//	public long getProductivityCountByWorkFlowIdAndPaymentType(@Param("workFlowId") Integer workFlowId, @Param("paymentType") Integer paymentType);


	@Query(value = "select count(*) from payment_productivity paymentpro0_ cross join payment_batch paymentbat1_ where paymentpro0_.batch_id=paymentbat1_.id and paymentpro0_.workflow_id=:workFlowId", 
			nativeQuery=true)
	public long getProductivityCountByworkflowId(@Param("workFlowId") Integer workFlowId);

//	@Query(value = "SELECT * FROM payment_productivity where payment_productivity_type=:paymentProductivityType and batch_id=:batchId", nativeQuery = true)
//	public List<PaymentProductivity> getByBatchIdAndType(Integer paymentProductivityType, Integer batchId);
	@Query(value = "select count(*) from payment_productivity paymentpro0_ cross join payment_batch paymentbat1_ where paymentpro0_.batch_id=paymentbat1_.id and paymentpro0_.workflow_id=:workFlowId and paymentpro0_.payment_productivity_type=:paymentType", 
			nativeQuery=true)
	public long getProductivityCountByWorkFlowIdAndPaymentType(@Param("workFlowId") Integer workFlowId, @Param("paymentType") Integer paymentType);

//	// code change 22 sep
//
	@Query(value = "SELECT * FROM payment_productivity where  batch_id=:batchId", nativeQuery = true)

	public List<PaymentProductivity> getByBatchIdAndType( Integer batchId);
//
//	//code change end
}
