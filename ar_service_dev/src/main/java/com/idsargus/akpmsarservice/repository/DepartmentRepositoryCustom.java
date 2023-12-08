package com.idsargus.akpmsarservice.repository;

import com.idsargus.akpmsarservice.model.domain.Department;
import com.idsargus.akpmscommonservice.entity.DepartmentEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;



	@RepositoryRestResource(path = "department", collectionResourceRel = "department")   
	public interface DepartmentRepositoryCustom extends PagingAndSortingRepository<Department, Integer> {
	

	}
