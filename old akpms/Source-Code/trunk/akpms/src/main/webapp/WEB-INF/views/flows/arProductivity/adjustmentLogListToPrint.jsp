<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en_US"/>
<div class="dashboard-container">
	
	<div style='text-align:center'>
    	<h1>ARGUS MEDICAL MANAGEMENT, LLC.</h1>
		<h3>5150 E.Pacific Coast Hwy, Suite 500</h3>
		<h3>Long Beach CA 90804</h3>
		<h3>Adjustment Log WorkFLow Report</h3>
		<br/>
	</div>
	
	<div class="table-grid" style="overflow-x: auto;">
		<table style="font-size:8pt!important; table-layout:fixed;">
			<thead>
				<tr>
					<th>Work Flow</th>
					<th>Patient ID</th>
					<th>Patient Name</th>
					<th>DOS</th>
					<th>CPT</th>
					<th>Doctor</th>
					<th>Database</th>
					<th>Team</th>
					<th>Balance</th>
					<th>Status Code</th>
					<th>Source</th>
					<th>Created By</th>
					<th>Created On</th>
				</tr>
			</thead>
			<tbody>
				
				<c:forEach var="adjustmentLog" items="${adjustmentLogList}">
				<c:set var="status" value=""></c:set>
				
				<c:if test="${adjustmentLog.workFlowStatus == 1 }">
					<c:set var="status" value="Approve"></c:set>
				</c:if>
				<c:if test="${adjustmentLog.workFlowStatus == 2 }">
					<c:set var="status" value="Reject"></c:set>
				</c:if>
				<c:if test="${adjustmentLog.workFlowStatus == 3 }">
					<c:set var="status" value="Escalate"></c:set>
				</c:if>
				<c:if test="${adjustmentLog.workFlowStatus == 4 }">
					<c:set var="status" value="Closed"></c:set>
				</c:if>
					<tr>
						<td>${status}</td>
						<td>${adjustmentLog.arProductivity.patientAccNo}</td>
						<td>${adjustmentLog.arProductivity.patientName}</td>
						<td>${adjustmentLog.arProductivity.dos}</td>
						<td>${adjustmentLog.arProductivity.cpt}</td>
						<td>${adjustmentLog.arProductivity.doctor.name}</td>
						<td>${adjustmentLog.arProductivity.arDatabase.name}</td>
						<td>${adjustmentLog.arProductivity.team.name}</td>
						<td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${adjustmentLog.arProductivity.balanceAmt}" /></td>
						<td>${adjustmentLog.arProductivity.statusCode}</td>
						<td>${adjustmentLog.arProductivity.source}</td>
					<td>${adjustmentLog.createdBy.firstName} ${adjustmentLog.createdBy.lastName}</td>
					<td><fmt:formatDate value="${adjustmentLog.createdOn}" pattern="MM/dd/yyyy"/></td>
					</tr>
					
				</c:forEach>		
			</tbody>
		</table>
	</div>
</div>   	