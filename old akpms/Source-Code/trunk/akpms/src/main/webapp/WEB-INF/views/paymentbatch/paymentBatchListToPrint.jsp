<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en_US"/>
<div class="dashboard-container">

<div style='text-align:center'>
    	<h1>ARGUS MEDICAL MANAGEMENT, LLC.</h1>
		<h3>PAYMENTS BATCH TRACKING SYSTEM</h3>
		<br/>
	</div>
	<c:set var="proHealthId" value="1"></c:set>
	<c:set var="proHealthName" value="ProHealth"></c:set>
	<c:set var="doctorName" value="" />
	<c:set var="doctorSubTotal" value="0"></c:set>
	<c:set var="ctSubTotal" value="0"></c:set>
	<c:set var="creditCardTotal" value="0"></c:set>
	<c:set var="EFTTotal" value="0"></c:set>
	<c:set var="NDBA" value="0"></c:set>
	<c:set var="ndbaSubTotal" value="0"></c:set>
	<c:forEach var="pbatch" items="${PAYMENT_BATCHES}" varStatus="counter">
		<c:set var="totalDepositAmt" value="${pbatch.ndba + pbatch.depositAmount}" />
		<c:set var="creditCardTotal" value="${creditCardTotal+pbatch.creditCard}"></c:set>
		<c:set var="NDBA" value="${NDBA+pbatch.ndba}"></c:set>
		
		<c:if test="${not empty pbatch.moneySource &&  pbatch.moneySource.id eq '1'}">
			<c:set var="EFTTotal" value="${EFTTotal+(pbatch.depositAmount + pbatch.ndba)}"></c:set>
		</c:if>
		
			<c:if test="${pbatch.doctor.name ne doctorName}">
				<c:if test="${counter.index != 0}">
						<tr>
							<c:choose>
								<c:when test="${doctorName eq proHealthName}">
									<td colspan="4">Sub Total:</td>
								</c:when>
								<c:otherwise>
									<td colspan="3">Sub Total:</td>
								</c:otherwise>
							</c:choose>
							
							<td style="text-align: right;">
								<fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${doctorSubTotal}" />
							</td>
							<td style="text-align: right;">
								<fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${ctSubTotal}" />
							</td>
							<td style="text-align: right;">
								<fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${ndbaSubTotal}" />
							</td>
							<td colspan="6">&nbsp;</td>
						</tr>
					</table>
					</div>
					<div class="page-break"></div>
					<div style='text-align:center'>
				    	<h1>ARGUS MEDICAL MANAGEMENT, LLC.</h1>
						<h3>PAYMENTS BATCH TRACKING SYSTEM</h3>
						<br/>
					</div>
				</c:if>
				<div class="table-grid" style="overflow:auto;">
				<table style="font-size:8pt!important; table-layout:fixed; width: 950px;">
					<thead>
						<tr>
							<th width="80px">Ticket #</th>
							<c:if test="${pbatch.doctor.id == proHealthId }">
								<th width="80px">Ph Doctor Name</th>
							</c:if>
							<th width="60px">Billing Month</th>
							<th width="78px">Deposit Date</th>
							<th width="80px">Deposit Amount</th>
							<th width="80px">CT Posted Amount</th>
							<th width="80px">NDBA</th>
							<th width="78px">CT Posted Date</th>
							<th width="59px">Posted By</th>
							<th width="79px">Payment Type</th>
							<th width="89px">Insurance</th>
							<th width="69px">ERA Check #</th>
							<c:choose>
								<c:when test="${pbatch.doctor.id == proHealthId }">
									<th width="107px">Comment</th>
								</c:when>
								<c:otherwise>
									<th width="187px">Comment</th>
								</c:otherwise>
							</c:choose>

			</tr>
					</thead>
					
					
					<tr>
					
						<c:choose>
							<c:when test="${pbatch.doctor.id == proHealthId }">
								<td colspan="12">
							</c:when>
							<c:otherwise>
								<td colspan="11">
							</c:otherwise>
						</c:choose>						
						<c:if test="${pbatch.doctor.nonDeposit}">
							<strong>${pbatch.doctor.name} (Non-Deposit)</strong>
						</c:if>
						<c:if test="${!pbatch.doctor.nonDeposit}">
							<strong>${pbatch.doctor.name}</strong>
						</c:if>						
						</td>
					</tr>
					
				<c:set var="doctorName" value="${pbatch.doctor.name}" />
				<c:set var="doctorSubTotal" value="0"></c:set>		
				<c:set var="ctSubTotal" value="0"></c:set>
				<c:set var="ndbaSubTotal" value="0"></c:set>
			</c:if>
			
			<c:set var="doctorSubTotal" value="${doctorSubTotal+pbatch.depositAmount}"></c:set>
			<c:set var="ctSubTotal" value="${ctSubTotal + pbatch.manuallyPostedAmt + pbatch.elecPostedAmt}"></c:set>
			<c:set var="ndbaSubTotal" value="${ndbaSubTotal+pbatch.ndba}"></c:set>
			
			<tr>
				<td>${pbatch.id}</td>
				<c:if test="${pbatch.doctor.id == proHealthId }">
					<td>${pbatch.phDoctor.name}</td>
				</c:if>
				<td>${pbatch.billingMonth} / ${pbatch.billingYear}</td>
				<td><fmt:formatDate value="${pbatch.depositDate}" pattern="MM/dd/yyyy"/></td>
				<td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${pbatch.depositAmount}" /></td>
				<td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${pbatch.ctPostedTotal}" /></td>
				<td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${pbatch.ndba}" /></td>
				<td><fmt:formatDate value="${pbatch.datePosted}" pattern="MM/dd/yyyy"/></td>
				<td>${pbatch.postedBy.firstName} ${pbatch.postedBy.lastName}</td>
				<td>${pbatch.paymentType.name}</td>
				<td>${pbatch.insurance.name}</td>
				<td>${pbatch.eraCheckNo}</td>
				<td>${pbatch.comment}</td>
			</tr>
			<c:if test="${counter.last}">		
					<tr>
					
						<c:choose>
							<c:when test="${pbatch.doctor.id == proHealthId }">
								<td colspan="4">Sub Total:</td>
							</c:when>
							<c:otherwise>
								<td colspan="3">Sub Total:</td>
							</c:otherwise>
						</c:choose>
						
						
						<td style="text-align: right;">
							<fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${doctorSubTotal}" />
						</td>
						<td style="text-align: right;">
							<fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${ctSubTotal}" />
						</td>
						<td style="text-align: right;">
								<fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${ndbaSubTotal}" />
						</td>
						<td colspan="6">&nbsp;</td>
					</tr>
				</table>
				</div>
			</c:if>
			
	</c:forEach>		
			
	
	<div class="page-break"></div>
	<div style='text-align:center'>
    	<h1>ARGUS MEDICAL MANAGEMENT, LLC.</h1>
		<h3>PAYMENTS BATCH TRACKING SYSTEM</h3>
		<br/>
	</div>
	<div class="table-grid" style="overflow:auto;">
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
			    	<td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${DEPOSIT_TOTAL}" /></td>
			    	<td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${POSTED_TOTAL}" /></td>
			    </tr>
		    </tbody>
	   	</table>	
	</div>
	
   	<div class="table-grid" style="overflow:auto;">
   		<table class="left_th plain_th right_td">
		  <tbody>
			  <tr>
			    <th>Deposit Total</th>
			    <td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${DEPOSIT_TOTAL}" /></td>
			  </tr>
			  <tr>
			    <th>CT Posted Total</th>
			    <td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${POSTED_TOTAL}" /></td>
			  </tr>
			  <tr>
			    <th>Over/Under Posting</th>
			    <td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${OVERUNDER_POSTING}" /></td>
			  </tr>
			  <tr>
			    <th>Agency Money</th>
			    <td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${AGENCY_MONEY}" /></td>
			  </tr>
			  <tr>
			    <th>Other Income</th>
			    <td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${OTHER_INCOME}" /></td>
			  </tr>
			  <tr>
			    <th>Old Prior AR</th>
			    <td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${OLD_PRIOR_AR}" /></td>
			  </tr>
			  <tr>
			    <th>CT NSF / System Refund</th>
			    <td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${SYS_REFUND_TOTAL}" /></td>
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