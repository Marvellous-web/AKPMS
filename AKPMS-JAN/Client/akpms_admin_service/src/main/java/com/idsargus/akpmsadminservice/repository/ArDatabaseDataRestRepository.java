package com.idsargus.akpmsadminservice.repository;

import java.util.List;

import com.idsargus.akpmsadminservice.entity.AdminDatabaseEntity;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;


@RepositoryRestResource(path = ArDatabaseDataRestRepository.MODULE_NAME, collectionResourceRel = ArDatabaseDataRestRepository.MODULE_NAME)
@CacheConfig(cacheNames = AdminSettingDataRestRepository.MODULE_NAME)
public interface ArDatabaseDataRestRepository extends CrudRepository<AdminDatabaseEntity, Integer> {

	public static final String MODULE_NAME = "ardatabases";

	@Override
	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "all", rel = "all")
	@Query("SELECT i FROM AdminDatabaseEntity i WHERE i.deleted = 0 ORDER By i.name")
	@Cacheable(key = "#all")
	public List<AdminDatabaseEntity> findAll();

	@Override
	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@CachePut
	<S extends AdminDatabaseEntity> S save(S arDatabaseEntity);

	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "name", rel = "name")
	@Query("SELECT i FROM AdminDatabaseEntity i WHERE i.deleted = 0 AND i.name=:name")
	@Cacheable(key = "#name")
	public AdminDatabaseEntity findByNameForAdmin(@Param("name") String name);

	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "allEnabled", rel = "allEnabled")
	@Query("SELECT i FROM AdminDatabaseEntity i where i.deleted = 0 AND i.enabled = 1 ORDER BY i.name")
	@Cacheable(key = "#enabled-only")
	public List<AdminDatabaseEntity> findAllEnabled();

	@Override
	@RestResource(exported = false)
	void deleteById(Integer id);

	@Override
	@RestResource(exported = false)
	void delete(AdminDatabaseEntity entity);

	@Override
	@RestResource(exported = false)
	void deleteAll();

	@Override
	@RestResource(exported = false)
	void deleteAll(Iterable<? extends AdminDatabaseEntity> entities);

//	@RestResource(path = "withStatus", rel = "withStatus")
//	@Query("SELECT i FROM ArDatabaseEntity i where i.deleted = 0 AND i.enabled = :status")
//	public Page<ArDatabaseEntity> findWithStatus(@Param("status") boolean status, Pageable p);
//
//	@RestResource(path = "enabledOnly", rel = "enabledOnly")
//	@Query("SELECT i FROM ArDatabaseEntity i where i.deleted = 0 AND i.enabled = 1")
//	public Page<ArDatabaseEntity> findForUser(Pageable p);
//
//	@RestResource(path = "enabledOnlyNameLike", rel = "enabledOnlyNameLike")
//	@Query("SELECT i FROM ArDatabaseEntity i where i.deleted = 0 AND i.enabled = 1 AND i.name like %:name%")
//	public Page<ArDatabaseEntity> findByNameLike(@Param("name") String name, Pageable p);

}
