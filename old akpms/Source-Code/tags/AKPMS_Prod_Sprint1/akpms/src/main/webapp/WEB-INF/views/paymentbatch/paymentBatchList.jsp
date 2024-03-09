<%@page import="argus.util.Constants"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
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

	<form class="form-container" id="paymentBatchSearchForm">
		<div class="form-container-inner">
			<h3>Search Payment Batch</h3>

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
						<option value="">-: Select Group/Doctor :-</option>
						<c:forEach var="doctor" items="${doctorList}">
							<option value="${doctor.id}">${doctor.name}</option>
						</c:forEach>
					</select>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<label>PH Doctor Name:</label>
				<div>
					<select id="phDoctorList" name="phDoctorList" disabled="disabled">
						<option value="">-: Select Prohealth Doctor :-</option>
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
						<span class="left_curve"></span> <input type="text"
							name="dateCreatedFrom" id="dateCreatedFrom" readonly="readonly"
							maxlength="50" class="mid" style="width: 90px;" /> <span
							class="right_curve"></span>
					</div>
				</div>
				<div class="element-block">
					<label class="right_lbl">To:</label>
					<div class="input_text">
						<span class="left_curve"></span> <input type="text"
							name="dateCreatedTo" id="dateCreatedTo" readonly="readonly"
							maxlength="50" class="mid" style="width: 90px;" /> <span
							class="right_curve"></span>
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
							maxlength="50" class="mid" style="width: 90px;" /> <span
							class="right_curve"></span>
					</div>
				</div>
				<div class="element-block">
					<label class="right_lbl">To:</label>
					<div class="input_text">
						<span class="left_curve"></span> <input type="text"
							name="datePostedTo" id="datePostedTo" readonly="readonly"
							maxlength="50" class="mid" style="width: 90px;" /> <span
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
							maxlength="50" class="mid" style="width: 90px;" /> <span
							class="right_curve"></span>
					</div>
				</div>
				<div class="element-block">
					<label class="right_lbl">To:</label>
					<div class="input_text">
						<span class="left_curve"></span> <input type="text"
							name="dateDepositTo" id="dateDepositTo" readonly="readonly"
							maxlength="50" class="mid" style="width: 90px;" /> <span
							class="right_curve"></span>
					</div>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<label>Payment Type: </label>
				<div style="padding-left: 160px">
					<c:forEach var="paymentType" items="${paymentTypeList}">
						<div style="display: inline; width: auto; padding-left: 5px; float: left;">
							<input type="checkbox" id="paymentType_${paymentType.id}" value="${paymentType.id}" />
							<label for="paymentType_${paymentType.id}" 	style="float: right; padding: 0px; width: auto;">${paymentType.name}</label>
						</div> 	&nbsp;&nbsp;
					</c:forEach>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<label>Revenue Type: </label>
				<div style="padding-left: 160px">
					<c:forEach var="revenueType" items="${revenueTypes}">
						<div
							style="display: inline; width: auto; padding-left: 5px; float: left;">
							<input type="checkbox" id="revenueType_${revenueType.id}"
								value="${revenueType.id}" title="${revenueType.name}"
								alt="${revenueType.name}" /> <label
								for="revenueType_${revenueType.id}"
								style="float: right; padding: 0px; width: auto;">${revenueType.name}</label>
						</div> &nbsp;&nbsp;
					</c:forEach>
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
				<label>Transaction Type:</label>
				<c:forEach var="reportType" items="${reportTypeList}">
					<span style="display: block; float: left; padding: 5px;"> <input
						type="radio" id="${reportType.key}" value="${reportType.key}"
						name="transactionType" /> ${reportType.value}
					</span>
				</c:forEach>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<label>&nbsp;</label> <input type="hidden" id="revenueTypeIds"
					value="" name="revenueTypeIds" />
					<input type="hidden" id="paymentTypeIds" value="${paymenttype}" name="paymentTypeIds" />
				<!--  input type="hidden" id="moneySourceIds" value="" name="moneySourceIds" /-->
				<input type="hidden" id="phDoctorIds" value="" name="phDoctorIds" />

				<input type="submit" title="Search" value="Search" class="login-btn" />
				<input type="reset" title="Cancel" value="Cancel" class="login-btn" onclick="javascript:resetForm(this.form);showHideElements();" />
			</div>

			<div class="noSearchItem">&nbsp;</div>
			<table id="flex1" style="display: none"></table>
		</div>
	</form>
	<div class="popup2" id="popup_showRecord" style="display: none;">
		<span class="button bClose"><span>X</span></span>
		<div class="popup-container" id="divContainer"><img	src="<c:url value="/static/resources/img/ajax-loader.gif"/>" alt="" /></div>
	</div>
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
	$("#flex1").flexigrid({
		url : contextPath + '/'+moduleName+'/json',
		preProcess : formatData,
		method : 'POST',

		colModel : [ {
			display : 'Ticket#',
			name : 'id',
			width : 40,
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
		},{
			display : 'Revise',
			name : 'edit',
			width : 40,
			sortable : false,
			align : 'center'
		},{
			display : 'Update/View Batch',
			name : 'updateBatch',
			width : 60,
			sortable : false,
			align : 'center'
		},{
			display : 'View Offset/Posting',
			name : 'viewOffset',
			width : 60,
			sortable : false,
			align : 'center'
		}],
		buttons : [ {
			name : 'Add',
			bclass : 'add',
			onpress : action
		} ],
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
		//var selectedMoneySources = new Array();
		var selectedPHDoctorIds = new Array();

		$('input:checkbox[id^="revenueType"]:checked').each(function() {
			selectedRevenueTypes.push($(this).val());
		});
		$('input:checkbox[id^="paymentType"]:checked').each(function() {
			selectedPaymentTypes.push($(this).val());
		});
		//$('input:checkbox[id^="moneySource"]:checked').each(function() {
		//	selectedMoneySources.push($(this).val());
		//});

		$('#revenueTypeIds').val(selectedRevenueTypes.toString());
		$('#paymentTypeIds').val(selectedPaymentTypes.toString());
		//$('#moneySourceIds').val(selectedMoneySources.toString());

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
		var view_offset = '<c:url value="/static/resources/img/view_offset.png"/>';
		var view_offset_posting = '<c:url value="/static/resources/img/view_offset_posting.png"/>';
		$.each(data.rows,function(i, row) {
				view = "&nbsp;&nbsp;<a href='"
					+ contextPath+"/"+ moduleName
					+ "/update/?updateBatchId="
					+ row.id+"&viewBatch=1"
					+ "' class='edit'><img src='"+viewIcon+"' title='View'/></a>";

				if(row.postedBy == null){
					update = "&nbsp;&nbsp;<a href='"
						+ contextPath+"/"+ moduleName
						+ "/update/?updateBatchId="
						+ row.id
						+ "' class='edit'><img src='"+reviseBatch+"' title='Update'/></a>";
				}else{
					if(canUpdateAfterPayProd){
						update = "&nbsp;&nbsp;<a href='"
							+ contextPath+"/"+ moduleName
							+ "/update/?updateBatchId="
							+ row.id
							+ "' class='edit'><img src='"+reviseBatch+"' title='Re-update'/></a>";
					}else{
						update = "";
					}
				}

				viewPostedOffset = "&nbsp;&nbsp;<a href='javascript:void(0);' onclick='javascript:show_offset("+row.id+");'title = 'View Offset' class='edit'><img src='"+view_offset+"'/></a>";
				viewPostedOffsetPosting = "&nbsp;&nbsp;<a href='javascript:void(0);' onclick='javascript:show_offsetPosting("+row.id+");' title = 'View Offset Posted' class='edit'><img src='"+view_offset_posting+"'/></a>";

				offsetTypeURL = "";
				if(row.paymentType == "offset"){
					offsetTypeURL = "&type=offset";
				}

				// user has permission to revise and ticket not posted yet
				if(row.postedBy == null && (canReviseCreate || (isOffsetManager && row.paymentType == "offset")) ){
					revise = "&nbsp;&nbsp;<a href='"
						+ contextPath+"/"+ moduleName
						+ "/add/?paymentBatchId="
						+ row.id
						+ offsetTypeURL
						+ "' class='edit'><img src='"+editIcon+"' title='Revise'/></a>";
				}else{
					revise = "";
				}

				// user has permission to revise or offset manager for payment type="offset" batch (THIS SHOULD BE THE CORRENT CONDITION)
				/*if(canReviseCreate || (isOffsetManager && row.paymentType == "offset")){ //&& row.postedBy == null
					revise = "&nbsp;&nbsp;<a href='"
						+ contextPath+"/"+ moduleName
						+ "/add/?paymentBatchId="
						+ row.id
						+ offsetTypeURL
						+ "' class='edit'><img src='"+editIcon+"' title='Revise'/></a>";
				}else{
					revise = "";
				}*/

				rows.push({
					id : row.id,
					cell : [row.id, row.billingMonth, row.doctor, row.depositDate, row.paymentType,row.insurance,
							row.createdBy, row.postedBy, row.postedOn, row.datePosted, row.depositAmount,
							row.ndba, row.revisedBy, row.revisedOn,
							revise, update+' '+view, viewPostedOffset+' '+viewPostedOffsetPosting]
				});
			});

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

	function show_offset(batchId) {
		var htmlString = "";
		$('#popup_showRecord').bPopup({
			onOpen : function() {

			},onClose: function(){
				$("#divContainer").html(ajaxLoadImg);
			}
		},function() {
			htmlString = "<div class='table-grid' style='overflow-x: auto;''>"
					+ "<table border='1' width='100%'><tr>"
					+ "<thead><th class='heading'>Account Number</th>"
					+ "<th class='heading'>Check Number</th>"
					+ "<th class='heading'>Check Date</th>"
					+ "<th class='heading'>Patient Name</th></thead>" + "</tr>";

					$.ajax({
					type : "GET",
					dataType : "json",
					url : contextPath+"/paymentbatch/getOffset",
					async: false,
					data : "batchId=" + batchId,
					success : function(data) {
						if (data != null) {
							for ( var i = 0; i < data.length; i++) {
								htmlString += "<tr>";
								htmlString += "<td>" + data[i].accountNumber+ "</td>";
								htmlString += "<td>" + data[i].chkNumber+ "</td>";
								htmlString += "<td>" + data[i].chkDate+ "</td>";
								htmlString += "<td>" + data[i].patientName+ "</td>";
								htmlString += "</tr>";
							}
							htmlString += "</table></div>";
						} else {
							htmlString = "No record found.";
						}
					},
					error : function(data) {
						alert(data);
					}
				});

			$("#divContainer").html(htmlString);
        });
	}

	function show_offsetPosting(batchId) {
		var htmlString = "";
		$('#popup_showRecord').bPopup({
			onOpen : function() {

			},onClose: function(){
				$("#divContainer").html(ajaxLoadImg);
			}
		},function() {
			htmlString = "<div class='table-grid' style='overflow-x: auto;''>"
				+ "<table border='1' width='100%'><tr>"
				+ "<thead><th class='heading'>Account Number</th>"
				+ "<th class='heading'>Check Number</th>"
				+ "<th class='heading'>Check Date</th>"
				+ "<th class='heading'>Patient Name</th></thead>" + "</tr>";

				$.ajax({
					type : "GET",
					dataType : "json",
					url : contextPath+"/paymentbatch/getOffsetPosting",
					async: false,
					data : "batchId=" + batchId,
					success : function(data) {
						if (data != null) {
							for ( var i = 0; i < data.length; i++) {
								htmlString += "<tr>";
								htmlString += "<td>" + data[i].accountNumber
										+ "</td>";
								htmlString += "<td>" + data[i].chkNumber
										+ "</td>";
								htmlString += "<td>" + data[i].chkDate
										+ "</td>";
								htmlString += "<td>" + data[i].patientName
										+ "</td>";
								htmlString += "</tr>";
							}
							htmlString += "</table></div>";
						} else {
							htmlString = "No record found.";
						}
					},
					error : function(data) {
						alert(data);
					}
				});

			$("#divContainer").html(htmlString);
        });
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
			$('.tDiv').hide();
		}
	});
</script>
