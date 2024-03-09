package com.idsargus.akpmsadminservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
@EnableJpaRepositories(basePackages = { "com.idsargus.akpmsadminservice.repository" })
@EntityScan({"com.idsargus.akpmscommonservice.entity", "com.idsargus.akpmsadminservice.entity"})
public class AkpmsAdminServiceApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {

		 SpringApplication.run(AkpmsAdminServiceApplication.class, args);

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

}
