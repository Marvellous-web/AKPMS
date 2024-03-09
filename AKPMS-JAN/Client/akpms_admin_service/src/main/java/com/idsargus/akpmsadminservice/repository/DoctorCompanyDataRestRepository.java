package com.idsargus.akpmsadminservice.repository;

import java.util.List;

import com.idsargus.akpmsadminservice.entity.AdminDoctorCompanyEntity;
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


@RepositoryRestResource(path = DoctorCompanyDataRestRepository.MODULE_NAME, collectionResourceRel = DoctorCompanyDataRestRepository.MODULE_NAME)
@CacheConfig(cacheNames = DoctorCompanyDataRestRepository.MODULE_NAME)
public interface DoctorCompanyDataRestRepository extends PagingAndSortingRepository<AdminDoctorCompanyEntity, Integer> {

	public static final String MODULE_NAME = "doctorcompanies";
	
	//1.
	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "all", rel = "all")
//	@Query("SELECT i FROM AdminDoctorCompanyEntity i  ORDER BY i.name")
	@Cacheable
	public Page<AdminDoctorCompanyEntity> findByNameContains(@Param("name") String name, Pageable pageable);
	

	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "allCompanies", rel = "allCompanies")
//	@Query("SELECT i FROM AdminDoctorCompanyEntity i where i.enabled = 1 ORDER BY i.name")
	@Cacheable
	public List<AdminDoctorCompanyEntity> findByEnabledIsTrue();

//		@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
//	@RestResource(path = "allCompaniesGrp", rel = "allCompaniesGrp")
//    @Query(value="SELECT* FROM doctor_company dc LEFT JOIN doctor_group dg ON dc.id = dg.doctor_company_id where dc.name =:name",nativeQuery = true)
//	@Cacheable
//	public Page<AdminDoctorCompanyEntity> findByQueryall(
//			@Param("name") String name,
//			Pageable pageable);





	//@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	//	@RestResource(path = "allEnabled", rel = "allEnabled")
	//	@Query("SELECT i FROM AdminDoctorEntity i where i.deleted = 0 AND i.enabled= 1 ORDER BY i.name")
	//	@Cacheable(key = "#enabled")
	//	public List<AdminDoctorEntity> findByAllEnabled();
	//2.
	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "allList", rel = "allList")
//	@Query("SELECT i FROM AdminDoctorCompanyEntity i where i.enabled=:enabled  ORDER BY i.name")
	@Cacheable(key = "#enabled-only")
	Page<AdminDoctorCompanyEntity> findByNameContainsAndEnabled(@Param("name") String name, @Param("enabled") Boolean enabled, Pageable pageable);
	

//	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
//	@RestResource(path = "customquery", rel = "customquery")
//	@Query("SELECT i FROM AdminDoctorCompanyEntity i where  CONCAT(i.name) LIKE %:query% AND (:enabled is null or i.enabled = :enabled)   ORDER BY i.name")
//	@Cacheable
//	public Page<AdminDoctorCompanyEntity> findByQuery(@Param("query") String query, @Param("enabled") Boolean enabled, Pageable pageable);


	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "customquery", rel = "customquery")
	@Query("SELECT i FROM AdminDoctorCompanyEntity i where  CONCAT(i.name) LIKE %:query% AND (:enabled is null or i.enabled = :enabled)   ORDER BY CASE WHEN :sortDirection = 'desc' " +
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
	public Page<AdminDoctorCompanyEntity> findByQuery(
			@Param("query") String query,
			@Param("columnName") String columnName	 ,
			@Param("sortDirection") String sortDirection,
			@Param("enabled") Boolean enabled, Pageable pageable);




	//all list
	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "customqueryall", rel = "customqueryall")
	@Query("SELECT i FROM AdminDoctorCompanyEntity i where  CONCAT(i.name) LIKE %:query% AND (:enabled is null or i.enabled = :enabled) ORDER BY i.name")
	@Cacheable
	public List<AdminDoctorCompanyEntity> findByQueryAll(@Param("query") String query, @Param("enabled") Boolean enabled);
	
	
	@Override
	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@CachePut
	<S extends AdminDoctorCompanyEntity> S save(S companyEntity);
	
	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "exists", rel = "exists")
	@Query("SELECT i FROM AdminDoctorCompanyEntity i where i.name=:name")
	@Cacheable(key = "#name")
	public AdminDoctorCompanyEntity findByName(@Param("name") String name);

	@Query(value="SELECT count(i) FROM AdminDoctorCompanyEntity i")
	public long countAllDoctorCompany();

//	@Override
//	@PreAuthorize("hasAuthority('role_admin')")
//	void delete(AdminDoctorCompanyEntity entity);

	/* Disabled */
	@Override
	@RestResource(exported = false)
	void delete(AdminDoctorCompanyEntity entity);

	@Override
	@RestResource(exported = false)
	List<AdminDoctorCompanyEntity> findAll();

	@Override
	@RestResource(exported = false)
	void deleteById(Integer id);

	@Override
	@RestResource(exported = false)
	void deleteAll();

	@Override
	@RestResource(exported = false)
	void deleteAll(Iterable<? extends AdminDoctorCompanyEntity> entities);

}
