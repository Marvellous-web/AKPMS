package com.idsargus.akpmsarservice.repository;

import com.idsargus.akpmsarservice.model.domain.QCPointChecklist;
import com.idsargus.akpmsarservice.model.domain.QcPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = QcPointRepository.MODULE_NAME, collectionResourceRel = QcPointRepository.MODULE_NAME)
public interface QcPointRepository  extends JpaRepository<QcPoint,Integer> {
    public static final String MODULE_NAME = "qcPoint";

    <S extends QcPoint> S save(S qcPoint);
}