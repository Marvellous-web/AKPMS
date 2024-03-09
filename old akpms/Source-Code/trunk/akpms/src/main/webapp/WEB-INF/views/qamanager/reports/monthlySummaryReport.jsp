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

		<table style="font-size: 9px !important; table-layout: fixed; margin: auto">
			<thead>
				<tr style="background-color: #DBDBDB">
					<th>Days</th>
					<th>Account <br/>Worked</th>
					<th>Proceed <br/> Sample</th>
					<th>Errors</th>
					<th>Error <br/> Rate</th>
				</tr>
			</thead>
			<tbody>
			<c:set var="totalAccounts" value="0"/>
			<c:set var="totalProceeds" value="0"/>
			<c:set var="totalErrors" value="0"/>
			<c:set var="totalErrorRate" value="0"/>
				<c:forEach var="dataRow" items="${reportData}">
					<tr>
						<td align="center">${dataRow[0]}</td>
						<td align="right">${dataRow[1]}</td>
						<td align="right">${dataRow[2]}</td>
						<td align="right">${dataRow[3]}</td>
						<td align="right">${dataRow[4]}</td>
						<c:set var="totalAccounts" value="${totalAccounts + dataRow[1]}"/>
						<c:set var="totalProceeds" value="${totalProceeds + dataRow[2]}"/>
						<c:set var="totalErrors" value="${totalErrors + dataRow[3]}"/>
						<c:set var="totalErrorRate" value="${totalErrorRate + dataRow[4]}"/>						
					</tr>
				</c:forEach>
				<tr>
				<td align="right"><strong>Totals: </strong></td>
				<td align="right">${totalAccounts}</td>
				<td align="right">${totalProceeds}</td>
				<td align="right">${totalErrors}</td>
				<td align="right"><fmt:formatNumber type="number" minFractionDigits="4" maxFractionDigits="4" value="${totalErrorRate}" /></td>
				</tr>
			</tbody>		
			
		</table>
		
	
	</div>
</div>

