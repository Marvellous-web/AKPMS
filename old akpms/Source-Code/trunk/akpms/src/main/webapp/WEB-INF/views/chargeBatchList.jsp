<%@ page language="java" contentType="text/html; charset=ISO-8859-1"    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li>&nbsp;Charge Batch List</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<div class="msg">
		<p class="success" id="msgP">${success}</p>
	</div>

	<c:set var="re_update_grant" value="false"></c:set>
	<sec:authorize ifAnyGranted="P-10">
		<c:set var="re_update_grant" value="true"></c:set>
	</sec:authorize>

	
	<c:set var="pagesThreshold"><spring:message code="pages.thresholdValue"></spring:message></c:set>
	<c:set var="printValidation"><spring:message code="printValidation" arguments="${pagesThreshold}"></spring:message></c:set>
	

	<form class="form-container" id="chargeBatchSearchForm">
		<div class="form-container-inner">
			<h3>Charge Batching System (CBS) Report</h3>

			<%-- <div class="row-container">
				<div class="element-block" >
					<label>Charge posting Month:</label>
					<div class="select-container2">
						<select id="month" name= "month">
							<option value="0">-:Month:-</option>
					    	<c:forEach var="month" items="${months}">
					    		<option value="${month.key}">${month.value}</option>
					    	</c:forEach>
					    </select>
				    </div>
				</div>
				<div class="element-block" >
					<label class="right_lbl">Year:</label>
					<div class="select-container2">
						<select id="year" name="year">
							<option value="0">-:Year:-</option>
					    	<c:forEach var="year" items="${years}">
					    		<option value="${year}">${year}</option>
					    	</c:forEach>
					    </select>
				    </div>
				</div>
				<div class="clr"></div>
			</div> --%>

		    <div class="row-container">
		    	<label>Group/Doctor Name:</label>
		    	<div>
					<select id="doctor" name= "doctor">
						<option value="0">-: Select All Group/Doctor :-</option>
				  <!--  	<c:forEach var="doctor" items="${doctorList}">
				    		<option value="${doctor.id}">
								${doctor.name}
				    			<c:if test="${not empty doctor.parent}">
				    			  (${doctor.parent.name})
				    			</c:if>
				    		</option>
				    	</c:forEach>  -->
						
				<c:forEach var="doctor" items="${doctorList}">
					<option value="${doctor.id}">${doctor.name}
					<c:if test="${doctor.nonDeposit && doctor.id=='86'}">(Non-Deposit)</c:if>
					<c:if test="${not empty doctor.parent}">
						(${doctor.parent.name})										
					</c:if>
					</option>
				</c:forEach>
						
				    </select>
				</div>
				<div class="clr"></div>
		    </div>
		    
		    <div class="row-container">
				<label>Type:</label>
				<c:forEach var="type" items="${reportTypeList}">
					<input type="checkbox" name="reportType" id="chk_${type}" value="${type.value}"/> ${type.value}&nbsp;&nbsp;&nbsp;
				</c:forEach>
				<div class="clr"></div>
			</div>

		    <div class="row-container">
				<div class="element-block" >
					<label>Batch DOS (From):</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input name="dosFrom" type="text" id="dosFrom" readonly="readonly" class="mid" style="width: 108px;" />
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="element-block" >
					<label class="right_lbl" >Batch DOS (To):</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input name="dosTo" id="dosTo" type="text" readonly="readonly" class="mid" style="width: 108px;"  />
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block">
					<label>Created Date From:</label>
					<div class="input_text">
						<span class="left_curve"></span> 
						<input type="text" name="dateCreatedFrom" id="dateCreatedFrom" readonly="readonly"
							maxlength="50" class="mid" style="width: 108px;" /> 
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="element-block">
					<label class="right_lbl">To:</label>
					<div class="input_text">
						<span class="left_curve"></span> 
						<input type="text" name="dateCreatedTo" id="dateCreatedTo" readonly="readonly"
							class="mid" style="width: 108px;" /> 
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="clr"></div>
			</div>
			
			<div class="row-container">
				<div class="element-block" >
					<label >Batch Received (From):</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input name="dateReceivedFrom" id="dateReceivedFrom" type="text"  readonly="readonly"  class="mid" style="width: 108px;" />
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="element-block" >
					<label class="right_lbl">Batch Received (To):</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input name="dateReceivedTo" type="text" id="dateReceivedTo" readonly="readonly"  class="mid" style="width: 108px;"  />
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<label>Received By:</label>
				<div>
					<select id="receivedBy" name= "receivedBy">
						<option value="0">-:Received By:-</option>
				    	<c:forEach var="user" items="${userList}">
				    		<option value="${user.id}">${user.firstName} ${user.lastName}</option>
				    	</c:forEach>
				    </select>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block" >
					<label>CT Posted Date (From):</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input name="dateBatchPostedFrom"  type="text" id="dateBatchPostedFrom" readonly="readonly"  class="mid" style="width: 108px;" />
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="element-block" >
					<label class="right_lbl">CT Posted Date (To):</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input name="dateBatchPostedTo" type="text" id="dateBatchPostedTo" readonly="readonly"  class="mid" style="width: 108px;"  />
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block" >
					<label id="lblPostedBy">Posted By:</label>
					<div class="select-container1">
						<select id="postedBy" name="postedBy">
							<option value="0">-:Select Posted By:-</option>
							<c:forEach var="user" items="${userList}">
								<option value="${user.id}">${user.firstName} ${user.lastName}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="element-block" >
					<label class="right_lbl">Created By:</label>
					<div class="select-container1">
						<select id="createdBy" name="createdBy">
							<option value="0">-:Select Created By:-</option>
							<c:forEach var="user" items="${userList}">
								<option value="${user.id}">${user.firstName} ${user.lastName}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="clr"></div>
			</div>
			
			<div class="row-container">
				<div class="element-block" >
					<label>Ticket Number:</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input type="text" name="ticketNumberSearch" id="ticketNumberSearch" maxlength="50" class="mid" style="width: 108px;" />
						<span class="right_curve"></span>
						<br/><small>(eg:"1", "5-10", "3,7,9")</small>
					</div>
				</div>
				<div class="element-block" >
					
						<input type="radio" name="received" id="rad_received" value="1"/> Received
						<input type="radio" name="received" id="rad_not_received" value="0"/> Not Received
						<input type="radio" name="received" id="rad_all" value=""/> All
								
				</div>
				<div class="clr"></div>
			</div>

			<span class="input-container"> <label>&nbsp;</label> 
				<input	type="submit" title="Search" value="Search" class="login-btn" />
				<input type="button" title="Cancel" value="Cancel" onclick="javascript:resetForm(this.form);" class="login-btn" />
			</span>

			<div class="noSearchItem">&nbsp;</div>

			<table id="flex1" style="display: none"></table>
		</div>
	</form>
