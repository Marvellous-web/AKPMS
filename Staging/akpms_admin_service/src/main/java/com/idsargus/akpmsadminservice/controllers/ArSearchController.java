package com.idsargus.akpmsadminservice.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import com.idsargus.akpmsadminservice.entity.AdminDepartmentEntity;
import com.idsargus.akpmsadminservice.repository.DepartmentDataRestRepository;
import com.idsargus.akpmsadminservice.services.AdminCountServiceImpl;
import com.idsargus.akpmsadminservice.wspojo.DashboardItem;
import com.idsargus.akpmsadminservice.wspojo.DepartmentTreeViewResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idsargus.akpmsadminservice.batchutilities.FileBatchJobsService;
import com.idsargus.akpmsadminservice.batchutilities.JobTypes;
import com.idsargus.akpmsadminservice.repository.BatchFilesMetaRepository;
import com.idsargus.akpmsadminservice.wspojo.ArSearchDto;
import com.idsargus.akpmsadminservice.wspojo.WsResponseDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1/adminapi/ar")
public class ArSearchController {

	@Autowired
	FileBatchJobsService filebatchjobs;

	@Autowired
	public AdminCountServiceImpl adminCountService;
	@Autowired
	BatchFilesMetaRepository batchFilesMetaRepository;

	@Autowired
	SimpMessagingTemplate simpMessagingTemplate;

	@Autowired
	private DepartmentDataRestRepository departmentDataRestRepository;
	@MessageMapping("/arbatch")
	public void searchForAr(@RequestBody ArSearchDto arsdto) {

		WsResponseDto wsResponseDto = filebatchjobs.initJob(JobTypes.ARSERVICE, arsdto);

	}

	@GetMapping("/download_report")
	public ResponseEntity<Resource> download(String filename, String exporter) throws IOException {
		File file = filebatchjobs.getFilefromName(filename, exporter);
		Path path = Paths.get(file.getAbsolutePath());
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

		return ResponseEntity.ok().contentLength(file.length()).contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(resource);
	}

	@GetMapping("/departmentstreeview")
	public ResponseEntity<?>  departmentsTreeView(){
		List<AdminDepartmentEntity> adminDepartmentEntityList = departmentDataRestRepository.findAll();

		Map<Integer, List<AdminDepartmentEntity>> deptList = adminDepartmentEntityList.stream().filter(dept->dept.getParentId()!=null)
				.filter(deptId->deptId.getId()!=null)
				.collect(Collectors.groupingBy(AdminDepartmentEntity::getParentId, Collectors.toList()));

		List<DepartmentTreeViewResponse> treeViewResponseList = new CopyOnWriteArrayList<>();

		for(Map.Entry<Integer, List<AdminDepartmentEntity>> entry : deptList.entrySet()){
			DepartmentTreeViewResponse departmentTreeViewResponse = new DepartmentTreeViewResponse();
			departmentTreeViewResponse.setId(entry.getKey());
			departmentTreeViewResponse.setListDepartment(entry.getValue());

			if(entry.getKey().equals(1)){
               departmentTreeViewResponse.setName("Coding and Charge Entry Department");
			}
			if(entry.getKey().equals(2)){
				departmentTreeViewResponse.setName("Payments Department");
			}

			if(entry.getKey().equals(3)){
				departmentTreeViewResponse.setName("Accounts Receivable Department");
			}

			if(entry.getKey().equals(4)){
				departmentTreeViewResponse.setName("Accounting Department");
			}

			treeViewResponseList.add(departmentTreeViewResponse);

		}

		List<AdminDepartmentEntity> result = adminDepartmentEntityList.stream()
				.filter(s->s.getParentId()==null && treeViewResponseList.stream().noneMatch(d->d.getId().equals(s.getId())))
				.collect(Collectors.toList());

		for(AdminDepartmentEntity adminDepartmentEntity : result){
			DepartmentTreeViewResponse departmentTreeViewResponse1 = new DepartmentTreeViewResponse();
			departmentTreeViewResponse1.setId(adminDepartmentEntity.getId());
			departmentTreeViewResponse1.setListDepartment(null);
			departmentTreeViewResponse1.setName(adminDepartmentEntity.getName());
			treeViewResponseList.add(departmentTreeViewResponse1);
		}
		return new ResponseEntity<>(treeViewResponseList, HttpStatus.OK);
	}

	@GetMapping("/dashboard/count")
	public List<DashboardItem> getAdminDashboardCount(){
		return adminCountService.getDashboardCountList();
	}

}