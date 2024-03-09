<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li>&nbsp; Report</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<div class="table-grid" style="overflow-x: auto;">
	<h3>Charge Batch System Report</h3>
         <table border="0" cellspacing="0" cellpadding="0" width="100%">
			<tr>
				<th style="white-space:nowrap">Created By</th>
				<th style="white-space:nowrap">Created On</th>
				<th style="white-space:nowrap">Doctor</th>
				<th style="white-space:nowrap">Date From</th>
				<th style="white-space:nowrap">Date To</th>
				<th style="white-space:nowrap">Attachments By Doctor</th>
				<th style="white-space:nowrap">Super Bills By Doctor</th>
				<th style="white-space:nowrap">Batch Amount</th>
				<th style="white-space:nowrap">Received By</th>
				<th style="white-space:nowrap">Received Date</th>
				<th style="white-space:nowrap">Attachments Received By Argus</th>
				<th style="white-space:nowrap">Super Bills Received By Argus</th>
				<th style="white-space:nowrap">Batch Amount Received By Argus</th>
				<th style="white-space:nowrap">Type</th>
							
			</tr>
			<c:forEach var="chargeBatch" items="${chargeBatchProcessingList}">
				<tr>
					
					<td align="center">${chargeBatch.createdBy.firstName}</td>
					<td align="center">${chargeBatch.createdOn}</td>
					<td align="center">${chargeBatch.doctor.name}</td>
					<td align="center">${chargeBatch.dosFrom}</td>
					<td align="center">${chargeBatch.dosTo}</td>
					<td align="center">${chargeBatch.numberOfAttachments}</td>
					<td align="center">${chargeBatch.numberOfSuperbills}</td>
					<td align="center">${chargeBatch.batchAmount}</td>
					<td align="center">${chargeBatch.receviedBy.firstName}</td>
					<td align="center">${chargeBatch.dateReceived}</td>
					<td align="center">${chargeBatch.numberOfAttachmentsArgus}</td>
					<td align="center">${chargeBatch.numberOfSuperbillsArgus}</td>
					<td align="center">${chargeBatch.batchAmountArgus}</td>
					<td align="center">${chargeBatch.type}</td>
					
				</tr>
			</c:forEach>
		</table>
	</div>
</div>
