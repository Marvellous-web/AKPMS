package com.idsargus.akpmsarservice.repository;

import com.idsargus.akpmsarservice.model.domain.ArHourlyTaskEntity;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Date;
import java.util.List;


// import com.idsargus.akpmsarservice.entity.AdminHourlyTaskEntity;


@RepositoryRestResource(path = HourlyTaskDataRestRepository.MODULE_NAME, collectionResourceRel = HourlyTaskDataRestRepository.MODULE_NAME)
@CacheConfig(cacheNames = HourlyTaskDataRestRepository.MODULE_NAME)
public interface HourlyTaskDataRestRepository extends CrudRepository<ArHourlyTaskEntity, Integer> {

    public static final String MODULE_NAME = "hourlytasks";

    @Override
    @PreAuthorize("hasAnyAuthority('role_admin','role_user')")
    @RestResource(path = "all", rel = "all")
    @Query("SELECT i FROM ArHourlyTaskEntity i  ORDER BY i.id DESC")
    //@Query("SELECT i FROM AdminHourlyTaskEntity i WHERE i.deleted = 0 ORDER BY i.id DESC")
    @Cacheable
    public List<ArHourlyTaskEntity> findAll();
    @PreAuthorize("hasAnyAuthority('role_admin','role_user')")
    @RestResource(path = "allpagewise", rel = "allpagewise")
    @Query("SELECT i FROM ArHourlyTaskEntity i  ORDER BY i.id desc")
    //@Query("SELECT i FROM AdminHourlyTaskEntity i WHERE i.deleted = 0 ORDER BY i.id desc")
    @Cacheable
    public Page<ArHourlyTaskEntity> findAll(Pageable pageable);

    @Override
    //@PreAuthorize("hasAuthority('role_admin')")
    @PreAuthorize("hasAnyAuthority('role_admin','role_user')")
    @CachePut
    <S extends ArHourlyTaskEntity> S save(S hourlyTaskEntity);


    //@PreAuthorize("hasAuthority('role_admin')")
//	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
//	@RestResource(path = "name", rel = "name")
//	@Query("SELECT i FROM AdminHourlyTaskEntity i WHERE AND i.name=:name")
//	//@Query("SELECT i FROM AdminHourlyTaskEntity i WHERE i.deleted = 0 AND i.name=:name")
//	@Cacheable(key = "#name")
//	public AdminHourlyTaskEntity findByName(@Param("name") String name);

