<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li><a href="<c:url value='/qamanager' />">&nbsp;QA Worksheet List</a>&raquo;</li>
			<li>&nbsp;${operationMode} QA Worksheet Doctor</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<div class="msg">
		<p class="success" id="msgP">${success}</p>
	</div>
	
	<div class="section-content">
		<h3 class="heading-h3">QA Worksheet Details</h3>
		<div class="row-container">
			<div class="element-block" >
				<span class="lbl">Worksheet Name:</span>
			    <span>${qaworksheet.name}</span>
			</div>
		</div>
		
		<div class="row-container">
			<div class="element-block" >
				<span class="lbl">Department Name:</span>
			    <span>${qaworksheet.department.name}</span>
			</div>
			
			<div class="element-block" >
				<c:if test="${not empty qaworksheet.subDepartment}">
					<span class="lbl">Sub-Department Name:</span>
				    <span>${qaworksheet.subDepartment.name}</span>
				</c:if>    
			</div>				
			<div class="clr"></div>
		</div>
		
		<div class="row-container">
			<div class="element-block" >
				<span class="lbl">Billing Month:</span>
			    <span>${qaworksheet.billingMonth}</span>
			</div>
			<div class="element-block" >
				<span class="lbl">Billing Year:</span>
			    <span>${qaworksheet.billingYear}</span>
			</div>	
			<div class="clr"></div>
		</div>
		
		<c:if test="${qaworksheet.postingDateFrom ne null && qaworksheet.postingDateTo ne null} ">
			<div class="row-container">
				<div class="element-block" >
					<span class="lbl">Posting Date From:</span>
				    <span>${qaworksheet.postingDateFrom}</span>
				</div>
				<div class="element-block" >
					<c:if test="${qaworksheet.postingDateTo ne null} ">
						<span class="lbl">Posting Date To:</span>
					    <span>${qaworksheet.postingDateTo}</span>
					</c:if>    
				</div>	
				<div class="clr"></div>
			</div>
		</c:if>
	</div>
	
	
	<%-- qaworksheetAddDoctor.jsp  --%>
	<form:form method="post" modelAttribute="qaworksheetDoctor" class="form-container"  id="form1">
		<div class="form-container-inner">
			<h3 class="heading-h3">Add Doctor to QA Worksheet</h3>
			<div class="row-container">
			
				<div class="element-block">
					<form:label path="doctor.id">Select Doctor:<em class="mandatory">*</em></form:label>
					<span class="select-container2" style="display:inline;">
						<form:select path="doctor.id" class="selectbox">
							<form:option value="">-: Select Doctor :-</form:option>
							<c:forEach items="${doctorList}" var="doctor">
								<form:option value="${doctor.id}">${doctor.name}</form:option>
							</c:forEach>
						</form:select> 
					</span>
					<form:errors path="doctor.id" cssClass="invalid" />
				</div>
				
				<form:hidden path="qaWorksheet.id" value="${qaworksheet.id}"/>
				<div class="element-block" id="divGeneralBox" style="visibility:inline;">
					<form:label path="percentageValue">Doctor Percentage:<em class="mandatory">*</em></form:label>
					<span class="input_text">
						<span class="left_curve"></span>
							<form:input path="percentageValue" type="text" name="doctorPercentage" id="percentage" cssClass="mid numbersOnly"
							cssStyle="width:34px" maxlength="5" size="5" /><Strong>%</Strong>
						<span class="right_curve"></span>
					</span>
					<form:errors path="percentageValue" cssClass="invalid"/>
				</div>	
				<div class="clr"></div>
			</div>
			
			<div class="row-container">	
				<div class="element-block">
					<a href='<c:url value="/qamanager"/>' style="color: blue"> Back to QAWorksheet List</a>
					<!-- <label> Add More </label> -->
					<input type="hidden" name="addMore" value="1"/>
				</div>
				<div class="element-block" >
					<input type="submit" id="addToList" class="login-btn" value="Add Doctor"/>
				</div>
				<div class="clr"></div>
			</div>
		</div>
	</form:form>
	
	<table id="flex1" style="display: none"></table>
	
</div>
<script type="text/javascript">
	 $("#flex1").flexigrid({
			url : contextPath + '/qamanager/doctorjson',
			preProcess : formatData,
			method : 'GET',
			colModel : [ {
				display : 'Delete',
				name : 'del',
				width : 50,
				sortable : false,
				align : 'center',
			}, {
				display : '%  ',
				name : 'limit',
				width : 100,
				sortable : false,
				align : 'right'
			}, {
				display : 'Name',
				name : 'doctorName',
				width : 200,
				sortable : false,
				align : 'left'
			} , {
				display : 'Remarks',
				name : 'remarks',
				width : 100,
				sortable : false,
				align : 'left'
			}],
			
			title : 'QA Worksheet Doctor List',
			singleSelect: true,
			showTableToggleBtn: true,
			onSubmit : addFormData,
			width : 100,
			height : 300
		});
		//This function adds paramaters to the post of flexigrid. You can add a verification as well by return to false if you don't want flexigrid to submit
		function addFormData() {
			//passing a form object to serializeArray will get the valid data from all the objects, but, if the you pass a non-form object, you have to specify the input elements that the data will come from
			var formdata = $('#form1').serializeArray();
			$("#flex1").flexOptions({
				params : formdata
			});
			return true;
		}
		
		$('#form1').submit(function() {
			$('#flex1').flexOptions({
				 newp : 1
			}).flexReload();
			return true; 
		});
		
		/* $(document).ready(function() {
		    $("#form1").submit(function() { 

			    var val = $("input[type=submit][clicked=true]").val()
				if (val == 'Add Doctor') {
					$('#flex1').flexOptions({
						 newp : 1
					}).flexReload();
					return false; 
				} else if (val == 'GoTo QAWorksheetList') {
					return true;
				}

			});
		}); */
		function formatData(data) {
			var rows = Array();
			var del = "";
			$.each(data.rows, function(i, row) {
		
				del = "<a href='#' onClick='send_delete(" + row.id + ")' class='delete'><img src='"+deleteIcon+"' alt='Delete' title='Delete' /></a>";
			
				rows.push({
					cell : [del, row.recordPercentage + '%', row.doctorName, row.remarks]
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
		
		function send_delete(id){
			$.ajax({
				type : "POST",
				dataType : "json",
				url : contextPath + "/qamanager/deletedoctor",
				data : "item=" + id,
				success : function(data) {
					if (data) {
						$("#flex1").flexReload();
						$("#msgP").text("Doctor have been successfully deleted.");
					} else {
						$("#msgP").text("Failed to delete Doctor.");
					}
				}
			});
		};
 </script>