<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li>&nbsp;<a href="<c:url value='/chargeproductivity/list' />">Charge Productivity List</a> &raquo; </li>
			<li>&nbsp;Charge Batch Productivity</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<div class="section-content">
		<h3 class="heading-h3">Charge Batch Details</h3>
		<div id="msg" class="error">${warning}</div>
		<div class="row-container">
			<c:choose>
				<c:when test="${hide!='hide'}">
					<form method="get" id="frmGetBatchDetails">
						<div class="element-block" style="width:100%">
							<label><strong>Ticket number:</strong><em class="mandatory">*</em></label>
							<div class="input_text" >
								<span class="left_curve"></span>
								<input type="text" id="ticketNumber" name="ticketNumber"  maxlength="7" class="mid integerOnly" style="width: 100px;" value="${chargeProductivity.ticketNumber.id }" />
								<span class="right_curve"></span>
							</div>
							<input type="submit" title="Submit" value="Get Detail" class="btn"/>
						</div>
					</form>
				</c:when>
				<c:otherwise>
				    <div class="element-block" >
			    		<span class="lbl">Ticket number:</span>
			    		<span id="ticketNumber" >${chargeProductivity.ticketNumber.id}</span>
			   		</div>
			   		 <c:if test="${!empty chargeProductivity.id}">
						<!-- div class="element-block" >
							<label>Productivity Ticket number: &nbsp;&nbsp;&nbsp;${chargeProductivity.id}</label>
						</div -->
					</c:if>
				</c:otherwise>
			</c:choose>
			<div class="clr"></div>
		</div>
	    <%-- <div class="row-container">
		    	<label>Provider: </label>
		    	<label id="provider">${chargeProductivity.chargeBatchProcessing.doctor.name}</label>
		    	<div class="clr"></div>
		</div> --%>
		<div class="row-container">
			<div class="element-block">
				<span  class="lbl">Provider Name:</span> <span id="providerName">${chargeProductivity.ticketNumber.doctor.name}</span>
			</div>
			<div class="element-block">
				<span  class="lbl">Batch Type:</span> <span id="type">${chargeProductivity.ticketNumber.tempBatchType}</span>
			</div>
			<div class="clr"></div>
		</div>

		<div class="row-container">
			<div class="element-block">
				<span  class="lbl">Date of Service From:</span> <span id="dosFrom"><fmt:formatDate value="${chargeProductivity.ticketNumber.dosFrom}" pattern="MM/dd/yyyy"/></span>
			</div>
			<div class="element-block">
				<span  class="lbl">Date of Service To:</span> <span id="dosTo"><fmt:formatDate value="${chargeProductivity.ticketNumber.dosTo}" pattern="MM/dd/yyyy"/></span>
			</div>
			<div class="clr"></div>
		</div>
	</div>
	<form:form commandName="chargeProductivity" id="form1">
		<div class="form-container" id="divProductivityForm" >
			<form:hidden path="ticketNumber.id" id="batchId" />
			<div class="form-container-inner">
				<h3>Charge Batch Productivity</h3>
				<div id="msg" class="error"></div>

				<div class="row-container">
					<form:label path="productivityType">Productivity Type:<em class="mandatory">*</em></form:label>
					<div class="input_text">
						<form:select path="productivityType" onchange="javascript:setWorkFlowTypes()">
							<form:option value="Charge Entry">Charge Entry</form:option>
							<form:option value="Demo">Demo</form:option>
							<form:option value="Coding">Coding</form:option>
						</form:select>
					</div>
					<form:errors class="invalid" path="productivityType" />
					<div class="clr"></div>
				</div>

				<div class="row-container">
					<div class="element-block">
						<form:label path="ticketNumber.dateBatchPosted">CT Posted Date:<em class="mandatory">*</em></form:label>
						<div class="input_text">
							<span class="left_curve"></span>
							<form:input path="ticketNumber.dateBatchPosted" id="dateBatchPosted" maxlength="50" readonly="true" cssClass="mid" cssStyle="width: 90px;" />
							<span class="right_curve"></span>
						</div>
						<form:errors class="invalid" path="ticketNumber.dateBatchPosted" />
					</div>
					<div class="element-block">
						<form:label path="numberOfTransactions">Number of Transactions:<em	class="mandatory">*</em></form:label>
						<div class="input_text">
							<span class="left_curve"></span>

							<form:input path="numberOfTransactions" maxlength="8" cssClass="mid integerOnly" cssStyle="width: 90px;" />

							<span class="right_curve"></span>
						</div>
						<form:errors class="invalid" path="numberOfTransactions" />
					</div>
					<div class="clr"></div>
				</div>

				<div class="row-container">
					<form:label path="remarks">Remarks:</form:label>
					<form:textarea path="remarks" cols="30" rows="5" />
					<div class="clr"></div>
				</div>

				<div class="row-container">
					<div class="element-block">
						<form:label path="time">Time (minute):<br /> If applicable</form:label>
						<div class="input_text">
							<span class="left_curve"></span>

							<form:input path="time" maxlength="5" cssClass="mid integerOnly" cssStyle="width: 90px;" />

							<span class="right_curve"></span>
						</div>
						<form:errors class="invalid" path="time" />
					</div>
					<div class="element-block">
						<label>Type:<em class="mandatory">*</em></label>
						<div class="select-container2">
							<form:select path="type" class="selectbox">
								<form:option value="CAP">CAP</form:option>
								<form:option value="FFS">FFS</form:option>
							</form:select>
						</div>
					</div>
					<div class="clr"></div>
				</div>

				<div class="row-container">
					<form:label path="workFlow">Work flow:<em class="mandatory">*</em></form:label>
					<form:select path="workFlow" class="workFlow">
						<form:option value="reject">Reject</form:option>
						<form:option value="paymentreversal">Payment Reversal</form:option>
						<form:option value="onhold">On Hold</form:option>
					</form:select>
					<form:errors class="invalid" path="workFlow" />
					<div class="clr"></div>
				</div>

				<div class="demo-div" style="border: 0px solid blue;display:none" >
					<div class="row-container">
						<form:label path="demoAndVerification">Demo And Verification:</form:label>
						<div class="input_text">
							<span class="left_curve"></span>
							<form:input path="demoAndVerification" maxlength="50"
								cssClass="mid " cssStyle="width: 90px;" />
							<span class="right_curve"></span>
						</div>
						<form:errors class="invalid" path="demoAndVerification" />
						<div class="clr"></div>
					</div>

					<div class="row-container">
						<form:label path="demoReviewAndVerification">Demo Review And Verification:</form:label>
						<div class="input_text">
							<span class="left_curve"></span>
							<form:input path="demoReviewAndVerification" maxlength="50"
								cssClass="mid " cssStyle="width: 90px;" />
							<span class="right_curve"></span>
						</div>
						<form:errors class="invalid" path="demoReviewAndVerification" />
						<div class="clr"></div>
					</div>

					<div class="row-container">
						<form:label path="demoReviewOnly">Demo Review Only</form:label>
						<div class="input_text">
							<span class="left_curve"></span>
							<form:input path="demoReviewOnly" maxlength="50"
								cssClass="mid " cssStyle="width: 90px;" />
							<span class="right_curve"></span>
						</div>
						<form:errors class="invalid" path="demoReviewOnly" />
						<div class="clr"></div>
					</div>
				</div>

				<div class="coding-div" style="border: 0px solid blue;display:none" >
					<div class="row-container">
						<form:label path="productivityUnitsAccountAndCodes">Productivity units a/c & codes:</form:label>
						<div class="input_text">
							<span class="left_curve"></span>
							<form:input path="productivityUnitsAccountAndCodes" maxlength="50" cssClass="mid" cssStyle="width: 90px;" />
							<span class="right_curve"></span>
						</div>
						<form:errors class="invalid" path="productivityUnitsAccountAndCodes" />
						<div class="clr"></div>
					</div>
				</div>

				<div class="row-container">
					<label>&nbsp;</label>
					<%-- <input type="submit" title="${workflow.value}" name="action" value="${workflow.value}" class="login-btn" onclick="return onSubmitCheck('${workflow.key}');"/> --%>

					<input type="submit" title="Save" value="Submit" class="login-btn"	onclick="javascript:disabledButtons();this.form.submit();" />
					<!-- <input type="button" title="Cancel" value="Cancel" class="login-btn"  onclick="javascript:resetForm(this.form);" /> -->
					<div class="clr"></div>
				</div>
			</div>
		</div>
	</form:form>
