<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li>&nbsp;Hourly List</li>
			<!--  <li>&nbsp;${operationType} User</li>-->
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<div class="msg">
		<p class="success" id="msgP">${success}</p>
	</div>
	<form class="form-container" id="hourlyListForm">
		<div class="form-container-inner">
			<h3>Search Hourly Productivity</h3>
			<div class="row-container">
				<label>Task Name:</label>
				<select class="selectbox" name="taskName" id="taskName">
					<option value="">-:Task Name:-</option>
					<c:forEach var="taskName" items="${taskNameList}">
						<option value="${taskName.id}">${taskName.name}</option>
					</c:forEach>
				</select>
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
					<label>Task Received Date From:</label>
					<div class="input_text">
						<span class="left_curve"></span> 
						<input type="text" name="taskReceivedDateFrom" id="taskReceivedDateFrom" readonly="readonly"
							maxlength="50" class="mid" style="width: 108px;"/> 
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="element-block">
					<label class="right_lbl">To:</label>
					<div class="input_text">
						<span class="left_curve"></span> 
						<input type="text" name="taskReceivedDateTo" id="taskReceivedDateTo" readonly="readonly"
							class="mid" style="width: 108px;"/> 
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="clr"></div>
			</div>
			<div class="row-container">
				<div class="element-block">
					<label>Task Completed Date From:</label>
					<div class="input_text">
						<span class="left_curve"></span> 
						<input type="text" name="taskCompletedDateFrom" id="taskCompletedDateFrom" readonly="readonly"
							maxlength="50" class="mid" style="width: 108px;"/> 
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="element-block">
					<label class="right_lbl">To:</label>
					<div class="input_text">
						<span class="left_curve"></span> 
						<input type="text" name="taskCompletedDateTo" id="taskCompletedDateTo" readonly="readonly"
							class="mid" style="width: 108px;"/> 
						<span class="right_curve"></span>
					</div>
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
								<option value="${user.id}">${user.firstName} ${user.lastName}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="clr"></div>
			</div>
			
			<br />
			<span class="input-container"> <label>&nbsp;</label> <input
				type="submit" title="Search" value="Search"
				 class="login-btn" />
				<input type="button" title="Cancel" value="Cancel" onclick="javascript:resetForm(this.form);"
				class="login-btn" />
			</span>
			<div class="noSearchItem">&nbsp;</div>

			<table id="flex1" style="display: none"></table>
		</div>
	</form>
</div>

<script type="text/javascript">
	var moduleName = "paymentproductivityhourly";
	
	$("#flex1").flexigrid({
		url : contextPath+'/'+moduleName+'/json',
		preProcess : formatData,
		method : 'POST',

		colModel : [{
			display : 'Edit',
			name : 'edit',
			width : 40,
			sortable : false,
			align : 'left'
		}, {
			display : 'Time',
			name : 'time',
			width : 50,
			sortable : false,
			align : 'left'
		}, {
			display : 'Task Name',
			name : 'tashName',
			width : 260,
			sortable : false,
			align : 'left'
		}, {
			display : 'Details',
			name : 'detailst',
			width : 260,
			sortable : false,
			align : 'left'
		},{
			display : 'Task Recieved Date',
			name : 'dateReceived',
			width : 120,
			sortable : false,
			align : 'left'
		},{
			display : 'Task Completed Date',
			name : 'taskCompletedDate',
			width : 120,
			sortable : false,
			align : 'left'
		}],
		 buttons : [ {
			name : 'Add',
			bclass : 'add',
			onpress : action
		}, {
			name : 'Print',
			bclass : 'print',
			onpress : action
		} ],
		searchitems : [ {
			display : 'Name',
			name : 'name',
			isdefault : true
		} ],
		sortname : "id",
		sortorder : "asc",
		title : 'Hourly List',
		showTableToggleBtn : true,
		onSubmit : addFormData,
		width : 100,
		height : 300
	});

	//This function adds paramaters to the post of flexigrid. You can add a verification as well by return to false if you don't want flexigrid to submit
	function addFormData() {
		//passing a form object to serializeArray will get the valid data from all the objects, but, if the you pass a non-form object, you have to specify the input elements that the data will come from
		var dt = $('#hourlyListForm').serializeArray();
		$("#flex1").flexOptions({
			params : dt
		});
		return true;
	}

	$('#hourlyListForm').submit(function() {

		if(null != $('#dateCreatedFrom').val() && null != $('#dateCreatedTo').val()){
			if(Date.parse($('#dateCreatedFrom').val()) > Date.parse($('#dateCreatedTo').val()) ){
				alert("Created date 'To' date must be after or equal to deposit date 'From' date. Please try again.");
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

		$.each(data.rows,function(i, row) {
			edit = "&nbsp;&nbsp;<a href='"+contextPath+"/"
					+ moduleName
					+ "/add?id="
					+ row.id
					+ "' class='edit'><img src='"+editIcon+"' title='Edit'/></a>";

			rows.push({
				id : row.id,
				cell : [edit, row.time, row.taskName, row.details,row.dateReceived, row.taskCompletedDate]
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
		}else if(com == 'Print'){
			akpmsPopupWindow(contextPath + '/' + moduleName	+ "/printableweb","print");
		}
	}

	$(function() {
		$("#dateCreatedTo").datepicker();
		$("#dateCreatedFrom").datepicker();
		$("#taskCompletedDateFrom").datepicker();
		$("#taskCompletedDateTo").datepicker();
		$("#taskReceivedDateFrom").datepicker();
		$("#taskReceivedDateTo").datepicker();
	});
</script>

