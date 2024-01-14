package com.idsargus.akpmsadminservice.repository;

import java.util.List;

import com.idsargus.akpmsadminservice.entity.AdminSourceEntity;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;


@RepositoryRestResource(path = ArSourceDataRestRepository.MODULE_NAME, collectionResourceRel = ArSourceDataRestRepository.MODULE_NAME)
@CacheConfig(cacheNames = ArSourceDataRestRepository.MODULE_NAME)
public interface ArSourceDataRestRepository extends CrudRepository<AdminSourceEntity, String> {

	public static final String MODULE_NAME = "arsources";

	@Override
	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "all", rel = "all")
	@Query("SELECT i FROM AdminSourceEntity i ORDER BY i.name")
	@Cacheable
	public List<AdminSourceEntity> findAll();

	@Override
	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@CachePut
	<S extends AdminSourceEntity> S save(S entity);

	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "name", rel = "name")
	@Query("SELECT i FROM AdminSourceEntity i where i.name=:name")
	@Cacheable(key = "#name")
	public AdminSourceEntity findByName(@Param("name") String name);

	@Override
	@RestResource(exported = false)
	void deleteById(String id);

	@Override
	@RestResource(exported = false)
	void delete(AdminSourceEntity entity);

	@Override
	@RestResource(exported = false)
	void deleteAll();

	@Override
	@RestResource(exported = false)
	void deleteAll(Iterable<? extends AdminSourceEntity> entities);
}
