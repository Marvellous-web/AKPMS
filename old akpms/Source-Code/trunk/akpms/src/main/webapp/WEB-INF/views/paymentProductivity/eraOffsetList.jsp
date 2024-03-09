<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li>&nbsp;Offset List</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<div class="msg">
		<p class="success" id="msgP">${success}</p>
	</div>
	<form class="form-container" id="offsetListForm">
		<div class="form-container-inner">
			<h3>Search</h3>
			<div class="row-container">
				<div class="element-block">
					<label>Ticket No:</label>
					<div class="input_text">
						<span class="left_curve"></span> 
							<input type="text" id="ticketNumber" name="ticketNumber" maxlength="15" class="mid integerOnly" style="width: 100px;" /> 
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="element-block">
					<label class="right_lbl">Patient Name:</label>
					<div class="input_text">
						<span class="left_curve"></span> <input type="text"
							id="patientName" name="patientName" maxlength="100"
							class="mid" style="width: 100px;" /> <span
							class="right_curve"></span>
					</div>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block">
					<label>Account No:</label>
					<div class="input_text">
						<span class="left_curve"></span> <input type="text"
							id="accountNumber" name="accountNumber" maxlength="100"
							class="mid" style="width: 100px;" /> <span
							class="right_curve"></span>
					</div>
				</div>
				<div class="element-block">
					<label class="right_lbl">Check No:</label>
					<div class="input_text">
						<span class="left_curve"></span> <input type="text"
							class="mid" id="checkNumber" name="checkNumber" maxlength="100"
							style="width: 100px;" /> <span
							class="right_curve"></span>
					</div>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block">
					<label>Posted From:</label>
					<div class="input_text">
						<span class="left_curve"></span> <input type="text"
							id="datePostedFrom" name="datePostedFrom" 
							class="mid" style="width: 100px;" readonly="readonly" /> <span
							class="right_curve"></span>
					</div>
				</div>

				<div class="element-block">
					<label class="right_lbl">To:</label>
					<div class="input_text">
						<span class="left_curve"></span> <input type="text"
							id="datePostedTo" name="datePostedTo" class="mid"
							style="width: 100px;" readonly="readonly" /> <span class="right_curve"></span>
					</div>
				</div>
				<div class="clr"></div>

			</div>

					
			<div class="row-container" >
            	<div class="element-block">
					<label>Posted By:</label>
					<div class="select-container2">
						<select id="postedById" name="postedById" class="selectbox">
							<option value="">-:Select Posted by:-</option>
							<c:forEach var="type" items="${postedBy}">
								<option value="${type.id}">${type.firstName} ${type.lastName}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="element-block">
					<label  class="right_lbl">Doctor Group/Name</label>
					<div class="select-container2">
						<select name="doctorId" id="doctorId" class="selectbox"
							onchange="javascript:showHideElements();">
							<option value="">-: Select All Group/Doctor :-</option>
							<c:forEach var="doctor" items="${doctorList}">
								<option value="${doctor.id}">${doctor.name}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="clr"></div>
			</div>
			
			<div class="row-container" >
            	<div class="element-block">
					<label>Insurance Name:</label>
					<div class="select-container2">
						<select id="insuranceId" name="insuranceId" class="selectbox">
							<option value="">-: Select Insurance :-</option>
							<c:forEach var="insurance" items="${insuranceList}">
								<option value="${insurance.id}">${insurance.name}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="element-block">
					<label  class="right_lbl">Status</label>
					<div class="select-container2">
						<select name="status" id="status" class="selectbox">
							<option value="">-: Select Status :-</option>
							<option value="Pending">Pending </option>
							<option value="AR Step Completed">AR Step Completed</option>
							<option value="Offset Resolve">Offset Resolve</option>
						</select>
					</div>
				</div>
				<div class="clr"></div>
			</div>
			
			<div class="row-container">
				<div class="element-block">
					<label>Offset Ticket No:</label>
					<div class="input_text">
						<span class="left_curve"></span> <input type="text"
							id="offsetTicketNumber" name="offsetTicketNumber" maxlength="100"
							class="mid" style="width: 100px;" /> <span class="right_curve"></span>
					</div>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<label>&nbsp;</label>
				<input type="submit" title="Search" value="Search"  class="login-btn" />
				<input type="button" title="Cancel" value="Cancel" 	onclick="javascript:resetForm(this.form);" class="login-btn" />
			</div>

			<div class="noSearchItem">&nbsp;</div>

			<table id="flex1" style="display: none"></table>
		</div>
	</form>
	<div class="popup2" id="popup_showRecord" style="display: none;">
		<span class="button bClose"><span>X</span></span>
		<div class="popup-container" id="divContainer"><img	src="<c:url value="/static/resources/img/ajax-loader.gif"/>" alt="" /></div>
	</div>
	
	<div class="popup2" id="popup_ticket_number" style="display:none;">
			   <span class="button bClose"><span>X</span></span>
			   <div class="popup-container">
			   		<div class="popup-container-inner" id="modificationSummaryContent">
			   			<h3>Enter Ticket Number</h3>
						<form:form id="offset_ticket_number" name="offset_ticket_number" onsubmit="return changeStatus('Offset Resolve')">
							<span class="button bClose"><span>X</span></span>
							<span class="input-container">
							 	<label>Ticket Number<em class="mandatory">*</em></label>
							</span>
							<span class="input-container">
							<div class="input_text">
							<span class="left_curve"></span>
								<input type="text" id="offset_ticketNumber" class="mid integerOnly"/>
							<span class="right_curve"></span>
							</div>
							</span>
							<div>
							<span class="invalid" id="offsetTicketError"></span>
							</div>
							<br/>
							<input type="button" title="Submit" value="Submit" class="submitButton" onclick="changeStatus('Offset Resolve')"/>
						</form:form>
			   		</div>
			   </div>
	</div>
