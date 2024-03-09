<%@page import="argus.util.Constants"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<c:set var="downloadAction">
	<c:url value='/paymentoffsetmanager/fileDownload' />
</c:set>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
		 	<li><a href="<c:url value='/paymentoffsetmanager' />">&nbsp;Offset Posting List</a> &raquo;</li>
			<li>&nbsp;${operationType} Offset posting by OFFSET Manager</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<div class="msg">
		<p class="success" id="msgP">${success}</p>
	</div>

	<div class="section-content">
		<h3 class="heading-h3">Batch/Ticket Details</h3>
		<div id="msg" class="error"></div>
		<div class="row-container">
			<c:choose>
				<c:when test="${empty paymentOffsetManager.id}">
					<form method="get" id="frmGetBatchDetails">
						<div class="element-block" style="width: 100%">
							<label><strong>Ticket Number</strong><em class="mandatory">*</em></label>
							<div class="input_text">
								<span class="left_curve"></span>
								<input type="text" id="ticketNo" name="ticketNo" maxlength="15" class="mid integerOnly"	style="width: 100px;"  value="${batchId}" />
								<span class="right_curve"></span>
							</div>
							<input type="submit" title="Submit" value="Get Detail" class="btn" />
						</div>
					</form>
				</c:when>
				<c:otherwise>
					<div class="element-block" >
				    	<span class="lbl">Ticket Number:</span> <span>${paymentOffsetManager.paymentBatch.id}</span>
				    </div>
				</c:otherwise>
			</c:choose>
			<div class="clr"></div>
		</div>
		<div class="row-container">
		    <div class="element-block" >
		    	<span class="lbl">Dr. Office:</span> <span id="doctor" >${paymentOffsetManager.paymentBatch.doctor.name}</span>
		    </div>
	    	<div class="clr"></div>
	    </div>

		<div class="row-container">
	    	<div class="element-block" >
		    	<span class="lbl">Insurance:</span> <span id="insurance" >${paymentOffsetManager.paymentBatch.insurance.name}</span>
		    </div>
		    <div class="element-block" >
	    		<span class="lbl">Deposit Amount(In USD):</span> <span id="depositAmount" >${paymentOffsetManager.paymentBatch.depositAmount}</span>
	    	</div>
	    	<div class="clr"></div>
	    </div>
	</div>

	<form:form commandName="paymentOffsetManager" id="form1" enctype="multipart/form-data">
	<div class="form-container" id="paymentOffsetManagerForm">
		<div class="form-container-inner" >
		<form:hidden path="paymentBatch.id" id="paymentBatchId" value="${batchId}"/>
			<h3>${operationType} Offset posting by OFFSET Manager</h3>

			<input type="hidden" name="offset.id" value="${offset}" />
			<span class="input-container">
				<form:label path="patientName">Patient Name:<em class="mandatory">*</em></form:label>
				<span class="input_text">
					<span class="left_curve"></span>
					<form:input path="patientName" maxlength="100" cssClass="mid" cssStyle="width: 373px;" />
					<span class="right_curve"></span>
				</span>
				<form:errors class="invalid" path="patientName" />
			</span>
			<span class="input-container">
				<form:label path="accountNumber">Account Number:<em class="mandatory">*</em></form:label>
				<span class="input_text">
					<span class="left_curve"></span>
					<form:input path="accountNumber" maxlength="100" cssClass="mid" cssStyle="width: 373px;" />
					<span class="right_curve"></span>
				</span>
				<form:errors class="invalid" path="accountNumber" />
			</span>

			<span class="input-container">
				<form:label path="attachment">Attachment:</form:label>
				<span class="input_text">
					<form:input	type="file" path="attachment.attachedFile" size="44" cssClass="mid"/>
				</span>
			</span>
			<c:if test="${not empty paymentOffsetManager.attachment.id}">
				<form:label path="attachment">Attached File:</form:label>
				<div class="download-container">
					${paymentOffsetManager.attachment.name}<a class="download" href="${downloadAction}?id=${paymentOffsetManager.attachment.id}" title="Download File"></a>
				</div>
			</c:if>

		<div class="row-container">
			<form:errors class="invalid" path="postingRecords"/>
		</div>

		<div class="table-grid" id="divMultiple">
			<table class="dd" width="100%" id="data" border = "2">
				<thead>
			        <tr>
			            <th>CPT</th>
			            <th>DOS From</th>
			            <th>DOS To</th>
			            <th>Amount</th>
			            <th>&nbsp;</th>
			        </tr>
			    </thead>
				<c:forEach var="postingRecords" items="${postingRecord}"
					varStatus="i">
					<tr id="row_${i.index }">
						<td>
							<form:input path="postingRecords[${i.index }].cpt" id="postingRecords_${i.index }_cpt" maxlength="50"/>
						</td>
						<td>
							<form:input path="postingRecords[${i.index }].dosFrom" id="postingRecords_${i.index }_dosFrom" cssClass="date" readonly="readonly"/>
						</td>
						<td>
							<form:input path="postingRecords[${i.index }].dosTo" id="postingRecords_${i.index }_dosTo" cssClass="date" readonly="readonly"/>
						</td>
						<td>
							<form:input path="postingRecords[${i.index }].amount" id="postingRecords_${i.index }_amount" cssClass="numbersOnly" maxlength="15"/>
						</td>
						<td>
							<a href='javascript:void(0)' onClick='javascript:deleteRow(${i.index});' class='delete'><img src='<c:url value="/static/resources/img/delete_icon.png"/>' alt='Delete' title='Delete' /></a>
						</td>
					</tr>
				</c:forEach>
				</table>
			</div>

			<input type="button" value="Add More" title="Add More"	id="addnew" align="left"/>

			<div class="row-container">
				<div class="element-block">
					<form:label path="dateOfCheck">Date of check:<em class="mandatory">*</em></form:label>
					<div class="input_text">
						<span class="left_curve"></span>
						<form:input path="dateOfCheck" maxlength="50" cssClass="mid date"
							cssStyle="width: 100px;"  readonly="readonly"/>
						<span class="right_curve"></span>
					</div>
					<form:errors class="invalid" path="dateOfCheck" />
				</div>

				<div class="element-block">
					<form:label path="checkNumber">Check number:<em class="mandatory">*</em></form:label>
					<div class="input_text">
						<span class="left_curve"></span>
						<form:input path="checkNumber" maxlength="50" cssClass="mid"
							cssStyle="width: 100px;" />
						<span class="right_curve"></span>
					</div>
					<form:errors class="invalid" path="checkNumber" />
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block">
					<form:label path="fcnOrAR">FCN / AR #</form:label>
					<div class="input_text">
						<span class="left_curve"></span>
						<form:input path="fcnOrAR" maxlength="200" cssClass="mid"
							cssStyle="width: 100px;" />
						<span class="right_curve"></span>
					</div>
					<form:errors class="invalid" path="fcnOrAR" />
				</div>

				<div class="element-block">
					<form:label path="time">Time(Minute) </form:label>
					<div class="input_text">
						<span class="left_curve"></span>
						<form:input path="time" maxlength="8" cssClass="mid integerOnly"
							cssStyle="width: 100px;" />
						<span class="right_curve"></span>
					</div>
					<form:errors class="invalid" path="time" />
				</div>
				<div class="clr"></div>
			</div>
			 <div class="row-container">
				<form:label path="offset">&nbsp;</form:label>
			    <form:checkbox path="offset" id = "offset" value="true" />Add more Offset for this Ticket Number
			</div>

			<div class="row-container">
				<label>&nbsp;</label>
				<input type="submit" title="${buttonName} Offset" value="${buttonName} Offset"  class="login-btn" />
				<input type="button" title="Cancel" value="Cancel" class="login-btn" onclick="javascript:resetForm(this.form);setTicketNumber();" />
			</div>
		</div>
	</div>
	<input type="hidden" name="toRemoveIds" value="${toRemoveIds}"/>
	</form:form>
