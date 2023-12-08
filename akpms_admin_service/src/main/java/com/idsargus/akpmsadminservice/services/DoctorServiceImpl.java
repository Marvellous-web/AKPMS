package com.idsargus.akpmsadminservice.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idsargus.akpmsadminservice.wspojo.ArSearchDto;
import com.idsargus.akpmscommonservice.entity.DoctorEntity;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
class DoctorServiceImpl {

	@Autowired
	private EntityManager entityManager;

	public List<DoctorEntity> performLookup(ArSearchDto dsdto) {
		/*
		 * CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		 * CriteriaQuery<DoctorEntity> cq = cb.createQuery(DoctorEntity.class);
		 * Root<DoctorEntity> docRoot = cq.from(DoctorEntity.class);
		 * 
		 * Predicate doctorNamePredicate = cb.like(docRoot.get("name"),
		 * "%"+dsdto.getName()+"%"); cq.where(doctorNamePredicate);
		 * TypedQuery<DoctorEntity> query = entityManager.createQuery(cq); return
		 * query.getResultList();
		 */
		return null;
	}

}
