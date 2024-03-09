<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li>&nbsp;Payment Productivity List</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<div class="msg">
		<p class="success" id="msgP">${success}</p>
	</div>
	
	<c:set var="pagesThreshold"><spring:message code="pages.thresholdValue"></spring:message></c:set>
	<c:set var="printValidation"><spring:message code="printValidation" arguments="${pagesThreshold}"></spring:message></c:set>
	
	<form class="form-container" id="paymentProductivityListForm">
		<div class="form-container-inner">
			<h3>Search </h3>
            <div class="row-container" >
            	<div class="element-block">
					<label>Productivity Type:</label>
					<div class="select-container1">
						<select id="prodType" name="prodType" class="selectbox">
							<option value="">-:Select Productivity:-</option>
							<c:forEach var="type" items="${typeList}">
								<option value="${type.key}" <c:if test="${prodType eq type.key}">selected</c:if>>${type.value}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="element-block">
					<label  class="right_lbl">Work Flow</label>
					<div class="select-container1">
						<select name="workflowId" id="workflowId" class="selectbox">
							<option value="">-: Select Work flow :-</option>
							<option value="1">To AR IPA FFS / HMO </option>
							<option value="2">To AR FFS</option>
							<option value="3">To AR CEP</option>
							<option value="4">To AR MCL</option>
							<option value="7">To AR MCR</option>
							<option value="8">To AR WC</option>
							<option value="6">Query to TL</option>
						</select>
					</div>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block">
					<label>Ticket No:</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input type = "text" id="ticketNumber" name="ticketNumber" maxlength="15" class="mid integerOnly" style="width: 108px;" />
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="element-block">
					<label  class="right_lbl">Posted By:</label>
					<div class="select-container1">
						<select id="postedById" name="postedById" class="selectbox">
							<option value="">-:Select Posted by:-</option>
							<c:forEach var="type" items="${postedBy}">
							<option value="${type.id}">${type.firstName} ${type.lastName}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block" >
					<label>CT Posted Date From:</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input type = "text"  id="datePostedFrom" name="datePostedFrom" class="mid" style="width: 108px;" readonly="readonly"/>
						<span class="right_curve"></span>
					</div>
				</div>

				<div class="element-block" >
					<label class="right_lbl">To:</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input type = "text" id="datePostedTo" name="datePostedTo" class="mid" style="width: 108px;" readonly="readonly" />
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="clr"></div>
			</div>
			
			<div class="row-container">
				<div class="element-block">
					<label>Created Date From:</label>
					<div class="input_text">
						<span class="left_curve"></span> 
						<input type="text" name="dateCreatedFrom" id="dateCreatedFrom" readonly="readonly"
							maxlength="50" class="mid" style="width: 108px;" /> 
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="element-block">
					<label class="right_lbl">To:</label>
					<div class="input_text">
						<span class="left_curve"></span> 
						<input type="text" name="dateCreatedTo" id="dateCreatedTo" readonly="readonly"
							class="mid" style="width: 108px;" /> 
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="clr"></div>
			</div>
			
			<div class="row-container">
				<label>Group/Doctor Name:</label>
				<div>
					<select name="doctorId" id="doctorId" class="selectbox">
						<option value="">-: Select All Group/Doctor :-</option>
						<c:forEach var="doctor" items="${doctorList}">
						<c:set var = "nonDeposit" value = "${doctor.nonDeposit}"/>
						<c:if test = "${nonDeposit}">
							<option value="${doctor.id}">${doctor.name} (Non-Deposit)</option>
						</c:if>
						<c:if test = "${!nonDeposit}">
							<option value="${doctor.id}">${doctor.name}</option>
						</c:if>
						</c:forEach>
					</select>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<label>&nbsp;</label>
				<input type="submit" title="Search" value="Search" class="login-btn" />
				<input type="button" title="Cancel" value="Cancel" onclick="javascript:resetForm(this.form);" class="login-btn" />
			</div>

			<div class="noSearchItem">&nbsp;</div>

			<table id="flex1" style="display: none"></table>
		</div>
	</form>
</div>

