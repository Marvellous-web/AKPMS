package com.idsargus.akpmsarservice.repository;

import java.util.Date;
import java.util.List;

import com.idsargus.akpmsarservice.model.domain.QAWorksheet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.RequestParam;


@RepositoryRestResource(path = QAWorksheetRepository.MODULE_NAME, collectionResourceRel = QAWorksheetRepository.MODULE_NAME)
public interface QAWorksheetRepository extends PagingAndSortingRepository<QAWorksheet,Integer> {

    public static final String MODULE_NAME = "qaWorksheetEntity";

    <S extends QAWorksheet> S save(S qaWorksheetEntity);
// @RestResource(path = "customqueryall", rel = "customqueryall")
//    @Query(value= "SELECT i FROM QAWorksheet i LEFT OUTER JOIN ArUser au ON i.createdBy.id = au.id where CONCAT(i.name,i.status) LIKE %:query% " +
//            " AND ((:createdBy) is null or au.id IN (:createdBy)) AND (:status is null or i.status= :status)" +
//            "AND (:departmentId is null or i.department.id= :departmentId) " +
//            "AND (:subDepartmentId is null or i.subDepartment.id= :subDepartmentId) " +
//            "AND (:createdFrom is null or i.createdOn > :createdFrom) " +
//            "AND (:createdTo is null or i.createdOn < :createdTo)  ORDER BY i.id DESC")
//value = "select * from qa_worksheet q where CONCAT(q.name,q.status) LIKE %:query% and q.created_by
// //value = "select * from qa_worksheet q where (:query is null or CONCAT(q.name,q.status) LIKE %:query%)

//    @RestResource(path = "customqueryall", rel = "customqueryall")
//
//    @Query(value = "select * from qa_worksheet q where CONCAT(q.name,q.status) LIKE %:query% and q.created_by IN (:createdBy)" +
//            "AND (:status is null or q.status= :status) AND (:departmentId is null or q.department_id= :departmentId) " +
//            " AND (:subDepartmentId is null or q.sub_department_id= :subDepartmentId) " +
//            "AND (:createdFrom is null or q.created_on > :createdFrom)" +
//            "AND (:createdTo is null or q.created_on < :createdTo)  ORDER BY q.id DESC", nativeQuery = true)
//    Page<QAWorksheet> findByQueryAll(@Param("query") String query,  @RequestParam("createdBy") List<Long> createdBy,
//                                            @Param("status") Integer status, @Param("departmentId") Integer departmentId,
//                                            @Param("subDepartmentId") Integer subDepartmentId, @Param("createdFrom") Date createdFrom,
//                                            @Param("createdTo") Date createdTo, Pageable pageable);

    @RestResource(path = "customqueryall", rel = "customqueryall")
    @Query(value = "select * from qa_worksheet q where CONCAT(q.name,q.status) LIKE %:query% and (COALESCE(:createdBy) IS NULL or q.created_by IN (:createdBy))" +
            "AND (:status is null or q.status= :status) AND (:departmentId is null or q.department_id= :departmentId) " +
            " AND (:subDepartmentId is null or q.sub_department_id= :subDepartmentId) " +
            "AND (:createdFrom is null or q.created_on > :createdFrom)" +
            "AND (:createdTo is null or q.created_on < :createdTo)  ORDER BY q.id DESC", nativeQuery = true)
    Page<QAWorksheet> findByQueryAll(@Param("query") String query,  @Param("createdBy") List<Long> createdBy,
                                     @Param("status") Integer status, @Param("departmentId") Integer departmentId,
                                     @Param("subDepartmentId") Integer subDepartmentId, @Param("createdFrom") Date createdFrom,
                                     @Param("createdTo") Date createdTo, Pageable pageable);

    /*
    Department  there could be all department
SubDepartment there could be all subdepartment
createdBy: multiple selected
status: There could be all status

createdDateFRom
createdFromTO
Search
     */

    @RestResource(path = "all", rel = "all")
    @Query("SELECT i FROM QAWorksheet i")
    Page<QAWorksheet> getAll(Pageable pageable);


//    @Query("Select * from qa_worksheet i where i.id = :qaWorksheetId")
//    public QAWorksheet fetchQaWorksheetById(Long qaWorksheetId);


    @RestResource(path = "count", rel = "count")
    @Query("SELECT count(id) FROM QAWorksheet")
    public long countById();

}
