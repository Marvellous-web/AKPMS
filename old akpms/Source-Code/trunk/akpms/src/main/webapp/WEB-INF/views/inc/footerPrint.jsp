<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- <div class="shadow-container">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
        	<td width="10%"><span class="left-shadow"></span></td>
            <td width="80%" class="mid-shadow">&nbsp;</td>
            <td width="10%"><span class="right-shadow"></span></td>
		</tr>
	</table>
</div> -->

<c:if test="${SHOW_FOOTER ne false}">
	<div style="padding-top:15px; background-color:#E1E1E1" class="dashboard-container">
		<table width="95%">
			<tr>
	        	<td width="70%" valign="top">
	        		<c:if test="${not empty PRINT_REPORT_CRITERIA}">
	        			
		        		<strong>Search Criteria:</strong><br/>
		        		<c:forEach items="${PRINT_REPORT_CRITERIA}" var="CRITERIA" >
		        			<c:choose>
		        				<c:when test="${CRITERIA.key eq 'ticketNumberSearch'}">
		        					Ticket Number: ${CRITERIA.value} <br/>
		        				</c:when>
		        				<c:when test="${CRITERIA.key eq 'dateCreatedFrom'}">
		        					Date Created From: ${CRITERIA.value} <br/>
		        				</c:when>
		        				<c:when test="${CRITERIA.key eq 'dateCreatedTo'}">
		        					Date Created To: ${CRITERIA.value} <br/>
		        				</c:when>
		        				<c:when test="${CRITERIA.key eq 'datePostedFrom'}">
		        					Date Posted From: ${CRITERIA.value} <br/>
		        				</c:when>
		        				<c:when test="${CRITERIA.key eq 'datePostedTo'}">
		        					Date Posted To: ${CRITERIA.value} <br/>
		        				</c:when>
		        				<c:when test="${CRITERIA.key eq 'dateDepositFrom'}">
		        					Date Deposit From: ${CRITERIA.value} <br/>
		        				</c:when>
		        				<c:when test="${CRITERIA.key eq 'dateDepositTo'}">
		        					Date Deposit To: ${CRITERIA.value} <br/>
		        				</c:when>
		        				<c:when test="${CRITERIA.key eq 'eraCheckNo'}">
		        					ERA Check No: ${CRITERIA.value} <br/>
		        				</c:when>
		        				<c:when test="${CRITERIA.key eq 'month'}">
		        					Month: ${CRITERIA.value} <br/>
		        				</c:when>
		        				<c:when test="${CRITERIA.key eq 'year'}">
		        					Year: ${CRITERIA.value} <br/>
		        				</c:when>
		        				<c:when test="${CRITERIA.key eq 'transactionType'}">
		        					Transaction Type: ${CRITERIA.value} <br/>
		        				</c:when>
		        				<%-- <c:when test="${CRITERIA.key eq 'transactionType'}">
		        					Transaction Type: ${CRITERIA.value} <br/>
		        				</c:when> --%>
		        				<%-- <c:when test="${CRITERIA.key eq 'ndba'}">
		        					NDBA: True <br/>
		        				</c:when>
		        				<c:when test="${CRITERIA.key eq 'suspenseAccount'}">
		        					Suspense Account: True <br/>
		        				</c:when>
		        				<c:when test="${CRITERIA.key eq 'oldPriorAr'}">
		        					Old Prior AR: True <br/>
		        				</c:when> --%>
		        				<c:otherwise>
		        					<!-- span class="hide">${CRITERIA.key}: ${CRITERIA.value}</span -->
		        				</c:otherwise>
		        			</c:choose>
		        		</c:forEach>
	        		
	        		</c:if>
	        	</td>
	            <td width="30%" align="right" valign="top">
	            	<c:set var="now" value="<%=new java.util.Date()%>"></c:set>
					<fmt:formatDate type="both" dateStyle="long" timeStyle="long"  value="${now}"></fmt:formatDate>
	            </td>
			</tr>
		</table>
	</div>
</c:if>


<script>

$('td').each(function() {
	  var val = $(this).text().replace("$","").substring(0), n = +val;
	  val = val.replace(",","").substring(0), n = +val; // replace ","
	  if (!isNaN(n) && /^\s*[+-]/.test(val)) {
		$(this).addClass(val >= 0 ? 'pos' : 'neg');
	  }
	});
	
window.print();
 
</script>


