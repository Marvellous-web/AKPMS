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

			<span class="input-container">
				<label>Patient Name:</label>
				<span class="input_text">
					<span class="left_curve"></span>
					<input type="text" id="patientName" name="arProductivity.patientName" maxlength="50"  class="mid" style="width: 373px" />
					<span class="right_curve"></span>
				</span>
			</span>
			<span class="input-container">
				<label>Patient Account No:</label>
				<span class="input_text">
					<span class="left_curve"></span>
					<input type="text" id="patientAccountNo" name="arProductivity.patientAccNo"  maxlength="50"  class="mid" style="width: 373px" />
					<span class="right_curve"></span>
				</span>
			</span>
			<div class="row-container">
				<div class="element-block" >
					<label>DOS From:</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input type = "text"  id="dosFrom" name="dosFrom" maxlength="6" class="mid" style="width: 100px;" readonly/>
						<span class="right_curve"></span>
					</div>
				</div>

				<div class="element-block" >
					<label class="right_lbl">DOS To:</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input type = "text" id="dosTo" name="dosTo" maxlength="6" class="mid" style="width: 100px;" readonly />
						<span class="right_curve"></span>
					</div>
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
<script type="text/javascript">
	var moduleName = "flows/codingcorrectionlogworkflow";
	$("#flex1").flexigrid({
		url : contextPath+'/'+moduleName+'/json',
		preProcess : formatData,
		method : 'GET',

		colModel : [ {
			display : 'Patient Name',
			name : 'arProductivity.patientName',
			width : 150,
			sortable : true,
			align : 'left'
		}, {
			display : 'Patient Account No.',
			name : 'patientAccountNo',
			width : 150,
			sortable : false,
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
			width : 150,
			sortable : false,
			align : 'center'
		} , {
			display : 'Provider',
			name : 'provider',
			width : 150,
			sortable : false,
			align : 'center'
		},  {
			display : 'DOS',
			name : 'dos',
			width : 150,
			sortable : false,
			align : 'center'
		},{
			display : 'Attachment',
			name : 'attachment',
			width : 150,
			sortable : false,
			align : 'center'
		}, {
			display : 'Edit',
			name : 'edit',
			width : 40,
			sortable : false,
			align : 'center'
		} ],
		sortname : "id",
		sortorder : "asc",
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

			attachment = "<div class='download-container'>"+row.attachmentName+"<a class='download' href='"+contextPath+"/flows/codingcorrectionlogworkflow/fileDownload?id="+row.attachmentId+"' title='Download File'></a></div>";
			edit = "<a href='"+contextPath+"/flows/codingcorrectionlogworkflow/add/?arProductivity.id="+row.arProdId+"' class='edit'><img src='"+editIcon+"'/></a>";
			rows.push({
				id : row.id,
				cell : [ row.patientName, row.patientAccNo, row.batchNo, row.seqNo, row.provider,row.dos, attachment, edit]
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
	    $( "#dosFrom" ).datepicker();
	});

	$(function() {
	    $( "#dosTo" ).datepicker();
	});
</script>