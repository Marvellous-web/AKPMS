package com.idsargus.akpmsarservice.repository;

import org.springframework.cache.annotation.CachePut;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.idsargus.akpmsarservice.model.domain.ArFiles;

@RepositoryRestResource(path = FilesRepository.MODULE_NAME, collectionResourceRel = FilesRepository.MODULE_NAME)
public interface FilesRepository extends PagingAndSortingRepository<ArFiles, Integer>{
	
	public static final String MODULE_NAME = "files";

	@Override
	@CachePut
	<S extends ArFiles> S save(S Files);
	

}
