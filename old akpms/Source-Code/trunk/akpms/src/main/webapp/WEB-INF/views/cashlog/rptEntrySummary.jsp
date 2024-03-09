<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.Calendar" %>
<fmt:setLocale value="en_US"/>
<div class="dashboard-container">
	<div style="width: 100%; text-align: center" id="reportHeading">
		<strong>PROHEALTH PARTNERS, A MEDICAL GROUP, INC.</strong> <br/>
		JOURNAL ENTRIES SUMMARY <br/>
		FOR THE MONTH OF ${month} - ${year}
	</div>
	
	<div class="table-grid" style="overflow-x: auto;">
		<table style="font-size:9px!important;table-layout: fixed;margin: auto" align="center">
		<thead>
			<tr>
				<th>Revenue Sub-Account</th>
				<th>Cost Center Number</th>
				<th colspan="2">Transaction Description</th>
				<th>Debit</th>
				<th>Credit</th>
			</tr>
		</thead>
			<c:set var="total" value="0"></c:set>
			<c:forEach var="row" items="${reportData}" varStatus="loop">
			<c:set var="total">${total + row[4]}</c:set>
				
						<tr>
							<td style="text-align: center">${row[0]}</td>
							<td style="text-align: center">${row[2]}</td>
							<td align="left">${row[1]}</td>
							<td align="left">${row[3]}</td>
							<td style="text-align: right">&nbsp;</td>
							<td style="text-align: right"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${row[4]}" /></td>
						</tr>
			</c:forEach>		
						<tr>
							<td style="text-align: center">1010</td>
							<td style="text-align: center">1100</td>
							<td align="left" colspan="2">Cash - Shared Account</td>
							<td style="text-align: right"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"   type="currency" value="${total}" /></td>
							<td style="text-align: right">&nbsp;</td>
						</tr>

			<tfoot>
				<tr>
					<td colspan="5" border="0" style="border: 0px !important;">
						<table style="border: 0px !important; width: 112%;" border="0"
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
								<td>Voucher#: ${month_numeric}02</td>
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
