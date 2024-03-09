<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript">
var showMenu = $('#selected_menu').html();
$(document).ready(function() {
	$("#"+showMenu).addClass("current");
	$("#menu li ul").hide();
	$("#"+showMenu+" ul").show();

	$("#menu li a").mouseover(
        function () {
            if($(this).parent().hasClass('mainNav')){
            	 $("#menu li ul").hide();
            	 $("#"+showMenu).children("ul").show();
         		//$(this).children("ul").slideToggle(250);
                 $(this).parent().children("ul").show(100);
            }
       
	});//hover
});

</script>
<c:set var ="showPaymentBatchMenu" value="false"></c:set>
<c:set var ="showCashlogMenu" value="false"></c:set>
<c:set var="showChargeBatchMenu" value="false"></c:set>
<c:set var="showARProdMenu" value="false"></c:set>
<c:set var="isOffsetManager" value="false"></c:set>

<c:forEach var="dept" items="${pageContext.request.userPrincipal.principal.departments}">
	<c:if test="${dept.id==2}">
		<c:set var="showPaymentBatchMenu" value="true"></c:set>
	</c:if>
	<c:if test="${dept.id==1}">
		<c:set var="showChargeBatchMenu" value="true"></c:set>
	</c:if>
	<c:if test="${dept.id==3}">
		<c:set var="showARProdMenu" value="true"></c:set>
	</c:if>
	<c:if test="${dept.id==4}">
		<c:set var="showCashlogMenu" value="true"></c:set>
	</c:if>
</c:forEach>

<sec:authorize ifAnyGranted="P-5">
<c:set var="isOffsetManager" value="true"></c:set>
</sec:authorize>
	<!-- Left Side Start-->
