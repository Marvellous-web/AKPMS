package com.idsargus.akpmscommonservice.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "batch_files_meta")
@Getter
@Setter
public class BatchFilesMetaEntity extends BaseIdEntity {

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