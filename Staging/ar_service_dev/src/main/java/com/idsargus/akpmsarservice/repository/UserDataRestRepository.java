package com.idsargus.akpmsarservice.repository;

import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import com.idsargus.akpmscommonservice.entity.UserEntity;

import javax.transaction.Transactional;

@RepositoryRestResource(path = UserDataRestRepository.MODULE_NAME, collectionResourceRel = UserDataRestRepository.MODULE_NAME)
//@CacheConfig(cacheNames = UserDataRestRepository.MODULE_NAME)
public interface UserDataRestRepository extends PagingAndSortingRepository<UserEntity, Integer> {

	public static final String MODULE_NAME = "users";

	@PreAuthorize("hasAuthority('role_admin')")
	@RestResource(path = "all", rel = "all")
	@Query("SELECT i FROM UserEntity i where CONCAT(i.firstName, '', i.lastName, '', i.email) LIKE %:name% OR i.enabled=:enabled AND i.deleted = 0 ORDER BY i.firstName, i.lastName")
	@Cacheable
	public Page<UserEntity> findAll(@Param("name") String query, @Param("enabled") Boolean enabled, Pageable pageable);

	@Query(value = "SELECT p.permission_id ,pp.name,pp.description, d.department_id , pp.read, pp.delete , " +
			"pp.write, pp.update FROM user_department d join  user_permission p  on  d.user_id = p.user_id " +
			"join permission  pp on pp.id = p.permission_id where d.user_id = :userId", nativeQuery = true)
	List<Map<String, Object>> findPermissionByDepartment(@Param("userId") String userId);


	@Query(value = "SELECT p.permission_id,pp.name,pp.description, pp.read, pp.delete ,pp.write, pp.update FROM user_permission p\n" +
			" join permission  pp on pp.id = p.permission_id where p.user_id = :userId" , nativeQuery = true)
	List<Map<String, Object>> findListOfPermissionByUser(@Param("userId") String userId);
	@Query(value = "SELECT  d.department_id FROM user_department d " +
			" where d.user_id = :userId ", nativeQuery = true)
	List<Map<String, Object>> findDepartmentsByUserId(@Param("userId") String userId);
//	@PreAuthorize("hasAuthority('role_admin')")
	@RestResource(path = "enableall", rel = "enableall")
	@Query("SELECT i FROM UserEntity i where  i.enabled= 1 AND i.deleted = 0 ORDER BY i.firstName, i.lastName")
	@Cacheable
	public List<UserEntity> findEnableAll();

//	@RestResource(path = "findByOrderByColumn", rel = "findByOrderByColumn")
//	@Query(value = "SELECT * FROM user u ORDER BY u.first_name asc", nativeQuery = true)
//	List<UserEntity> findUserByColumnName(@Param("columnName") String columnName);

	@PreAuthorize("hasAuthority('role_admin', 'role_user')")
	@RestResource(path = "customquery", rel = "customquery")
//	@Query("SELECT i FROM UserEntity i where CONCAT(i.firstName, '', i.lastName, '', i.email) " +
//			"LIKE %:query% AND (:enabled is null or i.enabled = :enabled) " +
//			"AND (:deleted is null or i.deleted = :deleted) ORDER BY i.firstName, i.lastName")
	@Query("SELECT i FROM UserEntity i where CONCAT(i.firstName, '', i.lastName, '', i.email) " +
			"LIKE %:query% AND (:enabled is null or i.enabled = :enabled) " +
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
	public Page<UserEntity> findByQueryAndOrderByColumnName(
		@Param("columnName") String columnName	 ,
		@Param("query") String query, @Param("enabled") Boolean enabled,
		@Param("sortDirection") String sortDirection,
															@Param("deleted") Boolean deleted,
															Pageable pageable);

	@Override
	@PreAuthorize("hasAuthority('role_admin', 'role_user')")
	@CachePut
	<S extends UserEntity> S save(S userEntity);

	@PreAuthorize("hasAuthority('role_admin')")
	@RestResource(path = "email", rel = "email")
	@Query("SELECT i FROM UserEntity i where i.deleted = 0 AND i.email=:email")
	@Cacheable(key = "#email")
	public UserEntity findByNameForAdmin(@Param("email") String email);

	@Query("SELECT i FROM UserEntity i where  i.id = :id")
	public UserEntity findByIdCustom(@Param("id") Integer id);

	@Modifying
	@Transactional
	@Query("update UserEntity i set i.password = :password where i.id= :id")
	 Integer updatePassword(String password, Integer id);
	@Override
	@RestResource(exported = false)
	void deleteById(Integer id);

	@Override
	@RestResource(exported = false)
	void delete(UserEntity entity);

	@Override
	@RestResource(exported = false)
	void deleteAll();

	@Override
	@RestResource(exported = false)
	void deleteAll(Iterable<? extends UserEntity> entities);

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
