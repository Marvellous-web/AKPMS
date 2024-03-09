<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
	<form class="form-container" id="sform" method="get">
		<%-- <input type="hidden" name="substatus" id="substatus" value="${substatus}"> --%>
		<div class="form-container-inner">
			<h3>Search AR Productivity</h3>

			<div class="row-container">
				<div class="element-block" style="width: 100%">
					<label>Keyword:</label>
					<span class="input_text">
						<span class="left_curve"></span>
						<input type="text" id="keyword" name="keyword" maxlength="50"  class="mid" style="width: 373px" />
						<span class="right_curve"></span>
					</span>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block" style="width: 100%">
					<label>Insurance:</label>
					<span class="select-container">
						<select name="insuranceId" id="insuranceId" class="selectbox">
							<option value="">-: Select Insurance :-</option>
							<c:forEach  items="${insuranceList}" var="insurance">
								<option value="${insurance.id}">${insurance.name}</option>
							</c:forEach>
						</select>
					</span>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block" style="width: 100%">
					<label>Provider (Doctor Office):</label>
					<span class="select-container">
						<select name="doctorId" id="doctorId" class="selectbox">
							<option value="">-: Select Doctor:-</option>
							<c:forEach  items="${doctorList}" var="doctor">
								<option value="${doctor.id}">
									${doctor.name}
									<c:if test="${not empty doctor.parent}">
										(${doctor.parent.name})
									</c:if>
								</option>
							</c:forEach>
						</select>
					</span>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block">
					<label>Status Code:</label>
					<span class="select-container1">
						<select id="statusCode" name="statusCode" class="selectbox">
							<option value="">-: Select Status Code :-</option>
							<c:forEach var="sCode" items="${STATUS_CODES}">
								<option value="${sCode.key}">${sCode.value}</option>
							</c:forEach>
						</select>
					</span>
				</div>
				<div class="element-block">
					<label  class="right_lbl">Source:</label>
					<span class="select-container1">
						<select id="source" name="source" class="selectbox">
							<option value="">-: Select Source :-</option>
							<c:forEach var="source" items="${SOURCES}">
								<option value="${fn:replace(source.key, '_',' ')}">${source.value}</option>
							</c:forEach>
						</select>
					</span>
				</div>
				<div class="clr"></div>
			</div>
			
			<div class="row-container">
				<div class="element-block">
					<label>Created Date From:</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input type="text" name="dateCreatedFrom" id="dateCreatedFrom" readonly="readonly"
							maxlength="50" class="mid" style="width: 108px;"/>
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="element-block">
					<label class="right_lbl">To:</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input type="text" name="dateCreatedTo" id="dateCreatedTo" readonly="readonly"
							class="mid" style="width: 108px;"/>
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="clr"></div>
			</div>
			
			<div class="row-container">
				<div class="element-block">
					<label>Created By:</label>
					<div class="select-container1">
						<select class="selectbox" id="createdBy" name="createdBy">
							<option value="">-: Created By :-</option>
							<c:forEach var="user" items="${userList}">
								<option value="${user.id}">${user.firstName}
									${user.lastName}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="element-block">
					<label for="teamId" class="right_lbl">Team:</label>
					<span class="select-container1" style="display:inline;">
						<select name="teamId" class="selectbox">
							<option value="">-: Select Team:-</option>
							<c:forEach var="team" items="${teamList}">
								<option value="${team.id}">
									${team.name}
								</option>
							</c:forEach>
						</select>
					</span>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block">
					<label>Work Flow:</label>
					<span class="select-container1" >
						<select id="workflowId" name="workflowId" class="selectbox">
							<option value="">-: Select Work flow :-</option>
							<option value="1">Adjustment log</option>
							<option value="2">Coding correction</option>
							<option value="4">Re-Key request to charge posting</option>
							<option value="5">Payment posting log</option>
							<option value="7">Query to TL</option>
							<option value="8">Refund Request</option>
							<!-- <option value="9">Stop payment/Reissue</option> -->
							<option value="10">Request for Docs</option>
						</select>
					</span>
				</div>
				<div class="element-block" id="divSubStatus">
					<label for="teamId" class="right_lbl">Sub-Status:</label>
					<span class="select-container1" style="display:inline;">
						<select name="substatus" id="substatus" class="selectbox">
							<option value="">-: Select sub-status:-</option>
							<option value="1">Open</option>
							<option value="2">Back To Team</option>
							<option value="3">Close</option>
						</select>
					</span>
				</div>
				<div class="clr"></div>
			</div>
			
			<div class="row-container">
				<div class="element-block">
					<label>Follow Up Date:</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input type="text" name="followUpDateFrom" id="followUpDateFrom" readonly="readonly"
							maxlength="50" class="mid" style="width: 108px;"/>
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="element-block">
					<label class="right_lbl">To:</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input type="text" name="followUpDateTo" id="followUpDateTo" readonly="readonly"
							class="mid" style="width: 108px;"/>
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="clr"></div>
			</div>
			
			<div class="row-container">
				<div class="element-block" style="width: 100%">
					<label>Follow Up Date Records:</label>
					<input type="checkbox" value="1" id="followups" name="followups" <c:if test="${followups eq '1'}">checked</c:if> />
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block" style="width: 100%">
					<label>&nbsp;</label>
					<input type="submit" title="Search" value="Search"	class="login-btn" />
					<input type="reset" title="Cancel" value="Cancel" class="login-btn" onclick="javascript:resetForm(this.form);"  />
				</div>
				<div class="clr"></div>
			</div>

			<div class="noSearchItem">&nbsp;</div>
			<br />
			<table id="flex1" style="display: none"></table>

		</div>
	</form>

	<%-- <div class="popup2" id="popup_workflowDetails" style="display: none;">
		<span class="button bClose"><span>X</span></span>
		<div class="popup-container" id="divContainer"><img	src="<c:url value="/static/resources/img/ajax-loader.gif"/>" alt="" /></div>
	</div> --%>
	
	<div class="popup2" id="workflow_popup" style="display:none;">
	   <span class="button bClose"><span>X</span></span>
	   <div class="popup-container">
	   		<div class="popup-container-inner" id="modificationSummaryContent">
	   			<h3>Work Flows</h3>
				<span class="button bClose"><span>X</span></span>
				<div class="input-container" >
					<ul id="workflows_container" style="padding: 10px;">
					
					</ul>
				</div>
	   		</div>
	   </div>
	</div>
			
