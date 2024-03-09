<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<fmt:setLocale value="en_US"/>
<div class="dashboard-container">
	
	<div style='text-align:center'>
    	<h1>ARGUS MEDICAL MANAGEMENT, LLC.</h1>
		<h3>CHARGE BATCH SYSTEM</h3>
		<br/>
	</div>
	
	<div class="table-grid" style="overflow-x: auto;">
		<table style="font-size:8pt!important; table-layout:fixed;">
			<thead>
				<tr>
					<th>Ticket #</th>
					<th>Type</th>
					<th>Doctor/Group Name</th>
					<th>DOS</th>
					<th>Superbills</th>
					<th>Attachements</th>
					<th>Total Pages</th>
					<th>Prepared By</th>
					<th>Date Prepared</th>
					<th>Received By</th>
					<th>Date Received</th>
					<th>Posted By</th>
					<th>Date Posted</th>					
					<th>Comment</th>
					<th>Argus Comment</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="cbatch" items="${CHARGE_BATCHES}" varStatus="counter">
					<tr>
						<td>${cbatch.id}</td>
						<td><spring:message code="${cbatch.type}"></spring:message></td>
						<td>${cbatch.doctor.name}</td>
						<td>
							<fmt:formatDate value="${cbatch.dosFrom}" pattern="MM/dd/yyyy"/> - <fmt:formatDate value="${cbatch.dosTo}" pattern="MM/dd/yyyy"/>
						</td>						
						<td>${cbatch.numberOfSuperbills}</td>
						<td>${cbatch.numberOfAttachments}</td>
						<td><c:set var="totalPages" value="${cbatch.numberOfAttachments + cbatch.numberOfSuperbills}" /> ${totalPages} </td>
						
						<td>${cbatch.createdBy.firstName} ${cbatch.createdBy.lastName}</td>
						<td><fmt:formatDate value="${cbatch.createdOn}" pattern="MM/dd/yyyy"/></td>
						
						<td>${cbatch.receivedBy.firstName} ${cbatch.receivedBy.lastName}</td>
						<td><fmt:formatDate value="${cbatch.dateReceived}" pattern="MM/dd/yyyy"/></td>
						
						<td>${cbatch.postedBy.firstName} ${cbatch.postedBy.lastName}</td>
						<td><fmt:formatDate value="${cbatch.dateBatchPosted}" pattern="MM/dd/yyyy"/></td>
					
						<td>${cbatch.remarks}</td>
						<td>${cbatch.remarksArgus}</td>
					</tr>
					
				</c:forEach>		
			</tbody>
		</table>
	</div>
	
	
	
   	
   	
</div>   	