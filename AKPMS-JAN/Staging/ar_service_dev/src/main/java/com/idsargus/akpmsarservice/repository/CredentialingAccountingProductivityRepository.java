package com.idsargus.akpmsarservice.repository;

import com.idsargus.akpmsarservice.model.domain.CredentialingAccountingProductivity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = CredentialingAccountingProductivityRepository.MODULE_NAME, collectionResourceRel = CredentialingAccountingProductivityRepository.MODULE_NAME)
public interface CredentialingAccountingProductivityRepository extends PagingAndSortingRepository<CredentialingAccountingProductivity, Integer> {

    public static final String MODULE_NAME = "credentialingAccountingProductivity";

    //	@CachePut
    @Override
    <S extends CredentialingAccountingProductivity> S save(S credentialingAccountingProductivity);

}

