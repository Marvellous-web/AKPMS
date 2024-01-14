package com.idsargus.akpmsarservice.repository;

import com.idsargus.akpmsarservice.model.domain.ArChargeProdReject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.idsargus.akpmscommonservice.entity.ChargeProdReject;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@RepositoryRestResource(path = ChargeProdRejectionRepository.MODULE_NAME, collectionResourceRel = ChargeProdRejectionRepository.MODULE_NAME)
public interface ChargeProdRejectionRepository extends PagingAndSortingRepository<ArChargeProdReject, Integer> {

    public static final String MODULE_NAME = "chargeProdReject";

    @Override
    <S extends ArChargeProdReject> S save(S arChargeProdReject);


//    @RestResource(path = "customqueryall", rel = "customqueryall")
//    @Query("SELECT i FROM ArChargeProdReject i where  " +
//            "(:query is null or CONCAT(i.patientName,i.account , i.chargeBatchProcessing.id) LIKE %:query% ) " +
//            " AND " +
//            " (COALESCE(:doctorId) is null or (i.chargeBatchProcessing.doctor.id IN (:doctorId))) " +
//            " AND (:groupId is null or i.chargeBatchProcessing.group.id=:groupId) " +
//            " AND (:companyId is null or i.chargeBatchProcessing.company.id=:companyId) " +
//            " AND (:createdTo is null or DATE(i.createdOn) <= :createdTo) AND (:createdFrom is null or DATE(i.createdOn) >= :createdFrom) AND " +
//         //   " (:isNewRejection is false or (DATEDIFF(CURRENT_DATE, i.createdOn) + 1) <= 5 )" +
//             " (:isNewRejection is false or ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) <= 5 )" +
////
////
//            " AND " +
//            " (:isFirstRequestRecord is false or ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) > 10) " +
//            "AND (:isFirstRequestRecord is false Or ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) <= 15 ) " +
//          //  " AND DAYOFWEEk(i.createdOn) not in (1,7) " +
//            "AND (:isFirstRequestDueRecord is false or ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) > 5) " +
//            " AND (:isFirstRequestDueRecord is false  or ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) <= 10 ) " +
//            "AND  (:isSecondRequestRecord is false or ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) > 20 )  " +
////            " AND (:isSecondRequestRecord is false  or ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) <= 25 ) " +
//
//            " AND (:isSecondRequestDueRecord is false or ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) > 15 ) " +
//            " AND (:isSecondRequestDueRecord is false or ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) <= 20 ) " +
//      //      "AND  (:status is null or i.status= :status) " +
//            "AND (COALESCE(:status) is null or (i.status IN (:status))) " +
//            " AND (:reasonToReject is null or i.reasonToReject= :reasonToReject) " +
//            "AND (:workFlow is null or i.workFlow= :workFlow)  " +
//            " AND (:createdBy is null or i.createdBy.id = :createdBy)" +
//            "AND (:dummyCpt is null or i.dummyCpt= :dummyCpt) " +
//         //   "AND (COALESCE(:dummyCpt) is null or i.dummyCpt= :dummyCpt) " +
//           //+ "AND (COALESCE(:doctor) IS NULL or (i.doctor.id IN (:doctor))) "
//            " ORDER BY CASE WHEN :sortDirection = 'desc' " +
//            " THEN CASE " +
//            " WHEN :columnName = 'status' THEN i.status "+
//          //  " WHEN :columnName = 'doctorName' THEN i.chargeBatchProcessing.doctor.name "+
//            " WHEN :columnName = 'doctorName' THEN i.chargeBatchProcessing.doctor.id "+
//            " WHEN :columnName  = 'location' THEN i.location.name " +
//            " WHEN :columnName  = 'workFlow' THEN i.workFlow " +
//            " WHEN :columnName  = 'ticketNumber' THEN i.chargeBatchProcessing.id " +
//            " WHEN :columnName = 'reasonToReject' THEN i.reasonToReject "+
//            " WHEN :columnName  = 'patientName' THEN i.patientName " +
//            " WHEN :columnName  = 'dob' THEN i.dob " +
//            " WHEN :columnName = 'sequence' THEN i.sequence "+
//            " WHEN :columnName  = 'account' THEN i.account " +
//            " WHEN :columnName  = 'dos' THEN i.dateOfService " +
//            " WHEN :columnName  = 'insuranceType' THEN i.insuranceType " +
//            " WHEN :columnName  = 'insuranceType' THEN i.patientName " +
//         //   " WHEN :columnName  = 'batchRecieved' THEN i.createdOn " +
//            " WHEN :columnName = 'createdBy' THEN i.createdBy.firstName "+
//            " WHEN :columnName  = 'createdOn' THEN i.createdOn " +
//            " WHEN :columnName  = 'completedDate' THEN i.completedOn " +
//            " WHEN :columnName  = 'modifiedBy' THEN i.modifiedBy.id " +
//            " WHEN :columnName  = 'modifiedOn' THEN i.modifiedOn " +
//            " WHEN :columnName  = 'dateOf1stRequest' THEN i.dateOfFirstRequestToDoctorOffice " +
//            " WHEN :columnName  = 'dateOf2ndRequest' THEN i.dateOfSecondRequestToDoctorOffice ELSE i.id END " +
//            " END DESC, " +
//            " CASE WHEN :sortDirection = 'asc' " +
//            " THEN CASE " +
//            " WHEN :columnName = 'status' THEN i.status "+
//       //     " WHEN :columnName = 'doctorName' THEN i.chargeBatchProcessing.doctor.name "+
//            " WHEN :columnName = 'doctorName' THEN i.chargeBatchProcessing.doctor.id "+
//            " WHEN :columnName  = 'location' THEN i.location.name " +
//            " WHEN :columnName  = 'workFlow' THEN i.workFlow " +
//            " WHEN :columnName  = 'ticketNumber' THEN i.chargeBatchProcessing.id " +
//            " WHEN :columnName = 'reasonToReject' THEN i.reasonToReject "+
//            " WHEN :columnName  = 'patientName' THEN i.patientName " +
//            " WHEN :columnName  = 'dob' THEN i.dob " +
//            " WHEN :columnName = 'sequence' THEN i.sequence "+
//            " WHEN :columnName  = 'account' THEN i.account " +
//            " WHEN :columnName  = 'dos' THEN i.dateOfService " +
//            " WHEN :columnName  = 'insuranceType' THEN i.insuranceType " +
//            " WHEN :columnName  = 'patientName' THEN i.patientName " +
//            //   " WHEN :columnName  = 'batchRecieved' THEN i.createdOn " +
//            " WHEN :columnName = 'createdBy' THEN i.createdBy.firstName "+
//            " WHEN :columnName  = 'createdOn' THEN i.createdOn " +
//            " WHEN :columnName  = 'completedDate' THEN i.completedOn " +
//            " WHEN :columnName  = 'modifiedBy' THEN i.modifiedBy.id " +
//            " WHEN :columnName  = 'modifiedOn' THEN i.modifiedOn " +
//            " WHEN :columnName  = 'dateOf1stRequest' THEN i.dateOfFirstRequestToDoctorOffice " +
//            " WHEN :columnName  = 'dateOf2ndRequest' THEN i.dateOfSecondRequestToDoctorOffice ELSE i.id END " +
//            " END ASC , i.id desc")
//    Page<ArChargeProdReject> findByQueryAll(
//            @Param("query") String query,
//        //    @Param("status") String status,
//            @Param("status") List<String> status,
//            @Param("workFlow") Integer workFlow,
//            @Param("isNewRejection") boolean isNewRejection,
//            @Param("isFirstRequestRecord") boolean isFirstRequestRecord,
//            @Param("isFirstRequestDueRecord") boolean isFirstRequestDueRecord,
//            @Param("isSecondRequestDueRecord") boolean isSecondRequestDueRecord,
//            @Param("isSecondRequestRecord") boolean isSecondRequestRecord,
//            @Param("doctorId") List<Integer> doctorId,
//            @Param("companyId") Integer companyId,
//            @Param("groupId") Integer groupId,
//            @Param("reasonToReject") String reasonToReject,
//            @Param("createdTo") Date createdTo,
//            @Param("createdBy") Integer createdBy,
//            @Param("createdFrom") Date createdFrom,
//       //     @Param("dummyCpt")boolean dummyCpt,
//             @Param("dummyCpt") String dummyCpt,
//            @Param("columnName") String columnName,
//            @Param("sortDirection") String sortDirection,
//            Pageable pageable);
//        @Query("SELECT count(id)  FROM ArChargeProdReject")
//
//    public long countById();
//
//    @Query("SELECT count(i)  FROM ArChargeProdReject i WHERE i.status ='Pending' and ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) <= 5")
//        public long countByNewRejection();
//
//    @Query("SELECT count(i)  FROM ArChargeProdReject i WHERE i.status ='Pending' and ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) > 10 and ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) <= 15")
//        public long countByFirstRequestRecord();
//
//    @Query("SELECT count(i)  FROM ArChargeProdReject i WHERE i.status ='Pending' and ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) > 5 and ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) <= 10")
//        public long countByFirstRequestDueRecord();
//
////    @Query("SELECT count(i)  FROM ArChargeProdReject i WHERE i.status ='Pending' and  ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) > 20 and ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) <= 25")
//    @Query("SELECT count(i)  FROM ArChargeProdReject i WHERE i.status ='Pending' and  ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) > 20 ")
//        public long countBySecondRequestRecord();
//
//    @Query("SELECT count(i)  FROM ArChargeProdReject i WHERE i.status ='Pending' and  ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) > 15 and ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) <= 20")
//        public long countBySecondRequestDueRecord();
//
//    @Query("SELECT count(i)  FROM ArChargeProdReject i WHERE i.status ='Resolved' ")
//        public long countByResolvedRejections();


//        @RestResource(path = "customqueryall", rel = "customqueryall")
//    @Query("SELECT i FROM ArChargeProdReject i where  " +
//            "(:query is null or CONCAT(i.patientName,i.account , i.chargeBatchProcessing.id) LIKE %:query% ) " +
//
//            "AND (COALESCE(:doctorId) is null or (i.chargeBatchProcessing.doctor.id IN (:doctorId))) " +
//            " AND (:groupId is null or i.chargeBatchProcessing.group.id=:groupId) " +
//            " AND (:companyId is null or i.chargeBatchProcessing.company.id=:companyId) " +
//            "AND (:createdTo is null or DATE(i.createdOn) <= :createdTo) AND (:createdFrom is null or DATE(i.createdOn) >= :createdFrom)  " +
//
//             " AND (:isNewRejection is false or (i.dateOfFirstRequestToDoctorOffice is null and ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) < 5) or (DATEDIFF(i.dateOfFirstRequestToDoctorOffice, CURRENT_DATE ) ) >= 0 )" +
//
//              "AND (:isFirstRequestDueRecord is false or ((DATEDIFF(CURRENT_DATE, i.dateOfFirstRequestToDoctorOffice ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.dateOfFirstRequestToDoctorOffice)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.dateOfFirstRequestToDoctorOffice) >= 6  THEN 1 ELSE 0 end )) >= 0 or (i.dateOfFirstRequestToDoctorOffice is  null and ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) >= 5) )" +
//            " AND (:isFirstRequestDueRecord is false  or ((DATEDIFF(CURRENT_DATE, i.dateOfFirstRequestToDoctorOffice ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.dateOfFirstRequestToDoctorOffice)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.dateOfFirstRequestToDoctorOffice) >= 6  THEN 1 ELSE 0 end )) < 5 or (i.dateOfFirstRequestToDoctorOffice is  null and ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) < 10 ) )" +
//
//            " AND (:isFirstRequestRecord is false or  ((DATEDIFF(CURRENT_DATE, i.dateOfFirstRequestToDoctorOffice ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.dateOfFirstRequestToDoctorOffice)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.dateOfFirstRequestToDoctorOffice) >= 6  THEN 1 ELSE 0 end )) >= 5 or (i.dateOfFirstRequestToDoctorOffice is  null and ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) >= 10) )" +
//            "AND (:isFirstRequestRecord is false or  ((DATEDIFF(CURRENT_DATE, i.dateOfFirstRequestToDoctorOffice ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.dateOfFirstRequestToDoctorOffice)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.dateOfFirstRequestToDoctorOffice) >= 6  THEN 1 ELSE 0 end )) < 10 or (i.dateOfFirstRequestToDoctorOffice is  null and ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) < 15) )" +
//
//
//            " AND (:isSecondRequestDueRecord is false or ((DATEDIFF(CURRENT_DATE, i.dateOfSecondRequestToDoctorOffice ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.dateOfSecondRequestToDoctorOffice)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.dateOfSecondRequestToDoctorOffice) >= 6  THEN 1 ELSE 0 end )) >= 0 or (i.dateOfSecondRequestToDoctorOffice is  null and ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) >= 15) )" +
//            " AND (:isSecondRequestDueRecord is false or ((DATEDIFF(CURRENT_DATE, i.dateOfSecondRequestToDoctorOffice ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.dateOfSecondRequestToDoctorOffice)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.dateOfSecondRequestToDoctorOffice) >= 6  THEN 1 ELSE 0 end )) < 5 or (i.dateOfSecondRequestToDoctorOffice is  null and ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) < 20)) " +
//
//            " AND (:isSecondRequestRecord is false or ((DATEDIFF(CURRENT_DATE, i.dateOfSecondRequestToDoctorOffice ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.dateOfSecondRequestToDoctorOffice)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.dateOfSecondRequestToDoctorOffice) >= 6  THEN 1 ELSE 0 end )) >= 5 or (i.dateOfSecondRequestToDoctorOffice is  null and ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) >= 20) ) " +
//
//
//      //      "AND  (:status is null or i.status= :status) " +
//            "AND (COALESCE(:status) is null or (i.status IN (:status))) " +
//            " AND (:reasonToReject is null or i.reasonToReject= :reasonToReject) " +
//            "AND (:workFlow is null or i.workFlow= :workFlow)  " +
//            " AND (:createdBy is null or i.createdBy.id = :createdBy)" +
//            "AND (:dummyCpt is null or i.dummyCpt= :dummyCpt) " +
//         //   "AND (COALESCE(:dummyCpt) is null or i.dummyCpt= :dummyCpt) " +
//           //+ "AND (COALESCE(:doctor) IS NULL or (i.doctor.id IN (:doctor))) "
//            " ORDER BY CASE WHEN :sortDirection = 'desc' " +
//            " THEN CASE " +
//            " WHEN :columnName = 'status' THEN i.status "+
//          //  " WHEN :columnName = 'doctorName' THEN i.chargeBatchProcessing.doctor.name "+
//            " WHEN :columnName = 'doctorName' THEN i.chargeBatchProcessing.doctor.id "+
//            " WHEN :columnName  = 'location' THEN i.location.name " +
//            " WHEN :columnName  = 'workFlow' THEN i.workFlow " +
//            " WHEN :columnName  = 'ticketNumber' THEN i.chargeBatchProcessing.id " +
//            " WHEN :columnName = 'reasonToReject' THEN i.reasonToReject "+
//            " WHEN :columnName  = 'patientName' THEN i.patientName " +
//            " WHEN :columnName  = 'dob' THEN i.dob " +
//            " WHEN :columnName = 'sequence' THEN i.sequence "+
//            " WHEN :columnName  = 'account' THEN i.account " +
//            " WHEN :columnName  = 'dos' THEN i.dateOfService " +
//            " WHEN :columnName  = 'insuranceType' THEN i.insuranceType " +
//            " WHEN :columnName  = 'insuranceType' THEN i.patientName " +
//         //   " WHEN :columnName  = 'batchRecieved' THEN i.createdOn " +
//            " WHEN :columnName = 'createdBy' THEN i.createdBy.firstName "+
//            " WHEN :columnName  = 'createdOn' THEN i.createdOn " +
//            " WHEN :columnName  = 'completedDate' THEN i.completedOn " +
//            " WHEN :columnName  = 'modifiedBy' THEN i.modifiedBy.id " +
//            " WHEN :columnName  = 'modifiedOn' THEN i.modifiedOn " +
//            " WHEN :columnName  = 'dateOf1stRequest' THEN i.dateOfFirstRequestToDoctorOffice " +
//            " WHEN :columnName  = 'dateOf2ndRequest' THEN i.dateOfSecondRequestToDoctorOffice ELSE i.id END " +
//            " END DESC, " +
//            " CASE WHEN :sortDirection = 'asc' " +
//            " THEN CASE " +
//            " WHEN :columnName = 'status' THEN i.status "+
//       //     " WHEN :columnName = 'doctorName' THEN i.chargeBatchProcessing.doctor.name "+
//            " WHEN :columnName = 'doctorName' THEN i.chargeBatchProcessing.doctor.id "+
//            " WHEN :columnName  = 'location' THEN i.location.name " +
//            " WHEN :columnName  = 'workFlow' THEN i.workFlow " +
//            " WHEN :columnName  = 'ticketNumber' THEN i.chargeBatchProcessing.id " +
//            " WHEN :columnName = 'reasonToReject' THEN i.reasonToReject "+
//            " WHEN :columnName  = 'patientName' THEN i.patientName " +
//            " WHEN :columnName  = 'dob' THEN i.dob " +
//            " WHEN :columnName = 'sequence' THEN i.sequence "+
//            " WHEN :columnName  = 'account' THEN i.account " +
//            " WHEN :columnName  = 'dos' THEN i.dateOfService " +
//            " WHEN :columnName  = 'insuranceType' THEN i.insuranceType " +
//            " WHEN :columnName  = 'patientName' THEN i.patientName " +
//            //   " WHEN :columnName  = 'batchRecieved' THEN i.createdOn " +
//            " WHEN :columnName = 'createdBy' THEN i.createdBy.firstName "+
//            " WHEN :columnName  = 'createdOn' THEN i.createdOn " +
//            " WHEN :columnName  = 'completedDate' THEN i.completedOn " +
//            " WHEN :columnName  = 'modifiedBy' THEN i.modifiedBy.id " +
//            " WHEN :columnName  = 'modifiedOn' THEN i.modifiedOn " +
//            " WHEN :columnName  = 'dateOf1stRequest' THEN i.dateOfFirstRequestToDoctorOffice " +
//            " WHEN :columnName  = 'dateOf2ndRequest' THEN i.dateOfSecondRequestToDoctorOffice ELSE i.id END " +
//            " END ASC , i.id desc")
    //Prateek
@RestResource(path = "customqueryall", rel = "customqueryall")
@Query("SELECT i FROM ArChargeProdReject i where  " +
        "(:query is null or CONCAT(i.patientName,i.account , i.chargeBatchProcessing.id) LIKE %:query% ) " +

        "AND (COALESCE(:doctorId) is null or (i.chargeBatchProcessing.doctor.id IN (:doctorId))) " +
        " AND (:groupId is null or i.chargeBatchProcessing.group.id=:groupId) " +
        " AND (:companyId is null or i.chargeBatchProcessing.company.id=:companyId) " +
        "AND (:createdTo is null or DATE(i.createdOn) <= :createdTo) AND (:createdFrom is null or DATE(i.createdOn) >= :createdFrom)  " +

        " AND (:isNewRejection is false or (i.dateOfFirstRequestToDoctorOffice is null and ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) < 5) or (DATEDIFF(i.dateOfFirstRequestToDoctorOffice, CURRENT_DATE ) ) >= 0 )" +

        "AND (:isFirstRequestDueRecord is false or ((DATEDIFF(CURRENT_DATE, i.dateOfFirstRequestToDoctorOffice ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.dateOfFirstRequestToDoctorOffice)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.dateOfFirstRequestToDoctorOffice) >= 6  THEN 1 ELSE 0 end )) >= 0 or (i.dateOfFirstRequestToDoctorOffice is  null and ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) >= 5) )" +
        " AND (:isFirstRequestDueRecord is false  or ((DATEDIFF(CURRENT_DATE, i.dateOfFirstRequestToDoctorOffice ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.dateOfFirstRequestToDoctorOffice)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.dateOfFirstRequestToDoctorOffice) >= 6  THEN 1 ELSE 0 end )) < 5 or (i.dateOfFirstRequestToDoctorOffice is  null and ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) < 10 ) )" +

        " AND (:isFirstRequestRecord is false or  ((DATEDIFF(CURRENT_DATE, i.dateOfFirstRequestToDoctorOffice ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.dateOfFirstRequestToDoctorOffice)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.dateOfFirstRequestToDoctorOffice) >= 6  THEN 1 ELSE 0 end )) >= 5 or (i.dateOfFirstRequestToDoctorOffice is  null and ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) >= 10) )" +
        //Prateek
       // " AND (:isFirstRequestRecord is false or ((DATEDIFF(CURRENT_DATE, i.dateOfFirstRequestToDoctorOffice ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.dateOfFirstRequestToDoctorOffice)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.dateOfFirstRequestToDoctorOffice) >= 6  THEN 1 ELSE 0 end )) >= 5 or (i.dateOfFirstRequestToDoctorOffice is null and ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) >= 10) or (:dateProvidedManually = true and :isFirstRequestRecord = true)) )" +
        "AND (:isFirstRequestRecord is false or  ((DATEDIFF(CURRENT_DATE, i.dateOfFirstRequestToDoctorOffice ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.dateOfFirstRequestToDoctorOffice)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.dateOfFirstRequestToDoctorOffice) >= 6  THEN 1 ELSE 0 end )) < 10 or (i.dateOfFirstRequestToDoctorOffice is  null and ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) < 15) )" +


        " AND (:isSecondRequestDueRecord is false or ((DATEDIFF(CURRENT_DATE, i.dateOfSecondRequestToDoctorOffice ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.dateOfSecondRequestToDoctorOffice)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.dateOfSecondRequestToDoctorOffice) >= 6  THEN 1 ELSE 0 end )) >= 0 or (i.dateOfSecondRequestToDoctorOffice is  null and ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) >= 15) )" +
        " AND (:isSecondRequestDueRecord is false or ((DATEDIFF(CURRENT_DATE, i.dateOfSecondRequestToDoctorOffice ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.dateOfSecondRequestToDoctorOffice)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.dateOfSecondRequestToDoctorOffice) >= 6  THEN 1 ELSE 0 end )) < 5 or (i.dateOfSecondRequestToDoctorOffice is  null and ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) < 20)) " +

        " AND (:isSecondRequestRecord is false or ((DATEDIFF(CURRENT_DATE, i.dateOfSecondRequestToDoctorOffice ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.dateOfSecondRequestToDoctorOffice)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.dateOfSecondRequestToDoctorOffice) >= 6  THEN 1 ELSE 0 end )) >= 5 or (i.dateOfSecondRequestToDoctorOffice is  null and ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) >= 20) ) " +


        //      "AND  (:status is null or i.status= :status) " +
        "AND (COALESCE(:status) is null or (i.status IN (:status))) " +
        " AND (:reasonToReject is null or i.reasonToReject= :reasonToReject) " +
        "AND (:workFlow is null or i.workFlow= :workFlow)  " +
        " AND (:createdBy is null or i.createdBy.id = :createdBy)" +
        "AND (:dummyCpt is null or i.dummyCpt= :dummyCpt) " +
        //   "AND (COALESCE(:dummyCpt) is null or i.dummyCpt= :dummyCpt) " +
        //+ "AND (COALESCE(:doctor) IS NULL or (i.doctor.id IN (:doctor))) "
        " ORDER BY CASE WHEN :sortDirection = 'desc' " +
        " THEN CASE " +
        " WHEN :columnName = 'status' THEN i.status "+
        //  " WHEN :columnName = 'doctorName' THEN i.chargeBatchProcessing.doctor.name "+
        " WHEN :columnName = 'doctorName' THEN i.chargeBatchProcessing.doctor.id "+
        " WHEN :columnName  = 'location' THEN i.location.name " +
        " WHEN :columnName  = 'workFlow' THEN i.workFlow " +
        " WHEN :columnName  = 'ticketNumber' THEN i.chargeBatchProcessing.id " +
        " WHEN :columnName = 'reasonToReject' THEN i.reasonToReject "+
        " WHEN :columnName  = 'patientName' THEN i.patientName " +
        " WHEN :columnName  = 'dob' THEN i.dob " +
        " WHEN :columnName = 'sequence' THEN i.sequence "+
        " WHEN :columnName  = 'account' THEN i.account " +
        " WHEN :columnName  = 'dos' THEN i.dateOfService " +
        " WHEN :columnName  = 'insuranceType' THEN i.insuranceType " +
        " WHEN :columnName  = 'insuranceType' THEN i.patientName " +
        //   " WHEN :columnName  = 'batchRecieved' THEN i.createdOn " +
        " WHEN :columnName = 'createdBy' THEN i.createdBy.firstName "+
        " WHEN :columnName  = 'createdOn' THEN i.createdOn " +
        " WHEN :columnName  = 'completedDate' THEN i.completedOn " +
        " WHEN :columnName  = 'modifiedBy' THEN i.modifiedBy.id " +
        " WHEN :columnName  = 'modifiedOn' THEN i.modifiedOn " +
        " WHEN :columnName  = 'dateOf1stRequest' THEN i.dateOfFirstRequestToDoctorOffice " +
        " WHEN :columnName  = 'dateOf2ndRequest' THEN i.dateOfSecondRequestToDoctorOffice ELSE i.id END " +
        " END DESC, " +
        " CASE WHEN :sortDirection = 'asc' " +
        " THEN CASE " +
        " WHEN :columnName = 'status' THEN i.status "+
        //     " WHEN :columnName = 'doctorName' THEN i.chargeBatchProcessing.doctor.name "+
        " WHEN :columnName = 'doctorName' THEN i.chargeBatchProcessing.doctor.id "+
        " WHEN :columnName  = 'location' THEN i.location.name " +
        " WHEN :columnName  = 'workFlow' THEN i.workFlow " +
        " WHEN :columnName  = 'ticketNumber' THEN i.chargeBatchProcessing.id " +
        " WHEN :columnName = 'reasonToReject' THEN i.reasonToReject "+
        " WHEN :columnName  = 'patientName' THEN i.patientName " +
        " WHEN :columnName  = 'dob' THEN i.dob " +
        " WHEN :columnName = 'sequence' THEN i.sequence "+
        " WHEN :columnName  = 'account' THEN i.account " +
        " WHEN :columnName  = 'dos' THEN i.dateOfService " +
        " WHEN :columnName  = 'insuranceType' THEN i.insuranceType " +
        " WHEN :columnName  = 'patientName' THEN i.patientName " +
        //   " WHEN :columnName  = 'batchRecieved' THEN i.createdOn " +
        " WHEN :columnName = 'createdBy' THEN i.createdBy.firstName "+
        " WHEN :columnName  = 'createdOn' THEN i.createdOn " +
        " WHEN :columnName  = 'completedDate' THEN i.completedOn " +
        " WHEN :columnName  = 'modifiedBy' THEN i.modifiedBy.id " +
        " WHEN :columnName  = 'modifiedOn' THEN i.modifiedOn " +
        " WHEN :columnName  = 'dateOf1stRequest' THEN i.dateOfFirstRequestToDoctorOffice " +
        " WHEN :columnName  = 'dateOf2ndRequest' THEN i.dateOfSecondRequestToDoctorOffice ELSE i.id END " +
        " END ASC , i.id desc")
    Page<ArChargeProdReject> findByQueryAll(
            @Param("query") String query,
        //    @Param("status") String status,
            @Param("status") List<String> status,
            @Param("workFlow") Integer workFlow,
            @Param("isNewRejection") boolean isNewRejection,
            @Param("isFirstRequestRecord") boolean isFirstRequestRecord,
            @Param("isFirstRequestDueRecord") boolean isFirstRequestDueRecord,
            @Param("isSecondRequestDueRecord") boolean isSecondRequestDueRecord,
            @Param("isSecondRequestRecord") boolean isSecondRequestRecord,
            @Param("doctorId") List<Integer> doctorId,
            @Param("companyId") Integer companyId,
            @Param("groupId") Integer groupId,
            @Param("reasonToReject") String reasonToReject,
            @Param("createdTo") Date createdTo,
            @Param("createdBy") Integer createdBy,
            @Param("createdFrom") Date createdFrom,
       //     @Param("dummyCpt")boolean dummyCpt,
             @Param("dummyCpt") String dummyCpt,
            @Param("columnName") String columnName,
            @Param("sortDirection") String sortDirection,
            Pageable pageable);

//    @RestResource(path = "customqueryall", rel = "customqueryall")
//    @Query("SELECT i FROM ArChargeProdReject i where  " +
//            "(:query is null or CONCAT(i.patientName,i.account , i.chargeBatchProcessing.id) LIKE %:query% ) " +
//
//            "AND (COALESCE(:doctorId) is null or (i.chargeBatchProcessing.doctor.id IN (:doctorId))) " +
//            " AND (:groupId is null or i.chargeBatchProcessing.group.id=:groupId) " +
//            " AND (:companyId is null or i.chargeBatchProcessing.company.id=:companyId) " +
//            "AND (:createdTo is null or DATE(i.createdOn) <= :createdTo) AND (:createdFrom is null or DATE(i.createdOn) >= :createdFrom)  " +
//
//             " AND (:isNewRejection is false or (i.dateOfFirstRequestToDoctorOffice is null and ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) <= 5) or (DATEDIFF(i.dateOfFirstRequestToDoctorOffice, CURRENT_DATE ) ) >= 0 )" +
////            "AND (:isNewRejection is false or ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) <= 5 )" +
//              "AND (:isFirstRequestDueRecord is false or ((DATEDIFF(CURRENT_DATE, i.dateOfFirstRequestToDoctorOffice ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.dateOfFirstRequestToDoctorOffice)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.dateOfFirstRequestToDoctorOffice) >= 6  THEN 1 ELSE 0 end )) >= 0) " +
//            " AND (:isFirstRequestDueRecord is false  or ((DATEDIFF(CURRENT_DATE, i.dateOfFirstRequestToDoctorOffice ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.dateOfFirstRequestToDoctorOffice)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.dateOfFirstRequestToDoctorOffice) >= 6  THEN 1 ELSE 0 end )) <= 5 ) " +
//
//            " AND (:isFirstRequestRecord is false or  ((DATEDIFF(CURRENT_DATE, i.dateOfFirstRequestToDoctorOffice ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.dateOfFirstRequestToDoctorOffice)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.dateOfFirstRequestToDoctorOffice) >= 6  THEN 1 ELSE 0 end )) > 5 ) " +
//            "AND (:isFirstRequestRecord is false Or  ((DATEDIFF(CURRENT_DATE, i.dateOfFirstRequestToDoctorOffice ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.dateOfFirstRequestToDoctorOffice)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.dateOfFirstRequestToDoctorOffice) >= 6  THEN 1 ELSE 0 end )) <= 10 ) " +
////            "AND (:isFirstRequestRecord is false Or (i.dateOfSecondRequestToDoctorOffice is null and ((DATEDIFF(CURRENT_DATE, i.dateOfFirstRequestToDoctorOffice ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.dateOfFirstRequestToDoctorOffice)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.dateOfFirstRequestToDoctorOffice) >= 6  THEN 1 ELSE 0 end )) <= 10 ) or (DATEDIFF(i.dateOfSecondRequestToDoctorOffice, CURRENT_DATE ) ) > 0) " +
//
//            " AND (:isSecondRequestDueRecord is false or ((DATEDIFF(CURRENT_DATE, i.dateOfSecondRequestToDoctorOffice ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.dateOfSecondRequestToDoctorOffice)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.dateOfSecondRequestToDoctorOffice) >= 6  THEN 1 ELSE 0 end )) >= 0 ) " +
//            " AND (:isSecondRequestDueRecord is false or ((DATEDIFF(CURRENT_DATE, i.dateOfSecondRequestToDoctorOffice ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.dateOfSecondRequestToDoctorOffice)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.dateOfSecondRequestToDoctorOffice) >= 6  THEN 1 ELSE 0 end )) <= 5 ) " +
//
//            " AND (:isSecondRequestRecord is false or ((DATEDIFF(CURRENT_DATE, i.dateOfSecondRequestToDoctorOffice ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.dateOfSecondRequestToDoctorOffice)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.dateOfSecondRequestToDoctorOffice) >= 6  THEN 1 ELSE 0 end )) > 5 )  " +
// //           " AND (:isSecondRequestRecord is false  or ((DATEDIFF(CURRENT_DATE, i.dateOfSecondRequestToDoctorOffice ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.dateOfSecondRequestToDoctorOffice)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.dateOfSecondRequestToDoctorOffice) >= 6  THEN 1 ELSE 0 end )) <= 10 ) " +
//
//      //      "AND  (:status is null or i.status= :status) " +
//            "AND (COALESCE(:status) is null or (i.status IN (:status))) " +
//            " AND (:reasonToReject is null or i.reasonToReject= :reasonToReject) " +
//            "AND (:workFlow is null or i.workFlow= :workFlow)  " +
//            " AND (:createdBy is null or i.createdBy.id = :createdBy)" +
//            "AND (:dummyCpt is null or i.dummyCpt= :dummyCpt) " +
//         //   "AND (COALESCE(:dummyCpt) is null or i.dummyCpt= :dummyCpt) " +
//           //+ "AND (COALESCE(:doctor) IS NULL or (i.doctor.id IN (:doctor))) "
//            " ORDER BY CASE WHEN :sortDirection = 'desc' " +
//            " THEN CASE " +
//            " WHEN :columnName = 'status' THEN i.status "+
//          //  " WHEN :columnName = 'doctorName' THEN i.chargeBatchProcessing.doctor.name "+
//            " WHEN :columnName = 'doctorName' THEN i.chargeBatchProcessing.doctor.id "+
//            " WHEN :columnName  = 'location' THEN i.location.name " +
//            " WHEN :columnName  = 'workFlow' THEN i.workFlow " +
//            " WHEN :columnName  = 'ticketNumber' THEN i.chargeBatchProcessing.id " +
//            " WHEN :columnName = 'reasonToReject' THEN i.reasonToReject "+
//            " WHEN :columnName  = 'patientName' THEN i.patientName " +
//            " WHEN :columnName  = 'dob' THEN i.dob " +
//            " WHEN :columnName = 'sequence' THEN i.sequence "+
//            " WHEN :columnName  = 'account' THEN i.account " +
//            " WHEN :columnName  = 'dos' THEN i.dateOfService " +
//            " WHEN :columnName  = 'insuranceType' THEN i.insuranceType " +
//            " WHEN :columnName  = 'insuranceType' THEN i.patientName " +
//         //   " WHEN :columnName  = 'batchRecieved' THEN i.createdOn " +
//            " WHEN :columnName = 'createdBy' THEN i.createdBy.firstName "+
//            " WHEN :columnName  = 'createdOn' THEN i.createdOn " +
//            " WHEN :columnName  = 'completedDate' THEN i.completedOn " +
//            " WHEN :columnName  = 'modifiedBy' THEN i.modifiedBy.id " +
//            " WHEN :columnName  = 'modifiedOn' THEN i.modifiedOn " +
//            " WHEN :columnName  = 'dateOf1stRequest' THEN i.dateOfFirstRequestToDoctorOffice " +
//            " WHEN :columnName  = 'dateOf2ndRequest' THEN i.dateOfSecondRequestToDoctorOffice ELSE i.id END " +
//            " END DESC, " +
//            " CASE WHEN :sortDirection = 'asc' " +
//            " THEN CASE " +
//            " WHEN :columnName = 'status' THEN i.status "+
//       //     " WHEN :columnName = 'doctorName' THEN i.chargeBatchProcessing.doctor.name "+
//            " WHEN :columnName = 'doctorName' THEN i.chargeBatchProcessing.doctor.id "+
//            " WHEN :columnName  = 'location' THEN i.location.name " +
//            " WHEN :columnName  = 'workFlow' THEN i.workFlow " +
//            " WHEN :columnName  = 'ticketNumber' THEN i.chargeBatchProcessing.id " +
//            " WHEN :columnName = 'reasonToReject' THEN i.reasonToReject "+
//            " WHEN :columnName  = 'patientName' THEN i.patientName " +
//            " WHEN :columnName  = 'dob' THEN i.dob " +
//            " WHEN :columnName = 'sequence' THEN i.sequence "+
//            " WHEN :columnName  = 'account' THEN i.account " +
//            " WHEN :columnName  = 'dos' THEN i.dateOfService " +
//            " WHEN :columnName  = 'insuranceType' THEN i.insuranceType " +
//            " WHEN :columnName  = 'patientName' THEN i.patientName " +
//            //   " WHEN :columnName  = 'batchRecieved' THEN i.createdOn " +
//            " WHEN :columnName = 'createdBy' THEN i.createdBy.firstName "+
//            " WHEN :columnName  = 'createdOn' THEN i.createdOn " +
//            " WHEN :columnName  = 'completedDate' THEN i.completedOn " +
//            " WHEN :columnName  = 'modifiedBy' THEN i.modifiedBy.id " +
//            " WHEN :columnName  = 'modifiedOn' THEN i.modifiedOn " +
//            " WHEN :columnName  = 'dateOf1stRequest' THEN i.dateOfFirstRequestToDoctorOffice " +
//            " WHEN :columnName  = 'dateOf2ndRequest' THEN i.dateOfSecondRequestToDoctorOffice ELSE i.id END " +
//            " END ASC , i.id desc")
//    Page<ArChargeProdReject> findByQueryAll(
//            @Param("query") String query,
//        //    @Param("status") String status,
//            @Param("status") List<String> status,
//            @Param("workFlow") Integer workFlow,
//            @Param("isNewRejection") boolean isNewRejection,
//            @Param("isFirstRequestRecord") boolean isFirstRequestRecord,
//            @Param("isFirstRequestDueRecord") boolean isFirstRequestDueRecord,
//            @Param("isSecondRequestDueRecord") boolean isSecondRequestDueRecord,
//            @Param("isSecondRequestRecord") boolean isSecondRequestRecord,
//            @Param("doctorId") List<Integer> doctorId,
//            @Param("companyId") Integer companyId,
//            @Param("groupId") Integer groupId,
//            @Param("reasonToReject") String reasonToReject,
//            @Param("createdTo") Date createdTo,
//            @Param("createdBy") Integer createdBy,
//            @Param("createdFrom") Date createdFrom,
//       //     @Param("dummyCpt")boolean dummyCpt,
//             @Param("dummyCpt") String dummyCpt,
//            @Param("columnName") String columnName,
//            @Param("sortDirection") String sortDirection,
//            Pageable pageable);
    @Query("SELECT count(id)  FROM ArChargeProdReject")

