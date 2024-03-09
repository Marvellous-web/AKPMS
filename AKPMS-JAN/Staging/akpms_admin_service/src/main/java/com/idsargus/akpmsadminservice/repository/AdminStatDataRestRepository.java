package com.idsargus.akpmsadminservice.repository;

import java.util.List;

import com.idsargus.akpmsadminservice.entity.ArAdminStatEntity;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;


@RepositoryRestResource(path = AdminStatDataRestRepository.MODULE_NAME, collectionResourceRel = AdminStatDataRestRepository.MODULE_NAME)
@CacheConfig(cacheNames = AdminSettingDataRestRepository.MODULE_NAME)
public interface AdminStatDataRestRepository extends CrudRepository<ArAdminStatEntity, String> {

	public static final String MODULE_NAME = "adminstats";

	@Override
	@PreAuthorize("hasAuthority('role_admin')")
	@RestResource(path = "all", rel = "all")
	@Query("SELECT i FROM ArAdminStatEntity i ORDER BY i.tableName")
	@Cacheable
	public List<ArAdminStatEntity> findAll();

	@Override
	@PreAuthorize("hasAuthority('role_admin')")
	@CachePut
	<S extends ArAdminStatEntity> S save(S AdminStatEntity);

	@Override
	@RestResource(exported = false)
	void deleteById(String id);

	@Override
	@RestResource(exported = false)
	void delete(ArAdminStatEntity entity);

	@Override
	@RestResource(exported = false)
	void deleteAll();

	@Override
	@RestResource(exported = false)
	void deleteAll(Iterable<? extends ArAdminStatEntity> entities);
}
