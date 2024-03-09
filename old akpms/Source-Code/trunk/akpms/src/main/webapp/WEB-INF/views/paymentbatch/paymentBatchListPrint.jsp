<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en_US"/>
<div class="dashboard-container">
	
	<div style='text-align:center'>
    	<h1>ARGUS MEDICAL MANAGEMENT, LLC.</h1>
		<h3>PAYMENTS BATCH TRACKING SYSTEM</h3>
		<br/>
	</div>
	
	<div class="table-grid" style="overflow-x: auto;">
		<table style="font-size:8pt!important; table-layout:fixed;">
			<thead>
				<tr>
					<th>Ticket #</th>
					<th>Doctor/Group Name</th>
					<th>Billing Month</th>
					<th>Deposit Date</th>
					<th>Deposit Amount</th>
					<th>NDBA</th>
					<th>CT Posted Amount</th>
					<th>CT Posted Date</th>
					<th>Posted By</th>
					<th>Payment Type</th>
					<th>Insurance</th>
					<th>ERA Check #</th>
					<th>Comment</th>
				</tr>
			</thead>
			<tbody>

				<c:set var="totalDepositAmt" value="0" />
				<c:set var="totalCTPostedAmt" value="0" />
				<c:set var="totalAgencyMoney" value="0" />
				<c:set var="totalOtherIncome" value="0" />
				<c:set var="totalOldPriorAr" value="0" />
				<c:set var="totalNsfSysRef" value="0" />
				<c:set var="creditCardTotal" value="0" />
				<c:set var="EFTTotal" value="0" />
				<c:set var="NDBA" value="0" />
				<c:forEach var="pbatch" items="${PAYMENT_BATCHES}" varStatus="counter">
				
					<c:if test="${not empty pbatch.moneySource &&  pbatch.moneySource.id eq '1'}">
						<c:set var="EFTTotal" value="${EFTTotal+(pbatch.depositAmount + pbatch.ndba)}"></c:set>
					</c:if>
				
					<c:set var="depositAmt" value="${pbatch.ndba + pbatch.depositAmount}" />
					<c:set var="totalDepositAmt" value="${totalDepositAmt + pbatch.depositAmount}" />
					<c:set var="totalCTPostedAmt" value="${totalCTPostedAmt + pbatch.manuallyPostedAmt + pbatch.elecPostedAmt}" />
					<c:set var="totalAgencyMoney" value="${totalAgencyMoney + pbatch.agencyMoney}" />
					<c:set var="totalOtherIncome" value="${totalOtherIncome + pbatch.otherIncome}" />
					<c:set var="totalOldPriorAr" value="${totalOldPriorAr + pbatch.otherIncome}" />
					<c:set var="totalNsfSysRef" value="${totalNsfSysRef + pbatch.nsfSysRef}" />
					<c:set var="creditCardTotal" value="${creditCardTotal+pbatch.creditCard}"></c:set>
					<c:set var="NDBA" value="${NDBA+pbatch.ndba}"></c:set>
					<tr>
						<td>${pbatch.id}</td>
						
						<td><c:set var="nonDeposit" scope="session" value="${pbatch.doctor.nonDeposit}"/>
						<c:if test="${nonDeposit}">
							 ${pbatch.doctor.name}(Non-Deposit)
						</c:if>
						<c:if test="${!nonDeposit}">
								${pbatch.doctor.name}
						</c:if> 
								<%-- ${pbatch.doctor.name} --%>
						<c:if test="${not empty pbatch.phDoctor.id}">(${pbatch.phDoctor.name})</c:if>
						</td>
						<td>${pbatch.billingMonth} / ${pbatch.billingYear}</td>
						<td><fmt:formatDate value="${pbatch.depositDate}" pattern="MM/dd/yyyy"/></td>
						<td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${pbatch.depositAmount}" /></td>
						<td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${pbatch.ndba}" /></td>
						<td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${pbatch.ctPostedTotal}" /></td>
						<td><fmt:formatDate value="${pbatch.datePosted}" pattern="MM/dd/yyyy"/></td>
						<td>${pbatch.postedBy.firstName} ${pbatch.postedBy.lastName}</td>
						<td>${pbatch.paymentType.name}</td>
						<td>${pbatch.insurance.name}</td>
						<td>${pbatch.eraCheckNo}</td>
						<td>${pbatch.comment}</td>
					</tr>
					
				</c:forEach>		
			</tbody>
		</table>
	</div>
	
	<div class="page-break"></div>
	<div class="table-grid" style="overflow-x: auto!important;">
		<table id=report class="plain_th right_td">
		  	<thead>
			  	<tr>
				    <th></th>
				    <th>Deposit Amount</th>
				    <th>Posted CT</th>
			    </tr>
		  	</thead>
		  	<tbody>  
			  	<tr>
			    	<th>Total</th>
			    	<td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${totalDepositAmt}" /></td>
			    	<td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${totalCTPostedAmt}" /></td>
			    </tr>
		    </tbody>
	   	</table>	
	</div>
	
   	<div class="table-grid" style="overflow-x: auto!important;">
   		<table class="left_th plain_th right_td">
		  <tbody>
			  <tr>
			    <th>Deposit Total</th>
			    <td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${totalDepositAmt}" /></td>
			  </tr>
			  <tr>
			    <th>CT Posted Total</th>
			    <td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${totalCTPostedAmt}" /></td>
			  </tr>
			  <tr>
			    <th>Over/Under Posting</th>
			    <td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${totalDepositAmt-totalCTPostedAmt}" /></td>
			  </tr>
			  <tr>
			    <th>Agency Money</th>
			    <td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${totalAgencyMoney}" /></td>
			  </tr>
			  <tr>
			    <th>Other Income</th>
			    <td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${totalOtherIncome}" /></td>
			  </tr>
			  <tr>
			    <th>Old Prior AR</th>
			    <td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${totalOldPriorAr}" /></td>
			  </tr>
			  <tr>
			    <th>CT NSF / System Refund</th>
			    <td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${totalNsfSysRef}" /></td>
			  </tr>
			  <tr>
			    <th>Credit Card Total</th>
			    <td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${creditCardTotal}" /></td>
			  </tr>
			  <tr>
			    <th>EFT Total</th>
			    <td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${EFTTotal}" /></td>
			  </tr>
			  <tr>
			    <th>NDBA</th>
			    <td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${NDBA}" /></td>
			  </tr>
		  </tbody>
	  </table>
   	</div>
   	
</div>   	