</div>

<script type="text/javascript">
	var moduleName = "paymentproderaoffset";
	var ids = "";
	var status = "${status}";
	if(status == 1){
		$("#status").val("Pending");
		}
	else if(status == 2){
		$("#status").val("AR Step Completed");
		}
	else if(status == 3){
		$("#status").val("Offset Resolve");
		}
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
		},{
			display : 'View',
			name : 'view',
			width : 40,
			sortable : false,
			align : 'center'
		}, {
			display : 'Ticket No.',
			name : 'batchId',
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			display : 'Patient Name',
			name : 'patientName',
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			display : 'Insurance Name',
			name : 'insuranceName',
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			display : 'Group/Doctor name',
			name : 'doctorName',
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			display : 'Status',
			name : 'status',
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			display : 'Offset Ticket No',
			name : 'offsetTicketNumber',
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			display : 'Account No.',
			name : 'accountNumber',
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			display : 'Total Amt',
			name : 'totalAmount',
			width : 80,
			sortable : false,
			align : 'left'
		},{
			display : 'Check No.',
			name : 'chkNumber',
			width : 80,
			sortable : true,
			align : 'left'
		},{
			display : 'Check Date',
			name : 'chkDate',
			width : 80,
			sortable : true,
			align : 'left'
		},{
			display : 'Posted Date',
			name : 'createdOn',
			width : 80,
			sortable : true,
			align : 'left'
		},{
			display : 'Posted By',
			name : 'createdBy',
			width : 80,
			sortable : true,
			align : 'left'
		},{
			display : 'Remark',
			name : 'remark',
			width : 200,
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
		}, {
			name : 'Offset Resolve',
			bclass : 'resolve',
			onpress : action
		} ],

		sortname : "id",
		sortorder : "desc",
		title : 'Offset List',
		singleSelect: false,
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
		var dt = $('#offsetListForm').serializeArray();
		$("#flex1").flexOptions({
			params : dt
		});
		return true;
	}

	$('#offsetListForm').submit(function() {
		if(null != $('#datePostedFrom').val() && null != $('#datePostedTo').val()){
			if(Date.parse($('#datePostedFrom').val()) > Date.parse($('#datePostedTo').val()) ){
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
        var view ="";
        var postOffset = "";

		$.each(data.rows,function(i, row) {

			edit = "&nbsp;&nbsp;<a href='"
				+ contextPath+"/"+ moduleName
				+ "/add?id="
				+ row.id
				+ "' class='edit'><img src='"+editIcon+"'/></a>";

				view = "&nbsp;&nbsp;<a href='javascript:void(0);' onclick='javascript:showPopup("+row.id+");' class='View'><img title='View' alt='View' src='"+viewIcon+"'/></a>";

				if(row.operation == 'ADD'){
					postOffset = "&nbsp;&nbsp;<a href='"
						+ contextPath
						+ "/paymentoffsetmanager"
						+ "/add?offset.id="
						+ row.id
						+ "&batchId="+row.ticketNumber+"' class='edit'><img src='"+editIcon+"' title='Edit'/></a>";
				}else{
					postOffset = "&nbsp;&nbsp;<a href='"
						+ contextPath
						+ "/paymentoffsetmanager"
						+ "/add?id="+row.offsetPostId+"&offset.id="
						+ row.id
						+ "&batchId="+row.ticketNumber+"' class='edit'><img src='"+editIcon+"'title='Edit'/></a>";
				}

				rows.push({
					id : row.id,
					cell : [edit, view, row.batchId, row.patientName,row.insuranceName, row.doctorName, row.status, row.offsetTicketNumber, row.accountNumber,row.totalAmount,row.chkNumber, row.chkDate,row.createdOn,row.postedBy,row.remark]
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

	function showPopup(offsetId) {
		var htmlString = "";
		$('#popup_showRecord').bPopup({
			onOpen : function() {

			},onClose: function(){
				$("#divContainer").html(ajaxLoadImg);
			}
       	},function() {
       		htmlString = "<div class='table-grid' style='overflow-x: auto;''>"+
            "<table border='1' width='100%'><tr>"+
 			"<thead><th class='heading'>CPT</th>"+
 			"<th class='heading'>DOS</th>"+
 			"<th class='heading'>Amount</th></thead>"+
 			"</tr>";

      		$.ajax({
				type : "GET",
				dataType : "json",
				async: false,
				url : moduleName+"/getRecord",
				data : "id=" + offsetId ,
				success : function(data) {
					for(var i=0;i<data.length;i++){
						htmlString += "<tr>";
						htmlString += "<td><span style='display: inline-block; width: 30em; overflow: auto;'>"+data[i].cpt+"</span></td>";
						htmlString += "<td>"+data[i].dos+"</td>";
						htmlString += "<td>"+data[i].amount+"</td>";
						htmlString += "</tr>";
					}
					htmlString += "</table></div>";
				},
				error: function(data){
					alert (data);
				}
			});

      		$("#divContainer").html(htmlString);
       	});
	}

	function action(com, grid) {
		if (com == 'Add') {
			window.location.href = contextPath+ "/" + moduleName	+ "/add";
		}
		else if (com == 'Print') {
			akpmsPopupWindow(contextPath + '/' + moduleName	+ "/printhtmlreport","ERA_Offset");
		}
		else if (com == 'Offset Resolve') {
			status = "Approve";
			//window.open(contextPath + '/' + moduleName	+ "/print","_blank");
			if(jQuery('#flex1 .trSelected').length>0){
			jQuery('#flex1 .trSelected').each( function(){
				//var id = parseInt($(this).attr('id'), 10);
                //alert($(this).attr('id').substring(3));
                ids = ids + $(this).attr('id').substring(3) +",";
            });

			$('#popup_ticket_number').bPopup({
	               onOpen: function() {
	            	   $('#offset_ticketNumber').val("");
	            	   $("#offsetTicketError").html("");
						
	            	   }
	           });
			}
			else{
				alert("Please select atleast one row");
			}
	            

		}
	}

function changeStatus(status){

		if($("#offset_ticketNumber").val().length == 0){
		$("#offsetTicketError").html("Please enter the Offset ticket number");
		return false;
		}
		
		$.ajax({
			type : "GET",
			dataType : "json",
			url : contextPath+'/'+ moduleName + "/changeStatus",
			data : "ids=" + ids +"&ticketNumber=" + $('#offset_ticketNumber').val() + "&status="+status,
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
</script>
<script type="text/javascript" src="<c:url value='/static/resources/js/common.js'/>"></script>
