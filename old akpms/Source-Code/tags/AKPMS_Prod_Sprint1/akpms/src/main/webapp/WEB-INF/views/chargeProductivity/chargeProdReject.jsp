<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li><a href="<c:url value='/chargeproductivityreject' />">&nbsp;Charge Batch Rejections List</a> &raquo;</li>
			 <li>&nbsp;${operationType}  Rejection</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<div class="section-content">
		<h3 class="heading-h3">Charge batch details</h3>
		<div id="msg" class="error">${warning}</div>
		<div class="row-container">
			<c:choose>
				<c:when test="${hide!='hide'}">
					<form method="get" id="frmGetBatchDetails">
						<div class="element-block" style="width:100%">
							<label><strong>Ticket number:</strong><em class="mandatory">*</em></label>
							<div class="input_text" >
								<span class="left_curve"></span>
									<input type="text" id="chargeBatchProcessId" name="chargeBatchProcessId" maxlength="7" class="mid integerOnly" style="width: 100px;" value="${chargeProdReject.chargeBatchProcessing.id }" />
								<span class="right_curve"></span>
							</div>
							<input type="submit" title="Submit" value="Get Detail" class="btn" />
						</div>
					</form>
				</c:when>

				<c:otherwise>
		    		<%-- <div class="element-block" >
				    	<span class="lbl">Ticket Number:</span> <span>${paymentProductivity.paymentBatch.id}</span>
				    </div> --%>
				    <div class="element-block" >
			    		<span class="lbl">Ticket number:</span>	<span id="ticketNumber" >${chargeProdReject.chargeBatchProcessing.id}</span>
			   		</div>
			   		 <c:if test="${!empty chargeProdReject.id}">
						<!-- div class="element-block" >
							<label>Reject Ticket: &nbsp;&nbsp;&nbsp;${chargeProdReject.id}</label>
						</div -->
					</c:if>
				</c:otherwise>
			</c:choose>
			<div class="element-block" >
				<span class="lbl">Provider name: </span> <span id="provider">${chargeProdReject.chargeBatchProcessing.doctor.name}</span>
	    	</div>
			<div class="clr"></div>
		</div>
	</div>

	<form:form commandName="chargeProdReject" id="form1" >
		<div class="form-container" id="divProductivityForm" >
			<div class="form-container-inner">
				 <h3>${operationType} Rejection </h3>
				<div class="row-container">
					<div class="element-block">
						<form:label path="patientName">Patient Name:<em class="mandatory">*</em></form:label>
							<div class="input_text">
								<span class="left_curve"></span>
									<form:input path="patientName" maxlength="50" class="mid " style="width: 90px;"  />
								<span class="right_curve"></span>
							</div>
						<form:errors class="invalid" path="patientName" />
					</div>
					<div class="element-block">
						<form:label path="dob">DOB:</form:label>
						<div class="input_text">
							<span class="left_curve"></span>
							<form:input path="dob" readonly="true" maxlength="50" cssClass="mid" cssStyle="width: 90px;" />
							<span class="right_curve"></span>
						</div>
						<form:errors class="invalid" path="dob" />
					</div>

					<div class="clr"></div>
				</div>

				<div class="row-container">
					<div class="element-block">
						<form:label path="sequence">Sequence:<em class="mandatory">*</em></form:label>
						<div class="input_text">
								<span class="left_curve"></span>
								<form:input path="sequence" maxlength="7" cssClass="mid integerOnly" cssStyle="width: 90px;" />
								<span class="right_curve"></span>
						</div>
						<form:errors class="invalid" path="sequence" />
					</div>
					<div class="element-block">
						<form:label path="account">Account:<em class="mandatory">*</em></form:label>
						<div class="input_text">
							<span class="left_curve"></span>
							<form:input path="account" maxlength="50" cssClass="mid" cssStyle="width: 90px;" />
							<span class="right_curve"></span>
						</div>
						<form:errors class="invalid" path="account" />
					</div>

					<div class="clr"></div>
				</div>
				<div class="row-container">
					<form:label path="dateOfService">DOS:<em class="mandatory">*</em></form:label>
					<div class="input_text">
						<span class="left_curve"></span>
						<form:input path="dateOfService" readonly="true" maxlength="50" cssClass="mid" cssStyle="width: 90px;" />
						<span class="right_curve"></span>
					</div>
					<form:errors class="invalid" path="dateOfService" />
					<div class="clr"></div>
				</div>
				<%-- <div class="row-container">
			    	<div class="element-block" >
			    		<label>Date eBridge:</label>
			    	</div>
			    	<div class="element-block" >
			    		<label>${chargeProductivity.ticketNumber.scanDate}</label>
			    	</div>
			    	<div class="clr"></div>
			    </div> --%>


				<div class="row-container">
					<form:label path="reasonToReject">Reason to Reject:<em class="mandatory">*</em></form:label>
					<form:select path="reasonToReject">
							<form:option value="">- Select a Query Type -</form:option>
							<c:forEach var="reasonToReject" items="${reasonsToReject}">
								<form:option value="${reasonToReject}">${reasonToReject}</form:option>
							</c:forEach>
						</form:select>
					<form:errors class="invalid" path="reasonToReject" />
					<div class="clr"></div>
				</div>

				<div class="row-container">
					<form:label path="insuranceType">Insurance Type:<em class="mandatory">*</em></form:label>
						<form:select path="insuranceType">
							<form:option value="">- Select a Insurance Type -</form:option>
							<c:forEach var="insuranceType" items="${insuranceTypes}">
								<form:option  value="${insuranceType.key}">${insuranceType.value}</form:option>
							</c:forEach>
						</form:select>
					<form:errors class="invalid" path="insuranceType" />
					<div class="clr"></div>
				</div>

				<div class="row-container">
					<form:label path="remarks">Remarks:</form:label>
						<form:textarea path="remarks" cols="30" rows="5"/>
					<div class="clr"></div>
				</div>

				<div class="row-container">
					<label>&nbsp;</label>
					<form:hidden path="chargeBatchProcessing.id" id="batchId" />
					<input type="submit" value="Make as Rejected" class ="login-btn"/>
					<input type="button" title="Cancel" value="Cancel" class="login-btn" onclick="javascript:resetForm(this.form);setTicketNumber();" />
					<div class="clr"></div>
				</div>
			</div>
		</div>
	</form:form>
