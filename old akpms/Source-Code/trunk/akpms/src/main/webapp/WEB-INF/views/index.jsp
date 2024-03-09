<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<sec:authorize access="hasRole('Document Manager')">
<%@include file="docManContent.jsp"%>
</sec:authorize>

<sec:authorize access="hasRole('Other')">
<%@include file="docManContent.jsp"%>
</sec:authorize>

<sec:authorize access="hasRole('Standard User')">
<%@include file="standUserContent.jsp"%>
</sec:authorize>

<sec:authorize access="hasRole('Trainee')">
<%@include file="traineeContent.jsp"%>
</sec:authorize>
<script type="text/javascript">
	var recordCount = 0; //-1 for all notificaion :nolimit
</script>
<script type="text/javascript" src="<c:url value='/static/resources/js/notification.js'/>"></script>