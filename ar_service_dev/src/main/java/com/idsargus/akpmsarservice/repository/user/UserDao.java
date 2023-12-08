package com.idsargus.akpmsarservice.repository.user;



import com.idsargus.akpmsarservice.model.domain.User;
import com.idsargus.akpmscommonservice.entity.UserEntity;

import java.util.List;
import java.util.Map;

public interface UserDao
{
    /**
     *
     * @param id
     * @param dependancies
     * @return
     */
    UserEntity findById(Long id, boolean dependancies) ;

    User findUserById(Integer id);
    /**
     *
     * @param email
     * @param loadDependacies
     * @return
     */
    User findByEmail(String email, boolean loadDependacies) ;

    /**
     *
     * @param orderBy
     * @return
     */
    List<User> findAll(String orderBy) ;

    /**
     *
     * @param user
     */
    void register(User user)  ;

    /**
     *
     * @param orderClauses
     * @param whereClauses
     * @return
     */
    List<User> findAll(Map<String,String> orderClauses, Map<String,String> whereClauses )  ;

    /**
     * delete single user
     * @param ids
     * @return
     */
    int deleteUser(Long id)  ;

    /**
     *
     * @param id
     * @param status
     */
    int changeStatus(long id, boolean status) ;



    /**
     *
     * @param conditionMap
     * @return
     */
    int totalRecord(Map<String,String> conditionMap)  ;

    /**
     *
     * @param user
     * @
     */
    void updateUser(User user) ;

    /**
     *
     * @return the stats of the user including total users,
     *         inactive users and active users
     */
    List<Object[]> getUserStats()  ;

    /**
     *function to put login details
     */
    void insertLoginDetails(Long userid)  ;

    /**
     * functio to get last login details
     * @param userId
     * @return
     * @
     */
    String getLastLoginDetails(Long userId)  ;

    /**
     *
     * @param orderBy
     * @return
     */
    List<User> getUserByDept() ;

    /**
     *
     * @param orderBy
     * @return
     */
    List<User> getCredentialingAccountingDeptUser() ;

    List<User> getOffsetUser() ;

    public int changePassword(Long id, String newPassword)
            ;

    List<User> getUserByParentDeptIdAndSubDeptById(long parentDeptId, long subDeptId) ;

    List<User> getUserByDeptId(long deptId);

}
