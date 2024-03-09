<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li>&nbsp;Evaluation Report</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<div class="table-grid" style="overflow-x: auto;">
         <table border="0" cellspacing="0" cellpadding="0" width="100%">
         	<tr>
         		<th>Trainee Name</th>
         		<th colspan="3">Evaluation Type</th>
         		<th>Total (%)</th>
         	</tr>
			<tr>
				<td class="heading">&nbsp;</td>
				<td class="heading">Process Manual Read</td>
				<td class="heading">IDS Argus</td>
				<td class="heading">Argus</td>
				<td class="heading">&nbsp;</td>
			</tr>
			<c:forEach var="trainee" items="${traineeEvaluationReportDataList}">
				<tr>
					<td class="trainee_name" style="white-space:nowrap">
						<a href="<c:url value='/traineeevaluation/evaluatetrainee?tid=${trainee.id}'/>">${trainee.firstName} ${trainee.lastName} </a>
					</td>
					<td align="center">${trainee.processManualReadpercent}</td>
					<td align="center">${trainee.idsArgusPercent}</td>
					<td align="center">${trainee.argusPercent}</td>
					<td align="center">${trainee.totalTraineePercent}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>
