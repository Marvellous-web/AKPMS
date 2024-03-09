<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home </a> &raquo;</li>
			<li>&nbsp;QA Reports</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<div class="msg">
		<p class="success" id="msgP">${success}</p>
		<p class="error" id="msgP">${error}</p>
	</div>

	<form  class="form-container" id="form1" method="get" target="_Blank">
		<div class="form-container-inner">
			<div class="row-container">
				<div class="element-block">
					<label>Department:<em class="mandatory">*</em> </label>
					<span class="select-container2" style="display: inline;"> 
					<select id="departmentId" name="department" class="selectbox">
							<option value="">-:Select Department:-</option>
							<c:forEach items="${parentDepartments}" var="parent">
								<option value="${parent.id}">${parent.name}</option>
							</c:forEach>
					</select>
					</span>
					<p class="invalid" id="errorMsgDepartment"></p>
				</div>
				<div class="element-block">
					<label>Sub Department:</label>
					<span class="select-container2" style="display: inline;"> 
					<select  id="subDepartmentId" name="subDepartment" class="selectbox">
						<option value="-1">All</option>
					</select>
					</span>
					<p class="invalid" id="errorMsgSubDepartment"></p>
				</div>
				<div class="clr"></div>
			</div>
			<div class="clr"></div>
			<div class="row-container">
				<div class="element-block">
					<label >Created Date From:<em class="mandatory">*</em> </label>
					<span class="input_text">
						<span class="left_curve"></span>
						<input type="text" name="qaReportFromDate" id="fromDateId" class="mid" style="width:100px;" />
						<span class="right_curve"></span>
					</span>
					<p class="invalid" id="errorMsgFromDate"></p>
				</div>
				<div class="element-block" >
					<label >Created Date To:<em class="mandatory">*</em> </label>
					<span class="input_text">
						<span class="left_curve"></span>
						<input type="text" name="qaReportToDate" id="toDateId" class="mid" style="width:100px;" />
						<span class="right_curve"></span>
					</span>
					<p class="invalid" id="errorMsgToDate"></p>
				</div>
				<div class="clr"></div>
				<%-- <div class="element-block">
					<label >Month:<em class="mandatory">*</em> </label>
					<span class="select-container2"> 
					<select id="monthId" name="month" class="selectbox">
							<option value="">-:Select month:-</option>
							<c:forEach var="month" items="${months}">
								<option value="${month.key}"> ${month.value} </option>
							</c:forEach>
					</select>
					</span>
					<p class="invalid" id="errorMsgMonth"></p>
				</div>
				<div class="element-block">
					<label>Year:<em class="mandatory">*</em> </label>
					<span class="select-container2"> 
					<select id="yearId" name="year" class="selectbox">
							<option value="">-:Select Year:-</option>
							<c:forEach var="year" items="${years}">
								<option value="${year}">${year}</option>
							</c:forEach>
						</select>
					</span>
					<p class="invalid" id="errorMsgYear"></p>
				</div> --%>
				
			</div>
			
			<div class="row-container">
				<div class="element-block" style="width:100%">
					<label>Created By:</label>
					<div class="box-left">
						<ul>
							<c:forEach var="qaManager" items="${QA_MANAGER_LIST}">
								<li>
									<input type="checkbox" id="qaManager_${qaManager.id}" name="createdBy" value="${qaManager.id}" />
									<label for="qaManager_${qaManager.id}" 	style="float: right; padding: 0px; width: auto;">${qaManager.firstName} ${qaManager.lastName}</label>
								</li>
							</c:forEach>
						</ul>
					</div>
				</div>
				<div class="clr"></div>
			</div>
			
			<div class="row-container">
				<div class="element-block" style="width:100%">
					<label>Report Type:<em class="mandatory">*</em> </label>
				<!-- </div>	
				<div class="element-block"> -->
					<input type="radio" checked="checked" name="qaReportType" value="qareportuser" id="qareportuser" > &nbsp;&nbsp;
					<label for="qareportuser" style="width: auto; display: inline; float: none">QA Report (User)</label>
					&nbsp;&nbsp;
					<input type="radio" name="qaReportType" value="qareportqcpoint" id="qareportqcpoint"> &nbsp;&nbsp;
					<label for="qareportqcpoint" style="width: auto; display: inline; float: none">QA Report (QC Point)</label>
					&nbsp;&nbsp;
					<input type="radio" name="qaReportType" value="summaryreport" id="summaryreport"> &nbsp;&nbsp;
					<label for="summaryreport" style="width: auto; display: inline; float: none">QA Summary Report</label>
					&nbsp;&nbsp;
					<input type="radio" name="qaReportType" value="monthlysummaryreport" id="monthlysummaryreport"> &nbsp;&nbsp;
					<label for="monthlysummaryreport" style="width: auto; display: inline; float: none">Monthly Summary Report</label>
				</div>
				<div class="clr"></div>
			</div>
			
			<!-- <div class="row-container">
				<div class="element-block">
					
				</div>
				<div class="clr"></div>	
			</div> -->
			
			<div class="row-container">
				<div class="element-block">
					<label>&nbsp;</label>
					<input type="submit" id="showQAReports" class="login-btn" value="Show Report" /> 
					<input type="button" title="Cancel" value="Cancel" class="login-btn" onclick="javascript:resetForm(this.form);showHideElements();" />
				</div>
				<div class="element-block">
					&nbsp;
				</div>
				<div class="clr"></div>
			</div>
			
			<div class="clr"></div>
			<div class="noSearchItem">&nbsp;</div>
		</div>
		</form>
	</div>
		<script type="text/javascript">
			var moduleName = "qamanager/reports";
			
			function changeFormAction(){
				$('#form1').attr('action', contextPath + '/' + moduleName + '/' 
						+ $('input[name=qaReportType]:checked', '#form1').val());
				$('#form1').submit();
			}
			
			function getSubDept(){
		    	 $.ajax({
			    		type : "Get",
			    		datatype:"json",
			     		url : contextPath + "/qamanager/subdepartment",
			     		data :"parentDepartmentId=" + $('#departmentId').val(),
			     		success : function(data) {
			     			//alert('success ' + data);
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
	    	  //createFlexiGrid ();
	    	   	$(function() {
		     	    $( "#fromDateId" ).datepicker();
		     	    $( "#toDateId" ).datepicker();
		     	});
		     	 
	    	  $('#form1').submit(function() {
					var flag = true;
		    	  
	    		  $("#errorMsgToDate").html("");
	    		  $("#errorMsgFromDate").html("");
	    		  $('#errorMsgDepartment').html("");
	    		  $('#errorMsgSubDepartment').html("");
    			  //Department validations
		  		  if ($('select#departmentId').val() == "") {
					$('#errorMsgDepartment').html("Please select department");
					flag = false;
				  }
				  //else if($('select#departmentId').val() == "1" && $('select#subDepartmentId').val() == "-1") {
				//	  $('#errorMsgSubDepartment').html("Please select sub department");
				//	  flag = false;
				 // }
	    		//Date validations 
	  	  		if(null != $('#fromDateId').val() && $('#fromDateId').val() != ''
	  				&& null != $('#toDateId').val() && $('#toDateId').val() != '') {
		  			
		  			if(Date.parse($('#fromDateId').val()) > Date.parse($('#toDateId').val()) ){
		  				$("#errorMsgToDate").html("Date 'To' date must be after or equal to date 'From' date. Please try again.");
		  				flag = false;
		  			}
	  			} else{
		  			 if (null == $('#fromDateId').val() || $('#fromDateId').val() == '') {
	  					$("#errorMsgFromDate").html("Please select 'From' Date.");	  		
	  					flag = false;		
	  				} 
		  			if (null == $('#toDateId').val() || $('#toDateId').val() == '') {
	  					$("#errorMsgToDate").html("Please select 'To' Date.");
	  					flag = false;
	  				}	  				
	  			}
	  	  		
	  			/* if ($('#monthId').val() == "") {
	  				$('#errorMsgMonth').html("Please select month");
			  		return false;
				}else {
					$('#errorMsgMonth').html("");
				}
	  			if ($('#yearId').val() == "") {
	  				$('#errorMsgYear').html("Please select year");
			  		return false;
				}else {
					$('#errorMsgYear').html("");
				} */

				if(flag) changeFormAction();
				
	  			return flag;
	  		}); 

	         $('select#departmentId').change(function(){
	        	 getSubDept();
	         });
	      });
			
		</script>