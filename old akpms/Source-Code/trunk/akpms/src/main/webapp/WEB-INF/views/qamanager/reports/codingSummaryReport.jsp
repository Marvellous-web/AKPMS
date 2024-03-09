<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<fmt:setLocale value="en_US" />
<div class="dashboard-container">

	<div style="width: 100%; text-align: center" id="reportHeading">
		<strong>ARGUS QA REPORTING</strong> 
		<br /> DEPARTMENT: ${fn:toUpperCase(department)} 
		<br /> FROM THE DATE: ${fn:toUpperCase(startMonth)} TO ${fn:toUpperCase(endMonth)}
		<br /> TEAM: ${fn:toUpperCase(subDepartmentName)} 
	</div>

	<div class="table-grid" style="overflow-x: auto;">

		<table style="font-size: 9px !important; table-layout: fixed; margin: auto">
			<thead>
				<c:if test="${not empty PARENT_QC_POINTS}">
					<tr>
						<th colspan="9">&nbsp;</th>
						<c:forEach var="PARENT_QC_POINT" items="${PARENT_QC_POINTS}">
							<th colspan="${PARENT_QC_POINT.childCount}">${PARENT_QC_POINT.name}</th>
						</c:forEach>
					</tr>
				</c:if>
				<tr style="background-color: #999999; color: white">
					<th>QA <br/> By</th>
					<th>QA <br/> Date</th>
					<th>DE <br /> Person</th>
					<th>Batch <br /> No</th>
					<th>Scan <br /> Date</th>
					<th>Posted <br /> Date</th>
					
					<th>QCed <br/> Accounts</th>
					<th>CPTs</th>
					<!-- <th>Batch<br />Perc.</th> -->
					
					<th>Physician <br /> Group</th>
			
					<c:forEach items="${CHILDREN_QC_POINTS}" var="CHILD_QC_POINT" varStatus="loop">
						<th class="chktd">
							${loop.index+1}
						</th>
					</c:forEach>
				</tr>
			</thead>
				
			<tbody>
				<c:if test="${fn:length(QA_PROD_SAMPLE_DATA_LIST) eq 0}">
					<tr>
						<td colspan="${fn:length(CHILDREN_QC_POINTS) + 9}">No Record Found.</td>
					</tr>
				</c:if>
				
				<c:set var="totalTrans" value="0" />
				<c:set var="totalErrors" value="0" />
				<c:set var="totalAccounts" value="0" />
				<c:set var="totalAccountsAdded" value="0" />
				<c:set var="totalQCedAccounts" value="0" />
				<c:forEach items="${QA_PROD_SAMPLE_DATA_LIST}" var="QA_PROD_SAMPLE_DATA" varStatus="loop">
				
					<tr id="sample_${QA_PROD_SAMPLE_DATA.id}_${QA_PROD_SAMPLE_DATA.chargeProductivity.createdBy.id}" style="background-color: #D6D6D6;" >
						<td> 
							${QA_PROD_SAMPLE_DATA.qaWorksheet.createdBy.firstName} 
							${QA_PROD_SAMPLE_DATA.qaWorksheet.createdBy.lastName}
						</td>
						<td><fmt:formatDate type="date" value="${QA_PROD_SAMPLE_DATA.qaWorksheet.createdOn}"/> </td>
						
						<td>
							${QA_PROD_SAMPLE_DATA.chargeProductivity.createdBy.firstName}
							${QA_PROD_SAMPLE_DATA.chargeProductivity.createdBy.lastName}
						</td>
						
						<td> ${QA_PROD_SAMPLE_DATA.chargeProductivity.ticketNumber.id} </td>
						
						<td> <fmt:formatDate type="date" value="${QA_PROD_SAMPLE_DATA.chargeProductivity.scanDate}"/> </td>
						<td> <fmt:formatDate type="date" value="${QA_PROD_SAMPLE_DATA.chargeProductivity.createdOn}"/> </td>
						
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
						<c:set var="totalQCedAccounts" value="${totalQCedAccounts + totalAccounts}" />
						<td>${totalAccounts}</td>
						<td>${QA_PROD_SAMPLE_DATA.cptCount}</td>
						
						<td colspan="${fn:length(CHILDREN_QC_POINTS) + 1}"> ${QA_PROD_SAMPLE_DATA.chargeProductivity.ticketNumber.doctor.name} </td>
					</tr>
						
						<c:if test="${fn:length(QA_PROD_SAMPLE_DATA.qaWorksheetPatientInfos) gt 0}">
							<tr>
								<td colspan='2'  style="background-color: #E0E1E5;">
									Patient Name
								</td>
								<td  style="background-color: #E0E1E5;">
									Acc No
								</td>
								
								<td  style="background-color: #E0E1E5;">
									CPT
								</td>
								<td  style="background-color: #E0E1E5;">
									Page
								</td>
								
								<td colspan="${fn:length(CHILDREN_QC_POINTS) + 5}"  style="background-color: #E0E1E5;">
									Remarks
								</td>
							</tr>
						</c:if>
						<c:set var="totalAccountsAdded" value="${totalAccountsAdded + fn:length(QA_PROD_SAMPLE_DATA.qaWorksheetPatientInfos)}" />
						
						
						<c:forEach items="${QA_PROD_SAMPLE_DATA.qaWorksheetPatientInfos}" var="PATIENT_INFO">
							<tr id="patientInfo_${PATIENT_INFO.id}">
								<td colspan='2'>
									${PATIENT_INFO.patientName}
								</td>
								<td>
									${PATIENT_INFO.accountNumber}
								</td>
								
								<td>
									${PATIENT_INFO.cptCodesDemo}
									
									
									<c:set var="myString" value="${PATIENT_INFO.cptCodesDemo}"/>
									<c:catch var="isNumeric">
									  	<fmt:formatNumber value="${myString}" pattern="0" var="myInteger"/>
									  	<c:set var="passed" value="${myInteger - myString eq 0}"/>
									</c:catch>
									
									<c:choose>
										<c:when test="${passed and isNumeric == null}">
											<c:set var="CPTNumeric" value="${PATIENT_INFO.cptCodesDemo}"/>
										</c:when>
										<c:otherwise>
											<c:set var="CPTNumeric" value="0"/>
										</c:otherwise>
									</c:choose>
									
									<c:set var="totalTrans" value="${totalTrans + CPTNumeric}" />
									
								</td>
								<td>
									${PATIENT_INFO.srNo}
								</td>
								
								<td colspan="4">
									${PATIENT_INFO.remarks}
								</td>
								
								<c:forEach items="${CHILDREN_QC_POINTS}" var="CHILD_QC_POINT" varStatus="loop">
									<td class="chktd">
										<c:choose>
											<c:when test="${fn:length(PATIENT_INFO.qcPointChecklist) > 0}">
											
												<c:set var="contains" value="false" />
												<c:forEach items="${PATIENT_INFO.qcPointChecklist}" var="QC_POINT_CHECKLIST">
													<c:if test="${QC_POINT_CHECKLIST.qcPoint.id eq CHILD_QC_POINT.id}">
														<c:set var="contains" value="true" />
													</c:if>
												</c:forEach>
												
												<c:choose>
													<c:when test="${contains}">
														<c:set var="totalErrors" value="${totalErrors+1}" />
														1
													</c:when>
													<c:otherwise>
														0
													</c:otherwise>
												</c:choose>
																														
											</c:when>
											<c:otherwise>
												0
											</c:otherwise>
										</c:choose>
									</td>
								</c:forEach>
							</tr>
					</c:forEach>
				</c:forEach>
				
			</tbody>
			
			<tfoot>
				<tr style="background-color: #DBDBDB">
					<td colspan="${fn:length(CHILDREN_QC_POINTS) + 9}" align="center">
						<table>
							<tr>
								<td colspan="2" style="background-color: #E0E1E5;">Summary</td>
							</tr>
							<tr>	
								<td>Total Batch</td><td>${fn:length(QA_PROD_SAMPLE_DATA_LIST)}</td>
							</tr>
							<%-- <tr>	
								<td>Total Accounts</td><td>${totalAccountsAdded}</td>
							</tr> --%>
							<tr>	
								<td>Total QCed Accounts</td><td>${totalQCedAccounts}</td>
							</tr>
							<tr>	
								<td>Total CPTs</td><td>${totalTrans}</td>
							</tr>
							<tr>	
								<td>Total Errors</td><td>${totalErrors}</td>
							</tr>
							<tr>	
								<td>Error Rate</td><td><fmt:formatNumber type="number" maxFractionDigits="5" value="${totalErrors / (fn:length(CHILDREN_QC_POINTS) * totalAccountsAdded)}" /></td>
							</tr>
						</table>
					</td>
				</tr>		
			</tfoot>
		</table>
		
	
	</div>
</div>

