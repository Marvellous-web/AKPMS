<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.Calendar" %>
<fmt:setLocale value="en_US"/>
<div class="dashboard-container">
	
	<div style="width: 100%; text-align: center" id="reportHeading">
		<strong>ARGUS MEDICAL MANAGEMENT, LLC.</strong> <br/>
		JOURNAL ENTRIES MANAGEMENT FEE <br/>
		FOR THE MONTH OF ${month} - ${year}
	</div>	
	
	<div class="table-grid" style="overflow-x: auto;">
		<table style="font-size:9px!important;table-layout: fixed;margin: auto;" align="center">
		<thead>
			<tr>
				<th style="width: 165px">Revenue Sub-Account</th>
				<th style="width: 165px">Cost Center Number</th>
				<th colspan="2">Transaction Description</th>
				<th style="width: 80px">Debit</th>
				<th style="width: 80px">Credit</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="row" items="${reportData}" varStatus="loop">
				<c:choose>
					<c:when test="${not loop.last}">
						<tr>
							<td style="text-align: center" >${row[0]}</td>
							<td style="text-align: center">${row[2]}</td>
							<td align="left">Management Fee</td>
							<td align="left">${row[3]}<c:if test="${empty row[3]}">&nbsp;</c:if>  </td>
							<td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${row[5]}" /></td>
							<td style="text-align: right;">&nbsp;</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<td style="text-align: center">3620</td>
							<td style="text-align: center">9260</td>
							<td align="left">Management Fee</td>
							<td align="left">B.O. - Payment</td>
							<td style="text-align: right;">&nbsp;</td>
							<td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${row[6]}" /></td>
						</tr>
						<tr>
							<td style="text-align: center">3620</td>
							<td style="text-align: center">9010</td>
							<td align="left">Management Fee</td>
							<td align="left">Accounting</td>
							<td style="text-align: right;">&nbsp;</td>
							<td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${row[7]}" /></td>
						</tr>
						<tr>
							<td style="text-align: center">3620</td>
							<td style="text-align: center">9030</td>
							<td align="left">Management Fee</td>
							<td align="left">Operation</td>
							<td style="text-align: right;">&nbsp;</td>
							<td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${row[8]}" /></td>
						</tr>
						<tr>
							<td style="text-align: center">&nbsp;</td>
							<td style="text-align: center">&nbsp;</td>
							<td align="left" colspan="2"><strong>TOTALS:</strong></td>
							<td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${row[5]}" /></td>
							<td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${row[5]}" /></td>
						</tr>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="6" border="0" style="border: 0px !important;">
						<table style="border: 0px !important; width: 100%;" border="0"
							class="innerTable">
							<tr>
								<td colspan="7">
									<hr>
								</td>
							</tr>
							<tr>
								<td>Description:</td>
								<td colspan="6">To: Record "Other Income" Revenue for Vault
									and Lockbox</td>
							</tr>
							<tr>
								<td>Voucher#: ${month_numeric}14</td>
								<td>&nbsp;&nbsp;&nbsp;${pageContext.request.userPrincipal.principal.firstName}
									${pageContext.request.userPrincipal.principal.lastName} <br />
									<hr>&nbsp;&nbsp;&nbsp;Prepared By
								</td>
								<td>&nbsp;&nbsp;&nbsp;<fmt:formatDate
										value="<%=new java.util.Date()%>" pattern="MM/dd/yyyy" /><br />
									<hr>&nbsp;&nbsp;&nbsp;Date
								</td>
								<td><br />
									<hr>&nbsp;&nbsp;&nbsp; Approved By</td>
								<td><br />
									<hr>&nbsp;&nbsp;&nbsp;Date</td>
								<td><br />
									<hr>&nbsp;&nbsp;&nbsp; Keyed</td>
								<td><br />
									<hr>&nbsp;&nbsp;&nbsp; Date</td>
							</tr>
						</table>
					</td>
				</tr>
			</tfoot>
		</table>
	</div>
	
</div>
