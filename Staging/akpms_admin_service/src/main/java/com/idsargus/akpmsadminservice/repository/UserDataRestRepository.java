package com.idsargus.akpmsadminservice.repository;

import java.util.List;

import com.idsargus.akpmsadminservice.entity.User;
import com.idsargus.akpmscommonservice.entity.UserEntity;
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


@RepositoryRestResource(path = UserDataRestRepository.MODULE_NAME, collectionResourceRel = UserDataRestRepository.MODULE_NAME)
@CacheConfig(cacheNames = UserDataRestRepository.MODULE_NAME)
public interface UserDataRestRepository extends PagingAndSortingRepository<User, Integer> {

	public static final String MODULE_NAME = "users";


	//@PreAuthorize("hasAuthority('role_admin')")
	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "all", rel = "all")
	@Query("SELECT i FROM User i where CONCAT(i.firstName, '', i.lastName, '', i.email) LIKE %:name% OR i.enabled=:enabled AND i.deleted = 0 ORDER BY i.firstName, i.lastName")
	@Cacheable
	public Page<User> findAll(@Param("name") String query, @Param("enabled") Boolean enabled, Pageable pageable);

//	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
//	@RestResource(path = "all", rel = "all")
//	@Query("SELECT i FROM User i where CONCAT(i.firstName, '', i.lastName, '', i.email) LIKE %:name% OR i.enabled=:enabled AND i.deleted = 0 " +
//			"ORDER BY  CASE WHEN :sortDirection = 'desc' " +
//			" THEN CASE " +
//			" WHEN :columnName = 'firstName' THEN i.firstName "+
//			" WHEN :columnName  = 'lastName' THEN i.lastName " +
//			" WHEN :columnName  = 'email' THEN i.email " +
//			" WHEN :columnName  = 'createdOn' THEN i.createdOn " +
//			" WHEN :columnName  = 'modifiedOn' THEN i.modifiedOn ELSE i.firstName END " +
//			" END DESC, " +
//			" CASE WHEN :sortDirection = 'asc' " +
//			" THEN CASE " +
//			" WHEN :columnName = 'firstName' THEN i.firstName "+
//			" WHEN :columnName  = 'lastName' THEN i.lastName " +
//			" WHEN :columnName  = 'email' THEN i.email " +
//			" WHEN :columnName  = 'createdOn' THEN i.createdOn " +
//			" WHEN :columnName  = 'modifiedOn' THEN i.modifiedOn ELSE i.firstName END " +
//			" END ASC ")
//	@Cacheable
//	//public Page<User> findAll(@Param("name") String query, @Param("enabled") Boolean enabled, Pageable pageable);
//	public Page<User> findByQueryAndOrderByColumnName(
//			@Param("columnName") String columnName	 ,
//			@Param("name") String query, @Param("enabled") Boolean enabled,
//			@Param("sortDirection") String sortDirection,
//			@Param("deleted") Boolean deleted,
//			Pageable pageable);

//	@PreAuthorize("hasAuthority('role_admin','role_user')")
//	@RestResource(path = "customquery", rel = "customquery")
//	@Query("SELECT i FROM UserEntity i where CONCAT(i.firstName, '', i.lastName, '', i.email) " +
//			"LIKE %:query% AND (:enabled is null or i.enabled = :enabled) " +
//			"AND (:deleted is null or i.deleted = :deleted) ORDER BY CASE WHEN :sortDirection = 'desc' " +
//			" THEN CASE " +
//			" WHEN :columnName = 'firstName' THEN i.firstName "+
//			" WHEN :columnName  = 'lastName' THEN i.lastName " +
//			" WHEN :columnName  = 'email' THEN i.email " +
//			" WHEN :columnName  = 'createdOn' THEN i.createdOn " +
//			" WHEN :columnName  = 'modifiedOn' THEN i.modifiedOn ELSE i.firstName END " +
//			" END DESC, " +
//			" CASE WHEN :sortDirection = 'asc' " +
//			" THEN CASE " +
//			" WHEN :columnName = 'firstName' THEN i.firstName "+
//			" WHEN :columnName  = 'lastName' THEN i.lastName " +
//			" WHEN :columnName  = 'email' THEN i.email " +
//			" WHEN :columnName  = 'createdOn' THEN i.createdOn " +
//			" WHEN :columnName  = 'modifiedOn' THEN i.modifiedOn ELSE i.firstName END " +
//			" END ASC ")
//@Cacheable
//	public Page<UserEntity> findByQueryAndOrderByColumnName(
//		@Param("columnName") String columnName	 ,
//		@Param("query") String query, @Param("enabled") Boolean enabled,
//		@Param("sortDirection") String sortDirection,
//															@Param("deleted") Boolean deleted,
//															Pageable pageable);
//	@PreAuthorize("hasAuthority('role_admin')")
	@RestResource(path = "enableall", rel = "enableall")
	@Query("SELECT i FROM User i where  i.enabled= 1 AND i.deleted = 0 ORDER BY i.firstName, i.lastName")
	@Cacheable
	public List<User> findEnableAll();
 
	//@PreAuthorize("hasAuthority('role_admin')")
