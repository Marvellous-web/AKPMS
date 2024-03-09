<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<fmt:setLocale value="en_US"/>
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
			<div class="clr"></div>
		</div>
		<div class="row-container">	
			<div class="element-block" >
				<span class="lbl">Provider name: </span> 
				<span id="providerName">
					${chargeProdReject.chargeBatchProcessing.doctor.name}
					<c:if test="${not empty chargeProdReject.chargeBatchProcessing.doctor.parent}">
						(${chargeProdReject.chargeBatchProcessing.doctor.parent.name})
					</c:if>
				</span>
	    	</div>
	    	<div class="element-block">
				<span  class="lbl">Batch Type:</span> 
				<span id="type">
					<c:if test="${not empty chargeProdReject.chargeBatchProcessing.type}">
						<fmt:bundle basename="chargeBatchType">
							<fmt:message key="${chargeProdReject.chargeBatchProcessing.type}"/>  
						</fmt:bundle>	
					</c:if>				
				</span>
			</div>
			<div class="clr"></div>
		</div>
		
		<div class="row-container">
			<div class="element-block">
				<span  class="lbl">Date of Service From:</span> <span id="dosFrom"><fmt:formatDate value="${chargeProdReject.chargeBatchProcessing.dosFrom}" pattern="MM/dd/yyyy"/></span>
			</div>
			<div class="element-block">
				<span  class="lbl">Date of Service To:</span> <span id="dosTo"><fmt:formatDate value="${chargeProdReject.chargeBatchProcessing.dosTo}" pattern="MM/dd/yyyy"/></span>
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
						<form:label path="patientName">Patient Name:<!-- <em class="mandatory">*</em> --></form:label>
							<div class="input_text">
								<span class="left_curve"></span>
									<form:input path="patientName" maxlength="50" class="mid " style="width: 100px;"  />
								<span class="right_curve"></span>
							</div>
						<form:errors class="invalid" path="patientName" />
					</div>
					<div class="element-block">
						<form:label path="dob">DOB:</form:label>
						<div class="input_text">
							<span class="left_curve"></span>
							<form:input path="dob" readonly="true" cssClass="mid" cssStyle="width: 100px;" />
							<span class="right_curve"></span>
						</div>
						<form:errors class="invalid" path="dob" />
					</div>

					<div class="clr"></div>
				</div>

				<div class="row-container">
					<div class="element-block">
						<form:label path="sequence">Sequence:<!-- <em class="mandatory">*</em> --></form:label>
						<div class="input_text">
								<span class="left_curve"></span>
								<form:input path="sequence" maxlength="7" cssClass="mid" cssStyle="width: 100px;" />
								<span class="right_curve"></span>
						</div>
						<form:errors class="invalid" path="sequence" />
					</div>
					<div class="element-block">
						<form:label path="account">Account:<!-- <em class="mandatory">*</em> --></form:label>
						<div class="input_text">
							<span class="left_curve"></span>
							<form:input path="account" maxlength="50" cssClass="mid" cssStyle="width: 100px;" />
							<span class="right_curve"></span>
						</div>
						<form:errors class="invalid" path="account" />
					</div>
					<div class="clr"></div>
				</div>
				<div class="row-container">
					<div class="element-block">
						<form:label path="dateOfService">DOS:<!-- <em class="mandatory">*</em> --></form:label>
						<div class="input_text">
							<span class="left_curve"></span>
							<form:input path="dateOfService" readonly="true" cssClass="mid" cssStyle="width: 100px;" />
							<span class="right_curve"></span>
						</div>
						<form:errors class="invalid" path="dateOfService" />
					</div>
					
					<%-- <div class="element-block">
						<form:label path="location">Location:</form:label>
						<div class="input_text">
							<span class="left_curve"></span>
							<form:input path="location" maxlength="50" cssClass="mid" cssStyle="width: 100px;" />
							<span class="right_curve"></span>
						</div>
						<form:errors class="invalid" path="location" />
					</div>
					<div class="clr"></div> --%>
					<div class="element-block">
						<form:label path="location">Location:</form:label>
						<div class="select-container2">
							<span class="left_curve"></span>
							<form:select path="location.id">
								<form:option value="0">Select Location</form:option>
								<c:forEach items="${locationList}" var="location">
									<form:option value="${location.id}">${location.name}</form:option>
								</c:forEach>
							</form:select>
							
							<%-- <form:input path="location" maxlength="50" cssClass="mid" cssStyle="width: 100px;" /> --%>
							<span class="right_curve"></span>
						</div>
						<form:errors class="invalid" path="location" />
					</div>
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
					<div class="element-block">
						<form:label path="reasonToReject">Reason to Reject:<em class="mandatory">*</em></form:label>
						<div class="select-container2">
							<form:select path="reasonToReject">
								<form:option value="">- Select a Reason to Reject -</form:option>
								<optgroup label="Coding">
									<form:option value="CO-Need CPT & ICD">CO-Need CPT & ICD</form:option>
									<form:option value="CO-Need PQRS Form">CO-Need PQRS Form</form:option>
									<form:option value="CO-Need BMI">CO-Need BMI</form:option>
									<form:option value="CO-Provide drug/Vaccine info">CO-Provide drug/Vaccine info</form:option>
									<form:option value="CO-Need Admin">CO-Need Admin</form:option>
									<form:option value="CO-Need CPT">CO-Need CPT</form:option>
									<form:option value="CO-Need ICD">CO-Need ICD</form:option>
									<form:option value="CO-Need LMP">CO-Need LMP</form:option>
									<form:option value="CO-Need Date of admission (DOA)">CO-Need Date of admission (DOA)</form:option>
									<form:option value="CO-Need Date of service (DOS)">CO-Need Date of service (DOS)</form:option>
									<form:option value="CO-Need Location/ POS">CO-Need Location/ POS</form:option>
									<form:option value="CO-Need Date of Injury (DOI)">CO-Need Date of Injury (DOI)</form:option>
									<form:option value="CO-Need ACF#">CO-Need ACF#</form:option>
									<form:option value="CO-Need Referring provider">CO-Need Referring provider</form:option>
									<form:option value="CO-Missing Surgery charges">CO-Missing Surgery charges</form:option>
									<form:option value="CO-Need Operative/ procedure report">CO-Need Operative/ procedure report</form:option>
									<form:option value="CO-Need NDC#">CO-Need NDC#</form:option>
									<form:option value="CO-Miscellaneous">CO-Miscellaneous</form:option>
								</optgroup>
								<optgroup label="Charge Entry">
									<form:option value="CE-For Documentation">CE-For Documentation</form:option>
									<form:option value="CE-Need CPT & ICD">CE-Need CPT & ICD</form:option>
									<form:option value="CE-Missing Super bill">CE-Missing Super bill</form:option>
									<form:option value="CE-Need PQRS Form">CE-Need PQRS Form</form:option>
									<form:option value="CE-Need BMI">CE-Need BMI</form:option>
									<form:option value="CE-Provide drug/Vaccine info">CE-Provide drug/Vaccine info</form:option>
									<form:option value="CE-Need Admin">CE-Need Admin</form:option>
									<form:option value="CE-Need Billing Provider">CE-Need Billing Provider</form:option>
									<form:option value="CE-Need Billing/Service provider">CE-Need Billing/Service provider</form:option>
									<form:option value="CE-Need CPT">CE-Need CPT</form:option>
									<form:option value="CE-Need ICD">CE-Need ICD</form:option>
									<form:option value="CE-Need LMP">CE-Need LMP</form:option>
									<form:option value="CE-Need Authorization#">CE-Need Authorization#</form:option>
									<form:option value="CE-Need Date of admission (DOA)">CE-Need Date of admission (DOA)</form:option>
									<form:option value="CE-Need Date of service (DOS)">CE-Need Date of service (DOS)</form:option>
									<form:option value="CE-Need Location/ POS">CE-Need Location/ POS</form:option>
									<form:option value="CE-Need Date of Injury (DOI)">CE-Need Date of Injury (DOI)</form:option>
									<form:option value="CE-Need ACF#">CE-Need ACF#</form:option>
									<form:option value="CE-Need Referring provider">CE-Need Referring provider</form:option>
									<form:option value="CE-Need Service provider">CE-Need Service provider</form:option>
									<form:option value="CE-Missing Surgery charges">CE-Missing Surgery charges</form:option>
									<form:option value="CE-Need Operative/ procedure report">CE-Need Operative/ procedure report</form:option>
									<form:option value="CE-Need NDC#">CE-Need NDC#</form:option>
									<form:option value="CE-Miscellaneous">CE-Miscellaneous</form:option>
								</optgroup>
								<optgroup label="Demo">
									<form:option value="DE-Policy# missing/invalid">DE-Policy# missing/invalid</form:option>
									<form:option value="DE-Need insurance info">DE-Need insurance info</form:option>
									<form:option value="DE-Provide patient demographic">DE-Provide patient demographic</form:option>
									<form:option value="DE-Miscellaneous">DE-Miscellaneous</form:option>
									<form:option value="DE-Argus to Call">DE-Argus to Call</form:option>
								</optgroup>
							</form:select>
						</div>
						<form:errors cssClass="invalid" path="reasonToReject" />
					</div>
					<div class="element-block">
						<form:label path="insuranceType">Insurance Type:<em class="mandatory">*</em></form:label>
						<div class="select-container2">
							<form:select path="insuranceType">
								<form:option value="">- Select a Insurance Type -</form:option>
								<c:forEach var="insuranceType" items="${insuranceTypes}">
									<form:option  value="${insuranceType.key}">${insuranceType.value}</form:option>
								</c:forEach>
							</form:select>
						</div>
						<form:errors cssClass="invalid" path="insuranceType" />					
					</div>
					<div class="clr"></div>
				</div>
				
				<div class="row-container">
					<div class="element-block">
						<form:label path="workFlow">Work Flow:</form:label>
						<span class="select-container1" >
							<form:select path="workFlow" cssClass="selectbox">
								<form:option value="0">-: Select Work flow :-</form:option>
								<form:option value="1">Internal</form:option>
								<form:option value="2">Argus TL</form:option>
								<form:option value="3">Dr.Office</form:option>
							</form:select>
						</span>
						<form:errors cssClass="invalid" path="workFlow" />
					</div>	
					<div class="element-block">
						&nbsp;
					</div>
					<div class="clr"></div>
				</div>

				<div class="row-container">
					<form:label path="remarks">Remarks:</form:label>
					<form:textarea path="remarks" cols="30" rows="5"/>
					<div class="clr"></div>
				</div>

				<c:if test="${operationType == 'Add'}" >
					<div class="row-container">
						<form:label path="addMore">Add more :</form:label>
					    <form:checkbox path="addMore" id = "addMore" value="true" />
					</div>
           		</c:if>
           					
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
						if(data != null && data.length > 0){
							
							if(data[0] == "err"){
								$('#msg').html(data[1]);
								$('#batchId').val(data[0]);
								$('#providerName').text('');
								$('#divProductivityForm').hide("slow");
								$('#dosFrom').text('');
								$('#dosTo').text('');
								$('#type').text('');
							}else{
								$('#divProductivityForm').show();
								$('#msg').html("");
								$('#batchId').val(data[0]);
								$('#dosFrom').text(data[1]);
								$('#dosTo').text(data[2]);
								$('#type').text(data[3]);
								$('#providerName').text(data[4]);
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