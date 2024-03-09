<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="dashboard-container" >
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home </a> &raquo; </li>
			<li>&nbsp;Department List</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<div class="msg">
		<p class="success" id="msgP">${success}</p>
	</div>

	<form class="form-container" id="departmentList">
		<div class="form-container-inner">
			<h3>Search Departments</h3>

			<span class="input-container">
				<label>Keyword:</label>
				<span class="input_text">
					<span class="left_curve"></span>
					<input type="text" id="keyword"  name="keyword" maxlength="50"  class="mid" style="width: 373px" />
					<span class="right_curve"></span>
				</span>
			</span>
			<br />

			<div class="element-block">
				<label>Status:</label>
				<div class="select-container2">
					<select id="status" name="status" >
						<option value="">All</option>
						<option value="1">Active</option>
						<option value="0">Inactive</option>
					</select>
				</div>
			</div>
			<br />
			<span class="input-container">
				<label>&nbsp;</label>
				<input type="submit" title="Search" value="Search" class="login-btn" />
				 <input type="button" title="Cancel" value="Cancel" onclick="javascript:resetForm(this.form);"
				class="login-btn" />
			</span>

			<div class="noSearchItem">&nbsp;</div>

			<table id="flex1" style="display: none"></table>
		</div>
	</form>
</div>
<script type="text/javascript">
	var moduleName = "department";
		$("#flex1").flexigrid({
			url : contextPath + '/admin/'+moduleName+'/json',
			preProcess : formatData,
			method : 'GET',

			colModel : [ {
				display : 'Name',
				name : 'name',
				width : 280,
				sortable : true,
				align : 'left'
			}, {
				display : 'Parent Department',
				name : 'parentName',
				width : 280,
				sortable : false,
				align : 'left'
			},{
				display : 'Status',
				name : 'status',
				width : 80,
				sortable : true,
				align : 'left'
			} , {
				display : 'Edit',
				name : 'edit',
				width : 40,
				sortable : false,
				align : 'center'
			} , {
				display : 'Delete',
				name : 'del',
				width : 40,
				sortable : false,
				align : 'center'
			} ],
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
			sortname : "id",
			sortorder : "asc",
			title : 'Departments',
			singleSelect: true,
			showTableToggleBtn : true,
			onSubmit : addFormData,
			width : 100,
			height : 300
		});

		//This function adds paramaters to the post of flexigrid. You can add a verification as well by return to false if you don't want flexigrid to submit
		function addFormData() {
			//passing a form object to serializeArray will get the valid data from all the objects, but, if the you pass a non-form object, you have to specify the input elements that the data will come from
			var dt = $('#departmentList').serializeArray();
			$("#flex1").flexOptions({
				params : dt
			});
			return true;
		}

		$('#departmentList').submit(function() {
			$('#flex1').flexOptions({
				newp : 1
			}).flexReload();
			return false;
		});

		function formatData(data) {
			var rows = Array();
			var status = "";
			var edit = "";

			$.each(data.rows, function(i, row) {

				if (row.status) {
					status = "<a href='javascript:void(0)' onClick='send_changeStatus(" + row.id + "," + row.status + "," + row.childCount + "," + row.parentStatus + ")' class='inactivate'><img src='"+inactiveIcon+"' alt='Make Inactive' title='Deactivate' /></a> Active";
				}else{
					status = "<a href='javascript:void(0)' onClick='send_changeStatus("	+ row.id + "," + row.status + "," + row.childCount + "," + row.parentStatus + ")' class='activate'><img src='"+activeIcon+"' alt='Make Activate' title='Activate' /></a> Inactive";
				}

				if(row.status == false && row.parentName != "--" && row.parentStatus == "false"){
					msg = 'Please activate parent department before editing.';
					edit = "<a href='javascript:void(0)' onclick='javascript:alert(\"Please activate parent department before editing.\");'><img src='"+editIcon+"' /></a>";
				}else{
					edit = "<a href='"+contextPath+"/admin/"+moduleName+"/add/?id="+row.id+"' class='edit'><img src='"+editIcon+"'/></a>";
				}

				del = "<a href='javascript:void(0)' onClick='send_delete(" + row.id + "," + row.childCount +")' class='delete'><img src='"+deleteIcon+"' alt='Delete' title='Delete' /></a>";

				rows.push({
					id : row.id,
					cell : [ row.name, row.parentName, status, edit, del ]
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

		function checkStatus(status, parentStatus){
			//alert("STATUS::" +status +"p St:"+ parentStatus);
			if(status == 0 && parentStatus == 0){
				alert('Please activate parent department before editing.');
				return false;
			}
		}

		function action(com, grid) {
			if (com == 'Add') {
				window.location.href = contextPath + "/admin/"+moduleName+"/add";
			}
		}

		function send_delete(id, childCount){
			var confirmation = false;

			if(childCount > 0 ){
				// if parent has children
				confirmation = confirm("Deleting this department will also delete its "+childCount+" sub-departments. Are you sure you wish to proceed?");
			}else{
				confirmation = confirm("Are you sure you wish to delete this department?");
			}

			if (confirmation) {
				$.ajax({
					type : "POST",
					dataType : "json",
					url : moduleName+"/delete",
					data : "item=" + id,
					success : function(data) {
						$("#flex1").flexReload();
						$("#msgP").text("Department(s) have been successfully deleted.");
					}
				});
			}
		}

		function send_changeStatus(id, status, childCount, parentStatus) {
			//alert("parentStatus: "+ parentStatus +" childCount:: "+childCount);

			var confirmation = false;
			var st = "";

			if (status == 0){
				status = 1;
				st = "activate";
			}else{
				status = 0;
				st = "deactivate";
			}

			if(childCount > 0 && status == 0){
				// if parent has childs
				confirmation = confirm("Deactivating this department will also deactivate its "+childCount+ " sub-departments. Are you sure you wish to proceed?");
			}else if(parentStatus == false && status == 1){
				// activate sub-department which have inactivated parent department
				//alert ("Please activate the parent department '"+parentName+"' before activating this department.");
				alert ("Please activate its parent department before activating this department.");
				return true;
			}else{
				confirmation = confirm("Are you sure you wish to "+st+" this department?");
			}

			if(confirmation){
				$.ajax({
					type : "GET",
					dataType : "json",
					url : moduleName+"/changeStatus",
					data : "id=" + id + "&status=" + status,
					success : function(data) {
						//alert("Query: " + data.query + " - Total affected rows: " + data.total);
						$("#flex1").flexReload();
							$("#msgP").text("Department(s) have been successfully "+st+"d.");
					},
					error: function(data){
						alert (data);
					}
				});
			}
		}

		$('#status').val('${status}');
</script>
