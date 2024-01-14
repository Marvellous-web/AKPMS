package com.idsargus.akpmsarservice.repository;

import com.idsargus.akpmsarservice.model.domain.ArProcessManual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArProcessManualRepository extends JpaRepository<ArProcessManual, Integer> {

    @Query(value= "select * from process_manual i where i.title = :title", nativeQuery = true)
    public List<ArProcessManual> isTitleExists(@Param("title") String title);


//    @Query(value= "select * from process_manual i where i.title = :title and i.id = id", nativeQuery = true)
//    public List<ArProcessManual> isProcessManualTitleExistById(String title, String id);
}
