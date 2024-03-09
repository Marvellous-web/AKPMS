<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li><a href="<c:url value='/chargeproductivityreject' />">&nbsp;Charge Batch Rejections List</a>
				&raquo;</li>
			 <li>&nbsp;${operationType}  Resolution</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<div class="section-content">
		<h3 class="heading-h3">Rejection Details</h3>
		<div id="msg" class="error"></div>
		<div class="row-container">
			<div class="element-block">
				<span class="lbl">Ticket number:</span>  <span id="ticketNumber" >${batchId}</span>
			</div>
			<div class="element-block">
				<span class="lbl">Reason To reject:</span>  <span id="reasonToReject" >${chargeProdReject.reasonToReject}</span>
			</div>
			<div class="clr"></div>
		</div>
		<div class="row-container">
			<div class="element-block">
				<span class="lbl">Patient Name:</span>  <span id="reasonToReject" >${chargeProdReject.patientName}</span>
			</div>
			<div class="element-block">
				<span class="lbl">Sequence:</span>  <span>${chargeProdReject.sequence}</span>
			</div>
			<div class="clr"></div>
		</div>
		<div class="row-container">
			<div class="element-block">
				<span class="lbl">Account:</span>  <span id="reasonToReject" >${chargeProdReject.account}</span>
			</div>
			<div class="element-block">
				<span class="lbl">Date Of Service:</span>  <span><fmt:formatDate value="${chargeProdReject.dateOfService}" pattern="MM/dd/yyyy"/></span>
			</div>
			<div class="clr"></div>
		</div>
		<div class="row-container">
			<div class="element-block">
				<span class="lbl">Insurance Type:</span>  <span id="reasonToReject" ><spring:message code ="${chargeProdReject.insuranceType}" /></span>
			</div>
			<div class="clr"></div>
		</div>
	</div>

	<form:form commandName="chargeProdReject" id="form1" class="form-container">
		<div class="form-container-inner">
			<h3>Resolution Details</h3>
			<div class="row-container">
				<form:label path="remarks">Remarks:<em class="mandatory">*</em></form:label>
					<form:textarea path="remarks" cols="30" rows="5"/>
					<form:errors class="invalid" path="remarks" />
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block">
					<form:label path="dateOfFirstRequestToDoctorOffice">Date Of First Request to Doctor Office:<em class="mandatory">*</em></form:label>
					<div class="input_text">
						<span class="left_curve"></span>
						<form:input path="dateOfFirstRequestToDoctorOffice" readonly="true" maxlength="50" cssClass="mid" cssStyle="width: 90px;" />
						<span class="right_curve"></span>
					</div>
					<form:errors class="invalid" path="dateOfFirstRequestToDoctorOffice" />
				</div>
				<div class="element-block">
					<form:label path="dateOfSecondRequestToDoctorOffice">Date Of Second Request to Doctor Office:</form:label>
					<div class="input_text">
						<span class="left_curve"></span>
						<form:input path="dateOfSecondRequestToDoctorOffice" readonly="true" maxlength="50" cssClass="mid" cssStyle="width: 90px;" />
						<span class="right_curve"></span>
					</div>
					<form:errors class="invalid" path="dateOfSecondRequestToDoctorOffice" />
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<form:label path="remarks2">Resolution:</form:label>
					<form:textarea path="remarks2" cols="30" rows="5"/>
					<form:errors class="invalid" path="remarks2" />
					<div id="resolutionError" class="invalid"></div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<form:checkbox path="dummyCpt"/>
				<form:label path="dummyCpt">dummy CPT:</form:label>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<label>&nbsp;</label>
				<input type="hidden" name ="operation" id="operation" />
				<c:if test="${chargeProdReject.resolved == true }">
					<sec:authorize ifAnyGranted="P-6">
						<input type="submit" title="Update"	value="Update" class="login-btn" onclick="javascript:disabledButtons();this.form.submit();"/>
					</sec:authorize>
				</c:if>
				<c:if test="${chargeProdReject.resolved != true }">
					<input type="submit" title="Update"	value="Update" class="login-btn" onclick="javascript:disabledButtons();this.form.submit();"/>
					<input type="submit" title="Resolved"	value="Set as resolved" class="login-btn" onclick="return resolutionValidationCheck(this.form);"/>
				</c:if>
				<div class="clr"></div>
			</div>
		</div>
	</form:form>
</div>
<script type="text/javascript">
	$(function() {
	    $( "#dateOfFirstRequestToDoctorOffice" ).datepicker();
	    $( "#dateOfSecondRequestToDoctorOffice" ).datepicker();
	    //$( "#postDate" ).datepicker();
	});

	function resolutionValidationCheck(frm)
	{
		if($('#remarks2').val() == '')
		{
			$('#resolutionError').html("Resolution field can not be left empty to make it resolved.");
			return false;
		}else{
			$('#operation').val('resolved');
			$('#resolutionError').html("");
			frm.submit();
			return true;
		}
	}
</script>
