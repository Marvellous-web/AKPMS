<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li><a href="<c:url value='/flows/arProductivity' />">&nbsp;AR
					Productivity List</a> &raquo;</li>
			<li>&nbsp;${operationType} Refund Request Work Flow</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<form:form commandName="refundRequest" id="form1" class="form-container">
		<div class="form-container-inner">
			<h3>${operationType} Refund Request Work Flow</h3>

			<input type="hidden" value="${arProductivity.id}"
				name="arProductivity.id">

				<span class="input-container">
				<form:label path="dataBas">DB: ${refundRequest.arProductivity.dataBas}</form:label>
				</span>

				<span class="input-container">
				<form:label path="patientName">Patient Name: ${refundRequest.arProductivity.patientName}</form:label>
				</span>

				<span class="input-container">
				<form:label path="patientAccNo">Patient Account Number: ${refundRequest.arProductivity.patientAccNo}</form:label>
				</span>

				<span class="input-container">
					<form:label path="responsibleParty">Responsible Party<em class="mandatory">*</em></form:label>
					<span class="input_text">
						<span class="left_curve"></span>
							<form:input path="responsibleParty" maxlength="200" cssClass="mid" cssStyle="width: 373px;" />
						<span class="right_curve"></span>
					</span>
					<span><form:errors class="invalid" path="responsibleParty" /></span>
				</span>
				<span class="input-container">
					<form:label path="totalAmount">Total Amount</form:label>
					<span class="input_text">
						<span class="left_curve"></span>
							<form:input path="totalAmount" maxlength="15" cssClass="mid numbersOnly" cssStyle="width: 373px;" />
						<span class="right_curve"></span>
					</span>
				</span>

				<span class="input-container">
					<form:label path="dos">DOS</form:label>
					<span class="input_text">
						<span class="left_curve"></span>
							<form:input path="dos" maxlength="200" cssClass="mid" cssStyle="width: 373px;" />
						<span class="right_curve"></span>
					</span>
				</span>

				<span class="input-container">
					<form:label path="reason">Reason:</form:label>
					<form:textarea rows="8" cols="20" path="reason" />
			   </span>

				<c:if test="${empty refundRequest.id}">
			<span class="input-container">
				<label>&nbsp;</label>
				<input type="submit" title="Forward to Payment Department"
				value="Forward to Payment Department" class="login-btn" />
			</span>
			</c:if>

			<c:if test="${not empty refundRequest.id}">

			<span class="input-container">
				<label id="remark">Remarks:</label>
			<form:textarea path="remark" cols="20" rows="8"/>
			</span>

			<span class="input-container" id="spanParent"> <form:label
						path="status">Action to be taken:
					</form:label> <span class="select-container" style="display: inline;"> <form:select
							path="status" cssClass="selectbox">
							<form:option value="0">-: Select Action:-</form:option>
							<form:option value="1">Done</form:option>
							<form:option value="2">Reject</form:option>
						</form:select>
				</span> <form:errors class="invalid" path="status" />
				</span>

			<span class="input-container">
				<label>&nbsp;</label>
				<input type="submit" title="Update"
				value="Update" class="login-btn" />
			</span>
			</c:if>
		 </div>
	</form:form>
</div>


<script type="text/javascript">
	jQuery(function ()
		{
		    jQuery(".numbersOnly").keydown(function(event) {
		          // Allow: backspace, delete, tab and escape
		        if ( event.keyCode == 46 || event.keyCode == 8 || event.keyCode == 9 || event.keyCode == 27 ||
		             // Allow: Ctrl+A
		            (event.keyCode == 65 && event.ctrlKey === true) ||
		             // Allow: home, end, left, right
		            (event.keyCode >= 35 && event.keyCode <= 39)) {
		                 // let it happen, don't do anything
		                 return;
		        }
		        else {
		            // Ensure that it is a number and stop the keypress
		            if ( event.shiftKey|| (event.keyCode < 48 || event.keyCode > 57) && (event.keyCode < 96 || event.keyCode > 105 ) && (event.keyCode != 190))
		            {
		       			event.preventDefault();
		            }
		        }

		        if ($(this).val().indexOf('.') > -1) {
		            if (event.keyCode == 190)
		                event.preventDefault();
		         }
		    });
			})

$(function() {
    $( "#checkIssueDate" ).datepicker();
    $( "#checkCashedDate" ).datepicker();
});
</script>
