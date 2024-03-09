<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="downloadAction">
	<c:url value='/flows/checkTracer/fileDownload' />
</c:set>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li><a href="<c:url value='/flows/arProductivity' />">&nbsp;AR Productivity List</a> &raquo;</li>
			<li>&nbsp;${operationType} Request for check tracer Work Flow</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<form:form commandName="checkTracer" id="form1" class="form-container" enctype="multipart/form-data">
		<input type="hidden" name="arProductivity.id" value="${AR_PRODUCTIVITY_ID}">
		<%-- <input type="hidden" value="${arProductivity.id}" name="arProductivity.id" /> --%>
		<input type="hidden" value="${checkTracer.id}" name="id" />

		<div class="form-container-inner">
			<h3>${operationType} Request for check tracer Work Flow</h3>
			<%-- <div class="row-container">
				<div class="element-block">
					<form:label	path="arProductivity.patientName">Patient Name<em class="mandatory">*</em></form:label>${checkTracer.arProductivity.patientName}
				</div>
				<div class="element-block">
					<form:label	path="arProductivity.patientAccNo">Patient ID</form:label>	${checkTracer.arProductivity.patientAccNo}
				</div>
				<div class="clr"></div>
			</div> --%>

			<span class="input-container">
				<label >Patient ID:</label> ${checkTracer.arProductivity.patientAccNo}
			</span>
			<span class="input-container">
				<label >Patient Name:</label> ${checkTracer.arProductivity.patientName}
			</span>
			<span class="input-container">
				<label >Insurance:</label> ${checkTracer.arProductivity.insurance.name}
			</span>
			<span class="input-container">
				<label >Provider (Doctor Office):</label> ${checkTracer.arProductivity.doctor.name}
			</span>
			<span class="input-container">
				<label >Balance Amount:</label> ${checkTracer.arProductivity.balanceAmt}
			</span>
			<span class="input-container">
				<label id="remark">Remarks:</label>	${checkTracer.arProductivity.remark}
			</span>

			<div class="row-container">
				<div class="element-block" style="width: 100%">
					<form:label path="checkMailingAdd">Check mailing address<em	class="mandatory">*</em></form:label>
					<form:textarea rows="5" cols="20" path="checkMailingAdd" readonly="${readOnly}" />
					<form:errors class="invalid" path="checkMailingAdd" />
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block" style="width: 100%">
					<form:label path="checkNo">Check number<em class="mandatory">*</em></form:label>
					<span class="input_text">
						<span class="left_curve"></span>
						<form:input	path="checkNo" maxlength="200" cssClass="mid" cssStyle="width: 373px;" readonly="${readOnly}" />
						<span class="right_curve"></span>
					</span>
					<form:errors class="invalid" path="checkNo" />
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block" style="width: 100%">
					<form:label	path="checkIssueDate">Check issue date :<em	class="mandatory">*</em></form:label>
					<span class="input_text">
						<span class="left_curve"></span>
						<form:input	path="checkIssueDate" cssClass="mid" cssStyle="width: 373px;" readonly="true" />
						<span class="right_curve"></span>
					</span>
					<form:errors class="invalid" path="checkIssueDate" />
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block" style="width: 100%">
					<form:label	path="checkCashedDate">Cashed date :<em	class="mandatory">*</em></form:label>
					<span class="input_text">
						<span class="left_curve"></span>
						<form:input	path="checkCashedDate" cssClass="mid" cssStyle="width: 373px;" readonly="true" />
						<span class="right_curve"></span>
					</span>
					<form:errors class="invalid" path="checkCashedDate" />
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block" style="width: 100%">
					<form:label	path="checkAmount">Check Amount(in USD):<em class="mandatory">*</em></form:label>
					<span class="input_text">
						<span class="left_curve"></span>
						<form:input	path="checkAmount" maxlength="15" cssClass="mid numbersOnly" cssStyle="width: 373px;" readonly="${readOnly}" />
						<span class="right_curve"></span>
					</span>
					<form:errors class="invalid" path="checkAmount" />
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block" style="width: 100%">
					<form:label path="checkAmount">Attachment:<em class="mandatory">*</em></form:label>
					<span class="input_text">
						<form:input	type="file" path="attachment.attachedFile" size="44" cssClass="mid"/>
					</span>
					<form:errors class="invalid" path="attachment.attachedFile" />
				</div>
				<div class="clr"></div>
			</div>

			<c:if test="${not empty checkTracer.id}">
				<div class="row-container">
					<div class="element-block" style="width: 100%">
						<form:label path="checkAmount">Attached File:<em class="mandatory">*</em></form:label>
						<div class="download-container">
							${checkTracer.attachment.name}<a class="download" href="${downloadAction}?id=${checkTracer.attachment.id}" title="Download File"></a>
						</div>
					</div>
				</div>
				<div class="clr"></div>
			</c:if>
			 <br />

			<div class="row-container">
				<div class="element-block" style="width: 100%">
					<label>&nbsp;</label>
					<input type="submit" title="Save / Update Insurance" value="${buttonName}"	class="login-btn" />
					<input type="button" title="Cancel"	value="Cancel" class="login-btn" onclick="javascript:resetForm(this.form);" />
				</div>
				<div class="clr"></div>
			</div>
		</div>
	</form:form>
</div>
<script type="text/javascript">
$(function() {
    $( "#checkIssueDate" ).datepicker();
    $( "#checkCashedDate" ).datepicker();
});
</script>
<script type="text/javascript" src="<c:url value='/static/resources/js/common.js'/>"></script>
