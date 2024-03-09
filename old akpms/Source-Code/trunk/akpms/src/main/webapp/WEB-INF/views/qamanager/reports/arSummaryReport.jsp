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
				<tr style="background-color: #DBDBDB">
					<th>QA <br/> By</th>
					<th>QA <br/> Date</th>
					<th>DE <br/> Person</th>
					<th>Posted <br/> Date</th>					
					<th>DOS</th>
					<th>Database</th>
					<th>PT Acct#</th>
					<th>CPT</th>
					<th>Errors</th>
					<th>Remarks</th>
					<c:forEach items="${CHILDREN_QC_POINTS}" var="CHILD_QC_POINT" varStatus="loop">
						<th>
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
				
				<c:set var="preErrCount" value="1" />
				<c:set var="forUncheck" value="0" />
				
				<c:set var="totalTrans" value="0" />
				<c:set var="totalTrans_error" value="0" />
				<c:set var="totalErrors" value="0" />
				<c:set var="totalAccounts" value="0" />
				
				<c:forEach items="${QA_PROD_SAMPLE_DATA_LIST}" var="QA_PROD_SAMPLE_DATA" >
					<tr id="sample_${QA_PROD_SAMPLE_DATA.id}_${QA_PROD_SAMPLE_DATA.arProductivity.createdBy.id}" style="background-color: #f9f9f9;" >
						<td> 
							${QA_PROD_SAMPLE_DATA.qaWorksheet.createdBy.firstName} 
							${QA_PROD_SAMPLE_DATA.qaWorksheet.createdBy.lastName}
						</td>
						<td><fmt:formatDate type="date" value="${QA_PROD_SAMPLE_DATA.qaWorksheet.createdOn}"/> </td>
						<td> 
							${QA_PROD_SAMPLE_DATA.arProductivity.createdBy.firstName} 
							${QA_PROD_SAMPLE_DATA.arProductivity.createdBy.lastName}
						</td>
						<td><fmt:formatDate type="date" value="${QA_PROD_SAMPLE_DATA.arProductivity.createdOn}"/> </td>
						<td> ${QA_PROD_SAMPLE_DATA.arProductivity.dos} </td>
						<td> ${QA_PROD_SAMPLE_DATA.arProductivity.arDatabase.name} </td>
						<td> ${QA_PROD_SAMPLE_DATA.arProductivity.patientAccNo} </td>
						<td> 
							<%-- ${QA_PROD_SAMPLE_DATA.arProductivity.cpt} --%>
							
							<c:set var="myString" value="${QA_PROD_SAMPLE_DATA.arProductivity.cpt}"/>
							<c:catch var="isNumeric">
							  <fmt:formatNumber value="${myString}" pattern="0" var="myInteger"/>
							  <c:set var="passed" value="${myInteger - myString eq 0}"/>
							</c:catch>
							
							<c:choose>
								<c:when test="${passed and isNumeric == null}">
									<c:set var="CPT" value="${QA_PROD_SAMPLE_DATA.arProductivity.cpt}"/>
								</c:when>
								<c:otherwise>
									<c:set var="CPT" value="1"/>
								</c:otherwise>
							</c:choose>
							
							<c:set var="totalTrans" value="${totalTrans + CPT}" />
							${CPT} 
						</td>
						<td>
							<c:set var="error_count" value="${fn:length(QA_PROD_SAMPLE_DATA.qcPointChecklists)}"/>
							<c:set var="totalTrans_error" value="${totalTrans_error + (CPT * error_count)}" />
							${CPT * error_count}
						</td>
						<td> ${QA_PROD_SAMPLE_DATA.remarks}</td>
						<c:if test="${not empty CHILDREN_QC_POINTS}">
							<c:forEach var="CHILD_QC_POINT" items="${CHILDREN_QC_POINTS}" varStatus="loop">
								<td class="chktd">
									<c:choose>
										<c:when test="${fn:length(QA_PROD_SAMPLE_DATA.qcPointChecklists) > 0}">
										
											<c:set var="contains" value="false" />
											<c:forEach items="${QA_PROD_SAMPLE_DATA.qcPointChecklists}" var="QC_POINT_CHECKLIST">
												<c:if test="${QC_POINT_CHECKLIST.qcPoint.id eq CHILD_QC_POINT.id}">
													<c:set var="contains" value="true" />
													<c:set var="preErrCount" value="${preErrCount+1}" />
												</c:if>
											</c:forEach>
											<c:set var="forUncheck" value="${preErrCount}" />
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
						</c:if>
					</tr>
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
								<td>Total Accounts</td><td>${fn:length(QA_PROD_SAMPLE_DATA_LIST)}</td>
							</tr>
							<tr>	
								<td>Total CPTs</td><td>${totalTrans}</td>
							</tr>
							<tr>	
								<td>Total Errors</td><td>${totalTrans_error}</td>
							</tr>
							<tr>	
								<td>Error Rate</td><td><fmt:formatNumber type="number" maxFractionDigits="5" value="${((totalTrans_error*100)/totalTrans)}" />%</td>
							</tr>
						</table>
					</td>
				</tr>		
			</tfoot>
		</table>
		
	
	</div>
</div>

