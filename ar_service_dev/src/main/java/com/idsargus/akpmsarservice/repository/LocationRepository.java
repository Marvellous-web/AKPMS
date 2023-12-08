package com.idsargus.akpmsarservice.repository;

import java.util.List;

import com.idsargus.akpmsarservice.model.domain.ArChargeProdReject;
import com.idsargus.akpmsarservice.model.domain.ArLocation;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.idsargus.akpmscommonservice.entity.Location;

@RepositoryRestResource(path = LocationRepository.MODULE_NAME, collectionResourceRel = LocationRepository.MODULE_NAME)
public interface LocationRepository extends PagingAndSortingRepository<ArLocation, Integer>{
	
	public static final String MODULE_NAME = "location";

	@Override
	<S extends ArLocation> S save(S arLocation);
	@RestResource(path = "all", rel = "all")
	@Query("SELECT i FROM ArLocation i  ORDER BY i.name")
	@Cacheable
	public List<ArLocation> findAll();
}
