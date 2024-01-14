package com.idsargus.akpmscommonservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.idsargus.akpmscommonservice.entity.ArDatabaseEntity;

@Repository
public interface ArDatabaseRepository extends JpaRepository<ArDatabaseEntity, Integer> {

}
