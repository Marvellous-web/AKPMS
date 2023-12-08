package com.idsargus.akpmsarservice.repository;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import com.idsargus.akpmscommonservice.entity.DoctorEntity;
import com.idsargus.akpmscommonservice.entity.UserEntity;

@RepositoryRestResource(path = DoctorDataRestRepository.MODULE_NAME, collectionResourceRel = DoctorDataRestRepository.MODULE_NAME)
@CacheConfig(cacheNames = DoctorDataRestRepository.MODULE_NAME)
public interface DoctorDataRestRepository extends PagingAndSortingRepository<DoctorEntity, Integer> {

	public static final String MODULE_NAME = "doctors";

//	@PreAuthorize("hasAuthority('role_admin')")
	@RestResource(path = "all", rel = "all")
//	@Query("SELECT i FROM DoctorEntity i where i.deleted = 0 ORDER BY i.name")
	@Cacheable
	public Page<DoctorEntity> findByNameContains(@Param("name") String name, Pageable pageable);

	@RestResource(path = "allDoctors", rel = "allDoctors")
	@Cacheable
	public List<DoctorEntity> findByEnabledIsTrue();

	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "allEnabled", rel = "allEnabled")
	@Query("SELECT i FROM DoctorEntity i where i.deleted = 0 AND i.enabled= 1 ORDER BY i.name")
	@Cacheable(key = "#enabled")
	public List<DoctorEntity> findByAllEnabled();

	@PreAuthorize("hasAuthority('role_admin')")
	@RestResource(path = "customquery", rel = "customquery")
	@Query("SELECT i FROM DoctorEntity i where  CONCAT(i.name) LIKE %:query% AND (:enabled is null or i.enabled = :enabled) AND (:deleted is null or i.deleted = :deleted)  ORDER BY i.name")
	@Cacheable
	public Page<DoctorEntity> findByQuery(@Param("query") String query, @Param("enabled") Boolean enabled,
			@Param("deleted") Boolean deleted, Pageable pageable);

	// all query list
	@PreAuthorize("hasAuthority('role_admin')")
	@RestResource(path = "customqueryall", rel = "customqueryall")
	@Query("SELECT i FROM DoctorEntity i where  CONCAT(i.name) LIKE %:query% AND (:enabled is null or i.enabled = :enabled) AND (:deleted is null or i.deleted = :deleted)  ORDER BY i.name")
	@Cacheable
	public List<DoctorEntity> findByQueryAll(@Param("query") String query, @Param("enabled") Boolean enabled,
			@Param("deleted") Boolean deleted);

	@Override 
//	@PreAuthorize("hasAuthority('role_admin')")
	@CachePut
	<S extends DoctorEntity> S save(S doctorEntity);

//	@PreAuthorize("hasAuthority('role_admin')")
	@RestResource(path = "parentonly", rel = "parentonly")
	@Query("SELECT i FROM DoctorEntity i where i.deleted = 0 and parent = null ORDER BY i.name")
	@Cacheable(key = "#parent-only")
	public List<DoctorEntity> findParentForAdmin();

//	@PreAuthorize("hasAuthority('role_admin')")
	@RestResource(path = "name", rel = "name")
	@Query("SELECT i FROM DoctorEntity i where i.deleted = 0 AND i.name=:name")
	@Cacheable(key = "#name")
	public DoctorEntity findByName(@Param("name") String name);

	@Override
	@RestResource(exported = false)
	void deleteById(Integer id);

	@Override
	@RestResource(exported = false)
	void delete(DoctorEntity entity);

	@Override
	@RestResource(exported = false)
	void deleteAll();

	@Override
	@RestResource(exported = false)
	void deleteAll(Iterable<? extends DoctorEntity> entities);
	
	
	
	@Query("SELECT count(id)  FROM DoctorEntity")
	public long countById();
	
	
	@Query(value="SELECT count(i) FROM DoctorEntity i where i.deleted = 0")
	public long countByIsDeleted();
	
	
	
//	@Query("SELECT count(*) FROM DoctorEntity")
//	public Long getDoctorCount();

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
