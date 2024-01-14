package com.idsargus.akpmscommonservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.idsargus.akpmscommonservice.entity.InsuranceEntity;

@Repository
public interface InsuranceRepository extends JpaRepository<InsuranceEntity, Integer> {

}
