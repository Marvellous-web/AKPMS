package com.idsargus.akpmsadminservice.repository;

import java.util.List;

import com.idsargus.akpmsadminservice.entity.AdminStatusCodeEntity;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;


@RepositoryRestResource(path = ArStatusCodeDataRestRepository.MODULE_NAME, collectionResourceRel = ArStatusCodeDataRestRepository.MODULE_NAME)
@CacheConfig(cacheNames = ArStatusCodeDataRestRepository.MODULE_NAME)
public interface ArStatusCodeDataRestRepository extends CrudRepository<AdminStatusCodeEntity, String> {

	public static final String MODULE_NAME = "arstatuscodes";

	@Override
	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "all", rel = "all")
	@Query("SELECT i FROM AdminStatusCodeEntity i ORDER BY i.name")
	@Cacheable
	public List<AdminStatusCodeEntity> findAll();

	@Override
	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@CachePut
	<S extends AdminStatusCodeEntity> S save(S statusCodeEntity);

	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "name", rel = "name")
	@Query("SELECT i FROM AdminStatusCodeEntity i where i.name=:name")
	@Cacheable(key = "#name")
	public AdminStatusCodeEntity findByName(@Param("name") String name);

	@Override
	@RestResource(exported = false)
	void deleteById(String id);

	@Override
	@RestResource(exported = false)
	void delete(AdminStatusCodeEntity entity);

	@Override
	@RestResource(exported = false)
	void deleteAll();

	@Override
	@RestResource(exported = false)
	void deleteAll(Iterable<? extends AdminStatusCodeEntity> entities);

}
