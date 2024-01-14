package com.idsargus.akpmsadminservice.batchutilities.arjob;

import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.idsargus.akpmsadminservice.batchutilities.FileBatchJobsService;
import com.idsargus.akpmsadminservice.repository.BatchFilesMetaRepository;
import com.idsargus.akpmsadminservice.wspojo.WsResponseDto;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class ArItemCountListener implements ChunkListener {

	private BatchFilesMetaRepository batchFilesMetaRepository;
	private SimpMessagingTemplate simpMessagingTemplate;
	private JdbcTemplate jdbcTemplate;
	private FileBatchJobsService fileBatchJobsService;

	@Value("#{jobExecution}")
	private JobExecution jobExecution;

	@Override
	public void beforeChunk(ChunkContext context) {
	}

	@Override
	public void afterChunk(ChunkContext context) {
		if (context.getStepContext().getStepExecution().getStepName().equals("step1Ar")) {
			ExecutionContext executionContext = jobExecution.getExecutionContext();
			float count = context.getStepContext().getStepExecution().getReadCount();
			int numRecords = executionContext.getInt("num_records");
			String filehandle = executionContext.getString("file_name");
			float processed_percent = ((count / numRecords) * 100);

			WsResponseDto wsResponseDto = this.getWsResponseDto(jobExecution.getJobParameters(),
					jobExecution.getStatus().name(), processed_percent);
			this.simpMessagingTemplate.convertAndSend(wsResponseDto.getWsfilehandle(), wsResponseDto);
			log.debug("processed record " + processed_percent + ", and wired over websocket "
					+ wsResponseDto.getWsfilehandle());
		}
	}

	@Override
	public void afterChunkError(ChunkContext context) {
	}

	public WsResponseDto getWsResponseDto(JobParameters jobParameters, String batchStatus, float progress) {
		WsResponseDto wsResponseDto = new WsResponseDto();

		wsResponseDto.setExporter(jobParameters.getString("exporter"));
		wsResponseDto.setWsfilehandle("/queue/arbatch/" + jobParameters.getString("filehandle"));
		wsResponseDto.setFilepath(null);
		wsResponseDto.setQueryParams(jobParameters.getString("params"));
		wsResponseDto.setTimeTaken(null);
		wsResponseDto.setProgress(progress);
		wsResponseDto.setStatus(batchStatus);
		wsResponseDto.setMessage("Processing");
		return wsResponseDto;

	}
}