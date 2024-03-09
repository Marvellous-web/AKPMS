package com.idsargus.akpmscommonservice.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.idsargus.akpmscommonservice.entity.DoctorEntity;

public class DoctorSpecification implements Specification<DoctorEntity>{

	@Override
	public Predicate toPredicate(Root<DoctorEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
	return criteriaBuilder.equal(root.get("enabled"), 1);
	}

}
