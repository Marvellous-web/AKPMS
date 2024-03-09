<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>

<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li>&nbsp;Charge Batch Productivity List</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<div class="msg">
		<p class="success" id="msgP">${success}</p>
	</div>
	<form class="form-container" id="searchForm">
		<div class="form-container-inner">
			<h3>Search Charge Batch Productivity</h3>

			<div class="row-container">
				<div class="element-block">
					<label>Productivity Type:</label>
					<div class="select-container2">
						<select class="selectbox" name="prodType" id="prodType">
							<option value="">-:Select:-</option>
							<option value="CE">Charge Entry</option>
							<option value="Demo">Demo</option>
							<option value="Coding">Coding</option>
						</select>
					</div>
				</div>
				<div class="element-block">
					<label class="right_lbl">Workflow:</label>
					<div class="select-container2">
						<select class="selectbox" name="workflow" id="workflow">
							<option value="">-:Select:-</option>
							<option value="reject">Reject</option>
							<option value="paymentreversal">Payment Reversal</option>
							<option value="internal">Internal</option>
							<option value="argustl">Argus TL</option>
						</select>
					</div>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block">
					<label>Posted Date (From):</label>
					<div class="input_text">
						<span class="left_curve"></span> <input type="text"
							name="dateCreatedFrom" id="dateCreatedFrom" readonly="readonly"
							class="mid" style="width: 100px;" /> <span
							class="right_curve"></span>
					</div>
				</div>
				<div class="element-block">
					<label class="right_lbl">Posted Date (To):</label>
					<div class="input_text">
						<span class="left_curve"></span> <input type="text"
							name="dateCreatedTo" id="dateCreatedTo" readonly="readonly"
							class="mid" style="width: 100px;" /> <span
							class="right_curve"></span>
					</div>
				</div>
				<div class="clr"></div>
			</div>
			
			<div class="row-container">
				<div class="element-block">
					<label>Scan Date (From):</label>
					<div class="input_text">
						<span class="left_curve"></span> 
						<input type="text" name="scanDateFrom" id="scanDateFrom" readonly="readonly" class="mid" style="width: 100px;" /> 
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="element-block">
					<label class="right_lbl">Scan Date (To):</label>
					<div class="input_text">
						<span class="left_curve"></span> 
						<input type="text" name="scanDateTo" id="scanDateTo" readonly="readonly" class="mid" style="width: 100px;" /> 
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block">
					<label>Ticket Number:</label>
					<span class="input_text">
						<span class="left_curve"></span>
						<input type="text" id="ticketNumber" name="ticketNumber" maxlength="7"  class="mid integerOnly"  style="width: 100px" />
						<span class="right_curve"></span>
					</span>
				</div>
				<div class="element-block">
					<label class="right_lbl">Posted By:</label>
					<div class="select-container2">
						<select class="selectbox" id="createdBy" name="createdBy">
							<option value="">-:Created By:-</option>
							<c:forEach var="user" items="${userList}">
								<option value="${user.id}">${user.firstName} ${user.lastName}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="clr"></div>
			</div>
			
			<div class="row-container">
				<div class="element-block">
					<label>On Hold:</label>
					<input type="checkbox" value="1" id="onHold" name="onHold" <c:if test="${onHold eq '1'}">checked</c:if> />
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<label>&nbsp;</label>
				<input type="submit" title="Search" value="Search" class="login-btn" />
				<input type="button" title="Cancel" value="Cancel" onclick="javascript:resetForm(this.form);" class="login-btn" />
				<div class="clr"></div>
			</div>

			<div class="noSearchItem">&nbsp;</div>
			<table id="flex1" style="display: none"></table>
		</div>
	</form>
</div>
<script type="text/javascript">
	var canUnhold = false;
</script>
<sec:authorize ifAnyGranted="P-7">
<script type="text/javascript">
	canUnhold = true;
</script>
</sec:authorize>

