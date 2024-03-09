<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="dashboard-container" >
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home </a> &raquo; </li>
			<li>&nbsp;QA WorkSheet List</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<div class="msg">
		<p class="success" id="msgP">${success}</p>
		<p class="error" id="msgP">${error}</p>
	</div>

	<form class="form-container" id="qaworksheetList">
		<div class="clr"></div>
		<div class="form-container-inner">
			<h3>Search QA Worksheet</h3>
			<div class="row-container">
				<div class="element-block" >
					<label>Department:</label>
					<div class="select-container1">
						<select id="departmentId" name="departmentId">
							<option value="">All-</option>
							<c:forEach var="department" items="${parentDepartments}" >
								<option value="${department.id}">${department.name}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="element-block" >
					<label class="right_lbl">Sub Department:</label>
					<div class="select-container1">
						<select id="subDepartmentId" name="subDepartmentId">
							<option value="-1">All-</option>
						</select>
					</div>
				</div>
				<div class="clr"></div>
		 	</div>
		 	<div class="row-container">
				<label>Created By: </label>
				<div class="box-left">
					<ul>
						<c:forEach var="qaManager" items="${QA_MANAGER_LIST}">
							<li>
								<input type="checkbox" id="qaManager_${qaManager.id}" name="createdBy" value="${qaManager.id}" />
								<label for="qaManager_${qaManager.id}" 	style="float: right; padding: 0px; width: auto;">${qaManager.firstName} ${qaManager.lastName}</label>
							</li>
						</c:forEach>
						<div class="clr"></div>
					</ul>
				</div>
				<div class="clr"></div>
			</div>
			<div class="row-container">
				<%--<div class="element-block" >
					 <label id="lblPostedBy">Created By:</label>
					<div class="select-container1">
						<select id="createdBy" name="createdBy">
							<option value="">-:QA Member List:-</option>
							<c:forEach var="QA_MANAGER" items="${QA_MANAGER_LIST}">
								<option value="${QA_MANAGER.id}">${QA_MANAGER.firstName} ${QA_MANAGER.lastName}</option>
							</c:forEach>
						</select>
					</div>
				</div> --%>
				<div class="element-block">
					<label >Status:</label>
					<div class="select-container1">
						<select id="status" name="status" >
							<option value="">All</option>
							<option value="0">Pending</option>
							<option value="1">In Progress</option>
							<option value="2">Completed</option>							
						</select>
					</div>
				</div>
				<div class="clr"></div>
			</div>
			
			<div class="row-container">
				<div class="element-block" >
					<label>Created Date From:</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input name="dateCreatedFrom"  type="text" id="dateCreatedFrom" readonly="readonly"  class="mid" style="width: 108px;" />
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="element-block" >
					<label class="right_lbl">Created Date To:</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input name="dateCreatedTo" type="text" id="dateCreatedTo" readonly="readonly"  class="mid" style="width: 108px;"  />
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="clr"></div>
			</div>
			
			<div class="row-container">
				<div class="element-block" style="width: 100%">
					<label>Keyword:</label>
					<span class="input_text">
						<span class="left_curve"></span>
						<input type="text" id="keyword"  name="keyword" maxlength="50"  class="mid" style="width: 250px" />
						<span class="right_curve"></span>
					</span>
				</div>
			</div>
			<br/>
			<br>
				
			<span class="input-container">
				<label>&nbsp;</label>
				<input type="submit" title="Search" value="Search" class="login-btn" />
				<input type="reset" title="Cancel" value="Cancel" class="login-btn" onclick="javascript:resetForm(this.form);" />
			</span>
			<div class="msg">
				<p class="success" id="msgSuccess" style="width: 100%"></p>
				<p class="error" id="msgError" style="width: 100%"></p>
			</div>
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
		var moduleName = "qamanager";
		$("#flex1").flexigrid({
			url : contextPath + '/' + moduleName + '/json',
			preProcess : formatData,
			method : 'GET',
			cache: false,

			colModel : [ {
				display : 'Action',
				name : 'action',
				width : 100,
				sortable : false,
				align : 'center'
			},{
				display : 'Name',
				name : 'name',
				width : 200,
				sortable : true,
				align : 'left'
			} ,{
				display : 'Month & Year',
				name : 'monthYear',
				width : 100,
				sortable : false,
				align : 'left'
			},{
				display : 'From & To Date',
				name : 'fromAndToDate',
				width : 100,
				sortable : false,
				align : 'left'
			} ,{
				display : 'Department',
				name : 'departmentName',
				width : 200,
				sortable : false,
				align : 'left'
			}, {
				display : 'Status',
				name : 'status',
				width : 100,
				sortable : false,
				align : 'left'
			}, {
				display : 'Created By',
				name : 'createdBy',
				width : 100,
				sortable : false,
				align : 'left'
			}, {
				display : 'Created On',
				name : 'createdOn',
				width : 80,
				sortable : false,
				align : 'left'
			}],
			buttons : [ {
				name : 'Add',
				bclass : 'add',
				onpress : action
			}, {
				name : 'PrintList',
				bclass : 'print',
				onpress : action
			} ],
			searchitems : [ {
				display : 'Name',
				name : 'name',
				isdefault : true
			} ],
			sortname : "id",
			sortorder : "asc",
			title : 'QA WorkSheet List',
			singleSelect: true,
			showTableToggleBtn : true,
			onSubmit : addFormData,
			width : 100,
			height : 300
		});

		//This function adds paramaters to the post of flexigrid. You can add a verification as well by return to false if you don't want flexigrid to submit
		function addFormData() {
			//passing a form object to serializeArray will get the valid data from all the objects, but, if the you pass a non-form object, you have to specify the input elements that the data will come from
			var dt = $('#qaworksheetList').serializeArray();
			$("#flex1").flexOptions({
				params : dt
			});
			return true;
		}
		
		$(function() {
		    $( "#dateCreatedFrom" ).datepicker();
		    $( "#dateCreatedTo" ).datepicker();
		});

		$('#qaworksheetList').submit(function() {

			if(null != $('#dateCreatedFrom').val() && null != $('#dateCreatedTo').val()){
				if(Date.parse($('#dateCreatedFrom').val()) > Date.parse($('#dateCreatedTo').val()) ){
					alert("Created date 'To' date must be after or equal to deposit date 'From' date. Please try again.");
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
			//var action = "";
			var status = "";
			var addUserIcon = '<c:url value="/static/resources/img/flexigrid/user_add.png"/>';
			var executeIcon = '<c:url value="/static/resources/img/flexigrid/execute.png"/>';
			var viewIcon = '<c:url value="/static/resources/img/view.png"/>';
			var temp = "";
			var viewDetailsIcon = '<c:url value="/static/resources/img/view_offset.png"/>';

			$.each(data.rows, function(i, row) {
				temp = "";
				status = "";
				//alert("row.status "+ row.status);
				if(row.status == "0"){
					temp += "<a href='"+contextPath+"/"+moduleName+"/add/?id="+row.id+"' class='edit' title='Edit' ><img src='"+editIcon+"'/></a>";
					
					temp += "&nbsp; <a href='javascript:void(0)' onClick='send_delete(" + row.id + ")' class='delete'><img src='"+deleteIcon+"' alt='Delete' title='Delete' /></a>";
					
					if(row.type == "1"){
						temp += "&nbsp; <a href='"+contextPath+"/"+moduleName+"/addstaff?id="+row.id+"' title='Add Staff' class='edit'><img src='"+addUserIcon+"'/></a>";
					}else if(row.type == "3"){
						temp += "&nbsp; <a href='"+contextPath+"/"+moduleName+"/adddoctor?id="+row.id+"' title='Add Doctor' class='edit'><img src='"+addUserIcon+"'/></a>";
					}
					
					temp += "&nbsp; <a href='javascript:void(0)'  onClick='javascript:executeQAWorksheet(" + row.id + ")' class='run' title='Execute Worksheet' ><img src='"+executeIcon+"'/></a>";
					
					status = "Pending";
					//alert("In 0");
					
				}else if(row.status == "1"){
					temp = "<a href='javascript:void(0)' onClick='send_delete(" + row.id + ")' class='delete'><img src='"+deleteIcon+"' alt='Delete' title='Delete' /></a>";
					temp += "&nbsp; <a href='#' class='run' title='Continue Worksheet' onclick='javascript:openQAWorksheetLayoutPOPUP("+row.id+");' ><img src='"+executeIcon+"'/></a>";
					status = "In Progress";
					
					//view remarks action
					temp += "&nbsp;&nbsp;<a href='javascript:void(0);' onclick='javascript:showPopup(" + row.id + ", " + row.departmentId + ", "+ row.type +" );' class='View'><img title='View QAWorksheet Users' alt='View' src='" + viewDetailsIcon + "'/></a>";
					//alert("In 1");
				}else if(row.status == "2"){
					status = "Completed";
					temp = "<a href='javascript:void(0);' class='run' title='View Worksheet' onclick='javascript:openQAWorksheetLayoutPOPUP("+row.id+");' ><img src='"+viewIcon+"'/></a>";
					//alert("In 2");
					//view remarks action
					temp += "&nbsp;&nbsp;<a href='javascript:void(0);' onclick='javascript:showPopup(" + row.id + ", " + row.departmentId + ", "+ row.type +" );' class='View'><img title='View QAWorksheet Users' alt='View' src='" + viewDetailsIcon + "'/></a>";
				}else{
					temp = "";
				}
				
				action = "<span id='spn_"+row.id+"'>" + temp + "</span>";

				//alert("action: "+ action);
				var department = row.departmentName;
				subDepartment = row.subDepartment;
				if (subDepartment != null) {
					department = department + " \n [ " + subDepartment + " ]"; 
				}
				var monthYearDate = row.month + " " + row.year;
				
				var fromAndToDate = "";
				if (row.fromDate != null) {
					fromAndToDate = row.fromDate + " " + row.toDate
				}
				
				rows.push({
					id : row.id,
					cell : [action, row.name, monthYearDate, fromAndToDate, department, status, row.createdBy, row.createdOn]
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
				window.location.href = contextPath + "/"+moduleName+"/add";
			}
			if (com == 'PrintList') {
				akpmsPopupWindow(contextPath + '/' + moduleName	+ "/printlist","qaworksheet_list");
				/* window.location.href = contextPath + "/"+moduleName+"/printlist"; */
			}
		}
		$('#status').val('${status}');
		
		function getSubDept(){
	    	 $.ajax({
		    		type : "Get",
		    		datatype:"json",
		     		url : contextPath + "/qamanager/subdepartment",
		     		data :"parentDepartmentId=" + $('#departmentId').val(),
		     		success : function(data) {
	                  var html = '<option value="-1">All</option>';
	                  var len = data.length;
	                  for(var i=0; i<len; i++){
	                       html += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
	                  }
	                  $('#subDepartmentId').empty().append(html);
	             },
	 		});
	     }

	    function openQAWorksheetLayoutPOPUP(rowId){
		    var url = contextPath+"/"+moduleName+"/continueintercept/?id="+rowId;
		    
	    	akpmsPopupWindow(url,"QAWorksheetLayout", "100%", "100%");
	    }
		
		$(document).ready(function() {
	         $('select#departmentId').change(function(){
	        	 getSubDept();
	         });
	    });
		
		function executeQAWorksheet(id) {
			// clear previous values
			$('#msgSuccess').html("");
			$('#msgError').html("");
			$('#spn_'+id).html(ajaxLoadImgHoriz);
			
			$.ajax({
				url : contextPath + "/" + moduleName + "/execute",
				type : 'POST',
				dataType : 'json',
				data : 'id=' + id,
				success : function (data) {
					//alert(data);
					//get first letter of data string that is "0" if no record found 
					//using this to show error or success message
					var res = data.substring(0, 1); 
					
					$('#flex1').flexOptions({
						newp : 1
					}).flexReload();
					// clear previous values
					$('#msgSuccess').html("");
					$('#msgError').html("");
					if(res == "0") {
						$('#msgError').html(data);
					} else {
						$('#msgSuccess').html(data);
					}
				}
			});
		}
		
		function send_delete(id){
			var confirmation = confirm("Are you sure you wish to delete this?");

			if (confirmation) {
				$.ajax({
					type : "POST",
					dataType : "json",
					url : contextPath + "/" + moduleName + "/delete",
					data : "item=" + id,
					success : function(data) {
						$("#flex1").flexReload();
						$("#msgP").text("QA worksheet has been successfully deleted.");
					}
				});
			}
		}
		//
	function showPopup(qaWorksheetId, departmentId, type ) {
		var htmlString = "";
		$('#popup_showRecord').bPopup({
			onOpen : function() {

			},onClose: function(){
				$("#divContainer").html(ajaxLoadImg);
			},follow: [true, false], //x, y
            position: ['auto', 10] //x, y
       	},function() {
       		htmlString = "<div class='table-grid' style='overflow-x: auto;'>"+
            "<table border='1' width='100%'>"
            + "<thead><tr><th class='heading'>Name</th>"
 			+ "<th class='heading'>Record Count</th>"
 			+ "</tr></thead><tbody>";

      		$.ajax({
				type : "GET",
				dataType : "json",
				async: false,
				url : contextPath + "/" + moduleName + '/qastaff_json',
				data : "id=" + qaWorksheetId + '&departmentId=' + departmentId,
				success : function(data) {
					for(var i=0;i<data.length;i++){
						htmlString += "<tr>";
						htmlString += "<td>" + data[i].userName + "</td>";
						htmlString += "<td>" + data[i].remarks + "</td>";
						//htmlString += "<td>" + data[i].arStatusCode + "</td>";
						
						htmlString += "</tr>";
					}
					htmlString += "</tbody></table></div>";
				},
				error: function(data){
					//alert (data);
				}
			});

      		$("#divContainer").html(htmlString);
       	});
	}
</script>

