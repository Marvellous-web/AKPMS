package com.idsargus.akpmsarservice;

import com.idsargus.akpmsarservice.repository.ArProductivityRepository;
//import com.idsargus.akpmsarservice.validation.ArProductivityValidator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
@EnableJpaRepositories(basePackages = { "com.idsargus.akpmsarservice.repository",
		"com.idsargus.akpmscommonservice.repository" })
@EntityScan(basePackages = {"com.idsargus.akpmscommonservice.entity", "com.idsargus.akpmsarservice.model.domain"})
@ComponentScan("com.idsargus.akpmsarservice")
@EnableAutoConfiguration(exclude = {
		SecurityAutoConfiguration.class
})
public class AkpmsArServiceApplication implements RepositoryRestConfigurer {


	public static void main(String[] args) {
		SpringApplication.run(AkpmsArServiceApplication.class, args);
		System.out.println("Hello world");
	}

//	@Bean
//	public ArProductivityValidator beforeCreateArProductivityValidator() {
//		return new ArProductivityValidator();
//	}

//commented today	
//	@Override
//	public void configureValidatingRepositoryEventListener(
//			ValidatingRepositoryEventListener v) {
//		v.addValidator("beforeSave", new ArProductivityValidator());
//	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
