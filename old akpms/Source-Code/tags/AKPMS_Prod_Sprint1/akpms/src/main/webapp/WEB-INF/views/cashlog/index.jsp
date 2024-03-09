<%@page import="argus.util.Constants"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li>&nbsp;Generate Cashlog Report</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<form id="form1" class="form-container" method="post" >
		<div class="form-container-inner">
			<h3>Generate Cashlog Report</h3>

			<div class="row-container">
				<div class="element-block" >
					<label>Month</label>
					<div class="select-container2">
						<select class="selectbox" name="month" id="month">
							<c:forEach var="month" items="${months}">
					    		<option value="${month.key}">${month.value}</option>
					    	</c:forEach>
						</select>
					</div>
				</div>
				<div class="element-block" >
					<label>Year</label>
					<div class="select-container2">
						<select class="selectbox" name="year" id="year">
							<c:forEach var="year" items="${years}">
					    		<option value="${year}">${year}</option>
					    	</c:forEach>
						</select>
					</div>
				</div>
				<div class="clr"></div>
			</div>
			<div class="row-container">
				<input type="radio" checked="checked" name="clReportType" value="rptjemngfee" id="rptjemngfee"> &nbsp;&nbsp;
				<label for="rptjemngfee" style="width: auto; display: inline; float: none">Journal Entries Management Fee</label>
				<div class="clr"></div>
			</div>
			<div class="row-container">
				<input type="radio" name="clReportType" value="rptentrysummary" id="rptentrysummary"> &nbsp;&nbsp;
				<label for="rptentrysummary" style="width: auto; display: inline; float: none">Journal Entries Summary</label>
				<div class="clr"></div>
			</div>
			<div class="row-container">
				<input type="radio" name="clReportType" value="rptentrydetailed" id="rptentrydetailed"> &nbsp;&nbsp;
				<label for="rptentrydetailed" style="width: auto; display: inline; float: none">Detailed Journal Entries</label>
				<div class="clr"></div>
			</div>
			<div class="row-container">
				<input type="radio" name="clReportType" value="rptentrywithmngfee" id="rptentrywithmngfee"> &nbsp;&nbsp;
				<label for="rptentrywithmngfee" style="width: auto; display: inline; float: none">Detailed Journal Entries With Management Fee</label>
				<div class="clr"></div>
			</div>
			<div class="row-container">
				<input type="radio" name="clReportType" value="rptdailypaymentreceiptlog" id="rptdailypaymentreceiptlog"> &nbsp;&nbsp;
				<label for="rptdailypaymentreceiptlog" style="width: auto; display: inline; float: none">Daily Payment Receipt Log</label>
				<div class="clr"></div>
			</div>
			<div class="row-container">
				<label>&nbsp;</label>
				<input type="submit" title="Generate Report" value="Generate Report" class="login-btn" onclick="javascript:changeFormAction();" />
				<div class="clr"></div>
			</div>
		</div>
	</form>
</div>

<script type="text/javascript">
	function changeFormAction(){
		$('#form1').attr('action', contextPath + '/cashlog/'+ $('input[name=clReportType]:checked', '#form1').val());
		$('#form1').submit();
	}
</script>
