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
			<li>&nbsp;Refund Request Productivity Report</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<div class="msg">
		<p class="success" id="msgP">${success}</p>
	</div>
	<form class="form-container" id="paymentProductivityListForm">
		<div class="form-container-inner">
			<h3>Refund Request Productivity Report </h3>
            <div class="row-container" >
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
          
			<div class="row-container">
				<div class="element-block" >
					<label>Created Date From:</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input type = "text"  id="datePostedFrom" name="datePostedFrom" maxlength="6" class="mid" style="width: 100px;" readonly/>
						<span class="right_curve"></span>
					</div>
				</div>

				<div class="element-block" >
					<label class="right_lbl">To:</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input type = "text" id="datePostedTo" name="datePostedTo" maxlength="6" class="mid" style="width: 100px;" readonly />
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="clr"></div>

			</div>
			
			<div class="row-container">
				<div class="element-block" >
					<label>Transaction Date From:</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input type = "text"  id="dateTransactionFrom" name="dateTransactionFrom" maxlength="6" class="mid" style="width: 100px;" readonly/>
						<span class="right_curve"></span>
					</div>
				</div>

				<div class="element-block" >
					<label class="right_lbl">To:</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input type = "text" id="dateTransactionTo" name="dateTransactionTo" maxlength="6" class="mid" style="width: 100px;" readonly />
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="clr"></div>

			</div>
           
           <div class="row-container">
				<div class="element-block" >
					<label>Resolution Date From:</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input type = "text"  id="dateResolutionFrom" name="dateResolutionFrom" maxlength="6" class="mid" style="width: 100px;" readonly/>
						<span class="right_curve"></span>
					</div>
				</div>

				<div class="element-block" >
					<label class="right_lbl">To:</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input type = "text" id="dateResolutionTo" name="dateResolutionTo" maxlength="6" class="mid" style="width: 100px;" readonly />
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="clr"></div>

			</div>
			

			<div class="row-container">
				<label>&nbsp;</label>
				<input type="button" title="Create Report" value="Create Report" onclick="searchERAProductivityReport();" class="login-btn" />
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
	var moduleName = "paymentproductivityrefundrequest";
	$("#flex1").flexigrid({
		url : contextPath + '/'+moduleName+'/json',
		preProcess : formatData,
		method : 'POST',

		colModel : [ {
			display : 'Ticket Number',
			name : 'ticketNumber',
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			display : 'Doctor Office',
			name : 'doctorName',
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			display : 'Credit Amount',
			name : 'creditAmount',
			width : 80,
			sortable : true,
			align : 'left'
		},{
			display : 'Creation Date',
			name : 'createdDate',
			width : 100,
			sortable : true,
			align : 'left'
		},{
			display : 'Transaction Date',
			name : 'transactionDate',
			width : 100,
			sortable : true,
			align : 'left'
		},{
			display : 'Resolution Date',
			name : 'resolutionDate',
			width : 100,
			sortable : true,
			align : 'left'
		},{
			display : 'Findings',
			name : 'findings',
			width : 150,
			sortable : true,
			align : 'left'
		},{
			display : 'Resolution',
			name : 'resolutionOrRemark',
			width : 150,
			sortable : true,
			align : 'left'
		} ],
		
		sortname : "id",
		sortorder : "asc",
		title : 'Refund Request Productivity Report',
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
					cell : [ row.ticketNumber, row.doctorName,row.creditAmount,row.createdDate,row.transactionDate,row.resolutionDate, row.findings,row.resolutionOrRemark]
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

	

	

	

	function searchERAProductivityReport() {

		if(null != $('#datePostedFrom').val() && null != $('#datePostedTo').val()){
			if(Date.parse($('#datePostedFrom').val()) > Date.parse($('#datePostedTo').val()) ){
				alert("To date must be after or equal to From date. Please try again.");
				return false;
			}
		}

	    if(null != $('#dateTransactionFrom').val() && null != $('#dateTransactionTo').val()){
			if(Date.parse($('#dateTransactionFrom').val()) > Date.parse($('#dateTransactionTo').val()) ){
				alert("To date must be after or equal to From date. Please try again.");
				return false;
			}
		}

	    if(null != $('#dateResolutionFrom').val() && null != $('#dateResolutionTo').val()){
			if(Date.parse($('#dateResolutionFrom').val()) > Date.parse($('#dateResolutionTo').val()) ){
				alert("To date must be after or equal to From date. Please try again.");
				return false;
			}
		}
	    $('#paymentProductivityListForm').submit();
	}

	$(function() {
	    $( "#datePostedFrom" ).datepicker();
	});

	$(function() {
	    $( "#datePostedTo" ).datepicker();
	});
	$(function() {
	    $( "#dateTransactionFrom" ).datepicker();
	});
	$(function() {
	    $( "#dateTransactionTo" ).datepicker();
	});
	$(function() {
	    $( "#dateResolutionFrom" ).datepicker();
	});
	$(function() {
	    $( "#dateResolutionTo" ).datepicker();
	});
	

</script>
<script type="text/javascript"
		src="<c:url value='/static/resources/js/common.js'/>"></script>
