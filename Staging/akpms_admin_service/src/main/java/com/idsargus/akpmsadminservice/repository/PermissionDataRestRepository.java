package com.idsargus.akpmsadminservice.repository;

import java.util.List;

import com.idsargus.akpmsadminservice.entity.AdminPermissionEntity;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;


@RepositoryRestResource(path = PermissionDataRestRepository.MODULE_NAME, collectionResourceRel = PermissionDataRestRepository.MODULE_NAME)
@CacheConfig(cacheNames = PermissionDataRestRepository.MODULE_NAME)
public interface PermissionDataRestRepository extends CrudRepository<AdminPermissionEntity, String> {

	public static final String MODULE_NAME = "permissions";

	@Override
	@PreAuthorize("hasAuthority('role_admin') or hasAuthority('role_user')")
	@RestResource(path = "all", rel = "all")
	@Query("SELECT i FROM AdminPermissionEntity i ORDER BY i.id")
	@Cacheable
	public List<AdminPermissionEntity> findAll();

	@Override
	@PreAuthorize("hasAuthority('role_admin')")
	@CachePut
	<S extends AdminPermissionEntity> S save(S permissionEntity);

	@Override
	@RestResource(exported = false)
	void deleteById(String id);

	@Override
	@RestResource(exported = false)
	void delete(AdminPermissionEntity entity);

	@Override
	@RestResource(exported = false)
	void deleteAll();

	@Override
	@RestResource(exported = false)
	void deleteAll(Iterable<? extends AdminPermissionEntity> entities);
}
