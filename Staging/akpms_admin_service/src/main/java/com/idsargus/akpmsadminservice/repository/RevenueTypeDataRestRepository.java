package com.idsargus.akpmsadminservice.repository;

import java.util.List;

import com.idsargus.akpmsadminservice.entity.AdminRevenueTypeEntity;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;


@RepositoryRestResource(path = RevenueTypeDataRestRepository.MODULE_NAME, collectionResourceRel = RevenueTypeDataRestRepository.MODULE_NAME)
@CacheConfig(cacheNames = RevenueTypeDataRestRepository.MODULE_NAME)
public interface RevenueTypeDataRestRepository extends CrudRepository<AdminRevenueTypeEntity, Integer> {

	public static final String MODULE_NAME = "revenuetypes";

	@Override
	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "all", rel = "all")
	@Query("SELECT i FROM AdminRevenueTypeEntity i where i.deleted = 0 ORDER BY i.name")
	@Cacheable
	public List<AdminRevenueTypeEntity> findAll();

	@Override
	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@CachePut
	<S extends AdminRevenueTypeEntity> S save(S revenueTypeEntity);

	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "name", rel = "name")
	@Query("SELECT i FROM AdminRevenueTypeEntity i where i.deleted = 0 AND i.name=:name")
	@Cacheable(key = "#name")
	public AdminRevenueTypeEntity findByNameForAdmin(@Param("name") String name);

	@Override
	@RestResource(exported = false)
	void deleteById(Integer id);

	@Override
	@RestResource(exported = false)
	void delete(AdminRevenueTypeEntity entity);

	@Override
	@RestResource(exported = false)
	void deleteAll();

	@Override
	@RestResource(exported = false)
	void deleteAll(Iterable<? extends AdminRevenueTypeEntity> entities);

//	@RestResource(path = "withStatus", rel = "status")
//	@Query("SELECT i FROM AdminRevenueTypeEntity i where i.deleted = 0 AND i.enabled = :status")
//	public Page<AdminRevenueTypeEntity> findWithStatus(@Param("status") boolean status, Pageable p);
//
//	@RestResource(path = "enabledOnly", rel = "enabledOnly")
//	@Query("SELECT i FROM AdminRevenueTypeEntity i where i.deleted = 0 AND i.enabled = 1")
//	public Page<AdminRevenueTypeEntity> findForUser(Pageable p);
//
//	@RestResource(path = "enabledOnlyNameLike", rel = "enabledOnlyNameLike")
//	@Query("SELECT i FROM AdminRevenueTypeEntity i where i.deleted = 0 AND i.enabled = 1 AND i.name like %:name%")
//	public Page<AdminRevenueTypeEntity> findByNameLike(@Param("name") String name, Pageable p);

}
