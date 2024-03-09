<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home </a> &raquo;</li>
			<li>&nbsp;User list</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<div class="msg">
		<p class="success" id="msgP">${success}</p>
	</div>
	<form class="form-container" id ="sform">
		<div class="form-container-inner">
			<h3>Search Users</h3>
           <input type="hidden" name="selectedRolesIds" id="selectedRolesIds" value="" />
           <input type="hidden" name="selectedDepartmentsIds" id="selectedDepartmentsIds" value="" />
			<span class="input-container"> <label>Keyword:</label>
				<span class="input_text">
					<span class="left_curve"></span>
					<input type="text" id="keyword" name = "keyword" maxlength="50"  class="mid" style="width: 373px" />
					<span class="right_curve"></span>
				</span>
			</span> <br />
			<div class="element-block">
				<label>Status:</label>
				<div class="select-container2">
					<select id="status" name="status" >
						<option value=""> All</option>
						<option value="1"> Active</option>
						<option value="0"> Inactive</option>
					</select>
				</div>
			</div></br>
			<h3>Role</h3>
			<span class="input-container"> <c:forEach var="role" items="${roles}">
					<input type="checkbox" id="role${role.id}" value="${role.id}" />&nbsp;${role.name} &nbsp;&nbsp;
				</c:forEach>
			</span> <br />
			<h3>Department</h3>

			<div class="input-container">
				<c:forEach var="dept" items="${departments}">
					<c:forEach var="child" items="${dept.value}">
						<c:choose>
							<c:when test="${dept.key eq child.name}">
								<div class="dept-heading">
									<input type="checkbox" id="department${child.id}" value="${child.id}" /> ${child.name}
								</div>
							</c:when>
							<c:otherwise>
								&nbsp; &nbsp; <input type="checkbox" id="department${child.id}" value="${child.id}" /> ${child.name}
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</c:forEach>
			</div>

			<span class="input-container"> <label>&nbsp;</label> 
				<input type="submit" title="Search" value="Search" class="login-btn" />
				<input type="button" title="Cancel" value="Cancel" onclick="javascript:resetForm(this.form);"
				class="login-btn" />
			</span>

			<div class="noSearchItem">&nbsp;</div>

			<table id="flex1" style="display: none"></table>

		</div>
		<input type="hidden" name="status" value="${status}" />
	</form>
