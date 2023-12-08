package com.idsargus.akpmsadminservice.services;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.idsargus.akpmsadminservice.wspojo.ArSearchDto;
import com.idsargus.akpmscommonservice.entity.DoctorEntity;

public interface DoctorService {


	List<DoctorEntity> performLookup(ArSearchDto dsdto);


}
