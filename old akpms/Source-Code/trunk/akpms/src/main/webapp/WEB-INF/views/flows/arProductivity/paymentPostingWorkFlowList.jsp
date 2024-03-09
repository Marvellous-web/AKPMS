<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="dashboard-container" >
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home </a> &raquo; </li>
			<li>&nbsp;Payment Posting WorkFlow List</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<div class="msg">
		<p class="success" id="msgP">${success}</p>
	</div>

	<form class="form-container" id="paymentPostingSearchForm">
		<div class="form-container-inner">
			<h3>Search Payment Posting WorkFlow</h3>

			<div class="row-container">
				<div class="element-block" >
					<label>Patient Name:</label>
					<span class="input_text">
						<span class="left_curve"></span>
						<input type="text" id="patientName" name="arProductivity.patientName" maxlength="50"  class="mid" style="width: 100px" />
						<span class="right_curve"></span>
					</span>
				</div>
				<div class="element-block" >
					<label class="right_lbl">Patient ID:</label>
					<span class="input_text">
						<span class="left_curve"></span>
						<input type="text" id="patientAccountNo" name="arProductivity.patientAccNo"  maxlength="50"  class="mid" style="width: 100px" />
						<span class="right_curve"></span>
					</span>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block" >
					<label>Logged Date (From):</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input type = "text"  id="dateCreatedFrom" name="dateCreatedFrom" class="mid" style="width: 100px;" readonly="readonly"/>
						<span class="right_curve"></span>
					</div>
				</div>

				<div class="element-block" >
					<label class="right_lbl">Logged Date (To):</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input type = "text" id="dateCreatedTo" name="dateCreatedTo"  class="mid" style="width: 100px;" readonly="readonly" />
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="clr"></div>
			</div>
			
			<div class="row-container">
				<div class="element-block" style="width: 100%">
					<label>Insurance:</label>
					<span class="select-container">
						<select name="insuranceId" id="insuranceId" class="selectbox">
							<option value="">-: Select Insurance :-</option>
							<c:forEach  items="${insuranceList}" var="insurance">
								<option value="${insurance.id}">${insurance.name}</option>
							</c:forEach>
						</select>
					</span>
				</div>
				<div class="clr"></div>
			</div>
			
			<div class="row-container">
				<div class="element-block" style="width: 100%">
					<label>Provider (Doctor Office):</label>
					<span class="select-container">
						<select name="doctorId" id="doctorId" class="selectbox">
							<option value="">-: Select Doctor:-</option>
							<c:forEach  items="${doctorList}" var="doctor">
								<option value="${doctor.id}">
									${doctor.name}
									<c:if test="${not empty doctor.parent}">
										(${doctor.parent.name})
									</c:if>
								</option>
							</c:forEach>
						</select>
					</span>
				</div>
				<div class="clr"></div>
			</div>
			
			<div class="row-container">
				<label>Status:</label>
				<input type="checkbox" name="status" id="status_pending" value="Pending" /> Pending &nbsp;&nbsp;&nbsp;
				<input type="checkbox" name="status" id="status_approved" value="Approve"/> Approve &nbsp;&nbsp;&nbsp;
				<input type="checkbox" name="status" id="status_reject" value="Reject"/> Rejected &nbsp;&nbsp;&nbsp;
			    <input type="checkbox" name="status" id="status_completed" value="Completed"/> Completed &nbsp;&nbsp;&nbsp;
				<div class="clr"></div>
			</div>
			
			<div class="row-container">
				<label>Offset:</label>
				<input type="checkbox" name="isOffset" id="isOffset" value="1" />
				<div class="clr"></div>
			</div>
			
			<div class="row-container">
				<div class="element-block" style="width: 100%">
					<label for="teamId">Team:</label>
					<span class="select-container1" style="display:inline;">
						<select name="teamId" class="selectbox">
							<option value="">-: Select Team:-</option>
							<c:forEach var="team" items="${teamList}">
								<option value="${team.id}">
									${team.name}
								</option>
							</c:forEach>
						</select>
					</span>
				</div>
				<div class="clr"></div>
			</div>

			<br />
			<input type="hidden" name="arProductivity.id" value="${prodId}"/>
			<span class="input-container">
				<label>&nbsp;</label>
				<input type="submit" title="Search" value="Search"	 class="login-btn" />
				 <input type="button" title="Cancel" value="Cancel" onclick="javascript:resetForm(this.form);"
				class="login-btn" />
			</span>

			<div class="noSearchItem">&nbsp;</div>

			<table id="flex1" style="display: none"></table>
		</div>
	</form>
	
	<div class="popup2" id="popup_ticket_number" style="display:none;">
	   <span class="button bClose"><span>X</span></span>
	   <div class="popup-container">
	   		<div class="popup-container-inner" id="modificationSummaryContent">
	   			<h3>Enter Ticket Number</h3>
				<form:form id="Argus_TL" name="Argus_TL" onsubmit="return changeStatus('Approve')">
					<span class="button bClose"><span>X</span></span>
					<span class="input-container">
					 	<label>Ticket Number</label>
					</span>
					<span class="input-container">
						<input type="text" id="ticketNumber" class="mid integerOnly"/>
					</span>
					<br/>
					<input type="button" title="Submit" value="Submit" class="submitButton" onclick="changeStatus('Approve')"/>
				</form:form>
	   		</div>
	   </div>
	</div>
	
