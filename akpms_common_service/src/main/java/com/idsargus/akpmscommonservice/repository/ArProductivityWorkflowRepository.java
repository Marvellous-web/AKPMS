package com.idsargus.akpmscommonservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.idsargus.akpmscommonservice.entity.ArProductivityWorkFlowEntity;

@Repository
public interface ArProductivityWorkflowRepository extends JpaRepository<ArProductivityWorkFlowEntity, Integer> {

}
