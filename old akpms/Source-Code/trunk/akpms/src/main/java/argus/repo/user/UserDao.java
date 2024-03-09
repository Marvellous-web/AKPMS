package argus.repo.user;

import java.util.List;
import java.util.Map;

import argus.domain.User;
import argus.exception.ArgusException;

public interface UserDao
{
	/**
	 *
	 * @param id
	 * @param dependancies
	 * @return
	 */
     User findById(Long id,boolean dependancies) throws ArgusException;

    /**
     *
     * @param email
     * @param loadDependacies
     * @return
     */
     User findByEmail(String email, boolean loadDependacies) throws ArgusException;

    /**
     *
     * @param orderBy
     * @return
     */
     List<User> findAll(String orderBy) throws ArgusException;

    /**
     *
     * @param user
     */
     void register(User user)  throws ArgusException;

    /**
     *
     * @param orderClauses
     * @param whereClauses
     * @return
     */
     List<User> findAll(Map<String,String> orderClauses, Map<String,String> whereClauses )  throws ArgusException;

    /**
    * delete single user
    * @param ids
    * @return
    */
    int deleteUser(Long id)  throws ArgusException;

    /**
     *
     * @param id
     * @param status
     */
     int changeStatus(long id, boolean status) throws ArgusException;

	/**
	 * 
	 * @param id
	 * @param status
	 */
	int resetPassword(long id) throws ArgusException;

    /**
     *
     * @param conditionMap
     * @return
     */
     int totalRecord(Map<String,String> conditionMap)  throws ArgusException;

    /**
     *
     * @param user
     * @throws ArgusException
     */
     void updateUser(User user) throws ArgusException;

    /**
     *
     * @return the stats of the user including total users,
     *         inactive users and active users
     */
     List<Object[]> getUserStats()  throws ArgusException;

    /**
     *function to put login details
     */
     void insertLoginDetails(Long userid)  throws ArgusException;

    /**
     * functio to get last login details
     * @param userId
     * @return
     * @throws ArgusException
     */
     String getLastLoginDetails(Long userId)  throws ArgusException;

     /**
     *
     * @param orderBy
     * @return
     */
     List<User> getUserByDept() throws ArgusException;

	/**
	 * 
	 * @param orderBy
	 * @return
	 */
	List<User> getCredentialingAccountingDeptUser() throws ArgusException;

	List<User> getOffsetUser() throws ArgusException;

	public int changePassword(Long id, String newPassword)
			throws ArgusException;

	List<User> getUserByParentDeptIdAndSubDeptById(long parentDeptId, long subDeptId) throws ArgusException;

	List<User> getUserByDeptId(long deptId);

}
