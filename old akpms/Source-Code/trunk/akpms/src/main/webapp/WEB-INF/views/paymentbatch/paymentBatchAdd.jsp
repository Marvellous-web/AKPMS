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
						<form:errors cssClass="invalid" path="billingMonth" />
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
						<form:errors cssClass="invalid" path="billingYear" />
					</div>
				</div>
				<div class="clr"></div>
			</div>
			
			<div class="row-container">
				<div class="element-block">
					<form:label path="depositDate">Deposit Date:<em
							class="mandatory">*</em>
					</form:label>
					<div class="input_text">
						<span class="left_curve"></span>
						<form:input path="depositDate" cssClass="mid"
							cssStyle="width: 108px;" readonly="true" />
						<span class="right_curve"></span>
					</div>
					<form:errors cssClass="invalid" path="depositDate" />
				</div>
				<div class="element-block">
					<form:label path="eraCheckNo">ERA-Check #:</form:label>
					<div class="input_text">
						<span class="left_curve"></span>
						<form:input path="eraCheckNo" maxlength="50" cssClass="mid"
							cssStyle="width: 108px;" readonly="${readOnly}" />
						<span class="right_curve"></span>
					</div>
					<form:errors cssClass="invalid" path="eraCheckNo" />
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<form:label path="doctor.id">Group/Doctor Name:<em class="mandatory">*</em></form:label>
				<div>
					<form:select id="doctorId" path="doctor.id" cssClass="selectbox"
						onchange="javascript:showHideElements();">
						<form:option value="0">-: Select Group/Doctor :-</form:option>
						<c:forEach var="doctor" items="${doctorList}">
						<c:set var = "nonDeposit" value = "${doctor.nonDeposit}"/>
						<c:if test = "${nonDeposit}">
							<form:option value="${doctor.id}">${doctor.name} (Non-Deposit)</form:option>
						</c:if>
						<c:if test = "${!nonDeposit}">
							<form:option value="${doctor.id}">${doctor.name}</form:option>
						</c:if>
						</c:forEach>
						<%-- <form:options items="${doctorList}" itemValue="id" itemLabel="name" /> --%>
					</form:select>
				</div>
				<form:errors cssClass="invalid" path="doctor.id" />
				<div class="clr"></div>
			</div>
						
			<div class="row-container" id="divPhDoctors" >
				<form:label path="phDoctor">PH Doctor Name:</form:label>
				<div>
					<form:select path="phDoctor.id" id="phDoctorId" cssClass="selectbox" disabled="disabled">
						<form:option value="" >-: Select PH Doctor: -</form:option>
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
						<form:options items="${paymentTypeList}" itemValue="id" itemLabel="name" />
					</form:select>
				</div>
				<form:errors cssClass="invalid" path="paymentType.id" />
				<div class="clr"></div>
			</div>
			
			<div id="divRevenueType" class="row-container">
				<form:label path="revenueType.id">Revenue Type:</form:label>
				<div>
					<form:select path="revenueType.id" cssClass="selectbox"	id="revenueTypeId" disabled="disabled" >
						<form:options items="${revenueTypes}" itemValue="id" itemLabel="name" />
					</form:select>
				</div>
				<form:errors cssClass="invalid" path="revenueType.id" />
				<div class="clr"></div>
			</div>

			<div id="moneySource" class="row-container">
				<form:label path="moneySource.id">Money Source:</form:label>
				<div>
					<form:select path="moneySource.id" cssClass="selectbox" id="moneySourceId" onchange="disableEnableDepositAmountOpt(this.value);" >
						<form:option value="">-: Select Money Source :-</form:option>
						<form:options items="${moneySources}" itemValue="id" itemLabel="name" />
					</form:select>
				</div>
				<form:errors cssClass="invalid" path="moneySource.id" />
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<form:label path="insurance.id">Insurance:</form:label>
				<div>
					<form:select path="insurance.id" cssClass="selectbox"
						id="insurance">
						<form:option value="">-: Select Insurance :-</form:option>
						<form:options items="${insuranceList}" itemValue="id" itemLabel="name" />
					</form:select>
				</div>
				<form:errors cssClass="invalid" path="insurance.id" />
				<div class="clr"></div>
			</div>
			
			<div class="row-container">
				<label>&nbsp;</label>
				<div class="element-block" style="width: 25%">
					<div class="input_text">
						<span>Credit Card</span><br/>
						<span class="left_curve"></span>
						<form:input path="creditCard" maxlength="15" cssClass="mid numbersOnly" cssStyle="width: 70px;" onblur="javascript:calculateDepositAmount();" onkeypress="javascript:calculateDepositAmount();" />
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="element-block" style="width: 25%">
					<div class="input_text">
						<span>Vault (Check/Cash)</span><br/>
						<span class="left_curve"></span>
						<form:input path="vault" maxlength="15" cssClass="mid numbersOnly" cssStyle="width: 70px;" onblur="javascript:calculateDepositAmount();" onkeypress="javascript:calculateDepositAmount();" />
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="element-block" style="width: 25%">
					<div class="input_text">
						<span>Telecheck</span><br/>
						<span class="left_curve"></span>
						<form:input path="telecheck" maxlength="15" cssClass="mid numbersOnly" cssStyle="width: 70px;" onblur="javascript:calculateDepositAmount();" onkeypress="javascript:calculateDepositAmount();" />
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="clr"></div>
			</div>
			
			<div class="row-container">
				<div class="element-block">
					<form:label path="depositAmount">Deposit Amount:<br/>(in USD)</form:label>
					<div class="input_text">
						<span class="left_curve"></span>
						<form:input path="depositAmount" maxlength="15" cssClass="mid numbersOnly" cssStyle="width: 108px;" readonly="${readOnly}" onblur="javascript:checkSourceValues();" />
						<span class="right_curve"></span>
					</div>
					<form:errors cssClass="invalid" path="depositAmount" />
				</div>
				<div class="element-block">
					<form:label path="ndba" class="label-txt-inputtxt">&nbsp;&nbsp; NDBA:<br/>(in USD)
					</form:label>
					<div class="input_text">
						<span class="left_curve"></span>
						<form:input path="ndba" maxlength="15" cssClass="mid numbersOnly"
							cssStyle="width: 108px;" readonly="${readOnly}" />
						<span class="right_curve"></span>
					</div>
					<form:errors cssClass="invalid" path="ndba" />
				</div>
				<span class="invalid" id="amt_ndba_error" style="width:100%"></span>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<form:label path="comment">Comment:</form:label>
				<form:textarea rows="5" cols="20" path="comment" readonly="${readOnly}" />
				<form:errors cssClass="invalid" path="comment" />
				<div class="clr"></div>
			</div>

			<!-- agency money, old prior ar and other income move to update page:BS -->

			<div class="row-container">
				<div class="element-block">
					<form:label path="refundChk">Refund Chk:<br/> (in USD)</form:label>
					<div class="input_text">
						<span class="left_curve"></span>
						<form:input path="refundChk" maxlength="15" cssClass="mid numbersOnly" cssStyle="width: 108px;"	readonly="${readOnly}" />
						<span class="right_curve"></span>
					</div>
					<form:errors cssClass="invalid" path="refundChk" />
				</div>
				<div class="element-block">
					<form:label path="nsfSysRef">NSF/Sys Ref:<br /> (in USD)</form:label>
					<div class="input_text">
						<span class="left_curve"></span>
						<form:input path="nsfSysRef" maxlength="15" cssClass="mid numbersOnly" cssStyle="width: 108px;"	readonly="${readOnly}" />
						<span class="right_curve"></span>
					</div>
					<form:errors cssClass="invalid" path="refundChk" />
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
					<form:errors cssClass="invalid" path="offsetMinus" />
					<form:errors cssClass="invalid" path="offsetPlus" />
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
				<input type="button" title="Cancel" value="Cancel" class="login-btn" onclick="javascript:resetForm(this.form);showHideElements();" />
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

	/* function totalDepostAmt(){
		var totalAmount = 0.0;
	    $("#divMoneySources :text").each(function(){
		   	amt = $(this).val();
		   	if(amt == "" || isNaN(amt)){
			   amt =0.0;
			}
		   	totalAmount += parseFloat(amt);
	    });
	    $("#depositAmount").val(parseFloat(totalAmount).toFixed(2));
	} */

	function showHideElements(){
		var paymentTypeAdminIncome = checkPaymentTypeAdminIncome();
		var proHealth = checkProHealth();
		//var paymentTypeOTC = checkPaymentTypeOTC();

		$('#revenueTypeId').attr("disabled", false);

		/*
		if(paymentTypeAdminIncome){
			$('#adminIncome').show();
		}else{
			$('#adminIncome').hide();
		}*/

		if(proHealth){
			$('#phDoctorId').attr("disabled", false);
			//$("#ndba").val("0.0");
			//$("#ndba").attr("readonly", "true");
		}else{
			//$('#divPhDoctors').hide();
			$('#phDoctorId').attr("disabled", true);
			//$("#ndba").removeAttr('readOnly');
		}

//		if( proHealth && (paymentTypeOTC || paymentTypeAdminIncome) ){
		if( paymentTypeAdminIncome ){
			$('#revenueTypeId').attr('disabled', false);
			//if(paymentTypeOTC){
			//	var revenueTypePatientCollection = "Constants.REVENUE_TYPE_PATIENT_COLLECTION";
			//	$("#revenueTypeId").val(revenueTypePatientCollection);
			//	$('#revenueTypeId').attr("disabled", true);
			//}else{
			//	$('#revenueTypeId').attr("disabled", false);
			//}
		}else{
			$('#revenueTypeId').attr('disabled', true);
		}

		window.setTimeout('$.uniform.update()',500);
	}

