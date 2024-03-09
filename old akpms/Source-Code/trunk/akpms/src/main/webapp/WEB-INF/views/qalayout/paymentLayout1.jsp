<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<table class="table" id="my-table" style="margin-bottom:270px;">
	<thead>
		<c:if test="${not empty PARENT_QC_POINTS}">
			<tr>
				<th colspan="4">&nbsp;</th>
				<c:forEach var="PARENT_QC_POINT" items="${PARENT_QC_POINTS}">
					<th colspan="${PARENT_QC_POINT.childCount}">${PARENT_QC_POINT.name}</th>
				</c:forEach>
			</tr>
		</c:if>
		<tr style="background-color: #999999; color: white">
			<th style="width:60px;">Posting<br /> Date</th>
			<th style="width:175px;">Operator<br />Name</th>
			<th style="width:80px;">Batch<br />Number</th>
			<th style="width:150px;">Doctor<br />Name</th>
			
			<c:forEach items="${CHILDREN_QC_POINTS}" var="CHILD_QC_POINT" varStatus="loop">
				<th class="chktd">
					<span class="btn btn-link" data-toggle="tooltip" style="padding: 0px;" data-placement="top"	title="${CHILD_QC_POINT.name}"> ${loop.index+1} </span>
				</th>
			</c:forEach>
			<%-- <th><img src='<c:url value='/static/resources/img/delete_icon.png'/>' alt='Delete' title='Delete' /></th> --%>
		</tr>
	</thead>

	<tbody>
		<c:set var="preErrCount" value="1" />
		<c:set var="forUncheck" value="0" />
		<c:forEach items="${QA_PROD_SAMPLE_DATA_LIST}" var="QA_PROD_SAMPLE_DATA" varStatus="loop">
			<tr id="sample_${QA_PROD_SAMPLE_DATA.id}_${QA_PROD_SAMPLE_DATA.paymentProductivity.createdBy.id}" style="background-color: #D6D6D6;" >
				<td>
					<fmt:formatDate type="date"	value="${QA_PROD_SAMPLE_DATA.paymentProductivity.paymentBatch.datePosted}" />
				</td>
				<td>
					${QA_PROD_SAMPLE_DATA.paymentProductivity.createdBy.firstName}
					${QA_PROD_SAMPLE_DATA.paymentProductivity.createdBy.lastName}
					<!-- <br/><small>Patient Name</small> -->
				</td>
				<td>
					${QA_PROD_SAMPLE_DATA.paymentProductivity.paymentBatch.id}
					<!-- <br/><small>Acc Number</small>	 -->
				</td>
				<td>
					${QA_PROD_SAMPLE_DATA.paymentProductivity.paymentBatch.doctor.name}
					<!-- <br/><small>#Tran</small> -->
				</td>				
				
				<td colspan="${fn:length(CHILDREN_QC_POINTS)}" class="buttons">
					<a href="javascript:void(0);" class="addAccBtn" id="${QA_PROD_SAMPLE_DATA.id}"> <img alt="Add" border="0" src="<c:url value='/static/resources/img/flexigrid/add.png'/>"> Add Account for Batch</a>
					&nbsp;&nbsp;&nbsp;					
					<a href="javascript:void(0);" class="deleteAccBtn" id="${QA_PROD_SAMPLE_DATA.id}"> <img alt="Delete" border="0" src="<c:url value='/static/resources/img/delete_icon.png'/>"> Delete Record (With its Patient Accounts)</a>
				</td>
			</tr>
			<c:forEach items="${QA_PROD_SAMPLE_DATA.qaWorksheetPatientInfos}" var="PATIENT_INFO" varStatus="patientLoop">
					<tr id="patientInfo_${PATIENT_INFO.id}">
						<td>
							<span class="buttons"><a href='javascript:void(0)' onClick='send_delete(${PATIENT_INFO.id})' class='delete'><img src='<c:url value='/static/resources/img/delete_icon.png'/>' alt='Delete' title='Delete' /></a> <br/></span>
							<span id="error_count_${PATIENT_INFO.id}">Errors: ${fn:length(PATIENT_INFO.qcPointChecklist)}</span>
						</td>
						<td>
							<input class="form-control input-sm" type="text" title="Patient Name" style="width:auto" size="10" onblur='javascript:lostFocusSave(this);' placeholder="Patient Name"  maxlength='200' value="${PATIENT_INFO.patientName}"  id='patientname_${QA_PROD_SAMPLE_DATA.id}_${PATIENT_INFO.id}'>
							<br/>
							<input class="form-control input-sm" type="text" title="Acc Number" style="width:auto" size="10" onblur='javascript:lostFocusSave(this);' placeholder="Acc Number" maxlength='20' value="${PATIENT_INFO.accountNumber}" id='accnumber_${QA_PROD_SAMPLE_DATA.id}_${PATIENT_INFO.id}'>
						</td>
						<td>
							<input class="form-control input-sm" type="text" title="#Tran" style="width:auto" size="3" onblur='javascript:lostFocusSave(this);' placeholder="#Tran" maxlength='10' value="${PATIENT_INFO.transaction}" id='trans_${QA_PROD_SAMPLE_DATA.id}_${PATIENT_INFO.id}' />
							<br/>
							<input class="form-control input-sm" type="text" title="Sr. No" style="width:auto" size="3" onblur='javascript:lostFocusSave(this);' placeholder="Sr." maxlength='10' value="${PATIENT_INFO.srNo}" id='srno_${QA_PROD_SAMPLE_DATA.id}_${PATIENT_INFO.id}' />
						</td>
						
						<td>
							<textarea rows='2' class="form-control input-sm" placeholder='Remarks' title="Remarks" onblur='javascript:lostFocusSave(this);'  style="width: auto" id='remark_${QA_PROD_SAMPLE_DATA.id}_${PATIENT_INFO.id}'>${PATIENT_INFO.remarks}</textarea>
						</td>
						
						<c:forEach items="${CHILDREN_QC_POINTS}" var="CHILD_QC_POINT" varStatus="loop">
							<td class="chktd" onMouseOver="highlightBG(this, 'over', ${CHILD_QC_POINT.id});highlightNext(this, 'over', ${CHILD_QC_POINT.id})"  onMouseOut="highlightBG(this, 'out', ${CHILD_QC_POINT.id}); highlightNext(this, 'out', ${CHILD_QC_POINT.id})" >
								${loop.index+1}
								<c:choose>
								
									<c:when test="${fn:length(PATIENT_INFO.qcPointChecklist) > 0}">
									
										<c:set var="contains" value="false" />
										<c:forEach items="${PATIENT_INFO.qcPointChecklist}" var="QC_POINT_CHECKLIST">
											<c:if test="${QC_POINT_CHECKLIST.qcPoint.id eq CHILD_QC_POINT.id}">
												<c:set var="contains" value="true" />
												<c:set var="preErrCount" value="${preErrCount+1}" />
											</c:if>
										</c:forEach>
										<c:set var="forUncheck" value="${preErrCount}" />
										<c:choose>
											<c:when test="${contains}">
												<input type="checkbox" id="qcpoint_${CHILD_QC_POINT.id}_${QA_PROD_SAMPLE_DATA.id}_${PATIENT_INFO.id}_${QA_PROD_SAMPLE_DATA.paymentProductivity.createdBy.id}" onclick="javascript:insertQCPoint(this);" checked="checked" >
											</c:when>
											<c:otherwise>
												<input type="checkbox" id="qcpoint_${CHILD_QC_POINT.id}_${QA_PROD_SAMPLE_DATA.id}_${PATIENT_INFO.id}_${QA_PROD_SAMPLE_DATA.paymentProductivity.createdBy.id}" onclick="javascript:insertQCPoint(this);">
											</c:otherwise>
										</c:choose>
																												
									</c:when>
									<c:otherwise>
										<input type="checkbox" id="qcpoint_${CHILD_QC_POINT.id}_${QA_PROD_SAMPLE_DATA.id}_${PATIENT_INFO.id}_${QA_PROD_SAMPLE_DATA.paymentProductivity.createdBy.id}" onclick="javascript:insertQCPoint(this);" >
									</c:otherwise>
								</c:choose>
							</td>
						</c:forEach>
						
					</tr>
										
			</c:forEach>
		</c:forEach>
		
	</tbody>

	<tfoot>
		<tr style="background-color: #DBDBDB">
			<td colspan="4"><span id="totalErrorsMsg" style="display: inline"></span></td>
			<td colspan="${fn:length(CHILDREN_QC_POINTS)}" align="right" id="tdComplete">
				<c:if test="${QA_WORKSHEET.status ne '2' }">
					Once Worksheet will "Set as Completed". User can't make modification in it. Are you sure to 
					<input type="button" class="btn btn-primary btn-lg" value="Set as Completed?" onclick="javascript:ajaxSetAsCompleted();">
				</c:if>
			</td>
		</tr>		
	</tfoot>
