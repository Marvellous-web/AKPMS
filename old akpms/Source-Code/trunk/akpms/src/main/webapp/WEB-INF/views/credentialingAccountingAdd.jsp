<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="dashboard-container">
<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li><a href="<c:url value='/credentialingaccountingproductivity' />">&nbsp;Credentialing and Accounting Productivity List</a>
				&raquo;</li>
			<li>&nbsp;${operationType} Credentialing and Accounting Productivity</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->

	<form:form commandName="credentialingAccounting" id="form1" class="form-container">
		<div class="form-container-inner">
			<h3>${operationType} Credentialing and Accounting Productivity</h3>
			<span class="input-container">
				<form:label path="dateRecd">Date Received<em class="mandatory">*</em></form:label>
				<span class="input_text">
					<span class="left_curve"></span>
						<form:input path="dateRecd"  cssClass="mid" cssStyle="width: 100px;"  readonly="true"/>
					<span class="right_curve"></span>
				</span>
				<form:errors class="invalid" path="dateRecd" />
			</span>
			<span class="input-container">
				<form:label path="credentialingTask">Credentiling Task<em class="mandatory">*</em></form:label>
				<span class="input_text">
					<span class="left_curve"></span>
						<form:input path="credentialingTask" maxlength="200" cssClass="mid" cssStyle="width: 373px;"  />
					<span class="right_curve"></span>
				</span>
				<form:errors class="invalid" path="credentialingTask" />
			</span>
			<span class="input-container">
				<form:label path="taskCompleted">Task Completed<em class="mandatory">*</em></form:label>
				<span class="input_text">
					<span class="left_curve"></span>
						<form:input path="taskCompleted"  cssClass="mid" cssStyle="width: 100px;" readonly="true" />
					<span class="right_curve"></span>
				</span>
				<form:errors class="invalid" path="taskCompleted" />
			</span>
			<span class="input-container">
				<form:label path="time">Time Taken</form:label>
				<span class="input_text">
					<span class="left_curve"></span>
						<form:input path="time" maxlength="8" cssClass="mid integerOnly" cssStyle="width: 100px;"  />
					<span class="right_curve"></span>
				</span>
				
			</span>
			<span class="input-container">
				<form:label path="remark">Remarks</form:label>
				<form:textarea rows="8" cols="20" path="remark" />
			</span>

			
			<span class="input-container"> <label>&nbsp;</label>
				<input type="submit" title="Save / Update"
				value="${buttonName}" class="login-btn" /> <input type="button"
				title="Cancel" value="Cancel" class="login-btn"  onclick="javascript:resetForm(this.form);" /> 
			</span>
		</div>
	</form:form>
</div>


<script type="text/javascript">
	$(function() {
	    $( "#dateRecd" ).datepicker();
	});

	$(function() {
	    $( "#taskCompleted" ).datepicker();
	});
	
	function resetForms(){
		//$('div[id|="uniform"]').find('span').removeClass("checked");
		$("#clear").click(function() {
		    $("input[class='mid']").val("");
		});

		$("#reset").click(function() {
			alert($('input:radio[name=bar]:checked').val());
			//$('.old-value').text($('input:radio[name=bar]:checked').val());
			//$('.click').click(function() {
		   //     $('.old-value').text( $('input').val() );
		   // });
		});
	}

	</script>
<script type="text/javascript" src="<c:url value='/static/resources/js/common.js'/>"></script>