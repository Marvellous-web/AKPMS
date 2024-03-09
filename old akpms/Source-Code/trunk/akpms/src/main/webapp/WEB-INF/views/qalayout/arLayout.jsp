<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<table class="table" style="margin-bottom:270px;">
	<thead>
		<%-- <c:if test="${not empty PARENT_QC_POINTS}">
			<tr>
				<th colspan="7">&nbsp;</th>
				<c:forEach var="PARENT_QC_POINT" items="${PARENT_QC_POINTS}">
					<th colspan="${PARENT_QC_POINT.childCount}">${PARENT_QC_POINT.name}</th>
				</c:forEach>
				<th>&nbsp;</th>
			</tr>
		</c:if> --%>
		<tr style="background-color: #999999; color: white" >
			<th>Sort By</th>
			<th>
				<select name="orderby" id="orderby" style="color:black">
					<option value="">Default</option>
					<option value="arProductivity.createdOn">Posted Date</option>
					<option value="arProductivity.createdBy.firstName">DE Person</option>
					<option value="arProductivity.arDatabase.name">Database</option>
					<option value="arProductivity.patientAccNo">Patient Number</option>
				</select>
			</th>
			<th>
				<button type='button' name='btnOrder' id="btnOrder" class='btn btn-info btn-xs'>Set Order</button>
			</th>
			<th colspan="4">&nbsp;</th>
			<th colspan="2" style="text-align:right">
				<c:if test="${mode ne 'search'}">
					<input type="checkbox" value="showall" name="mode" id="chkShowAll" > <label for="chkShowAll">Show all hidden rows</label>
				</c:if>
			</th>
		</tr>
		<tr style="background-color: #999999; color: white">
			<th>Posted <br/> Date</th>
			<!-- <th>QA <br/> Date</th> -->
			<th>DE <br/> Person</th>
			<th>DOS</th>
			<th>Database</th>
			<th>PT Acct#</th>
			<th>CPT <br/> Codes</th>
			<th>Remarks</th>
			<th>Errors</th>
			<%-- <c:forEach items="${CHILDREN_QC_POINTS}" var="CHILD_QC_POINT" varStatus="loop">
				<th>
					<button type="button" class="btn btn-link" data-toggle="tooltip" style="padding:0px;" data-placement="top" title="${CHILD_QC_POINT.name}">${loop.index+1}</button>
				</th>
			</c:forEach> --%>
			<!-- <th>Remarks</th> -->
			<th style="text-align:right">Action</th>
		</tr>
	</thead>
		
	<tbody>
	
		<c:if test="${fn:length(QA_PROD_SAMPLE_DATA_LIST) eq 0}">
			<tr>
				<td colspan="9">No Sample Record Found.</td>
			</tr>
		</c:if>
	
		<c:set var="preErrCount" value="1" />
		<c:set var="forUncheck" value="0" />
		<c:forEach items="${QA_PROD_SAMPLE_DATA_LIST}" var="QA_PROD_SAMPLE_DATA" >
			<tr id="sample_${QA_PROD_SAMPLE_DATA.id}_${QA_PROD_SAMPLE_DATA.arProductivity.createdBy.id}" style="background-color: #D6D6D6;"  <c:if test="${QA_PROD_SAMPLE_DATA.hidden && (mode ne 'search' || mode ne 'showall')}">class="hide_tr"</c:if>  >
				<td> <fmt:formatDate type="date" value="${QA_PROD_SAMPLE_DATA.arProductivity.createdOn}"/> </td>
				<%-- <td> <fmt:formatDate type="date" value="${QA_PROD_SAMPLE_DATA.createdOn}"/> </td> --%>
				<td> 
					${QA_PROD_SAMPLE_DATA.arProductivity.createdBy.firstName} 
					${QA_PROD_SAMPLE_DATA.arProductivity.createdBy.lastName}
				</td>
				<td> ${QA_PROD_SAMPLE_DATA.arProductivity.dos} </td>
				<td> ${QA_PROD_SAMPLE_DATA.arProductivity.arDatabase.name} </td>
				<td> ${QA_PROD_SAMPLE_DATA.arProductivity.patientAccNo} </td>
				
				<c:set var="myString" value="${QA_PROD_SAMPLE_DATA.arProductivity.cpt}"/>
				<c:catch var="isNumeric">
				  <fmt:formatNumber value="${myString}" pattern="0" var="myInteger"/>
				  <c:set var="passed" value="${myInteger - myString eq 0}"/>
				</c:catch>
				
				<c:choose>
					<c:when test="${passed and isNumeric == null}">
						<c:set var="CPT" value="${QA_PROD_SAMPLE_DATA.arProductivity.cpt}"/>
					</c:when>
					<c:otherwise>
						<c:set var="CPT" value="1"/>
					</c:otherwise>
				</c:choose>
				
				<td> <span id="multiple_by_${QA_PROD_SAMPLE_DATA.id}">${CPT}</span> </td>
				
				<td>
					<textarea rows='2' class="form-control input-sm" placeholder='Remarks' title="Remarks" onblur='javascript:lostFocusSave(this);'  style="width: 100%" id='remark_${QA_PROD_SAMPLE_DATA.id}'>${QA_PROD_SAMPLE_DATA.remarks}</textarea>				
				</td>
				
				<td>
					<c:set var="error_count" value="${CPT * fn:length(QA_PROD_SAMPLE_DATA.qcPointChecklists)}"/>						
					
					<span id="error_count_${QA_PROD_SAMPLE_DATA.id}">${error_count}</span>				
				</td>
				
				<td class="buttons" style='width: 100px;'>
					<%-- <a href="javascript:void(0);"  id="${QA_PROD_SAMPLE_DATA.id}"> <img alt="Delete" border="0" src="<c:url value='/static/resources/img/delete_icon.png'/>"> </a> --%>
					<button type="button" name="qcPopAccBtn" class="btn btn-info btn-xs qcPopAccBtn" id="arqcpoint_${CHILD_QC_POINT.id}_${QA_PROD_SAMPLE_DATA.id}_${QA_PROD_SAMPLE_DATA.arProductivity.createdBy.id}" >QC</button>

					<c:choose>
						<c:when test="${QA_PROD_SAMPLE_DATA.hidden}">
							<button type="button" name="unHideAccBtn" class="btn btn-warning btn-xs unHideAccBtn" title="Un-Hide"><i class="fa fa-eye"></i></button>
						</c:when>						
						<c:otherwise>
							<button type="button" name="hideAccBtn" class="btn btn-warning btn-xs hideAccBtn" title="Hide"><i class="fa fa-eye-slash"></i></button>
						</c:otherwise>
					</c:choose>
					
					<button type="button" name="deleteAccBtn" class="btn btn-danger btn-xs deleteAccBtn" title="Delete"><i class="fa fa-minus-square"></i></button>
					<!-- <div class="clearfix" style="height:5px;"></div> -->
										
				</td>
			</tr>
		</c:forEach>		
	</tbody>
	
	<c:if test="${mode ne 'search'}">
		<tfoot>
			<tr style="background-color: #DBDBDB">
				<td colspan="3"><span id="totalErrorsMsg" style="display: inline"></span></td>
				<td colspan="6" align="right" id="tdComplete">
					<c:if test="${QA_WORKSHEET.status ne '2' }">
						Once Worksheet will "Set as Completed". User can't make modification in it. Are you sure to 
						<input type="button" class="btn btn-primary btn-lg" value="Set as Completed?" onclick="javascript:ajaxSetAsCompleted();">
					</c:if>
				</td>
			</tr>		
		</tfoot>
	</c:if>
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