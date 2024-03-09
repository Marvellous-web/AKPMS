<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="dashboard-container">

		<c:forEach var="chargebatch" items="${CHARGE_BATCHES}" varStatus="counter">
			<div style='text-align:center'>
			   	<h1>Argus Medical Management, LLC.<br/>
					5150 E.Pacific Coast Hwy, Suite 500<br/>
					Long Beach CA 90804<br/>
				</h1>
				<br/>
			</div>

		<div style='text-align: center; font-size: 16px;'>CHARGES/SUPERBILLS BATCH HEADER FORM</div>
		<br/>
				
		<div class="table-grid" style="overflow-x: auto;">
					<table style="width: 17cm;margin: auto; table-layout: fixed; border-width: 1px;">
						<tbody>
							<tr>
								<td class="left-heading"><strong>Batch ID</Strong></td>
								<td class="left-text"><strong style="font-size: 15pt;">${chargebatch.id}</strong></td>
								
								<td class="left-heading"><strong>Type</Strong></td>
								<td class="left-text" ><spring:message code="${chargebatch.type}"></spring:message></td>
							</tr>
							
							<tr>
								<td colspan="4" class="left-heading"><strong style="font-size: 12pt;">Part 1 Doctor Office</strong></td>								
							</tr>
							
							<tr>
								<td class="left-heading"><strong>Office Name/Location</strong></td>
								<td class="left-text" >${chargebatch.doctor.name}
									<c:if test="${not empty chargebatch.doctor.parent}">(${chargebatch.doctor.parent.name})</c:if>
								</td>
																
								<td class="right-heading"><strong>Date/s of Service</strong></td>
								<td class="right-text" ><fmt:formatDate value="${chargebatch.dosFrom}" pattern="MM/dd/yyyy"/> - <br/><fmt:formatDate value="${chargebatch.dosTo}" pattern="MM/dd/yyyy"/></td>
							</tr>
							
							<tr>
								<td class="left-heading"><strong>Prepared by</strong></td>
								<td class="left-text" >${chargebatch.createdBy.firstName} ${chargebatch.createdBy.lastName}</td>
							
								<td class="right-heading"><strong>Total No. of Superbills</strong></td>
								<td class="right-text" >${chargebatch.numberOfSuperbills}</td>
							</tr>
							
							<tr>
								<td class="left-heading"><strong>Date Prepared</strong></td>
								<td class="left-text"><fmt:formatDate value="${chargebatch.createdOn}" pattern="MM/dd/yyyy"/></td>
								
								<td class="right-heading"><strong>Total No. of Attachments</strong></td>
								<td class="right-text">${chargebatch.numberOfAttachments}</td>
							</tr>
							
							<tr>
								<td colspan="2" class="left-heading"></td>																
								<td class="right-heading"><strong>Total No. of Pages</strong></td>
								<td class="right-text">${chargebatch.numberOfAttachments + chargebatch.numberOfSuperbills}</td>
							</tr>
							
							<tr>
								<td class="left-heading"><strong>Notes:</strong></td>
								<td class="left-text" colspan="3">${chargebatch.remarks}</td>
																
							</tr>
							
							<tr>
								<td colspan="4" class="left-heading" height="20px"/>
							</tr>
							
							<tr>
								<td colspan="4" class="left-heading"><strong style="font-size: 12pt;">Part 2 Argus Coding Department</strong></td>								
							</tr>
							
							<tr>
								<td class="left-heading"><strong>Date Received</strong></td>
								<td class="left-text"><fmt:formatDate value="${chargebatch.dateReceived}" pattern="MM/dd/yyyy"/></td>
								
								<td class="right-heading"><strong>Received By</strong></td>
								<td class="right-text" >${chargebatch.receivedBy.firstName} ${chargebatch.receivedBy.lastName}</td>
							</tr>
							
							<tr>
								<td class="left-heading"><strong>Total No. of Superbills</strong></td>
								<td class="left-text">${chargebatch.numberOfSuperbillsArgus} </td>
								
								<td class="right-heading"><strong>Superbill Difference</strong></td>
								<td class="right-text" >${chargebatch.numberOfSuperbills - chargebatch.numberOfSuperbillsArgus}</td>
							</tr>
							
							<tr>
								<td class="left-heading"><strong>Total No. of Attachments</strong></td>
								<td class="left-text">${chargebatch.numberOfAttachmentsArgus}</td>
								
								<td class="right-heading"><strong>Attachment Difference</strong></td>
								<td class="right-text" >${chargebatch.numberOfAttachments - chargebatch.numberOfAttachmentsArgus}</td>
							</tr>
							
							<tr>
								<td class="left-heading"><strong>Total No. of Pages</strong></td>
								<td class="right-text" >${chargebatch.numberOfAttachmentsArgus + chargebatch.numberOfSuperbillsArgus}</td>
								
								<td class="right-heading"><strong>Total Pages Difference</strong></td>
								<td>${(chargebatch.numberOfSuperbills - chargebatch.numberOfSuperbillsArgus) + (chargebatch.numberOfAttachments - chargebatch.numberOfAttachmentsArgus)}</td>
							</tr>
							
							<tr>
								<td class="left-heading"><strong>Notes:</strong></td>
								<td class="left-text" colspan="3">${chargebatch.remarksArgus}</td>
																
							</tr>
							
						</tbody>
					</table>
				</div>
					<c:if test="${counter.last eq false}">	
						<div class="page-break"></div>
					</c:if>
					<!--  <div class="page-break"></div>-->
								
				</c:forEach>
	</div>