</div>
<c:if test="${empty chargeProdReject.chargeBatchProcessing.id  and validationError !='yes' }">
	<script type="text/javascript">
	$(document).ready(function(){
		$('#divProductivityForm').hide();
		});
	function setTicketNumber(){
		$('#batchId').val($('#chargeBatchProcessId').val());
	}
	</script>
</c:if>

<c:if test="${not empty chargeProdReject.chargeBatchProcessing.id}">

<script type="text/javascript">
function setTicketNumber(){
	return true;
}
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
		$( "#dob" ).datepicker();
	    $( "#dateOfService" ).datepicker();
	    $( "#dateOfFirstRequestToDoctorOffice" ).datepicker();
	    $( "#dateOfSecondRequestToDoctorOffice" ).datepicker();
	    //$( "#postDate" ).datepicker();
	});

	function getDetail()
	{
		 var batchId = $('#chargeBatchProcessId').val();
		 /* if(batchId == null || $.trim(batchId) == ''){
				//$('#msg').html("Ticket number cannot be left blank.");
			 	$('#batchId').val("");
				$('#provider').text('');
				$('#divProductivityForm').hide("slow");
			 }else{ */
				$.ajax({
					type : "POST",
					dataType : "json",
					url : contextPath+"/chargeproductivityreject/batch/json",
					data : "batchId="+batchId,
					success : function(data) {
						if(data != null && data.length > 0)
						{
							if(data[0] == "err")
							{
								$('#msg').html(data[1]);
								$('#batchId').val(data[0]);
								$('#provider').text('');
								$('#divProductivityForm').hide("slow");
							}
							else
							{
								$('#divProductivityForm').show();
								$('#msg').html("");
								$('#batchId').val(data[0]);
								$('#provider').text(data[1]);
							}
						}
					},
					error: function(data){
						$('#divProductivityForm').hide("slow");
						//alert (data);
						//$("#modificationSummaryContent").html("Error:"+data);
					}
				});
			 //}
	}

	function checkBatchDetails(){
		if($('#batchId').val() > 0){
			var chargeProdRejectId = "${chargeProdReject.id}";
			var hide = "${hide}";
			if(chargeProdRejectId != null || chargeProdRejectId>0)
			{
				$('#divProductivityForm').show();
			}
			else
			{
				if(hide == 'hide')
				{
					$('#divProductivityForm').show();
				}
				else
				{
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
		}
		else
		{
			$('#divProductivityForm').hide("slow");
		}
	}

	checkBatchDetails();

</script>
<script type="text/javascript" src="<c:url value='/static/resources/js/common.js'/>"></script>