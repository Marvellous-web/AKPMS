<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript" src="<c:url value='/static/resources/js/common.js'/>"></script>

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
							<div class="input_text">
								<span class="left_curve"></span>
								<input type="text" id="ticketNumber" name="ticketNumber" maxlength="7" class="mid integerOnly" style="width: 100px;" value="${chargeProductivity.ticketNumber.id }" />
								<span class="right_curve"></span>
							</div>
							<input type="hidden" name="prodType" id="prodType" value="${prodType}" />
							<input type="submit" title="Submit" value="Get Detail" class="btn"/>
						</div>
					</form>
				</c:when>
				<c:otherwise>
				    <div class="element-block" >
			    		<span class="lbl">Ticket number:</span>
			    		<span id="ticketNumber" >${chargeProductivity.ticketNumber.id}</span>
			   		</div>
			   		 <%-- <c:if test="${!empty chargeProductivity.id}">
						<div class="element-block" >
							<label>Productivity Ticket number: &nbsp;&nbsp;&nbsp;${chargeProductivity.id}</label>
						</div>
					</c:if> --%>
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
				<span  class="lbl">Provider Name:</span> <span id="providerName">(${chargeProductivity.ticketNumber.doctor.name})
				<%-- <c:set var="doctorName" value="${chargeProductivity.ticketNumber.doctor.name}"/>
				${fn:replace(doctorName,'(Non-Deposit)',' ')} --%>
					<c:if test="${not empty chargeProductivity.ticketNumber.doctor.parent}">
						(${chargeProductivity.ticketNumber.doctor.parent.name})
					</c:if>
				</span>
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
					<label>Productivity Type:</label>
					<div class="input_text"><form:hidden path="productivityType" value="${prodType}" />	${prodType}	</div>
					<div class="clr"></div>
				</div>
				
				<c:if test="${prodType eq 'CE'}">
					<%@include file="chargeBatchProductivityCE.jsp"%>
				</c:if>
				<c:if test="${prodType eq 'Demo'}">
					<%@include file="chargeBatchProductivityDemo.jsp"%>
				</c:if>
				<c:if test="${prodType eq 'Coding'}">
					<%@include file="chargeBatchProductivityCoding.jsp"%>
				</c:if>
				
				<div class="row-container">
					<div class="element-block">
						<form:label path="time">Time (minute):</form:label>
						<div class="input_text">
							<span class="left_curve"></span>
							<form:input path="time" maxlength="5" cssClass="mid integerOnly" cssStyle="width: 100px;" />
							<span class="right_curve"></span>
						</div>
						<form:errors class="invalid" path="time" />
					</div>
					<div class="element-block" >
						<form:label path="scanDate">Scan Date:<em class="mandatory">*</em></form:label>
						<div class="input_text">
							<span class="left_curve"></span>
							<form:input path="scanDate" id="scanDate" cssClass="mid" cssStyle="width: 75px;"  readonly="true" />
							<span class="right_curve"></span>
						</div>
						<form:errors class="invalid" path="scanDate" />
					</div>
					<div class="clr"></div>
				</div>
				
				<div class="row-container">
					<form:label path="remarks" id="lblRemark">Remarks:</form:label>
					<form:textarea path="remarks" cols="30" rows="5" />
					<div class="clr"></div>
					<form:errors class="invalid" path="remarks" />
					<div class="clr"></div>
				</div>
				
				<div class="row-container">
					<form:label path="remarks">On Hold:</form:label>
					<form:checkbox path="onHold" id="onHold" value="1" onclick="onholdProcess();" />
					<div class="clr"></div>
				</div>
				
				<div class="row-container">
					<form:label path="workFlow">Work flow:</form:label>
					<form:select path="workFlow" class="workFlow">
						<form:option value="">-: Select Workflow :-</form:option>
						<form:option value="reject">Reject</form:option>
						<form:option value="paymentreversal">Payment Reversal</form:option>
						<form:option value="argustl">Argus TL</form:option>
						<form:option value="internal">Internal</form:option>
					</form:select>
					<div class="clr"></div>
				</div>

				<div class="row-container">
					<label>&nbsp;</label>
					<input type="submit" title="Save" value="Submit" class="login-btn"	onclick="javascript:disabledButtons();this.form.submit();" />
					<input type="reset" title="Cancel" value="Cancel" class="login-btn"  onclick="javascript:resetForm(this.form);" />
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
	$(function() {
	    $("#scanDate").datepicker();
	});
	$('#frmGetBatchDetails').submit(function() {
		$('#form1')[0].reset();
		$.uniform.update();
		clearErrors();
		getDetail();
		return false;
	});

	function getDetail(){
		var batchId = $('#ticketNumber').val();
		$.ajax({
			type : "POST",
			dataType : "json",
			url : contextPath+"/chargeproductivity/json",
			data : "batchId="+batchId + "&prodType="+$("#prodType").val(),
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
		}else{
			$('#divProductivityForm').show;
		}
	}

	checkBatchDetails();
	//$('#workFlow').val('${chargeProductivity.workFlow}');
</script>

<c:if test="${!empty chargeProductivity.workFlow and chargeProductivity.workFlow == 'onhold'}">
	<c:if test="${!empty chargeProductivity.onHoldBy}">
		<script type="text/javascript">
			$("#workFlow").attr("disabled",true);
		</script>
	</c:if>
</c:if>

<script type="text/javascript">
	var reject = '${rejection}';
	if(reject == '1'){
		$("#workFlow").attr("disabled",true);
	}

	function onholdProcess(){
		if($('#onHold').is(":checked")){
			$("#lblRemark").html("On Hold Remarks<em class='mandatory'>*</em>:");
		}else{
			$("#lblRemark").html("Remarks:");
		}
	}

	onholdProcess();
</script>

<c:if test="${prodType eq 'Demo'}"> 
<script type="text/javascript">
	$("#time").attr('readonly', 'readonly')
</script>
</c:if>