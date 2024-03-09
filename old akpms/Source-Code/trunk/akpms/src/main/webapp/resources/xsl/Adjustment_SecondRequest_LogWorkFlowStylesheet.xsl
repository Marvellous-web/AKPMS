<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<html>
			<body>
			<xsl:choose>
				<xsl:when test="empty">
					<h1><xsl:value-of select="empty"/></h1>
				</xsl:when>
				<xsl:otherwise>
					<div>
						Argus Medical Management, LLC.<br/>
						5150 E.Pacific Coast Hwy, Suite 500<br/>
						Long Beach CA 90804<br/>
					</div>

						<table border="1">
							<tr>
								<th>Work Flow</th>
								<th>Patient ID</th>
								<th>Patient Name</th>
								<th>DOS</th>
								<th>CPT</th>
								<th>Doctor</th>
								<th>Insurance</th>
								<th>Database</th>
								<th>Balance</th>
								<th>Status Code</th>
								<th>Source</th>
							</tr>
							<xsl:for-each select="list/ARLogWorkFlow">
								<tr>
									<td>
										<xsl:if test="boolean(workFlowStatus = 1)">
											<xsl:text>Approve</xsl:text>
										</xsl:if>
										<xsl:if test="boolean(workFlowStatus = 2)">
											<xsl:text>Reject</xsl:text>
										</xsl:if>
										<xsl:if test="boolean(workFlowStatus = 3)">
											<xsl:text>Escalate</xsl:text>
										</xsl:if>
										<xsl:if test="boolean(status = 1)">
											<xsl:text>Approve</xsl:text>
										</xsl:if>
										<xsl:if test="boolean(status = 2)">
											<xsl:text>Reject</xsl:text>
										</xsl:if>
										<xsl:if test="boolean(status = 3)">
											<xsl:text>Escalate</xsl:text>
										</xsl:if>
										<!-- <xsl:value-of select="arProductivity/workFlowName"/> -->
									</td>
									<td>
										<xsl:value-of select="arProductivity/patientAccNo"/>
									</td>
									<td>
										<xsl:value-of select="arProductivity/patientName"/>
									</td>
									<td>
										<xsl:value-of select="arProductivity/dos"/>
									</td>
									<td>
										<xsl:value-of select="arProductivity/cpt"/>
									</td>
									<td>
										<xsl:value-of select="arProductivity/doctor/name"/>
									</td>
									<td>
										<xsl:value-of select="arProductivity/insurance/name"/>
									</td>
									<td>
										<xsl:value-of select="arProductivity/dataBas"/>
									</td>
									<td>
										<xsl:value-of select="arProductivity/balanceAmt"/>
									</td>
									<td>
										<xsl:value-of select="arProductivity/statusCode"/>
									</td>
									<td>
										<xsl:value-of select="arProductivity/source"/>
									</td>
								</tr>
							</xsl:for-each>
						</table>
				</xsl:otherwise>
			</xsl:choose>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>
