package com.idsargus.akpmsadminservice.repository;



import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import com.idsargus.akpmsadminservice.entity.ChargeBatchType;
import com.idsargus.akpmscommonservice.entity.HourlyTaskName;


@RepositoryRestResource(path = BatchTypeDataRestRepository.MODULE_NAME, collectionResourceRel = BatchTypeDataRestRepository.MODULE_NAME)
@CacheConfig(cacheNames = BatchTypeDataRestRepository.MODULE_NAME)
public interface BatchTypeDataRestRepository extends CrudRepository<ChargeBatchType, Integer>{

    public static final String MODULE_NAME = "batchtype";


    @Override
    @PreAuthorize("hasAnyAuthority('role_admin','role_user')")
    @RestResource(path = "all", rel = "all")
    @Query("SELECT i FROM ChargeBatchType i where i.deleted = 0 ORDER BY i.id")
    @Cacheable
    public List<ChargeBatchType> findAll();

//    @PreAuthorize("hasAnyAuthority('role_admin','role_user')")
//    @RestResource(path = "exists", rel = "exists")
//    @Query("SELECT i FROM DoctorCompanyEntity i where i.name=:name")
//    @Cacheable(key = "#name")
//    public ChargeBatchType findByName(@Param("name") String name);

    @Override
    @PreAuthorize("hasAuthority('role_admin')")
    @CachePut
    <S extends ChargeBatchType> S save(S chargeBatchType);


    @PreAuthorize("hasAuthority('role_admin')")
    @RestResource(path = "name", rel = "name")
    @Query("SELECT i FROM ChargeBatchType i WHERE i.deleted = 0 AND i.name=:name")
    @Cacheable(key = "#name")
    public ChargeBatchType findByName(@Param("name") String name);



    @Override
    @RestResource(exported = false)
    void deleteById(Integer id);
    // delete batch type
    @Override
    @RestResource(exported = false)
    void delete(ChargeBatchType entity);

    @Override
    @RestResource(exported = false)
    void deleteAll();

    @Override
    @RestResource(exported = false)
    void deleteAll(Iterable<? extends ChargeBatchType> entities);

}