//@Query(value= "select * from akpmsdb.user us cross join akpmsdb.user_department dept, akpmsdb.department dep1 cross join akpmsdb.user_permission p3,akpmsdb.permission p4 where us.id=dept.user_id and dept.department_id=dep1.id and us.id=p3.user_id and p3.permission_id=p4.id and  CONCAT(us.first_name, '', us.last_name, '', us.email) LIKE '%:query%' and (:enabled is null or us.enabled = :enabled) AND (:deleted is null or us.deleted = :deleted) AND  (:departmentId is null or dep1.id = :departmentId) AND(:permissionId is null or p4.id = :permissionId) ORDER BY us.first_name, us.last_name ", nativeQuery = true)

//	@Query("SELECT i FROM UserEntity i  where CONCAT(i.firstName, '', i.lastName, '', i.email) LIKE %:query% AND" +
//			" (:enabled is null or i.enabled = :enabled) AND (:deleted is null or i.deleted = :deleted) AND  " +
//			"(:departmentId is null or i.departments.id = :departmentId) AND (:permissionId is null or i.permissions.id = :permissionId) ORDER BY i.firstName, i.lastName")
//@Query("SELECT i FROM UserEntity i  where CONCAT(i.firstName, '', i.lastName, '', i.email) LIKE %:query% AND" +
//		" (:enabled is null or i.enabled = :enabled) AND (:deleted is null or i.deleted = :deleted)")



//	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
//@RestResource(path = "customquery", rel = "customquery")
//	@Query("SELECT i FROM User i  where CONCAT(i.firstName, '', i.lastName, '', i.email) LIKE %:query% AND" +
//		" (:enabled is null or i.enabled = :enabled) AND (:deleted is null or i.deleted = :deleted)")
//   @Cacheable
//	Page<User> findByQuery(@Param("query") String query,
//										@Param("enabled") Boolean enabled,
//										@Param("deleted") Boolean deleted, Pageable pageable);


	@Query(value = "SELECT count(i) FROM User i where i.enabled = :enabled")
	 long countByEnable(@Param("enabled") Boolean enabled);


//	@RestResource(path = "sortByColumn", rel = "sortByColumn")
//	@Query(value = "SELECT i FROM User i order by (:columnName) (:orderBy)")
//	Page<User> findByQuery(@Param("columnName") String columnName, @Param("orderBy") String orderBy,
//						   Pageable pageable);
//	@Query(value= "select * from akpmsdb.user us cross join akpmsdb.user_department department1_, " +
//			"akpmsdb.department department2_ cross join akpmsdb.user_permission permission3_, " +
//			"akpmsdb.permission permission4_ where us.id=department1_.user_id " +
//			"and department1_.department_id=department2_.id and " +
//			"us.id=permission3_.user_id and permission3_.permission_id=permission4_.id " +
//			"and  CONCAT(us.first_name, '', us.last_name, '', us.email) LIKE '%:query%' and (:enabled is null or us.enabled = :enabled) AND (:deleted is null or us.deleted = :deleted) AND  (:departmentId is null or department2_.department_id = :departmentId) AND (:permissionId is null or permission4_.permission_id = :permissionId) ORDER BY us.first_name, us.last_name ;", nativeQuery = true)

