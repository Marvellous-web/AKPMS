package argus.repo.doctor;

import java.util.List;
import java.util.Map;

import argus.domain.Doctor;
import argus.exception.ArgusException;

public interface DoctorDao
{
    /**
     *
     * @param id
     * @return
     */
	Doctor findById(Long id, boolean dependancies) throws ArgusException;

	/**
	 *
	 * @param name
	 * @return
	 */
	Doctor findByName(String name) throws ArgusException;

    /**
     *
     * @param orderClauses key=(orderBy,sortBy,offset,limit)
     * @param whereClauses
     * @return
     */

    /**
     * @param name It is the name of the doctor to be searched
     * @param id It is the id of the doctor to be searched
     */
	List<Doctor> findAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses, boolean dependancies)
			throws ArgusException;

    /**
     *
     * @return
     */
	List<Doctor> findAllParentOrderedByName(Long id)
			throws ArgusException;


    /**
     *
     * @param doctor
     */
	void addDoctor(Doctor doctor) throws ArgusException;

    /**
     *
     * @param doctor
     */
	void updateDoctor(Doctor doctor) throws ArgusException;

    /**
     * delete single record
     * @param id
     * @return
     */
	int deleteDoctor(Long id) throws ArgusException;

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
	int totalRecord(Map<String, String> conditionMap)
			throws ArgusException;

    /**
	 * 
	 * @return
	 * @throws ArgusException
	 */
	List<Doctor> getDoctorsWithParentIdAndChildCount()
			throws ArgusException;

    /**
     *
     * @return Return thr
     */
	List<Object[]> getDoctorStats() throws ArgusException;

	Doctor findByDoctorCode(String doctorCode, boolean dependancies)
			throws ArgusException;

}
