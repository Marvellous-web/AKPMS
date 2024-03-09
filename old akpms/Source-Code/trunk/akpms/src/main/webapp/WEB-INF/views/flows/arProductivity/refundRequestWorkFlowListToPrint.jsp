<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en_US"/>
<div class="dashboard-container">
	
	<div style='text-align:center'>
    	<h1>ARGUS MEDICAL MANAGEMENT, LLC.</h1>
		<h3>5150 E.Pacific Coast Hwy, Suite 500</h3>
		<h3>Long Beach CA 90804</h3>
		<h3>Refund Request WorkFLow Report</h3>
		<br/>
	</div>
	
	<div class="table-grid" style="overflow-x: auto;">
		<table style="font-size:8pt!important; table-layout:fixed;">
			<thead>
				<tr>
					<th>Status</th>
					<th>Patient ID</th>
					<th>Patient Name</th>
					<th>Team</th>
					<th>Responsible Party</th>
					<th>Total Amount</th>
					<th>DOS</th>
					<th>Reason</th>
				</tr>
			</thead>
			<tbody>

				
				<c:forEach var="refundRequest" items="${refundRequestWorkFlowList}">
				
					<c:set var="status" value=""></c:set>
					<c:if test="${refundRequest.status == 0}">
						<c:set var="status" value="Pending"></c:set>
					</c:if>
					<c:if test="${refundRequest.status == 1}">
						<c:set var="status" value="Done"></c:set>
					</c:if>
					<c:if test="${refundRequest.status == 2}">
						<c:set var="status" value="reject"></c:set>
					</c:if>
					<tr>
						<td>${status}</td>
						<td>${refundRequest.arProductivity.patientAccNo}</td>
						<td>${refundRequest.arProductivity.patientName}</td>
						<td>${refundRequest.arProductivity.team.name}</td>
						<td>${refundRequest.responsibleParty}</td>
						<td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${refundRequest.totalAmount}" /></td>
						<td>${refundRequest.dos}</td>
						<td>${refundRequest.reason}</td>
					</tr>
					
				</c:forEach>		
			</tbody>
		</table>
	</div>
</div>   	