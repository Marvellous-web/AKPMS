<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li>&nbsp;Offset Work Flow Report</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<div class="msg">
		<p class="success" id="msgP">${success}</p>
	</div>
	<form class="form-container" id="offsetListForm">
		<div class="form-container-inner">
			<h3>Offset Work Flow Report </h3>


			<div class="row-container">
				<label>Ticket No:</label>

				<div class="input_text">
					<span class="left_curve"></span>
					<input type = "text" id="ticketNumber" name="ticketNumber" maxlength="15" class="mid integerOnly" style="width: 373px;" />
					<span class="right_curve"></span>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block" >
					<label>Posted From:</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input type = "text"  id="datePostedFrom" name="datePostedFrom" maxlength="50" class="mid" style="width: 100px;" readonly/>
						<span class="right_curve"></span>
					</div>
				</div>

				<div class="element-block" >
					<label class="right_lbl">To:</label>
					<div class="input_text">
						<span class="left_curve"></span>
						<input type = "text" id="datePostedTo" name="datePostedTo" maxlength="50" class="mid" style="width: 100px;" readonly />
						<span class="right_curve"></span>
					</div>
				</div>
				<div class="clr"></div>

			</div>

			<div class="row-container" >
				<label>Posted By:</label>
				<div>
					<select id="postedById" name="postedById" class="selectbox">
						<option value="">-:Select Posted by:-</option>
						<c:forEach var="type" items="${postedBy}">
						<option value="${type.id}">${type.firstName}</option>
						</c:forEach>
					</select>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<label>&nbsp;</label>
				<input type="submit" title="Create Report" value="Create Report" class="login-btn" />
				<input type="button" title="Cancel" value="Cancel" onclick="javascript:resetForm(this.form);"	class="login-btn" />
			</div>

			<div class="noSearchItem">&nbsp;</div>
			<table id="flex1" style="display: none"></table>
		</div>
	</form>
		<div class="popup2" id="popup_showRecord" style="display: none;">
		<span class="button bClose"><span>X</span></span>
		<div class="popup-container" id="divContainer"></div>
	</div>
</div>

