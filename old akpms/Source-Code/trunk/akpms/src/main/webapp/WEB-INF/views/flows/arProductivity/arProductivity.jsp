<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li><a href="<c:url value='/flows/arProductivity' />">&nbsp;AR Productivity List</a>&raquo;</li>
			<li>&nbsp;${operationType} AR Productivity</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<form:form commandName="arProductivity" id="form1" class="form-container">
		<div class="form-container-inner">
			<h3>${operationType} AR Productivity</h3>
			
			<div class="row-container">
				<div class="element-block">
					<form:label path="arDatabase.id">Database:<em class="mandatory">*</em></form:label>
					<span class="select-container1" style="display:inline;">
						<form:select path="arDatabase.id" cssClass="selectbox">
							<form:option value="">-: Select Database :-</form:option>
							<form:options items="${arDatabaseList}" itemValue="id" itemLabel="name" />
						</form:select>
					</span>
					<div class="clr"></div>
					<form:errors cssClass="invalid" path="arDatabase.id" />
				</div>
				<div class="element-block">
					<form:label path="source">Source:<em class="mandatory">*</em></form:label>
					<span class="select-container1" style="display:inline;">
						<form:select path="source" cssClass="selectbox">
							<form:option value="">-: Select Source :-</form:option>
							<c:forEach var="source" items="${SOURCES}">
								<form:option value="${fn:replace(source.key, '_',' ')}">${source.value}</form:option>
							</c:forEach>
						</form:select>
					</span>
					<form:errors cssClass="invalid" path="source" />
				</div>
				<div class="clr"></div>
			</div>
			
			<div class="row-container">
				<div class="element-block">
					<form:label path="patientAccNo">Patient ID:<em class="mandatory">*</em></form:label>
					<span class="input_text">
						<span class="left_curve"></span>
						<form:input path="patientAccNo" maxlength="200" cssClass="mid" cssStyle="width: 100px;"  />
						<span class="right_curve"></span>
					</span>
					<form:errors cssClass="invalid" path="patientAccNo" />
				</div>
				<div class="element-block">
					<form:label path="statusCode">Status Code:</form:label>
					<span class="select-container1" style="display:inline;">
						<form:select path="statusCode" cssClass="selectbox">
							<form:option value="">-: Select Status Code :-</form:option>
							<c:forEach var="sCode" items="${STATUS_CODES}">
								<form:option value="${sCode.key}">${sCode.value}</form:option>
							</c:forEach>
						</form:select>
					</span>
					<form:errors cssClass="invalid" path="statusCode" />	
				</div>
				<div class="clr"></div>
			</div>
			
			<div class="row-container">
				<div class="element-block" style="width: 100%" >
					<form:label path="patientName">Patient Name:<em class="mandatory">*</em></form:label>
					<span class="input_text">
						<span class="left_curve"></span>
						<form:input path="patientName" maxlength="200" cssClass="mid" cssStyle="width: 373px;"  />
						<span class="right_curve"></span>
					</span>
					<form:errors cssClass="invalid" path="patientName" />	
				</div>
				<div class="clr"></div>
			</div>
			
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
				<div class="element-block">
					<form:label path="dos">DOS:</form:label>
					<span class="input_text">
						<span class="left_curve"></span>
						<form:input path="dos" maxlength="255" cssClass="mid" cssStyle="width: 100px;"/>
						<span class="right_curve"></span>
					</span>
					<form:errors cssClass="invalid" path="dos" />
				</div>
				<div class="element-block">
					<form:label path="cpt">Number Of CPT:<em class="mandatory">*</em></form:label>
					<span class="input_text">
						<span class="left_curve"></span>
						<form:input path="cpt" maxlength="100" cssClass="mid integerOnly" cssStyle="width: 100px;" />
						<span class="right_curve"></span>
					</span>
					<form:errors class="invalid" path="cpt" />
				</div>
								
				<div class="clr"></div>	
			</div>	
				
			<div class="row-container">
				<div class="element-block">
					<form:label path="doctor.id">Provider (Doctor Office):<em class="mandatory">*</em></form:label>
					<span class="select-container1" style="display:inline;">
						<form:select path="doctor.id" cssClass="selectbox">
							<form:option value="">-: Select Provider (Doctor Office) :-</form:option>
							<c:forEach var="doctor" items="${doctorList}">
								<form:option value="${doctor.id}">
									${doctor.name} <c:if test="${not empty doctor.parent}">	(${doctor.parent.name})	</c:if>
								</form:option>
							</c:forEach>
						</form:select>
					</span>
					<form:errors cssClass="invalid" path="doctor.id" />
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
			
			<div class="row-container">
				<div class="element-block">
					<form:label path="team.id">Team:</form:label>
					<span class="select-container1" style="display:inline;">
						<form:select path="team.id" cssClass="selectbox">
							<form:option value="">-: Select Team:-</form:option>
							<c:forEach var="team" items="${teamList}">
								<form:option value="${team.id}">
									${team.name} 
								</form:option>
							</c:forEach>
						</form:select>
					</span>
					<%-- <form:errors cssClass="invalid" path="teamId" /> --%>
				</div>
				<div class="element-block">
					<form:label path="followUpDate">Follow Up Date:</form:label>
					<span class="input_text">
						<span class="left_curve"></span>
							<form:input path="followUpDate" readonly="true" cssClass="mid" cssStyle="width: 100px;" />
						<span class="right_curve"></span>
					</span>
					<%-- <form:errors class="invalid" path="followUpDate" /> --%>
				</div>
				<div class="clr"></div>
			</div>
			
			<div class="row-container">
				<div class="element-block" style="width: 100%">
					<form:label path="remark">Notes:<em class="mandatory" id="emNotes" >*</em></form:label>
					<form:textarea rows="5" cols="50" path="remark" />
					<div class="clr"></div>
					<form:errors cssClass="invalid" path="remark" />
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block">
					<form:label path="workFlows">WorkFlow:</form:label>
					<ul style="padding-left: 161px;" id="workflowCheckboxes">
						<c:forEach var="arWorkFlow" items="${ArWorkFlows}" varStatus="counter">
							<li>
							<c:choose>
								<c:when test="${workFLowCount[0][counter.index] gt 0 }">
									<%-- <form:hidden path="workFlows" value="${arWorkFlow}" id="chkWorkflow_${arWorkFlow}"  />
									<img src="<c:url value='/static/resources/img/checked.png'/>" /> --%>
									<form:checkbox path="workFlows" value="${arWorkFlow}" id="chkWorkflow_${arWorkFlow}" onclick="javascript:checkCheckbox(this);" onkeydown="javascript:checkCheckbox(this);"    />
								</c:when>
								<c:otherwise>
									<form:checkbox path="workFlows" value="${arWorkFlow}" id="chkWorkflow_${arWorkFlow}"  />
								</c:otherwise>
							</c:choose>
								<span class="dept-heading">${arWorkFlow.name}</span>
							</li>
						</c:forEach>
					</ul>
				</div>
				<div class="clr"></div>
			</div>
			

			<div class="row-container">
				<%-- <div class="element-block">
					<form:label path="workFlow">Work Flow:</form:label>
					<span class="select-container1" >
						<form:select path="workFlow" cssClass="selectbox" disabled="${readOnly}" onchange="javascript:showHideOptions(this);" >
							<form:option value="0">-: Select Work flow :-</form:option>
							<form:option value="1">Adjustment log</form:option>
							<form:option value="2">Coding correction</form:option>
							<form:option value="3">Second request log</form:option>
							<form:option value="4">Re-Key request to charge posting</form:option>
							<form:option value="5">Payment posting log</form:option>
							<form:option value="6">Request for check tracer</form:option>
							<form:option value="8">Refund Request</form:option>
							<form:option value="9">Stop payment/Reissue</form:option>
							<form:option value="10">Request for Docs</form:option>
							<form:option value="7">Query to TL</form:option>
						</form:select>
					</span>
					<form:errors cssClass="invalid" path="workFlow" />
				</div>	 --%>
				<div class="element-block" id="divTimilyFiling">
					<form:label path="timilyFiling">Timely Filing:</form:label>
					<form:checkbox path="timilyFiling" value="1" />
				</div>
				<div class="clr"></div>
			</div>

			<br/>
			<div class="row-container">
				<div class="element-block" style="width: 100%">
					<label>&nbsp;</label>
					<input type="submit" title="${buttonName}" value="${buttonName}" class="login-btn" /> 
					<input type="button" title="Cancel" value="Cancel" class="login-btn"  onclick="javascript:resetForm(this.form);" />
				</div>
				<div class="clr"></div> 
			</div>
		</div>
	</form:form>
</div>

<script type="text/javascript">
	function checkCheckbox(chk){
		chk.checked = true;
	}
	
	function showHideOptions(){
		if($("#chkWorkflow_1").is(':checked')){
			$("#divTimilyFiling").show();
		}else{
			$("#divTimilyFiling").hide();
		}

		var len = $("#workflowCheckboxes input[name='workFlows']:checked").length;
		
		if(len > 0){
			$("#emNotes").show();
		}else{
			$("#emNotes").hide();
		}
	}
	$(function() {
	    $( "#followUpDate" ).datepicker();
	    showHideOptions();
	});

	$('#workflowCheckboxes input:checkbox').click(function(){
		showHideOptions();
	});
</script>
<script type="text/javascript" src="<c:url value='/static/resources/js/common.js'/>"></script>
