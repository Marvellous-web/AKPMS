package com.idsargus.akpmsadminservice.batchutilities.arjob;

import java.io.IOException;
import java.io.Writer;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import com.idsargus.akpmsadminservice.batchutilities.FileBatchJobsService;
import com.idsargus.akpmsadminservice.batchutilities.ResourceCleanupTasklet;
import com.idsargus.akpmsadminservice.repository.BatchFilesMetaRepository;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableBatchProcessing
@Slf4j
public class BatchConfigurationArSearch {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Autowired
	private BatchFilesMetaRepository batchFilesMetaRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private FileBatchJobsService fileBatchJobsService;

	/* Reader for Ar */
	@Bean
	@StepScope
	public ItemReader<ArSearchFieldsMapper> readerAr(@Value("#{jobParameters[select_clause]}") String selectClause,
			@Value("#{jobParameters[from_clause]}") String fromClause,
			@Value("#{jobParameters[where_clause]}") String whereClause, @Value("#{jobParameters[limit]}") long limit,
			@Value("#{jobParameters[sort_key]}") String sortkey) {

		JdbcPagingItemReader<ArSearchFieldsMapper> reader = new JdbcPagingItemReader<ArSearchFieldsMapper>();
		final SqlPagingQueryProviderFactoryBean sqlPagingQueryProviderFactoryBean = new SqlPagingQueryProviderFactoryBean();

		sqlPagingQueryProviderFactoryBean.setDataSource(dataSource);
		sqlPagingQueryProviderFactoryBean.setSelectClause(selectClause);
		if (fromClause != null)
			sqlPagingQueryProviderFactoryBean.setFromClause(fromClause);
		if (whereClause != null)
			sqlPagingQueryProviderFactoryBean.setWhereClause(whereClause);

		if (limit != 0)
			reader.setMaxItemCount((int) limit);

		sqlPagingQueryProviderFactoryBean.setSortKey(sortkey);
		try {
			reader.setQueryProvider(sqlPagingQueryProviderFactoryBean.getObject());
		} catch (Exception e) {
			e.printStackTrace();
		}
		reader.setDataSource(dataSource);
		reader.setPageSize(1000);
		reader.setRowMapper(new BeanPropertyRowMapper<ArSearchFieldsMapper>(ArSearchFieldsMapper.class));
		return reader;
	}

	/* Writer for AR */
	@Bean
	@StepScope
	public FlatFileItemWriter<ArSearchFieldsMapper> writerAr(
			@Value("#{jobParameters[abspathtofile]}") String AbspathToFile) {

		FlatFileItemWriter<ArSearchFieldsMapper> writer = new FlatFileItemWriter<ArSearchFieldsMapper>();
		Resource outputResource = new FileSystemResource(AbspathToFile);
		writer.setResource(outputResource);
		writer.setHeaderCallback(new FlatFileHeaderCallback() {

			@Override
			public void writeHeader(Writer writer) throws IOException {
				writer.write("Id,Patient Name,Remark,Status Code,Balance,CPT,DOS,Followup Date,Patient Account Number");

			}
		});

		writer.setLineAggregator(new DelimitedLineAggregator<ArSearchFieldsMapper>() {
			{
				setDelimiter(",");
				setFieldExtractor(new BeanWrapperFieldExtractor<ArSearchFieldsMapper>() {
					{
						setNames(new String[] { "arid", "patientname", "remark", "source", "statuscode", "balance",
								"cpt", "dos", "followupdate", "patientaccountnumber" });
					}
				});
			}
		});

		return writer;
	}

	/* Step for AR */
	@Bean

	public Step step1Ar() {
		return stepBuilderFactory.get("step1Ar").listener(arStepListener())
				.<ArSearchFieldsMapper, ArSearchFieldsMapper>chunk(1000).reader(readerAr(null, null, null, 0, null))
				.writer(writerAr(null)).listener(arItemCountListener()).build();
	}

	@Bean

	public Step step2Cleanup() {
		return stepBuilderFactory.get("step2Cleanup").tasklet(resourceCleanupTasklet()).build();
	}

	/* Job instance AR service */
	@Bean
	public Job arListExportJob(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {

		return jobBuilderFactory.get("exportArSearchtoExcel").preventRestart().incrementer(new RunIdIncrementer())
				.flow(step1Ar()).next(step2Cleanup()).end().build();
	}

	ArJobListener arJobListener() {
		ArJobListener arJobListener = new ArJobListener();
		arJobListener.setBatchFilesMetaRepository(batchFilesMetaRepository);
		arJobListener.setSimpMessagingTemplate(simpMessagingTemplate);
		arJobListener.setJdbcTemplate(jdbcTemplate);
		return arJobListener;

	}

	ArStepListener arStepListener() {
		ArStepListener arStepListener = new ArStepListener();
		arStepListener.setBatchFilesMetaRepository(batchFilesMetaRepository);
		arStepListener.setSimpMessagingTemplate(simpMessagingTemplate);
		arStepListener.setJdbcTemplate(jdbcTemplate);
		arStepListener.setFileBatchJobsService(fileBatchJobsService);
		return arStepListener;

	}

	@JobScope
	@Bean
	public ArItemCountListener arItemCountListener() {
		ArItemCountListener aaArItemCountListener = new ArItemCountListener();
		aaArItemCountListener.setBatchFilesMetaRepository(batchFilesMetaRepository);
		aaArItemCountListener.setSimpMessagingTemplate(simpMessagingTemplate);
		aaArItemCountListener.setJdbcTemplate(jdbcTemplate);
		aaArItemCountListener.setFileBatchJobsService(fileBatchJobsService);
		return aaArItemCountListener;
	}

	ResourceCleanupTasklet resourceCleanupTasklet() {
		ResourceCleanupTasklet resourceCleanupTasklet = new ResourceCleanupTasklet();
		resourceCleanupTasklet.setHistoricRetentionMonth(1);
		resourceCleanupTasklet.setJdbcTemplate(jdbcTemplate);
		resourceCleanupTasklet.setTablePrefix("BATCH_");
		resourceCleanupTasklet.setBatchFilesMetaRepository(batchFilesMetaRepository);
		return resourceCleanupTasklet;
	}

}
