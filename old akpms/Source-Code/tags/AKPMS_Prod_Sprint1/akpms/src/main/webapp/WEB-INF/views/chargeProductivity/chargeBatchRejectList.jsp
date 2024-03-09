<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li>&nbsp;Charge Batch Rejections	</li>
			<!--  <li>&nbsp;${operationType} User</li>-->
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<div class="msg">
		<p class="success" id="msgP">${success}</p>
	</div>
	<form class="form-container" id="rejectListForm">
		<div class="form-container-inner">
			<h3>Search Charge Rejections </h3>
			<span class="input-container"> <label>Ticket No:</label>
				<span class="input_text">
					<span class="left_curve"></span>
					<input type="text" id="ticketNumber" name="ticketNumber" maxlength="7"  class="mid integerOnly" style="width: 373px" />
					<span class="right_curve"></span>
				</span>
			</span> <br />


			<div class="row-container">
				<div class="element-block">
					<input type="checkbox" name="newRejections" <c:if test="${fetchType == 'newrejection' }"> checked </c:if> id="newRejections" value="1" /> New Rejections
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block" style="width: 100%">
					<input type="radio" name="requestRecord" <c:if test="${fetchType == '1req' }"> checked </c:if> id="firstRequestRecord" value="1"/> 1<sup>st</sup> request records
					&nbsp;&nbsp;&nbsp;
					<input type="radio" name="requestRecord" <c:if test="${fetchType == '2req' }"> checked </c:if> id="secondRequestRecord" value="2" /> 2<sup>nd</sup> request records
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block" style="width: 100%">
					<input type="radio" name="requestDueRecord" <c:if test="${fetchType == '1reqdue' }"> checked </c:if>  value="1"/> 1<sup>st</sup> request due records
					&nbsp;&nbsp;&nbsp;
					<input type="radio"	name="requestDueRecord" <c:if test="${fetchType == '2reqdue' }"> checked </c:if>  value="2" />2<sup>nd</sup> request due records
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block">
					<input type="checkbox" 	name="resolved" <c:if test="${fetchType == 'resolved' }"> checked </c:if> value="1" id="resolved" /> Resolved Only
				</div>
				<div class="clr"></div>
			</div>
			<div class="row-container">
				<div class="element-block">
					<input type="checkbox" name="dummyCpt" <c:if test="${fetchType == 'resolvedcpt' }"> checked </c:if> value="1" id="dummyCpt"/> Dummy CPT records only
				</div>
				<div class="clr"></div>
			</div>

			<span class="input-container">
				<label>&nbsp;</label>
				<input	type="submit" title="Search" value="Search"	class="login-btn" />
				<input type="button" title="Cancel" value="Cancel" onclick="javascript:resetForm(this.form);" class="login-btn" />
			</span>

			<div class="noSearchItem">&nbsp;</div>

			<table id="flex1" style="display: none"></table>

		</div>
	</form>
</div>

<script type="text/javascript">
	var moduleName = "chargeproductivityreject";
	$("#flex1").flexigrid({
		url : moduleName+'/json',
		preProcess : formatData,
		method : 'POST',

		colModel : [ {
			display : 'Ticket No',
			name : 'chargeBatchProcessing',
			width : 80,
			sortable : true,
			align : 'left'
		},{
			display : 'Patient Name',
			name : 'patientName',
			width : 160,
			sortable : true,
			align : 'left'
		},{
			display : 'DOB',
			name : 'dob',
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			display : 'Sequence',
			name : 'sequence',
			width : 40,
			sortable : true,
			align : 'left'
		}, {
			display : 'Account',
			name : 'account',
			width : 40,
			sortable : true,
			align : 'left'
		}, {
			display : 'DOS',
			name : 'dateOfService',
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			display : 'Reason to Reject',
			name : 'reasonToReject',
			width : 80,
			sortable : true,
			align : 'left'
		},{
			display : 'Insurance type',
			name : 'insuranceType',
			width : 80,
			sortable : true,
			align : 'left'
		},{
			display : 'Date of 1st request to Doctor Office',
			name : 'dateOfFirstRequestToDoctorOffice',
			width : 80,
			sortable : true,
			align : 'left'
		},{
			display : 'Date of 2nd request to Doctor Office',
			name : 'dateOfSecondRequestToDoctorOffice',
			width : 80,
			sortable : true,
			align : 'left'
		},{
			display : 'Edit',
			name : 'edit',
			width : 40,
			sortable : false,
			align : 'center'
		},{
			display : 'Resolution',
			name : 'resolution',
			width : 40,
			sortable : false,
			align : 'center'
		} ],
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
		title : 'Charge Batch Rejection List',
		showTableToggleBtn : true,
		onSubmit : addFormData,
		width : 100,
		height : 300
	});

	//This function adds paramaters to the post of flexigrid. You can add a verification as well by return to false if you don't want flexigrid to submit
	function addFormData() {
		//passing a form object to serializeArray will get the valid data from all the objects, but, if the you pass a non-form object, you have to specify the input elements that the data will come from
		var dt = $('#rejectListForm').serializeArray();
		$("#flex1").flexOptions({
			params : dt
		});
		return true;
	}

	$('#rejectListForm').submit(function() {
		$('#flex1').flexOptions({
			newp : 1
		}).flexReload();
		return false;
	});

	function formatData(data) {
		var rows = Array();
		var resolutionIcon = '<c:url value="/static/resources/img/resolution_icon.png"/>';

		$.each(data.rows,function(i, row) {
			edit = "&nbsp;&nbsp;<a href='"+contextPath+"/"
					+ moduleName
					+ "/add?rejectId="
					+ row.id
					+ "' class='edit'><img alt='Edit' title='Edit' src='"+editIcon+"'/></a>";

			resolution = "&nbsp;&nbsp;<a href='"+contextPath+"/"
						+ moduleName
						+ "/reupdate/?rejectId="
						+ row.id +"&batchId=" + row.chargeBatchProcessing
						+ "' class='edit'><img alt='Resolution' title='Resolution' src='"+resolutionIcon+"'/></a>";

			rows.push({
				id : row.id,
				cell : [row.chargeBatchProcessing,
						row.patientName,
						row.dob,
						row.sequence,
						row.account,
						row.dateOfService,
						row.reasonToReject,
						row.insuranceType,
						row.dateOfFirstRequestToDoctorOffice,
						row.dateOfSecondRequestToDoctorOffice,
						edit,
						resolution]
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
			window.location.href = contextPath+"/" + moduleName	+ "/add";
		}
	}	
</script>
<script type="text/javascript" src="<c:url value='/static/resources/js/common.js'/>"></script>
