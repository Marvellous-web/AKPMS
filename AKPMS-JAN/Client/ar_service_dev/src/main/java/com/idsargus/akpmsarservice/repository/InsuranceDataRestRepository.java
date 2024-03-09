package com.idsargus.akpmsarservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import com.idsargus.akpmscommonservice.entity.InsuranceEntity;

@RepositoryRestResource(path = InsuranceDataRestRepository.MODULE_NAME, collectionResourceRel = InsuranceDataRestRepository.MODULE_NAME)
@CacheConfig(cacheNames = InsuranceDataRestRepository.MODULE_NAME)
public interface InsuranceDataRestRepository extends CrudRepository<InsuranceEntity, Integer> {

	public static final String MODULE_NAME = "insurances";

	@Override
//	@PreAuthorize("hasAuthority('role_admin')")
	@RestResource(path = "all", rel = "all")
	@Query("SELECT i FROM InsuranceEntity i where i.deleted = 0 ORDER BY i.name")
	@Cacheable
	public List<InsuranceEntity> findAll();

	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "allEnabled", rel = "allEnabled")
	@Query("SELECT i FROM InsuranceEntity i where i.deleted = 0 AND i.enabled = 1 ORDER BY i.name")
	@Cacheable(key = "#enabled-only")
	public List<InsuranceEntity> findAllEnabled();

	@Override
//	@PreAuthorize("hasAuthority('role_admin')")
	@CachePut
	<S extends InsuranceEntity> S save(S insuranceEntity);

//	@PreAuthorize("hasAuthority('role_admin')")
	@RestResource(path = "name", rel = "name")
	@Query("SELECT i FROM InsuranceEntity i where i.deleted = 0 AND i.name=:name")
	@Cacheable(key = "#name")
	public InsuranceEntity findByNameForAdmin(@Param("name") String name);

	@Override
	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@Query("SELECT i FROM InsuranceEntity i where i.deleted = 0 AND i.id=:id")
	@Cacheable(key = "#id")
	public Optional<InsuranceEntity> findById(@Param("id") Integer id);

	@Override
	@RestResource(exported = false)
	void deleteById(Integer id);

	@Override
	@RestResource(exported = false)
	void delete(InsuranceEntity entity);

	@Override
	@RestResource(exported = false)
	void deleteAll();

	@Override
	@RestResource(exported = false)
	void deleteAll(Iterable<? extends InsuranceEntity> entities);

	//Arindam Code Change
		@Query("SELECT count(id) FROM InsuranceEntity")
		public long getInsuranceCount();
		
		@Query(value = "SELECT count(i) FROM InsuranceEntity i where i.deleted = 0 ")
		public long countByIsDeleted();
	
//	@RestResource(path = "withStatus", rel = "status")
//	@Query("SELECT i FROM InsuranceEntity i where i.deleted = 0 AND i.enabled = :status")
//	public Page<InsuranceEntity> findWithStatus(@Param("status") boolean status, Pageable p);
//
//	@RestResource(path = "enabledOnly", rel = "enabledOnly")
//	@Query("SELECT i FROM InsuranceEntity i where i.deleted = 0 AND i.enabled = 1")
//	public Page<InsuranceEntity> findForUser(Pageable p);
//
//	@RestResource(path = "enabledOnlyNameLike", rel = "enabledOnlyNameLike")
//	@Query("SELECT i FROM InsuranceEntity i where i.deleted = 0 AND i.enabled = 1 AND i.name like %:name%")
//	public Page<InsuranceEntity> findByNameLike(@Param("name") String name, Pageable p);

}
