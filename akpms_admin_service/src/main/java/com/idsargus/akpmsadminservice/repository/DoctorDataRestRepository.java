package com.idsargus.akpmsadminservice.repository;

import java.util.List;

import javax.websocket.server.PathParam;

import com.idsargus.akpmsadminservice.entity.AdminDoctorEntity;
import com.idsargus.akpmsadminservice.entity.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;



@RepositoryRestResource(path = DoctorDataRestRepository.MODULE_NAME, collectionResourceRel = DoctorDataRestRepository.MODULE_NAME)
@CacheConfig(cacheNames = DoctorDataRestRepository.MODULE_NAME)
public interface DoctorDataRestRepository extends PagingAndSortingRepository<AdminDoctorEntity, Integer> {

	public static final String MODULE_NAME = "doctors";

	//	@PreAuthorize("hasAuthority('role_admin')")
	@RestResource(path = "all", rel = "all")
//	@Query("SELECT i FROM AdminDoctorEntity i where i.deleted = 0 ORDER BY i.name")
	@Cacheable
	public Page<AdminDoctorEntity> findByNameContains(@Param("name") String name, Pageable pageable);

	@RestResource(path = "allDoctors", rel = "allDoctors")
	@Cacheable
	public List<AdminDoctorEntity> findByEnabledIsTrue();

	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "allEnabled", rel = "allEnabled")
	@Query("SELECT i FROM AdminDoctorEntity i where i.deleted = 0 AND i.enabled= 1 ORDER BY i.name")
	@Cacheable(key = "#enabled")
	public List<AdminDoctorEntity> findByAllEnabled();

//	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
//	@RestResource(path = "customquery", rel = "customquery")
//	@Query("SELECT i FROM AdminDoctorEntity i where  CONCAT(i.name) LIKE %:query% AND (:enabled is null or i.enabled = :enabled) AND" +
//			" (:deleted is null or i.deleted = :deleted)  ORDER BY i.name")
//	@Cacheable
//	public Page<AdminDoctorEntity> findByQuery(@Param("query") String query, @Param("enabled") Boolean enabled,
//										  @Param("deleted") Boolean deleted, Pageable pageable);

	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "customquery", rel = "customquery")
	@Query("SELECT i FROM AdminDoctorEntity i where  CONCAT(i.name) LIKE %:query% AND (:enabled is null or i.enabled = :enabled) AND" +
			" (:deleted is null or i.deleted = :deleted)  ORDER BY  CASE WHEN :sortDirection = 'desc' " +
			" THEN CASE " +
			" WHEN :columnName = 'name' THEN i.name "+
			" WHEN :columnName  = 'companyName' THEN i.company.name " +
			" WHEN :columnName  = 'groupName' THEN i.group.name " +
			" WHEN :columnName  = 'createdOn' THEN i.createdOn " +
			" WHEN :columnName  = 'modifiedOn' THEN i.modifiedOn ELSE i.name END " +
			" END DESC, " +
			" CASE WHEN :sortDirection = 'asc' " +
			" THEN CASE " +
			" WHEN :columnName = 'name' THEN i.name "+
			" WHEN :columnName  = 'companyName' THEN i.company.name  " +
			" WHEN :columnName  = 'groupName' THEN i.group.name " +
			" WHEN :columnName  = 'createdOn' THEN i.createdOn " +
			" WHEN :columnName  = 'modifiedOn' THEN i.modifiedOn ELSE i.name END " +
			" END ASC ")
	@Cacheable
	public Page<AdminDoctorEntity> findByQuery(
			@Param("columnName") String columnName,
			@Param("query") String query,
			@Param("enabled") Boolean enabled,
			@Param("sortDirection") String sortDirection,
			@Param("deleted") Boolean deleted,
			Pageable pageable);


	// all query list
	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "customqueryall", rel = "customqueryall")
	@Query("SELECT i FROM AdminDoctorEntity i where  CONCAT(i.name) LIKE %:query% AND (:enabled is null or i.enabled = :enabled) AND (:deleted is null or i.deleted = :deleted)  ORDER BY i.name")
	@Cacheable
	public List<AdminDoctorEntity> findByQueryAll(@Param("query") String query, @Param("enabled") Boolean enabled,
											 @Param("deleted") Boolean deleted);


	@RestResource(path = "findByCompanygroup", rel = "findByCompanygroup")
	@Query(value="SELECT * FROM doctor i where (:groupId is null or i.group_id = :groupId) AND (:companyId is null or i.company_id = :companyId)", nativeQuery=true)
	public List<AdminDoctorEntity> findByCompanyAndGroup(@Param("groupId") Integer groupId,
														 @Param("companyId") Integer companyId);

	@Override
//	@PreAuthorize("hasAuthority('role_admin')")
	@CachePut
	<S extends AdminDoctorEntity> S save(S doctorEntity);

	//	@PreAuthorize("hasAuthority('role_admin')")
	@RestResource(path = "parentonly", rel = "parentonly")
	@Query("SELECT i FROM AdminDoctorEntity i where i.deleted = 0 and parent = null ORDER BY i.name")
	@Cacheable(key = "#parent-only")
	public List<AdminDoctorEntity> findParentForAdmin();

	//	@PreAuthorize("hasAuthority('role_admin')")
	@RestResource(path = "name", rel = "name")
	@Query("SELECT i FROM AdminDoctorEntity i where i.deleted = 0 AND i.name=:name")
	@Cacheable(key = "#name")
	public AdminDoctorEntity findByName(@Param("name") String name);
	@Query(value="SELECT count(i) FROM DoctorEntity i where i.deleted = 0")
	public long countByIsDeleted();

	@Override
	@RestResource(exported = false)
	void deleteById(Integer id);

	@Override
	@RestResource(exported = false)
	void delete(AdminDoctorEntity entity);

	@Override
	@RestResource(exported = false)
	void deleteAll();

	@Override
	@RestResource(exported = false)
	void deleteAll(Iterable<? extends AdminDoctorEntity> entities);

//	@RestResource(path = "withStatus", rel = "withStatus")
//	@Query("SELECT i FROM DoctorEntity i where i.deleted = 0 AND i.enabled = :status")
//	public Page<DoctorEntity> findWithStatus(@Param("status") boolean status, Pageable p);
//
//	@RestResource(path = "enabledOnly", rel = "enabledOnly")
//	@Query("SELECT i FROM DoctorEntity i where i.deleted = 0 AND i.enabled = 1")
//	public Page<DoctorEntity> findForUser(Pageable p);
//
//	@RestResource(path = "enabledOnlyNameLike", rel = "enabledOnlyNameLike")
//	@Query("SELECT i FROM DoctorEntity i where i.deleted = 0 AND i.enabled = 1 AND i.name like %:name%")
//	public Page<DoctorEntity> findByNameLike(@Param("name") String name, Pageable p);

}
