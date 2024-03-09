<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<table class="table" id="my-table" style="margin-bottom:270px;">
	<thead>
		<tr style="background-color: #999999; color: white" >
			<th>Sort By</th>
			<th>
				<select name="orderby" id="orderby" style="color:black">
					<option value="">Default</option>
					<option value="chargeProductivity.scanDate">Scan Date</option>
					<option value="chargeProductivity.ticketNumber.dateBatchPosted">Posted Date</option>
					<option value="chargeProductivity.createdBy.firstName">Operator Name</option>
					<option value="chargeProductivity.ticketNumber.id">Batch Number</option>
					<option value="chargeProductivity.ticketNumber.doctor.name">Doctor Name</option>
				</select>
			</th>
			<th>
				<button type='button' name='btnOrder' id="btnOrder" class='btn btn-info btn-xs'>Set Order</button>
			</th>
			<th>
				&nbsp;
			</th>
			<th colspan="4" style="text-align:right">
				<c:if test="${mode ne 'search'}">
					<input type="checkbox" value="showall" name="mode" id="chkShowAll" > <label for="chkShowAll">Show all hidden rows</label>
				</c:if>
			</th>
		</tr>
		<tr style="background-color: #999999; color: white">
			<th></th>
			<th>Scan <br /> Date</th>
			<th>Posted <br /> Date</th>
			<th>DE <br /> Person</th>
			<th>Batch <br /> No</th>
			<!-- <th>Sq#</th> -->
			<th>Physician <br /> Group</th>
			<th>QCed <br /> Accounts</th>
			<!-- <th>CPTs</th>
			<th>Batch<br />Percentage</th> -->
			<th>Errors</th>
			<th style="text-align:right">Action</th>
		</tr>
	</thead>

	<tbody>
		<c:if test="${fn:length(QA_PROD_SAMPLE_DATA_LIST) eq 0}">
			<tr>
				<td colspan="8">No Sample Record Found.</td>
			</tr>
		</c:if>
		
		<c:forEach items="${QA_PROD_SAMPLE_DATA_LIST}" var="QA_PROD_SAMPLE_DATA" varStatus="loop">
			<tr id="sample_${QA_PROD_SAMPLE_DATA.id}_${QA_PROD_SAMPLE_DATA.chargeProductivity.createdBy.id}" style="background-color: #D6D6D6;" <c:if test="${QA_PROD_SAMPLE_DATA.hidden && (mode ne 'search' || mode ne 'showall')}">class="hide_tr"</c:if> >
				<td>S. no.</td>
				<td> <fmt:formatDate type="date" value="${QA_PROD_SAMPLE_DATA.chargeProductivity.scanDate}"/> </td>
				<td> <fmt:formatDate type="date" value="${QA_PROD_SAMPLE_DATA.chargeProductivity.createdOn}"/> </td>
				<td>
					${QA_PROD_SAMPLE_DATA.chargeProductivity.createdBy.firstName}
					${QA_PROD_SAMPLE_DATA.chargeProductivity.createdBy.lastName}
				</td>
				
				<td> ${QA_PROD_SAMPLE_DATA.chargeProductivity.ticketNumber.id} </td>
				<%-- <td> ${QA_PROD_SAMPLE_DATA.chargeProductivity.id} </td> --%>
				<td> ${QA_PROD_SAMPLE_DATA.chargeProductivity.ticketNumber.doctor.name} </td>
				
				
				<c:set var="totalAccounts" value="0"/>
				<c:choose>
					<c:when test="${QA_PROD_SAMPLE_DATA.chargeProductivity.productivityType eq 'Demo'}">
						<c:set var="totalAccounts" value="${QA_PROD_SAMPLE_DATA.chargeProductivity.t1 + QA_PROD_SAMPLE_DATA.chargeProductivity.t2}"/>
					</c:when>
					<c:when test="${QA_PROD_SAMPLE_DATA.chargeProductivity.productivityType eq 'CE'}">
						<c:set var="totalAccounts" value="${QA_PROD_SAMPLE_DATA.chargeProductivity.t3}"/>
					</c:when>
					<c:otherwise>
						<c:set var="totalAccounts" value="${QA_PROD_SAMPLE_DATA.chargeProductivity.t1}"/>
					</c:otherwise>
				</c:choose>
				
				<c:choose>
					<c:when test="${mode ne 'search'}">
						<c:set var="persAccount" value="0"/>
						<c:set var="persAccount" value="${(totalAccounts*QA_WORKSHEET.accountPercentage)/100}"/>
						<td><fmt:parseNumber integerOnly="true" type="number" value="${persAccount}" />/${totalAccounts} (${QA_WORKSHEET.accountPercentage})</td>
					</c:when>
					<c:otherwise>
						<td>${totalAccounts}</td>
					</c:otherwise>				
				</c:choose>
				
				<%-- <td><input type="text" size="3" maxlength="5" placeholder='0' title="Number of CPTs" onblur='javascript:lostFocusSaveBatchInfo(this);'  style="width: 40px;" id='cptCount_${QA_PROD_SAMPLE_DATA.id}' value="${QA_PROD_SAMPLE_DATA.cptCount}" /></td>
				<td><input type="text" size="3" maxlength="3" placeholder='%' title="Batch Percentage" onblur='javascript:lostFocusSaveBatchInfo(this);'  style="width: 40px;" id='batchPercentage_${QA_PROD_SAMPLE_DATA.id}' value="${QA_PROD_SAMPLE_DATA.batchPercentage}" /></td> --%>
				<td>&nbsp;</td>
				
				<td class="buttons" style='width: 100px;' align='right'>
					<button type="button" name="addAccBtn" class="btn btn-primary btn-xs addAccBtn" title="Add Account" ><i class="fa fa-plus-square"></i></button>
					
					<!-- <button type="button" name="hideAccBtn" class="btn btn-warning btn-xs hideAccBtn" title="Hide Account"><i class="fa fa-eye-slash"></i></button> -->
					<c:choose>
						<c:when test="${QA_PROD_SAMPLE_DATA.hidden}">
							<button type="button" name="unHideAccBtn" class="btn btn-warning btn-xs unHideAccBtn" title="Un-Hide"><i class="fa fa-eye"></i></button>
						</c:when>						
						<c:otherwise>
							<button type="button" name="hideAccBtn" class="btn btn-warning btn-xs hideAccBtn" title="Hide"><i class="fa fa-eye-slash"></i></button>
						</c:otherwise>
					</c:choose>
					
					<button type="button" name="deleteAccBtn" class="btn btn-danger btn-xs deleteAccBtn" title="Delete Batch"><i class="fa fa-minus-square"></i></button>
					<%-- <a href="javascript:void(0);" class="addAccBtn" id="${QA_PROD_SAMPLE_DATA.id}"><img alt="Add" border="0" src="<c:url value='/static/resources/img/flexigrid/add.png'/>"> Add Account for Batch</a>
					&nbsp;&nbsp;&nbsp;					
					<a href="javascript:void(0);" class="deleteAccBtn" id="${QA_PROD_SAMPLE_DATA.id}"> <img alt="Delete" border="0" src="<c:url value='/static/resources/img/delete_icon.png'/>"> Delete Record (With its Patient Accounts)</a> --%>
				</td>
			</tr>
			<c:forEach items="${QA_PROD_SAMPLE_DATA.qaWorksheetPatientInfos}" var="PATIENT_INFO" varStatus="patientLoop">
					<tr id="patientInfo_${PATIENT_INFO.id}" <c:if test="${QA_PROD_SAMPLE_DATA.hidden && (mode ne 'search' || mode ne 'showall')}">class="hide_tr"</c:if> >
						<td>${patientLoop.count}</td>
						<td>
							CPT:
							<input class="form-control input-sm" type="text" placeholder='CPT' title="CPT" onblur='javascript:lostFocusSave(this);' style="width: auto" size="5" maxlength='20' value="${PATIENT_INFO.cptCodesDemo}"  id='cptcodedemo_${QA_PROD_SAMPLE_DATA.id}_${PATIENT_INFO.id}'>
						</td>
							
						<td >
							Account Number:
							<input class="form-control input-sm" type="text" title="Acc Number" style="width:auto" size="10" onblur='javascript:lostFocusSave(this);' placeholder="Acc Number" maxlength='20' value="${PATIENT_INFO.accountNumber}" id='accnumber_${QA_PROD_SAMPLE_DATA.id}_${PATIENT_INFO.id}'>
						</td>
						
						<td >
							<input type="hidden" value="${QA_PROD_SAMPLE_DATA.id}" name="sampleId" id="sampleId_${PATIENT_INFO.id}">
		      				<input type="hidden" value="${QA_PROD_SAMPLE_DATA.chargeProductivity.createdBy.id}" name="userId" id="userId_${PATIENT_INFO.id}"> 
							<%-- <span class="buttons"><a href='javascript:void(0)' onClick='send_delete(${PATIENT_INFO.id})' class='delete'><img src='<c:url value='/static/resources/img/delete_icon.png'/>' alt='Delete' title='Delete' /></a> <br/></span> 
							<span id="error_count_${PATIENT_INFO.id}">Errors: ${fn:length(PATIENT_INFO.qcPointChecklist)}</span>--%>
							Patient Name:
							<input class="form-control input-sm" type="text" title="Patient Name" style="width:100%" size="10" onblur='javascript:lostFocusSave(this);' placeholder="Patient Name"  maxlength='200' value="${PATIENT_INFO.patientName}"  id='patientname_${QA_PROD_SAMPLE_DATA.id}_${PATIENT_INFO.id}'>
						</td>
						
						<td>
							Page No:
							<input class="form-control input-sm" type="text" title="Page" style="width:auto" size="3" onblur='javascript:lostFocusSave(this);' placeholder="Page" maxlength='10' value="${PATIENT_INFO.srNo}" id='srno_${QA_PROD_SAMPLE_DATA.id}_${PATIENT_INFO.id}' />
						</td>
						
						<td colspan="2">
							<textarea rows='2' class="form-control input-sm" placeholder='Remarks' title="Remarks" onblur='javascript:lostFocusSave(this);' id='remark_${QA_PROD_SAMPLE_DATA.id}_${PATIENT_INFO.id}'>${PATIENT_INFO.remarks}</textarea>
						</td>
						<td><span id="error_count_${QA_PROD_SAMPLE_DATA.id}_${PATIENT_INFO.id}">${fn:length(PATIENT_INFO.qcPointChecklist)}</span></td>
						<td class="buttons" style='width: 100px;' align='right'>
							<button type="button" name="qcPopAccBtn" class="btn btn-info btn-xs qcPopAccBtn" title="QC Points" id="arqcpoint_${QA_PROD_SAMPLE_DATA.id}_${QA_PROD_SAMPLE_DATA.arProductivity.createdBy.id}_${PATIENT_INFO.id}" >QC</button>
							<!-- <div style='height:5px;' class='clearfix'></div> -->
							<button type="button" name="deletePatientAccBtn" class="btn btn-danger btn-xs deletePatientAccBtn" title="Delete Account">Del</button>
						</td>
					</tr>
										
			</c:forEach>
		</c:forEach>
		
	</tbody>
	
	<c:if test="${mode ne 'search'}">
		<tfoot>
			<tr style="background-color: #DBDBDB">
				<td colspan="2"><span id="totalErrorsMsg" style="display: inline"></span></td>
				<td colspan="6" align="right" id="tdComplete">
					<c:if test="${QA_WORKSHEET.status ne '2' }">
						Once Worksheet will "Set as Completed". User can't make modification in it. Are you sure to 
						<input type="button" class="btn btn-primary btn-lg" value="Set as Completed?" onclick="javascript:ajaxSetAsCompleted();">
					</c:if>
				</td>
			</tr>		
		</tfoot>
	</c:if>
