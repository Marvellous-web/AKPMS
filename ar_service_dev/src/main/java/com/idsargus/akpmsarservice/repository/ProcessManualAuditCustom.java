package com.idsargus.akpmsarservice.repository;

import com.idsargus.akpmsarservice.model.domain.ArProcessManualAudit;
import com.idsargus.akpmsarservice.model.domain.CodingCorrectionLogWorkFlow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProcessManualAuditCustom extends JpaRepository<ArProcessManualAudit,Integer> {

    @Query(value= "select i from ArProcessManualAudit i where " +
            "(:processManualId is null or i.processManualId = :processManualId) ")
            public List<ArProcessManualAudit> findByProcessManualId(Long processManualId);

}
