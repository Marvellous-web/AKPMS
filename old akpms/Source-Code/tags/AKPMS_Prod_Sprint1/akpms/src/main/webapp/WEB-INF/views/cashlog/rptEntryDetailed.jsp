<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li><a href="<c:url value='/cashlog/' />">&nbsp;CashLog Reports</a>
				&raquo;</li>
			<li>&nbsp;Detailed Journal Entries Report</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<c:set var="docCode" value="123"></c:set>
	<c:set var="isCheck" value="false"></c:set>
	<c:set var="dept" value=""></c:set>
	<c:set var="subTotal" value=""></c:set>
	<div class="table-grid" style="overflow-x: auto;">
		<table border="0" cellspacing="0" cellpadding="0" width="100%">
			<tr>
				<th>Dept No</th>
				<th>Dept Name</th>
				<th>Date</th>
				<th>Month</th>
				<th>Money Source</th>
				<th>Revenue SUB-ACCOUNT</th>
				<th>REVENUE</th>
				<th>TOTAL</th>
			</tr>

			<c:forEach var="row" items="${reportData}">

				<c:set var="docCode" value="${row[1]}"></c:set>
				<c:set var="isCheck" value="true"></c:set>
				<c:choose>
					<c:when test="${not empty row[3]}">
						<tr>
							<td align="center">${row[1]}</td>
							<td align="center">${row[2]}</td>
							<td align="center">Date</td>
							<td align="center">Month</td>
							<td align="center">${row[3]}</td>
							<td align="center">${row[0]}</td>
							<td align="center">${row[5]}</td>
							<td align="center">${row[6]}</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:if test="${not empty row[1]}">
							<tr>
								<td colspan="7" align="center"><strong>Department Name Sub Total ${row[2]}</strong></td>
								<td align="center"><strong>${row[6]}</strong></td>
							</tr>
						</c:if>
					</c:otherwise>
				</c:choose>
				<c:set var="dept" value="${row[2]}"></c:set>
			</c:forEach>

		</table>
	</div>

</div>
