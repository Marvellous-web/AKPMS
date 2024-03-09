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
			<li><a href="<c:url value='/flows/arProductivity' />">&nbsp;AR
					Productivity List</a> &raquo;</li>
			<li>&nbsp;${operationType} Request for check tracer Work Flow</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<form:form commandName="checkTracer" id="form1" class="form-container" enctype="multipart/form-data">
		<div class="form-container-inner">
			<h3>${operationType} Request for check tracer Work Flow</h3>

			<input type="hidden" value="${arProductivity.id}"
				name="arProductivity.id"> <span class="input-container">
			<div class="row-container">
					<div class="element-block">
						<span class="input-container"> <form:label
								path="arProductivity.patientName">Patient Name<em
									class="mandatory">*</em>
							</form:label>${checkTracer.arProductivity.patientName}
						</span>
					</div>
					<div class="element-block">
						<span class="input-container"> <form:label
								path="arProductivity.patientAccNo">Patient Account No.</form:label>
							${checkTracer.arProductivity.patientAccNo}
						</span>
					</div>
					<div class="clr"></div>
				</div> <form:label path="checkMailingAdd">Check mailing address<em
						class="mandatory">*</em>
				</form:label> <form:textarea rows="8" cols="20" path="checkMailingAdd"
					readonly="${readOnly}" /> <form:errors class="invalid"
					path="checkMailingAdd" />
			</span> <span class="input-container"> <form:label path="checkNo">Check number<em
						class="mandatory">*</em>
				</form:label> <span class="input_text"> <span class="left_curve"></span> <form:input
						path="checkNo" maxlength="200" cssClass="mid"
						cssStyle="width: 373px;" readonly="${readOnly}" /> <span
					class="right_curve"></span>
			</span> <form:errors class="invalid" path="checkNo" />
			</span> <span class="input-container"> <form:label
					path="checkIssueDate">Check issue date :<em
						class="mandatory">*</em>
				</form:label> <span class="input_text"> <span class="left_curve"></span> <form:input
						path="checkIssueDate" maxlength="255" cssClass="mid"
						cssStyle="width: 373px;" readonly="true" /> <span
					class="right_curve"></span>
			</span> <form:errors class="invalid" path="checkIssueDate" />
			</span> <span class="input-container"> <form:label
					path="checkCashedDate">Cashed date :<em
						class="mandatory">*</em>
				</form:label> <span class="input_text"> <span class="left_curve"></span> <form:input
						path="checkCashedDate" maxlength="255" cssClass="mid"
						cssStyle="width: 373px;" readonly="true" /> <span
					class="right_curve"></span>
			</span> <form:errors class="invalid" path="checkCashedDate" />
			</span> <span class="input-container"> <form:label
					path="checkAmount">Check Amount(in USD):<em class="mandatory">*</em>
				</form:label> <span class="input_text"><span class="left_curve"></span> <form:input
						path="checkAmount" maxlength="15" cssClass="mid numbersOnly"
						cssStyle="width: 373px;" readonly="${readOnly}" /> <span
					class="right_curve"></span> </span> <form:errors class="invalid"
					path="checkAmount" /></span>
			<span class="input-container">
				<form:label path="checkAmount">Attachment:<em class="mandatory">*</em></form:label>
						<span class="input_text">
							<form:input	type="file" path="attachment.attachedFile" size="44" cssClass="mid"/>
						</span>
						<form:errors class="invalid" path="attachment.attachedFile" />
			</span>
			<c:if test="${not empty checkTracer.id}">
			<form:label path="checkAmount">Attached File:<em class="mandatory">*</em></form:label>
			<div class="download-container">
			${checkTracer.attachment.name}<a class="download" href="${downloadAction}?id=${checkTracer.attachment.id}" title="Download File"></a>
			</div>
			</c:if>
			 <br /> <br />

				<span class="input-container"> <label>&nbsp;</label> <input
					type="submit" title="Save / Update Insurance" value="${buttonName}"
					class="login-btn" /> <input type="button" title="Cancel"
					value="Cancel" class="login-btn"
					onclick="javascript:resetForm(this.form);" />
				</span>
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
