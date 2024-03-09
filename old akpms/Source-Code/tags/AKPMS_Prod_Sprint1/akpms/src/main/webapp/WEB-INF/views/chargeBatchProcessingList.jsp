<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
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

	<form class="form-container" id="chargeBatchSearchForm">
		<div class="form-container-inner">
			<h3>Search Charge Batch Processing</h3>

			<div class="row-container">
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
			</div>

		    <div class="row-container">
				<label>Type:</label>
				<c:forEach var="type" items="${reportTypeList}">
					<input type="checkbox" name="reportType" id="chk_${type}" value="${type.value}"/> ${type.value}&nbsp;&nbsp;&nbsp;
				</c:forEach>
				<div class="clr"></div>
			</div>

		    <div class="row-container">
		    	<label>Group/Doctor Name:</label>
		    	<div>
					<select id="doctor" name= "doctor">
						<option value="0">-:Doctor:-</option>
				    	<c:forEach var="doctor" items="${doctorList}">
				    		<option value="${doctor.id}">${doctor.name}</option>
				    	</c:forEach>
				    </select>
				</div>
				<div class="clr"></div>
		    </div>

		    <div class="row-container">
				<div class="element-block" >
					<label>Batch Prepared (From):</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input name="dosFrom" type="text" id="dosFrom" readonly="true" maxlength="50" class="mid" style="width: 90px;" />
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="element-block" >
					<label class="right_lbl" >Batch Prepared (To):</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input name="dosTo" id="dosTo" type="text" readonly="true" maxlength="50" class="mid" style="width: 90px;"  />
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
						<input name="dateReceivedFrom" id="dateReceivedFrom" type="text"  readonly="true" maxlength="50" class="mid" style="width: 90px;" />
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="element-block" >
					<label class="right_lbl">Batch Received (To):</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input name="dateReceivedTo" type="text" id="dateReceivedTo" readonly="true" maxlength="50" class="mid" style="width: 90px;"  />
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<label >Received By:</label>
				<div>
					<select id="recievedBy" name= "recievedBy">
						<option value="0">-:Received By:-</option>
				    	<c:forEach var="receivedByUsers" items="${receivedByUsers}">
				    		<option value="${receivedByUsers.id}">${receivedByUsers.firstName}</option>
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
						<input name="dateBatchPostedFrom"  type="text" id="dateBatchPostedFrom" readonly="true" maxlength="50" class="mid" style="width: 90px;" />
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="element-block" >
					<label class="right_lbl">CT Posted Date (To):</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input name="dateBatchPostedTo" type="text" id="dateBatchPostedTo" readonly="true" maxlength="50" class="mid" style="width: 90px;"  />
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<label name="postedBy">Posted By:</label>
				<div>
					<select id="postedBy" name="postedBy">
						<option value="0">-:Select Posted By:-</option>
						<c:forEach var="postedByUsers" items="${postedByUsers}">
							<option value="${postedByUsers.id}">${postedByUsers.firstName}</option>
						</c:forEach>
					</select>
				</div>
				<div class="clr"></div>
			</div>

			<span class="input-container"> <label>&nbsp;</label> <input
				type="submit" title="Search" value="Search" class="login-btn" />
				<input type="button" title="Cancel" value="Cancel" onclick="javascript:resetForm(this.form);"
				class="login-btn" />
			</span>

			<div class="noSearchItem">&nbsp;</div>

			<table id="flex1" style="display: none"></table>

		</div>
	</form>
</div>

<script type="text/javascript">
	var moduleName = "chargebatchprocess";
	$("#flex1").flexigrid({
		url : contextPath + '/'+moduleName+'/json',
		preProcess : formatData,
		method : 'GET',

		colModel : [ {
			display : 'Ticket #',
			name : 'id',
			width : 40,
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
			name : 'receviedBy',
			width : 80,
			sortable : true,
			align : 'left'
		},{
			display : 'Received Date',
			name : 'dateReceived',
			width : 80,
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
		},{
			display : 'Action',
			name : 'edit',
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
		sortorder : "desc",
		title : 'Charge Batch List',
		showTableToggleBtn : true,
		onSubmit : addFormData,
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

		$('#flex1').flexOptions({
			newp : 1
		}).flexReload();

		return false;
	});

	function formatData(data) {
		var rows = Array();

		$.each(data.rows,function(i, row) {
			var reUpdateIcon = '<c:url value="/static/resources/img/re_update.png"/>';
			var viewIcon = '<c:url value="/static/resources/img/view.png"/>';
			var edit;
			var re_upate_grant = ''+${re_update_grant};
			var receiveIcon = '<c:url value="/static/resources/img/batch_receive.png"/>';
			if((row.receivedBy != null && $.trim(row.receivedBy) != '')&& (row.postedBy == null || $.trim(row.postedBy) == ''))
			{
				edit = "&nbsp;&nbsp;<a href='"+contextPath + '/'
						+ moduleName
						+ "/add/?id="
						+ row.id
						+ "' class='edit'><img src='"+editIcon+"' title='Edit' alt='Edit'/></a>";
			}
			else if((row.receivedBy == null || $.trim(row.receivedBy) == '')|| (row.postedBy == null || $.trim(row.postedBy) == ''))
			{
				edit = "&nbsp;&nbsp;<a href='"+contextPath + '/'
						+ moduleName
						+ "/add/?id="
						+ row.id
						+ "' class='edit'><img src='"+receiveIcon+"' title='Receive' alt='Receive'/></a>";
			}else if((row.receivedBy != null && $.trim(row.receivedBy) != '')&& re_upate_grant == 'true')
			{
				edit = "&nbsp;&nbsp;<a href='"+contextPath + '/'
				+ moduleName
				+ "/add/?id="
				+ row.id
				+ "' class='edit'><img src='"+reUpdateIcon+"' title='Re-update' alt='Re-update'/></a>";
			}else{
				edit = "&nbsp;&nbsp;<a href='"+contextPath + '/'
				+ moduleName
				+ "/add/?id="
				+ row.id
				+ "' class='edit'><img src='"+viewIcon+"' title='View' alt='View'/></a>";
			}
			billDoctor = row.numberOfSuperbills;
			billArgus = row.numberOfSuperbillsArgus;

			attachementsDoctor = row.numberOfAttachments;
			attachementsArgus = row.numberOfAttachmentsArgus;

			rows.push({
				id : row.id,
				cell : [row.id,
						row.type,
						row.doctorName,
						row.dosFrom,
						row.dosTo,
						billDoctor,billArgus,
						attachementsDoctor,attachementsArgus,
						row.receivedBy,
						row.dateReceived,
						row.postedBy,
						row.dateBatchPosted,
						edit ]
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
			window.location.href = contextPath + '/' + moduleName	+ "/add";
		}
	}
</script>