<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li><a href="<c:url value='/cashlog/' />">&nbsp;CashLog
					Reports</a> &raquo;</li>
			<li>&nbsp;Journal Entries Summary Report</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<div class="table-grid" style="overflow-x: auto;">
		<table border="0" cellspacing="0" cellpadding="0" width="100%">
			<tr>
				<th>Revenue Sub-Account</th>
				<th>Department Number</th>
				<th colspan="2">Transaction Description</th>
				<th>Credit</th>
			</tr>

			<c:forEach var="row" items="${reportData}" varStatus="loop">
				<c:choose>
					<c:when test="${not loop.last}">
						<tr>
							<td align="center">${row[0]}</td>
							<td align="center">${row[2]}</td>
							<td align="center">${row[1]}</td>
							<td align="center">${row[3]}</td>
							<td align="center">${row[4]}</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<td align="center" colspan="4"><strong>TOTALS:</strong></td>
							<td align="center">${row[4]}
						</tr>
					</c:otherwise>
				</c:choose>
			</c:forEach>

		</table>
	</div>

</div>
