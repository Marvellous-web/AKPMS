<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li><a href="<c:url value='/paymentproductivitynonera' />">&nbsp;Payment Productivity List</a>&raquo;</li>
			<li>&nbsp;${operationType} ERA Productivity</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<div class="section-content">
		<h3 class="heading-h3">Payment Batch Details</h3>
		<div id="msg" class="error"></div>
		<div class="row-container">
			<c:choose>
				<c:when test="${hide!='hide'}">
					<form method="get" id="frmGetBatchDetails">
						<div class="element-block" style="width:100%">
							<label><strong>Ticket Number</strong><em class="mandatory">*</em></label>
							<div class="input_text" >
								<span class="left_curve"></span>
									<input type="text" id="ticketNumber" name="ticketNumber" maxlength="15" class="mid integerOnly" style="width: 100px;" value="${paymentProductivity.paymentBatch.id}" />
								<span class="right_curve"></span>
							</div>
							<input type="submit" title="Submit" value="${getButton}" class="btn" />
						</div>
					</form>
				</c:when>
				<c:otherwise>
		    		<div class="element-block" >
				    	<span class="lbl">Ticket Number:</span> <span>${paymentProductivity.paymentBatch.id}</span>
				    </div>
				</c:otherwise>
			</c:choose>
			<div class="clr"></div>
		</div>

		<div class="row-container">
	    	<div class="element-block" >
		    	<span  class="lbl">Date Received:</span> <span id="dateRecieved" ></span>
		    </div>
		    <div class="element-block" >
		    	<span class="lbl">Dr. Office:</span> <span id="doctor" ></span>
		    </div>
	    	<div class="clr"></div>
	    </div>

		<div class="row-container">
	    	<div class="element-block" >
		    	<span class="lbl">Insurance:</span> <span id="insurance" ></span>
		    </div>
		    <div class="element-block" >
	    		<span class="lbl">Deposit Amount(In USD):</span> <span id="depositAmount" ></span>
	    	</div>
	    	<div class="clr"></div>
	    </div>
	</div>

	<form:form commandName="paymentProductivity" id="form1">
		<div class="form-container" id="divProductivityForm">
			<div class="form-container-inner" >
				<h3>${operationType} ERA Productivity</h3>
				<div class="row-container">
				<div class="element-block" >
					<form:label path="chkNumber">CK Number<em class="mandatory">*</em></form:label>
					<div class="input_text">
						<span class="left_curve"></span>
							<form:input path="chkNumber" maxlength="200" cssClass="mid" cssStyle="width: 75px;"  />
						<span class="right_curve"></span>

					</div>
					<form:errors class="invalid" path="chkNumber" />
				</div>
				<div class="element-block" >
					<form:label path="paymentBatch.otherIncome">Other income <br/>&nbsp;&nbsp;&nbsp;(in USD)</form:label>
					<div class="input_text">
						<span class="left_curve"></span>
						<form:input path="paymentBatch.otherIncome" maxlength="15" cssClass="mid numbersOnly" cssStyle="width: 75px;"  />
						<span class="right_curve"></span>

					</div>
					<form:errors class="invalid" path="paymentBatch.otherIncome" />
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block" >
					<form:label path="paymentBatch.manuallyPostedAmt">1. Manually<br/>&nbsp;&nbsp;&nbsp;(in USD)</form:label>
					<div class="input_text">
						<span class="left_curve"></span>
							<form:input path="paymentBatch.manuallyPostedAmt" id="manuallyPostedAmt"  maxlength="15" cssClass="mid numbersOnly" cssStyle="width: 75px;" onkeyup="javascript:totalCTPosted();" onchange="javascript:totalCTPosted();" onblur="javascript:totalCTPosted();"/>
						<span class="right_curve"></span>
					</div>
					<form:errors class="invalid" path="paymentBatch.manuallyPostedAmt" />
				</div>
				<div class="element-block" >
					<form:label path="paymentBatch.agencyMoney">Agency Money <br/>&nbsp;&nbsp;&nbsp;(in USD)</form:label>
					<div class="input_text">
						<span class="left_curve"></span>
							<form:input path="paymentBatch.agencyMoney" maxlength="15" cssClass="mid numbersOnly" cssStyle="width: 75px;"  />
						<span class="right_curve"></span>

					</div>
					<form:errors class="invalid" path="paymentBatch.agencyMoney" />
				</div>
	           <div class="clr"></div>
			</div>

			<div class="row-container">
				 <div class="element-block" >
					<form:label path="paymentBatch.elecPostedAmt">2. Electronically<em class="mandatory">*</em><br/>&nbsp;&nbsp;&nbsp;(in USD)</form:label>
					<div class="input_text">
						<span class="left_curve"></span>
							<form:input path="paymentBatch.elecPostedAmt" id="elecPostedAmt" maxlength="15" cssClass="mid numbersOnly" cssStyle="width: 75px;" onkeyup="javascript:totalCTPosted();" onchange="javascript:totalCTPosted();" onblur="javascript:totalCTPosted();"/>
						<span class="right_curve"></span>

					</div>
					<form:errors class="invalid" path="paymentBatch.elecPostedAmt" />
				</div>
				<div class="element-block" >
					<form:label path="paymentBatch.oldPriorAr">Old prior AR <br/>&nbsp;&nbsp;&nbsp;(in USD)</form:label>
					<div class="input_text">
						<span class="left_curve"></span>
						<form:input path="paymentBatch.oldPriorAr" maxlength="15" cssClass="mid numbersOnly" cssStyle="width: 75px;"  />
						<span class="right_curve"></span>

					</div>
					<form:errors class="invalid" path="paymentBatch.oldPriorAr" />
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block" >
					<span id="spnCTTotalPosted">0</span>
					<form:errors class="invalid" path="ctTotalPosted" />
				</div>
				<div class="element-block" >
					<label><strong>+/- Posting:</strong><br/>&nbsp;&nbsp;(in USD)</label>
					<span id="plusMinusPosting">0</span>
				</div>
				<div class="clr"></div>
	        </div>

			<div class="row-container">
				<div class="element-block" >
					<form:label path="paymentBatch.suspenseAccount">3. Suspense Amount <br/>&nbsp;&nbsp;&nbsp;(in USD)</form:label>
					<div class="input_text">
						<span class="left_curve"></span>
							<form:input path="paymentBatch.suspenseAccount" maxlength="15" cssClass="mid numbersOnly" cssStyle="width: 75px;"  />
						<span class="right_curve"></span>

					</div>
				</div>
				<div class="element-block" >
					<form:label path="manualTransaction">Manual transaction</form:label>
					<div class="input_text">
						<span class="left_curve"></span>
						<form:input path="manualTransaction" maxlength="8" cssClass="mid integerOnly" cssStyle="width: 75px;"  />
						<span class="right_curve"></span>
					</div>
					<form:errors class="invalid" path="manualTransaction" />
				</div>
				<div class="clr"></div>
	        </div>

			<div class="row-container">

				<div class="element-block" >
					<form:label path="paymentBatch.datePosted">Date posted<em class="mandatory">*</em></form:label>
					<div class="input_text">
						<span class="left_curve"></span>
							<form:input path="paymentBatch.datePosted" id="datePosted" maxlength="200" cssClass="mid" cssStyle="width: 75px;"  readonly="true"/>
						<span class="right_curve"></span>
					</div>
					<form:errors class="invalid" path="paymentBatch.datePosted" />
				</div>

				<div class="element-block" >
					<form:label path="elecTransaction">Electronic transaction<em class="mandatory">*</em></form:label>
					<div class="input_text">
						<span class="left_curve"></span>
							<form:input path="elecTransaction" maxlength="8" cssClass="mid integerOnly" cssStyle="width: 75px;"  />
						<span class="right_curve"></span>
					</div>
					<form:errors class="invalid" path="elecTransaction" />
				</div>

				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block" >
					<form:label path="time">Time (min)<em class="mandatory"></em></form:label>
					<div class="input_text">
						<span class="left_curve"></span>
							<form:input path="time" maxlength="8" cssClass="mid integerOnly" cssStyle="width: 75px;"  />
						<span class="right_curve"></span>

					</div>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<form:label path="remark">Remarks</form:label>
				<form:textarea rows="5" cols="20" path="remark" />
				<form:errors class="invalid" path="remark" />
				<div class="clr"></div>
			</div>
			<c:if test="${operationType == 'Add'}">
            <div class="row-container">
				<form:label path="offset">Offset</form:label>
			    <form:checkbox path="offset" id = "offset" value="true" onclick="javascript:checkOffsetWithWorkflow();" />
			</div>
			</c:if>
			 <div  class="row-container">
				<form:label path="workFlowId">Work Flow<em class="mandatory">*</em></form:label>
				<div>
					<form:select path="workFlowId" id="workFlowId" cssClass="selectbox" onchange="javascript:checkOffsetWithWorkflow();">
						<form:option value="0">-: Select Work flow :-</form:option>
						<form:option value="1">To AR IPA FFS / HMO </form:option>
						<form:option value="2">To AR FFS</form:option>
						<form:option value="3">To AR CEP</form:option>
						<form:option value="4">To AR MCL</form:option>
			  <!-- 	   <form:option value="5">OFFSET</form:option>  -->
						<form:option value="6">Query to TL</form:option>
					</form:select>
				</div>
				<form:errors class="invalid" path="workFlowId" />
			</div>
			<br/>
			<div class="row-container">
			 	<label>&nbsp;</label>
			 	<form:hidden path="paymentBatch.id" id="paymentBatchId"/>
				<input type="submit" title="Save / Update Insurance" value="${buttonName}" class="login-btn" />
				<input type="button" title="Cancel" value="Cancel" class="login-btn"  onclick="javascript:resetForm(this.form);setTicketNumber();totalCTPosted();" />
			</div>
		</div>
		</div>
	</form:form>
