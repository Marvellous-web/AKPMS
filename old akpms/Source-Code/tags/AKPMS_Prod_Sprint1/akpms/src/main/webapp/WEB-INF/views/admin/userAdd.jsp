<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li><a href="<c:url value='/admin/user' />">&nbsp;User List</a>
				&raquo;</li>
			<li>&nbsp;${operationType} User</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<form:form commandName="user" id="form1" class="form-container">
		<div class="form-container-inner">
			<h3>${operationType} User</h3>
			<span class="input-container">
				<form:label path="firstName">First Name<em class="mandatory">*</em></form:label>
				<span class="input_text">
					<span class="left_curve"></span>
						<form:input path="firstName" maxlength="50" cssClass="mid" cssStyle="width: 373px;"  />
					<span class="right_curve"></span>
				</span>
				<form:errors class="invalid" path="firstName" />
			</span>
			<span class="input-container">
				<form:label path="lastName">Last Name<em class="mandatory">*</em></form:label>
				<span class="input_text">
					<span class="left_curve"></span>
						<form:input path="lastName" maxlength="50"  cssClass="mid" cssStyle="width: 373px;" />
					<span class="right_curve"></span>
				</span>
				<form:errors class="invalid" path="lastName" />
			</span>
			<span class="input-container">
				<form:label path="email">Email<em class="mandatory">*</em></form:label>
				 <span class="input_text">
					<span class="left_curve"></span>
					<form:input path="email" maxlength="255" cssClass="mid" cssStyle="width: 373px;"/>
					<span class="right_curve"></span>
				</span>
				<form:errors class="invalid" path="email" />
			</span>
			<span class="input-container">
				<form:label path="contact">Contact<em class="mandatory">*</em></form:label>
				<span class="input_text">
					<span class="left_curve"></span>
					<form:input path="contact" maxlength="255" cssClass="mid" cssStyle="width: 373px;"/>
					<span class="right_curve"></span>
				</span>
				<form:errors class="invalid" path="contact" />
			</span>
			<span class="input-container">
				<form:label path="address">Address</form:label>
				<form:textarea rows="3" cols="20" path="address" />
			</span>
			<span class="input-container"> <form:label path="Status">Status:</form:label>
				<form:radiobutton path="status" value="1" />Active
				<form:radiobutton path="status" value="0" />Inactive
			</span>
			<br/>
			<h3>
				User Role<em class="mandatory">*</em>
			</h3>
			<span class="input-container">
				<c:forEach var="role" items="${roles}">
					<form:radiobutton path="role.id" value="${role.id}" onchange="showHidePermissions(this.value);" />
						${role.name} &nbsp;&nbsp;
				</c:forEach>
				<form:errors class="invalid" path="role" />
			</span>
			<br /> <br />
			<h3>Departments</h3>
			<div class="input-container">
				<c:forEach var="dept" items="${departments}">
					<c:forEach var="child" items="${dept.value}">
						<c:choose>
							<c:when test="${dept.key eq child.name}">
								<div class="dept-heading">
									<form:checkbox path="departments" value="${child}" id="p_${fn:replace(dept.key,' ','_')}" onclick="javascript:checkChild(this);" /> ${dept.key}
								</div>
							</c:when>
							<c:otherwise>
								&nbsp; &nbsp; <form:checkbox path="departments" value="${child}" id="p_${fn:replace(dept.key,' ','_')}_child_${child}"  onclick="javascript:checkParent(this,\"${fn:replace(dept.key,' ','_')}\");" /> ${child.name}
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</c:forEach>
				<form:errors class="invalid" path="departments" />
			</div>
			<br /> <br />
			<div id="divExtraPermissions">
				<h3>Extra Permissions</h3>
				<div class="input-container">
					<ul class="profile-ul">
						<c:forEach var="permission" items="${permissions}">
							<li><form:checkbox path="permissions" value="${permission}" /> ${permission.name}</li>
						</c:forEach>
					</ul>
				</div>
				<br /> <br />
			</div>
			<h3>Email Notifications</h3>
			<div class="input-container">
				<ul class="profile-ul">
					<c:forEach var="eTemplate" items="${emailTemplate}">
						<li><form:checkbox path="emailTemplate" value="${eTemplate}" /> ${eTemplate.name}</li>
					</c:forEach>
				</ul>
			</div>
			<br /> <br />
			<span class="input-container"> <label>&nbsp;</label>
				<input type="submit" title="Save / Update User"
				value="${buttonName}" class="login-btn" onclick="javascript:disabledButtons();this.form.submit();"/>
				<input type="button" title="Cancel" value="Cancel" class="login-btn"  onclick="javascript:resetForm(this.form);" /> <c:if
					test="${operationType == 'Edit'}">
					<input type="button" title="Delete" value="Delete"
						class="login-btn"
						onclick="deleteUser(${user.id})" />
				</c:if>
			</span>
		</div>
	</form:form>
</div>
<script type="text/javascript">
	function deleteUser(id) {
		if (confirm("Are you sure you wish to delete this user?")) {
			$.ajax({
				type : "POST",
				dataType : "json",
				url : contextPath + "/admin/user/delete",
				data : "item=" + id,
				success : function(data) {
					window.location.href= contextPath+'/admin/user?successUpdate=user.deletedSuccessfully';
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

	function showHidePermissions(roleId){
		if(roleId == '${traineeUserRole}'){
			$('#divExtraPermissions').hide();
		}else{
			$('#divExtraPermissions').show();
		}
	}

	showHidePermissions('${userRole}');
</script>