</div>
<script type="text/javascript">
	var moduleName = "arProductivity";
	$("#dateCreatedTo").val('${createdTo}');
	$("#dateCreatedFrom").val('${createdFrom}');
	$("#createdBy").val('${createdBy}');
	
	$("#flex1").flexigrid({
		url : contextPath + '/flows/'+moduleName+'/json',
		preProcess : formatData,
		method : 'GET',

		colModel : [{
			display : 'Action',
			name : 'edit',
			width : 60,
			sortable : false,
			align : 'left'
		},{
			display : 'Patient ID',
			name : 'patientAccNo',
			width : 100,
			sortable : true,
			align : 'left'
		}, {
			display : 'Patient Name',
			name : 'patientName',
			width : 100,
			sortable : true,
			align : 'left'
		}, {
			display : 'DOS',
			name : 'dos',
			width : 60,
			sortable : true,
			align : 'left'
		}, {
			display : 'CPT',
			name : 'cpt',
			width : 40,
			sortable : true,
			align : 'left'
		} , {
			display : 'Doctor',
			name : 'doctor.name',
			width : 100,
			sortable : true,
			align : 'left'
		} ,{
			display : 'Insurance',
			name : 'insurance.name',
			width : 100,
			sortable : true,
			align : 'left'
		} ,{
			display : 'Database',
			name : 'arDatabase.name',
			width : 40,
			sortable : true,
			align : 'left'
		},{
			display : 'Team',
			name : 'team.name',
			width : 40,
			sortable : true,
			align : 'left'
		} ,{
			display : 'Follow Up Date',
			name : 'followUpDate',
			width : 40,
			sortable : true,
			align : 'left'
		}  , {
			display : 'Balance',
			name : 'balanceAmt',
			width : 40,
			sortable : true,
			align : 'center'
		} ,{
			display : 'Status Code',
			name : 'statusCode',
			width : 40,
			sortable : true,
			align : 'left'
		} ,{
			display : 'Source',
			name : 'source',
			width : 40,
			sortable : true,
			align : 'left'
		},{
			display : 'Created By',
			name : 'createdBy',
			width : 40,
			sortable : true,
			align : 'left'
		},{
			display : 'Created On',
			name : 'createdOn',
			width : 40,
			sortable : true,
			align : 'left'
		} ],
		buttons : [ {
			name : 'Add',
			bclass : 'add',
			onpress : action
		} ,{
			name : 'Print',
			bclass : 'print',
			onpress : action
		}],
		searchitems : [ {
			display : 'Name',
			name : 'name',
			isdefault : true
		} ],
		sortname : "id",
		sortorder : "desc",
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

	var view_offset = '<c:url value="/static/resources/img/view_offset.png"/>';
	function formatData(data) {
		var rows = Array();
		var queryIcon = '<c:url value="/static/resources/img/query.png"/>';
		var queryDoneIcon = '<c:url value="/static/resources/img/query_done.png"/>';
		$.each(data.rows,function(i, row) {
				
				var adjustmentLogWorkflow = "";
				var codingCorrectionWorkFlow = "";
				var secondRequestWorkFlow = "";
				var rekeyRequestWorkFlow = "";
				var paymentPostingWorkflow = "";
				var queryToTLWorkFlow = "";
				var refundRequestWorkFLow = "";
				var workFlowIds = row.workFlows.split(",");
				workFlowIds = "\""+workFlowIds+"\"";
				viewWFDetails = "&nbsp;&nbsp;<a href='javascript:showPopup("+workFlowIds+","+row.id+")' title = 'View Work Flows' class='edit'><img src='"+view_offset+"'/>";
				edit = "&nbsp;&nbsp;<a href='"+contextPath+"/flows/"
						+ moduleName
						+ "/add/?id="
						+ row.id
						+ "' class='edit'><img src='"+editIcon+"'/></a>";

				rows.push({
					id : row.id,
					cell : [ edit + viewWFDetails,
								row.patientAccNo, row.patientName, row.dos,
								row.cpt, row.doctor,
							 	row.insurance, row.dataBas, row.team, row.followUpDate, row.balanceAmt,
							 	row.statusCode , row.source,row.createdBy,row.createdOn]
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
		else if (com == 'Print') {
			akpmsPopupWindow(contextPath + "/flows/" + moduleName	+ "/printhtmlreport","AR_Productivity");
		}
	}

	function showPopup(workFlows,prodId) {

		/*
			1: adjustment log
			2: Coding correction log
			3: Second request log
			4: Re-Key request to charge posting
			5: Payment posting log
			6: Request for check tracer
			7: Query to TL
			8: Refund Request
		*/
		
		var workFlowIds = workFlows.split(",");
		var htmlString = "";
		$('#workflow_popup').bPopup({
			onOpen : function() {

			},onClose: function(){
				
			}
       	},function() {
       		var adjustmentLogWorkflow = "";
			var codingCorrectionWorkFlow = "";
			var secondRequestWorkFlow = "";
			var rekeyRequestWorkFlow = "";
			var paymentPostingWorkflow = "";
			var paymentPostingWorkflowList = "";
			var queryToTLWorkFlow = "";
			var refundRequestWorkFLow = "";
			
			if(workFlowIds != ""){
				for (var i=0;i<workFlowIds.length;i++){
					if(workFlowIds[i] == 1){
						adjustmentLogWorkflow = "<li style='padding-top:10px;'><a href='"+contextPath+"/flows/adjustmentlogs/add?arProductivity.id="+prodId+"&single=1' title = 'View Adjustment logs' class='edit'>Adjustment logs</a></li>";
					} else if(workFlowIds[i] == 2){
						codingCorrectionWorkFlow = "<li style='padding-top:10px;'><a href='"+contextPath+"/flows/codingcorrectionlogworkflow/add?arProductivity.id="+prodId+"&single=1' title = 'View Coding Correction logs' class='edit'>Coding Correction logs</a></li>";
					} else if(workFlowIds[i] == 3){
						secondRequestWorkFlow = "<li style='padding-top:10px;'><a href='"+contextPath+"/flows/secondrequestlogworkflow/add?arProductivity.id="+prodId+"&single=1' title = 'View Second Request logs' class='edit'>Second Request logs</a></li>";
					} else if(workFlowIds[i] == 4){
						rekeyRequestWorkFlow = "<li style='padding-top:10px;'><a href='"+contextPath+"/flows/rekeyrequest/add?arProductivity.id="+prodId+"&single=1' title = 'View Rekey Request' class='edit'>View Rekey Request</a></li>";
					} else if(workFlowIds[i] == 5){
						paymentPostingWorkflow = "<li style='padding-top:10px;'><a href='"+contextPath+"/flows/paymentpostingworkflow?arProductivity.id="+prodId+"&single=1' title = 'View Payment Posting' class='edit'>Payment Posting List</a>"
						paymentPostingWorkflowList = "&nbsp;&nbsp;(<a href='"+contextPath+"/flows/paymentpostingworkflow/add?arProductivity.id="+prodId+"&single=1' title = 'Add Payment Posting' class='edit'>Add Payment Posting</a>)</li>";
					} else if(workFlowIds[i] == 7){
						queryToTLWorkFlow = "<li style='padding-top:10px;'><a href='"+contextPath+"/flows/arProductivity/queryToTL/add?arProductivity.id="+prodId+"&single=1' title = 'Query to TL' class='edit'>Query to TL</a></li>";
					} else if(workFlowIds[i] == 8){
						refundRequestWorkFLow = "<li style='padding-top:10px;'><a href='"+contextPath+"/flows/refundrequest/add?arProductivity.id="+prodId+"&single=1' title = 'View Refund Request' class='edit'>Refund Request</a></li>";
					}
				}
				
	       		htmlString =
	           		adjustmentLogWorkflow+codingCorrectionWorkFlow+secondRequestWorkFlow+
	           		rekeyRequestWorkFlow+paymentPostingWorkflow+paymentPostingWorkflowList+
	           		queryToTLWorkFlow+refundRequestWorkFLow;
			}else{
				htmlString = "No Workflow has selected for this productivity.";
			}
       		
      		$("#workflows_container").html(htmlString);
       	});
	}

	$(function() {
		$("#dateCreatedTo").datepicker();
		$("#dateCreatedFrom").datepicker();

		$("#followUpDateTo").datepicker();
		$("#followUpDateFrom").datepicker();
	});

	$("#workflowId").val('${workflowId}');
	$("#substatus").val('${substatus}');
</script>
