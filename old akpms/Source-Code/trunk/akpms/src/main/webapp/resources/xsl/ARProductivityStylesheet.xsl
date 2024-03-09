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
							<xsl:for-each select="list/ArProductivity">
								<tr>
									<td>
										<xsl:value-of select="workFlowName"/>
									</td>
									<td>
										<xsl:value-of select="patientAccNo"/>
									</td>
									<td>
										<xsl:value-of select="patientName"/>
									</td>
									<td>
										<xsl:value-of select="dos"/>
									</td>
									<td>
										<xsl:value-of select="cpt"/>
									</td>
									<td>
										<xsl:value-of select="doctor/name"/>
									</td>
									<td>
										<xsl:value-of select="insurance/name"/>
									</td>
									<td>
										<xsl:value-of select="dataBas"/>
									</td>
									<td>
										<xsl:value-of select="balanceAmt"/>
									</td>
									<td>
										<xsl:value-of select="statusCode"/>
									</td>
									<td>
										<xsl:value-of select="source"/>
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
