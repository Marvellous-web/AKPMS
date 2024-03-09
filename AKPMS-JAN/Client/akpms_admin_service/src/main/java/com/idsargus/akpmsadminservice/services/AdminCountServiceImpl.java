package com.idsargus.akpmsadminservice.services;

import com.idsargus.akpmsadminservice.repository.*;
import com.idsargus.akpmsadminservice.wspojo.DashboardCount;
import com.idsargus.akpmsadminservice.wspojo.DashboardItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.idsargus.akpmsadminservice.util.Constants;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//import static javax.swing.text.rtf.RTFAttributes.BooleanAttribute.True;

@Slf4j
@Component
public class AdminCountServiceImpl {

	@Autowired
	private DoctorDataRestRepository doctorDataRestRepository;

	@Autowired
	private DoctorCompanyDataRestRepository doctorCompanyDataRestRepository;
	@Autowired
	private DepartmentDataRestRepository departmentRepository;


	@Autowired
	private DoctorGroupDataRestRepository doctorGroupDataRestRepository;
	@Autowired
	private InsuranceDataRestRepository insuranceDataRestRepository;

	@Autowired
	private PaymentTypeDataRestRepository paymentTypeRepository;

	@Autowired
	private UserDataRestRepository userDataRestRepository;
	public List<DashboardItem> getDashboardCountList() {
		List<DashboardItem> dashboardItemList = new ArrayList<>();
		List<DashboardCount> dashboardCountList = new ArrayList<>();

		//Master Data Tables
		DashboardItem dsMaster = new DashboardItem();
		dsMaster.setId(1);
		dsMaster.setName(Constants.OTHER_STATISTICS);
		DashboardCount dcMaster1 = new DashboardCount();
		dcMaster1.setId(1);
		dcMaster1.setName("Total Doctors");
		//	dcMaster1.setCount(doctorDataRestRepository.count());
		dcMaster1.setCount(doctorDataRestRepository.countByIsDeleted());
		dcMaster1.setUrl("/doctor");
		DashboardCount dcMaster2 = new DashboardCount();
		dcMaster2.setId(2);
		dcMaster2.setName("Total Insurances");
		//	dcMaster2.setCount(insuranceDataRestRepository.count());
		dcMaster2.setCount(insuranceDataRestRepository.countByIsDeleted());
		dcMaster2.setUrl("/insurance");

		DashboardCount dcMaster3 = new DashboardCount();
		dcMaster3.setId(3);
		dcMaster3.setName("Total Payment Types");
		//dcMaster3.setCount(paymentTypeRepository.count());
		dcMaster3.setCount(paymentTypeRepository.countByIsDeleted());
		dcMaster3.setUrl("/paymenttype");


		DashboardCount dcMaster4 = new DashboardCount();
		dcMaster4.setId(4);
		dcMaster4.setName("Total Departments");
		//dcMaster3.setCount(paymentTypeRepository.count());
		dcMaster4.setCount(departmentRepository.countByIsDeleted());
		dcMaster4.setUrl("/department");

		DashboardCount dcMaster5 = new DashboardCount();
		dcMaster5.setId(5);
		dcMaster5.setName("Total Companies/Database");
		//dcMaster3.setCount(paymentTypeRepository.count());
		dcMaster5.setCount(doctorCompanyDataRestRepository.countAllDoctorCompany());
		dcMaster5.setUrl("/doctor/add-company");

		DashboardCount dcMaster6 = new DashboardCount();
		dcMaster6.setId(6);
		dcMaster6.setName("Total Groups");
		//dcMaster3.setCount(paymentTypeRepository.count());
		dcMaster6.setCount(doctorGroupDataRestRepository.countAllDoctorGroup());
		dcMaster6.setUrl("/doctor/add-group");


		dashboardCountList.add(dcMaster1);
		dashboardCountList.add(dcMaster2);
		dashboardCountList.add(dcMaster3);
		dashboardCountList.add(dcMaster4);
		dashboardCountList.add(dcMaster5);
		dashboardCountList.add(dcMaster6);
		dsMaster.setChildren(dashboardCountList);
//


		//doctorCompanyDataRestRepository.countAllDoctorCompany();
		//doctorGroupDataRestRepository.countAllDoctorGroup()

		// USER DETAILS
		DashboardItem dsUsr = new DashboardItem();
		dsUsr.setId(2);
		dsUsr.setName(Constants.USERS);
		DashboardCount dcUsr1 = new DashboardCount();
		dcUsr1.setId(1);
		dcUsr1.setName(Constants.TOTAL_USERS);
		dcUsr1.setCount(userDataRestRepository.countByEnable (true)+userDataRestRepository.countByEnable (false));
		dcUsr1.setUrl("/user");

		DashboardCount dcUsr2 = new DashboardCount();
		dcUsr2.setId(2);
		dcUsr2.setName(Constants.ACTIVE_USERS);
		dcUsr2.setCount(userDataRestRepository.countByEnable (true));
		dcUsr2.setUrl("/user/1");
//				dcUsr2.setFilterName1("");
//				dcUsr2.setFilterValue1("");
//				dcUsr2.setFilterName2("");
//				dcUsr2.setFilterValue2("");

		DashboardCount dcUsr3 = new DashboardCount();
		dcUsr3.setId(3);
		dcUsr3.setName(Constants.INACTIVE_USERS);
		dcUsr3.setCount(userDataRestRepository.countByEnable (false));
		dcUsr3.setUrl("/user/2");
//				dcUsr3.setFilterName1("");
//				dcUsr3.setFilterValue1("");
//				dcUsr3.setFilterName2("");
//				dcUsr3.setFilterValue2("");


		dashboardCountList = new ArrayList<>();
		dashboardCountList.add(dcUsr1);
		dashboardCountList.add(dcUsr2);
		dashboardCountList.add(dcUsr3);

		dsUsr.setChildren(dashboardCountList);
//
//
//      	SET DASHBOARD ITEMS
		dashboardItemList.add(dsUsr);
		dashboardItemList.add(dsMaster);

//
		log.debug("Dashboard List :"+dashboardItemList);
//
		return dashboardItemList;
	}
}
