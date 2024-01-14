package com.idsargus.akpmsarservice.repository;

import com.idsargus.akpmsarservice.model.domain.AdjustmentLogWorkFlow;
import com.idsargus.akpmsarservice.model.domain.ProcessManualDepartmentRelEntity;
import com.idsargus.akpmscommonservice.entity.PaymentType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = ProcessManualDepartmentRel.MODULE_NAME, collectionResourceRel = ProcessManualDepartmentRel.MODULE_NAME)
public interface ProcessManualDepartmentRel extends PagingAndSortingRepository<ProcessManualDepartmentRelEntity, Integer> {


    public static final String MODULE_NAME = "processManualDepartmentRel";

    @Override
//	@CachePut
    <S extends ProcessManualDepartmentRelEntity> S save(S processManualDepartmentRelEntity);


}
