package com.idsargus.akpmsarservice.repository;

import java.util.Date;
import java.util.List;

import com.idsargus.akpmscommonservice.entity.PaymentBatch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;


@RepositoryRestResource(path = PaymentBatchRepository.MODULE_NAME, collectionResourceRel = PaymentBatchRepository.MODULE_NAME)
public interface PaymentBatchRepository extends CrudRepository<PaymentBatch, Integer>{
	

	public static final String MODULE_NAME = "paymentBatch";
	
//	@RestResource(path = "customqueryall", rel = "customqueryall")
//	@Query("SELECT i FROM PaymentProductivity i where i.paymentProdType = :paymentType AND (:batch is null OR i.paymentBatch.id = :batch) AND (:doctor is null OR i.paymentBatch.doctor.id= :doctor) AND (:postedBy is null OR i.createdBy.id= :postedBy) AND (:workFlowId is null OR i.workFlowId = :workFlowId) AND (:createdFrom is null or i.createdOn > :createdFrom) AND (:createdTo is null or i.createdOn < :createdTo) AND (:postedFrom is null or i.paymentBatch.postedOn > :postedFrom) AND (:postedTo is null or i.paymentBatch.postedOn < :postedTo) ")
//	public Page<PaymentProductivity> findByQueryAll(@Param("paymentType") Integer paymentType, @Param("batch") Integer batch,  @Param("doctor") Integer doctor, @Param("postedBy") Integer postedBy, @Param("workFlowId") Integer workFlowId, @Param("createdFrom") Date createdFrom, @Param("createdTo") Date createdTo, @Param("postedFrom") Date postedFrom, @Param("postedTo") Date postedTo, Pageable pageable);

	
	
//	@RestResource(path = "customqueryall", rel = "customqueryall")
//	@Query("SELECT i FROM PaymentBatch i where (:billingMonth is null OR i.billingMonth = : billingMonth) AND (:billingYear is null or i.billingYear =  :billingYear) AND (:postedBy is null OR i.postedBy.id = :postedBy) AND (:doctor is null OR i.doctor.id= doctor) AND (:phDoctor is null OR i.doctor.id = :phDoctor) AND (coalesce(:paymentType) is null OR i.paymentType.id in (:paymentType)) AND (coalesce(:revenueType) is null OR i.revenueType.id IN (:revenueType)) AND (coalesce(:moneySource) is null OR i.moneySource.id IN (:moneySource)) AND (:insurance is null OR i.insurance.id= :insurance) AND (:createdBy is NULL OR i.createdBy.id= :createdBy) AND (:eraCheckNo is NULL OR i.eraCheckNo= :eraCheckNo) AND (:ticket is NULL OR i.id= :ticket) AND (:ndba is null OR i.ndba > 0) AND (:oldPriorAr is NULL OR i.oldPriorAr > 0) AND (:suspenseAccount is NULL OR i.suspenseAccount > 0) AND (:createdFrom is null or i.createdOn > :createdFrom) AND (:createdTo is null or i.createdOn < :createdTo)  AND (:ctPostedFrom is null or i.datePosted >= :ctPostedFrom) AND (:ctPostedTo is null or i.datePosted <= :ctPostedTo) AND  (:postedFrom is null or i.postedOn > :postedFrom) AND (:postedTo is null or i.postedOn < :postedTo) AND (:depositFrom is null or i.depositDate > :depositFrom) AND (:depositTo is null or i.depositDate < :depositTo) AND(:tType is null OR (:tType = 0 AND i.postedBy is null) OR (:tType = 1 AND i.postedBy is not null)) order by i.id DESC")
//	public Page<PaymentBatch> findByQueryAll(@Param("billingMonth") Long billingMonth, @Param("billingYear") Long billingYear, @Param("postedBy") Integer postedBy,  @Param("doctor") Integer doctor, @Param("phDoctor") Integer phDoctor, @Param("paymentType") List<Integer> paymentType, @Param("revenueType") List<Integer> revenueType, @Param("moneySource") List<Integer> moneySource, @Param("insurance") Integer insurance, @Param("createdBy") Integer createdBy, @Param("eraCheckNo") String eraCheckNo, @Param("ticket") Integer ticket, @Param("ndba") Double ndba, @Param("oldPriorAr") Double oldPriorAr, @Param("suspenseAccount") Double suspenseAccount, @Param("createdFrom") Date createdFrom, @Param("createdTo") Date createdTo, @Param("postedFrom") Date postedFrom, @Param("postedTo") Date postedTo, @Param("ctPostedTo") Date ctPostedTo, @Param("ctPostedFrom") Date ctPostedFrom, @Param("depositFrom") Date depositFrom, @Param("depositTo") Date depositTo, @Param("tType") Integer tType, Pageable pageable);

