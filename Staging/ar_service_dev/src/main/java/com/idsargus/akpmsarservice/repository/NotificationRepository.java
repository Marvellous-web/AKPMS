package com.idsargus.akpmsarservice.repository;


import com.idsargus.akpmsarservice.model.domain.Notifications;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(path = NotificationRepository.MODULE_NAME, collectionResourceRel = NotificationRepository.MODULE_NAME)
public interface NotificationRepository extends PagingAndSortingRepository<Notifications, Integer> {

    public static final String MODULE_NAME = "notifications";

    @Override
    <S extends Notifications> S save(S notifications);

}