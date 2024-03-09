<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li><a href="<c:url value='/flows/arProductivity' />">&nbsp;AR Productivity List</a> &raquo;</li>
			<li>Rekey Request WorkFlow</li>
		</ul>
	</div>
	<div class="clr"></div>
	
	<c:set var="showChargeBatchOption" value="false"></c:set>

	<c:forEach var="dept" items="${pageContext.request.userPrincipal.principal.departments}">
		<c:if test="${dept.id == 1}">
			<c:set var="showChargeBatchOption" value="true"></c:set>
		</c:if>
	</c:forEach>

	
	<!-- Bread Crumb End-->
	<form:form commandName="rekeyRequestWorkFlow" id="form1" class="form-container">
		<div class="form-container-inner">
			<h3>${operationType} Rekey Request WorkFlow</h3>

		<div class="productivityInfo">
			<span class="subHeading">Productivity Information</span>
			<br/>
			<div class="row-container">
		    	<div class="element-block" >
			    	<span class="lbl">Patient Name:</span> <span>${arProductivity.patientName}</span>
			    </div>
			    <div class="element-block" >
		    		<span class="lbl">Patient ID:</span> <span>${arProductivity.patientAccNo}</span>
		    	</div>
		    	<div class="clr"></div>
		    </div>
		    
		    <div class="row-container">
		    	<div class="element-block" >
			    	<span class="lbl">Insurance:</span> <span>${arProductivity.insurance.name}</span>
			    </div>
			    <div class="element-block" >
		    		<span class="lbl">Provider (Doctor Office):</span> <span>${arProductivity.doctor.name}</span>
		    	</div>
		    	<div class="clr"></div>
		    </div>
		    
			<div class="row-container">
		    	<div class="element-block" >
			    	<span class="lbl">Balance Amount:</span> <span><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${arProductivity.balanceAmt}" /></span>
			    </div>
			    <div class="element-block" >
		    		<span class="lbl">Remarks:</span> <span>${arProductivity.remark}</span>
		    	</div>
		    	<div class="clr"></div>
		    </div>
		    
		    <div class="row-container">
		    	<div class="element-block" >
			    	<span class="lbl">CPT:</span> <span>${arProductivity.cpt}</span>
			    </div>
			    <div class="element-block" >
		    		<span class="lbl">DOS:</span> <span>${arProductivity.dos}</span>
		    	</div>
		    	<div class="clr"></div>
		    </div>
			
		</div>
		
		<!-- Editable information from productivity [start]-->
			    <div class="row-container">
				<div class="element-block" style="width: 100%">
					<form:label path="insurance.id">Insurance:<em class="mandatory">*</em></form:label>
					<span class="select-container">
						<form:select path="insurance.id" cssClass="selectbox">
							<form:option value="">-: Select Insurance :-</form:option>
							<form:options items="${insuranceList}" itemValue="id" itemLabel="name" />
						</form:select>
					</span>
					<div class="clr"></div>
					<form:errors cssClass="invalid" path="insurance.id" />
				</div>
				<div class="clr"></div>
			</div>
			
			<div class="row-container">
				<%-- <div class="element-block">
					<form:label path="dos">DOS:</form:label>
					<span class="input_text">
						<span class="left_curve"></span>
						<form:input path="dos" maxlength="255" cssClass="mid" cssStyle="width: 100px;"/>
						<span class="right_curve"></span>
					</span>
					<form:errors cssClass="invalid" path="dos" />
				</div> --%>
				<div class="element-block" style="width: 100%">
					<form:label path="doctor.id">Provider (Doctor Office):<em class="mandatory">*</em></form:label>
					<span class="select-container" style="display:inline;">
						<form:select path="doctor.id" cssClass="selectbox">
							<form:option value="">-: Select Provider (Doctor Office) :-</form:option>
							<c:forEach var="doctor" items="${doctorList}">
								<form:option value="${doctor.id}">
									${doctor.name} <c:if test="${not empty doctor.parent}">	(${doctor.parent.name})	</c:if>
								</form:option>
							</c:forEach>
						</form:select>
					</span>
					<div class="clr"></div>
					<form:errors cssClass="invalid" path="doctor.id" />
				</div>
				<div class="clr"></div>
			</div>
				
			<div class="row-container">
				<div class="element-block">
					<form:label path="cpt">CPT:<em class="mandatory">*</em></form:label>
					<span class="input_text">
						<span class="left_curve"></span>
						<form:input path="cpt" maxlength="100" cssClass="mid" cssStyle="width: 100px;" />
						<span class="right_curve"></span>
					</span>
					<form:errors class="invalid" path="cpt" />
				</div>
				<div class="element-block">
					<form:label path="balanceAmt">Balance Amount:<em class="mandatory">*</em></form:label>
					<span class="input_text">
						<span class="left_curve"></span>
						<form:input path="balanceAmt" maxlength="15" cssClass="mid numbersOnly" cssStyle="width: 100px;" />
						<span class="right_curve"></span>
					</span>
					<form:errors cssClass="invalid" path="balanceAmt" />
				</div>
				<div class="clr"></div>
			</div>
			<!-- Editable information from productivity [end]-->
			
			<input type="hidden" name="arProductivity.id" value="${arProductivity.id }">
			
			<div class="row-container">
				<div class="element-block">
					<form:label path="batchNumber">Batch Number:<em class="mandatory">*</em></form:label>
					<span class="input_text">
						<span class="left_curve"></span>
						<form:input path="batchNumber" maxlength="255" cssClass="mid" cssStyle="width: 100px;" />
						<span class="right_curve"></span>
					</span>
					<form:errors cssClass="invalid" path="batchNumber" />
				</div>
				
				<div class="element-block">
					<form:label path="dos">DOS:<em class="mandatory">*</em></form:label>
					<span class="input_text">
						<span class="left_curve"></span>
						<form:input path="dos" maxlength="255" cssClass="mid" cssStyle="width: 100px;" />
						<span class="right_curve"></span>
					</span>
					<form:errors cssClass="invalid" path="dos" />
				</div>
				<div class="clr"></div>
			</div>
			
			<div class="row-container">
				<div class="element-block">
					<form:label path="requestReason">Request Reason:<em class="mandatory">*</em></form:label>
					<span class="select-container1" style="display:inline;">
						<form:select path="requestReason" cssClass="selectbox">
							<form:option value="0">-: Select Reason :-</form:option>
							<c:forEach var="requestReason" items="${REJECTION_REASONS}">
								<form:option value="${requestReason.key}">${requestReason.value}</form:option>
							</c:forEach>
						</form:select>
					</span>
					<form:errors cssClass="invalid" path="requestReason" />
				</div>
				<div class="element-block">
					<form:label path="status">Status:<em class="mandatory">*</em></form:label>
					<span class="select-container1" style="display:inline;">
						<form:select path="status" cssClass="selectbox">
							<form:option value="0">-: Select Status :-</form:option>
							<form:option value="1">Query To CE</form:option>
							<%-- <form:option value="2">Coding to CE Returned</form:option> --%>
							<form:option value="3">Return to AR</form:option>
							<%-- <form:option value="4">Query to Coding</form:option> --%>
							<form:option value="5">Close</form:option>
						</form:select>
					</span>
					<form:errors cssClass="invalid" path="status" />
				</div>
				<div class="clr"></div>
			</div>
			
			<div class="row-container">
					<form:errors class="invalid" path="rekeyRequestRecords"/>
			</div>
			<div class="table-grid" id="divMultiple">
				<table class="dd" width="100%" id="data" border = "2">
					<thead>
				        <tr>
				            <th>CPT</th>
				            <th>Remark</th>
				            <th>&nbsp;</th>
				        </tr>
				    </thead>
					<c:forEach var="rekeyRequestRecord" items="${rekeyRequestRecords}"
						varStatus="i">
						<tr id="row_${i.index }">
							<td>
								<form:input path="rekeyRequestRecords[${i.index }].cpt" id="rekeyRequestRecords_${i.index }_cpt" maxlength="50"/>
							</td>
							<td>
								<form:input path="rekeyRequestRecords[${i.index }].remark" id="rekeyRequestRecords_${i.index }_remark" />
							</td>
							<td>
								<a href='javascript:void(0)' onClick='javascript:deleteRow(${i.index});' class='delete'><img src='<c:url value="/static/resources/img/delete_icon.png"/>' alt='Delete' title='Delete' /></a>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<input type="hidden" name="toRemoveIds" value="${toRemoveIds}"/>
			<input type="button" value="Add More" title="Add More"	id="addnew" align="left"/>

			<c:if test="${showChargeBatchOption && not empty rekeyRequestWorkFlow.id }">
				<div class="row-container">
					<div class="element-block" style="width: 100%">
						<label id="chargePostingRemark">Charge Posting Remarks:</label>
						<form:textarea path="chargePostingRemark" cols="20" rows="5" />
					</div>
					<span class="invalid" id="chargePostingRemark_validation"></span>
					<div class="clr"></div>
				</div>
			</c:if>
					
			<%-- <c:if test="${(not empty rekeyRequestWorkFlow.id && not empty rekeyRequestWorkFlow.codingRemark) || (rekeyRequestWorkFlow.status == 4)}"> --%>
			<c:if test="${(not empty rekeyRequestWorkFlow.id)}">
				<div class="row-container">
					<div class="element-block" style="width: 100%">
						<label id="codingRemark">Coding Remark:</label>
						<form:textarea path="codingRemark" cols="20" rows="5" />
					</div>
					<span class="invalid" id="codingRemark_validation"></span>
					<div class="clr"></div>
				</div>
			</c:if>

		
			<div class="row-container">
				<div class="element-block" style="width: 100%">
					<label>&nbsp;</label>
					<input type="submit" title="${buttonName}" value="${buttonName}" class="login-btn" />
					<input type="button" title="Cancel" value="Cancel" class="login-btn" onclick="javascript:resetForm(this.form);" />
				</div>
				<div class="clr"></div>
			</div>
		
		</div>
	</form:form>
