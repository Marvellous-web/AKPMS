<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li>Payment Productivity Hourly List	</li>
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
			<h3>Search Payment Productivity Hourly </h3>
			<div class="row-container">
				<label>Task Name:</label>
				<select class="selectbox" name="taskName" id="taskName">
					<option value="">-:Task Name:-</option>
					<c:forEach var="taskName" items="${taskNameList}">
						<option value="${taskName.id}">${taskName.name}</option>
					</c:forEach>
				</select>
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

		colModel : [ {
			display : 'Time',
			name : 'time',
			width : 160,
			sortable : false,
			align : 'left'
		}, {
			display : 'Task Name',
			name : 'tashName',
			width : 160,
			sortable : false,
			align : 'left'
		}, {
			display : 'Details',
			name : 'detailst',
			width : 160,
			sortable : false,
			align : 'left'
		}, {
			display : 'Edit',
			name : 'edit',
			width : 160,
			sortable : false,
			align : 'left'
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
		sortorder : "asc",
		title : 'Payment Productivity Hourly List',
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
				cell : [ row.time, row.taskName, row.details,edit]
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
