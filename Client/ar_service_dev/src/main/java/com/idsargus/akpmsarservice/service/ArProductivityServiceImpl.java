package com.idsargus.akpmsarservice.service;

import com.idsargus.akpmsarservice.model.DashboardCount;
import com.idsargus.akpmsarservice.model.DashboardItem;
import com.idsargus.akpmsarservice.repository.*;
import com.idsargus.akpmsarservice.util.Constants;
import com.idsargus.akpmsarservice.util.ExcelHelper;
import com.idsargus.akpmscommonservice.entity.ArProductivityEntity;
import com.idsargus.akpmsarservice.repository.ChargeProductivityRepository;
import com.idsargus.akpmscommonservice.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ArProductivityServiceImpl {//implements ArProductivityService, QueryBuilder {

	@Autowired
	private ArProductivityRepository repository;

	@Autowired
	private DoctorRepository doctorRepository;
	
	@Autowired
	private DoctorDataRestRepository doctorDataRestRepository;

	@Autowired
	private InsuranceRepository insuranceRepository;

	@Autowired
	private DepartmentRepository departmentRepository;
 
	@Autowired
	private ArDatabaseRepository arDatabaseRepository;
	
	@Autowired
	private InsuranceDataRestRepository insuranceDataRestRepository;

	@Autowired
	private ArProductivityWorkflowRepository arProductivityWorkflowRepository;
	
	@Autowired
	private PaymentTypeRepository paymentTypeRepository;
	
	//Arindam Code Changes
	@Autowired
	private HourlyTaskRepository hourlyTaskRepository;
	@Autowired
	private  HourlyTaskDataRestRepository hourlyTaskDataRestRepository;
	
	@Autowired
	private PaymentProductivityRepository paymentProductivityRepository;
	
	@Autowired
	private CodingCorrectionLogWorkFlowRepository codingCorrectionLogWorkFlowRepository;

	@Autowired
    private RekeyRequestWorkFlowRepository rekeyRequestWorkFlowRepository;
	
	@Autowired
	private PaymentProductivityOffsetRepository paymentProductivityOffsetRepository;
	
	@Autowired
	private PaymentPostingLogWorkFlowRepository paymentPostingLogWorkFlowRepository;
	
	@Autowired
	private AdjustmentLogWorkFlowrepository adjustmentLogWorkFlowrepository;
	
	@Autowired
	private QueryToTLRepository queryToTLRepository;
	
	@Autowired
	private RefundRequestWorkFlowRepository refundRequestWorkFlowRepository;

	@Autowired
	private ChargeProductivityRepository chargeProductivityRepository;

	@Autowired
	private ChargeProdRejectionRepository chargeProdRejectionRepository;
	
	//ARN for excel file download...
	public ByteArrayInputStream getExcelFileAsStream() {
		
		Pageable pageable = PageRequest.of(0, 10);
		Page<ArProductivityEntity> allArData = repository.getAll(pageable);
		
		CSVFormat csvFormat = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);
		try(ByteArrayOutputStream out = new ByteArrayOutputStream();
				CSVPrinter csPrinter = new CSVPrinter(new PrintWriter(out), csvFormat)){
			
			
			while(!allArData.isEmpty()) {
				pageable = pageable.next();
				for(ArProductivityEntity entity : allArData ) {
					List<String> data = new ArrayList<String>();
					data.add(entity.getPatientAccountNumber());
					data.add(entity.getPatientName());
					data.add(entity.getDos());
					data.add(entity.getCpt());
					data.add(entity.getPatientName());
					data.add(entity.getStatusCode());
					data.add(entity.getDatabase().getName());
					data.add(entity.getInsurance().getName());
					data.add(entity.getTeam().getName());
					data.add(entity.getDoctor().getName());
					data.add(""+entity.getBalanceAmount());
					data.add(entity.getCreatedBy().getFirstName());
					data.add(""+entity.getCreatedOn());
					
					csPrinter.printRecord(data);
				}
				
			}
			
			csPrinter.flush();
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
//	@Autowired
//	private ConstantConfig constantConfig;
//
//	@Override
//	@Transactional
//	public ArProductivityEntity add(ArProductivityEntity entity) throws Exception {
//
//		return repository.save(entity);
//	}
//
//	@Override
//	@Transactional
//	public ArProductivityEntity update(ArProductivityEntity entity) throws Exception {
//
//		return repository.save(entity);
//	}
//
//	@Override
//	@Transactional
//	public ArProductivityEntity delete() throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	@Transactional(readOnly = true)
//	public ArProductivityEntity getById(Integer id) throws ResourceNotFoundException {
//		Optional<ArProductivityEntity> entityOptional = repository.findById(id);
//
//		if (entityOptional.isPresent()) {
//			return entityOptional.get();
//		}
//
//		throw new ResourceNotFoundException("ArProductivity", "id", id);
//	}
//
//	/**
//	 * Function to manage server side errors. 1. User email should be unique.
//	 * 
//	 * @param entity
//	 * @return
//	 */
//	@Override
//	public Map<String, String> validation(ArProductivityEntity entity) throws Exception {
//		Map<String, String> errors = new HashMap<>();
//
//		if (entity.getId() == null) {
////			ArProductivityEntity entity = repository.findUserByEmail(userEntity.getEmail());
//
////			if (entity != null) {
////				errors.put("email", "Already exist.");
////			}
//		}
//
//		return errors;
//	}
//
//	@Override
//	@Transactional(readOnly = true)
//	public List<ArProductivityEntity> list(String orderBy, String direction, Integer page, Integer size,
//			List<SearchCriteria> searchCriterias) throws Exception {
//
//		StringBuilder query = QueryBuilder.prepareWhereClause(searchCriterias);
//
//		// Set default if null any
//		orderBy = (orderBy == null) ? constantConfig.getOrderBy() : orderBy;
//		direction = (direction == null) ? constantConfig.getDirection() : direction;
//		page = (page == null) ? constantConfig.getPage() : page;
//		size = (size == null) ? constantConfig.getSize() : size;
//
//		QueryBuilder.prepareOrderByClause(query, "u." + orderBy, direction);
//
//		log.debug("Query returned from CustomQueryBuilder :: {} ", query.toString());
//
//		return repository.search(query.toString(), page, size);
//	}
//
//	@Override
//	public ArProductivityEntity enrichEntity(ArProductivityRequestDto requestDto, ArProductivityEntity entity)
//			throws Exception {
//		if (requestDto.getDoctorId() != null) {
//			Optional<DoctorEntity> doctorEntityOpt = doctorRepository.findById(requestDto.getDoctorId());
//
//			if (doctorEntityOpt.isPresent()) {
//				entity.setDoctor(doctorEntityOpt.get());
//			}
//		}
//
//		if (requestDto.getInsuranceId() != null) {
//			Optional<InsuranceEntity> insuranceEntityOpt = insuranceRepository.findById(requestDto.getInsuranceId());
//
//			if (insuranceEntityOpt.isPresent()) {
//				entity.setInsurance(insuranceEntityOpt.get());
//			}
//		}
//
//		if (requestDto.getTeamId() != null) {
//			Optional<DepartmentEntity> departmentEntityOpt = departmentRepository.findById(requestDto.getTeamId());
//
//			if (departmentEntityOpt.isPresent()) {
//				entity.setTeam(departmentEntityOpt.get());
//			}
//		}
//
//		if (requestDto.getDatabaseId() != null) {
//			Optional<ArDatabaseEntity> arDatabaseEntityOpt = arDatabaseRepository.findById(requestDto.getDatabaseId());
//
//			if (arDatabaseEntityOpt.isPresent()) {
//				entity.setDatabase(arDatabaseEntityOpt.get()); 
//			}
//		}
//
//		if (requestDto.getWorkflowIds() != null && !requestDto.getWorkflowIds().isEmpty()) {
//			Set<ArProductivityWorkFlowEntity> arProductivityWorkFlowEntities = new HashSet<>();
//
//			for (Integer workflowId : requestDto.getWorkflowIds()) {
//				Optional<ArProductivityWorkFlowEntity> arProductivityWorkFlowEntityOpt = arProductivityWorkflowRepository
//						.findById(workflowId);
//
//				if (arProductivityWorkFlowEntityOpt.isPresent()) {
//					arProductivityWorkFlowEntities.add(arProductivityWorkFlowEntityOpt.get());
//				}
//			}
//
//			entity.setArWorkflows(arProductivityWorkFlowEntities);
//		}
//
//		return entity;
//	}
//
//	@Override
//	public Long count(List<SearchCriteria> searchCriterias) throws Exception {
//		StringBuilder query = QueryBuilder.prepareWhereClause(searchCriterias);
//
//		log.debug("Query returned from CustomQueryBuilder :: {} ", query.toString());
//
//		return repository.count(query.toString());
//	}
//	
//	@Override
//	public Page<ArProductivityResponseDto> getarProductivityList(ArProductivityRequest arProductivityRequest, Pageable pageable ){
//		
//		
//		
//		
//	
//		return null;
//		
//	}
	
	//ARN FOR DASH BOARD
	public List<DashboardItem> getDashboardCountList(){
		List<DashboardItem> dashboardItemList = new ArrayList<>();
		List<DashboardCount> dashboardCountList = new ArrayList<>();
		
		//Master Data Tables
//		DashboardItem dsMaster = new DashboardItem();
//		dsMaster.setId(1);
//		dsMaster.setName(Constants.MASTER_DATA_TABLES);
//		DashboardCount dcMaster1 = new DashboardCount();
//		dcMaster1.setId(1);
//		dcMaster1.setName("Total Doctors");
//		//	dcMaster1.setCount(doctorDataRestRepository.count());
//		dcMaster1.setCount(doctorDataRestRepository.countByIsDeleted());
//		dcMaster1.setUrl("/doctor");
//		DashboardCount dcMaster2 = new DashboardCount();
//		dcMaster2.setId(2);
//		dcMaster2.setName("Total Insurances");
//		//	dcMaster2.setCount(insuranceDataRestRepository.count());
//		dcMaster2.setCount(insuranceDataRestRepository.countByIsDeleted());
//		dcMaster2.setUrl("/insurance");
//		DashboardCount dcMaster3 = new DashboardCount();
//		dcMaster3.setId(3);
//		dcMaster3.setName("Total Payment Types");
//		//dcMaster3.setCount(paymentTypeRepository.count());
//		dcMaster3.setCount(paymentTypeRepository.countByIsDeleted());
//		dcMaster3.setUrl("/paymenttype");
//		dashboardCountList.add(dcMaster1);
//		dashboardCountList.add(dcMaster2);
//		dashboardCountList.add(dcMaster3);
//		dsMaster.setChildren(dashboardCountList);
		
		//Hourly Projects Productivity
		DashboardItem dsHpp = new DashboardItem();
		dsHpp.setId(2);
		dsHpp.setName(Constants.HOURLY_PROJECTS_PRODUCTIVITY);
		DashboardCount dcHpp = new DashboardCount();
		dcHpp.setId(1);
		dcHpp.setName("Hourly List");
//----------------------------------------------------------------------------
//		dcHpp.setCount(hourlyTaskRepository.count());
	//	dcHpp.setCount(hourlyTaskRepository.countByDeleted());
//		------------------------------------------------------------------------------
		dcHpp.setCount(hourlyTaskDataRestRepository.countByDeleted());

		dcHpp.setUrl("/hourly-productivity");
		dashboardCountList = new ArrayList<>();
		dashboardCountList.add(dcHpp);
		dsHpp.setChildren(dashboardCountList);
		
		//Payments Team Productivity
		DashboardItem dsPtp = new DashboardItem();
		dsPtp.setId(3);
		dsPtp.setName(Constants.PAYMENTS_TEAM_PRODUCTIVITY);
		DashboardCount dcPtp1 = new DashboardCount();
		dcPtp1.setId(1);
		dcPtp1.setName(Constants.ERA_LIST);
		dcPtp1.setCount(paymentProductivityRepository.getProductivityCountById(1));
		dcPtp1.setUrl("/payment-productivity-list/1");
		DashboardCount dcPtp2 = new DashboardCount();
		dcPtp2.setId(2);
		dcPtp2.setName(Constants.NON_ERA_LIST);
		dcPtp2.setUrl("/payment-productivity-list/2");
		dcPtp2.setCount(paymentProductivityRepository.getProductivityCountById(2));
		DashboardCount dcPtp3 = new DashboardCount();
		dcPtp3.setId(3);
		dcPtp3.setName(Constants.CAP_LIST);
		dcPtp3.setUrl("/payment-productivity-list/3");
		dcPtp3.setCount(paymentProductivityRepository.getProductivityCountById(3));
		
		dashboardCountList = new ArrayList<>();
		dashboardCountList.add(dcPtp1);
		dashboardCountList.add(dcPtp2);
		dashboardCountList.add(dcPtp3);
		dsPtp.setChildren(dashboardCountList);
		
//		
//		// Payments Process Workflow
//		DashboardItem dsPpw = new DashboardItem();
//		dsPpw.setId(4);
//		dsPpw.setName(Constants.PAYMENTS_PROCESS_WORKFLOW);
//		DashboardCount dcPpw1 = new DashboardCount();
//		dcPpw1.setId(1);
//		dcPpw1.setName(Constants.TO_AR_IPA_FFS_HMO);
//		dcPpw1.setCount(paymentProductivityRepository.getProductivityCountByworkflowId(1));
//		dcPpw1.setUrl("/payment-productivity-list/2/1");
//		dcPpw1.setFilterName1("workFlowId");
//		dcPpw1.setFilterValue1("1");
//		DashboardCount dcPpw2 = new DashboardCount();
//		dcPpw2.setId(2);
//		dcPpw2.setName(Constants.TO_AR_FFS);
//		dcPpw2.setCount(paymentProductivityRepository.getProductivityCountByworkflowId(2));
//		dcPpw2.setUrl("/payment-productivity-list/2/2");
//		dcPpw2.setFilterName1("workFlowId");
//		dcPpw2.setFilterValue1("2");
//		DashboardCount dcPpw3 = new DashboardCount();
//		dcPpw3.setId(3);
//		dcPpw3.setName(Constants.TO_AR_CEP);
//		dcPpw3.setCount(paymentProductivityRepository.getProductivityCountByworkflowId(3));
//		dcPpw3.setUrl("/payment-productivity-list/2/3");
//		dcPpw3.setFilterName1("workFlowId");
//		dcPpw3.setFilterValue1("3");
//		DashboardCount dcPpw4 = new DashboardCount();
//		dcPpw4.setId(4);
//		dcPpw4.setName(Constants.TO_AR_MCL);
//		dcPpw4.setCount(paymentProductivityRepository.getProductivityCountByworkflowId(4));
//		dcPpw4.setUrl("/payment-productivity-list/2/4");
//		dcPpw4.setFilterName1("workFlowId");
//		dcPpw4.setFilterValue1("4");
//		DashboardCount dcPpw5 = new DashboardCount();
//		dcPpw5.setId(5);
//		dcPpw5.setName(Constants.TO_AR_MCR);
//		dcPpw5.setCount(paymentProductivityRepository.getProductivityCountByworkflowId(5));
//		dcPpw5.setUrl("/payment-productivity-list/2/5");
//		dcPpw5.setFilterName1("workFlowId");
//		dcPpw5.setFilterValue1("5");
//		DashboardCount dcPpw6 = new DashboardCount();
//		dcPpw6.setId(6);
//		dcPpw6.setName(Constants.TO_AR_WC);
//		dcPpw6.setCount(paymentProductivityRepository.getProductivityCountByworkflowId(6));
//		dcPpw6.setUrl("/payment-productivity-list/2/6");
//		dcPpw6.setFilterName1("workFlowId");
//		dcPpw6.setFilterValue1("6");
//		DashboardCount dcPpw7 = new DashboardCount();
//		dcPpw7.setId(7);
//		dcPpw7.setName(Constants.QUERY_TO_TL);
//		dcPpw7.setCount(paymentProductivityRepository.getProductivityCountByworkflowId(7));
//		dcPpw7.setUrl("/payment-productivity-list/2/7");
//		dcPpw7.setFilterName1("workFlowId");
//		dcPpw7.setFilterValue1("7");
//		
//		dashboardCountList = new ArrayList<>();
//		dashboardCountList.add(dcPpw1);
//		dashboardCountList.add(dcPpw2);
//		dashboardCountList.add(dcPpw3);
//		dashboardCountList.add(dcPpw4);
//		dashboardCountList.add(dcPpw5);
//		dashboardCountList.add(dcPpw6);
//		dashboardCountList.add(dcPpw7);
//		dsPpw.setChildren(dashboardCountList);
		
//		// Payments Process Workflow(ERA)
		DashboardItem dsPpwERA = new DashboardItem();
		dsPpwERA.setId(4);
		dsPpwERA.setName(Constants.PAYMENTS_PROCESS_WORKFLOW_ERA);
		DashboardCount dcPpwERA1 = new DashboardCount();
		dcPpwERA1.setId(1);
		dcPpwERA1.setName(Constants.TO_AR_IPA_FFS_HMO);
		dcPpwERA1.setCount(paymentProductivityRepository.getProductivityCountByWorkFlowIdAndPaymentType(1,1));
		dcPpwERA1.setUrl("/payment-productivity-list/1/1");
		dcPpwERA1.setFilterName1("workFlowId");
		dcPpwERA1.setFilterValue1("1");
		DashboardCount dcPpwERA2 = new DashboardCount();
		dcPpwERA2.setId(2);
		dcPpwERA2.setName(Constants.TO_AR_FFS);
		dcPpwERA2.setCount(paymentProductivityRepository.getProductivityCountByWorkFlowIdAndPaymentType(2,1));
		dcPpwERA2.setUrl("/payment-productivity-list/1/2");
		dcPpwERA2.setFilterName1("workFlowId");
		dcPpwERA2.setFilterValue1("2");
		DashboardCount dcPpwERA3 = new DashboardCount();
		dcPpwERA3.setId(3);
		dcPpwERA3.setName(Constants.TO_AR_CEP);
		dcPpwERA3.setCount(paymentProductivityRepository.getProductivityCountByWorkFlowIdAndPaymentType(3,1));
		dcPpwERA3.setUrl("/payment-productivity-list/1/3");
		dcPpwERA3.setFilterName1("workFlowId");
		dcPpwERA3.setFilterValue1("3");
		DashboardCount dcPpwERA4 = new DashboardCount();
		dcPpwERA4.setId(4);
		dcPpwERA4.setName(Constants.TO_AR_MCL);
		dcPpwERA4.setCount(paymentProductivityRepository.getProductivityCountByWorkFlowIdAndPaymentType(4,1));
		dcPpwERA4.setUrl("/payment-productivity-list/1/4");
		dcPpwERA4.setFilterName1("workFlowId");
		dcPpwERA4.setFilterValue1("4");
		DashboardCount dcPpwERA5 = new DashboardCount();
		dcPpwERA5.setId(5);
		dcPpwERA5.setName(Constants.TO_AR_MCR);
		dcPpwERA5.setCount(paymentProductivityRepository.getProductivityCountByWorkFlowIdAndPaymentType(5,1));
		dcPpwERA5.setUrl("/payment-productivity-list/1/5");
		dcPpwERA5.setFilterName1("workFlowId");
		dcPpwERA5.setFilterValue1("5");
		DashboardCount dcPpwERA6 = new DashboardCount();
		dcPpwERA6.setId(6);
		dcPpwERA6.setName(Constants.TO_AR_WC);
		dcPpwERA6.setCount(paymentProductivityRepository.getProductivityCountByWorkFlowIdAndPaymentType(6,1));
		dcPpwERA6.setUrl("/payment-productivity-list/1/6");
		dcPpwERA6.setFilterName1("workFlowId");
		dcPpwERA6.setFilterValue1("6");
		DashboardCount dcPpwERA7 = new DashboardCount();
		dcPpwERA7.setId(7);
		dcPpwERA7.setName(Constants.QUERY_TO_TL);
		dcPpwERA7.setCount(paymentProductivityRepository.getProductivityCountByWorkFlowIdAndPaymentType(7,1));
		dcPpwERA7.setUrl("/payment-productivity-list/1/7");
		dcPpwERA7.setFilterName1("workFlowId");
		dcPpwERA7.setFilterValue1("7");
		
		dashboardCountList = new ArrayList<>();
		dashboardCountList.add(dcPpwERA1);
		dashboardCountList.add(dcPpwERA2);
		dashboardCountList.add(dcPpwERA3);
		dashboardCountList.add(dcPpwERA4);
		dashboardCountList.add(dcPpwERA5);
		dashboardCountList.add(dcPpwERA6);
		dashboardCountList.add(dcPpwERA7);
		dsPpwERA.setChildren(dashboardCountList);
		
		// Payments Process Workflow(Non ERA)
		
		DashboardItem dsPpwNonERA = new DashboardItem();
		dsPpwNonERA.setId(5);
		dsPpwNonERA.setName(Constants.PAYMENTS_PROCESS_WORKFLOW_NONERA);
		DashboardCount dcPpwNonERA1 = new DashboardCount();
		dcPpwNonERA1.setId(1);
		dcPpwNonERA1.setName(Constants.TO_AR_IPA_FFS_HMO);
		dcPpwNonERA1.setCount(paymentProductivityRepository.getProductivityCountByWorkFlowIdAndPaymentType(1,2));
		dcPpwNonERA1.setUrl("/payment-productivity-list/2/1");
		dcPpwNonERA1.setFilterName1("workFlowId");
		dcPpwNonERA1.setFilterValue1("1");
		DashboardCount dcPpwNonERA2 = new DashboardCount();
		dcPpwNonERA2.setId(2);
		dcPpwNonERA2.setName(Constants.TO_AR_FFS);
		dcPpwNonERA2.setCount(paymentProductivityRepository.getProductivityCountByWorkFlowIdAndPaymentType(2,2));
		dcPpwNonERA2.setUrl("/payment-productivity-list/2/2");
		dcPpwNonERA2.setFilterName1("workFlowId");
		dcPpwNonERA2.setFilterValue1("2");
		DashboardCount dcPpwNonERA3 = new DashboardCount();
		dcPpwNonERA3.setId(3);
		dcPpwNonERA3.setName(Constants.TO_AR_CEP);
		dcPpwNonERA3.setCount(paymentProductivityRepository.getProductivityCountByWorkFlowIdAndPaymentType(3,2));
		dcPpwNonERA3.setUrl("/payment-productivity-list/2/3");
		dcPpwNonERA3.setFilterName1("workFlowId");
		dcPpwNonERA3.setFilterValue1("3");
		DashboardCount dcPpwNonERA4 = new DashboardCount();
		dcPpwNonERA4.setId(4);
		dcPpwNonERA4.setName(Constants.TO_AR_MCL);
		dcPpwNonERA4.setCount(paymentProductivityRepository.getProductivityCountByWorkFlowIdAndPaymentType(4,2));
		dcPpwNonERA4.setUrl("/payment-productivity-list/2/4");
		dcPpwNonERA4.setFilterName1("workFlowId");
		dcPpwNonERA4.setFilterValue1("4");
		DashboardCount dcPpwNonERA5 = new DashboardCount();
		dcPpwNonERA5.setId(5);
		dcPpwNonERA5.setName(Constants.TO_AR_MCR);
		dcPpwNonERA5.setCount(paymentProductivityRepository.getProductivityCountByWorkFlowIdAndPaymentType(5,2));
		dcPpwNonERA5.setUrl("/payment-productivity-list/2/5");
		dcPpwNonERA5.setFilterName1("workFlowId");
		dcPpwNonERA5.setFilterValue1("5");
		DashboardCount dcPpwNonERA6 = new DashboardCount();
		dcPpwNonERA6.setId(6);
		dcPpwNonERA6.setName(Constants.TO_AR_WC);
		dcPpwNonERA6.setCount(paymentProductivityRepository.getProductivityCountByWorkFlowIdAndPaymentType(6,2));
		dcPpwNonERA6.setUrl("/payment-productivity-list/2/6");
		dcPpwNonERA6.setFilterName1("workFlowId");
		dcPpwNonERA6.setFilterValue1("6");
		DashboardCount dcPpwNonERA7 = new DashboardCount();
		dcPpwNonERA7.setId(7);
		dcPpwNonERA7.setName(Constants.QUERY_TO_TL);
		dcPpwNonERA7.setCount(paymentProductivityRepository.getProductivityCountByWorkFlowIdAndPaymentType(7,2));
		dcPpwNonERA7.setUrl("/payment-productivity-list/2/7");
		dcPpwNonERA7.setFilterName1("workFlowId");
		dcPpwNonERA7.setFilterValue1("7");
		
		dashboardCountList = new ArrayList<>();
		dashboardCountList.add(dcPpwNonERA1);
		dashboardCountList.add(dcPpwNonERA2);
		dashboardCountList.add(dcPpwNonERA3);
		dashboardCountList.add(dcPpwNonERA4);
		dashboardCountList.add(dcPpwNonERA5);
		dashboardCountList.add(dcPpwNonERA6);
		dashboardCountList.add(dcPpwNonERA7);
		dsPpwNonERA.setChildren(dashboardCountList);
		
		// Payments Process Workflow(CAP)
		
		DashboardItem dsPpwCAP = new DashboardItem();
		dsPpwCAP.setId(6);
		dsPpwCAP.setName(Constants.PAYMENTS_PROCESS_WORKFLOW_CAP);
		DashboardCount dcPpwCAP1 = new DashboardCount();
		dcPpwCAP1.setId(1);
		dcPpwCAP1.setName(Constants.TO_AR_IPA_FFS_HMO);
		dcPpwCAP1.setCount(paymentProductivityRepository.getProductivityCountByWorkFlowIdAndPaymentType(1,3));
		dcPpwCAP1.setUrl("/payment-productivity-list/3/1");
		dcPpwCAP1.setFilterName1("workFlowId");
		dcPpwCAP1.setFilterValue1("1");
		DashboardCount dcPpwCAP2 = new DashboardCount();
		dcPpwCAP2.setId(2);
		dcPpwCAP2.setName(Constants.TO_AR_FFS);
		dcPpwCAP2.setCount(paymentProductivityRepository.getProductivityCountByWorkFlowIdAndPaymentType(2,3));
		dcPpwCAP2.setUrl("/payment-productivity-list/3/2");
		dcPpwCAP2.setFilterName1("workFlowId");
		dcPpwCAP2.setFilterValue1("2");
		DashboardCount dcPpwCAP3 = new DashboardCount();
		dcPpwCAP3.setId(3);
		dcPpwCAP3.setName(Constants.TO_AR_CEP);
		dcPpwCAP3.setCount(paymentProductivityRepository.getProductivityCountByWorkFlowIdAndPaymentType(3,3));
		dcPpwCAP3.setUrl("/payment-productivity-list/3/3");
		dcPpwCAP3.setFilterName1("workFlowId");
		dcPpwCAP3.setFilterValue1("3");
		DashboardCount dcPpwCAP4 = new DashboardCount();
		dcPpwCAP4.setId(4);
		dcPpwCAP4.setName(Constants.TO_AR_MCL);
		dcPpwCAP4.setCount(paymentProductivityRepository.getProductivityCountByWorkFlowIdAndPaymentType(4,3));
		dcPpwCAP4.setUrl("/payment-productivity-list/3/4");
		dcPpwCAP4.setFilterName1("workFlowId");
		dcPpwCAP4.setFilterValue1("4");
		DashboardCount dcPpwCAP5 = new DashboardCount();
		dcPpwCAP5.setId(5);
		dcPpwCAP5.setName(Constants.TO_AR_MCR);
		dcPpwCAP5.setCount(paymentProductivityRepository.getProductivityCountByWorkFlowIdAndPaymentType(5,3));
		dcPpwCAP5.setUrl("/payment-productivity-list/3/5");
		dcPpwCAP5.setFilterName1("workFlowId");
		dcPpwCAP5.setFilterValue1("5");
		DashboardCount dcPpwCAP6 = new DashboardCount();
		dcPpwCAP6.setId(6);
		dcPpwCAP6.setName(Constants.TO_AR_WC);
		dcPpwCAP6.setCount(paymentProductivityRepository.getProductivityCountByWorkFlowIdAndPaymentType(6,3));
		dcPpwCAP6.setUrl("/payment-productivity-list/3/6");
		dcPpwCAP6.setFilterName1("workFlowId");
		dcPpwCAP6.setFilterValue1("6");
		DashboardCount dcPpwCAP7 = new DashboardCount();
		dcPpwCAP7.setId(7);
		dcPpwCAP7.setName(Constants.QUERY_TO_TL);
		dcPpwCAP7.setCount(paymentProductivityRepository.getProductivityCountByWorkFlowIdAndPaymentType(7,3));
		dcPpwCAP7.setUrl("/payment-productivity-list/3/7");
		dcPpwCAP7.setFilterName1("workFlowId");
		dcPpwCAP7.setFilterValue1("7");
		
		dashboardCountList = new ArrayList<>();
		dashboardCountList.add(dcPpwCAP1);
		dashboardCountList.add(dcPpwCAP2);
		dashboardCountList.add(dcPpwCAP3);
		dashboardCountList.add(dcPpwCAP4);
		dashboardCountList.add(dcPpwCAP5);
		dashboardCountList.add(dcPpwCAP6);
		dashboardCountList.add(dcPpwCAP7);
		dsPpwCAP.setChildren(dashboardCountList);
		

		
		//Coding Correction Log
		DashboardItem dsCcl= new DashboardItem();
		dsCcl.setId(7);
		dsCcl.setName(Constants.CODING_CORRECTION_LOG);
		DashboardCount dcCcl1 = new DashboardCount();
		dcCcl1.setId(1);
		dcCcl1.setName(Constants.FORWARD_TO_CODING_CORRECTION);
		dcCcl1.setCount(codingCorrectionLogWorkFlowRepository.getCodingCorrectionLogWorkFlowCountByworkflowStatus("Forward to coding correction"));
	//	dcCcl1.setUrl("/productivityList/2/1");
		dcCcl1.setUrl("/codingcorrection/2/1");
		dcCcl1.setFilterName1("nextAction");
		dcCcl1.setFilterValue1("Forward to coding correction");

		
		DashboardCount dcCcl2 = new DashboardCount();
		dcCcl2.setId(2);
		dcCcl2.setName(Constants.ESCALATE_TO_ARGUS_TL);
		dcCcl2.setCount(codingCorrectionLogWorkFlowRepository.getCodingCorrectionLogWorkFlowCountByworkflowStatus("Escalate to Argus TL"));
	//	dcCcl2.setUrl("/productivityList/2/2");
		dcCcl2.setUrl("/codingcorrection/2/2");
		dcCcl2.setFilterName1("nextAction");
		dcCcl2.setFilterValue1("Escalate to Argus TL");
		
		DashboardCount dcCcl3 = new DashboardCount();
		dcCcl3.setId(3);
		dcCcl3.setName(Constants.RETURN_TO_AR);
		dcCcl3.setCount(codingCorrectionLogWorkFlowRepository.getCodingCorrectionLogWorkFlowCountByworkflowStatus("Return to AR"));
		dcCcl3.setUrl("/codingcorrection/2/3");
		dcCcl3.setFilterName1("nextAction");
		dcCcl3.setFilterValue1("Return to AR");
		
		DashboardCount dcCcl4 = new DashboardCount();
		dcCcl4.setId(4);
		dcCcl4.setName(Constants.CODING_TO_CE);
		dcCcl4.setCount(codingCorrectionLogWorkFlowRepository.getCodingCorrectionLogWorkFlowCountByworkflowStatus("Coding to CE"));
		dcCcl4.setUrl("/codingcorrection/2/4");
		dcCcl4.setFilterName1("nextAction");
		dcCcl4.setFilterValue1("Coding to CE");
		
		DashboardCount dcCcl5 = new DashboardCount();
		dcCcl5.setId(5);
		dcCcl5.setName(Constants.DONE);
		dcCcl5.setCount(codingCorrectionLogWorkFlowRepository.getCodingCorrectionLogWorkFlowCountByworkflowStatus("Done"));
		dcCcl5.setUrl("/codingcorrection/2/5");
		dcCcl5.setFilterName1("nextAction");
		dcCcl5.setFilterValue1("Done");
		
		dashboardCountList = new ArrayList<>();
		dashboardCountList.add(dcCcl1);
		dashboardCountList.add(dcCcl2);
		dashboardCountList.add(dcCcl3);
		dashboardCountList.add(dcCcl4); 
		dashboardCountList.add(dcCcl5);
		dsCcl.setChildren(dashboardCountList);

		// Rekey Request Log
		DashboardItem dsRrl = new DashboardItem();
		dsRrl.setId(8);
		dsRrl.setName(Constants.REKEY_REQUEST_LOG);
		
		DashboardCount dcRrl1 = new DashboardCount();
		dcRrl1.setId(1);
		dcRrl1.setName(Constants.QUERY_TO_CHARGE_ENTRY);
		dcRrl1.setCount(rekeyRequestWorkFlowRepository.getRekeyRequestCountByStatus(1));
		dcRrl1.setUrl("/rekeyrequest/4/1");
		DashboardCount dcRrl2 = new DashboardCount();
		dcRrl2.setId(2);
		dcRrl2.setName(Constants.RETURN_TO_AR);
		dcRrl2.setCount(rekeyRequestWorkFlowRepository.getRekeyRequestCountByStatus(3));
		dcRrl2.setUrl("/rekeyrequest/4/3");
		DashboardCount dcRrl3 = new DashboardCount();
		dcRrl3.setId(3);
		dcRrl3.setName(Constants.CLOSED);
		dcRrl3.setCount(rekeyRequestWorkFlowRepository.getRekeyRequestCountByStatus(5));
		dcRrl3.setUrl("/rekeyrequest/4/5");
		dashboardCountList = new ArrayList<>();
		dashboardCountList.add(dcRrl1);
		dashboardCountList.add(dcRrl2);
		dashboardCountList.add(dcRrl3);
		dsRrl.setChildren(dashboardCountList);
		
		//Adjustment Log
		DashboardItem dsAdl = new DashboardItem();
		dsAdl.setId(9);
		dsAdl.setName(Constants.ADJUSTMENT_LOG);
		DashboardCount dcAdl1 = new DashboardCount();
		dcAdl1.setId(1);
		dcAdl1.setName(Constants.ADJUSTMENT_LOG_LIST_ALL);
		dcAdl1.setCount(adjustmentLogWorkFlowrepository.countById());
//      dcAdl1.setUrl("/productivityList/1");		
		dcAdl1.setUrl("/adjustmentlog/1");
		
		DashboardCount dcAdl2 = new DashboardCount();
		dcAdl2.setId(2);
		dcAdl2.setName(Constants.TIMELY_FILING);
		dcAdl2.setCount(adjustmentLogWorkFlowrepository.getAdjustmentLogCount(1));
		dcAdl2.setUrl("/adjustmentlog/1/1");
		dcAdl2.setFilterName1("withoutTimilyFiling");
		dcAdl2.setFilterValue1("1");
		DashboardCount dcAdl3 = new DashboardCount();
		dcAdl3.setId(3);
		dcAdl3.setName(Constants.WITHOUT_TIMELY_FILING);
		dcAdl3.setCount(adjustmentLogWorkFlowrepository.getAdjustmentLogCount(0));	
		dcAdl3.setUrl("/adjustmentlog/1/0");
		dcAdl3.setFilterName1("withoutTimilyFiling");
		dcAdl3.setFilterValue1("0");
		
		dashboardCountList = new ArrayList<>();
		dashboardCountList.add(dcAdl1);
		dashboardCountList.add(dcAdl2);
		dashboardCountList.add(dcAdl3);
		dsAdl.setChildren(dashboardCountList);

		
	      //	Adjustment Log (Timely Filing)
		DashboardItem dsAltf = new DashboardItem();
		dsAltf.setId(10);
		dsAltf.setName(Constants.ADJUSTMENT_LOG_TIMELY_FILING);
		DashboardCount dcAltf1 = new DashboardCount();
		dcAltf1.setId(1);
		dcAltf1.setName(Constants.APPROVED);
		dcAltf1.setCount(adjustmentLogWorkFlowrepository.getAdjustmentLogCountTimilyFiling (1,1));
		dcAltf1.setUrl("/adjustmentlog/1/1/1");
		dcAltf1.setFilterName1("withoutTimilyFiling");
		dcAltf1.setFilterValue1("1");
		dcAltf1.setFilterName2("workFlowStatus");
		dcAltf1.setFilterValue2("1");
		DashboardCount dcAltf2 = new DashboardCount();
		dcAltf2.setId(2);
		dcAltf2.setName(Constants.ESCALATE);
		dcAltf2.setCount(adjustmentLogWorkFlowrepository.getAdjustmentLogCountTimilyFiling (1,3));
		dcAltf2.setUrl("/adjustmentlog/1/1/3");
		dcAltf2.setFilterName1("withoutTimilyFiling");
		dcAltf2.setFilterValue1("1");
		dcAltf2.setFilterName2("workFlowStatus");
		dcAltf2.setFilterValue2("3");
		
		DashboardCount dcAltf3 = new DashboardCount();
		dcAltf3.setId(3);
		dcAltf3.setName(Constants.REJECT);
		dcAltf3.setCount(adjustmentLogWorkFlowrepository.getAdjustmentLogCountTimilyFiling (1,2));
		dcAltf3.setUrl("/adjustmentlog/1/1/2");
		dcAltf3.setFilterName1("withoutTimilyFiling");
		dcAltf3.setFilterValue1("1");
		dcAltf3.setFilterName2("workFlowStatus");
		dcAltf3.setFilterValue2("2");
		
		DashboardCount dcAltf4 = new DashboardCount();
		dcAltf4.setId(4);
		dcAltf4.setName(Constants.CLOSED);
		dcAltf4.setCount(adjustmentLogWorkFlowrepository.getAdjustmentLogCountTimilyFiling(1,4));
		dcAltf4.setUrl("/adjustmentlog/1/1/4");
		dcAltf4.setFilterName1("withoutTimilyFiling");
		dcAltf4.setFilterValue1("1");
		dcAltf4.setFilterName2("workFlowStatus");
		dcAltf4.setFilterValue2("4");
		
		dashboardCountList = new ArrayList<>();
		dashboardCountList.add(dcAltf1);
		dashboardCountList.add(dcAltf2);
		dashboardCountList.add(dcAltf3);
		dashboardCountList.add(dcAltf4);
		dsAltf.setChildren(dashboardCountList);
				
		   //	Adjustment Log (Without Timely Filing)
		DashboardItem dsAlwtf = new DashboardItem();
		dsAlwtf.setId(11);
		dsAlwtf.setName(Constants.ADJUSTMENT_LOG_WITHOUT_TIMELY_FILING);
		DashboardCount dcAlwtf1 = new DashboardCount();
		dcAlwtf1.setId(1);
		dcAlwtf1.setName(Constants.APPROVED);
		dcAlwtf1.setCount(adjustmentLogWorkFlowrepository.getAdjustmentLogCountTimilyFiling (0,1));
	//  dcAlwtf1.setUrl("/productivityList/1/0/1");
		dcAlwtf1.setUrl("/adjustmentlog/1/0/1");
		dcAlwtf1.setFilterName1("withoutTimilyFiling");
		dcAlwtf1.setFilterValue1("0");
		dcAlwtf1.setFilterName2("workFlowStatus");
		dcAlwtf1.setFilterValue2("1");
		
		DashboardCount dcAlwtf2 = new DashboardCount();
		dcAlwtf2.setId(2);
		dcAlwtf2.setName(Constants.ESCALATE);
		dcAlwtf2.setCount(adjustmentLogWorkFlowrepository.getAdjustmentLogCountTimilyFiling (0,3));
		dcAlwtf2.setUrl("/adjustmentlog/1/0/3");
		dcAlwtf2.setFilterName1("withoutTimilyFiling");
		dcAlwtf2.setFilterValue1("0");
		dcAlwtf2.setFilterName2("workFlowStatus");
		dcAlwtf2.setFilterValue2("3");
		
		DashboardCount dcAlwtf3 = new DashboardCount();
		dcAlwtf3.setId(3);
		dcAlwtf3.setName(Constants.REJECT);
		dcAlwtf3.setCount(adjustmentLogWorkFlowrepository.getAdjustmentLogCountTimilyFiling (0,2));
		dcAlwtf3.setUrl("/adjustmentlog/1/0/2"); 
		dcAlwtf3.setFilterName1("withoutTimilyFiling");
		dcAlwtf3.setFilterValue1("0");
		dcAlwtf3.setFilterName2("workFlowStatus");
		dcAlwtf3.setFilterValue2("2");
		
		DashboardCount dcAlwtf4 = new DashboardCount();
		dcAlwtf4.setId(4);
		dcAlwtf4.setName(Constants.CLOSED);
		dcAlwtf4.setCount(adjustmentLogWorkFlowrepository.getAdjustmentLogCountTimilyFiling(0,4));
		dcAlwtf4.setUrl("/adjustmentlog/1/0/4");
		dcAlwtf4.setFilterName1("withoutTimilyFiling");
		dcAlwtf4.setFilterValue1("0");
		dcAlwtf4.setFilterName2("workFlowStatus");
		dcAlwtf4.setFilterValue2("4");
		
		dashboardCountList = new ArrayList<>();
		dashboardCountList.add(dcAlwtf1);
		dashboardCountList.add(dcAlwtf2);
		dashboardCountList.add(dcAlwtf3);
		dashboardCountList.add(dcAlwtf4);
		dsAlwtf.setChildren(dashboardCountList);
				
			


		
		// Payment Posting OffsetLog	
		DashboardItem dsPpolg = new DashboardItem();
		dsPpolg .setId(12);
		dsPpolg .setName(Constants.PAYMENT_POSTING_OFFSET_LOG);
		DashboardCount dcPpolg1 = new DashboardCount();
		dcPpolg1.setId(1);
		dcPpolg1.setName(Constants.OFFSETS_PENDING);
		//earlier //dcPpolg1.setCount(paymentPostingLogWorkFlowRepository.getPaymentPostingCountByStatusAndOffset("Pending",true));
		//dcPpolg1.setCount(paymentPostingLogWorkFlowRepository.getPaymentPostingCountByStatusAndOffset("Pending",false));
		dcPpolg1.setCount(paymentPostingLogWorkFlowRepository.getPaymentPostingCountByStatusAndOffset("Pending","1"));
		//dcPpolg1.setUrl("/productivityList/3/1/1");
		dcPpolg1.setUrl("/paymentposting/5/1/1");
		dcPpolg1.setFilterName1("status");
		dcPpolg1.setFilterValue1("Pending");
		dcPpolg1.setFilterName2("offSet");
		dcPpolg1.setFilterValue2("1");
		
		DashboardCount dcPpolg2 = new DashboardCount();
		dcPpolg2.setId(2);
		dcPpolg2.setName(Constants.OFFSETS_APPROVED);
		//dcPpolg2.setCount(paymentPostingLogWorkFlowRepository.getPaymentPostingCountByStatusAndOffset("Approve",true));
		dcPpolg2.setCount(paymentPostingLogWorkFlowRepository.getPaymentPostingCountByStatusAndOffset("Approved","1"));
		dcPpolg2.setUrl("/paymentposting/5/1/0");
		dcPpolg2.setFilterName1("status");
		dcPpolg2.setFilterValue1("Approve");
		dcPpolg2.setFilterName2("offSet");
		dcPpolg2.setFilterValue2("1");
		
		DashboardCount dcPpolg3 = new DashboardCount();
		dcPpolg3.setId(3);
		dcPpolg3.setName(Constants.OFFSETS_COMPLETED);
		//dcPpolg2.setCount(paymentPostingLogWorkFlowRepository.getPaymentPostingCountByStatusAndOffset("Approve",true));
		dcPpolg3.setCount(paymentPostingLogWorkFlowRepository.getPaymentPostingCountByStatusAndOffset("completed","1"));
		dcPpolg3.setUrl("/paymentposting/5/1/2");
		dcPpolg3.setFilterName1("status");
		dcPpolg3.setFilterValue1("Completed");
		dcPpolg3.setFilterName2("offSet");
		dcPpolg3.setFilterValue2("1");
		
		dashboardCountList = new ArrayList<>();
		dashboardCountList.add(dcPpolg1);
		dashboardCountList.add(dcPpolg2);
		dashboardCountList.add(dcPpolg3);
		dsPpolg.setChildren(dashboardCountList);

		
		
		
		// Payment Posting Log
		DashboardItem dsPplg = new DashboardItem();
		dsPplg.setId(13);
		dsPplg.setName(Constants.PAYMENT_POSTING_LOG);
        DashboardCount dcPplg1 = new DashboardCount();
		dcPplg1.setId(1);
		dcPplg1.setName(Constants.PENDING);
//		dcPplg1.setUrl("/productivityList/3/0/1");
		dcPplg1.setUrl("/paymentposting/5/0/1");
		//dcPplg1.setCount(paymentPostingLogWorkFlowRepository.getPaymentPostingCountBystatus("Pending"));
		dcPplg1.setCount(paymentPostingLogWorkFlowRepository.getPaymentPostingCountByStatusAndOffset("Pending","0"));

		dcPplg1.setFilterName1("status");
		dcPplg1.setFilterValue1("Pending");
		
		DashboardCount dcPplg2 = new DashboardCount();
		dcPplg2.setId(2);
		dcPplg2.setName(Constants.REJECTED);
		//dcPplg2.setCount(paymentPostingLogWorkFlowRepository.getPaymentPostingCountBystatus("Reject"));
		dcPplg2.setCount(paymentPostingLogWorkFlowRepository.getPaymentPostingCountByStatusAndOffset("Rejected","0"));

		dcPplg2.setUrl("/paymentposting/5/0/3");
		//dcPplg2.setUrl("/paymentposting/5/0/1");
		dcPplg2.setFilterName1("status");
		dcPplg2.setFilterValue1("Reject");
		
		DashboardCount dcPplg3 = new DashboardCount();
		dcPplg3.setId(3);
		dcPplg3.setName(Constants.APPROVED);
		//dcPplg3.setCount(paymentPostingLogWorkFlowRepository.getPaymentPostingCountBystatus("Approve"));
		dcPplg3.setCount(paymentPostingLogWorkFlowRepository.getPaymentPostingCountByStatusAndOffset("Approved","0"));

		dcPplg3.setUrl("/paymentposting/5/0/0");
		//dcPplg3.setUrl("/paymentposting/5/0/2");
		dcPplg3.setFilterName1("status");
		dcPplg3.setFilterValue1("Approve");
		
		DashboardCount dcPplg4 = new DashboardCount();
		dcPplg4.setId(4);
		dcPplg4.setName(Constants.COMPLETED);
		//dcPplg4.setCount(paymentPostingLogWorkFlowRepository.getPaymentPostingCountBystatus("Completed"));
		dcPplg4.setCount(paymentPostingLogWorkFlowRepository.getPaymentPostingCountByStatusAndOffset("completed","0"));

		dcPplg4.setUrl("/paymentposting/5/0/2");
		//dcPplg4.setUrl("/paymentposting/5/0/3");
		dcPplg3.setFilterName1("status");
		dcPplg3.setFilterValue1("Completed");
		
		dashboardCountList = new ArrayList<>();
		dashboardCountList.add(dcPplg1);
		dashboardCountList.add(dcPplg2);
		dashboardCountList.add(dcPplg3);
		dashboardCountList.add(dcPplg4);
		dsPplg .setChildren(dashboardCountList);
		
		
		//Accounts Receivable (AR) Team Productivity & Process
		
		DashboardItem dsArtpp = new DashboardItem();
		dsArtpp.setId(14);
		dsArtpp.setName(Constants.ACCOUNTS_RECEIVABLE_TEAM_PRODUCTIVITY_AND_PROCESS);
		DashboardCount dcArtpp1 = new DashboardCount();
		dcArtpp1.setId(1);
		dcArtpp1.setName(Constants.LIST_AR_PRODUCTIVITY);
		dcArtpp1.setCount(repository.countById());
		dcArtpp1.setUrl("/productivityList");
		
		DashboardCount dcArtpp2 = new DashboardCount();
		dcArtpp2.setId(2);
		dcArtpp2.setName(Constants.ADJUSTMENT_LOG);
		dcArtpp2.setCount(adjustmentLogWorkFlowrepository.countById());
		dcArtpp2.setUrl("/productivityList/1");
		
		DashboardCount dcArtpp3 = new DashboardCount();
		dcArtpp3.setId(3);
		// have set the name to CODING CRRECTION not CODING_CORRECTION_LOG here only
		dcArtpp3.setName(Constants.CODING_CORRECTION_LOG);
		dcArtpp3.setCount(codingCorrectionLogWorkFlowRepository.countById());
		dcArtpp3.setUrl("/productivityList/2");
		
		DashboardCount dcArtpp4 = new DashboardCount();
		dcArtpp4.setId(4);
		dcArtpp4.setName(Constants.PAYMENT_POSTING_LOG);
		dcArtpp4.setCount(paymentPostingLogWorkFlowRepository.countById());
		dcArtpp4.setUrl("/productivityList/5");
		
		DashboardCount dcArtpp5 = new DashboardCount();
		dcArtpp5.setId(5);
		dcArtpp5.setName(Constants.REFUND_REQUEST_LIST_ALL);
		dcArtpp5.setUrl("/productivityList/8");
		dcArtpp5.setCount(refundRequestWorkFlowRepository.countById());

		DashboardCount dcArtpp6 = new DashboardCount();
		dcArtpp6.setId(6);
		dcArtpp6.setName("Query to TL");
		dcArtpp6.setCount(queryToTLRepository.countById());
		dcArtpp6.setUrl("/productivityList/7");

		//		DashboardCount dcArtpp6 = new DashboardCount();
//		dcArtpp6.setId(6);
//		dcArtpp6.setName(Constants.QUERY_TO_TL);
//		dcArtpp6.setUrl("/productivityList/7");
//		dcArtpp6.setCount(queryToTLRepository.countById());
//		DashboardCount dcArtpp7 = new DashboardCount();
//		dcArtpp7.setId(7);
//		dcArtpp7.setName(Constants.NEXT_FOLLOW_UP_DATE);
//		dcArtpp7.setCount(repository.getFollowupCount(3));
//		dcArtpp7.setUrl("/productivityList/8");
		//DashboardItem dsQrTL = new DashboardItem();
		//		dsQrTL.setId(15);
		//		dsQrTL.setName(Constants.QUERY_TO_TL);
		//		DashboardCount dcQrTL1 = new DashboardCount();
		//		dcQrTL1.setId(1);
		//		dcQrTL1.setName("Query to TL");
		//		dcQrTL1.setCount(queryToTLRepository.countById());
		//		dcQrTL1.setUrl("/query-to-tl/7");
		//

		DashboardCount dcArtpp7 = new DashboardCount();
		dcArtpp7.setId(7);
		dcArtpp7.setName(Constants.OPEN);
		dcArtpp7.setCount(queryToTLRepository.countByStatus(String.valueOf(1)));
		dcArtpp7.setUrl("/productivityList/7/1");
//		dcQrTL2.setFilterName1("withoutTimilyFiling");
//		dcQrTL2.setFilterValue1("1");
//		dcQrTL2.setFilterName2("workFlowStatus");
//		dcQrTL2.setFilterValue2("3");
//
		DashboardCount dcArtpp8 = new DashboardCount();
		dcArtpp8.setId(8);
		dcArtpp8.setName(Constants.BACK_TO_TEAM);
		dcArtpp8.setCount(queryToTLRepository.countByStatus(String.valueOf(2)));
		dcArtpp8.setUrl("/productivityList/7/2");
//		dcQrTL3.setFilterName1("withoutTimilyFiling");
//		dcQrTL3.setFilterValue1("1");
//		dcQrTL3.setFilterName2("workFlowStatus");
//		dcQrTL3.setFilterValue2("2");
//
		DashboardCount dcArtpp9 = new DashboardCount();
		dcArtpp9.setId(4);
		dcArtpp9.setName(Constants.CLOSED);
		dcArtpp9.setCount(queryToTLRepository.countByStatus(String.valueOf(3)));
		dcArtpp9.setUrl("/productivityList/7/3");
//		dcQrTL4.setFilterName1("withoutTimilyFiling");
//		dcQrTL4.setFilterValue1("1");
//		dcQrTL4.setFilterName2("workFlowStatus");
//		dcQrTL4.setFilterValue2("4");
//


		
		dashboardCountList = new ArrayList<>();
		dashboardCountList.add(dcArtpp1);
		dashboardCountList.add(dcArtpp2);
		dashboardCountList.add(dcArtpp3);
		dashboardCountList.add(dcArtpp4);
		dashboardCountList.add(dcArtpp5);
		dashboardCountList.add(dcArtpp6);
		dashboardCountList.add(dcArtpp7);
		dashboardCountList.add(dcArtpp8);
		dashboardCountList.add(dcArtpp9);
		dsArtpp .setChildren(dashboardCountList);

		
		
//   // Query To Tl		
		DashboardItem dsQrTL = new DashboardItem();
		dsQrTL.setId(15);
		dsQrTL.setName(Constants.QUERY_TO_TL);
		DashboardCount dcQrTL1 = new DashboardCount();
		dcQrTL1.setId(1);
		dcQrTL1.setName("Query to TL");
		dcQrTL1.setCount(queryToTLRepository.countById());
		dcQrTL1.setUrl("/query-to-tl/7");
		
		DashboardCount dcQrTL2 = new DashboardCount();
		dcQrTL2.setId(2);
		dcQrTL2.setName(Constants.OPEN);
		dcQrTL2.setCount(queryToTLRepository.countByStatus(String.valueOf(1)));
		dcQrTL2.setUrl("/query-to-tl/7/1");
//		dcQrTL2.setFilterName1("withoutTimilyFiling");
//		dcQrTL2.setFilterValue1("1");
//		dcQrTL2.setFilterName2("workFlowStatus");
//		dcQrTL2.setFilterValue2("3");
//		
		DashboardCount dcQrTL3 = new DashboardCount();
		dcQrTL3.setId(3);
		dcQrTL3.setName(Constants.BACK_TO_TEAM);
		dcQrTL3.setCount(queryToTLRepository.countByStatus(String.valueOf(2)));
		dcQrTL3.setUrl("/query-to-tl/7/2");
//		dcQrTL3.setFilterName1("withoutTimilyFiling");
//		dcQrTL3.setFilterValue1("1");
//		dcQrTL3.setFilterName2("workFlowStatus");
//		dcQrTL3.setFilterValue2("2");
//		
		DashboardCount dcQrTL4 = new DashboardCount();
		dcQrTL4.setId(4);
		dcQrTL4.setName(Constants.CLOSED);
		dcQrTL4.setCount(queryToTLRepository.countByStatus(String.valueOf(3)));
		dcQrTL4.setUrl("/query-to-tl/7/3");
//		dcQrTL4.setFilterName1("withoutTimilyFiling");
//		dcQrTL4.setFilterValue1("1");
//		dcQrTL4.setFilterName2("workFlowStatus");
//		dcQrTL4.setFilterValue2("4");
//		
		dashboardCountList = new ArrayList<>();
		dashboardCountList.add(dcQrTL1);
		dashboardCountList.add(dcQrTL2);
		dashboardCountList.add(dcQrTL3);
		dashboardCountList.add(dcQrTL4);
		dsQrTL.setChildren(dashboardCountList);
//		DashboardCount dcArtpp6 = new DashboardCount();
//		dcArtpp6.setId(6);
//		dcArtpp6.setName(Constants.QUERY_TO_TL);
//		dcArtpp6.setUrl("/productivityList/7");
//		dcArtpp6.setCount(queryToTLRepository.countById());	
//		DashboardCount dcArtpp7 = new DashboardCount();

////	//Hourly Projects Productivity
//	DashboardItem dsHpp = new DashboardItem();
//	dsHpp.setId(2);
//	dsHpp.setName(Constants.HOURLY_PROJECTS_PRODUCTIVITY);
//	DashboardCount dcHpp = new DashboardCount();
//	dcHpp.setId(1);
//	dcHpp.setName("Hourly List");
////	dcHpp.setCount(hourlyTaskRepository.count());
//	dcHpp.setCount(hourlyTaskRepository.countByDeleted());
//	dcHpp.setUrl("/hourlytask");
//	dashboardCountList = new ArrayList<>();
//	dashboardCountList.add(dcHpp);
//	dsHpp.setChildren(dashboardCountList);	
		
		
		// Offset Reference & Postings
		DashboardItem dsOrp = new DashboardItem();
		dsOrp.setId(16);
		dsOrp.setName(Constants.OFFSET_REFERENCE_POSTINGS);
		
		long pendingCount = paymentProductivityOffsetRepository.getPaymentProductivityOffsetCountByStatus("Pending");
		long arStepCount = paymentProductivityOffsetRepository.getPaymentProductivityOffsetCountByStatus("AR Step Completed");
		long offsetResoleCount = paymentProductivityOffsetRepository.getPaymentProductivityOffsetCountByStatus("Offset Resolve");
		
		DashboardCount dcOrpc1 = new DashboardCount();
		dcOrpc1.setId(1);
		dcOrpc1.setName(Constants.OFFSET_REFERENCE_LIST);
		dcOrpc1.setCount(pendingCount+arStepCount+offsetResoleCount);
		dcOrpc1.setUrl("/payment-productivity-offset-reference");
		DashboardCount dcOrpc2 = new DashboardCount();
		dcOrpc2.setId(2);
		dcOrpc2.setName(Constants.OFFSET_REFERENCE_PENDING);
		dcOrpc2.setCount(pendingCount);
		dcOrpc2.setUrl("/payment-productivity-offset-reference/0");
		DashboardCount dcOrpc3 = new DashboardCount();
		dcOrpc3.setId(3);
		dcOrpc3.setName(Constants.OFFSET_REFERENCE_AR_STEP_COMPLETED);
		dcOrpc3.setCount(arStepCount);
		dcOrpc3.setUrl("/payment-productivity-offset-reference/1");
		DashboardCount dcOrpc4 = new DashboardCount();
		dcOrpc4.setId(4);
		dcOrpc4.setName(Constants.OFFSET_REFERENCE_OFFSET_RESOLVED);
		dcOrpc4.setCount(offsetResoleCount);
		dcOrpc4.setUrl("/payment-productivity-offset-reference/2");
		
//		dashboardCountList = new ArrayList<>();
//		List<DashboardCount> dashboardCountSubList = new ArrayList<>();
//		dashboardCountSubList.add(dcOrpc1);
//		dashboardCountSubList.add(dcOrpc2);
//		dashboardCountSubList.add(dcOrpc3);
//		dashboardCountSubList.add(dcOrpc3);
//		dcOrp.setChildren(dashboardCountSubList);
//		dashboardCountList.add(dcOrp);
		dashboardCountList = new ArrayList<>();
		dashboardCountList.add(dcOrpc1);
		dashboardCountList.add(dcOrpc2);
		dashboardCountList.add(dcOrpc3);
		dashboardCountList.add(dcOrpc4);
		dsOrp.setChildren(dashboardCountList);
//		
		
	//// Refund Request
//				DashboardItem dsRr = new DashboardItem();
//				dsRr.setId(17);
//				dsRr.setName(Constants.REFUND_REQUEST);
//		      DashboardCount dcRr1 = new DashboardCount();
//				dcRr1.setId(1);
//				dcRr1.setName(Constants.PENDING);
//				dcRr1.setUrl("/refundRequest/8/1");
//				dcRr1.setCount(refundRequestWorkFlowRepository.getRefundRequestCountByStatus(1));
//
//				DashboardCount dcRr2 = new DashboardCount();
//				dcRr2.setId(2);
//				dcRr2.setName(Constants.REJECTED);
//				dcRr2.setCount(refundRequestWorkFlowRepository.getRefundRequestCountByStatus(3));
//				dcRr2.setUrl("/refundRequest/8/3");
//
//
//				DashboardCount dcRr3 = new DashboardCount();
//				dcRr3.setId(3);
//				dcRr3.setName(Constants.APPROVED);
//				dcRr3.setCount(refundRequestWorkFlowRepository.getRefundRequestCountByStatus(0));
//				dcRr3.setUrl("/refundRequest/8/0");
//
//		//		dcRr3.setFilterName1("status");
//		//		dcRr3.setFilterValue1("Approve");
//		//
//				DashboardCount dcRr4 = new DashboardCount();
//				dcRr4.setId(4);
//				dcRr4.setName(Constants.COMPLETED);
//				dcRr4.setCount(refundRequestWorkFlowRepository.getRefundRequestCountByStatus(2));
//				dcRr4.setUrl("/refundRequest/8/2");
//
//		//		dcRr4.setFilterName1("status");
//		//		dcRr4.setFilterValue1("Completed");
//
//			DashboardCount dcRr5 = new DashboardCount();
//				dcRr5.setId(5);
//				dcRr5.setName(Constants.REFUND_REQUEST_LIST_ALL);
//				dcRr5.setUrl("/refundRequest/8");
//				dcRr5.setCount(refundRequestWorkFlowRepository.countById());
//				dashboardCountList = new ArrayList<>();
//				dashboardCountList.add(dcRr1);
//				dashboardCountList.add(dcRr2);
//				dashboardCountList.add(dcRr3);
//				dashboardCountList.add(dcRr4);
//		      dashboardCountList.add(dcRr5);
//				dsRr .setChildren(dashboardCountList);


		// Demo, CE & Coding Validation Team Prod. & Process
		DashboardItem dsDcc= new DashboardItem();
		dsDcc.setId(18);
		dsDcc.setName(Constants.DEMO_CE_CODING_VAL_PROCESS);
		DashboardCount dcDcc1 = new DashboardCount();
		dcDcc1.setId(1);
		dcDcc1.setName(Constants.LIST_PRODUCTIVITY);
		dcDcc1.setCount(chargeProductivityRepository.countById());
		dcDcc1.setUrl("/CE-productivity/0");
		DashboardCount dcDcc2 = new DashboardCount();
		dcDcc2.setId(2);
		dcDcc2.setName(Constants.ON_HOLD);
		dcDcc2.setCount(chargeProductivityRepository.countonHold());
		dcDcc2.setUrl("/CE-productivity/0/true");
		DashboardCount dcDcc3 = new DashboardCount();
		dcDcc3.setId(3);
		dcDcc3.setName(Constants.REJECTED_LOGS);
		dcDcc3.setCount(chargeProdRejectionRepository.countById());
		dcDcc3.setUrl("/rejectedlogs");
		DashboardCount dcDcc4 = new DashboardCount();
		dcDcc4.setId(4);
		dcDcc4.setName(Constants.NEW_REJECTIONS);
		//dcDcc4.setCount(paymentProductivityRepository.getProductivityCountById(4));
		dcDcc4.setCount(chargeProdRejectionRepository.countByNewRejection());
		//dcDcc4.setUrl("/rejectedlogs?newRejections=true");
		dcDcc4.setUrl("/rejectedlogs/newRejection/true");
		DashboardCount dcDcc5 = new DashboardCount();
		dcDcc5.setId(5);
		dcDcc5.setName(Constants.FIRST_REQUEST_DUE);
		//dcDcc5.setCount(paymentProductivityRepository.getProductivityCountById(5));
		dcDcc5.setCount(chargeProdRejectionRepository.countByFirstRequestDueRecord());
		dcDcc5.setUrl("/rejectedlogs/firstReqDue/1");
		DashboardCount dcDcc6 = new DashboardCount();
		dcDcc6.setId(6);
		dcDcc6.setName(Constants.NUMBER_OF_FIRST_REQUESTS);
	//	dcDcc6.setCount(paymentProductivityRepository.getProductivityCountById(6));
		dcDcc6.setCount(chargeProdRejectionRepository.countByFirstRequestRecord());
		dcDcc6.setUrl("/rejectedlogs/noFirstReq/1");
		DashboardCount dcDcc7 = new DashboardCount();
		dcDcc7.setId(7);
		dcDcc7.setName(Constants.SECOND_REQUEST_DUE);
	//	dcDcc7.setCount(paymentProductivityRepository.getProductivityCountById(7));
		dcDcc7.setCount(chargeProdRejectionRepository.countBySecondRequestDueRecord());
		dcDcc7.setUrl("/rejectedlogs/secondReqDue/2");
		DashboardCount dcDcc8 = new DashboardCount();
		dcDcc8.setId(8);
		dcDcc8.setName(Constants.NUMBER_OF_SECOND_REQUESTS);
	//	dcDcc8.setCount(paymentProductivityRepository.getProductivityCountById(8));
		dcDcc8.setCount(chargeProdRejectionRepository.countBySecondRequestRecord());
		dcDcc8.setUrl("/rejectedlogs/noSecondReq/2");
		DashboardCount dcDcc9 = new DashboardCount();
		dcDcc9.setId(9);
		dcDcc9.setName(Constants.RESOLVED_REJECTIONS);
	//	dcDcc9.setCount(paymentProductivityRepository.getProductivityCountById(9));
		dcDcc9.setCount(chargeProdRejectionRepository.countByResolvedRejections());
		dcDcc9.setUrl("/rejectedlogs/resolvedRej/Resolved");
		DashboardCount dcDcc10 = new DashboardCount();
		dcDcc10.setId(10);
		dcDcc10.setName(Constants.RESOLVED_REJECTIONS_WITH_DUMMY_CPT);
	//	dcDcc10.setCount(paymentProductivityRepository.getProductivityCountById(10));
		dcDcc10.setCount(chargeProdRejectionRepository.countByResolvedRejectionsWithDummyCPT());
		dcDcc10.setUrl("/rejectedlogs/dummyCPT/true");
		DashboardCount dcDcc11 = new DashboardCount();
		dcDcc11.setId(11);
		dcDcc11.setName(Constants.COMPLETED_REJECTIONS);
		//dcDcc11.setCount(paymentProductivityRepository.getProductivityCountById(11));
		dcDcc11.setCount(chargeProdRejectionRepository.countByCompletedRejections());
		dcDcc11.setUrl("/rejectedlogs/completedRej/Completed");


		dashboardCountList = new ArrayList<>();
		dashboardCountList.add(dcDcc1);
		dashboardCountList.add(dcDcc2);
		dashboardCountList.add(dcDcc3);
		dashboardCountList.add(dcDcc4);
		dashboardCountList.add(dcDcc5);
		dashboardCountList.add(dcDcc6);
		dashboardCountList.add(dcDcc7);
		dashboardCountList.add(dcDcc8);
		dashboardCountList.add(dcDcc9);
		dashboardCountList.add(dcDcc10);
		dashboardCountList.add(dcDcc11);
		dsDcc.setChildren(dashboardCountList);
		
		
		
		
		
		//	SET DASHBOARD ITEMS
		
//		dashboardItemList.add(dsMaster);
		dashboardItemList.add(dsHpp);
		dashboardItemList.add(dsPtp);
		dashboardItemList.add(dsPpwERA);
		dashboardItemList.add(dsPpwNonERA);
		dashboardItemList.add(dsPpwCAP);
		dashboardItemList.add(dsCcl);
		dashboardItemList.add(dsOrp);
		dashboardItemList.add(dsRrl);
		dashboardItemList.add(dsAdl);
//		dashboardItemList.add(dsAltf);
		dashboardItemList.add(dsAlwtf);
		dashboardItemList.add(dsPpolg);
		dashboardItemList.add(dsPplg);
		//dashboardItemList.add(dsRr);
		dashboardItemList.add(dsArtpp);
		dashboardItemList.add(dsQrTL);
		dashboardItemList.add(dsDcc);
		//Prateek
//		dashboardItemList.add(dsDcc);
//		dashboardItemList.add(dsArtpp);
//		dashboardItemList.add(dsPtp);
//		dashboardItemList.add(dsAdl);
//
//		dashboardItemList.add(dsPpolg);
//
//		dashboardItemList.add(dsAltf);
//		dashboardItemList.add(dsPpwERA);
//		dashboardItemList.add(dsCcl);
//		dashboardItemList.add(dsPpwNonERA);
//		dashboardItemList.add(dsPplg);
//		dashboardItemList.add(dsPpwCAP);
//		dashboardItemList.add(dsOrp);
//		dashboardItemList.add(dsRrl);
//		dashboardItemList.add(dsQrTL);
//		dashboardItemList.add(dsHpp);


//		dashboardItemList.add(dsAlwtf);






//		dashboardItemList.add(dsAdl);
//		dashboardItemList.add(dsAltf);



		//dashboardItemList.add(dsRr);



		log.debug("Dashboard List :"+dashboardItemList);
		
		return dashboardItemList;
	}

	
	public ByteArrayInputStream load() {
		
	

		Pageable pageable = PageRequest.of(0, 5);
		//Page<ArProductivityEntity> allArData = repository.getAll(pageable);
		List<ArProductivityEntity>  arProductivityList =  repository.getAll(pageable).getContent();
		ByteArrayInputStream in = ExcelHelper.loadExcelRecords(arProductivityList);
	    return in;
	}
//	public ByteArrayInputStream load() {
//		
//		
//
//		Pageable pageable = PageRequest.of(0, 5);
//		//Page<ArProductivityEntity> allArData = repository.getAll(pageable);
//		List<ArProductivityEntity>  arProductivityList =  repository.getAll(pageable).getContent();
//		ByteArrayInputStream in = ExcelHelper.loadExcelRecords(arProductivityList);
//	    return in;
//	}
	


//	public boolean findAll(String patientAccountNumber ){
//
//		Iterable<ArProductivityEntity> listArPro = repository.findAll();
//		List<ArProductivityEntity> actualList = StreamSupport
//				.stream(listArPro.spliterator(), false)
//				.collect(Collectors.toList());
//
//
//		return (actualList.stream()
//				.filter(row -> row.getPatientAccountNumber().equals(patientAccountNumber)
//						&& row.getPatientAccountNumber().contains(patientAccountNumber)).count() == 0);
//
//	}
}
