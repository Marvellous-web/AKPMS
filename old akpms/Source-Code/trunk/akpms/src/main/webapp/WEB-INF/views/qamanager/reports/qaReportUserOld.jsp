<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<fmt:setLocale value="en_US" />
<div class="dashboard-container">

	<div style="width: 100%; text-align: center" id="reportHeading">
		<strong>ARGUS QA REPORTING</strong> <br /> DEPARTMENT:
		${fn:toUpperCase(department)}
		<br /> FROM THE DATE: ${fn:toUpperCase(startMonth)} TO ${fn:toUpperCase(endMonth)}
		<br /> TEAM: ${fn:toUpperCase(subDepartmentName)} 
	</div>

	<%-- <div class="table-grid" style="overflow-x: auto;">
		<table
			style="font-size: 9px !important; table-layout: fixed; margin: auto">
			<thead>
				<tr>

					<th>User</th>
					<c:forEach var="worksheet" items="${qaWorksheetNames}">
						<th>${worksheet}</th>
					</c:forEach>
					<th>Consolidated</th>

					<c:forEach var="row" items="${qaReportData}">
						<tr>
							<td style="text-align: center" title="username">${row[1].firstName} ${row[1].lastName}</td>
							<c:forEach var="worksheet" items="${qaWorksheetNames}">
								<c:choose>
									<c:when test="${departmentId eq '1'}">
										<td align="left" title="totalErrorsCalculated">
										<fmt:formatNumber value="${d}" maxFractionDigits="0" />
											${row[4] / row[5]. + row[5].t2 + row[5].t3}%</td>
									</c:when>
									<c:when test="${departmentId eq '2'}">
										<td align="left" title="totalErrorsCalculated">
											${row[4] / row[5].manualTransaction + row[5].elecTransaction}%</td>
									</c:when>
								</c:choose>
							</c:forEach>
							<td style="text-align: center" title="consolidated">----</td>
						</tr>
					</c:forEach>
				</tr>
			</thead>
		</table>
	</div> --%>
	<div class="table-grid" style="overflow-x: auto;">
		<table
			style="font-size: 9px !important; table-layout: fixed; margin: auto">

			<c:set var="totalRecords" value="${0}" />
			<c:set var="totalErrors" value="${0}" />
			<thead>
				<tr>
					<th>QAWorksheet
					<th>User</th>
					<th>Total Record</th>
					<!--  	<th>Total Accounts </th> -->
					<th>Total Errors</th>
					<th>Errors Rate <br /> Percentage
					</th>
					<!-- qaWorksheet is on position 1 -->
					<c:choose>
						<c:when test="${not empty reportData}">
							<c:forEach var="dataRow" items="${reportData}">

								<c:choose>
									<c:when test="${empty dataRow[1] && not empty dataRow[2]}">
										<tr>
											<td style="text-align: center; font-weight: bold">Consolidated</td>

											<td style="font-weight: bold" align="left" title="username">${dataRow[3]}</td>

											<td style="text-align: center" title="totalRecord">${dataRow[4]}
												<c:set var="totalRecords" value="${totalRecords+dataRow[4]}" />
											</td>
											<!--  <td style="text-align: center" title="totalAccount">${dataRow[5]}</td>-->

											<td style="text-align: center" title="totalErrors">${dataRow[7]}
												<c:set var="totalErrors" value="${totalErrors + dataRow[7]}" />
											</td>
											<td style="text-align: center" title="errorsRatePercentage">${dataRow[9]}%</td>
										</tr>
										<tr>
											<td colspan="5">&nbsp;</td>
										</tr>
									</c:when>
									<c:when test="${not empty dataRow[2]}">
										<tr>
											<td style="text-align: center" title="worksheetName">${dataRow[0]}</td>

											<td align="left" title="username">${dataRow[3]}</td>

											<td style="text-align: center" title="totalRecord">${dataRow[4]}</td>

											<td style="text-align: center" title="totalErrors">${dataRow[7]}</td>

											<td style="text-align: center" title="errorsRatePercentage">${dataRow[9]}%</td>

										</tr>
									</c:when>
								</c:choose>
							</c:forEach>

							<tr>
								<td style="text-align: center; font-weight: bold" colspan="2">Total</td>





								<td style="text-align: center; font-weight: bold"
									title="totalRecord"><c:out value="${totalRecords}" /></td>

								<td style="text-align: center; font-weight: bold"
									title="totalErrors"><c:out value="${totalErrors}" /></td>
								<c:set var="totalPercent"
									value="${(totalErrors*100)/totalRecords}" />
								<fmt:parseNumber var="per" type="number" value="${totalPercent}" />
								<td style="text-align: center; font-weight: bold"
									title="errorsRatePercentage"><c:out value="${per}" />%</td>
							</tr>
							<tr>
								<td colspan="5">&nbsp;</td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr align="center">
								<td style="color: red; font-weight: bold" colspan="5">NO
									RECORD FOUND</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</tr>
			</thead>
		</table>
	</div>
</div>

