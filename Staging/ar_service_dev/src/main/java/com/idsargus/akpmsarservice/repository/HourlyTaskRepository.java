package com.idsargus.akpmsarservice.repository;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.idsargus.akpmscommonservice.entity.HourlyTaskEntity;

@RepositoryRestResource(path = HourlyTaskRepository.MODULE_NAME, collectionResourceRel = HourlyTaskRepository.MODULE_NAME)
@CacheConfig(cacheNames = HourlyTaskRepository.MODULE_NAME)
public interface HourlyTaskRepository extends CrudRepository<HourlyTaskEntity, Integer>{
	
	public static final String MODULE_NAME = "hourly_tasks";

	//@Query(value = "SELECT count(i) FROM HourlyTaskEntity i") --- use it if error comes in hourly Task arService
	//@Query(value = "SELECT count(i) FROM HourlyTaskEntity i WHERE i.deleted = 0")
	@Query(value = "SELECT count(i) FROM HourlyTaskEntity i")
	public long countByDeleted();

}
