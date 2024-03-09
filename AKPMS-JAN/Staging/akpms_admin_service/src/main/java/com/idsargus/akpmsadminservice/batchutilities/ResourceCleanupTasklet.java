package com.idsargus.akpmsadminservice.batchutilities;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.idsargus.akpmsadminservice.entity.AdminBatchFilesMetaEntity;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.repository.dao.AbstractJdbcBatchMetadataDao;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.idsargus.akpmsadminservice.repository.BatchFilesMetaRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Tasklet used to delete data from Spring Batch Metadata tables that are N
 * months old.
 * 
 * <p>
 * The row numbers in the 6 tables of Spring Batch may increase a lot. This
 * tasklet cleans the Spring Batch database by removing old job instances
 * executions and keep the historic of recent job executions (last 6 months by
 * default).<br>
 * Spring Batch tables prefix could be customized by the
 * {@link #setTablePrefix(String)}<br>
 * Thanks to Giovanni Dalloglio for his initial SQL statements.
 * </p>
 * 
 * @see https://jira.springsource.org/browse/BATCH-1747
 * @author arey
 * 
 */
@Slf4j
public class ResourceCleanupTasklet implements Tasklet, InitializingBean {

	/**
	 * SQL statements removing step and job executions compared to a given date.
	 */
	private static final String SQL_DELETE_BATCH_STEP_EXECUTION_CONTEXT = "DELETE FROM %PREFIX%STEP_EXECUTION_CONTEXT WHERE STEP_EXECUTION_ID IN (SELECT STEP_EXECUTION_ID FROM %PREFIX%STEP_EXECUTION WHERE JOB_EXECUTION_ID IN (SELECT JOB_EXECUTION_ID FROM  %PREFIX%JOB_EXECUTION where CREATE_TIME < ?))";
	private static final String SQL_DELETE_BATCH_STEP_EXECUTION = "DELETE FROM %PREFIX%STEP_EXECUTION WHERE JOB_EXECUTION_ID IN (SELECT JOB_EXECUTION_ID FROM %PREFIX%JOB_EXECUTION where CREATE_TIME < ?)";
	private static final String SQL_DELETE_BATCH_JOB_EXECUTION_CONTEXT = "DELETE FROM %PREFIX%JOB_EXECUTION_CONTEXT WHERE JOB_EXECUTION_ID IN (SELECT JOB_EXECUTION_ID FROM  %PREFIX%JOB_EXECUTION where CREATE_TIME < ?)";
	private static final String SQL_DELETE_BATCH_JOB_EXECUTION_PARAMS = "DELETE FROM %PREFIX%JOB_EXECUTION_PARAMS WHERE JOB_EXECUTION_ID IN (SELECT JOB_EXECUTION_ID FROM %PREFIX%JOB_EXECUTION where CREATE_TIME < ?)";
	private static final String SQL_DELETE_BATCH_JOB_EXECUTION = "DELETE FROM %PREFIX%JOB_EXECUTION where CREATE_TIME < ?";
	private static final String SQL_DELETE_BATCH_JOB_INSTANCE = "DELETE FROM %PREFIX%JOB_INSTANCE WHERE JOB_INSTANCE_ID NOT IN (SELECT JOB_INSTANCE_ID FROM %PREFIX%JOB_EXECUTION)";

	/**
	 * Default value for the table prefix property.
	 */
	private static final String DEFAULT_TABLE_PREFIX = AbstractJdbcBatchMetadataDao.DEFAULT_TABLE_PREFIX;

	/**
	 * Default value for the data retention (in month)
	 */
	private static final Integer DEFAULT_RETENTION_MONTH = 0;

	private String tablePrefix = DEFAULT_TABLE_PREFIX;

	private Integer historicRetentionMonth = DEFAULT_RETENTION_MONTH;

	private JdbcTemplate jdbcTemplate;

	private BatchFilesMetaRepository batchFilesMetaRepository;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
		int totalCount = 0;
		Date date = DateUtils.addMonths(new Date(), -historicRetentionMonth);
		DateFormat df = new SimpleDateFormat();
		log.info("Remove the Spring Batch history before the {}", df.format(date));

		int rowCount = jdbcTemplate.update(getQuery(SQL_DELETE_BATCH_STEP_EXECUTION_CONTEXT), date);
		log.info("Deleted rows number from the BATCH_STEP_EXECUTION_CONTEXT table: {}", rowCount);
		totalCount += rowCount;

		rowCount = jdbcTemplate.update(getQuery(SQL_DELETE_BATCH_STEP_EXECUTION), date);
		log.info("Deleted rows number from the BATCH_STEP_EXECUTION table: {}", rowCount);
		totalCount += rowCount;

		rowCount = jdbcTemplate.update(getQuery(SQL_DELETE_BATCH_JOB_EXECUTION_CONTEXT), date);
		log.info("Deleted rows number from the BATCH_JOB_EXECUTION_CONTEXT table: {}", rowCount);
		totalCount += rowCount;

		rowCount = jdbcTemplate.update(getQuery(SQL_DELETE_BATCH_JOB_EXECUTION_PARAMS), date);
		log.info("Deleted rows number from the BATCH_JOB_EXECUTION_PARAMS table: {}", rowCount);
		totalCount += rowCount;

		rowCount = jdbcTemplate.update(getQuery(SQL_DELETE_BATCH_JOB_EXECUTION), date);
		log.info("Deleted rows number from the BATCH_JOB_EXECUTION table: {}", rowCount);
		totalCount += rowCount;

		rowCount = jdbcTemplate.update(getQuery(SQL_DELETE_BATCH_JOB_INSTANCE));
		log.info("Deleted rows number from the BATCH_JOB_INSTANCE table: {}", rowCount);
		totalCount += rowCount;

		/* Remove old files */
		List<AdminBatchFilesMetaEntity> eligibleTodelete = batchFilesMetaRepository
				.findByCreatedAtBefore(new DateTime().minusDays(1).toDate());
		log.debug("Attempting to delete previous file history...");

		if (!eligibleTodelete.isEmpty()) {
			eligibleTodelete.forEach(f -> {
				final String filepath = f.getFilepath();
			//	final Long id = f.getId();
				final Integer id = f.getId();
				final File file = new File(filepath);
				if (file.delete()) {
					log.debug("File " + f.getFilename() + " deleted successfully");
					// file deleted delete the corresponding record
					batchFilesMetaRepository.deleteById(id.intValue());
				}

			});
		} else {
			log.debug("Nothing to delete");
		}
		/**/

		contribution.incrementWriteCount(totalCount);

		return RepeatStatus.FINISHED;
	}

	protected String getQuery(String base) {
		return StringUtils.replace(base, "%PREFIX%", tablePrefix);
	}

	public void setTablePrefix(String tablePrefix) {
		this.tablePrefix = tablePrefix;
	}

	public void setHistoricRetentionMonth(Integer historicRetentionMonth) {
		this.historicRetentionMonth = historicRetentionMonth;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void setBatchFilesMetaRepository(BatchFilesMetaRepository batchFilesMetaRepository) {
		this.batchFilesMetaRepository = batchFilesMetaRepository;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(jdbcTemplate, "The jdbcTemplate must not be null");
	}

}