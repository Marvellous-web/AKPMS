<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<sec:authorize access="hasRole('Admin')">
<%@include file="adminContent.jsp"%>
</sec:authorize>
