<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home </a>&raquo;</li>
			<li>&nbsp;My Profile</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<div class="msg">
		<p class="success">${success}</p>
	</div>
	<form:form commandName="profileCommand" id="form1" class="form-container"
		method="post">
		<div class="form-container-inner">
			<h3>My Profile</h3>
			<span class="input-container">
				<form:label path="firstName">First Name<em class="mandatory">*</em></form:label>
				<span class="input_text">
					<span class="left_curve"></span>
					<form:input path="firstName" maxlength="50" cssClass="mid" cssStyle="width: 373px;"/>
					<span class="right_curve"></span>
				</span>
				<form:errors class="invalid" path="firstName" />
			</span>
			<span class="input-container">
				<form:label path="lastName">Last Name<em class="mandatory">*</em></form:label>
				<span class="input_text">
					<span class="left_curve"></span>
					<form:input path="lastName"  maxlength="50"  cssClass="mid" cssStyle="width: 373px;"/>
					<span class="right_curve"></span>
				</span>
				<form:errors class="invalid" path="lastName" />
			</span>
			<span class="input-container">
				<form:label path="email">Email<em class="mandatory">*</em></form:label>
				<span class="input_text">
					<span class="left_curve"></span>
					<form:input path="email" readonly="true" maxlength="255" cssClass="mid" cssStyle="width: 373px;"/>
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

			<h3>User Role</h3>
			<div class="input-container">
				<ul class="profile-ul">
					<li  style="margin:5px;">${role.name}</li>
				</ul>
			</div>

			<sec:authorize ifNotGranted="Admin">
				<br />
				<h3>Departments</h3>
				<div class="input-container">
					<ul class="profile-ul">
						<c:forEach var="dept" items="${departments}">
							<li  style="margin:5px;">${dept.name}</li>
						</c:forEach>
					</ul>
				</div>
				<br />
				<sec:authorize ifNotGranted="Trainee">
				<h3>Extra Permissions</h3>
				<div class="input-container">
					<ul class="profile-ul">
						<c:forEach var="permission" items="${permissions}">
							<li style="margin:5px;">${permission.name}</li>
						</c:forEach>
					</ul>
				</div>
				<br />
				</sec:authorize>
				<h3>Email Notifications</h3>
				<div class="input-container">
				<ul class="profile-ul">
					<c:forEach var="eTemplate" items="${emailTemplate}">
						<li><form:checkbox path="emailTemplate" value="${eTemplate}" /> ${eTemplate.name}</li>
					</c:forEach>
				</ul>
			</div>
			</sec:authorize>
			<span class="input-container"> <label>&nbsp;</label> <input
				type="submit" title="Update Profile" 
				value="Update Profile" class="login-btn" onclick="javascript:disabledButtons();this.form.submit();" /> <input
				type="button" title="Cancel" value="Cancel" class="login-btn" onclick="javascript:resetForm(this.form);" />
			</span>
		</div>
	</form:form>
</div>