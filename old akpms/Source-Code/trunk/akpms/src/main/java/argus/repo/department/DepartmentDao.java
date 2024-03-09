package argus.repo.department;

import java.util.List;
import java.util.Map;

import argus.domain.Department;
import argus.exception.ArgusException;

public interface DepartmentDao
{
    /**
     *
     * @param id
     * @return
     */
	 Department findById(Long id, boolean dependancies)  throws ArgusException;

	/**
	 *
	 * @param name
	 * @return
	 */
     Department findByName(String name) throws ArgusException;

    /**
     *
     * @param orderClauses key=(orderBy,sortBy,offset,limit)
     * @param whereClauses
     * @return
     */



     List<Department> findAll(Map<String,String> orderClauses, Map<String,String> whereClauses ,boolean dependancies)  throws ArgusException;

    /**
     *
     * @return
     */
     List<Department> findAllParentOrderedByName()  throws ArgusException;

    /**
     * parent should not return in parent list
     * @param id
     * @return
     */
     List<Department> findAllParentOrderedByName(Long id)  throws ArgusException;

    /**
     *
     * @param department
     */
     void addDepartment(Department department)  throws ArgusException;

    /**
     *
     * @param department
     */
     void updateDepartment(Department department)  throws ArgusException;

    /**
     * delete single record
     * @param id
     * @return
     */
     int deleteDepartment(Long id)  throws ArgusException;

    /**
     *
     * @param id
     * @param status
     */
     int changeStatus(long id, boolean status) throws ArgusException;

    /**
     *
     * @param conditionMap
     * @return
     */
     int totalRecord(Map<String,String> conditionMap)  throws ArgusException;

    /**
     *
     * @return
     * @throws ArgusException
     */
     List<Department> getDepartmentsWithParentIdAndChildCount() throws ArgusException;

    /**
     *
     * @return Return thr
     */
     List<Object[]> getDepartmentStats()  throws ArgusException;
     
}