</div>

<c:if test="${empty paymentProductivity.id}">
	<script type="text/javascript">
		$(document).ready(function(){
			$('#divProductivityForm').hide();
		});
		function setTicketNumber(){
			$('#paymentBatchId').val($('#ticketNumber').val());
		}
	</script>
</c:if>

<c:if test="${not empty paymentProductivity.id}">
<script type="text/javascript">
function setTicketNumber(){
	return true;
}
</script>
</c:if>

<script type="text/javascript">
var isQueryTOTL = false;
</script>
<c:if test="${not empty paymentProductivity.paymentProdQueryToTL}">
	<script type="text/javascript">
		isQueryTOTL = true;
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
	    $( "#datePosted" ).datepicker();
	});

	function totalCTPosted(){
		manuallyPostedAmt = $('#manuallyPostedAmt').val();
	    elecPostedAmt = $('#elecPostedAmt').val();
	    if(manuallyPostedAmt == "" || manuallyPostedAmt == "."){
	    	manuallyPostedAmt = 0.0;
		}
		if(elecPostedAmt == "" || elecPostedAmt == "."){
			elecPostedAmt = 0.0;
		}
		totalCTPostedAmt = parseFloat(manuallyPostedAmt) + parseFloat(elecPostedAmt);
		var depositAmount = parseFloat($('#depositAmount').text()).toFixed(2);
		if(isNaN(depositAmount)){
			var amount = "${paymentProductivity.paymentBatch.depositAmount}";
			if(amount == 0){
				depositAmount = parseFloat('${paymentProductivity.paymentBatch.ndba}').toFixed(2);
			}else{
				depositAmount = parseFloat('${paymentProductivity.paymentBatch.depositAmount}').toFixed(2);
			}
		}
		$('#plusMinusPosting').html("<strong>"+parseFloat(depositAmount-totalCTPostedAmt).toFixed(2)+"</strong>");
     	$('#spnCTTotalPosted').html("Total posted amount:   <strong>"+ parseFloat(totalCTPostedAmt).toFixed(2)+"</strong>" +"<br/>&nbsp;&nbsp;&nbsp; (in USD)");
    }

	function checkOffsetWithWorkflow(){
		var offsetChecked = $('#offset').is(":checked");
		var workflow = $('#workFlowId').find(":selected").text();
		var totalProdOffsets = '${totalProdOffsets}';
		if(workflow == "Query to TL" && offsetChecked == true){
			alert("Offset option can't be selected with query to TL workflow.");
			var chkOffset = $("#offset").attr("checked", false);
			$.uniform.update(chkOffset);
		}
		if(workflow == "Query to TL" && parseInt(totalProdOffsets)>0){
			alert("Workflow cannot be changed as there are offsets attached corresponding to the given ticket number.");
			var chkDropdown = $('#workFlowId').val("${paymentProductivity.workFlowId}");
			$.uniform.update(chkDropdown);
			}
	}


	function getDetail(){
	 	var batchId = $('#ticketNumber').val();
		 if(batchId == null || $.trim(batchId) == ''){
			$('#msg').html("Ticket number cannot be left blank.");
		 	$('#paymentBatchId').val("");
			$('#dateRecieved').text('');
			$('#doctor').text('');
			$('#insurance').text('');
			$('#depositAmount').text('');
			$('#divProductivityForm').hide("slow");
		 }else{
			$.ajax({
				type : "POST",
				dataType : "json",
				url : contextPath+"/paymentproductivityERA/getticketdetail/json",
				data : "batchId="+batchId,
				success : function(data) {
					if(data != null && data.length > 0){
						if(data[0] == "err"){
							$('#msg').html(data[1]);
							$('#paymentBatchId').val("");
							$('#dateRecieved').text('');
							$('#doctor').text('');
							$('#insurance').text('');
							$('#depositAmount').text('');
							$('#divProductivityForm').hide("slow");
						}else{
							$('#divProductivityForm').show();
							$('#msg').html("");
							$('#paymentBatchId').val(data[0].id);
							$('#dateRecieved').text('${currentDate}');
							$('#doctor').text(data[0].doctor);
							$('#insurance').text(data[0].insurance);
							$('#depositAmount').text( parseFloat(data[0].depositAmount).toFixed(2));
							totalCTPosted();
						}
					}
				},error: function(data){
					$('#divProductivityForm').hide("slow");
					//alert (data);
				}
			});
		}
	}

	function checkBatchDetails(){
		if($('#paymentBatchId').val() > 0){
			var paymentProdId = "${paymentProductivity.id}";

			if(paymentProdId == null || $.trim(paymentProdId) == ''){
				getDetail();
			}else{
				$('#divProductivityForm').show();
				if(isQueryTOTL)	{
					$('#workFlowId').attr("disabled", true);
				}
				$('#doctor').text("${paymentProductivity.paymentBatch.doctor.name}");
				$('#insurance').text("${paymentProductivity.paymentBatch.insurance.name}");
				$('#dateRecieved').text('${currentDate}');
				var amount = "${paymentProductivity.paymentBatch.depositAmount}";
				if(amount == 0){
					$('#depositAmount').text("${paymentProductivity.paymentBatch.ndba}");
				}else{
					$('#depositAmount').text("${paymentProductivity.paymentBatch.depositAmount}");
				}
			}
		}

	}

	checkBatchDetails();
	totalCTPosted();

</script>
<script type="text/javascript" src="<c:url value='/static/resources/js/common.js'/>"></script>
