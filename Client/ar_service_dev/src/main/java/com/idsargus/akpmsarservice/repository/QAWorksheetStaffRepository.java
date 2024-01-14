package com.idsargus.akpmsarservice.repository;

import com.idsargus.akpmsarservice.model.domain.QAWorksheetStaff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

@RepositoryRestResource(path = QAWorksheetStaffRepository.MODULE_NAME, collectionResourceRel = QAWorksheetStaffRepository.MODULE_NAME)
public interface QAWorksheetStaffRepository extends JpaRepository<QAWorksheetStaff, Integer> {
    public static final String MODULE_NAME = "qaWorksheetStaff";

    <S extends QAWorksheetStaff> S save(S qaWorksheetStaff);

    @RestResource(path = "QaWorksheetByAddMore", rel = "QaWorksheetByAddMore")
    @Query(value= "select * from qa_worksheet_staff i where (:qaWorkSheetId is null or i.qaworksheet_id = :qaWorkSheetId) " +
            "and (:userId is null or i.user_id= :userId)",
            nativeQuery = true)
    public Page<QAWorksheetStaff> getQaWorkSheetAddMore(@Param("userId") String userId, @Param("qaWorkSheetId") String qaWorkSheetId,
                                                        Pageable pageable);

    @Modifying
    @Transactional
    @RestResource(path = "deleteStaff", rel = "deleteStaff")
    @Query(value = "DELETE FROM qa_worksheet_staff  WHERE (:qaWorkSheetStaffId is null or id = :qaWorkSheetStaffId)" , nativeQuery = true)
    public Integer deleteStaff(@Param("qaWorkSheetStaffId") String qaWorkSheetStaffId);
}
