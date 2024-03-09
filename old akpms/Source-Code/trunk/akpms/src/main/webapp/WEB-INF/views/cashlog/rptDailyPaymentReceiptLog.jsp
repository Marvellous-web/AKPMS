<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en_US"/>
<div class="dashboard-container">
	
	<div style="width: 100%; text-align: center" id="reportHeading">
		<strong>ARGUS MEDICAL MANAGEMENT, LLC.</strong> <br/>
		DETAILED REPORT BY BILLING MONTH<br/>
		FOR THE MONTH OF ${month} - ${year}<br/>
		<%-- ${depositDateFrom} ${depositDateTo} --%>
	</div>
	
	<div class="table-grid" style="overflow-x:auto;">
		<table style="font-size:9px!important;table-layout: fixed;margin: auto">
		<thead>
			<tr>
				<th>Provider</th>
				<th>Deposit Date</th>
				<th>Revenue Type</th>
				<th>Deposit Total</th>
				<th>Lock Box</th>
				<th>VAULT</th>
				<th>Credit Card</th>
				<th>Telechecks</th>
				<th>Eft</th>
			</tr>
		</thead>
			<c:forEach var="row" items="${reportData}">
				<tr>
					<td align="left" title="provider" >${row[0]}</td>
					<td style="text-align: center" title="depositDate">${row[1]}</td>
					<td align="left" title="revenueType">${row[2]}</td>
					<td style="text-align: right" title="depositTotal"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${row[3]}" /></td>
					<td style="text-align: right" title="lockbox" ><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${row[8]}" /></td>
					<td style="text-align: right" title="vault" ><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${row[4]}" /></td>
					<td style="text-align: right" title="creditcard"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${row[5]}" /></td>
					<td style="text-align: right" title="telecheck"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${row[6]}" /></td>
					<td style="text-align: right" title="eft"><fmt:formatNumber  pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${row[9]}" /></td>
				</tr>
			</c:forEach>
				<tr>
					<td style="text-align: center"></td>
					<td style="text-align: center"></td>
					<td style="text-align: center"><strong>TOTAL</strong></td>
					<td style="text-align: right"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${totalDeposit}" /></td>
					<td style="text-align: right"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${totalLockBox}" /></td>
					<td style="text-align: right"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${totalVault}" /></td>
					<td style="text-align: right"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${totalCreditCard}" /></td>
					<td style="text-align: right"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${totalTelecheck}" /></td>
					<td style="text-align: right"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${totalEft}" /></td>
				</tr>
		</table>
	</div>
</div>
