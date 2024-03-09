<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" import="java.util.*" %>
<c:set var="downloadAction">
	<c:url value='/flows/codingcorrectionlogworkflow/fileDownload' />
</c:set>

<%
ResourceBundle messages = ResourceBundle.getBundle("codingCorrectionNextStep");
//String emailSuccess = messages.getString("email.passwordMailSent");
%>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li><a href="<c:url value='/flows/arProductivity' />">&nbsp;AR Productivity List</a> &raquo;</li>
			<li>Coding Correction Log WorkFlow</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<form:form commandName="codingCorrectionLogWorkFlow" id="form1" class="form-container" enctype="multipart/form-data">
		<input type="hidden" name="arProductivity.id" value="${AR_PRODUCTIVITY_ID}">
		<input type="hidden" name="currentAction" value="${codingCorrectionLogWorkFlow.nextAction}">

		<div class="form-container-inner">
			<h3>${operationType} Coding Correction Log WorkFlow</h3>

			
			<div class="productivityInfo">
				<span class="subHeading">Productivity Information</span>
				<br/>
				<div class="row-container">
			    	<div class="element-block" >
				    	<span class="lbl">Patient Name:</span> <span>${codingCorrectionLogWorkFlow.arProductivity.patientName}</span>
				    </div>
				    <div class="element-block" >
			    		<span class="lbl">Patient ID:</span> <span>${codingCorrectionLogWorkFlow.arProductivity.patientAccNo}</span>
			    	</div>
			    	<div class="clr"></div>
			    </div>
			    
			    <div class="row-container">
			    	<div class="element-block" >
				    	<span class="lbl">Insurance:</span> <span>${codingCorrectionLogWorkFlow.arProductivity.insurance.name}</span>
				    </div>
				    <div class="element-block" >
			    		<span class="lbl">Provider (Doctor Office):</span> <span>${codingCorrectionLogWorkFlow.arProductivity.doctor.name}</span>
			    	</div>
			    	<div class="clr"></div>
			    </div>
			    
				<div class="row-container">
			    	<div class="element-block" >
				    	<span class="lbl">Balance Amount:</span> <span><fmt:formatNumber pattern="$#,##0.00;-$#,##0.00"  type="currency" value="${codingCorrectionLogWorkFlow.arProductivity.balanceAmt}" /></span>
				    </div>
				    <div class="element-block" >
			    		<span class="lbl">Remarks:</span> <span>${codingCorrectionLogWorkFlow.arProductivity.remark}</span>
			    	</div>
			    	<div class="clr"></div>
			    </div>
			    
			    <div class="row-container">
			    	<div class="element-block" >
				    	<span class="lbl">CPT:</span> <span>${codingCorrectionLogWorkFlow.arProductivity.cpt}</span>
				    </div>
				    <div class="element-block" >
			    		<span class="lbl">DOS:</span> <span>${codingCorrectionLogWorkFlow.arProductivity.dos}</span>
			    	</div>
			    	<div class="clr"></div>
			    </div>
		    </div>

			<%-- <div class="row-container">
				<div class="element-block" style="width: 100%">
					<span><strong>Remarks: </strong> ${codingCorrectionLogWorkFlow.arProductivity.remark}</span>
				</div>
				<div class="clr"></div>
			</div> --%>
			
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
			<div class="row-container">
				<div class="element-block" style="width: 100%">
					<form:label path="remark">Remark:</form:label>
					<form:textarea rows="5" cols="20" path="remark" />
					<div class="clr"></div>
					<span id="remark_errors" class="invalid"></span>
				</div>
				<div class="clr"></div>
			</div>
			<!-- Editable information from productivity [end]-->
			

			<div class="row-container">
				<div class="element-block">
					<form:label path="batchNo">Batch number</form:label>
					<span class="input_text">
						<span class="left_curve"></span>
							<form:input path="batchNo" maxlength="200" cssClass="mid" cssStyle="width: 100px;"  readonly="${readOnly}"/>
						<span class="right_curve"></span>
					</span>
					<form:errors cssClass="invalid" path="batchNo" />
				</div>
				<div class="element-block">
					<form:label path="sequenceNo">Sequence number</form:label>
					<span class="input_text">
						<span class="left_curve"></span>
							<form:input path="sequenceNo" maxlength="200" cssClass="mid" cssStyle="width: 100px;" readonly="${readOnly}" />
						<span class="right_curve"></span>
					</span>
					<form:errors cssClass="invalid" path="sequenceNo" />
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<div class="element-block" style="width: 100%">
					<form:label path="attachment">Attachment:</form:label>
					<span class="input_text">
						<form:input	type="file" path="attachment.attachedFile" size="44" cssClass="mid"/>
					</span>
					<form:errors cssClass="invalid" path="attachment.attachedFile" />
				</div>
				<div class="clr"></div>
			</div>

			<c:if test="${not empty codingCorrectionLogWorkFlow.attachment.id}">
				<div class="row-container" id="file_row">
					<div class="element-block" style="width: 100%">
						<form:label path="attachment">Attached File:</form:label>
						<div class="download-container">
							${codingCorrectionLogWorkFlow.attachment.name}<a class="download" href="${downloadAction}?id=${codingCorrectionLogWorkFlow.attachment.id}" title="Download File"></a>
							<a href="javascript:void(0);" onclick='javascript:deleteFile(${codingCorrectionLogWorkFlow.attachment.id})' title="Delete File"><img title="Delete File" alt="Delete File" style="vertical-align: middle;" src="<c:url value="/static/resources/img/cross.png"/>" ></a> &nbsp;
						</div>
					</div>
				</div>
				<div class="clr"></div>
			</c:if>

			<c:set var="showChargeBatchOption" value="false"></c:set>

			<c:forEach var="dept" items="${pageContext.request.userPrincipal.principal.departments}">
				<c:if test="${dept.id == 1}">
					<c:set var="showChargeBatchOption" value="true"></c:set>
				</c:if>
			</c:forEach>
			
			<c:choose>
				<c:when test="${showChargeBatchOption && not empty codingCorrectionLogWorkFlow.id}">
					<div class="row-container">
						<div class="element-block" style="width: 100%">
							<label id="remark">Coding Remarks:<em class="mandatory">*</em></label>
							<form:textarea path="codingRemark" cols="20" rows="5"/>
						</div>
						<span class="invalid" id="codingRemark_validation" ></span>
						<div class="clr"></div>
					</div>
					
					<c:if test="${codingCorrectionLogWorkFlow.nextAction == 'Forward to coding correction'}">
						<div class="row-container">
								<div class="element-block">
									<form:label path="nextAction">Next Action Step:<em class="mandatory">*</em></form:label>
									<span class="select-container1" style="display:inline;">
										<form:select path="nextAction" cssClass="selectbox">
											<form:option value="">-: Select Work fLow :-</form:option>
											<form:option value="Escalate to Argus TL">Escalate to Argus TL</form:option>
											<form:option value="Forward to coding correction">Forward to coding correction</form:option>
											<form:option value="Return to AR">Return to AR</form:option>
											<form:option value="Coding to CE">Coding to CE</form:option>
											<form:option value="Done">Done</form:option>
										</form:select>
									</span>
									<form:errors cssClass="invalid" path="nextAction" />
								</div>
							<div class="clr"></div>
						</div>
					</c:if>
					
					<c:if test="${codingCorrectionLogWorkFlow.nextAction == 'Return to AR'}">
						<div class="row-container">
								<div class="element-block">
									<form:label path="nextAction">Next Action Step:<em class="mandatory">*</em></form:label>
									<span class="select-container1" style="display:inline;">
										<form:select path="nextAction" cssClass="selectbox">
											<form:option value="">-: Select Work fLow :-</form:option>
											<form:option value="Escalate to Argus TL">Escalate to Argus TL</form:option>
											<form:option value="Forward to coding correction">Forward to coding correction</form:option>
											<form:option value="Return to AR">Return to AR</form:option>
											<form:option value="Coding to CE">Coding to CE</form:option>
											<form:option value="Done">Done</form:option>
										</form:select>
									</span>
									<form:errors cssClass="invalid" path="nextAction" />
								</div>
							<div class="clr"></div>
						</div>
					</c:if>
					
					<c:if test="${codingCorrectionLogWorkFlow.nextAction == 'Escalate to Argus TL'}">
						<div class="row-container">
								<div class="element-block">
									<form:label path="nextAction">Next Action Step:<em class="mandatory">*</em></form:label>
									<span class="select-container1" style="display:inline;">
										<form:select path="nextAction" cssClass="selectbox">
											<form:option value="">-: Select Work fLow :-</form:option>
											<form:option value="Escalate to Argus TL">Escalate to Argus TL</form:option>
											<form:option value="Forward to coding correction">Forward to coding correction</form:option>
											<form:option value="Return to AR">Return to AR</form:option>
											<form:option value="Done">Done</form:option>
										</form:select>
									</span>
									<form:errors cssClass="invalid" path="nextAction" />
								</div>
							<div class="clr"></div>
						</div>
					</c:if>
					
					<c:if test="${codingCorrectionLogWorkFlow.nextAction == 'Coding to CE'}">
						<div class="row-container">
								<div class="element-block">
									<form:label path="nextAction">Next Action Step:<em class="mandatory">*</em></form:label>
									<span class="select-container1" style="display:inline;">
										<form:select path="nextAction" cssClass="selectbox">
											<form:option value="">-: Select Work fLow :-</form:option>
											<form:option value="Coding to CE">Coding to CE</form:option>
											<form:option value="Forward to coding correction">Forward to coding correction</form:option>
											<form:option value="Return to AR">Return to AR</form:option>
											<form:option value="Done">Done</form:option>
										</form:select>
									</span>
									<form:errors cssClass="invalid" path="nextAction" />
								</div>
							<div class="clr"></div>
						</div>
					</c:if>
					
					<c:if test="${codingCorrectionLogWorkFlow.nextAction == 'Done'}">
						<div class="row-container">
								<div class="element-block">
									<form:label path="nextAction">Next Action Step:<em class="mandatory">*</em></form:label>
									<span class="select-container1" style="display:inline;">
										<form:select path="nextAction" cssClass="selectbox">
											<form:option value="">-: Select Work fLow :-</form:option>
											<form:option value="Escalate to Argus TL">Escalate to Argus TL</form:option>
											<form:option value="Forward to coding correction">Forward to coding correction</form:option>
											<form:option value="Return to AR">Return to AR</form:option>
											<form:option value="Done">Done</form:option>
										</form:select>
									</span>
									<form:errors cssClass="invalid" path="nextAction" />
								</div>
							<div class="clr"></div>
						</div>
					</c:if>
					
					<div class="row-container">
						<div class="element-block" style="width: 100%">
							<label>&nbsp;</label>
							<input type="submit" title="Submit" value="Submit" class="login-btn" onclick="return checkCodingRemark();" />
							<input type="button" title="Cancel" value="Cancel" class="login-btn" onclick="javascript:resetForm(this.form);" />
						</div>
						<div class="clr"></div>
					</div>
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${empty codingCorrectionLogWorkFlow.id}">
							<div class="row-container">
								<div class="element-block">
									<form:label path="nextAction">Next Action Step:<em class="mandatory">*</em></form:label>
									<span class="select-container1" style="display:inline;">
										<form:select path="nextAction" cssClass="selectbox">
											<form:option value="">-: Select Work fLow :-</form:option>
											<form:option value="Forward to coding correction">Forward to coding correction</form:option>
										</form:select>
									</span>
									<form:errors cssClass="invalid" path="nextAction" />
								</div>
								<div class="clr"></div>
							</div>
							<div class="row-container">
									<div class="element-block" style="width: 100%">
										<label>&nbsp;</label>
										<input type="submit" title="submit" value="submit" class="login-btn" />
										<input type="button" title="Cancel" value="Cancel" class="login-btn" onclick="javascript:resetForm(this.form);" />
									</div>
								<div class="clr"></div>
							</div>
						</c:when>
						<c:otherwise>
							<c:if test="${!empty codingCorrectionLogWorkFlow.codingRemark}">
								<div class="row-container">
									<div class="element-block" style="width: 100%">
										<label>Coding Remarks:</label>
										${codingCorrectionLogWorkFlow.codingRemark}
									</div>
									<div class="clr"></div>
								</div>
							</c:if>
							<div class="row-container">
								<div class="element-block">
									<form:label path="nextAction">Next Action Step:<em class="mandatory">*</em></form:label>
									<span class="select-container1" style="display:inline;">
										<form:select path="nextAction" cssClass="selectbox">
											<form:option value="">-: Select Work flow :-</form:option>
											<form:option value="Escalate to Argus TL">Escalate to Argus TL</form:option>
											<form:option value="Forward to coding correction">Forward to coding correction</form:option>
											<form:option value="Return to AR">Return to AR</form:option>
											<form:option value="Coding to CE">Coding to CE</form:option>
											<form:option value="Done">Done</form:option>
										</form:select>
									</span>
									<form:errors cssClass="invalid" path="nextAction" />
								</div>
								<div class="clr"></div>
							</div>
							<div class="row-container">
								<div class="element-block" style="width: 100%">
									<label>&nbsp;</label>
									<input type="submit" title="Update" value="Update" class="login-btn" />
									<input type="button" title="Cancel" value="Cancel" class="login-btn" onclick="javascript:resetForm(this.form);" />
								</div>
								<div class="clr"></div>
							</div>
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
		</div>
		<input type="hidden" name="fileDeleted" id="fileDeleted" value=""/>
	</form:form>
</div>
<script type="text/javascript">
	function checkCodingRemark(){
		if($('#codingRemark') == null || $.trim($('#codingRemark').val())== ""){
			$('#codingRemark_validation').html ("Coding Remark can not be left empty.");
			return false;
		}else{
			$('#codingRemark_validation').html ("");
			return true;
		}
	}

	 function deleteFile(fileId)
		{
			if(confirm("Are you sure you wish to delete this Attachment?")){
	 		$("#file_row").hide('slow');
			$('#fileDeleted').val("true");
			}
		}
</script>
<script type="text/javascript" src="<c:url value='/static/resources/js/common.js'/>"></script>