<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en_US"/>
<div class="dashboard-container">
	
	<div style='text-align:center'>
    	<h1>ARGUS MEDICAL MANAGEMENT, LLC.</h1>
		<h3>CHARGE BATCH PRODUCTIVITY</h3>
		<br/>
	</div>
	
	<div class="table-grid" style="overflow-x: auto;">
		<table style="font-size:9px!important;table-layout: fixed;">
				<thead>
				<tr>
					<th>Ticket #</th>
					<th>Prod Type</th>
					<th>Doctor name</th>
					<th>Scan Date</th>
					<th>Created Date</th>
					<th>Created By</th>
					<th>Time</th>
					<th>Work Flow</th>
					<th>Other Info</th>
				</tr>
				</thead>
				<tbody>
			 	<c:set var="otherInfo" value="" />
			 	<c:set var = "totalTime" value ="0.00"/>
			 	<c:set var = "totalFFS" value ="0.00"/>
			 	<c:set var = "totalCAP" value ="0.00"/>
			 	
			 	
				
				<c:forEach var="batch" items="${CHARGE_BATCHES_PROD}" >
					<c:set var="t1" value="0" />
					<c:set var="t2" value="0" />
					<c:set var="t3" value="0" />
					<c:set var = "totalTime" value ="${totalTime + batch.time}"/>
					<c:if test="${batch.t1 gt 0}">
						<c:set var="t1" value="${batch.t1}" />					
					</c:if>
					
					<c:if test="${batch.t2 gt 0}">
						<c:set var="t2" value="${batch.t2}" />					
					</c:if>
					
					<c:if test="${batch.t3 gt 0}">
						<c:set var="t3" value="${batch.t3}" />					
					</c:if>
					
					<c:if test="${batch.productivityType == 'Demo'}">	
							${pbatch.doctor.name}
						<c:set var="otherInfo" value="New Patient: ${t1}, Existing Patient: ${t2}" />		
					</c:if>
					
					<c:if test="${batch.productivityType == 'Coding'}">	
							${pbatch.doctor.name}
						<c:set var="otherInfo" value="Number Of A/c: ${t1}, ICDs: ${t2}, CPTs: ${t3}" />		
					</c:if>
					
					<c:if test="${batch.productivityType == 'CE'}">	
							${pbatch.doctor.name}
						<c:set var="otherInfo" value="FFS: ${t1}, CAP: ${t2}" />	
						<c:set var = "totalFFS" value ="${totalFFS + t1}"/>
						<c:set var = "totalCAP" value ="${totalCAP + t2}"/>	
					</c:if>
					
					<tr>
						<td>${batch.ticketNumber.id}</td>
						<td>${batch.productivityType}</td>
						<td>${batch.ticketNumber.doctor.name}
						<td style="text-align: center"><fmt:formatDate value="${batch.scanDate}" pattern="MM/dd/yyyy"/></td>
						<td style="text-align: center"><fmt:formatDate value="${batch.createdOn}" pattern="MM/dd/yyyy"/></td>
						<td>${batch.createdBy.firstName}  ${batch.createdBy.lastName}</td>
						<td style="text-align: right">${batch.time}</td>
						<td>
							<c:choose>
								<c:when test="${batch.workFlow eq 'reject'}">
								Reject
								</c:when>
								<c:when test="${batch.workFlow eq 'paymentreversal'}">
								Payment Reversal
								</c:when>
								<c:when test="${batch.workFlow eq 'onhold'}">
								On Hold
								</c:when>
								<c:when test="${batch.workFlow eq 'argustl'}">
								Argus TL
								</c:when>
								<c:when test="${batch.workFlow eq 'internal'}">
								Internal
								</c:when>
							</c:choose>
						</td>
						<td>
							${otherInfo}
							<c:if test="${batch.onHold}">
								<b>On Hold Remark:</b> ${batch.remarks}
							</c:if>
						</td>
					</tr>
				</c:forEach>
				<tr>
					<th colspan = '5'> Total FFS: </th>
					<td colspan = '4'><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${totalFFS}" /></td>
				</tr>
				<tr>
					<th colspan = '5'> Total CAP: </th>
					<td colspan = '4'><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${totalCAP}" /></td>
				</tr>
				<tr>
					<th colspan = '5'> Total : </th>
					<td colspan = '4'><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${totalTime}" /></td>
				</tr>		
			</tbody>
		</table>
	</div>
</div>   	