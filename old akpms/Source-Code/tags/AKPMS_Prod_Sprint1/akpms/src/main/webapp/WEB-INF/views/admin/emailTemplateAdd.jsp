<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>	
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

 <div class="dashboard-container" >
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home  </a>&raquo;</li>
			<li><a href="<c:url value='/admin/emailtemplate' />">&nbsp;Email Templates</a>&raquo;</li>
			<li>&nbsp;${operationType} Email Templates</li>
		</ul>
	</div>
	<div class="clr"></div>
	<form:form commandName="emailTemplate" id="form1"  class="form-container">
		<div class="form-container-inner">
		    <h3>${operationType} Email Template</h3>
		    <span class="input-container">
				<form:label path="name">Name<em class="mandatory">*</em></form:label>
				<span class="input_text">
					<span class="left_curve"></span>
					<form:input path="name" maxlength="255" cssClass="mid" cssStyle="width: 373px;" />
					<span class="right_curve"></span>
				</span>
				<form:errors class="invalid" path="name" />
			</span>

	    	<span class="input-container">
				<form:label path="subscriptionEmail">Subscription Email</form:label>
				<form:checkbox path="subscriptionEmail" />
			</span>

			<span class="input-container">
				<form:label path="Content">Content<em class="mandatory">*</em></form:label>
			</span>
			<FCK:editor toolbarSet="Basic" instanceName="content" width="750" height="425" >
			    <jsp:attribute name="value" trim="true">
			    ${emailTemplate.content}
			    </jsp:attribute>
			    <jsp:body></jsp:body>
			</FCK:editor>
			<form:errors class="error" path="content" />
		    			
	    	<span class="input-container">
				<form:label path="Status" >Status:</form:label>
				<form:radiobutton path="status" value="1" />Active
				<form:radiobutton path="status" value="0" />Inactive
			</span>
	    	<span class="input-container">
				<label>&nbsp;</label>
				<input type="submit" title="${buttonName} Email Template" value="${buttonName}"
				class="login-btn" />
				<input type="button" title="Cancel" onclick="javascript:resetForm(this.form);"
				value="Cancel" class="login-btn" />
			</span>
		</div>
	</form:form>
</div>
<script type="text/javascript">
function FCKeditor_OnComplete(editorInstance) {
	//window.status = editorInstance.Description;
}
</script>