package com.idsargus.akpmsadminservice.entity;

import com.idsargus.akpmscommonservice.entity.BaseIdEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "batch_files_meta")
@Getter
@Setter
public class AdminBatchFilesMetaEntity extends BaseIdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull
	private String filename;

	@NotNull
	private String filepath;

	@NotNull
	private String params;

	@NotNull
	private String exporter;

	@NotNull
	@Column(name = "created_at")
	private Date createdAt;

	@NotNull
	private String status;

	@NotNull
	private int num_records;

	private String timetaken;
}