<div id="left-side">
	<!--Nav Container Start-->
	<div id="nav-container">
		<div id="nav">
			<ul id="menu">
				<li id="dashboardMenu" class="mainNav"><a href="<c:url value='/' />"
					class="dashboard current">Dashboard</a>
					<!--<ul>
						 <li class="active"><a href="#">Settings</a></li>
						<li><a href="#">Notifications</a></li>
						 <li><a href="#">Activities</a></li>
					</ul>-->
				</li>
				<sec:authorize ifAnyGranted="Standard User">

					<c:if test="${showChargeBatchMenu}">
						
							<li id="chargeBatchMenu" class="mainNav"><a href="#"
										class="chargeBatch">Charge Batching Sys.</a>
								<ul class="subNav">
								<sec:authorize ifAnyGranted="P-15">
									<li><a href="<c:url value='/chargebatchprocess/add' />">Add Charge Batch</a></li>
								</sec:authorize>
									<li><a href="<c:url value='/chargebatchprocess' />">Charge Batch List</a></li>
									<!--<li><a href="<c:url value='/chargebatchprocess/print' />">Print Charge Batch</a></li>
									<li><a href="<c:url value='/chargebatchprocess/report' />">Report Batch</a></li -->
								</ul>
							</li>
						<li id="chargeBatchProductivityMenu" class="mainNav"><a
										href="#" class="productivityReport">Demo/CE/Coding Prod.</a>
							<ul class="subNav">
								<li><a
												href="<c:url value='/chargeproductivity?prodType=Coding' />">Add Coding Prod.</a></li>
								<li><a
												href="<c:url value='/chargeproductivity?prodType=Demo' />">Add Demo Productivity</a></li>
								<li><a
												href="<c:url value='/chargeproductivity?prodType=CE' />">Add CE Productivity</a></li>
								
								<li><a href="<c:url value='/chargeproductivity/list' />">List Productivity</a></li>
								<li><a
												href="<c:url value='/chargeproductivityreject/add' />">Add Rejection</a></li>
								<li><a href="<c:url value='/chargeproductivityreject' />">Rejected Logs</a></li>
							</ul>
						</li>
					</c:if>
					
					<c:if test="${showPaymentBatchMenu || isOffsetManager}">
						<li id="paymentBatchingMenu" class="mainNav"><a href="#"
										class="paymentBatch">Payment Batching Sys.</a>
							<ul class="subNav">
								<sec:authorize ifAnyGranted="P-8,P-5">
									<li><a href="<c:url value='/paymentbatch/add' />">Add Payment Batch</a></li>
								</sec:authorize>
								<li><a href="<c:url value='/paymentbatch' />">Payment Batch List</a></li>
								<%-- <li><a href="<c:url value='/paymentbatch/report' />">Print Payment Batch</a></li> --%>
							</ul>
						</li>
					</c:if>

					<c:if test="${showPaymentBatchMenu}">
						<li id="paymentProductivityMenu" class="mainNav"><a href="#"
										class="productivityReport">Payment Productivity</a>
							<ul class="subNav">
								<li><a href="<c:url value='/paymentproductivityERA/add' />">Add ERA Productivity</a></li>
								<li><a
												href="<c:url value='/paymentproductivitynonera/add?prodType=2' />">Add Non-ERA Prod.</a></li>
								<li><a
												href="<c:url value='/paymentproductivitynonera/add?prodType=3' />">Add Cap Productivity</a></li>
								<li><a
												href="<c:url value='/paymentproductivitynonera?prodType=1'/>">ERA List</a></li>
								<li><a
												href="<c:url value='/paymentproductivitynonera?prodType=2'/>">Non-ERA List</a></li>
								<li><a
												href="<c:url value='/paymentproductivitynonera?prodType=3'/>">CAP List</a></li>
								<!-- <li><a href="<c:url value='/paymentproductivityrefundrequest/report'/>"> Refund Request</a></li> 
								<li><a href="<c:url value='/paymentproderaoffset/report'/>">Offset Report</a></li>
								<li><a href="<c:url value='/paymentoffsetmanager/report'/>">Offset Posted</a></li> -->
							</ul>
						</li>
					</c:if>
					
					<c:if test="${showCashlogMenu}">
					 	<li id="cashlogMenu" class="mainNav"><a href="#"
										class="cashlogReports">Cashlog Reports</a>
							<ul class="subNav">
								<li><a href="<c:url value='/cashlog/'/>">Generate Report</a></li>
							</ul>
						</li>
					 </c:if>
					
					<c:if test="${showARProdMenu}">
						<%-- <li id="arProductivityMenu" class="mainNav"><a href="#"
							class="arProductivity">AR Productivity</a>
							<ul class="subNav">
								<li><a href="<c:url value='/flows/arProductivity/add' />">Add AR Productivity</a></li>
								<li><a href="<c:url value='/flows/arProductivity' />">AR Prod. List</a></li>
							</ul>
						</li> --%>
					</c:if>
					
				</sec:authorize>
					
					<sec:authorize ifAnyGranted="P-1">
						<li id="evaluationMenu" class="mainNav"><a href="#"
										class="evaluationMenu">Trainee Evaluation</a>
							<ul class="subNav">
								<li><a href="<c:url value='/traineeevaluation'/>">Trainee List</a></li>
							</ul>
						</li>
			        </sec:authorize>
			        
		         <sec:authorize ifAnyGranted="P-14, P-1, Trainee">
					<li id="reportMenu" class="mainNav"><a href="#"
									class="reportMenu">Reports</a>
						<ul class="subNav">
						 	<sec:authorize ifAnyGranted="P-1">
								<li><a
												href="<c:url value='/generatereport/traineeevaluationreport'/>">Evaluation Report</a></li>
							</sec:authorize>
							<sec:authorize ifAnyGranted="P-14, Trainee">
								<li><a
												href="<c:url value='/generatereport/processmanualreport'/>">Process Manual</a></li>
							</sec:authorize>
						</ul>
					</li>
		        </sec:authorize>
		        
		        <sec:authorize
								ifAnyGranted="Trainee, Standard User, Admin,Other">
					<li id="processManualMenu" class="mainNav"><a href="#"
									class="mpm">Process Manuals</a>
			            <ul>
			              <li class="active"><a
											href="<c:url value='/processmanual' />">List Sections</a></li>
			              <sec:authorize ifAnyGranted="P-14">
						  	<li><a href="<c:url value='/processmanual/add' />">Add Section</a></li>			              
			              </sec:authorize>
			            </ul>
			        </li>
		        </sec:authorize>
		        
				<sec:authorize ifAnyGranted="Standard User">
					<sec:authorize ifAnyGranted="P-11,P-12,P-13">
						<li id="doctorMenu" class="mainNav"><a href="#"
										class="doctor">Doctor</a>
							<ul class="subNav">
								<li><a href="<c:url value='/admin/doctor/add' />">Add Doctor</a></li>
								<li><a href="<c:url value='/admin/doctor' />">Doctor List</a>
							
										</ul>
						</li>
					</sec:authorize>
					
					<sec:authorize ifAnyGranted="P-12">
						<li id="insuranceMenu" class="mainNav"><a href="#" class="insurance">Insurances</a>
							<ul class="subNav">
								<li><a href="<c:url value='/admin/insurance/add' />">Add Insurances</a></li>
								<li><a href="<c:url value='/admin/insurance' />">Insurance List</a>
							</ul>
						</li>
						
						<li id="locationMenu" class="mainNav"><a href="#" class="insurance">Locations</a>
							<ul class="subNav">
								<li><a href="<c:url value='/admin/location/add' />">Add Location</a></li>
								<li><a href="<c:url value='/admin/location' />">Location List</a>
							</ul>
						</li>
					 </sec:authorize>
	
					 <sec:authorize ifAnyGranted="P-12">
						<%-- c:if test="${showPaymentBatchMenu}">--%>
						<li id="paymentTypeMenu" class="mainNav"><a href="#"
										class="paymentType">Payment Type</a>
							<ul class="subNav">
								<li><a href="<c:url value='/admin/payment/add' />">Add Payment Type</a></li>
								<li><a href="<c:url value='/admin/payment' />">Payment Type List</a>
							
										</ul></li>
						<%-- /c:if >--%>
					</sec:authorize>
				</sec:authorize>
				
				<sec:authorize ifAnyGranted="Admin">
					<ul>
						<li id="userMenu" class="mainNav"><a href="#" class="users">Users</a>
							<ul class="subNav">
								<li class="subMenu"><a href="<c:url value='/admin/user/add' />">Add User</a></li>
								<li class="subMenu"><a href="<c:url value='/admin/user' />">User List</a>
							</ul>
						</li>

						<li id="departmentMenu" class="mainNav"><a href="#" class="departments">Departments</a>
							<ul class="subNav">
								<li><a href="<c:url value='/admin/department/add' />">Add Department</a></li>
								<li><a href="<c:url value='/admin/department' />">Department List</a>
							</ul>
						</li>

						<li id="insuranceMenu" class="mainNav"><a href="#" class="insurance">Insurances</a>
							<ul class="subNav">
								<li><a href="<c:url value='/admin/insurance/add' />">Add Insurances</a></li>
								<li><a href="<c:url value='/admin/insurance' />">Insurance List</a>
							</ul>
						</li>
						
						<li id="qcPointMenu" class="mainNav"><a href="#" class="insurance">Manage QC Points</a>
							<ul class="subNav">
								<li><a href="<c:url value='/admin/qcpoint' />">List QC Points</a>
								<li><a href="<c:url value='/admin/qcpoint/add' />">Add QC Points</a>
							</ul>
						</li>

						<%--  <li id="productivityMenu" class="mainNav"><a href="#" class="arProductivity">AR Productivity</a>
							<ul class="subNav">
								<li><a href="<c:url value='/flows/arProductivity/add' />">Add AR Productivity</a></li>
								<li><a href="<c:url value='/flows/arProductivity' />">AR Productivity List</a>
							</ul>
						</li> --%>

						<li id="paymentTypeMenu" class="mainNav"><a href="#" class="paymentType">Payment Type</a>
							<ul class="subNav">
								<li><a href="<c:url value='/admin/payment/add' />">Add Payment Type</a></li>
								<li><a href="<c:url value='/admin/payment' />">Payment Type List</a>
							</ul>
						</li>
						
						<li id="doctorMenu" class="mainNav"><a href="#" class="doctor">Doctor</a>
							<ul class="subNav">
								<li><a href="<c:url value='/admin/doctor/add' />">Add Doctor</a></li>
								<li><a href="<c:url value='/admin/doctor' />">Doctor List</a>
							</ul>
						</li>

						<li id="emailTemplateMenu" class="mainNav"><a href="#" class="emailTemplate">Email Templates</a>
							<ul class="subNav">
								<li><a href="<c:url value='/admin/emailtemplate' />">Email Temp. List</a>
								<!-- <li><a href="<c:url value='/admin/emailtemplate/add' />">Add
										Email Template</a></li> -->
							</ul>
						</li>
						
						<li id="hourlyMenu" class="mainNav"><a href="#" class="hourly">Hourly Task</a>
							<ul class="subNav">
								<li><a href="<c:url value='/admin/hourlytask' />">Hourly Task List</a>
								<li><a href="<c:url value='/admin/hourlytask/add' />">Add
										Hourly Task</a></li>
							</ul>
						</li>
						
						
					</ul>
				</sec:authorize>

				<sec:authorize  ifAnyGranted="P-12, Admin">
					<li id="revenueTypeMenu" class="mainNav">
						<a href="#" class="paymentType">Revenue Type</a>
						<ul class="subNav">
							<li><a href="<c:url value='/admin/revenuetype/add' />">Add Revenue Type</a></li>
							<li><a href="<c:url value='/admin/revenuetype' />">Revenue Type List</a>
						</ul>
					</li>
				</sec:authorize>

				<%--<sec:authorize ifAnyGranted="P-14">
					<li id="processManualMenu" class="mainNav"><a href="#" class="mpm">Manage Process Manuals</a>
			            <ul>
			            	<li><a href="<c:url value='/processmanual/add' />">Add Section</a></li>
			              	<li class="active"><a href="<c:url value='/processmanual' />">List Sections</a></li>
			            </ul>
			        </li>
		        </sec:authorize> --%>

				<li id="settingsMenu" class="mainNav">
					<a href="#" class="settings">Settings</a>
					<ul class="subNav">
						<li><a href="<c:url value='/myprofile'/>">My Profile</a></li>
						<li><a href="<c:url value='/changepassword'/>">Change Password</a></li>
						<sec:authorize ifAnyGranted="Admin">
							<li><a href="<c:url value='/admin/evaluationsettings'/>">Evaluation Settings</a></li>
						</sec:authorize>
					</ul>
				</li>
				<sec:authorize ifAnyGranted="P-2">
					<li id="qamanagerMenu" class="mainNav">
						<a href="#" class="qamanager">QA Manager</a>
						<ul class="subNav">
							<li><a href="<c:url value='/qamanager/add' />">Create QA Worksheet</a></li>
							<li><a href="<c:url value='/qamanager' />">List QA Worksheet</a>
							<li><a href="<c:url value='/qamanager/sample' />">Search In Samples</a>
							<li><a href="<c:url value='/qamanager/reports/' />">Reports</a>
						</ul>
					</li>
				</sec:authorize>
			</ul>

		</div>
	</div>
	<!--Nav Container End-->
</div>
<!-- Left Side End-->
