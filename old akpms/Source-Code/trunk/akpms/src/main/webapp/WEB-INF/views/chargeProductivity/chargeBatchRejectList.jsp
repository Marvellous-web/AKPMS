<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li>&nbsp;Charge Batch Rejections</li>
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
			<h3>Search Charge Rejections</h3>
			<span class="input-container"> 
				<label>Keyword:</label> 
				<!-- <span class="input_text"> 
					<span class="left_curve"></span> 
					<input type="text" id="ticketNumber" name="ticketNumber" maxlength="7" class="mid integerOnly" style="width: 373px" /> 
					<span class="right_curve"></span>
				</span> -->
				<span class="input_text"> 
					<span class="left_curve"></span> 
					<input type="text" id="keyword" name="keyword" maxlength="7" class="mid" style="width: 373px" /> 
					<span class="right_curve"></span>
				</span>
			</span> 
			
			<br />

			<div class="row-container">
				<div class="element-block">
					<input type="checkbox" name="newRejections"
						<c:if test="${fetchType == 'newrejection' }"> checked </c:if>
						id="newRejections" value="1" /> New Rejections
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block" style="width: 100%">
					<input type="radio" name="requestDueRecord"
						<c:if test="${fetchType == '1reqdue' }"> checked </c:if> value="1" />
					1<sup>st</sup> request due records &nbsp;&nbsp;&nbsp; <input
						type="radio" name="requestDueRecord"
						<c:if test="${fetchType == '2reqdue' }"> checked </c:if> value="2" />2<sup>nd</sup>
					request due records
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block" style="width: 100%">
					<input type="radio" name="requestRecord"
						<c:if test="${fetchType == '1req' }"> checked </c:if>
						id="firstRequestRecord" value="1" /> 1<sup>st</sup> request
					records &nbsp;&nbsp;&nbsp; <input type="radio" name="requestRecord"
						<c:if test="${fetchType == '2req' }"> checked </c:if>
						id="secondRequestRecord" value="2" /> 2<sup>nd</sup> request
					records
				</div>
				<div class="clr"></div>
			</div>

			<%-- <div class="row-container">
				<div class="element-block">
					<input type="checkbox" 	name="resolved" <c:if test="${fetchType == 'resolved' }"> checked </c:if> value="1" id="resolved" /> Resolved Only
				</div>
				<div class="clr"></div>
			</div> --%>

			<div class="row-container">
				<div class="element-block">
					<input type="checkbox" name="dummyCpt"
						<c:if test="${fetchType == 'resolvedcpt' }"> checked </c:if>
						value="1" id="dummyCpt" /> Dummy CPT records only
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block">
					<label>Reason to Reject:</label>
					<div class="select-container1">
						<select id="reasonToReject" name="reasonToReject">
							<option value="">- Select a Reason to Reject -</option>
							<optgroup label="Coding">
								<option value="CO-Need CPT & ICD">CO-Need CPT & ICD</option>
								<option value="CO-Need PQRS Form">CO-Need PQRS Form</option>
								<option value="CO-Need BMI">CO-Need BMI</option>
								<option value="CO-Provide drug/Vaccine info">CO-Provide
									drug/Vaccine info</option>
								<option value="CO-Need Admin">CO-Need Admin</option>
								<option value="CO-Need CPT">CO-Need CPT</option>
								<option value="CO-Need ICD">CO-Need ICD</option>
								<option value="CO-Need LMP">CO-Need LMP</option>
								<option value="CO-Need Date of admission (DOA)">CO-Need
									Date of admission (DOA)</option>
								<option value="CO-Need Date of service (DOS)">CO-Need
									Date of service (DOS)</option>
								<option value="CO-Need Location/ POS">CO-Need Location/
									POS</option>
								<option value="CO-Need Date of Injury (DOI)">CO-Need
									Date of Injury (DOI)</option>
								<option value="CO-Need ACF#">CO-Need ACF#</option>
								<option value="CO-Need Referring provider">CO-Need
									Referring provider</option>
								<option value="CO-Missing Surgery charges">CO-Missing
									Surgery charges</option>
								<option value="CO-Need Operative/ procedure report">CO-Need
									Operative/ procedure report</option>
								<option value="CO-Need NDC#">CO-Need NDC#</option>
								<option value="CO-Miscellaneous">CO-Miscellaneous</option>
							</optgroup>
							<optgroup label="Charge Entry">
								<option value="CE-For Documentation">CE-For Documentation</option>
								<option value="CE-Need CPT & ICD">CE-Need CPT & ICD</option>
								<option value="CE-Missing Super bill">CE-Missing Super
									bill</option>
								<option value="CE-Need PQRS Form">CE-Need PQRS Form</option>
								<option value="CE-Need BMI">CE-Need BMI</option>
								<option value="CE-Provide drug/Vaccine info">CE-Provide
									drug/Vaccine info</option>
								<option value="CE-Need Admin">CE-Need Admin</option>
								<option value="CE-Need Billing Provider">CE-Need
									Billing Provider</option>
								<option value="CE-Need Billing/Service provider">CE-Need
									Billing/Service provider</option>
								<option value="CE-Need CPT">CE-Need CPT</option>
								<option value="CE-Need ICD">CE-Need ICD</option>
								<option value="CE-Need LMP">CE-Need LMP</option>
								<option value="CE-Need Authorization#">CE-Need
									Authorization#</option>
								<option value="CE-Need Date of admission (DOA)">CE-Need
									Date of admission (DOA)</option>
								<option value="CE-Need Date of service (DOS)">CE-Need
									Date of service (DOS)</option>
								<option value="CE-Need Location/ POS">CE-Need Location/
									POS</option>
								<option value="CE-Need Date of Injury (DOI)">CE-Need
									Date of Injury (DOI)</option>
								<option value="CE-Need ACF#">CE-Need ACF#</option>
								<option value="CE-Need Referring provider">CE-Need
									Referring provider</option>
								<option value="CE-Need Service provider">CE-Need
									Service provider</option>
								<option value="CE-Missing Surgery charges">CE-Missing
									Surgery charges</option>
								<option value="CE-Need Operative/ procedure report">CE-Need
									Operative/ procedure report</option>
								<option value="CE-Need NDC#">CE-Need NDC#</option>
								<option value="CE-Miscellaneous">CE-Miscellaneous</option>
							</optgroup>
							<optgroup label="Demo">
								<option value="DE-Policy# missing/invalid">DE-Policy#
									missing/invalid</option>
								<option value="DE-Need insurance info">DE-Need
									insurance info</option>
								<option value="DE-Provide patient demographic">DE-Provide
									patient demographic</option>
								<option value="DE-Miscellaneous">DE-Miscellaneous</option>
								<option value="DE-Argus to Call">DE-Argus to Call</option>
							</optgroup>
						</select>
					</div>
				</div>
				<div class="element-block">
					<label class="right_lbl">Doctor/Group Name:</label>
					<div class="select-container1">
						<select name="doctorId" class="selectbox" id="doctorId">
							<option value="">-: Select Provider Name :-</option>
						<!--	<c:forEach var="doctor" items="${doctorList}">
								<option value="${doctor.id}">
									${doctor.name}
									<c:if test="${not empty doctor.parent}">
										(${doctor.parent.name})
									</c:if>
								</option>
							</c:forEach>  -->
							
							<c:forEach var="doctor" items="${doctorList}">
								<option value="${doctor.id}">${doctor.name}
									<c:if test="${doctor.nonDeposit && doctor.id=='86'}">(Non-Deposit)</c:if>
									<c:if test="${not empty doctor.parent}">
										(${doctor.parent.name})										
									</c:if>
								</option>
							</c:forEach>
							
						</select>
					</div>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block">
					<label>Work Flow:</label>
					<div class="select-container1">
						<select id="workFlow" name="workFlow">
							<option value="">- Select -</option>
							<option value="1">Internal</option>
							<option value="2">Argus TL</option>
							<option value="3">Dr.Office </option>
						</select>
					</div>
				</div>
				<div class="element-block">
					<label class="right_lbl">Status:</label> <input type="checkbox"
						name="status" id="status_resolved" value="Resolved" /> Resolved
					&nbsp;&nbsp;&nbsp; <input type="checkbox" name="status"
						id="status_completed" value="Completed" /> Completed
					&nbsp;&nbsp;&nbsp;
					<input type="checkbox"
						name="status" id="status_pending" value="Pending"/> Pending
					&nbsp;&nbsp;&nbsp;
					
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block">
					<label>Created Date From:</label>
					<div class="input_text">
						<span class="left_curve"></span> <input type="text"
							name="dateCreatedFrom" id="dateCreatedFrom" readonly="readonly"
							maxlength="50" class="mid" style="width: 108px;" /> <span
							class="right_curve"></span>
					</div>
				</div>
				<div class="element-block">
					<label class="right_lbl">To:</label>
					<div class="input_text">
						<span class="left_curve"></span> <input type="text"
							name="dateCreatedTo" id="dateCreatedTo" readonly="readonly"
							class="mid" style="width: 108px;" /> <span class="right_curve"></span>
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
								<option value="${user.id}">${user.firstName}
									${user.lastName}</option>
							</c:forEach>
						</select>
					</div>
				</div>

				<div class="clr"></div>
			</div>

			<span class="input-container"> <label>&nbsp;</label> <input
				type="submit" title="Search" value="Search" class="login-btn" /> <input
				type="button" title="Cancel" value="Cancel"
				onclick="javascript:resetForm(this.form);" class="login-btn" />
			</span>

			<div class="noSearchItem">&nbsp;</div>

			<table id="flex1" style="display: none"></table>

		</div>
	</form>
