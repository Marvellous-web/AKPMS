package com.idsargus.akpmsauthservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.idsargus.akpmsauthservice.entity.UserEntity;
import com.idsargus.akpmsauthservice.model.CustomUser;
import com.idsargus.akpmsauthservice.repository.IUserRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private IUserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = null;

		try {
			userEntity = userRepository.findByUsername(username);
			//List<Map<String, Object>> permissionWithDepartments =   userRepository.findPermissionByDepartment(userEntity.getId().toString());
          //  userEntity.setPermissionWithDepartments(permissionWithDepartments);
			if (userEntity != null && userEntity.getId() != null) {
				CustomUser customUser = new CustomUser(userEntity);
				log.debug("CustomUser: {}", customUser.toString());

				return customUser;
			} else {
				log.error("User not found: {}", username);
				throw new UsernameNotFoundException("User " + username + " was not found in the database");
			}
		} catch (Exception e) {
			log.error("User not found: {}", username);
			throw new UsernameNotFoundException("User " + username + " was not found in the database");
		}

	}
}
