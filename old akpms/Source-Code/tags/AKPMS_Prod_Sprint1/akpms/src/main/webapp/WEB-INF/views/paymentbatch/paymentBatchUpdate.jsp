<%@page import="argus.util.Constants"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
	<sec:authorize ifAnyGranted="Standard User">
		<c:set var="updateOrReupdate" value="UPDATE"></c:set>
		<div class="section-content">
			<h3 class="heading-h3">Payment batch details</h3>
			<div class="row-container">
		    	<div class="element-block" >
			    	<span  class="lbl">Ticket/Batch No:</span> <span>${paymentBatch.id}</span>
			    </div>
		    	<div class="clr"></div>
		    </div>
		    
			<div class="row-container">
		    	<div class="element-block" >
			    	<span  class="lbl">Billing Month:</span> <span>${paymentBatch.billingMonth}/${paymentBatch.billingYear}</span>
			    </div>
			    <div class="element-block" >
			    	<span class="lbl">Group/Doctor Name:</span> <span>${paymentBatch.doctor.name}</span>
			    </div>
		    	<div class="clr"></div>
		    </div>

		    <div class="row-container">
		    	<div class="element-block" >
			    	<span  class="lbl">Payment Type:</span> <span>${paymentBatch.paymentType.name}</span>
			    </div>
			    <div class="element-block" >
			    	<span class="lbl">PH Doctor Name:</span> <span>${paymentBatch.phDoctorList.name}</span>
			    </div>
		    	<div class="clr"></div>
		    </div>

		    <div class="row-container">
		    	<c:if test="${not empty paymentBatch.adminIncome}">
			    	<div class="element-block" >
				    	<span class="lbl">Admin Income:</span> <span>${paymentBatch.adminIncome.name}</span>
				    </div>
			    </c:if>
			    <c:if test="${not empty paymentBatch.revenueType}">
				    <div class="element-block" >
				    	<span class="lbl">Revenue Type:</span> <span>${paymentBatch.revenueType.name}</span>
				    </div>
			    </c:if>
		    	<div class="clr"></div>
		    </div>

		    <div class="row-container">
		    	<div class="element-block" >
			    	<span  class="lbl">Insurance:</span> <span>${paymentBatch.insurance.name}</span>
			    </div>
		    	<div class="clr"></div>
		    </div>

		    <div class="row-container">
		    	<div class="element-block" >
			    	<span class="lbl">Money Source (in USD):</span>
			    	<div>
			    		<table style='width:100%'>
							<tr>
								<c:forEach var="payBatchMoneySources" items="${payBatchMoneySources}" varStatus="i">
									<c:if test="${payBatchMoneySources.amount gt 0}">
						    			<td><i>${payBatchMoneySources.moneySource.name}:</i> ${payBatchMoneySources.amount}</td>
									</c:if>
					    		</c:forEach>
							</tr>
						</table>
			    	</div>
			    </div>
		    	<div class="clr"></div>
		    </div>

		    <div class="row-container">
		    	<div class="element-block" >
			    	<span  class="lbl">Deposit Date:</span> <span><fmt:formatDate value="${paymentBatch.depositDate}" pattern="MM/dd/yyyy"/></span>
			    </div>
			    <div class="element-block" >
			    	<span class="lbl">ERA-Check #:</span> <span>${paymentBatch.eraCheckNo}</span>
			    </div>
		    	<div class="clr"></div>
		    </div>

		    <div class="row-container">
		    	<div class="element-block" >
			    	<span  class="lbl">Deposit Amount (In USD):</span> <span><fmt:formatNumber type="number" maxFractionDigits="2" value="${paymentBatch.depositAmount}" /></span>
			    </div>
			    <div class="element-block" >
			    	<span class="lbl">NDBA (In USD):</span> <span><fmt:formatNumber type="number" maxFractionDigits="2" value="${paymentBatch.ndba}" /></span>
			    </div>
		    	<div class="clr"></div>
		    </div>

		    <div class="row-container">
		    	<div class="element-block" >
			    	<span  class="lbl">Agency Money:</span> <span><fmt:formatNumber type="number" maxFractionDigits="2" value="${paymentBatch.agencyMoney}" /></span>
			    </div>
			    <div class="element-block" >
			    	<span class="lbl">Other Income:</span> <span><fmt:formatNumber type="number" maxFractionDigits="2" value="${paymentBatch.otherIncome}" /></span>
			    </div>
		    	<div class="clr"></div>
		    </div>

		    <div class="row-container">
		    	<div class="element-block" >
			    	<span  class="lbl">OLD Prior AR:</span> <span><fmt:formatNumber type="number" maxFractionDigits="2" value="${paymentBatch.oldPriorAr}" /></span>
			    </div>
			    <div class="element-block" >
			    	<span class="lbl">Refund Chk:</span> <span><fmt:formatNumber type="number" maxFractionDigits="2" value="${paymentBatch.refundChk}" /></span>
			    </div>
		    	<div class="clr"></div>
		    </div>

		    <div class="row-container">
		    	<div class="element-block" >
			    	<span  class="lbl">NSF/Sys Ref:</span> <span><fmt:formatNumber type="number" maxFractionDigits="2" value="${paymentBatch.nsfSysRef}" /></span>
			    </div>
			    <div class="element-block" >
			    	<span  class="lbl">Suspense Account:</span> <span><fmt:formatNumber type="number" maxFractionDigits="2" value="${paymentBatch.suspenseAccount}" /></span>
			    </div>

		    	<div class="clr"></div>
		    </div>

			<div class="row-container">
				<div class="element-block" >
			    	<span  class="lbl">CT Posted Total:</span> <span><fmt:formatNumber type="number" maxFractionDigits="2" value="${paymentBatch.ctPostedTotal}" /></span>
			    </div>
				<div class="element-block">
					<span class="lbl">Offset:</span> <span><fmt:formatNumber type="number" maxFractionDigits="2" value="${paymentBatch.offsetMinus}" /></span>
				</div>
				<div class="clr"></div>
			</div>
			
			<div class="row-container">
		    	<div class="element-block" style="width: 100%">
			    	<span  class="lbl">+/- Posting:</span> ${plusMinusPosting}<span></span>
			    </div>
		    	<div class="clr"></div>
		    </div>

		    <div class="row-container">
		    	<div class="element-block" style="width: 100%">
			    	<span  class="lbl">Comment:</span> <span>${paymentBatch.comment}</span>
			    </div>
		    	<div class="clr"></div>
		    </div>

			<c:if test="${not empty paymentBatch.revisedBy}">
				<div class="row-container">
					<div class="element-block">
						<span class="lbl">Revised By:</span> <span>${paymentBatch.revisedBy.firstName}</span>
					</div>
					<div class="element-block">
						<span class="lbl">Revised On:</span> <span><fmt:formatDate value="${paymentBatch.revisedOn}" pattern="MM/dd/yyyy"/></span>
					</div>
					<div class="clr"></div>
				</div>
			</c:if>

			<c:if test="${not empty paymentBatch.postedBy}">
			    <div class="row-container">
					<div class="element-block">
						<span class="lbl">Posted By:</span> <span>${paymentBatch.postedBy.firstName}&nbsp;${paymentBatch.postedBy.lastName}</span>
					</div>
					<div class="element-block">
						<span class="lbl">CT Posted Date:</span> <span><fmt:formatDate value="${paymentBatch.datePosted}" pattern="MM/dd/yyyy"/></span>
					</div>
					<div class="clr"></div>
				</div>
			</c:if>
		</div>
		<c:if test="${not batchPosted}">
		<c:if test="${readOnly}">
			<form:form commandName="paymentBatch" id="form1" class="form-container">
				<input type="hidden" name="hidDepositAmt" id="hidDepositAmt" value="${paymentBatch.depositAmount}" />
				<input type="hidden" name="hidNDBA" id="hidNDBA" value="${paymentBatch.ndba}" />
				<input type="hidden" name="revisedById"  value="${paymentBatch.revisedBy.id}" />
				<div class="form-container-inner">
					<h3>${operationType} Payment Batch</h3>
					<div class="row-container">
						<div class="element-block">
							<form:label path="manuallyPostedAmt">Manually: <br/>(in USD)</form:label>
							<div class="input_text">
								<span class="left_curve"></span>
								<form:input path="manuallyPostedAmt" maxlength="15" cssClass="mid numbersOnly" cssStyle="width: 100px;" onkeyup="javascript:totalCTPosted();" onchange="javascript:totalCTPosted();" onblur="javascript:totalCTPosted();"/>
								<span class="right_curve"></span>
							</div>
							<form:errors class="invalid" path="manuallyPostedAmt" />
						</div>

						<div class="element-block">
							<form:label path="otherIncome">Other Income:<br/> (in USD)</form:label>
							<div class="input_text">
								<span class="left_curve"></span>
								<form:input path="otherIncome" maxlength="15"
									cssClass="mid numbersOnly" cssStyle="width: 100px;"	 />
								<span class="right_curve"></span>
							</div>
							<form:errors class="invalid" path="otherIncome" />
						</div>
						<div class="clr"></div>
					</div>

					<div class="row-container">
						<div class="element-block">
							<form:label path="elecPostedAmt">Electronically: <br/>(in USD)</form:label>
							<div class="input_text">
								<span class="left_curve"></span>
								<form:input path="elecPostedAmt" maxlength="15" cssClass="mid numbersOnly" cssStyle="width: 100px;" onkeyup="javascript:totalCTPosted();" onchange="javascript:totalCTPosted();" onblur="javascript:totalCTPosted();"/>
								<span class="right_curve"></span>
							</div>
							<form:errors class="invalid" path="elecPostedAmt" />
						</div>
						<div class="element-block">
							<form:label path="oldPriorAr">OLD Prior AR:<br/> (in USD)</form:label>
							<div class="input_text">
								<span class="left_curve"></span>
								<form:input path="oldPriorAr" maxlength="15"
									cssClass="mid numbersOnly" cssStyle="width: 100px;"	/>
								<span class="right_curve"></span>
							</div>
							<form:errors class="invalid" path="oldPriorAr" />
						</div>
						<div class="clr"></div>
					</div>

					<div class="row-container">
						<div class="element-block">
							<label>CT Posted Total: <br /> (in USD) </label>
							<span id="spnCTTotalPosted">0</span>
						</div>
						<div class="element-block">
							<form:label path="agencyMoney">Agency Money:<br/> (in USD)</form:label>
							<div class="input_text">
								<span class="left_curve"></span>
								<form:input path="agencyMoney" maxlength="15"
									cssClass="mid numbersOnly" cssStyle="width: 100px;"	 />
								<span class="right_curve"></span>
							</div>
							<form:errors class="invalid" path="agencyMoney" />
						</div>
						<div class="clr"></div>
					</div>

					<div class="row-container">
						<div class="element-block" >
							<label><strong>+/- Posting:</strong><br/>&nbsp;&nbsp;(in USD)</label>
							<span id="plusMinusPosting">0</span>
						</div>
						<div class="element-block">
							<form:label path="suspenseAccount">Suspense Account: <br/>(in USD)</form:label>
							<div class="input_text">
								<span class="left_curve"></span>
								<form:input path="suspenseAccount" maxlength="15" cssClass="mid numbersOnly" cssStyle="width: 100px;" />
								<span class="right_curve"></span>
							</div>
							<form:errors class="invalid" path="suspenseAccount" />
						</div>
						<div class="clr"></div>
					</div>

					<div class="row-container">
						<div class="element-block">
							<form:label path="offsetMinus">Offset:</form:label>
							<div class="input_text">
								<b style="float:left; padding-top:7px;">+</b>
								<span class="left_curve"></span>
								<form:input path="offsetMinus" maxlength="15" cssClass="mid numbersOnly" cssStyle="width: 26px;" onkeyup="checkOffsetAmount();" onblur="checkOffsetAmount();" />
								<span class="right_curve"></span>
							</div>
							&nbsp;&nbsp;
							<div class="input_text">
								<b style="float:left; padding-top:7px;">-</b>
								<span class="left_curve"></span>
								<form:input path="offsetPlus" maxlength="15" cssClass="mid numbersOnly" cssStyle="width: 26px;"	readonly="true" />
								<span class="right_curve"></span>
							</div>
							<form:errors class="invalid" path="offsetMinus" />
							<form:errors class="invalid" path="offsetPlus" />
						</div>
						<div class="clr"></div>
					</div>

					<c:if test="${not empty paymentBatch.postedBy}">
						<div class="row-container">
							<div class="element-block">
								<form:label path="postedBy">Posted By:<em class="mandatory">*</em></form:label>
								<div class="select-container2">
									<form:select path="postedBy.id" cssClass="selectbox">
										<form:option value="0">-:User:-</form:option>
										<c:forEach var="user" items="${userList}">
											<form:option value="${user.id}">${user.firstName} ${user.lastName}</form:option>
										</c:forEach>
									</form:select>
									<form:errors class="invalid" path="postedBy.id" />
								</div>
							</div>
							<div class="element-block">
								<form:label path="datePosted">CT Posted Date:<em class="mandatory">*</em></form:label>
								<div class="input_text">
									<span class="left_curve"></span>
									<form:input path="datePosted" maxlength="50" cssClass="mid" cssStyle="width: 100px;" readonly="true" />
									<span class="right_curve"></span>
								</div>
								<form:errors class="invalid" path="datePosted" />
							</div>
							<div class="clr"></div>
						</div>
						<c:set var="updateOrReupdate" value="Re-UPDATE"></c:set>
					</c:if>
					<div class="row-container">
						<label>&nbsp;</label>
							<input type="submit" title="${updateOrReupdate}" onclick="return checkDepositAmt();" value="${updateOrReupdate}" class="login-btn" />
							<input type="button" title="Cancel"	value="Cancel" class="login-btn" onclick="javascript:resetForm(this.form);" />
						<div class="clr"></div>
					</div>
				</div>
			</form:form>
		</c:if>
		</c:if>
	</sec:authorize>
