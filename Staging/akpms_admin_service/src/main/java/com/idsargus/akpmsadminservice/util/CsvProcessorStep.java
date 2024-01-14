package com.idsargus.akpmsadminservice.util;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class CsvProcessorStep implements Tasklet {

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		log.debug("Task execution started****************************************8888888888888888888");
		return RepeatStatus.FINISHED;
	}

}
