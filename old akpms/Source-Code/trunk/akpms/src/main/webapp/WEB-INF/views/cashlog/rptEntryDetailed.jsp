<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en_US"/>
<div class="dashboard-container">
	<div style="width: 100%; text-align: center" id="reportHeading">
		<strong>PROHEALTH PARTNERS, A MEDICAL GROUP, INC.</strong> <br/>
		DETAILED JOURNAL ENTRIES<br/>
		FOR THE MONTH OF ${month} - ${year}
	</div>
	
	<c:set var="isCheck" value="false"></c:set>
	<c:set var="dept" value=""></c:set>
	<c:set var="subTotal" value=""></c:set>
	<div class="table-grid" style="overflow-x: auto;">
		<table style="font-size:9px!important;table-layout: fixed;margin: auto">
		<thead>
			<tr>
				<c:if test="${showTicketNumber}">
					<th>Batch#</th>
				</c:if>
				<th>Cost Center Number</th>
				<th>Cost Center Name</th>
				<th>Deposit Date</th>
				<th>Month</th>
				<th>Money Source</th>
				<th>Revenue Sub-Account</th>
				<th>Revenue Type</th>
				<th>Total</th>
			</tr>
			</thead>
			<c:set var="grandTotal" value="0" />
			
			<c:forEach var="row" items="${reportData}">
				<c:set var="isCheck" value="true"></c:set>
				<c:choose>
					<c:when test="${not empty row[0]}">
						<tr>
							<c:if test="${showTicketNumber}">
								<td style="text-align: center" title="TicketNumber" >${row[0]}</td>
							</c:if>
							<td style="text-align: center" title="DeptNumber">${row[1]}</td>
							<td align="left" title="DeptName">${row[2]}</td>
							<td style="text-align: center" title="DepositDate">${row[3]}</td>
							<td align="left" title="billingMonthYear">${bilingMonth}-${billingYear}</td>
							<td align="left" title="MoneySourceName">${row[5]}</td>
							<td style="text-align: center" title="RevenueTypeCode">${row[6]}</td>
							<td align="left" title="RevenueTypeName">${row[7]}</td>
							<td style="text-align: right" title="DepositAmount"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${row[8]}" /></td>
							<c:set var="grandTotal" value="${grandTotal + row[8]}" />
						</tr>
					</c:when>
					<c:when test="${not empty row[2] and  empty row[0]}">
						<tr>
							<c:if test="${showTicketNumber}">
								<td style="text-align: center" title="TicketNumber" >&nbsp;</td>
							</c:if>
							<td colspan="7" align="left"><strong>Department Name Sub Total ${row[2]}</strong></td>
							<td style="text-align: right"><strong><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${row[8]}" /></strong></td>
						</tr>
					</c:when>
				</c:choose>
				<c:set var="dept" value="${row[2]}"></c:set>
			</c:forEach>
			<tr>	
				<c:if test="${showTicketNumber}">
					<td style="text-align: center" title="TicketNumber" >&nbsp;</td>
				</c:if>
				<td align="left"><strong>Grand Total:</strong></td>
				<td style="text-align: right" title="grandTotal"><strong><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${grandTotal}" /></strong></td>
				<td colspan="6">&nbsp;</td>
			</tr>
		</table>
	</div>

</div>
