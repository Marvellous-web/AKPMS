<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li><a href="<c:url value='/admin/revenuetype' />">&nbsp;Revenue Type List</a>
				&raquo;</li>
			<li>&nbsp;${operationType} Revenue Type</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<form:form commandName="revenuetype" id="form1" class="form-container">
		<div class="form-container-inner">
			<h3>${operationType} Revenue</h3>
			<span class="input-container">
				<form:label path="name">Name<em class="mandatory">*</em></form:label>
				<span class="input_text">
					<span class="left_curve"></span>
						<form:input path="name" maxlength="200" cssClass="mid" cssStyle="width: 373px;"  />
					<span class="right_curve"></span>
				</span>
				<form:errors cssClass="invalid" path="name" />
			</span>
			
			<span class="input-container">
				<form:label path="name">Code<em class="mandatory">*</em></form:label>
				<span class="input_text">
					<span class="left_curve"></span>
						<form:input path="code" maxlength="200" cssClass="mid integerOnly" cssStyle="width: 373px;"  />
					<span class="right_curve"></span>
				</span>
				<form:errors cssClass="invalid" path="code" />
			</span>
			
			<span class="input-container">
				<form:label path="desc">Description:</form:label>
				<form:textarea rows="8" cols="20" path="desc" />
			</span>
			
			<span class="input-container" id="accountingContainer">
				<form:label path="accounting">Accounting</form:label>
				<span class="input_text">
					<span class="left_curve"></span>
						<form:input path="accounting" maxlength="5" cssClass="mid numbersOnly" cssStyle="width: 60px;"  onblur="calculatePercantage();"/>
					<span class="right_curve"></span>
				</span>
				<form:errors cssClass="invalid" path="accounting" />
			</span>

			<span class="input-container" id="paymentsContainer">
				<form:label path="payments">Payments</form:label>
				<span class="input_text">
					<span class="left_curve"></span>
						<form:input path="payments" maxlength="5" cssClass="mid numbersOnly" cssStyle="width: 60px;"  onblur="calculatePercantage();"/>
					<span class="right_curve"></span>
				</span>
				<form:errors cssClass="invalid" path="payments" />
			</span>

			<span class="input-container" id="operationsContainer">
				<form:label path="operations">Operations</form:label>
				<span class="input_text">
					<span class="left_curve"></span>
						<form:input path="operations" maxlength="5" cssClass="mid numbersOnly" cssStyle="width: 60px;"  onblur="calculatePercantage();"/>
					<span class="right_curve"></span>
				</span>
				<form:errors cssClass="invalid" path="operations" />
			</span>

			<span class="input-container" id="percentageContainer">
				<label>Percentage</label>
				<span id="percentage"></span>
			</span>

			<span class="input-container" style="display:none">
				<form:label path="Status">Status:<em class="mandatory">*</em></form:label>
				<form:radiobutton path="status" value="1" />Active
				<form:radiobutton path="status" value="0" />Inactive
			</span>
			<br/>
			<br/>
			<span class="input-container"> <label>&nbsp;</label>
				<input type="submit" title="Save / Update Revenue" value="${buttonName}" class="login-btn" /> 
				<input type="button" title="Cancel" value="Cancel" class="login-btn"  onclick="javascript:resetForm(this.form);" /> 
			</span>
		</div>
	</form:form>
</div>
<script type="text/javascript" src="<c:url value='/static/resources/js/common.js'/>"></script>
<script type="text/javascript">
function calculatePercantage(){
	
	//$("#percentage").text( parseFloat(parseFloat($("#accounting").val()) + parseFloat($("#payments").val()) + parseFloat($("#operations").val())).toFixed(2) );
	$("#percentage").text( addFloats($("#accounting").val(), $("#payments").val(), $("#operations").val()));
}
calculatePercantage();
</script>