</div>
<c:if test="${empty chargeProductivity.id and validationError !='yes' }">
	<script type="text/javascript">
	$(document).ready(function(){
		$('#divProductivityForm').hide();
		});
	</script>
</c:if>
<script type="text/javascript">
	$('#frmGetBatchDetails').submit(function() {
		$('#form1')[0].reset();
		$.uniform.update();
		clearErrors();
		getDetail();
		return false;
	});

	$(function() {
	    /* $( "#scanDate" ).datepicker(); */
	    $( "#dateBatchPosted" ).datepicker();
	});

	function getDetail(){
	 var batchId = $('#ticketNumber').val();
		$.ajax({
			type : "POST",
			dataType : "json",
			url : contextPath+"/chargeproductivity/json",
			data : "batchId="+batchId,
			success : function(data) {
				if(data != null && data.length > 0){
					if(data[0] == "err"){
						$('#msg').html(data[1]);
						$('#batchId').val("");
						$('#dosFrom').text('');
						$('#dosTo').text('');
						$('#type').text('');
						$('#providerName').text('');
						$('#divProductivityForm').hide("slow");
					}else{
						$('#msg').html("");
						$('#batchId').val(data[0]);
						$('#dosFrom').text(data[1]);
						$('#dosTo').text(data[2]);
						$('#type').text(data[3]);
						$('#providerName').text(data[4]);
						$('#divProductivityForm').show();
					}
				}
			},
			error: function(data){
				//alert (data);
				//$("#modificationSummaryContent").html("Error:"+data);
			}
		});
	}

	/* function onSubmitCheck(workFlowKey)
	{
		 var batchId = $('#batchId').val();

		 if(batchId == null || $.trim(batchId) == ''){
			alert("please get ticket detail first");
			return false;
		 }else{
			 // $('#workFlow').val(workFlowKey);
			 disabledButtons();
			 $('#chargeProductivity').submit();
		 }
	} */

	function setWorkFlowTypes()	{
		var prodType = $('#productivityType').val();
		var options = "";
		var select = $('#workFlow');
		if(select.prop) {
		  	options = select.prop('options');
		}else {
		 	options = select.attr('options');
		}
		//$('option', select).remove();
		$('#workFlow').empty();

		if(prodType != null && $.trim(prodType) != ""){
			if(prodType == "Charge Entry")	{
				var newOptions = {
				    'reject' : 'Reject',
				    'paymentreversal' : 'Payment Reversal',
				    'onhold' : 'On Hold'
				};
				var selectedOption = 'reject';
				$.each(newOptions, function(val, text) {
				    options[options.length] = new Option(text, val);
				});
				select.val(selectedOption);
				/* workFlowSetectBox.empty().append(function() {
					var output;
		                output = '<option value="Reject"> Reject </option>';
		                output += '<option value="Payment Reversal"> Payment Reversal </option>';
		            return output;
		        }); */
		        $('.coding-div').hide();
				$('.demo-div').hide();
				/* $('#productivityUnitsAccountAndCodes').val('');
				$('#demoReviewOnly').val('');
				$('#demoReviewAndVerification').val('');
				$('#demoAndVerification').val(''); */
			}else if(prodType == "Coding"){
					var newOptions = {
					    'internal' : 'Internal',
					    'argustl' : 'Argus TL'
					};
					var selectedOption = 'internal';
					$.each(newOptions, function(val, text) {
					    options[options.length] = new Option(text, val);
					});
					select.val(selectedOption);
				/* workFlowSetectBox.empty().append(function() {
					var output;
		                output = '<option value="Internal"> Internal</option>';
		                output += '<option value="Argus TL"> Argus TL </option>';
		            return output;
		        }); */
		        $('.coding-div').show();
				$('.demo-div').hide();
				/* $('#demoReviewOnly').val('');
				$('#demoReviewAndVerification').val('');
				$('#demoAndVerification').val(''); */
			}else if(prodType == "Demo"){
					var newOptions = {
					    'reject' : 'Reject'
					};
				var selectedOption = 'reject';
				$.each(newOptions, function(val, text) {
				    options[options.length] = new Option(text, val);
				});
				select.val(selectedOption);
				/* workFlowSetectBox.empty().append(function() {
					var output ;
		                output = '<option value="Reject"> Reject </option>';
		            return output;
		        }); */
				$('.demo-div').show();
				$('.coding-div').hide();
				/* $('#productivityUnitsAccountAndCodes').val(''); */
			}
		}else{
			$('.demo-div').hide();
			$('.coding-div').hide();
		}

		window.setTimeout('$.uniform.update()',200);
		return;
	}

	function checkBatchDetails(){
		if($('#batchId').val() > 0){
			var chargeProductivityId = "${chargeProductivity.id}";
			var hide = "${hide}";

			if(chargeProductivityId != null || chargeProductivityId>0){
				$('#divProductivityForm').show();
			}else{
				if(hide == 'hide'){
					$('#divProductivityForm').show();
				}else{
					$('#divProductivityForm').hide("slow");
				}
			}

			/* if(chargeProdRejectId == null || $.trim(chargeProdRejectId) == ''){
				getDetail();
			}else{
				$('#divProductivityForm').show();
				$('#workFlowId').attr("disabled", true);
				$('#doctor').text("${paymentProductivity.paymentBatch.doctor.name}");
			} */
		}else{
			$('#divProductivityForm').show;
		}
	}

	checkBatchDetails();
	setWorkFlowTypes();
	$('#workFlow').val('${chargeProductivity.workFlow}');
</script>

<c:if test="${!empty chargeProductivity.workFlow and chargeProductivity.workFlow == 'onhold'}">
	<c:if test="${!empty chargeProductivity.onHoldBy}">
		<script type="text/javascript">
			$("#productivityType").attr("disabled",true);
			$("#workFlow").attr("disabled",true);
		</script>
	</c:if>
</c:if>
<script type="text/javascript">
	var reject = '${rejection}';
	if(reject == '1')
	{
		$("#productivityType").attr("disabled",true);
		$("#workFlow").attr("disabled",true);
	}
</script>
<%-- <c:if test="${!empty chargeProductivity.id}">
	<script type="text/javascript">
		$("#productivityType").attr("disabled",true);
		$("#workFlow").attr("disabled",true);
	</script>
</c:if> --%>
<script type="text/javascript" src="<c:url value='/static/resources/js/common.js'/>"></script>