</table>

<script type="text/javascript">
<!--
function ajaxInsertBlankPatientInfo(sampleId, userId) {
	$.ajax({
		type : "POST",
		dataType : "json",
		url : contextPath + "/" + moduleName + "/savepatientinfo",
		data : {
			'sampleid' : sampleId
		},
		success : function(qaPatientInfoId) {

			var strHTML = "<tr id='patientInfo_"+qaPatientInfoId+"'>";
			strHTML += "<td>"
				+ "<span class='buttons'><a href='javascript:void(0)' onClick='send_delete("+ qaPatientInfoId+ ")' class='delete'><img src='"+deleteIcon+"' alt='Delete' title='Delete' /> </a><br/></span>"
				+ "<span id='error_count_" + qaPatientInfoId + "' >Errors: 0</span>"
				+ "</td>";

			strHTML += "<td><input type='text' title='Patient Name' style='width:auto' size='10' maxlength='200' id='patientname_"+ sampleId+ "_" + qaPatientInfoId + "' value='' class='form-control input-sm' placeholder='Patient Name' onblur='javascript:lostFocusSave(this);'>";
			strHTML += "<br/><input type='text' title='Acc Number' style='width:auto' size='10' maxlength='20' id='accnumber_"+ sampleId+ "_"+ qaPatientInfoId+ "' value='' class='form-control input-sm' placeholder='Acc Number' onblur='javascript:lostFocusSave(this);'></td>";
			strHTML += "<td><input type='text' title='#Tran' style='width:auto' size='3' maxlength='10' id='trans_"+ sampleId+ "_"+ qaPatientInfoId+ "' value='' class='form-control input-sm' placeholder='#Tran' onblur='javascript:lostFocusSave(this);'>";
			strHTML += "<br/><input type='text' title='Sr. No' style='width:auto' size='3' maxlength='10' id='srno_"+ sampleId+ "_"+ qaPatientInfoId+ "' value='' class='form-control input-sm' placeholder='Sr No' onblur='javascript:lostFocusSave(this);'></td>";
			strHTML += "<td><textarea rows='2' placeholder='Remarks' title='Remarks' style='width: auto' id='remark_"+ sampleId+ "_" + qaPatientInfoId + "' class='form-control input-sm' onblur='javascript:lostFocusSave(this);'></textarea></td>";

			strHTML += renderCheckboxes(sampleId, qaPatientInfoId, userId);

			strHTML += "</tr>";

			$("#sample_" + sampleId+"_"+userId).after(strHTML);
		},
		error : function(error) {
			//alert('error; ' + eval(error));
		}
	});
}

