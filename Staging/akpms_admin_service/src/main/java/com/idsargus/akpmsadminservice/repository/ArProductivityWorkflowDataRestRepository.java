package com.idsargus.akpmsadminservice.repository;

import java.util.List;

import com.idsargus.akpmsadminservice.entity.AdminProductivityWorkFlowEntity;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;


@RepositoryRestResource(path = ArProductivityWorkflowDataRestRepository.MODULE_NAME, collectionResourceRel = ArProductivityWorkflowDataRestRepository.MODULE_NAME)
@CacheConfig(cacheNames = ArProductivityWorkflowDataRestRepository.MODULE_NAME)
public interface ArProductivityWorkflowDataRestRepository
		extends CrudRepository<AdminProductivityWorkFlowEntity, Integer> {

	public static final String MODULE_NAME = "arproductivityworkflows";

	@Override
	@PreAuthorize("hasAnyAuthority('role_admin', 'role_user')")
	@RestResource(path = "all", rel = "all")
	@Query("SELECT i FROM AdminProductivityWorkFlowEntity i ORDER BY i.name")
	@Cacheable(key = "#all")
	public List<AdminProductivityWorkFlowEntity> findAll();

	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "allEnabled", rel = "allEnabled")
	@Query("SELECT i FROM AdminProductivityWorkFlowEntity i WHERE i.enabled = 1 ORDER BY i.name")
	@Cacheable(key = "#enabled-only")
	public List<AdminProductivityWorkFlowEntity> findAllEnabled();

	@Override
	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@CachePut
	<S extends AdminProductivityWorkFlowEntity> S save(S arProductivityWorkFlowEntity);

	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "name", rel = "name")
	@Query("SELECT i FROM AdminProductivityWorkFlowEntity i WHERE i.name=:name")
	@Cacheable(key = "#name")
	public AdminProductivityWorkFlowEntity findByNameForAdmin(@Param("name") String name);

	@Override
	@RestResource(exported = false)
	void deleteById(Integer id);

	@Override
	@RestResource(exported = false)
	void delete(AdminProductivityWorkFlowEntity entity);

	@Override
	@RestResource(exported = false)
	void deleteAll();

	@Override
	@RestResource(exported = false)
	void deleteAll(Iterable<? extends AdminProductivityWorkFlowEntity> entities);
}
