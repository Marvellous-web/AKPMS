package com.idsargus.akpmsarservice.config;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
//import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

import com.idsargus.akpmsarservice.projections.ArProj;

@Configuration
public class RepositoryConfig implements RepositoryRestConfigurer   { //RepositoryRestConfigurerAdapter replaced by RepositoryRestConfigurer

	@Autowired
	private EntityManager entityManager;

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
//		config.exposeIdsFor(InsuranceEntity.class, UserEntity.class, DoctorEntity.class);

		// to expose id for all entities.
		config.exposeIdsFor(
				entityManager.getMetamodel().getEntities().stream().map(Type::getJavaType).toArray(Class[]::new));
		config.getProjectionConfiguration().addProjection(ArProj.class);
	}
}
