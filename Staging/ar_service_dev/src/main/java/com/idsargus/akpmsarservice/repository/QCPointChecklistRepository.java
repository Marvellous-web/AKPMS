package com.idsargus.akpmsarservice.repository;

import com.idsargus.akpmsarservice.model.domain.QAWorksheet;
import com.idsargus.akpmsarservice.model.domain.QCPointChecklist;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = QCPointChecklistRepository.MODULE_NAME, collectionResourceRel = QCPointChecklistRepository.MODULE_NAME)
public interface QCPointChecklistRepository extends PagingAndSortingRepository<QCPointChecklist,Integer> {

    public static final String MODULE_NAME = "qcPointChecklist";
    <S extends QCPointChecklist> S save(S qcPointChecklist);
}