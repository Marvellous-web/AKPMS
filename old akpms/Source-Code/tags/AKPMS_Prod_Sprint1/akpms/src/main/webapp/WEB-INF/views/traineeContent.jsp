<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="dashboard-container" >
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="#">Home &raquo; </a></li>
			<li>Dashboard</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	
	<!-- Notification Start-->
	<div class="notification" id="notification">
	    <h3>Notifications</h3>
	    <ul id="notificationUL">
       	</ul>
       	<div class="view-all"><a class="btn-red" href="<c:url value='/notifications'/>">View All</a></div>
    </div>
	<!-- Notification End-->
	
	<div class="content-box">
		<!-- Statistics Start -->
		<div class="stat-box">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th colspan="2"><h3>Trainee Evaluation</h3></th>
				</tr>
				<tr class="odd">
					<td>Process Manual Read Evaluation</td>
					<td>${processManualReadPercent}</td>
				</tr>
				<tr class="even">
					<td>Argus Evaluation</td>
					<td>${argusPercent}</td>
				</tr>
				<tr class="odd">
					<td>IDS Argus Evaluation</td>
					<td>${idsArgusPercent}</td>
				</tr>
				<tr class="even">
					<td>Total Percent</td>
					<td>${traineePercent}</td>
				</tr>
			</table>
		</div>

		<div class="activity-box">

		</div>
		<div class="clr"></div>
	</div>
</div>