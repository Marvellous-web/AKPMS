package com.idsargus.akpmsarservice.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.idsargus.akpmscommonservice.entity.PaymentBatch;
import com.idsargus.akpmscommonservice.entity.PaymentBatchEntity;
import com.idsargus.akpmscommonservice.entity.PaymentProductivity;
import com.idsargus.akpmscommonservice.entity.RevenueType;

@RepositoryRestResource(path = RevenueTypeRepository.MODULE_NAME, collectionResourceRel = RevenueTypeRepository.MODULE_NAME)
public interface RevenueTypeRepository extends CrudRepository<RevenueType, Integer>{
	

	public static final String MODULE_NAME = "revenueType";
	
	
	
	@RestResource(path = "customqueryall", rel = "customqueryall")
	@Query("SELECT i FROM RevenueType i  ")
	public Page<RevenueType> findByQueryAll(Pageable pageable);

//	@RestResource(path = "customquery", rel = "customquery")
//	@Query("SELECT i FROM RevenueType i  ")
//	public Page<RevenueType> findByQuery();
	@Override
	<S extends RevenueType> S save(S revenueType);
}
