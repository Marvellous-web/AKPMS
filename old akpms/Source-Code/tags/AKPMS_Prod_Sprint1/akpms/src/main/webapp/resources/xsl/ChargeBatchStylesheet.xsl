<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<html>
			<body>
			<h1><xsl:value-of select="empty"/></h1>
				<xsl:for-each select="list/ChargeBatchProcessing">

					<div>
						Argus Medical Management, LLC.<br/>
						5150 E.Pacific Coast Hwy, Suite 500<br/>
						Long Beach CA 90804<br/>
					</div>
					<p>CHARGES/SUPER BILLS BATCH HEADER FORM</p>
					Batch ID: <xsl:value-of select="id"/><br/>
					Type: <xsl:value-of select="type"/>
					<table border="1">
						<tr  bgcolor="#9acd32">
							<td colspan="4">Part 1 Doctor Office</td>
						</tr>
						<tr>
							<td>Office Name/Location</td>
							<td><xsl:value-of select="doctor/name"/></td>
							<td>Date of Service</td>
							<td><xsl:value-of select="dosFrom"/></td>
						</tr>
						<tr>
							<td>Prepared by</td>
							<td><xsl:value-of select="createdBy/name"/></td>
							<td>Total no. of Superbills</td>
							<td><xsl:value-of select="numberOfSuperbills"/></td>
						</tr>
						<tr>
							<td>Date Prepared </td>
							<td><xsl:value-of select="createdOn"/></td>
							<td>Total no. of Attachments</td>
							<td><xsl:value-of select="numberOfAttachments"/></td>
						</tr>
						<!-- <tr>
							<td></td>
							<td></td>
							<td>Total no. of Pages</td>
							<td><xsl:value-of select="ChargeBatchProcessing/dosFrom"/></td>
						</tr> -->
						<tr>
							<td colspan="4">Notes: <xsl:value-of select="remarks"/></td>
						</tr>
						<tr>
							<td colspan="4"><br/></td>
						</tr>
						<tr  bgcolor="#9acd32">
							<td colspan="4">Part 2 Argus Coding Department</td>
						</tr>
						<tr>
							<td>Date Received</td>
							<td><xsl:value-of select="dateReceived"/></td>
							<td>Received By</td>
							<td><xsl:value-of select="receviedBy/name"/></td>
						</tr>
						<tr>
							<td>Total no. of Superbills</td>
							<td><xsl:value-of select="numberOfSuperbillsArgus"/></td>
							<td>Superbill Difference</td>
							<td><xsl:value-of select="superBillDifference"/></td>
						</tr>
						<tr>
							<td>Total no. of Attachments </td>
							<td><xsl:value-of select="numberOfAttachmentsArgus"/></td>
							<td>Attachment Difference</td>
							<td><xsl:value-of select="attachmentDifference"/></td>
						</tr>
						<!-- <tr>
							<td>Total no. of Pages</td>
							<td></td>
							<td>Total Pages Difference</td>
							<td></td>
						</tr>
						<tr>
							<td>Total Amount of Batch</td>
							<td></td>
							<td>Amount Difference</td>
							<td></td>
						</tr> -->
						<tr>
							<td colspan="4">Notes: <xsl:value-of select="remarksArgus"/></td>
						</tr>
					</table>
				</xsl:for-each>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>
