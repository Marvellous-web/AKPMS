<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
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
	<form:form commandName="secondRequestLogWorkFlow" id="form1" class="form-container">
		<div class="form-container-inner">
			<h3>${operationType} Coding Correction Log WorkFlow</h3>
			<span class="input-container">
				<form:label path="pcp">PCP<em class="mandatory">*</em></form:label>
				<span class="input_text">
					<span class="left_curve"></span>
						<form:input path="pcp" maxlength="200" cssClass="mid" cssStyle="width: 373px;"  readonly="${readOnly}"/>
					<span class="right_curve"></span>
				</span>
				<form:errors class="invalid" path="pcp" />
			</span>

			<span class="input-container">
				<form:label path="regionalManager">Regional Manager<em class="mandatory">*</em></form:label>
				<span class="input_text">
					<span class="left_curve"></span>
						<form:input path="regionalManager" maxlength="200" cssClass="mid" cssStyle="width: 373px;" readonly="${readOnly}" />
					<span class="right_curve"></span>
				</span>
				<form:errors class="invalid" path="regionalManager" />
			</span>

			<span class="input-container">
				<label id="remark">Remarks/Denial:</label>
				<textarea rows="8" cols="20" placeholder="${arProductivity.remark}" readonly="readonly" name="remarks" id="remarks"></textarea>
			</span>

			<span class="input-container">
				<label id="infoNeeded">Info Needed:</label>
			<form:textarea path="infoNeeded" cols="20" rows="8" readonly="${readOnly}"/>
			<form:errors class="invalid" path="infoNeeded" />
			</span>

			<input type="hidden" name="arProductivity.id" value="${arProductivity.id }">

			<c:choose>
			<c:when test="${empty secondRequestLogWorkFlow.id}">

			<span class="input-container" id="spanParent" >
				<form:label path="status">Work Flow:</form:label>
				<span class="select-container" style="display:inline;">
					<form:select path="status" cssClass="selectbox">
						<form:option value="0">-: Select Status:-</form:option>
						<form:option value="1">Done</form:option>
						<form:option value="2">Reject</form:option>
						<form:option value="3">Escalate</form:option>
					</form:select>
				</span>
				<form:errors class="invalid" path="status" />
			</span>
			</c:when>
			<c:otherwise>
			<span class="input-container" id="spanParent" >
			<form:label path="status">Work Flow:</form:label>
			<spring:message code="status.${initialStatus}"></spring:message>
			</span>
			</c:otherwise>
			</c:choose>


			<c:if test="${empty secondRequestLogWorkFlow.id}">
			<span class="input-container">
				<label>&nbsp;</label>
				<input type="submit" title="Submit"
				value="Submit" class="login-btn" />
			</span>
			</c:if>

			<c:if test="${not empty secondRequestLogWorkFlow.id && initialStatus == 3}">

			<span class="input-container">
				<label id="remark">Remarks:</label>
			<form:textarea path="managerRemark" cols="20" rows="8"/>
			<form:errors class="invalid" path="managerRemark" />
			</span>

			<span class="input-container" id="spanParent" >
				<form:label path="status">Status:<em class="mandatory">*</em></form:label>
				<span class="select-container" style="display:inline;">
					<form:select path="status" cssClass="selectbox">
						<form:option value="0">-: Select Status:-</form:option>
						<form:option value="1">Done</form:option>
						<form:option value="2">Reject</form:option>
					</form:select>
				</span>
				<form:errors class="invalid" path="status" />
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
