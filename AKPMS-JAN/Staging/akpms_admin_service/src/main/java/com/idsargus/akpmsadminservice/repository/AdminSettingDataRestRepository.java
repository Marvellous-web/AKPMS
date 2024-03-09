package com.idsargus.akpmsadminservice.repository;

import java.util.Optional;

import com.idsargus.akpmsadminservice.entity.AdminAdminSettingEntity;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;


@RepositoryRestResource(path = AdminSettingDataRestRepository.MODULE_NAME, collectionResourceRel = AdminSettingDataRestRepository.MODULE_NAME)
@CacheConfig(cacheNames = AdminSettingDataRestRepository.MODULE_NAME)
public interface AdminSettingDataRestRepository extends CrudRepository<AdminAdminSettingEntity, Integer> {

	public static final String MODULE_NAME = "adminsettings";

	@Override
	@PreAuthorize("hasAuthority('role_admin')")
	@Cacheable
	Iterable<AdminAdminSettingEntity> findAll();

	@Override
	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@Cacheable
	@Query("SELECT i FROM AdminAdminSettingEntity i where i.id=:id")
	public Optional<AdminAdminSettingEntity> findById(@Param("id") Integer id);

	@Override
	@PreAuthorize("hasAuthority('role_admin')")
	@CachePut
	<S extends AdminAdminSettingEntity> S save(S adminSettingEntity);

	@Override
	@RestResource(exported = false)
	void deleteById(Integer id);

	@Override
	@RestResource(exported = false)
	void delete(AdminAdminSettingEntity entity);

	@Override
	@RestResource(exported = false)
	void deleteAll();

	@Override
	@RestResource(exported = false)
	void deleteAll(Iterable<? extends AdminAdminSettingEntity> entities);
}
