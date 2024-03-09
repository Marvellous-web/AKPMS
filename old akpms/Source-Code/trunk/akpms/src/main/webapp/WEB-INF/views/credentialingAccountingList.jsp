<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li> Credentialling and Accounting</li>

		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<div class="msg">
		<p class="success" id="msgP">${success}</p>
	</div>
	<form class="form-container" id="paymentProductivityListForm">
		<div class="form-container-inner">
			<h3>Search</h3>
			<div class="row-container">
				<div class="element-block" >
					<label>Posted From:</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input type = "text"  id="datePostedFrom" name="datePostedFrom"  class="mid" style="width: 100px;" readonly="readonly"/>
						<span class="right_curve"></span>
					</div>
				</div>

				<div class="element-block" >
					<label class="right_lbl">To:</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input type = "text" id="datePostedTo" name="datePostedTo" class="mid" style="width: 100px;" readonly="readonly" />
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="clr"></div>

			</div>

			<div class="row-container">
				<div class="element-block" >
					<label>Received Date From:</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input type = "text"  id="dateReceivedFrom" name="dateReceivedFrom" class="mid" style="width: 100px;" readonly="readonly"/>
						<span class="right_curve"></span>
					</div>
				</div>

				<div class="element-block" >
					<label class="right_lbl">To:</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input type = "text" id="dateReceivedTo" name="dateReceivedTo" class="mid" style="width: 100px;" readonly="readonly" />
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="clr"></div>

			</div>

			<div class="row-container" >
				<label>Posted By:</label>
				<div>
					<select id="postedById" name="postedById" class="selectbox">
						<option value="">-:Select Posted by:-</option>
						<c:forEach var="type" items="${postedBy}">
						<option value="${type.id}">${type.firstName}</option>
						</c:forEach>
					</select>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<label>&nbsp;</label>
				<input type="submit" title="Search" value="Search"  class="login-btn" />
				<input type="button" title="Cancel" value="Cancel" onclick="javascript:resetForm(this.form);"
				class="login-btn" />
			</div>

			<div class="noSearchItem">&nbsp;</div>

			<table id="flex1" style="display: none"></table>

		</div>
	</form>
</div>

<script type="text/javascript">
var contextPath = "<c:out value="${pageContext.request.contextPath}" />";
	var moduleName = "credentialingaccountingproductivity";
	$("#flex1").flexigrid({
		url : contextPath + '/'+moduleName+'/json',
		preProcess : formatData,
		method : 'POST',

		colModel : [ {
			display : 'Edit',
			name : 'edit',
			width : 40,
			sortable : false,
			align : 'center'
		},{
			display : 'Credentialing Task',
			name : 'credentialingTask',
			width : 200,
			sortable : true,
			align : 'left'
		},{
			display : 'Date Received',
			name : 'dateRecd',
			width : 80,
			sortable : true,
			align : 'left'
		},{
			display : 'Task Completed Date',
			name : 'taskCompleted',
			width : 130,
			sortable : true,
			align : 'left'
		},{
			display : 'Time Taken',
			name : 'time',
			width : 80,
			sortable : true,
			align : 'left'
		},{
			display : 'Created By',
			name : 'postedBy',
			width : 90,
			sortable : false,
			align : 'left'
		} ],
		buttons : [ {
			name : 'Add',
			bclass : 'add',
			onpress : action
		},{
			name : 'Print Report',
			bclass : 'print',
			onpress : action
		} ],

		sortname : "id",
		sortorder : "asc",
		title : 'Credentialing and Accounting Productivity',
		showTableToggleBtn : true,
		onSubmit : addFormData,
		width : 100,
		height : 300
	});
	//This function adds paramaters to the post of flexigrid. You can add a verification as well by return to false
	// if you don't want flexigrid to submit
	function addFormData() {
		//passing a form object to serializeArray will get the valid data from all the objects, but,
		//if the you pass a non-form object, you have to specify the input elements that the data will come from
		var dt = $('#paymentProductivityListForm').serializeArray();
		$("#flex1").flexOptions({
			params : dt
		});
		return true;
	}

	$('#paymentProductivityListForm').submit(function() {
		if(null != $('#datePostedFrom').val() && null != $('#datePostedTo').val()){
			if(Date.parse($('#datePostedFrom').val()) > Date.parse($('#datePostedTo').val()) ){
				alert("To date must be after or equal to From date. Please try again.");
				return false;
			}
		}

	    if(null != $('#dateReceivedFrom').val() && null != $('#dateReceivedTo').val()){
			if(Date.parse($('#dateReceivedFrom').val()) > Date.parse($('#dateReceivedTo').val()) ){
				alert("To date must be after or equal to From date. Please try again.");
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

			edit = "&nbsp;&nbsp;<a href='"
				+ contextPath+"/"
				+ "credentialingaccountingproductivity"
				+ "/add?id="
				+ row.id
				+ "' class='edit'><img src='"+editIcon+"'/></a>";


            rows.push({
					id : row.id,
					cell : [ edit, row.credentialingTask, row.dateRecd,row.taskCompleted,row.time,row.postedBy ]
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
			window.location.href = contextPath + "/"+moduleName+"/add";
		}else if(com == 'Print Report'){
			var dt = $('#paymentProductivityListForm').serializeArray();
			var datePostedFrom = $('#datePostedFrom').val();
			var datePostedTo = $('#datePostedTo').val();
			var dateReceivedFrom = $('#dateRecievedFrom').val();
			var dateReceivedTo = $('#dateRecievedTo').val();
			var postedById = $("#postedById option:selected").val();
			var params = 'datePostedFrom='+$('#datePostedFrom').val()+'&datePostedTo='+$('#datePostedTo').val()
						  +'&dateReceivedFrom='+$('#dateReceivedFrom').val()+'&dateReceivedTo='+$('#dateReceivedTo').val()
						  + '&postedById='+$("#postedById option:selected").val();
			window.open (contextPath+'/'+moduleName+'/printreport?'+params,"mywindow","menubar=1,resizable=1,width=700,height=700");
		}
	}

	$(function() {
	    $( "#datePostedFrom" ).datepicker();
	});

	$(function() {
	    $( "#datePostedTo" ).datepicker();
	});
	$(function() {
	    $( "#dateReceivedFrom" ).datepicker();
	});
	$(function() {
	    $( "#dateReceivedTo" ).datepicker();
	});

	function printReport(){

		}

</script>
