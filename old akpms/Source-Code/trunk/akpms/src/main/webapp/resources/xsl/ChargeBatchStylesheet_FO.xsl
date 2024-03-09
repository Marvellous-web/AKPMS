<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format"
	xmlns:barcode="org.krysalis.barcode4j.xalan.BarcodeExt" xmlns:common="http://exslt.org/common"
	xmlns:xalan="http://xml.apache.org" exclude-result-prefixes="barcode common xalan">
	<xsl:template match="/">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
			<fo:layout-master-set>
				<fo:simple-page-master master-name="simple"
					page-height="11in" page-width="8in" margin-left="0.2cm"
					margin-right="0.2cm" margin-bottom="1cm" margin-top="0.5cm">
					<fo:region-body margin-top="0.5cm" />
				</fo:simple-page-master>
			</fo:layout-master-set>
			<fo:page-sequence master-reference="simple">
				<fo:flow flow-name="xsl-region-body">
					<xsl:choose>
						<xsl:when test="empty">
							<fo:block>
								<fo:block font-size="9pt">
									<xsl:value-of select="empty" />
								</fo:block>
							</fo:block>
						</xsl:when>
						<xsl:otherwise>

							<xsl:for-each select="list/ChargeBatchProcessing">
								<fo:block text-indent="1em" font-family="sans-serif"
									font-size="14pt" space-before.minimum="2pt"
									space-before.maximum="6pt" space-before.optimum="4pt"
									space-after.minimum="2pt" space-after.maximum="6pt"
									space-after.optimum="4pt">
									<fo:block>Argus Medical Management, LLC.</fo:block>
									<fo:block>5150 E.Pacific Coast Hwy, Suite 500</fo:block>
									<fo:block>Long Beach CA 90804</fo:block>
								</fo:block>
								<fo:block start-indent="1em" space-before.minimum="10pt"
									space-before.maximum="30pt" space-before.optimum="15pt"
									space-after.minimum="2pt" space-after.maximum="6pt"
									space-after.optimum="4pt" font-family="sans" font-size="12pt"
									font-weight="normal">


									<fo:block>
										<fo:inline font-weight="bold">Batch ID: </fo:inline>
										<xsl:value-of select="id" />
									</fo:block>
									<fo:block>
										<fo:inline font-weight="bold">Type: </fo:inline>
										<xsl:value-of select="type" />
									</fo:block>

									<fo:table border="solid 0.1mm black">
										<fo:table-column column-number="1"
											column-width="1.8in" border="solid 0.5mm black" />
										<fo:table-column column-number="2"
											column-width="1.8in" border="solid 0.5mm black" />
										<fo:table-column column-number="3"
											column-width="1.8in" border="solid 0.5mm black" />
										<fo:table-column column-number="4"
											column-width="2in" border="solid 0.5mm black" />
										<fo:table-body height="9in" border="solid 0.1mm black">

											<fo:table-row>
												<fo:table-cell text-align="left"
													number-columns-spanned="4">
													<fo:block font-family="sans-serif" font-weight="bold"
														font-size="13pt">
														Part 1 Doctor Office
													</fo:block>
												</fo:table-cell>
											</fo:table-row>

											<fo:table-row border="solid 0.5mm black">
												<fo:table-cell text-align="left">
													<fo:block font-weight="bold">
														Office Name/Location
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block>
														<xsl:value-of select="doctor/name" />
													</fo:block>
													<fo:block>
														<xsl:value-of select="doctor/parent/name" />
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block font-weight="bold">
														Date/s of Service
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block>
														<xsl:value-of select="dosFrom" />
														-
														<xsl:value-of select="dosTo" />
													</fo:block>
												</fo:table-cell>
											</fo:table-row>

											<fo:table-row border="solid 0.5mm black">
												<fo:table-cell text-align="left">
													<fo:block font-weight="bold">
														Prepared by
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block>
														<xsl:value-of select="createdBy/name" />
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block font-weight="bold">
														Total No. of Superbills
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block>
														<xsl:value-of select="numberOfSuperbills" />
													</fo:block>
												</fo:table-cell>
											</fo:table-row>

											<fo:table-row border="solid 0.5mm black">
												<fo:table-cell text-align="left">
													<fo:block font-weight="bold">
														Date Prepared
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block>
														<xsl:value-of select="createdOn" />
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block font-weight="bold">
														Total No. of Attachments
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block>
														<xsl:value-of select="numberOfAttachments" />
													</fo:block>
												</fo:table-cell>
											</fo:table-row>


											<fo:table-row border="solid 0.5mm black">
												<fo:table-cell text-align="left">
													<fo:block font-weight="bold">

													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block>

													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block font-weight="bold">
														Total No. of Pages
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block>
														<xsl:value-of select="(numberOfAttachments+numberOfSuperbills)" />
													</fo:block>
												</fo:table-cell>
											</fo:table-row>

											<fo:table-row border="solid 0.5mm black">
												<fo:table-cell text-align="left"
													number-columns-spanned="4">
													<fo:block>
														<fo:inline font-weight="bold"> Notes: </fo:inline>
														<xsl:value-of select="remarks" />
													</fo:block>
												</fo:table-cell>
											</fo:table-row>

										</fo:table-body>
									</fo:table>

									<fo:block start-indent="1em" space-before.minimum="10pt"
										space-before.maximum="15pt" space-before.optimum="15pt"
										space-after.minimum="2pt" space-after.maximum="6pt"
										space-after.optimum="4pt" font-family="sans" font-size="12pt"
										font-weight="normal">
										<fo:table>
											<fo:table-column column-number="1"
												column-width="1.8in" border="solid 0.5mm black" />
											<fo:table-column column-number="2"
												column-width="1.8in" border="solid 0.5mm black" />
											<fo:table-column column-number="3"
												column-width="1.8in" border="solid 0.5mm black" />
											<fo:table-column column-number="4"
												column-width="2in" border="solid 0.5mm black" />
											<fo:table-body height="9in" border="solid 0.1mm black">

												<fo:table-row border="solid 0.5mm black">
													<fo:table-cell text-align="left"
														number-columns-spanned="4">
														<fo:block font-family="sans-serif" font-weight="bold"
															font-size="13pt">
															Part 2 Argus Coding Department
														</fo:block>
													</fo:table-cell>
												</fo:table-row>

												<fo:table-row border="solid 0.5mm black">
													<fo:table-cell text-align="left">
														<fo:block font-weight="bold">
															Date Received
														</fo:block>
													</fo:table-cell>
													<fo:table-cell text-align="left">
														<fo:block>
															<xsl:value-of select="dateReceived" />
														</fo:block>
													</fo:table-cell>
													<fo:table-cell text-align="left">
														<fo:block font-weight="bold">
															Received By
														</fo:block>
													</fo:table-cell>
													<fo:table-cell text-align="left">
														<fo:block>
															<xsl:value-of select="receivedBy/name" />
														</fo:block>
													</fo:table-cell>
												</fo:table-row>

												<fo:table-row border="solid 0.5mm black">
													<fo:table-cell text-align="left">
														<fo:block font-weight="bold">
															Total No. of Superbills
														</fo:block>
													</fo:table-cell>
													<fo:table-cell text-align="left">
														<fo:block>
															<xsl:value-of select="numberOfSuperbillsArgus" />
														</fo:block>
													</fo:table-cell>
													<fo:table-cell text-align="left">
														<fo:block font-weight="bold">
															Superbill Difference
														</fo:block>
													</fo:table-cell>
													<fo:table-cell text-align="left">
														<fo:block>
															<xsl:value-of select="superBillDifference" />
														</fo:block>
													</fo:table-cell>
												</fo:table-row>

												<fo:table-row border="solid 0.5mm black">
													<fo:table-cell text-align="left">
														<fo:block font-weight="bold">
															Total No. of Attachments
														</fo:block>
													</fo:table-cell>
													<fo:table-cell text-align="left">
														<fo:block>
															<xsl:value-of select="numberOfAttachmentsArgus" />
														</fo:block>
													</fo:table-cell>
													<fo:table-cell text-align="left">
														<fo:block font-weight="bold">
															Attachment Difference
														</fo:block>
													</fo:table-cell>
													<fo:table-cell text-align="left">
														<fo:block>
															<xsl:value-of select="attachmentDifference" />
														</fo:block>
													</fo:table-cell>
												</fo:table-row>

												<fo:table-row border="solid 0.5mm black">
													<fo:table-cell text-align="left">
														<fo:block font-weight="bold">
															Total No. of Pages
														</fo:block>
													</fo:table-cell>
													<fo:table-cell text-align="left">
														<fo:block>
															<xsl:value-of
																select="(numberOfAttachmentsArgus+numberOfSuperbillsArgus)" />
														</fo:block>
													</fo:table-cell>
													<fo:table-cell text-align="left">
														<fo:block font-weight="bold">
															Total Pages Difference
														</fo:block>
													</fo:table-cell>
													<fo:table-cell text-align="left">
														<fo:block>
															 <xsl:value-of
																select="(superBillDifference+attachmentDifference)" />
														</fo:block>
													</fo:table-cell>
												</fo:table-row>

												<fo:table-row border="solid 0.5mm black">
													<fo:table-cell text-align="left"
														number-columns-spanned="4">
														<fo:block>
															<fo:inline font-weight="bold">Notes: </fo:inline>
															<xsl:value-of select="remarksArgus" />
														</fo:block>
													</fo:table-cell>
												</fo:table-row>
											</fo:table-body>
										</fo:table>
									</fo:block>

									 <xsl:if test="not(position()=last())">
								       	<fo:block break-before="page" />
								     </xsl:if>

								</fo:block>
							</xsl:for-each>
						</xsl:otherwise>
					</xsl:choose>
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>
</xsl:stylesheet>
