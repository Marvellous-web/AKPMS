package com.idsargus.akpmsarservice.repository;

import com.idsargus.akpmsarservice.model.domain.QAWorksheetPatientInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = QAWorksheetPatientInfoRepository.MODULE_NAME, collectionResourceRel = QAWorksheetPatientInfoRepository.MODULE_NAME)
public interface QAWorksheetPatientInfoRepository  extends JpaRepository<QAWorksheetPatientInfo,Integer> {
    public static final String MODULE_NAME = "qaWorksheetPatientInfo";

    <S extends QAWorksheetPatientInfo> S save(S qaWorksheetPatientInfo);
}
