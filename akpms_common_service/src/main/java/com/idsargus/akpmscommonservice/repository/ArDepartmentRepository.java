package com.idsargus.akpmscommonservice.repository;

import com.idsargus.akpmscommonservice.entity.ArDatabaseEntity;
import com.idsargus.akpmscommonservice.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArDepartmentRepository extends JpaRepository<DepartmentEntity, Integer> {

}
