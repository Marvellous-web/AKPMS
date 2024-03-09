<%@page import="argus.util.Constants"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="dashboard-container" >
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home </a> &raquo; </li>
			<li>&nbsp;Process Manual List</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<div class="msg">
		<p class="success">${success}</p>
	</div>

	<div class="form-container">
		<div class="form-container-inner">
			<h3>Search Process Manual</h3>
			<form action="<c:url value='/processmanual' />" method="get">
				<span class="input-container">
					<label>Keyword:</label>
					<%
						String kwd = "";
						if(request.getParameter("kwd") != null){
							kwd = request.getParameter("kwd");
						}
					%>
					<span class="input_text">
						<span class="left_curve"></span>
						<input type="text" name="kwd" id="keyword" value="<%=kwd%>"  maxlength="50" class="mid" style="width: 373px"/>
						<span class="right_curve"></span>
					</span>
				</span>
				<br />
				<span class="input-container">
					<label>&nbsp;</label>
					<input type="submit" title="Search" value="Search" class="login-btn" />
					<input type="button" title="Cancel" value="Cancel" class="login-btn" onclick="javascript:resetForm(this.form);" />
				</span>
			</form>
			<br />
			<br />
			<div id="listManuals">
				<ul class="sectionList">
					<c:choose>
						<c:when test="${section==''}">
							<p class="sectionContent"><%=Constants.NO_PROCESS_MANUAL_FOUND %></p>
						</c:when>
						<c:otherwise>
							<c:forEach var="section" items="${section}">
								<li class="main-process"><a href="<c:url value='/processmanual/detail' />?id=${section.id}">${section.title}</a></li>
								<c:forEach var="subSection" items="${section.processManualList}">
									<c:if test="${subSection.deleted eq false}">
										<li><a href="<c:url value='/processmanual/detail' />?id=${section.id}#div_subsection_${subSection.id}">${subSection.title}</a></li>
									</c:if>
								</c:forEach>
							</c:forEach>					
						</c:otherwise>
					</c:choose>					
				</ul>
			</div>

			<div id="searchManuals">
				<ul class="sectionList">
					<c:choose>
						<c:when test="${searchSection==''}">
							<p class="sectionContent"><%=Constants.NO_RECORD_FOUND %></p>
						</c:when>
						<c:otherwise>
							<c:forEach var="section" items="${searchSection}">
								<c:choose>
									<c:when test="${section.parent.id > 0}">
										<li class="main-process"><a href="<c:url value='/processmanual/detail' />?id=${section.parent.id}#div_subsection_${section.id}">${section.title}</a></li>
									</c:when>
									<c:otherwise>
										<li class="main-process"><a href="<c:url value='/processmanual/detail' />?id=${section.id}">${section.title}</a></li>
									</c:otherwise>
								</c:choose>
								<p class="sectionContent">${fn:substring(section.content, 0, 400)} ...</p>
							</c:forEach>						
						</c:otherwise>
					</c:choose>	
				</ul>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
var kwd = "<%=kwd%>";;
if(kwd == ""){
	$("#listManuals").show();
	$("#searchManuals").hide();
}else{
	$("#listManuals").hide();
	$("#searchManuals").show();
}

</script>