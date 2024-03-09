<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript">
var showMenu = $('#selected_menu').html();
$(document).ready(function() {
	$("#"+showMenu).addClass("current");
	$("#menu li ul").hide();
	$("#"+showMenu+" ul").show();

	$("#menu li").click(
        function () {
        //$("#menu li ul").hide();
		$(this).children("ul").slideToggle(250);
	});//hover
});

</script>
<c:set var ="showPaymentBatchMenu" value="false"></c:set>
<c:set var="showChargeBatchMenu" value="false"></c:set>
<c:set var="isOffsetManager" value="false"></c:set>
<c:forEach var="dept" items="${pageContext.request.userPrincipal.principal.departments}">
	<c:if test="${dept.id==2}">
		<c:set var="showPaymentBatchMenu" value="true"></c:set>
	</c:if>
	<c:if test="${dept.id==1}">
		<c:set var="showChargeBatchMenu" value="true"></c:set>
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
				<li id="dashboardMenu"><a href="<c:url value='/' />"
					class="dashboard current">Dashboard</a>
					<!--<ul>
						 <li class="active"><a href="#">Settings</a></li>
						<li><a href="#">Notifications</a></li>
						 <li><a href="#">Activities</a></li>
					</ul>-->
				</li>
				<sec:authorize ifAnyGranted="Admin">
					<ul>
						<li id="userMenu"><a href="#" class="users">Users</a>
							<ul class="subNav">
								<li class="subMenu"><a href="<c:url value='/admin/user/add' />">Add User</a></li>
								<li class="subMenu"><a href="<c:url value='/admin/user' />">User List</a>
							</ul>
						</li>

						<li id="departmentMenu"><a href="#" class="departments">Departments</a>
							<ul class="subNav">
								<li><a href="<c:url value='/admin/department/add' />">Add Department</a></li>
								<li><a href="<c:url value='/admin/department' />">Department List</a>
							</ul>
						</li>

						<li id="insuranceMenu"><a href="#" class="insurance">Insurances</a>
							<ul class="subNav">
								<li><a href="<c:url value='/admin/insurance/add' />">Add Insurances</a></li>
								<li><a href="<c:url value='/admin/insurance' />">Insurance List</a>
							</ul>
						</li>

							<%--  <li id="productivityMenu"><a href="#" class="arProductivity">AR Productivity</a>
							<ul class="subNav">
								<li><a href="<c:url value='/flows/arProductivity/add' />">Add AR Productivity</a></li>
								<li><a href="<c:url value='/flows/arProductivity' />">AR Productivity List</a>
							</ul>
						</li> --%>

						<li id="paymentTypeMenu"><a href="#" class="paymentType">Payment Type</a>
							<ul class="subNav">
								<li><a href="<c:url value='/admin/payment/add' />">Add Payment Type</a></li>
								<li><a href="<c:url value='/admin/payment' />">Payment Type List</a>
							</ul>
						</li>

						<li id="doctorMenu"><a href="#" class="doctor">Doctor</a>
							<ul class="subNav">
								<li><a href="<c:url value='/admin/doctor/add' />">Add Doctor</a></li>
								<li><a href="<c:url value='/admin/doctor' />">Doctor List</a>
							</ul>
						</li>

						<li id="emailTemplateMenu"><a href="#" class="emailTemplate">Email Templates</a>
							<ul class="subNav">
								<li><a href="<c:url value='/admin/emailtemplate' />">Email
										Templates List</a>
								<!-- <li><a href="<c:url value='/admin/emailtemplate/add' />">Add
										Email Template</a></li> -->
							</ul>
						</li>
					</ul>
				</sec:authorize>


				<sec:authorize ifAnyGranted="Document Manager">
					<li id="processManualMenu"><a href="#" class="mpm">Manage Process Manuals</a>
			            <ul>
			            	<li><a href="<c:url value='/processmanual/add' />">Add Section</a></li>
			              	<li class="active"><a href="<c:url value='/processmanual' />">List Sections</a></li>
			            </ul>
			        </li>
		        </sec:authorize>

		        <sec:authorize ifAnyGranted="Trainee, Standard User, Admin">
					<li  id="processManualMenu"><a href="#" class="mpm">Process Manuals</a>
			            <ul>
			              <li class="active"><a href="<c:url value='/processmanual' />">List Sections</a></li>
			            </ul>
			        </li>
		        </sec:authorize>

				<sec:authorize ifAnyGranted="Standard User">

					<c:if test="${showChargeBatchMenu}">
						<li id="chargeBatchMenu"><a href="#" class="chargeBatch">Charge Batching Sys.</a>
							<ul class="subNav">
								<li><a href="<c:url value='/chargebatchprocess/add' />">Add Charge Batch</a></li>
								<li><a href="<c:url value='/chargebatchprocess' />">Charge Batch List</a></li>
								<li><a href="<c:url value='/chargebatchprocess/print' />">Print Charge Batch</a></li>
								<!-- li><a href="<c:url value='/chargebatchprocess/report' />">Report Batch</a></li -->
							</ul>
						</li>
						<%-- <li id="chargeBatchProductivityMenu"><a href="#">Charge Batch Productivity</a>
							<ul class="subNav">
								<li><a href="<c:url value='/chargeproductivity' />">Add Productivity</a></li>
								<li><a href="<c:url value='/chargeproductivity/list' />">List Productivity</a></li>
								<li><a href="<c:url value='/chargeproductivityreject' />">List Rejected Batchs</a></li>
								<li><a href="<c:url value='/chargeproductivityreject/add' />">Add Rejection</a></li>
							</ul>

						</li> --%>
					</c:if>
					<c:if test="${showPaymentBatchMenu || isOffsetManager}">
						<li id="paymentBatchingMenu"><a href="#" class="paymentBatch">Payment Batching Sys.</a>
							<ul class="subNav">
								<sec:authorize ifAnyGranted="P-8,P-5">
									<li><a href="<c:url value='/paymentbatch/add' />">Add Payment Batch</a></li>
								</sec:authorize>
								<li><a href="<c:url value='/paymentbatch' />">Payment Batch List</a></li>
								<li><a href="<c:url value='/paymentbatch/report' />">Print Payment Batch</a></li>
							</ul>
						</li>
					</c:if>

					<c:if test="${showPaymentBatchMenu}">
						<li id="paymentProduvtivityReportMenu"><a href="#"
							class="productivityReport">Productivity Report</a>
							<ul class="subNav">
								<li><a href="<c:url value='/paymentproductivityERA/report'/>">ERA Report</a></li>
								<li><a href="<c:url value='/paymentproductivitynonera/report'/>">NON ERA Report</a></li>
								<li><a href="<c:url value='/paymentproductivitynonera/capreport'/>">CAP Report</a></li>
								<!-- <li><a href="<c:url value='/paymentproductivityrefundrequest/report'/>"> Refund Request</a></li>  -->
								<li><a href="<c:url value='/paymentproderaoffset/report'/>">Offset Report</a></li>
								<li><a href="<c:url value='/paymentoffsetmanager/report'/>">Offset Posted</a></li>
							</ul></li>
					</c:if>
				</sec:authorize>

		        <sec:authorize ifAnyGranted="P-1">
					<li id="evaluationMenu"><a href="#" class="evaluationMenu">Trainee Evaluation</a>
						<ul class="subNav">
							<li><a href="<c:url value='/traineeevaluation'/>">Trainee List</a></li>
						</ul>
					</li>
		        </sec:authorize>

		        <sec:authorize ifAnyGranted="Document Manager, P-1, Trainee">
					<li id="reportMenu"><a href="#" class="reportMenu">Reports</a>
						<ul class="subNav">
						 	<sec:authorize ifAnyGranted="P-1">
								<li><a href="<c:url value='/generatereport/traineeevaluationreport'/>">Evaluation Report</a></li>
							</sec:authorize>
							<sec:authorize ifAnyGranted="Document Manager, Trainee">
								<li><a href="<c:url value='/generatereport/processmanualreport'/>">Process Manual</a></li>
							</sec:authorize>
						</ul>
					</li>
		        </sec:authorize>

				 <sec:authorize ifAnyGranted="P-11,P-12">
						<li id="doctorMenu"><a href="#" class="doctor">Doctor</a>
							<ul class="subNav">
								<li><a href="<c:url value='/admin/doctor/add' />">Add Doctor</a></li>
								<li><a href="<c:url value='/admin/doctor' />">Doctor List</a>
							</ul>
						</li>

						<li id="insuranceMenu"><a href="#" class="insurance">Insurances</a>
							<ul class="subNav">
								<li><a href="<c:url value='/admin/insurance/add' />">Add Insurances</a></li>
								<li><a href="<c:url value='/admin/insurance' />">Insurance List</a>
							</ul>
						</li>
				 </sec:authorize>

				 <sec:authorize ifAnyGranted="P-12">
					<%-- c:if test="${showPaymentBatchMenu}">--%>
						<li id="paymentTypeMenu"><a href="#" class="paymentType">Payment Type</a>
							<ul class="subNav">
								<li><a href="<c:url value='/admin/payment/add' />">Add Payment Type</a></li>
								<li><a href="<c:url value='/admin/payment' />">Payment Type List</a>
							</ul></li>
					<%-- /c:if >--%>
				</sec:authorize>

				 <sec:authorize ifAnyGranted="P-13">
				 <li id="cashlogMenu"><a href="#" class="cashlogReports">Cashlog
								Reports</a>
							<ul class="subNav">
								<li><a href="<c:url value='/cashlog/'/>">Generate
										Report</a></li>
							</ul></li>
				 </sec:authorize>

				<li id="settingsMenu">
					<a href="#" class="settings">Settings</a>
					<ul class="subNav">
						<li><a href="<c:url value='/myprofile'/>">My Profile</a></li>
						<li><a href="<c:url value='/changepassword'/>">Change Password</a></li>
						<sec:authorize ifAnyGranted="Admin">
							<li><a href="<c:url value='/admin/evaluationsettings'/>">Evaluation Settings</a></li>
						</sec:authorize>
					</ul>
				</li>
			</ul>

		</div>
	</div>
	<!--Nav Container End-->
</div>
<!-- Left Side End-->

