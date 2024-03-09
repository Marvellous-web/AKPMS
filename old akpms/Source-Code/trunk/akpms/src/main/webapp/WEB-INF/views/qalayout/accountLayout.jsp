<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<h1>in account layout jsp</h1>
<!-- Bread Crumb End-->
<div class="alert alert-danger alert-dismissible" role="alert">
	<button type="button" class="close" data-dismiss="alert">
		<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
	</button>
	<strong>${error}</strong>
</div>

<table class="table">
	<thead>
		<c:if test="${not empty PARENT_QC_POINTS}">
			<tr>
				<th colspan="4">&nbsp;</th>
				<c:forEach var="PARENT_QC_POINT" items="${PARENT_QC_POINTS}">
					<th colspan="${PARENT_QC_POINT.childCount}">${PARENT_QC_POINT.name}</th>
				</c:forEach>
			</tr>
		</c:if>
		<c:forEach items="${CHILDREN_QC_POINTS}" var="CHILD_QC_POINT" varStatus="loop">
			<th>
				<button type="button" class="btn btn-link" data-toggle="tooltip" style="padding:0px;" data-placement="top" title="${CHILD_QC_POINT.name}">${loop.index+1}</button>
			</th>
		</c:forEach>
		<tr>
			<th>Received Date</th>
			<th>QA Date</th>
			<th>DE Person</th>
			<th>Batch#</th>
			<c:forEach items="${QC_POINTS}" var="QC_POINT_PARENT">
				<th><strong>${QC_POINT_PARENT.key.name}</strong> <c:forEach
						items="${QC_POINT_PARENT.value}" var="QC_POINT_CHILD">
						<span>${QC_POINT_CHILD.name}</span>
					</c:forEach></th>
			</c:forEach>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${QA_PROD_DATA}" var="QA_PRODUCTIVITIES">
			<tr>
				<td><fmt:formatDate type="date"
						value="${QA_PRODUCTIVITIES.credentialingAccountingProductivity.dateRecd}" />
				</td>
				<td><fmt:formatDate type="date"
						value="${QA_PRODUCTIVITIES.createdOn}" /></td>
				<td>
					${QA_PRODUCTIVITIES.credentialingAccountingProductivity.createdBy.firstName}
				</td>
				<td>
					${QA_PRODUCTIVITIES.credentialingAccountingProductivity.id}</td>
			</tr>
		</c:forEach>
	</tbody>
	
	<tfoot>
	</tfoot>
</table>