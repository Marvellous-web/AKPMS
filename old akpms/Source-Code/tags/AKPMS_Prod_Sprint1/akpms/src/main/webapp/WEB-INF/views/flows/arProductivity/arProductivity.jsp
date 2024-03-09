<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li><a href="<c:url value='/flows/arProductivity' />">&nbsp;AR Productivity List</a>
				&raquo;</li>
			<li>&nbsp;${operationType} AR Productivity</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<form:form commandName="arProductivity" id="form1" class="form-container">
		<div class="form-container-inner">
			<h3>${operationType} AR Productivity</h3>
			<span class="input-container">
				<form:label path="patientAccNo">Patient Acc. No.<em class="mandatory">*</em></form:label>
				<span class="input_text">
					<span class="left_curve"></span>
						<form:input path="patientAccNo" maxlength="200" cssClass="mid" cssStyle="width: 373px;"  />
					<span class="right_curve"></span>
				</span>
				<form:errors class="invalid" path="patientAccNo" />
			</span>
					<span class="input-container">
				<form:label path="patientName">Patient Name<em class="mandatory">*</em></form:label>
				<span class="input_text">
					<span class="left_curve"></span>
						<form:input path="patientName" maxlength="200" cssClass="mid" cssStyle="width: 373px;"  />
					<span class="right_curve"></span>
				</span>
				<form:errors class="invalid" path="patientName" />
			</span>

			<span class="input-container" id="spanParent" >
				<form:label path="insurance.id">Insurance:<em class="mandatory">*</em></form:label>
				<span class="select-container" style="display:inline;">
					<form:select path="insurance.id" cssClass="selectbox">
						<form:option value="0">-: Select Insurance :-</form:option>
						<form:options items="${insuranceList}" itemValue="id"
							itemLabel="name" />
					</form:select>
				</span>
				<form:errors class="invalid" path="insurance.id" />
			</span>


			<span class="input-container">
				<form:label path="dos">DOS:<em class="mandatory">*</em></form:label>
				<span class="input_text">
					<span class="left_curve"></span>
					<form:input path="dos" maxlength="255" cssClass="mid" cssStyle="width: 373px;" readonly="true"/>
					<span class="right_curve"></span>
				</span>
				<form:errors class="invalid" path="dos" />
			</span>


			<span class="input-container">
				<form:label path="cpt">CPT:<em class="mandatory">*</em></form:label>
				<span class="input_text">
					<span class="left_curve"></span>
					<form:input path="cpt" maxlength="255" cssClass="mid" cssStyle="width: 373px;" />
					<span class="right_curve"></span>
				</span>
				<form:errors class="invalid" path="cpt" />
			</span>

			<span class="input-container" id="spanParent" >
				<form:label path="doctor.id">Provider (Doctor Office):<em class="mandatory">*</em></form:label>
				<span class="select-container" style="display:inline;">
					<form:select path="doctor.id" cssClass="selectbox">
						<form:option value="0">-: Select Provider (Doctor Office) :-</form:option>
						<form:options items="${doctorList}" itemValue="id"
							itemLabel="name" />
					</form:select>
				</span>
				<form:errors class="invalid" path="doctor.id" />
			</span>

			<span class="input-container">
				<form:label path="dataBas">Database:<em class="mandatory">*</em></form:label>
				<span class="input_text">
					<span class="left_curve"></span>
					<form:input path="dataBas" maxlength="255" cssClass="mid" cssStyle="width: 373px;" />
					<span class="right_curve"></span>
				</span>
				<form:errors class="invalid" path="dataBas" />
			</span>


			<span class="input-container">
				<form:label path="balanceAmt">Balance Amount:<em class="mandatory">*</em></form:label>
				<span class="input_text">
					<span class="left_curve"></span>
					<form:input path="balanceAmt" maxlength="15" cssClass="mid numbersOnly" cssStyle="width: 373px;" />
					<span class="right_curve"></span>
				</span>
				<form:errors class="invalid" path="balanceAmt" />
			</span>

			<span class="input-container">
				<form:label path="remark">Remark:<em class="mandatory">*</em></form:label>
				<form:textarea rows="8" cols="20" path="remark" />
				<form:errors class="invalid" path="remark" />
			</span>

			<span class="input-container" id="spanParent" >
				<form:label path="source">Source:<em class="mandatory">*</em></form:label>
				<span class="select-container" style="display:inline;">
					<form:select path="source" cssClass="selectbox">
						<form:option value="0">-: Select Source :-</form:option>
						<form:option value="1">Payer Edit</form:option>
						<form:option value="2">Unpaid / Inactive</form:option>
						<form:option value="3">Correspondence</form:option>
						<form:option value="4">Divider / Set to deny</form:option>
						<form:option value="5">IPA -FFS</form:option>
						<form:option value="6">Missing Info.</form:option>
						<form:option value="7">Hourly</form:option>
					</form:select>
				</span>
				<form:errors class="invalid" path="source" />
			</span>


			<span class="input-container" id="spanParent" >
				<form:label path="workFlow">Work Flow:<em class="mandatory">*</em></form:label>
				<span class="select-container" style="display:inline;">
					<form:select path="workFlow" cssClass="selectbox" disabled="${readOnly}">
						<form:option value="0">-: Select Work flow :-</form:option>
						<form:option value="1">Adjustment log</form:option>
						<form:option value="2">Coding correction</form:option>
						<form:option value="3">Second request log</form:option>
						<form:option value="4">Re-Key request to charge posting</form:option>
						<form:option value="5">Payment posting log</form:option>
						<form:option value="6">Request for check tracer</form:option>
						<form:option value="7">Query to TL</form:option>
						<form:option value="8">Refund Request</form:option>
					</form:select>
				</span>
				<form:errors class="invalid" path="workFlow" />
			</span>

			<br/>
			<br/>
			<span class="input-container"> <label>&nbsp;</label>
				<input type="submit" title="Save / Update Insurance"
				value="${buttonName}" class="login-btn" /> <input type="button"
				title="Cancel" value="Cancel" class="login-btn"  onclick="javascript:resetForm(this.form);" /> 
			</span>
		</div>
	</form:form>
</div>


<script type="text/javascript">
	$(function() {
	    $( "#dos" ).datepicker();
	});
	
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

</script>
