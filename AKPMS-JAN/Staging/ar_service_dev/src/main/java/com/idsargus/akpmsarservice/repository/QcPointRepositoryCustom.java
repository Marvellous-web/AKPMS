package com.idsargus.akpmsarservice.repository;

import com.idsargus.akpmsarservice.model.domain.QCPointChecklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QcPointRepositoryCustom extends JpaRepository<QCPointChecklist, Integer> {
}