</div>

<script type="text/javascript">
var canReviseCreate = false;
</script>

<sec:authorize ifAnyGranted="P-15">
	<script type="text/javascript">
		canReviseCreate = true;
	</script>
</sec:authorize>


<script type="text/javascript">
	var moduleName = "chargebatchprocess";
    $("#dateCreatedTo").val('${createdTo}');
	$("#dateCreatedFrom").val('${createdFrom}');
	var totalRecord = 0;
	$("#flex1").flexigrid({
		url : contextPath + '/'+moduleName+'/json',
		preProcess : formatData,
		method : 'GET',

		colModel : [{
			display : 'Action',
			name : 'edit',
			width : 60,
			sortable : false,
			align : 'left'
		},{
			display : 'Ticket #',
			name : 'id',
			width : 95,
			sortable : true,
			align : 'left'
		},{
			display : 'Type',
			name : 'type',
			width : 40,
			sortable : true,
			align : 'left'
		}, {
			display : 'Doctor',
			name : 'doctor',
			width : 160,
			sortable : true,
			align : 'left'
		}, {
			display : 'From',
			name : 'dosFrom',
			width : 64,
			sortable : true,
			align : 'left'
		}, {
			display : 'To',
			name : 'dosTo',
			width : 64,
			sortable : true,
			align : 'left'
		},{
			display : 'Posted By',
			name : 'postedBy',
			width : 80,
			sortable : true,
			align : 'left'
		},{
			display : 'CT Posted Date',
			name : 'dateBatchPosted',
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			display : 'Super Bills Doctor',
			name : 'numberOfSuperbills',
			width : 85,
			sortable : true,
			align : 'left'
		},{
			display : 'Super Bills Argus',
			name : 'numberOfSuperbillsArgus',
			width : 85,
			sortable : true,
			align : 'left'
		},{
			display : 'Attachments Doctor',
			name : 'numberOfAttachments',
			width : 85,
			sortable : true,
			align : 'left'
		},{
			display : 'Attachments Argus',
			name : 'numberOfAttachmentsArgus',
			width : 85,
			sortable : true,
			align : 'left'
		},{
			display : 'Received By',
			name : 'receivedBy',
			width : 80,
			sortable : true,
			align : 'left'
		},{
			display : 'Received Date',
			name : 'dateReceived',
			width : 80,
			sortable : true,
			align : 'left'
		} ],
		buttons : [ {
			name : 'Add',
			bclass : 'add',
			onpress : action
		},{
			name : 'Print Batches',
			bclass : 'print',
			onpress : action
		},{
			name : 'CBS List',
			bclass : 'printable',
			onpress : action
		} ],
		searchitems : [ {
			display : 'Name',
			name : 'name',
			isdefault : true
		} ],
		sortname : "id",
		sortorder : "desc",
		title : 'Charge Batch List',
		showTableToggleBtn : true,
		onSubmit : addFormData,
		//rpOptions: [10, 15, 20, 30, 50,100],
		width : 100,
		height : 300
		
	});

	//This function adds paramaters to the post of flexigrid. You can add a verification as well by return to false if you don't want flexigrid to submit
	function addFormData() {
		//passing a form object to serializeArray will get the valid data from all the objects, but, if the you pass a non-form object, you have to specify the input elements that the data will come from
		var dt = $('#chargeBatchSearchForm').serializeArray();
		$("#flex1").flexOptions({
			params : dt
		});

		return true;
	}

	$(function() {
	    $( "#dosFrom" ).datepicker();
	    $( "#dosTo" ).datepicker();
	    $( "#dateReceivedFrom" ).datepicker();
	    $( "#dateReceivedTo" ).datepicker();
	    $( "#dateBatchPostedFrom" ).datepicker();
	    $( "#dateBatchPostedTo" ).datepicker();
	    $("#dateCreatedFrom").datepicker();
	    $("#dateCreatedTo").datepicker();

	});

	$('#chargeBatchSearchForm').submit(function() {
		if(null != $('#dosFrom').val() && null != $('#dosTo').val()){
			if(Date.parse($('#dosFrom').val()) > Date.parse($('#dosTo').val()) ){
				alert("Sorry, the Batch Prepared(To) date cannot be lesser than the Batch Prepared(From) date.");
				return false;
			}
		}

		if(null != $('#dateReceivedFrom').val() && null != $('#dateReceivedTo').val()){
			if(Date.parse($('#dateReceivedFrom').val()) > Date.parse($('#dateReceivedTo').val()) ){
				alert("Sorry, the Batch Received(To) date cannot be lesser than the Batch Received(From) date.");
				return false;
			}
		}

		if(null != $('#dateBatchPostedFrom').val() && null != $('#dateBatchPostedTo').val()){
			if(Date.parse($('#dateBatchPostedFrom').val()) > Date.parse($('#dateBatchPostedTo').val()) ){
				alert("Sorry, the Batch Posted(To) date cannot be lesser than the Batch Posted(From) date.");
				return false;
			}
		}

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

		var edit = "";
		var view = "";
		var receive = "";
		var reUpdate = "";

		var reUpdateGrant = ''+${re_update_grant};

		var reUpdateIcon = '<c:url value="/static/resources/img/re_update.png"/>';
		var viewIcon = '<c:url value="/static/resources/img/view.png"/>';
		var receiveIcon = '<c:url value="/static/resources/img/batch_receive.png"/>';

		$.each(data.rows,function(i, row) {
			
			
			edit = "";
			view = "";
			receive = "";
			reUpdate = "";			

			var url = new String(contextPath + '/'+ moduleName + "/add/?id=" + row.id);

			view = "&nbsp;<a href='javascript:void(0);' onclick='javascript:akpmsPopupHTML(\""+url+"&viewBatch=1&Popup=1\")' class='view'><img src='"+viewIcon+"' title='View' alt='View'/></a>";
			
			
			/* if((row.receivedBy == null || $.trim(row.receivedBy) == '') || (row.postedBy == null || $.trim(row.postedBy) == '') ){
				receive = "&nbsp;<a href='javascript:void(0);' onclick='javascript:akpmsPopupWindow(\""+url+"&Popup=1\",\"editChBatch\")' class='edit'><img src='"+receiveIcon+"' title='Receive' alt='Receive'/></a>";
				edit = "&nbsp;<a href='javascript:void(0);' onclick='javascript:akpmsPopupWindow(\""+url+"&Popup=1&operationType=0\",\"editChBatch\")' class='edit'><img src='"+editIcon+"' title='Edit' alt='Edit'/></a>";
			} */

			if((row.postedBy != null && $.trim(row.postedBy) != '') && reUpdateGrant == 'true'){
				reUpdate = "&nbsp;<a href='javascript:void(0);' onclick='javascript:akpmsPopupWindow(\""+url+"&Popup=1\",\"editChBatch\")' class='edit'><img src='"+reUpdateIcon+"' title='Re-update' alt='Re-update'/></a>";
			}

			else{
				receive = "&nbsp;<a href='javascript:void(0);' onclick='javascript:akpmsPopupWindow(\""+url+"&Popup=1\",\"editChBatch\")' class='edit'><img src='"+receiveIcon+"' title='Receive' alt='Receive'/></a>";
			}

			if (canReviseCreate == true) {
				
				edit = "&nbsp;<a href='javascript:void(0);' onclick='javascript:akpmsPopupWindow(\""+url+"&Popup=1&operationType=0\",\"editChBatch\")' class='edit'><img src='"+editIcon+"' title='Edit' alt='Edit'/></a>";				
			}
			
			billDoctor = row.numberOfSuperbills;
			billArgus = row.numberOfSuperbillsArgus;

			attachementsDoctor = row.numberOfAttachments;
			attachementsArgus = row.numberOfAttachmentsArgus;

			rows.push({
				id : row.id,
				cell : [edit + receive +view + reUpdate,
				        '<span style="font-size:15px;">'+row.id+'</span>',
						row.type,
						row.doctorName,
						row.dosFrom,
						row.dosTo,
						row.postedBy,
						row.dateBatchPosted,
						billDoctor,billArgus,
						attachementsDoctor,attachementsArgus,
						row.receivedBy,
						row.dateReceived]
			});
		});
		totalRecord = data.total; 
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
			window.location.href = contextPath + '/' + moduleName	+ "/add";
		}
		else if (com == 'Print Batches') {
			// validate here
			  if(totalRecord > parseInt('${pagesThreshold}'))
				{
					alert("${printValidation}");
				}  
			  else{
				//window.open(contextPath + '/' + moduleName	+ "/print","_blank");
				akpmsPopupWindow(contextPath + '/' + moduleName	+ "/printhtml","charge_batch_print");
			  }
						
		}else if(com == 'CBS List') {
			/*  if(totalRecord > parseInt('${pagesThreshold}'))
			{
				alert("${printValidation}");
			} */ 
			 
			//window.open(contextPath + '/' + moduleName	+ "/printablepdf","_blank");
			akpmsPopupWindow(contextPath + '/' + moduleName	+ "/printhtmltabular","charge_batch_tabular");
		}
	}

	$(function() {
		if (canReviseCreate == false) {
			$('.add').hide();
		}
	});
</script>
