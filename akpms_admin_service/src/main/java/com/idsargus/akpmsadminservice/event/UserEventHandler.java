package com.idsargus.akpmsadminservice.event;

import java.util.Optional;

import com.idsargus.akpmsadminservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.idsargus.akpmsadminservice.repository.UserDataRestRepository;

@Component
@RepositoryEventHandler(User.class)
public class UserEventHandler {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserDataRestRepository userDataRestRepository;

	private final String PWD = "password";

	private final String RESET = "reset";

	@HandleBeforeCreate
	public void handleUserCreate(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
	}

//	@HandleBeforeSave
//	public void handleUserUpdate(UserEntity user) {
//		if (user.getPassword() == null || user.getPassword().equals("")) {
//			// keeps the last password
//			Optional<User> optUser = userDataRestRepository.findById(user.getId().intValue());
//
//			if (optUser.isPresent()) {
//				User storedUser = optUser.get();
//				user.setPassword(storedUser.getPassword());
//			}
//		} else if (user.getPassword().equalsIgnoreCase(RESET)) {
//			user.setPassword(passwordEncoder.encode(PWD));
//		} else {
//			// password change request
//			user.setPassword(passwordEncoder.encode(user.getPassword()));
//		}
//	}

}
