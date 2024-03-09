<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<!-- TODO: Auto-generated template -->
		<html>
			<body>
			<h1><xsl:value-of select="empty"/></h1>
				<xsl:for-each select="list/PaymentBatch">

				Ticket Number	<xsl:value-of select="id"/><br/>
				Created On <xsl:value-of select="createdOn"/><br/>
				Created By <xsl:value-of select="createdBy/name"/><br/>
				Doctor Name <xsl:value-of select="doctor/name"/><br/>
				Billing Month <xsl:value-of select="billingMonth"/> / <xsl:value-of select="billingYear"/>
				<br/><br/><br/>
				<table>
					<tr>
						<td cellspacing="2"> Deposit Date </td>
						<td> <xsl:value-of select="depositDate"/></td>
						<td> CT Posted Date</td>
						<td> <xsl:value-of select="datePosted"/></td>
					</tr>
					<tr>
						<td>Insurance</td>
						<td><xsl:value-of select="insurance/name"/></td>
						<td>Posted By</td>
						<td><xsl:value-of select="postedBy"/></td>
					</tr>
					<tr>
						<td> Payment Type</td>
						<td> <xsl:value-of select="paymentType/name"/></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td> Money Source</td>
						<td> <xsl:value-of select="moneySource/name"/></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td> ERA Check #</td>
						<td> <xsl:value-of select="eraCheckNo"/></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td> Comment</td>
						<td> <xsl:value-of select="comment"/></td>
					</tr>
					<tr>
						<td> Revenue Type</td>
						<td> <xsl:value-of select="revenueType/name"/></td>
					</tr>
					<tr></tr>
					<tr></tr>
					<tr></tr>
					<tr></tr>
					<tr>
						<td> Deposit Amount</td>
						<td> <xsl:value-of select="depositAmount"/></td>
					</tr>
					<tr>
						<td> Agency Money</td>
						<td> <xsl:value-of select="agencyMoney"/></td>
						<td> CT Posted Total</td>
						<td> <xsl:value-of select="ctPostedTotal"/></td>
					</tr>
					<tr>
						<td> Other Income</td>
						<td> <xsl:value-of select="otherIncome"/></td>
						<td> Suspense Account</td>
						<td> <xsl:value-of select="suspenseAccount"/></td>
					</tr>
					<tr>
						<td> Old prior A/R</td>
						<td> <xsl:value-of select="oldPriorAr"/></td>
						<td> +/- Posting</td>
						<td> <xsl:value-of select="posting"/></td>

					</tr>


				</table>


			<br/><br/><br/>
				</xsl:for-each>

			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>
