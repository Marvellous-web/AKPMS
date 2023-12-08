package com.idsargus.akpmsarservice.projections;

import java.util.List;

import com.idsargus.akpmscommonservice.entity.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import com.idsargus.akpmsarservice.model.domain.Doctor;
 
@Projection(
		  name = "arProj", 
		  types = { ArProductivityEntity.class }) 
public interface ArProj {
	 @Value("#{target.id}")
	    long getId();
//String getName();
//String getDoctorCode();
//boolean getNonDeposit();
//float getPayments();
//float getAccounting();
//float getOperations();
//Doctor getParent();
//boolean getStatus();

boolean getEnabled();
Integer getSubStatus();
InsuranceEntity getInsurance();
DoctorEntity getDoctor();
DepartmentEntity getDepartmentEntity();

}
