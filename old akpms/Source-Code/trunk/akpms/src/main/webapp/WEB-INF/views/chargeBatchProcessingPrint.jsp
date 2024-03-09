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
			<li>&nbsp; Print Charge Batch</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<form:form commandName="chargeBatchProcess" id="form1" class="form-container" target="_blank">
		<div class="form-container-inner">
		<div id="msg" class="error">${warning}</div>
			<div class="row-container">
				<div class="element-block" >
					<label>Charge posting Month:</label>
					<div class="select-container2">
						<select id="month" name= "month">
							<option value="0">-:Month:-</option>
					    	<c:forEach var="month" items="${months}">
					    		<option value="${month.key}">${month.value}</option>
					    	</c:forEach>
					    </select>
				    </div>
				</div>
				<div class="element-block" >
					<label class="right_lbl">Year:</label>
					<div class="select-container2">
						<select id="year" name="year">
							<option value="0">-:Year:-</option>
					    	<c:forEach var="year" items="${years}">
					    		<option value="${year}">${year}</option>
					    	</c:forEach>
					    </select>
				    </div>
				</div>
				<div class="clr"></div>
			</div>

		    <div class="row-container">
		    	<form:label path="doctor.id">Group/Doctor Name:</form:label>
		    	<div>
					<form:select path="doctor.id" cssClass="selectbox" id="doctorId">
						<form:option value="">-: Select Group/Doctor Name :-</form:option>
						<form:options items="${doctorList}" itemValue="id" itemLabel="name" />
					</form:select>
				</div>
				<div class="clr"></div>
		    </div>

			<div class="row-container">
				<div class="element-block" >
					<form:label path="dateReceivedFrom">Batch Received Date From:<em class="mandatory">*</em></form:label>
					<div class="input_text">
						<span class="left_curve"></span>
						<form:input path="dateReceivedFrom" readonly="true" cssClass="mid" cssStyle="width: 90px;" />
						<span class="right_curve"></span>
					</div>
					<form:errors class="invalid" path="dateReceivedFrom" />
				</div>
				<div class="element-block" >
					<form:label path="dateReceivedTo" cssClass="right_lbl">To:<em class="mandatory">*</em></form:label>
					<div class="input_text">
						<span class="left_curve"></span>
						<form:input path="dateReceivedTo" readonly="true"  cssClass="mid" cssStyle="width: 90px;"  />
						<span class="right_curve"></span>
					</div>
					<form:errors class="invalid" path="dateReceivedTo" />
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<form:label path="receivedBy.id">Received By:</form:label>
				<div>
					<form:select path="receivedBy.id" cssClass="selectbox" id="receivedById">
						<form:option value="">-: Select Received By :-</form:option>
						<form:options items="${receivedByUsers}" itemValue="id"	itemLabel="firstName" />
					</form:select>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block" >
					<label>Ticket Number From:</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input type="text" name="ticketFrom" id="ticketFrom" maxlength="7" class="mid integerOnly" style="width: 90px;" />
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="element-block" >
					<label class="right_lbl">To:</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input type="text" name="ticketTo" id="ticketTo" maxlength="7" class="mid integerOnly" style="width: 90px;"  />
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<label>&nbsp;</label><!--this.form.submit();  -->
				<input type="submit" title="Print Batches"	value="Print Batches" class="login-btn" onclick="return dateValidationCheck(this.form);"/>
				<input type="button" title="Cancel" value="Cancel" class="login-btn"  onclick="javascript:resetForm(this.form);" />
				<div class="clr"></div>
			</div>
		</div>

	</form:form>
</div>
<script type="text/javascript">

	$(function() {
	    /* $( "#dosFrom" ).datepicker();
	    $( "#dosTo" ).datepicker(); */
	    $( "#dateReceivedFrom" ).datepicker();
	    $( "#dateReceivedTo" ).datepicker();
	    $("#dateReceivedFrom").datepicker("setDate", new Date());
	    $("#dateReceivedTo" ).datepicker("setDate", new Date());

	});

	function dateValidationCheck(frm)
	{
		if($('#dateReceivedFrom').val() == ""){
			alert("Batch Received(From) cannot be left blank.");
			return false;
		}

		if($('#dateReceivedTo').val() == ""){
			alert("Batch Received(To) cannot be left blank.");
			return false;
		}

		if(null != $('#dateReceivedFrom').val() && null != $('#dateReceivedTo').val()){
			if(Date.parse($('#dateReceivedFrom').val()) > Date.parse($('#dateReceivedTo').val()) ){
				alert("Sorry, the Batch Received(To) date cannot be lesser than the Batch Received(From) date.");
				return false;
			}
		}

		if(parseInt($('#ticketFrom').val()) > parseInt($('#ticketTo').val()) ){
			alert("Sorry, the Ticket(To) value cannot be lesser than the Ticket(From) value.");
			return false;
		}
	}

</script>
<script type="text/javascript" src="<c:url value='/static/resources/js/common.js'/>"></script>
