<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="argus" uri="http://www.arguscore.com/jsp/argus/tags" %>

	<div class="dashboard-container" >
		<div style='text-align:center'>
		   	<h1><spring:message code="text.argus.medical.management"/></h1>
			<h3><spring:message code="text.argus.qaworksheets"/></h3>
			<br/>
		</div>
		<div class='table-grid' style='overflow-x: auto;'>
		<table border='1' width='100%'>
			<thead>
				<tr> 
					<th> <strong>QAWorksheet Name</Strong> </th>
					<th> <strong>Month & Year</Strong> </th>
					<th> <strong>From & To Date</Strong> </th>
					<th> <strong>Department</Strong> </th>
					<th> <strong>Status</Strong> </th>
					<th> <strong>Created By</Strong> </th>
					<th> <strong>Creation Date</Strong> </th>
				</tr>
			</thead>
			<c:forEach var="qaWorksheet" items="${qaWorksheets}" varStatus="counter">
				
				<tbody>
					<tr>
						<td><strong>${qaWorksheet.name}</strong></td>
						<td ><argus:formatMonth value="${qaWorksheet.billingMonth}"/> ${qaWorksheet.billingYear}</td>
						<td>
							<fmt:formatDate value="${qaWorksheet.postingDateFrom}" pattern="MM/dd/yyyy"/> <br/>
							<fmt:formatDate value="${qaWorksheet.postingDateTo}" pattern="MM/dd/yyyy"/>
						</td>
					
						<td>${qaWorksheet.department.name} <br/> 
						<c:if test="${not empty qaWorksheet.subDepartment}">
							[ ${qaWorksheet.subDepartment.name} ]
						</c:if>
						</td>
						<c:choose>
							<c:when test="${qaWorksheet.status eq '0'}">
								<td>Pending</td>
							</c:when>
							<c:when test="${qaWorksheet.status eq '1'}">
								<td>In Progress</td>
							</c:when>
							<c:when test="${qaWorksheet.status eq '2'}">
								<td>Completed</td>
							</c:when>
						</c:choose>
						
					
						<td>${qaWorksheet.createdBy.firstName} ${qaWorksheet.createdBy.lastName}</td>
						
						<td><fmt:formatDate value="${qaWorksheet.createdOn}" pattern="MM/dd/yyyy"/></td>
					</tr>
				</tbody>
					
				<c:if test="${counter.last eq false}">	
					<div class="page-break"></div>
				</c:if>
			</c:forEach>
		</table>
		</div>
	</div>