</table>

<script type="text/javascript">

		function ajaxInsertBlankPatientInfo(sampleId, userId) {
			$.ajax({
				type : "POST",
				dataType : "json",
				url : contextPath + "/" + moduleName + "/savepatientinfo",
				data : {
					'sampleid' : sampleId
				},
				success : function(qaPatientInfoId) {
		
					var strHTML = "<tr id='patientInfo_" + qaPatientInfoId + "'>";
					//strHTML += "<td>"
						//+ "<span class='buttons'><a href='javascript:void(0)' onClick='send_delete("+ qaPatientInfoId+ ")' class='delete'><img src='"+deleteIcon+"' alt='Delete' title='Delete' /> </a><br/></span>"
						//+ "<span id='error_count_" + qaPatientInfoId + "' >Errors: 0</span>"
						//+ "</td>";
					strHTML += "<td>";
					strHTML += "<input type='hidden' value='"+sampleId+"' name='sampleId' id='sampleId_"+qaPatientInfoId+"'>";
					strHTML += "<input type='hidden' value='"+userId+"' name='userId' id='userId_"+qaPatientInfoId+"'>";
					strHTML += "Patient Name:<input type='text' title='Patient Name' style='width:auto' size='10' maxlength='200' id='patientname_"+ sampleId+ "_" + qaPatientInfoId + "' value='' class='form-control input-sm' placeholder='Patient Name' onblur='javascript:lostFocusSave(this);'>";
					strHTML += "</td>"
					strHTML += "<td>Account Number:<input type='text' title='Acc Number' style='width:auto' size='10' maxlength='20' id='accnumber_"+ sampleId+ "_"+ qaPatientInfoId+ "' value='' class='form-control input-sm' placeholder='Acc Number' onblur='javascript:lostFocusSave(this);'></td>";
					strHTML += "<td>CPT:<input type='text' title='CPT' style='width:auto' size='3' maxlength='10' id='cptcodedemo_"+ sampleId+ "_"+ qaPatientInfoId+ "' value='' class='form-control input-sm' placeholder='CPT' onblur='javascript:lostFocusSave(this);'></td>";
					strHTML += "<td>Page No:<input type='text' title='Page' style='width:auto' size='3' maxlength='10' id='srno_"+ sampleId+ "_"+ qaPatientInfoId+ "' value='' class='form-control input-sm' placeholder='Page' onblur='javascript:lostFocusSave(this);'></td>";
					strHTML += "<td colspan='2'><textarea rows='2' placeholder='Remarks' title='Remarks' id='remark_"+ sampleId+ "_" + qaPatientInfoId + "' class='form-control input-sm' onblur='javascript:lostFocusSave(this);'></textarea></td>";
		
				/* 	strHTML += renderCheckboxes(sampleId, qaPatientInfoId, userId); */
					strHTML += "<td><span id='error_count_"+sampleId+"_"+qaPatientInfoId+"'>0</span></td>";
					strHTML += "<td class='buttons' style='width: 100px;' style='text-align:right' align='right'>";
					strHTML += "<button type='button' name='qcPopAccBtn' class='btn btn-info btn-xs qcPopAccBtn' id='arqcpoint_" + sampleId + "_" + userId + "_" + qaPatientInfoId+"'>QC</button>";
					strHTML += "&nbsp;<button type='button' name='deletePatientAccBtn' class='btn btn-danger btn-xs deletePatientAccBtn'>Del</button>";
					strHTML += "</td>";
		
					strHTML += "</tr>";
		
					$("#sample_" + sampleId+"_"+userId).after(strHTML);
				},
				error : function(error) {
					//alert('error; ' + eval(error));
				}
			});
		}

	
	function lostFocusSave(element) {
		var elementId = element.id;
		var elementInfo = elementId.split("_");
	
		var sampleId = elementInfo[1];
		var id = elementInfo[2]; //patientInfoId
	
		var patientName = $("#patientname_" + elementInfo[1] + "_" + elementInfo[2]).val();
		var accNumber = $("#accnumber_" + elementInfo[1] + "_" + elementInfo[2]).val();
		
		//var trans = $("#trans_" + elementInfo[1] + "_" + elementInfo[2]).val();
		var cptCodesDemo = $("#cptcodedemo_" + elementInfo[1] + "_" + elementInfo[2]).val();
		
		var srno = $("#srno_" + elementInfo[1] + "_" + elementInfo[2]).val();
		var remark = $("#remark_" + elementInfo[1] + "_" + elementInfo[2]).val();
	
		$.ajax({
			type : "POST",
			dataType : "json",
			url : contextPath + "/" + moduleName + "/savepatientinfo",
			data : {
				'sampleid' : sampleId,
				'id' : id,
				'patientName' : patientName,
				'accountNumber' : accNumber,
				'cptCodeDemo' : cptCodesDemo,
				'srNo': srno,
				'remark' : remark
			},
			success : function(qaPatientInfoId) {
				//alert('success: ' + qaPatientInfoId);
				$("#msgSuccess").html("Saved..");
				$("#msgSuccess").show().delay(1000).fadeOut();
			},
			error : function(error) {
				//alert('error: ' + eval(error));
			}
		});
	} 


	function lostFocusSaveBatchInfo(element) {
		var elementId = element.id;
		var elementInfo = elementId.split("_");

		var sampleId = elementInfo[1];
		//var id = elementInfo[2]; //patientInfoId

		//var accountCount = $("#accountCount_" + elementInfo[1]).val();
		var cptCount = $("#cptCount_" + elementInfo[1]).val();
		//var batchPercentage = $("#batchPercentage_" + elementInfo[1]).val();

		$.ajax({
			type : "POST",
			dataType : "json",
			url : contextPath + "/" + moduleName + "/savesampleremark",
			data : {
				'id' : sampleId,
				'cptCount' : cptCount
			},
			success : function(qaPatientInfoId) {
				//alert('success: ' + qaPatientInfoId);
				//var Trans = $("#spnTransactions_"+sampleId).text();
			
				//var acc = parseInt(parseInt(Trans) *  parseInt(batchPercentage) / 100);
				//if(isNaN(acc)) acc = 0;
				//$("#spnBatchPercentage_"+sampleId).html(" = "+ acc);
				
				$("#msgSuccess").html("Saved..");
				$("#msgSuccess").show().delay(1000).fadeOut();
			},
			error : function(error) {
				//alert('error: ' + eval(error));
			}
		});
	}

</script>