</div>
<script type="text/javascript">
	var moduleName = "user";
	$("#flex1").flexigrid({
		url : contextPath + '/admin/'+moduleName+'/json',
		preProcess : formatData,
		method : 'GET',

		colModel : [{
			display : 'Delete',
			name : 'del',
			width : 40,
			sortable : false,
			align : 'center'
		}, {
			display : 'Edit',
			name : 'edit',
			width : 40,
			sortable : false,
			align : 'center'
		}, {
			display : 'Status',
			name : 'status',
			width : 65,
			sortable : true,
			align : 'left'
		} ,{
			display : 'Name',
			name : 'firstName',
			width : 160,
			sortable : true,
			align : 'left'
		}, {
			display : 'E-mail',
			name : 'email',
			width : 160,
			sortable : true,
			align : 'left'
		}, {
			display : 'Role',
			name : 'roleName',
			width : 112,
			sortable : true,
			align : 'left'
		}, {
			display : 'Departments',
			name : 'departmentNames',
			width : 210,
			sortable : false,
			align : 'left'
		}],
		buttons : [ {
			name : 'Add',
			bclass : 'add',
			onpress : action
		} ],
		searchitems : [ {
			display : 'Name',
			name : 'name',
			isdefault : true
		} ],
		sortname : "firstName",
		sortorder : "asc",
		title : 'Users',
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
		var inputTags = document.getElementsByTagName("input");

		var selectedRoles = "";
		var selectedDepartments = "";
		var deptCount = 0;
		var roleCount = 0;
		for ( var count = 0; count < inputTags.length; count++) {
			if (inputTags[count].checked) {
				name = inputTags[count].id;
				namePart = name.substring(0, 4);

				if (namePart == 'role') {
					//alert("it is a role ");
					if (roleCount == 0)
						selectedRoles = selectedRoles + inputTags[count].value;
					else
						selectedRoles = selectedRoles + ","
								+ inputTags[count].value;
					roleCount++;
				} else if (namePart == 'depa') {
					//alert("It is a department");
					if (deptCount == 0)
						selectedDepartments = selectedDepartments
								+ inputTags[count].value;
					else
						selectedDepartments = selectedDepartments + ","
								+ inputTags[count].value;
					deptCount++;
				}
			}
		}
		$('#selectedRolesIds').val(selectedRoles);
		$('#selectedDepartmentsIds').val(selectedDepartments);
		
		
		$('#flex1').flexOptions({
			newp : 1,
		}).flexReload();
		return false;
	});

	function formatData(data) {
		var rows = Array();
		var status = "";
		var resetPassword = "";

		$.each(data.rows,function(i, row) {
				if (row.status) {
					status = "<a href='javascript:void(0)' onClick='send_changeStatus("	+ row.id + "," + row.status + ")' class='inactivate'><img src='"+inactiveIcon+"' alt='Make Inactivate' title='Deactivate' /></a> Active";
				}else{
					status = "<a href='javascript:void(0)' onClick='send_changeStatus("	+ row.id + "," + row.status + ")'' class='activate'><img src='"+activeIcon+"' alt='Make Activate' title='Activate' /></a> Inactive";
				}

				resetPassword = "<a href='javascript:void(0)' onClick='resetPassword("	+ row.id + ")'' class='reset'><img src='"+resetIcon+"' alt='Reset Password' title='Reset Password' /></a>";
				
				edit = "&nbsp;&nbsp;<a href='"+contextPath+"/admin/"
						+ moduleName
						+ "/add/?id="
						+ row.id
						+ "' class='edit'><img src='"+editIcon+"' title='Edit'/></a> "+ resetPassword;
				del = "<a href='javascript:void(0)' onClick='send_delete(" + row.id + "," + row.childCount +")' class='delete'><img src='"+deleteIcon+"' alt='Delete' title='Delete' /></a>";

				rows.push({
					id : row.id,
					cell : [ del, edit, status, 
							row.firstName+' '+row.lastName, row.email,
							row.roleName,
							row.departmentNames
							 ]
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

	function send_delete(id, childCount){
		if (confirm("Are you sure you wish to delete the selected user?")) {
			$.ajax({
				type : "POST",
				dataType : "json",
				url : moduleName+"/delete",
				data : "item=" + id,
				success : function(data) {
					$("#flex1").flexReload();
					$("#msgP").text("User has been successfully deleted.");
				}
			});
		}
	}

	function action(com, grid) {
		if (com == 'Add') {
			window.location.href = contextPath + "/admin/" + moduleName	+ "/add";
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

		if(confirm("Are you sure you wish to "+st+" this user?")){
			$.ajax({
				type : "GET",
				dataType : "json",
				url : moduleName + "/changeStatus",
				data : "id=" + id + "&status=" + status,
				success : function(data) {
					//alert("Query: " + data.query + " - Total affected rows: " + data.total);
					$("#flex1").flexReload();
					$("#msgP").text("User has been successfully "+st+"d.");
				},
				error : function(data) {
					alert(data);
				}
			});
		}
	}


	function resetPassword(id) {
		var st = "";

		if(confirm("Are you sure you wish to reset password for this user?")){
			$.ajax({
				type : "GET",
				dataType : "json",
				url : moduleName + "/resetPassword",
				data : "id=" + id ,
				success : function(data) {
					//alert("Query: " + data.query + " - Total affected rows: " + data.total);
					$("#flex1").flexReload();
					$("#msgP").text("User's password has been reset successfully.");
				},
				error : function(data) {
					alert(data);
				}
			});
		}
	}

	
	$('#status').val('${status}');
</script>
