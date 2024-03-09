package com.idsargus.akpmsadminservice.batchutilities.arjob;

import java.time.Duration;
import java.util.Date;
import java.util.Optional;
import java.util.StringJoiner;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.metrics.BatchMetrics;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.idsargus.akpmsadminservice.batchutilities.FileBatchJobsService;
import com.idsargus.akpmsadminservice.repository.BatchFilesMetaRepository;
import com.idsargus.akpmsadminservice.wspojo.WsResponseDto;
import com.idsargus.akpmsadminservice.entity.AdminBatchFilesMetaEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@Component
public class ArStepListener implements StepExecutionListener {
	private BatchFilesMetaRepository batchFilesMetaRepository;
	private SimpMessagingTemplate simpMessagingTemplate;
	private JdbcTemplate jdbcTemplate;
	private FileBatchJobsService fileBatchJobsService;

	@Override
	public void beforeStep(StepExecution stepExecution) {
		if (stepExecution.getStepName().equals("step1Ar")) {
			JobExecution jobExecution = stepExecution.getJobExecution();
			JobParameters jobParameters = jobExecution.getJobParameters();
			AdminBatchFilesMetaEntity entity = new AdminBatchFilesMetaEntity();
			entity.setCreatedAt(new Date());
			entity.setExporter(jobParameters.getString("exporter"));
			entity.setFilename(jobParameters.getString("filehandle"));
			entity.setFilepath(jobParameters.getString("abspathtofile"));
			entity.setParams(jobParameters.getString("params"));
			entity.setStatus(stepExecution.getStatus().name());

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
			AdminBatchFilesMetaEntity adminBatchFilesMetaEntity = this.batchFilesMetaRepository.save(entity);
			jobExecution.getExecutionContext().putLong("file_id", adminBatchFilesMetaEntity.getId());
			this.simpMessagingTemplate.convertAndSend("/queue/arbatch",
					this.getWsResponseDtoScheduled(jobParameters, stepExecution.getStatus().name()));
			log.debug("Job successfully created");
		}
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		if (stepExecution.getStepName().equals("step1Ar")) {
			JobExecution jobExecution = stepExecution.getJobExecution();

			switch (stepExecution.getStatus()) {
			case COMPLETED:
				this.prepareCompletedResponse(jobExecution, stepExecution);
				break;
			default:
				this.prepareFailedResponse(jobExecution, stepExecution);
				break;
			}
		}
		return stepExecution.getExitStatus();
	}

	private void prepareCompletedResponse(JobExecution jobExecution, StepExecution stepExecution) {
		JobParameters jobParameters = jobExecution.getJobParameters();

		Optional<AdminBatchFilesMetaEntity> entity = this.batchFilesMetaRepository
				.findById(jobExecution.getExecutionContext().getInt("file_id"));
		if (entity.isPresent()) {
			final String baseUrl = "/v1/adminapi/ar/download_report?exporter=ARSERVICE&filename="
					+ jobParameters.getString("filehandle");
			AdminBatchFilesMetaEntity adminBatchFilesMetaEntity = entity.get();
			adminBatchFilesMetaEntity.setStatus(stepExecution.getStatus().name());

			Duration stepExecutionDuration = BatchMetrics.calculateDuration(stepExecution.getStartTime(), new Date());
			adminBatchFilesMetaEntity.setTimetaken(BatchMetrics.formatDuration(stepExecutionDuration));

			this.batchFilesMetaRepository.save(adminBatchFilesMetaEntity);
			WsResponseDto wsResponseDto = this.getWsResponseDtoCompleted(jobParameters,
					jobExecution.getStatus().name());
			wsResponseDto.setProgress(100f);
			wsResponseDto.setMessage("file successfully processed");
			wsResponseDto.setFilepath(baseUrl);
			wsResponseDto.setStatus(stepExecution.getStatus().name());

			wsResponseDto.setTimeTaken(BatchMetrics.formatDuration(stepExecutionDuration));

			this.simpMessagingTemplate.convertAndSend(wsResponseDto.getWsfilehandle(), wsResponseDto);
		}

	}

	private void prepareFailedResponse(JobExecution jobExecution, StepExecution stepExecution) {
		JobParameters jobParameters = jobExecution.getJobParameters();

		Optional<AdminBatchFilesMetaEntity> entity = this.batchFilesMetaRepository
				.findById(jobExecution.getExecutionContext().getInt("file_id"));
		if (entity.isPresent()) {

			AdminBatchFilesMetaEntity adminBatchFilesMetaEntity = entity.get();
			adminBatchFilesMetaEntity.setStatus(stepExecution.getStatus().name());

			Duration stepExecutionDuration = BatchMetrics.calculateDuration(stepExecution.getStartTime(), new Date());
			adminBatchFilesMetaEntity.setTimetaken(BatchMetrics.formatDuration(stepExecutionDuration));

			this.batchFilesMetaRepository.save(adminBatchFilesMetaEntity);
			WsResponseDto wsResponseDto = this.getWsResponseDtoCompleted(jobParameters,
					jobExecution.getStatus().name());
			wsResponseDto.setProgress(0f);
			wsResponseDto.setMessage("Sorry something went wrong, unable to process file.");
			wsResponseDto.setStatus(stepExecution.getStatus().name());

			wsResponseDto.setTimeTaken(BatchMetrics.formatDuration(stepExecutionDuration));

			this.simpMessagingTemplate.convertAndSend(wsResponseDto.getWsfilehandle(), wsResponseDto);
		}

	}

	private WsResponseDto getWsResponseDtoScheduled(JobParameters jobParameters, String batchStatus) {
		WsResponseDto wsResponseDto = new WsResponseDto();

		wsResponseDto.setExporter(jobParameters.getString("exporter"));
		wsResponseDto.setWsfilehandle("/queue/arbatch/" + jobParameters.getString("filehandle"));
		wsResponseDto.setFilepath(null);
		wsResponseDto.setQueryParams(jobParameters.getString("params"));
		wsResponseDto.setTimeTaken(null);
		wsResponseDto.setProgress(0.0f);
		wsResponseDto.setStatus(batchStatus);
		wsResponseDto.setMessage("File successfully scheduled for processing....");
		return wsResponseDto;

	}

	private WsResponseDto getWsResponseDtoCompleted(JobParameters jobParameters, String batchStatus) {
		WsResponseDto wsResponseDto = new WsResponseDto();
		wsResponseDto.setExporter(jobParameters.getString("exporter"));
		wsResponseDto.setWsfilehandle("/queue/arbatch/" + jobParameters.getString("filehandle"));
		wsResponseDto.setQueryParams(jobParameters.getString("params"));

		return wsResponseDto;

	}

}