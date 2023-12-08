package com.idsargus.akpmsadminservice.batchutilities.arjob;

import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameters;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.idsargus.akpmsadminservice.repository.BatchFilesMetaRepository;
import com.idsargus.akpmsadminservice.entity.AdminBatchFilesMetaEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@Component
public class ArJobListener implements JobExecutionListener, ItemWriteListener<ArSearchFieldsMapper> {

	private BatchFilesMetaRepository batchFilesMetaRepository;
	private SimpMessagingTemplate simpMessagingTemplate;
	private JdbcTemplate jdbcTemplate;

	@Override
	public void beforeJob(JobExecution jobExecution) {

		JobParameters jobParameters = jobExecution.getJobParameters();
		AdminBatchFilesMetaEntity entity = new AdminBatchFilesMetaEntity();
		entity.setCreatedAt(new Date());
		entity.setExporter(jobParameters.getString("exporter"));
		entity.setFilename(jobParameters.getString("filehandle"));
		entity.setFilepath(jobParameters.getString("abspathtofile"));
		entity.setParams(jobParameters.getString("params"));
		entity.setStatus(jobExecution.getStatus().name());

		String fromClause = jobParameters.getString("from_clause");
		String whereClause = jobParameters.getString("where_clause");
		String limit = jobParameters.getString("limit");

		StringJoiner finalQuery = new StringJoiner(" ");
		finalQuery.add("SELECT COUNT(*)");
		finalQuery.add(fromClause);

		if (whereClause != null)
			finalQuery.add(whereClause);

		finalQuery.add("LIMIT");
		finalQuery.add(limit);

		int num_records = jdbcTemplate.queryForObject(finalQuery.toString(), Integer.class);
		int rec_limit = Integer.parseInt(limit);
		if (num_records >= rec_limit)
			num_records = rec_limit;

		entity.setNum_records(num_records);
		jobExecution.getExecutionContext().putInt("num_records", num_records);
		jobExecution.getExecutionContext().putString("file_name", jobParameters.getString("filehandle"));
		this.batchFilesMetaRepository.save(entity);

		// WsRequestDto wsResponseDto =
		// this.simpMessagingTemplate.convertAndSend("/queue/arbatch", wsResponseDto);
		log.debug("Job successfully created");

	}

	@Override
	public void afterJob(JobExecution jobExecution) {

		switch (jobExecution.getStatus()) {
		case COMPLETED:
			log.debug("Job successfully completed" + jobExecution.getEndTime());
			break;
		case FAILED:
			log.debug("Job failed.................");
			break;
		default:
			log.debug("Unknown error occured , please contact site administsrator.................");
			break;
		}

	}

	@Override
	public void beforeWrite(List<? extends ArSearchFieldsMapper> items) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterWrite(List<? extends ArSearchFieldsMapper> items) {
		// log.debug(this.getJe().toString());

	}

	@Override
	public void onWriteError(Exception exception, List<? extends ArSearchFieldsMapper> items) {
		// TODO Auto-generated method stub

	}

}