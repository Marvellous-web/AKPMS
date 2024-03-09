<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="en_US"/>
<div class="dashboard-container">
	
	<div style='text-align:center'>
    	<h1>ARGUS MEDICAL MANAGEMENT, LLC.</h1>
		<h3>HOURLY PRODUCTIVITY LIST</h3>
		<br/>
	</div>
	
	<div class="table-grid" style="overflow-x: auto!important;">
		<table style="font-size:9px!important;">
			<thead>
				<tr>
					<th>Task Name</th>
					<th>Time <br/> (In Hour)</th>
					<th>Task <br/>Received Date</th>
					<th>Task <br/>Completed Date</th>
					<th>Created By</th>
					<th>Created On</th>
					<th>Details</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="task" items="${HOURLY_TASK_DETAILS}" >
				<c:set var="total">${total + task.hours + task.minutes/60}</c:set>
				<c:set var="totalMin">${totalMin + task.minutes}</c:set>
				<c:set var="total_roundOff" value="${fn:split(total, '.')}"></c:set>
					<tr>
						<td>${task.hourlyTask.name}</td>
						<td><fmt:formatNumber value="${task.hours + task.minutes/60}" maxFractionDigits="2"/></td>
						<td><fmt:formatDate value="${task.dateReceived}" pattern="MM/dd/yyyy"/></td>
						<td><fmt:formatDate value="${task.taskCompleted}" pattern="MM/dd/yyyy"/></td>
						<td>${task.createdBy.firstName} ${task.createdBy.lastName}</td>
						<td><fmt:formatDate value="${task.createdOn}" pattern="MM/dd/yyyy"/></td>
						<td>${task.detail}</td>
					</tr>
				</c:forEach>
					<tr>
						<td><strong>Total</strong></td>
						<td><strong><fmt:formatNumber value="${total_roundOff[0]}" maxFractionDigits="0"/> <i>hrs</i></strong></td>
						<td><strong>${totalMin%60} <i>mins</i></strong></td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>		
			</tbody>
		</table>
	</div>
</div>   	