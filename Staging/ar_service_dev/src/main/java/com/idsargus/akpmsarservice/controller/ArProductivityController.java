package com.idsargus.akpmsarservice.controller;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.idsargus.akpmsarservice.dto.PatientAccountNumberResponse;
import com.idsargus.akpmsarservice.dto.ReasonToRejectResponse;
import com.idsargus.akpmsarservice.model.domain.ArChargeProdReject;
import com.idsargus.akpmsarservice.model.domain.ArProductivity;
import com.idsargus.akpmsarservice.repository.ChargeProdRejectionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.google.common.net.HttpHeaders;
import com.idsargus.akpmsarservice.repository.ArProductivityRepository;
import com.idsargus.akpmsarservice.repository.ProductivityRepository;
import com.idsargus.akpmsarservice.service.ArProductivityServiceImpl;
import com.idsargus.akpmscommonservice.entity.ArProductivityEntity;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("v1/arapi/arProductivity")
//@RequestMapping("v1/arapi/arproductivities")
@Slf4j
public class ArProductivityController {

    @Autowired
    private ProductivityRepository productivityRepository;


    @Autowired
    private ChargeProdRejectionRepository chargeProdRejectionRepository;

    @Autowired
    private ArProductivityServiceImpl arProdservice;

    @Autowired
    private ArProductivityRepository arProductivityRepository;

    @PostMapping(path = {"/save"})
    public ResponseEntity<ArProductivityEntity> save(@RequestBody ArProductivityEntity arProductivityEntity) {

        ArProductivityEntity result = (ArProductivityEntity) this.productivityRepository.save(arProductivityEntity);
        return new ResponseEntity(result, HttpStatus.CREATED);
    }

    @GetMapping(path = "/dashboard/count")
    public ResponseEntity<List> getDashboardCount() {
        return new ResponseEntity<List>(arProdservice.getDashboardCountList(), HttpStatus.OK);
    }

    // ARN for excel file download...
    @GetMapping("/excel_download")
    public ResponseEntity<Resource> excelDownload() {
        String ouputFilename = "ExcelFileDowload.csv";
        InputStreamResource dowloadFile = new InputStreamResource(arProdservice.getExcelFileAsStream());

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + ouputFilename)
                .contentType(MediaType.parseMediaType("application/csv")).body(dowloadFile);
    }

    // excel on whole data
//	@GetMapping("/download_in_excel")
//	public ResponseEntity<Resource> download(){
//		
//		String fileName = "testProductivity.xlsx";
//		InputStreamResource file = new InputStreamResource(arProdservice.load());
//		
//		 return ResponseEntity.ok()
//			        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
//			        .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
//			        .body(file);
//	}

    // load all records for Ar productivity
    @GetMapping("/download_in_excel")
    public ResponseEntity<?> download() {
        Pageable pageable = PageRequest.of(0, 5);
        return ResponseEntity.ok().body(arProductivityRepository.findAll());

    }
