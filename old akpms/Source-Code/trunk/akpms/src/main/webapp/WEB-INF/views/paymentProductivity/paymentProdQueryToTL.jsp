<%@page import="argus.util.Constants"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li><a href="<c:url value='/paymentproductivitynonera' />">&nbsp;Payment productivity list</a> &raquo; </li>
			<li>&nbsp;${operationType} Query to TL</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<div class="msg">
		<p class="success" id="msgP">${success}</p>
	</div>
	<div class="section-content">
		<h3 class="heading-h3">Payment Batch Details</h3>
		<div class="row-container">
			<div class="element-block" >
				<span class='lbl'>Ticket Number:</span>	<span>${paymentProductivity.paymentBatch.id}</span>
			</div>
			<div class="clr"></div>
		</div>

		<div class="row-container">
			<div class="element-block" >
				<span class='lbl'>Doctor/Group:</span>	<span>
					${paymentProductivity.paymentBatch.doctor.name} 
					<c:if test="${not empty paymentProductivity.paymentBatch.phDoctor}">
						(${paymentProductivity.paymentBatch.phDoctor.name})
					</c:if>
				</span>
			</div>
			<div class="element-block" >
				<span class='lbl'>Insurance Name:</span> <span>${paymentProductivity.paymentBatch.insurance.name}</span>
			</div>
			<div class="clr"></div>
		</div>
		
		<div class="row-container">
			<div class="element-block" >
				<span class='lbl'>Payment Type:</span>	<span>${paymentProductivity.paymentBatch.paymentType.name}</span>
			</div>
			<div class="element-block" >
				<span class='lbl'>Revenue Type:</span> <span>${paymentProductivity.paymentBatch.revenueType.name}</span>
			</div>
			<div class="clr"></div>
		</div>

		<div class="row-container">
			<div class="element-block" >
				<span class='lbl'>Remarks:</span> <span>${paymentProductivity.remark}</span>
			</div>
			<div class="clr"></div>
		</div>
	</div>

	<form:form commandName="paymentProdQueryToTL" id="form1" class="form-container">
		<div class="form-container-inner">
			<h3>${operationType} Query to TL</h3>
			<div class="row-container">
			<div class="element-block">
					<form:label path="chkNumber">Check Number:<em class="mandatory">*</em></form:label>
					<div class="input_text">
						<span class="left_curve"></span>
						<form:input path="chkNumber" maxlength="50" cssClass="mid" cssStyle="width: 100px;" readonly="${readOnly}"/>
						<span class="right_curve"></span>
					</div>
					<form:errors class="invalid" path="chkNumber" />
				</div>
				<div class="element-block">
					<form:label path="chkDate" cssClass="right_lbl">Check Date:<em class="mandatory">*</em></form:label>
					<div class="input_text">
						<span class="left_curve"></span>
						<form:input path="chkDate" cssClass="mid" cssStyle="width: 100px;" readonly="true"/>
						<span class="right_curve"></span>
					</div>
					<form:errors class="invalid" path="chkDate" />
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block">
					<form:label path="patientName">Patient Name:<em class="mandatory">*</em></form:label>
					<div class="input_text">
						<span class="left_curve"></span>
						<form:input path="patientName" maxlength="50" cssClass="mid" cssStyle="width: 100px;"/>
						<span class="right_curve"></span>
					</div>
					<form:errors class="invalid" path="patientName" />
				</div>
				<div class="element-block">
					<form:label path="amount"  cssClass="right_lbl">Amount (in USD):<em class="mandatory">*</em></form:label>
					<div class="input_text">
						<span class="left_curve"></span>
						<form:input path="amount" maxlength="15" cssClass="mid numbersOnly" cssStyle="width: 100px;" readonly="${readOnly}"/>
						<span class="right_curve"></span>
					</div>
					<form:errors class="invalid" path="amount" />
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block">
					<form:label path="accountNumber">Account Number:<em class="mandatory">*</em></form:label>
					<div class="input_text">
						<span class="left_curve"></span>
						<form:input path="accountNumber" maxlength="50" cssClass="mid" cssStyle="width: 100px;" readonly="${readOnly}"/>
						<span class="right_curve"></span>
					</div>
					<form:errors class="invalid" path="accountNumber" />
				</div>
				<div class="clr"></div>
			</div>

			<c:if test="${not empty  paymentProdQueryToTL.id}">
				<div class="row-container">
					<form:label path="tlRemark">TL Remark:</form:label>
					<form:textarea rows="5" cols="20" path="tlRemark" readonly="${readOnly}" />
					<form:errors class="invalid" path="tlRemark" />
					<div class="clr"></div>
				</div>
			</c:if>

			<br/>
			<span class="input-container">
				<label>&nbsp;</label>
				<input type="submit" id="btnUpdate" value="Save" class="login-btn" />
				<input type="button" title="Cancel" value="Cancel" class="login-btn"  onclick="javascript:resetForm(this.form);" />
			</span>
		</div>
	</form:form>
</div>


<script type="text/javascript">
$(function() {
    $( "#chkDate" ).datepicker();
});
</script>
<c:if test="${not empty  paymentProdQueryToTL.id}">
<script type="text/javascript">
	$('#btnUpdate').val('Update');
</script>
</c:if>
<script type="text/javascript"	src="<c:url value='/static/resources/js/common.js'/>"></script>
