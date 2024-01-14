package com.idsargus.akpmsarservice.repository;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.idsargus.akpmscommonservice.entity.PaymentProductivityOffset;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;


@RepositoryRestResource(path = PaymentProductivityOffsetRepository.MODULE_NAME, collectionResourceRel = PaymentProductivityOffsetRepository.MODULE_NAME)
@CacheConfig(cacheNames = PaymentProductivityOffsetRepository.MODULE_NAME)
public interface PaymentProductivityOffsetRepository extends CrudRepository<PaymentProductivityOffset, Integer>{
	

	public static final String MODULE_NAME = "paymentProductivityOffset";
	
//	@RestResource(path = "paymentoffset", rel = "paymentoffset")
//	@Query("SELECT i FROM PaymentProductivityOffset i where i.paymentProdType = :paymentType AND (:batch is null OR i.paymentBatch.id = :batch) AND (:doctor is null OR i.paymentBatch.doctor.id= :doctor) AND (:postedBy is null OR i.createdBy.id= :postedBy) AND (:workFlowId is null OR i.workFlowId = :workFlowId) AND (:createdFrom is null or i.createdOn > :createdFrom) AND (:createdTo is null or i.createdOn < :createdTo) AND (:postedFrom is null or i.paymentBatch.postedOn > :postedFrom) AND (:postedTo is null or i.paymentBatch.postedOn < :postedTo) ")
//	public Page<PaymentProductivityOffset> findByQueryAll(@Param("paymentProductivityOffset") Integer paymentpaymentProductivityOffset, @Param("batch") Integer batch,  @Param("doctor") Integer doctor, @Param("postedBy") Integer postedBy, @Param("workFlowId") Integer workFlowId, @Param("createdFrom") Date createdFrom, @Param("createdTo") Date createdTo, @Param("postedFrom") Date postedFrom, @Param("postedTo") Date postedTo, Pageable pageable);
	
	
//	@RestResource(path = "customqueryall", rel = "customqueryall")
//	@Query("SELECT i FROM PaymentProductivityOffset i where  " +
//			"CONCAT(i.accountNumber,i.chkNumber) LIKE %:query% AND (:status is null or i.status = :status) AND (:accountNumber is null or i.accountNumber= :accountNumber) AND (:chkNumber is null or i.chkNumber = :chkNumber) AND (:patientName is null or i.patientName=:patientName) AND (:paymentBatchId is null or i.paymentBatchId = :paymentBatchId)")
//	public Page<PaymentProductivityOffset> findByQueryAll(@Param("query") String query,
//														  @Param("status") String status,
//														  @Param("accountNumber") String accountNumber,
//														  @Param("patientName") String patientName,
//														  @Param("paymentBatchId") String paymentBatchId,
//														  @Param("chkNumber") String chkNumber,
//														  Pageable pageable);

//	@RestResource(path = "customqueryall", rel = "customqueryall")
//	@Query("SELECT i FROM PaymentProductivityOffset i where  " +
//			"(:query is null or CONCAT(i.accountNumber,i.chkNumber ,i.paymentBatch.id) LIKE %:query% )" +
//			" AND (:status is null or i.status = :status)  " +
//			" AND (COALESCE(:doctor) IS NULL or (i.paymentBatch.doctor.id IN (:doctor)))" +
//			"AND (:accountNumber is null or i.accountNumber= :accountNumber) " +
//			"AND (:chkNumber is null or i.chkNumber = :chkNumber) " +
//			"AND (:patientName is null or i.patientName=:patientName)" +
//			" AND (:paymentBatchId is null or i.paymentBatch.id = :paymentBatchId) " +
//			"AND (:insurance is null OR i.paymentBatch.insurance.id= :insurance) " +
//			"AND (:postedFrom is null or FUNCTION('DATE' , i.paymentBatch.postedOn)  >= :postedFrom) " +
//			"AND (:postedTo is null or FUNCTION('DATE',i.paymentBatch.postedOn) <= :postedTo) " +
//			"AND (:postedBy is null or i.paymentBatch.postedBy.id  = :postedBy) " +
//			"AND (:offsetTicketNumber is null or i.offsetTicketNumber = :offsetTicketNumber)")

