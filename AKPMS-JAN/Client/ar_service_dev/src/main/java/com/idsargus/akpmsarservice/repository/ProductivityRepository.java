package com.idsargus.akpmsarservice.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.idsargus.akpmscommonservice.entity.ArProductivityEntity;

@Repository
public interface ProductivityRepository extends PagingAndSortingRepository<ArProductivityEntity, Integer> {
	
	

}
