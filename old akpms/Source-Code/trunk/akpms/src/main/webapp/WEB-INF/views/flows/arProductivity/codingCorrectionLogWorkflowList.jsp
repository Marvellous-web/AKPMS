<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="dashboard-container" >
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home </a> &raquo; </li>
			<li>&nbsp;Coding Correction Log List</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<div class="msg">
		<p class="success" id="msgP">${success}</p>
	</div>

	<form class="form-container" id="codingCorrectionLogSearchForm">
		<div class="form-container-inner">
			<h3>Search Coding Correction Log</h3>

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
				<div class="element-block" >
					<label>Work flows:</label>
					<span class="select-container1" style="display:inline;">
						<select id="workflow" name="workflow" class="selectbox">
							<option value="">-: Select Work flow :-</option>
							<option value="Forward to coding correction">Forward to coding correction</option>
							<option value="Escalate to Argus TL">Escalate to Argus TL</option>
							<option value="Return to AR">Return to AR</option>
							<option value="Coding to CE">Coding to CE</option>
							<option value="Done">Done</option>
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
</div>

<c:if test="${not empty workflow }">
	<script type="text/javascript">
		$("#workflow").val('${workflow}');
	</script>	
</c:if>

<script type="text/javascript">
	var moduleName = "flows/codingcorrectionlogworkflow";
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
		},{
			display : 'Status',
			name : 'codingRemark',
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
		},{
			display : 'Batch Number',
			name : 'batchNo',
			width : 150,
			sortable : true,
			align : 'left'
		} , {
			display : 'Sequence Number',
			name : 'sequenceNo',
			width : 100,
			sortable : true,
			align : 'center'
		} , {
			display : 'Provider',
			name : 'arProductivity.doctor.name',
			width : 150,
			sortable : true,
			align : 'left'
		},  {
			display : 'DOS',
			name : 'arProductivity.dos',
			width : 80,
			sortable : true,
			align : 'center'
		},  {
			display : 'Team',
			name : 'arProductivity.team.name',
			width : 80,
			sortable : false,
			align : 'left'
		},{
			display : 'Attachment',
			name : 'attachment',
			width : 80,
			sortable : false,
			align : 'left'
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
		sortname : "id",
		sortorder : "desc",
		title : 'Coding correction Log',
		singleSelect: true,
		showTableToggleBtn : true,
		onSubmit : addFormData,
		width : 100,
		height : 300
	});

	//This function adds paramaters to the post of flexigrid. You can add a verification as well by return to false if you don't want flexigrid to submit
	function addFormData() {
		//passing a form object to serializeArray will get the valid data from all the objects, but, if the you pass a non-form object, you have to specify the input elements that the data will come from
		var dt = $('#codingCorrectionLogSearchForm').serializeArray();
		$("#flex1").flexOptions({
			params : dt
		});
		return true;
	}

	$('#codingCorrectionLogSearchForm').submit(function() {

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
		var attachment = "";
		var edit = "";
		$.each(data.rows, function(i, row) {
			var url = contextPath+"/flows/codingcorrectionlogworkflow/add/?arProductivity.id="+row.arProdId;
			attachment = "";
			//row.attachmentName
			if(row.attachmentId != null){
			attachment = "<div class='download-container'><a class='download' href='"+contextPath+"/flows/codingcorrectionlogworkflow/fileDownload?id="+row.attachmentId+"' title='Download File'>Download</a></div>";
			}
// 			edit = "<a href='"+contextPath+"/flows/codingcorrectionlogworkflow/add/?arProductivity.id="+row.arProdId+"' class='edit'><img src='"+editIcon+"'/></a>";
			edit = "&nbsp;<a href='javascript:void(0);' onclick='javascript:akpmsPopupWindow(\""
									+ url
									+ "&Popup=1&operationType=0\")' class='edit'><img src='"+editIcon+"' title='Edit' alt='Edit'/></a>";
			rows.push({
				id : row.id,
				cell : [edit,row.status, row.patientName, row.patientAccNo, row.batchNo, row.seqNo, row.provider,row.dos, row.team, attachment, row.createdBy, row.createdOn]
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
		if (com == 'Print') {
			akpmsPopupWindow(contextPath + '/' + moduleName	+ "/printhtmlreport","coding_correction");
		}
	}
	$(function() {
	    $( "#dateCreatedFrom" ).datepicker();
	});

	$(function() {
	    $( "#dateCreatedTo" ).datepicker();
	});
</script>
