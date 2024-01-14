package com.idsargus.akpmsarservice.repository;

import com.idsargus.akpmsarservice.model.domain.ArDatabase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(path = "database", collectionResourceRel = "database")
public interface DatabaseRepository extends PagingAndSortingRepository<ArDatabase, Integer> {

}

