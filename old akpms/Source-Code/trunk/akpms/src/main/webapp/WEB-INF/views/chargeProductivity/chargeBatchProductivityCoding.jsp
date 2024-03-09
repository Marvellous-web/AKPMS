<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="row-container">
	<div class="element-block">
		<form:label path="t1">Number Of Accounts:</form:label>
		<div class="input_text">
			<span class="left_curve"></span>
			<form:input path="t1" cssClass="mid integerOnly" maxlength="5" cssStyle="width: 100px;" />
			<span class="right_curve"></span>
		</div>
		<form:errors cssClass="invalid" path="t1" />
	</div>
	<div class="element-block">
		<form:label path="t2">Number Of ICDs:</form:label>
		<div class="input_text">
			<span class="left_curve"></span>
			<form:input path="t2" cssClass="mid integerOnly" maxlength="5" cssStyle="width: 100px;" />
			<span class="right_curve"></span>
		</div>
		<form:errors cssClass="invalid" path="t2" />
	</div>		
	<div class="clr"></div>
</div>

<div class="row-container">
	<div class="element-block">
		<form:label path="t3">Number Of CPTs:</form:label>
		<div class="input_text">
			<span class="left_curve"></span>
			<form:input path="t3" cssClass="mid integerOnly" maxlength="5" cssStyle="width: 100px;" />
			<span class="right_curve"></span>
		</div>
		<form:errors cssClass="invalid" path="t3" />
	</div>
	<div class="clr"></div>
</div>