<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title><tiles:insertAttribute name="browser-title" /></title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/static/resources/css/styleprint.css"/>" media="screen" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/static/resources/css/print.css"/>" media="print" />
	<script type="text/javascript" src="<c:url value='/static/resources/js/jquery-1.8.2.min.js'/>"></script>
	<style type="text/css">
		td, th{
			word-wrap:break-word;
		}
		
		.pos{
			color:green;
		}
		.neg{
			color:red;
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