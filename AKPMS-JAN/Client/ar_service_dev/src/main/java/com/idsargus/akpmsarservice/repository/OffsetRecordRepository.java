package com.idsargus.akpmsarservice.repository;

import com.idsargus.akpmsarservice.model.domain.OffsetRecordEntity;
import com.idsargus.akpmsarservice.model.domain.RekeyRequestRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = OffsetRecordRepository.MODULE_NAME, collectionResourceRel = OffsetRecordRepository.MODULE_NAME)
    public interface OffsetRecordRepository extends PagingAndSortingRepository<OffsetRecordEntity, Integer> {


public static final String MODULE_NAME = "offsetRecordEntity";

    @Override
//	@CachePut
    <S extends OffsetRecordEntity> S save(S offsetRecordEntity);



    @RestResource(path = "fetchCptById", rel = "fetchCptById")
    @Query(value = "SELECT *  FROM offset_record i where i.offset_id = :offsetId", nativeQuery = true)
    public List<OffsetRecordEntity> getOffsetById(@Param("offsetId")  String offsetId);

}
