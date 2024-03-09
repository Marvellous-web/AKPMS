<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- <c:if test="${ not empty CHILDREN_QC_POINTS}">
	<nav class="navbar navbar-default navbar-fixed-bottom" role="navigation" >
		 <div class="container" style="background-color: #C7CFD6; width: 100%; padding:0 7px;">
			<table class="table" style="margin: 0px;">
				<thead id="legend-thead">
					<tr style="background-color: #2465A7; color: white">
						<th style="font-size: 16px;">
							<span>QC Points</span>&nbsp;&nbsp;&nbsp;
							<span id="msgSuccess" style="color:white;">&nbsp;</span>
							<span id="msgDelete" style="color:red;">&nbsp;</span>
						</th>
						<th align="right" style="padding-right:0px; font-size: 16px;"  colspan="4">
							<span style="color:white; float:right; padding-right:10px" id="spnQCPoint"></span>
						</th>
					</tr>
				</thead>
				<tbody id="legend-tbody">
					<tr>
						<c:forEach items="${CHILDREN_QC_POINTS}" var="CHILD_QC_POINT" varStatus="loop">
							<c:if test="${loop.index mod 5 == 0}">
								</tr><tr>
							</c:if>
							<td id="lgd_${CHILD_QC_POINT.id}">
								<strong>${loop.index+1}.</strong> ${CHILD_QC_POINT.name}
							</td>
						</c:forEach>
					</tr>
				</tbody>
			</table>
		</div>
	</nav>

	<script type="text/javascript">
	$(document).on('click','thead',function(){
	    $('#legend-tbody').toggle("slow");
	 });
	$('#legend-tbody').hide();
	</script>
</c:if> --%>

<span id="msgSuccess" style="color:white;">&nbsp;</span>
<span id="msgDelete" style="color:red;">&nbsp;</span>

<!-- Modal -->
<div class="modal fade" id="qcPointPopup" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
  	<form action="get" id='frmQCPoint'>
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
	        <h4 class="modal-title" id="myModalLabel">QC Points</h4>
	      </div>
	      <div class="modal-body">
	      	<input type="hidden" value="" name="sampleId" id="popupSampleId">
	      	<input type="hidden" value="" name="userId" id="popupUserId"> 
	      	<input type="hidden" value="" name="qaPatientInfoId" id="popupQaPatientInfoId">
	      	
	       	<ul class="list-group">
	       		<c:set var="parentQCPoint" value="" />
				<c:forEach items="${CHILDREN_QC_POINTS}" var="CHILD_QC_POINT" varStatus="loop">
					
					<c:if test="${PARENT_QC_POINTS_MAP[CHILD_QC_POINT.parent.id] ne parentQCPoint}">
						<li class="list-group-item" style="background-color:silver">
							<label style="font-size:11pt">${PARENT_QC_POINTS_MAP[CHILD_QC_POINT.parent.id]}</label>
						</li>
						
						<c:set var="parentQCPoint" value="${PARENT_QC_POINTS_MAP[CHILD_QC_POINT.parent.id]}" />
					</c:if>
				
					<li class="list-group-item">
						<input type="checkbox" class='chkQcpoint' value="${CHILD_QC_POINT.id}" name='qcpoint' id="chk_qcpoint_${CHILD_QC_POINT.id}" > &nbsp; 
						<label for="chk_qcpoint_${CHILD_QC_POINT.id}"> 
							${CHILD_QC_POINT.name} 
						</label>						
					</li>
				</c:forEach>
				
			</ul>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default btn-cancel" data-dismiss="modal">Close</button>
	        <button type="button" id="btnSaveQcPoints" class="btn btn-primary" onclick="javascript:insertQCPoints();">Save changes</button>
	        <div id="results"></div>
	      </div>
	    </div>
    </form>
  </div>
</div>

