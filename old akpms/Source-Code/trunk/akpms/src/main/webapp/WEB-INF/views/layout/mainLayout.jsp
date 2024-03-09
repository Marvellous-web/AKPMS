<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=9" />
	<!-- <meta http-equiv="cache-control" content="max-age=0" />
	<meta http-equiv="cache-control" content="no-store, no-cache, must-revalidate" />
	<meta http-equiv="expires" content="0" />
	<meta http-equiv="expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />
	<meta http-equiv="pragma" content="no-cache" /> -->
	<title><tiles:insertAttribute name="browser-title" /></title>

	<link rel="stylesheet" type="text/css" href="<c:url value="/static/resources/css/style.css"/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/static/resources/css/flexigrid.css"/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/static/resources/css/popupstyle.css"/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/static/resources/css/rating.css"/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/static/resources/css/forms.css"/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/static/resources/css/jquery-ui.css"/>" />
	<script type="text/javascript" src="<c:url value='/static/resources/js/jquery-1.8.2.min.js'/>"></script>

	<script type="text/javascript" charset="utf-8">
		var contextPath = "<c:out value="${pageContext.request.contextPath}" />";
	</script>

	<script type="text/javascript" src="<c:url value='/static/resources/js/forms.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/static/resources/js/flexigrid/flexigrid.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/static/resources/js/jquery.rating.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/static/resources/js/jquery.bpopup-0.7.0.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/static/resources/js/jquery-ui.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/static/resources/js/jquery.limit.js'/>"></script>

	<script type="text/javascript" charset="utf-8">
		$.ajaxSetup({
			// Disable caching of AJAX responses
			cache : true
		});

		$(function() {
			$("input, select").uniform();
		});

		var editIcon = '<c:url value="/static/resources/img/edit_icon.png"/>';
		var viewIcon = '<c:url value="/static/resources/img/view.png"/>';
		var deleteIcon = '<c:url value="/static/resources/img/delete_icon.png"/>';
		var inactiveIcon = '<c:url value="/static/resources/img/green_icon.png"/>';
		var activeIcon = '<c:url value="/static/resources/img/red_icon.png"/>';
		var resetIcon = '<c:url value="/static/resources/img/reset_icon.png"/>';
		var deleteMsg = 'Are you sure you wish to delete the selected record(s)?';
		var noItemSelectedMsg = 'Please select at least one record.';
		var ajaxLoadImg = "<img alt='Loding...' title='Loding...' src='"+contextPath+"/static/resources/img/ajax-loader.gif'/>";
		var ajaxLoadImgHoriz = "<img alt='Loding...' title='Loding...' src='"+contextPath+"/static/resources/img/flexigrid/ajax-loader.gif'/>";
		
		function logout(){
			var Backlen = history.length;
			history.go(-Backlen);
			window.location.href=contextPath+"/j_spring_security_logout";
		}
	</script>
</head>
<body>
	<tiles:insertAttribute name="header-content" />

	<!-- Main Content Start -->
	<div id="main-content">
		<!-- Right Side Start-->
		<div id="right-side-container">
			<div id="right-side">
				<!-- Dashboard Heading Start-->
				<div class="dashboard-heading-container">
					<div class="heading-txt">
						<h2>
							<tiles:insertAttribute name="section-title" />
							<span id="selected_menu" style="display: none;"><tiles:insertAttribute name="show-menu-name" /></span>
						</h2>
						<p><!-- General Information -->&nbsp;</p>
					</div>
					<%
						String kwd = "";
						if(request.getParameter("kwd") != null)	{
							kwd = request.getParameter("kwd");
						}
					%>
					<div class="search-box">
						<form action="<c:url value='/processmanual' />" method="get">
							<input type="text" name="kwd" maxlength="50" title="Search Process Manual" value="<%=kwd%>"/>
							<input type="submit" title="Click to go" value="" />
						</form>
					</div>
					<div class="clr"></div>
				</div>
				<!-- Dashboard Heading End-->

				<tiles:insertAttribute name="primary-content" />

				<tiles:insertAttribute name="footer-content" />

			</div>
			<div class="clr"></div>
			<div id="copyright">Copyright &copy; 2012 Argus.All Rights are Reserved.</div>
		</div>
		<!-- Right Side End-->
		<tiles:insertAttribute name="left-navigation-content" />
	</div>
	<!-- Main Content End -->
	<!-- Main Page End -->
</body>
</html>
