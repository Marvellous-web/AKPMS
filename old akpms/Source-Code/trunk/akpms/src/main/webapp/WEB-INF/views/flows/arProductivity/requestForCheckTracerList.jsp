<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="dashboard-container" >
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home </a> &raquo; </li>
			<li>&nbsp;Check Tracer List</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<div class="msg">
		<p class="success" id="msgP">${success}</p>
	</div>

	<form class="form-container" id="requestCheckTracerSearchForm">
		<div class="form-container-inner">
			<h3>Search Check tracer</h3>

			<span class="input-container">
				<label>Patient Name:</label>
				<span class="input_text">
					<span class="left_curve"></span>
					<input type="text" id="patientName" name="arProductivity.patientName" maxlength="50"  class="mid" style="width: 373px" />
					<span class="right_curve"></span>
				</span>
			</span>
			<span class="input-container">
				<label>Patient ID:</label>
				<span class="input_text">
					<span class="left_curve"></span>
					<input type="text" id="patientAccountNo" name="arProductivity.patientAccNo"  maxlength="50"  class="mid" style="width: 373px" />
					<span class="right_curve"></span>
				</span>
			</span>
			<span class="input-container">
				<label>Check No:</label>
				<span class="input_text">
					<span class="left_curve"></span>
					<input type="text" id="checkNumber" name="checkNo" maxlength="50"  class="mid" style="width: 373px" />
					<span class="right_curve"></span>
				</span>
			</span>
			<br />
			<span class="input-container">
				<label>&nbsp;</label>
				<input type="submit" title="Search" value="Search" class="login-btn" />
				<input type="button" title="Cancel" value="Cancel" onclick="javascript:resetForm(this.form);" class="login-btn" />
			</span>

			<div class="noSearchItem">&nbsp;</div>

			<table id="flex1" style="display: none"></table>
		</div>
	</form>
</div>
<script type="text/javascript">
	var moduleName = "flows/checkTracer";
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
			display : 'Check No.',
			name : 'checkNo',
			width : 150,
			sortable : true,
			align : 'left'
		} , {
			display : 'Issue Date',
			name : 'checkIssueDate',
			width : 150,
			sortable : true,
			align : 'center'
		} , {
			display : 'Cashed Date',
			name : 'checkCashedDate',
			width : 150,
			sortable : true,
			align : 'center'
		}, {
			display : 'Attachment',
			name : 'attachment',
			width : 150,
			sortable : false,
			align : 'center'
		} ],buttons : [{
			name : 'Print',
			bclass : 'print',
			onpress : action
		}],
		sortname : "id",
		sortorder : "asc",
		title : 'Request for check tracer',
		singleSelect: true,
		showTableToggleBtn : true,
		onSubmit : addFormData,
		width : 100,
		height : 300
	});

	//This function adds paramaters to the post of flexigrid. You can add a verification as well by return to false if you don't want flexigrid to submit
	function addFormData() {
		//passing a form object to serializeArray will get the valid data from all the objects, but, if the you pass a non-form object, you have to specify the input elements that the data will come from
		var dt = $('#requestCheckTracerSearchForm').serializeArray();
		$("#flex1").flexOptions({
			params : dt
		});
		return true;
	}

	$('#requestCheckTracerSearchForm').submit(function() {
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

			attachment = "<div class='download-container'><a class='download' href='"+contextPath+"/flows/checkTracer/fileDownload?id="+row.attachmentId+"' title='"+row.attachmentName+"'>Download</a></div>";
			edit = "<a href='"+contextPath+"/flows/checkTracer/add/?arProductivity.id="+row.arProdId+"' class='edit'><img src='"+editIcon+"'/></a>";
			rows.push({
				id : row.id,
				cell : [ edit, row.patientName, row.patientAccNo, row.checkNo, row.checkIssueDate, row.cashedDate, attachment]
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
			window.open(contextPath + '/' + moduleName	+ "/print","_blank");
		}
	}
</script>