<script type="text/javascript">
	var moduleName = "qaworksheetlayout";

	if ( $( "#orderby" ).length ) {
		$( "#orderby" ).val('${ORDER_BY}');
	}

	function disableElements(){
		$(":input").attr("disabled","disabled");
		$(":button").css("display","none");

		$(".qcPopAccBtn").attr("disabled",false);
		$(".qcPopAccBtn").css("display","block");
		
		$(".close").attr("disabled",false);
		$(".close").css("display","block");
		
		$(".btn-cancel").attr("disabled",false);
		$(".btn-cancel").css("display","block");

		/*setting elements*/
		$("#orderby").attr("disabled",false);
		$("#btnOrder").attr("disabled",false);
		$("#btnOrder").css("display","block");

		if ( $( "#chkShowAll" ).length ) {
			$("#chkShowAll").attr("disabled",false);
		}

		if ( $( "#tdComplete" ).length ) {
			$("#tdComplete").html("Worksheet has been set as Completed.");
		}
	} 

	function insertQCPoints(){
		var action = "insertqcpoints";
		
		var params = $('#frmQCPoint').serializeArray();
		var param = [];

		var qcPointIds = [];
		
		$.each(params, function(i, field){
			if(field.name == 'qcpoint'){
				qcPointIds.push(field.value);
			}
		});

		var sampleId = $("#popupSampleId").val();
		var userId = $("#popupUserId").val();
		var qaPatientInfoId = $("#popupQaPatientInfoId").val();
		
		$.ajax({
			type : "POST",
			dataType : "json",
			url : contextPath + "/" + moduleName + "/" + action,
			data : {
				'sampleid' : sampleId,
				'qaPatientInfoId' : qaPatientInfoId,
				'qcPointIds' : qcPointIds.toString(),
				'userid': userId
			},
			success : function() {
				//alert('success: ' + qaPatientInfoId);
				$("#qcPointPopup").modal('hide');
				$("#msgSuccess").html("Saved..");
				$("#msgSuccess").show().delay(1000).fadeOut();

				if(qaPatientInfoId > 0){
					var multiple_by = 1;//$("#multiple_by_"+sampleId+"_"+qaPatientInfoId).text();
					$("#error_count_"+sampleId+"_"+qaPatientInfoId).html(qcPointIds.length * multiple_by);
				}else{
					var multiple_by = $("#multiple_by_"+sampleId).text();
					if(isNaN(multiple_by)) multiple_by = 1;
					$("#error_count_"+sampleId).html(qcPointIds.length * multiple_by);
				}
				//countErrors (chk);
			},
			error : function(error) {
				//alert('error: ' + eval(error));
			}
		});

	}
</script>

<c:if test="${QA_WORKSHEET.status eq '2' || mode eq 'search'}">
	<script type="text/javascript">
		disableElements();
	</script>
</c:if>
<script type="text/javascript">
<!--
//var QC_POINTS = ${CHILDREN_QC_POINTS};
//-->
</script>
<script type="text/javascript" src="<c:url value='/static/resources/js/qa_worksheet_layout.js'/>"></script>

<script type="text/javascript">
<!--
/* not in use */
function highlightNext(element, mode, id) {
	return false;
	/* not in use */
	var color;
	if(mode == 'over'){
		color = "blue";
	}else{
		color = "white";
	}
	
    var next = element;
    do { // find next td node
        next = next.nextSibling;
    }
    while (next && !('nodeName' in next && next.nodeName === 'TD'));
    if (next) {
        next.style.color = color;
        //$("#lgd_"+id).style.color = color;
        //$("#lgd_"+id).css('background-color',"#C7CFD6");
    }
} 

function highlightBG(element, mode, id) {
	if(mode == 'over'){
		var backgroundColor = "#2465A7";
		var textColor = "white";
		
		element.style.backgroundColor = backgroundColor;
		element.style.color = textColor;
		
	    $("#lgd_"+id).css('background-color',backgroundColor);
	    $("#lgd_"+id).css('color',textColor);

	    $("#spnQCPoint").html( $("#lgd_"+id).html());
	}else{
		var textColor = "black";
		
		element.style.backgroundColor = "white";
		element.style.color = textColor;
		
	    $("#lgd_"+id).css('background-color',"#C7CFD6");
	    $("#lgd_"+id).css('color',textColor);

	    $("#spnQCPoint").html("");
	}
}

function ajaxSetAsCompleted(){
	var qaworksheetId = '${QA_WORKSHEET.id}';
	send_changeStatus(qaworksheetId, 2);	
}

function send_changeStatus(id, status) {
	var confirmation = "Are you sure you wish to 'Set as Completed' this QA worksheet?";

	bootbox.confirm(confirmation , function(result) {
		if(!result){
			//return false;
		}else{
			$.ajax({
				type : "GET",
				dataType : "json",
				url : contextPath + "/qamanager/change_status",
				data : "id=" + id + "&status=" + status,
				success : function(data) {
					reloadParentFlexigrid();
					disableElements();
				},
				error: function(data){
					alert (data);
				}
			});
		}		
	});	
}

function reloadParentFlexigrid() {
	if (window.opener && !window.opener.closed) {
		if( window.opener.reloadFlexigrid ) {
			window.opener.reloadFlexigrid();
		}
    }
}

/*
window.onload = reloadParentFlexigrid;

$('input.form-control').focus(function () {
    $(this).animate({ width: "200px" }, 500); 
});

$('input.form-control').blur(function () {
    $(this).animate({ width: "80px" }, 500); 
});
*/
//-->
</script>
