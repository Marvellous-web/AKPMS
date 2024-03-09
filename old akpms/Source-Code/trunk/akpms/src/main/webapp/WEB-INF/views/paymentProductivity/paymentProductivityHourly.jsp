<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li><a href="<c:url value='/paymentproductivityhourly' />">Hourly List</a> &raquo;</li>
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
				<form:label path="hours"></form:label>
				<div class="input_text">
					<span></span>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>Hours</strong>
					<span></span>
				</div>
				<div class="input_text">
					<span></span>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>Mins</strong>
					<span></span>
				</div>
				<div class="input_text">
					<span></span>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>Total</strong>
					<span></span>
				</div>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<form:label path="hours">Time(Hours)<em class="mandatory">*</em></form:label>
				<div class="input_text">
					<span class="left_curve"></span>
					<form:input path="hours" maxlength="7" cssClass="mid integerOnly" cssStyle="width: 75px;"  onblur="calculateTime()" onchange="calculateTime()"/>
					<span class="right_curve"></span>
				</div>
				<div class="input_text">
					<span class="left_curve"></span>
					<form:input path="minutes" maxlength="7" cssClass="mid integerOnly" cssStyle="width: 75px;"  onblur="calculateTime()" onchange="calculateTime()"/>
					<span class="right_curve"></span>
				</div>
				&nbsp;&nbsp;&nbsp;&nbsp;<span id="calculatedTime"> </span>
				<form:errors class="invalid" path="hours" />
				<div class="clr"></div>
			</div>

			<div class="row-container" >
				<form:label path="hourlyTask.id">Task Name:<em class="mandatory">*</em></form:label>
				<div>
					<form:select id="taskName" path="hourlyTask.id" cssClass="selectbox" onchange="changeDetail();">
							<%-- <form:option value="0">-: Select Task Name:-</form:option>
							<form:options items="${taskNameList}" itemValue="id" itemLabel="[department.name] name" /> --%>
							<form:option value="">-: Select Task Name:-</form:option>
							<c:forEach items="${taskNameList}" var="task">
								<c:choose>
									<c:when test="${not empty task.department}">
										<form:option value="${task.id}">[ ${task.department.name} ] ${task.name}</form:option>
									</c:when>
									<c:otherwise>
										<form:option value="${task.id}">${task.name}</form:option>										
									</c:otherwise>
								</c:choose>
							</c:forEach>
					</form:select>
				</div>
				<form:errors class="invalid" path="hourlyTask.id" />
				<div class="clr"></div>
			</div>
			
			<div class="row-container">
				<div class="element-block">
					<form:label path="dateReceived">Date Received</form:label>
					<span class="input_text">
						<span class="left_curve"></span>
							<form:input path="dateReceived" cssClass="mid" cssStyle="width: 100px;"  readonly="true"/>
						<span class="right_curve"></span>
					</span>
					<form:errors cssClass="invalid" path="dateReceived" />
				</div>
				<div class="element-block">
					<form:label path="taskCompleted">Task Completed</form:label>
					<span class="input_text">
						<span class="left_curve"></span>
							<form:input path="taskCompleted" cssClass="mid" cssStyle="width: 100px;" readonly="true" />
						<span class="right_curve"></span>
					</span>
					<form:errors cssClass="invalid" path="taskCompleted" />
				</div>
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
				title="Cancel" value="Cancel" class="login-btn"  onclick="javascript:resetForm(this.form);calculateTime();" />
			</div>


		</div>
	</form:form>
</div>
<script type="text/javascript">

var hours = 0.0;
var mins = 0.0;

function changeDetail(){

	$(function() {
	    $( "#dateReceived" ).datepicker();
	    $( "#taskCompleted" ).datepicker();
	});
	
	if($("#taskName").val()>0)
		{
			var taskName = $("#taskName option:selected").text();
			$("#detail").html(taskName+' detail <em class="mandatory">*</em>');
		}
	else{
			$("#detail").html('Detail <em class="mandatory">*</em>');
		}
}

function calculateTime(){
	hours = 0.0;
	mins = 0.0;
	if(!isNaN($("#hours").val()) && $("#hours").val()!=""){
		hours = $("#hours").val();
	}
	
	if(!isNaN($("#minutes").val()) && $("#minutes").val()!=""){
		mins = $("#minutes").val();
	}
	
	if(hours!=0 || mins!=0){
	//var id = '${paymentProductivityHourly.id}';
		var convertedMins = parseFloat(mins/60);
		$("#calculatedTime").html('<strong>= '+(parseInt(hours)+convertedMins).toFixed(2)+' hours </strong>');
	}else{
		$("#calculatedTime").html('');
	}	
}

calculateTime();
changeDetail();
</script>
<script type="text/javascript" src="<c:url value='/static/resources/js/common.js'/>"></script>