    public long countById();

//    @Query("SELECT count(i)  FROM ArChargeProdReject i WHERE i.status ='Pending' and
//    ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) -
//    (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6
//    THEN 1 ELSE 0 end )) <= 5")
//    @Query("SELECT count(i) FROM ArChargeProdReject i\n" +
//            "WHERE i.status ='Pending' and (\n" +
//            "    (i.dateOfFirstRequestToDoctorOffice is null and \n" +
//            "        ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) - WEEK(i.createdOn)) * 2) - \n" +
//            "        (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 THEN 1 ELSE 0 END ) - \n" +
//            "        (CASE WHEN WEEKDAY(i.createdOn) >= 6 THEN 1 ELSE 0 END )) <= 5) \n" +
//            "    OR \n" +
//            "    (DATEDIFF(i.dateOfFirstRequestToDoctorOffice, CURRENT_DATE ) > 0)\n" +
//            )
    @Query("SELECT count(i)  FROM ArChargeProdReject i WHERE i.status ='Pending' " +
            "and (i.dateOfFirstRequestToDoctorOffice IS NULL " +
            "and ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) - " +
            " WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) -" +
            " (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) < 5) or " +
            "(DATEDIFF(i.dateOfFirstRequestToDoctorOffice, CURRENT_DATE ) ) >= 0 ")
        public long countByNewRejection();



//  @Query("SELECT count(i)  FROM ArChargeProdReject i WHERE i.status ='Pending' and ((DATEDIFF(CURRENT_DATE, i.dateOfFirstRequestToDoctorOffice ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.dateOfFirstRequestToDoctorOffice)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.dateOfFirstRequestToDoctorOffice) >= 6  THEN 1 ELSE 0 end )) >= 0 and ((DATEDIFF(CURRENT_DATE, i.dateOfFirstRequestToDoctorOffice ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.dateOfFirstRequestToDoctorOffice)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.dateOfFirstRequestToDoctorOffice) >= 6  THEN 1 ELSE 0 end )) <= 5")
   @Query("SELECT count(i)  FROM ArChargeProdReject i WHERE i.status ='Pending' " +
           "and (((DATEDIFF(CURRENT_DATE, i.dateOfFirstRequestToDoctorOffice ) + 1) - " +
           "((WEEK(CURRENT_DATE) -  WEEK(i.dateOfFirstRequestToDoctorOffice)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) -" +
           " (CASE WHEN WEEKDAY(i.dateOfFirstRequestToDoctorOffice) >= 6  THEN 1 ELSE 0 end )) >= 0 " +
           "or (i.dateOfFirstRequestToDoctorOffice is  null and ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) -" +
           " ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - " +
           "(CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) >= 5)) " +
           " and (((DATEDIFF(CURRENT_DATE, i.dateOfFirstRequestToDoctorOffice ) + 1) - " +
           "((WEEK(CURRENT_DATE) -  WEEK(i.dateOfFirstRequestToDoctorOffice)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - " +
           "(CASE WHEN WEEKDAY(i.dateOfFirstRequestToDoctorOffice) >= 6  THEN 1 ELSE 0 end )) < 5" +
           " or (i.dateOfFirstRequestToDoctorOffice is  null and ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - " +
           "((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) -" +
           " (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) < 10))")
        public long countByFirstRequestDueRecord();
//


