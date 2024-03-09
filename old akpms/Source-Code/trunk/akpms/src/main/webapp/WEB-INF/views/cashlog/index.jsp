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
	<form id="form1" class="form-container" method="get" target="_blank" >
		<div class="form-container-inner">
			<h3>Generate Cashlog Report</h3>
			
			<table style="width: 100%">
				<tr>
					<td style="width: 50%; vertical-align: top;" >
						<div class="row-container">
							<input type="radio" checked="checked" name="clReportType" value="rptjemngfee" id="rptjemngfee" onclick="javascript:showHideOptions(this);" > &nbsp;&nbsp;
							<label for="rptjemngfee" style="width: auto; display: inline; float: none">Journal Entries Management Fee</label>
							<div class="clr"></div>
						</div>
						<div class="row-container">
							<input type="radio" name="clReportType" value="rptentrysummary" id="rptentrysummary" onclick="javascript:showHideOptions(this);"> &nbsp;&nbsp;
							<label for="rptentrysummary" style="width: auto; display: inline; float: none">Journal Entries Summary</label>
							<div class="clr"></div>
						</div>
						<div class="row-container">
							<input type="radio" name="clReportType" value="rptentrydetailed" id="rptentrydetailed" onclick="javascript:showHideOptions(this);"> &nbsp;&nbsp;
							<label for="rptentrydetailed" style="width: auto; display: inline; float: none">Detailed Journal Entries</label>
							<div class="clr"></div>
						</div>
						<div class="row-container">
							<input type="radio" name="clReportType" value="rptentrywithmngfee" id="rptentrywithmngfee" onclick="javascript:showHideOptions(this);"> &nbsp;&nbsp;
							<label for="rptentrywithmngfee" style="width: auto; display: inline; float: none">Detailed Journal Entries With Management Fee</label>
							<div class="clr"></div>
						</div>
						<div class="row-container">
							<input type="radio" name="clReportType" value="rptdailypaymentreceiptlog" id="rptdailypaymentreceiptlog" onclick="javascript:showHideOptions(this);"> &nbsp;&nbsp;
							<label for="rptdailypaymentreceiptlog" style="width: auto; display: inline; float: none">Daily Payment Receipt Log</label>
							<div class="clr"></div>
						</div>
						<div class="row-container">
							<div class="element-block" >
								<input type="submit" title="Generate Report" value="Generate Report" class="login-btn" />
							</div>
							<div class="clr"></div>
						</div>
					</td>
					<td style="width: 50%; vertical-align: top;">
						<div class="row-container">
							<div class="element-block" style="width: 100%" >
								<label>Month</label>
								<div class="select-container2">
									<select class="selectbox" name="month" id="month">
										<c:forEach var="month" items="${months}">
								    		<option value="${month.key}">${month.value}</option>
								    	</c:forEach>
									</select>
								</div>
							</div>
							<div class="clr"></div>
						</div>
						<div class="row-container">	
							<div class="element-block" style="width: 100%">
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
						<div class="row-container" id="divTicketNumber" style="display: none;"> 	
							<div class="element-block" style="width: 100%">
								<span>
									<label for="chkTicketNumber" style="width: auto; display: inline; float: none">Show ticket number in report</label>
									&nbsp;&nbsp;<input type="checkbox" name="chkTicketNumber" value="true" id="chkTicketNumber">
								</span>
							</div>
							<div class="clr"></div>
						</div>
						<div class="row-container" id="divDateRange" style="display: none;"> 	
							<div class="element-block"  style="width: 100%">
								<label>Deposit Date (From)</label>
								<div class="input_text">
									<span class="left_curve"></span>
									<input name="depositDateFrom" id="depositDateFrom" class="mid" style="width: 100px; border: 0px"/>
									<span class="right_curve"></span>
								</div>
								<div class="clr"><br /></div>
								<label>Deposit Date (To)</label>
								<div class="input_text">
									<span class="left_curve"></span>
									<input name="depositDateTo" id="depositDateTo" class="mid" style="width: 100px; border: 0px"/>
									<span class="right_curve"></span>
								</div>
							</div>
							<div class="clr"></div>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>

<script type="text/javascript">
	function changeFormAction(){
		$('#form1').attr('action', contextPath + '/cashlog/'+ $('input[name=clReportType]:checked', '#form1').val());
		$('#form1').submit();
	}

	$(function() {
	    $("#depositDateFrom").datepicker();
	    $("#depositDateTo").datepicker();
	});

	function showHideOptions(opt){
		//console.log(opt.id);
		if(opt.id =='rptentrydetailed' || opt.id =='rptentrywithmngfee'){
			$("#divTicketNumber").css({'display':'block'});
		}else{
			$("#divTicketNumber").css({'display':'none'});
		}

		if(opt.id =='rptdailypaymentreceiptlog'){
			$("#divDateRange").css({'display': 'block'});	
		}else{
			$("#divDateRange").css({'display': 'none'});
			$("#depositDateFrom").val("");
		    $("#depositDateTo").val("");
		}
	}

	$('#form1').submit(function() {
		// add multi values as comma seprated
		if(null != $('#depositDateFrom').val() && null != $('#depositDateTo').val()){
			if(Date.parse($('#depositDateFrom').val()) > Date.parse($('#depositDateTo').val()) ){
				alert("Deposit date 'To' date must be after or equal to deposit date 'From' date. Please try again.");
				return false;
			}
		}
		changeFormAction();

		//akpmsPopupWindow(contextPath + '/cashlog/'+ $('input[name=clReportType]:checked', '#form1').val(), 'cashlogReport');
		return true;
	});
</script>
