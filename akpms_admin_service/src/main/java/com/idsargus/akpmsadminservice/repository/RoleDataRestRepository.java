package com.idsargus.akpmsadminservice.repository;

import java.util.List;

import com.idsargus.akpmsadminservice.entity.AdminRoleEntity;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;


@RepositoryRestResource(path = RoleDataRestRepository.MODULE_NAME, collectionResourceRel = RoleDataRestRepository.MODULE_NAME)
@CacheConfig(cacheNames = RoleDataRestRepository.MODULE_NAME)
public interface RoleDataRestRepository extends CrudRepository<AdminRoleEntity, Integer> {

	public static final String MODULE_NAME = "roles";

	@Override
	@PreAuthorize("hasAuthority('role_admin') Or hasAuthority('role_user')")
	@RestResource(path = "all", rel = "all")
	@Query("SELECT i FROM AdminRoleEntity i")
	@Cacheable
	public List<AdminRoleEntity> findAll();

	@Override
	@RestResource(exported = false)
	<S extends AdminRoleEntity> S save(S entity);

	@Override
	@RestResource(exported = false)
	void deleteById(Integer id);

	@Override
	@RestResource(exported = false)
	void delete(AdminRoleEntity entity);

	@Override
	@RestResource(exported = false)
	void deleteAll();

	@Override
	@RestResource(exported = false)
	void deleteAll(Iterable<? extends AdminRoleEntity> entities);

}