    //@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
//	@RestResource(path = "customqueryall", rel = "customqueryall")
//	//SELECT i FROM AdminDoctorCompanyEntity i where  CONCAT(i.name) LIKE %:query% AND
//	//value="SELECT i FROM AdminHourlyTaskEntity i WHERE i.name= :name and
//	@Query(value="SELECT i FROM AdminHourlyTaskEntity i WHERE (:name is null or i.name= :name) and" +
//			" (:createdFrom is null or i.createdOn >= :createdFrom) AND " +
//			"(:createdTo is null or i.createdOn <= :createdTo) AND " +
//			"(:taskCompletedFrom is null or i.taskCompleted >= :taskCompletedFrom) AND (:taskCompletedTo is null or i.taskCompleted <= :taskCompletedTo) AND (:dateReceivedFrom is null or i.dateReceived >= :dateReceivedFrom) AND (:dateReceivedTo is null or i.dateReceived >= :dateReceivedTo) AND (:createdBy is null or i.createdBy.id = :createdBy)")
//	public Page<AdminHourlyTaskEntity> findByQueryAll(@Param("name") String name,
//													  @Param("createdFrom") Date createdFrom,
//													  @Param("createdTo") Date createdTo,
//													  @Param("taskCompletedFrom") Date taskCompletedFrom,
//													  @Param("taskCompletedTo") Date taskCompletedTo,
//													  @Param("dateReceivedFrom") Date dateReceivedFrom,
//													  @Param("dateReceivedTo") Date dateReceivedTo,
//													  @Param("createdBy") Integer createdBy,
//													  Pageable pageable);	// customquerryall added by Tausif
    @PreAuthorize("hasAnyAuthority('role_admin','role_user')")
    @RestResource(path = "customqueryall", rel = "customqueryall")
    //SELECT i FROM AdminDoctorCompanyEntity i where  CONCAT(i.name) LIKE %:query% AND
    //value="SELECT i FROM AdminHourlyTaskEntity i WHERE i.name= :name and
	/*
	@Query("SELECT i FROM UserEntity i where CONCAT(i.firstName, '', i.lastName, '', i.email) " +
			"LIKE %:query% AND (:enabled is null or i.enabled = :enabled) " +
			"AND (:deleted is null or i.deleted = :deleted) ORDER BY CASE WHEN :sortDirection = 'desc' " +
			" THEN CASE " +
			" WHEN :columnName = 'firstName' THEN i.firstName "+
			" WHEN :columnName  = 'lastName' THEN i.lastName " +
			" WHEN :columnName  = 'email' THEN i.email " +
			" WHEN :columnName  = 'createdOn' THEN i.createdOn " +
			" WHEN :columnName  = 'modifiedOn' THEN i.modifiedOn ELSE i.firstName END " +
			" END DESC, " +
			" CASE WHEN :sortDirection = 'asc' " +
			" THEN CASE " +
			" WHEN :columnName = 'firstName' THEN i.firstName "+
			" WHEN :columnName  = 'lastName' THEN i.lastName " +
			" WHEN :columnName  = 'email' THEN i.email " +
			" WHEN :columnName  = 'createdOn' THEN i.createdOn " +
			" WHEN :columnName  = 'modifiedOn' THEN i.modifiedOn ELSE i.firstName END " +
			" END ASC ")
	 */
//	@Query(value="SELECT i FROM AdminHourlyTaskEntity i WHERE (:name is null or i.hourlyTask.id= :name) and" +
//			" (:createdFrom is null or i.createdOn >= :createdFrom) AND " +
//			"(:createdTo is null or i.createdOn <= :createdTo) AND " +
//			"(:taskCompletedFrom is null or i.taskCompleted >= :taskCompletedFrom) AND (:taskCompletedTo is null or i.taskCompleted <= :taskCompletedTo) AND (:dateReceivedFrom is null or i.dateReceived >= :dateReceivedFrom) AND (:dateReceivedTo is null or i.dateReceived >= :dateReceivedTo) AND (:createdBy is null or i.createdBy.id = :createdBy)")
    @Query(value="SELECT i FROM ArHourlyTaskEntity i WHERE" +
            " (:name is null or i.hourlyTask.id= :name) and" +
            " (:createdFrom is null or i.createdOn >= :createdFrom) AND " +
            "(:createdTo is null or i.createdOn <= :createdTo) AND " +
            "(:taskCompletedFrom is null or i.taskCompleted >= :taskCompletedFrom) AND " +
            "(:taskCompletedTo is null or i.taskCompleted <= :taskCompletedTo) AND " +
            "(:dateReceivedFrom is null or i.dateReceived >= :dateReceivedFrom) AND " +
            "(:dateReceivedTo is null or i.dateReceived >= :dateReceivedTo) AND " +
            "(:createdBy is null or i.createdBy = :createdBy) ORDER BY CASE" +
            " WHEN :sortDirection = 'desc' " +
            " THEN CASE " +
            " WHEN :columnName = 'time' THEN i.time "+
            " WHEN :columnName  = 'taskName' THEN i.hourlyTask.name " +
            " WHEN :columnName  = 'details' THEN i.details " +
            " WHEN :columnName  = 'modifiedBy' THEN i.modifiedBy " +
            " WHEN :columnName  = 'createdOn' THEN i.createdOn " +
            " WHEN :columnName  = 'createdBy' THEN i.createdBy " +
            " WHEN :columnName  = 'dateReceived' THEN i.dateReceived " +
            " WHEN :columnName  = 'modifiedOn' THEN i.modifiedOn " +
            " WHEN :columnName  = 'taskCompleted' THEN i.taskCompleted ELSE i.id END " +
            " END DESC, " +
            " CASE WHEN :sortDirection = 'asc' " +
            " THEN CASE " +
            " WHEN :columnName = 'time' THEN i.time "+
            " WHEN :columnName  = 'taskName' THEN i.hourlyTask.name " +
            " WHEN :columnName  = 'details' THEN i.details " +
            " WHEN :columnName  = 'modifiedBy' THEN i.modifiedBy " +
            " WHEN :columnName  = 'createdOn' THEN i.createdOn " +
            " WHEN :columnName  = 'createdBy' THEN i.createdBy " +
            " WHEN :columnName  = 'dateReceived' THEN i.dateReceived " +
            " WHEN :columnName  = 'modifiedOn' THEN i.modifiedOn " +
            " WHEN :columnName  = 'taskCompleted' THEN i.taskCompleted ELSE i.id END " +
            " END ASC, i.id desc ")
    public Page<ArHourlyTaskEntity> findByQueryAll(@Param("name") Integer name,
                                                      @Param("createdFrom") Date createdFrom,
                                                      @Param("createdTo") Date createdTo,
                                                      @Param("taskCompletedFrom") Date taskCompletedFrom,
                                                      @Param("taskCompletedTo") Date taskCompletedTo,
                                                      @Param("dateReceivedFrom") Date dateReceivedFrom,
                                                      @Param("dateReceivedTo") Date dateReceivedTo,
                                                      @Param("createdBy") Integer createdBy,
                                                      @Param("sortDirection") String sortDirection,
                                                      @Param("columnName") String columnName,
                                                      Pageable pageable);




    @Override
    @RestResource(exported = false)
    void deleteById(Integer id);

    @Override
    @RestResource(exported = false)
    void delete(ArHourlyTaskEntity entity);

    @Override
    @RestResource(exported = false)
    void deleteAll();

    @Override
    @RestResource(exported = false)
    void deleteAll(Iterable<? extends ArHourlyTaskEntity> entities);

//	@Query("SELECT ht FROM AdminHourlyTaskEntity ht where ht.deleted = 0 AND ht.enabled = :status")
//	List<AdminHourlyTaskEntity> findWithStatus(@Param("status") boolean status);

    @Query(value = "SELECT count(i) FROM ArHourlyTaskEntity i")
    public long countByDeleted();

}
