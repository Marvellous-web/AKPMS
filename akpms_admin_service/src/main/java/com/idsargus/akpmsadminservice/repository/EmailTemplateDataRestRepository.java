package com.idsargus.akpmsadminservice.repository;

import java.util.List;

import com.idsargus.akpmsadminservice.entity.AdminEmailTemplateEntity;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;


@RepositoryRestResource(path = EmailTemplateDataRestRepository.MODULE_NAME, collectionResourceRel = EmailTemplateDataRestRepository.MODULE_NAME)
@CacheConfig(cacheNames = EmailTemplateDataRestRepository.MODULE_NAME)
public interface EmailTemplateDataRestRepository extends CrudRepository<AdminEmailTemplateEntity, Integer> {

	public static final String MODULE_NAME = "emailtemplates";

	@Override
	@PreAuthorize("hasAuthority('role_admin')")
	@RestResource(path = "all", rel = "all")
	@Query("SELECT e FROM AdminEmailTemplateEntity e ORDER BY e.subscriptionEmail")
	@Cacheable
	List<AdminEmailTemplateEntity> findAll();

	@Override
	//@PreAuthorize("hasAuthority('role_admin')")
	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@CachePut
	<S extends AdminEmailTemplateEntity> S save(S emailTemplateEntity);

	@PreAuthorize("hasAuthority('role_admin') OR hasAuthority('role_user')")
	@RestResource(path = "subscriptionOnly", rel = "subscriptionOnly")
	@Query("SELECT e FROM AdminEmailTemplateEntity e WHERE e.subscriptionEmail = 1")
	@Cacheable(key = "#subscription-only")
	List<AdminEmailTemplateEntity> subscriptionOnlyEmailTemplate();
	
	@Override
	@RestResource(exported = false)
	void deleteById(Integer id);

	@Override
	@RestResource(exported = false)
	void delete(AdminEmailTemplateEntity entity);

	@Override
	@RestResource(exported = false)
	void deleteAll();

	@Override
	@RestResource(exported = false)
	void deleteAll(Iterable<? extends AdminEmailTemplateEntity> entities);

//	@Query("SELECT e FROM AdminEmailTemplateEntity e where e.id in :ids AND e.enabled = 1")
//	List<AdminEmailTemplateEntity> findByIds(List<Integer> ids);
//
//	@Query("SELECT e FROM AdminEmailTemplateEntity e where e.id in :ids AND e.enabled = 1 AND e.subscriptionEmail = 1")
//	List<AdminEmailTemplateEntity> findSubscriptionEmailByIds(List<Integer> ids);
}
