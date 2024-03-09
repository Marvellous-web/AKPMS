<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<fmt:setLocale value="en_US" />
<div class="dashboard-container">

	<div style="width: 100%; text-align: center" id="reportHeading">
		<strong>ARGUS QA REPORTING</strong> 
		<br /> DEPARTMENT: ${fn:toUpperCase(department)} 
		<br /> FROM THE DATE: ${fn:toUpperCase(startMonth)} TO ${fn:toUpperCase(endMonth)}
		<br /> TEAM: ${fn:toUpperCase(subDepartmentName)} 
	</div>

	<div class="table-grid" style="overflow-x: auto;">
		<table
			style="font-size: 9px !important; table-layout: fixed; margin: auto">
			<thead>
				<tr>
					<th>QC Point</th>
					<th>User</th>
					<th>Error Count</th>
					<c:set var="totalErrors" value="0"/>
					<c:forEach var="row" items="${qaReportData}">
						<c:set var="totalErrors" value="${totalErrors + row[2]}"/>
						<tr>
							<td style="text-align: left" title="qcpoints">${row[0]}</td>
							<td style="text-align: center" title="usernames">${row[1]}</td>
							<td style="text-align: center" title="errors">${row[2]}</td>
						</tr>
					</c:forEach>
				</tr>
				<tr>
				<td colspan="2" align="right"><strong>Total: </strong></td>
				<td style="text-align: center">${totalErrors}</td>
				</tr>
			</thead>
		</table>
	</div>
</div>