<script type="text/javascript">
var contextPath = "<c:out value="${pageContext.request.contextPath}" />";
	var moduleName = "paymentproderaoffset";
	$("#flex1").flexigrid({
		url : contextPath + '/'+moduleName+'/json',
		preProcess : formatData,
		method : 'POST',

		colModel : [ {
			display : 'Ticket No.',
			name : 'batchId',
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			display : 'Patient Name',
			name : 'patientName',
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			display : 'Account',
			name : 'accountNumber',
			width : 80,
			sortable : true,
			align : 'left'
		},{
			display : 'Check Number',
			name : 'chkNumber',
			width : 80,
			sortable : true,
			align : 'left'
		},{
			display : 'Date of Check',
			name : 'chkDate',
			width : 80,
			sortable : true,
			align : 'left'
		},{
			display : 'Posted Date',
			name : 'createdOn',
			width : 80,
			sortable : true,
			align : 'left'
		},{
			display : 'Posted By',
			name : 'createdBy',
			width : 80,
			sortable : true,
			align : 'left'
		},{
			display : 'Remarks',
			name : 'remark',
			width : 200,
			sortable : true,
			align : 'left'
		}, {
			display : 'Total Posted Amount',
			name : 'totalAmount',
			width : 120,
			sortable : false,
			align : 'left'
		},{
			display : 'Total Posted Detail',
			name : 'recordlist1',
			width : 400,
			sortable : false,
			align : 'left'
		}],

		sortname : "id",
		sortorder : "asc",
		title : 'Offset Work Flow Report',
		showTableToggleBtn : true,
		onSubmit : addFormData,
		width : 100,
		height : 300
	});
	//This function adds paramaters to the post of flexigrid. You can add a verification as well by return to false
	// if you don't want flexigrid to submit
	function addFormData() {
		//passing a form object to serializeArray will get the valid data from all the objects, but,
		//if the you pass a non-form object, you have to specify the input elements that the data will come from
		var dt = $('#offsetListForm').serializeArray();
		$("#flex1").flexOptions({
			params : dt
		});
		return true;
	}

	$('#offsetListForm').submit(function() {
		if(null != $('#datePostedFrom').val() && null != $('#datePostedTo').val()){
			if(Date.parse($('#datePostedFrom').val()) > Date.parse($('#datePostedTo').val()) ){
				alert("To date must be after or equal to From date. Please try again.");
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
        var view ="";
        var postOffset="";
        var recordlist1 = "";

		$.each(data.rows,function(i, row) {

			edit = "&nbsp;&nbsp;<a href='"
				+ contextPath+"/"
				+ moduleName
				+ "/add?id="
				+ row.id
				+ "' class='edit'><img src='"+editIcon+"'/></a>";


				view = "&nbsp;&nbsp;<a href='javascript:void(0);' onclick='javascript:my_function("+row.id+");' class='edit'><img src='"+editIcon+"'/></a>";

				if(row.operation == 'ADD'){

				postOffset = "&nbsp;&nbsp;<a href='"
					+ contextPath+"/"
					+ "paymentoffsetmanager"
					+ "/add?offset.id="
					+ row.id
					+ "&batchId="+row.ticketNumber+"' class='edit'><img src='"+editIcon+"'/></a>";
				}else{

					postOffset = "&nbsp;&nbsp;<a href='"
						+ contextPath+"/"
						+ "paymentoffsetmanager"
						+ "/add?id="+row.offsetPostId+"&offset.id="
						+ row.id
						+ "&batchId="+row.ticketNumber+"' class='edit'><img src='"+editIcon+"'/></a>";

					}

				var record = row.recordList;
				recordlist1 = "<table border=1> <tr> <th>CPT</th> <th>DOS</th> <th>AMOUNT</th> </tr>";
				for(var i=0;i<record.length;i++){
					recordlist1 += "<tr>";
					recordlist1 += "<td>"+record[i].cpt+"</td>";
					recordlist1 += "<td>"+record[i].dos+"</td>";
					recordlist1 += "<td>"+record[i].amount+"</td>";
					recordlist1 += "</tr>";

				}
				recordlist1 += "</table>";

            rows.push({
					id : row.id,
					cell : [ row.batchId,row.patientName,row.accountNumber,row.chkNumber, row.chkDate,row.createdOn,row.postedBy,row.remark,row.totalAmount,recordlist1]
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

	$(function() {
	    $( "#datePostedFrom" ).datepicker();
	});

	$(function() {
	    $( "#datePostedTo" ).datepicker();
	});


	function my_function(offsetId) {
       // Prevents the default action to be triggered.
       //e.preventDefault();
       // Triggering bPopup when click event is fired

       htmlString = "<div class='table-grid' style='overflow-x: auto;''>"+
           "<table border='1' width='100%'><tr>"+
			"<thead><th class='heading'>CPT</th>"+
			"<th class='heading'>DOS</th>"+
			"<th class='heading'>Amount</th></thead>"+
			"</tr>";

       			$.ajax({
					type : "GET",
					dataType : "json",
					url : moduleName+"/getRecord",
					data : "id=" + offsetId ,
					success : function(data) {
						for(var i=0;i<data.length;i++){
							htmlString += "<tr>";
							htmlString += "<td>"+data[i].cpt+"</td>";
							htmlString += "<td>"+data[i].dos+"</td>";
							htmlString += "<td>"+data[i].amount+"</td>";
							htmlString += "</tr>";
						}
						htmlString += "</table></div>";
					},
					error: function(data){
						alert (data);
					}
				});

       			setTimeout('ShowPopUp(htmlString)',700);


   }

	function ShowPopUp(htmlString) {
	 	$('#popup_showRecord').bPopup({
           onOpen: function() {
        	   $("#divContainer").html(htmlString);
           }
       });
	}

</script>
<script type="text/javascript"
		src="<c:url value='/static/resources/js/common.js'/>"></script>