    //   @Query("SELECT count(i)  FROM ArChargeProdReject i WHERE i.status ='Pending' and ((DATEDIFF(CURRENT_DATE, i.dateOfFirstRequestToDoctorOffice ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.dateOfFirstRequestToDoctorOffice)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.dateOfFirstRequestToDoctorOffice) >= 6  THEN 1 ELSE 0 end )) > 5 and ((DATEDIFF(CURRENT_DATE, i.dateOfFirstRequestToDoctorOffice ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.dateOfFirstRequestToDoctorOffice)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.dateOfFirstRequestToDoctorOffice) >= 6  THEN 1 ELSE 0 end )) <= 10")
    @Query("SELECT count(i)  FROM ArChargeProdReject i WHERE i.status ='Pending' and (((DATEDIFF(CURRENT_DATE, i.dateOfFirstRequestToDoctorOffice ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.dateOfFirstRequestToDoctorOffice)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.dateOfFirstRequestToDoctorOffice) >= 6  THEN 1 ELSE 0 end )) >= 5 or (i.dateOfFirstRequestToDoctorOffice is  null and ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) >= 10)) and (((DATEDIFF(CURRENT_DATE, i.dateOfFirstRequestToDoctorOffice ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.dateOfFirstRequestToDoctorOffice)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.dateOfFirstRequestToDoctorOffice) >= 6  THEN 1 ELSE 0 end )) < 10 or (i.dateOfFirstRequestToDoctorOffice is  null and ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) < 15))")
    public long countByFirstRequestRecord();



//    @Query("SELECT count(i)  FROM ArChargeProdReject i WHERE i.status ='Pending' and  ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) > 15 and ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) <= 20")
 //    @Query("SELECT count(i)  FROM ArChargeProdReject i WHERE i.status ='Pending' and  ((DATEDIFF(CURRENT_DATE, i.dateOfSecondRequestToDoctorOffice ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.dateOfSecondRequestToDoctorOffice)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.dateOfSecondRequestToDoctorOffice) >= 6  THEN 1 ELSE 0 end )) >= 0 and ((DATEDIFF(CURRENT_DATE, i.dateOfSecondRequestToDoctorOffice ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.dateOfSecondRequestToDoctorOffice)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.dateOfSecondRequestToDoctorOffice) >= 6  THEN 1 ELSE 0 end )) <= 5")
    @Query("SELECT count(i)  FROM ArChargeProdReject i WHERE i.status ='Pending' and (((DATEDIFF(CURRENT_DATE, i.dateOfSecondRequestToDoctorOffice ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.dateOfSecondRequestToDoctorOffice)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.dateOfSecondRequestToDoctorOffice) >= 6  THEN 1 ELSE 0 end )) >= 0 or (i.dateOfSecondRequestToDoctorOffice is  null and ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) >= 15)) and (((DATEDIFF(CURRENT_DATE, i.dateOfSecondRequestToDoctorOffice ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.dateOfSecondRequestToDoctorOffice)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.dateOfSecondRequestToDoctorOffice) >= 6  THEN 1 ELSE 0 end )) < 5 or (i.dateOfSecondRequestToDoctorOffice is  null and ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) < 20))")
    public long countBySecondRequestDueRecord();



