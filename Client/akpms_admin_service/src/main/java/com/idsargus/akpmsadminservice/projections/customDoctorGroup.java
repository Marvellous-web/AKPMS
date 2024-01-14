package com.idsargus.akpmsadminservice.projections;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import com.idsargus.akpmscommonservice.entity.DoctorCompanyEntity;
import com.idsargus.akpmscommonservice.entity.DoctorGroupEntity;

@Projection(
		  name = "customDoctorGroup", 
		  types = { DoctorGroupEntity.class }) 
public interface customDoctorGroup {
	 @Value("#{target.id}")
	    long getId();
String getName();
boolean getEnabled();
DoctorCompanyEntity getCompany();
}
