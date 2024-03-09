<%@ page import="argus.util.Constants"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home </a> &raquo;</li>
			<li>&nbsp;Payment Batch List</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<div class="msg">
		<p class="success" id="msgP">${success}</p>
	</div>

	
	<c:set var="pagesThreshold"><spring:message code="pages.thresholdValue"></spring:message></c:set>
	<c:set var="printValidation"><spring:message code="printValidation" arguments="${pagesThreshold}"></spring:message></c:set>
	
	<form class="form-container" id="paymentBatchSearchForm">
		<div class="form-container-inner">
			<h3>Payment Batching System (PBS) Report</h3>

			<div class="row-container">
				<div class="element-block">
					<label>Billing Month:</label>
					<div class="select-container2">
						<select class="selectbox" name="month" id="month">
							<option value="">-:Month:-</option>
							<c:forEach var="month" items="${months}">
								<option value="${month.key}">${month.value}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="element-block">
					<label class="right_lbl">Billing Year:</label>
					<div class="select-container2">
						<select class="selectbox" name="year" id="year">
							<option value="">-:Year:-</option>
							<c:forEach var="year" items="${years}">
								<option value="${year}">${year}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<label>Group/Doctor Name:</label>
				<div>
					<select name="doctorId" id="doctorId" class="selectbox"
						onchange="javascript:showHideElements();">
						<option value="">-: Select All Group/Doctor :-</option>
						<c:forEach var="doctor" items="${doctorList}">
						<c:set var="nonDeposit" scope="session" value="${doctor.nonDeposit}"/>
						<c:if test="${nonDeposit}">
							<option value="${doctor.id}">${doctor.name} (Non-Deposit)</option>
						</c:if>
						<c:if test="${!nonDeposit}">
							<option value="${doctor.id}">${doctor.name}</option>
						</c:if>
						</c:forEach>
					</select>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<label>PH Doctor Name:</label>
				<div>
					<select id="phDoctorList" name="phDoctorList" disabled="disabled">
						<option value="">-: Select PH Doctor :-</option>
						<c:forEach var="proHealthDoctor" items="${proHealthDoctorList}">
							<option value="${proHealthDoctor.id}">${proHealthDoctor.name}</option>
						</c:forEach>
					</select>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block">
					<label>Created Date From:</label>
					<div class="input_text">
						<span class="left_curve"></span> 
						<input type="text" name="dateCreatedFrom" id="dateCreatedFrom" readonly="readonly"
							maxlength="50" class="mid" style="width: 108px;"/> 
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="element-block">
					<label class="right_lbl">To:</label>
					<div class="input_text">
						<span class="left_curve"></span> 
						<input type="text" name="dateCreatedTo" id="dateCreatedTo" readonly="readonly"
							class="mid" style="width: 108px;"/> 
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block">
					<label>CT Posted Date From:</label>
					<div class="input_text">
						<span class="left_curve"></span> <input type="text"
							name="datePostedFrom" id="datePostedFrom" readonly="readonly"
							class="mid" style="width: 108px;" /> <span
							class="right_curve"></span>
					</div>
				</div>
				<div class="element-block">
					<label class="right_lbl">To:</label>
					<div class="input_text">
						<span class="left_curve"></span> <input type="text"
							name="datePostedTo" id="datePostedTo" readonly="readonly"
							class="mid" style="width: 108px;" /> <span
							class="right_curve"></span>
					</div>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block">
					<label>Deposit Date From:</label>
					<div class="input_text">
						<span class="left_curve"></span> <input type="text"
							name="dateDepositFrom" id="dateDepositFrom" readonly="readonly"
							class="mid" style="width: 108px;" /> <span
							class="right_curve"></span>
					</div>
				</div>
				<div class="element-block">
					<label class="right_lbl">To:</label>
					<div class="input_text">
						<span class="left_curve"></span> <input type="text"
							name="dateDepositTo" id="dateDepositTo" readonly="readonly"
							class="mid" style="width: 108px;" /> <span
							class="right_curve"></span>
					</div>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<label>Payment Type: </label>
				<div class="box-left">
					<ul>
						<c:forEach var="paymentType" items="${paymentTypeList}">
							<li>
								<input type="checkbox" id="paymentType_${paymentType.id}" value="${paymentType.id}" />
								<label for="paymentType_${paymentType.id}" 	style="float: right; padding: 0px; width: auto;">${paymentType.name}</label>
							</li>
						</c:forEach>
						<div class="clr"></div>
					</ul>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<label>Revenue Type: </label>
				<div class="box-left">
					<ul>
					<c:forEach var="revenueType" items="${revenueTypes}">
						<li>
							<input type="checkbox" id="revenueType_${revenueType.id}"
								value="${revenueType.id}" title="${revenueType.name}"
								alt="${revenueType.name}" /> <label for="revenueType_${revenueType.id}" style="float: right; padding: 0px; width: auto;">${revenueType.name}</label>
						</li>
					</c:forEach>
					<div class="clr"></div>
					</ul>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<label>Money Source: </label>
				<div class="box-left">
					<ul>
						<c:forEach var="moneySource" items="${moneySources}">
							<li>
								<input type="checkbox" id="moneySource_${moneySource.id}"
									value="${moneySource.id}" title="${moneySource.name}"
									alt="${moneySource.name}" /> <label
									for="moneySource_${moneySource.id}"
									style="float: right; padding: 0px; width: auto;">${moneySource.name}</label>
							</li>
						</c:forEach>
						<div class="clr"></div>
					</ul>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<label>Insurance Name:</label>
				<div>
					<select id="insuranceId" name="insuranceId" class="selectbox">
						<option value="">-: Select Insurance :-</option>
						<c:forEach var="insurance" items="${insuranceList}">
							<option value="${insurance.id}">${insurance.name}</option>
						</c:forEach>
					</select>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block">
					<label>Created By:</label>
					<div class="select-container1">
						<select class="selectbox" id="createdBy" name="createdBy">
							<option value="">-: Created By :-</option>
							<c:forEach var="user" items="${userList}">
								<option value="${user.id}">${user.firstName}
									${user.lastName}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="element-block">
					<label class="right_lbl">Posted By:</label>
					<div class="select-container1">
						<select class="selectbox" id="postedBy" name="postedBy">
							<option value="">-: Posted By :-</option>
							<c:forEach var="user" items="${userList}">
								<option value="${user.id}">${user.firstName}
									${user.lastName}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block" >
					<label>ERA Check#:</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input type="text" name="eraCheckNo" id="eraCheckNo" maxlength="50" class="mid" style="width: 108px;"  />
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="element-block" >
					<label class="right_lbl">Transaction Type:</label>
					<c:forEach var="reportType" items="${reportTypeList}">
						<span style="display: block; float: left; padding: 5px;"> <input
							type="radio" id="${reportType.key}" value="${reportType.key}"
							name="transactionType" /> ${reportType.value}
						</span>
					</c:forEach>
				</div>
				<div class="clr"></div>
			</div>
			
			<div class="row-container">
				<div class="element-block" style="width: 100%" >
					<label>Ticket Number:</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input type="text" name="ticketNumberSearch" id="ticketNumberSearch" maxlength="50" class="mid" style="width: 372px;" />
						<span class="right_curve"></span>
						<br/><small>(eg:"1", "5-10", "3,7,9")</small>
					</div>
				</div>
				<!-- <div class="element-block" >
					<label class="right_lbl">Ticket Number (To):</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input type="text" name="ticketNumberTo" id="ticketNumberTo"  maxlength="15" class="mid integerOnly" style="width: 108px;"  />
						<span class="right_curve"></span>
					</div>
				</div> -->
				
				<div class="clr"></div>
			</div>
			
			<div class="row-container">
				<div class="element-block" style="width: 100%">
					<label>&nbsp;</label>
					<span style="display: block; float: left; padding: 5px;">
						<input type="checkbox" id="ndba" name="ndba" value="1" title="NDBA" alt="NDBA" />
						<label for="ndba"  style='width:50px'>NDBA</label>						 
					</span>
					<span style="display: block; float: left; padding: 5px;">
						<input type="checkbox" name="oldPriorAr" id="oldPriorAR" value="1" title="Old Prior AR" alt="Old Prior AR" />
						<label style="width:80px" for="oldPriorAR">Old Prior AR</label> 
					</span>
					<span style="display: block; float: left; padding: 5px;">
						<input type="checkbox" id="suspenseAccount" name="suspenseAccount" value="1" title="Suspense Account" alt="Suspense Account" /> 
						<label for="suspenseAccount"  style='width:120px'>Suspense Account</label> 
					</span>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<label>&nbsp;</label> <input type="hidden" id="revenueTypeIds" value="" name="revenueTypeIds" />
				<input type="hidden" id="paymentTypeIds" value="${paymenttype}" name="paymentTypeIds" />
				<input type="hidden" id="moneySourceIds" value="" name="moneySourceIds" />
				<input type="hidden" id="phDoctorIds" value="" name="phDoctorIds" />
				<input type="submit" title="Search" value="Search" class="login-btn" />
				<input type="reset" title="Cancel" value="Cancel" class="login-btn" onclick="javascript:resetForm(this.form);showHideElements();" />
				<div class="clr"></div>
			</div>

			<div class="noSearchItem">&nbsp;</div>
			<table id="flex1" style="display: none"></table>
		</div>
	</form>
