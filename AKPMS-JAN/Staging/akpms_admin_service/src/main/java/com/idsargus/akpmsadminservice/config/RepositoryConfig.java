package com.idsargus.akpmsadminservice.config;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import com.idsargus.akpmsadminservice.projections.customDoctorGroup;

@Configuration
public class RepositoryConfig extends RepositoryRestConfigurerAdapter {

	@Autowired
	private EntityManager entityManager;

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
//		config.exposeIdsFor(AdminInsuranceEntity.class, UserEntity.class, DoctorEntity.class);
		
		// to expose id for all entities.
		config.exposeIdsFor(
				entityManager.getMetamodel().getEntities().stream().map(Type::getJavaType).toArray(Class[]::new));
		config.getProjectionConfiguration().addProjection(customDoctorGroup.class);
	}
}
