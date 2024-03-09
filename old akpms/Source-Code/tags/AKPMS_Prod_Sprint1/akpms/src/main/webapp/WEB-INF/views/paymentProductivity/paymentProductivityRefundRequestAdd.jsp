<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li>&nbsp;Payment Productivity Refund Request</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<div class="form-container">
		<div class="form-container-inner">
			<h3>${operationType} Refund Request</h3>
			<div id="msg" class="error"></div>
			<span class="input-container">
				<label>Ticket Number<em class="mandatory">*</em></label>
				<c:choose>
					<c:when test="${hide!='hide'}">
						<form method="get" id="frmGetBatchDetails">
							<span class="input_text">
								<span class="left_curve"></span>
									<input type="text" id="ticketNumber" name="ticketNumber"  maxlength="200" class="mid" style="width: 100px;" value="${paymentProductivityRefundRequest.paymentBatch.id}" />
								<span class="right_curve"></span>
							</span>
							<input type="submit" title="Submit"	value="${getButton}" class="login-btn" />
						</form>
					</c:when>
					<c:otherwise>
					${paymentProductivityRefundRequest.paymentBatch.id}
					</c:otherwise>
				</c:choose>
			</span>

			 <div class="row-container">
		    	<div class="element-block" >
			    	<label>Doctor:</label>
			    </div>
			    <div class="element-block" >
		    		<label id="doctor" ></label>
		    	</div>
		    	<div class="clr"></div>
		    </div>

		    <div class="row-container">
			    <div class="element-block" >
			    	<label>Insurance:</label>
			    </div>
			    <div class="element-block" >
			    	<label id="insurance" ></label>
			    </div>
			    <div class="clr"></div>
		    </div>

		    <div class="row-container">
		    	<div class="element-block" >
			    	<label>Deposit Amount:</label>
			    </div>
			    <div class="element-block" >
			    	<label id="depositAmount" ></label>
			    </div>
			    <div class="clr"></div>
		    </div>

		</div>
	</div>

	<form:form commandName="paymentProductivityRefundRequest" id="form1" class="form-container" onsubmit="return checkDetails();">
		<div class="form-container-inner">

			<div class="row-container">

				<label>Date</label>
				<c:choose>
					<c:when test="${not empty paymentProductivityRefundRequest.id}">
							${paymentProductivityRefundRequest.createdOn }
					</c:when>
					<c:otherwise>
						${currentDate}
					</c:otherwise>
				</c:choose>
				<div class="clr"></div>
			</div>


			<div class="row-container" >
				<form:label path="doctor.id">Group/Doctor Name<em class="mandatory">*</em></form:label>
				<div>
					<form:select id="doctorId" path="doctor.id" cssClass="selectbox">
							<form:option value="0">-: Select Group/Doctor :-</form:option>
							<form:options items="${doctorList}" itemValue="id" itemLabel="name" />
					</form:select>
				</div>
				<form:errors class="invalid" path="doctor.id" />

				<div class="clr"></div>
			</div>


		<div class="row-container">
			<div class="element-block" >
				<form:label path="patientName">Patient Name<em class="mandatory">*</em></form:label>
				<div class="input_text">
					<span class="left_curve"></span>
						<form:input path="patientName" maxlength="200" cssClass="mid" cssStyle="width: 75px;"  />
					<span class="right_curve"></span>
				</div>
				<form:errors class="invalid" path="patientName" />
			</div>
			<div class="element-block" >
				<form:label path="accountNumber">Account Number<em class="mandatory">*</em></form:label>
				<div class="input_text">
					<span class="left_curve"></span>
						<form:input path="accountNumber" maxlength="200" cssClass="mid" cssStyle="width: 75px;"  />
					<span class="right_curve"></span>
				</div>
				<form:errors class="invalid" path="accountNumber" />
			</div>
			<div class="clr"></div>
          </div>


		<div class="row-container">
			<div class="element-block" >
				<form:label path="creditAmount">Credit Amount<em class="mandatory">*</em>(In USD)</form:label>
				<div class="input_text">
					<span class="left_curve"></span>
					<form:input path="creditAmount" maxlength="15" cssClass="mid numbersOnly" cssStyle="width: 75px;"  />
					<span class="right_curve"></span>
				</div>
				<form:errors class="invalid" path="creditAmount" />
			</div>

			<div class="element-block" >
				<form:label path="transactionDate">Transaction Date<em class="mandatory">*</em></form:label>
				<div class="input_text">
					<span class="left_curve"></span>
					<form:input path="transactionDate" maxlength="200" cssClass="mid" cssStyle="width: 75px;"  />
					<span class="right_curve"></span>
				</div>
				<form:errors class="invalid" path="transactionDate" />
			</div>
			<div class="clr"></div>
          </div>

			<div class="row-container">
				<form:label path="timeTaken">Time Taken</form:label>
				<div class="input_text">
					<span class="left_curve"></span>
					<form:input path="timeTaken" maxlength="200" cssClass="mid" cssStyle="width: 75px;"  />
					<span class="right_curve"></span>
				</div>
				<form:errors class="invalid" path="timeTaken" />
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<form:label path="findings">Findings</form:label>
				<form:textarea rows="8" cols="20" path="findings" />
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<form:label path="resolutionOrRemark">Resolution/remark</form:label>
				<form:textarea rows="8" cols="20" path="resolutionOrRemark" />
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<form:label path="requestDate">Date</form:label>
				<div class="input_text">
					<span class="left_curve"></span>
					<form:input path="requestDate" maxlength="200" cssClass="mid" cssStyle="width: 75px;"  />
					<span class="right_curve"></span>
				</div>
				<form:errors class="invalid" path="requestDate" />
			</div>
			<form:hidden path="paymentBatch.id" id="paymentBatchId"/>
			<br/>
			<br/>
			<div class="row-container">
			 <label>&nbsp;</label>
				<input type="submit" title="Save"
				value="${buttonName}" class="login-btn" /> <input type="button"
				title="Cancel" value="Cancel" class="login-btn"  onclick="javascript:resetForm(this.form);" /> <c:if
					test="${operationType == 'Edit'}">

				</c:if>
			</div>


		</div>
	</form:form>
