<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li>&nbsp;Evaluation Settings</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<div class="msg">
		<p class="success" id="msgP">${success}</p>
	</div>
	<form:form commandName="evaluationSettings" id="form1" class="form-container">
		<div class="form-container-inner">
			<h3>Evaluation Settings</h3>
			<span class="input-container"> <form:label
					path="proceesManaulReadStatusRatings">Process Manual Read Status<em
						class="mandatory">*</em>
				</form:label> <span class="input_text"> <span class="left_curve"></span> <form:input
						path="proceesManaulReadStatusRatings" maxlength="7"
						cssClass="mid numbersOnly" cssStyle="width: 45px;" /> <span
					class="right_curve"></span>%
			</span> <form:errors class="invalid" path="proceesManaulReadStatusRatings" />
			</span> <span class="input-container"> <form:label
					path="idsArgusRatings">IDS Argus Ratings<em
						class="mandatory">*</em>
				</form:label> <span class="input_text"> <span class="left_curve"></span> <form:input
						path="idsArgusRatings" maxlength="7" cssClass="mid numbersOnly"
						cssStyle="width: 45px;" /> <span class="right_curve"></span>%
			</span> <form:errors class="invalid" path="idsArgusRatings" />
			</span>
			<span class="input-container">
				<form:label path="argusRatings">Argus Ratings<em class="mandatory">*</em></form:label>
					<span class="input_text"> <span class="left_curve"></span>
						<form:input path="argusRatings" maxlength="7" cssClass="mid numbersOnly"
						cssStyle="width: 45px;" />
					<span class="right_curve"></span>%
				</span> <br/><br/>
				<form:errors class="invalid" path="argusRatings" />
			</span>
			<span class="input-container">
				<strong>Note:</strong>
				1)The ratings value cannot be less than 0 and greater than 100<br/>
				2)The sum of all the ratings should not exceed 100.<br/>
				3)The ratings can accept the values upto 2 decimal places.<br/>
			</span>
			<span class="input-container">
				<label>&nbsp;</label>
				<input type="submit" title="update" value="update"
				class="login-btn" />
				<input type="button" title="Cancel" onclick="javascript:resetForm(this.form);"
				value="Cancel" class="login-btn" />
			</span>
		</div>
	</form:form>
</div>
<html>
<script type="text/javascript" src="<c:url value='/static/resources/js/common.js'/>"></script>
</html>