</div>

<script type="text/javascript">
	var moduleName = "chargeproductivityreject";
	var status = "${fetchType}";
	if (status == 'resolved') {
		$('#status_resolved').prop('checked', true);
	} else if (status == 'completed') {

		$('#status_completed').prop('checked', true);
	}

	$("#flex1").flexigrid({
		url : moduleName + '/json',
		preProcess : formatData,
		method : 'POST',

		colModel : [ {
			display : 'Edit',
			name : 'edit',
			width : 40,
			sortable : false,
			align : 'center'
		}, {
			display : 'Resolution',
			name : 'resolution',
			width : 40,
			sortable : false,
			align : 'center'
		}, {
			display : 'Status',
			name : 'status',
			width : 60,
			sortable : true,
			align : 'center'
		}, {
			display : 'Location',
			name : 'location',
			width : 60,
			sortable : true,
			align : 'center'
		}, {
			display : 'Work FLow',
			name : 'workFlow',
			width : 60,
			sortable : true,
			align : 'center'
		}, {
			display : 'Ticket No',
			name : 'chargeBatchProcessing',
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			display : 'Doctor Name',
			name : 'chargeBatchProcessing.doctor.name',
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			display : 'Reason to Reject',
			name : 'reasonToReject',
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			display : 'Patient Name',
			name : 'patientName',
			width : 160,
			sortable : true,
			align : 'left'
		}, {
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
			display : 'Insurance type',
			name : 'insuranceType',
			width : 80,
			sortable : true,
			align : 'left'
		},{
			display : 'Batch Received',
			name : 'dateReceived',
			width : 80,
			sortable : false,
			align : 'left'
		},{
			display : 'Created By',
			name : 'createdBy',
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			display : 'Created On',
			name : 'createdOn',
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			display : 'Completed Date',
			name : 'completedOn',
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			display : 'Date of 1st request to Doctor Office',
			name : 'dateOfFirstRequestToDoctorOffice',
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			display : 'Date of 2nd request to Doctor Office',
			name : 'dateOfSecondRequestToDoctorOffice',
			width : 80,
			sortable : true,
			align : 'left'
		} ],
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

	$('#rejectListForm')
			.submit(
					function() {

						if (null != $('#dateCreatedFrom').val()
								&& null != $('#dateCreatedTo').val()) {
							if (Date.parse($('#dateCreatedFrom').val()) > Date
									.parse($('#dateCreatedTo').val())) {
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
		var resolutionIcon = '<c:url value="/static/resources/img/resolution_icon.png"/>';

		var workFlow = "";
		$.each(
						data.rows,
						function(i, row) {
							var url1 = contextPath
							+ "/"
							+ moduleName
							+ "/add?rejectId="
							+ row.id;
							
							var url_Reupdate = contextPath
							+ "/"
							+ moduleName
							+ "/reupdate/?rejectId="
							+ row.id
							+ "&batchId="
							+ row.chargeBatchProcessing;
							workFlow = "";
							/* edit = "&nbsp;&nbsp;<a href='"
									+ contextPath
									+ "/"
									+ moduleName
									+ "/add?rejectId="
									+ row.id
									+ "' class='edit'><img alt='Edit' title='Edit' src='"+editIcon+"'/></a>";
							 */
							 edit = "&nbsp;<a href='javascript:void(0);' onclick='javascript:akpmsPopupWindow(\""
									+ url1
									+ "&Popup=1&operationType=0\",\"rejectionEdit\")' class='edit'><img src='"+editIcon+"' title='Edit' alt='Edit'/></a>";

							/* resolution = "&nbsp;&nbsp;<a href='"
									+ contextPath
									+ "/"
									+ moduleName
									+ "/reupdate/?rejectId="
									+ row.id
									+ "&batchId="
									+ row.chargeBatchProcessing
									+ "' class='edit'><img alt='Resolution' title='Resolution' src='"+resolutionIcon+"'/></a>"; */
									resolution = "&nbsp;&nbsp;<a href='javascript:void(0);' onclick='javascript:akpmsPopupWindow(\""
										+url_Reupdate
										+"&Popup=1\",\"rejectionResolution\");' class='edit'><img alt='Resolution' src='"+resolutionIcon+"' title='Resolution'/></a>";
									

									if (row.workFLow == 1) {
										workFlow = "Internal";
									} else if (row.workFLow == 2) {
										workFlow = "Argus TL";
									}else if(row.workFLow == 3) {
										workFlow = "Dr.Office"
									}
									
							rows.push({
								id : row.id,
								cell : [
										edit,
										resolution,
										row.status,
										row.location,
										workFlow,
										'<span style="font-size:15px;">'
												+ row.chargeBatchProcessing
												+ '</span>', row.doctorName,
										row.reasonToReject, row.patientName,
										row.dob, row.sequence, row.account,
										row.dateOfService, row.insuranceType,
										row.dateReceived,
										row.createdBy, row.createdOn,row.completedOn,
										row.dateOfFirstRequestToDoctorOffice,
										row.dateOfSecondRequestToDoctorOffice ]
							});
						});

		// if no record then the div will not appear there
		if (data.total == 0) {
			$('div.noSearchItem').text('No records found.');
		} else {
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
			window.location.href = contextPath + "/" + moduleName + "/add";
		} else if (com == 'Print') {
			akpmsPopupWindow(contextPath + '/' + moduleName + "/printableweb",
					"print");
		}
	}

	$(function() {
		$("#dateCreatedTo").datepicker();
		$("#dateCreatedFrom").datepicker();
	});
</script>
<script type="text/javascript"
	src="<c:url value='/static/resources/js/common.js'/>"></script>
