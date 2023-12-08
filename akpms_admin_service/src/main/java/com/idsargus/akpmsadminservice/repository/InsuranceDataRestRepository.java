package com.idsargus.akpmsadminservice.repository;

import java.util.List;
import java.util.Optional;

import com.idsargus.akpmsadminservice.entity.AdminDoctorEntity;
import com.idsargus.akpmsadminservice.entity.AdminInsuranceEntity;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;


@RepositoryRestResource(path = InsuranceDataRestRepository.MODULE_NAME, collectionResourceRel = InsuranceDataRestRepository.MODULE_NAME)
@CacheConfig(cacheNames = InsuranceDataRestRepository.MODULE_NAME)
public interface InsuranceDataRestRepository extends CrudRepository<AdminInsuranceEntity, Integer> {

	public static final String MODULE_NAME = "insurances";

	@Override
//	@PreAuthorize("hasAuthority('role_admin')")
	@RestResource(path = "all", rel = "all")
	@Query("SELECT i FROM AdminInsuranceEntity i where i.deleted = 0 ORDER BY i.name")
	@Cacheable
	public List<AdminInsuranceEntity> findAll();

//	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
//	@RestResource(path = "customquery", rel = "customquery")
//	@Query("SELECT i FROM AdminInsuranceEntity i where  CONCAT(i.name) LIKE %:query% AND  i.deleted = 0  ORDER BY  CASE WHEN :sortDirection = 'desc' " +
//			" THEN CASE " +
//			" WHEN :columnName = 'name' THEN i.name "+
//		//	" WHEN :columnName  = 'status' THEN i.status " +
//			" ELSE i.name END " +
//			" END DESC, " +
//			" CASE WHEN :sortDirection = 'asc' " +
//			" THEN CASE " +
//			" WHEN :columnName = 'name' THEN i.name "+
//		//	" WHEN :columnName  = 'status' THEN i.status  " +
//			" ELSE i.name END " +
//			" END ASC ")
//	@Cacheable
//	public Page<AdminDoctorEntity> findByQuery(
//			@Param("columnName") String columnName,
//			@Param("sortDirection") String sortDirection,
//			Pageable pageable);

	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "allEnabled", rel = "allEnabled")
	@Query("SELECT i FROM AdminInsuranceEntity i where i.deleted = 0 AND i.enabled = 1 ORDER BY i.name")
	@Cacheable(key = "#enabled-only")
	public List<AdminInsuranceEntity> findAllEnabled();

	@Override
//	@PreAuthorize("hasAuthority('role_admin')")
	@CachePut
	<S extends AdminInsuranceEntity> S save(S insuranceEntity);

//	@PreAuthorize("hasAuthority('role_admin')")
	@RestResource(path = "name", rel = "name")
	@Query("SELECT i FROM AdminInsuranceEntity i where i.deleted = 0 AND i.name=:name")
	@Cacheable(key = "#name")
	public AdminInsuranceEntity findByNameForAdmin(@Param("name") String name);

	@Override
	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@Query("SELECT i FROM AdminInsuranceEntity i where i.deleted = 0 AND i.id=:id")
	@Cacheable(key = "#id")
	public Optional<AdminInsuranceEntity> findById(@Param("id") Integer id);

	@Query(value = "SELECT count(i) FROM AdminInsuranceEntity i where i.deleted = 0 ")
	public long countByIsDeleted();
	@Override
	@RestResource(exported = false)
	void deleteById(Integer id);

	@Override
	@RestResource(exported = false)
	void delete(AdminInsuranceEntity entity);

	@Override
	@RestResource(exported = false)
	void deleteAll();

	@Override
	@RestResource(exported = false)
	void deleteAll(Iterable<? extends AdminInsuranceEntity> entities);

//	@RestResource(path = "withStatus", rel = "status")
//	@Query("SELECT i FROM AdminInsuranceEntity i where i.deleted = 0 AND i.enabled = :status")
//	public Page<AdminInsuranceEntity> findWithStatus(@Param("status") boolean status, Pageable p);
//
//	@RestResource(path = "enabledOnly", rel = "enabledOnly")
//	@Query("SELECT i FROM AdminInsuranceEntity i where i.deleted = 0 AND i.enabled = 1")
//	public Page<AdminInsuranceEntity> findForUser(Pageable p);
//
//	@RestResource(path = "enabledOnlyNameLike", rel = "enabledOnlyNameLike")
//	@Query("SELECT i FROM AdminInsuranceEntity i where i.deleted = 0 AND i.enabled = 1 AND i.name like %:name%")
//	public Page<AdminInsuranceEntity> findByNameLike(@Param("name") String name, Pageable p);

}
