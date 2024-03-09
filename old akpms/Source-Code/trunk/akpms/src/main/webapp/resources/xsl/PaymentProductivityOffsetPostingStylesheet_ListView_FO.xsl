<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format"
                xmlns:barcode="org.krysalis.barcode4j.xalan.BarcodeExt" xmlns:common="http://exslt.org/common"
                xmlns:xalan="http://xml.apache.org" exclude-result-prefixes="barcode common xalan">
	<xsl:template match="/">
	<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
		<fo:layout-master-set>
            <fo:simple-page-master master-name="simple"
                            page-height="8in" page-width="11in" margin-left="0.2cm"
                            margin-right="0.2cm" margin-bottom="1cm" margin-top="0.5cm">
                            <fo:region-body margin-top="0.5cm" />
            </fo:simple-page-master>
            </fo:layout-master-set>
            	<fo:page-sequence master-reference="simple">
                	<fo:flow flow-name="xsl-region-body">
						<xsl:choose>
							<xsl:when test="empty">
								<fo:block>
									<fo:block font-size="10pt"><xsl:value-of select="empty"/></fo:block>
								</fo:block>
							</xsl:when>
							<xsl:otherwise>
							<fo:block  space-before.minimum="10pt"
					        space-before.maximum="30pt"
					        space-before.optimum="15pt"
					        space-after.minimum="2pt"
					        space-after.maximum="6pt"
					        space-after.optimum="4pt" text-align="center" font-family="Verdana, Geneva, sans-serif" font-size="9pt" font-weight="normal">
					        <fo:block font-size="14pt" font-weight="bold">
					        	ARGUS MEDICAL MANAGEMENT, LLC.
					        </fo:block>
					        <fo:block>
					        	PAYMENTS PRODUCTIVITY TRACKING SYSTEM
					        </fo:block>
					        </fo:block>
                        <fo:block  space-before.minimum="10pt"
					        space-before.maximum="30pt"
					        space-before.optimum="15pt"
					        space-after.minimum="2pt"
					        space-after.maximum="6pt"
					        space-after.optimum="4pt" font-family="Verdana, Geneva, sans-serif" font-size="9pt" font-weight="normal">

	                            <fo:table  text-align="center"  border="solid 0.1mm black" space-before.optimum="5pt">
	                               <fo:table-column column-width="1cm" border="solid 0.1mm black"/>
	                               <fo:table-column column-width="1.4cm" border="solid 0.1mm black"/>
	                               <fo:table-column column-width="auto" border="solid 0.1mm black"/>
	                               <fo:table-column column-width="auto" border="solid 0.1mm black"/>
	                               <fo:table-column column-width="1.7cm" border="solid 0.1mm black"/>
	                               <fo:table-column column-width="1.7cm" border="solid 0.1mm black"/>
	                               <fo:table-column column-width="auto" border="solid 0.1mm black"/>
	                               <fo:table-column column-width="auto" border="solid 0.1mm black"/>
	                               <fo:table-column column-width="auto" border="solid 0.1mm black"/>
	                               <fo:table-column column-width="1cm" border="solid 0.1mm black"/>
	                               <fo:table-column column-width="1cm" border="solid 0.1mm black"/>
	                               <fo:table-column column-width="auto" border="solid 0.1mm black"/>
	                               <fo:table-column column-width="6cm" border="solid 0.1mm black"/>

		                               <fo:table-header >
			                               <fo:table-row line-height="20pt">
									            <fo:table-cell  margin-left="0.2mm" margin-right="0.2mm">
									            	<fo:block font-weight="bold"> Ticket No. </fo:block>
									            </fo:table-cell>
									            <fo:table-cell >
									            	<fo:block font-weight="bold">Patient Name</fo:block>
									            </fo:table-cell>
									            <fo:table-cell >
									            	<fo:block font-weight="bold">Insurance  </fo:block>
									            </fo:table-cell>
									            <fo:table-cell >
									            	<fo:block font-weight="bold">Account </fo:block>
									            </fo:table-cell>
									            <fo:table-cell >
									            	<fo:block font-weight="bold">Posted Date</fo:block>
									            </fo:table-cell>
									            <fo:table-cell >
									            	<fo:block font-weight="bold">Date of Check  </fo:block>
									            </fo:table-cell>

									            <fo:table-cell >
									            	<fo:block font-weight="bold">Check Number</fo:block>
									            </fo:table-cell>
									            <fo:table-cell >
									            	<fo:block font-weight="bold">Posted By</fo:block>
									            </fo:table-cell>
									            <fo:table-cell >
									            	<fo:block font-weight="bold">Provider</fo:block>
									            </fo:table-cell>
									            <fo:table-cell >
									            	<fo:block font-weight="bold">FCN/ AR</fo:block>
									            </fo:table-cell>
									            <fo:table-cell >
									            	<fo:block font-weight="bold">Time Taken</fo:block>
									            </fo:table-cell>
									            <fo:table-cell >
									            	<fo:block font-weight="bold">Total Posted Amount</fo:block>
									            </fo:table-cell>
									            <fo:table-cell margin-left="0.3mm" margin-right="0.2mm" >
									            	<fo:block font-weight="bold"> Total Posted Detail</fo:block>
									            </fo:table-cell>
											</fo:table-row>
									   </fo:table-header>

		                               <fo:table-body  height="9in" border="solid 0.1mm black">
										<xsl:for-each select="list/PaymentPostingByOffSetManager">
											<fo:table-row border="solid 0.1mm black" padding-top="1cm">

												<fo:table-cell >
	                                                <fo:block>
	                                                	<xsl:value-of select="id"/>
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
														<xsl:value-of select="patientName"/>
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
	                                                	<xsl:value-of select="paymentBatch/insurance/name"/>
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
	                                                	<xsl:value-of select="accountNumber"/>
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
	                                                	<xsl:value-of select="createdOn"/>
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
														<xsl:value-of select="dateOfCheck"/>
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
														<xsl:value-of select="checkNumber"/>
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
														<xsl:value-of select="createdBy/name"/>
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
														<xsl:value-of select="paymentBatch/doctor/name"/>
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
														<xsl:value-of select="fcnOrAR"/>
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
														<xsl:value-of select="time"/>
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
														$<xsl:value-of select="totalPosted"/>
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
														<fo:table display-align="center">
															<fo:table-header >
													            <fo:table-cell >
													            	<fo:block font-weight="bold">CPT</fo:block>
													            </fo:table-cell>
													            <fo:table-cell >
													            	<fo:block font-weight="bold">DOS FROM</fo:block>
													            </fo:table-cell>
													            <fo:table-cell >
													            	<fo:block font-weight="bold">DOS TO</fo:block>
													            </fo:table-cell>
													            <fo:table-cell >
													            	<fo:block font-weight="bold">AMOUNT</fo:block>
													            </fo:table-cell>
												            </fo:table-header>
												            <fo:table-body>
												            	<xsl:for-each select="OffsetPostingRecord">
													            	<fo:table-row line-height="15pt" border="solid 0.1mm black">
																		<fo:table-cell margin-left="0.2mm" margin-right="0.1mm" >
							                                                <fo:block wrap-option="wrap">
																				<xsl:value-of select="cpt"/>
																			</fo:block>
																		</fo:table-cell>
																		<fo:table-cell margin-right="0.2mm">
							                                                <fo:block>
																				<xsl:value-of select="dosFrom"/>
																			</fo:block>
																		</fo:table-cell>
																		<fo:table-cell margin-left="0.2mm">
							                                                <fo:block>
																				<xsl:value-of select="dosTo"/>
																			</fo:block>
																		</fo:table-cell>
																		<fo:table-cell >
							                                                <fo:block>
																				$<xsl:value-of select="amount"/>
																			</fo:block>
																		</fo:table-cell>
													            	</fo:table-row>
												            	</xsl:for-each>
												            </fo:table-body>
														</fo:table>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:for-each>
									</fo:table-body>
							</fo:table >
						</fo:block>
						</xsl:otherwise>
					</xsl:choose>
               	</fo:flow>
             </fo:page-sequence>
    </fo:root>
	</xsl:template>
</xsl:stylesheet>
