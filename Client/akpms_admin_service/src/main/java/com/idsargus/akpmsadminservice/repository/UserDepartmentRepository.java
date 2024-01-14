//package com.idsargus.akpmsadminservice.repository;
//
//import com.idsargus.akpmsadminservice.entity.UserDepartment;
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;
//
//
//
//@RepositoryRestResource(path = UserDepartmentRepository.MODULE_NAME, collectionResourceRel
//        = UserDepartmentRepository.MODULE_NAME)
//@CacheConfig(cacheNames = UserDepartmentRepository.MODULE_NAME)
//public interface UserDepartmentRepository extends CrudRepository<UserDepartment, Integer> {
//    public static final String MODULE_NAME = "userdepartment";
//}