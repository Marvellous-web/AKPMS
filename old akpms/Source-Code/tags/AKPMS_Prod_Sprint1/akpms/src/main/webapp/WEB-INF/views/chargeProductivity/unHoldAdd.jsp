<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li>&nbsp;<a href="<c:url value='/chargeproductivity/list' />">Charge Productivity List</a> &raquo; </li>
			<li>&nbsp;On Hold Process</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<div class="section-content">
		<h3 class="heading-h3">Charge Batch Details</h3>
		<div class="row-container">
			<div class="element-block">
				<span  class="lbl">Provider Name:</span> <span id="providerName">${chargeProductivity.ticketNumber.doctor.name}</span>
			</div>
			<div class="element-block">
			<span  class="lbl">Batch Type:</span> <span id="type"><spring:message code ="${chargeProductivity.ticketNumber.type}" /></span>
			</div>
			<div class="clr"></div>
		</div>

		<div class="row-container">
			<div class="element-block">
				<span  class="lbl">Date of Service From:</span> <span id="dosFrom"><fmt:formatDate value="${chargeProductivity.ticketNumber.dosFrom}" pattern="MM/dd/yyyy"/></span>
			</div>
			<div class="element-block">
				<span  class="lbl">Date of Service To:</span> <span id="dosTo"><fmt:formatDate value="${chargeProductivity.ticketNumber.dosTo}" pattern="MM/dd/yyyy"/></span>
			</div>
			<div class="clr"></div>
		</div>
		<div class="row-container">
			<div class="element-block">
				<span  class="lbl">Posting Date:</span> <span id="postDate"><fmt:formatDate value="${chargeProductivity.postDate}" pattern="MM/dd/yyyy"/></span>
			</div>
			<div class="element-block">
				<span  class="lbl">Number of Transactions:</span> <span id="type">${chargeProductivity.numberOfTransactions}</span>
			</div>
			<div class="clr"></div>
		</div>
		<div class="row-container">
			<div class="element-block">
				<span  class="lbl">Productivity Type:</span> <span id="type">${chargeProductivity.productivityType}</span>
			</div>
			<div class="element-block">
				<span  class="lbl">Remarks:</span> <span id="type">${chargeProductivity.remarks}</span>
			</div>
			<div class="clr"></div>
		</div>
	</div>
	<form:form commandName="chargeProductivity" id="form1">
		<div class="form-container" id="divProductivityForm" >
			<div class="form-container-inner">
				<c:if test="${onHoldBy != null}">
					<div class="row-container">
						<div class="element-block">
							<span  class="lbl">Un Hold On:</span> <span id="onHoldOn"><fmt:formatDate value="${chargeProductivity.onHoldOn}" pattern="MM/dd/yyyy"/></span>
						</div>
						<div class="element-block">
							<span  class="lbl">Un Hold By:</span> <span id="type">${onHoldBy.firstName}  ${onHoldBy.lastName}</span>
						</div>
						<div class="clr"></div>
					</div>
				</c:if>
				<c:choose>
					<c:when test="${readOnly != 'readOnly'}">
						<div class="row-container">
							<div class="input_text">
								<form:label path="unholdRemarks">Un Hold Remarks:<em class="mandatory">*</em></form:label>
								<form:textarea path="unholdRemarks" cols="30" rows="5" />
							</div>
							<div class="clr"></div>
							<form:errors class="invalid" path="unholdRemarks" />
							<div class="clr"></div>
						</div>

		         	 	<form:hidden path="onHoldFlag" value = "true"/>
						<div class="row-container">
							<label>&nbsp;</label>
							<input type="submit" title="Done" value="Done" class="login-btn" onclick="javascript:disabledButtons();this.form.submit();" />
							<div class="clr"></div>
						</div>
					</c:when>
					<c:otherwise>
						<div class="element-block">
							<span  class="lbl">Un Hold Remarks:</span> <span id="type">${chargeProductivity.unholdRemarks}</span>
					    </div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</form:form>
</div>
