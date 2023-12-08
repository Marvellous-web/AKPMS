package com.idsargus.akpmsadminservice.repository;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import com.idsargus.akpmsadminservice.entity.AdminBatchFilesMetaEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BatchFilesMetaRepository extends CrudRepository<AdminBatchFilesMetaEntity, Integer> {
	<S extends AdminBatchFilesMetaEntity> S save(S batchFilesMetaEntity);

	@Modifying
	public List<AdminBatchFilesMetaEntity> findByCreatedAtBefore(Date expiryDate);

	@Query("SELECT b FROM AdminBatchFilesMetaEntity b")
	Stream<AdminBatchFilesMetaEntity> getAll();
}
