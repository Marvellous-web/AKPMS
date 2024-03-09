<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="downloadAction">
	<c:url value='/flows/codingcorrectionlogworkflow/fileDownload' />
</c:set>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li><a href="<c:url value='/flows/arProductivity' />">&nbsp;AR Productivity Add</a>
				&raquo;</li>
			<li>Coding Correction Log WorkFlow</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<form:form commandName="codingCorrectionLogWorkFlow" id="form1" class="form-container" enctype="multipart/form-data">
		<div class="form-container-inner">
			<h3>${operationType} Coding Correction Log WorkFlow</h3>
			<span class="input-container">
				<label>Patient Name<em class="mandatory">*</em>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${codingCorrectionLogWorkFlow.arProductivity.patientName}</label>
			</span>
			<span class="input-container">
				<form:label path="batchNo">Batch number<em class="mandatory">*</em></form:label>
				<span class="input_text">
					<span class="left_curve"></span>
						<form:input path="batchNo" maxlength="200" cssClass="mid" cssStyle="width: 373px;"  readonly="${readOnly}"/>
					<span class="right_curve"></span>
				</span>
				<form:errors class="invalid" path="batchNo" />
			</span>

			<span class="input-container">
				<form:label path="sequenceNo">Sequence number<em class="mandatory">*</em></form:label>
				<span class="input_text">
					<span class="left_curve"></span>
						<form:input path="sequenceNo" maxlength="200" cssClass="mid" cssStyle="width: 373px;" readonly="${readOnly}" />
					<span class="right_curve"></span>
				</span>
				<span><form:errors class="invalid" path="sequenceNo" /></span>
			</span>

			<span class="input-container">
				<label id="remark">Remarks:</label>
				<textarea rows="8" cols="20" placeholder="${codingCorrectionLogWorkFlow.arProductivity.remark}" readonly="readonly" name="remarks" id="remarks"></textarea>
			</span>

			<span class="input-container">
				<form:label path="attachment">Attachment:<em class="mandatory">*</em></form:label>
						<span class="input_text">
							<form:input	type="file" path="attachment.attachedFile" size="44" cssClass="mid"/>
						</span>
						<form:errors class="invalid" path="attachment.attachedFile" />
			</span>
			<c:if test="${not empty codingCorrectionLogWorkFlow.id}">
			<form:label path="attachment">Attached File:<em class="mandatory">*</em></form:label>
			<div class="download-container">
			${codingCorrectionLogWorkFlow.attachment.name}<a class="download" href="${downloadAction}?id=${codingCorrectionLogWorkFlow.attachment.id}" title="Download File"></a>
			</div>
			</c:if>

			<input type="hidden" name="arProductivity.id" value="${arProductivity.id }">

			<c:if test="${empty codingCorrectionLogWorkFlow.id}">
			<span class="input-container">
				<label>&nbsp;</label>
				<input type="submit" title="Forward to coding correction"
				value="Forward to coding correction" class="login-btn" />
			</span>
			</c:if>

			<c:if test="${not empty codingCorrectionLogWorkFlow.id}">

			<span class="input-container">
				<label id="remark">Coding Remarks:</label>
			<form:textarea path="codingRemark" cols="20" rows="8"/>
			<form:errors class="invalid" path="codingRemark" />
			</span>

			<span class="input-container">
				<label>&nbsp;</label>
				<input type="submit" title="Return to AR"
				value="Return to AR" class="login-btn" />
			</span>
			</c:if>
		</div>
	</form:form>
</div>