</div>

<script type="text/javascript">
<!--
var currentItem = parseInt("${fn:length(rekeyRequestRecords)}")-1;
$(document).ready(function() {
	$('#addnew').click(function(){
		currentItem++;
		var strToAdd = '<tr id="row_'+currentItem+'">'
		+'<td><input type= "text" name = "rekeyRequestRecords['+currentItem+'].cpt" id="rekeyRequestRecords_'+currentItem+'_cpt" maxlength="50"/></td>'
		+'<td><input type= "text" name = "rekeyRequestRecords['+currentItem+'].remark" id="rekeyRequestRecords_'+currentItem+'_remard" /></td>'
		+'<td><a href="javascript:void(0)" onClick="javascript:deleteRow('+currentItem+');" class="delete"><img src="'+deleteIcon+'" alt="Delete" title="Delete" /></a>'
		+'<input type = "hidden" name = "rekeyRequestRecords['+currentItem+'].rekeyRequestWorkFlow.id" value="${rekeyRequestWorkFlow.id}" /></td>'
		+'</tr>';

		$('#data').append(strToAdd);
	});
});
	
function deleteRow(rowid){
	var counterWithNoneStyle = 0;
	$('#data >tbody >tr').each(function(){
		if(this.style.display == 'none'){
			counterWithNoneStyle ++;
		}
	});
	totalRows = $('#data >tbody >tr').length;
	if((parseInt(totalRows) - parseInt(counterWithNoneStyle)) == 1 ){
		alert("Last row cannot be deleted.");
		return false;
	}
	else{
		$('#row_'+rowid).hide(250);
		$('#rekeyRequestRecords_'+rowid+'_cpt').val("");
		$('#rekeyRequestRecords_'+rowid+'_remark').val("");
		//var originalState = $("#divMultiple").clone();
		//$("#divMultiple").replaceWith(originalState);
	}
}
//-->
</script>
<script type="text/javascript" src="<c:url value='/static/resources/js/common.js'/>"></script>
