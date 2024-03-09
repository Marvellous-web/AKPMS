<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li><a href="<c:url value='/admin/hourlytask' />">&nbsp;Hourly Task List</a>
				&raquo;</li>
			<li>&nbsp;${operationType} Hourly Task</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<form:form commandName="hourlyTask" id="form1" class="form-container">
		<div class="form-container-inner">
			<h3>${operationType} Hourly Task</h3>
			<span class="input-container">
				<form:label path="name">Name<em class="mandatory">*</em></form:label>
				<span class="input_text">
					<span class="left_curve"></span>
						<form:input path="name" maxlength="200" cssClass="mid" cssStyle="width: 373px;"  />
					<span class="right_curve"></span>
				</span>
				<form:errors class="invalid" path="name" />
			</span>
			<span class="input-container">
				<form:label path="department.id">Department<!-- <em class="mandatory">*</em> --></form:label>
					<span class="left_curve"></span>
						<form:select path="department.id" id="department.id" cssClass="selectbox">
							<form:option value="">-:Select Department:-</form:option>
							<c:forEach items="${departments}" var="parent">
								<form:option value="${parent.id}">${parent.name}</form:option>
							</c:forEach>
						</form:select>
					<span class="right_curve"></span>
				<form:errors class="invalid" path="department.id" />
			</span>
			<span class="input-container">
				<form:label path="description">Description:</form:label>
				<form:textarea rows="8" cols="20" path="description" />
			</span>
			<span class="input-container">
				<form:label path="status">Chargeable:<em class="mandatory">*</em></form:label>
				<form:radiobutton path="chargeable" value="1" />Chargeable
				<form:radiobutton path="chargeable" value="0" />Not Chargeable
			</span>
			<span class="input-container" style="display:none">
				<form:label path="status">Status:<em class="mandatory">*</em></form:label>
				<form:radiobutton path="status" value="1" />Active
				<form:radiobutton path="status" value="0" />Inactive
			</span>
			<br/>
			<br/>
			<span class="input-container"> <label>&nbsp;</label>
				<input type="submit" title="Save / Update Task" value="${buttonName}" class="login-btn" /> 
				<input type="button" title="Cancel" value="Cancel" class="login-btn"  onclick="javascript:resetForm(this.form);" /> 
				<c:if test="${operationType == 'Edit'}">
					<!-- input type="button" title="Delete" value="Delete"	class="login-btn" onclick="deletePayment(${payment.id})" /-->
				</c:if>
			</span>
		</div>
	</form:form>
</div>


<script type="text/javascript">
	/* function deletePayment(id) {
		if (confirm("Are you sure you wish to delete this payment type?")) {
			$.ajax({
				type : "POST",
				dataType : "json",
				url : contextPath + "/admin/payment/delete",
				data : "items=" + id,
				success : function(data) {
					window.location.href= contextPath + '/admin/payment?successUpdate=paymenttype.deletedSuccessfully ';
				}
			});
		}
	} */
</script>