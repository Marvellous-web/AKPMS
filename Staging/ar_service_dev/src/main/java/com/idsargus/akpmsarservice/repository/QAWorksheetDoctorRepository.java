package com.idsargus.akpmsarservice.repository;

import com.idsargus.akpmsarservice.model.domain.QAWorksheetDoctor;
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

@RepositoryRestResource(path = QAWorksheetDoctorRepository.MODULE_NAME, collectionResourceRel = QAWorksheetDoctorRepository.MODULE_NAME)
public interface QAWorksheetDoctorRepository extends JpaRepository<QAWorksheetDoctor,Integer> {
    public static final String MODULE_NAME = "qaWorksheetDoctor";

    <S extends QAWorksheetDoctor> S save(S qaWorksheetDoctor);

    @RestResource(path = "QaWorksheetDoctorByAddMore", rel = "QaWorksheetDoctorByAddMore")
    @Query(value= "select * from qa_worksheet_doctor i where (:doctorId is null or i.doctor_id = :doctorId)" +
            " and (:qaWorkSheetId is null or i.qaworksheet_id = :qaWorkSheetId)",
            nativeQuery = true)
    public Page<QAWorksheetDoctor> getQaWorkSheetDoctorAddMore(@Param("doctorId") String doctorId, @Param("qaWorkSheetId") String qaWorkSheetId,
                                                        Pageable pageable);

    @Modifying
    @Transactional
    @RestResource(path = "deleteQaWorksheetdoctor", rel = "deleteQaWorksheetdoctor")
    @Query(value = "DELETE FROM qa_worksheet_doctor  WHERE (:qaWorkSheetDoctorId is null or id = :qaWorkSheetDoctorId)" , nativeQuery = true)
    public Integer deleteById(@Param("qaWorkSheetDoctorId") String qaWorkSheetDoctorId);
}
