package com.idsargus.akpmsarservice.repository;

import com.idsargus.akpmsarservice.model.domain.EmailTemplate;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = EmailTemplateRestRepository.MODULE_NAME, collectionResourceRel = EmailTemplateRestRepository.MODULE_NAME)
public interface EmailTemplateRestRepository extends PagingAndSortingRepository<EmailTemplate, Integer> {
    public static final String MODULE_NAME = "emailTemplateRestRepository";
}