</div>

<c:if test="${offset eq '1'}">
	<script type="text/javascript">
		$('#isOffset').prop('checked', true);
	</script>
</c:if>

<c:choose>
	<c:when test="${status eq 'Approve'}">
		<script type="text/javascript">
			$('#status_approved').prop('checked', true);
		</script>
	</c:when>
	<c:when test="${status eq 'Completed'}">
		<script type="text/javascript">
			$('#status_completed').prop('checked', true);
		</script>
	</c:when>
	<c:when test="${status eq 'Reject'}">
		<script type="text/javascript">
			$('#status_reject').prop('checked', true);
		</script>
	</c:when>
	<c:otherwise>
		<script type="text/javascript">
			$('#status_pending').prop('checked', true);
		</script>
	</c:otherwise>
</c:choose>

<script type="text/javascript">
	var moduleName = "flows/paymentpostingworkflow";
	var ids = "";
	var status = "";
	$("#flex1").flexigrid({
		url : contextPath+'/'+moduleName+'/json',
		preProcess : formatData,
		method : 'GET',

		colModel : [ {
			display : 'Edit',
			name : 'edit',
			width : 40,
			sortable : false,
			align : 'center'
		}, {
			display : 'Status',
			name : 'status',
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			display : 'Patient Name',
			name : 'arProductivity.patientName',
			width : 150,
			sortable : true,
			align : 'left'
		}, {
			display : 'Patient ID',
			name : 'arProductivity.patientAccNo',
			width : 150,
			sortable : true,
			align : 'left'
		}, {
			display : 'CPT',
			name : 'cpt',
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			display : 'Doctor Name',
			name : 'arProductivity.doctor.name',
			width : 150,
			sortable : true,
			align : 'left'
		}, {
			display : 'Insurance',
			name : 'arProductivity.insurance.name',
			width : 150,
			sortable : true,
			align : 'left'
		}, {
			display : 'DOS',
			name : 'dos',
			width : 150,
			sortable : true,
			align : 'left'
		}, {
			display : 'Team',
			name : 'arProductivity.team.name',
			width : 80,
			sortable : false,
			align : 'left'
		}, {
			display : 'Billed Amount',
			name : 'billedAmount',
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			display : 'Amount Paid By Primary ',
			name : 'primaryAmount',
			width : 80,
			sortable : true,
			align : 'left'
		},{
			display : 'Amount Paid By Secondary',
			name : 'secondaryAmount',
			width : 80,
			sortable : true,
			align : 'left'
		} , {
			display : 'Contractual Adjustment',
			name : 'contractualAdj',
			width : 80,
			sortable : true,
			align : 'center'
		} , {
			display : 'Bulk Amount of Payment',
			name : 'bulkPaymentAmount',
			width : 80,
			sortable : true,
			align : 'left'
		},  {
			display : 'Patient Response',
			name : 'patientResponse',
			width : 80,
			sortable : true,
			align : 'center'
		}, {
			display : 'Date Check Issue',
			name : 'checkIssueDate',
			width : 80,
			sortable : true,
			align : 'center'
		}, {
			display : 'Check #',
			name : 'checkNo',
			width : 80,
			sortable : true,
			align : 'center'
		}, {
			display : 'Date Check Cashed',
			name : 'checkCashedDate',
			width : 80,
			sortable : true,
			align : 'center'
		}, {
			display : 'Address Check Send To',
			name : 'addressCheckSend',
			width : 80,
			sortable : true,
			align : 'center'
		}, {
			display : 'Copy of Cancel Check',
			name : 'copyCancelCheck',
			width : 80,
			sortable : false,
			align : 'left'
		}, {
			display : 'EOB',
			name : 'eob',
			width : 80,
			sortable : false,
			align : 'left'
		}, {
			display : 'Modified On',
			name : 'modifiedOn',
			width : 80,
			sortable : true,
			align : 'left'
		} , {
			display : 'Modified By',
			name : 'modifiedBy',
			width : 80,
			sortable : true,
			align : 'left'
		} ],buttons : [{
			name : 'Print',
			bclass : 'print',
			onpress : action
		},{
			name : 'Approve',
			bclass : 'approve',
			onpress : action
		},{
			name : 'Reject',
			bclass : 'reject',
			onpress : action
		}],
		sortname : "id",
		sortorder : "desc",
		title : 'Payment Posting Workflow',
		singleSelect: false,
		showTableToggleBtn : true,
		onSubmit : addFormData,
		width : 100,
		height : 300
	});

	//This function adds paramaters to the post of flexigrid. You can add a verification as well by return to false if you don't want flexigrid to submit
	function addFormData() {
		//passing a form object to serializeArray will get the valid data from all the objects, but, if the you pass a non-form object, you have to specify the input elements that the data will come from
		var dt = $('#paymentPostingSearchForm').serializeArray();
		$("#flex1").flexOptions({
			params : dt
		});
		return true;
	}

	$('#paymentPostingSearchForm').submit(function() {

		if(null != $('#dateCreatedFrom').val() && null != $('#dateCreatedTo').val()){
			if(Date.parse($('#dateCreatedFrom').val()) > Date.parse($('#dateCreatedTo').val()) ){
				alert("Logged Date 'To' date must be after or equal to Logged Date 'From' date. Please try again.");
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
		var eob = "";
		var copyCancelCheck = "";
		var edit = "";
		$.each(data.rows, function(i, row) {
			eob = "";
			copyCancelCheck = "";
			var url = contextPath+"/flows/paymentpostingworkflow/add/?id="+row.id;
			//row.attachmentName
			if(row.copyCancelCheckAttId!=null){
			copyCancelCheck = "<div class='download-container'><a class='download' href='"+contextPath+"/file/fileDownload?id="+row.copyCancelCheckAttId+"' title='Download File'>"+row.copyCancelCheckAttName+"</a></div>";
			}
			if(row.eobAttId!=null){
			eob = "<div class='download-container'><a class='download' href='"+contextPath+"/file/fileDownload?id="+row.eobAttId+"' title='Download File'>"+row.eobAttName+"</a></div>";
			}
// 			edit = "<a href='"+contextPath+"/flows/paymentpostingworkflow/add/?id="+row.id+"' class='edit'><img src='"+editIcon+"'/></a>";

			edit = "&nbsp;<a href='javascript:void(0);' onclick='javascript:akpmsPopupWindow(\""
				+ url
				+ "&Popup=1&operationType=0\")' class='edit'><img src='"+editIcon+"' title='Edit' alt='Edit'/></a>";

			rows.push({
				id : row.id,
				cell : [edit, row.status, row.patientName, row.patientAccNo, row.cpt,
						row.provider, row.insurance, row.dos, row.team,
						 row.billedAmount, row.primaryAmount, row.secondaryAmount, row.contractualAdj,
						 row.bulkPaymentAmount,row.patientResponse, row.checkIssueDate,row.checkNo,
						 row.checkCashedDate,row.addressCheckSend, copyCancelCheck, eob ,row.modifiedOn,row.modifiedBy]
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
		if (com == 'Approve') {
			status = "Approve";
			//window.open(contextPath + '/' + moduleName	+ "/print","_blank");
			if(jQuery('#flex1 .trSelected').length>0){
			jQuery('#flex1 .trSelected').each( function(){
				//var id = parseInt($(this).attr('id'), 10);
                //alert($(this).attr('id').substring(3));
                ids = ids + $(this).attr('id').substring(3) +",";
            });
			changeStatus('Approve');
			}
			else{
					alert("Please select atleast one row");
				}

			/* $('#popup_ticket_number').bPopup({
	               onOpen: function() {
	            	   $('#ticketNumber').val("");
						
	            	   }
	           }); */


		}
		
		else if(com == 'Reject'){
			if(jQuery('#flex1 .trSelected').length>0){
			jQuery('#flex1 .trSelected').each( function(){
				//var id = parseInt($(this).attr('id'), 10);
                //alert($(this).attr('id').substring(3));
                ids = ids + $(this).attr('id').substring(3) +",";
            });
			changeStatus('Reject');
			}
			else{
				alert("Please select atleast one row");
			}
			}
			
		if (com == 'Print') {
			akpmsPopupWindow(contextPath + '/' + moduleName	+ "/printhtmlreport","payment_posting_report");
		}
	}

	function changeStatus(status){
		
		$.ajax({
			type : "GET",
			dataType : "json",
			url : contextPath+'/'+ moduleName + "/changeStatus",
			data : "ids=" + ids +"&ticketNumber=" + $('#ticketNumber').val() + "&status="+status,
			success : function(data) {
				//alert("Query: " + data.query + " - Total affected rows: " + data.total);
				//$("#flex1").flexReload();
				//$("#msgP").text("User has been successfully "+st+"d.");
				window.location.reload();
			},
			error : function(data) {
				alert(data);
			}
		});

		return false;
		
		}
	$(function() {
	    $( "#dateCreatedFrom" ).datepicker();
	});

	$(function() {
	    $( "#dateCreatedTo" ).datepicker();
	});

</script>
