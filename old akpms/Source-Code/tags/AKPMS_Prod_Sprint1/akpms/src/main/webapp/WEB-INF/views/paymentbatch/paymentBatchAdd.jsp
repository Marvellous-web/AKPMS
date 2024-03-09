<%@page import="argus.util.Constants"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li><a href="<c:url value='/paymentbatch' />">&nbsp;Payment	Batch List</a> &raquo;</li>
			<li>&nbsp;${operationType} Payment Batch</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<div class="msg">
		<p class="success" id="msgP">${success}</p>
	</div>
	<form:form commandName="paymentBatch" id="form1" class="form-container">
		<div class="form-container-inner">
			<h3>${operationType} Payment Batch</h3>

			<div class="row-container">
				<div class="element-block">
					<form:label path="billingMonth">Billing Month:<em class="mandatory">*</em></form:label>
					<div class="select-container2">
						<form:select path="billingMonth" cssClass="selectbox">
							<form:option value="0">-:Month:-</form:option>
							<c:forEach var="month" items="${months}">
								<form:option value="${month.key}">${month.value}</form:option>
							</c:forEach>
						</form:select>
						<form:errors class="invalid" path="billingMonth" />
					</div>
				</div>
				<div class="element-block">
					<form:label path="billingYear">Billing Year:<em class="mandatory">*</em></form:label>
					<div class="select-container2">
						<form:select path="billingYear" cssClass="selectbox">
							<form:option value="0">-:Year:-</form:option>
							<c:forEach var="year" items="${years}">
								<form:option value="${year}">${year}</form:option>
							</c:forEach>
						</form:select>
						<form:errors class="invalid" path="billingYear" />
					</div>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<form:label path="doctor.id">Group/Doctor Name:<em class="mandatory">*</em></form:label>
				<div>
					<form:select id="doctorId" path="doctor.id" cssClass="selectbox"
						onchange="javascript:showHideElements();">
						<form:option value="0">-: Select Group/Doctor :-</form:option>
						<form:options items="${doctorList}" itemValue="id"
							itemLabel="name" />
					</form:select>
				</div>
				<form:errors class="invalid" path="doctor.id" />
				<div class="clr"></div>
			</div>

			<div class="row-container" id="divPhDoctors" style="display: none;">
				<form:label path="phDoctorList">PH Doctors:</form:label>
				<div>
					<form:select path="phDoctorList.id" cssClass="selectbox">
						<form:options items="${proHealthDoctorList}" itemValue="id"	itemLabel="name" />
					</form:select>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<form:label path="paymentType.id">Payment Type:<em class="mandatory">*</em></form:label>
				<div>
					<form:select id="paymentTypeId" path="paymentType.id" cssClass="selectbox" onchange="showHideElements();checkOffset();">
						<form:option value="0">-: Select Payment Type :-</form:option>
						<form:options items="${paymentTypeList}" itemValue="id"
							itemLabel="name" />
					</form:select>
				</div>
				<form:errors class="invalid" path="paymentType.id" />
				<div class="clr"></div>
			</div>

			<div id="adminIncome" class="row-container" style="display: none;">
				<form:label path="adminIncome.id">Admin Income:</form:label>
				<div>
					<form:select path="adminIncome.id" cssClass="selectbox" id="adminIncomeId">
						<form:options items="${adminIncomes}" itemValue="id" itemLabel="name" />
					</form:select>
				</div>
				<form:errors class="invalid" path="adminIncome.id" />
				<div class="clr"></div>
			</div>

			<div id="divRevenueType" class="row-container" style="display: none;">
				<form:label path="revenueType.id">Revenue Type:</form:label>
				<div>
					<form:select path="revenueType.id" cssClass="selectbox"
						id="revenueTypeId">
						<form:options items="${revenueTypes}" itemValue="id"
							itemLabel="name" />
					</form:select>
				</div>
				<form:errors class="invalid" path="revenueType.id" />
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<form:label path="insurance.id">Insurance:<em
						class="mandatory">*</em>
				</form:label>
				<div>
					<form:select path="insurance.id" cssClass="selectbox"
						id="insurance">
						<form:option value="0">-: Select Insurance :-</form:option>
						<form:options items="${insuranceList}" itemValue="id"
							itemLabel="name" />
					</form:select>
				</div>
				<form:errors class="invalid" path="insurance.id" />
				<div class="clr"></div>
			</div>

			<span class="input-container" id="source">
				<label>&nbsp;</label>
				<form:checkbox path="moneySource" id="moneySource" value="true" onclick="javascript:showHideMoneySource(this.checked);" />Fill Money Source
			</span>

			<div class="row-container" id="divMoneySources" style="display: none;">
				<form:label path="paymentBatchMoneySources">Money Source:</form:label>
				<table style='width:70%'>
					<tr>
						<c:forEach var="paymentBatchMoneySources" items="${paymentBatchMoneySources}" varStatus="i">
							<td>
								<div class="input_text">
									<em style="float: left; padding-left: 5px;">${paymentBatchMoneySources.moneySource.name}</em>
									<br />
									<span class="left_curve"></span>
										<form:input path="paymentBatchMoneySources[${i.index }].amount"
											id="paymentBatchMoneySources[${i.index }].amount"
											class="mid numbersOnly" style="width:50px;"
											onkeyup="javascript:totalDepostAmt();"
											onchange="javascript:totalDepostAmt();"
											onblur="javascript:totalDepostAmt();"
											 maxlength="15"/>
									<span class="right_curve"></span>
									<input type="hidden" name="paymentBatchMoneySources[${i.index }].moneySource.id"  value="${paymentBatchMoneySources.moneySource.id}" />
								</div>
							</td>
							<c:if test="${(i.index+1) % 3 eq 0}">
								</tr><tr>
							</c:if>
						</c:forEach>
					</tr>
				</table>
				<div class="clr"></div>
			</div>
			<div class="row-container">
				<div class="element-block">
					<form:label path="depositAmount">Deposit Amount:<br/>(in USD)
					</form:label>
					<div class="input_text">
						<span class="left_curve"></span>
						<form:input path="depositAmount" maxlength="15"
							cssClass="mid numbersOnly" cssStyle="width: 100px;"
							readonly="${readOnly}" />
						<span class="right_curve"></span>
					</div>
					<form:errors class="invalid" path="depositAmount" />
				</div>
				<div class="element-block">
					<form:label path="ndba" class="label-txt-inputtxt">&nbsp;&nbsp; NDBA:<br/>(in USD)
					</form:label>
					<div class="input_text">
						<span class="left_curve"></span>
						<form:input path="ndba" maxlength="15" cssClass="mid numbersOnly"
							cssStyle="width: 100px;" readonly="${readOnly}" />
						<span class="right_curve"></span>
					</div>
					<form:errors class="invalid" path="ndba" />
				</div>
				<span class="invalid" id="amt_ndba_error"></span>
				<div class="clr"></div>
			</div>
			<div class="row-container">
				<div class="element-block">
					<form:label path="depositDate">Deposit Date:<em
							class="mandatory">*</em>
					</form:label>
					<div class="input_text">
						<span class="left_curve"></span>
						<form:input path="depositDate" maxlength="50" cssClass="mid"
							cssStyle="width: 100px;" readonly="true" />
						<span class="right_curve"></span>
					</div>
					<form:errors class="invalid" path="depositDate" />
				</div>
				<div class="element-block">
					<form:label path="eraCheckNo">ERA-Check #:</form:label>
					<div class="input_text">
						<span class="left_curve"></span>
						<form:input path="eraCheckNo" maxlength="50" cssClass="mid"
							cssStyle="width: 100px;" readonly="${readOnly}" />
						<span class="right_curve"></span>
					</div>
					<form:errors class="invalid" path="eraCheckNo" />
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<form:label path="comment">Comment:</form:label>
				<form:textarea rows="5" cols="20" path="comment"
					readonly="${readOnly}" />
				<form:errors class="invalid" path="comment" />
				<div class="clr"></div>
			</div>

			<!-- agency money, old prior ar and other income move to update page:BS -->

			<div class="row-container">
				<div class="element-block">
					<form:label path="refundChk">Refund Chk:<br/> (in USD)</form:label>
					<div class="input_text">
						<span class="left_curve"></span>
						<form:input path="refundChk" maxlength="15"
							cssClass="mid numbersOnly" cssStyle="width: 100px;"
							readonly="${readOnly}" />
						<span class="right_curve"></span>
					</div>
					<form:errors class="invalid" path="refundChk" />
				</div>
				<div class="element-block">
					<form:label path="nsfSysRef">NSF/Sys Ref:<br /> (in USD)</form:label>
					<div class="input_text">
						<span class="left_curve"></span>
						<form:input path="nsfSysRef" maxlength="15"
							cssClass="mid numbersOnly" cssStyle="width: 100px;"
							readonly="${readOnly}" />
						<span class="right_curve"></span>
					</div>
					<form:errors class="invalid" path="refundChk" />
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block" style="width: 100%" >
					<form:label path="offsetMinus">Offset:</form:label>
					<div class="input_text">
						<b style="float:left; padding-top:7px;">+</b>
						<span class="left_curve"></span>
						<form:input path="offsetMinus" maxlength="15" cssClass="mid numbersOnly" cssStyle="width: 50px;" onkeyup="checkOffsetAmount();" onblur="checkOffsetAmount();" />
						<span class="right_curve"></span>
					</div>
					&nbsp;&nbsp;
					<div class="input_text">
						<b style="float:left; padding-top:7px;">-</b>
						<span class="left_curve"></span>
						<form:input path="offsetPlus" maxlength="15" cssClass="mid numbersOnly" cssStyle="width: 50px;"	readonly="true" />
						<span class="right_curve"></span>
					</div>
					<form:errors class="invalid" path="offsetMinus" />
					<form:errors class="invalid" path="offsetPlus" />
				</div>
				<div class="clr"></div>
			</div>
			<c:if test="${operationType == 'Add'}" >
				<div class="row-container">
					<form:label path="addMore">Add more batches:</form:label>
				    <form:checkbox path="addMore" id = "addMore" value="true" />
				</div>
           </c:if>
			<div class="row-container">
				<label>&nbsp;</label>
				<input type="submit" title="Save / Update PaymentBatch" value="${buttonName}" class="login-btn" />
				<input type="button" title="Cancel" value="Cancel" class="login-btn" onclick="javascript:resetForm(this.form);showHideElements();showHideMoneySourceReset();" />
				<div class="clr"></div>
			</div>

		</div>
	</form:form>