	//working code  multiple search doctor on 03 05 2023 by tsf added  below
	@RestResource(path = "customqueryall", rel = "customqueryall")
	@Query("SELECT i FROM PaymentBatch i  where (:billingMonth is null OR i.billingMonth = :billingMonth) "
			+ "AND (:billingYear is null or i.billingYear =  :billingYear) "
			+ "AND (:postedBy is null OR i.postedBy.id = :postedBy) "
//			+"AND (COALESCE(:doctor) IS NULL or (i.doctor.id IN (:doctor))) "
			+ "AND (coalesce(:doctor) is null OR (i.doctor.id IN (:doctor)))"
//			+ "AND (coalesce(:ticketMultiple) is null OR (i.id IN (:ticketMultiple)))"
			+"AND (:companyId is null OR i.company.id= :companyId)"
			+"AND (:groupId is null OR i.group.id= :groupId)"
			+ " AND (:phDoctor is null OR i.doctor.id = :phDoctor) "
			+ "AND (coalesce(:paymentType) is null OR i.paymentType.id in (:paymentType)) "
			+ "AND (coalesce(:revenueType) is null OR i.revenueType.id IN (:revenueType))"
			+ " AND (coalesce(:moneySource) is null OR i.moneySource.id IN (:moneySource))"
			+ " AND (:insurance is null OR i.insurance.id= :insurance) "
			+ "AND (:createdBy is NULL OR i.createdBy.id= :createdBy) "
			+ "AND (:eraCheckNo is NULL OR i.eraCheckNo= :eraCheckNo) "
	//		+ "AND (:ticket is NULL OR i.id= :ticket) "
			+ "AND (coalesce(:ticket) is null OR (i.id IN (:ticket)))"
			+ "AND (:ticketNumberFrom is NULL OR i.id>= :ticketNumberFrom) "
			+ "AND (:ticketNumberTo is NULL OR i.id<= :ticketNumberTo) "
			+ "AND (:ndba is null OR i.ndba > 0) AND (:oldPriorAr is NULL OR i.oldPriorAr > 0)"
			+ " AND (:suspenseAccount is NULL OR i.suspenseAccount > 0) "
			+ "AND (:createdFrom is null or DATE(i.createdOn) >= :createdFrom) "
			+ "AND (:createdTo is null or DATE(i.createdOn) <= :createdTo)  "
			+ "AND (:ctPostedFrom is null or DATE(i.datePosted) >= :ctPostedFrom) "
			+ "AND (:ctPostedTo is null or DATE( i.datePosted ) <= :ctPostedTo) "
			+ "AND  (:postedFrom is null or DATE(i.postedOn) >= :postedFrom) "
			+ "AND (:postedTo is null or DATE(i.postedOn ) <= :postedTo) "
			+ "AND (:depositFrom is null or DATE(i.depositDate) >= :depositFrom) "
			+ "AND (:depositTo is null or DATE(i.depositDate) <= :depositTo) "
			+ "AND(:tType is null OR (:tType = 0 AND i.postedBy is null) OR " +
			"(:tType = 1 AND i.postedBy is not null)) ORDER BY CASE WHEN :sortDirection = 'desc' " +
			" THEN CASE " +
			" WHEN :columnName = 'ctPostedDt' THEN i.datePosted "+
			" WHEN :columnName  = 'depositAmt' THEN i.depositAmount " +
			" WHEN :columnName  = 'NDBA' THEN i.ndba " +
			" WHEN :columnName  = 'revisedBy' THEN i.revisedBy " +
		//	" WHEN :columnName  = 'revisedBy' THEN i.revisedBy.firstName " +
			" WHEN :columnName  = 'revisedDt' THEN i.revisedOn " +
			" WHEN :columnName  = 'createdOn' THEN i.createdOn " +
		//	" WHEN :columnName  = 'modifiedBy' THEN i.modifiedBy.firstName " +
			" WHEN :columnName  = 'modifiedBy' THEN i.modifiedBy " +
			" WHEN :columnName  = 'modifiedOn' THEN i.modifiedOn " +
			" WHEN :columnName = 'ticketNumber' THEN i.id "+
			" WHEN :columnName  = 'batchMonth' THEN i.billingMonth " +
			//" WHEN :columnName  = 'doctorName' THEN i.doctor.name " + will not get data where doctorid is null
			" WHEN :columnName  = 'doctorName' THEN i.doctor.id " +
			" WHEN :columnName  = 'createdBy' THEN i.createdBy.firstName " +
			" WHEN :columnName  = 'postedBy' THEN i.postedBy " +
		//	" WHEN :columnName  = 'postedBy' THEN i.postedBy.firstName " +
			" WHEN :columnName  = 'postedOn' THEN i.postedOn " +
			" WHEN :columnName  = 'depositDt' THEN i.depositDate " +
			" WHEN :columnName  = 'payment' THEN i.paymentType ELSE i.id END " +
			" END DESC, " +
			" CASE WHEN :sortDirection = 'asc' " +
			" THEN CASE " +
			" WHEN :columnName = 'ctPostedDt' THEN i.datePosted "+
			" WHEN :columnName  = 'depositAmt' THEN i.depositAmount " +
			" WHEN :columnName  = 'NDBA' THEN i.ndba " +
			" WHEN :columnName  = 'revisedBy' THEN i.revisedBy " +
		//  " WHEN :columnName  = 'revisedBy' THEN i.revisedBy.firstName " +
			" WHEN :columnName  = 'revisedDt' THEN i.revisedOn " +
			" WHEN :columnName  = 'createdOn' THEN i.createdOn " +
		//	" WHEN :columnName  = 'modifiedBy' THEN i.modifiedBy.firstName " +
			" WHEN :columnName  = 'modifiedBy' THEN i.modifiedBy " +
			" WHEN :columnName  = 'modifiedOn' THEN i.modifiedOn " +
			" WHEN :columnName = 'ticketNumber' THEN i.id "+
			" WHEN :columnName  = 'batchMonth' THEN i.billingMonth " +
			//" WHEN :columnName  = 'doctorName' THEN i.doctor.name " + will not get data where doctorid is null
			" WHEN :columnName  = 'doctorName' THEN i.doctor.id " +
			" WHEN :columnName  = 'createdBy' THEN i.createdBy.firstName " +
			" WHEN :columnName  = 'postedBy' THEN i.postedBy" +
		//	" WHEN :columnName  = 'postedBy' THEN i.postedBy.firstName " +
			" WHEN :columnName  = 'postedOn' THEN i.postedOn " +
			" WHEN :columnName  = 'depositDt' THEN i.depositDate " +
			" WHEN :columnName  = 'payment' THEN i.paymentType ELSE i.id END " +
			" END ASC, i.id DESC " )
	public Page<PaymentBatch> findByQueryAll(@Param("billingMonth") Long billingMonth,
											 @Param("billingYear") Long billingYear, @Param("postedBy") Integer postedBy,
											 @Param("doctor") List<Integer> doctor, @Param("phDoctor") Integer phDoctor,
											 @Param("companyId") Integer companyId,
											 @Param("groupId") Integer groupId,
											 @Param("paymentType") List<Integer> paymentType,
											 @Param("revenueType") List<Integer> revenueType,
											 @Param("moneySource") List<Integer> moneySource,
											 @Param("insurance") Integer insurance, @Param("createdBy") Integer createdBy,
											 @Param("eraCheckNo") String eraCheckNo,
										//	 @Param("ticket") Integer ticket,
											 @Param("ticket") List<Integer> ticket,
											 @Param("ticketNumberFrom") Integer ticketNumberFrom,
											 @Param("ticketNumberTo") Integer ticketNumberTo,
											 @Param("ndba") Double ndba, @Param("oldPriorAr") Double oldPriorAr,
											 @Param("suspenseAccount") Double suspenseAccount,
											 @Param("createdFrom") Date createdFrom,
											 @Param("createdTo") Date createdTo,
											 @Param("postedFrom") Date postedFrom,
											 @Param("postedTo") Date postedTo, @Param("ctPostedTo") Date ctPostedTo,
											 @Param("ctPostedFrom") Date ctPostedFrom, @Param("depositFrom") Date depositFrom,
											 @Param("depositTo") Date depositTo,
//											 @Param("ticketMultiple") List<Integer> ticketMultiple,
											 @Param("columnName") String columnName,
											 @Param("sortDirection") String sortDirection,
											 @Param("tType") Integer tType, Pageable pageable);
	@Override
	<S extends PaymentBatch> S save(S paymentBatch);
}
