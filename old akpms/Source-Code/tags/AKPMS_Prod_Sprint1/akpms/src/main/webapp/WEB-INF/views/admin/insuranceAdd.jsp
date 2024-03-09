<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li><a href="<c:url value='/admin/insurance' />">&nbsp;Insurance List</a>
				&raquo;</li>
			<li>&nbsp;${operationType} Insurance</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<form:form commandName="insurance" id="form1" class="form-container">
		<div class="form-container-inner">
			<h3>${operationType} Insurance</h3>
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
				<form:label path="desc">Description:</form:label>
				<form:textarea rows="8" cols="20" path="desc" />
			</span>

			<span class="input-container">
				<form:label path="Status">Status:<em class="mandatory">*</em></form:label>
				<form:radiobutton path="status" value="1" />Active
				<form:radiobutton path="status" value="0" />Inactive
			</span>
			<br/>
			<br/>
			<span class="input-container"> <label>&nbsp;</label>
				<input type="submit" title="Save / Update Insurance"
				value="${buttonName}" class="login-btn" /> <input type="button"
				title="Cancel" value="Cancel" class="login-btn"  onclick="javascript:resetForm(this.form);" /> <c:if
					test="${operationType == 'Edit'}">
					<input type="button" title="Delete" value="Delete"
						class="login-btn"
						onclick="deleteInsurance(${insurance.id})" />
				</c:if>
			</span>
		</div>
	</form:form>
</div>


<script type="text/javascript">
	function deleteInsurance(id) {
		if (confirm("Are you sure you wish to delete this insurance?")) {
			$.ajax({
				type : "POST",
				dataType : "json",
				url : contextPath + "/admin/insurance/delete",
				data : "items=" + id,
				success : function(data) {
					window.location.href= contextPath + '/admin/insurance?successUpdate=insurance.deletedSuccessfully ';
				}
			});
		}
	}


	function resetForms(){
		//$('div[id|="uniform"]').find('span').removeClass("checked");
		$("#clear").click(function() {
		    $("input[class='mid']").val("");
		});

		$("#reset").click(function() {
			alert($('input:radio[name=bar]:checked').val());
			//$('.old-value').text($('input:radio[name=bar]:checked').val());
			//$('.click').click(function() {
		   //     $('.old-value').text( $('input').val() );
		   // });
		});
	}

	</script>
