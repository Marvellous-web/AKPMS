<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home </a> &raquo;</li>
			<li>&nbsp;AR Productivity List</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<div class="msg">
		<p class="success" id="msgP">${success}</p>
	</div>
	<form class="form-container" id="sform">
		<div class="form-container-inner">
			<h3>Search AR Productivity</h3>

			<span class="input-container"> <label>Keyword:</label>
				<span class="input_text">
					<span class="left_curve"></span>
					<input type="text" id="keyword" name="keyword" maxlength="50"  class="mid" style="width: 373px" />
					<span class="right_curve"></span>
				</span>
			</span> <br />

			<span class="input-container"> <label>&nbsp;</label> 
				<input type="submit" title="Search" value="Search" class="login-btn" />
				<input type="reset" title="Cancel" value="Cancel" class="login-btn" />
			</span>

			<div class="noSearchItem">&nbsp;</div>

			<table id="flex1" style="display: none"></table>
		</div>
	</form>
</div>
<script type="text/javascript">
	var moduleName = "adjustmentlogs";
	$("#flex1").flexigrid({
		url : contextPath + '/flows/'+moduleName+'/json',
		preProcess : formatData,
		method : 'GET',

		colModel : [ {
			display : 'Patient Acc. No.',
			name : 'patient_acc_no',
			width : 160,
			sortable : true,
			align : 'left'
		}, {
			display : 'Patient Name',
			name : 'patientName',
			width : 160,
			sortable : true,
			align : 'left'
		}, {
			display : 'DOS',
			name : 'dos',
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			display : 'CPT',
			name : 'cpt',
			width : 80,
			sortable : true,
			align : 'left'
		} , {
			display : 'Doctor',
			name : 'doctor.name',
			width : 40,
			sortable : false,
			align : 'center'
		} ,{
			display : 'Insurance',
			name : 'insurance.name',
			width : 40,
			sortable : false,
			align : 'center'
		} ,{
			display : 'Database',
			name : 'dataBas',
			width : 40,
			sortable : false,
			align : 'center'
		} , {
			display : 'Balance',
			name : 'balanceAmt',
			width : 40,
			sortable : false,
			align : 'center'
		} ,{
			display : 'Source',
			name : 'source',
			width : 40,
			sortable : false,
			align : 'center'
		} ,{
			display : 'Work Flow',
			name : 'status',
			width : 40,
			sortable : false,
			align : 'center'
		},{
			display : 'Edit',
			name : 'edit',
			width : 40,
			sortable : false,
			align : 'center'
		}],
		searchitems : [ {
			display : 'Name',
			name : 'name',
			isdefault : true
		} ],
		sortname : "id",
		sortorder : "asc",
		title : 'AR Productivity Sheet',
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
		$('#flex1').flexOptions({
			newp : 1
		}).flexReload();
		return false;
	});

	function formatData(data) {
		var rows = Array();
		$.each(data.rows,function(i, row) {

				edit = "&nbsp;&nbsp;<a href='"+contextPath+"/flows/"
						+ moduleName
						+ "/add/?arProductivity.id="
						+ row.id
						+ "' class='edit'><img src='"+editIcon+"'/></a>";

				rows.push({
					id : row.id,
					cell : [ row.patientAccNo, row.patientName, row.dos, row.cpt, row.doctor,
							 row.insurance, row.dataBas, row.balanceAmt,
							 row.sourceName, row.workFlowName,
							 edit]
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
	}
</script>