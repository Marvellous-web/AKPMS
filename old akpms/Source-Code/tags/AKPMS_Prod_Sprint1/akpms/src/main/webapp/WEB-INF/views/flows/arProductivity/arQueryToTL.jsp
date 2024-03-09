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

	<form:form commandName="arProductivity" id="form1" class="form-container">

		<div class="form-container-inner">
			<h3>Query To TL</h3>
			<div class="row-container">
				<div class="element-block">
					<span class="lbl">Patient Name:</span>${arProductivity.patientName}
				</div>
				<div class="element-block">
					<span class="lbl">Account Number:</span>${arProductivity.patientAccNo}
				</div>
				<div class="clr"></div>
			</div>
			<div class="row-container">
				<div class="element-block">
					<span class="lbl">Insurance:</span>${arProductivity.insurance.name}
				</div>
				<div class="element-block">
					<span class="lbl">DOS:</span>${arProductivity.dos}
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<form:label path="remark">Remark</form:label>
				<form:textarea rows="5" cols="20" path="remark" readonly="readonly"/>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<form:label path="tlRemark">TL Remark<em class="mandatory">*</em>
				</form:label>
				<form:textarea rows="5" cols="20" path="tlRemark" />
				<form:errors class="invalid" path="tlRemark" />
				<div class="clr"></div>
			</div>
			<span class="input-container"> <label>&nbsp;</label>
			 <input type="submit" title="Query To TL" value="Query To TL"
				class="login-btn" />
			</span>
		</div>
	</form:form>
	</div>
