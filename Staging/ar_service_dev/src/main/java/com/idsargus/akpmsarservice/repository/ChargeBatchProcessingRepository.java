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
import com.idsargus.akpmscommonservice.entity.ChargeBatchProcessing;

@RepositoryRestResource(path = ChargeBatchProcessingRepository.MODULE_NAME, collectionResourceRel = ChargeBatchProcessingRepository.MODULE_NAME)
public interface ChargeBatchProcessingRepository extends PagingAndSortingRepository<ChargeBatchProcessing, Integer>{

	public static final String MODULE_NAME = "chargeBatchProcessing";

	@Override
	<S extends ChargeBatchProcessing> S save(S chargeBatchProcessing);

//	@PreAuthorize("hasAuthority('role_admin')")
//	@RestResource(path = "customqueryall", rel = "customqueryall")
//	@Query("SELECT i FROM ChargeBatchProcessing i where  CONCAT(i.patientName, i.patientAccountNumber) LIKE %:query% AND (:statusCode is null or i.statusCode = :statusCode) AND (:source is null or i.source = :source) AND (:doctor is null or i.doctor.id = :doctor) AND (:insurance is null or i.insurance.id = :insurance) AND (:team is null or i.team.id = :team) AND (:subStatus is null or i.subStatus = :subStatus) AND (:createdFrom is null or i.createdOn > :createdFrom) AND (:createdTo is null or i.createdOn < :createdTo) AND (:followFrom is null or i.followUpDate > :followFrom) AND (:followto is null or i.followUpDate < :followto) AND (:createdBy is null or i.createdBy = :createdBy) ORDER BY i.patientName")
//	public Page<ChargeProductivity> findByQueryAll(@Param("query") String query, @Param("statusCode") String statusCode, @Param("source") String source, //, @Param("doctor") Integer doctor, Pageable pageable);
//			@Param("doctor") Integer doctor, @Param("insurance") Integer insurance, @Param("team") Integer team, @Param("subStatus") Integer subStatus, @Param("createdFrom") Date createdFrom, @Param("createdTo") Date createdTo, @Param("followFrom") Date followFrom, @Param("followto") Date followto, @Param("createdBy") String createdBy, Pageable pageable);
	
//	@RestResource(path = "customqueryall", rel = "customqueryall")
//	@Query("SELECT i FROM ChargeBatchProcessing i  where (:doctor is null OR i.doctor.id= :doctor) AND (:type is null OR i.type= :type) AND (:receivedBy is null OR i.receivedBy.id= :receivedBy)  AND (:isPostedBatch is false or i.postedBy.id != null) AND (:isUnPostedPostedBatch is false or i.postedBy.id = null) AND(:isBatchWithDiscrepancy is false or i.postedBy.id != null and i.numberOfSuperbills =0 )  AND (:postedBy is null OR i.postedBy.id= :postedBy) AND (:createdBy is null OR i.createdBy.id= :createdBy) AND (:tNo is null OR i.id= :tNo) AND(:isRecieved is null OR (:isRecieved = 0 AND i.receivedBy.id is null) OR (:isRecieved = 1 AND i.receivedBy.id is not null)) AND (:dosFrom is null OR i.dosFrom >= :dosFrom) AND (:dosTo is null OR i.dosTo <= :dosTo) AND (:createdFrom is null or i.createdOn > :createdFrom) AND (:createdTo is null or i.createdOn < :createdTo) AND (:receivedFrom is null or i.dateReceived > :receivedFrom) AND (:receivedTo is null or i.dateReceived < :receivedTo) AND (:postedFrom is null or i.dateBatchPosted > :postedFrom) AND (:postedTo is null or i.dateBatchPosted < :postedTo) ORDER BY i.id DESC")
//	public Page<ChargeBatchProcessing> findByQueryAll(@Param("doctor") Integer doctor, @Param("isPostedBatch") boolean isPostedBatch, @Param("isUnPostedPostedBatch") boolean isBatchWithDiscrepancy, @Param("isBatchWithDiscrepancy") boolean isUnPostedPostedBatch,  @Param("type") String type, @Param("receivedBy") Integer receivedBy, @Param("postedBy") Integer postedBy, @Param("createdBy") Integer createdBy, @Param("tNo") Integer tNo,  @Param("isRecieved") Integer isRecieved, @Param("dosFrom") Date dosFrom, @Param("dosTo") Date dosTo, @Param("createdFrom") Date createdFrom, @Param("createdTo") Date createdTo, @Param("receivedFrom") Date receivedFrom, @Param("receivedTo") Date receivedTo, @Param("postedFrom") Date postedFrom, @Param("postedTo") Date postedTo,  Pageable pageable);
//
	//Arindam Code Change
	