<script type="text/javascript">
	var moduleName = "paymentproductivitynonera";
	$("#dateCreatedTo").val('${createdTo}');
	$("#dateCreatedFrom").val('${createdFrom}');
	var totalRecord = 0;
	$("#flex1").flexigrid({
		url : contextPath + '/'+moduleName+'/json',
		preProcess : formatData,
		method : 'POST',

		colModel : [{
			display : 'Edit',
			name : 'edit',
			width : 40,
			sortable : false,
			align : 'center'
		}, {
			display : 'Query To TL',
			name : 'queryToTL',
			width : 40,
			sortable : false,
			align : 'center'
		}, {
			display : 'Ticket No.',
			name : 'paymentBatch',
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			display : 'Dr. Office',
			name : 'paymentBatch.doctor',
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			display : 'PaymentType',
			name : 'paymentProdType',
			width : 140,
			sortable : true,
			align : 'left'
		}, {
			display : 'Ck Number',
			name : 'chkNumber',
			width : 140,
			sortable : true,
			align : 'left'
		}, {
			display : 'Posted Amount',
			name : 'paymentBatch.manuallyPostedAmt',
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			display : 'Elec.Posted Amount',
			name : 'paymentBatch.elecPostedAmt',
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			display : 'Suspense Amount',
			name : 'paymentBatch.suspenseAccount',
			width : 90,
			sortable : true,
			align : 'left'
		},{
			display : 'Agency Money',
			name : 'paymentBatch.agencyMoney',
			width : 80,
			sortable : true,
			align : 'left'
		},{
			display : 'Other Income',
			name : 'paymentBatch.otherIncome',
			width : 80,
			sortable : true,
			align : 'left'
		},{
			display : 'Old Prior AR',
			name : 'paymentBatch.oldPriorAr',
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
			display : 'Manually',
			name : 'manualTransaction',
			width : 80,
			sortable : true,
			align : 'left'
		},{
			display : 'Elec. Transaction',
			name : 'elecTransaction',
			width : 80,
			sortable : true,
			align : 'left'
		},{
			display : 'Scan Date',
			name : 'scanDate',
			width : 80,
			sortable : true,
			align : 'left'
		},{
			display : 'Work Flow',
			name : 'workFlowName',
			width : 80,
			sortable : false,
			align : 'left'
		},{
			display : 'Posted By',
			name : 'postedBy',
			width : 80,
			sortable : false,
			align : 'left'
		}],
		buttons : [ {
			name : 'Add (ERA)',
			bclass : 'add',
			onpress : action
		},{
			name : 'Add (NON ERA)',
			bclass : 'add',
			onpress : action
		},{
			name : 'Add (CAP)',
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
		title : 'Payment Productivity',
		showTableToggleBtn : true,
		onSubmit : addFormData,
		//rpOptions: [10, 15, 20, 30, 50,100],
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

		if(null != $('#dateCreatedFrom').val() && null != $('#dateCreatedTo').val()){
			if(Date.parse($('#dateCreatedFrom').val()) > Date.parse($('#dateCreatedTo').val()) ){
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
		var queryIcon = '<c:url value="/static/resources/img/query.png"/>';
		var queryToTL = "";

		$.each(data.rows,function(i, row) {

			if(row.workFlowName == "Query to TL"){
				queryToTL = "&nbsp;&nbsp;<a href='"
					+ contextPath
					+"/paymentprodquerytotl/add?prodId="+
					+ row.id +"&ticketNumber="+row.paymentBatch+"'><img src='"+queryIcon+"' alt='Query To TL' title='Query To TL'/></a>";
			}else{
				queryToTL = "";
			}

			if(row.paymentProdType =='NON ERA'||row.paymentProdType =='CAP'){
	         	edit = "&nbsp;&nbsp;<a href='"
	         			+ contextPath
						+ "/paymentproductivitynonera"
						+ "/add?id="
						+ row.id
						+ "' class='edit'><img src='"+editIcon+"' title='Edit'/></a>";
			}else{
				edit = "&nbsp;&nbsp;<a href='"
					+ contextPath
					+ "/paymentproductivityERA"
					+ "/add?id="
					+ row.id
					+ "'class='edit'><img src='"+editIcon+"' title='Edit'/></a>";
			}

            rows.push({
					id : row.id,
					cell : [edit,queryToTL, 
					        '<span style="font-size:15px;">'+row.paymentBatch+'</span>',
					        row.doctorName,row.paymentProdType, 
							row.chkNumber, '$'+row.manuallyPostedAmt.toFixed(2), '$'+row.elecPostedAmt.toFixed(2),
							'$'+row.suspense.toFixed(2),'$'+row.agencyIncome.toFixed(2),'$'+row.otherIncome.toFixed(2),
							'$'+row.oldPriorAr.toFixed(2),row.datePosted,
							row.manualTransaction,row.elecTransaction,
							row.scanDate,row.workFlowName,row.postedBy ]
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
		if (com == 'Add (NON ERA)') {
			window.location.href = contextPath + "/"+ moduleName	+ "/add?prodType=2";
		}else if(com == 'Add (CAP)'){
			window.location.href = contextPath + "/"+ moduleName + "/add?prodType=3";
		}else if(com == 'Add (ERA)'){
			moduleName = "paymentproductivityERA";
			window.location.href = contextPath + "/"+ moduleName + "/add";
		}else if (com == 'Print Productivity') {

			/* if(totalRecord > parseInt('${pagesThreshold}'))
			{
				alert("${printValidation}");
			} */
			
			//window.open(contextPath + '/' + moduleName	+ "/print","_blank");
			akpmsPopupWindow(contextPath + '/' + moduleName	+ "/printhtml","payment_prod_list");
			
		}
	}

	$(function() {
	    $( "#datePostedFrom" ).datepicker();
	    $( "#datePostedTo" ).datepicker();
	    $("#dateCreatedTo").datepicker();
		$("#dateCreatedFrom").datepicker();

	});
	$('#workflowId').val('${workflowId}');
</script>
<script type="text/javascript"	src="<c:url value='/static/resources/js/common.js'/>"></script>
