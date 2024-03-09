<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Main Page -->
<!-- Header Start -->
<div id="header">
	<!-- Logo Start -->
	<div id="logo">
		<h1>
			<a href="#"><img src="<c:url value="/static/resources/img/main-logo.png"/>" alt="" /></a>
		</h1>
	</div>
	<!-- Logo End -->
	<!-- User Setting Start -->
	<div id="user-setting">
		<div class="wc-txt">
			Welcome
			<sec:authentication property="principal.firstName" />
		</div>
		<div class="user-btn-container">
			<a href="javascript:void(0);" onclick="javascript:logout();" title="Logout"><img src="<c:url value="/static/resources/img/logout.png"/>" alt="Logout" title="Logout" onclick="javascript:logout();"  /></a>
			<!-- <a href="#" class="setting-btn"> <span class="left-img"></span>
					<span class="right-txt">Settings</span>
				</a> -->
			<!-- <br/><br/>Last Login = <sec:authentication property="principal.lastLogin" /> -->
		</div>
	</div>
	<div class="clr"></div>
	<!-- User Setting End -->
</div>
<!-- Header End -->