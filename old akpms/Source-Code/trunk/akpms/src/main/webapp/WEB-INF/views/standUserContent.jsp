<%@page import="argus.util.Constants"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="#">Home &raquo; </a></li>
			<li>Dashboard</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->

	<!-- Notification Start-->
	<div class="notification" id="notification">
		<h3>Notifications</h3>
		<ul id="notificationUL" style="overflow: auto; height:80px; overflow-x: hidden">
		</ul>
		<div class="view-all">
			<a class="btn-red" href="<c:url value='/notifications'/>">View All</a>
		</div>
	</div>
	<!-- Notification End-->
	
		<div class="content-box">
			<!-- Statistics Start -->
			<sec:authorize ifAnyGranted="P-11,P-12">
			<div class="stat-box">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th colspan="2"><h3>Master Data Tables</h3></th>
					</tr>
					<tr class="odd">
						<td align="left" class="stat-left-txt"><a
							href="${contextPath}admin/doctor">Total Doctors</a></td>
						<td align="right" class="stat-right-txt"><a
							href="${contextPath}admin/doctor">${totalDoctors}</a></td>
					</tr>
					<tr class="even">
						<td align="left" class="stat-left-txt"><a
							href="${contextPath}admin/insurance">Total Insurances</a></td>
						<td align="right" class="stat-right-txt"><a
							href="${contextPath}admin/insurance">${totalInsurances}</a></td>
					</tr>
					<sec:authorize ifAnyGranted="P-12">
						<tr class="odd">
							<td align="left" class="stat-left-txt left-round"><a
								href="${contextPath}admin/payment">Total Payment Types</a></td>
							<td align="right" class="stat-right-txt right-round"><a
								href="${contextPath}admin/payment">${totalPaymentTypes }</a></td>
						</tr>
					</sec:authorize>
					<!-- tr class="even">
						<td align="left" class="stat-left-txt"><a href="${contextPath}admin/doctor?status=1">Active Doctors</a></td>
						<td align="right" class="stat-right-txt"><a href="${contextPath}admin/doctor?status=1">${activeDoctors}</a></td>
					</tr>
					<tr class="odd">
						<td align="left" class="stat-left-txt left-round"><a href="${contextPath}admin/doctor?status=0">Inactive Doctors</a></td>
						<td align="right" class="stat-right-txt right-round"><a href="${contextPath}admin/doctor?status=0">${inactiveDoctors}</a></td>
					</tr -->

				</table>
			</div>
			</sec:authorize>
			<!-- Statistics End -->
			<!-- Activities Start -->
			<div class="activity-box">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th colspan="3"><h3>Hourly Projects Productivity</h3></th>
					</tr>
					<tr class="odd">
						<td align="left" class="stat-left-txt left-round"><a
							href="paymentproductivityhourly">Hourly List</a></td>
						<td align="right">${hourly}</td>
						<td align="right" class="stat-right-txt right-round"><a
							href="paymentproductivityhourly/add">Add Hourly</a></td>
					</tr>
				</table>
			</div>
			<div class="clr"></div>
			<!-- Activities End -->
		</div>


	<!-- <div class="content-box"> -->
	<!-- Statistics Start -->
	<%-- <div class="stat-box">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th colspan="2"><h3>Payment Types</h3></th>
					</tr>
					<tr class="odd">
						<td align="left" class="stat-left-txt"><a href="${contextPath}admin/payment">Total Payment Types</a></td>
						<td align="right" class="stat-right-txt"><a href="${contextPath}admin/payment">${totalPaymentTypes }</a></td>
					</tr>
					<tr class="even">
						<td align="left" class="stat-left-txt"><a href="${contextPath}admin/payment?status=1">Active Payment Types</a></td>
						<td align="right" class="stat-right-txt"><a href="${contextPath}admin/payment?status=1">${activePaymentTypes}</a></td>
					</tr>
					<tr class="odd">
						<td align="left" class="stat-left-txt left-round"><a href="${contextPath}admin/payment?status=0">Inactive Payment Types</a></td>
						<td align="right" class="stat-right-txt right-round"><a href="${contextPath}admin/payment?status=0">${inactivePaymentTypes}</a></td>
					</tr>
				</table>
			</div> --%>
	<!-- Statistics End -->
	<!-- <div class="clr"></div>
		</div> -->

	<c:set var="isOffsetManager" value="false"></c:set>
	<c:set var="showChargeBatchMenu" value="false"></c:set>
	<c:set var="showPaymentMenu" value="false"></c:set>
	<c:set var="showARMenu" value="false"></c:set>
	
	<sec:authorize ifAnyGranted="P-5">
	<c:set var="isOffsetManager" value="true"></c:set>
	</sec:authorize>
	
	<c:forEach var="dept"
		items="${pageContext.request.userPrincipal.principal.departments}">
		<c:if test="${dept.id==1}">
			<c:set var="showChargeBatchMenu" value="true"></c:set>
		</c:if>
		<c:if test="${dept.id==2}">
			<c:set var="showPaymentMenu" value="true"></c:set>
		</c:if>
		<c:if test="${dept.id==3}">
			<c:set var="showARMenu" value="true"></c:set>
		</c:if>
	</c:forEach>

	<c:if test="${showPaymentMenu}">
		<div class="content-box">
			<div class="stat-box">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th colspan="3"><h3>Payments Team Productivity</h3></th>
					</tr>
					<tr class="odd">
						<td align="left" class="stat-left-txt"><a
							href="paymentproductivitynonera?prodType=1">ERA List</a></td>
						<td align="right">${era}</td>
						<td align="right" class="stat-right-txt"><a
							href="paymentproductivityERA/add">Add ERA</a></td>
					</tr>
					<tr class="even">
						<td align="left" class="stat-left-txt"><a
							href="paymentproductivitynonera?prodType=2">NON-ERA List</a></td>
						<td align="right">${nonEra}</td>
						<td align="right" class="stat-right-txt"><a
							href="paymentproductivitynonera/add?prodType=2">Add NON-ERA</a></td>
					</tr>
					<tr class="odd">
						<td align="left" class="stat-left-txt left-round"><a
							href="paymentproductivitynonera?prodType=3">CAP List</a></td>
						<td align="right">${cap}</td>
						<td align="right" class="stat-right-txt right-round"><a
							href="paymentproductivitynonera/add?prodType=3">Add CAP</a></td>
					</tr>
				
				</table>
			</div>
			<div class="activity-box">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th colspan="3"><h3>Payments Process Workflow</h3></th>
					</tr>
					<tr class="odd">
						<td align="left" class="stat-left-txt"><a
							href="paymentproductivitynonera?workflowId=1">To AR IPA FFS
								HMO</a></td>
						<td align="right" class="stat-right-txt"><a
							href="paymentproductivitynonera?workflowId=1">${toArIpaFfsHmo}</a></td>
					</tr>
					<tr class="even">
						<td align="left" class="stat-left-txt"><a
							href="paymentproductivitynonera?workflowId=2">To AR FFS</a></td>
						<td align="right" class="stat-right-txt"><a
							href="paymentproductivitynonera?workflowId=2">${toArFfs}</a></td>
					</tr>
					<tr class="odd">
						<td align="left" class="stat-left-txt"><a
							href="paymentproductivitynonera?workflowId=3">To AR CEP</a></td>
						<td align="right" class="stat-right-txt"><a
							href="paymentproductivitynonera?workflowId=3">${toArCep}</a></td>
					</tr>
					<tr class="even">
						<td align="left" class="stat-left-txt"><a
							href="paymentproductivitynonera?workflowId=4">To AR MCL</a></td>
						<td align="right" class="stat-right-txt"><a
							href="paymentproductivitynonera?workflowId=4">${toArMcl}</a></td>
					</tr>
					<tr class="odd">
						<td align="left" class="stat-left-txt"><a
							href="paymentproductivitynonera?workflowId=7">To AR MCR</a></td>
						<td align="right" class="stat-right-txt"><a
							href="paymentproductivitynonera?workflowId=7">${toArMcr}</a></td>
					</tr>
					<tr class="even">
						<td align="left" class="stat-left-txt"><a
							href="paymentproductivitynonera?workflowId=8">To AR WC</a></td>
						<td align="right" class="stat-right-txt"><a
							href="paymentproductivitynonera?workflowId=8">${toArWc}</a></td>
					</tr>
					<%-- <tr class="odd">
						<td align="left" class="stat-left-txt">
							<a href="<c:url value='/flows/paymentpostingworkflow'/>">Payment Posting Log</a>
							[<a href="<c:url value='/flows/paymentpostingworkflow?offset=1'/>">Offset (${offsetCount})</a>]
						</td>
						<td align="right" class="stat-right-txt">
							<a href="<c:url value='/flows/arProductivity?workflowId=5'/>">${paymentPosting}</a>
						</td>
					</tr> --%>
					<tr class="even">
						<td align="left" class="stat-left-txt left-round" ><a
							href="paymentproductivitynonera?workflowId=6">Query to TL</a></td>
						<td align="right" class="stat-right-txt right-round">${queryToTl}</td>
					</tr>
				</table>
			</div>
			<div class="clr"></div>
		</div>
	</c:if>

	<%-- <div class="content-box">
		<sec:authorize ifAllGranted="Standard User,P-5">
			<div class="stat-box">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th colspan="3"><h3>Manage Offset</h3></th>
					</tr>
					<tr class="odd">
						<td align="left" class="stat-left-txt"><a
							href="paymentproderaoffset">Offset Reference List</a></td>
						<td align="right" class="stat-right-txt"><a
							href="paymentproderaoffset">${offset}</a></td>
					</tr>
					<tr class="even">
						<td align="left" class="stat-left-txt"><a
							href="paymentbatch?paymenttype=<%=Constants.PAYMENT_TYPE_OFFSET%>">Offset
								Payment Batches</a></td>
						<td align="right" class="stat-right-txt"><a
							href="paymentbatch?paymenttype=<%=Constants.PAYMENT_TYPE_OFFSET%>">${totalOffsetBatches}</td>
					</tr>
					<tr class="odd">
						<td align="left" class="stat-left-txt left-round"><a
							href="paymentoffsetmanager">OFFSET Posted</a>&nbsp;&nbsp;[<a
							href='paymentbatch/add?type=offset'>Add Offline Offset</a>]</td>
						<td align="right" class="stat-right-txt right-round"><a
							href="paymentoffsetmanager">${postedOffset}</a></td>
					</tr>
				</table>
			</div>
		</sec:authorize>
		<c:if test="${credentialingAccounting}">
			<div class="activity-box" >
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th colspan="2"><h3>Credentialing & Accounting Team
								productivity</h3></th>
					</tr>
					<tr class="odd">
						<td align="left" class="stat-left-txt"><a
							href="credentialingaccountingproductivity/">Credentialing/Accounting
								Productivity</a></td>
						<td class="stat-right-txt right-round">&nbsp;</td>
					</tr>
				</table>
			</div>
		</c:if>
		<div class="clr"></div>
	</div>
 --%>
	<div class="content-box">
		<%-- <sec:authorize ifAnyGranted="Standard User"> --%>
			<c:if test="${showChargeBatchMenu}">
				<div class="stat-box">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<th colspan="3"><h3>Demo, CE & Coding Validation Team Prod. & Process Workflow</h3></th>
						</tr>
						<tr class="odd">
							<td align="left" class="stat-left-txt">
								<a href="<c:url value='/chargeproductivity/list' />">List Productivity</a>
							</td>
							<td align="right" class="stat-right-txt">
								Add <a href="<c:url value='/chargeproductivity?prodType=Coding' />">Coding</a>
									<a href="<c:url value='/chargeproductivity?prodType=Demo' />">Demo</a>
									<a href="<c:url value='/chargeproductivity?prodType=CE' />">CE</a>
								Prod.
							</td>
						</tr>
						<tr class="even">
							<td align="left" class="stat-left-txt"><a
								href="<c:url value='/chargeproductivity/list?onHold=1' />">On Hold</a></td>
							<td align="right" class="stat-right-txt">${totalOnHold}</td>
						</tr>
						<tr class="even1">
							<td align="left" class="stat-left-txt"><a href="<c:url value='/chargeproductivityreject' />">Rejected Logs</a></td>
							<td align="right" class="stat-right-txt"><a href="<c:url value='/chargeproductivityreject/add' />">Add Rejection</a></td>
						</tr>
						
						<tr class="odd">
							<td align="left" class="stat-left-txt"><a href="<c:url value='/chargeproductivityreject?fetchType=newrejection' />">New Rejections</a></td>
							<td align="right" class="stat-right-txt">${rejectionCount}</td>
						</tr>
						
						<tr class="even">
							<td align="left" class="stat-left-txt"><a href="<c:url value='/chargeproductivityreject?fetchType=1reqdue' />">1st Request Due</a></td>
							<td align="right" class="stat-right-txt">${firstRequestDueCount}</td>
						</tr>
						
						<tr class="odd">
							<td align="left" class="stat-left-txt"><a href="<c:url value='/chargeproductivityreject?fetchType=1req' />">Number Of First Requests </a></td>
							<td align="right" class="stat-right-txt">${numberOfFirstRequestsCount}</td>
						</tr>
						
						<tr class="even">
							<td align="left" class="stat-left-txt"><a href="<c:url value='/chargeproductivityreject?fetchType=2reqdue' />">2nd Request Due</a></td>
							<td align="right" class="stat-right-txt">${secondRequestDueCount}</td>
						</tr>
						
						<tr class="odd">
							<td align="left" class="stat-left-txt"><a href="<c:url value='/chargeproductivityreject?fetchType=2req' />">Number Of Second Requests</a></td>
							<td align="right" class="stat-right-txt">${numberOfSecondRequestsCount}</td>
						</tr>
						
						<tr class="even">
							<td align="left" class="stat-left-txt"><a href="<c:url value='/chargeproductivityreject?fetchType=resolved' />">Resolved Rejections </a></td>
							<td align="right" class="stat-right-txt">${resolvedRejectionCount}</td>
						</tr>
						
						<tr class="odd">
							<td align="left" class="stat-left-txt"><a href="<c:url value='/chargeproductivityreject?fetchType=resolvedcpt' />">Resolved Rejections with dummy CPT </a></td>
							<td align="right" class="stat-right-txt">${resolvedRejectionWithDummyCPTCount}</td>
						</tr>
						
						<tr class="even">
							<td align="left" class="stat-left-txt left-round"><a href="<c:url value='/chargeproductivityreject?fetchType=completed' />">Completed Rejections </a></td>
							<td align="right" class="stat-right-txt right-round">${completedRejectionCount}</td>
						</tr>
						

						<%-- <tr class="even">
							<td align="left" class="stat-left-txt left-round">
								<a href="<c:url value='/flows/codingcorrectionlogworkflow'/>">Coding Correction List</a>
							</td>
							<td align="right" class="stat-right-txt right-round">
								<a href="<c:url value='/flows/codingcorrectionlogworkflow'/>">${codingCorrCount}</a>
							</td>
						</tr> --%>
					</table>
				</div>
			</c:if>
			
			<c:if test="${showARMenu || showChargeBatchMenu}">
				<!-- Statistics Start -->
				<div class="activity-box">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<th colspan="2"><h3>Coding Correction Log</h3></th>
						</tr>
						<tr class="odd">
								<td align="left" class="stat-left-txt  "><a
									href="flows/codingcorrectionlogworkflow?workflow=Forward to coding correction">Forward to coding correction</a></td>
								<td align="right" class="stat-right-txt  "><a
									href="flows/codingcorrectionlogworkflow?workflow=Forward to coding correction">${cclForwardToCodingCorrection}</a></td>
						</tr>
						<tr class="even">
							<td align="left" class="stat-left-txt  "><a
								href="flows/codingcorrectionlogworkflow?workflow=Escalate to Argus TL">Escalate to Argus TL</a></td>
							<td align="right" class="stat-right-txt  "><a
								href="flows/codingcorrectionlogworkflow?workflow=Escalate to Argus TL">${cclEscalateToArgusTL}</a></td>
						</tr>
						
						<tr class="odd">
							<td align="left" class="stat-left-txt  "><a
								href="flows/codingcorrectionlogworkflow?workflow=Return to AR">Return to AR</a></td>
							<td align="right" class="stat-right-txt  "><a
								href="flows/codingcorrectionlogworkflow?workflow=Return to AR">${cclReturnToAr}</a></td>
						</tr>
						<tr class="even">
							<td align="left" class="stat-left-txt  "><a
								href="flows/codingcorrectionlogworkflow?workflow=Coding to CE">Coding to CE</a></td>
							<td align="right" class="stat-right-txt  "><a
								href="flows/codingcorrectionlogworkflow?workflow=Coding to CE">${cclCodingToCE}</a></td>
						</tr>
						<tr class="odd">
							<td align="left" class="stat-left-txt  left-round"><a
								href="flows/codingcorrectionlogworkflow?workflow=Done">Done</a></td>
							<td align="right" class="stat-right-txt  right-round"><a
								href="flows/codingcorrectionlogworkflow?workflow=Done">${cclDone}</a></td>
						</tr>
					</table>
				</div>
			</c:if>
			<%-- </sec:authorize> --%>
			<div class="clr"></div>
			<!-- Statistics End -->
		</div>
	
	
		<div class="content-box">
			<c:if test="${showPaymentMenu || showARMenu || isOffsetManager}">
			<!-- Statistics Start -->
			<div class="stat-box">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th colspan="2"><h3>Offset Reference &amp; Postings </h3></th>
					</tr>
					<tr class="even1">
						<td align="left" class="stat-left-txt"><a href="paymentproderaoffset">Offset Reference List </a></td>
						<td align="right" class="stat-right-txt"><a href="paymentproderaoffset">${offset}</a></td>
					</tr>
					
					<tr class="odd">
						<td align="left" class="stat-left-txt  "><a
							href="paymentproderaoffset?status=1">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Offset Reference Pending </a></td>
						<td align="right" class="stat-right-txt  "><a
							href="paymentproderaoffset?status=1">${offsetSatusPending}</a></td>
					</tr>
					
					<tr class="even">
						<td align="left" class="stat-left-txt  "><a
							href="paymentproderaoffset?status=2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Offset Reference AR Step Completed</a></td>
						<td align="right" class="stat-right-txt  "><a
							href="paymentproderaoffset?status=2">${offsetStatusARCompleted}</a></td>
					</tr>
					
					<tr class="odd">
						<td align="left" class="stat-left-txt  left-round"><a
							href="paymentproderaoffset?status=3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Offset Reference Offset Resolved</a></td>
						<td align="right" class="stat-right-txt  right-round"><a
							href="paymentproderaoffset?status=3">${offsetStatusOffsetResolve}</a></td>
					</tr>
				</table>
			</div>
			</c:if>
			<c:if test="${showChargeBatchMenu || showARMenu}">
			
			<!-- Activities Start -->
			 <div class="activity-box">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th colspan="2"><h3>Rekey Request Log</h3></th>
					</tr>
					<%-- <tr class="even1">
						<td align="left" class="stat-left-txt"><a
							href="paymentproderaoffset">Offset Reference List </a></td>
						<td align="right" class="stat-right-txt"><a
							href="paymentproderaoffset">${offset}</a></td>
					</tr> --%>
					
					<tr class="odd">
						<td align="left" class="stat-left-txt  "><a
							href="flows/rekeyrequest?status=1">Query to Charge Entry</a></td>
						<td align="right" class="stat-right-txt  "><a
							href="flows/rekeyrequest?status=1">${queryToCe}</a></td>
					</tr>
					
					<%-- <tr class="even">
						<td align="left" class="stat-left-txt  "><a
							href="flows/rekeyrequest?status=2">Coding to Charge Entry returned</a></td>
						<td align="right" class="stat-right-txt  "><a
							href="flows/rekeyrequest?status=2">${codingToCeReturned}</a></td>
					</tr> --%>
					
					<tr class="even">
						<td align="left" class="stat-left-txt  "><a
							href="flows/rekeyrequest?status=3">Return to AR</a></td>
						<td align="right" class="stat-right-txt  "><a
							href="flows/rekeyrequest?status=3">${returnToAr}</a></td>
					</tr>
					
					<%-- <tr class="even">
						<td align="left" class="stat-left-txt  "><a
							href="flows/rekeyrequest?status=4">Query to Coding</a></td>
						<td align="right" class="stat-right-txt  "><a
							href="flows/rekeyrequest?status=4">${queryToCoding}</a></td>
					</tr> --%>
					
					<tr class="odd">
						<td align="left" class="stat-left-txt  left-round"><a
							href="flows/rekeyrequest?status=5">Closed </a></td>
						<td align="right" class="stat-right-txt  right-round"><a
							href="flows/rekeyrequest?status=5">${close}</a></td>
					</tr>

				</table>
			</div>
		</c:if>
			
			<div class="clr"></div>
		</div>
	
	
	<!-- Statistics End -->
	
		
		
	
	
	<c:if test="${showARMenu}">
		<div class="content-box">
			 <div class="stat-box" >
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th colspan="3"><h3>Accounts Receivable (AR) Team Productivity &amp; Process Workflow</h3></th>
					</tr>
					<tr class="even">
						<td align="left" class="stat-left-txt">
							<a href="<c:url value='/flows/arProductivity' />">List AR Productivity</a>
						</td>
						<td align="right" class="stat-right-txt">
							<a href="<c:url value='/flows/arProductivity/add' />">Add AR Productivity</a>
						</td>
					</tr>
					<tr class="odd">
						<td align="left" class="stat-left-txt">
							<a href="<c:url value='/flows/arProductivity?workflowId=1' />">Adjustment Log</a>
							<%-- [<a href="<c:url value='/flows/adjustmentlogs'/>">List All</a>]
							<br/>
							<a href="<c:url value='/flows/adjustmentlogs?timilyFiling=1'/>" style="font-size:10px">Timily Filing (${timilyFiling})</a>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="<c:url value='/flows/adjustmentlogs?timilyFiling=0'/>" style="font-size:10px">Without Timily Filing (${withoutTimilyFiling})</a> --%>
						</td>
						<td align="right" class="stat-right-txt">
							<a href="<c:url value='/flows/arProductivity?workflowId=1' />">${adjustLog}</a>
						</td>
					</tr>
					<tr class="even">
						<td align="left" class="stat-left-txt">
							<a href="<c:url value='/flows/arProductivity?workflowId=2'/>">Coding Correction</a>
							<%-- [<a href="<c:url value='/flows/codingcorrectionlogworkflow'/>">List All</a>] --%>
						</td>
						<td align="right" class="stat-right-txt">
							<a href="<c:url value='/flows/arProductivity?workflowId=2'/>">${codingCorr}</a>
						</td>
					</tr>
					<tr class="odd">
						<td align="left" class="stat-left-txt">
							<a href="<c:url value='/flows/arProductivity?workflowId=5'/>">Payment Posting Log</a>
							<%-- [<a href="<c:url value='/flows/paymentpostingworkflow'/>">List All</a>]
							[<a href="<c:url value='/flows/paymentpostingworkflow?offset=1'/>">Offset (${offsetCount})</a>] --%>
						</td>
						<td align="right" class="stat-right-txt">
							<a href="<c:url value='/flows/arProductivity?workflowId=5'/>">${paymentPosting}</a>
						</td>
					</tr>
					<%-- <tr class="even">
						<td align="left" class="stat-left-txt">
							<a href="<c:url value='/flows/arProductivity?workflowId=10'/>">Request For Docs</a>
						</td>
						<td align="right" class="stat-right-txt">
							<a href="<c:url value='/flows/arProductivity?workflowId=10'/>">${requestForDocs}</a>
						</td>
					</tr> --%>
					 <tr class="even">
						<td align="left" class="stat-left-txt">
							<a href="<c:url value='/flows/arProductivity?workflowId=8'/>">Refund Request</a>
							[<a href="<c:url value='/flows/refundrequest'/>">List All</a>]
						</td>
						<td align="right" class="stat-right-txt">
							<a href="<c:url value='/flows/arProductivity?workflowId=8'/>">${refundRequest}</a>
						</td>
					</tr>
					
					<%-- <tr class="even">
						<td align="left" class="stat-left-txt">
							<a href="<c:url value='/flows/arProductivity?workflowId=4'/>">Re-key request to charge posting</a>
							[<a href="<c:url value='/flows/rekeyrequest'/>">List All</a>]
						</td>
						<td align="right" class="stat-right-txt">
							<a href="<c:url value='/flows/arProductivity?workflowId=4'/>">${rekeyReq}</a>
						</td>
					</tr> --%>
					<tr class="odd">
						<td align="left" class="stat-left-txt left-round">
							<a href="<c:url value='/flows/arProductivity?workflowId=7'/>">Query to TL</a>
						</td>
						<td align="right" class="stat-right-txt right-round">
							<a href="<c:url value='/flows/arProductivity?workflowId=7'/>">${toTL}</a>
						</td>
					</tr>
					<tr class="odd">
						<td align="left" class="stat-left-txt" >
						<a href="<c:url value='/flows/arProductivity?workflowId=7&substatus=1'/>">&nbsp;&nbsp;&nbsp;&nbsp; Open</a></td>
						<td align="right" class="stat-right-txt">
							<a href="<c:url value='/flows/arProductivity?workflowId=7&substatus=1'/>">${opened} </a>
						</td>
					</tr>
					<tr class="odd">
						<td align="left" class="stat-left-txt" ><a
							href="<c:url value='/flows/arProductivity?workflowId=7&substatus=2'/>">&nbsp;&nbsp;&nbsp;&nbsp; Back To Team</a></td>
						<td align="right" class="stat-right-txt right-round">
							<a href="<c:url value='/flows/arProductivity?workflowId=7&substatus=2'/>">${backToTeam}</a>
						</td>
					</tr>
					<tr class="odd">
						<td align="left" class="stat-left-txt" ><a
							href="<c:url value='/flows/arProductivity?workflowId=7&substatus=3'/>">&nbsp;&nbsp;&nbsp;&nbsp; Closed</a></td>
						<td align="right" class="stat-right-txt right-round">
							<a href="<c:url value='/flows/arProductivity?workflowId=7&substatus=3'/>">${closed} </a>
						</td>
					</tr>
					<tr class="even">
						<td align="left" class="stat-left-txt left-round">
							<a href="<c:url value='/flows/arProductivity?followups=1'/>">Next Follow Up Date</a>
						</td> 
						<td align="right" class="stat-right-txt left-round">
							<a href="<c:url value='/flows/arProductivity?followups=1'/>">${followUpDate}</a>
						</td>
					</tr>					
				</table>
			</div>
		
		 	<div class="activity-box" >
			 	<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th colspan="3"><h3>Adjustment Log</h3></th>
					</tr>
					<tr class="odd">
						<td align="left" class="stat-left-txt">
							<a href="<c:url value='/flows/arProductivity?workflowId=1' />">Adjustment Log</a>
							[<a href="<c:url value='/flows/adjustmentlogs'/>">List All</a>]
							<br/>
							
						</td>
						<td align="right" class="stat-right-txt">
							<a href="<c:url value='/flows/arProductivity?workflowId=1' />">${adjustLog}</a>
						</td>
					</tr>
					<tr class="even1">
						<td align="left" class="stat-left-txt">
							<a href="<c:url value='/flows/adjustmentlogs?timilyFiling=1'/>" >Timely Filing </a>
						</td>
						<td align="right" class="stat-right-txt">
							<a href="<c:url value='/flows/adjustmentlogs?timilyFiling=1' />">${timilyFiling}</a>
						</td>
					</tr>
					
					<tr class="odd">
						<td align="left" class="stat-left-txt">
							<a href="<c:url value='/flows/adjustmentlogs?timilyFiling=1&workFlowStatus=1'/>" >Approved </a>
						</td>
						<td align="right" class="stat-right-txt">
							<a href="<c:url value='/flows/adjustmentlogs?timilyFiling=1&workFlowStatus=1' />">${timilyFilingApprove}</a>
						</td>
					</tr>
					<tr class="even">
						<td align="left" class="stat-left-txt">
							<a href="<c:url value='/flows/adjustmentlogs?timilyFiling=1&workFlowStatus=3'/>" >Escalate </a>
						</td>
						<td align="right" class="stat-right-txt">
							<a href="<c:url value='/flows/adjustmentlogs?timilyFiling=1&workFlowStatus=3' />">${timilyFilingEscalate}</a>
						</td>
					</tr>
					<tr class="Odd">
						<td align="left" class="stat-left-txt">
							<a href="<c:url value='/flows/adjustmentlogs?timilyFiling=1&workFlowStatus=2'/>" >Reject </a>
						</td>
						<td align="right" class="stat-right-txt">
							<a href="<c:url value='/flows/adjustmentlogs?timilyFiling=1&workFlowStatus=2' />">${timilyFilingReject}</a>
						</td>
					</tr>
					<tr class="even">
						<td align="left" class="stat-left-txt">
							<a href="<c:url value='/flows/adjustmentlogs?timilyFiling=1&workFlowStatus=4'/>" >Closed </a>
						</td>
						<td align="right" class="stat-right-txt">
							<a href="<c:url value='/flows/adjustmentlogs?timilyFiling=1&workFlowStatus=4' />">${timilyFilingClosed}</a>
						</td>
					</tr>
					
					<tr class="even1">
						<td align="left" class="stat-left-txt">
							<a href="<c:url value='/flows/adjustmentlogs?timilyFiling=0'/>" >Without Timely Filing </a>
						</td>
						<td align="right" class="stat-right-txt">
							<a href="<c:url value='/flows/adjustmentlogs?timilyFiling=0'/>" >${withoutTimilyFiling}</a>
						</td>
					</tr>
					
					<tr class="odd">
						<td align="left" class="stat-left-txt">
							<a href="<c:url value='/flows/adjustmentlogs?timilyFiling=0&workFlowStatus=1'/>">Approved </a>
						</td>
						<td align="right" class="stat-right-txt">
							<a href="<c:url value='/flows/adjustmentlogs?timilyFiling=0&workFlowStatus=1' />">${withoutTimilyFilingApprove}</a>
						</td>
					</tr>
					<tr class="even">
						<td align="left" class="stat-left-txt">
							<a href="<c:url value='/flows/adjustmentlogs?timilyFiling=0&workFlowStatus=3'/>" >Escalate </a>
						</td>
						<td align="right" class="stat-right-txt">
							<a href="<c:url value='/flows/adjustmentlogs?timilyFiling=0&workFlowStatus=3' />">${withoutTimilyFilingEscalate}</a>
						</td>
					</tr>
					<tr class="Odd">
						<td align="left" class="stat-left-txt left-round">
							<a href="<c:url value='/flows/adjustmentlogs?timilyFiling=0&workFlowStatus=2'/>" >Reject </a>
						</td>
						<td align="right" class="stat-right-txt right-round">
							<a href="<c:url value='/flows/adjustmentlogs?timilyFiling=0&workFlowStatus=2' />">${withoutTimilyFilingReject}</a>
						</td>
					</tr>
					<tr class="even">
						<td align="left" class="stat-left-txt left-round">
							<a href="<c:url value='/flows/adjustmentlogs?timilyFiling=0&workFlowStatus=4'/>" >Closed </a>
						</td>
						<td align="right" class="stat-right-txt right-round">
							<a href="<c:url value='/flows/adjustmentlogs?timilyFiling=0&workFlowStatus=4' />">${withoutTimilyFilingClosed}</a>
						</td>
					</tr>
				</table>
			 </div>
			<div class="clr"></div>
		</div>
	</c:if>
	
		
	<c:if test="${showPaymentMenu || showARMenu || isOffsetManager}">
		<div class="content-box">
			<!-- Statistics Start -->
			<div class="stat-box">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th colspan="2"><h3>Payment Posting Log</h3></th>
					</tr>
					<tr class="even1">
						<td align="left" class="stat-left-txt"><a href="<c:url value='/flows/paymentpostingworkflow?offset=1'/>">Offsets Pending</a></td>
						<td align="right" class="stat-right-txt"><a href="<c:url value='/flows/paymentpostingworkflow?offset=1'/>">${offsetCountPending}</a></td>
					</tr>
					<tr class="odd1">
						<td align="left" class="stat-left-txt"><a href="<c:url value='/flows/paymentpostingworkflow?offset=1'/>">Offsets Approved</a></td>
						<td align="right" class="stat-right-txt"><a href="<c:url value='/flows/paymentpostingworkflow?offset=1'/>">${offsetCountApproved}</a></td>
					</tr>
					<tr class="even1">
						<td align="left" class="stat-left-txt"><a href="<c:url value='/flows/paymentpostingworkflow?offset=1'/>">Offsets Completed</a></td>
						<td align="right" class="stat-right-txt"><a href="<c:url value='/flows/paymentpostingworkflow?offset=1'/>">${offsetCountCompleted}</a></td>
					</tr>
					<tr class="odd1">
						<td align="left" class="stat-left-txt"><a href="<c:url value='/flows/paymentpostingworkflow'/>">Pending</a></td>
						<td align="right" class="stat-right-txt"><a href="<c:url value='/flows/paymentpostingworkflow'/>">${Pending}</a></td>
					</tr>
					<tr class="even">
						<td align="left" class="stat-left-txt"><a href="<c:url value='/flows/paymentpostingworkflow?status=Reject'/>">Rejected</a></td>
						<td align="right" class="stat-right-txt"><a href="<c:url value='/flows/paymentpostingworkflow?status=Reject'/>">${Reject}</a></td>
					</tr>
					<tr class="odd">
						<td align="left" class="stat-left-txt left-round"><a href="<c:url value='/flows/paymentpostingworkflow?status=Approve'/>">Approved</a></td>
						<td align="right" class="stat-right-txt right-round"><a href="<c:url value='/flows/paymentpostingworkflow?status=Approve'/>">${Approve}</a></td>
					</tr>
				 	<tr class="even">
						<td align="left" class="stat-left-txt"><a href="<c:url value='/flows/paymentpostingworkflow?status=Completed'/>">Completed</a></td>
						<td align="right" class="stat-right-txt"><a href="<c:url value='/flows/paymentpostingworkflow?status=Completed'/>">${Completed}</a></td>
					</tr>
			
				</table>
			</div>
			<div class="clr"></div>
			<!-- Activities End -->
		</div>
	</c:if>
</div>
