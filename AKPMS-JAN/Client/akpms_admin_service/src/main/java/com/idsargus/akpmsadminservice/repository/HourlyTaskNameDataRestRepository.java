package com.idsargus.akpmsadminservice.repository;



        import java.util.List;

        import com.idsargus.akpmsadminservice.entity.AdminHourlyTaskName;
        import org.springframework.cache.annotation.CacheConfig;
        import org.springframework.cache.annotation.CachePut;
        import org.springframework.cache.annotation.Cacheable;
        import org.springframework.data.jpa.repository.Query;
        import org.springframework.data.repository.CrudRepository;
        import org.springframework.data.repository.query.Param;
        import org.springframework.data.rest.core.annotation.RepositoryRestResource;
        import org.springframework.data.rest.core.annotation.RestResource;
        import org.springframework.security.access.prepost.PreAuthorize;



@RepositoryRestResource(path = HourlyTaskNameDataRestRepository.MODULE_NAME, collectionResourceRel = HourlyTaskNameDataRestRepository.MODULE_NAME)
@CacheConfig(cacheNames = HourlyTaskNameDataRestRepository.MODULE_NAME)
public interface HourlyTaskNameDataRestRepository extends CrudRepository<AdminHourlyTaskName, Integer> {

    public static final String MODULE_NAME = "hourlytaskname";


    @Override
    @PreAuthorize("hasAnyAuthority('role_admin','role_user')")
    @RestResource(path = "all", rel = "all")
    @Query("SELECT i FROM AdminHourlyTaskName i WHERE i.deleted = 0 ORDER BY i.name DESC")
    @Cacheable
    public List<AdminHourlyTaskName> findAll();

    @Override
    @PreAuthorize("hasAnyAuthority('role_admin','role_user')")
    @CachePut
    <S extends AdminHourlyTaskName> S save(S hourlyTaskName);

    @PreAuthorize("hasAnyAuthority('role_admin','role_user')")
    @RestResource(path = "name", rel = "name")
    @Query("SELECT i FROM AdminHourlyTaskName i WHERE  i.deleted = 0 AND i.name=:name")
    @Cacheable(key = "#name")
    public AdminHourlyTaskName findByName(@Param("name") String name);

    @Override
    @RestResource(exported = false)
    void deleteById(Integer id);

    @Override
    @RestResource(exported = false)
    void delete(AdminHourlyTaskName entity);

    @Override
    @RestResource(exported = false)
    void deleteAll();

    @Override
    @RestResource(exported = false)
    void deleteAll(Iterable<? extends AdminHourlyTaskName> entities);

//@RepositoryRestResource(path = HourlyTaskNameDataRestRepository.MODULE_NAME, collectionResourceRel = HourlyTaskNameDataRestRepository.MODULE_NAME)
//@CacheConfig(cacheNames = HourlyTaskNameDataRestRepository.MODULE_NAME)
//public interface HourlyTaskNameDataRestRepository extends CrudRepository<AdminHourlyTaskName, String> {
//
//    public static final String MODULE_NAME = "hourlytaskname";
//
//    @Override
//    @PreAuthorize("hasAnyAuthority('role_admin','role_user')")
//    @RestResource(path = "all", rel = "all")
//    @Query("SELECT i FROM AdminHourlyTaskName i ORDER BY i.id")
//    @Cacheable
//    public List<AdminHourlyTaskName> findAll();
//
//    @Override
//    @PreAuthorize("hasAnyAuthority('role_admin','role_user')")
//    @CachePut
//    <S extends AdminHourlyTaskName> S save(S hourlyTaskName);
//
//    @PreAuthorize("hasAnyAuthority('role_admin','role_user')")
//    @RestResource(path = "name", rel = "name")
//    @Query("SELECT i FROM AdminHourlyTaskName i where i.name=:name")
//    @Cacheable(key = "#name")
//    public AdminHourlyTaskName findByName(@Param("name") String name);
//
//    @Override
//    @RestResource(exported = false)
//    void deleteById(String id);
//
//    @Override
//    @RestResource(exported = false)
//    void delete(AdminHourlyTaskName entity);
//
//    @Override
//    @RestResource(exported = false)
//    void deleteAll();
//
//    @Override
//    @RestResource(exported = false)
//    void deleteAll(Iterable<? extends AdminHourlyTaskName> entities);
//	@Query("SELECT ht FROM AdminHourlyTaskEntity ht where ht.deleted = 0 AND ht.enabled = :status")
//	List<AdminHourlyTaskEntity> findWithStatus(@Param("status") boolean status);


}
