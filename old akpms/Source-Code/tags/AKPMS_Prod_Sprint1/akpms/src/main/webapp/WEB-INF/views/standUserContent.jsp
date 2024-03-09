<%@page import="argus.util.Constants"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="dashboard-container" >
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
	    <ul id="notificationUL">
	    </ul>
	    <div class="view-all"><a class="btn-red" href="<c:url value='/notifications'/>">View All</a></div>
    </div>
	<!-- Notification End-->
	<sec:authorize ifAnyGranted="P-11,P-12">
		<div class="content-box">
		<!-- Statistics Start -->
			<div class="stat-box">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
					<th colspan="2"><h3>Doctors</h3></th>
				</tr>
				<tr class="odd">
					<td align="left" class="stat-left-txt"><a href="${contextPath}admin/doctor">Total Doctors</a></td>
					<td align="right" class="stat-right-txt"><a href="${contextPath}admin/doctor">${totalDoctors}</a></td>
				</tr>
				<tr class="even">
					<td align="left" class="stat-left-txt"><a href="${contextPath}admin/doctor?status=1">Active Doctors</a></td>
					<td align="right" class="stat-right-txt"><a href="${contextPath}admin/doctor?status=1">${activeDoctors}</a></td>
				</tr>
				<tr class="odd">
					<td align="left" class="stat-left-txt left-round"><a href="${contextPath}admin/doctor?status=0">Inactive Doctors</a></td>
					<td align="right" class="stat-right-txt right-round"><a href="${contextPath}admin/doctor?status=0">${inactiveDoctors}</a></td>
				</tr>


				</table>
			</div>
			<!-- Statistics End -->
			<!-- Activities Start -->
			<div class="activity-box">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
					<th colspan="2"><h3>Insurances</h3></th>
				</tr>
				<tr class="odd">
					<td align="left" class="stat-left-txt"><a href="${contextPath}admin/insurance">Total Insurances</a></td>
					<td align="right" class="stat-right-txt"><a href="${contextPath}admin/insurane">${totalInsurances}</a></td>
				</tr>
				<tr class="even">
					<td align="left" class="stat-left-txt"><a href="${contextPath}admin/insurance?status=1">Active Insurances</a></td>
					<td align="right" class="stat-right-txt"><a href="${contextPath}admin/insurance?status=1">${activeInsurances}</a></td>
				</tr>
				<tr class="odd">
					<td align="left" class="stat-left-txt left-round"><a href="${contextPath}admin/insurance?status=0">Inactive Insurances</a></td>
					<td align="right" class="stat-right-txt right-round"><a href="${contextPath}admin/insurance?status=0">${inactiveInsurances}</a></td>
				</tr>


				</table>
			</div>
			<div class="clr"></div>
			<!-- Activities End -->
		</div>
	</sec:authorize>

	<sec:authorize ifAnyGranted="P-12">
		<div class="content-box">
			<!-- Statistics Start -->
			<div class="stat-box">
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
			</div>
			<!-- Statistics End -->
			<div class="clr"></div>
		</div>
	</sec:authorize>

	<c:set var="showChargeBatchMenu" value="false"></c:set>
	<c:set var="showPaymentMenu" value="false"></c:set>
	<c:set var="showARMenu" value="false"></c:set>

	<c:forEach var="dept" items="${pageContext.request.userPrincipal.principal.departments}">
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
						<th colspan="3"><h3>Payment Productivity Types</h3></th>
					</tr>
					<tr class="odd">
						<td align="left" class="stat-left-txt"><a href="paymentproductivitynonera?prodType=1">ERA List</a></td>
						<td align="right">${era}</td>
						<td align="right" class="stat-right-txt"><a href="paymentproductivityERA/add">Add ERA</a></td>
					</tr>
					<tr class="even">
						<td align="left" class="stat-left-txt"><a href="paymentproductivitynonera?prodType=2">NON-ERA List</a></td>
						<td align="right">${nonEra}</td>
						<td align="right" class="stat-right-txt"><a href="paymentproductivitynonera/add?prodType=2">Add NON-ERA</a></td>
					</tr>
					<tr class="odd">
						<td align="left" class="stat-left-txt"><a href="paymentproductivitynonera?prodType=3">CAP List</a></td>
						<td align="right" >${cap}</td>
						<td align="right" class="stat-right-txt"><a href="paymentproductivitynonera/add?prodType=3">Add CAP</a></td>
					</tr>
					<tr class="even1">
						<td align="left" class="stat-left-txt left-round"><a href="paymentproductivityhourly">Hourly List</a></td>
						<td align="right">${hourly}</td>
						<td align="right" class="stat-right-txt right-round"><a href="paymentproductivityhourly/add">Add Hourly</a></td>
					</tr>
				</table>
			</div>
			<div class="activity-box">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th colspan="3"><h3>Payment Workflow Reports</h3></th>
					</tr>
					<tr class="odd">
						<td align="left" class="stat-left-txt"><a href="paymentproductivitynonera?workflowId=1">To AR IPA FFS HMO</a></td>
						<td align="right" class="stat-right-txt"><a href="paymentproductivitynonera?workflowId=1">${toArIpaFfsHmo}</a></td>
					</tr>
					<tr class="even">
						<td align="left"  class="stat-left-txt"><a href="paymentproductivitynonera?workflowId=2">To AR FFS</a></td>
						<td align="right" class="stat-right-txt"><a href="paymentproductivitynonera?workflowId=2">${toArFfs}</a></td>
					</tr>
					<tr class="odd">
						<td align="left" class="stat-left-txt"><a href="paymentproductivitynonera?workflowId=3">To AR CEP</a></td>
						<td align="right" class="stat-right-txt"><a href="paymentproductivitynonera?workflowId=3">${toArCep}</a></td>
					</tr>
					<tr class="even">
						<td align="left" class="stat-left-txt"><a href="paymentproductivitynonera?workflowId=4">To AR MCL</a></td>
						<td align="right" class="stat-right-txt"><a href="paymentproductivitynonera?workflowId=4">${toArMcl}</a></td>
					</tr>
					<tr class="odd">
						<td align="left" class="stat-left-txt"><a href="paymentproductivitynonera?workflowId=6">Query to TL</a></td>
						<td align="right" class="stat-right-txt">${queryToTl}</td>
					</tr>
					<tr class="even1">
						<td align="left" class="stat-left-txt  left-round"><a href="paymentproderaoffset">Offset Reference List </a></td>
						<td align="right" class="stat-right-txt  right-round"><a href="paymentproderaoffset">${offset}</a></td>
					</tr>
				</table>
			</div>
			<div class="clr"></div>
		</div>
	</c:if>

	<div class="content-box">
		<sec:authorize ifAllGranted="Standard User,P-5">
			<div class="stat-box">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th colspan="3"><h3>Manage Offset</h3></th>
					</tr>
					<tr class="odd">
						<td align="left" class="stat-left-txt"><a href="paymentproderaoffset">Offset Reference List</a></td>
						<td align="right" class="stat-right-txt"><a href="paymentproderaoffset">${offset}</a></td>
					</tr>
					<tr class="even">
						<td align="left" class="stat-left-txt"><a href="paymentbatch?paymenttype=<%=Constants.PAYMENT_TYPE_OFFSET%>">Offset Payment Batches</a></td>
						<td align="right" class="stat-right-txt"><a href="paymentbatch?paymenttype=<%=Constants.PAYMENT_TYPE_OFFSET%>">${totalOffsetBatches}</td>
					</tr>
					<tr class="odd">
						<td align="left" class="stat-left-txt left-round">
							<a href="paymentoffsetmanager">OFFSET Posted</a>&nbsp;&nbsp;[<a href='paymentbatch/add?type=offset'>Add Offline Offset</a>]
						</td>
						<td align="right" class="stat-right-txt right-round"><a href="paymentoffsetmanager">${postedOffset}</a></td>
					</tr>
				</table>
			</div>
		</sec:authorize>
		<c:if test="${credentialingAccounting}">
			<div class="activity-box" style='display:none;'>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th colspan="2"><h3>Credentialing & Accounting Team productivity</h3></th>
					</tr>
					<tr class="odd">
						<td align="left" class="stat-left-txt"><a href="credentialingaccountingproductivity/">Credentialing/Accounting Productivity</a></td>
						<td  class="stat-right-txt right-round">&nbsp;</td>
					</tr>
				</table>
			</div>
		</c:if>
		<div class="clr"></div>
	</div>

	<div class="content-box">
		<sec:authorize ifAnyGranted="Standard User">
			<c:if test="${showChargeBatchMenu}">
				<div class="stat-box">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<th colspan="3"><h3>Charge Prod. Workflow Reports</h3></th>
						</tr>
						<tr class="odd">
							<td align="left" class="stat-left-txt"><a href="<c:url value='/chargeproductivity/list' />">List Productivity</a></td>
							<td align="right" class="stat-right-txt"><a href="<c:url value='/chargeproductivity' />">Add Productivity</a></td>
						</tr>
						<tr class="even">
							<td align="left" class="stat-left-txt"><a href="<c:url value='/chargeproductivity/list?workflow=onhold' />">On Hold</a></td>
							<td align="right"  class="stat-right-txt">${totalOnHold} (New: ${onHold})</td>
						</tr>
						<tr class="odd">
							<td align="left" class="stat-left-txt"><a href="<c:url value='/chargeproductivityreject' />">Rejected Logs</a></td>
							<td align="right" class="stat-right-txt"><a href="<c:url value='/chargeproductivityreject/add' />">Add Rejection</a></td>
						</tr>
						<tr class="even">
							<td align="left" class="stat-left-txt"><a href="<c:url value='/chargeproductivityreject?fetchType=1reqdue' />">1st Request Due</a></td>
							<td align="right" class="stat-right-txt">${firstRequestDueCount}</td>
						</tr>
						<tr class="odd">
							<td align="left" class="stat-left-txt"><a href="<c:url value='/chargeproductivityreject?fetchType=2reqdue' />">2nd Request Due</a></td>
							<td align="right" class="stat-right-txt">${secondRequestDueCount}</td>
						</tr>
						<tr class="even">
							<td align="left" class="stat-left-txt"><a href="<c:url value='/chargeproductivityreject?fetchType=1req' />">Number Of First Requests </a></td>
							<td align="right" class="stat-right-txt">${numberOfFirstRequestsCount}</td>
						</tr>
						<tr class="odd">
							<td align="left" class="stat-left-txt"><a href="<c:url value='/chargeproductivityreject?fetchType=2req' />">Number Of Second Requests</a> </td>
							<td align="right"  class="stat-right-txt">${numberOfSecondRequestsCount}</td>
						</tr>
						<tr class="even">
							<td align="left" class="stat-left-txt"><a href="<c:url value='/chargeproductivityreject?fetchType=newrejection' />">New Rejections</a></td>
							<td align="right" class="stat-right-txt">${rejectionCount}</td>
						</tr>
						<tr class="odd">
							<td align="left" class="stat-left-txt"><a href="<c:url value='/chargeproductivityreject?fetchType=resolved' />">Resolved Rejections </a></td>
							<td align="right"  class="stat-right-txt">${resolvedRejectionCount}</td>
						</tr>
						<tr class="even">
							<td align="left" class="stat-left-txt left-round"><a href="<c:url value='/chargeproductivityreject?fetchType=resolvedcpt' />">Resolved Rejections with dummy CPT </a></td>
							<td align="right" class="stat-right-txt right-round">${resolvedRejectionWithDummyCPTCount}</td>
						</tr>
					</table>
				</div>
			</c:if>
		</sec:authorize>

		<c:if test="${showPaymentMenu}">
			<div class="activity-box" style='display:none;'>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th colspan="3"><h3>AR Productivity Workflow Reports</h3></th>
					</tr>
					<tr class="odd">
						<td align="left" class="stat-left-txt">Adjustment Log</td>
						<td align="right" class="stat-right-txt">${adjustLog}</td>
					</tr>
					<tr class="even">
						<td align="left" class="stat-left-txt"><a href="flows/codingcorrectionlogworkflow/">Coding Correction</a></td>
						<td align="right" class="stat-right-txt"><a href="flows/codingcorrectionlogworkflow/">${codingCorr}</a></td>
					</tr>
					<tr class="odd">
						<td align="left" class="stat-left-txt">Second Request</td>
						<td align="right" class="stat-right-txt">${secondReq}</td>
					</tr>
					<tr class="even">
						<td align="left" class="stat-left-txt">Re-Key request to charge posting</td>
						<td align="right" class="stat-right-txt">${rekeyReq}</td>
					</tr>
					<tr class="odd">
						<td align="left" class="stat-left-txt">Payment posting log</td>
						<td align="right" class="stat-right-txt">${paymentPosting}</td>
					</tr>
					<tr class="even">
						<td align="left" class="stat-left-txt"><a href="flows/checkTracer/">Request for check tracer</a></td>
						<td align="right" class="stat-right-txt"><a href="flows/checkTracer/">${checkTracer}</a></td>
					</tr>
					<tr class="odd">
						<td align="left" class="stat-left-txt">Query to TL</td>
						<td align="right" class="stat-right-txt">${toTL}</td>
					</tr>
					<tr class="even">
						<td align="left" class="stat-left-txt left-round"><a href="flows/refundrequest/">Refund Request</a></td>
						<td align="right" class="stat-right-txt right-round"><a href="flows/refundrequest/">${refundRequest}</a></td>
					</tr>
				</table>
			</div>
		</c:if>
		<div class="clr"></div>
		<!-- Statistics End -->
	</div>

</div>
