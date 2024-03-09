package com.idsargus.akpmsarservice.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.idsargus.akpmscommonservice.entity.MoneySource;
import com.idsargus.akpmscommonservice.entity.PaymentBatch;
import com.idsargus.akpmscommonservice.entity.PaymentBatchEntity;
import com.idsargus.akpmscommonservice.entity.PaymentProductivity;
import com.idsargus.akpmscommonservice.entity.RevenueType;

@RepositoryRestResource(path = MoneySourceRepository.MODULE_NAME, collectionResourceRel = MoneySourceRepository.MODULE_NAME)
public interface MoneySourceRepository extends CrudRepository<MoneySource, Integer>{
	

	public static final String MODULE_NAME = "moneySource";
	
	
	
	@RestResource(path = "customqueryall", rel = "customqueryall")
	@Query("SELECT i FROM MoneySource i  ")
	public Page<MoneySource> findByQueryAll(Pageable pageable);
	
	@Override
	<S extends MoneySource> S save(S moneySource);
}