</div>

<script type="text/javascript">
	function checkOffsetAmount(){
		$("#offsetPlus").val($("#offsetMinus").val());
	}

	$(function() {
	    $( "#depositDate" ).datepicker();
	    $( "#postedOn" ).datepicker();
	});

	function totalDepostAmt(){
		var totalAmount = 0.0;
	    $("#divMoneySources :text").each(function(){
		   	amt = $(this).val();
		   	if(amt == "" || isNaN(amt)){
			   amt =0.0;
			}
		   	totalAmount += parseFloat(amt);
	    });
	    $("#depositAmount").val(parseFloat(totalAmount).toFixed(2));
	}

	function showHideElements(){
		var paymentTypeAdminIncome = checkPaymentTypeAdminIncome();
		var proHealth = checkProHealth();
		var paymentTypeOTC = checkPaymentTypeOTC();

		$('#revenueTypeId').attr("disabled", false);

		if(paymentTypeAdminIncome){
			$('#adminIncome').show();
		}else{
			$('#adminIncome').hide();
		}

		if(proHealth){
			$('#divPhDoctors').show();
			$('#phDoctorList').attr("disabled", false);
			$("#ndba").val("0.0");
			$("#ndba").attr("readonly", "true");
		}else{
			$('#divPhDoctors').hide();
			$('#phDoctorList').attr("disabled", true);
			$("#ndba").removeAttr('readOnly');
		}

		if( proHealth && (paymentTypeOTC || paymentTypeAdminIncome) ){
			$('#divRevenueType').show();

			if(paymentTypeOTC){
				var revenueTypePatientCollection = "<%=Constants.REVENUE_TYPE_PATIENT_COLLECTION%>";
				//$("#revenueTypeId option[value='"+revenueTypePatientCollection+"']").prop('selected',true);
				$("#revenueTypeId").val(revenueTypePatientCollection);
				$('#revenueTypeId').attr("disabled", true);
			}else{
				$('#revenueTypeId').attr("disabled", false);
			}
		}else{
			$('#divRevenueType').hide();
		}

		window.setTimeout('$.uniform.update()',500);
	}

