<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo; </li>
			<li><a href="<c:url value='/admin/doctor' />">&nbsp; Doctor
					List</a> &raquo; </li>
			<li>&nbsp;${operationType} Doctor</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<form:form commandName="doctor" modelAttribute="doctor" id="form1" class="form-container">
		<div class="form-container-inner">
		    <h3>${operationType} Doctor</h3>

			<span class="input-container">
				<form:label path="name">Name:<em class="mandatory">*</em></form:label>
				<span class="input_text">
					<span class="left_curve"></span>
					<form:input path="name" maxlength="255" cssClass="mid" cssStyle="width: 373px;" />
					<span class="right_curve"></span>
				</span>
				<form:errors class="invalid" path="name" />
			</span>

			<span class="input-container" id="spanParent" >
				<form:label path="parent.id">Parent Doctor:</form:label>
				<span class="select-container" style="display:inline;">
					<form:select path="parent.id" cssClass="selectbox">
						<form:option value="">-: Select Parent Doctor :-</form:option>
						<form:options items="${parentDoctors}" itemValue="id"
							itemLabel="name" />
					</form:select>
				</span>
			</span>

			<c:if test="${childCount gt 0}">
				<script type="text/javascript">
					$('#spanParent').css("display","none");
				</script>
			</c:if>
			<input type="hidden" value="${childCount}" name="childCount" />
			<span class="input-container">
				<form:label path="doctorCode">Doctor Code<em class="mandatory">*</em></form:label>
				<span class="input_text">
					<span class="left_curve"></span>
						<form:input path="doctorCode" maxlength="200" cssClass="mid" cssStyle="width: 373px;"  />
					<span class="right_curve"></span>
				</span>
				<form:errors class="invalid" path="doctorCode" />
			</span>

			<span class="input-container">
				<form:label path="doctorCode">Accounting</form:label>
				<span class="input_text">
					<span class="left_curve"></span>
						<form:input path="accounting" maxlength="5" cssClass="mid numbersOnly" onblur="setZeroIfEmpty(this);" cssStyle="width: 60px;"  />
					<span class="right_curve"></span>
				</span>
				<form:errors class="invalid" path="accounting" />
			</span>

			<span class="input-container">
				<form:label path="doctorCode">Percentage</form:label>
				<span class="input_text">
					<span class="left_curve"></span>
						<form:input path="percentage" maxlength="5" cssClass="mid numbersOnly" onblur="setZeroIfEmpty(this);" cssStyle="width: 60px;"  />
					<span class="right_curve"></span>
				</span>
				<form:errors class="invalid" path="percentage" />
			</span>

			<span class="input-container">
				<form:label path="doctorCode">Payments</form:label>
				<span class="input_text">
					<span class="left_curve"></span>
						<form:input path="payments" maxlength="5" cssClass="mid numbersOnly" onblur="setZeroIfEmpty(this);" cssStyle="width: 60px;"  />
					<span class="right_curve"></span>
				</span>
				<form:errors class="invalid" path="payments" />
			</span>

			<span class="input-container">
				<form:label path="doctorCode">Operations</form:label>
				<span class="input_text">
					<span class="left_curve"></span>
						<form:input path="operations" maxlength="5" cssClass="mid numbersOnly" onblur="setZeroIfEmpty(this);" cssStyle="width: 60px;"  />
					<span class="right_curve"></span>
				</span>
				<form:errors class="invalid" path="operations" />
			</span>

			<span class="input-container">
				<form:label path="status" >Status:</form:label>
				<form:radiobutton path="status" value="1" />Active
				<form:radiobutton path="status" value="0" />Inactive
			</span>

			<span class="input-container">
				<label>&nbsp;</label>
				<input type="submit" title="${buttonName} Doctor" value="${buttonName}"
				class="login-btn" onclick="return confirmChildInactivation(${childCount});" />
				<input type="button" title="Cancel" value="Cancel" onclick="javascript:resetForm(this.form);"
				class="login-btn" />
				<c:if test="${operationType == 'Edit'}">
					<input type="button" title="Delete" value="Delete"
						class="login-btn"
						onclick="javascript:deleteDoctor(${doctor.id}, ${childCount})" />
				</c:if>
			</span>
		</div>
	</form:form>
</div>
<script type="text/javascript">
function confirmChildInactivation(childCount){
	var radioStatus = $("input[name='status']:checked").val();

	if(radioStatus == 0 && childCount > 0){
		return confirm("Deactivating this doctor will also inactivate its "+childCount+ " sub-doctors. Are you sure you wish to proceed?");
	}
}

function setZeroIfEmpty(element){
	if(element.value == "" || element.value == ".") {
		element.value = '0.0';
	}
}

function deleteDoctor(id, childCount) {
	var confirmation = false;

	if(childCount > 0 ){
		// if parent has childs
		confirmation = confirm("Deleting this doctor will also delete its "+childCount+" sub-doctors. Are you sure you wish to proceed?");
	}else{
		confirmation = confirm("Are you sure you wish to delete this doctor?");
	}

	if (confirmation) {
	$.ajax({
		type : "POST",
		dataType : "json",
		url : contextPath + "/admin/doctor/delete",
		data : "item=" + id,
		success : function(data) {
			/*alert("Query: " + data.query
					+ " - Total affected rows: "
					+ data.total);*/
			window.location.href= contextPath + "/admin/doctor?successUpdate=doctor.deletedSuccessfully";
		}
	});
	}
}
</script>

<script type="text/javascript" src="<c:url value='/static/resources/js/common.js'/>"></script>