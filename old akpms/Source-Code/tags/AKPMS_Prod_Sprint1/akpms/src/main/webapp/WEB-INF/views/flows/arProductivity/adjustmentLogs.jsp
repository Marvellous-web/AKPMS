<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li><a href="<c:url value='/flows/arProductivity' />">&nbsp;AR
					Productivity List</a> &raquo;</li>
			<li>&nbsp;${operationType} Adjustment Log Work Flow</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<form:form commandName="adjustmentLogs" id="form1"
		class="form-container">
		<div class="form-container-inner">
			<h3>${operationType} Adjustment Log Work Flow</h3>

			<input type="hidden" value="${arProductivity.id}"
				name="arProductivity.id">

			 <span class="input-container">
				<form:label path="remark">Remark:<em class="mandatory">*</em>
				</form:label> ${arProductivity.remark}
			</span>

			<c:if test="${showFinalRemark == 1}">
			 <span class="input-container">
				<form:label path="remark">Remark:<em class="mandatory">*</em>
				</form:label> ${remark}
			</span>
			</c:if>

			<c:choose>
			<c:when test="${empty adjustmentLogs.id}">
			 <span class="input-container" id="spanParent">
			  <form:label
					path="workFlowStatus">Work flow:<em class="mandatory">*</em>
				</form:label> <span class="select-container" style="display: inline;">
					<form:select path="workFlowStatus" cssClass="selectbox" disabled="${readOnly}">
						<form:option value="0">-: Select Work flow :-</form:option>
						<form:option value="1">Approve</form:option>
						<form:option value="2">Reject</form:option>
						<form:option value="3">Escalate</form:option>
					</form:select>
			</span>

			<form:errors class="invalid" path="workFlowStatus" />
			</span>
			</c:when>
				<c:otherwise>
					<span class="input-container" id="spanParent"> <form:label
							path="workFlowStatus">Work Flow:</form:label> <spring:message
							code="status.${initialStatus}"></spring:message>
					</span>
				</c:otherwise>
			</c:choose>

			 <br /> <br /> <span class="input-container"> <label>&nbsp;</label>
				<c:if test="${operationType == 'Add'}">
					<input type="submit" title="Save / Update Insurance"
						value="${buttonName}" class="login-btn" />
					<input type="button" title="Cancel" value="Cancel"
						class="login-btn" onclick="javascript:resetForm(this.form);" />

					<!-- <input type="button" title="Delete" value="Delete"
						class="login-btn"
						onclick="deleteInsurance(${insurance.id})" /> -->
				</c:if>
			</span>

		<c:if test="${operationType == 'Edit' && checkWorkFlow == 3}">
		<br/><br/>

			<span class="input-container">
				<form:label path="remark">Remark:<em class="mandatory">*</em></form:label>
				<form:textarea rows="8" cols="20" path="remark" />
				<form:errors class="invalid" path="remark" />
			</span>

				<span class="input-container" id="spanParent"> <form:label
						path="workFlowStatus">Status:<em class="mandatory">*</em>
					</form:label> <span class="select-container" style="display: inline;"> <form:select
							path="workFlowStatus" cssClass="selectbox">
							<form:option value="0">-: Select Work flow :-</form:option>
							<form:option value="1">Approve</form:option>
							<form:option value="2">Reject</form:option>
						</form:select>
				</span> <form:errors class="invalid" path="workFlowStatus" />
				</span> <br /> <br /> <span class="input-container"> <label>&nbsp;</label>
					<c:if test="${operationType == 'Edit'}">
						<input type="submit" title="Save / Update Insurance"
							value="${buttonName}" class="login-btn" />
						<input type="button" title="Cancel" value="Cancel"
							class="login-btn" onclick="javascript:resetForm(this.form);" />

						<!-- <input type="button" title="Delete" value="Delete"
						class="login-btn"
						onclick="deleteInsurance(${insurance.id})" /> -->
					</c:if>
				</span>
		</c:if>
		</div>
	</form:form>
</div>