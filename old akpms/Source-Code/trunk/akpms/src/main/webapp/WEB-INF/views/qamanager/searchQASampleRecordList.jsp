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
	
	<form class="form-container" method="get" id="searchSampleRecord" target="_blank" action="searchsample">
		<div class="clr"></div>
		<div class="form-container-inner">
			<h3>Search QA Worksheet</h3>
			<div class="row-container">
				<div class="element-block" >
					<label>Department:</label>
					<div class="select-container1">
						<select id="departmentId" name="departmentId">
							<option value="">-: Select Department :-</option>
							<c:forEach var="department" items="${parentDepartments}" >
								<option value="${department.id}">${department.name}</option>
							</c:forEach>
						</select>
					</div>
					<span class="invalid" id="spnDepartment"></span>
				</div>
				<div class="element-block" >
					<label class="right_lbl">Sub Department:</label>
					<div class="select-container1">
						<select id="subDepartmentId" name="subDepartmentId" onchange="javascript:itemFocus('submit');">
							<option value="-1">-All-</option>
						</select>
					</div>
					<span class="invalid" id="spnSubDepartment"></span>
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
					<span class="invalid" id="spnKeyword"></span>
				</div>
				<div class="clr"></div>
			</div>
			<br/>
				
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
		</div>
	</form>
	<div class="popup2" id="popup_showRecord" style="display: none;">
		<span class="button bClose"><span>X</span></span>
		<div class="popup-container" id="divContainer"><img	src="<c:url value="/static/resources/img/ajax-loader.gif"/>" alt="" /></div>
	</div>
</div>
<script type="text/javascript">
		var moduleName = "qamanager";
		
		$('#searchSampleRecord').submit(function() {
			var flag = true;
			$("#spnDepartment").html("");
			$("#spnSubDepartment").html("");			
			$("#spnKeyword").html("");
			
			if($("#departmentId").val() == ""){
				$("#spnDepartment").html("Please select department.");
				flag = false;
			}
			//else if ($("#departmentId").val() == 1 && $("#subDepartmentId").val() == "-1") {
			//	$("#spnSubDepartment").html("Please select sub department.");
			//	flag = false;	
			//}

			if($.trim($("#keyword").val()) == ""){
				$("#spnKeyword").html("Please enter Search keyword.");
				flag = false;
			}
			
			return flag;
		});
		
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

		$(document).ready(function() {
	         $('select#departmentId').change(function(){
	        	 getSubDept();
	         });
	    });
		
</script>

