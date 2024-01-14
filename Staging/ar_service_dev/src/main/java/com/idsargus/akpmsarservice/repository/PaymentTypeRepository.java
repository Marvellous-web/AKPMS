package com.idsargus.akpmsarservice.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import com.idsargus.akpmscommonservice.entity.PaymentType;
import com.idsargus.akpmscommonservice.entity.PaymentTypeEntity;

@RepositoryRestResource(path = PaymentTypeRepository.MODULE_NAME, collectionResourceRel = PaymentTypeRepository.MODULE_NAME)
public interface PaymentTypeRepository extends CrudRepository<PaymentType, Integer>{
	

	public static final String MODULE_NAME = "paymentType";
	
	
	/*
	 * Added by ARN
	@Override
	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "all", rel = "all")
	@Query("SELECT i FROM PaymentTypeEntity i WHERE i.deleted = 0 ORDER BY i.name")
	@Cacheable
	public List<PaymentType> findAll();
	 */
	@Override
	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@RestResource(path = "all", rel = "all")
	@Query("SELECT i FROM PaymentType i WHERE i.deleted = 0 ORDER BY i.name")
	@Cacheable
	public List<PaymentType> findAll(); 
	
	@RestResource(path = "customqueryall", rel = "customqueryall")
	@Query("SELECT i FROM PaymentType i  ")
	public Page<PaymentType> findByQueryAll(Pageable pageable);
	
	@Override
	<S extends PaymentType> S save(S paymentType);
	

	
		@Query("SELECT count(*) FROM PaymentType")
		public Long getPaymentTypeCount();
		
		@Query(value="SELECT count(i) FROM PaymentType i where i.deleted =0")
		public long countByIsDeleted();
		//comment to upload
}