</div>

<c:if test="${empty paymentOffsetManager.id}">

<script type="text/javascript">
$(document).ready(function(){
	$('#paymentOffsetManagerForm').hide();
});

function setTicketNumber(){
	$('#hiddenTicketNumber').val($('#ticketNo').val());
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
	var currentItem = parseInt("${fn:length(postingRecord)}")-1;
	$(document).ready(function() {
		$('#addnew').click(function(){
			currentItem++;
			//$('#items').val(currentItem);
			var strToAdd = '<tr id="row_'+currentItem+'">'
			+'<td><input type = "text" name = "postingRecords['+currentItem+'].cpt" id="postingRecords_'+currentItem+'_cpt" maxlength="50"/></td>'
			+'<td><input type = "text" name = "postingRecords['+currentItem+'].dosFrom" class="date" id="postingRecords_'+currentItem+'_dosFrom" readonly="readonly" /></td>'
			+'<td><input type = "text" name = "postingRecords['+currentItem+'].dosTo" class="date" id="postingRecords_'+currentItem+'_dosTo" readonly="readonly"/></td>'
			+'<td><input type = "text" name = "postingRecords['+currentItem+'].amount" class="numbersOnly" id="postingRecords_'+currentItem+'_amount" maxlength="15" value="0"/></td>'
			+'<td><a href="javascript:void(0)" onClick="javascript:deleteRow('+currentItem+');" class="delete"><img src="'+deleteIcon+'" alt="Delete" title="Delete" /></a>'
			+'<input type = "hidden" name = "postingRecords['+currentItem+'].offSetManager.id" value="${paymentOffsetManager.id}" /></td>'
			+'</tr>';

			$('#data').append(strToAdd);

			$('#postingRecords_'+currentItem+'_dosFrom').datepicker('destroy').datepicker({showOn:'focus'});
			$('#postingRecords_'+currentItem+'_dosTo').datepicker('destroy').datepicker({showOn:'focus'});

			//$('.date').live('click', function() {
			//	$(this).datepicker('destroy').datepicker({showOn:'focus'}).focus();
			//});
		});

		$('.date').live('focus', function () {
		    $(this).not('.hasDatePicker').datepicker();
		});

		$('.numbersOnly').live('keydown', function(event) {
	          // Allow: backspace, delete, tab , escape and enter
	        if ( event.keyCode == 46 || event.keyCode == 8 || event.keyCode == 9 || event.keyCode == 27 ||event.keyCode == 13||
	             // Allow: Ctrl+A
	            (event.keyCode == 65 && event.ctrlKey === true) ||
	             // Allow: home, end, left, right
	            (event.keyCode >= 35 && event.keyCode <= 39)) {
	                 // let it happen, don't do anything
	                 return;
	        }else {
	            // Ensure that it is a number and stop the keypress
	            if ( event.shiftKey|| (event.keyCode < 48 || event.keyCode > 57) && (event.keyCode < 96 || event.keyCode > 105 ) && (event.keyCode != 190) && (event.keyCode != 110))
	            {
	       			event.preventDefault();
	            }
	        }

	        if ($(this).val().indexOf('.') > -1) {
	            if (event.keyCode == 190 || event.keyCode == 110)
	                event.preventDefault();
//	           if($(this).val().substring($(this).val().indexOf('.')+1).length>1)
//	        	   event.preventDefault();
	        }
	    });


		$('.numbersOnly').live('blur', function(event) {
	    	if ($.trim($(this).val()).length == 0 || $.trim($(this).val())=='.') {
	    		$(this).val('0.0');
	    	}
	    	if ($(this).val().indexOf('.') > -1) {
		    	if($(this).val().substring($(this).val().indexOf('.') + 1).length > 2 ){
		    		$(this).val(parseFloat($(this).val()).toFixed(2));
		    		//$(this).val('0.0');
		    		//alert("Value can be filled upto 2 decimal places.");
		    	}
	    	}
	    });
	});

	function deleteRow(rowid){
		var counterWithNoneStyle = 0;
		$('#data >tbody >tr').each(function(){
			if(this.style.display == 'none'){
				counterWithNoneStyle ++;
			}
		});
		totalRows = $('#data >tbody >tr').length;
		if((parseInt(totalRows) - parseInt(counterWithNoneStyle)) == 1 ){
			alert("Last row cannot be deleted.");
			return false;
		}
		else{
			$('#row_'+rowid).hide(250);
			$('#postingRecords_'+rowid+'_cpt').val("");
			$('#postingRecords_'+rowid+'_dosFrom').val("");
			$('#postingRecords_'+rowid+'_dosTo').val("");
			$('#postingRecords_'+rowid+'_amount').val("");
			//var originalState = $("#divMultiple").clone();
			//$("#divMultiple").replaceWith(originalState);
		}
	}

	jQuery(function (){
		$('.date').each(function(){
    		$(this).attr('readonly','readonly');
		});
	})

	function getDetail(){
	 	var batchId = $('#ticketNo').val();
		 if(batchId == null || $.trim(batchId) == ''){
			$(".invalid").text("");
			$('#msg').html("Ticket number cannot be left blank.");
		 	$('#ticketNum').val("");

		 	$('#doctor').text('');
			$('#insurance').text('');
			$('#depositAmount').text('');

			$('#paymentOffsetManagerForm').hide("slow");
		 }else{
			$.ajax({
				type : "POST",
				dataType : "json",
				url : contextPath+"/paymentoffsetmanager/getticketdetail/json",
				data : "batchId="+batchId,
				success : function(data) {
					if(data != null && data.length > 0){
						if(data[0] == "err"){
							$('#msg').html(data[1]);
							$('#ticketNum').text("");
							$('#paymentOffsetManagerForm').hide("slow");
							$(".invalid").text("");
							$('#doctor').text('');
							$('#insurance').text('');
							$('#depositAmount').text('');
						}else{
							$('#paymentOffsetManagerForm').show();
							$('#msg').html("");
							$('#paymentBatchId').val(data[0].id);

							$('#doctor').text(data[0].doctor);
							$('#insurance').text(data[0].insurance);
							$('#depositAmount').text(parseFloat(data[0].depositAmount).toFixed(2));
						}
					}
				},error: function(data){
					$('#paymentOffsetManagerForm').hide("slow");
					//alert (data);
				}
			});
		}
	}
</script>

<c:choose>
	<c:when test="${empty paymentOffsetManager.id}">
	  <c:if test="${not empty batchId }">
		<script type="text/javascript">
			getDetail();
		</script>
	</c:if>
	</c:when>
	<c:otherwise>
		<script type="text/javascript">
			$('#paymentOffsetManagerForm').show();
			function setTicketNumber(){
				return true;
			}
		</script>
	</c:otherwise>
</c:choose>
<script type="text/javascript"	src="<c:url value='/static/resources/js/common.js'/>"></script>