	@Query("SELECT count(id) FROM ChargeBatchProcessing")
	//	@Query(value= "SELECT count(*) FROM charge_batch_processing i cross join " +
//			" ar_productivity arp where i.ar_productivity_id=arp.id", nativeQuery = true)
	public long getChargeBatchProcessingCount();

	@Query(value = "SELECT * FROM charge_batch_processing c  where c.id = :ticketNumber", nativeQuery = true)
	public ChargeBatchProcessing findByTicketNumber(String ticketNumber);

@RestResource(path = "customqueryall", rel = "customqueryall")
	@Query(value="SELECT i FROM ChargeBatchProcessing i " +

			"where (COALESCE(:doctor) IS NULL or (i.doctor.id IN (:doctor)))" +
			"AND (:type is null or i.type= :type)" +
			"AND (:receivedBy is null OR i.receivedBy.id= :receivedBy)" +
			"AND (:companyId is null OR i.company.id= :companyId)" +
			"AND (:groupId is null OR i.group.id= :groupId)" +
			"AND (:isPostedBatch is false or i.postedBy is not null)" +
			"AND (:isUnPostedPostedBatch is false or i.postedBy is  null)" +
			"AND(:isBatchWithDiscrepancy is false or i.postedBy is not null and i.numberOfSuperbills = 0 )" +
			"AND (:postedBy is null OR i.postedBy.id= :postedBy)" +
			"AND (:createdBy is null OR i.createdBy.id = :createdBy)" +
//			"AND (:tNo is null OR i.id= :tNo)" +
			 "AND (coalesce(:tNo) is null OR (i.id IN (:tNo)))"+
			"AND (:tNoFrom is NULL OR i.id>= :tNoFrom) " +
			"AND (:tNoTo is NULL OR i.id<= :tNoTo) " +
			"AND(:isRecieved is null OR (:isRecieved = 0 AND i.receivedBy is null) OR (:isRecieved = 1 AND i.receivedBy is not null))" +
			"AND (:dosFrom is null OR DATE(i.dosFrom) >= :dosFrom)" +
			"AND (:dosTo is null OR DATE(i.dosTo )<= :dosTo)" +
			"AND (:createdFrom is null or DATE(i.createdOn) >= :createdFrom)" +
			"AND (:createdTo is null or DATE(i.createdOn) <= :createdTo)" +
			"AND (:receivedFrom is null or DATE(i.dateReceived) >= :receivedFrom)" +
			"AND (:receivedTo is null or DATE(i.dateReceived) <= :receivedTo)" +
		//	AND (:postedTo is null or DATE(i.postedOn) <= :postedTo)
		//	"AND (:postedFrom is null or DATE(i.postedOn) >= :postedFrom)" +
			"AND (:postedFrom is null or DATE(i.dateBatchPosted) >= :postedFrom)" +
			"AND (:postedTo is null or DATE(i.dateBatchPosted) <= :postedTo) ORDER BY CASE WHEN :sortDirection = 'desc' " +
			" THEN CASE " +
			//" WHEN :columnName = 'doctorName' THEN doctorAlias.name "+
			//" WHEN :columnName = 'doctorName' THEN i.doctor.name " +
		//	 " WHEN :columnName = 'doctorName' THEN i.id.doctor_id.name " +
			" WHEN :columnName = 'doctorName' THEN i.doctor.id " +
			" WHEN :columnName  = 'ticketNumber' THEN i.id " +
			" WHEN :columnName  = 'type' THEN i.type " +
			" WHEN :columnName  = 'from' THEN i.dosFrom " +
			" WHEN :columnName  = 'to' THEN i.dosTo " +
			//" WHEN :columnName  = 'postedBy' THEN userAlias.first_name " +
			//" WHEN :columnName  = 'ctPostedDate' THEN i.postedOn " +
			" WHEN :columnName  = 'ctPostedDate' THEN i.dateBatchPosted " +
			//	" WHEN :columnName  = 'Super Bills Doctor' THEN i.email " +
			" WHEN :columnName  = 'numberOfSuperbillsArgus' THEN i.numberOfSuperbillsArgus " +
	//		" WHEN :columnName  = 'receivedBy' THEN i.received_by " +
			" WHEN :columnName  = 'ReceivedDate' THEN i.dateReceived " +
			" WHEN :columnName  = 'createdBy' THEN i.createdBy.firstName" +

			" WHEN :columnName  = 'createdOn' THEN i.createdOn " +
			" WHEN :columnName  = 'modifiedBy' THEN i.modifiedBy " +
			" WHEN :columnName  = 'modifiedOn' THEN i.modifiedOn ELSE i.id " +
			" END " +
			" END DESC, " +
			" CASE WHEN :sortDirection = 'asc' " +
			" THEN CASE " +
			//" WHEN :columnName = 'doctorName' THEN doctorAlias.name "+
			//" WHEN :columnName = 'doctorName' THEN i.doctor.name " + ---- will not get data

			" WHEN :columnName = 'doctorName' THEN i.doctor.id " +
			" WHEN :columnName  = 'ticketNumber' THEN i.id " +
			" WHEN :columnName  = 'type' THEN i.type " +
			" WHEN :columnName  = 'from' THEN i.dosFrom " +
			" WHEN :columnName  = 'to' THEN i.dosTo " +
			//" WHEN :columnName  = 'postedBy' THEN userAlias.first_name " +
			//" WHEN :columnName  = 'ctPostedDate' THEN i.postedOn " +
			" WHEN :columnName  = 'ctPostedDate' THEN i.dateBatchPosted " +
			//	" WHEN :columnName  = 'Super Bills Doctor' THEN i.email " +
			" WHEN :columnName  = 'numberOfSuperbillsArgus' THEN i.numberOfSuperbillsArgus " +
	//		" WHEN :columnName  = 'receivedBy' THEN i.received_by " +
			" WHEN :columnName  = 'ReceivedDate' THEN i.dateReceived " +
			" WHEN :columnName  = 'createdBy' THEN i.createdBy.firstName " +

				" WHEN :columnName  = 'createdOn' THEN i.createdOn " +
			" WHEN :columnName  = 'modifiedBy' THEN i.modifiedBy " +
			" WHEN :columnName  = 'modifiedOn' THEN i.modifiedOn ELSE i.id " +
			" END " +
			" END ASC, i.id desc ")
	public Page<ChargeBatchProcessing> findByQueryCustomAll(@Param("doctor") List<Integer> doctor,
													  @Param("companyId") Integer companyId,
													  @Param("groupId") Integer groupId,
													  @Param("isPostedBatch") boolean isPostedBatch,
													  @Param("isBatchWithDiscrepancy") boolean isBatchWithDiscrepancy,
													  @Param("isUnPostedPostedBatch") boolean isUnPostedPostedBatch,
													  @Param("type") String type, @Param("receivedBy") Integer receivedBy,
													  @Param("postedBy") Integer postedBy,
													  @Param("createdBy") Integer createdBy,
//													  @Param("tNo") Integer tNo,
															@Param("tNo") List<Integer> tNo,
															 @Param("tNoFrom") Integer tNoFrom,
																 @Param("tNoTo") Integer tNoTo,
													  @Param("isRecieved") Integer isRecieved,
													  @Param("dosFrom") Date dosFrom, @Param("dosTo") Date dosTo,
													  @Param("createdFrom") Date createdFrom, @Param("createdTo") Date createdTo,
													  @Param("receivedFrom") Date receivedFrom,
													  @Param("receivedTo") Date receivedTo,
													  @Param("postedFrom") Date postedFrom, @Param("postedTo") Date postedTo,
													  @Param("sortDirection") String sortDirection,
													  @Param("columnName") String columnName,
													  Pageable pageable);
//	@RestResource(path = "customqueryall", rel = "customqueryall")
//	@Query(value="SELECT * FROM charge_batch_processing i " +
////			"join doctor doctorAlias on doctorAlias.id = i.doctor_id " +
////			" join user userAlias on userAlias.id = i.posted_by " +
//			"where (COALESCE(:doctor) IS NULL or (i.doctor_id IN (:doctor)))" +
//			"AND (:type is null or i.type= :type)" +
//			"AND (:receivedBy is null OR i.received_by= :receivedBy)" +
//			"AND (:isPostedBatch is false or i.posted_by is not null)" +
//			"AND (:isUnPostedPostedBatch is false or i.posted_by is  null)" +
//			"AND(:isBatchWithDiscrepancy is false or i.posted_by is not null and i.number_of_superbills = 0 )" +
//			"AND (:postedBy is null OR i.posted_by= :postedBy)" +
//			"AND (:createdBy is null OR i.created_by= :createdBy)" +
//			"AND (:tNo is null OR i.id= :tNo)" +
//			"AND(:isRecieved is null OR (:isRecieved = 0 AND i.received_by is null) OR (:isRecieved = 1 AND i.received_by is not null))" +
//			"AND (:dosFrom is null OR DATE(i.dos_from) >= :dosFrom)" +
//			"AND (:dosTo is null OR DATE(i.dos_to )<= :dosTo)" +
//			"AND (:createdFrom is null or DATE(i.created_on) >= :createdFrom)" +
//			"AND (:createdTo is null or DATE(i.created_on) <= :createdTo)" +
//			"AND (:receivedFrom is null or DATE(i.date_received) >= :receivedFrom)" +
//			"AND (:receivedTo is null or DATE(i.date_received) <= :receivedTo)" +
//			"AND (:postedFrom is null or DATE(i.posted_on) >= :postedFrom)" +
//			"AND (:postedTo is null or DATE(i.posted_on) <= :postedTo) ORDER BY CASE WHEN :sortDirection = 'desc' " +
//			" THEN CASE " +
//			//" WHEN :columnName = 'doctorName' THEN doctorAlias.name "+
//			//" WHEN :columnName = 'doctorName' THEN i.doctor_id.name " +
//		//	 " WHEN :columnName = 'doctorName' THEN i.id.doctor_id.name " +
//		//	" WHEN :columnName = 'doctorName' THEN i.doctor.name " +
//			" WHEN :columnName  = 'ticketNumber' THEN i.id " +
//			" WHEN :columnName  = 'type' THEN i.type " +
//			" WHEN :columnName  = 'from' THEN i.dos_from " +
//			" WHEN :columnName  = 'to' THEN i.dos_to " +
//			//" WHEN :columnName  = 'postedBy' THEN userAlias.first_name " +
//			" WHEN :columnName  = 'ctPostedDate' THEN i.posted_on " +
//			//	" WHEN :columnName  = 'Super Bills Doctor' THEN i.email " +
//			" WHEN :columnName  = 'numberOfSuperbillsArgus' THEN i.number_of_superbills_argus " +
//			" WHEN :columnName  = 'numberOfSuperbills' THEN i.number_of_superbills " +
//		    " WHEN :columnName  = 'numberOfAttachmentsArgus' THEN i.number_of_attachments_argus " +
//			" WHEN :columnName  = 'numberOfAttachments' THEN i.number_of_attachments " +
//			" WHEN :columnName  = 'receivedBy' THEN i.received_by " +
//			" WHEN :columnName  = 'ReceivedDate' THEN i.date_received " +
//		//	" WHEN :columnName  = 'createdBy' THEN i.created_by.first_name " +
//			" WHEN :columnName  = 'createdBy' THEN i.created_by " +
//			" WHEN :columnName  = 'createdOn' THEN i.created_on " +
//			" WHEN :columnName  = 'modifiedBy' THEN i.modified_by " +
//			" WHEN :columnName  = 'modifiedOn' THEN i.modified_on ELSE i.id " +
//			" END " +
//			" END DESC, " +
//			" CASE WHEN :sortDirection = 'asc' " +
//			" THEN CASE " +
//		//	" WHEN :columnName = 'doctorName' THEN i.doctor_id.id " +
//		//	" WHEN :columnName = 'doctorName' THEN i.doctor_id.name " +
//		//	" WHEN :columnName = 'doctorName' THEN i.id.doctor_id.name " +
//		//	" WHEN :columnName = 'doctorName' THEN i.doctor.name " +
//			" WHEN :columnName  = 'ticketNumber' THEN i.id " +
//			" WHEN :columnName  = 'type' THEN i.type " +
//			" WHEN :columnName  = 'from' THEN i.dos_from " +
//			" WHEN :columnName  = 'to' THEN i.dos_to " +
//			//	" WHEN :columnName  = 'postedBy' THEN i.posted_by " +
//			" WHEN :columnName  = 'ctPostedDate' THEN i.posted_on " +
//			//	" WHEN :columnName  = 'Super Bills Doctor' THEN i.email " +
//			" WHEN :columnName  = 'numberOfSuperbillsArgus' THEN i.number_of_superbills_argus " +
//			" WHEN :columnName  = 'numberOfSuperbills' THEN i.number_of_superbills " +
//			" WHEN :columnName  = 'numberOfAttachmentsArgus' THEN i.number_of_attachments_argus " +
//			" WHEN :columnName  = 'numberOfAttachments' THEN i.number_of_attachments " +
//			" WHEN :columnName  = 'receivedBy' THEN i.received_by " +
//			" WHEN :columnName  = 'ReceivedDate' THEN i.date_received " +
//		//	" WHEN :columnName  = 'createdBy' THEN i.created_by.first_name " +
//			" WHEN :columnName  = 'createdBy' THEN i.created_by " +
//			" WHEN :columnName  = 'createdOn' THEN i.created_on " +
//			" WHEN :columnName  = 'modifiedBy' THEN i.modified_by " +
//			" WHEN :columnName  = 'modifiedOn' THEN i.modified_on ELSE i.id " +
////			" WHEN :columnName  = 'email' THEN i.email " +
////			" WHEN :columnName  = 'createdOn' THEN i.createdOn " +
////			" WHEN :columnName  = 'modifiedOn' THEN i.modifiedOn ELSE i.firstName " +
//			" END " +
//			" END ASC, i.id desc "
//			,nativeQuery = true)
//	public Page<ChargeBatchProcessing> findByQueryAll(@Param("doctor") List<Integer> doctor,
//													  @Param("isPostedBatch") boolean isPostedBatch,
//													  @Param("isBatchWithDiscrepancy") boolean isBatchWithDiscrepancy,
//													  @Param("isUnPostedPostedBatch") boolean isUnPostedPostedBatch,
//													  @Param("type") String type, @Param("receivedBy") Integer receivedBy,
//													  @Param("postedBy") Integer postedBy,
//													  @Param("createdBy") Integer createdBy,
//													  @Param("tNo") Integer tNo,
//													  @Param("isRecieved") Integer isRecieved,
//													  @Param("dosFrom") Date dosFrom, @Param("dosTo") Date dosTo,
//													  @Param("createdFrom") Date createdFrom, @Param("createdTo") Date createdTo,
//													  @Param("receivedFrom") Date receivedFrom,
//													  @Param("receivedTo") Date receivedTo,
//													  @Param("postedFrom") Date postedFrom, @Param("postedTo") Date postedTo,
//													  @Param("sortDirection") String sortDirection,
//													  @Param("columnName") String columnName,
//													  Pageable pageable);
//	//
}
