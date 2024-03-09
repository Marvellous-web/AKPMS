<%@page import="argus.util.Constants"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<c:set var="downloadAction">
	<c:url value='/file/fileDownload' />
</c:set>

<c:set var="showARContent" value="false"></c:set>

	<c:forEach var="dept"
		items="${pageContext.request.userPrincipal.principal.departments}">
		<c:if test="${dept.id == 3}">
			<c:set var="showARContent" value="true"></c:set>
		</c:if>
	</c:forEach>



<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li><a href="<c:url value='/paymentproderaoffset' />">&nbsp;Offset List</a> &raquo;</li>
			<li>&nbsp;${operationType} Offset</li>
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
					<c:when test="${empty paymentProdOffset.id}">
						<form method="get" id="frmGetBatchDetails">
							<div class="element-block" style="width: 100%">
								<label><strong>Ticket Number</strong><em class="mandatory">*</em></label>
								<div class="input_text">
									<span class="left_curve"></span>
									<input type="text" id="ticketNo" name="ticketNo"  maxlength="15" class="mid integerOnly" style="width: 100px;" value="${ticketNumber}" />
									<span class="right_curve"></span>
								</div>
								<input type="submit" title="Submit" value="Get Detail" class="btn" />
							</div>
						</form>
					</c:when>
					<c:otherwise>
			    		<div class="element-block" >
					    	<span class="lbl">Ticket Number:</span> <span>${paymentProdOffset.paymentBatch.id}</span>
					    </div>
					</c:otherwise>
				</c:choose>
				<div class="clr"></div>
			</div>

			<div class="row-container">
			    <div class="element-block" >
			    	<span class="lbl">Dr. Office:</span> <span id="doctor" >${paymentProdOffset.paymentBatch.doctor.name}</span>
			    </div>
		    	<div class="clr"></div>
		    </div>

			<div class="row-container">
		    	<div class="element-block" >
			    	<span class="lbl">Insurance:</span> <span id="insurance" >${paymentProdOffset.paymentBatch.insurance.name}</span>
			    </div>
			    <div class="element-block" >
		    		<span class="lbl">Deposit Amount(In USD):</span> <span id="depositAmount" >${paymentProdOffset.paymentBatch.depositAmount}</span>
		    	</div>
		    	<div class="clr"></div>
		    </div>
		</div>


	<form:form commandName="paymentProdOffset" id="form1" enctype="multipart/form-data">
		<div class="form-container" id="paymentProdOffsetForm">
			<div class="form-container-inner">
				<h3>${operationType} Offset payments</h3>

				<div class="row-container">
					<div class="element-block">
						<form:label path="chkNumber">Check Number:<em class="mandatory">*</em></form:label>
						<div class="input_text">
							<span class="left_curve"></span>
							<form:input path="chkNumber" maxlength="50" cssClass="mid" cssStyle="width: 100px;" readonly="${readOnly}" />
							<span class="right_curve"></span>
						</div>
						<form:errors class="invalid" path="chkNumber" />
					</div>
					<div class="element-block">
						<form:label path="chkDate">Check Date:<em class="mandatory">*</em></form:label>
						<div class="input_text">
							<span class="left_curve"></span>
							<form:input path="chkDate" maxlength="50" cssClass="mid date" cssStyle="width: 100px;" readonly="readonly" />
							<span class="right_curve"></span>
						</div>
						<form:errors class="invalid" path="chkDate" />
					</div>
					<div class="clr"></div>
				</div>

				<div class="row-container">
					<div class="element-block">
						<form:label path="patientName">Patient Name:<em class="mandatory">*</em></form:label>
						<div class="input_text">
							<span class="left_curve"></span>
							<form:input path="patientName" maxlength="50" cssClass="mid" cssStyle="width: 100px;" readonly="${readOnly}"/>
							<span class="right_curve"></span>
						</div>
						<form:errors class="invalid" path="patientName" />
					</div>
					<div class="element-block">
						<form:label path="accountNumber">Account Number:<em class="mandatory">*</em></form:label>
						<div class="input_text">
							<span class="left_curve"></span>
							<form:input path="accountNumber" maxlength="50" cssClass="mid"	cssStyle="width: 100px;" readonly="${readOnly}" />
							<span class="right_curve"></span>
						</div>
						<form:errors class="invalid" path="accountNumber" />
					</div>
					<div class="clr"></div>
				</div>

				<div class="row-container">
					<form:errors class="invalid" path="offsetRecords"/>
				</div>
				<div class="table-grid" id="divMultiple">
					<table class="dd" width="100%" id="data" border="2">
						<thead>
							<tr>
								<th>CPT</th>
								<th>DOS</th>
								<th>Amount</th>
								<th></th>
							</tr>
						</thead>
						<c:forEach var="offsetRecord" items="${offsetRecords}"	varStatus="i">
							<tr id="row_${i.index }">
								<td><form:input path="offsetRecords[${i.index }].cpt"
										id="offsetRecords_${i.index }_cpt" readonly="${readOnly}" maxlength="50"/></td>
								<td><form:input path="offsetRecords[${i.index }].dos"
										id="offsetRecords_${i.index }_dos" cssClass="date"
										readonly="readonly" /></td>
								<td><form:input path="offsetRecords[${i.index }].amount"
										id="offsetRecords_${i.index }_amount" cssClass="numbersOnly" readonly="${readOnly}" maxlength="15"/>
								</td>
								<td>
									<a href='javascript:void(0)' onClick='javascript:deleteRow(${i.index});' class='delete'>
										<img src='<c:url value="/static/resources/img/delete_icon.png"/>' alt='Delete' title='Delete' />
									</a>
								</td>
							 </tr>
						</c:forEach>
					</table>
				</div>
				<input type="button" value="Add More" title="Add More"	id="addnew" align="left"/>

				<div class="row-container">
					<form:label path="remark">Remark:<em class="mandatory">*</em></form:label>
					<form:textarea rows="5" cols="20" path="remark"	readonly="${readOnly}" />
					<div class="clr"></div>
					<form:errors class="invalid" path="remark" />
					<div class="clr"></div>
				</div>

			<c:if test="${operationType == 'Edit'}">
				<c:if test="${not empty paymentProdOffset.arRemark}">
					<div class="row-container">
						<form:label path="arRemark">AR Remark:</form:label>
						<span>${paymentProdOffset.arRemark}</span>
						<form:errors class="invalid" path="arRemark" />
						<div class="clr"></div>
					</div>
				</c:if>
				<%-- <div class="row-container">
					<div class="element-block" style="width: 100%">
						<form:label path="arAttachment">Attachment:</form:label>
						<span class="input_text">
							<form:input	type="file" path="arAttachment.attachedFile" size="44" cssClass="mid"/>
						</span>
						<form:errors cssClass="invalid" path="arAttachment.attachedFile" />
					</div>
					<div class="clr"></div>
				</div> --%>
				<c:if test="${showARContent}">
					<c:if test="${(not empty paymentProdOffset.arAttachment.id) && (paymentProdOffset.arAttachment.deleted == false)}">
						<div class="row-container" id="file_row1">
							<div class="element-block" style="width: 100%">
								<form:label path="arAttachment">Attached File:</form:label>
								<div class="download-container">
									${paymentProdOffset.arAttachment.name}<a class="download" href="${downloadAction}?id=${paymentProdOffset.arAttachment.id}" title="Download File"></a>
									<%-- <a href="javascript:void(0);" onclick='javascript:deleteFile(${paymentProdOffset.arAttachment.id})' title="Delete File"><img title="Delete File" alt="Delete File" style="vertical-align: middle;" src="<c:url value="/static/resources/img/cross.png"/>" ></a> &nbsp; --%>
								</div>
							</div>
						</div>
						<div class="clr"></div>
					</c:if>
			
					<div class="row-container">
						<label>&nbsp;</label>
						<!-- <input type="button" title="AR Step completed" value="AR Step completed" id="arStep" class="login-btn" name="arStep"/> -->
						<a href="javascript:void(0);" id="arStep" >AR Step completed</a>
						<div class="clr"></div>
					</div>
				</c:if>
				
				
				<c:if test="${not empty paymentProdOffset.offsetRemark}">
					<div class="row-container">
						<form:label path="offsetRemark">Offset Manager Remark:</form:label>
						<span>${paymentProdOffset.offsetRemark}</span>
						<div class="clr"></div>
					</div>
					<div class="row-container">
						<div class="element-block" style="width: 100%">
							<form:label path="offsetTicketNumber">Offset Ticket Number:</form:label>
							<span>${paymentProdOffset.offsetTicketNumber}</span>
						</div>
						<div class="clr"></div>
					</div>
				</c:if>
					
				<sec:authorize ifAnyGranted="P-5">	
					<div class="row-container">
						<label>&nbsp;</label>
						<!-- <input type="submit" title="Offset Resolve" value="Offset Resolve" class="login-btn" name="offsetResolve" id="offsetResolveButton"/> -->
						<a href="javascript:void(0);" id="offsetResolveButton">Offset Resolve</a>
						<div class="clr"></div>
					</div>
				</sec:authorize>

				<sec:authorize ifNotGranted="P-5">
					<c:if test="${not empty paymentProdOffset.offsetRemark}">
						<div class="row-container">
							<form:label path="offsetRemark">Offset Manager Remark: </form:label>
							<form:textarea rows="5" cols="20" path="offsetRemark" readonly="readonly"/>
							<form:errors class="invalid" path="offsetRemark" />
							<div class="clr"></div>
						</div>
					</c:if>
				</sec:authorize>

			</c:if>
			
			<c:if test="${operationType =='Add'}">
				<div class="row-container">
					<form:label path="offset">&nbsp;</form:label>
				    <form:checkbox path="offset" id = "offset" value="true" />Add more Offset for this Ticket Number
				</div>
			</c:if>

				<div class="row-container">
					<label>&nbsp;</label>
					<input type="submit" title="${buttonName} Offset" value="${buttonName} Offset" class="login-btn" />
					<input type="button" title="Cancel" value="Cancel" class="login-btn" onclick="javascript:resetForm(this.form);setTicketNumber();" />
					<div class="clr"></div>
				</div>
			</div>
		</div>
		<input type="hidden" name="paymentBatch.id" id="hiddenTicketNumber" value="${paymentBatch.id}"/>
		<input type="hidden" name="paymentBatch.insurance.name" id="hiddenInsuranceName" value="${paymentBatch.insurance.name}"/>
		<input type="hidden" name="toRemoveIds" value="${toRemoveIds}"/>
	</form:form>
	
	<c:if test="${operationType == 'Edit'}">
	
	<div class="popup2" id="popup_ar_step" style="display:none;">
		<span class="button bClose"><span>X</span></span>
		<div class="popup-container">
			<div class="popup-container-inner" id="modificationSummaryContent">
		   		<h3>AR Step</h3>
				<form:form id="arStep" name="arStep" commandName="arStep" enctype="multipart/form-data" action="/akpms/paymentproderaoffset/arUpdate?id=${paymentProdOffset.id}">
					<span class="button bClose"><span>X</span></span>
					<div class="row-container">
						<form:label path="arRemark">AR Remark:</form:label>
						<form:textarea rows="5" cols="20" path="arRemark" />
						<form:errors class="invalid" path="arRemark" />
						<div class="clr"></div>
					</div>
			
					<div class="row-container">
						<div class="element-block" style="width: 100%">
							<form:label path="arAttachment">Attachment:</form:label>
							<span class="input_text">
								<form:input	type="file" path="arAttachment.attachedFile" size="44" cssClass="mid"/>
							</span>
							<form:errors cssClass="invalid" path="arAttachment.attachedFile" />
						</div>
						<div class="clr"></div>
					</div>
		
					<c:if test="${(not empty paymentProdOffset.arAttachment.id) && (paymentProdOffset.arAttachment.deleted == false)}">
						<div class="row-container" id="file_row">
							<div class="element-block" style="width: 100%">
								<form:label path="arAttachment">Attached File:</form:label>
								<div class="download-container">
									${paymentProdOffset.arAttachment.name}<a class="download" href="${downloadAction}?id=${paymentProdOffset.arAttachment.id}" title="Download File"></a>
									<a href="javascript:void(0);" onclick='javascript:deleteFile(${paymentProdOffset.arAttachment.id})' title="Delete File"><img title="Delete File" alt="Delete File" style="vertical-align: middle;" src="<c:url value="/static/resources/img/cross.png"/>" ></a> &nbsp;
								</div>
							</div>
						</div>
						<div class="clr"></div>
					</c:if>
	
					<div class="row-container">
						<label>&nbsp;</label>
						<input type="submit" title="SAVE" value="SAVE" class="login-btn" />
						<input type="button" title="Cancel" value="Cancel" class="login-btn" onclick="javascript:resetForm(this.form);setTicketNumber();" />
						<div class="clr"></div>
					</div>
					<input type="hidden" name="fileDeleted" id="fileDeleted" value=""/>
				</form:form>
	   		</div>
	   </div>
	</div>
	
	
	<div class="popup2" id="popup_offset_resolve" style="display:none;">
			   <span class="button bClose"><span>X</span></span>
			   <div class="popup-container">
			   		<div class="popup-container-inner" id="offsetResolvedContent">
			   			<h3>Offset Resolve</h3>
						<form:form id="offsetResolveForm" name="offsetResolveForm" commandName="offsetResolve" action="${pageContext.request.contextPath}/paymentproderaoffset/offsetResolve?id=${paymentProdOffset.id}" onsubmit="return submitOffsetResolve();">
							<span class="button bClose"><span>X</span></span>
							<div class="row-container">
								<form:label path="offsetRemark">Offset Manager Remark:</form:label>
								<form:textarea rows="5" cols="20" path="offsetRemark" />
								<div class="clr"></div>
							</div>
							<div class="row-container">
								<div class="element-block" style="width: 100%">
									<form:label path="offsetTicketNumber">Offset Ticket Number:<em class="mandatory">*</em></form:label>
									<div class="input_text">
										<span class="left_curve"></span>
										<form:input path="offsetTicketNumber" maxlength="50" cssClass="mid integerOnly" cssStyle="width: 100px;" readonly="${readOnly}" />
										<span class="right_curve"></span>
									</div>
									<div>
									<span class="invalid" id="offsetTicketError"></span>
									</div>
								</div>
								<div class="clr"></div>
							</div>
					
							<div class="row-container">
								<label>&nbsp;</label>
								<input type="submit" title="SAVE" value="SAVE" class="login-btn" name="offsetResolve" onclick="javascript:submitOffsetResolve();"/>
								<input type="button" title="Cancel" value="Cancel" class="login-btn" onclick="javascript:resetForm(this.form);setTicketNumber();" />
								<div class="clr"></div>
							</div>
		
						</form:form>
			   		</div>
			   </div>
	</div> 
	</c:if>
	
