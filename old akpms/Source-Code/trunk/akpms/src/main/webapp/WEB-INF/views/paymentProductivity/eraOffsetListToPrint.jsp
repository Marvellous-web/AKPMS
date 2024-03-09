<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="en_US"/>
<div class="dashboard-container">
	
	<div style='text-align:center'>
    	<h1>ARGUS MEDICAL MANAGEMENT, LLC.</h1>
		<h3>5150 E.Pacific Coast Hwy, Suite 500</h3>
		<h3>Long Beach CA 90804</h3>
		<br/>
	</div>
	
	<div class="table-grid" style="overflow-x: auto;">
		<table style="font-size:7pt!important; table-layout:fixed;">
			<thead>
				<tr>
					<th>Ticket #</th>
					<th>Doctor Name</th>
					<th>Insurance Name</th>
					<th>Patient Name</th>
					<th>Patient ID</th>
					<th>Total Amt</th>
					<th>Check No.</th>
					<th>Check Date</th>
					<th>Posted Date </th>
					<th>Posted By</th>
					<th>Remark</th>
					<th colspan="3">Total Posted Details</th>
				</tr>
				<!-- <tr>
					<th>CPT</th>
					<th>DOS</th>
					<th>Amount</th>
				</tr> -->
			</thead>
			<tbody>
				
				<c:forEach var="eraOffset" items="${eraOffsetList}">
				<c:set var="rowSpan">${fn:length(eraOffset.offsetRecords)}</c:set>
					<tr>
						<td rowspan="${rowSpan+1}">${eraOffset.paymentBatch.id}</td>
						
						<td rowspan="${rowSpan+1}">
							${eraOffset.paymentBatch.doctor.name}
							<c:if test="${not empty eraOffset.paymentBatch.phDoctor}">
								(${eraOffset.paymentBatch.phDoctor.name})
							</c:if>
						</td>
						<td rowspan="${rowSpan+1}">${eraOffset.paymentBatch.insurance.name}</td>
						
						<td rowspan="${rowSpan+1}">${eraOffset.patientName}</td>
						<td rowspan="${rowSpan+1}">${eraOffset.accountNumber}</td>
						<td rowspan="${rowSpan+1}" style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${eraOffset.totalAmount}" /></td>
						<td rowspan="${rowSpan+1}">${eraOffset.chkNumber}</td>
						<td rowspan="${rowSpan+1}"><fmt:formatDate value="${eraOffset.chkDate}" pattern="MM/dd/yyyy"/></td>
						<td rowspan="${rowSpan+1}"><fmt:formatDate value="${eraOffset.paymentBatch.datePosted}" pattern="MM/dd/yyyy"/></td>
						<td rowspan="${rowSpan+1}">${eraOffset.paymentBatch.postedBy.firstName} ${eraOffset.paymentBatch.postedBy.lastName}</td>
						<td rowspan="${rowSpan+1}">${eraOffset.remark}</td>
						<td style="font-weight: bold;">
							 <c:if test="${rowSpan gt 0 }">
								CPT
							</c:if>
						</td>
						<td style="font-weight: bold;">
							<c:if test="${rowSpan gt 0 }">
								DOS
							</c:if>
						</td>
						<td style="font-weight: bold;">
							<c:if test="${rowSpan gt 0 }">
								Amount
							</c:if>
						</td>
					</tr>
					<c:if test="${rowSpan gt 0}">
						<c:forEach var="offsetRecord" items="${eraOffset.offsetRecords}">
							<tr>
								<td>${offsetRecord.cpt}</td>
								<td ><fmt:formatDate value="${offsetRecord.dos}" pattern="MM/dd/yyyy"/></td>
								<td style="text-align: right;"><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${offsetRecord.amount}" /></td>
							</tr>
						</c:forEach>
					</c:if>
					
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
