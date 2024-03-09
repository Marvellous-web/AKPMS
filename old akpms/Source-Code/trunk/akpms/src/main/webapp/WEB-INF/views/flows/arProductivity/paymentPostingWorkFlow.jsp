<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="downloadAction">
	<c:url value='/file/fileDownload' />
</c:set>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li><a href="<c:url value='/flows/arProductivity' />">&nbsp;AR Productivity List</a> &raquo;</li>
			<li>Payment Posting WorkFlow</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<div class="msg">
		<p class="success" id="msgP">${success}</p>
	</div>
	<form:form commandName="paymentPostingWorkFlow" id="form1" class="form-container" enctype="multipart/form-data" onsubmit="return validateForm();">
		<input type="hidden" name="arProductivity.id" value="${paymentPostingWorkFlow.arProductivity.id}">

		<div class="form-container-inner">
			<h3>${operationType} Payment Posting WorkFlow</h3>

			<%-- <div class="row-container">
				<div class="element-block" style="width: 100%">
					<span><strong>Patient Name: </strong> ${codingCorrectionLogWorkFlow.arProductivity.patientName}</span>
				</div>
				<div class="clr"></div>
			</div> --%>
			<%-- <span class="input-container">
				<label >Patient ID:</label> ${paymentPostingWorkFlow.arProductivity.patientAccNo}
			</span>
			<span class="input-container">
				<label >Patient Name:</label> ${paymentPostingWorkFlow.arProductivity.patientName}
			</span> --%>
			<div class="productivityInfo">
				<span class="subHeading">Productivity Information</span>
				<br/>
				<div class="row-container">
			    	<div class="element-block" >
				    	<span class="lbl">Patient Name:</span> <span>${paymentPostingWorkFlow.arProductivity.patientName}</span>
				    </div>
				    <div class="element-block" >
			    		<span class="lbl">Patient ID:</span> <span>${paymentPostingWorkFlow.arProductivity.patientAccNo}</span>
			    	</div>
			    	<div class="clr"></div>
			    </div>
			    
			    <div class="row-container">
			    	<div class="element-block" >
				    	<span class="lbl">Insurance:</span> <span>${paymentPostingWorkFlow.arProductivity.insurance.name}</span>
				    </div>
				    <div class="element-block" >
			    		<span class="lbl">DOS:</span> <span>${paymentPostingWorkFlow.arProductivity.dos}</span>
			    	</div>
			    	<div class="clr"></div>
			    </div>
			    
				<%-- <span class="input-container">
					<label >Insurance:</label> ${paymentPostingWorkFlow.arProductivity.insurance.name}
				</span>
				<span class="input-container">
					<label >Provider (Doctor Office):</label> ${paymentPostingWorkFlow.arProductivity.doctor.name}
				</span> --%>
				
				<div class="row-container">
			    	<div class="element-block" >
				    	<span class="lbl">Balance Amount:</span> <span><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${paymentPostingWorkFlow.arProductivity.balanceAmt}" /></span>
				    </div>
				    <div class="element-block" >
			    		<span class="lbl">Remarks:</span> <span>${paymentPostingWorkFlow.arProductivity.remark}</span>
			    	</div>
			    	<div class="clr"></div>
			    </div>
			   
		    </div>
		    
		    <!-- Editable information from productivity [start]-->
			    <div class="row-container">
				<div class="element-block" style="width: 100%">
					<form:label path="insurance.id">Insurance:<em class="mandatory">*</em></form:label>
					<span class="select-container">
						<form:select path="insurance.id" cssClass="selectbox">
							<form:option value="">-: Select Insurance :-</form:option>
							<form:options items="${insuranceList}" itemValue="id" itemLabel="name" />
						</form:select>
					</span>
					<div class="clr"></div>
					<form:errors cssClass="invalid" path="insurance.id" />
				</div>
				<div class="clr"></div>
			</div>
			
			<div class="row-container">
				<div class="element-block"  style="width: 100%">
					<form:label path="doctor.id">Provider (Doctor Office):<em class="mandatory">*</em></form:label>
					<span class="select-container" style="display:inline;">
						<form:select path="doctor.id" cssClass="selectbox">
							<form:option value="">-: Select Provider (Doctor Office) :-</form:option>
							<c:forEach var="doctor" items="${doctorList}">
								<form:option value="${doctor.id}">
									${doctor.name} <c:if test="${not empty doctor.parent}">	(${doctor.parent.name})	</c:if>
								</form:option>
							</c:forEach>
						</form:select>
					</span>
					<form:errors cssClass="invalid" path="doctor.id" />
				</div>
				<div class="clr"></div>
			</div>
				
			<div class="row-container">
				<div class="element-block">
					<form:label path="dos">DOS:</form:label>
					<span class="input_text">
						<span class="left_curve"></span>
						<form:input path="dos" maxlength="255" cssClass="mid" cssStyle="width: 100px;"/>
						<span class="right_curve"></span>
					</span>
					<form:errors cssClass="invalid" path="dos" />
				</div>
				<div class="element-block">
					<form:label path="balanceAmt">Balance Amount:<em class="mandatory">*</em></form:label>
					<span class="input_text">
						<span class="left_curve"></span>
						<form:input path="balanceAmt" maxlength="15" cssClass="mid numbersOnly" cssStyle="width: 100px;" />
						<span class="right_curve"></span>
					</span>
					<form:errors cssClass="invalid" path="balanceAmt" />
				</div>
				<div class="clr"></div>
			</div>
			
			<div class="row-container">
				<div class="element-block" style="width: 100%">
					<form:label path="remark">Remark:</form:label>
					<form:textarea rows="5" cols="20" path="remark" />
					<div class="clr"></div>
					<span id="remark_errors" class="invalid"></span>
				</div>
				<div class="clr"></div>
			</div>
			<!-- Editable information from productivity [end]-->
		    
			<%-- <span class="input-container">
				<label >Balance Amount:</label> ${paymentPostingWorkFlow.arProductivity.balanceAmt}
			</span>
			<span class="input-container">
				<label >Remark:</label> ${paymentPostingWorkFlow.arProductivity.remark}
			</span> --%>

			<%-- <div class="row-container">
				<div class="element-block" style="width: 100%">
					<span><strong>Remarks: </strong> ${codingCorrectionLogWorkFlow.arProductivity.remark}</span>
				</div>
				<div class="clr"></div>
			</div> --%>

			<div class="row-container">
		    	<div class="element-block" style="float: left;" id="isEob_block">
					<form:label path="eobAvailable">EOB Available:</form:label>
					<form:checkbox path="eobAvailable" id="isEob"/>
			    </div>
		    	<div class="clr"></div>
		    </div>
		    
		    <div class="row-container">
		    	<div class="element-block" >
					<form:label path="offSet">Offset:</form:label>
					<form:checkbox path="offSet" id="isOffSet"/>
			    </div>
		    	<div class="clr"></div>
		    </div>
		    
			<div class="row-container">
				<div class="element-block" style="width: 100%" id="copyCancelCheck_block">
					<form:label path="copyCancelCheck">Copy of cancel check:<em class="mandatory">*</em></form:label>
					<span class="input_text">
						<form:input	type="file" path="copyCancelCheck.attachedFile" size="44" cssClass="mid"/>
					</span>
					<form:errors cssClass="invalid" path="copyCancelCheck.attachedFile"  id="copyCancelCheckError"/>
				</div>
				
				<c:if test="${(not empty paymentPostingWorkFlow.copyCancelCheck.id) && (paymentPostingWorkFlow.copyCancelCheck.deleted == false)}">
					<div class="row-container" id="check_file_row">
						<div class="element-block" style="width: 100%">
							<form:label path="copyCancelCheck">Attached File:</form:label>
							<div class="download-container">
								${paymentPostingWorkFlow.copyCancelCheck.name}<a class="download" href="<c:url value='/file/fileDownload?id=${paymentPostingWorkFlow.copyCancelCheck.id}'/>" title="Download File"></a>
								<a href="javascript:void(0);" onclick='javascript:deleteCopyOfCheckFile(${paymentPostingWorkFlow.copyCancelCheck.id})' title="Delete File"><img title="Delete File" alt="Delete File" style="vertical-align: middle;" src="<c:url value="/static/resources/img/cross.png"/>" ></a> &nbsp;
							</div>
						</div>
					</div>
					<div class="clr"></div>
				</c:if>
				
				<div class="element-block" style="width: 100%" id="eob_block">
					<form:label path="eob">EOB:<em class="mandatory">*</em></form:label>
					<span class="input_text">
						<form:input	type="file" path="eob.attachedFile" size="44" cssClass="mid"/>
					</span>
					<form:errors cssClass="invalid" path="eob.attachedFile" id="eobError"/>
				</div>
				<div class="clr"></div>
				
				<c:if test="${(not empty paymentPostingWorkFlow.eob.id) && (paymentPostingWorkFlow.eob.deleted == false)}">
					<div class="row-container" id="eob_file_row">
						<div class="element-block" style="width: 100%">
							<form:label path="eob">Attached File:</form:label>
							<div class="download-container">
								${paymentPostingWorkFlow.eob.name}<a class="download" href="<c:url value='/file/fileDownload?id=${paymentPostingWorkFlow.eob.id}'/>" title="Download File"></a>
								<a href="javascript:void(0);" onclick='javascript:deleteEobFile(${paymentPostingWorkFlow.eob.id})' title="Delete File"><img title="Delete File" alt="Delete File" style="vertical-align: middle;" src="<c:url value="/static/resources/img/cross.png"/>" ></a> &nbsp;
							</div>
						</div>
					</div>
					<div class="clr"></div>
				</c:if>
			  </div>
			
			<div class="row-container">
				<div class="element-block" id="cpt_block">
					<form:label path="cpt">CPT<em class="mandatory">*</em></form:label>
					<span class="input_text">
						<span class="left_curve"></span>
							<form:input path="cpt" maxlength="200" cssClass="mid" cssStyle="width: 100px;"  readonly="${readOnly}"/>
						<span class="right_curve"></span>
					</span>
					<form:errors cssClass="invalid" path="cpt" id="cptError"/>
				</div>
				<div class="element-block" id="billedAmount_block">
					<form:label path="billedAmount">Billed Amount<em class="mandatory">*</em><br /> (in USD)</form:label>
					<span class="input_text">
						<span class="left_curve"></span>
							<form:input path="billedAmount" maxlength="15" cssClass="mid numbersOnly" cssStyle="width: 100px;" readonly="${readOnly}" />
						<span class="right_curve"></span>
					</span>
					<form:errors cssClass="invalid" path="billedAmount" id="billedAmountError"/>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block" id="primaryAmount_block">
					<form:label path="primaryAmount">Amount paid by primary<em class="mandatory">*</em><br /> (in USD)</form:label>
					<span class="input_text">
						<span class="left_curve"></span>
							<form:input path="primaryAmount" maxlength="15" cssClass="mid numbersOnly" cssStyle="width: 100px;"  readonly="${readOnly}"/>
						<span class="right_curve"></span>
					</span>
					<form:errors cssClass="invalid" path="primaryAmount" id="primaryAmountError"/>
				</div>
				<div class="element-block" id="secondaryAmount_block">
					<form:label path="secondaryAmount">Amount paid by secondary<em class="mandatory">*</em><br /> (in USD)</form:label>
					<span class="input_text">
						<span class="left_curve"></span>
							<form:input path="secondaryAmount" maxlength="15" cssClass="mid numbersOnly" cssStyle="width: 100px;" readonly="${readOnly}" />
						<span class="right_curve"></span>
					</span>
					<form:errors cssClass="invalid" path="secondaryAmount" id="secondaryAmountError"/>
				</div>
				<div class="clr"></div>
			</div>
			
			<div class="row-container">
				<div class="element-block" id="contractualAdj_block">
					<form:label path="contractualAdj">Contractual Adjustment<em class="mandatory">*</em><br /> (in USD)</form:label>
					<span class="input_text">
						<span class="left_curve"></span>
							<form:input path="contractualAdj" maxlength="15" cssClass="mid numbersOnly" cssStyle="width: 100px;"  readonly="${readOnly}"/>
						<span class="right_curve"></span>
					</span>
					<form:errors cssClass="invalid" path="contractualAdj" id="contractualAdjError"/>
				</div>
				<div class="element-block" id="bulkPaymentAmount_block">
					<form:label path="bulkPaymentAmount">Bulk Amount of payment<br /> (in USD)</form:label>
					<span class="input_text">
						<span class="left_curve"></span>
							<form:input path="bulkPaymentAmount" maxlength="15" cssClass="mid numbersOnly" cssStyle="width: 100px;" readonly="${readOnly}" />
						<span class="right_curve"></span>
					</span>
					<form:errors cssClass="invalid" path="bulkPaymentAmount" id="bulkPaymentAmountError"/>
				</div>
				<div class="clr"></div>
			</div>
			
			<div class="row-container">
				<div class="element-block" id="patientResponse_block">
					<form:label path="patientResponse">Patient Response<br /> (in USD)</form:label>
					<span class="input_text">
						<span class="left_curve"></span>
							<form:input path="patientResponse" maxlength="200" cssClass="mid numbersOnly" cssStyle="width: 100px;"  readonly="${readOnly}"/>
						<span class="right_curve"></span>
					</span>
					<form:errors cssClass="invalid" path="patientResponse" id="patientResponseError"/>
				</div>
				<div class="element-block" id="checkIssueDate_block">
					<form:label path="checkIssueDate">Check issue date<em class="mandatory">*</em></form:label>
					<span class="input_text">
						<span class="left_curve"></span>
							<form:input path="checkIssueDate" cssClass="mid" cssStyle="width: 100px;" readonly="true" />
						<span class="right_curve"></span>
					</span>
					<form:errors cssClass="invalid" path="checkIssueDate" id="checkIssueDateError"/>
				</div>
				<div class="clr"></div>
			</div>
			
			<div class="row-container">
				<div class="element-block" id="checkCashedDate_block">
					<form:label path="checkCashedDate">Date check cashed<!-- <em class="mandatory">*</em> --></form:label>
					<span class="input_text">
						<span class="left_curve"></span>
							<form:input path="checkCashedDate" cssClass="mid" cssStyle="width: 100px;"  readonly="true"/>
						<span class="right_curve"></span>
					</span>
					<form:errors cssClass="invalid" path="checkCashedDate" id="checkCashedDateError"/>
				</div>
				<div class="element-block" id="checkNo_block">
					<form:label path="checkNo">Check #<em class="mandatory">*</em></form:label>
					<span class="input_text">
						<span class="left_curve"></span>
							<form:input path="checkNo" maxlength="200" cssClass="mid" cssStyle="width: 100px;" readonly="${readOnly}" />
						<span class="right_curve"></span>
					</span>
					<form:errors cssClass="invalid" path="checkNo" id="checkNoError"/>
				</div>
				<div class="clr"></div>
			</div>
			
			<div class="row-container">
				<div class="element-block" style="width: 100%" id="addressCheckSend_block">
					<label id="addressCheckSend">Address check send to:<em class="mandatory">*</em></label>
					<form:textarea path="addressCheckSend" cols="20" rows="5"/>
					<!-- <span class="invalid" id="addressCheckSend" ></span> -->
					<form:errors cssClass="invalid" path="addressCheckSend" id="addressCheckSendError"/>
				</div>
				<div class="clr"></div>
			</div>

		 	<c:if test="${operationType == 'Add'}" >
				<div class="row-container">
					<form:label path="addMore">Add more :</form:label>
				    <form:checkbox path="addMore" id = "addMore" value="true" />
				</div>
           	</c:if>
           	
           	<c:choose>
           		<c:when test="${paymentPostingWorkFlow.status eq 'Reject'}">
					<div class="row-container">
						<div class="element-block" style="width: 100%">
							<form:label path="status">Status:<em class="mandatory">*</em></form:label>
							<span class="select-container">
								<form:select path="status" cssClass="selectbox">
									<form:option value="Reject">Reject</form:option>
									<form:option value="Pending">Pending</form:option>
									<form:option value="Completed">Completed</form:option>
								</form:select>
							</span>
							<div class="clr"></div>
							<form:errors cssClass="invalid" path="insurance.id" />
						</div>
						<div class="clr"></div>
					</div>
				</c:when>
				<c:otherwise>
					<form:hidden path="status"/>
				</c:otherwise>
           	</c:choose>
           	
           
			<div class="row-container">
				<div class="element-block" style="width: 100%">
					<label>&nbsp;</label>
					<input type="submit" title="Save" value="Save" class="login-btn" />
					
					
					<c:if test="${paymentPostingWorkFlow.status eq 'Approve' || paymentPostingWorkFlow.status eq 'Reject'}">
						<input type="submit" title="Completed"	value="Set as completed" class="login-btn" onclick="return completedValidationCheck(this.form);"/> 
					</c:if>
					
					<input type="button" title="Cancel" value="Cancel" class="login-btn" onclick="javascript:resetForm(this.form);" />
				</div>
				<div class="clr"></div>
			</div>
		</div>
		
		<input type="hidden" name="eobDeleted" id="eobDeleted" />
		<input type="hidden" name="copyOfCheckDeleted" id="copyOfCheckDeleted" />
	</form:form>
