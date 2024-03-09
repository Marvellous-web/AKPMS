<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="row-container">
	<div class="element-block">
		<form:label path="t1">New Patient Count:</form:label>
		<div class="input_text">
			<span class="left_curve"></span>
			<form:input path="t1" cssClass="mid integerOnly" maxlength="5"  cssStyle="width: 100px;" onkeypress="javascript:calculatePatient();" onblur="javascript:calculatePatient();" />
			<span class="right_curve"></span>
		</div>
		<form:errors cssClass="invalid" path="t1" />
	</div>
	<div class="element-block">
		<form:label path="t2">Existing Patient Count:</form:label>
		<div class="input_text">
			<span class="left_curve"></span>
			<form:input path="t2" cssClass="mid integerOnly" maxlength="5" cssStyle="width: 100px;"  onkeypress="javascript:calculatePatient();" onblur="javascript:calculatePatient();"  />
			<span class="right_curve"></span>
		</div>
		<form:errors cssClass="invalid" path="t2" />
	</div>	
	<div class="clr"></div>
</div>
<div class="row-container">
	<div class="element-block">
		<label>Total Patients:</label>
		<span id="t1t2">0</span>
	</div>
	<div class="clr"></div>
</div>

<form:hidden path="t3"/>

<script type="text/javascript">
<!--
function calculatePatient(){
	var demoNewCustomer = '${demoNewCustomer}';
	var demoExistingCustomer = '${demoExistingCustomer}';
	
	var t1 = $("#t1").val();
	var t2 = $("#t2").val();
	
	t1 = t1 || 0;
	t2 = t2 || 0;
	
	$("#t1t2").text(addIntegers(t1,t2));

	$("#time").val(addFloats((t1 * demoNewCustomer) , (t2*demoExistingCustomer)));	
}
//-->
calculatePatient();
</script> 