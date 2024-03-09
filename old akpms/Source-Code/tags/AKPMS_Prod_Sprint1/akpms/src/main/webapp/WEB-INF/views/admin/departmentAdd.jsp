<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo; </li>
			<li><a href="<c:url value='/admin/department' />">&nbsp; Department
					List</a> &raquo; </li>
			<li>&nbsp;${operationType} Department</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<form:form commandName="department" modelAttribute="department" id="form1" class="form-container">
		<div class="form-container-inner">
		    <h3>${operationType} Department</h3>

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
				<form:label path="parent.id">Parent Department:</form:label>
				<span class="select-container" style="display:inline;">
					<form:select path="parent.id" cssClass="selectbox">
						<form:option value="">-: Select Parent Department :-</form:option>
						<form:options items="${subDepartments}" itemValue="id"
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
				<form:label path="desc">Description:</form:label>
				<form:textarea rows="8" cols="20" path="desc" />
			</span>

			<span class="input-container">
				<form:label path="status" >Status:</form:label>
				<form:radiobutton path="status" value="1" />Active
				<form:radiobutton path="status" value="0" />Inactive
			</span>

			<span class="input-container">
				<label>&nbsp;</label>
				<input type="submit" title="${buttonName} Department" value="${buttonName}"
				class="login-btn" onclick="return confirmChildInactivation(${childCount});" />
				<input type="button" title="Cancel" value="Cancel" onclick="javascript:resetForm(this.form);"
				class="login-btn" />
				<c:if test="${operationType == 'Edit'}">
					<input type="button" title="Delete" value="Delete"
						class="login-btn"
						onclick="javascript:deleteDepartment(${department.id}, ${childCount})" />
				</c:if>
			</span>
		</div>
	</form:form>
</div>
<script type="text/javascript">
function confirmChildInactivation(childCount){
	var radioStatus = $("input[name='status']:checked").val();

	if(radioStatus == 0 && childCount > 0){
		return confirm("Deactivating this department will also inactivate its "+childCount+ " sub-departments. Are you sure you wish to proceed?");
	}
}

function deleteDepartment(id, childCount) {
	var confirmation = false;

	if(childCount > 0 ){
		// if parent has childs
		confirmation = confirm("Deleting this department will also delete its "+childCount+" sub-departments. Are you sure you wish to proceed?");
	}else{
		confirmation = confirm("Are you sure you wish to delete this department?");
	}

	if (confirmation) {
	$.ajax({
		type : "POST",
		dataType : "json",
		url : contextPath + "/admin/department/delete",
		data : "item=" + id,
		success : function(data) {
			/*alert("Query: " + data.query
					+ " - Total affected rows: "
					+ data.total);*/
			window.location.href= contextPath + "/admin/department?successUpdate=department.deletedSuccessfully";
		}
	});
	}
}
</script>
