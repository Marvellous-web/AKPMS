package com.idsargus.akpmscommonservice.repository;

import com.idsargus.akpmscommonservice.entity.InsuranceEntity;
import com.idsargus.akpmscommonservice.entity.QueryToTL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface QueryToTLReposiotry extends JpaRepository<QueryToTL, Integer> {

}
