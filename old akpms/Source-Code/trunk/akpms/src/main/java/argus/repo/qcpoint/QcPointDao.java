package argus.repo.qcpoint;

import java.util.List;
import java.util.Map;

import argus.domain.QcPoint;
import argus.exception.ArgusException;

public interface QcPointDao {
	/**
	 *
	 * @param id
	 * @return
	 */
	QcPoint findById(Long id, boolean dependancies) throws ArgusException;

	/**
	 *
	 * @param name
	 * @return
	 */
	QcPoint findByName(String name) throws ArgusException;

	/**
	 *
	 * @param orderClauses
	 *            key=(orderBy,sortBy,offset,limit)
	 * @param whereClauses
	 * @return
	 */

	List<QcPoint> findAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses, boolean dependancies)
			throws ArgusException;

	/**
	 *
	 * @return
	 */
	List<QcPoint> findAllParentOrderedByName() throws ArgusException;

	/**
	 * parent should not return in parent list
	 *
	 * @param id
	 * @return
	 */
	List<QcPoint> findAllParentOrderedByName(Long id) throws ArgusException;

	/**
	 *
	 * @param department
	 */
	void addQcPoint(QcPoint qcPoint) throws ArgusException;

	/**
	 *
	 * @param department
	 */
	void updateQcPoint(QcPoint qcPoint) throws ArgusException;

	/**
	 * delete single record
	 *
	 * @param id
	 * @return
	 */
	int deleteQcPoint(Long id) throws ArgusException;

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
	int totalRecord(Map<String, String> conditionMap) throws ArgusException;

	/**
	 *
	 * @return
	 * @throws ArgusException
	 */
	List<QcPoint> getQcPointsWithParentIdAndChildCount(Long departmentId,
			Long subDepartmentId) throws ArgusException;

	/**
	 *
	 * @return Return thr
	 */
	List<Object[]> getQcPointStats() throws ArgusException;

	QcPoint findByNameAndDepartmentId(String name, Long departmentId)
			throws ArgusException;
}