<script type="text/javascript">
	var moduleName = "chargeproductivity";
	$("#dateCreatedTo").val('${createdTo}');
	$("#dateCreatedFrom").val('${createdFrom}');
	$("#flex1").flexigrid({
		url : contextPath + '/'+moduleName+'/list/json',
		preProcess : formatData,
		method : 'GET',

		colModel : [{
			display : 'Action',
			name : 'action',
			width : 50,
			sortable : false,
			align : 'left'
		}, {
			display : 'Batch/Ticket#',
			name : 'ticketNumber',
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			display : 'Prod. Type',
			name : 'productivityType',
			width : 90,
			sortable : true,
			align : 'left'
		}, {
			display : 'Scan Date',
			name : 'scanDate',
			width : 90,
			sortable : false,
			align : 'left'
		}, {
			display : 'CT Posted Date',
			name : 'ctPostedDate',
			width : 90,
			sortable : false,
			align : 'left'
		}, {
			display : 'Time',
			name : 'time',
			width : 40,
			sortable : true,
			align : 'left'
		},{
			display : 'Work flow',
			name : 'workFlow',
			width : 120,
			sortable : true,
			align : 'left'
		}, {
			display : 'Other Info',
			name : 'other',
			width : 300,
			sortable : false,
			align : 'left'
		}, {
			display : 'Created Date',
			name : 'createdOn',
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			display : 'Created By',
			name : 'createdBy',
			width : 80,
			sortable : true,
			align : 'left'
		} ],
		 buttons : [ {
			name : 'Add Coding Productivity',
			bclass : 'add',
			onpress : action
		},{
			name : 'Add Demo Productivity',
			bclass : 'add',
			onpress : action
		},{
			name : 'Add CE Productivity',
			bclass : 'add',
			onpress : action
		},{
			name : 'Print Productivity',
			bclass : 'print',
			onpress : action
		} ],
		searchitems : [ {
			display : 'Name',
			name : 'name',
			isdefault : true
		} ],
		sortname : "id",
		sortorder : "desc",
		title : 'Charge Batch Productivity List',
		showTableToggleBtn : true,
		onSubmit : addFormData,
		width : 100,
		height : 300
	});

	//This function adds paramaters to the post of flexigrid. You can add a verification as well by return to false if you don't want flexigrid to submit
	function addFormData() {
		//passing a form object to serializeArray will get the valid data from all the objects, but, if the you pass a non-form object, you have to specify the input elements that the data will come from
		var dt = $('#searchForm').serializeArray();
		$("#flex1").flexOptions({
			params : dt
		});
		return true;
	}

	$('#searchForm').submit(function() {
		if(null != $('#dateCreatedFrom').val() && null != $('#dateCreatedTo').val()){
			if(Date.parse($('#dateCreatedFrom').val()) > Date.parse($('#dateCreatedTo').val()) ){
				alert("Sorry, the Created Date(To) date cannot be lesser than the Created Date(From) date.");
				return false;
			}
		}

		if(null != $('#scanDateFrom').val() && null != $('#scanDateTo').val()){
			if(Date.parse($('#scanDateFrom').val()) > Date.parse($('#scanDateTo').val()) ){
				alert("Sorry, the Scan Date(To) date cannot be lesser than the Scan Date(From) date.");
				return false;
			}
		}
		
		$('#flex1').flexOptions({
			newp : 1
		}).flexReload();
		return false;
	});

	function formatData(data) {
		var rows = Array();
		var strWorkflow = "";
		var ctPostedDate = "";
		//var strUnhold = "";
		var unHoldDoneIcon = '<c:url value="/static/resources/img/unholddone_icon.png"/>';
		var unHoldIcon = '<c:url value="/static/resources/img/unhold_icon.png"/>';
		$.each(data.rows,function(i, row) {
			ctPostedDate = "";
			 edit = "&nbsp;<a href='"+contextPath+"/"
					+ moduleName
					+ "?productivityId="
					+ row.productivityId
					+ "&prodType="
					+ row.productivityType
					+"' class='edit'><img alt='Edit' title='Edit' src='"+editIcon+"'/></a>";


			if(row.workFlow == "reject"){
				strWorkflow = "Reject";
			}else if(row.workFlow == "paymentreversal"){
				strWorkflow = "Payment Reversal";
			}else if(row.workFlow == "internal"){
				strWorkflow = "Internal";
			}else if(row.workFlow == "argustl"){
				strWorkflow = "Argus TL";
			}else{
				strWorkflow = "";
			}

			var otherInfo = "";

			if(row.productivityType == 'Demo'){
				otherInfo = "New Patient: "+parseInt(row.t1 = row.t1 || 0)+", Existing Patient: "+parseInt(row.t2 = row.t2 || 0);
			}else if(row.productivityType == 'Coding'){
				otherInfo = "Number Of A/c: "+parseInt(row.t1  = row.t1 || 0 ) +", ICDs:"+parseInt(row.t2 = row.t2 || 0)+", CPTs: "+parseInt(row.t3 = row.t3 || 0);
			}else if(row.productivityType == 'CE'){
				otherInfo = "FFS: "+parseInt(row.t1 = row.t1 || 0)+ ", CAP: "+parseInt(row.t2 = row.t2 || 0);
				ctPostedDate = row.ctPostedDate;
			}			

			if(row.onHold == "1"){
				otherInfo += " On Hold Remark: "+row.remarks;
			}
			
			rows.push({
				id : row.productivityId,
				cell : [ edit ,
				         '<span style="font-size:15px;">'+row.ticketNumber+'</span>',
							row.productivityType,
							row.scanDate,
							ctPostedDate,
							row.time,
							strWorkflow,
							otherInfo,
							row.createdOn,
							row.createdBy]
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
		if (com == 'Add Demo Productivity') {
			window.location.href = contextPath+"/"+ moduleName+"?prodType=Demo";
		}else if(com == 'Add Coding Productivity'){
			window.location.href = contextPath+"/"+ moduleName+"?prodType=Coding";
		}else if(com == 'Add CE Productivity'){
			window.location.href = contextPath+"/"+ moduleName+"?prodType=CE";
		}else if(com == 'Print Productivity'){
			akpmsPopupWindow(contextPath + '/' + moduleName	+ "/printableweb","charge_prod_print");
		}
	}

	$(function() {
		$("#dateCreatedFrom").datepicker();
		$("#dateCreatedTo").datepicker();

		$("#scanDateFrom").datepicker();
		$("#scanDateTo").datepicker();
	});

	$('#workflow').val('${workflow}');
	window.setTimeout('$.uniform.update()',200);
</script>
<script type="text/javascript" src="<c:url value='/static/resources/js/common.js'/>"></script>
