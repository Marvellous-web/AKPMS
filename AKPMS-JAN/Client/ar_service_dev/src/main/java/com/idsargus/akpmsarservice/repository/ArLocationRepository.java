//package com.idsargus.akpmsarservice.repository;
//
//import com.idsargus.akpmsarservice.model.domain.ArLocation;
//import com.idsargus.akpmscommonservice.entity.Location;
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.data.repository.PagingAndSortingRepository;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;
//
//
//@RepositoryRestResource(path = ArLocationRepository.MODULE_NAME, collectionResourceRel = ArLocationRepository.MODULE_NAME)
//@CacheConfig(cacheNames = ArLocationRepository.MODULE_NAME)
//public interface ArLocationRepository extends PagingAndSortingRepository<ArLocation, Long> {
//
//    public static final String MODULE_NAME = "location";
//
//}