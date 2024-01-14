package com.idsargus.akpmsadminservice.repository;

import java.util.List;

import com.idsargus.akpmsadminservice.entity.AdminQcPointEntity;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;


@RepositoryRestResource(path = QcPointDataRestRepository.MODULE_NAME, collectionResourceRel = QcPointDataRestRepository.MODULE_NAME)
@CacheConfig(cacheNames = QcPointDataRestRepository.MODULE_NAME)
public interface QcPointDataRestRepository extends CrudRepository<AdminQcPointEntity, Integer> {

	public static final String MODULE_NAME = "qcpoints";

	@Override
	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "all", rel = "all")
	@Query("SELECT i FROM AdminQcPointEntity i where i.deleted = 0 ORDER BY i.name")
	@Cacheable
	public List<AdminQcPointEntity> findAll();

	@Override
	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@CachePut
	<S extends AdminQcPointEntity> S save(S qcPointEntity);

	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "parentonly", rel = "parentonly")
	@Query("SELECT i FROM AdminQcPointEntity i where i.deleted = 0 and parent = null ORDER BY i.name")
	@Cacheable(key = "#parent-only")
	public List<AdminQcPointEntity> findParentForAdmin();

	@PreAuthorize("hasAuthority('role_admin') or hasAuthority('role_user')")
	@RestResource(path = "withStatusAndParentId", rel = "withStatusAndParentId")
	@Query("SELECT d FROM AdminQcPointEntity d where d.deleted = 0 AND d.enabled = :status AND d.parent.id = :parentId ORDER BY d.name")
	@Cacheable(key = "#parent-id")
	public List<AdminQcPointEntity> findWithStatusAndParentId(@Param("status") boolean status,
															  @Param("parentId") Integer parentId);

	@Override
	@RestResource(exported = false)
	void deleteById(Integer id);

	@Override
	@RestResource(exported = false)
	void delete(AdminQcPointEntity entity);

	@Override
	@RestResource(exported = false)
	void deleteAll();

	@Override
	@RestResource(exported = false)
	void deleteAll(Iterable<? extends AdminQcPointEntity> entities);

//	@RestResource(path = "withStatus", rel = "withStatus")
//	@Query("SELECT i FROM AdminQcPointEntity i where i.deleted = 0 AND i.enabled = :status")
//	public Page<AdminQcPointEntity> findWithStatus(@Param("status") boolean status, Pageable p);
//
//	@RestResource(path = "enabledOnly", rel = "enabledOnly")
//	@Query("SELECT i FROM AdminQcPointEntity i where i.deleted = 0 AND i.enabled = 1")
//	public Page<AdminQcPointEntity> findForUser(Pageable p);
//
//	@RestResource(path = "enabledOnlyNameLike", rel = "enabledOnlyNameLike")
//	@Query("SELECT i FROM AdminQcPointEntity i where i.deleted = 0 AND i.enabled = 1 AND i.name like %:name%")
//	public Page<AdminQcPointEntity> findByNameLike(@Param("name") String name, Pageable p);
//
//	@RestResource(path = "name", rel = "name")
//	@Query("SELECT i FROM AdminQcPointEntity i where i.deleted = 0 AND i.name=:name")
//	public AdminQcPointEntity findByName(@Param("name") String name);

	// TODO search by parent id
	// TODO search by department
	@PreAuthorize("hasAuthority('role_admin') or hasAuthority('role_user')")
	@RestResource(path = "withdepartmentId", rel = "withdepartmentId")
	@Query("SELECT q FROM AdminQcPointEntity q where q.deleted = 0  AND q.department.id = :departmentId ORDER BY q.name")
	@Cacheable(key = "#department-id")
	public List<AdminQcPointEntity> findWithDepartment(@Param("departmentId") Integer departmentId);

	// TODO search by department and sub department
	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "withDepartmentAndSubdepartment", rel = "withDepartmentAndSubdepartment")
	@Query("SELECT p FROM AdminQcPointEntity p where CONCAT(p.name,p.description) LIKE %:query% AND p.deleted = 0 AND (:departmentId is null or p.department.id = :departmentId) AND (:subDepartmentId is null or p.subDepartment.id = :subDepartmentId) ORDER BY p.name")
	@Cacheable(key = "#parent-id")
	public List<AdminQcPointEntity> findwithDepartmentAndSubdepartment(@Param("query") String query , @Param("departmentId") Integer departmentId,
																	   @Param("subDepartmentId") Integer subDepartmentId);


}
