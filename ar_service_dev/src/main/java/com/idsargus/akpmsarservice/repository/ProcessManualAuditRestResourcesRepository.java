package com.idsargus.akpmsarservice.repository;

import com.idsargus.akpmscommonservice.entity.ChargeProductivity;
import com.idsargus.akpmscommonservice.entity.ProcessManualAudit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;


@RepositoryRestResource(path = ProcessManualAuditRestResourcesRepository.MODULE_NAME, collectionResourceRel = ProcessManualAuditRestResourcesRepository.MODULE_NAME)
public interface ProcessManualAuditRestResourcesRepository extends PagingAndSortingRepository<ProcessManualAudit, Integer> {


    public static final String MODULE_NAME = "processManualAuditRestResources";

    @Override
    <S extends ProcessManualAudit> S save(S processManualAudit);

    @RestResource(path = "notification", rel = "notification")
    @Query(value = "Select i from ProcessManualAudit i where i.notification = true")
    public Page<ProcessManualAudit> getNotifcationDetails(Pageable pageable);


}