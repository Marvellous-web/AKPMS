<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en_US"/>
<div class="dashboard-container">
	
	<div style='text-align:center'>
    	<h1>ARGUS MEDICAL MANAGEMENT, LLC.</h1>
		<h3>PAYMENTS PRODUCTIVITY TRACKING SYSTEM</h3>
		<br/>
	</div>
	
	<div class="table-grid" style="overflow-x: auto;">
		<table style="font-size:8px!important;table-layout: fixed;">
			<thead>
				<tr>
					<th>#</th>
					<th>Date Recd</th>
					<th>Ticket No</th>
					<th>Dr. Office</th>
					<th>Insurance</th>
					<th>Deposit Amt.</th>
					<th>Manual Posted Amt.</th>
					<th>Elect. Posted Amt.</th>
					<th>Amt. Posted in CT</th>
					<th>Scan Date</th>
					<th>Total Time Taken</th>
					<th>Manual Trans.</th>
					<th>Elect. Trans.</th>
					<th>Total Trans.</th>
					<th>Date Posted</th>
					<th>Posted By</th>
					<th>Remarks</th>
				</tr>
			</thead>
			<tbody>
				<c:set var="totalDepositAmt" value="0" />
				<c:set var="totalTime" value="0" />
				<c:set var="totalManualTran" value="0" />
				<c:set var="totalElecTran" value="0" />
				<c:set var="totalTran" value="0" />
				<c:set var="totalCTPostedAmt" value="0" />
				<c:set var="totalManualAmt" value="0" />
				<c:set var="totalElecAmt" value="0" />
				<c:forEach var="payProd" items="${PAYMENT_PROD_LIST}" varStatus="counter">
				
				<c:set var="depositAmt" value="${payProd.paymentBatch.ndba + payProd.paymentBatch.depositAmount}" />
				<c:set var="totalDepositAmt" value="${totalDepositAmt + depositAmt}" />
				<c:set var="totalManualAmt" value="${totalManualAmt + payProd.paymentBatch.manuallyPostedAmt}" />	
				<c:set var="totalElecAmt" value="${totalElecAmt + payProd.paymentBatch.elecPostedAmt}" />
				<c:set var="totalManualTran" value="${totalManualTran + payProd.manualTransaction}" />
				<c:set var="totalElecTran" value="${totalElecTran + payProd.elecTransaction}" />
				<c:set var="totalTime" value="${totalTime + payProd.time}" />
					
				<c:set var="doctorSubTotal" value="${doctorSubTotal+totalDepositAmt}"></c:set>
				<c:set var="ctSubTotal" value="${ctSubTotal + pbatch.manuallyPostedAmt + pbatch.elecPostedAmt}"></c:set>
					<tr>
						<td>${counter.index + 1}</td>
						<td><fmt:formatDate value="${payProd.createdOn}" pattern="MM/dd/yyyy"/></td>
						<td>${payProd.paymentBatch.id}</td>
						<td><c:if test="${payProd.paymentBatch.doctor.nonDeposit}">
							${payProd.paymentBatch.doctor.name} (Non-Deposit)
						</c:if>
						<c:if test="${not payProd.paymentBatch.doctor.nonDeposit}">
							${payProd.paymentBatch.doctor.name}
						</c:if>
						<c:if test="${not empty payProd.paymentBatch.phDoctor.id}">(${payProd.paymentBatch.phDoctor.name})</c:if>
						</td>
						<td>${payProd.paymentBatch.insurance.name}</td>
						<td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${depositAmt}" /></td>
						<td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${payProd.paymentBatch.manuallyPostedAmt}" /></td>
						<td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${payProd.paymentBatch.elecPostedAmt}" /></td>
						<td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${payProd.paymentBatch.manuallyPostedAmt + payProd.paymentBatch.elecPostedAmt}" /></td>
						<td><fmt:formatDate value="${payProd.scanDate}" pattern="MM/dd/yyyy"/></td>
						<td>${payProd.time + 0}</td>
						<td>${payProd.manualTransaction + 0}</td>
						<td>${payProd.elecTransaction + 0}</td>
						<td>${payProd.manualTransaction + payProd.elecTransaction}</td>
						<td><fmt:formatDate value="${payProd.paymentBatch.datePosted}" pattern="MM/dd/yyyy"/></td>
						<td>${payProd.paymentBatch.postedBy.firstName} ${payProd.paymentBatch.postedBy.lastName}</td>
						<td>${payProd.paymentBatch.comment}</td>
					</tr>
					
					<%-- <c:if test="${counter.last}">		
						<tr>
						<td colspan="3">Sub Total:</td>
							<td>
							<fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${totalDepositAmt}" />
							</td>
							<td>
							<fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${totalManualAmt}" />
							</td>
							<td>
							<fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${totalElecAmt}" />
							</td>
							<td>
							<fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${totalManualAmt + totalElecAmt}" />
							</td>
							
							<td colspan="6"></td>
						</tr>
						<tr><td colspan="11"></td></tr>
					</c:if> --%>
				</c:forEach>
				
				<tr>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
							<td style="text-align: right;">
							<fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${totalDepositAmt}" />
							</td>
							<td style="text-align: right;">
							<fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${totalManualAmt}" />
							</td>
							<td style="text-align: right;">
							<fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${totalElecAmt}" />
							</td>
							<td style="text-align: right;">
							<fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${totalManualAmt + totalElecAmt}" />
							</td>
						<td></td>
						<td>${totalTime}</td>	
						<td>${totalManualTran}</td>
						<td>${totalElecTran}</td>
						<td>${totalManualTran + totalElecTran}</td>
						<td></td>
						<td></td>
						<td></td>
						</tr>		
			</tbody>
		</table>
	</div>
		
</div>   	