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
								<th>Patient ID</th>
								<th>Patient Name</th>
								<th>Check No.</th>
								<th>Issue Date</th>
								<th>Cashed Date</th>
								<th>Attachment</th>
							</tr>
							<xsl:for-each select="list/RequestCheckTracerWorkFlow">
								<tr>

									<td>
										<xsl:value-of select="arProductivity/patientAccNo"/>
									</td>
									<td>
										<xsl:value-of select="arProductivity/patientName"/>
									</td>
									<td>
										<xsl:value-of select="checkNo"/>
									</td>
									<td>
										<xsl:value-of select="checkIssueDate"/>
									</td>
									<td>
										<xsl:value-of select="checkCashedDate"/>
									</td>
									<td>
										<xsl:value-of select="attachment/name"/>
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