	/*Dashboard - Offset Reference & Postings - List
Batch/Ticket No.#
Patient Name
Insurance
Doctor/Group Name
Status
Offset Ticket No.
Account. No.
Check No.
Check Date
Posted Date
Posted By
remarks
	 */

//	@RestResource(path = "customqueryall", rel = "customqueryall")
//	@Query("SELECT i FROM PaymentProductivityOffset i where  " +
//			"(:query is null or CONCAT(i.accountNumber ,i.chkNumber ,i.paymentBatch.id,i.patientName) LIKE %:query% )" +
//			" AND (:status is null or i.status = :status)  " +
//			" AND (COALESCE(:doctor) IS NULL or (i.paymentBatch.doctor.id IN (:doctor)))" +
//			"AND (:accountNumber is null or i.accountNumber= :accountNumber) " +
//			"AND (:chkNumber is null or i.chkNumber = :chkNumber) " +
//			"AND (:patientName is null or i.patientName=:patientName)" +
//			" AND (:paymentBatchId is null or i.paymentBatch.id = :paymentBatchId) " +
//			"AND (:insurance is null OR i.paymentBatch.insurance.id= :insurance) " +
//			"AND (:postedFrom is null or FUNCTION('DATE' , i.paymentBatch.postedOn)  >= :postedFrom) " +
//			"AND (:postedTo is null or FUNCTION('DATE',i.paymentBatch.postedOn) <= :postedTo) " +
//			"AND (:postedBy is null or i.paymentBatch.postedBy.id  = :postedBy) " +
//			"AND (:offsetTicketNumber is null or i.offsetTicketNumber = :offsetTicketNumber) " +
//			" ORDER BY CASE WHEN :sortDirection = 'desc' " +
//			" THEN CASE " +
//					" WHEN :columnName = 'TicketNo' THEN i.accountNumber "+
//					" WHEN :columnName  = 'PatientName' THEN i.patientName " +
//					" WHEN :columnName  = 'Insurance' THEN i.paymentBatch.insurance.id " +
//					" WHEN :columnName  = 'DoctorName' THEN i.paymentBatch.doctor.name " +
//			" WHEN :columnName = 'OffsetTicketNo' THEN i.offsetTicketNumber "+
//			" WHEN :columnName  = 'AccountNo' THEN i.accountNumber " +
//			" WHEN :columnName  = 'checkNo' THEN i.chkNumber " +
//			" WHEN :columnName  = 'postedDate' THEN i.paymentBatch.postedOn " +
//			" WHEN :columnName  = 'postedBy' THEN i.paymentBatch.postedBy.firstName " +
//			" WHEN :columnName  = 'remark' THEN i.remark " +
//			" WHEN :columnName  = 'status' THEN i.status " +
//			" WHEN :columnName  = 'chkDate' THEN i.chkDate " +
//			" WHEN :columnName  = 'createdBy' THEN i.createdBy.firstName " +
//			" WHEN :columnName  = 'modifiedBy' THEN i.modifiedBy " +
//			" WHEN :columnName  = 'modifiedOn' THEN i.modifiedOn " +
//			" WHEN :columnName  = 'createdOn' THEN i.createdOn " +
//			" ELSE i.id END " +
//					" END DESC, " +
//					" CASE WHEN :sortDirection = 'asc' " +
//					" THEN CASE " +
//			" WHEN :columnName = 'TicketNo' THEN i.accountNumber "+
//			" WHEN :columnName  = 'PatientName' THEN i.patientName " +
//			" WHEN :columnName  = 'Insurance' THEN i.paymentBatch.insurance.id " +
//			" WHEN :columnName  = 'DoctorName' THEN i.paymentBatch.doctor.name " +
//			" WHEN :columnName = 'OffsetTicketNo' THEN i.offsetTicketNumber "+
//			" WHEN :columnName  = 'AccountNo' THEN i.accountNumber " +
//			" WHEN :columnName  = 'checkNo' THEN i.chkNumber " +
//			" WHEN :columnName  = 'postedDate' THEN i.paymentBatch.postedOn " +
//			" WHEN :columnName  = 'postedBy' THEN i.paymentBatch.postedBy.firstName " +
//			" WHEN :columnName  = 'remark' THEN i.remark " +
//			" WHEN :columnName  = 'status' THEN i.status " +
//			" WHEN :columnName  = 'chkDate' THEN i.chkDate " +
//			" WHEN :columnName  = 'createdBy' THEN i.createdBy.firstName " +
//			" WHEN :columnName  = 'modifiedBy' THEN i.modifiedBy " +
//			" WHEN :columnName  = 'modifiedOn' THEN i.modifiedOn " +
//			" WHEN :columnName  = 'createdOn' THEN i.createdOn " +
//			" ELSE i.id END " +
//					" END ASC , i.id desc")
//	public Page<PaymentProductivityOffset> findByQueryAll(@Param("query") String query,
//														  @Param("status") String status,
//														  @Param("accountNumber") String accountNumber,
//														  @Param("patientName") String patientName,
//														  @Param("paymentBatchId") Integer paymentBatchId,
//														  @Param("offsetTicketNumber") Integer offsetTicketNumber,
//														  @Param("doctor") List<Integer> doctor,
//														  @Param("insurance") Integer insurance,
//														  @Param("postedBy") Integer postedBy,
//														  @Param("postedFrom") Date postedFrom,
//														  @Param("postedTo") Date postedTo,
//														  @Param("chkNumber") String chkNumber,
//														  @Param("columnName") String columnName,
//														  @Param("sortDirection") String sortDirection,
//														  Pageable pageable);
	//Prateek
@RestResource(path = "customqueryall", rel = "customqueryall")
@Query("SELECT i FROM PaymentProductivityOffset i where  " +
		"(:query is null or CONCAT(i.accountNumber ,i.chkNumber ,i.paymentBatch.id,i.patientName) LIKE %:query% )" +
		" AND (:status is null or i.status = :status)  " +
		" AND (COALESCE(:company) IS NULL or (i.paymentBatch.company.id IN (:company)))" +  // Change this line
		" AND (:accountNumber is null or i.accountNumber= :accountNumber) " +
		" AND (:chkNumber is null or i.chkNumber = :chkNumber) " +
		" AND (:patientName is null or i.patientName=:patientName)" +
		" AND (:paymentBatchId is null or i.paymentBatch.id = :paymentBatchId) " +
		" AND (:insurance is null OR i.paymentBatch.insurance.id= :insurance) " +
		" AND (:postedFrom is null or FUNCTION('DATE' , i.paymentBatch.postedOn)  >= :postedFrom) " +
		" AND (:postedTo is null or FUNCTION('DATE',i.paymentBatch.postedOn) <= :postedTo) " +
		" AND (:postedBy is null or i.paymentBatch.postedBy.id  = :postedBy) " +
		" AND (:offsetTicketNumber is null or i.offsetTicketNumber = :offsetTicketNumber) " +
		" ORDER BY CASE WHEN :sortDirection = 'desc' " +
		" THEN CASE " +
		" WHEN :columnName = 'TicketNo' THEN i.accountNumber "+
		" WHEN :columnName  = 'PatientName' THEN i.patientName " +
		" WHEN :columnName  = 'Insurance' THEN i.paymentBatch.insurance.id " +
		" WHEN :columnName  = 'DoctorName' THEN i.paymentBatch.company.id " +  // Change this line
		" WHEN :columnName = 'OffsetTicketNo' THEN i.offsetTicketNumber "+
		" WHEN :columnName  = 'AccountNo' THEN i.accountNumber " +
		" WHEN :columnName  = 'checkNo' THEN i.chkNumber " +
		" WHEN :columnName  = 'postedDate' THEN i.paymentBatch.postedOn " +
		" WHEN :columnName  = 'postedBy' THEN i.paymentBatch.postedBy.firstName " +
		" WHEN :columnName  = 'remark' THEN i.remark " +
		" WHEN :columnName  = 'status' THEN i.status " +
		" WHEN :columnName  = 'chkDate' THEN i.chkDate " +
		" WHEN :columnName  = 'createdBy' THEN i.createdBy.firstName " +
		" WHEN :columnName  = 'modifiedBy' THEN i.modifiedBy " +
		" WHEN :columnName  = 'modifiedOn' THEN i.modifiedOn " +
		" WHEN :columnName  = 'createdOn' THEN i.createdOn " +
		" ELSE i.id END " +
		" END DESC, " +
		" CASE WHEN :sortDirection = 'asc' " +
		" THEN CASE " +
		" WHEN :columnName = 'TicketNo' THEN i.accountNumber "+
		" WHEN :columnName  = 'PatientName' THEN i.patientName " +
		" WHEN :columnName  = 'Insurance' THEN i.paymentBatch.insurance.id " +
		" WHEN :columnName  = 'DoctorName' THEN i.paymentBatch.company.id " +  // Change this line
		" WHEN :columnName = 'OffsetTicketNo' THEN i.offsetTicketNumber "+
		" WHEN :columnName  = 'AccountNo' THEN i.accountNumber " +
		" WHEN :columnName  = 'checkNo' THEN i.chkNumber " +
		" WHEN :columnName  = 'postedDate' THEN i.paymentBatch.postedOn " +
		" WHEN :columnName  = 'postedBy' THEN i.paymentBatch.postedBy.firstName " +
		" WHEN :columnName  = 'remark' THEN i.remark " +
		" WHEN :columnName  = 'status' THEN i.status " +
		" WHEN :columnName  = 'chkDate' THEN i.chkDate " +
		" WHEN :columnName  = 'createdBy' THEN i.createdBy.firstName " +
		" WHEN :columnName  = 'modifiedBy' THEN i.modifiedBy " +
		" WHEN :columnName  = 'modifiedOn' THEN i.modifiedOn " +
		" WHEN :columnName  = 'createdOn' THEN i.createdOn " +
		" ELSE i.id END " +
		" END ASC , i.id DESC")
public Page<PaymentProductivityOffset> findByQueryAll(@Param("query") String query,
													  @Param("status") String status,
													  @Param("accountNumber") String accountNumber,
													  @Param("patientName") String patientName,
													  @Param("paymentBatchId") Integer paymentBatchId,
													  @Param("offsetTicketNumber") Integer offsetTicketNumber,
													  @Param("company") List<Integer> company,  // Change this line
													  @Param("insurance") Integer insurance,
													  @Param("postedBy") Integer postedBy,
													  @Param("postedFrom") Date postedFrom,
													  @Param("postedTo") Date postedTo,
													  @Param("chkNumber") String chkNumber,
													  @Param("columnName") String columnName,
													  @Param("sortDirection") String sortDirection,
													  Pageable pageable);

	@Override
	<S extends PaymentProductivityOffset> S save(S paymentProductivityOffset);
	
	
	@RestResource(path = "paymentoffset", rel = "paymentoffset")
	@Query("select count(id) from PaymentProductivityOffset i where i.status = :status")
	public long getPaymentProductivityOffsetCountByStatus(@Param("status") String status);



	@Transactional
	@Modifying
	@Query(value = "update payment_productivity_offset set " +
			" status = :status, " +
			" offset_remark =  CASE WHEN :offSetRemark <> '' THEN :offSetRemark ELSE offset_remark END, " +
			//"created_by = CASE WHEN  :createdBy <> '' THEN :createdBy ELSE created_by END , " +
			"remark = CASE WHEN  :remark <> '' THEN :remark ELSE remark END  " +
			" where id IN (:ids)", nativeQuery = true)
	public Integer bulkUpdateByIds(@Param("ids") List<Integer> ids,
											@Param("status") String status,
											@Param("offSetRemark") String offSetRemark,
											@Param("remark") String remark);

}


	
	

