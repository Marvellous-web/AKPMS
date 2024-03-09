<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<c:set var ="canHandleEscalation" value="false"></c:set>
<sec:authorize ifAnyGranted="P-3">
	<c:set var="canHandleEscalation" value="true"></c:set>
</sec:authorize>

<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li><a href="<c:url value='/flows/arProductivity' />">&nbsp;AR Productivity List</a> &raquo;</li>
			<li>&nbsp;${operationType} Adjustment Log Work Flow</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<form:form commandName="adjustmentLogs" id="form1"
		class="form-container">
		<div class="form-container-inner">
			<h3>${operationType} Adjustment Log Work Flow</h3>

			<input type="hidden" value="${arProductivity.id}" name="arProductivity.id" />

			<%-- <span class="input-container">
				<label >Patient ID:</label> ${arProductivity.patientAccNo}
			</span>
			<span class="input-container">
				<label >Patient Name:</label> ${arProductivity.patientName}
			</span>
			<span class="input-container">
				<label >Insurance:</label> ${arProductivity.insurance.name}
			</span>
			<span class="input-container">
				<label >Provider (Doctor Office):</label> ${arProductivity.doctor.name}
			</span>
			<span class="input-container">
				<label >Balance Amount:</label> ${arProductivity.balanceAmt}
			</span>
			<span class="input-container">
				<label >DOS:</label> ${arProductivity.dos}
			</span>
			<span class="input-container">
				<label >CPT:</label> ${arProductivity.cpt}
			</span>
			<span class="input-container">
				<label >Timily Filing:</label> 
				<c:choose>
					<c:when test="${arProductivity.timilyFiling eq true}">
						Yes
					</c:when>
					<c:otherwise>
						No
					</c:otherwise>
				</c:choose>
			</span>
			<span class="input-container">
				<label >Remark:</label> ${arProductivity.remark}
			</span> --%>

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
			    
			    <div class="row-container">
			    	<div class="element-block" >
				    	<span class="lbl">Timely Filing:</span>
						<c:choose>
							<c:when test="${arProductivity.timilyFiling eq true}">
							<span>Yes</span>
						</c:when>
							<c:otherwise>
							<span>No</span>
						</c:otherwise>
						</c:choose>
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
			
			<%-- <div class="row-container">
				<div class="element-block" style="width: 100%">
					<form:label path="withoutTimilyFiling">Without Timily Filing:</form:label>
					<form:checkbox path="withoutTimilyFiling" value="1" />
				</div>
				<div class="clr"></div>	
			</div> --%>
			
			<div class="row-container">
				<div class="element-block" style="width: 100%">
					<form:label path="remark">Remark:<em class="mandatory">*</em></form:label>
					<form:textarea rows="5" cols="20" path="remark" />
					<div class="clr"></div>
					<span id="remark_errors" class="invalid"></span>
				</div>
				<div class="clr"></div>
			</div>
			
			
			<%-- <div class="row-container">
				<div class="element-block" style="width: 100%">
					<form:label path="remark">Remarks:</form:label>
					<form:textarea rows="5" cols="50" path="remark" />
					<div class="clr"></div>
					<form:errors cssClass="invalid" path="remark" />
				</div>
				<div class="clr"></div>
			</div> --%>
			<!-- Editable information from productivity [end]-->
			
			<%-- <c:if test="${showFinalRemark == 1}">
				 <span class="input-container">
					<form:label path="remark">Remark:</form:label> ${remark}
				</span>
			</c:if> --%>
			
			<c:if test="${canHandleEscalation or !empty adjustmentLogs.remark}">
				<div class="row-container">
					<div class="element-block" style="width: 100%">
						<form:label path="managerRemark">Manager Remark:<!-- <em class="mandatory">*</em> --></form:label>
						<form:textarea rows="5" cols="20" path="managerRemark" />
						<div class="clr"></div>
						<span id="manager_remark_errors" class="invalid"></span>
					</div>
					<div class="clr"></div>
				</div>
			</c:if>

			<c:choose>
				<c:when test="${empty adjustmentLogs.id}">
					<div class="row-container">
						<div class="element-block" style="width: 100%">
						  	<form:label path="workFlowStatus">Work flow:<em class="mandatory">*</em></form:label>
						  	<span class="select-container2">
						  		<c:choose>
						  			<c:when test="${canHandleEscalation}">
						  				<form:select path="workFlowStatus" cssClass="selectbox" >
											<form:option value="0">-: Select Work flow :-</form:option>
											<form:option value="1">Approve</form:option>
											<form:option value="2">Reject</form:option>
											<form:option value="3">Escalate</form:option>
											<form:option value="4">Closed</form:option>
										</form:select>
						  			</c:when>
						  			<c:otherwise>
						  				<form:select path="workFlowStatus" cssClass="selectbox" >
											<form:option value="0">-: Select Work flow :-</form:option>
											<form:option value="3">Escalate</form:option>
											<form:option value="4">Closed</form:option>
										</form:select>
						  			</c:otherwise>
						  		</c:choose>
							</span>
							<div class="clr"></div>
							<span id="workFlowStatus_errors" class="invalid"></span>
						</div>
						<div class="clr"></div>
					</div>

					<div class="row-container">
						<div class="element-block" style="width: 100%">
							<label>&nbsp;</label>
							<input type="submit" title="${buttonName}" value="${buttonName}" class="login-btn" onclick="return validation('false');" />
							<input type="button" title="Cancel" value="Cancel" class="login-btn" onclick="javascript:resetForm(this.form);" />
						</div>
						<div class="clr"></div>
					</div>
				</c:when>
				<c:otherwise>
					<%-- <c:choose> --%>
						<%-- <c:when test="${adjustmentLogs.workFlowStatus == 3 || adjustmentLogs.workFlowStatus == 4 ||  (!empty adjustmentLogs.remark && adjustmentLogs.workFlowStatus != 2) }"> --%>
							<%-- <c:if test="${adjustmentLogs.workFlowStatus == 3 || adjustmentLogs.workFlowStatus == 4}">
								<span class="input-container">
									<form:label path="workFlowStatus">Work Flow:</form:label>
									<spring:message code="status.${adjustmentLogs.workFlowStatus}"></spring:message>
								</span>
							</c:if>		 --%>
							<c:choose>
								<c:when test="${canHandleEscalation}">
									<div class="row-container">
										<div class="element-block" style="width: 100%">
											<form:label path="workFlowStatus">Status:<em class="mandatory">*</em></form:label>
											<span class="select-container2" style="display: inline;">
												<form:select path="workFlowStatus" cssClass="selectbox">
													<form:option value="0">-: Select Work flow :-</form:option>
													<form:option value="1">Approve</form:option>
													<form:option value="2">Reject</form:option>
													<form:option value="3">Escalate</form:option>
													<form:option value="4">Closed</form:option>
												</form:select>
											</span>
											<div class="clr"></div>
											<span id="workFlowStatus_errors" class="invalid"></span>
										</div>
										<div class="clr"></div>
									</div>
		
									<div class="row-container">
										<div class="element-block" style="width: 100%">
											<label>&nbsp;</label>
											<input type="submit" title="${buttonName}" value="${buttonName}" class="login-btn" onclick="return validation('true');" />
											<input type="button" title="Cancel" value="Cancel" class="login-btn" onclick="javascript:resetForm(this.form);" />
										</div>
										<div class="clr"></div>
									</div>
								</c:when>
								<c:otherwise>
									<div class="row-container">
										<div class="element-block" style="width: 100%">
											Your have no permission to handle escalation. 
										</div>
									</div>	
								</c:otherwise>
							</c:choose>			
							<c:if test="">
								<%-- <div class="row-container">
									<div class="element-block" style="width: 100%">
										<form:label path="managerRemark">Manager Remark:<!-- <em class="mandatory">*</em> --></form:label>
										<form:textarea rows="5" cols="20" path="managerRemark" />
										<div class="clr"></div>
										<span id="manager_remark_errors" class="invalid"></span>
									</div>
									<div class="clr"></div>
								</div> --%>
								
								
							</c:if>
						<%-- </c:when> --%>
						<%-- <c:otherwise>
						  	<div class="row-container">
								<div class="element-block" style="width: 100%">
									<form:label path="workFlowStatus">Work flow:<em class="mandatory">*</em></form:label>
									<span class="select-container2" style="display: inline;">
										<c:choose>
								  			<c:when test="${canHandleEscalation}">
								  				<form:select path="workFlowStatus" cssClass="selectbox" >
													<form:option value="0">-: Select Work flow :-</form:option>
													<form:option value="1">Approve</form:option>
													<form:option value="2">Reject</form:option>
													<form:option value="3">Escalate</form:option>
													<form:option value="4">Closed</form:option>
												</form:select>
								  			</c:when>
								  			<c:when test="${adjustmentLogs.workFlowStatus == 1}">
								  				<form:select path="workFlowStatus" cssClass="selectbox" >
													<form:option value="1">Approve</form:option>
													<form:option value="3">Escalate</form:option>
													<form:option value="4">Closed</form:option>
												</form:select>
								  			</c:when>
								  			<c:when test="${adjustmentLogs.workFlowStatus == 2}">
								  				<form:select path="workFlowStatus" cssClass="selectbox" >
													<form:option value="2">Reject</form:option>
													<form:option value="3">Escalate</form:option>
													<form:option value="4">Closed</form:option>
												</form:select>
								  			</c:when>
								  			<c:otherwise>								  				
								  				<form:select path="workFlowStatus" cssClass="selectbox" >
													<form:option value="0">-: Select Work flow :-</form:option>
													<form:option value="3">Escalate</form:option>
													<form:option value="4">Closed</form:option>
												</form:select>
								  			</c:otherwise>
								  		</c:choose>
									</span>
									<div class="clr"></div>
									<span id="workFlowStatus_errors" class="invalid"></span>
								</div>
								<div class="clr"></div>
							</div>

							<div class="row-container">
								<div class="element-block" style="width: 100%">
									<label>&nbsp;</label>
									<input type="submit" title="${buttonName}" value="${buttonName}" class="login-btn" onclick="return validation('false');" />
									<input type="button" title="Cancel" value="Cancel" class="login-btn" onclick="javascript:resetForm(this.form);" />
								</div>
								<div class="clr"></div>
							</div>
						</c:otherwise> 
					</c:choose>--%>
				</c:otherwise>
			</c:choose>
		</div>
	</form:form>
</div>

<script type="text/javascript">
	function validation(validateRemark){
		frmSubmit = true;

		if(validateRemark == 'true'){
			if ($.trim($('#remark').val()) == "") {
				$("#remark_errors").text("Remark cannot be left blank.");
				frmSubmit = false;
			}else{
				$("#remark_errors").text("");
			}
		}

		if($("#workFlowStatus").val() == 0){
			$("#workFlowStatus_errors").text("Work flow cannot be left blank.");
			frmSubmit = false;
		}else{
			$("#workFlowStatus_errors").text("");
		}
		return frmSubmit;
	}
</script>
