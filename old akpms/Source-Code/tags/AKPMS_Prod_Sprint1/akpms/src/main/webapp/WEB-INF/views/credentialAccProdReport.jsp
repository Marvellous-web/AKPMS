<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<link rel="stylesheet" type="text/css"
		href="<c:url value="/static/resources/css/style.css"/>" />
<link rel="stylesheet" type="text/css"
		href="<c:url value="/static/resources/css/forms.css"/>" />
</head>
<body>
<div class="dashboard-container">
	<h1>Credentialing and Accounting Productivity Report</h1>
	<div class="table-grid" style="overflow-x:auto;">
		<table border="0" cellspacing="0" cellpadding="0" width="100%">
			<tr>
				<th>Credentialing Task</th>
				<th>Date Receive</th>
				<th>Task Completed Date</th>
				<th>Time Taken</th>
				<th>Created By</th>
				<th>Remarks</th>
			</tr>

			<c:forEach var="row" items="${rows}">
				<tr>
					<td align="center">${row.credentialingTask}</td>
					<td align="center"><fmt:formatDate value="${row.dateRecd}" pattern="MM/dd/yyyy"/></td>
					<td align="center"><fmt:formatDate value="${row.taskCompleted}" pattern="MM/dd/yyyy"/></td>
					<td align="center">${row.time}</td>
					<td align="center">${row.createdBy.firstName}</td>
					<td align="center">${row.remark}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>
</body>
</html>
