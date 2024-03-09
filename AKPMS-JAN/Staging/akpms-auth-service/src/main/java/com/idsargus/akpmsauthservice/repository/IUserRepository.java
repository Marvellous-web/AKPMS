package com.idsargus.akpmsauthservice.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.idsargus.akpmsauthservice.entity.UserEntity;

import java.util.List;
import java.util.Map;

@Repository
@Transactional
public interface IUserRepository extends JpaRepository<UserEntity, Long> {

	UserEntity findByEmail(String email);

	@Query("SELECT u FROM UserEntity u where u.email = :email and u.enabled = true")
	UserEntity findByUsername(@Param("email") String email);

//	@Query(value = "SELECT p.permission_id ,pp.name, d.department_id , pp.read, pp.delete , pp.write, pp.update FROM user_department d join  user_permission p  on  d.user_id = p.user_id " +
//			"join permission  pp on pp.id = p.permission_id where d.user_id = :userId", nativeQuery = true)
//	List<Map<String, Object>> findPermissionByDepartment(String userId);


}
