package com.idsargus.akpmsadminservice.repository;

import com.idsargus.akpmsadminservice.entity.Department;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
    @RepositoryRestResource(path = DepartmentCustomRepository.MODULE_NAME, collectionResourceRel = DepartmentCustomRepository.MODULE_NAME)
    @CacheConfig(cacheNames =DepartmentCustomRepository.MODULE_NAME)
    public interface DepartmentCustomRepository extends CrudRepository<Department, Integer> {

        public static final String MODULE_NAME = "departmenttest";

}