</div>

<script type="text/javascript">
$(function() {
	    $( "#checkIssueDate" ).datepicker();
	    $( "#checkCashedDate" ).datepicker();
	});



$('#isOffSet').change(function () {
    if ($(this).attr("checked")) {

    	$("#cpt_block").hide();
    	$("#cptError").html("");
        $("#primaryAmount_block").hide();
        $("#primaryAmountError").html("");
        $("#billedAmount_block").hide();
        $("#billedAmountError").html("");
        $("#secondaryAmount_block").hide();
        $("#secondaryAmountError").html("");
        $("#contractualAdj_block").hide();
        $("#contractualAdjError").html("");
        $("#patientResponse_block").hide();
        $("#patientResponseError").html("");
        $("#checkCashedDate_block").hide();
        $("#checkCashedDateError").html("");
        $("#checkIssueDate_block").hide();
        $("#checkIssueDateError").html("");
		$("#isEob_block").hide();
        $("#eob_block").show();
    	$("#copyCancelCheck_block").hide();
    	$("#check_file_row").hide();
    	$("#eob_file_row").show();
    	$("#addressCheckSend_block").hide();
    	$("#checkNo_block").hide();
    	$("#bulkPaymentAmount_block").hide();
  } else
	  {
	  isEobAvailabel();
// 	  $("#isEob_block").show();
// 	  $("#cpt_block").hide();
//       $("#primaryAmount_block").hide();
//       $("#billedAmount_block").hide();
//       $("#secondaryAmount_block").hide();
//       $("#contractualAdj_block").hide();
//       $("#patientResponse_block").hide();
//       $("#checkCashedDate_block").hide();
//       $("#checkIssueDate_block").hide();
//       $("#eob_block").show();
//   	$("#copyCancelCheck_block").show();
//   	$("#check_file_row").show();
//   	$("#eob_file_row").show();
//   	$("#addressCheckSend_block").show();
	  	
	  }
	  
});
	
 $('#isEob').change(function () {
    if ($(this).attr("checked")) {

    	$("#cpt_block").hide();
    	$("#cptError").html("");
        $("#primaryAmount_block").hide();
        $("#primaryAmountError").html("");
        $("#billedAmount_block").hide();
        $("#billedAmountError").html("");
        $("#secondaryAmount_block").hide();
        $("#secondaryAmountError").html("");
        $("#contractualAdj_block").hide();
        $("#contractualAdjError").html("");
        $("#patientResponse_block").hide();
        $("#patientResponseError").html("");
        $("#checkCashedDate_block").hide();
        $("#checkCashedDateError").html("");
        $("#checkIssueDate_block").hide();
        $("#checkIssueDateError").html("");
        $("#eob_block").show();
    	$("#copyCancelCheck_block").show();
    	$("#check_file_row").show();
    	$("#eob_file_row").show();
    }
    else{
    	$("#eob_block").hide();
    	$("#eobError").html("");
    	$("#copyCancelCheck_block").hide();
    	$("#copyCancelCheckError").html("");
    	$("#check_file_row").hide();
    	$("#eob_file_row").hide();
    	$("#cpt_block").show();
        $("#primaryAmount_block").show();
        $("#billedAmount_block").show();
        $("#secondaryAmount_block").show();
        $("#contractualAdj_block").show();
        $("#patientResponse_block").show();
        $("#checkCashedDate_block").show();
        $("#checkIssueDate_block").show();
        }

    $("#checkNoError").html("");
    $("#addressCheckSendError").html("");
});

