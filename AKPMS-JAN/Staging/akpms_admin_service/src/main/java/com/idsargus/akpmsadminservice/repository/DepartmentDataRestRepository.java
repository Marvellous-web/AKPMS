package com.idsargus.akpmsadminservice.repository;

import java.util.List;

import com.idsargus.akpmsadminservice.entity.AdminDepartmentEntity;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;


@RepositoryRestResource(path = DepartmentDataRestRepository.MODULE_NAME, collectionResourceRel = DepartmentDataRestRepository.MODULE_NAME)
@CacheConfig(cacheNames = DepartmentDataRestRepository.MODULE_NAME)
public interface DepartmentDataRestRepository extends CrudRepository<AdminDepartmentEntity, Integer> {

	public static final String MODULE_NAME = "departments";

	@Override
	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "all", rel = "all")
	@Query("SELECT i FROM AdminDepartmentEntity i WHERE i.deleted = 0  ORDER BY i.name")
	@Cacheable
	public List<AdminDepartmentEntity> findAll();

	@Override
	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@CachePut
	<S extends AdminDepartmentEntity> S save(S departmentEntity);

	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "parentonly", rel = "parentonly")
	@Query("SELECT i FROM AdminDepartmentEntity i WHERE i.deleted = 0 AND parent = null ORDER BY i.name")
	@Cacheable(key = "#parent-only")
	public List<AdminDepartmentEntity> findParentForAdmin();

	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "subdeptonly", rel = "subdeptonly")
	@Query("SELECT i FROM AdminDepartmentEntity i WHERE i.deleted = 0 AND parent != null ORDER BY i.name")
	@Cacheable(key = "#all-sub-department-only")
	public List<AdminDepartmentEntity> findSubDepartment();

	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "subdeptbydept", rel = "subdeptbydept")
	@Query("SELECT i FROM AdminDepartmentEntity i where i.deleted = 0 and i.parent.id = :deptId")
	@Cacheable(key = "#sub-department-only-by-deptid")
	public List<AdminDepartmentEntity> findSubDepartmentByDeptId(@Param("deptId") Integer deptId);

	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "name", rel = "name")
	@Query("SELECT i FROM AdminDepartmentEntity i where i.deleted = 0 AND i.name=:name")
	@Cacheable(key = "#name")
	public AdminDepartmentEntity findByNameForAdmin(@Param("name") String name);

	@Query(value="SELECT count(i) FROM AdminDepartmentEntity i where i.deleted = 0")
	public long countByIsDeleted();
	@Override
	@RestResource(exported = false)
	void deleteById(Integer id);

	@Override
	@RestResource(exported = false)
	void delete(AdminDepartmentEntity entity);

	@Override
	@RestResource(exported = false)
	void deleteAll();

	@Override
	@RestResource(exported = false)
	void deleteAll(Iterable<? extends AdminDepartmentEntity> entities);

//	@RestResource(path = "withStatus", rel = "withStatus")
//	@Query("SELECT i FROM AdminDepartmentEntity i where i.deleted = 0 AND i.enabled = :status")
//	public Page<AdminDepartmentEntity> findWithStatus(@Param("status") boolean status, Pageable p);
//
//	@RestResource(path = "enabledOnly", rel = "enabledOnly")
//	@Query("SELECT i FROM AdminDepartmentEntity i where i.deleted = 0 AND i.enabled = 1")
//	public Page<AdminDepartmentEntity> findForUser(Pageable p);
//
//	@RestResource(path = "enabledOnlyNameLike", rel = "enabledOnlyNameLike")
//	@Query("SELECT i FROM AdminDepartmentEntity i where i.deleted = 0 AND i.enabled = 1 AND i.name like %:name%")
//	public Page<AdminDepartmentEntity> findByNameLike(@Param("name") String name, Pageable p);

	/*****************************
	 * CUSTOM API
	 *****************************/

//	@Query("SELECT d FROM AdminDepartmentEntity d where d.id in :ids AND d.deleted = 0 AND d.enabled = 1")
//	List<AdminDepartmentEntity> findByIds(List<Integer> ids);

	/*****************************
	 * WITHOUT API
	 *****************************/

//	@RestResource(path = "withStatusAndParentId", rel = "withStatusAndParentId")
//	@Query("SELECT d FROM AdminDepartmentEntity d where d.deleted = 0 AND d.enabled = :status AND d.parent.id = :parentId")
//	public List<AdminDepartmentEntity> findWithStatusAndParentId(@Param("status") boolean status,
//			@Param("parentId") Integer parentId);

}
