<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home </a> &raquo;</li>
			<li>&nbsp;Trainee list</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<div class="msg">
		<p class="success">${success}</p>
	</div>
	<form class="form-container" id="sform">
		<div class="form-container-inner">
			<h3>Search Trainees</h3>

			<span class="input-container"> <label>Keyword:</label>
				<span class="input_text">
					<span class="left_curve"></span>
					<input type="text" id="keyword"  name="keyword" maxlength="50"  class="mid" style="width: 373px" />
					<span class="right_curve"></span>
				</span>
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
				<input	type="submit" title="Search" value="Search"	 class="login-btn" />
				<input type="button" title="Cancel" value="Cancel"
				class="login-btn" onclick="javascript:resetForm(this.form);"/>
			</span>

			<div class="noSearchItem">&nbsp;</div>

			<table id="flex1" style="display: none"></table>

		</div>
		<input type="hidden" name="selectedRolesIds" id="selectedRolesIds" value="" />
        <input type="hidden" name="selectedDepartmentsIds" id="selectedDepartmentsIds" value="" />
	</form>
</div>

<script type="text/javascript">
	var moduleName = "traineeevaluation";
	$("#flex1").flexigrid({
		url : contextPath + '/'+moduleName+'/json',
		preProcess : formatData,
		method : 'GET',

		colModel : [ {
			display : 'Name',
			name : 'firstName',
			width : 180,
			sortable : true,
			align : 'left'
		}, {
			display : 'Departments',
			name : 'departmentNames',
			width : 180,
			sortable : false,
			align : 'left'
		}, {
			display : 'Evaluated On',
			name : 'lastEvaluatedOn',
			width : 112,
			sortable : false,
			align : 'left'
		}, {
			display : 'Evaluated By',
			name : 'lastEvaluatedBy',
			width : 80,
			sortable : false,
			align : 'left'
		}, {
			display : 'Current Score(%)',
			name : '',
			width : 100,
			sortable : false,
			align : 'left'
		} , {
			display : 'Evaluate',
			name : 'traineeEvaluation',
			width : 100,
			sortable : false,
			align : 'center'
		} ],

		searchitems : [ {
			display : 'Name',
			name : 'name',
			isdefault : true
		} ],
		sortname : "id",
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
		var traineeEvaluation = "";
		var evaluationIcon = '<c:url value="/static/resources/img/evaluation_trainee.png"/>';
		var lastEvaluatedOn = "";

		$.each(data.rows,function(i, row) {

				traineeEvaluation = "&nbsp;&nbsp;<a href='"
						+ contextPath+"/"
						+ moduleName
						+ "/evaluatetrainee/?tid="
						+ row.id
						+ "'><img src='"+evaluationIcon+"' alt='Evaluate Trainee' /> Evaluate </a>";
				rows.push({
					id : row.id,
					cell : [ row.firstName+' '+row.lastName, row.departmentNames,
					        row.lastEvaluatedOn,
							row.lastEvaluatedBy,
							row.traineePercent, traineeEvaluation ]
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
</script>