</div>


<script type="text/javascript">
	$('#frmGetBatchDetails').submit(function() {
		$('#form1')[0].reset();
		$.uniform.update();
		clearErrors();
		getDetail();
		return false;
	});
	
	$(function() {
	    $( "#transactionDate" ).datepicker();
	    $( "#requestDate" ).datepicker();
	});
	
	function getDetail(){
		 var batchId = $('#ticketNumber').val();
		 if(batchId == null || $.trim(batchId) == ''){
			 $('#msg').html("Ticket number cannot be left blank.");
			 $('#paymentBatchId').val("");
			 $('#doctor').text('');
			 $('#insurance').text('');
			 $('#depositAmount').text('');
		 }
	 else
		 {
		$.ajax({
			type : "POST",
			dataType : "json",
			url : contextPath+"/paymentproductivityrefundrequest/getticketdetail/json",
			data : "batchId="+batchId,
			success : function(data) {
				if(data != null && data.length > 0)
				{
					if(data[0] == "err")
					{
						$('#msg').html(data[1]);
						$('#paymentBatchId').val("");
						$('#doctor').text('');
						$('#insurance').text('');
						$('#depositAmount').text('');
					}
					else
					{
						$('#msg').html("");
						$('#paymentBatchId').val(data[0].id);
						$('#doctor').text(data[0].doctor);
						$('#insurance').text(data[0].insurance);
						$('#depositAmount').text(data[0].depositAmount);
					}
				}

			},
			error: function(data){
				//alert (data);
				//$("#modificationSummaryContent").html("Error:"+data);
			}
		});
		 }
	}

	function checkDetails()
	{
		var batchId = $('#paymentBatchId').val();
		if(batchId == null || $.trim(batchId) == ''){
			alert("please get ticket detail first");
			return false;
		}
		return true;
	}

	var batchId = $('#paymentBatchId').val();
	if(batchId == null || $.trim(batchId) == ''){
	}
	else
		{var paymentProdId = "${paymentProductivityRefundRequest.id}";
		if(paymentProdId == null || $.trim(paymentProdId) == ''){
			getDetail();
			}
		else{
			$('#doctor').text("${paymentProductivityRefundRequest.paymentBatch.doctor.name}");
			$('#insurance').text("${paymentProductivityRefundRequest.paymentBatch.insurance.name}");
			var amount = "${paymentProductivityRefundRequest.paymentBatch.depositAmount}";
			if(amount == 0)
				{
					$('#depositAmount').text("${paymentProductivityRefundRequest.paymentBatch.ndba}");
				}
				else
				{
					$('#depositAmount').text("${paymentProductivityRefundRequest.paymentBatch.depositAmount}");
				}
			}
		}
</script>
<script type="text/javascript"
		src="<c:url value='/static/resources/js/common.js'/>"></script>