function lostFocusSave(element) {
	var elementId = element.id;
	var elementInfo = elementId.split("_");

	var sampleId = elementInfo[1];
	var id = elementInfo[2]; //patientInfoId

	var patientName = $(
			"#patientname_" + elementInfo[1] + "_" + elementInfo[2]).val();
	var accNumber = $("#accnumber_" + elementInfo[1] + "_" + elementInfo[2])
			.val();
	var trans = $("#trans_" + elementInfo[1] + "_" + elementInfo[2]).val();
	var srno = $("#srno_" + elementInfo[1] + "_" + elementInfo[2]).val();
	var remark = $("#remark_" + elementInfo[1] + "_" + elementInfo[2]).val();

	$.ajax({
		type : "POST",
		dataType : "json",
		url : contextPath + "/" + moduleName + "/savepatientinfo",
		data : {
			'sampleid' : sampleId,
			'id' : id,
			'patientName' : patientName,
			'accountNumber' : accNumber,
			'transaction' : trans,
			'srNo': srno,
			'remark' : remark
		},
		success : function(qaPatientInfoId) {
			//alert('success: ' + qaPatientInfoId);
			$("#msgSuccess").html("Saved..");
			$("#msgSuccess").show().delay(1000).fadeOut();
		},
		error : function(error) {
			//alert('error: ' + eval(error));
		}
	});
}
//-->
</script>