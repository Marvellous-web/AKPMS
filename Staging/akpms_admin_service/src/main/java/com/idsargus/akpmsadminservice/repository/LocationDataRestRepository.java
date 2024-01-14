package com.idsargus.akpmsadminservice.repository;

import java.util.List;

import com.idsargus.akpmsadminservice.entity.AdminLocation;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;


@RepositoryRestResource(path = LocationDataRestRepository.MODULE_NAME, collectionResourceRel = LocationDataRestRepository.MODULE_NAME)
@CacheConfig(cacheNames = LocationDataRestRepository.MODULE_NAME)
public interface LocationDataRestRepository extends CrudRepository<AdminLocation, Integer> {

	public static final String MODULE_NAME = "locations";

	@Override
	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "all", rel = "all")
	@Query("SELECT i FROM AdminLocation i WHERE i.deleted = 0 ORDER BY i.name")
	@Cacheable
	public List<AdminLocation> findAll();

	@Override
	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@CachePut
	<S extends AdminLocation> S save(S locationEntity);

	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "name", rel = "name")
	@Query("SELECT i FROM LocationEntity i WHERE i.deleted = 0 AND i.name=:name")
	@Cacheable(key = "#name")
	public AdminLocation findByNameForAdmin(@Param("name") String name);

	@Override
	@RestResource(exported = false)
	void deleteById(Integer id);

	@Override
	@RestResource(exported = false)
	void delete(AdminLocation entity);

	@Override
	@RestResource(exported = false)
	void deleteAll();

	@Override
	@RestResource(exported = false)
	void deleteAll(Iterable<? extends AdminLocation> entities);

//	@RestResource(path = "withStatus", rel = "status")
//	@Query("SELECT i FROM LocationEntity i where i.deleted = 0 AND i.enabled = :status")
//	public Page<LocationEntity> findWithStatus(@Param("status") boolean status, Pageable p);
//
//	@RestResource(path = "enabledOnly", rel = "enabledOnly")
//	@Query("SELECT i FROM LocationEntity i where i.deleted = 0 AND i.enabled = 1")
//	public Page<LocationEntity> findForUser(Pageable p);
//
//	@RestResource(path = "enabledOnlyNameLike", rel = "enabledOnlyNameLike")
//	@Query("SELECT i FROM LocationEntity i where i.deleted = 0 AND i.enabled = 1 AND i.name like %:name%")
//	public Page<LocationEntity> findByNameLike(@Param("name") String name, Pageable p);

}