</div>

<c:if test="${empty paymentProdOffset.id}">
	<script type="text/javascript">
		$(document).ready(function(){
			$('#paymentProdOffsetForm').hide();
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
	var currentItem = parseInt("${fn:length(offsetRecords)}")-1;
	var readOnly = "${readOnly}";
	$(document).ready(function() {
		$('#addnew').click(function(){
			currentItem++;
			//$('#items').val(currentItem);
			var strToAdd = '<tr id="row_'+currentItem+'">'
			+'<td><input type = "text" name = "offsetRecords['+currentItem+'].cpt" id="offsetRecords_'+currentItem+'_cpt" maxlength="50"/></td>'
			+'<td><input type = "text" name = "offsetRecords['+currentItem+'].dos" class="date" id="offsetRecords_'+currentItem+'_dos" readonly="readonly"/></td>'
			+'<td><input type = "text" name = "offsetRecords['+currentItem+'].amount" class="numbersOnly" id="offsetRecords_'+currentItem+'_amount" maxlength="15" value="0"/></td>'
			+'<td><a href="javascript:void(0)" onClick="javascript:deleteRow('+currentItem+');" class="delete"><img src="'+deleteIcon+'" alt="Delete" title="Delete" /></a>'
			+'<input type = "hidden" name = "offsetRecords['+currentItem+'].productivityOffset.id" value="${paymentProdOffset.id}" /></td>'
			+'</tr>';

			$('#data').append(strToAdd);

			$('#offsetRecords_'+currentItem+'_dos').datepicker('destroy').datepicker({showOn:'focus'});
		});

		//$('.date').live('click', function() {
		//	$(this).datepicker('destroy').datepicker().focus();
		//});

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
		//alert(rowid);
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
			$('#offsetRecords_'+rowid+'_cpt').val("");
			$('#offsetRecords_'+rowid+'_dos').val("");
			$('#offsetRecords_'+rowid+'_amount').val("");
			//var originalState = $("#divMultiple").clone();
			//$("#divMultiple").replaceWith(originalState);
		}
		/*if(confirm("Are you sure you wish to delete this offset posting record")){
			if(parseInt(offsetRecordId)>0)
				{
			$.ajax({
				type : "POST",
				dataType : "json",
				url : contextPath+"/paymentproderaoffset/deleteoffsetrecord",
				data : "offsetRecordId="+offsetRecordId+"&offsetId=${paymentProdOffset.id}",
				success : function(data) {
						if(data){
						$('#offsetRecords_'+rowid+'_cpt').val("");
						$('#offsetRecords_'+rowid+'_dos').val("");
						$('#offsetRecords_'+rowid+'_amount').val("");
						$('#row_'+rowid).hide();
						}else{
							alert("unable to delete this row");
						}
				},error: function(data){
					$('#form1').hide("slow");
					//alert (data);
				}
			});
				}

		else{

		}
		}
		*/
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

			$('#paymentProdOffsetForm').hide("slow");
		 }else{
			$.ajax({
				type : "POST",
				dataType : "json",
				url : contextPath+"/paymentproderaoffset/getticketdetail/json",
				data : "batchId="+batchId,
				success : function(data) {
					if(data != null && data.length > 0){
						if(data[0] == "err"){
							$('#msg').html(data[1]);
							$('#ticketNum').text("");
							$('#doctor').text('');
							$('#insurance').text('');
							$('#depositAmount').text('');
							$('#paymentProdOffsetForm').hide("slow");
							$(".invalid").text("");
						}else{
							$('#paymentProdOffsetForm').show();
							$('#msg').html("");
							$('#ticketNum').text(data[0].id);
							$('#doctor').text(data[0].doctor);
							$('#insurance').text(data[0].insurance);
							$('#depositAmount').text(parseFloat(data[0].depositAmount).toFixed(2));

							$('#hiddenTicketNumber').val(data[0].id);
							$('#hiddenInsuranceName').val(data[0].insurance);
						}
					}
				},error: function(data){
					$('#paymentProdOffsetForm').hide("slow");
					//alert (data);
				}
			});
		}
	}

	
	  $('#arStep').bind('click', function(e) {
          // Prevents the default action to be triggered.
          e.preventDefault();
          // Triggering bPopup when click event is fired
          $('#popup_ar_step').bPopup({
              onOpen: function() {}
          });
      });

	  $('#offsetResolveButton').bind('click', function(e) {
          // Prevents the default action to be triggered.
          e.preventDefault();
          // Triggering bPopup when click event is fired
          $('#popup_offset_resolve').bPopup({
              onOpen: function() {},
              onClose: function() { $('#offsetTicketError').html('');}
          });
      });

	  function deleteFile(fileId)
		{
			if(confirm("Are you sure you wish to delete this Attachment?")){
	 		$("#file_row").hide('slow');
	 		$("#file_row1").hide('slow');
			$('#fileDeleted').val("true");
			}
		}

		function submitOffsetResolve(){
			if($.trim($('#offsetTicketNumber').val()).length==0){
				$('#offsetTicketError').html('Offset ticket number cannot be left blank');
				return false;
				}
			}
</script>


<c:choose>
	<c:when test="${empty paymentProdOffset.id}">
		<script type="text/javascript">
			$('#paymentProdOffsetForm').hide();
		</script>
	  	<c:if test="${not empty ticketNumber }">
			<script type="text/javascript">
				getDetail();
			</script>
		</c:if>
	</c:when>
	<c:otherwise>
		<script type="text/javascript">
			$('#paymentProdOffsetForm').show();
			function setTicketNumber(){
				return true;
			}
		</script>
	</c:otherwise>
</c:choose>
<script type="text/javascript" src="<c:url value='/static/resources/js/common.js'/>"></script>
