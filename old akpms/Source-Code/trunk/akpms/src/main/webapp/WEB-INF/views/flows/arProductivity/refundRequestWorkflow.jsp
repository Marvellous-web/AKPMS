<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li><a href="<c:url value='/flows/arProductivity' />">&nbsp;AR
					Productivity List</a> &raquo;</li>
			<li>&nbsp;${operationType} Refund Request Work Flow</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<form:form commandName="refundRequest" id="form1" class="form-container">
		<div class="form-container-inner">
			<h3>${operationType} Refund Request Work Flow</h3>

			<input type="hidden" value="${arProductivity.id}"	name="arProductivity.id">

			<div class="productivityInfo">
				<span class="subHeading">Productivity Information</span>
				<br/>
				<div class="row-container">
			    	<div class="element-block" >
				    	<span class="lbl">Patient Name:</span> <span>${refundRequest.arProductivity.patientName}</span>
				    </div>
				    <div class="element-block" >
			    		<span class="lbl">Patient ID:</span> <span>${refundRequest.arProductivity.patientAccNo}</span>
			    	</div>
			    	<div class="clr"></div>
			    </div>
			    
			    <div class="row-container">
			    	<div class="element-block" >
				    	<span class="lbl">Database:</span> <span>${refundRequest.arProductivity.arDatabase.getName()}</span>
				    </div>
				    <div class="element-block" >
			    		<span class="lbl">Provider (Doctor Office):</span> <span>${refundRequest.arProductivity.doctor.name}</span>
			    	</div>
			    	<div class="clr"></div>
			    </div>
			    
				<div class="row-container">
			    	<div class="element-block" >
				    	<span class="lbl">Balance Amount:</span> <span><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${refundRequest.arProductivity.balanceAmt}" /></span>
				    </div>
				    <div class="element-block" >
			    		<span class="lbl">Remarks:</span> <span>${refundRequest.arProductivity.remark}</span>
			    	</div>
			    	<div class="clr"></div>
			    </div>
			    
			    <div class="row-container">
			    	<div class="element-block" >
				    	<span class="lbl">CPT:</span> <span>${refundRequest.arProductivity.cpt}</span>
				    </div>
				    <div class="element-block" >
			    		<span class="lbl">DOS:</span> <span>${refundRequest.arProductivity.dos}</span>
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
					<form:label path="cpt">CPT:<em class="mandatory">*</em></form:label>
					<span class="input_text">
						<span class="left_curve"></span>
						<form:input path="cpt" maxlength="100" cssClass="mid" cssStyle="width: 100px;" />
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
			<!-- Editable information from productivity [end]-->



				<span class="input-container">
					<form:label path="responsibleParty">Responsible Party<em class="mandatory">*</em></form:label>
					<span class="input_text">
						<span class="left_curve"></span>
							<form:input path="responsibleParty" maxlength="200" cssClass="mid" cssStyle="width: 373px;" />
						<span class="right_curve"></span>
					</span>
					<span><form:errors class="invalid" path="responsibleParty" /></span>
				</span>
				<span class="input-container">
					<form:label path="totalAmount">Total Amount</form:label>
					<span class="input_text">
						<span class="left_curve"></span>
							<form:input path="totalAmount" maxlength="15" cssClass="mid numbersOnly" cssStyle="width: 373px;" />
						<span class="right_curve"></span>
					</span>
				</span>

				<%-- <span class="input-container">
					<form:label path="dos">DOS</form:label>
					<span class="input_text">
						<span class="left_curve"></span>
							<form:input path="dos" maxlength="200" cssClass="mid" cssStyle="width: 373px;" />
						<span class="right_curve"></span>
					</span>
				</span> --%>

				<span class="input-container">
					<form:label path="reason">Reason:</form:label>
					<form:textarea rows="5" cols="20" path="reason" />
			   </span>

			<c:if test="${empty refundRequest.id}">
				<span class="input-container">
					<label>&nbsp;</label>
					<input type="submit" title="Forward to Payment Department"
					value="Forward to Payment Department" class="login-btn" />
				</span>
			</c:if>

			<c:if test="${not empty refundRequest.id}">
				<span class="input-container">
					<label id="remark">Remarks:</label>
					<form:textarea path="remark" cols="20" rows="5"/>
				</span>
	
				<span class="input-container" id="spanParent"> <form:label
							path="status">Action to be taken:
						</form:label> <span class="select-container" style="display: inline;"> <form:select
								path="status" cssClass="selectbox">
								<form:option value="0">-: Select Action:-</form:option>
								<form:option value="1">Done</form:option>
								<form:option value="2">Reject</form:option>
							</form:select>
					</span> <form:errors class="invalid" path="status" />
					</span>
	
				<span class="input-container">
					<label>&nbsp;</label>
					<input type="submit" title="Update"
					value="Update" class="login-btn" />
				</span>
			</c:if>
		 </div>
	</form:form>
</div>


<script type="text/javascript">
$(function() {
    $( "#checkIssueDate" ).datepicker();
    $( "#checkCashedDate" ).datepicker();
});
</script>
<script type="text/javascript" src="<c:url value='/static/resources/js/common.js'/>"></script>