</div>

<script type="text/javascript">
var canReviseCreate = false;
var canUpdateAfterPayProd = false;
var isOffsetManager = false;
</script>

<sec:authorize ifAnyGranted="P-8">
	<script type="text/javascript">
		canReviseCreate = true;
	</script>
</sec:authorize>

<sec:authorize ifAnyGranted="P-9">
	<script type="text/javascript">
		canUpdateAfterPayProd=true;
	</script>
</sec:authorize>

<sec:authorize ifAnyGranted="P-5">
	<script type="text/javascript">
		isOffsetManager=true;
	</script>
</sec:authorize>

<script type="text/javascript">
	var moduleName = "paymentbatch";
    $("#dateCreatedTo").val('${createdTo}');
	$("#dateCreatedFrom").val('${createdFrom}');
	var totalRecord = 0;
	$("#flex1").flexigrid({
		url : contextPath + '/'+moduleName+'/json',
		preProcess : formatData,
		method : 'POST',

		colModel : [ {
			display : 'Action',
			name : 'action',
			width : 45,
			sortable : false,
			align : 'left'
		},{
			display : 'Ticket#',
			name : 'id',
			width : 95,
			sortable : true,
			align : 'left'
		},{
			display : 'Billing Month',
			name : 'billingMonth',
			width : 70,
			sortable : true,
			align : 'left'
		}, {
			display : 'Doctor Name',
			name : 'doctor.name',
			width : 100,
			sortable : true,
			align : 'left'
		}, {
			display : 'Deposit Date',
			name : 'depositDate',
			width : 70,
			sortable : true,
			align : 'left'
		} , {
			display : 'Payment Type',
			name : 'paymentType',
			width : 40,
			sortable : true,
			align : 'center'
		},{
			display : 'Insurance',
			name : 'insurance',
			width : 40,
			sortable : true,
			align : 'center'
		},{
			display : 'Created By',
			name : 'createdBy',
			width : 40,
			sortable : true,
			align : 'center'
		} , {
			display : 'Posted By',
			name : 'postedBy',
			width : 40,
			sortable : true,
			align : 'center'
		}, {
			display : 'Posted On',
			name : 'postedOn',
			width : 70,
			sortable : true,
			align : 'left'
		}, {
			display : 'CT Posted Date',
			name : 'datePosted',
			width : 70,
			sortable : true,
			align : 'left'
		} ,{
			display : 'Deposit Amount',
			name : 'depositAmount',
			width : 40,
			sortable : true,
			align : 'center'
		} ,{
			display : 'NDBA',
			name : 'ndba',
			width : 40,
			sortable : true,
			align : 'center'
		} ,{
			display : 'Revised By',
			name : 'revisedBy',
			width : 40,
			sortable : true,
			align : 'center'
		},{
			display : 'Revised Date',
			name : 'revisedOn',
			width : 40,
			sortable : true,
			align : 'center'
		}],
		buttons : [ {
			name : 'Add',
			bclass : 'add',
			onpress : action
		}, {
			name : 'Print Batches',
			bclass : 'print',
			onpress : action
		}, {
			name : 'PBS List',
			bclass : 'printable',
			onpress : action
		}, {
			name : 'PBS List (Doctor Wise)',
			bclass : 'printable',
			onpress : action
		}],
		searchitems : [ {
			display : 'Name',
			name : 'name',
			isdefault : true
		} ],
		sortname : "id",
		sortorder : "desc",
		title : 'Payment Batch List',
		showTableToggleBtn : true,
		onSubmit : addFormData,
		//rpOptions: [10, 15, 20, 30, 50,100],
		width : 100,
		height : 300
	});

	//	This function adds paramaters to the post of flexigrid. You can add a verification as well by return to false
	// 	if you don't want flexigrid to submit
	function addFormData() {
		//passing a form object to serializeArray will get the valid data from all the objects, but,
		//if the you pass a non-form object, you have to specify the input elements that the data will come from
		var dt = $('#paymentBatchSearchForm').serializeArray();
		$("#flex1").flexOptions({
			params : dt
		});
		return true;
	}

	$('#paymentBatchSearchForm').submit(function() {
		// add multi values as comma seprated
		if(null != $('#datePostedFrom').val() && null != $('#datePostedTo').val()){
			if(Date.parse($('#datePostedFrom').val()) > Date.parse($('#datePostedTo').val()) ){
				alert("Posted date 'To' date must be after or equal to posted date 'From' date. Please try again.");
				return false;
			}
		}
		if(null != $('#dateDepositFrom').val() && null != $('#dateDepositTo').val()){
			if(Date.parse($('#dateDepositFrom').val()) > Date.parse($('#dateDepositTo').val()) ){
				alert("Deposit date 'To' date must be after or equal to deposit date 'From' date. Please try again.");
				return false;
			}
		}

		if(null != $('#dateCreatedFrom').val() && null != $('#dateCreatedTo').val()){
				if(Date.parse($('#dateCreatedFrom').val()) > Date.parse($('#dateCreatedTo').val()) ){
					alert("Created date 'To' date must be after or equal to deposit date 'From' date. Please try again.");
					return false;
				}
		}
		var selectedRevenueTypes = new Array();
		var selectedPaymentTypes = new Array();
		var selectedMoneySources = new Array();
		var selectedPHDoctorIds = new Array();

		$('input:checkbox[id^="revenueType"]:checked').each(function() {
			selectedRevenueTypes.push($(this).val());
		});
		$('input:checkbox[id^="paymentType"]:checked').each(function() {
			selectedPaymentTypes.push($(this).val());
		});
		$('input:checkbox[id^="moneySource"]:checked').each(function() {
			selectedMoneySources.push($(this).val());
		});

		$('#revenueTypeIds').val(selectedRevenueTypes.toString());
		$('#paymentTypeIds').val(selectedPaymentTypes.toString());
		$('#moneySourceIds').val(selectedMoneySources.toString());

		selectedPHDoctorIds = $('#phDoctorList').val();
		if(null != selectedPHDoctorIds){
			$('#phDoctorIds').val(selectedPHDoctorIds.toString());
		}

		$('#flex1').flexOptions({
			newp : 1
		}).flexReload();
		return false;
	});

	function formatData(data) {
		var rows = Array();
		var reviseBatch = '<c:url value="/static/resources/img/evaluation_trainee.png"/>';
		var revise = "";
		var update = "";
		var viewIcon = '<c:url value="/static/resources/img/view.png"/>';

		$.each(data.rows,function(i, row) {
				url = new String(contextPath+'/'+ moduleName	+ '/update/?updateBatchId='	+ row.id);
				
				view = "<a href='javascript:void(0);' onclick='javascript:akpmsPopupHTML(\""+url+"&viewBatch=1&Popup=1\")' class='edit'><img src='"+viewIcon+"' title='View'/></a>";

				if(row.postedBy == null){
					update = "<a href='javascript:void(0);' onclick='javascript:akpmsPopupWindow(\""+url+"&Popup=1\",\"update\");' class='edit'><img src='"+reviseBatch+"' title='Update'/></a>";
				}else{
					if(canUpdateAfterPayProd){
						update = "<a href='javascript:void(0);' onclick='javascript:akpmsPopupWindow(\""+url+"&Popup=1\",\"update\");' class='edit'><img src='"+reviseBatch+"' title='Re-update'/></a>";
					}else{
						update = "";
					}
				}

				offsetTypeURL = "";
				if(row.paymentType == "offset"){
					offsetTypeURL = "&type=offset";
				}

				// user has permission to revise and ticket not posted yet
				if(canReviseCreate || (isOffsetManager && row.paymentType == "offset") ){
					reviseUrl = contextPath+'/'+ moduleName + '/add/?paymentBatchId=' + row.id + offsetTypeURL;
					revise = "<a href='javascript:void(0);' onclick='javascript:akpmsPopupWindow(\""+reviseUrl+"&Popup=1\",\"update\");' class='edit'><img src='"+editIcon+"' title='Revise'/></a>";
				}else{
					revise = "";
				}

				rows.push({
					id : row.id,
					cell : [view + '&nbsp; '+update + '<br/>'+revise,
							'<span style="font-size:15px;">'+row.id+'</span>' , 
							row.billingMonth, row.doctor, row.depositDate, row.paymentType,row.insurance,
							row.createdBy, row.postedBy, row.postedOn, row.datePosted, '$'+row.depositAmount.toFixed(2),
							'$'+row.ndba.toFixed(2), row.revisedBy, row.revisedOn
							]
				});
			});

		totalRecord = data.total;
		// if no record then the div will not appear there
		if(data.total == 0){
			$('div.noSearchItem').text('No records found.');
		}else{
			$('div.noSearchItem').text('');
		}
		return {
			total : data.total,
			page : data.page,
			rows : rows
		};
	}

	function action(com, grid) {
		if (com == 'Add') {
			window.location.href = contextPath + '/' + moduleName	+ "/add";
		}

		else if (com == 'Print Batches') {
			   if(totalRecord > parseInt('${pagesThreshold}'))
			{
				alert("${printValidation}");
			} else{
			//window.open(contextPath + '/' + moduleName	+ "/print","_blank");
			akpmsPopupWindow(contextPath + '/' + moduleName	+ "/printbatcheshtml","payment_batch_print");
			}
			
			
		}else if(com == 'PBS List') {
			/*  if(totalRecord > parseInt('${pagesThreshold}'))
			{
				alert("${printValidation}");
			} */ 
			 
			//window.open(contextPath + '/' + moduleName	+ "/printablepdf","_blank");
			akpmsPopupWindow(contextPath + '/' + moduleName	+ "/printablehtml","payment_batch_tabular");
			 
			
			
		}else if(com == 'PBS List (Doctor Wise)'){
			akpmsPopupWindow(contextPath + '/' + moduleName	+ "/printableweb","payment_batch_doctor");
		}
		/* else if(com == 'Update Batch'){
			var items = $('.trSelected',grid);
			if(typeof items[0] == 'undefined'){
				alert("Please select a row for update.");
				return false;
			}
            var url = contextPath + '/' + moduleName + '/update/?updateBatchId='+items[0].id.substr(3)+'&Popup=1';
            akpmsPopupWindow(url,'update');
		} */
	}

	function showHideElements(){
		var proHealth = checkProHealth();

		if(proHealth){
			$('#phDoctorList').removeAttr("disabled");
		}else{
			$('#phDoctorList').attr("disabled","disabled");
		}
		$.uniform.update();
	}

	function checkProHealth(){
		var proHealthGroupId = "<%=Constants.PRO_HEALTH_GROUP_ID%>";

		if ($('#doctorId').val() == proHealthGroupId) {
			return true;
		} else {
			return false;
		}
	}
</script>
<c:if test="${not empty paymenttype}">
	<script type="text/javascript">
		$("#paymentType_"+${paymenttype}).attr("checked", 'checked');
	</script>
</c:if>
<script type="text/javascript">
	$(function() {
		$("#datePostedFrom").datepicker();
		$("#datePostedTo").datepicker();
		$("#dateDepositFrom").datepicker();
		$("#dateDepositTo").datepicker();
		$("#dateCreatedTo").datepicker();
		$("#dateCreatedFrom").datepicker();
		if (canReviseCreate == false && isOffsetManager == false) {
			$('.add').hide();
		}

	});
</script>
