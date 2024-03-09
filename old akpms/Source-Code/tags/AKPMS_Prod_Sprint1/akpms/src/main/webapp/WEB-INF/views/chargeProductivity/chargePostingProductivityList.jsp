<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
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
							<option value="Charge Entry">Charge Entry</option>
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
							<option value="onhold">On Hold</option>
							<option value="internal">Internal</option>
							<option value="argustl">Argus TL</option>
						</select>
					</div>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block">
					<label>Created Date (From):</label>
					<div class="input_text">
						<span class="left_curve"></span> <input type="text"
							name="dateCreatedFrom" id="dateCreatedFrom" readonly="readonly"
							maxlength="50" class="mid" style="width: 90px;" /> <span
							class="right_curve"></span>
					</div>
				</div>
				<div class="element-block">
					<label class="right_lbl">Created Date (To):</label>
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
					<label>Ticket Number:</label>
					<span class="input_text">
						<span class="left_curve"></span>
						<input type="text" id="ticketNumber" name="ticketNumber" maxlength="7"  class="mid integerOnly"  style="width: 90px" />
						<span class="right_curve"></span>
					</span>
				</div>
				<div class="element-block">
					<label class="right_lbl">Created By:</label>
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
	$("#flex1").flexigrid({
		url : contextPath + '/'+moduleName+'/list/json',
		preProcess : formatData,
		method : 'GET',

		colModel : [ {
			display : 'Ticket Number',
			name : 'ticketNumber',
			width : 160,
			sortable : true,
			align : 'left'
		}, {
			display : 'Productivity Type',
			name : 'productivityType',
			width : 160,
			sortable : true,
			align : 'left'
		}, {
			display : 'Number of Transactions',
			name : 'numberOfTransactions',
			width : 160,
			sortable : true,
			align : 'left'
		}, {
			display : 'Created Date',
			name : 'createdOn',
			width : 100,
			sortable : true,
			align : 'left'
		}, {
			display : 'Time',
			name : 'time',
			width : 80,
			sortable : true,
			align : 'left'
		},{
			display : 'Work flow',
			name : 'workFlow',
			width : 80,
			sortable : true,
			align : 'left'
		},{
			display : 'Action',
			name : 'action',
			width : 80,
			sortable : false,
			align : 'left'
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
		
		$('#flex1').flexOptions({
			newp : 1
		}).flexReload();
		return false;
	});


	function formatData(data) {
		var rows = Array();
		var strWorkflow = "";
		var strUnhold = "";
		var unHoldDoneIcon = '<c:url value="/static/resources/img/unholddone_icon.png"/>';
		var unHoldIcon = '<c:url value="/static/resources/img/unhold_icon.png"/>';
		$.each(data.rows,function(i, row) {

			 edit = "&nbsp;&nbsp;<a href='"+contextPath+"/"
					+ moduleName
					+ "?productivityId="
					+ row.productivityId
					+ "' class='edit'><img alt='Edit' title='Edit' src='"+editIcon+"'/></a>";

			if(canUnhold && row.workFlow == "onhold"){
				if(row.unholdRemarks != null){
					unHoldIconStatus = unHoldDoneIcon;
					title = 'Show unhold remark';
				}else{
					unHoldIconStatus = unHoldIcon;
					title = 'Put unhold remark';
				}

				strUnhold = "&nbsp;&nbsp;<a href='"+contextPath+"/"
				+ moduleName + "/unhold"
				+ "?productivityId="
				+ row.productivityId
				+ "' class='unhold'><img title='"+title+"' alt='"+title+"' src='"+unHoldIconStatus+"'/></a>";

			}else if(row.workFlow == "onhold" && row.unholdRemarks != null){

				strUnhold = "&nbsp;&nbsp;<a href='"+contextPath+"/"
				+ moduleName + "/unhold"
				+ "?productivityId="
				+ row.productivityId + "&canUnhold=" + canUnhold
				+ "' class='unhold'><img alt='Show unhold remark' title='Show unhold remark' src='"+unHoldDoneIcon+"'/></a>";
			}else{
				strUnhold = "";
			}

			if(row.workFlow == "onhold"){
				strWorkflow = "On Hold";
			}else if(row.workFlow == "reject"){
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
			rows.push({
				id : row.productivityId,
				cell : [ row.ticketNumber,
							row.productivityType,
							row.numberOfTransactions,row.createdOn,
							row.time,
							strWorkflow,edit +' '+strUnhold]
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
			window.location.href = contextPath+"/"+ moduleName;
		}
	}

	$(function() {
		$("#dateCreatedFrom").datepicker();
		$("#dateCreatedTo").datepicker();
	});

	$('#workflow').val('${workflow}');
	window.setTimeout('$.uniform.update()',200);
</script>
<script type="text/javascript" src="<c:url value='/static/resources/js/common.js'/>"></script>
