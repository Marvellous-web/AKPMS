<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li><a href="<c:url value='/chargebatchprocess' />">&nbsp;Charge Batch Processing List</a>&raquo;</li>
			<li>&nbsp;${operationMode} Charge Batch</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<c:if test="${operationType == 'Edit'}">
		<div class="section-content">
			<h3 class="heading-h3">Charge Batch Details (Doctor Office)</h3>
			<div class="row-container">
				<div class="element-block" >
			    	<span  class="lbl">Type:</span> <span>${chargeBatchProcess.typeValue}</span>
			    </div>
			    <div class="element-block" >
			    	<span  class="lbl">Provider Name:</span> <span>${chargeBatchProcess.doctor.name}</span>
			    </div>
		    	<div class="clr"></div>
			</div>

			<div class="row-container">
			    <div class="element-block" >
			    	<span class="lbl">Date Of Service(From):</span> <span>${chargeBatchProcess.dateFrom}</span>
			    </div>
			    <div class="element-block" >
			    	<span class="lbl">Date Of Service(To):</span> <span>${chargeBatchProcess.dateTo}</span>
			    </div>
		    	<div class="clr"></div>
		    </div>

		    <div class="row-container">
			    <div class="element-block" >
			    	<span class="lbl">Number Of Super Bills:</span> <span>${chargeBatchProcess.numberOfSuperbills}</span>
			    </div>
			    <div class="element-block" >
			    	<span class="lbl">Number Of Attachments:</span> <span>${chargeBatchProcess.numberOfAttachments}</span>
			    </div>
		    	<div class="clr"></div>
		    </div>

		    <div class="row-container">
			    <div class="element-block" >
			    	<c:set var="noOfPages" value="${chargeBatchProcess.numberOfSuperbills + chargeBatchProcess.numberOfAttachments}" />
			    	<span class="lbl">Total no. of Pages:</span> <span>${noOfPages}</span>
			    </div>
		    	<div class="clr"></div>
		    </div>

		    <div class="row-container">
			    <div class="element-block" style="width: 100%">
			    	<span class="lbl">Remarks:</span> <span>${chargeBatchProcess.remarks}</span>
			    </div>
		    	<div class="clr"></div>
		    </div>

		    <div class="row-container">
			    <div class="element-block" >
			    	<span class="lbl">Prepared By:</span> <span>${chargeBatchProcess.createdBy.firstName} ${chargeBatchProcess.createdBy.lastName}</span>
			    </div>
			    <div class="element-block" >
			    	<span class="lbl">Prepared Date:</span> <span><fmt:formatDate value="${chargeBatchProcess.createdOn}" pattern="MM/dd/yyyy"/></span>
			    </div>
		    	<div class="clr"></div>
		    </div>

		    <c:if test="${not empty chargeBatchProcess.modifiedBy}">
			    <div class="row-container">
				    <div class="element-block" >
				    	<span class="lbl">Updated By:</span> <span>${chargeBatchProcess.modifiedBy.firstName} ${chargeBatchProcess.modifiedBy.lastName}</span>
				    </div>
				    <div class="element-block" >
				    	<span class="lbl">Updated Date:</span> <span><fmt:formatDate value="${chargeBatchProcess.modifiedOn}" pattern="MM/dd/yyyy"/></span>
				    </div>
			    	<div class="clr"></div>
			    </div>
		    </c:if>
		    <c:if test="${empty chargeBatchProcess.postedBy}">
			    <div class="row-container">
				    <div class="element-block" >
			    		<input type="button" class="btn" value="Edit" onclick="javascript:editDoctorPart();" />
				    </div>
			    	<div class="clr"></div>
			    </div>
		    </c:if>
		</div>
		<c:if test="${not empty chargeBatchProcess.postedBy}">
			<div class="section-content">
				<h3 class="heading-h3">Reject log for batch</h3>
				<div class="row-container">
				    <div class="element-block" >
				    	<span class="lbl">1st Request Due: </span> <span>${firstRequestDueCount}</span>
				    </div>
				    <div class="element-block" >
				    	<span class="lbl">2nd Request Due: </span> <span>${secondRequestDueCount}</span>
				    </div>
			    	<div class="clr"></div>
			    </div>
			    <div class="row-container">
				    <div class="element-block" >
				    	<span class="lbl">Number Of First Requests: </span> <span>${numberOfFirstRequestsCount}</span>
				    </div>
				    <div class="element-block" >
				    	<span class="lbl">Number Of Second Requests: </span> <span>${numberOfSecondRequestsCount}</span>
				    </div>
			    	<div class="clr"></div>
			    </div>
			    <div class="row-container">
				    <div class="element-block" >
				    	<span class="lbl">New Rejections: </span> <span>${rejectionCount}</span>
				    </div>
				    <div class="element-block" >
				    	<span class="lbl">Resolved Rejections: </span> <span>${resolvedRejectionCount}</span>
				    </div>
			    	<div class="clr"></div>
			    </div>
			    <div class="row-container">
				    <div class="element-block" >
				    	<span class="lbl">Resolved Rejections with dummy CPT: </span> <span>${resolvedRejectionWithDummyCPTCount}</span>
				    </div>
			    	<div class="clr"></div>
			    </div>
			</div>
		</c:if>
	</c:if>

	<c:if test="${not empty chargeBatchProcess.postedBy}">
		<div class="section-content">
			<h3 class="heading-h3">Charge Batch Details (Argus Office)</h3>
			<div class="row-container">
			    <div class="element-block" >
			    	<span class="lbl">Number Of Super Bills:</span> <span>${chargeBatchProcess.numberOfSuperbillsArgus}</span>
			    </div>
			    <div class="element-block" >
			    	<span class="lbl">Number Of Attachments:</span> <span>${chargeBatchProcess.numberOfAttachmentsArgus}</span>
			    </div>
		    	<div class="clr"></div>
		    </div>

		    <div class="row-container">
			    <div class="element-block" >
			    	<c:set var="noOfPagesArgus" value="${chargeBatchProcess.numberOfSuperbillsArgus + chargeBatchProcess.numberOfAttachmentsArgus}" />
			    	<span class="lbl">Total no. of Pages:</span> <span>${noOfPagesArgus}</span>
			    </div>
		    	<div class="clr"></div>
		    </div>

		    <div class="row-container">
			    <div class="element-block" style="width: 100%">
			    	<span class="lbl">Remarks:</span> <span>${chargeBatchProcess.remarksArgus}</span>
			    </div>
		    	<div class="clr"></div>
		    </div>

		    <div class="row-container">
			    <div class="element-block" >
			    	<span class="lbl">Received By:</span> <span>${chargeBatchProcess.receviedBy.firstName} ${chargeBatchProcess.receviedBy.lastName}</span>
			    </div>
			    <div class="element-block" >
			    	<span class="lbl">Received Date:</span> <span><fmt:formatDate value="${chargeBatchProcess.dateReceived}" pattern="MM/dd/yyyy"/></span>
			    </div>
		    	<div class="clr"></div>
		    </div>
			<div class="row-container">
				<div class="element-block" >
			    	<span  class="lbl">Posted By:</span> <span>${chargeBatchProcess.postedBy.firstName} ${chargeBatchProcess.postedBy.lastName}</span>
			    </div>
			    <div class="element-block" >
			    	<span  class="lbl">CT Date Posted:</span> <span><fmt:formatDate value="${chargeBatchProcess.dateBatchPosted}" pattern="MM/dd/yyyy"/></span>
			    </div>
		    	<div class="clr"></div>
			</div>
		</div>
	</c:if>

	<form:form commandName="chargeBatchProcess" id="form1" >
		<c:if test="${operationType == 'Add'}">
			<div class="form-container">
				<div class="form-container-inner">
					<h3>Type<em class="mandatory">*</em></h3>
					<div class="row-container">
						<table style="width: 70%">
							<tr>
								<c:forEach var="type" items="${typeProperty}" varStatus="theCount">
									<td><form:radiobutton path="type" value="${type.key}" /> ${type.value}</td>
									<c:if test="${(theCount.index+1) % 4 eq 0}">
										</tr><tr>
									</c:if>
								</c:forEach>
							</tr>
						</table>
						<form:errors class="invalid" path="type" cssStyle="margin-left:0px;" />
						<div class="clr"></div>
					</div>
					<br/>
					<h3>Doctor Office</h3>
					<div class="row-container">
						<form:label path="doctor.id">Provider Name:<em class="mandatory">*</em></form:label>
						<span class="select-container" style="display:inline;">
							<form:select path="doctor.id" cssClass="selectbox" id="doctorId">
								<form:option value="">-: Select Provider Name :-</form:option>
								<form:options items="${doctorList}" itemValue="id"
									itemLabel="name" />
							</form:select>
						</span>
						<form:errors class="invalid" path="doctor" />
						<div class="clr"></div>
					</div>

					<div class="row-container">
						<div class="element-block">
							<form:label path="dosFrom">Date of Service(From):<em class="mandatory">*</em></form:label>
							<span class="input_text">
								<span class="left_curve"></span>
									<form:input path="dosFrom" readonly="true" maxlength="50" cssClass="mid" cssStyle="width: 90px;" />
								<span class="right_curve"></span>
							</span>
							<form:errors class="invalid" path="dosFrom" />
						</div>
						<div class="element-block">
							<form:label path="dosTo">Date of Service(To):<em class="mandatory">*</em></form:label>
							<span class="input_text">
								<span class="left_curve"></span>
									<form:input path="dosTo" readonly="true" maxlength="50" cssClass="mid" cssStyle="width: 90px;"  />
								<span class="right_curve"></span>
							</span>
							<form:errors class="invalid" path="dosTo" />
						</div>
						<div class="clr"></div>
					</div>

					<div class="row-container">
						<div class="element-block">
							<form:label path="numberOfSuperbills">Number of Super Bills:<em class="mandatory">*</em></form:label>
							<span class="input_text">
								<span class="left_curve"></span>

									<form:input path="numberOfSuperbills" maxlength="8" cssClass="mid integerOnly" cssStyle="width: 90px;"  />

								<span class="right_curve"></span>
							</span>
							<form:errors class="invalid" path="numberOfSuperbills" />
						</div>
						<div class="element-block">
							<form:label path="numberOfAttachments">Number of Attachments:<em class="mandatory">*</em></form:label>
							<span class="input_text">
								<span class="left_curve"></span>

									<form:input path="numberOfAttachments" maxlength="8" cssClass="mid integerOnly" cssStyle="width: 90px;"  />
								<span class="right_curve"></span>
							</span>
							<form:errors class="invalid" path="numberOfAttachments" />
						</div>
						<div class="clr"></div>
					</div>


					<%-- <span class="input-container">
						<form:label path="batchAmount">Total Amount Of Batch (in USD):<em class="mandatory">*</em></form:label>
						<span class="input_text">
							<span class="left_curve"></span>
								<form:input path="batchAmount" maxlength="50" cssClass="mid numbersOnly" cssStyle="width: 373px;"  />
							<span class="right_curve"></span>
						</span>
						<form:errors class="invalid" path="batchAmount" />
					</span> --%>

					<div class="row-container">
						<form:label path="remarks">Remark:</form:label>
						<form:textarea rows="5" cols="20" path="remarks" />
						<div class="clr"></div>
					</div>

					<div class="row-container">
						<label>&nbsp;</label>
						<input type="submit" title="Submit" value="Submit" class="login-btn" onclick="javascript:disabledButtons();this.form.submit();"/>
						<input type="button" title="Cancel" value="Cancel" class="login-btn"  onclick="javascript:resetForm(this.form);" />
						<div class="clr"></div>
					</div>
				</div>
			</div>
		</c:if>

		<c:if test="${operationType == 'Edit' && empty chargeBatchProcess.postedBy}">
			<input type="hidden" name="mode" value="update" id="mode" />
			<div class="form-container">
				<div class="form-container-inner">
					<h3>Argus Coding Department</h3>

					<div class="row-container">
						<form:label path="dateReceived">Date Received:<em class="mandatory">*</em></form:label>
						<span class="input_text">
							<span class="left_curve"></span>
							<form:input path="dateReceived" readonly="true" maxlength="50" cssClass="mid" cssStyle="width: 90px;"  />
							<span class="right_curve"></span>
						</span>
						<form:errors class="invalid" path="dateReceived" />
						<div class="clr"></div>
					</div>

					<div class="row-container">
						<form:label path="receviedBy.id">Received By:<em class="mandatory">*</em></form:label>
						<span class="select-container" style="display:inline;">
							<form:select path="receviedBy.id" cssClass="selectbox" id="receviedById">
								<form:option value="">-: Select Received By :-</form:option>
								<form:options items="${receivedByUsers}" itemValue="id" itemLabel="firstName" />
							</form:select>
						</span>
						<form:errors class="invalid" path="receviedBy" />
						<div class="clr"></div>
					</div>

					<div class="row-container">
						<div class="element-block">
							<form:label path="numberOfSuperbillsArgus">Number of Super Bills:<em class="mandatory">*</em></form:label>
							<span class="input_text">
								<span class="left_curve"></span>

								<form:input path="numberOfSuperbillsArgus" maxlength="8" cssClass="mid integerOnly" cssStyle="width: 90px;"  />

								<span class="right_curve"></span>
							</span>
							<form:errors class="invalid" path="numberOfSuperbillsArgus" />
						</div>
						<div class="element-block">
							<form:label path="numberOfAttachmentsArgus">Number of Attachments:<em class="mandatory">*</em></form:label>
							<span class="input_text">
								<span class="left_curve"></span>

								<form:input path="numberOfAttachmentsArgus" maxlength="8" cssClass="mid integerOnly" cssStyle="width: 90px;"  />

								<span class="right_curve"></span>
							</span>
							<form:errors class="invalid" path="numberOfAttachmentsArgus" />
						</div>
						<div class="clr"></div>
					</div>

				<%-- <span class="input-container">
					<form:label path="batchAmountArgus">Batch Amount(in USD:<em class="mandatory">*</em></form:label>
					<span class="input_text">
						<span class="left_curve"></span>
							<form:input path="batchAmountArgus" maxlength="50" cssClass="mid numbersOnly" cssStyle="width: 373px;"  />
						<span class="right_curve"></span>
					</span>
					<form:errors class="invalid" path="batchAmountArgus" />
				</span> --%>

					<div class="row-container">
						<form:label path="remarks">Remark:</form:label>
						<form:textarea rows="5" cols="20" path="remarksArgus" />
					</div>

					<div class="row-container">
						<label>&nbsp;</label>
						<input type="submit" id="btnUpdate" title="Update" value="Update" class="login-btn" onclick="javascript:disabledButtons();this.form.submit();" />
						<input type="button" title="Cancel" value="Cancel" class="login-btn"  onclick="javascript:resetForm(this.form);" />
						<div class="clr"></div>
					</div>
				</div>
			</div>
		</c:if>

		<sec:authorize ifAnyGranted="P-10">
			<c:if test="${(operationType == 'Edit') && (not empty chargeBatchProcess.postedBy)}">
				<input type="hidden" name="mode" value="reupdate" id="mode" />
				<input type="hidden" name="receivedById" value="${chargeBatchProcess.receviedBy.id}" />
				<div class="form-container">
					<div class="form-container-inner">
						<div class="row-container">
							<div class="element-block">
								<form:label path="postedBy.id">Posted By:<em class="mandatory">*</em></form:label>
								<span class="select-container2" style="display:inline;">
									<form:select path="postedBy.id" cssClass="selectbox" id="reupdatePostedBy">
										<form:options items="${postedByUsers}" itemValue="id"	itemLabel="firstName" />
									</form:select>
								</span>
								<form:errors class="invalid" path="postedBy" />
							</div>
							<div class="element-block">
								<form:label path="dateBatchPosted">CT Date Posted:<em class="mandatory">*</em></form:label>
								<span class="input_text">
									<span class="left_curve"></span>
										<form:input path="dateBatchPosted" readonly="true" maxlength="50" cssClass="mid" cssStyle="width: 90px;" />
									<span class="right_curve"></span>
								</span>
								<form:errors class="invalid" path="dateBatchPosted" />
							</div>
							<!--<form:errors class="invalid" path="receviedBy" />-->
							<div class="clr"></div>
						</div>
						<div class="row-container">
							<label>&nbsp;</label>
							<input type="submit" id="btnUpdate" title="Re-Update" value="Re-Update" class="login-btn" onclick="javascript:disabledButtons();this.form.submit();" />
							<input type="button" title="Cancel" value="Cancel" class="login-btn"  onclick="javascript:resetForm(this.form);" />
							<div class="clr"></div>
						</div>
					</div>
				</div>
				<script language="text/javascript">
					$('#reupdatePostedBy').val(${chargeBatchProcess.postedBy});
				</script>
			</c:if>
		</sec:authorize>
	</form:form>
</div>
<script type="text/javascript">
$(function() {
    $( "#dosFrom").datepicker();
    $( "#dosTo").datepicker();
    $( "#dateReceived").datepicker();
    $( "#dateBatchPosted" ).datepicker();
});
function editDoctorPart(){
	chargeBatchId = '${chargeBatchProcess.id}';
	url = contextPath +"/chargebatchprocess/add?id="+chargeBatchId+"&operationType=0";
	window.location = url;
}
</script>
<script type="text/javascript" src="<c:url value='/static/resources/js/common.js'/>"></script>
