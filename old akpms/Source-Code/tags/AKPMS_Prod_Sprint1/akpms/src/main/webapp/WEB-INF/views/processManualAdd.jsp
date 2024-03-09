<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo; </li>
			<li><a href="<c:url value='/processmanual' />">&nbsp; Process Manual List</a> &raquo; </li>
			<li>&nbsp;${operationType} Process Manual</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<form:form commandName="processManual" id="form1" class="form-container" >
		<div class="form-container-inner">
		    <h3>${operationType} ProcessManual</h3>

			<span class="input-container">
				<form:label path="title">Title<em class="mandatory">*</em>:</form:label>
				<span class="input_text">
					<span class="left_curve"></span>
					<form:input path="title" maxlength="254" cssClass="mid" cssStyle="width: 373px;" />
					<span class="right_curve"></span>
				</span>
				<form:errors class="invalid" path="title" />
			</span>
			<form:hidden path="id" />
			<form:hidden path="parent.id" ></form:hidden>
			<input type="hidden" name="parentId" id="parentId" value="0" />
			<span class="input-container">
				<form:label path="content">Content<em class="mandatory">*</em>:</form:label>
			</span>
			<div class="input-container">
				<FCK:editor toolbarSet="Akpms" instanceName="content" width="750" height="425" >
				    <jsp:attribute name="value" trim="true">
				    ${processManual.content}
				    </jsp:attribute>
				</FCK:editor>
			</div>
			<form:errors class="error" path="content" />
			<span class="input-container">
				<form:label path="Status" >Status:</form:label>
				<form:radiobutton path="status" value="1" />Active
				<form:radiobutton path="status" value="0" />Inactive
			</span>
			<span class="input-container">
				<form:label path="notification" >Send Notification:</form:label>
				<form:checkbox path="notification" value="1"/>
			</span>
			<br />
			<h3>Departments</h3>
			<div class="input-container">
				<c:forEach var="dept" items="${departments}">
					<c:forEach var="child" items="${dept.value}">
						<c:choose>
							<c:when test="${dept.key eq child.name}">
								<div class="dept-heading"><form:checkbox path="departments" value="${child}" id="p_${fn:replace(dept.key,' ','_')}" onclick="javascript:checkChild(this);" /> ${dept.key}</div>
							</c:when>
							<c:otherwise>
								&nbsp; &nbsp; <form:checkbox path="departments" value="${child}" id="p_${fn:replace(dept.key,' ','_')}_child_${child}"  onclick="javascript:checkParent(this,\"${fn:replace(dept.key,' ','_')}\");" /> ${child.name}
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</c:forEach>
				<form:errors class="invalid" path="departments" />
			</div>

			<c:if test="${operationType == 'Edit'}">
				<span class="input-container">
					<form:label path="modificationSummary">Modification Summary:</form:label>
					<form:textarea rows="8" cols="20" path="modificationSummary" />
					<form:errors class="invalid" path="modificationSummary" />
				</span>
			</c:if>

			<span class="input-container">
				<label>&nbsp;</label>
				<input type="submit" title="${buttonName} ProcessManual" value="${buttonName}"
				class="login-btn" onclick="return warnChildInactive(this.form);" />
				<input type="button" title="Cancel" value="Cancel" class="login-btn" onclick="javascript:resetForm(this.form);" />
				<c:if test="${operationType == 'Edit'}">
					<input type="button" title="Delete" value="Delete"
						class="login-btn"
						onclick="deleteProcessManual(${processManual.id})" />
				</c:if>
			</span>
		</div>
	</form:form>
</div>
<script type="text/javascript">
function deleteProcessManual(id) {
	if (confirm("Deleting the main section will also delete its sub-sections. Are you sure you wish to proceed?")) {
		$.ajax({
			type : "POST",
			dataType : "json",
			url : contextPath + "/processmanual/delete",
			data : "item=" + id,
			success : function(data) {
				window.location.href= contextPath + "/processmanual";
			}
		});
	}
}

function checkParent(chkbx, parentDepartmentId){
	if(chkbx.checked){
		$("#p_"+parentDepartmentId).attr('checked', true);
		$.uniform.update("#p_"+parentDepartmentId);		
	}
	$.uniform.update("#"+chkbx.id);
}

function checkChild(chkbx){
	if(chkbx.checked){
		$('input:checkbox[id^="'+chkbx.id+'_child"]').attr('checked', "checked");
	}else{
		$('input:checkbox[id^="'+chkbx.id+'_child"]').removeAttr('checked');
	}
	$.uniform.update();	
}

function FCKeditor_OnComplete(editorInstance) {
	//window.status = editorInstance.Description;
}

function warnChildInactive(form){
	if($('input[name=status]:checked').val() == 0 && $("#id").val() > 0){
		//if($('#status2').attr('checked') && $("#id").val() > 0) {
		if(confirm("Inactivating this section will also inactivate its all sub-sections. Are you sure you wish to proceed?")){
			disabledButtons();
			form.submit();
		}else{
			return false;
		}
	}
	disabledButtons();
	form.submit();
}
</script>
