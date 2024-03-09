<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li><a href="<c:url value='/admin/payment' />">&nbsp;Payment Type List</a>
				&raquo;</li>
			<li>&nbsp;${operationType} Payment Type</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<form:form commandName="payment" id="form1" class="form-container">
		<div class="form-container-inner">
			<h3>${operationType} Payment</h3>
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

			<span class="input-container" style="display:none">
				<form:label path="Type">Type:<em class="mandatory">*</em></form:label>
				<!-- form:radiobutton path="type" value="moneysource" />Money source -->
				<form:radiobutton path="type" value="moneytypes" />Money types
				<form:radiobutton path="type" value="paytypes"/>Payment types
			</span>

			<span class="input-container">
				<form:label path="Status">Status:<em class="mandatory">*</em></form:label>
				<form:radiobutton path="status" value="1" />Active
				<form:radiobutton path="status" value="0" />Inactive
			</span>
			<br/>
			<br/>
			<span class="input-container"> <label>&nbsp;</label>
				<input type="submit" title="Save / Update Payment"
				value="${buttonName}" class="login-btn" /> <input type="button"
				title="Cancel" value="Cancel" class="login-btn"  onclick="javascript:resetForm(this.form);" /> <c:if
					test="${operationType == 'Edit'}">
					<input type="button" title="Delete" value="Delete"
						class="login-btn"
						onclick="deletePayment(${payment.id})" />
				</c:if>
			</span>
		</div>
	</form:form>
</div>


<script type="text/javascript">
	function deletePayment(id) {
		if (confirm("Are you sure you wish to delete this payment?")) {
			$.ajax({
				type : "POST",
				dataType : "json",
				url : contextPath + "/admin/payment/delete",
				data : "items=" + id,
				success : function(data) {
					window.location.href= contextPath + '/admin/payment?successUpdate=payment.deletedSuccessfully ';
				}
			});
		}
	}
</script>