<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li><a href="<c:url value='/cashlog/' />">&nbsp;CashLog Reports</a>
				&raquo;</li>
			<li>&nbsp;Daily Payment Receipt Log Report</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<div class="table-grid" style="overflow-x:auto;">
		<table border="0" cellspacing="0" cellpadding="0" width="100%">
			<tr>
				<th>Deposit Date</th>
				<th>Revenue Type</th>
				<th>Deposit Total</th>
				<th>Lock Box</th>
				<th>Mail</th>
				<th>Tele Checks</th>
				<th>EFT</th>
			</tr>

			<c:forEach var="row" items="${reportData}">
						<tr>
							<td align="center">${row[6]}</td>
							<td align="center">${row[2]}</td>
							<td align="center">$${row[5]}</td>
							<td align="center">$${row[9]}</td>
							<td align="center">$${row[10]}</td>
							<td align="center">$${row[11]}</td>
							<td align="center">$${row[8]}</td>
						</tr>
			</c:forEach>
						<tr>
							<td align="center"></td>
							<td align="center"></td>
							<td align="center">TOTAL</td>
							<td align="center">$${totalLockBox}</td>
							<td align="center">$${totalMail}</td>
							<td align="center">$${totalTeleChecks}</td>
							<td align="center">$${totalEft}</td>
						</tr>
		</table>
	</div>
</div>
