package com.idsargus.akpmsadminservice.batchutilities;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Service;

import com.idsargus.akpmsadminservice.repository.BatchFilesMetaRepository;
import com.idsargus.akpmsadminservice.wspojo.ArSearchDto;
import com.idsargus.akpmsadminservice.wspojo.WsRequestDto;
import com.idsargus.akpmsadminservice.wspojo.WsResponseDto;

@Service
public class FileBatchJobsService {
	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	@Qualifier("batchTaskExecutor")
	ThreadPoolTaskExecutor executer;

	@Autowired
	@Qualifier("arListExportJob")
	Job jobar;

	@Autowired
	BatchFilesMetaRepository batchFilesMetaRepository;

	// init a job
	public <T extends WsRequestDto> WsResponseDto initJob(JobTypes jobTypes, T wsrdto) {
		// set filename
		this.createFileName(wsrdto);

		WsResponseDto srdto = new WsResponseDto();
		switch (jobTypes) {
		case ARSERVICE:

			srdto.setWsfilehandle(wsrdto.getFilename());
			srdto.setExporter(JobTypes.ARSERVICE.name());
			srdto.setMessage("File successfully shceduled for processing.");
			this.startFileJob(jobTypes, wsrdto);
			break;

		default:
			break;
		}
		return srdto;
	}

	// start batch job
	private <T extends WsRequestDto> void startFileJob(JobTypes jobtype, T wsrdto) {

		executer.execute(new Runnable() {

			JobParameters jobparameters = getJobParameters(jobtype, wsrdto);

			@Override
			public void run() {
				switch (jobtype) {
				case ARSERVICE:
					try {
						jobLauncher.run(jobar, jobparameters);
					} catch (JobExecutionAlreadyRunningException | JobRestartException
							| JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;

				default:
					break;
				}

			}
		});

	}

	// list all running jobs
	public List<Job> getAllFileJobs() {
		// TODO Auto-generated method stub
		return null;
	}

	// prepare job parameters
	private <T extends WsRequestDto> JobParameters getJobParameters(JobTypes jobTypes, T wsrdto) {
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();

		switch (jobTypes) {
		case ARSERVICE:
			jobParametersBuilder.addDate("date", new Date());
			jobParametersBuilder.addString("select_clause",
					"SELECT ap.id AS \"ap.id\" , ap.id AS arid,  ap.patient_name AS patientname,  ap.remark AS remark, ap.source AS source , ap.status_code AS statuscode, ap.balance_amount AS balance, ap.cpt AS cpt, ap.dos AS dos, ap.follow_up_date AS followupdate, ap.patient_account_number AS patientaccountnumber");
			jobParametersBuilder.addString("from_clause",
					"FROM akpmsdb.ar_productivity ap INNER JOIN insurance i on ap.insurance_id = i.id INNER JOIN ar_database ad on ap.ar_database_id = ad.id INNER JOIN user u on ap.created_by = u.email INNER JOIN department d on ap.team_id= d.id");
			jobParametersBuilder.addString("where_clause",
					"WHERE ap.status_code = 'BCP' and ap.source = 'Email' and ap.created_by = 'arshad.i@idsil.com' and ap.team_id= 16 ");
			jobParametersBuilder.addLong("limit", 400000L);
			jobParametersBuilder.addString("sort_key", "ap.id");
			jobParametersBuilder.addString("abspathtofile", "output/ar/" + wsrdto.getFilename() + ".csv");
			jobParametersBuilder.addString("exporter", jobTypes.ARSERVICE.name());
			jobParametersBuilder.addString("filehandle", wsrdto.getFilename());
			jobParametersBuilder.addString("params", wsrdto.toString());

			break;

		default:
			break;
		}
		return jobParametersBuilder.toJobParameters();

	}

	// create file handle
	private <T extends WsRequestDto> void createFileName(T wsrdto) {
		String key = wsrdto.toString() + System.currentTimeMillis();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(key.getBytes());
			byte[] digest = md.digest();
			String fileNameHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
			wsrdto.setFilename(fileNameHash);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public File getFilefromName(String filename, String exporter) {
		File file = null;
		switch (exporter) {
		case "ARSERVICE":
			file = new File("./output/ar/" + filename + ".csv");
			break;

		default:
			break;
		}
		return file;
	}

}
