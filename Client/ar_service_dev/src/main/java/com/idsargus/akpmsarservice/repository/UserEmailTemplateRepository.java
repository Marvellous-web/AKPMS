package com.idsargus.akpmsarservice.repository;

import com.idsargus.akpmsarservice.model.domain.UserEmailTemplateEntity;
import com.idsargus.akpmscommonservice.entity.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;


@RepositoryRestResource(path = UserEmailTemplateRepository.MODULE_NAME, collectionResourceRel
        = UserEmailTemplateRepository.MODULE_NAME)
public interface UserEmailTemplateRepository extends PagingAndSortingRepository<UserEmailTemplateEntity
        , Integer> {

    public static final String MODULE_NAME = "userEmailTemplate";

    @Query("Select i from UserEmailTemplateEntity i where i.userId = :userId and  emailTemplateId = 1")
    public UserEmailTemplateEntity findSubscriptionByUserId(Integer userId);

}
