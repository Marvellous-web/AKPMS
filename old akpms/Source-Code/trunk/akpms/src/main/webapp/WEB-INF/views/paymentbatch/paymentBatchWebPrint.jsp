<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

	<div class="dashboard-container" >
		<c:forEach var="pbatch" items="${PAYMENT_BATCHES}" varStatus="counter">
			<div style='text-align:center'>
			   	<h1>ARGUS MEDICAL MANAGEMENT, LLC.</h1>
				<h3>PAYMENTS BATCH TRACKING SYSTEM TICKET</h3>
				<br/>
			</div>
			
	 		<div class="table-grid" style="overflow-x: auto;">
				<table align="center" style="width: 17cm; margin: auto; table-layout:fixed; border-width: 1px;">
					<tbody>
						<tr>
							<td class="left-heading"><strong>Ticket Number</Strong></td>
							<td class="left-text" colspan="3"><strong style="font-size: 15pt;">${pbatch.id}</strong></td>
						</tr>
						<tr>
							<td class="left-heading"><strong>Billing Month</Strong></td>
							<td class="left-text" >${pbatch.billingMonth}/${pbatch.billingYear}</td>
							
							<td class="right-heading"><strong>Group/Doctor Name</strong></td>
							
							<td class="right-text" >
							<c:set var="nonDeposit" scope="session" value="${pbatch.doctor.nonDeposit}"/>
						<c:if test="${nonDeposit}">
							 ${pbatch.doctor.name}(Non-Deposit)
						</c:if>
						<c:if test="${!nonDeposit}">
								${pbatch.doctor.name}
						</c:if> </td>

				<%-- 			${pbatch.doctor.name}</td> --%>
						</tr>
						
						<tr>
							<td class="left-heading"><strong>Payment Type</strong></td>
							<td class="left-text" >${pbatch.paymentType.name}</td>
							
							<td class="right-heading"><strong>PH Doctor Name</strong></td>
							<td class="right-text" >${pbatch.phDoctor.name}</td>
						</tr>
						
						<tr>
							<td class="left-heading"><strong>Insurance</strong></td>
							<td class="left-text" >${pbatch.insurance.name}</td>
							
							<td class="right-heading"><strong>Revenue Type</strong></td>
							<td class="right-text" >${pbatch.revenueType.name}</td>
						</tr>
						
						<tr>
							<td class="left-heading"><strong>Money Source</strong></td>
							<td class="left-text" >${pbatch.moneySource.name}</td>
						
							<td class="right-heading"><strong>Refund Check</strong></td>
							<td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${pbatch.refundChk}" /></td>
						</tr>
						
						<tr>
							<td class="left-heading"><strong>Deposit Date</strong></td>
							<td><fmt:formatDate value="${pbatch.depositDate}" pattern="MM/dd/yyyy"/></td>
							
							<td class="right-heading"><strong>Agency Money</strong></td>
							<td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${pbatch.agencyMoney}" /></td>
						</tr>
						
						<tr>
							<td class="left-heading"><strong>Deposit Amount</strong></td>
							<td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${pbatch.depositAmount}" /></td>
							
							<td class="right-heading"><strong>OLD Prior AR</strong></td>
							<td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${pbatch.oldPriorAr}" /></td>
						</tr>
						
						<tr>
							<td class="left-heading"><strong>NDBA</strong></td>
							<td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${pbatch.ndba}" /></td>
							
							<td class="right-heading"><strong>CT Posted Total</strong></td>
							<td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${pbatch.ctPostedTotal}" /></td>
						</tr>
						
						<tr>
							<td class="left-heading"><strong>Other Income</strong></td>
							<td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${pbatch.otherIncome}" /></td>
							
							<td class="right-heading"><strong>+/- Posting</strong></td>
							<td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${pbatch.depositAmount+pbatch.ndba-pbatch.manuallyPostedAmt-pbatch.elecPostedAmt}" /></td>
						</tr>
						
						<tr>
							<td class="left-heading"><strong>NSF/Sys Ref</strong></td>
							<td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${pbatch.nsfSysRef}" /></td>
							
							<td class="right-heading"><strong>Suspense Account</strong></td>
							<td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${pbatch.suspenseAccount}" /></td>
						</tr>
						
						<tr>
							<td class="left-heading"><strong>ERA-Check #</strong></td>
							<td class="right-text" >${pbatch.eraCheckNo}</td>
							
							<td class="right-heading"><strong>Offset</strong></td>
							<td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${pbatch.posting}" /></td>
						</tr>
						
						<tr>
							<td class="left-heading"><strong>Created By</strong></td>
							<td class="right-text" >${pbatch.createdBy.firstName} ${pbatch.createdBy.lastName}</td>
							
							<td class="right-heading"><strong>Created On</strong></td>
							<td><fmt:formatDate value="${pbatch.createdOn}" pattern="MM/dd/yyyy"/></td>
						</tr>
						
						<c:if test="${not empty pbatch.revisedOn}">
							<tr>
								<td class="left-heading"><strong>Revised By</strong></td>
								<td class="right-text" >${pbatch.revisedBy.firstName} ${pbatch.revisedBy.lastName}</td>
								
								<td class="right-heading"><strong>Revised On</strong></td>
								<td><fmt:formatDate value="${pbatch.revisedOn}" pattern="MM/dd/yyyy"/></td>
							</tr>
						</c:if>
						
						<c:if test="${not empty pbatch.postedOn}">
							<tr>
								<td class="left-heading"><strong>Posted By</strong></td>
								<td class="right-text" >${pbatch.postedBy.firstName} ${pbatch.postedBy.lastName}</td>
								
								<td class="right-heading"><strong>CT Posted Date</strong></td>
								<td><fmt:formatDate value="${pbatch.datePosted}" pattern="MM/dd/yyyy"/></td>
							</tr>
						</c:if>
						
						<tr>
							<td class="left-heading"><strong>Comment:</strong></td>
							<td class="right-text"  colspan="3">${pbatch.comment}</td>
						</tr>
					</tbody>
				</table>
			</div>
			<c:if test="${counter.last eq false}">	
				<div class="page-break"></div>
			</c:if>
		</c:forEach>
	</div>