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

import com.idsargus.akpmscommonservice.entity.ChargeProductivity;

@RepositoryRestResource(path = ChargeProductivityRepository.MODULE_NAME, collectionResourceRel = ChargeProductivityRepository.MODULE_NAME)
public interface ChargeProductivityRepository extends PagingAndSortingRepository<ChargeProductivity, Integer>{
	
	public static final String MODULE_NAME = "chargeProductivity";
	
	@Override
	<S extends ChargeProductivity> S save(S chargeProductivity);
	
//	@PreAuthorize("hasAuthority('role_admin')")
//	@RestResource(path = "customqueryall", rel = "customqueryall")
//	@Query("SELECT i FROM ChargeProductivity i where  CONCAT(i.patientName, i.patientAccountNumber) LIKE %:query% AND (:statusCode is null or i.statusCode = :statusCode) AND (:source is null or i.source = :source) AND (:doctor is null or i.doctor.id = :doctor) AND (:insurance is null or i.insurance.id = :insurance) AND (:team is null or i.team.id = :team) AND (:subStatus is null or i.subStatus = :subStatus) AND (:createdFrom is null or i.createdOn > :createdFrom) AND (:createdTo is null or i.createdOn < :createdTo) AND (:followFrom is null or i.followUpDate > :followFrom) AND (:followto is null or i.followUpDate < :followto) AND (:createdBy is null or i.createdBy = :createdBy) ORDER BY i.patientName")
//	public Page<ChargeProductivity> findByQueryAll(@Param("query") String query, @Param("statusCode") String statusCode, @Param("source") String source, //, @Param("doctor") Integer doctor, Pageable pageable);
//			@Param("doctor") Integer doctor, @Param("insurance") Integer insurance, @Param("team") Integer team, @Param("subStatus") Integer subStatus, @Param("createdFrom") Date createdFrom, @Param("createdTo") Date createdTo, @Param("followFrom") Date followFrom, @Param("followto") Date followto, @Param("createdBy") String createdBy, Pageable pageable);
	
