<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<c:set var="isRead" value="false"></c:set>
<c:set var="containsDept" value="false"></c:set>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home &raquo; </a></li>
			<li>Manual Report</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<div class="table-grid">
		<sec:authorize ifAnyGranted="P-14">
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
				<tr>
					<th width="*" style="min-width: 300px;">Title</th>
					<c:forEach var="trainee" items="${traineeList}">
						<th><label style="white-space: nowrap;">${trainee.firstName}
								${trainee.lastName}</label></th>
					</c:forEach>
				</tr>
				<c:forEach var="processManual" items="${processManualList}">
					<tr>
						<td align="left" style="font-weight: bold;">${processManual.title}</strong></td>
						<c:forEach var="trainee" items="${traineeList}">
							<c:forEach var="processManualTrainee"
								items="${processManual.userList}">
								<c:if test="${processManualTrainee.id == trainee.id}">
									<c:set var="isRead" value="true"></c:set>
								</c:if>
							</c:forEach>

							<c:forEach var="processManualDept"
								items="${processManual.departments}">
								<c:forEach var="traineeDept" items="${trainee.departments}">
									<c:if test="${traineeDept.id == processManualDept.id}">
										<c:set var="containsDept" value="true"></c:set>
									</c:if>
								</c:forEach>
							</c:forEach>

							<c:choose>
								<c:when test="${isRead}">
									<td style="vertical-align: middle;" align="center"><img
										alt="read"
										src="<c:url value="/static/resources/img/tick.png"/>" /></td>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${containsDept}">
											<td style="vertical-align: middle;" align="center"><img
												alt="unread"
												src="<c:url value="/static/resources/img/cross.png"/>" /></td>
										</c:when>
										<c:otherwise>
											<td style="vertical-align: middle;" align="center">N.A</td>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
							<c:set var="isRead" value="false"></c:set>
							<c:set var="containsDept" value="false"></c:set>
						</c:forEach>
					</tr>

					<c:forEach var="subprocessManual"
						items="${processManual.processManualList}">
						<tr>
							<td><label style="margin-left: 10px;">${subprocessManual.title}</label></td>
							<c:forEach var="trainee" items="${traineeList}">
								<c:forEach var="subProcessManualTrainee"
									items="${subprocessManual.userList}">
									<c:if test="${subProcessManualTrainee.id == trainee.id}">
										<c:set var="isRead" value="true"></c:set>
									</c:if>
								</c:forEach>
								<c:forEach var="processManualDept"
									items="${processManual.departments}">
									<c:forEach var="traineeDept" items="${trainee.departments}">
										<c:if test="${traineeDept.id == processManualDept.id}">
											<c:set var="containsDept" value="true"></c:set>
										</c:if>
									</c:forEach>
								</c:forEach>
								<c:choose>
									<c:when test="${isRead}">
										<td style="vertical-align: middle;" align="center"><img
											alt="read"
											src="<c:url value="/static/resources/img/tick.png"/>" /></td>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${containsDept}">
												<td style="vertical-align: middle;" align="center"><img
													alt="unread"
													src="<c:url value="/static/resources/img/cross.png"/>" /></td>
											</c:when>
											<c:otherwise>
												<td style="vertical-align: middle;" align="center">N.A</td>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
								<c:set var="isRead" value="false"></c:set>
								<c:set var="containsDept" value="false"></c:set>
							</c:forEach>
						</tr>
					</c:forEach>
				</c:forEach>
			</table>
		</sec:authorize>
		<sec:authorize ifAnyGranted="Trainee">
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
				<tr>
					<th>Title</th>
					<th width="1%"><label style="white-space: nowrap;">Read
							Status</label></th>
				</tr>
				<c:forEach var="processManual" items="${processManualList}">
					<tr>
						<td align="left"><strong>${processManual.title}</strong></td>
						<c:forEach var="processManualTrainee"
							items="${processManual.userList}">
							<c:if test="${processManualTrainee.id == trainee.id}">
								<c:set var="isRead" value="true"></c:set>
							</c:if>
						</c:forEach>
						<c:choose>
							<c:when test="${isRead}">
								<td style="vertical-align: middle;" align="center"><img
									alt="read"
									src="<c:url value="/static/resources/img/tick.png"/>" /></td>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${processManual.showReadAndUnderstood}">
										<td style="vertical-align: middle;" align="center"><img
											alt="unread"
											src="<c:url value="/static/resources/img/cross.png"/>" /></td>
									</c:when>
									<c:otherwise>
										<td style="vertical-align: middle;" align="center">N.A</td>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
						<c:set var="isRead" value="false"></c:set>
					</tr>

					<c:forEach var="subprocessManual"
						items="${processManual.processManualList}">
						<tr>
							<td align="left"><label style="margin-left: 10px;">${subprocessManual.title}</label></td>
							<c:forEach var="subProcessManualTrainee"
								items="${subprocessManual.userList}">
								<c:if test="${subProcessManualTrainee.id == trainee.id}">
									<c:set var="isRead" value="true"></c:set>
								</c:if>
							</c:forEach>
							<c:choose>
								<c:when test="${isRead}">
									<td style="vertical-align: middle;" align="center"><img
										alt="read"
										src="<c:url value="/static/resources/img/tick.png"/>" /></td>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${processManual.showReadAndUnderstood}">
											<td style="vertical-align: middle;" align="center"><img
												alt="unread"
												src="<c:url value="/static/resources/img/cross.png"/>" /></td>
										</c:when>
										<c:otherwise>
											<td style="vertical-align: middle;" align="center">N.A</td>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
							<c:set var="isRead" value="false"></c:set>
						</tr>
					</c:forEach>
				</c:forEach>
			</table>
		</sec:authorize>
	</div>
</div>
