<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="argus.util.Constants"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%-- <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%> --%>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home </a> &raquo;</li>
			<li>&nbsp;Print Payment Batch </li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<div class="msg">
		<p class="success" id="msgP">${success}</p>
	</div>

	<form:form commandName="paymentBatch" class="form-container" id="paymentBatchSearchForm" target="_blank">
		<div class="form-container-inner">
			<h3>Print Payment Batch Ticket</h3>

			<div class="row-container">
		    	<form:label path="doctor.id">Group/Doctor Name:</form:label>
		    	<div>
					<form:select id="doctorId" path="doctor.id" cssClass="selectbox" onchange="javascript:checkProHealth();">
						<form:option value="">-: Select Group/Doctor Name :-</form:option>
						<form:options items="${doctorList}" itemValue="id" itemLabel="name" />
					</form:select>
				</div>
				<div class="clr"></div>
		    </div>

		    <div class="row-container">
				<label>PH Doctor Name:</label>
				<div>
					<select id="phDoctorIds" name="phDoctorIds" disabled="disabled">
						<option value="">-: Select Prohealth Doctor :-</option>
						<c:forEach var="proHealthDoctor" items="${proHealthDoctorList}">
							<option value="${proHealthDoctor.id}">${proHealthDoctor.name}</option>
						</c:forEach>
					</select>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block" >
					<label>Ticket Number (From):</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input type="text" name="ticketNumberFrom" id="ticketNumberFrom" maxlength="15" class="mid integerOnly" style="width: 90px;" />
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="element-block" >
					<label class="right_lbl">Ticket Number (To):</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input type="text" name="ticketNumberTo" id="ticketNumberTo"  maxlength="15" class="mid integerOnly" style="width: 90px;"  />
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block" >
					<label >Batch Created (From):</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input name="dateCreatedFrom" id="dateCreatedFrom" type="text"  readonly="true" maxlength="50" class="mid" style="width: 90px;" />
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="element-block" >
					<label class="right_lbl">Batch Created (To):</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input name="dateCreatedTo" type="text" id="dateCreatedTo" readonly="true" maxlength="50" class="mid" style="width: 90px;"  />
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block" >
				<label>Created By :</label>
					<div class="select-container1">
						<form:select path="createdBy.id" cssClass="selectbox" id="createdById">
							<form:option value="">-: Select User :-</form:option>
							<form:options items="${userList}" itemValue="id" itemLabel="firstName" />
						</form:select>
					</div>
				</div>
				<div class="element-block" >
					<label class="right_lbl">ERA Check # :</label>
					<div class="input_text">
						<span class="left_curve"></span>
							<form:input path="eraCheckNo" maxlength="50" class="mid" style="width: 90px;"  />
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<label>&nbsp;</label>
				<input type="submit" title="Print Batches" value="Print Batches" onclick="return dateValidationCheck(this.form);" class="login-btn" />
				<input type="reset" title="Cancel" value="Cancel" class="login-btn" onclick="javascript:resetForm(this.form);"/>
				<div class="clr"></div>
			</div>
		</div>
	</form:form>
</div>
<script type="text/javascript">
$(function() {
    $("#dateCreatedFrom").datepicker();
    $("#dateCreatedTo").datepicker();
    $("#dateCreatedFrom").datepicker("setDate", new Date());
    $("#dateCreatedTo").datepicker("setDate", new Date());
});

function checkProHealth(){
	var proHealthGroupId = "<%=Constants.PRO_HEALTH_GROUP_ID%>";

	if ($('#doctorId').val() == proHealthGroupId) {
		$('#phDoctorIds').removeAttr("disabled");
	} else {
		$('#phDoctorIds').val("");
		$('#phDoctorIds').attr("disabled","disabled");
	}

	$.uniform.update();
}

function dateValidationCheck(frm)
{
	if($('#dateCreatedFrom').val() == ""){
		alert("Batch Created(From) cannot be left blank.");
		return false;
	}

	if($('#dateCreatedTo').val() == ""){
		alert("Batch Created(To) cannot be left blank.");
		return false;
	}

	if(null != $('#dateCreatedFrom').val() && null != $('#dateCreatedTo').val()){
		if(Date.parse($('#dateCreatedFrom').val()) > Date.parse($('#dateCreatedTo').val()) ){
			alert("Sorry, the Batch Created(To) date cannot be lesser than the Batch Created(From) date.");
			return false;
		}
	}

	if(parseInt($('#ticketNumberFrom').val()) > parseInt($('#ticketNumberTo').val()) ){
		alert("Sorry, the Ticket Number(To) value cannot be lesser than the Ticket Number(From) value.");
		return false;
	}

}
</script>
<script type="text/javascript" src="<c:url value='/static/resources/js/common.js'/>"></script>
