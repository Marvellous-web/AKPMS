<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home </a> &raquo; </li>
			<li>&nbsp;Change Password</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<div class="msg">
		<p class="errror">${error}</p>
		<p class="success">${success}</p>
	</div>
	<form:form commandName="changePassword" method="post" class="form-container">
		<div class="form-container-inner">
			<h3>Change Password</h3>
			<span class="input-container">
				<form:label path="oldPassword">Old Password<em class="mandatory">*</em></form:label>
				<span class="input_text">
					<span class="left_curve"></span>
						<form:password path="oldPassword" maxlength="50" cssClass="mid" cssStyle="width: 373px;" />
					<span class="right_curve"></span>
				</span>
				<span class="left_curve">
				<form:errors class="invalid" path="oldPassword" />
				</span>
			</span>

			<span class="input-container">
				<form:label path="newPassword">New Password<em class="mandatory">*</em></form:label>
				<span class="input_text">
					<span class="left_curve"></span>
					<form:password path="newPassword" maxlength="50" cssClass="mid" cssStyle="width: 373px;" />
					<span class="right_curve"></span>
				</span>
				<form:errors class="invalid" path="newPassword" />
			</span>

			<span class="input-container">
				<form:label path="confirmPassword">Confirm Password<em class="mandatory">*</em></form:label>
				<span class="input_text">
					<span class="left_curve"></span>
					<form:password path="confirmPassword" maxlength="50" cssClass="mid" cssStyle="width: 373px;" />
					<span class="right_curve"></span>
				</span>
				<form:errors class="invalid" path="confirmPassword" />
			</span>

			<span class="input-container">
				<label>&nbsp;</label>
				<input type="submit" title="Submit" value="Submit"
				class="login-btn" onclick="javascript:disabledButtons();this.form.submit();" />
				<input type="button" title="Cancel" onclick="javascript:resetForm(this.form);"
				value="Cancel" class="login-btn"  />
			</span>
		</div>
	</form:form>
</div>