    //  @Query("SELECT count(i)  FROM ArChargeProdReject i WHERE i.status ='Pending' and  ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) > 20 ")
    @Query("SELECT count(i)  FROM ArChargeProdReject i WHERE i.status ='Pending' and ((DATEDIFF(CURRENT_DATE, i.dateOfSecondRequestToDoctorOffice ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.dateOfSecondRequestToDoctorOffice)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.dateOfSecondRequestToDoctorOffice) >= 6  THEN 1 ELSE 0 end )) >= 5 or (i.dateOfSecondRequestToDoctorOffice is  null and ((DATEDIFF(CURRENT_DATE, i.createdOn ) + 1) - ((WEEK(CURRENT_DATE) -  WEEK(i.createdOn)) * 2) - (CASE WHEN WEEKDAY(CURRENT_DATE) >= 5 then 1 else 0 end ) - (CASE WHEN WEEKDAY(i.createdOn) >= 6  THEN 1 ELSE 0 end )) >= 20) ")
    public long countBySecondRequestRecord();




    @Query("SELECT count(i)  FROM ArChargeProdReject i WHERE i.status ='Resolved' ")
        public long countByResolvedRejections();

//    @Query("SELECT count(i)  FROM ArChargeProdReject i  WHERE i.status ='Resolved' AND i.dummyCpt = true ")
//        public long countByResolvedRejectionsWithDummyCPT();
//    @Query("SELECT count(i)  FROM ArChargeProdReject i  WHERE i.status ='Resolved' AND i.dummyCpt = 1 ")
//    public long countByResolvedRejectionsWithDummyCPT();
      @Query("SELECT count(i)  FROM ArChargeProdReject i  WHERE i.dummyCpt = 1 ")
      public long countByResolvedRejectionsWithDummyCPT();

@Query("SELECT count(i)  FROM ArChargeProdReject i WHERE i.status ='Completed'")
        public long countByCompletedRejections();






//    @Query("SELECT i  FROM ArChargeProdReject i where i.charge_batch_id in (:ticketNumber)")
//    public List<ArChargeProdReject> findByTicketNumber(List<String> ticketNumber);
    //@RestResource(path = "allreasontoreject", rel = "allreasontoreject")
//	@Query("SELECT i FROM ChargeProdReject")
//	public List<ChargeProdReject> reasonToReject();

//	@Query("SELECT count(i.id) FROM ChargeProdReject i where i.followUpDate= :followUpDate ")
//	long getCompletedRejections (@Param("followUpDate") Date followUpDate);
//
//charge_batch_id

