<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	
	<c:if test="${not empty continueUrl}">
		<meta http-equiv="refresh" content="2;url=<c:url value="${continueUrl}" />"/>
	</c:if>
	
	<title><tiles:insertAttribute name="browser-title" /></title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/static/resources/css/bootstrap.min.css"/>" media="screen" />
	<%-- <link rel="stylesheet" type="text/css" href="<c:url value="/static/resources/css/bootstrap-theme.min.css"/>" media="screen" /> --%>
	
	
	<script type="text/javascript" src="<c:url value='/static/resources/js/jquery-1.8.2.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/static/resources/js/bootstrap.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/static/resources/js/bootbox.min.js'/>"></script>
	
	<script type="text/javascript" src="<c:url value='/static/resources/js/jquery.cookie.js'/>"></script>
	
	<%-- <link rel="stylesheet" type="text/css" href="<c:url value="/static/resources/css/fontawesome-cheatsheet.css"/>" media="screen" /> --%>
	<link rel="stylesheet" type="text/css" href="<c:url value="/static/resources/css/font-awesome.css"/>" media="screen" />
	
	<script type="text/javascript" charset="utf-8">
		var contextPath = "<c:out value="${pageContext.request.contextPath}" />";
		var deleteIcon = '<c:url value="/static/resources/img/delete_icon.png"/>';
		var addIcon = '<c:url value="/static/resources/img/flexigrid/add.png"/>';
	</script>
	
	<style type="text/css">
		body{
			font-size: 12px;
		}
		.chktd{
			width: 20px;
			padding:2px;
		}
		.chktd1{
			padding: 11px;
    		width: 31px;
		}
		.heading-txt{
			background-color: #2465A7;
			color: #FFFFFF;
			font-size: 18px;
			font-weight: bold;
    		padding: 8px;
		}
		#logo {
			float:left;
			margin:0 0 0 10px;
		}
		#user-setting {
			float:right;
		    padding: 0 10px;
		}
		.wc-txt {
			background:url(../img/user-icon-small.png) no-repeat;
			padding:4px 0 1px 22px;
			color:#003b77;
			font-size:11px;
			font-weight:bold;
		}
		.user-btn-container a {
			float:left;
			margin:0 10px 0 0;
			cursor:pointer;
		}
		.clr {
			clear:both;
		}
		img {
			border:none;
		}
		#main-content {
			margin:10px 8px 0;
		}
		
		.modal-dialog{
			 width: 750px;
		}
		
		.list-group-item{
			padding: 5px;
			font-size: 11px;
			font-weight: normal;
		} 
		
		tr.hide_tr{
			display: none;
		}
		
		tr.show_tr{
			display: table-row!important;
			background-color: #D4E9FC!important;
		}
		
	</style>
	
</head>

<body>

	<tiles:insertAttribute name="header-content" />
	
	<div class="heading-txt">
		<tiles:insertAttribute name="section-title" />
	</div>
	
	<!-- Main Content Start -->
	<div id="main-content">
		<!-- Right Side Start-->
		
			<div style="margin-left:0px !important;">
			
				<c:if test="${not empty error}">
					<div class="alert alert-danger alert-dismissible" role="alert">
						<button type="button" class="close" data-dismiss="alert">
							<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
						</button>
						<strong>${error}</strong>
					</div>
				</c:if>
				
				<c:if test="${not empty success}">
					<div class="alert alert-success alert-dismissible" role="alert">
						<button type="button" class="close" data-dismiss="alert">
							<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
						</button>
						<strong>${success}</strong>
					</div>
				</c:if>
				
				<tiles:insertAttribute name="primary-content" />
				<tiles:insertAttribute name="footer-content" />
			</div>
			<div class="clr"></div>
		</div>
	
	<!-- Main Content End -->
	<!-- Main Page End -->
</body>
</html>
