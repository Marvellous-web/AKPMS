package com.idsargus.akpmsadminservice.repository;

import java.util.List;

import com.idsargus.akpmsadminservice.entity.AdminPaymentTypeEntity;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;


@RepositoryRestResource(path = PaymentTypeDataRestRepository.MODULE_NAME, collectionResourceRel = PaymentTypeDataRestRepository.MODULE_NAME)
@CacheConfig(cacheNames = PaymentTypeDataRestRepository.MODULE_NAME)
public interface PaymentTypeDataRestRepository extends CrudRepository<AdminPaymentTypeEntity, Integer> {

	public static final String MODULE_NAME = "paymenttypes";

	@Override
	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "all", rel = "all")
	@Query("SELECT i FROM AdminPaymentTypeEntity i WHERE i.deleted = 0 ORDER BY i.name")
	@Cacheable
	public List<AdminPaymentTypeEntity> findAll();

	@Override
	//@PreAuthorize("hasAuthority('role_admin')")
	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@CachePut
	<S extends AdminPaymentTypeEntity> S save(S paymentTypeEntity);

	//@PreAuthorize("hasAuthority('role_admin')")
	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "name", rel = "name")
	@Query("SELECT i FROM AdminPaymentTypeEntity i WHERE i.deleted = 0 AND i.name=:name")
	@Cacheable(key = "#name")
	public AdminPaymentTypeEntity findByNameByAdmin(@Param("name") String name);

	@Override
	@RestResource(exported = false)
	void deleteById(Integer id);

	@Override
	@RestResource(exported = false)
	void delete(AdminPaymentTypeEntity entity);

	@Query(value="SELECT count(i) FROM AdminPaymentType i where i.deleted =0")
	public long countByIsDeleted();
	@Override
	@RestResource(exported = false)
	void deleteAll();

	@Override
	@RestResource(exported = false)
	void deleteAll(Iterable<? extends AdminPaymentTypeEntity> entities);

//	@RestResource(path = "withStatus", rel = "status")
//	@Query("SELECT i FROM AdminPaymentTypeEntity i where i.deleted = 0 AND i.enabled = :status")
//	public Page<AdminPaymentTypeEntity> findWithStatus(@Param("status") boolean status, Pageable p);
//
//	@RestResource(path = "enabledOnly", rel = "enabledOnly")
//	@Query("SELECT i FROM AdminPaymentTypeEntity i where i.deleted = 0 AND i.enabled = 1")
//	public Page<AdminPaymentTypeEntity> findForUser(Pageable p);
//
//	@RestResource(path = "enabledOnlyNameLike", rel = "enabledOnlyNameLike")
//	@Query("SELECT i FROM AdminPaymentTypeEntity i where i.deleted = 0 AND i.enabled = 1 AND i.name like %:name%")
//	public Page<AdminPaymentTypeEntity> findByNameLike(@Param("name") String name, Pageable p);

}