    /*
    update akpmsdbdev.charge_productivity_reject set remarks = 'updqated remarks' , remarks2 = "updatedremark2"
, date_of_first_request_to_doctor_office = "2014-01-16 02:40:04"
, date_of_second_request_to_doctor_office = "2014-01-16 02:40:04" where charge_batch_id IN ('1006185','1006185','1006091');
     */

    // @RestResource(path = "bulkUpdateChargeProductivityRejectByTicket", rel = "bulkUpdateChargeProductivityRejectByTicket")
//    @Query(value = "update charge_productivity_reject set " +
//            "remarks2 = :resolution , status = :status, resloved_by_id = :resolvedBy, " +
//            "date_of_first_request_to_doctor_office = CASE WHEN  :dateOfFirstRequestToDoctorOffice <> '' THEN :dateOfFirstRequestToDoctorOffice ELSE date_of_first_request_to_doctor_office END , " +
//            "date_of_second_request_to_doctor_office = CASE WHEN  :dateOfSecondRequestToDoctorOffice <> '' THEN :dateOfSecondRequestToDoctorOffice ELSE date_of_second_request_to_doctor_office END , " +
//            "modified_by = CASE WHEN  :modifiedBy <> '' THEN :modifiedBy ELSE modified_by END ," +
//            " remarks = CASE WHEN :remarks <> '' THEN :remarks ELSE remarks END , " +
//            "modified_on = CASE WHEN  :modifiedOn <> '' THEN :modifiedOn ELSE modified_on END , " +
//            " dummy_cpt  = CASE WHEN :dummyCPT <> '' THEN :dummyCPT ELSE dummy_cpt END " +
//            " where charge_batch_id IN (:ticketNumber)", nativeQuery = true)
    @Transactional
    @Modifying
    //@Query(value = "update charge_productivity_reject set remarks = :remarks , " +
    @Query(value = "update charge_productivity_reject set " +
            "remarks2 = :resolution , status = :status, " +
            "date_of_first_request_to_doctor_office =  CASE WHEN :dateOfFirstRequestToDoctorOffice <> '' THEN :dateOfFirstRequestToDoctorOffice ELSE null END, " +
            "date_of_second_request_to_doctor_office = CASE WHEN :dateOfSecondRequestToDoctorOffice <> '' THEN :dateOfSecondRequestToDoctorOffice ELSE null END, " +
            "modified_by = CASE WHEN  :modifiedBy <> '' THEN :modifiedBy ELSE modified_by END , " +
            "resloved_by_id = CASE WHEN  :resolvedBy <> '' THEN :resolvedBy ELSE resloved_by_id END , " +
            "resolved_on = CASE WHEN :resolvedOn <> '' THEN :resolvedOn ELSE resolved_on END, " +
            "completed_on = CASE WHEN :completedOn <> '' THEN :completedOn ELSE completed_on END, " +
            "completed_by_id = CASE WHEN  :completedById <> '' THEN :completedById ELSE completed_by_id END ," +
            " remarks = CASE WHEN :remarks <> '' THEN :remarks ELSE remarks END , " +
            "modified_on = CASE WHEN  :modifiedOn <> '' THEN :modifiedOn ELSE modified_on END , " +
            " dummy_cpt  = CASE WHEN :dummyCPT <> '' THEN :dummyCPT ELSE dummy_cpt END " +
            " where id IN (:chargeProRejectIds)", nativeQuery = true)
    public Integer bulkUpdateByTicketNumber(List<String> chargeProRejectIds,
                                            String remarks,
                                            String resolution,
                                            String status,
                                            String resolvedBy,
                                            String modifiedBy,
                                            String modifiedOn,
                                            String dateOfFirstRequestToDoctorOffice,
                                            String dateOfSecondRequestToDoctorOffice,
                                            String dummyCPT,
                                            String completedOn,
                                            String completedById,
                                            String resolvedOn
    );
//   //@Query(value = "update charge_productivity_reject set remarks = :remarks , " +
//    @Query(value = "update charge_productivity_reject set " +
//            "remarks2 = :resolution , status = :status, resloved_by_id = :resolvedBy , " +
//            "date_of_first_request_to_doctor_office =  CASE WHEN :dateOfFirstRequestToDoctorOffice <> '' THEN :dateOfFirstRequestToDoctorOffice ELSE null END, " +
//            "date_of_second_request_to_doctor_office = CASE WHEN :dateOfSecondRequestToDoctorOffice <> '' THEN :dateOfSecondRequestToDoctorOffice ELSE null END, " +
//            "modified_by = CASE WHEN  :modifiedBy <> '' THEN :modifiedBy ELSE modified_by END , " +
//            "resolved_on = CASE WHEN :resolvedOn <> '' THEN :resolvedOn ELSE resolved_on END, " +
//            "completed_on = CASE WHEN :completedOn <> '' THEN :completedOn ELSE completed_on END, " +
//            "completed_by_id = CASE WHEN  :completedById <> '' THEN :completedById ELSE completed_by_id END ," +
//            " remarks = CASE WHEN :remarks <> '' THEN :remarks ELSE remarks END , " +
//            "modified_on = CASE WHEN  :modifiedOn <> '' THEN :modifiedOn ELSE modified_on END , " +
//            " dummy_cpt  = CASE WHEN :dummyCPT <> '' THEN :dummyCPT ELSE dummy_cpt END " +
//            " where id IN (:chargeProRejectIds)", nativeQuery = true)
//    public Integer bulkUpdateByTicketNumber(List<String> chargeProRejectIds,
//                                            String remarks,
//                                            String resolution,
//                                            String status,
//                                            String resolvedBy,
//                                            String modifiedBy,
//                                            String modifiedOn,
//                                            String dateOfFirstRequestToDoctorOffice,
//                                            String dateOfSecondRequestToDoctorOffice,
//                                            String dummyCPT,
//                                            String completedOn,
//                                            String completedById,
//                                            String resolvedOn
//    );
    //    @Query(value = "select count(*) from charge_productivity_reject c join  charge_productivity   cp " +
//            "where cp.ticket_number = c.charge_batch_id and c.reason_to_reject = :reasonToReject and " +
//            " cp.ticket_number = :ticketNumber and c.patient_name = :patientName and c.dob = :dob " , nativeQuery = true)
// "modified_by = CASE WHEN  :modifiedBy <> '' THEN :modifiedBy ELSE modified_by END ," +
//    @Query(value = "select count(*) from charge_productivity_reject c  " +
//            "where (:reasonToReject is null or c.reason_to_reject = :reasonToReject) and " +
//            " (:dateOfService is null or c.date_of_service = :dateOfService ) and " +
//            " c.charge_batch_id = :ticketNumber and c.patient_name = :patientName and (:dob is null or c.dob = :dob) ", nativeQuery = true)
//    Integer findTicketNumberByPNameAndDOB(String ticketNumber,
//                                          String reasonToReject,
//                                          String patientName,
//                                          String dob,
//                                          String dateOfService);

    @Query(value = "select count(*) from charge_productivity_reject c  " +
            "where" +
           // " (:reasonToReject is null or c.reason_to_reject = :reasonToReject) and " +
            " (:dateOfService is null or c.date_of_service = :dateOfService ) and " +
            " c.charge_batch_id = :ticketNumber and c.patient_name = :patientName and (:dob is null or c.dob = :dob) ", nativeQuery = true)
    Integer findTicketNumberByPNameAndDOB(String ticketNumber,
                                         // String reasonToReject,
                                          String patientName,
                                          String dob,
                                          String dateOfService);
}
