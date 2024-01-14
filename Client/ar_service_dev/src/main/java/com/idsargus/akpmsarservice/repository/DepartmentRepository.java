package com.idsargus.akpmsarservice.repository;

import java.util.List;
import java.util.Map;


import com.idsargus.akpmscommonservice.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.idsargus.akpmsarservice.exception.AppException;
import com.idsargus.akpmsarservice.model.domain.Department;


public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Integer>{

	  /**
    *
    * @param id
    * @return
    */
	 Department findById(Integer id, boolean dependancies)  throws AppException;

	/**
	 *
	 * @param name
	 * @return
	 */
    Department findByName(String name) throws AppException;

   /**
    *
    * @param orderClauses key=(orderBy,sortBy,offset,limit)
    * @param whereClauses
    * @return
    */



    List<Department> findAll(Map<String,String> orderClauses, Map<String,String> whereClauses ,boolean dependancies)  throws AppException;

   /**
    *
    * @return
    */
    List<Department> findAllParentOrderedByName()  throws AppException;

   /**
    * parent should not return in parent list
    * @param id
    * @return
    */
    List<Department> findAllParentOrderedByName(Long id)  throws AppException;

   /**
    *
    * @param department
    */
    void addDepartment(Department department)  throws AppException;

   /**
    *
    * @param department
    */
    void updateDepartment(Department department)  throws AppException;

   /**
    * delete single record
    * @param id
    * @return
    */
    int deleteDepartment(Long id)  throws AppException;

   /**
    *
    * @param id
    * @param status
    */
    int changeStatus(long id, boolean status) throws AppException;

   /**
    *
    * @param conditionMap
    * @return
    */
    int totalRecord(Map<String,String> conditionMap)  throws AppException;

   /**
    *
    * @return
    * @throws AppException
    */
    List<Department> getDepartmentsWithParentIdAndChildCount() throws AppException;

   /**
    *
    * @return Return thr
    */
    List<Object[]> getDepartmentStats()  throws AppException;
    
	
}
