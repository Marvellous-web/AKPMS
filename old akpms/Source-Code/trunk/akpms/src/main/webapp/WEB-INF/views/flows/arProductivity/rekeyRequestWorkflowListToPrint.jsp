<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="en_US"/>
<div class="dashboard-container">
	
	<div style='text-align:center'>
    	<h1>ARGUS MEDICAL MANAGEMENT, LLC.</h1>
		<h3>5150 E.Pacific Coast Hwy, Suite 500</h3>
		<h3>Long Beach CA 90804</h3>
		<h3>Re-key Request WorkFLow Report</h3>
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
					<th>Insurance</th>
					<th>Doctor</th>
					<th>Database</th>
					<th>Team</th>
					<th>Batch Number</th>
					<th>Reason Request</th>
					<th>Created By</th>
					<th>Created On</th>
					<th colspan="2">Details</th>
				</tr>
			</thead>
			<tbody>
				
				<c:forEach var="rekeyRequest" items="${rekeyRequestList}">
				<c:set var="rowSpan">${fn:length(rekeyRequest.rekeyRequestRecords)}</c:set>
				<c:set var="status" value=""></c:set>
				
				<c:if test="${rekeyRequest.status == 1 }">
					<c:set var="status" value="Open"></c:set>
				</c:if>
				<c:if test="${rekeyRequest.status == 2 }">
					<c:set var="status" value="Returned"></c:set>
				</c:if>
				<c:if test="${rekeyRequest.status == 3 }">
					<c:set var="status" value="Return to AR"></c:set>
				</c:if>
				<c:if test="${rekeyRequest.status == 4 }">
					<c:set var="status" value="Query to Coding"></c:set>
				</c:if>
				<c:if test="${rekeyRequest.status == 5 }">
					<c:set var="status" value="Close"></c:set>
				</c:if>
					<tr>
						<td rowspan="${rowSpan+1}">${status}</td>
						<td rowspan="${rowSpan+1}">${rekeyRequest.arProductivity.patientAccNo}</td>
						<td rowspan="${rowSpan+1}">${rekeyRequest.arProductivity.patientName}</td>
						<td rowspan="${rowSpan+1}">${rekeyRequest.arProductivity.dos}</td>
						<td rowspan="${rowSpan+1}">${rekeyRequest.arProductivity.cpt}</td>
						<td rowspan="${rowSpan+1}">${rekeyRequest.arProductivity.insurance.name}</td>
						<td rowspan="${rowSpan+1}">${rekeyRequest.arProductivity.doctor.name}</td>
						<td rowspan="${rowSpan+1}">${rekeyRequest.arProductivity.arDatabase.name}</td>
						<td rowspan="${rowSpan+1}">${rekeyRequest.arProductivity.team.name}</td>
						<td rowspan="${rowSpan+1}">${rekeyRequest.batchNumber}</td>
						<td rowspan="${rowSpan+1}"><spring:message code="${rekeyRequest.requestReason}"></spring:message></td>
						<td rowspan="${rowSpan+1}">${rekeyRequest.createdBy.firstName} ${rekeyRequest.createdBy.firstName}</td>
						<td rowspan="${rowSpan+1}"><fmt:formatDate value="${rekeyRequest.createdOn}" pattern="MM/dd/yyyy"/></td>
						<td style="font-weight: bold;">
						<c:if test="${rowSpan gt 0 }">
						CPT
						</c:if>
						</td>
						<td style="font-weight: bold;">
						<c:if test="${rowSpan gt 0 }">
						Remark
						</c:if>
						</td>
					</tr>
					<c:if test="${fn:length(rekeyRequest.rekeyRequestRecords) gt 0}">
								<c:forEach var="rekeyRecord" items="${rekeyRequest.rekeyRequestRecords}">
								<tr>
									<td>${rekeyRecord.cpt}</td>
									<td>${rekeyRecord.remark}</td>
								</tr>
								</c:forEach>
					</c:if>
					
				</c:forEach>		
			</tbody>
		</table>
	</div>
</div>   	