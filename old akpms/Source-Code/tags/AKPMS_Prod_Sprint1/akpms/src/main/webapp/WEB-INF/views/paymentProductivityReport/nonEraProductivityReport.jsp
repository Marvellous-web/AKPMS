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
			<li>&nbsp;NON ERA Productivity Report</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<div class="msg">
		<p class="success" id="msgP">${success}</p>
	</div>
	<form class="form-container" id="paymentProductivityListForm">
		<div class="form-container-inner">
			<h3>NON ERA Productivity Report </h3>
            <div class="row-container" style="display:none;">
				<label>Doctor Office:</label>
				<div>
					<select id="doctor" name="doctor" class="selectbox">
						<option value="">-:Select Productivity:-</option>
						<c:forEach var="doctor" items="${doctorList}">
							<option value="${doctor.id}">${doctor.name}</option>
						</c:forEach>
					</select>
				</div>
			</div>

           <div class="row-container" >
				<label>Insurance:</label>
				<div>
					<select id="insurance" name="insurance" class="selectbox">
						<option value="">-:Select Productivity:-</option>
						<c:forEach var="insurance" items="${insuranceList}">
							<option value="${insurance.id}">${insurance.name}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="row-container">
				<label>Ticket Number:</label>

				<div class="input_text">
					<span class="left_curve"></span>
					<input type = "text" id="ticketNumber" name="ticketNumber" maxlength="15" class="mid integerOnly" style="width: 373px;" />
					<span class="right_curve"></span>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block" >
					<label>CT Posted Date From:</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input type = "text"  id="datePostedFrom" name="datePostedFrom" maxlength="50" class="mid" style="width: 100px;" readonly/>
						<span class="right_curve"></span>
					</div>
				</div>

				<div class="element-block" >
					<label class="right_lbl">To:</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input type = "text" id="datePostedTo" name="datePostedTo" maxlength="50" class="mid" style="width: 100px;" readonly />
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="clr"></div>

			</div>

			<div class="row-container">
				<div class="element-block" >
					<label>Created Date From:</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input type = "text"  id="dateReceivedFrom" name="dateReceivedFrom" maxlength="50" class="mid" style="width: 100px;" readonly/>
						<span class="right_curve"></span>
					</div>
				</div>

				<div class="element-block" >
					<label class="right_lbl">To:</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input type = "text" id="dateReceivedTo" name="dateReceivedTo" maxlength="50" class="mid" style="width: 100px;" readonly />
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
				<input type="submit" title="Create Report" value="Create Report" class="login-btn" />
				<input type="button" title="Cancel" value="Cancel" onclick="javascript:resetForm(this.form);"
				class="login-btn" />
			</div>

			<div class="noSearchItem">&nbsp;</div>

			<table id="flex1" style="display: none"></table>

		</div>
	</form>
</div>

<script type="text/javascript">
	var moduleName = "paymentproductivitynonera";
	$("#flex1").flexigrid({
		url : contextPath + '/'+moduleName+'/reportJson',
		preProcess : formatData,
		method : 'POST',

		colModel : [ {
			display : 'Ticket Number',
			name : 'paymentBatch',
			width : 85,
			sortable : true,
			align : 'left'
		}, {
			display : 'Total Amount Posted',
			name : 'paymentBatch.manuallyPostedAmt',
			width : 110,
			sortable : true,
			align : 'left'
		}, {
			display : 'Deposit Amount',
			name : 'depositAmt',
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			display : 'Doctor Office',
			name : 'doctorName',
			width : 85,
			sortable : true,
			align : 'left'
		}, {
			display : 'Insurance',
			name : 'insurance',
			width : 80,
			sortable : true,
			align : 'left'
		},{
			display : 'CT Posted Date',
			name : 'paymentBatch.datePosted',
			width : 80,
			sortable : true,
			align : 'left'
		},{
			display : 'Date created',
			name : 'createdOn',
			width : 90,
			sortable : true,
			align : 'left'
		},{
			display : 'Posted By',
			name : 'postedBy',
			width : 80,
			sortable : true,
			align : 'left'
		},{
			display : 'Time Taken',
			name : 'postedBy',
			width : 80,
			sortable : true,
			align : 'left'
		},{
			display : 'Remarks',
			name : 'remark',
			width : 200,
			sortable : true,
			align : 'left'
		} ],

		sortname : "id",
		sortorder : "asc",
		title : 'NON ERA Productivity Report',
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
            rows.push({
					id : row.id,
					cell : [ row.paymentBatch, row.manuallyPostedAmt,row.depositAmt,row.doctorName,row.insurance,row.datePosted, row.receivedDate,row.postedBy,row.time,row.remark ]
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

</script>
<script type="text/javascript"
		src="<c:url value='/static/resources/js/common.js'/>"></script>
