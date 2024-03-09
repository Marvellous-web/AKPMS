<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="en_US"/>
<div class="dashboard-container">
	
	<div style='text-align:center'>
    	<h1>ARGUS MEDICAL MANAGEMENT, LLC.</h1>
		<h3>CHARGE BATCH REJECT PRODUCTIVITY</h3>
		<br/>
	</div>
	
	<div class="table-grid" style="overflow-x: auto!important;">
		<table style="font-size:7px!important;">
			<thead>
				<tr>
					<th>Ticket #</th>
					<th>Patient Name</th>
					<th>DOB</th>
					<th>Seq.</th>
					<th>Location</th>
					<th>Account</th>
					<th>DOS</th>
					<th>Doctor/Group Name</th>
					<th>Reason to Reject</th>
					<th>Insurance type</th>
					<th>Batch Received</th>
					<th>Doctor Name</th>
					<th>Created On</th>
					<th>Date of 1st request to Doctor Office</th>
					<th>Date of 2nd request to Doctor Office</th>
					<th>Resolved Date</th>
					<th>Completed Date</th>
					<th>Rejection Remark</th>
					<th>Resolution Remark</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="batch" items="${CHARGE_BATCHES_REJECT}" >
					<tr>
						<td>${batch.chargeBatchProcessing.id}</td>
						<td>${batch.patientName}</td>
						<td><fmt:formatDate value="${batch.dob}" pattern="MM/dd/yyyy"/></td>
						<td>${batch.sequence}</td>
						<td>
							<c:if test="${not empty batch.location}">
								${batch.location.name}
							</c:if>
						</td>
						<td>${batch.account}</td>
						<td><fmt:formatDate value="${batch.dateOfService}" pattern="MM/dd/yyyy"/></td>
						<td>${batch.chargeBatchProcessing.doctor.name}</td>
						<td>${batch.reasonToReject}</td>
						<td>
							<c:if test="${not empty batch.insuranceType}">
								<fmt:bundle basename="chargeBatchInsuranceType">
									<fmt:message key="${batch.insuranceType}"/>
								</fmt:bundle>
							</c:if>
						</td>
						<td><fmt:formatDate value="${batch.chargeBatchProcessing.dateReceived}" pattern="MM/dd/yyyy"/></td>
						<td>
							${batch.chargeBatchProcessing.doctor.name}
							<c:if test="${not empty batch.chargeBatchProcessing.doctor.parent}">
								(${batch.chargeBatchProcessing.doctor.parent.name})
							</c:if>
						</td>
						<td><%-- ${batch.createdBy.firstName} ${batch.createdBy.lastName} <br />--%>
						<fmt:formatDate value="${batch.createdOn}" pattern="MM/dd/yyyy"/>
						</td>
						<td><fmt:formatDate value="${batch.dateOfFirstRequestToDoctorOffice}" pattern="MM/dd/yyyy"/></td>
						<td><fmt:formatDate value="${batch.dateOfSecondRequestToDoctorOffice}" pattern="MM/dd/yyyy"/></td>
						<td><fmt:formatDate value="${batch.resolvedOn}" pattern="MM/dd/yyyy"/></td>
						<td><fmt:formatDate value="${batch.completedOn}" pattern="MM/dd/yyyy"/></td>
						<td>${batch.remarks}</td>
						<td>${batch.remarks2}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
