<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<meta http-equiv="cache-control" content="max-age=0" />
	<meta http-equiv="cache-control" content="no-store, no-cache, must-revalidate" />
	<meta http-equiv="expires" content="0" />
	<meta http-equiv="expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />
	<meta http-equiv="pragma" content="no-cache" />
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
			cache : false
		});

		$(function() {
			$("input, select").uniform();
		});
		var deleteIcon = '<c:url value="/static/resources/img/delete_icon.png"/>';
	</script>
	<style type="text/css">
		.dashboard-container {
			border:1px solid #8aacce;
			background:#fff;
			-moz-border-radius:7px;
			-webkit-border-radius:7px;
			border-radius:7px;
			padding:5px 18px 15px;
			text-align: left;
		}
		.row-container {
		    text-align: left;
		}
		#right-side-container1 #bread-crumb{
			display:none;
		}
		#right-side-container1 {
			float:left;
			width:100%;
		}
	</style>
</head>
<body>
	<!-- Main Content Start -->
	<div id="main-content">
		<!-- Right Side Start-->
		<div id="right-side-container1">
			<div id="right-side" style="margin-left:0px !important;">
				<tiles:insertAttribute name="primary-content" />
				<tiles:insertAttribute name="footer-content" />
			</div>
			<div class="clr"></div>
		</div>
	</div>
	<!-- Main Content End -->
	<!-- Main Page End -->	
</body>
</html>