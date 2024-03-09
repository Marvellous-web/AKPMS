<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li><a href="<c:url value='/chargebatchprocess' />">&nbsp;Charge Batch Processing List</a> &raquo; </li>
			<!-- <li>&nbsp;${operationType} User</li> -->
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<form:form commandName="chargeBatchProcess" id="form1" class="form-container">
		<div class="form-container-inner">

			<div class="row-container">
				<div class="element-block" >
					<label>Charge posting Month</label>
					<div class="select-container2">
						<select id="month" name= "month">
							<option value="">-:Month:-</option>
					    	<c:forEach var="month" items="${months}">
					    		<option value="${month.key}">${month.value}</option>
					    	</c:forEach>
					    </select>
				    </div>
				</div>
				<div class="element-block" >
					<label class="right_lbl">Year</label>
					<div class="select-container2">
						<select id="year" name="year">
							<option value="">-:Year:-</option>
					    	<c:forEach var="year" items="${years}">
					    		<option value="${year}">${year}</option>
					    	</c:forEach>
					    </select>
				    </div>
				</div>
				<div class="clr"></div>
			</div>

		    <div class="row-container">
				<form:label path="type" >Type</form:label>
				<c:forEach var="type" items="${reportTypeList}">
					<input type="checkbox" name="reportType" id="chk_${type}" value="${type.value}"/> ${type.value}&nbsp;&nbsp;&nbsp;
				</c:forEach>
				<div class="clr"></div>
			</div>


		    <div class="row-container">
		    	<form:label path="doctor.id">Group/Doctor Name:</form:label>
		    	<div>
					<form:select path="doctor.id" cssClass="selectbox">
						<form:option value="">-: Select Group/Doctor Name :-</form:option>
						<form:options items="${doctorList}" itemValue="id" itemLabel="name" />
					</form:select>
				</div>
				<div class="clr"></div>
		    </div>

		    <div class="row-container">
				<div class="element-block" >
					<form:label path="dosFrom">Batch Prepared Date From:</form:label>
					<div class="input_text">
						<span class="left_curve"></span>
						<form:input path="dosFrom" readonly="true" maxlength="50" cssClass="mid" cssStyle="width: 90px;" />
						<span class="right_curve"></span>
					</div>
					<form:errors class="invalid" path="dosFrom" />
				</div>
				<div class="element-block" >
					<form:label path="dosTo" cssClass="right_lbl" >To:</form:label>
					<div class="input_text">
						<span class="left_curve"></span>
						<form:input path="dosTo" readonly="true" maxlength="50" cssClass="mid" cssStyle="width: 90px;"  />
						<span class="right_curve"></span>
					</div>
					<form:errors class="invalid" path="dosTo" />
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block" >
					<form:label path="dateReceivedFrom">Batch Received Date From:</form:label>
					<div class="input_text">
						<span class="left_curve"></span>
						<form:input path="dateReceivedFrom" readonly="true" maxlength="50" cssClass="mid" cssStyle="width: 90px;" />
						<span class="right_curve"></span>
					</div>
					<form:errors class="invalid" path="dateReceivedFrom" />
				</div>
				<div class="element-block" >
					<form:label path="dateReceivedTo" cssClass="right_lbl">To:</form:label>
					<div class="input_text">
						<span class="left_curve"></span>
						<form:input path="dateReceivedTo" readonly="true" maxlength="50" cssClass="mid" cssStyle="width: 90px;"  />
						<span class="right_curve"></span>
					</div>
					<form:errors class="invalid" path="dateReceivedTo" />
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<form:label path="receviedBy.id">Received By:</form:label>
				<div>
					<form:select path="receviedBy.id" cssClass="selectbox">
						<form:option value="">-: Select Received By :-</form:option>
						<form:options items="${receivedByUsers}" itemValue="id"	itemLabel="firstName" />
					</form:select>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block" >
					<form:label path="dateBatchPostedFrom">Batch Posted From:</form:label>
					<div class="input_text">
						<span class="left_curve"></span>
						<form:input path="dateBatchPostedFrom" readonly="true" maxlength="50" cssClass="mid" cssStyle="width: 90px;" />
						<span class="right_curve"></span>
					</div>
					<form:errors class="invalid" path="dateBatchPostedFrom" />
				</div>
				<div class="element-block" >
					<form:label path="dateBatchPostedTo" cssClass="right_lbl">To:</form:label>
					<div class="input_text">
						<span class="left_curve"></span>
						<form:input path="dateBatchPostedTo" readonly="true" maxlength="50" cssClass="mid" cssStyle="width: 90px;"  />
						<span class="right_curve"></span>
					</div>
					<form:errors class="invalid" path="dateBatchPostedTo" />
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<form:label path="postedBy.id">Posted By:</form:label>
				<div>
					<form:select path="postedBy.id" cssClass="selectbox">
						<form:option value="">-: Select Posted By :-</form:option>
						<form:options items="${postedByUsers}" itemValue="id" itemLabel="firstName" />
					</form:select>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<label>&nbsp;</label>
				<input type="submit" title="Run Report"	value="Run Report" class="login-btn" onclick="javascript:disabledButtons();this.form.submit();"/>
				<input type="button" title="Cancel" value="Cancel" class="login-btn"  onclick="javascript:resetForm(this.form);" />
				<div class="clr"></div>
			</div>
		</div>

	</form:form>
</div>
<script type="text/javascript">

$(function() {
    $( "#dosFrom" ).datepicker();
    $( "#dosTo" ).datepicker();
    $( "#dateReceivedFrom" ).datepicker();
    $( "#dateReceivedTo" ).datepicker();
    $( "#dateBatchPostedFrom" ).datepicker();
    $( "#dateBatchPostedTo" ).datepicker();
});




</script>