//	@Autowired
//	private ArProductivityService service;
//
//	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
//	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<?> post(@RequestBody ArProductivityRequestDto requestDto) {
//		log.debug("In post. RequestDto {}", requestDto.toString());
//
//		ArProductivityEntity entity = new ArProductivityEntity();
//		BeanUtils.copyProperties(requestDto, entity);
//
//		try {
//			Map<String, String> errors = service.validation(entity);
//			if (!errors.isEmpty()) {
//				return new ResponseEntity<Map<String, String>>(errors, HttpStatus.BAD_REQUEST);
//			}
//
//			entity = service.enrichEntity(requestDto, entity);
//
//			if (entity.getTimilyFiling() == null) {
//				entity.setTimilyFiling(false);
//			}
//			entity = service.add(entity);
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			new ResponseEntity<AppException>(new AppException(e.getMessage(), e.getCause()),
//					HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//
//		return new ResponseEntity<ArProductivityResponseDto>(populateResponseDto(entity, false), HttpStatus.CREATED);
//	}
//
//	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
//	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<?> put(@RequestBody ArProductivityRequestDto requestDto,
//			@PathVariable(value = "id") Integer id) {
//		log.debug("In put. RequestDto {}, Id {}", requestDto.toString(), id);
//
//		ArProductivityEntity entity = service.getById(id);
//		BeanUtils.copyProperties(requestDto, entity, "password", "email");
//
//		try {
//			Map<String, String> errors = service.validation(entity);
//			if (!errors.isEmpty()) {
//				return new ResponseEntity<Map<String, String>>(errors, HttpStatus.BAD_REQUEST);
//			}
//
//			entity = service.enrichEntity(requestDto, entity);
//
//			entity = service.update(entity);
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			new ResponseEntity<AppException>(new AppException(e.getMessage(), e.getCause()),
//					HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//
//		log.debug("Out put.");
//		return new ResponseEntity<ArProductivityResponseDto>(populateResponseDto(entity, false), HttpStatus.OK);
//	}
//
//	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
//	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = { "orderBy", "direction", "page", "size" })
//	public ResponseEntity<?> getAll(@RequestParam(value = "orderBy", required = false) String orderBy,
//			@RequestParam(value = "direction", required = false) String direction,
//			@RequestParam(value = "page", required = false) Integer page,
//			@RequestParam(value = "size", required = false) int size, @RequestBody(required = false) String filter) {
//
//		log.debug("In getAll.");
//
//		List<SearchCriteria> searchCriterias = null;
//		try {
//			searchCriterias = QueryBuilder.prepareSearchCriteria(filter);
//		} catch (IOException e1) {
//			log.error(e1.getMessage());
//		}
//
//		List<ArProductivityEntity> entities = new ArrayList<>();
//		Long total = 0L;
//
//		try {
//			total = service.count(searchCriterias);
//			if (total > 0) {
//				entities = service.list(orderBy, direction, page, size, searchCriterias);
//			}
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			new ResponseEntity<AppException>(new AppException(e.getMessage(), e.getCause()),
//					HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//
//		List<ArProductivityResponseDto> responseDtos = new ArrayList<>();
//
//		for (ArProductivityEntity entity : entities) {
//			responseDtos.add(populateResponseDto(entity, true));
//		}
//
//		log.debug("Out getAll. size {}", responseDtos.size());
//		return new ResponseEntity<PagingResult<ArProductivityResponseDto>>(new PagingResult<>(total, responseDtos),
//				HttpStatus.OK);
//	}
//
//	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
//	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<?> delete(@PathParam(value = "id") Integer id) {
//		log.debug("In delete. Id {}", id);
//
//		ArProductivityEntity entity = service.getById(id);
////		entity.setDeleted(true);
//		try {
//			entity = service.update(entity);
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			new ResponseEntity<AppException>(new AppException(e.getMessage(), e.getCause()),
//					HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//
//		log.debug("Out delete. Id {}", id);
//		return new ResponseEntity<String>("Deleted", HttpStatus.NO_CONTENT);
//	}

//	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
//	@GetMapping("/{id}")
//	public ResponseEntity<?> get(@PathVariable(value = "id") Integer id) {
//		log.debug("In get. Id {}", id);
//
//		ArProductivityEntity entity = service.getById(id);
//
//		log.debug("Out get.");
//		return new ResponseEntity<ArProductivityResponseDto>(populateResponseDto(entity, true), HttpStatus.OK);
//	}

//	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
//	@GetMapping("/search")
//	public ResponseEntity<Page<Arpro>> getArProductivityList(@RequestBody ArProductivityRequest arProductivityRequest, Pageable pageable) {
//		log.debug("Ar Productivity list....");
//
//		org.springframework.data.domain.Page<ArProductivityResponseDto> entity = service.getarProductivityList(arProductivityRequest, pageable);
//       
//		log.debug("Out get.");
//		return new ResponseEntity<ArProductivityResponseDto>(populateResponseDto(entity, true), HttpStatus.OK);
//	}

//	private ArProductivityResponseDto populateResponseDto(ArProductivityEntity entity, boolean dependencies) {
//		ArProductivityResponseDto responseDto = new ArProductivityResponseDto();
//
//		BeanUtils.copyProperties(entity, responseDto);
//		responseDto.setCreatedOn(entity.getCreatedOn().getTime() + "");
//		if (entity.getModifiedOn() != null) {
//			responseDto.setModifiedOn(entity.getModifiedOn().getTime() + "");
//		}
//
//		if (entity.getDatabase() != null)
//			responseDto.setDatabaseName(entity.getDatabase().getName());
//		if (entity.getInsurance() != null)
//			responseDto.setInsuranceName(entity.getInsurance().getName());
//		if (entity.getDoctor() != null)
//			responseDto.setDoctorName(entity.getDoctor().getName());
//		if (entity.getTeam() != null)
//			responseDto.setTeamName(entity.getTeam().getName());
//
//		if (dependencies) {
//			Set<Integer> arProductivityWorkflowSet = new HashSet<>();
//			for (ArProductivityWorkFlowEntity arProductivityWorkflowEntity : entity.getArWorkflows()) {
//				arProductivityWorkflowSet.add(arProductivityWorkflowEntity.getId());
//			}

//			Set<String> departmentSet = new HashSet<>();
//			for (DepartmentEntity departmentEntity : entity.getDepartments()) {
//				departmentSet.add(departmentEntity.getId().toString());
//			}

//			Set<String> emailTemplateSet = new HashSet<>();
//			for (EmailTemplateEntity emailTemplateEntity : entity.getEmailTemplates()) {
//				emailTemplateSet.add(emailTemplateEntity.getId().toString());
//			}

//			responseDto.setPermissions(permissionSet);
//			responseDto.setDepartments(departmentSet);
//			responseDto.setEmailTemplates(emailTemplateSet);
//		} else {
//			responseDto.setWorkflowIds(null);
////			responseDto.setDepartments(null);
////			responseDto.setEmailTemplates(null);
//		}
//
//		return responseDto;
//	}

    @GetMapping("/allreasontoreject")
    public ResponseEntity<?> getAllReasonToReject() {
        ReasonToRejectResponse reasonToRejectResponse = new ReasonToRejectResponse();

        Iterable<ArChargeProdReject> iterable = chargeProdRejectionRepository.findAll();
        List<ArChargeProdReject> myList = StreamSupport
                .stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
        List<String> reason = myList.stream().filter(y -> y.getReasonToReject() != null).map(x -> x.getReasonToReject()).distinct().collect(
                Collectors.toList()

        );
        reasonToRejectResponse.setReasonToReject(reason);
        return new ResponseEntity(reasonToRejectResponse, HttpStatus.OK);

    }


    @GetMapping("/checkpatientaccountnumber/{patientaccountnumber}")
    public ResponseEntity<?> isPatientAccountNumberExists(@PathVariable String patientaccountnumber) {
        PatientAccountNumberResponse patientAccountNumberResponse = new PatientAccountNumberResponse();
        List<ArProductivity> arProductive =  arProductivityRepository.getByPatientAccountNumber(patientaccountnumber);
      if (arProductive.size() != 0) {
            patientAccountNumberResponse.setPatientAccountNumberExists(true);
        } else {
            patientAccountNumberResponse.setPatientAccountNumberExists(false);
        }
        return new ResponseEntity(patientAccountNumberResponse, HttpStatus.OK);
    }
}
