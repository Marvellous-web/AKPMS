<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- Main Page -->
<!-- Header Start -->
<div id="header">
	<!-- Logo Start -->
	<div id="logo">
		<h1>
			<a href="/akpms/"><img src="<c:url value="/static/resources/img/main-logo.png"/>" alt="" /></a>
		</h1>
	</div>
	<!-- Logo End -->
	<!-- User Setting Start -->
	<div id="user-setting">
		<div class="wc-txt">
			Welcome <sec:authentication property="principal.firstName" />
		</div>
		<div class="user-btn-container">
			<jsp:useBean id="now" class="java.util.Date"/>
		</div>
	</div>
	<span style="color: #6D6C6C;"><fmt:formatDate value="${now}" dateStyle="medium" timeStyle="short"  pattern="E, MMM dd, yyyy HH:mm a z"/></span>
	<div class="clr"></div>
	<!-- User Setting End -->
</div>
<!-- Header End -->
