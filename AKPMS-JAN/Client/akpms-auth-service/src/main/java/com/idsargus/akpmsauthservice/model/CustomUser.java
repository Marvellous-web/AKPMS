package com.idsargus.akpmsauthservice.model;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.User;

import com.idsargus.akpmsauthservice.entity.DepartmentEntity;
import com.idsargus.akpmsauthservice.entity.PermissionEntity;
import com.idsargus.akpmsauthservice.entity.UserEntity;
import com.idsargus.akpmsauthservice.util.Constants;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CustomUser extends User {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String email;
	private String role;
	private String firstName;
	private String lastName;

	private String address;
	private String contact;
	private Set<DepartmentEntity> departments;
	private Set<PermissionEntity> permissions;

	private List<UserMenu> userManu;
	private List<UserDashBoard> dashboardList;


	public CustomUser(UserEntity userEntity) {
		super(userEntity.getEmail(), userEntity.getPassword(), userEntity.getAuthorities());

		this.id = userEntity.getId().toString();
		this.name = userEntity.getFirstName() + " " + userEntity.getLastName();
		this.role = userEntity.getRole().getName();
		this.firstName= userEntity.getFirstName().toString();
		this.lastName = userEntity.getLastName().toString();
		this.address = userEntity.getAddress();
		this.contact = userEntity.getContact();
		this.email = userEntity.getEmail();
		this.departments = userEntity.getDepartments();
		this.permissions = userEntity.getPermissions();
		this.dashboardList = getDepartmentsWithChild(userEntity.getDepartments(), userEntity.getRole().getName(),userEntity.getPermissions() );
		this.userManu = getUserMenuByRole(userEntity.getRole().getName(), userEntity.getDepartments());


	}


	@SuppressWarnings("unchecked")
	public List<UserDashBoard> getDepartmentsWithChild(Set<DepartmentEntity> listDepartments, String roleName, Set<PermissionEntity> permissions){

		@SuppressWarnings("rawtypes")
		List<UserDashBoard> dashboardList = new CopyOnWriteArrayList();
		if(listDepartments.size() == 0){
			for(PermissionEntity permissionEntity : permissions){
				if(permissionEntity.getName().equals("Document Manager")
						|| permissionEntity.getName().equals("Document Manager Read Only")){
					UserDashBoard nonDepUserdashBoardChilds = new UserDashBoard();
					nonDepUserdashBoardChilds.setId(Integer.valueOf(this.getDepartmentsAndDashboardKey("Hourly Projects Productivity")));
					nonDepUserdashBoardChilds.setLinkName("Hourly Projects Productivity");
					dashboardList.add(nonDepUserdashBoardChilds);
				}
			}
		}


		for(DepartmentEntity childDept : listDepartments) {
//childDept.getName().equals("QA Section")  ------ > childDept.getName().equals("QA Department")
			if (childDept.getName().equals("QA Department")) {
				UserDashBoard userdashBoardChilds = new UserDashBoard();
				userdashBoardChilds.setId(Integer.valueOf(this.getDepartmentsAndDashboardKey("Hourly Projects Productivity")));
				userdashBoardChilds.setLinkName("Hourly Projects Productivity");
				dashboardList.add(userdashBoardChilds);
			}
			if (childDept.getName().equals("Accounting Department")) {
				UserDashBoard userdashBoardChilds = new UserDashBoard();
				userdashBoardChilds.setId(Integer.valueOf(this.getDepartmentsAndDashboardKey("Hourly Projects Productivity")));
				userdashBoardChilds.setLinkName("Hourly Projects Productivity");
				dashboardList.add(userdashBoardChilds);
			}

			UserDashBoard userdashBoardChilds1;
			UserDashBoard userdashBoardChilds2;
			UserDashBoard userdashBoardChilds3;
			UserDashBoard userdashBoardChilds4;
			UserDashBoard userdashBoardChilds6;
			UserDashBoard userdashBoardChilds7;
			UserDashBoard userdashBoardChilds8;
			if (childDept.getName().equals("Accounts Receivable Department")) {
				UserDashBoard userdashBoardChilds = new UserDashBoard();
				userdashBoardChilds.setId(Integer.valueOf(this.getDepartmentsAndDashboardKey("Hourly Projects Productivity")));
				userdashBoardChilds.setLinkName("Hourly Projects Productivity");
				dashboardList.add(userdashBoardChilds);
				userdashBoardChilds1 = new UserDashBoard();
				userdashBoardChilds1.setId(Integer.valueOf(this.getDepartmentsAndDashboardKey("Coding Correction Log")));
				userdashBoardChilds1.setLinkName("Coding Correction Log");
				dashboardList.add(userdashBoardChilds1);
				userdashBoardChilds2 = new UserDashBoard();
				userdashBoardChilds2.setId(Integer.valueOf(this.getDepartmentsAndDashboardKey("Offset Reference & Postings")));
				userdashBoardChilds2.setLinkName("Offset Reference & Postings");
				dashboardList.add(userdashBoardChilds2);
				userdashBoardChilds3 = new UserDashBoard();
				userdashBoardChilds3.setId(Integer.valueOf(this.getDepartmentsAndDashboardKey("Accounts Receivable (AR) Team Productivity & Process Workflow")));
				userdashBoardChilds3.setLinkName("Accounts Receivable (AR) Team Productivity & Process Workflow");
				dashboardList.add(userdashBoardChilds3);
				userdashBoardChilds4 = new UserDashBoard();
				userdashBoardChilds4.setId(Integer.valueOf(this.getDepartmentsAndDashboardKey("Payment Posting Log")));
				userdashBoardChilds4.setLinkName("Payment Posting Log");
				dashboardList.add(userdashBoardChilds4);
				userdashBoardChilds6 = new UserDashBoard();
				userdashBoardChilds6.setId(Integer.valueOf(this.getDepartmentsAndDashboardKey("Rekey Request Log")));
				userdashBoardChilds6.setLinkName("Rekey Request Log");
				dashboardList.add(userdashBoardChilds6);
				userdashBoardChilds7 = new UserDashBoard();
				userdashBoardChilds7.setId(Integer.valueOf(this.getDepartmentsAndDashboardKey("Adjustment Log")));
				userdashBoardChilds7.setLinkName("Adjustment Log");
				dashboardList.add(userdashBoardChilds7);

				userdashBoardChilds8 = new UserDashBoard();
				userdashBoardChilds8.setId(Integer.valueOf(this.getDepartmentsAndDashboardKey("Master Data Tables")));
				userdashBoardChilds8.setLinkName("Master Data Tables");
				dashboardList.add(userdashBoardChilds8);

				UserDashBoard userdashBoardChilds9 = new UserDashBoard();
				userdashBoardChilds9.setId(Integer.valueOf(this.getDepartmentsAndDashboardKey("Adjustment Log Without Timely Filing")));
				userdashBoardChilds9.setLinkName("Adjustment Log Without Timely Filing");
				dashboardList.add(userdashBoardChilds9);

				UserDashBoard userdashBoardChilds10 = new UserDashBoard();
				userdashBoardChilds10.setId(Integer.valueOf(this.getDepartmentsAndDashboardKey("Adjustment Log Timely Filing")));
				userdashBoardChilds10.setLinkName("Adjustment Log Timely Filing");
				dashboardList.add(userdashBoardChilds10);

				UserDashBoard userdashBoardChilds11 = new UserDashBoard();
				userdashBoardChilds11.setId(Integer.valueOf(this.getDepartmentsAndDashboardKey("Query to TL")));
				userdashBoardChilds11.setLinkName("Query to TL");
				dashboardList.add(userdashBoardChilds11);

				UserDashBoard userdashBoardChilds12 = new UserDashBoard();
				userdashBoardChilds12.setId(Integer.valueOf(this.getDepartmentsAndDashboardKey("Payment Posting Offset Log")));
				userdashBoardChilds12.setLinkName("Payment Posting Offset Log");
				dashboardList.add(userdashBoardChilds12);


			}

			if (childDept.getName().equals("Payments Department")) {
				UserDashBoard userdashBoardChilds = new UserDashBoard();
				userdashBoardChilds.setId(Integer.valueOf(this.getDepartmentsAndDashboardKey("Hourly Projects Productivity")));
				userdashBoardChilds.setLinkName("Hourly Projects Productivity");
				dashboardList.add(userdashBoardChilds);
				userdashBoardChilds1 = new UserDashBoard();
				userdashBoardChilds1.setId(Integer.valueOf(this.getDepartmentsAndDashboardKey("Payments Team Productivity")));
				userdashBoardChilds1.setLinkName("Payments Team Productivity");
				dashboardList.add(userdashBoardChilds1);
				userdashBoardChilds2 = new UserDashBoard();
				userdashBoardChilds2.setId(Integer.valueOf(this.getDepartmentsAndDashboardKey("Offset Reference & Postings")));
				userdashBoardChilds2.setLinkName("Offset Reference & Postings");
				dashboardList.add(userdashBoardChilds2);
				userdashBoardChilds3 = new UserDashBoard();
				userdashBoardChilds3.setId(Integer.valueOf(this.getDepartmentsAndDashboardKey("Payment Posting Log")));
				userdashBoardChilds3.setLinkName("Payment Posting Log");
				dashboardList.add(userdashBoardChilds3);
				userdashBoardChilds4 = new UserDashBoard();
				userdashBoardChilds4.setId(Integer.valueOf(this.getDepartmentsAndDashboardKey("Payments Process Workflow (ERA)")));
				userdashBoardChilds4.setLinkName("Payments Process Workflow (ERA)");
				dashboardList.add(userdashBoardChilds4);
				userdashBoardChilds6 = new UserDashBoard();
				userdashBoardChilds6.setId(Integer.valueOf(this.getDepartmentsAndDashboardKey("Payments Process Workflow (Non ERA)")));
				userdashBoardChilds6.setLinkName("Payments Process Workflow (Non ERA)");
				dashboardList.add(userdashBoardChilds6);
				userdashBoardChilds7 = new UserDashBoard();
				userdashBoardChilds7.setId(Integer.valueOf(this.getDepartmentsAndDashboardKey("Payments Process Workflow (CAP)")));
				userdashBoardChilds7.setLinkName("Payments Process Workflow (CAP)");
				dashboardList.add(userdashBoardChilds7);
				userdashBoardChilds8 = new UserDashBoard();
				userdashBoardChilds8.setId(Integer.valueOf(this.getDepartmentsAndDashboardKey("Payment Posting Offset Log")));
				userdashBoardChilds8.setLinkName("Payment Posting Offset Log");
				dashboardList.add(userdashBoardChilds8);
			}

			if (childDept.getName().equals("Coding and Charge Entry Department")) {
				UserDashBoard userdashBoardChilds = new UserDashBoard();
				userdashBoardChilds.setId(Integer.valueOf(this.getDepartmentsAndDashboardKey("Hourly Projects Productivity")));
				userdashBoardChilds.setLinkName("Hourly Projects Productivity");
				dashboardList.add(userdashBoardChilds);
				userdashBoardChilds1 = new UserDashBoard();
				userdashBoardChilds1.setId(Integer.valueOf(this.getDepartmentsAndDashboardKey("Demo, CE & Coding Validation Team Prod. & Process Workflow")));
				userdashBoardChilds1.setLinkName("Demo, CE & Coding Validation Team Prod. & Process Workflow");
				dashboardList.add(userdashBoardChilds1);
				userdashBoardChilds2 = new UserDashBoard();
				userdashBoardChilds2.setId(Integer.valueOf(this.getDepartmentsAndDashboardKey("Coding Correction Log")));
				userdashBoardChilds2.setLinkName("Coding Correction Log");
				dashboardList.add(userdashBoardChilds2);
				userdashBoardChilds3 = new UserDashBoard();
				userdashBoardChilds3.setId(Integer.valueOf(this.getDepartmentsAndDashboardKey("Rekey Request Log")));
				userdashBoardChilds3.setLinkName("Rekey Request Log");
				dashboardList.add(userdashBoardChilds3);
				userdashBoardChilds4 = new UserDashBoard();
				userdashBoardChilds4.setId(Integer.valueOf(this.getDepartmentsAndDashboardKey("Refund Request")));
				userdashBoardChilds4.setLinkName("Refund Request");
				dashboardList.add(userdashBoardChilds4);
			}

			if (roleName.equals("role_trainee") && !childDept.getName().equals("Coding and Charge Entry Department") && !childDept.getName().equals("Payments Department") && !childDept.getName().equals("Accounts Receivable Department")) {
				UserDashBoard userdashBoardChilds = new UserDashBoard();
				userdashBoardChilds.setId(Integer.valueOf(this.getDepartmentsAndDashboardKey("Trainee Evaluation")));
				userdashBoardChilds.setLinkName("Trainee Evaluation");
				dashboardList.add(userdashBoardChilds);
			}
		}
		return dashboardList
				.stream()
				.collect(Collectors.toCollection(
						() -> new TreeSet<>(Comparator.comparing(UserDashBoard::getId)))
				).stream().collect(Collectors.toList());


	}

	private String getDepartmentsAndDashboardKey(String name){
		Map<String, String> obj = new HashMap<>();
		obj.put("1","Master Data Tables");
		obj.put("2","Hourly Projects Productivity");
		obj.put("3","Payments Team Productivity");
		obj.put("4","Payments Process Workflow (ERA)");
		obj.put("5","Payments Process Workflow (Non ERA)");
		obj.put("6","Payments Process Workflow (CAP)");
		obj.put("7","Coding Correction Log");
		obj.put("8","Rekey Request Log");
		obj.put("9","Adjustment Log");
		obj.put("10","Adjustment Log Timely Filing");
		obj.put("11","Adjustment Log Without Timely Filing");
		obj.put("12","Payment Posting Offset Log");
		obj.put("13","Payment Posting Log");
		obj.put("14","Accounts Receivable (AR) Team Productivity & Process Workflow");
		obj.put("15","Query to TL");
		obj.put("16","Offset Reference & Postings");
		obj.put("17","Refund Request");
		obj.put("18","Demo, CE & Coding Validation Team Prod. & Process Workflow");
		String key = null;
		for(Map.Entry<String, String> entry: obj.entrySet()) {
			if(entry.getValue() == name) {
				key =  entry.getKey();
				break;
			}
		}
		return key;

	}
	@SuppressWarnings("rawtypes")
	public List<UserMenu> getUserMenuByRole(String roleName , Set<DepartmentEntity> listDepartments) {
			//,List<Map<String, Object>> permissionWithDepartments){


		List<UserMenu> menuList = new ArrayList();
//		Map<Object, List<Map<String, Object>>> test =
//				permissionWithDepartments.stream().collect(Collectors.groupingBy(data->data.get("department_id")));

		// add Menu as per xl sheet
		if(roleName.equals("role_user") ) {
			List<DepartmentEntity> onlyDept =
					listDepartments.stream().filter(x -> x.getParent() == null).collect(Collectors.toList());

			if (onlyDept.size() != 4) {
				for (DepartmentEntity childDept : listDepartments) {
					if (childDept.getName().equals(Constants.ACCOUNTING_DEPARTMENT_NAME)) {
						UserMenu menu = new UserMenu();
						menu.setId(getMenuId("Dashboard"));
						menu.setLinkName("Dashboard");
						//menu.setPermissionList(test.get(childDept.getId()));
						menu.setLinkUrl("/dashboard");
						menu.setLinkImage("dash-icon.png");
						menuList.add(menu);

						UserMenu menu1 = new UserMenu();
						menu1.setId(getMenuId("Generate Cashlog Report"));
						//menu1.setPermissionList(test.get(childDept.getId()));
						menu1.setLinkName("Generate Cashlog Report");
						menu1.setLinkUrl("/generate-cashlog-report");
						menu1.setLinkImage("cashlog.png");
						menuList.add(menu1);

						UserMenu menu2 = new UserMenu();
						menu2.setId(getMenuId("Process Manual"));
						//menu2.setPermissionList(test.get(childDept.getId()));
						menu2.setLinkName("Process Manual");
						menu2.setLinkUrl("/process-manual");
						menu2.setLinkImage("process-icon.png");
						menuList.add(menu2);

//					UserMenu menu3 = new UserMenu();
//					menu3.setId(getMenuId("Setting"));
//					menu3.setLinkUrl("/setting");
//					menu3.setLinkImage("");
//					menu3.setLinkName("Setting");
//					menuList.add(menu3);

					}

					if (childDept.getName().equals(Constants.AR_DEPARTMENT_NAME)) {
						UserMenu menu1 = new UserMenu();
						menu1.setId(getMenuId("Dashboard"));
						menu1.setLinkName("Dashboard");
						menu1.setLinkUrl("/dashboard");
						//menu1.setPermissionList(test.get(childDept.getId()));

						menu1.setLinkImage("dash-icon.png");
						menuList.add(menu1);

						UserMenu menu2 = new UserMenu();
						menu2.setId(getMenuId("Process Manual"));
						menu2.setLinkName("Process Manual");
						menu2.setLinkUrl("/process-manual");
						//menu2.setPermissionList(test.get(childDept.getId()));
						menu2.setLinkImage("process-icon.png");
						menuList.add(menu2);

//					UserMenu menu3 = new UserMenu();
//					menu3.setId(getMenuId("Setting"));
//					menu3.setLinkName("Setting");
//					menu3.setLinkUrl("/setting");
//					menu3.setLinkImage("");
//					menuList.add(menu3);
					}
					if (childDept.getName().equals(Constants.PAYMENT_DEPARTMENT_NAME)) {
						UserMenu menu1 = new UserMenu();
						menu1.setId(getMenuId("Dashboard"));
						menu1.setLinkName("Dashboard");
						menu1.setLinkUrl("/dashboard");
						//menu1.setPermissionList(test.get(childDept.getId()));

						menu1.setLinkImage("dash-icon.png");
						menuList.add(menu1);

						UserMenu menu2 = new UserMenu();
						menu2.setId(getMenuId("Payment Batching System"));
						menu2.setLinkName("Payment Batching System");
						//menu2.setPermissionList(test.get(childDept.getId()));

						menu2.setLinkUrl("/paymentbatch");
						menu2.setLinkImage("payment-icon.png");
						menuList.add(menu2);
//
//
						UserMenu menu3 = new UserMenu();
						menu3.setId(getMenuId("Payment Productivity"));
						menu3.setLinkName("Payment Productivity");
						//menu3.setPermissionList(test.get(childDept.getId()));

						//menu3.setLinkUrl("/payment-productivity-list");
						menu3.setLinkImage("paymentproductivity-icon.png");
						menu3.setHasSubMenu(true);
						UserMenu submenu1 = new UserMenu();
						List<UserMenu> submenu = new ArrayList();
						submenu1.setId(Integer.valueOf(menu3.getId() + "001"));
						submenu1.setLinkName("ERA List");
						submenu1.setLinkUrl("/payment-productivity-list/1");
						submenu.add(submenu1);

						UserMenu submenu2 = new UserMenu();
						submenu2.setId(Integer.valueOf(menu3.getId() + "002"));
						submenu2.setLinkName("CAP List");
						submenu2.setLinkUrl("/payment-productivity-list/3");
						submenu.add(submenu2);

						UserMenu submenu3 = new UserMenu();
						submenu3.setId(Integer.valueOf(menu3.getId() + "003"));
						submenu3.setLinkName("Non-ERA List");
						submenu3.setLinkUrl("/payment-productivity-list/2");
						submenu.add(submenu3);
						//menu3.setSubMenu(submenu);

						//-----------------------------------Added New submenu List All----------//
						UserMenu submenu4 = new UserMenu();
						submenu4.setId(Integer.valueOf(menu3.getId() + "004"));
						submenu4.setLinkName("List All");
						submenu4.setLinkUrl("/payment-productivity-list/4");
						submenu.add(submenu4);
						menu3.setSubMenu(submenu);

						//------------------------------End Block----------------------------------------//

						menuList.add(menu3);

						UserMenu menu4 = new UserMenu();
						menu4.setId(getMenuId("Process Manual"));
						menu4.setLinkName("Process Manual");
						menu4.setLinkName("Process Manual");
						//menu4.setPermissionList(test.get(childDept.getId()));

						menu4.setLinkUrl("/process-manual");
						menu4.setLinkImage("process-icon.png");
						menuList.add(menu4);

//					UserMenu menu5 = new UserMenu();
//					menu5.setId(getMenuId("Setting"));
//					menu5.setLinkName("Setting");
//					menu5.setLinkUrl("/setting");
//					menu5.setLinkImage("");
//					menuList.add(menu5);

					}

					if (childDept.getName().equals(Constants.CHARGE_DEPARTMENT_NAME)) {
						UserMenu menu1 = new UserMenu();
						menu1.setId(getMenuId("Dashboard"));
						menu1.setLinkName("Dashboard");
						//menu1.setPermissionList(test.get(childDept.getId()));

						menu1.setLinkUrl("/dashboard");
						menu1.setLinkImage("dash-icon.png");
						menuList.add(menu1);

						UserMenu menu2 = new UserMenu();
						menu2.setId(getMenuId("Charge Batching System"));
						menu2.setLinkName("Charge Batching System");
						//menu2.setPermissionList(test.get(childDept.getId()));

						menu2.setLinkUrl("/chargebatch");
						menu2.setLinkImage("charge-icon.png");
						menuList.add(menu2);

						UserMenu menu3 = new UserMenu();
						menu3.setId(getMenuId("Demo/CE/Coding Prod."));
						menu3.setLinkName("Demo/CE/Coding Prod.");
						//menu3.setLinkUrl("/CE-productivity");
						//menu3.setPermissionList(test.get(childDept.getId()));

						menu3.setLinkImage("productivity-icon.png");
						menu3.setHasSubMenu(true);
						List<UserMenu> submenu = new ArrayList<>();
						UserMenu submenu3 = new UserMenu();
						submenu3.setId(Integer.valueOf(getMenuId("Demo/CE/Coding Prod.") + "1"));
						submenu3.setLinkName("CE Productivity");
						submenu3.setLinkUrl("/CE-productivity/3");
						submenu.add(submenu3);

						UserMenu submenu2 = new UserMenu();
						submenu2.setId(Integer.valueOf(getMenuId("Demo/CE/Coding Prod.") + "2"));
						submenu2.setLinkName("Demo Productivity");
						submenu2.setLinkUrl("/CE-productivity/2");
						submenu.add(submenu2);

						UserMenu submenu1 = new UserMenu();
						submenu1.setId(43);
						submenu1.setLinkName("Coding Productivity");
						submenu1.setLinkUrl("/CE-productivity/1");
						submenu.add(submenu1);

						UserMenu submenu4 = new UserMenu();
						submenu4.setId(Integer.valueOf(getMenuId("Demo/CE/Coding Prod.") + "4"));
						submenu4.setLinkName("Rejected Logs");
						submenu4.setLinkUrl("/rejectedlogs");
						submenu.add(submenu4);

						menu3.setSubMenu(submenu);
						menuList.add(menu3);

						UserMenu menu4 = new UserMenu();
						menu4.setId(getMenuId("Process Manual"));
						menu4.setLinkName("Process Manual");
						menu4.setLinkUrl("/process-manual");
						//menu4.setPermissionList(test.get(childDept.getId()));

						menu4.setLinkImage("process-icon.png");
						menuList.add(menu4);

//					UserMenu menu5 = new UserMenu();
//					menu5.setId(getMenuId("Setting"));
//					menu5.setLinkName("Setting");
//					menu5.setLinkUrl("/setting");
//					menu5.setLinkImage("");
//					menuList.add(menu5);

					}
				}
			} else{


				UserMenu menu2 = new UserMenu();
				menu2.setId(1);
				menu2.setLinkName("Dashboard");
				//menu2.setPermissionList(permissionWithDepartments);
				menu2.setLinkUrl("/dashboard");
				menu2.setLinkImage("dash-icon.png");
				menuList.add(menu2);
				UserMenu menu3 = new UserMenu();
				menu3.setId(2);
				menu3.setLinkName("Charge Batching System");
				menu3.setLinkUrl("/chargebatch");
				//menu3.setPermissionList(permissionWithDepartments);
				menu3.setLinkImage("charge-icon.png");
				menuList.add(menu3);
				UserMenu menu1 = new UserMenu();
				menu1.setId(3);
				menu1.setLinkName("Demo/CE/Coding Prod.");
			//	menu1.setPermissionList(permissionWithDepartments);
				menu1.setLinkImage("productivity-icon.png");
				menu1.setHasSubMenu(true);
				List<UserMenu> submenu = new ArrayList();
				   menu3 = new UserMenu();
				menu3.setId(1);
				menu3.setLinkName("Coding Productivity");
				menu3.setLinkUrl("/CE-productivity/1");
				submenu.add(menu3);
				UserMenu  submenu1 = new UserMenu();
				submenu1.setId(2);
				submenu1.setLinkName("Demo Productivity");
				submenu1.setLinkUrl("/CE-productivity/2");
				submenu.add(submenu1);
				UserMenu submenu3 = new UserMenu();
				submenu3.setId(3);
				submenu3.setLinkName("CE Productivity");
				submenu3.setLinkUrl("/CE-productivity/3");
				submenu.add(submenu3);
				UserMenu submenu2 = new UserMenu();
				submenu2.setId(4);
				submenu2.setLinkName("Rejected Logs");
				submenu2.setLinkUrl("/rejectedlogs");
				submenu.add(submenu2);
				menu1.setSubMenu(submenu);
				menuList.add(menu1);
				  submenu1 = new UserMenu();
				submenu1.setId(4);
				submenu1.setLinkName("Payment Batching System");
				submenu1.setLinkUrl("/paymentbatch");
				submenu1.setLinkImage("payment-icon.png");
				menuList.add(submenu1);
				UserMenu submenu4 = new UserMenu();
				submenu4.setId(5);
				submenu4.setLinkName("Payment Productivity");
				submenu4.setLinkImage("paymentproductivity-icon.png");
				submenu4.setHasSubMenu(true);
				UserMenu menu4 = new UserMenu();
				List<UserMenu> submenu00 = new ArrayList();
				menu4.setId(1);
				menu4.setLinkName("ERA List");
				menu4.setLinkUrl("/payment-productivity-list/1");
				submenu00.add(menu4);
				UserMenu menu14 = new UserMenu();
				menu14.setId(2);
				menu14.setLinkName("CAP List");
				menu14.setLinkUrl("/payment-productivity-list/3");
				submenu00.add(menu14);
				UserMenu   menu15 = new UserMenu();
				menu15.setId(3);
				menu15.setLinkName("Non-ERA List");
				menu15.setLinkUrl("/payment-productivity-list/2");
				submenu00.add(menu15);
				submenu4.setSubMenu(submenu00);
				//menuList.add(submenu4);

				//------------------Commented Above Line-----------------Added New submenu List All----------//
				UserMenu menu100 = new UserMenu();
				menu100.setId(4);
				menu100.setLinkName("List All");
				menu100.setLinkUrl("/payment-productivity-list/4");
				submenu00.add(menu100);
				submenu4.setSubMenu(submenu00);
				menuList.add(submenu4);
				//----------------------------------------------------------------------//

				UserMenu menu16 = new UserMenu();
				menu16.setId(6);
				menu16.setLinkName("Cashlog Report");
				menu16.setLinkUrl("/generate-cashlog-report");
				//menu16.setPermissionList(permissionWithDepartments);
				menu16.setLinkImage("dash-icon.png");
				menuList.add(menu16);
				UserMenu menu17 = new UserMenu();
				menu17.setId(7);
				menu17.setLinkName("Process Manual");
				menu17.setLinkUrl("/process-manual");
				//menu17.setPermissionList(permissionWithDepartments);
				menu17.setLinkImage("process-icon.png");
				menuList.add(menu17);
				 submenu1 = new UserMenu();
				submenu1.setId(8);
				submenu1.setLinkName("Doctor");
				submenu1.setLinkUrl("/doctor");
				submenu1.setLinkImage("doctor-icon.png");
				menuList.add(submenu1);
				UserMenu menu9 = new UserMenu();
				menu9.setId(9);
				menu9.setLinkName("Group");
				//menu9.setPermissionList(permissionWithDepartments);
				menu9.setLinkUrl("/doctor/add-group");
				menu9.setLinkImage("group.png");
				menuList.add(menu9);
				UserMenu menu10 = new UserMenu();
				menu10.setId(10);
				menu10.setLinkName("Insurance");
			//	menu10.setPermissionList(permissionWithDepartments);
				menu10.setLinkUrl("/insurance");
				menu10.setLinkImage("insurance-icon.png");
				menuList.add(menu10);
				UserMenu menu11 = new UserMenu();
				menu11.setId(11);
				menu11.setLinkName("Company");
				menu11.setLinkUrl("/doctor/add-company");
			//	menu11.setPermissionList(permissionWithDepartments);
				menu11.setLinkImage("company.png");
				menuList.add(menu11);
				UserMenu menu12 = new UserMenu();
				menu12.setId(12);
				menu12.setLinkName("AR Database");
				//menu12.setPermissionList(permissionWithDepartments);
				menu12.setLinkUrl("/ardatabase");
				menu12.setLinkImage("traine-icon.png");
				menuList.add(menu12);
				UserMenu menu13 = new UserMenu();
				menu13.setId(13);
				menu13.setLinkName("Location");
				menu13.setLinkUrl("/location");
				//menu13.setPermissionList(permissionWithDepartments);
				menu13.setLinkImage("traine-icon.png");
				menuList.add(menu13);
				 menu14 = new UserMenu();
				menu14.setId(14);
				menu14.setLinkName("Payment Type");
				//menu14.setPermissionList(permissionWithDepartments);
				menu14.setLinkUrl("/paymenttype");
				menu14.setLinkImage("paymenttype-icon.png");
				menuList.add(menu14);
				 menu15 = new UserMenu();
				menu15.setId(15);
				menu15.setLinkName("Revenue Type");
				//menu15.setPermissionList(permissionWithDepartments);
				menu15.setLinkUrl("/revenuetype");
				menu15.setLinkImage("traine-icon.png");
				menuList.add(menu15);
							}

			UserMenu menu16 = new UserMenu();
			menu16.setId(16);
			menu16.setLinkName("QA Manager");
			//	menu16.setPermissionList(permissionWithDepartments);
			menu16.setHasSubMenu(true);
			menu16.setLinkImage("traine-icon.png");
			List<UserMenu> subMenuList16 = new ArrayList();
			UserMenu submenu16 = new UserMenu();
			submenu16.setId(1);
			submenu16.setLinkName("QA Work Sheet");
			submenu16.setLinkUrl("/qamanager");
			subMenuList16.add(submenu16);
			menu16.setSubMenu(subMenuList16);
			UserMenu submenu17 = new UserMenu();
			submenu17.setId(2);
			submenu17.setLinkName("Search in Samples");
			submenu17.setLinkUrl("/qasamples");
			subMenuList16.add(submenu17);
			menu16.setSubMenu(subMenuList16);
			UserMenu submenu18 = new UserMenu();
			submenu18.setId(3);
			submenu18.setLinkName(" Reports");
			submenu18.setLinkUrl("/qareports");
			subMenuList16.add(submenu18);
			menu16.setSubMenu(subMenuList16);
			menuList.add(menu16);

		}

//
//
		if(roleName.equals("role_admin") ) {

			UserMenu menu = new UserMenu();
		//	menu.setId(getMenuId("Dashboard"));
			menu.setId(100);
			menu.setLinkName("Dashboard");
			menu.setLinkUrl("/dashboard");
			menu.setLinkImage("dash-icon.png");
			menuList.add(menu);

//

			UserMenu menu1 = new UserMenu();
		//	menu1.setId(getMenuId("Process Manual"));
			menu1.setId(101);
			menu1.setLinkName("Process Manual");
			menu1.setLinkUrl("/process-manual");
			menu1.setLinkImage("process-icon.png");
			menuList.add(menu1);

			UserMenu menu2 = new UserMenu();
			menu2.setId(102);
			menu2.setLinkName("User");
			menu2.setLinkUrl("/user");
			menu2.setLinkImage("traine-icon.png");
			menuList.add(menu2);

			UserMenu menu3 = new UserMenu();
			menu3.setId(103);
			menu3.setLinkUrl("/department");
			menu3.setLinkImage("traine-icon.png");
			menu3.setLinkName("Department");
			menuList.add(menu3);

			UserMenu menu4 = new UserMenu();
			menu4.setId(104);
			menu4.setLinkName("Insurance");
			menu4.setLinkUrl("/insurance");
			menu4.setLinkImage("insurance-icon.png");
			menuList.add(menu4);

			UserMenu menu5 = new UserMenu();
			menu5.setId(105);
			menu5.setLinkName("Manage QC Point");
			menu5.setLinkUrl("/qcpoint");
			menu5.setLinkImage("traine-icon.png");
			menuList.add(menu5);

			UserMenu menu6 = new UserMenu();
			menu6.setId(106);
			menu6.setLinkName("Payment Type");
			menu6.setLinkUrl("/paymenttype");
			menu6.setLinkImage("paymenttype-icon.png");
			menuList.add(menu6);


			UserMenu menu7 = new UserMenu();
			menu7.setId(107);
			menu7.setLinkName("AR Database");
			menu7.setLinkUrl("/ardatabase");
			menu7.setLinkImage("traine-icon.png");
			menuList.add(menu7);

			UserMenu menu8 = new UserMenu();
			menu8.setId(108);
			menu8.setLinkName("Company");
			menu8.setLinkUrl("/doctor/add-company");
			menu8.setLinkImage("company.png");
			menuList.add(menu8);

			UserMenu menu9 = new UserMenu();
			menu9.setId(109);
			menu9.setLinkName("Group");
			menu9.setLinkUrl("/doctor/add-group");
			menu9.setLinkImage("group.png");
			menuList.add(menu9);

			UserMenu menu10 = new UserMenu();
			menu10.setId(110);
			menu10.setLinkName("Doctor");
			menu10.setLinkUrl("/doctor");
			menu10.setLinkImage("doctor-icon.png");
			menuList.add(menu10);


			UserMenu menu11 = new UserMenu();
			menu11.setId(111);
			menu11.setLinkName("Email Template");
			menu11.setLinkUrl("/emailtemplate");
			menu11.setLinkImage("traine-icon.png");
			menuList.add(menu11);

			UserMenu menu12 = new UserMenu();
			menu12.setId(112);
			menu12.setLinkName("Hourly Task");
			menu12.setLinkUrl("/hourlytask");
			menu12.setLinkImage("traine-icon.png");
			menuList.add(menu12);

			UserMenu menu13 = new UserMenu();
			menu13.setId(113);
			menu13.setLinkName("Revenue Type");
			menu13.setLinkUrl("/revenuetype");
			menu13.setLinkImage("traine-icon.png");
			menuList.add(menu13);

			UserMenu menu14 = new UserMenu();
			menu14.setId(114);
			menu14.setLinkName("Location");
			menu14.setLinkUrl("/location");
			menu14.setLinkImage("traine-icon.png");
			menuList.add(menu14);

			UserMenu menu15 = new UserMenu();
			menu15.setId(115);
			menu15.setLinkName("Money Source");
			menu15.setLinkUrl("/moneysource");
			menu15.setLinkImage("traine-icon.png");
			menuList.add(menu15);

//			UserMenu menu16 = new UserMenu();
//			menu16.setId(116);
//			menu16.setLinkName("Trainee Evaluation");
//			//menu16.setLinkUrl("");
//			menu16.setLinkImage("qa-icon.png");
//			menu16.setHasSubMenu(true);
//			UserMenu submenu1 = new UserMenu();
//			List<UserMenu> submenu = new ArrayList();
//			submenu1.setId((int) 16.1);
//			submenu1.setLinkName("Trainee List");
//			submenu1.setLinkUrl("/trainee-evaluation-list");
//			submenu.add(submenu1);
//			menu16.setSubMenu(submenu);
//			menuList.add(menu16);

			UserMenu menu17 = new UserMenu();
			menu17.setId(117);
			menu17.setLinkName("Charge Batch Type");
			menu17.setLinkUrl("/charge-batch-type");
			menu17.setLinkImage("traine-icon.png");
			menuList.add(menu17);

//
//
//			UserMenu menu18 = new UserMenu();
//			menu18.setId(18);
//			menu18.setLinkName("Reports");
//			menu18.setLinkUrl("");
//			menu18.setLinkImage("qa-icon.png");
//
//			UserMenu submenu1 = new UserMenu();
//			List<UserMenu> submenu = new ArrayList();
//			submenu1.setId(1);
//			submenu1.setLinkName("Evaluation Report");
//			submenu1.setLinkUrl("/evaluationReports");
//			submenu.add(submenu1);
//			 menu18.setSubMenu(submenu);
//			menuList.add(menu18);

		}
//
//
		if(roleName.equals("role_trainee")) {
			UserMenu menu1 = new UserMenu();
			menu1.setId(1);
			menu1.setLinkName("Dashboard");
			menu1.setLinkUrl("/dashboard");
			menu1.setLinkImage("dash-icon.png");
			menuList.add(menu1);

			UserMenu menu2 = new UserMenu();
			menu2.setId(2);
			menu2.setLinkName("Cashlog Report");
			menu2.setLinkUrl("/generate-cashlog-report");
			menu2.setLinkImage("cashlog.png");
			menuList.add(menu2);

			UserMenu menu3 = new UserMenu();
			menu3.setId(3);
			menu3.setLinkName("Process Manual");
			menu3.setLinkUrl("/process-manual");
			menu3.setLinkImage("process-icon.png");
			menuList.add(menu3);

//			UserMenu menu4 = new UserMenu();
//			menu4.setId(4);
//			menu4.setLinkName("Setting");
//			menu4.setLinkUrl("/setting");
//			menu4.setLinkImage("");
//			menuList.add(menu4);
		}

		return menuList
				.stream()
				.collect(Collectors.toCollection(
						() -> new TreeSet<>(Comparator.comparing(UserMenu::getId)))
				).stream().collect(Collectors.toList());

		//return menuList;
	}

	private Integer getMenuId(String menuName){
		Map<Integer, String> obj = new HashMap();
		obj.put(1, "Dashboard");
		obj.put(2, "Generate Cashlog Report");
		obj.put(3, "Charge Batching System");
		obj.put(4, "Demo/CE/Coding Prod.");
		obj.put(5, "Payment Batching System");
		obj.put(6, "Payment Productivity");
		obj.put(7, "Process Manual");
		obj.put(8, "Trainee Evaluation");
		obj.put(9, "Setting");
		obj.put(10, "Reports");
		obj.put(11, "Doctor");
		obj.put(12, "Group");
		obj.put(13, "Company");
		obj.put(14, "Insurance");
		obj.put(15, "Location");
		obj.put(16, "Payment Type");
		obj.put(17, "Revenue Type");
		obj.put(18, "QA Manager");
		obj.put(19, "Demo Productivity");
		obj.put(20,"Search in Samples");
		obj.put(21,"QA Work Sheet");
		Integer key = null;
		for(Map.Entry<Integer, String> entry: obj.entrySet()) {
			if(entry.getValue().equals(menuName)) {
				key =  entry.getKey();
				break;
			}
		}
		return key;
	}
}