//function checkPaymentTypeOTC(){
//	var paymentTypeOTC = "Constants.PAYMENT_TYPE_OTC";

//	if($("#paymentTypeId").val() == paymentTypeOTC){
//		return true;
//	}else{
//		return false;
//	}
//}

	function checkPaymentTypeAdminIncome(){
		var paymentTypeAdminIncome = "<%=Constants.PAYMENT_TYPE_ADMIN_INCOME%>";
	
		if($("#paymentTypeId").val() == paymentTypeAdminIncome){
			return true;
		}else{
			return false;
		}
	}
	
	function checkProHealth(){
		var proHealthGroupId = "<%=Constants.PRO_HEALTH_GROUP_ID %>";
	
		if($('#doctorId').val() ==  proHealthGroupId){
			return true;
		}else{
			return false;
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

	var calculatedDepositAmount = 0;
	
	function calculateDepositAmount(){
		var creditCard = $("#creditCard").val();
		//if($.trim(creditCard) == "" || $.trim(creditCard) == ".")creditCard = 0;
		var vault = $("#vault").val();
		//if($.trim(vault) == "" || $.trim(vault) == ".")vault = 0;
		var telecheck = $("#telecheck").val();
		//if($.trim(telecheck) == "" || $.trim(telecheck) == ".")telecheck = 0;
		
		calculatedDepositAmount = addFloats(creditCard, vault, telecheck);
		
		$("#depositAmount").val(calculatedDepositAmount); 
	}

	function checkSourceValues(){
		if($("#depositAmount").val() != calculatedDepositAmount){
			$("#creditCard").val(0);
			$("#vault").val(0);
			$("#telecheck").val(0);
		}
	}

	function disableEnableDepositAmountOpt(dropDownValue){
		moneySourceLockbox = "<%=Constants.MONEY_SOURCE_LOCKBOX %>";
		moneySourceEFT = "<%=Constants.MONEY_SOURCE_EFT %>";
		if(dropDownValue == moneySourceLockbox || dropDownValue == moneySourceEFT){
			$("#creditCard").attr("disabled", true);
			$("#vault").attr("disabled", true);
			$("#telecheck").attr("disabled", true);
		}else{
			$("#creditCard").attr("disabled", false);
			$("#vault").attr("disabled", false);
			$("#telecheck").attr("disabled", false);
		}
	}
	

showHideElements();
disableEnableDepositAmountOpt($("#moneySourceId").val());
</script>
<script type="text/javascript" src="<c:url value='/static/resources/js/common.js'/>"></script>