function checkPaymentTypeOTC(){
	var paymentTypeOTC = "<%=Constants.PAYMENT_TYPE_OTC%>";

	if($("#paymentTypeId").val() == paymentTypeOTC){
		return true;
	}else{
		return false;
	}
}

function checkPaymentTypeAdminIncome(){
	var paymentTypeAdminIncome = "<%=Constants.PAYMENT_TYPE_ADMIN_INCOME%>";

	if($("#paymentTypeId").val() == paymentTypeAdminIncome){
		return true;
	}else{
		return false;
	}
}

function checkProHealth(){
	var proHealthGroupId = "<%=Constants.PRO_HEALTH_GROUP_ID%>";

	if($('#doctorId').val() ==  proHealthGroupId){
		return true;
	}else{
		return false;
	}
}

function showHideMoneySourceReset(){
	var booleanChk = $("#moneySource").is(':checked');
	showHideMoneySource(booleanChk);
}

function showHideMoneySource(checked){
	var proHealth = checkProHealth();

	if(checked){
		$('#divMoneySources').show();
		$("#depositAmount").attr("readonly", "true");
		$("#ndba").val("0.0");
		$("#ndba").attr("readonly", "true");
		//$("#depositAmount").val("0.0");
	}else{
		$('#divMoneySources').hide();
		$("#depositAmount").removeAttr('readOnly');
		//$("#depositAmount").val("0.0");

		if(!proHealth){
			$("#ndba").removeAttr('readOnly');
		}

		$("#divMoneySources :text").each(function(){
	 		$("#divMoneySources :text").val("0");
		});
	}
}

function checkOffset(){
	var totalOffsetPosted = '${totalOffsetPosted}';
	if(parseInt(totalOffsetPosted)>0){
		alert("Payment Type cannot be changed as there are offsets attached corresponding to the following ticket number.");
		var chkDropdown = $('#paymentTypeId').val("${paymentBatch.paymentType.id}");
		$.uniform.update(chkDropdown);
	}
}

showHideElements();
showHideMoneySource(${paymentBatch.moneySource});
</script>
<script type="text/javascript" src="<c:url value='/static/resources/js/common.js'/>"></script>
