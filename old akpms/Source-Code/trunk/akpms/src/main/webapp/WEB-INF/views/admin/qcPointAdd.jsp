<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo; </li>
			<li><a href="<c:url value='/admin/qcpoint' />">&nbsp; QC Point List</a> &raquo; </li>
			<li>&nbsp;${operationType} QC Point</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<form:form commandName="qcPoint" modelAttribute="qcPoint" id="form1" class="form-container">
		<div class="form-container-inner">
		    <h3>${operationType} QC Point</h3>

			<span class="input-container">
				<form:label path="name">QC Point:<em class="mandatory">*</em></form:label>
				<span class="input_text">
					<span class="left_curve"></span>
					<form:input path="name" maxlength="255" cssClass="mid" cssStyle="width: 373px;" />
					<span class="right_curve"></span>
				</span>
				<form:errors cssClass="invalid" path="name" />
			</span>


			<span class="input-container" id="spanParent" >
				<form:label path="parent.id">Parent QC Point:</form:label>
				<span class="select-container" style="display:inline;">
					<form:select path="parent.id" cssClass="selectbox">
						<form:option value="">-: Select Parent QC Point :-</form:option>
						<form:options items="${subQcPoints}" itemValue="id" itemLabel="name" />
					</form:select>
				</span>
			</span>
			
			<span class="input-container" id="spanDepartment" >
				<form:label path="department.id">Department:<em class="mandatory">*</em></form:label>
				<span class="select-container" style="display:inline;">
					<form:select path="department.id" id="departmentId" cssClass="selectbox">
						<form:option value="">-: Select Department :-</form:option>
						<form:options items="${departmentList}" itemValue="id" itemLabel="name" />
					</form:select>
				</span>
			</span>
			
			<span class="input-container" id="spanDepartment" >
				<form:label path="subDepartment.id">Sub Department:</form:label>
				<span class="select-container" style="display:inline;">
					<form:select path="subDepartment.id" id="subDepartmentId" cssClass="selectbox" >
						<form:option value="">All</form:option>
					</form:select>
				</span>
				<form:errors path="subDepartment.id" cssClass="invalid"/>
			</span>

			<c:if test="${childCount gt 0}">
				<script type="text/javascript">
					$('#spanParent').css("display","none");
				</script>
			</c:if>
			<input type="hidden" value="${childCount}" name="childCount" />

			<span class="input-container">
				<form:label path="description">Description:</form:label>
				<form:textarea rows="8" cols="20" path="description" />
			</span>

			<%-- <span class="input-container">
				<form:label path="status" >Status:</form:label>
				<form:radiobutton path="status" value="1" />Active
				<form:radiobutton path="status" value="0" />Inactive
			</span> --%>

			<span class="input-container">
				<label>&nbsp;</label>
				<input type="submit" title="${buttonName} QC Point" value="${buttonName}"
				class="login-btn" onclick="return confirmChildInactivation(${childCount});" />
				<input type="button" title="Cancel" value="Cancel" onclick="javascript:resetForm(this.form);"
				class="login-btn" />
				<c:if test="${operationType == 'Edit'}">
					<!-- input type="button" title="Delete" value="Delete"
						class="login-btn"
						onclick="javascript:deleteDepartment(${department.id}, ${childCount})" / -->
				</c:if>
			</span>
		</div>
	</form:form>
</div>
<script type="text/javascript">
function confirmChildInactivation(childCount){
	var radioStatus = $("input[name='status']:checked").val();

	if(radioStatus == 0 && childCount > 0){
		return confirm("Deactivating this QC point will also inactivate its "+childCount+ " sub-qc points. Are you sure you wish to proceed?");
	}
}

function getSubDept(){
    var subDepartmentId = "${subDepartmentId}";
	 $.ajax({
    		type : "Get",
    		datatype:"json",
     		url : contextPath + "/qamanager/subdepartment",
     		data :"parentDepartmentId=" + $('#departmentId').val(),
     		success : function(data) {
     			//alert('success ' + data);
              var html = '<option value="">All</option>';
              var len = data.length;
              for(var i=0; i<len; i++){
            		if(data[i].id == subDepartmentId){
            			html += '<option value="' + data[i].id + '" selected >' + data[i].name + '</option>';
                	}else{
                		html += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
                    }    
               }
              $('#subDepartmentId').empty().append(html);
              $.uniform.update();
         },
		});

 }
 
  $(document).ready(function() {
     $('select#departmentId').change(function(){
    	 getSubDept();
     });
     //populate subdepartment when editing qaworksheet
     getSubDept();
  });
</script>