//	@Query("SELECT i FROM UserEntity i IN i.departments departments  where CONCAT(i.firstName, '', i.lastName, '', i.email) LIKE %:query% AND" +
//			" (:enabled is null or i.enabled = :enabled) AND (:deleted is null or i.deleted = :deleted) AND (departments.id= :departmentId)")

	//@PreAuthorize("hasAuthority('role_admin', 'role_user')")
	@RestResource(path = "customquery", rel = "customquery")
	@Query("SELECT i FROM User i where CONCAT(i.firstName, '', i.lastName, '', i.email) LIKE %:query% " +
			"AND (:enabled is null or i.enabled = :enabled) " +
			"AND (:role is null or i.role.id = :role) " +
			"AND (COALESCE(:departmentIds) is null or EXISTS (select 1 from i.departments d where d.id IN (:departmentIds))) " +
			"AND (:deleted is null or i.deleted = :deleted) ORDER BY CASE WHEN :sortDirection = 'desc' " +
			" THEN CASE " +
			" WHEN :columnName = 'firstName' THEN i.firstName "+
			" WHEN :columnName  = 'lastName' THEN i.lastName " +
			" WHEN :columnName  = 'email' THEN i.email " +
			" WHEN :columnName  = 'createdOn' THEN i.createdOn " +
			" WHEN :columnName  = 'modifiedOn' THEN i.modifiedOn ELSE i.firstName END " +
			" END DESC, " +
			" CASE WHEN :sortDirection = 'asc' " +
			" THEN CASE " +
			" WHEN :columnName = 'firstName' THEN i.firstName "+
			" WHEN :columnName  = 'lastName' THEN i.lastName " +
			" WHEN :columnName  = 'email' THEN i.email " +
			" WHEN :columnName  = 'createdOn' THEN i.createdOn " +
			" WHEN :columnName  = 'modifiedOn' THEN i.modifiedOn ELSE i.firstName END " +
			" END ASC ")
	@Cacheable
	public Page<User> findByQueryAndOrderByColumnName(
			@Param("columnName") String columnName,
			@Param("query") String query,
			@Param("enabled") Boolean enabled,
			@Param("role") Integer role,
			@Param("departmentIds") List<Integer> departmentIds,
			@Param("sortDirection") String sortDirection,
			@Param("deleted") Boolean deleted,
			Pageable pageable);

	//
	//@Param("departmentId") String departmentId,
	///@Param("permissionId") String permissionId,
	@Override
	//@PreAuthorize("hasAuthority('role_admin')")
	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@CachePut
	<S extends User> S save(S user);

	//@PreAuthorize("hasAuthority('role_admin')")
	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "email", rel = "email")
	@Query("SELECT i FROM User i where i.deleted = 0 AND i.email=:email")
	@Cacheable(key = "#email")
	public User findByNameForAdmin(@Param("email") String email);

	@Override
	@RestResource(exported = false)
	void deleteById(Integer id);

	@Override
	@RestResource(exported = false)
	void delete(User entity);

	@Override
	@RestResource(exported = false)
	void deleteAll();

	@Override
	@RestResource(exported = false)
	void deleteAll(Iterable<? extends User> entities);

//	@RestResource(path = "withStatus", rel = "status")
//	@Query("SELECT i FROM UserEntity i where i.deleted = 0 AND i.enabled = :status")
//	public Page<UserEntity> findWithStatus(@Param("status") boolean status, Pageable p);
//
//	@RestResource(path = "enabledOnly", rel = "enabledOnly")
//	@Query("SELECT i FROM UserEntity i where i.deleted = 0 AND i.enabled = 1")
//	public Page<UserEntity> findForUser(Pageable p);
//
//	@RestResource(path = "enabledOnlyNameLike", rel = "enabledOnlyNameLike")
//	@Query("SELECT i FROM UserEntity i where i.deleted = 0 AND i.enabled = 1 AND (i.firstName like %:name% or i.lastName like %:name%)")
//	public Page<UserEntity> findByNameLike(@Param("name") String name, Pageable p);

}
