<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home </a> &raquo;</li>
			<li>&nbsp;Notifications</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<!-- Notification Start-->
	<div class="notification" id="notification">
	    <h3>Notifications</h3>
	    <ul id="notificationUL">
       	</ul>
    </div>
	<!-- Notification End-->	
</div>
<script type="text/javascript">
	var recordCount = -1; //-1 for all notificaion :nolimit
</script>
<script type="text/javascript" src="<c:url value='/static/resources/js/notification.js'/>"></script>