function isEobAvailabel(){
	if ($("#isOffSet").attr("checked")) {
    	$("#cpt_block").hide();
    	$("#cptError").html("");
        $("#primaryAmount_block").hide();
        $("#primaryAmountError").html("");
        $("#billedAmount_block").hide();
        $("#billedAmountError").html("");
        $("#secondaryAmount_block").hide();
        $("#secondaryAmountError").html("");
        $("#contractualAdj_block").hide();
        $("#contractualAdjError").html("");
        $("#patientResponse_block").hide();
        $("#patientResponseError").html("");
        $("#checkCashedDate_block").hide();
        $("#checkCashedDateError").html("");
        $("#checkIssueDate_block").hide();
        $("#checkIssueDateError").html("");
		$("#isEob_block").hide();
        $("#eob_block").show();
    	$("#copyCancelCheck_block").hide();
    	$("#check_file_row").hide();
    	$("#eob_file_row").show();
    	$("#addressCheckSend_block").hide();
    	$("#checkNo_block").hide();
    	$("#bulkPaymentAmount_block").hide();
  }
	else{
		$("#isEob_block").show();
		$("#addressCheckSend_block").show();
		$("#checkNo_block").show();
		$("#bulkPaymentAmount_block").show();
		 if ($("#isEob").attr("checked")) {

		$("#cpt_block").hide();
        $("#primaryAmount_block").hide();
        $("#billedAmount_block").hide();
        $("#secondaryAmount_block").hide();
        $("#contractualAdj_block").hide();
        $("#patientResponse_block").hide();
        $("#checkCashedDate_block").hide();
        $("#checkIssueDate_block").hide();
        $("#eob_block").show();
    	$("#copyCancelCheck_block").show();
    	$("#check_file_row").show();
    	$("#eob_file_row").show();
    	        
    }
    else{
    	$("#eob_block").hide();
    	$("#copyCancelCheck_block").hide();
    	$("#check_file_row").hide();
    	$("#eob_file_row").hide();
    	$("#cpt_block").show();
        $("#primaryAmount_block").show();
        $("#billedAmount_block").show();
        $("#secondaryAmount_block").show();
        $("#contractualAdj_block").show();
        $("#patientResponse_block").show();
        $("#checkCashedDate_block").show();
        $("#checkIssueDate_block").show();
        
        }
	}
}

function deleteEobFile(fileId)
	{
		if(confirm("Are you sure you wish to delete this Attachment?")){
	 		$("#eob_file_row").hide('slow');
			$('#eobDeleted').val("true");
		}
	}

function deleteCopyOfCheckFile(fileId)
{
	if(confirm("Are you sure you wish to delete this Attachment?")){
		$("#check_file_row").hide('slow');
		$('#copyOfCheckDeleted').val("true");
	}

}

function validateForm(){

	if(null != $('#checkCashedDate').val() && null != $('#checkIssueDate').val()){
		if(Date.parse($('#checkIssueDate').val()) > Date.parse($('#checkCashedDate').val()) ){
			alert("Date check cashed must be after or equal to Check issue date. Please try again.");
			return false;
		}
	}
}

function completedValidationCheck(frm)
{

		$('#status').val('Completed');
		frm.submit();
		return true;
	
}
isEobAvailabel();
</script>
<script type="text/javascript" src="<c:url value='/static/resources/js/common.js'/>"></script>
