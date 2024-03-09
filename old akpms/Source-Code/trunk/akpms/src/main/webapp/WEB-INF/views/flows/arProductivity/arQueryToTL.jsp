<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="downloadAction">
	<c:url value='/flows/codingcorrectionlogworkflow/fileDownload' />
</c:set>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li><a href="<c:url value='/flows/arProductivity' />">&nbsp;AR Productivity List</a>
				&raquo;</li>
			<li>Query To TL Log WorkFlow</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->

	<form:form commandName="arProductivity" id="form1" class="form-container">

		<div class="form-container-inner">
			<h3>Query To TL</h3>
			<div class="row-container">
		    	<div class="element-block" >
			    	<span class="lbl">Patient Name:</span> <span>${arProductivity.patientName}</span>
			    </div>
			    <div class="element-block" >
		    		<span class="lbl">Patient ID:</span> <span>${arProductivity.patientAccNo}</span>
		    	</div>
		    	<div class="clr"></div>
		    </div>
		    
		    <div class="row-container">
		    	<div class="element-block" >
			    	<span class="lbl">Insurance:</span> <span>${arProductivity.insurance.name}</span>
			    </div>
			    <div class="element-block" >
		    		<span class="lbl">Provider (Doctor Office):</span> <span>${arProductivity.doctor.name}</span>
		    	</div>
		    	<div class="clr"></div>
		    </div>
		    
			<div class="row-container">
		    	<div class="element-block" >
			    	<span class="lbl">Balance Amount:</span> <span>${arProductivity.balanceAmt}</span>
			    </div>
			    <div class="element-block" >
		    		<span class="lbl">Remarks:</span> <span>${arProductivity.remark}</span>
		    	</div>
		    	<div class="clr"></div>
		    </div>
		    
		    <div class="row-container">
		    	<div class="element-block" >
			    	<span class="lbl">CPT:</span> <span>${arProductivity.cpt}</span>
			    </div>
			    <div class="element-block" >
		    		<span class="lbl">DOS:</span> <span>${arProductivity.dos}</span>
		    	</div>
		    	<div class="clr"></div>
		    </div>

			<div class="row-container">
				<form:label path="remark">Remark</form:label>
				<form:textarea rows="5" cols="20" path="remark"  readonly="true"/>
				<div class="clr"></div>
			</div>

			<div class="row-container">
				<form:label path="tlRemark">TL Remark<em class="mandatory">*</em>
				</form:label>
				<form:textarea rows="5" cols="20" path="tlRemark" />
				<div class="clr"></div>
				<span class="invalid" id="tlRemark_validation" ></span>
				<div class="clr"></div>
			</div>
			
			
			<div class="row-container">
				<form:label path="subStatus">Status<em class="mandatory">*</em></form:label>
				<form:select path="subStatus">
					<form:option value="">- : Select Status : -</form:option>
					<form:option value="1">Open</form:option>
					<form:option value="2">Back To Team</form:option>
					<form:option value="3">Closed</form:option>
				</form:select>
				<div class="clr"></div>
			</div>
			
			<span class="input-container"> <label>&nbsp;</label>
			 <input type="submit" title="Query To TL" value="Query To TL" onclick="return checkEmpty()"
				class="login-btn" />
			</span>
		</div>
	</form:form>
	</div>
	<script type="text/javascript">
		function checkEmpty()
		{
			if($('#tlRemark') == null || $.trim($('#tlRemark').val())== "")
				{
					$('#tlRemark_validation').html ("TL Remark can not be left empty.");
					return false;
				}
			else{
				$('#tlRemark_validation').html ("");
				return true;
			}
		}
	</script>
