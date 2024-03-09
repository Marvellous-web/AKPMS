package com.idsargus.akpmsadminservice.repository;

import java.util.List;

import com.idsargus.akpmsadminservice.entity.AdminMoneySourceEntity;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;


@RepositoryRestResource(path = MoneySourceDataRestRepository.MODULE_NAME, collectionResourceRel = MoneySourceDataRestRepository.MODULE_NAME)
@CacheConfig(cacheNames = MoneySourceDataRestRepository.MODULE_NAME)
public interface MoneySourceDataRestRepository extends CrudRepository<AdminMoneySourceEntity, Integer> {

	public static final String MODULE_NAME = "moneysources";

	@Override
	@PreAuthorize("hasAuthority('role_admin')")
	@RestResource(path = "all", rel = "all")
	@Query("SELECT i FROM AdminMoneySourceEntity i WHERE i.deleted = 0 ORDER BY i.name")
	@Cacheable
	public List<AdminMoneySourceEntity> findAll();

	@Override
	@PreAuthorize("hasAuthority('role_admin')")
	@CachePut
	<S extends AdminMoneySourceEntity> S save(S moneySourceEntity);

	@PreAuthorize("hasAuthority('role_admin')")
	@RestResource(path = "name", rel = "name")
	@Query("SELECT i FROM AdminMoneySourceEntity i WHERE i.deleted = 0 AND i.name=:name")
	@Cacheable(key = "#name")
	public AdminMoneySourceEntity findByNameForAdmin(@Param("name") String name);

	@Override
	@RestResource(exported = false)
	void deleteById(Integer id);

	@Override
	@RestResource(exported = false)
	void delete(AdminMoneySourceEntity entity);

	@Override
	@RestResource(exported = false)
	void deleteAll();

	@Override
	@RestResource(exported = false)
	void deleteAll(Iterable<? extends AdminMoneySourceEntity> entities);

//	@RestResource(path = "withStatus", rel = "status")
//	@Query("SELECT i FROM AdminMoneySourceEntity i where i.deleted = 0 AND i.enabled = :status")
//	public Page<AdminMoneySourceEntity> findWithStatus(@Param("status") boolean status, Pageable p);
//
//	@RestResource(path = "enabledOnly", rel = "enabledOnly")
//	@Query("SELECT i FROM AdminMoneySourceEntity i where i.deleted = 0 AND i.enabled = 1")
//	public Page<AdminMoneySourceEntity> findForUser(Pageable p);
//
//	@RestResource(path = "enabledOnlyNameLike", rel = "enabledOnlyNameLike")
//	@Query("SELECT i FROM AdminMoneySourceEntity i where i.deleted = 0 AND i.enabled = 1 AND i.name like %:name%")
//	public Page<AdminMoneySourceEntity> findByNameLike(@Param("name") String name, Pageable p);

}
