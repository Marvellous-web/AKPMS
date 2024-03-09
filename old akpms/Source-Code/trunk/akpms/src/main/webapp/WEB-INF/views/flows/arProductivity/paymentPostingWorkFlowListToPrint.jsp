<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en_US"/>
<div class="dashboard-container">
	
	<c:set var="billedAmount" value="0"></c:set>
	<c:set var="primaryAmount" value="0"></c:set>
	<c:set var="secondaryAmount" value="0"></c:set>
	<div style='text-align:center'>
    	<h1>ARGUS MEDICAL MANAGEMENT, LLC.</h1>
		<h3>5150 E.Pacific Coast Hwy, Suite 500</h3>
		<h3>Long Beach CA 90804</h3>
		<h3>Payment Posting Work FLow Report</h3>
		<br/>
	</div>
	
	<div class="table-grid" style="overflow-x: auto;">
		<table style="font-size:7px!important; table-layout:fixed;">
			<thead>
				<tr>
					<th>Status</th>
					<th>Patient ID</th>
					<th>Patient Name</th>
					<th>CPT</th>
					<th>Doctor Name</th>
					<th>Insurance</th>
					<th>DOS</th>
					<th>Team</th>
					<th>Billed Amount</th>
					<th>Amount Paid By Primary</th>
					<th>Amount Paid By Secondary</th>
					<th>Contractual Adjustment</th>
					<th>Bulk Payment</th>
					<th>Patient Response</th>
					<th>Check issue date</th>
					<th>Check #</th>
					<th>Check Cashed date</th>
					<th>Address check send to</th>
					<th>Eob</th>
					<th>Copy of cancel check</th>
					<th>Created Date</th>
				</tr>
			</thead>
			<tbody>

				<c:forEach var="paymentPosting" items="${paymentPostingWorkFlowList}">
				
					<tr>
						<td>${paymentPosting.status}</td>
						<td>${paymentPosting.arProductivity.patientAccNo}</td>
						<td>${paymentPosting.arProductivity.patientName}</td>
						<td>${paymentPosting.cpt}</td>
						<td>${paymentPosting.arProductivity.doctor.name}</td>
						<td>${paymentPosting.arProductivity.insurance.name}</td>
						<td>${paymentPosting.dos}</td>
						<td>${paymentPosting.arProductivity.team.name}</td>
						<td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${paymentPosting.billedAmount}" /></td>
						<td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${paymentPosting.primaryAmount}" /></td>
						<td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${paymentPosting.secondaryAmount}" /></td>
						<td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${paymentPosting.contractualAdj}" /></td>
						<td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${paymentPosting.bulkPaymentAmount}" /></td>
						<td>${paymentPosting.patientResponse}</td>
						<td><fmt:formatDate value="${paymentPosting.checkIssueDate}" pattern="MM/dd/yyyy"/></td>
						<td>${paymentPosting.checkNo}</td>
						<td><fmt:formatDate value="${paymentPosting.checkCashedDate}" pattern="MM/dd/yyyy"/></td>
						<td>${paymentPosting.addressCheckSend}</td>
						<td>${paymentPosting.eob.name}</td>
						<td>${paymentPosting.copyCancelCheck.name}</td>
						<td><fmt:formatDate value="${paymentPosting.createdOn}" pattern="MM/dd/yyyy"/></td>
						<c:set var="billedAmount" value="${billedAmount + paymentPosting.billedAmount }"></c:set>
						<c:set var="primaryAmount" value="${primaryAmount + paymentPosting.primaryAmount }"></c:set>
						<c:set var="secondaryAmount" value="${secondaryAmount + paymentPosting.secondaryAmount }"></c:set>
					</tr>
					
				</c:forEach>
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td colspan="4"><b>Total</b></td>
						<td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${billedAmount}" /></td>
						<td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${primaryAmount}" /></td>
						<td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${secondaryAmount}" /></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
			</tbody>
		</table>
	</div>
</div>
