package com.idsargus.akpmsarservice.repository;


import com.idsargus.akpmscommonservice.entity.DoctorGroupEntity;
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

import java.util.List;


@RepositoryRestResource(path = DoctorGroupDataRestRepository.MODULE_NAME, collectionResourceRel = DoctorGroupDataRestRepository.MODULE_NAME)
@CacheConfig(cacheNames = DoctorGroupDataRestRepository.MODULE_NAME)
public interface DoctorGroupDataRestRepository extends PagingAndSortingRepository<DoctorGroupEntity, Integer> {

	public static final String MODULE_NAME = "doctorgroups";
	
	//1.
	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "all", rel = "all")
//	@Query("SELECT i FROM DoctorGroupEntity i  ORDER BY i.name")
	@Cacheable
	public Page<DoctorGroupEntity> findByNameContains(@Param("name") String name, Pageable pageable);
	

		//2.
	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "allEnabled", rel = "allEnabled")
//	@Query("SELECT i FROM DoctorGroupEntity i where  i.enabled =:enabled ORDER BY i.name")
	@Cacheable(key = "#enabled-only")
	public Page<DoctorGroupEntity> findByNameContainsAndEnabled(@Param("name") String name, @Param("enabled") Boolean enabled, Pageable pageable);


//	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
//	@RestResource(path = "customquery", rel = "customquery")
//	@Query("SELECT i FROM DoctorGroupEntity i where  CONCAT(i.name) LIKE %:query% AND (:enabled is null or i.enabled = :enabled)  ORDER BY i.name")
//	@Cacheable
//	public Page<AdminDoctorGroup> findByQuery(@Param("query") String query, @Param("enabled") Boolean enabled, Pageable pageable);
//


	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "customquery", rel = "customquery")
	@Query("SELECT i FROM DoctorGroupEntity i where  CONCAT(i.name) LIKE %:query% AND" +
			" (:enabled is null or i.enabled = :enabled)  ORDER BY CASE WHEN :sortDirection = 'desc' " +
			" THEN CASE " +
			" WHEN :columnName = 'name' THEN i.name "+
			" WHEN :columnName  = 'enabled' THEN i.enabled ELSE i.name END" +
			" END DESC, " +
			" CASE WHEN :sortDirection = 'asc' " +
			" THEN CASE " +
			" WHEN :columnName = 'name' THEN i.name "+
			" WHEN :columnName  = 'enabled' THEN i.enabled " +
			"ELSE i.name END " +
			" END ASC ")

	@Cacheable
	public Page<DoctorGroupEntity> findByQuery(@Param("query") String query,
											  @Param("columnName") String columnName	 ,
											  @Param("sortDirection") String sortDirection,
											  @Param("enabled") Boolean enabled, Pageable pageable);


	//all list
	
	//@PreAuthorize("hasAuthority('role_admin')")
	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "customqueryall", rel = "customquery")
	@Query("SELECT i FROM DoctorGroupEntity i where  CONCAT(i.name) LIKE %:query% AND (:enabled is null or i.enabled = :enabled)  ORDER BY i.name")
	@Cacheable
	public List<DoctorGroupEntity> findByQueryAll(@Param("query") String query, @Param("enabled") Boolean enabled, Pageable pageable);
	
	//@PreAuthorize("hasAnyAuthority('role_admin')")
	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "allGroups", rel = "allGroups")
	@Cacheable(key = "#allGroups")
	public List<DoctorGroupEntity> findByEnabledIsTrue();

	@Override
	//@PreAuthorize("hasAuthority('role_admin')")
	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@CachePut
	<S extends DoctorGroupEntity> S save(S DoctorGroup);
	
	//@PreAuthorize("hasAuthority('role_admin')")
	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "exists", rel = "exists")
	@Query("SELECT i FROM DoctorGroupEntity i where i.name=:name")
	@Cacheable(key = "#name")
	public DoctorGroupEntity findByName(@Param("name") String name);


	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "companyGroup", rel = "companyGroup")
	@Query(value="select * from doctor_group i where i.doctor_company_id = :companyId",nativeQuery = true)
		public List<DoctorGroupEntity> findByCompany(@Param("companyId") Integer companyId);

	@Query(value="SELECT count(i) FROM DoctorGroupEntity i")
	public long countAllDoctorGroup();

//	@Override
//	@PreAuthorize("hasAuthority('role_admin')")
//	void delete(DoctorGroupEntity entity);

	/* Disabled */
	@Override
	@RestResource(exported = false)
	void delete(DoctorGroupEntity entity);

	

	@Override
	@RestResource(exported = false)
	void deleteById(Integer id);

	@Override
	@RestResource(exported = false)
	void deleteAll();

	@Override
	@RestResource(exported = false)
	void deleteAll(Iterable<? extends DoctorGroupEntity> entities);

}