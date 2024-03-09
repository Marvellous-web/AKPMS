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
</div>