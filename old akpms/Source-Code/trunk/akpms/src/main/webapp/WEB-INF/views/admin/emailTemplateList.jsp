<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="dashboard-container" >
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home </a>&raquo;</li>
			<li>&nbsp;Email Templates</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<div class="msg">
		<p class="success" id="msgP">${success}</p>
	</div>

	<form class="form-container" id="emailTemplateList">
		<div class="form-container-inner">
		<h3>Search Email Templates</h3>

			<span class="input-container">
				<label>Keyword:</label>
				<span class="input_text">
					<span class="left_curve"></span>
					<input type="text" id="keyword" name="keyword" maxlength="50"  class="mid" style="width: 373px" />
					<span class="right_curve"></span>
				</span>
			</span>
			<br />
			<span class="input-container">
				<label>&nbsp;</label>
				<input type="submit" title="Search" value="Search" 	class="login-btn" />
				 <input type="button" title="Cancel" value="Cancel" onclick="javascript:resetForm(this.form);" class="login-btn" />
			</span>

			<div class="noSearchItem">&nbsp;</div>

			<table id="flex1" style="display: none"></table>
		</div>
	</form>
</div>
<script type="text/javascript">
	var moduleName = "emailtemplate";
	$("#flex1").flexigrid({
		url : contextPath + '/admin/'+moduleName+'/json',
		preProcess : formatData,
		method : 'GET',

		colModel : [{
			display : 'Edit',
			name : 'edit',
			width : 40,
			sortable : false,
			align : 'center'
		},{
			display : 'Status',
			name : 'status',
			width : 60,
			sortable : true,
			align : 'left'
		},{
			display : 'Name',
			name : 'name',
			width : 340,
			sortable : true,
			align : 'left'
		},{
			display : 'Type',
			name : 'subscriptionEmail',
			width : 110,
			sortable : true,
			align : 'left'
		} ],

		searchitems : [ {
			display : 'Name',
			name : 'name',
			isdefault : true
		} ],
		sortname : "id",
		sortorder : "asc",
		title : 'Email Templates',
		showTableToggleBtn : true,
		onSubmit : addFormData,
		width : 100,
		height : 300
	});

	//This function adds paramaters to the post of flexigrid. You can add a verification as well by return to false if you don't want flexigrid to submit
	function addFormData() {
		//passing a form object to serializeArray will get the valid data from all the objects, but, if the you pass a non-form object, you have to specify the input elements that the data will come from
		var dt = $('#emailTemplateList').serializeArray();
		$("#flex1").flexOptions({
			params : dt
		});
		return true;
	}

	$('#emailTemplateList').submit(function() {
		$('#flex1').flexOptions({
			newp : 1
		}).flexReload();
		return false;
	});

	function formatData(data) {
		var rows = Array();
		var status = "";

		$.each(data.rows, function(i, row) {
			if (row.status) {
				status = "<a href='javascript:void(0)' onClick='send_changeStatus(" + row.id + "," + row.status + ")' class='inactivate'><img src='"+inactiveIcon+"' alt='Make Deactivate' title='Deactivate' /></a> Active";
			}else{
				status = "<a href='javascript:void(0)' onClick='send_changeStatus("	+ row.id + "," + row.status + ")'' class='activate'><img src='"+activeIcon+"' alt='Make Activate' title='Activate' /></a> Deactivate";
			}
			edit = "&nbsp;&nbsp;<a href='"+contextPath+"/admin/"+moduleName+"/add/?id="+row.id+"' class='edit'><img src='"+editIcon+"'/></a>";
			
			rows.push({
				id : row.id,
				cell : [edit, status, row.name, row.subscriptionEmail ]
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
			window.location.href = contextPath + "/admin/"+moduleName+"/add";
		}
	}

	function send_changeStatus(id, status) {
		var st = "";

		if (status == 0){
			status = 1;
			st = "activate";
		}else{
			status = 0;
			st = "deactivate";
		}

		if(confirm("Are you sure you wish to "+st+" this email template?")){
			$.ajax({
				type : "GET",
				dataType : "json",
				url : moduleName+"/changeStatus",
				data : "id=" + id + "&status=" + status,
				success : function(data) {
					//alert("Query: " + data.query + " - Total affected rows: " + data.total);
					$("#flex1").flexReload();
					$("#msgP").text("Email template has been successfully "+st+"d.");
				},
				error: function(data){
					alert (data);
				}
			});
		}
	}
</script>