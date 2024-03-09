<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li><a href="<c:url value='/flows/arProductivity' />">&nbsp;AR Productivity List</a>	&raquo;</li>
			<li>Second Request Log WorkFlow</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<form:form commandName="secondRequestLogWorkFlow" id="form1" class="form-container">
		<div class="form-container-inner">
			<h3>${operationType} Second Request Log WorkFlow</h3>

			<span class="input-container">
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
				<label id="remark">Remarks/Denial:</label>	${arProductivity.remark}
			</span>

			<span class="input-container">
				<form:label path="pcp">PCP<em class="mandatory">*</em></form:label>
				<span class="input_text">
					<span class="left_curve"></span>
						<form:input path="pcp" maxlength="200" cssClass="mid" cssStyle="width: 373px;"  readonly="${readOnly}"/>
					<span class="right_curve"></span>
				</span>
				<%-- <form:errors class="invalid" path="pcp" /> --%>
				<span id="pcp_errors" class="invalid" style="display:none"></span>
			</span>

			<span class="input-container">
				<form:label path="regionalManager">Regional Manager<em class="mandatory">*</em>:</form:label>
				<span class="input_text">
					<span class="left_curve"></span>
						<form:input path="regionalManager" maxlength="200" cssClass="mid" cssStyle="width: 373px;" readonly="${readOnly}" />
					<span class="right_curve"></span>
				</span>
				<%-- <form:errors class="invalid" path="regionalManager" /> --%>
				<span id="regionalManager_errors" class="invalid" style="display:none"></span>
			</span>

			<span class="input-container">
				<label>Info Needed:<em class="mandatory">*</em></label>
				<form:textarea path="infoNeeded" cols="20" rows="5" />
				<span id="infoNeeded_errors" class="invalid"></span>
			</span>

			<input type="hidden" name="arProductivity.id" value="${arProductivity.id }">

			<c:choose>
				<c:when test="${empty secondRequestLogWorkFlow.id}">
					<span class="input-container"  >
						<form:label path="status">Work Flow:<em class="mandatory">*</em></form:label>
						<span class="select-container" style="display:inline;">
							<form:select path="status" cssClass="selectbox">
								<form:option value="0">-: Select Status:-</form:option>
								<form:option value="1">Done</form:option>
								<form:option value="2">Reject</form:option>
								<form:option value="3">Escalate</form:option>
							</form:select>
						</span>
						<%-- <form:errors class="invalid" path="status" /> --%>
						<span id="status_errors" class="invalid" style="display:none"></span>
					</span>
					<span class="input-container">
						<label>&nbsp;</label>
						<input type="submit" title="Update" value="Update" class="login-btn"  onclick="return validation('false');" />
						<input type="button" title="Cancel" value="Cancel" class="login-btn" onclick="javascript:resetForm(this.form);" />
					</span>
				</c:when>
				<c:otherwise>

					<c:choose>
						<c:when test="${secondRequestLogWorkFlow.status == 3 ||  !empty secondRequestLogWorkFlow.managerRemark }">

							<c:if test="${secondRequestLogWorkFlow.status == 3}">
								<span class="input-container"  >
									<form:label path="status">Work Flow:</form:label>
									<spring:message code="status.${secondRequestLogWorkFlow.status}"></spring:message>
								</span>
							</c:if>

							<span class="input-container">
								<label id="remark">Remarks:<em class="mandatory">*</em></label>
								<form:textarea path="managerRemark" cols="20" rows="5"/>
								<%-- <form:errors class="invalid" path="managerRemark" /> --%>
								<span id="managerRemark_errors" class="invalid" style="display:none"></span>
							</span>

							<span class="input-container"  >
								<form:label path="status">Status:<em class="mandatory">*</em></form:label>
								<span class="select-container" style="display:inline;">
									<form:select path="status" cssClass="selectbox">
										<form:option value="0">-: Select Status:-</form:option>
										<form:option value="1">Done</form:option>
										<form:option value="2">Reject</form:option>
									</form:select>
								</span>
								<%-- <form:errors class="invalid" path="status" /> --%>
								<span id="status_errors" class="invalid" style="display:none"></span>
							</span>

							<span class="input-container">
								<label>&nbsp;</label>
								<input type="submit" title="Update" value="Update" class="login-btn"  onclick="return validation('true');" />
								<input type="button" title="Cancel" value="Cancel" class="login-btn" onclick="javascript:resetForm(this.form);" />
							</span>
						</c:when>
						<c:otherwise>
							<span class="input-container">
								<form:label path="status">Work Flow:<em class="mandatory">*</em></form:label>
								<span class="select-container" style="display:inline;">
									<form:select path="status" cssClass="selectbox">
										<form:option value="0">-: Select Status:-</form:option>
										<form:option value="1">Done</form:option>
										<form:option value="2">Reject</form:option>
										<form:option value="3">Escalate</form:option>
									</form:select>
								</span>
								<%-- <form:errors class="invalid" path="status" /> --%>
								<span id="status_errors" class="invalid" style="display:none"></span>
							</span>

							<span class="input-container">
								<label>&nbsp;</label>
								<input type="submit" title="Update" value="Update" class="login-btn"  onclick="return validation('false');" />
								<input type="button" title="Cancel" value="Cancel" class="login-btn" onclick="javascript:resetForm(this.form);" />
							</span>

						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
		</div>
	</form:form>
</div>
<script type="text/javascript">
	function validation(validateRemark){
		frmSubmit = true;

		if ($.trim($('#pcp').val()) == "") {
			$("#pcp_errors").text("PCP cannot be left blank.");
			$("#pcp_errors").css("display", "block");
			frmSubmit = false;
		}else{
			$("#pcp_errors").css("display", "none");
		}

		if ($.trim($('#regionalManager').val()) == "") {
			$("#regionalManager_errors").text("Regional Manager cannot be left blank.");
			$("#regionalManager_errors").css("display", "block");
			frmSubmit = false;
		}else{
			$("#regionalManager_errors").css("display", "none");
		}

		if ($.trim($('#infoNeeded').val()) == "") {
			$("#infoNeeded_errors").text("Info Needed cannot be left blank.");
			$("#infoNeeded_errors").css("display", "block");
			frmSubmit = false;
		}else{
			$("#infoNeeded_errors").css("display", "none");
		}

		if($("#status").val() == 0){
			$("#status_errors").text("Status cannot be left blank.");
			$("#status_errors").css("display", "block");
			frmSubmit = false;
		}else{
			$("#status_errors").css("display", "none");
		}

		if(validateRemark == 'true'){
			if ($.trim($('#managerRemark').val()) == "") {
				$("#managerRemark_errors").text("Manager Remark cannot be left blank.");
				$("#managerRemark_errors").css("display", "block");
				frmSubmit = false;
			}else{
				$("#managerRemark_errors").css("display", "none");
			}
		}

		return frmSubmit;
	}
</script>
