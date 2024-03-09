<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home </a> &raquo;</li>
			<li>&nbsp;Rekey Request List</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<div class="msg">
		<p class="success" id="msgP">${success}</p>
	</div>
	<form class="form-container" id="sform">
		<div class="form-container-inner">
			<h3>Search Rekey Request</h3>

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
						<input type = "text" id="dateCreatedTo" name="dateCreatedTo" class="mid" style="width: 100px;" readonly="readonly" />
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="clr"></div>
			</div>
			
			<div class="row-container">
				<div class="element-block">
					<label  id="statusLabel">Status:</label>
					<span class="select-container1" style="display:inline;">
						<select  id ="status" name="status" class="selectbox">
							<option value="">-: Select Status :-</option>
							<option value="1">Query To CE</option>
							<!-- <option value="2">Coding to CE Returned</option> -->
							<option value="3">Return to AR</option>
							<!-- <option value="4">Query to Coding</option> -->
							<option value="5">Close</option>
						</select>
					</span>
				</div>
				<div class="element-block">
					<label for="teamId" class="right_lbl">Team:</label>
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
			<div class="clr"></div>
			
			<span class="input-container">
				<label>&nbsp;</label>
				<input type="submit" title="Search" value="Search" class="login-btn" />
				<input type="button" title="Cancel" value="Cancel" onclick="javascript:resetForm(this.form);" class="login-btn" />
			</span>

			<div class="noSearchItem">&nbsp;</div>

			<table id="flex1" style="display: none"></table>

		</div>
	</form>
	
	<div class="popup2" id="popup_showRecord" style="display: none;">
		<span class="button bClose"><span>X</span></span>
		<div class="popup-container" id="divContainer"><img	src="<c:url value="/static/resources/img/ajax-loader.gif"/>" alt="" /></div>
	</div>
	
</div>
<script type="text/javascript">

	var status = "<%= request.getParameter("status")%>";

	$("#status").val(status);
	var moduleName = "rekeyrequest";
	$("#flex1").flexigrid({
		url : contextPath + '/flows/'+moduleName+'/json',
		preProcess : formatData,
		method : 'GET',

		colModel : [ {
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
		},{
			display : 'Workflow',
			name : 'workFlowStatus',
			width : 80,
			sortable : true,
			align : 'left'
		},{
			display : 'Patient ID',
			name : 'arProductivity.patientAccNo',
			width : 100,
			sortable : true,
			align : 'left'
		}, {
			display : 'Patient Name',
			name : 'arProductivity.patientName',
			width : 100,
			sortable : true,
			align : 'left'
		}, {
			display : 'DOS',
			name : 'arProductivity.dos',
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			display : 'Doctor',
			name : 'arProductivity.doctor.name',
			width : 100,
			sortable : false,
			align : 'center'
		} ,{
			display : 'Insurance',
			name : 'insurance.name',
			width : 100,
			sortable : false,
			align : 'center'
		},{
			display : 'Team',
			name : 'arProductivity.team.name',
			width : 100,
			sortable : false,
			align : 'left'
		},{
			display : 'Batch No',
			name : 'batchNumber',
			width : 100,
			sortable : false,
			align : 'center'
		},{
			display : 'Request Reason',
			name : 'requestReason',
			width : 100,
			sortable : false,
			align : 'center'
		}, {
			display : 'Created By ',
			name : 'createdBy',
			width : 40,
			sortable : false,
			align : 'center'
		}, {
			display : 'Created On ',
			name : 'createdOn',
			width : 40,
			sortable : false,
			align : 'center'
		} ],buttons : [{
			name : 'Print',
			bclass : 'print',
			onpress : action
		}],
		searchitems : [ {
			display : 'Name',
			name : 'name',
			isdefault : true
		} ],
		sortname : "id",
		sortorder : "desc",
		title : 'Re-Key Request',
		showTableToggleBtn : true,
		onSubmit : addFormData,
		width : 100,
		height : 300
	});

	//This function adds paramaters to the post of flexigrid. You can add a verification as well by return to false if you don't want flexigrid to submit
	function addFormData() {
		//passing a form object to serializeArray will get the valid data from all the objects, but, if the you pass a non-form object, you have to specify the input elements that the data will come from
		var dt = $('#sform').serializeArray();
		$("#flex1").flexOptions({
			params : dt
		});
		return true;
	}

	$('#sform').submit(function() {
		
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
		var wfStatus = "";
		var view ="";
		$.each(data.rows,function(i, row) {
				var url = contextPath+"/flows/"
				+ moduleName
				+ "/add/?arProductivity.id="
				+ row.arProdId;
				
// 				edit = "&nbsp;&nbsp;<a href='"+contextPath+"/flows/"
// 						+ moduleName
// 						+ "/add/?arProductivity.id="
// 						+ row.arProdId
// 						+ "' class='edit'><img src='"+editIcon+"'/></a>";
				view = "&nbsp;&nbsp;<a href='javascript:void(0);' onclick='javascript:showPopup("+row.id+");' class='View'><img title='View' alt='View' src='"+viewIcon+"'/></a>";

				edit = "&nbsp;<a href='javascript:void(0);' onclick='javascript:akpmsPopupWindow(\""
						+ url
						+ "&Popup=1&operationType=0\")' class='edit'><img src='"+editIcon+"' title='Edit' alt='Edit'/></a>";

				if(row.workflowId == 1){
					wfStatus = "Query To CE";
				}else if(row.workflowId == 2){
					wfStatus = "Coding to CE Returned";
				}else if(row.workflowId == 3){
					wfStatus = "Return to AR";
				}
				else if(row.workflowId == 4){
					wfStatus = "Query to Coding";
				}
				else if(row.workflowId == 5){
					wfStatus = "Close";
				}
				rows.push({
					id : row.id,
					cell : [ edit,view,wfStatus,row.patientAccNo, row.patientName, row.dos, row.doctor,
							 row.insurance, row.team, row.batchNo, row.requestReason, row.createdBy, row.createdOn]
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
			window.location.href = contextPath + "/flows/" + moduleName	+ "/add";
		}
		else if (com == 'Print') {
			akpmsPopupWindow(contextPath + '/flows/' + moduleName	+ "/printhtmlreport","rekey_request");
		}
	}

	$(function() {
	    $( "#dateCreatedFrom" ).datepicker();
	});

	$(function() {
	    $( "#dateCreatedTo" ).datepicker();
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
 			"<th class='heading'>Remark</th>"+
 			"</tr>";

      		$.ajax({
				type : "GET",
				dataType : "json",
				async: false,
				url : contextPath + '/flows/'+moduleName+'/getRecord',
				data : "id=" + offsetId ,
				success : function(data) {
					for(var i=0;i<data.length;i++){
						htmlString += "<tr>";
						htmlString += "<td><span style='display: inline-block; width: 30em; overflow: auto;'>"+data[i].cpt+"</span></td>";
						htmlString += "<td><span style='display: inline-block; width: 30em; overflow: auto;'>"+data[i].remark+"</span></td>";
						htmlString += "</tr>";
					}
					htmlString += "</table></div>";
				},
				error: function(data){
					//alert (data);
				}
			});

      		$("#divContainer").html(htmlString);
       	});
	}
</script>
