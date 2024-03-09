<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en_US"/>
<div class="dashboard-container">
	
	<div style='text-align:center'>
    	<h1>ARGUS MEDICAL MANAGEMENT, LLC.</h1>
		<h3>5150 E.Pacific Coast Hwy, Suite 500</h3>
		<h3>Long Beach CA 90804</h3>
		<h3>AR Productivity Report</h3>
		<br/>
	</div>
	
	<div class="table-grid" style="overflow-x: auto;">
		<table style="font-size:8pt!important; table-layout:fixed;">
			<thead>
				<tr>
					<!-- <th>Work Flow</th> -->
					<th>Patient ID</th>
					<th>Patient Name</th>
					<th>DOS</th>
					<th>CPT</th>
					<th>Doctor</th>
					<th>Insurance</th>
					<th>Database</th>
					<th>Team</th>
					<th>Follow Up Date</th>
					<th>Balance</th>
					<th>Status Code</th>
					<th>Source</th>	
					<th>Created By</th>				
					<th>Created On</th>
				</tr>
			</thead>
			<tbody>

				<c:forEach var="arProductivity" items="${arProductivityList}">
				
					<tr>
						<%-- <td>${arProductivity.workFlowName}</td> --%>
						<td>${arProductivity.patientAccNo}</td>
						<td>${arProductivity.patientName}</td>
						<td>${arProductivity.dos}</td>
						<td>${arProductivity.cpt}</td>
						<td>${arProductivity.doctor.name}</td>
						<td>${arProductivity.insurance.name}</td>
						<td>${arProductivity.arDatabase.name}</td>
						<td>${arProductivity.team.name}</td>
						<td><fmt:formatDate value="${arProductivity.followUpDate}" pattern="MM/dd/yyyy"/></td>
						<td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${arProductivity.balanceAmt}" /></td>
						<td>${arProductivity.statusCode}</td>
						<td>${arProductivity.source}</td>
						<td>${arProductivity.createdBy.firstName} ${arProductivity.createdBy.lastName}</td>
						<td><fmt:formatDate value="${arProductivity.createdOn}" pattern="MM/dd/yyyy"/></td>
					</tr>
					
				</c:forEach>		
			</tbody>
		</table>
	</div>
</div>   	