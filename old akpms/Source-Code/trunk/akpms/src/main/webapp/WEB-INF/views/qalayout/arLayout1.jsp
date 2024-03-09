<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<table class="table" style="margin-bottom:270px;">
	<thead>
		<c:if test="${not empty PARENT_QC_POINTS}">
			<tr>
				<th colspan="7">&nbsp;</th>
				<c:forEach var="PARENT_QC_POINT" items="${PARENT_QC_POINTS}">
					<th colspan="${PARENT_QC_POINT.childCount}">${PARENT_QC_POINT.name}</th>
				</c:forEach>
				<th>&nbsp;</th>
			</tr>
		</c:if>
		<tr style="background-color: #DBDBDB">
			<th>Posted <br/> Date</th>
			<th>QA <br/> Date</th>
			<th>DE <br/> Person</th>
			<th>DOS</th>
			<th>Physician <br/> Group</th>
			<th>PT Acct#</th>
			<th>CPT <br/> Codes</th>
			<c:forEach items="${CHILDREN_QC_POINTS}" var="CHILD_QC_POINT" varStatus="loop">
				<th>
					<button type="button" class="btn btn-link" data-toggle="tooltip" style="padding:0px;" data-placement="top" title="${CHILD_QC_POINT.name}">${loop.index+1}</button>
				</th>
			</c:forEach>
			<!-- <th>Remarks</th> -->
			<th><img src='<c:url value='/static/resources/img/delete_icon.png'/>' alt='Delete' title='Delete' /></th>
		</tr>
	</thead>
		
	<tbody>
		<c:set var="preErrCount" value="1" />
		<c:set var="forUncheck" value="0" />
		<c:forEach items="${QA_PROD_SAMPLE_DATA_LIST}" var="QA_PROD_SAMPLE_DATA" >
			<tr id="sample_${QA_PROD_SAMPLE_DATA.id}_${QA_PROD_SAMPLE_DATA.arProductivity.createdBy.id}" style="background-color: #f9f9f9;" >
				<td> <fmt:formatDate type="date" value="${QA_PROD_SAMPLE_DATA.arProductivity.createdOn}"/> </td>
				<td> <fmt:formatDate type="date" value="${QA_PROD_SAMPLE_DATA.createdOn}"/> </td>
				<td> 
					${QA_PROD_SAMPLE_DATA.arProductivity.createdBy.firstName} 
					${QA_PROD_SAMPLE_DATA.arProductivity.createdBy.lastName}
				</td>
				<td> ${QA_PROD_SAMPLE_DATA.arProductivity.dos} </td>
				<td> ${QA_PROD_SAMPLE_DATA.arProductivity.doctor.name} </td>
				<td> ${QA_PROD_SAMPLE_DATA.arProductivity.patientAccNo} </td>
				<td> ${QA_PROD_SAMPLE_DATA.arProductivity.cpt} </td>
				
				<!-- </tr>
				<tr>
				<td colspan="7">&nbsp;</td> -->
				<c:if test="${not empty CHILDREN_QC_POINTS}">
					<c:forEach var="CHILD_QC_POINT" items="${CHILDREN_QC_POINTS}" varStatus="loop">
						<td class="chktd" style="background-color: white" onMouseOver="highlightBG(this, 'over', ${CHILD_QC_POINT.id});highlightNext(this, 'over', ${CHILD_QC_POINT.id})"  onMouseOut="highlightBG(this, 'out', ${CHILD_QC_POINT.id}); highlightNext(this, 'out', ${CHILD_QC_POINT.id})">
							${loop.index+1}
						
							<%-- <input type="checkbox" id="arqcpoint_${CHILD_QC_POINT.id}_${QA_PROD_SAMPLE_DATA.id}_${QA_PROD_SAMPLE_DATA.arProductivity.createdBy.id}" onclick="javascript:insertQCPoint(this);" > --%>
							<c:choose>
								<c:when test="${fn:length(QA_PROD_SAMPLE_DATA.qcPointChecklists) > 0}">
								
									<c:set var="contains" value="false" />
									<c:forEach items="${QA_PROD_SAMPLE_DATA.qcPointChecklists}" var="QC_POINT_CHECKLIST">
										<c:if test="${QC_POINT_CHECKLIST.qcPoint.id eq CHILD_QC_POINT.id}">
											<c:set var="contains" value="true" />
											<c:set var="preErrCount" value="${preErrCount+1}" />
										</c:if>
									</c:forEach>
									<c:set var="forUncheck" value="${preErrCount}" />
									<c:choose>
										<c:when test="${contains}">
											<input type="checkbox" id="arqcpoint_${CHILD_QC_POINT.id}_${QA_PROD_SAMPLE_DATA.id}_${QA_PROD_SAMPLE_DATA.arProductivity.createdBy.id}" onclick="javascript:insertQCPoint(this);" checked="checked" >
										</c:when>
										<c:otherwise>
											<input type="checkbox" id="arqcpoint_${CHILD_QC_POINT.id}_${QA_PROD_SAMPLE_DATA.id}_${QA_PROD_SAMPLE_DATA.arProductivity.createdBy.id}" onclick="javascript:insertQCPoint(this);" >
										</c:otherwise>
									</c:choose>
																											
								</c:when>
								<c:otherwise>
									<input type="checkbox" id="arqcpoint_${CHILD_QC_POINT.id}_${QA_PROD_SAMPLE_DATA.id}_${QA_PROD_SAMPLE_DATA.arProductivity.createdBy.id}" onclick="javascript:insertQCPoint(this);" >
								</c:otherwise>
							</c:choose>
						</td>
					</c:forEach>
				</c:if>
				
				<td class="buttons">
					<a href="javascript:void(0);" class="deleteAccBtn" id="${QA_PROD_SAMPLE_DATA.id}"> <img alt="Delete" border="0" src="<c:url value='/static/resources/img/delete_icon.png'/>"> </a>
				</td>
			</tr>
			<tr id="sample_${QA_PROD_SAMPLE_DATA.id}_${QA_PROD_SAMPLE_DATA.arProductivity.createdBy.id}_other1">
				<td colspan="7" style="background-color: #f9f9f9; border-top:0px;">
				&nbsp;
				</td>
				<td colspan="${fn:length(CHILDREN_QC_POINTS)}" style="border-top:0px;">
					<textarea rows='1' class="form-control input-sm" placeholder='Remarks' title="Remarks" onblur='javascript:lostFocusSave(this);'  style="width: 100%" id='remark_${QA_PROD_SAMPLE_DATA.id}'>${QA_PROD_SAMPLE_DATA.remarks}</textarea>
				</td>
				<td style="background-color: #f9f9f9; border-top:0px;">
					<span id="error_count_${QA_PROD_SAMPLE_DATA.id}">Errors: ${fn:length(QA_PROD_SAMPLE_DATA.qcPointChecklists)}</span>
				</td>
			</tr>
		</c:forEach>		
	
	</tbody>
	
	<tfoot>
		<tr style="background-color: #DBDBDB">
			<td colspan="3"><span id="totalErrorsMsg" style="display: inline"></span></td>
			<td colspan="${fn:length(CHILDREN_QC_POINTS)+5}" align="right" id="tdComplete">
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

function lostFocusSave(element) {
	var elementId = element.id;
	var elementInfo = elementId.split("_");

	var sampleId = elementInfo[1];
	//var id = elementInfo[2]; //patientInfoId

	var remark = $("#remark_" + elementInfo[1]).val();

	$.ajax({
		type : "POST",
		dataType : "json",
		url : contextPath + "/" + moduleName + "/savesampleremark",
		data : {
			'id' : sampleId,
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