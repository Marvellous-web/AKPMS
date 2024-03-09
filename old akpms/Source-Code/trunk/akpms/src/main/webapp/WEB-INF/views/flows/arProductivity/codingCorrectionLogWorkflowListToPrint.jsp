<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en_US"/>
<div class="dashboard-container">
	
	<div style='text-align:center'>
    	<h1>ARGUS MEDICAL MANAGEMENT, LLC.</h1>
		<h3>5150 E.Pacific Coast Hwy, Suite 500</h3>
		<h3>Long Beach CA 90804</h3>
		<h3>Coding Correction WorkFLow Report</h3>
		<br/>
	</div>
	
	<div class="table-grid" style="overflow-x: auto;">
		<table style="font-size:8pt!important; table-layout:fixed;">
			<thead>
				<tr>
					<th>Work Flow</th>
					<th>Patient ID</th>
					<th>Patient Name</th>
					<th>Batch Number</th>
					<th>Sequence Number</th>
					<th>Provider</th>
					<th>Team</th>
					<th>DOS</th>
					<th>Attachment</th>
					<th>Created By </th>
					<th>Created On</th>
				</tr>
			</thead>
			<tbody>

				<c:forEach var="codingCorrectionLog" items="${codingCorrectionList}">
				
					<tr>
						<td>${codingCorrectionLog.nextAction}</td>
						<td>${codingCorrectionLog.arProductivity.patientAccNo}</td>
						<td>${codingCorrectionLog.arProductivity.patientName}</td>
						<td>${codingCorrectionLog.batchNo}</td>
						<td>${codingCorrectionLog.sequenceNo}</td>
						<td>${codingCorrectionLog.arProductivity.doctor.name}</td>
						<td>${codingCorrectionLog.arProductivity.team.name}</td>
						<td>${codingCorrectionLog.arProductivity.dos}</td>
						<td>${codingCorrectionLog.attachment.name}</td>
						<td> ${codingCorrectionLog.createdBy.firstName} ${codingCorrectionLog.createdBy.lastName}</td>
						<td> <fmt:formatDate value="${codingCorrectionLog.createdOn}" pattern="MM/dd/yyyy"/></td>
						
					</tr>
					
				</c:forEach>		
			</tbody>
		</table>
	</div>
</div>   	