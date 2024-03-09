package com.idsargus.akpmsarservice.repository;

import com.idsargus.akpmsarservice.model.domain.ArProcessManual;
import com.idsargus.akpmsarservice.model.domain.ProcessManual;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = ArProcessManualListAllRepository.MODULE_NAME, collectionResourceRel = ArProcessManualListAllRepository.MODULE_NAME)
public interface ArProcessManualListAllRepository
        extends PagingAndSortingRepository<ProcessManual, Integer> {

    public static final String MODULE_NAME = "arProcessManualListAllRepository";

//    @Query(value = "SELECT * FROM process_manual where is_deleted=0 and parent_id is null",
//            nativeQuery = true)
//    public Page<ProcessManual> getProcessManualIsDeletedNull(Pageable pageable);

    //added on 17 oct 2023 for order
    @Query(value = "SELECT * FROM process_manual where is_deleted=0 and parent_id is null order by id asc",
            nativeQuery = true)
    public Page<ProcessManual> getProcessManualIsDeletedNull(Pageable pageable);

    ////---------following Change -------///
//@Query(value = "SELECT * FROM process_manual where is_deleted=0 ",
//        nativeQuery = true)
    @Query(value = "Select count(*) from process_manual where is_deleted =0 and parent_id is null",
            nativeQuery = true)
    public int getTotalElements();



//        @Query(value = "SELECT * FROM process_manual where is_deleted=0 and parent_id is null",
//            nativeQuery = true)
//    public List<ProcessManual> getProcessManualIsDeletedNullWithoutPagination();
    //added on 17 oct 2023
@Query(value = "SELECT * FROM process_manual where is_deleted=0 and parent_id is null order by id asc",
        nativeQuery = true)
public List<ProcessManual> getProcessManualIsDeletedNullWithoutPagination();

    @Query(value = "SELECT * FROM process_manual where is_deleted=0 " +
            "and parent_id = :parentId ", nativeQuery = true)
    public Page<ProcessManual> getProcessManualIsDeletedNullByProcessManualParentId(@Param("parentId")  Integer parentId,
                                                                                    Pageable pageable);

//@Query(value = "SELECT * FROM process_manual where is_deleted=0 " +
//            "and parent_id = :parentId and title LIKE %:title% order by position asc", nativeQuery = true)
//
    @Query(value = "SELECT * FROM process_manual where is_deleted=0 " +
            "and parent_id = :parentId and title LIKE %:title% order by position asc", nativeQuery = true)
    public List<ProcessManual> getProcessManualIsDeletedNullByProcessManual(@Param("parentId")  Integer parentId,
                                                                            @Param("title") String title);

//    @Query(value = "SELECT * FROM process_manual where is_deleted=0 " +
//            "and parent_id = :parentId order by position asc", nativeQuery = true)
//    public List<ProcessManual> getChildProcessManualList(@Param("parentId")  Integer parentId);
@Query(value = "SELECT * FROM process_manual where is_deleted=0 " +
        "and parent_id = :parentId", nativeQuery = true)
public List<ProcessManual> getChildProcessManualList(@Param("parentId")  Integer parentId);

//    @Query(value = "SELECT * FROM process_manual where is_deleted=0 " +
//            "and id = :id order by position asc", nativeQuery = true)
//    public List<ProcessManual> findByIdIsDeleted(@Param("id") Integer id);
@Query(value = "SELECT * FROM process_manual where is_deleted=0 " +
        "and id = :id order by id asc", nativeQuery = true)
public List<ProcessManual> findByIdIsDeleted(@Param("id") Integer id);
//    @Query(value = "SELECT * FROM process_manual p1 where title LIKE %:title% and is_deleted=0", nativeQuery = true)
//    List<ProcessManual> findProcessManualBySearchTitle(@Param("title") String title);
@Query(value = "SELECT * FROM process_manual p1 where title LIKE %:title% or content LIKE %:title% and is_deleted=0", nativeQuery = true)
List<ProcessManual> findProcessManualBySearchTitle(@Param("title") String title);
//SELECT * FROM process_manual p1 where title LIKE %:title% or content LIKE %:title% and is_deleted=0
    //@Query(value = "SELECT * FROM process_manual p1 where title LIKE %:title% and is_deleted=0", nativeQuery = true)
    //    List<ProcessManual> findProcessManualBySearchTitle(@Param("title") String title);

    @Query(value = "SELECT * FROM process_manual p1 where p1.parent_id= :id and is_deleted=0 ", nativeQuery = true)
    List<ProcessManual> findAllChildListByParent(@Param("id") Integer id);

}
