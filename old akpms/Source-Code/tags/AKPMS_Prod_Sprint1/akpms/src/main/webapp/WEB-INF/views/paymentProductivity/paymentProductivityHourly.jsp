<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li>&nbsp;${operationType} Hourly</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->

	<form:form commandName="paymentProductivityHourly" id="form2" action="add" class="form-container" >
		<div class="form-container-inner">
		<h3>${operationType} Hourly</h3>
		<div id="msg" class="error">${error}</div>
			<div class="row-container">
				<label>Date:</label>
					<c:choose>
						<c:when test="${not empty paymentProductivityHourly.id}">
							<fmt:formatDate value="${paymentProductivityHourly.createdOn}" pattern="MM/dd/yyyy"/>
						</c:when>
						<c:otherwise>
							<fmt:formatDate value="${currentDate}" pattern="MM/dd/yyyy"/>
						</c:otherwise>
					</c:choose>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<form:label path="time">Time(Hours)<em class="mandatory">*</em></form:label>
				<div class="input_text">
					<span class="left_curve"></span>
					<form:input path="time" maxlength="7" cssClass="mid numbersOnly" cssStyle="width: 75px;"  />
					<span class="right_curve"></span>
				</div>
				<form:errors class="invalid" path="time" />
				<div class="clr"></div>
			</div>

			<div class="row-container" >
				<form:label path="taskName.id">Task Name:<em class="mandatory">*</em></form:label>
				<div>
					<form:select id="taskName" path="taskName.id" cssClass="selectbox" onchange="changeDetail();">
							<form:option value="0">-: Select Task Name:-</form:option>
							<form:options items="${taskNameList}" itemValue="id" itemLabel="name" />
					</form:select>
				</div>
				<form:errors class="invalid" path="taskName.id" />
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<form:label path="detail" id="detail">Detail:<em class="mandatory">*</em></form:label>
				<form:textarea rows="5" cols="20" path="detail" />
				<div class="clr"></div>
				<form:errors class="invalid" path="detail" />
				<div class="clr"></div>
			</div>

			<br/>
			<br/>
			<div class="row-container">
			 <label>&nbsp;</label>
				<input type="submit" title="Save"
				value="${buttonName}" class="login-btn" /> <input type="button"
				title="Cancel" value="Cancel" class="login-btn"  onclick="javascript:resetForm(this.form);" />
			</div>


		</div>
	</form:form>
</div>
<script type="text/javascript">
function changeDetail(){
	if($("#taskName").val()>0)
		{
			var taskName = $("#taskName option:selected").text();
			$("#detail").html(taskName+' detail <em class="mandatory">*</em>');
		}
	else{
			$("#detail").html('Detail <em class="mandatory">*</em>');
		}
}
changeDetail();
</script>
<script type="text/javascript" src="<c:url value='/static/resources/js/common.js'/>"></script>