	//@RestResource(path = "customqueryall", rel = "customqueryall")
	//	@Query("SELECT i FROM ChargeProductivity i where
	//	(:prodType is null or i.productivityType= :prodType) AND
	//	(:workFlow is null or i.workFlow= :workFlow) AND
	//	(:ticketNumber is null or i.ticketNumber.id= :ticketNumber)
	//	AND (:postedBy is null or i.ticketNumber.postedBy.id= :postedBy) AND " +
	//			"(:onHold is null or i.onHold= :onHold) and " +
	//			" (COALESCE(:providerId) is null or (i.ticketNumber.doctor.id IN (:providerId))) " +
	//			" AND (:scanFrom is null or i.scanDate >= :scanFrom) " +
	//			"AND (:scanTo is null or i.scanDate <= :scanTo) AND (:postedFrom is null or " +
	//			"i.ticketNumber.postedOn >= :postedFrom) AND (:postedTo is null or " +
	//			"i.ticketNumber.postedOn <= :postedTo) ORDER BY i.id DESC")
	//	public Page<ChargeProductivity> findByQueryAll(@Param("prodType") String prodType,
	//												   @Param("workFlow") String workFlow,
	//												   @Param("ticketNumber") Integer ticketNumber,
	//												   @Param("postedBy") Integer postedBy,
	//												   @Param("onHold") Boolean onHold,
	//												   @Param("scanFrom") Date scanFrom,
	//												   @Param("scanTo") Date scanTo,
	//                                                 @Param("createdBy") Integer createdBy,
	//												   @Param("postedFrom") Date postedFrom,
	//												   @Param("postedTo") Date postedTo,
	//												   @Param("providerId") List<Integer> providerId,
	//												   Pageable pageable);
	@RestResource(path = "customqueryall", rel = "customqueryall")
	@Query("SELECT i FROM ChargeProductivity i where (:prodType is null or i.productivityType= :prodType) " +
			"AND (:workFlow is null or i.workFlow= :workFlow) AND " +
			"(:ticketNumber is null or i.ticketNumber.id= :ticketNumber) " +
			" AND (:groupId is null or i.ticketNumber.group.id=:groupId) " +
			" AND (:companyId is null or i.ticketNumber.company.id=:companyId) " +
		//	"(:ticketNumber is null or i.ticketNumber = :ticketNumber) " +
			"AND (:createdBy is null or i.createdBy.id= :createdBy)  " +
			"AND (:postedBy is null or i.ticketNumber.postedBy.id= :postedBy) AND " +
			"(:onHold is null or i.onHold= :onHold) and " +
			"(:modifiedBy is null or i.modifiedBy.id= :modifiedBy) and " +
			" (COALESCE(:providerId) is null or (i.ticketNumber.doctor.id IN (:providerId))) " +
			" AND (:scanFrom is null or DATE(i.scanDate) >= :scanFrom) " +
			"AND (:scanTo is null or DATE(i.scanDate) <= :scanTo)  " +
			"AND (:datePostedFrom is null or DATE(i.ticketNumber.dateBatchPosted) >= :datePostedFrom ) " +
			"AND (:datePostedTo is null or DATE(i.ticketNumber.dateBatchPosted) <= :datePostedTo ) " +
			"AND (:datePostedFromWithPostedon is null or DATE(i.ticketNumber.dateBatchPosted) >= :datePostedFromWithPostedon or DATE(i.ticketNumber.postedOn) >= :datePostedFromWithPostedon) " +
			"AND (:datePostedToWithPostedon is null or DATE(i.ticketNumber.dateBatchPosted) <= :datePostedToWithPostedon or DATE(i.ticketNumber.postedOn) <= :datePostedToWithPostedon) " +
//
			"AND (:postedFrom is null or DATE(i.ticketNumber.postedOn) >= :postedFrom) " +
			"AND (:postedTo is null or DATE(i.ticketNumber.postedOn )<= :postedTo) ORDER BY CASE WHEN :sortDirection = 'desc' " +
			" THEN CASE " +
		//	" WHEN :columnName = 'doctorName' THEN i.ticketNumber.doctor.name "+
			" WHEN :columnName = 'doctorName' THEN i.ticketNumber.doctor.id "+
			" WHEN :columnName  = 'ticketNumber' THEN i.ticketNumber " +
			" WHEN :columnName  = 'ProductivityType' THEN i.productivityType " +
//			" WHEN :columnName  = 'ctPostedDate' THEN i.ticketNumber.postedOn " +
			" WHEN :columnName  = 'ctPostedDate' THEN i.ticketNumber.dateBatchPosted " +
			" WHEN :columnName  = 'scanDate' THEN i.scanDate " +
			" WHEN :columnName  = 'time' THEN i.time " +
			" WHEN :columnName  = 'workFlow' THEN i.workFlow " +
			//	" WHEN :columnName  = 'Super Bills Doctor' THEN i.email " +
			" WHEN :columnName  = 'numberOfAcc' THEN i.t1 " +
			" WHEN :columnName  = 'NumberOfICD' THEN i.t2 " +
			" WHEN :columnName  = 'NumberOfCPT' THEN i.t3 " +
			" WHEN :columnName  = 'NewPatientCount' THEN i.t4 " +
			" WHEN :columnName  = 'ExistingPatientCount' THEN i.t5 " +
			" WHEN :columnName  = 'NumberOfReviews' THEN i.numberOfReviews " +
			" WHEN :columnName  = 'NumberOfReports' THEN i.numberOfReports " +
		//	" WHEN :columnName  = 'FFS' THEN i.received_by " +
		//	" WHEN :columnName  = 'CAP' THEN i.date_received " +
			" WHEN :columnName  = 'createdBy' THEN i.createdBy.firstName " +
			" WHEN :columnName  = 'createdOn' THEN i.createdOn " +
			" WHEN :columnName  = 'modifiedBy' THEN i.modifiedBy.id " +
			" WHEN :columnName  = 'modifiedOn' THEN i.modifiedOn ELSE i.id " +
			" END " +
			" END DESC, " +
			" CASE WHEN :sortDirection = 'asc' " +
			" THEN CASE " +
		//	" WHEN :columnName = 'doctorName' THEN i.ticketNumber.doctor.name "+
			" WHEN :columnName = 'doctorName' THEN i.ticketNumber.doctor.id "+
			" WHEN :columnName  = 'ticketNumber' THEN i.ticketNumber " +
			" WHEN :columnName  = 'ProductivityType Type' THEN i.productivityType " +
//			" WHEN :columnName  = 'ctPostedDate' THEN i.ticketNumber.postedOn " +
			" WHEN :columnName  = 'ctPostedDate' THEN i.ticketNumber.dateBatchPosted " +
			" WHEN :columnName  = 'scanDate' THEN i.scanDate " +
			" WHEN :columnName  = 'time' THEN i.time " +
			" WHEN :columnName  = 'workFlow' THEN i.workFlow " +
			//	" WHEN :columnName  = 'Super Bills Doctor' THEN i.email " +
			" WHEN :columnName  = 'numberOfAcc' THEN i.t1 " +
			" WHEN :columnName  = 'NumberOfICD' THEN i.t2 " +
			" WHEN :columnName  = 'NumberOfCPT' THEN i.t3 " +
			" WHEN :columnName  = 'NewPatientCount' THEN i.t4 " +
			" WHEN :columnName  = 'ExistingPatientCount' THEN i.t5 " +
			" WHEN :columnName  = 'NumberOfReviews' THEN i.numberOfReviews " +
			" WHEN :columnName  = 'NumberOfReports' THEN i.numberOfReports " +
			//	" WHEN :columnName  = 'FFS' THEN i.received_by " +
			//	" WHEN :columnName  = 'CAP' THEN i.date_received " +
			" WHEN :columnName  = 'createdBy' THEN i.createdBy.firstName " +
			" WHEN :columnName  = 'createdOn' THEN i.createdOn " +
			" WHEN :columnName  = 'modifiedBy' THEN i.modifiedBy.id " +
			" WHEN :columnName  = 'modifiedOn' THEN i.modifiedOn  " +
//			" WHEN :columnName  = 'email' THEN i.email " +
			" ELSE i.id END " +
			" END ASC , i.id desc")
	public Page<ChargeProductivity> findByQueryAll(@Param("prodType") String prodType,
												   @Param("companyId") Integer companyId,
												   @Param("groupId") Integer groupId,
												   @Param("workFlow") String workFlow,
												   @Param("ticketNumber") Integer ticketNumber,
												   @Param("postedBy") Integer postedBy,
												   @Param("onHold") Boolean onHold,
												   @Param("scanFrom") Date scanFrom,
												   @Param("scanTo") Date scanTo,
												   @Param("createdBy") Integer createdBy,
												   @Param("modifiedBy") Integer modifiedBy,
												   @Param("postedFrom") Date postedFrom,
												   @Param("postedTo") Date postedTo,

												   @Param("datePostedFrom") Date datePostedFrom,
												   @Param("datePostedTo") Date datePostedTo,
												   @Param("datePostedFromWithPostedon") Date datePostedFromWithPostedon,
												   @Param("datePostedToWithPostedon") Date datePostedToWithPostedon,
												   @Param("providerId") List<Integer> providerId,
												   @Param("columnName") String columnName,
												   @Param("sortDirection") String sortDirection,
												   Pageable pageable);



	@Query(value="SELECT * FROM charge_productivity where productivity_type=:productivityType and ticket_number= :ticketNumber", nativeQuery = true)
	List<ChargeProductivity> getByTicketNumberAndProductivityType(String ticketNumber
			,String productivityType);

	@Query("SELECT count(id)  FROM ChargeProductivity")
	public long countById();

	@Query("SELECT count(id)  FROM ChargeProductivity i where i.onHold=1")
	public long countonHold();

}