</div>

<script type="text/javascript">
	$(function() {
	    $( "#datePosted" ).datepicker();
	});

	function checkOffsetAmount(){
		$("#offsetPlus").val($("#offsetMinus").val());
	}

	function checkDepositAmt(){
		if((parseFloat($('#manuallyPostedAmt').val()) + parseFloat($('#elecPostedAmt').val())) > (parseFloat($('#hidDepositAmt').val()) + parseFloat($('#hidNDBA').val())) ){
			alert("CT posted total should be equal or less than deposit amount/NDBA.");
			return false;
		}
		return true;
	}

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
			var amount = "${paymentBatch.depositAmount}";
			if(amount == 0){
				depositAmount = parseFloat('${paymentBatch.ndba}').toFixed(2);
			}else{
				depositAmount = parseFloat('${paymentBatch.depositAmount}').toFixed(2);
			}
		}
		$('#plusMinusPosting').html("<strong>"+parseFloat(depositAmount-totalCTPostedAmt).toFixed(2)+"</strong>");
     	$('#spnCTTotalPosted').html("<strong>"+ parseFloat(totalCTPostedAmt).toFixed(2)+"</strong>");
    }
	totalCTPosted();
</script>
<script type="text/javascript" src="<c:url value='/static/resources/js/common.js'/>"></script>
